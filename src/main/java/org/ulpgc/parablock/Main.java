package org.ulpgc.parablock;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributeMultiplicationClient;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributedMultiplicationOrchestrator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        DenseMatrix matrixA = buildDenseMatrix(32);
        DenseMatrix matrixB = buildDenseMatrix(32);
        System.out.println(matrixA);
        System.out.println(matrixB);

        DistributeMultiplicationClient client = new DistributeMultiplicationClient();
        client.start();

        DistributedMultiplicationOrchestrator distributedMultiplicationOrchestrator = new DistributedMultiplicationOrchestrator();
        Matrix result = distributedMultiplicationOrchestrator.multiply(matrixA, matrixB);
        System.out.println(result);
    }

    private static DenseMatrix buildDenseMatrix(int size) {
        double[][] denseMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(size, denseMatrix);
    }
}
