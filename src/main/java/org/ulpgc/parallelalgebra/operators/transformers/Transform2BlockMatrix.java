package org.ulpgc.parallelalgebra.operators.transformers;

import org.ulpgc.parallelalgebra.builders.block.BlockMatrixBuilder;
import org.ulpgc.parallelalgebra.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixTransformer;

public class Transform2BlockMatrix<Type> extends MatrixTransformer<Type> {
    @Override
    public BlockMatrix<Type> execute(Matrix matrix) {
        return isDense(matrix) ? transformFromDense(matrix) : (BlockMatrix<Type>) matrix;
    }

    private boolean isDense(Matrix matrix) {
        return matrix instanceof DenseMatrix<?>;
    }

    private BlockMatrix<Type> transformFromDense(Matrix matrix) {
        DenseMatrix<Type> denseMatrix = (DenseMatrix<Type>) matrix;
        BlockMatrixBuilder<Type> matrixBuilder = new BlockMatrixBuilder<>(denseMatrix.size());

        for (int ii = 0; ii < denseMatrix.size(); ii = ii + BLOCK_SIZE)
            for (int jj = 0; jj < denseMatrix.size(); jj = jj + BLOCK_SIZE)
                matrixBuilder.set(
                        new Coordinate(ii, jj),
                        getBlock(denseMatrix, ii, jj)
                );

        return matrixBuilder.get();
    }

    private DenseMatrix<Type> getBlock(DenseMatrix<Type> denseMatrix, int ii, int jj) {
        DenseMatrixBuilder<Type> blockBuilder = new DenseMatrixBuilder<>(BLOCK_SIZE);
        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                blockBuilder.set(
                        new Coordinate(i, j),
                        denseMatrix.get(ii + i, jj + j)
                );
            }
        }

        return blockBuilder.get();
    }
}
