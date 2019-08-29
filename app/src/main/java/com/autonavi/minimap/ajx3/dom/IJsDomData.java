package com.autonavi.minimap.ajx3.dom;

public interface IJsDomData {
    float[] dimensions();

    int getAttributeCount();

    String getAttributeKey(int i);

    String getAttributeValue(int i);

    IJsDomData[] getChildren();

    int getPropertyCount();

    int getPropertyKey(int i);

    int getPropertyStyle(int i);

    int getPropertyValueType(int i);

    float[] paddings();

    boolean propertyBooleanValue(int i);

    Object propertyFilterValue(int i);

    float[] propertyFloatArray(int i);

    float propertyFloatValue(int i);

    int[] propertyIntArray(int i);

    int propertyIntValue(int i);

    Object[] propertyObjArray(int i);

    String propertyOriginValue(int i);

    String propertyStringValue(int i);
}
