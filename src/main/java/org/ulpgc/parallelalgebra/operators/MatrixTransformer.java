package org.ulpgc.parallelalgebra.operators;

import org.ulpgc.parallelalgebra.matrix.Matrix;

public interface MatrixTransformer<Type> {
    Matrix<Type> execute(Matrix<Type> matrix);
}
