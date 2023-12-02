package org.ulpgc.parablock.operators.tests.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.ParallelBlockMatrixMultiplication;

import java.util.Map;
import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class ParallelBlockMultiplicationTests {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static int SIZE = AVAILABLE_PROCESSORS;
    private final static int BLOCK_SIZE = 4096/AVAILABLE_PROCESSORS;
    private final MatrixMultiplication blockMatrixMultiplication = new ParallelBlockMatrixMultiplication();
    private final Matrix matrixA = buildBlockMatrix();
    private final Matrix matrixB = buildBlockMatrix();
    private final Matrix matrixC = buildBlockMatrix();

    @Test
    public void blockMultiplicationTest() {
        System.out.println(AVAILABLE_PROCESSORS);
        Matrix AB = blockMatrixMultiplication.multiply(matrixA, matrixB);
        Matrix AB_C = blockMatrixMultiplication.multiply(AB, matrixC);

        Matrix BC = blockMatrixMultiplication.multiply(matrixB, matrixC);
        Matrix A_BC = blockMatrixMultiplication.multiply(matrixA, BC);

        assertEquals(AB_C, A_BC);
    }

    private Matrix buildBlockMatrix() {
        BlockMatrixBuilder blockMatrixBuilder = new BlockMatrixBuilder(SIZE);

        for (int ii = 0; ii < SIZE; ii++) {
            for (int jj = 0; jj < SIZE; jj++) {
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
