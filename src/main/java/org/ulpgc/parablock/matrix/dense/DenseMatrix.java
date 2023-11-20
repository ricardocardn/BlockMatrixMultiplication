package org.ulpgc.parablock.matrix.dense;

import org.ulpgc.parablock.matrix.Matrix;

import java.util.Arrays;
import java.util.Objects;

public class DenseMatrix<Type extends Number> implements Matrix<Type> {
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

    public DenseMatrix<Type> deepCopy() {
        Type[][] copiedMatrix = Arrays.stream(matrix)
                .map(row -> Arrays.copyOf(row, row.length))
                .toArray(size -> Arrays.copyOf(matrix, size));

        return new DenseMatrix<>(size, copiedMatrix);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DenseMatrix{Size: ").append(size).append(": ");

        for (Type[] row : matrix) {
            stringBuilder.append(Arrays.toString(row)).append("; ");
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenseMatrix<?> that = (DenseMatrix<?>) o;
        return size == that.size && Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, Arrays.deepHashCode(matrix));
    }

}
