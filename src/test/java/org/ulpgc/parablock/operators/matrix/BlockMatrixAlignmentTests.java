package org.ulpgc.parablock.operators.matrix;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.block.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class BlockMatrixAlignmentTests {
    static int SIZE = 6;
    static int BLOCK_SIZE = 2;

    @Test
    public void horizontalAlignmentTest() {
        BlockMatrix<Integer> matrix = buildBlockMatrix();
        BlockMatrix<Integer> copy = matrix.deepCopy();

        for (int alignment = 0; alignment < matrix.size(); alignment++)
            for (int i = 0; i < alignment; i++)
                for (int j = 0; j < matrix.size(); j++)
                    matrix.horizontalAlign(matrix.size() - i);

        assertEquals(matrix, copy);
    }

    @Test
    public void verticalAlignmentTest() {
        BlockMatrix<Integer> matrix = buildBlockMatrix();
        BlockMatrix<Integer> copy = matrix.deepCopy();
        System.out.println(matrix);

        for (int alignment = 0; alignment < matrix.size(); alignment++)
            for (int i = 0; i < alignment + 1; i++)
                for (int j = 0; j < matrix.size(); j++)
                    matrix.verticalAlign(matrix.size() - j);

        assertEquals(matrix, copy);
    }

    private BlockMatrix<Integer> buildBlockMatrix() {
        BlockMatrixBuilder<Integer> blockMatrixBuilder = new BlockMatrixBuilder<>(SIZE/BLOCK_SIZE);

        for (int ii = 0; ii < SIZE/BLOCK_SIZE; ii++) {
            for (int jj = 0; jj < SIZE/BLOCK_SIZE; jj++) {
                DenseMatrix<Integer> denseMatrix = buildSubDenseMatrix();
                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrix
                );
            }
        }
        return blockMatrixBuilder.get();
    }

    private DenseMatrix<Integer> buildSubDenseMatrix() {
        DenseMatrixBuilder<Integer> denseMatrixBuilder = new DenseMatrixBuilder<>(BLOCK_SIZE);
        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                denseMatrixBuilder.set(
                        new Coordinate(i, j),
                        new Random().nextInt()
                );
            }
        }
        return denseMatrixBuilder.get();
    }
}
