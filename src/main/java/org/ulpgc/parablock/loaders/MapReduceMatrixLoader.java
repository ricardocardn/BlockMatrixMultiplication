package org.ulpgc.parablock.loaders;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixTransformer;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MapReduceMatrixLoader implements MatrixLoader {
    private final MatrixTransformer transformer;

    public MapReduceMatrixLoader() {
        this.transformer = new Transform2DenseMatrix();
    }

    @Override
    public Matrix load(String file) {
        return null;
    }

    @Override
    public void store(Matrix matrix, String file, String name) {
        DenseMatrix denseMatrix = (DenseMatrix) transformer.execute(matrix);

        int n = denseMatrix.size();

        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                saveToFile(new Coordinate(i,j),
                        denseMatrix.get(i,j),
                        file,
                        name);

    }

    public void saveToFile(Coordinate coordinate, double value, String fileName, String name) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(String.format(
                    "%s,%d,%d,%s\n", name, coordinate.i, coordinate.j, Double.toString(value)
            ));

            bufferedWriter.close();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
