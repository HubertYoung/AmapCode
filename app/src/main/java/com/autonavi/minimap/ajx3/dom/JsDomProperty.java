package com.autonavi.minimap.ajx3.dom;

public final class JsDomProperty {
    public final int key;
    private String mOriginString;
    private long mPtr;
    public final int type;
    public final Object value;
    public final int valueType;

    private native boolean nativeGetBooleanValue(long j);

    private native Object nativeGetFilterValue(long j);

    private native float[] nativeGetFloatArrayValue(long j);

    private native float nativeGetFloatValue(long j);

    private native int[] nativeGetIntArrayValue(long j);

    private native int nativeGetIntValue(long j);

    private native int nativeGetKey(long j);

    private native Object[] nativeGetObjectArrayValue(long j);

    private native String nativeGetOriginString(long j);

    private native String nativeGetStringValue(long j);

    private native int nativeGetType(long j);

    private native int nativeGetValueType(long j);

    JsDomProperty(long j) {
        this.mPtr = j;
        this.type = nativeGetType(j);
        this.key = nativeGetKey(j);
        this.valueType = nativeGetValueType(j);
        switch (this.valueType) {
            case 0:
                this.value = Boolean.valueOf(nativeGetBooleanValue(j));
                return;
            case 1:
                this.value = Integer.valueOf(nativeGetIntValue(j));
                return;
            case 2:
                this.value = Float.valueOf(nativeGetFloatValue(j));
                return;
            case 4:
                this.value = nativeGetStringValue(j);
                return;
            case 5:
                this.value = nativeGetIntArrayValue(j);
                return;
            case 6:
                this.value = nativeGetFloatArrayValue(j);
                return;
            case 7:
                this.value = nativeGetObjectArrayValue(j);
                return;
            case 8:
                this.value = nativeGetFilterValue(j);
                return;
            default:
                this.value = null;
                return;
        }
    }

    public final String getOriginString() {
        if (this.mOriginString == null) {
            this.mOriginString = nativeGetOriginString(this.mPtr);
        }
        return this.mOriginString;
    }
}
