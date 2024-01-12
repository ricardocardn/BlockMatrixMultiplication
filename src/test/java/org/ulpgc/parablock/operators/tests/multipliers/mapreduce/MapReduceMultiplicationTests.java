package org.ulpgc.parablock.operators.tests.multipliers.mapreduce;

import org.testng.annotations.Test;
import org.ulpgc.parablock.loaders.MapReduceMatrixLoader;
import org.ulpgc.parablock.loaders.MatrixLoader;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.multipliers.DenseMatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.mapreduce.MapReduceMatrixMultiplication;

import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;
import static org.apache.commons.math3.util.Precision.round;

public class MapReduceMultiplicationTests {
    private final int size = 64;
    private final double epsilon = 0.0001;

    @Test
    public void multiplicationTests() throws IOException, InterruptedException, ClassNotFoundException {
        DenseMatrix matrixA = buildDenseMatrix(size);
        DenseMatrix matrixB = buildDenseMatrix(size);

        MapReduceMatrixMultiplication multiplier = new MapReduceMatrixMultiplication();
        multiplier.multiply(matrixA, matrixB);

        MatrixLoader loader = new MapReduceMatrixLoader(size);
        Matrix mapReduceResult = loader.load("src/main/resources/matrixfiles/outputfile.txt/part-r-00000");

        System.out.println(mapReduceResult);

        DenseMatrixMultiplication denseMultiplier = new DenseMatrixMultiplication();
        Matrix denseMultiplicationResult = denseMultiplier.multiply(matrixA, matrixB);

        assert matrixEquals((DenseMatrix) mapReduceResult, (DenseMatrix) denseMultiplicationResult);
    }

    private boolean matrixEquals(DenseMatrix matrixA, DenseMatrix matrixB) {
        for (int i = 0; i < matrixA.size(); i++)
            for (int j = 0; j < matrixA.size(); j++)
                if (abs(matrixA.get(i, j) -  matrixB.get(i, j)) > epsilon)
                    return false;

        return true;
    }

    private DenseMatrix buildDenseMatrix(int size) {
        double[][] denseMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }
        return new DenseMatrix(size, denseMatrix);
    }
}

