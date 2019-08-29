package com.autonavi.minimap.ajx3.dom;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
abstract class JsDomNodeData implements IJsDomData {
    JsDomNodeData[] mChildren;
    float[] mDimensions;
    float[] mPaddings;
    long mShadow;

    private native int nativeGetAttributeCount(long j);

    private native String nativeGetAttributeKey(long j, int i);

    private native String nativeGetAttributeValue(long j, int i);

    private native float[] nativeGetPadding(long j);

    private native boolean nativeGetPropertyBooleanValue(long j, int i);

    private native int nativeGetPropertyCount(long j);

    private native Object nativeGetPropertyFilterValue(long j, int i);

    private native float[] nativeGetPropertyFloatArrayValue(long j, int i);

    private native float nativeGetPropertyFloatValue(long j, int i);

    private native int[] nativeGetPropertyIntArrayValue(long j, int i);

    private native int nativeGetPropertyIntValue(long j, int i);

    private native int nativeGetPropertyKey(long j, int i);

    private native Object[] nativeGetPropertyObjectArrayValue(long j, int i);

    private native String nativeGetPropertyOriginValue(long j, int i);

    private native String nativeGetPropertyStringValue(long j, int i);

    private native int nativeGetPropertyStyle(long j, int i);

    private native int nativeGetPropertyValueType(long j, int i);

    private native float[] nativeGetSize(long j);

    public abstract IJsDomData[] getChildren();

    JsDomNodeData() {
    }

    JsDomNodeData(long j) {
        this.mShadow = j;
        this.mDimensions = nativeGetSize(this.mShadow);
        this.mPaddings = nativeGetPadding(this.mShadow);
    }

    public float[] dimensions() {
        return this.mDimensions;
    }

    public float[] paddings() {
        return this.mPaddings;
    }

    public int getAttributeCount() {
        return nativeGetAttributeCount(this.mShadow);
    }

    public String getAttributeKey(int i) {
        return nativeGetAttributeKey(this.mShadow, i);
    }

    public String getAttributeValue(int i) {
        return nativeGetAttributeValue(this.mShadow, i);
    }

    public int getPropertyCount() {
        return nativeGetPropertyCount(this.mShadow);
    }

    public int getPropertyStyle(int i) {
        return nativeGetPropertyStyle(this.mShadow, i);
    }

    public int getPropertyKey(int i) {
        return nativeGetPropertyKey(this.mShadow, i);
    }

    public int getPropertyValueType(int i) {
        return nativeGetPropertyValueType(this.mShadow, i);
    }

    public boolean propertyBooleanValue(int i) {
        return nativeGetPropertyBooleanValue(this.mShadow, i);
    }

    public int propertyIntValue(int i) {
        return nativeGetPropertyIntValue(this.mShadow, i);
    }

    public float propertyFloatValue(int i) {
        return nativeGetPropertyFloatValue(this.mShadow, i);
    }

    public String propertyStringValue(int i) {
        return nativeGetPropertyStringValue(this.mShadow, i);
    }

    public int[] propertyIntArray(int i) {
        return nativeGetPropertyIntArrayValue(this.mShadow, i);
    }

    public float[] propertyFloatArray(int i) {
        return nativeGetPropertyFloatArrayValue(this.mShadow, i);
    }

    public Object[] propertyObjArray(int i) {
        return nativeGetPropertyObjectArrayValue(this.mShadow, i);
    }

    public Object propertyFilterValue(int i) {
        return nativeGetPropertyFilterValue(this.mShadow, i);
    }

    public String propertyOriginValue(int i) {
        return nativeGetPropertyOriginValue(this.mShadow, i);
    }
}
