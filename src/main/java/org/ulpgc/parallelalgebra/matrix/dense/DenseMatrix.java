package org.ulpgc.parallelalgebra.matrix.dense;

import org.ulpgc.parallelalgebra.matrix.Matrix;

public class DenseMatrix<Type> implements Matrix {
    private final int size;
    private final Type[][] matrix;

    public DenseMatrix(int size, Type[][] matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Type get(int i, int j) {
        return matrix[i][j];
    }
}
