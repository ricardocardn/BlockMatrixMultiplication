package org.ulpgc.parallelalgebra.builders;

import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;

public interface MatrixBuilder<Type> {
    void set(Coordinate coordinate, Object value);
    Matrix<Type> get();
}
