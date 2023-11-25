package org.ulpgc.parablock.operators.multipliers.distributed;

import org.ulpgc.parablock.builders.BlockMatrixBuilder;
import org.ulpgc.parablock.builders.MatrixBuilder;
import org.ulpgc.parablock.matrix.BlockMatrix;
import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixAddition;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.adders.DenseMatrixAddition;
import org.ulpgc.parablock.operators.multipliers.DenseMatrixMultiplication;
import org.ulpgc.parablock.operators.transformers.Transform2BlockMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DistributedTileMultiplication implements DistributedMultiplication {
    private final int AVAILABLE_CORES  = Runtime.getRuntime().availableProcessors();
    private final Transform2BlockMatrix transformer;
    private final MatrixMultiplication denseMultiplier;
    private final MatrixAddition matrixAddition;
    private ExecutorService service;

    public DistributedTileMultiplication() {
        transformer = new Transform2BlockMatrix();
        denseMultiplier = new DenseMatrixMultiplication();
        matrixAddition = new DenseMatrixAddition();
    }

    @Override
    public Matrix multiply(Matrix A, Matrix B, int startingRow, int endingRow) {
        service = Executors.newFixedThreadPool(AVAILABLE_CORES);

        BlockMatrix matrixA = transformer.execute(A);
        BlockMatrix matrixB = transformer.execute(B);

        int SIZE = matrixA.size();
        int BLOCK_SIZE = matrixA.get(0,0).size();

        final DenseMatrix[][] blocks = new DenseMatrix[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (startingRow <= i && i < endingRow)
                    computeInParallel(matrixA, matrixB, blocks, i, j);
                else
                    blocks[i][j] = zeros(BLOCK_SIZE);
            }
        }

        try {
            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return asBlockMatrix(blocks);
    }

    private void computeInParallel(BlockMatrix matrixA, BlockMatrix matrixB, DenseMatrix[][] blocks, int i, int j) {
        service.submit(() -> {
                    IntStream.range(0, matrixA.size()).forEach(
                            k -> {
                                Matrix product = denseMultiplier.multiply(matrixA.get(i, k), matrixB.get(k, j));
                                if (blocks[i][j] == null)
                                    blocks[i][j] = (DenseMatrix) product;
                                else
                                    blocks[i][j] = (DenseMatrix) matrixAddition.add(blocks[i][j], product);
                            }
                    );
                }
        );
    }

    private DenseMatrix zeros(int size) {
        double[][] zeros = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                zeros[i][j] = 0;

        return new DenseMatrix(size, zeros);
    }

    private Matrix asBlockMatrix(DenseMatrix[][] blocks) {
        MatrixBuilder matrixBuilder = new BlockMatrixBuilder(blocks.length);
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                matrixBuilder.set(
                        new Coordinate(i, j),
                        blocks[i][j]
                );

        return matrixBuilder.get();
    }
}
