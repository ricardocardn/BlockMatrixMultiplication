package org.ulpgc.parablock.operators.multipliers.distributed;

import com.google.gson.Gson;
import com.hazelcast.client.ClientService;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

import java.util.Map;
import java.util.UUID;

public class DistributeMultiplicationClient extends Thread {
    private final HazelcastInstance hazelcastInstance;

    public DistributeMultiplicationClient() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        ClientService clientService = hazelcastInstance.getClientService();
    }

    @Override
    public void run() {
        Map<UUID, int[]> map = hazelcastInstance.getMap("MatrixMultiplication");
        UUID uuid = hazelcastInstance.getLocalEndpoint().getUuid();
        hazelcastInstance.getTopic("AvailableClients").publish(uuid.toString());

        while (!map.containsKey(uuid)) {}

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
}
