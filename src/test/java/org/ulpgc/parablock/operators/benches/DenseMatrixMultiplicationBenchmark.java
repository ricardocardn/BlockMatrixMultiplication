package org.ulpgc.parablock.operators.benches;

import org.openjdk.jmh.annotations.*;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.DenseMatrixMultiplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
@State(Scope.Benchmark)
public class DenseMatrixMultiplicationBenchmark {
    @Param({"16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "8192"})
    public int n;
    Matrix matrixA;
    MatrixMultiplication matrixMultiplication = new DenseMatrixMultiplication();

    @Setup
    public void setup() {
        matrixA = buildDenseMatrix(n);
    }

    @Benchmark
    public void multiplication() {
        matrixMultiplication.multiply(matrixA, matrixA);
    }

    private DenseMatrix buildDenseMatrix(int n) {
        double[][] denseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                denseMatrix[i][j] = new Random().nextDouble();
            }
        }

        return new DenseMatrix(n, denseMatrix);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}