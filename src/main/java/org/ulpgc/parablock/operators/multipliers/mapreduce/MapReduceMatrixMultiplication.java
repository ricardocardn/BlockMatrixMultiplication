package org.ulpgc.parablock.operators.multipliers.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.ulpgc.parablock.matrix.Matrix;
import org.ulpgc.parablock.operators.MatrixMultiplication;
import org.ulpgc.parablock.operators.multipliers.mapreduce.mappers.TextMapper;
import org.ulpgc.parablock.operators.multipliers.mapreduce.reducers.TextReducer;
import org.ulpgc.parablock.savers.MapReduceMatrixSaver;
import org.ulpgc.parablock.savers.MatrixSaver;

import java.io.File;
import java.io.IOException;

public class MapReduceMatrixMultiplication {
    private final String inputPath = "src/main/resources/matrixfiles/inputfile.txt";
    private final String outputPath = "src/main/resources/matrixfiles/outputfile.txt";

    public void multiply(Matrix matrixA, Matrix matrixB) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration config = new Configuration();
        config.set("size", Integer.toString(matrixA.size()));
        Job job = getJob(config);

        removeFilesIfExist();
        saveMatricesToInput(matrixA, matrixB);

        long start = System.currentTimeMillis();

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
        long end = System.currentTimeMillis();
        System.out.println("Time matrix multiplication took (ms): " + (end - start));
    }

    public void multiply(int size) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration config = new Configuration();
        config.set("size", String.valueOf(size));
        Job job = getJob(config);

        long start = System.currentTimeMillis();

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
        long end = System.currentTimeMillis();
        System.out.println("Time matrix multiplication took (ms): " + (end - start));
    }

    private void saveMatricesToInput(Matrix matrixA, Matrix matrixB) {
        MatrixSaver saver = new MapReduceMatrixSaver();
        saver.store(matrixA, inputPath, "A");
        saver.store(matrixB, inputPath, "B");
    }

    private void removeFilesIfExist() {
        new File(inputPath).delete();
        new File(outputPath).delete();
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
