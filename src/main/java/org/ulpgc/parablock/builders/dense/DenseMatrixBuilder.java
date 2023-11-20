package org.ulpgc.parablock.builders.dense;

import org.ulpgc.parablock.builders.MatrixBuilder;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

public class DenseMatrixBuilder<Type extends Number> implements MatrixBuilder<Type> {
    private final int size;
    private final Type[][] matrix;

    public DenseMatrixBuilder(int size) {
        this.size = size;
        matrix = (Type[][]) new Number[size][size];
    }

    @Override
    public void set(Coordinate coordinate, Object value) {
        matrix[coordinate.i][coordinate.j] = (Type) value;
    }

    @Override
    public DenseMatrix<Type> get() {
        return new DenseMatrix<>(size, matrix);
    }
}
