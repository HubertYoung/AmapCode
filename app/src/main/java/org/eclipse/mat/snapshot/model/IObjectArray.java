package org.eclipse.mat.snapshot.model;

public interface IObjectArray extends IArray {
    long[] getReferenceArray();

    long[] getReferenceArray(int i, int i2);
}
