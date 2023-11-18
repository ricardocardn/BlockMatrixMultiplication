package org.ulpgc.parallelalgebra.operators.multipliers;

import org.ulpgc.parallelalgebra.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;
import org.ulpgc.parallelalgebra.operators.MatrixMultiplication;
import org.ulpgc.parallelalgebra.operators.transformers.Transform2DenseMatrix;

import java.lang.reflect.InvocationTargetException;

public class DenseMatrixMultiplication<Type extends Number> implements MatrixMultiplication<Type> {
    private final Transform2DenseMatrix<Type> transformer = new Transform2DenseMatrix<>();

    @Override
    public DenseMatrix<Type> multiply(Matrix A, Matrix B) {
        DenseMatrix<Type> matrixA = transformer.execute(A);
        DenseMatrix<Type> matrixB = transformer.execute(B);

        DenseMatrixBuilder<Type> matrixBuilder = new DenseMatrixBuilder<>(matrixA.size());

        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixA.size(); j++) {
                Number value = zero();
                for (int k = 0; k < matrixA.size(); k++) {
                    // Multiplication implementation
                    continue;
                }
            }
        }

        return matrixBuilder.get();
    }

    private Type zero() {
        try {
            return (Type) Number.class.getDeclaredMethod("valueOf", String.class).invoke(null, "0");
        } catch (Exception e) {
            throw new RuntimeException("Invalid matrix type for multiplication");
        }
    }
}

