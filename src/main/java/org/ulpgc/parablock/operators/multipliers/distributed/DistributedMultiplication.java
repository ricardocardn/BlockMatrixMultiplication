package org.ulpgc.parablock.operators.multipliers.distributed;

import org.ulpgc.parablock.matrix.Matrix;

public interface DistributedMultiplication {
    Matrix multiply(Matrix matrixA, Matrix matrixB, int startingRow, int endingRow);
}
