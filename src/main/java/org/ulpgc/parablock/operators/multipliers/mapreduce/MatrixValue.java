package org.ulpgc.parablock.operators.multipliers.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

@InterfaceAudience.Public
@InterfaceStability.Stable
public
class MatrixValue implements Writable {
    public String matrix;
    public int column;
    public double value;

    public MatrixValue(String matrix, int column, double value) {
        this.matrix = matrix;
        this.column = column;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixValue that = (MatrixValue) o;
        return column == that.column && Double.compare(that.value, value) == 0 && Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix, column, value);
    }

    @Override
    public String toString() {
        return "MatrixValue{" +
                "matrix='" + matrix + '\'' +
                ", column=" + column +
                ", value=" + value +
                '}';
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(matrix);
        dataOutput.writeInt(column);
        dataOutput.writeDouble(value);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        matrix = dataInput.readUTF();
        column = dataInput.readInt();
        value = dataInput.readDouble();
    }
}