package org.ulpgc.parablock.operators.multipliers.mapreduce.mappers;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.ulpgc.parablock.operators.multipliers.mapreduce.MatrixValue;

import java.io.IOException;

public abstract class MatrixMapper extends Mapper<Object,Text,Text, MatrixValue> {
    private Mapper<Object, Text, Text, MatrixValue>.Context context;

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, MatrixValue>.Context context) throws IOException, InterruptedException {
        this.context = context;
        map(value.toString());
    }

    public abstract void map(String toString);

    public void write(String key, MatrixValue value) {
        try {
            context.write(new Text(key), value);
            System.out.println(key + "->" + value);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
