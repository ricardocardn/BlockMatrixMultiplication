package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixMultiplication<Type> {
    Matrix<Type> multiply(Matrix<Type> matrixA, Matrix<Type> matrixB) throws Exception;
}
