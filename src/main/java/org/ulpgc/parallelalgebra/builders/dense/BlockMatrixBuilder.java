package org.ulpgc.parallelalgebra.builders.dense;

import org.ulpgc.parallelalgebra.builders.MatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.block.BlockMatrix;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;
import org.ulpgc.parallelalgebra.matrix.dense.DenseMatrix;

import java.util.HashMap;
import java.util.Map;

public class BlockMatrixBuilder<Type> implements MatrixBuilder<Type> {
    private Map<Coordinate, DenseMatrix<Type>> matrixMap;
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
        return new BlockMatrix<Type>(size, matrixMap);
    }
}
