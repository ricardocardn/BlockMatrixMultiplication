package org.ulpgc.parablock.operators.transformers;

import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixTransformer;

public class Transform2DenseMatrix<Type> extends MatrixTransformer<Type> {
    @Override
    public DenseMatrix<Type> execute(Matrix<Type> matrix) {
        return isOfBlocks(matrix) ? transformFromBlocks(matrix) : (DenseMatrix<Type>) matrix;
    }

    private boolean isOfBlocks(Matrix<Type> matrix) {
        return matrix instanceof BlockMatrix<?>;
    }

    private DenseMatrix<Type> transformFromBlocks(Matrix<Type> matrix) {
        BlockMatrix<Type> blockMatrix = (BlockMatrix<Type>) matrix;
        DenseMatrixBuilder<Type> matrixBuilder = new DenseMatrixBuilder<>(size(blockMatrix));

        for (int ii = 0; ii < blockMatrix.size(); ii++)
            for (int jj = 0; jj < blockMatrix.size(); jj++)
                insertSubBlock(blockMatrix, matrixBuilder, ii, jj);

        return matrixBuilder.get();
    }

    private int size(BlockMatrix<Type> blockMatrix) {
        return blockMatrix.size() * BLOCK_SIZE;
    }

    private void insertSubBlock(BlockMatrix<Type> blockMatrix, DenseMatrixBuilder<Type> matrixBuilder, int ii, int jj) {
        for (int i = 0; i < BLOCK_SIZE; i++)
            for (int j = 0; j < BLOCK_SIZE; j++)
                matrixBuilder.set(
                        new Coordinate(ii*BLOCK_SIZE + i, jj*BLOCK_SIZE + j),
                        blockMatrix.get(ii, jj).get(i, j)
                );
    }

}
