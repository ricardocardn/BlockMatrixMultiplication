package org.ulpgc.parablock.operators.multipliers.distributed;

import com.google.gson.Gson;
import com.hazelcast.client.Client;
import com.hazelcast.client.ClientService;
import com.hazelcast.cluster.Cluster;
import com.hazelcast.cluster.Member;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.multimap.MultiMap;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.adders.BlockMatrixAddition;

import java.rmi.server.UID;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DistributedMultiplicationOrchestrator implements MatrixMultiplication {
    private final HazelcastInstance hazelcastInstance;
    private final ClientService clientService;

    public DistributedMultiplicationOrchestrator() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        clientService = hazelcastInstance.getClientService();
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
            map.put(member.getUuid(), new int[]{i, matrixA.size()/(members.size())});
            i = i + matrixA.size()/(members.size());
        }

        return null;
    }

    private int connectedClients() {
        return clientService.getConnectedClients().size();
    }
}
