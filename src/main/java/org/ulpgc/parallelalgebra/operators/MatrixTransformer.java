package org.ulpgc.parallelalgebra.operators;

import org.ulpgc.parallelalgebra.matrix.Matrix;

public abstract class MatrixTransformer<Type> {
    protected final static int BLOCK_SIZE = 2;

    public abstract Matrix<Type> execute(Matrix<Type> matrix);
}
