package org.ulpgc.parallelalgebra.operators.transformers;

import org.ulpgc.parallelalgebra.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixTransformer;

public class Transform2DenseMatrix<Type> extends MatrixTransformer<Type> {
    @Override
    public DenseMatrix<Type> execute(Matrix matrix) {
        return isOfBlocks(matrix) ? transformFromBlocks(matrix) : (DenseMatrix<Type>) matrix;
    }

    private boolean isOfBlocks(Matrix matrix) {
        return matrix instanceof BlockMatrix<?>;
    }

    private DenseMatrix<Type> transformFromBlocks(Matrix matrix) {
        BlockMatrix<Type> blockMatrix = (BlockMatrix<Type>) matrix;
        DenseMatrixBuilder<Type> matrixBuilder = new DenseMatrixBuilder<>(size(blockMatrix));

        for (int ii = 0; ii < blockMatrix.size(); ii++)
            for (int jj = 0; jj < blockMatrix.size(); jj++)
                insertSubBlock(blockMatrix, matrixBuilder, ii, jj);

        return matrixBuilder.get();
    }

    private void insertSubBlock(BlockMatrix<Type> blockMatrix, DenseMatrixBuilder<Type> matrixBuilder, int ii, int jj) {
        for (int i = 0; i < BLOCK_SIZE; i++)
            for (int j = 0; j < BLOCK_SIZE; j++)
                matrixBuilder.set(
                        new Coordinate(ii*BLOCK_SIZE + i, jj*BLOCK_SIZE + j),
                        blockMatrix.get(ii, jj).get(i, j)
                );
    }

    private int size(BlockMatrix<Type> blockMatrix) {
        return blockMatrix.size() * BLOCK_SIZE;
    }
}
