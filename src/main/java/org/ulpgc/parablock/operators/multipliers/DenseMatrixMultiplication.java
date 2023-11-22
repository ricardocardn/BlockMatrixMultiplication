package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

public class DenseMatrixMultiplication implements MatrixMultiplication {
    private final Transform2DenseMatrix transformer = new Transform2DenseMatrix();

    @Override
    public DenseMatrix multiply(Matrix A, Matrix B) {
        DenseMatrix matrixA = transformer.execute(A);
        DenseMatrix matrixB = transformer.execute(B);

        double[][] denseMatrix = new double[matrixA.size()][matrixA.size()];

        for (int i = 0; i < matrixA.size(); i++) {
            for (int k = 0; k < matrixA.size(); k++) {
                for (int j = 0; j < matrixA.size(); j++) {
                    denseMatrix[i][j] += matrixA.get(i, k) * matrixB.get(k, j);
                }
            }
        }

        return new DenseMatrix(matrixA.size(), denseMatrix);
    }
}

