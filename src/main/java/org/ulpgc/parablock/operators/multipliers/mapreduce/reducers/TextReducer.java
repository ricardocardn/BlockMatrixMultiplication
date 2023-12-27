package org.ulpgc.parablock.operators.multipliers.mapreduce.reducers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextReducer extends Reducer<Text, Text,Text,Text> {
    private Reducer<Text, Text, Text, Text>.Context context;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) {
        this.context = context;
        reduce(key.toString(), values);
    }

    protected void reduce(String key, Iterable<Text> values) {
        String[] value;

        HashMap<Integer, Double> hashA = new HashMap<>();
        HashMap<Integer, Double> hashB = new HashMap<>();
        for (Text val : values) {
            value = val.toString().split(",");
            if (value[0].equals("A")) {
                hashA.put(Integer.parseInt(value[1]), Double.parseDouble(value[2]));
            } else {
                hashB.put(Integer.parseInt(value[1]), Double.parseDouble(value[2]));
            }
        }

        multiply(key, hashA, hashB);
    }

    private void multiply(String key, HashMap<Integer, Double> hashA, HashMap<Integer, Double> hashB) {
        Configuration config = context.getConfiguration();
        int size = Integer.parseInt(config.get("size"));

        float result = 0.0f;

        double m_ij;
        double n_jk;

        for (int j = 0; j < size; j++) {
            m_ij = hashA.containsKey(j) ? hashA.get(j) : 0.0f;
            n_jk = hashB.containsKey(j) ? hashB.get(j) : 0.0f;
            result += m_ij * n_jk;
        }

        if (result != 0.0f) {
            write(key,
                    new Text(Double.toString(result)));
        }
    }

    public void write(String key, Text value) {
        try {
            context.write(new Text(key), new Text(value));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
