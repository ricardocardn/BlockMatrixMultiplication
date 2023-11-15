package org.ulpgc.parallelalgebra;

import org.testng.annotations.Test;
import org.ulpgc.parallelalgebra.builders.MatrixBuilder;
import org.ulpgc.parallelalgebra.builders.dense.BlockMatrixBuilder;
import org.ulpgc.parallelalgebra.builders.dense.DenseMatrixBuilder;
import org.ulpgc.parallelalgebra.matrix.block.coordinates.Coordinate;

import java.util.Random;

public class Tests {
    @Test
    public void test1() {
        int size = 4;
        int blockSize = 2;

        MatrixBuilder<Integer> blockMatrixBuilder = new BlockMatrixBuilder<Integer>(size/blockSize);

        for (int ii = 0; ii < 2; ii++) {
            for (int jj = 0; jj < 2; jj++) {
                DenseMatrixBuilder<Integer> denseMatrixBuilder = new DenseMatrixBuilder<>(size/blockSize);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        denseMatrixBuilder.set(
                                new Coordinate(i, j),
                                new Random().nextInt()
                        );
                    }
                }

                blockMatrixBuilder.set(
                        new Coordinate(ii, jj),
                        denseMatrixBuilder.get()
                );
            }
        }

    }
}
