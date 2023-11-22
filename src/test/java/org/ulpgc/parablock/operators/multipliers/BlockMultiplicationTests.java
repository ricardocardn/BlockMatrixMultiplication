package org.ulpgc.parablock.operators.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;

import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class BlockMultiplicationTests {
    private static int SIZE = 16;
    private static int BLOCK_SIZE = 4;

    @Test
    public void blockMultiplicationTest() {
        Matrix matrixA = buildBlockMatrix();
        Matrix matrixB = buildBlockMatrix();
        Matrix matrixC = buildBlockMatrix();

        BlockMatrixMultiplication blockMatrixMultiplication = new BlockMatrixMultiplication();
        Matrix AB = blockMatrixMultiplication.multiply(matrixA, matrixB);
        Matrix AB_C = blockMatrixMultiplication.multiply(AB, matrixC);

        Matrix BC = blockMatrixMultiplication.multiply(matrixB, matrixC);
        Matrix A_BC = blockMatrixMultiplication.multiply(matrixA, BC);

        assertEquals(AB_C, A_BC);
    }

    private Matrix buildBlockMatrix() {
        BlockMatrixBuilder blockMatrixBuilder = new BlockMatrixBuilder(SIZE/BLOCK_SIZE);

        for (int ii = 0; ii < SIZE/BLOCK_SIZE; ii++) {
            for (int jj = 0; jj < SIZE/BLOCK_SIZE; jj++) {
                DenseMatrix denseMatrix = buildSubDenseMatrix();
                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrix
                );
            }
        }
        return blockMatrixBuilder.get();
    }

    private DenseMatrix buildSubDenseMatrix() {
        double[][] denseMatrix = new double[SIZE / BLOCK_SIZE][SIZE / BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(SIZE / BLOCK_SIZE, denseMatrix);
    }
}
