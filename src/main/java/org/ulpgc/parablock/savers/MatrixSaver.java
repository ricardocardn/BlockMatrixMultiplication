package org.ulpgc.parablock.savers;

import org.ulpgc.parablock.matrix.Matrix;

public interface MatrixSaver {
    void store(Matrix matrix, String file, String name);
}
