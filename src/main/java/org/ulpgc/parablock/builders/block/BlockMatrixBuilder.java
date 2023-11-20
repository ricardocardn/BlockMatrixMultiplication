package org.ulpgc.parablock.builders.block;

import org.ulpgc.parablock.builders.MatrixBuilder;
import org.ulpgc.parablock.matrix.block.BlockMatrix;
import org.ulpgc.parablock.matrix.block.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.dense.DenseMatrix;

import java.util.HashMap;
import java.util.Map;

public class BlockMatrixBuilder<Type extends Number> implements MatrixBuilder<Type> {
    private final Map<Coordinate, DenseMatrix<Type>> matrixMap;
    private final int size;

    public BlockMatrixBuilder(int size) {
        this.size = size;
        matrixMap = new HashMap<>();
    }

    @Override
    public void set(Coordinate coordinate, Object value) {
        matrixMap.put(coordinate, (DenseMatrix<Type>) value);
    }

    @Override
    public BlockMatrix<Type> get() {
        return new BlockMatrix<>(size, matrixMap);
    }
}
