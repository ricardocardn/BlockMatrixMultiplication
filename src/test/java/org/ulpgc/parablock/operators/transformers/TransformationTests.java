package org.ulpgc.parablock.operators.transformers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.MatrixBuilder;
import org.ulpgc.parablock.builders.block.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class TransformationTests {

    @Test
    public void testTransformEquals() {
        Matrix blockMatrix = buildBlockMatrix();

        Transform2DenseMatrix<Integer> transformer2Dense = new Transform2DenseMatrix<>();
        DenseMatrix<Integer> denseMatrix = transformer2Dense.execute(blockMatrix);

        Transform2BlockMatrix<Integer> transform2Block = new Transform2BlockMatrix<>();
        BlockMatrix<Integer> blockAfterTransform = transform2Block.execute(denseMatrix);

        assertEquals(blockMatrix, blockAfterTransform);
    }

    private Matrix buildBlockMatrix() {
        int size = 4;
        int blockSize = 2;

        MatrixBuilder<Integer> blockMatrixBuilder = new BlockMatrixBuilder<>(size/blockSize);

        for (int ii = 0; ii < 2; ii++) {
            for (int jj = 0; jj < 2; jj++) {
                DenseMatrix<Integer> denseMatrix = buildSubDenseMatrix(size, blockSize);
                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrix
                );
            }
        }
        return blockMatrixBuilder.get();
    }

    private DenseMatrix<Integer> buildSubDenseMatrix(int size, int blockSize) {
        DenseMatrixBuilder<Integer> denseMatrixBuilder = new DenseMatrixBuilder<>(size / blockSize);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                denseMatrixBuilder.set(
                        new Coordinate(i, j),
                        new Random().nextInt()
                );
            }
        }
        return denseMatrixBuilder.get();
    }
}
