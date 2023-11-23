package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixAddition;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.adders.DenseMatrixAddition;
import org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix;

public class BlockMatrixMultiplication implements MatrixMultiplication {
    private final Transform2BlockMatrix transformer;
    private final DenseMatrixMultiplication denseMultiplier;
    private final MatrixAddition matrixAddition;

    public BlockMatrixMultiplication() {
        transformer = new Transform2BlockMatrix();
        denseMultiplier = new DenseMatrixMultiplication();
        matrixAddition = new DenseMatrixAddition();
    }

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        BlockMatrix matrixA = transformer.execute(A);
        BlockMatrix matrixB = transformer.execute(B);

        BlockMatrixBuilder matrixBuilder = new BlockMatrixBuilder(matrixA.size());

        for (int ii = 0; ii < matrixA.size(); ii++) {
            for (int jj = 0; jj < matrixA.size(); jj++) {
                int size = matrixA.get(ii, jj).size();
                DenseMatrix denseMatrix = new DenseMatrix(size, new double[size][size]);
                for (int kk = 0; kk < matrixA.size(); kk++) {
                    Matrix product = denseMultiplier.multiply(matrixA.get(ii, kk), matrixB.get(kk, jj));
                    denseMatrix = matrixAddition.add(denseMatrix, product);
                }

                matrixBuilder.set(new Coordinate(ii, jj), denseMatrix);
            }
        }
        return matrixBuilder.get();
    }
}
