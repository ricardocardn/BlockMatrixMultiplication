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
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.MatrixAddition;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.adders.BlockMatrixAddition;
import org.ulpgc.parablock.operators.adders.DenseMatrixAddition;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DistributedMultiplicationOrchestrator implements MatrixMultiplication {
    private final HazelcastInstance hazelcastInstance;
    private final Transform2DenseMatrix transformer;

    public DistributedMultiplicationOrchestrator() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        ClientService clientService = hazelcastInstance.getClientService();
        transformer = new Transform2DenseMatrix();
    }

    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        IMap<UUID, int[]> map = hazelcastInstance.getMap("MatrixMultiplication");
        Cluster cluster = hazelcastInstance.getCluster();
        Set<Member> members = cluster.getMembers();

        IList<String> matrices = hazelcastInstance.getList("Matrices");
        matrices.add(new Gson().toJson(matrixA));
        matrices.add(new Gson().toJson(matrixB));

        int i = 0;
        int BLOCK_SIZE = Math.min(Runtime.getRuntime().availableProcessors(), matrixA.size());
        int SIZE = matrixA.size()/BLOCK_SIZE;

        for (Member member : members) {
            int rows = SIZE/(members.size());
            System.out.println("Added member for mult.: " + member.getUuid());
            System.out.println("    |- Assigned rows: " + i + " - " + (i + rows));
            map.put(member.getUuid(), new int[]{i, i + rows});
            i = i + rows;
        }

        multiplySubMatrices(map);
        return calculateResult();
    }

    private void multiplySubMatrices(IMap<UUID, int[]> map) {
        UUID uuid = hazelcastInstance.getLocalEndpoint().getUuid();
        IList<String> matrices = hazelcastInstance.getList("Matrices");
        Matrix matrixA = new Gson().fromJson(matrices.get(0), DenseMatrix.class);
        Matrix matrixB = new Gson().fromJson(matrices.get(1), DenseMatrix.class);

        DistributedTileMultiplication multiplier = new DistributedTileMultiplication();
        int[] pos = map.get(uuid);

        hazelcastInstance.getMap("results").put(
                uuid,
                new Gson().toJson(
                        new Transform2DenseMatrix().execute(
                            multiplier.multiply(matrixA, matrixB, pos[0], pos[1])
                        )
                )
        );
    }

    private Matrix calculateResult() {
        MatrixAddition denseMatrixAdder = new DenseMatrixAddition();
        IMap<UUID, String> results = hazelcastInstance.getMap("results");

        Matrix finalMatrix = null;
        for (UUID uuid : results.keySet()) {
            if (finalMatrix == null)
                finalMatrix = new Gson().fromJson(results.get(uuid), DenseMatrix.class);
            else
                finalMatrix = denseMatrixAdder.add(
                        finalMatrix, new Gson().fromJson(results.get(uuid), DenseMatrix.class)
                );
        }

        hazelcastInstance.shutdown();
        return finalMatrix;
    }
}
