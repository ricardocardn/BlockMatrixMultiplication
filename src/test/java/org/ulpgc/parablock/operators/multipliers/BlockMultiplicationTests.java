package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;

import java.util.Random;

public class BlockMultiplicationTests {
    private static int SIZE = 4;
    private static int BLOCK_SIZE = 2;



    private Matrix buildBlockMatrix() {
        BlockMatrixBuilder blockMatrixBuilder = new BlockMatrixBuilder(SIZE/BLOCK_SIZE);

        for (int ii = 0; ii < 2; ii++) {
            for (int jj = 0; jj < 2; jj++) {
                DenseMatrix denseMatrix = buildSubDenseMatrix(SIZE, BLOCK_SIZE);
                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrix
                );
            }
        }
        return blockMatrixBuilder.get();
    }

    private DenseMatrix buildSubDenseMatrix(int SIZE, int BLOCK_SIZE) {
        double[][] denseMatrix = new double[SIZE / BLOCK_SIZE][SIZE / BLOCK_SIZE];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(SIZE / BLOCK_SIZE, denseMatrix);
    }
}
