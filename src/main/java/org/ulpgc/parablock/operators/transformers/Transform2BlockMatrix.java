package org.ulpgc.parablock.operators.transformers;

import org.ulpgc.parablock.builders.block.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixTransformer;

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
        BlockMatrixBuilder<Type> matrixBuilder = new BlockMatrixBuilder<>(denseMatrix.size()/BLOCK_SIZE);

        for (int ii = 0; ii < denseMatrix.size()/BLOCK_SIZE; ii++)
            for (int jj = 0; jj < denseMatrix.size()/BLOCK_SIZE; jj++)
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
                        denseMatrix.get(ii*BLOCK_SIZE + i, jj*BLOCK_SIZE + j)
                );
            }
        }

        return blockBuilder.get();
    }
}
