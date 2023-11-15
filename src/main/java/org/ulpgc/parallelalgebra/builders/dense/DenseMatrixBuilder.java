package org.ulpgc.parallelalgebra.builders.dense;

import org.ulpgc.parallelalgebra.builders.MatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;

public class DenseMatrixBuilder<Type> implements MatrixBuilder<Type> {
    private final int size;
    private Type[][] matrix;

    public DenseMatrixBuilder(int size) {
        this.size = size;
        matrix = (Type[][]) new Object[size][size];
    }

    @Override
    public void set(Coordinate coordinate, Object value) {
        matrix[coordinate.i][coordinate.j] = (Type) value;
    }

    @Override
    public Matrix<Type> get() {
        return new DenseMatrix<Type>(size, matrix);
    }
}
