package org.ulpgc.parablock.matrix;

import org.ulpgc.parablock.matrix.coordinates.Coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockMatrix implements org.ulpgc.parablock.matrix.Matrix {
    private final int size;
    private final Map<Coordinate, DenseMatrix> matrix;

    public BlockMatrix(int size, Map<Coordinate, DenseMatrix> matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public org.ulpgc.parablock.matrix.DenseMatrix get(int i, int j) {
        return matrix.get(
                new Coordinate(i,j)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockMatrix that = (BlockMatrix) o;
        return size == that.size && Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, matrix);
    }

    @Override
    public String toString() {
        return "BlockMatrix{" +
                "size=" + size +
                ", matrix=" + matrix +
                '}';
    }
}
