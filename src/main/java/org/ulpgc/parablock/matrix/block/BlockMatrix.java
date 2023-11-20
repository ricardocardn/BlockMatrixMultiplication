package org.ulpgc.parablock.matrix.block;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockMatrix<Type extends Number> implements Matrix<Type> {
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

    public void horizontalAlign(int align) {
        for (int i = 0; i < align; i++) {
            int row = size - i - 1;
            for (int j = 0; j < size - 1; j++) {
                DenseMatrix<Type> cur = matrix.get(new Coordinate(row, j));
                int col = (j - 1) >= 0 ? j - 1 : size - 1;
                matrix.put(new Coordinate(row, j), matrix.get(new Coordinate(row, col)));
                matrix.put(new Coordinate(row, col), cur);
            }
        }
    }

    public void verticalAlign(int align) {
        for (int j = 0; j < align; j++) {
            int col = size - j - 1;
            for (int i = 0; i < size - 1; i++) {
                DenseMatrix<Type> cur = matrix.get(new Coordinate(i, col));
                int row = (i - 1) >= 0 ? i - 1 : size - 1;
                matrix.put(new Coordinate(i, col), matrix.get(new Coordinate(row, col)));
                matrix.put(new Coordinate(row, col), cur);
            }
        }
    }

    public BlockMatrix<Type> deepCopy() {
        Map<Coordinate, DenseMatrix<Type>> newMatrix = new HashMap<>();

        for (Map.Entry<Coordinate, DenseMatrix<Type>> entry : matrix.entrySet()) {
            Coordinate coordinate = entry.getKey();
            DenseMatrix<Type> originalMatrix = entry.getValue();
            DenseMatrix<Type> clonedMatrix = originalMatrix.deepCopy();

            newMatrix.put(coordinate, clonedMatrix);
        }

        return new BlockMatrix<>(size, newMatrix);
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
