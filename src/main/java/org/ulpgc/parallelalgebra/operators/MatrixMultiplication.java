package org.ulpgc.parallelalgebra.operators;

import org.ulpgc.parallelalgebra.matrix.Matrix;

public interface MatrixMultiplication<Type> {
    Matrix multiply(Matrix matrixA, Matrix matrixB);
}
