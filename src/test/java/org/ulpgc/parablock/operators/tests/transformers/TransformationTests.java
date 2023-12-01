package org.ulpgc.parablock.operators.tests.transformers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class TransformationTests {
    private static final int SIZE = 33;

    @Test
    public void testTransformEquals() {
        Matrix denseMatrix = buildDenseMatrix();

        Transform2BlockMatrix transform2BlockMatrix = new Transform2BlockMatrix();
        BlockMatrix blockMatrix = transform2BlockMatrix.execute(denseMatrix);

        Transform2DenseMatrix transform2DenseMatrix = new Transform2DenseMatrix();
        DenseMatrix result = transform2DenseMatrix.execute(blockMatrix);

        assertEquals(denseMatrix, subMatrix(result));
    }

    private Matrix subMatrix(DenseMatrix result) {
        DenseMatrixBuilder denseMatrixBuilder = new DenseMatrixBuilder(SIZE);
        for (int i = 0; i < SIZE; i++)
             for (int j = 0; j < SIZE; j++)
                 denseMatrixBuilder.set(
                         new Coordinate(i, j),
                         result.get(i, j)
                 );

        return denseMatrixBuilder.get();
    }

    private DenseMatrix buildDenseMatrix() {
        DenseMatrixBuilder denseMatrixBuilder = new DenseMatrixBuilder(SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                denseMatrixBuilder.set(
                        new Coordinate(i, j),
                        new Random().nextDouble()
                );
            }
        }
        return denseMatrixBuilder.get();
    }
}
