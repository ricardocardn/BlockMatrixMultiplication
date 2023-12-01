package org.ulpgc.parablock.operators.tests.multipliers.distributed;

import org.testng.annotations.Test;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.multipliers.BlockMatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.DenseMatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributeMultiplicationClient;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributedMultiplicationOrchestrator;

import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class HazelCastMultiplicationTests {

    @Test
    public void multiplicationTests() {
        DenseMatrix matrixA = buildDenseMatrix(128);
        DenseMatrix matrixB = buildDenseMatrix(128);

        DenseMatrixMultiplication denseMultiplier = new DenseMatrixMultiplication();
        Matrix standardResult = denseMultiplier.multiply(matrixA, matrixB);

        DistributeMultiplicationClient client = new DistributeMultiplicationClient();
        client.start();

        DistributedMultiplicationOrchestrator orchestrator = new DistributedMultiplicationOrchestrator();
        Matrix result = orchestrator.multiply(matrixA, matrixB);

        assertEquals(standardResult, result);
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
