package org.ulpgc.parablock.loaders;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.MatrixTransformer;
import org.ulpgc.parablock.operators.transformers.Transform2DenseMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapReduceMatrixLoader implements MatrixLoader {
    private final int size;

    public MapReduceMatrixLoader(int size) {
        this.size = size;
    }

    @Override
    public Matrix load(String file) {
        try {
            double[][] matrixArray = new double[size][size];

            BufferedReader reader = new BufferedReader(new FileReader(file));

            Pattern pattern = Pattern.compile("\\(([0-9]+),([0-9]+)\\)\\s+([0-9.]+)");
            Matcher matcher;

            String line;
            while ((line = reader.readLine()) != null) {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int i = Integer.parseInt(matcher.group(1));
                    int j = Integer.parseInt(matcher.group(2));
                    double value = Double.parseDouble(matcher.group(3));
                    matrixArray[i][j] = value;
                }
            }

            reader.close();

            DenseMatrix loadedMatrix = new DenseMatrix(size, matrixArray);
            return loadedMatrix;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
