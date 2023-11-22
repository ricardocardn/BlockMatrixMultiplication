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
    private static int SIZE = 4;
    private static int BLOCK_SIZE = 2;

    @Test
    public void testTransformEquals() {
        Matrix blockMatrix = buildBlockMatrix();

        org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix transformer2Dense = new org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix();
        DenseMatrix denseMatrix = transformer2Dense.execute(blockMatrix);

        org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix transform2Block = new org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix();
        BlockMatrix blockAfterTransform = transform2Block.execute(denseMatrix);

        assertEquals(blockMatrix, blockAfterTransform);
    }

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
