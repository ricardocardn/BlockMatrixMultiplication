package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;

public abstract class MatrixTransformer<Type> {
    protected final static int BLOCK_SIZE = 2;

    public abstract Matrix<Type> execute(Matrix<Type> matrix);
}
