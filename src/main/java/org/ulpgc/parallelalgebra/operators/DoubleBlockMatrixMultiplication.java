package org.ulpgc.parallelalgebra.operators;

import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixMultiplication;
import org.ulpgc.parallelalgebra.operators.MatrixTransformer;
import org.ulpgc.parallelalgebra.operators.transformers.Transform2BlockMatrix;

public class DoubleBlockMatrixMultiplication implements MatrixMultiplication<Double> {
    private final Transform2BlockMatrix<Double> transformer = new Transform2BlockMatrix<>();
    private final MatrixMultiplication<Double> denseMultiplier = new DenseMatrixMultiplication<>();

    @Override
    public BlockMatrix<Double> multiply(Matrix A, Matrix B) {
        BlockMatrix<Double> matrixA = transformer.execute(A);
        BlockMatrix<Double> matrixB = transformer.execute(B);

        for (int cycle = 0; cycle < matrixA.size(); cycle++)
            continue;

        return null;
    }
}
