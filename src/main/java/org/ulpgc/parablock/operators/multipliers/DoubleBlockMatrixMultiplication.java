package org.ulpgc.parablock.operators.multipliers;

import org.ulpgc.parablock.builders.block.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix;

public class DoubleBlockMatrixMultiplication implements MatrixMultiplication<Double> {
    private final Transform2BlockMatrix<Double> transformer = new Transform2BlockMatrix<>();
    private final MatrixMultiplication<Double> denseMultiplier = new DenseMatrixMultiplication<>();

    @Override
    public BlockMatrix<Double> multiply(Matrix<Double> A, Matrix<Double> B) throws Exception {
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
