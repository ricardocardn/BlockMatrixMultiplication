package org.ulpgc.parablock.builders;

import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;

public class DenseMatrixBuilder {
    private final int size;
    private final double[][] matrix;

    public DenseMatrixBuilder(int size) {
        this.size = size;
        matrix = new double[size][size];
    }

    public void set(Coordinate coordinate, double value) {
        matrix[coordinate.i][coordinate.j] = value;
    }

    public DenseMatrix get() {
        return new DenseMatrix(size, matrix);
    }
}
