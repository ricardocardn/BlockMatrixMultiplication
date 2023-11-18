package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixMultiplication<Type> {
    Matrix multiply(Matrix matrixA, Matrix matrixB) throws Exception;
}
