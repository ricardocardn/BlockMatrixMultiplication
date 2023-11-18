package org.ulpgc.parablock.builders;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;

public interface MatrixBuilder<Type> {
    void set(Coordinate coordinate, Object value);
    Matrix get();
}
