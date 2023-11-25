package org.ulpgc.parablock.operators.multipliers.distributed;

import com.google.gson.Gson;

import com.hazelcast.client.ClientService;
import com.hazelcast.cluster.Cluster;
import com.hazelcast.cluster.Member;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.adders.BlockMatrixAddition;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DistributedMultiplicationOrchestrator implements MatrixMultiplication {
    private final HazelcastInstance hazelcastInstance;

    public DistributedMultiplicationOrchestrator() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        ClientService clientService = hazelcastInstance.getClientService();
    }

    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        IMap<UUID, int[]> map = hazelcastInstance.getMap("MatrixMultiplication");
        Cluster cluster = hazelcastInstance.getCluster();
        Set<Member> members = cluster.getMembers();

        IList<String> matrices = hazelcastInstance.getList("Matrices");
        matrices.add(new Gson().toJson(matrixA));
        matrices.add(new Gson().toJson(matrixB));

        int i = 0;
        for (Member member : members) {
            System.out.println("Added member for mult.: " + member.getUuid());
            System.out.println("    |- Assigned rows: " + i + " - " + (i + matrixA.size()/(members.size())));
            map.put(member.getUuid(), new int[]{i, matrixA.size()/(members.size())});
            i = i + matrixA.size()/(members.size());
        }

        multiplySubMatrices(map);
        return calculateResult();
    }

    private void multiplySubMatrices(IMap<UUID, int[]> map) {
        IList<String> matrices = hazelcastInstance.getList("Matrices");
        Matrix matrixA = new Gson().fromJson(matrices.get(0), BlockMatrix.class);
        Matrix matrixB = new Gson().fromJson(matrices.get(1), BlockMatrix.class);

        DistributedTileMultiplication multiplier = new DistributedTileMultiplication();
        UUID uuid = hazelcastInstance.getLocalEndpoint().getUuid();
        int[] pos = map.get(uuid);

        hazelcastInstance.getMap("results").put(
                uuid,
                multiplier.multiply(matrixA, matrixB, pos[0], pos[1])
        );
    }

    private Matrix calculateResult() {
        BlockMatrixAddition blockMatrixAddition = new BlockMatrixAddition();
        IMap<Object, Object> results = hazelcastInstance.getMap("results");

        Matrix finalMatrix = null;
        for (Object matrix : results.keySet()) {
            if (finalMatrix == null)
                finalMatrix = (BlockMatrix) matrix;
            else
                finalMatrix = blockMatrixAddition.add(finalMatrix, (BlockMatrix) matrix);
        }

        return finalMatrix;
    }
}
