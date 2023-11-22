package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixMultiplication {
    Matrix multiply(Matrix matrixA, Matrix matrixB);
}
