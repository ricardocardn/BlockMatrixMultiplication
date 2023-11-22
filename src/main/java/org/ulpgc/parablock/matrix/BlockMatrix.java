package org.ulpgc.parablock.matrix;

import org.ulpgc.parablock.matrix.coordinates.Coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockMatrix implements org.ulpgc.parablock.matrix.Matrix {
    private final int size;
    private final Map<Coordinate, org.ulpgc.parablock.matrix.DenseMatrix> matrix;

    public BlockMatrix(int size, Map<Coordinate, org.ulpgc.parablock.matrix.DenseMatrix> matrix) {
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

    public BlockMatrix deepCopy() {
        Map<Coordinate, org.ulpgc.parablock.matrix.DenseMatrix> newMatrix = new HashMap<>();

        for (Map.Entry<Coordinate, org.ulpgc.parablock.matrix.DenseMatrix> entry : matrix.entrySet()) {
            Coordinate coordinate = entry.getKey();
            org.ulpgc.parablock.matrix.DenseMatrix originalMatrix = entry.getValue();
            org.ulpgc.parablock.matrix.DenseMatrix clonedMatrix = originalMatrix.deepCopy();

            newMatrix.put(coordinate, clonedMatrix);
        }

        return new BlockMatrix(size, newMatrix);
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
