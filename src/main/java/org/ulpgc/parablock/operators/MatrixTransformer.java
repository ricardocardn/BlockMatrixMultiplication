package org.ulpgc.parablock.operators;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixTransformer {
    public Matrix execute(Matrix matrix);
}
