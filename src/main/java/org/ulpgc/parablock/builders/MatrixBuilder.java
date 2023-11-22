package org.ulpgc.parablock.builders;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;

public interface MatrixBuilder {
    void set(Coordinate coordinate, Object value);
    Matrix get();
}
