package org.ulpgc.parablock.operators.multipliers.distributed;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;

import java.util.Random;

public class DistributedMultiplicationTests {
    public static void main(String[] args) {
        DenseMatrix matrixA = buildDenseMatrix(128);
        DenseMatrix matrixB = buildDenseMatrix(128);

        DistributeMultiplicationClient client = new DistributeMultiplicationClient();
        client.start();

        DistributedMultiplicationOrchestrator orchestrator = new DistributedMultiplicationOrchestrator();
        Matrix result = orchestrator.multiply(matrixA, matrixB);
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
