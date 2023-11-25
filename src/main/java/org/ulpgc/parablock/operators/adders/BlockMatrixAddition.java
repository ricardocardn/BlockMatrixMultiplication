package org.ulpgc.parablock.operators.adders;

import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixAddition;

public class BlockMatrixAddition implements MatrixAddition {
    @Override
    public BlockMatrix add(Matrix matrixA, Matrix matrixB) {
        BlockMatrixBuilder matrixBuilder = new BlockMatrixBuilder(matrixA.size());
        for (int i = 0; i < matrixA.size(); i++)
            for (int j = 0; j < matrixA.size(); j++)
                matrixBuilder.set(
                        new Coordinate(i, j),
                        new DenseMatrixAddition().add((DenseMatrix) matrixA.get(i,j), (DenseMatrix) matrixB.get(i,j))
                );

        return matrixBuilder.get();
    }
}
