package org.ulpgc.parablock.operators.transformers;

import org.ulpgc.parablock.builders.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixTransformer;

public class Transform2DenseMatrix implements MatrixTransformer {
    private static int BLOCK_SIZE;

    @Override
    public DenseMatrix execute(Matrix matrix) {
        return isOfBlocks(matrix) ? transformFromBlocks(matrix) : (DenseMatrix) matrix;
    }

    private boolean isOfBlocks(Matrix matrix) {
        return matrix instanceof BlockMatrix;
    }

    private DenseMatrix transformFromBlocks(Matrix matrix) {
        BLOCK_SIZE = blockSize(matrix);
        BlockMatrix blockMatrix = (BlockMatrix) matrix;
        DenseMatrixBuilder matrixBuilder = new DenseMatrixBuilder(size(blockMatrix));

        for (int ii = 0; ii < blockMatrix.size(); ii++)
            for (int jj = 0; jj < blockMatrix.size(); jj++)
                insertSubBlock(blockMatrix, matrixBuilder, ii, jj);

        return matrixBuilder.get();
    }

    private int size(BlockMatrix blockMatrix) {
        return blockMatrix.size() * BLOCK_SIZE;
    }

    private void insertSubBlock(BlockMatrix blockMatrix, DenseMatrixBuilder matrixBuilder, int ii, int jj) {
        for (int i = 0; i < BLOCK_SIZE; i++)
            for (int j = 0; j < BLOCK_SIZE; j++)
                matrixBuilder.set(
                        new Coordinate(ii*BLOCK_SIZE + i, jj*BLOCK_SIZE + j),
                        blockMatrix.get(ii, jj).get(i, j)
                );
    }

    private int blockSize(Matrix matrix) {
        return ((BlockMatrix) matrix).get(0,0).size();
    }

}
