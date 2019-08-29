package com.autonavi.minimap.ajx3.platform.ackor;

import java.lang.reflect.Array;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class Parcel {
    private long mPtr;

    private native void init();

    private native void nativeDestroy(long j);

    private native boolean nativeReadBoolean(long j);

    private native byte nativeReadByte(long j);

    private native byte[] nativeReadBytes(long j);

    private native char nativeReadChar(long j);

    private native double nativeReadDouble(long j);

    private native float nativeReadFloat(long j);

    private native int nativeReadInt(long j);

    private native long nativeReadLong(long j);

    private native short nativeReadShort(long j);

    private native String nativeReadString(long j);

    private native void nativeReset(long j);

    private native boolean nativeWriteBoolean(long j, boolean z);

    private native boolean nativeWriteByte(long j, byte b);

    private native boolean nativeWriteBytes(long j, byte[] bArr);

    private native boolean nativeWriteChar(long j, char c);

    private native boolean nativeWriteDouble(long j, double d);

    private native boolean nativeWriteFloat(long j, float f);

    private native boolean nativeWriteInt(long j, int i);

    private native boolean nativeWriteLong(long j, long j2);

    private native boolean nativeWriteShort(long j, short s);

    private native boolean nativeWriteString(long j, String str);

    public Parcel() {
        init();
    }

    public Parcel(long j) {
        this.mPtr = j;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    private void destroy() {
        nativeDestroy(this.mPtr);
    }

    public void reset() {
        nativeReset(this.mPtr);
    }

    public boolean writeBoolean(boolean z) {
        return nativeWriteBoolean(this.mPtr, z);
    }

    public boolean writeByte(byte b) {
        return nativeWriteByte(this.mPtr, b);
    }

    public boolean writeChar(char c) {
        return nativeWriteChar(this.mPtr, c);
    }

    public boolean writeShort(short s) {
        return nativeWriteShort(this.mPtr, s);
    }

    public boolean writeInt(int i) {
        return nativeWriteInt(this.mPtr, i);
    }

    public boolean writeFloat(float f) {
        return nativeWriteFloat(this.mPtr, f);
    }

    public boolean writeLong(long j) {
        return nativeWriteLong(this.mPtr, j);
    }

    public boolean writeDouble(double d) {
        return nativeWriteDouble(this.mPtr, d);
    }

    public boolean writeBytes(byte[] bArr) {
        return nativeWriteBytes(this.mPtr, bArr);
    }

    public boolean readBoolean() {
        return nativeReadBoolean(this.mPtr);
    }

    public byte readByte() {
        return nativeReadByte(this.mPtr);
    }

    public char readChar() {
        return nativeReadChar(this.mPtr);
    }

    public short readShort() {
        return nativeReadShort(this.mPtr);
    }

    public int readInt() {
        return nativeReadInt(this.mPtr);
    }

    public float readFloat() {
        return nativeReadFloat(this.mPtr);
    }

    public long readLong() {
        return nativeReadLong(this.mPtr);
    }

    public double readDouble() {
        return nativeReadDouble(this.mPtr);
    }

    public byte[] readBytes() {
        return nativeReadBytes(this.mPtr);
    }

    public boolean writeString(String str) {
        return nativeWriteString(this.mPtr, str);
    }

    public String readString() {
        return nativeReadString(this.mPtr);
    }

    public static <T> T readParcel(Parcel parcel, Class<T> cls) {
        T t;
        InstantiationException e;
        IllegalAccessException e2;
        if (parcel == null) {
            return null;
        }
        parcel.reset();
        try {
            t = cls.newInstance();
            try {
                if (t instanceof Parcelable) {
                    ((Parcelable) t).readFromParcel(parcel);
                }
            } catch (InstantiationException e3) {
                e = e3;
                e.printStackTrace();
                parcel.destroy();
                return t;
            } catch (IllegalAccessException e4) {
                e2 = e4;
                e2.printStackTrace();
                parcel.destroy();
                return t;
            }
        } catch (InstantiationException e5) {
            e = e5;
            t = null;
            e.printStackTrace();
            parcel.destroy();
            return t;
        } catch (IllegalAccessException e6) {
            e2 = e6;
            t = null;
            e2.printStackTrace();
            parcel.destroy();
            return t;
        }
        parcel.destroy();
        return t;
    }

    public static <T> T[] readParcelArray(Parcel parcel, Class<T> cls) {
        if (parcel == null) {
            return null;
        }
        parcel.reset();
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            return null;
        }
        T[] tArr = (Object[]) Array.newInstance(cls, readInt);
        for (int i = 0; i < readInt; i++) {
            try {
                T newInstance = cls.newInstance();
                if (newInstance instanceof Parcelable) {
                    ((Parcelable) newInstance).readFromParcel(parcel);
                }
                tArr[i] = newInstance;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        parcel.destroy();
        return tArr;
    }
}
