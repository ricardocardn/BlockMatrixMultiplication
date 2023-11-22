package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.DenseMatrix;

public interface MatrixAddition {
    DenseMatrix add(Matrix matrixA, Matrix matrixB);
}
