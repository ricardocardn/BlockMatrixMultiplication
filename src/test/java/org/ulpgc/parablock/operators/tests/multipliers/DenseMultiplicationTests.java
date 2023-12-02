package org.ulpgc.parablock.operators.tests.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.DenseMatrixMultiplication;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class DenseMultiplicationTests {
    private static int SIZE = 10000;

    @Test
    public void multiplyTest() {
        DenseMatrix matrixA = buildDenseMatrix();
        DenseMatrix matrixB = buildDenseMatrix();
        DenseMatrix matrixC = buildDenseMatrix();

        System.out.println("Starting");

        MatrixMultiplication denseMultiplier = new DenseMatrixMultiplication();
        Matrix AB = denseMultiplier.multiply(matrixA, matrixB);
        Matrix AB_C = denseMultiplier.multiply(AB, matrixC);

        Matrix BC = denseMultiplier.multiply(matrixB, matrixC);
        Matrix A_BC = denseMultiplier.multiply(matrixA, BC);

        assertEquals(AB_C, A_BC);
    }

    private DenseMatrix buildDenseMatrix() {
        double[][] denseMatrix = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }

        return new DenseMatrix(SIZE, denseMatrix);
    }
}
