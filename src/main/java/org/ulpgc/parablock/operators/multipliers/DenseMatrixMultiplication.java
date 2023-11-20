package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

public class DenseMatrixMultiplication<Type extends Number> implements MatrixMultiplication<Type> {
    private final Transform2DenseMatrix<Type> transformer = new Transform2DenseMatrix<>();

    @Override
    public DenseMatrix<Type> multiply(Matrix<Type> A, Matrix<Type> B) {
        DenseMatrix<Type> matrixA = transformer.execute(A);
        DenseMatrix<Type> matrixB = transformer.execute(B);

        DenseMatrixBuilder<Type> matrixBuilder = new DenseMatrixBuilder<>(matrixA.size());

        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixA.size(); j++) {
                Number value = 0;
                for (int k = 0; k < matrixA.size(); k++) {
                    value = (byte) (value.byteValue() + matrixA.get(i,k).byteValue()*matrixB.get(k,j).byteValue());
                }

                matrixBuilder.set(new Coordinate(i,j),(Type) value);
            }
        }

        return matrixBuilder.get();
    }
}

