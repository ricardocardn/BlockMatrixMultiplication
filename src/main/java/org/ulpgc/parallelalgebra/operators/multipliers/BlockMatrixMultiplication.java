package org.ulpgc.parallelalgebra.operators.multipliers;

import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixMultiplication;
import org.ulpgc.parallelalgebra.operators.MatrixTransformer;
import org.ulpgc.parallelalgebra.operators.transformers.Transform2BlockMatrix;

public class BlockMatrixMultiplication<Type> implements MatrixMultiplication<Type> {
    private final MatrixTransformer<Type> transformer = new Transform2BlockMatrix<>();

    @Override
    public BlockMatrix<Type> multiply(Matrix matrixA, Matrix matrixB) {
        return null;
    }
}
