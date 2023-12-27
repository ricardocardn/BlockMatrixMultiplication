package org.ulpgc.parablock.operators.multipliers.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.ulpgc.parablock.builders.DenseMatrixBuilder;
import org.ulpgc.parablock.loaders.MapReduceMatrixLoader;
import org.ulpgc.parablock.loaders.MatrixLoader;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.matrix.coordinates.Coordinate;
import org.ulpgc.parablock.operators.multipliers.mapreduce.mappers.TextMapper;
import org.ulpgc.parablock.operators.multipliers.mapreduce.reducers.TextReducer;

import java.io.IOException;

public class MapReduceMatrixMultiplication {
    private static final MatrixLoader loader = new MapReduceMatrixLoader();

    public void multiply(Matrix matrixA, Matrix matrixB) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration config = new Configuration();
        config.set("size", Integer.toString(matrixA.size()));
        Job job = getJob(config);

        loader.store(matrixA, "src/main/resources/matrixfiles/inputfile.txt", "A");
        loader.store(matrixB, "src/main/resources/matrixfiles/inputfile.txt", "B");

        FileInputFormat.addInputPath(job, new Path("src/main/resources/matrixfiles/inputfile.txt"));
        FileOutputFormat.setOutputPath(job, new Path("src/main/resources/matrixfiles/outputfile.txt"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private Job getJob(Configuration config) throws IOException {
        Job job = Job.getInstance(config, "matrixMultiplication");

        job.setJarByClass(MapReduceMatrixMultiplication.class);
        job.setMapperClass(TextMapper.class);
        job.setReducerClass(TextReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        return job;
    }
}
