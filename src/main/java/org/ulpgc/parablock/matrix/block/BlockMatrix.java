package org.ulpgc.parablock.matrix.block;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

import java.util.Map;
import java.util.Objects;

public class BlockMatrix<Type> implements Matrix<Type> {
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

    public void reAlign(Coordinate coordinate1, Coordinate coordinate2) {
        DenseMatrix<Type> denseCoordinate1 = matrix.get(coordinate1);
        DenseMatrix<Type> denseCoordinate2 = matrix.get(coordinate2);

        matrix.put(coordinate1, denseCoordinate2);
        matrix.put(coordinate2, denseCoordinate1);
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
