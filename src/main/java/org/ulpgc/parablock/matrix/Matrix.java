package org.ulpgc.parablock.matrix;

public interface Matrix<Type> {
    int size();
    Object get(int i, int j);
}