package org.ulpgc.parablock.operators.multipliers;

import org.testng.annotations.Test;
import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class DenseMultiplicationTests {
    private static int SIZE = 2;

    @Test
    public void multiplyIntegerTest() {
        DenseMatrix<Integer> matrixA = buildIntegerDenseMatrix();
        DenseMatrix<Integer> matrixB = buildIntegerDenseMatrix();
        DenseMatrix<Integer> matrixC = buildIntegerDenseMatrix();

        System.out.println(matrixA);
        System.out.println(matrixB);
        System.out.println(matrixC);

        MatrixMultiplication<Integer> denseMultiplier = new DenseMatrixMultiplication<>();
        Matrix<Integer> AB = denseMultiplier.multiply(matrixA, matrixB);
        Matrix<Integer> AB_C = denseMultiplier.multiply(AB, matrixC);

        Matrix<Integer> BC = denseMultiplier.multiply(matrixB, matrixC);
        Matrix<Integer> A_BC = denseMultiplier.multiply(matrixA, BC);

        System.out.println(A_BC);

        assertEquals(AB_C, A_BC);
    }

    @Test
    public void multiplyDoubleTest() {
        DenseMatrix<Double> matrixA = buildDoubleDenseMatrix();
        DenseMatrix<Double> matrixB = buildDoubleDenseMatrix();
        DenseMatrix<Double> matrixC = buildDoubleDenseMatrix();

        MatrixMultiplication<Double> denseMultiplier = new DenseMatrixMultiplication<>();
        Matrix<Double> AB = denseMultiplier.multiply(matrixA, matrixB);
        Matrix<Double> AB_C = denseMultiplier.multiply(AB, matrixC);

        Matrix<Double> BC = denseMultiplier.multiply(matrixB, matrixC);
        Matrix<Double> A_BC = denseMultiplier.multiply(matrixA, BC);

        assertEquals(AB_C, A_BC);
    }

    private DenseMatrix<Double> buildDoubleDenseMatrix() {
        DenseMatrixBuilder<Double> denseMatrixBuilder = new DenseMatrixBuilder<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                denseMatrixBuilder.set(
                        new Coordinate(i, j),
                        new Random().nextInt(10)
                );
            }
        }
        return denseMatrixBuilder.get();
    }

    private DenseMatrix<Integer> buildIntegerDenseMatrix() {
        DenseMatrixBuilder<Integer> denseMatrixBuilder = new DenseMatrixBuilder<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                denseMatrixBuilder.set(
                        new Coordinate(i, j),
                        new Random().nextInt(10)
                );
            }
        }
        return denseMatrixBuilder.get();
    }
}
