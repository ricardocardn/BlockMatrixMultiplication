package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

public interface MatrixAddition<Type extends Number> {
    DenseMatrix<Type> add(Matrix<Type> matrixA, Matrix<Type> matrixB);
}
