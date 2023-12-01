package org.ulpgc.parablock.operators.transformers;

import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixTransformer;

public class Transform2BlockMatrix implements MatrixTransformer {
    private static int BLOCK_SIZE;

    @Override
    public BlockMatrix execute(Matrix matrix) {
        return isDense(matrix) ? transformFromDense(matrix) : (BlockMatrix) matrix;
    }

    private boolean isDense(Matrix matrix) {
        return matrix instanceof DenseMatrix;
    }

    private BlockMatrix transformFromDense(Matrix matrix) {
        BLOCK_SIZE = Math.min(Runtime.getRuntime().availableProcessors(), matrix.size());
        DenseMatrix denseMatrix = (DenseMatrix) matrix;
        int size = (int) Math.ceil(denseMatrix.size()/ (double) BLOCK_SIZE);
        BlockMatrixBuilder matrixBuilder = new BlockMatrixBuilder(size);

        for (int ii = 0; ii < size; ii++)
            for (int jj = 0; jj < size; jj++)
                matrixBuilder.set(
                        new Coordinate(ii, jj),
                        getBlock(denseMatrix, ii, jj)
                );

        return matrixBuilder.get();
    }

    private DenseMatrix getBlock(DenseMatrix denseMatrix, int ii, int jj) {
        DenseMatrixBuilder blockBuilder = new DenseMatrixBuilder(BLOCK_SIZE);
        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                try {
                    blockBuilder.set(
                            new Coordinate(i, j),
                            denseMatrix.get(ii * BLOCK_SIZE + i, jj * BLOCK_SIZE + j)
                    );
                } catch (Exception e) {
                    blockBuilder.set(
                            new Coordinate(i, j),
                            0
                    );
                }
            }
        }

        return blockBuilder.get();
    }
}
