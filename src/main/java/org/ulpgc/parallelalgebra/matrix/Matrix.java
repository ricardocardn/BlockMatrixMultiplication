package org.ulpgc.parallelalgebra.matrix;

public interface Matrix<Type> {
    int size();
    Object get(int i, int j);
}
