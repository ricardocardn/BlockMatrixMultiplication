package org.ulpgc.parablock.operators.tests.multipliers.mapreduce;

import org.testng.annotations.Test;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.multipliers.mapreduce.MapReduceMatrixMultiplication;

import java.io.IOException;
import java.util.Random;

public class MapReduceMultiplicationTests {

    @Test
    public void multiplicationTests() throws IOException, InterruptedException, ClassNotFoundException {
        DenseMatrix matrixA = buildDenseMatrix(128);
        DenseMatrix matrixB = buildDenseMatrix(128);

        MapReduceMatrixMultiplication multiplier = new MapReduceMatrixMultiplication();
        multiplier.multiply(matrixA, matrixB);
    }

    private static DenseMatrix buildDenseMatrix(int size) {
        double[][] denseMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(size, denseMatrix);
    }
}

