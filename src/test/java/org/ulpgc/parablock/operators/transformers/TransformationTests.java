package org.ulpgc.parablock.operators.transformers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;

import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class TransformationTests {
    private static final int SIZE = 32;
    private static final int BLOCK_SIZE = Runtime.getRuntime().availableProcessors();

    @Test
    public void testTransformEquals() {
        Matrix blockMatrix = buildBlockMatrix();

        Transform2DenseMatrix transformer2Dense = new Transform2DenseMatrix();
        DenseMatrix denseMatrix = transformer2Dense.execute(blockMatrix);

        Transform2BlockMatrix transform2Block = new Transform2BlockMatrix();
        BlockMatrix blockAfterTransform = transform2Block.execute(denseMatrix);

        assertEquals(blockMatrix, blockAfterTransform);
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
