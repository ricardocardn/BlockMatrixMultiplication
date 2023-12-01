package org.ulpgc.parablock.matrix;

import java.util.Arrays;
import java.util.Objects;

public class DenseMatrix implements org.ulpgc.parablock.matrix.Matrix {
    private final int size;
    private final double[][] matrix;

    public DenseMatrix(int size, double[][] matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Double get(int i, int j) {
        return matrix[i][j];
    }

    public double[][] matrix() {
        return matrix;
    }

    public DenseMatrix deepCopy() {
        double[][] copiedMatrix = Arrays.stream(matrix)
                .map(row -> Arrays.copyOf(row, row.length))
                .toArray(size -> Arrays.copyOf(matrix, size));

        return new DenseMatrix(size, copiedMatrix);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DenseMatrix{Size: ").append(size).append(": ");

        for (double[] row : matrix) {
            stringBuilder.append(Arrays.toString(row)).append("; ");
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenseMatrix that = (DenseMatrix) o;
        return size == that.size && doubleArrayEquals(matrix, that.matrix, 1E-8);
    }

    private boolean doubleArrayEquals(double[][] matrixA, double[][] matrixB, double epsilon) {
        if (matrixA.length != matrixB.length) return false;
        for (int i = 0; i < matrixA.length; i++)
            for (int j = 0; j < matrixA.length; j++)
                if (Math.abs(matrixA[i][j] - matrixB[i][j]) > epsilon)
                    return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, Arrays.deepHashCode(matrix));
    }

}
