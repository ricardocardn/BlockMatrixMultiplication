package org.ulpgc.parablock.operators.multipliers.distributed;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IList;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;
import org.ulpgc.parablock.matrix.Matrix;

import java.util.Map;
import java.util.UUID;

public class DistributeMultiplicationClient extends Thread {
    private final HazelcastInstance hazelcastClient;

    public DistributeMultiplicationClient() {
        ClientConfig clientConfig = new ClientConfig();
        hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Override
    public void run() {
        Map<UUID, int[]> map = hazelcastClient.getMap("MatrixMultiplication");
        UUID uuid = hazelcastClient.getLocalEndpoint().getUuid();
        while (!map.containsKey(uuid)) {}

        IList<Matrix> matrices = hazelcastClient.getList("Matrices");
        Matrix matrixA = matrices.get(0);
        Matrix matrixB = matrices.get(1);

        DistributedTileMultiplication multiplier = new DistributedTileMultiplication();
        int[] pos = map.get(uuid);

        hazelcastClient.getMap("results").put(
                uuid,
                multiplier.multiply(matrixA, matrixB, pos[0], pos[1])
        );
    }
}
