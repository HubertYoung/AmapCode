package org.eclipse.mat.snapshot.model;

public interface IPrimitiveArray extends IArray {
    public static final Class<?>[] COMPONENT_TYPE = {null, null, null, null, Boolean.TYPE, Character.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE};
    public static final int[] ELEMENT_SIZE = {-1, -1, -1, -1, 1, 2, 4, 8, 1, 2, 4, 8};
    public static final byte[] SIGNATURES = {-1, -1, -1, -1, 90, 67, 70, 68, 66, 83, 73, 74};
    public static final String[] TYPE = {null, null, null, null, "boolean[]", "char[]", "float[]", "double[]", "byte[]", "short[]", "int[]", "long[]"};

    Class<?> getComponentType();

    int getType();

    Object getValueArray();

    Object getValueArray(int i, int i2);

    Object getValueAt(int i);
}
