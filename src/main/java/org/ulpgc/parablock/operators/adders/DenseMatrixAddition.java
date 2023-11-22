package org.ulpgc.parablock.operators.adders;

import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixAddition;

public class DenseMatrixAddition implements MatrixAddition {
    @Override
    public DenseMatrix add(Matrix matrixA, Matrix matrixB) {
        double[][] denseMatrix = new double[matrixA.size()][matrixB.size()];
        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixB.size(); j++) {
                denseMatrix[i][j] = (double) matrixA.get(i,j) + (double) matrixB.get(i,j);
            }
        }

        return new DenseMatrix(matrixA.size(), denseMatrix);
    }
}
