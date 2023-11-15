package org.ulpgc.parallelalgebra.operators.multipliers;

import org.ulpgc.parallelalgebra.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixMultiplication;
import org.ulpgc.parallelalgebra.operators.MatrixTransformer;
import org.ulpgc.parallelalgebra.operators.transformers.Transform2DenseMatrix;

public class DenseMatrixMultiplication<Type> implements MatrixMultiplication<Type> {
    private final MatrixTransformer<Type> transformer = new Transform2DenseMatrix<>();

    @Override
    public DenseMatrix<Type> multiply(Matrix<Type> A, Matrix<Type> B) {
        DenseMatrix<Type> matrixA = (DenseMatrix<Type>) transformer.execute(A);
        DenseMatrix<Type> matrixB = (DenseMatrix<Type>) transformer.execute(A);

        DenseMatrixBuilder<Type> matrixBuilder = new DenseMatrixBuilder<>(matrixA.size());

        for (int i = 0; i < matrixA.size(); i++)
            for (int k = 0; k < matrixA.size(); k++)
                for (int j = 0; j < matrixA.size(); j++)
                    break;

        return matrixBuilder.get();
    }
}
