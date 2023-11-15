package org.ulpgc.parallelalgebra.matrix.block;

import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;

import java.util.Map;

public class BlockMatrix<Type> implements Matrix {
    private final int size;
    private final Map<Coordinate, DenseMatrix<Type>> matrix;

    public BlockMatrix(int size, Map<Coordinate, DenseMatrix<Type>> matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public DenseMatrix<Type> get(int i, int j) {
        return matrix.get(
                new Coordinate(i,j)
        );
    }
}
