package org.ulpgc.parablock.loaders;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixLoader {
    Matrix load(String file);
}
