package org.ulpgc.parallelalgebra.matrix.block;

import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;

import java.util.Map;
import java.util.Objects;

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

    public void align(Coordinate coord1, Coordinate coord2) {
        DenseMatrix<Type> denseCoord1 = matrix.get(coord1);
        DenseMatrix<Type> denseCoord2 = matrix.get(coord2);

        matrix.put(coord1, denseCoord2);
        matrix.put(coord2, denseCoord1);
    }

    @Override
    public String toString() {
        return "BlockMatrix{" +
                "size=" + size +
                ", matrix=" + matrix +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockMatrix<?> that = (BlockMatrix<?>) o;
        return size == that.size && Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, matrix);
    }
}
