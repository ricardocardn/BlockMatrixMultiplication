package org.ulpgc.parablock.operators.multipliers.mapreduce.mappers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TextMapper extends Mapper<Object,Text,Text,Text> {
    private Mapper<Object, Text, Text, Text>.Context context;

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        this.context = context;
        map(value.toString());
    }

    public void map(String value) {
        Configuration config = context.getConfiguration();
        int size = Integer.parseInt(config.get("size"));

        String[] splitValue = value.split(",");
        if (splitValue[0].equals("A")) {
            int i = Integer.parseInt(splitValue[1]);
            int j = Integer.parseInt(splitValue[2]);
            double val = Double.parseDouble(splitValue[3]);

            for (int k = 0; k < size; k++) {
                write(String.format("(%d,%d)", i, k), String.format("A,%s,%s", j, val));
            }
        } else {
            int j = Integer.parseInt(splitValue[1]);
            int k = Integer.parseInt(splitValue[2]);
            double val = Double.parseDouble(splitValue[3]);

            for (int i = 0; i < size; i++) {
                write(String.format("(%d,%d)", i, k), String.format("B,%s,%s", j, val));
            }
        }
    }

    public void write(String key, String value) {
        try {
            context.write(new Text(key), new Text(value));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
