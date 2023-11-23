package org.ulpgc.parablock.operators.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixMultiplication;

import java.util.Random;

public class MultiplicationTests {
    private static int SIZE = 2048;
    private static int BLOCK_SIZE = 250;
    private final MatrixMultiplication blockMatrixMultiplication = new ParallelBlockMatrixMultiplication();
    private final MatrixMultiplication denseMatrixMultiplication = new DenseMatrixMultiplication();
    private final Matrix matrixA = buildBlockMatrix();
    private final Matrix matrixC = buildSubDenseMatrix();
    @Test
    public void blockMultiplicationTest() {
        blockMatrixMultiplication.multiply(matrixA, matrixA);
    }

    @Test
    public void denseMultiplicationTest() {
        denseMatrixMultiplication.multiply(matrixC, matrixC);
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
        double[][] denseMatrix = new double[BLOCK_SIZE][BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(BLOCK_SIZE, denseMatrix);
    }
}
