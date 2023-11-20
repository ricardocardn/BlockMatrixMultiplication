package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.builders.block.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix;

public class BlockMatrixMultiplication<Type extends Number> implements MatrixMultiplication<Type> {
    private final Transform2BlockMatrix<Type> transformer;
    private final MatrixMultiplication<Type> denseMultiplier;

    public BlockMatrixMultiplication() {
        transformer = new Transform2BlockMatrix<>();
        denseMultiplier = new DenseMatrixMultiplication<>();
    }

    @Override
    public Matrix<Type> multiply(Matrix<Type> A, Matrix<Type> B) {
        BlockMatrix<Type> matrixA = transformer.execute(A);
        BlockMatrix<Type> matrixB = transformer.execute(B);

        BlockMatrixBuilder<Type> matrixBuilder = new BlockMatrixBuilder<>(matrixA.size());

        for (int epoch = 0; epoch < matrixA.size(); epoch++) {
            matrixA.horizontalAlign(epoch);
            matrixB.verticalAlign(epoch);

            for (int ii = 0; ii < matrixA.size(); ii++) {
                for (int jj = 0; jj < matrixA.size(); jj++) {
                    Matrix<Type> product = denseMultiplier.multiply(matrixA.get(ii, jj), matrixB.get(ii, jj));
                    matrixBuilder.set(new Coordinate(ii, jj), product);
                }
            }
        }

        return matrixBuilder.get();
    }
}
