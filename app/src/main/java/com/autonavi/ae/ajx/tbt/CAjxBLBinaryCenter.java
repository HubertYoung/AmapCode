package com.autonavi.ae.ajx.tbt;

public class CAjxBLBinaryCenter {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native int addBinaryData(byte[] bArr);

    private static native long createNativeObj();

    private static native void destroyInstance();

    private static native long getBinaryData(int i);

    private static native long getInstance();

    private static native long nativeAddPathResultData(long j, int i);

    private static native byte[] nativeGetBinaryData(int i);

    private static native void nativeRemovePathResultData(long j);

    private static native void removeBinaryData(int i);

    protected CAjxBLBinaryCenter(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(CAjxBLBinaryCenter cAjxBLBinaryCenter) {
        if (cAjxBLBinaryCenter == null) {
            return 0;
        }
        return cAjxBLBinaryCenter.swigCPtr;
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                throw new UnsupportedOperationException("C++ destructor does not have public access");
            }
            this.swigCPtr = 0;
        }
    }

    public static int addBinaryDataS(byte[] bArr) {
        return addBinaryData(bArr);
    }

    public static void removeBinaryDataS(int i) {
        removeBinaryData(i);
    }

    public static AjxBinaryStruct getBinaryDataS(int i) {
        long binaryData = getBinaryData(i);
        if (binaryData == 0) {
            return null;
        }
        return new AjxBinaryStruct(binaryData, false);
    }

    public static long addPathResultData(long j, int i) {
        return nativeAddPathResultData(j, i);
    }

    public static void removePathResultData(long j) {
        nativeRemovePathResultData(j);
    }

    public static CAjxBLBinaryCenter getInstanceS() {
        long instance = getInstance();
        if (instance == 0) {
            return null;
        }
        return new CAjxBLBinaryCenter(instance, false);
    }

    public static void destroyInstanceS() {
        destroyInstance();
    }

    public CAjxBLBinaryCenter() {
        this(createNativeObj(), true);
    }

    public static byte[] getBinaryDataBytes(int i) {
        return nativeGetBinaryData(i);
    }
}
