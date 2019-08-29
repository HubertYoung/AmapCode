package com.autonavi.ae.ajx.tbt;

public class AjxBinaryStruct {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native long createNativeObj();

    private static native String dataBytesGet(long j, AjxBinaryStruct ajxBinaryStruct);

    private static native void dataBytesSet(long j, AjxBinaryStruct ajxBinaryStruct, String str);

    private static native int dataLengthGet(long j, AjxBinaryStruct ajxBinaryStruct);

    private static native void dataLengthSet(long j, AjxBinaryStruct ajxBinaryStruct, int i);

    private static native void destroyNativeObj(long j);

    protected AjxBinaryStruct(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(AjxBinaryStruct ajxBinaryStruct) {
        if (ajxBinaryStruct == null) {
            return 0;
        }
        return ajxBinaryStruct.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                destroyNativeObj(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setDataBytes(String str) {
        dataBytesSet(this.swigCPtr, this, str);
    }

    public String getDataBytes() {
        return dataBytesGet(this.swigCPtr, this);
    }

    public void setDataLength(int i) {
        dataLengthSet(this.swigCPtr, this, i);
    }

    public int getDataLength() {
        return dataLengthGet(this.swigCPtr, this);
    }

    public AjxBinaryStruct() {
        this(createNativeObj(), true);
    }
}
