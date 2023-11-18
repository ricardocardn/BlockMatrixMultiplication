package org.ulpgc.parallelalgebra.operators.multipliers;

import org.ulpgc.parallelalgebra.builders.block.BlockMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.Matrix;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.operators.MatrixMultiplication;
import org.ulpgc.parallelalgebra.operators.transformers.Transform2BlockMatrix;

public class DoubleBlockMatrixMultiplication implements MatrixMultiplication<Double> {
    private final Transform2BlockMatrix<Double> transformer = new Transform2BlockMatrix<>();
    private final MatrixMultiplication<Double> denseMultiplier = new DenseMatrixMultiplication<>();

    @Override
    public BlockMatrix<Double> multiply(Matrix A, Matrix B) throws Exception {
        BlockMatrix<Double> matrixA = transformer.execute(A);
        BlockMatrix<Double> matrixB = transformer.execute(B);

        BlockMatrixBuilder<Double> matrixBuilder = new BlockMatrixBuilder<>(matrixA.size());
        for (int ii = 0; ii < matrixA.size(); ii++)
            for (int jj = 0; jj < matrixA.size(); jj++)
                for (int k = 0; k < matrixA.size(); k++)
                    matrixBuilder.set(
                            new Coordinate(ii, jj),
                            denseMultiplier.multiply(matrixA.get(ii, k), matrixB.get(k, jj))
                    );

        return null;
    }
}
