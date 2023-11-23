package org.ulpgc.parablock.builders;

import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.matrix.DenseMatrix;

import java.util.HashMap;
import java.util.Map;

public class BlockMatrixBuilder implements MatrixBuilder {
    private final Map<Coordinate, DenseMatrix> matrixMap;
    private final int size;

    public BlockMatrixBuilder(int size) {
        this.size = size;
        matrixMap = new HashMap<>();
    }

    public void set(Coordinate coordinate, Object value) {
        matrixMap.put(coordinate, (DenseMatrix) value);
    }

    public BlockMatrix get() {
        return new BlockMatrix(size, matrixMap);
    }
}
