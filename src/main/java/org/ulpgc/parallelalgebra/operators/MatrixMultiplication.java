package org.ulpgc.parallelalgebra.operators;

import org.ulpgc.parallelalgebra.matrix.Matrix;

public interface MatrixMultiplication<Type> {
    Matrix<Type> multiply(Matrix<Type> matrixA, Matrix<Type> matrixB);
}
