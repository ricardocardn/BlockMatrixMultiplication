package org.ulpgc.parablock.operators.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixMultiplication;

import java.util.Random;

public class MultiplicationTests {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static int SIZE = AVAILABLE_PROCESSORS;
    private final static int BLOCK_SIZE = 2048/AVAILABLE_PROCESSORS;
    private final MatrixMultiplication blockMatrixMultiplication = new ParallelBlockMatrixMultiplication();
    private final MatrixMultiplication denseMatrixMultiplication = new DenseMatrixMultiplication();
    private final ParallelBlockMatrixMultiplication parallelMultiplication = new ParallelBlockMatrixMultiplication();
    private final Matrix matrixA = buildBlockMatrix();
    private final Matrix matrixC = buildSubDenseMatrix(SIZE*BLOCK_SIZE);

    @Test
    public void parallelMultiplicationTest() {
        parallelMultiplication.multiply(matrixA, matrixA);
    }

    @Test
    public void blockMultiplicationTest() {
        blockMatrixMultiplication.multiply(matrixA, matrixA);
    }

    @Test
    public void denseMultiplicationTest() {
        denseMatrixMultiplication.multiply(matrixA, matrixA);
    }

    private Matrix buildBlockMatrix() {
        BlockMatrixBuilder blockMatrixBuilder = new BlockMatrixBuilder(SIZE);

        for (int ii = 0; ii < SIZE; ii++) {
            for (int jj = 0; jj < SIZE; jj++) {
                DenseMatrix denseMatrix = buildSubDenseMatrix(BLOCK_SIZE);
                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrix
                );
            }
        }
        return blockMatrixBuilder.get();
    }

    private DenseMatrix buildSubDenseMatrix(int size) {
        double[][] denseMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(size, denseMatrix);
    }
}
