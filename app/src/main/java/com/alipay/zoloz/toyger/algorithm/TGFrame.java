package com.alipay.zoloz.toyger.algorithm;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TGFrame {
    public static final int MODE_BGR = 2;
    public static final int MODE_BGRA = 1;
    public static final int MODE_NV21 = 0;
    public static final int MODE_RGB = 4;
    public static final int MODE_RGBA = 3;
    public static final int TYPE_DARK = 1;
    public static final int TYPE_DEPTH = 2;
    public static final int TYPE_LIGHT = 0;
    public ByteBuffer byteBuffer;
    public byte[] data;
    public int frameMode;
    public int frameType;
    public int height;
    public int rotation;
    public int width;

    public TGFrame() {
    }

    public TGFrame(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        this.data = bArr;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
        this.frameMode = i4;
        this.frameType = i5;
    }

    public TGFrame(ByteBuffer byteBuffer2, int i, int i2, int i3, int i4, int i5) {
        this.byteBuffer = byteBuffer2;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
        this.frameMode = i4;
        this.frameType = i5;
    }

    public TGFrame(TGFrame tGFrame) {
        if (tGFrame != null) {
            if (tGFrame.data != null) {
                this.data = (byte[]) tGFrame.data.clone();
            }
            this.width = tGFrame.width;
            this.height = tGFrame.height;
            this.rotation = tGFrame.rotation;
            this.frameMode = tGFrame.frameMode;
            this.frameType = tGFrame.frameType;
            this.byteBuffer = tGFrame.byteBuffer;
        }
    }

    public TGFrame deepCopy() {
        TGFrame tGFrame = new TGFrame((byte[]) null, this.width, this.height, this.rotation, this.frameMode, this.frameType);
        if (this.data != null && this.data.length != 0) {
            tGFrame.data = Arrays.copyOf(this.data, this.data.length);
        } else if (this.byteBuffer != null) {
            tGFrame.data = new byte[this.byteBuffer.remaining()];
            this.byteBuffer.get(tGFrame.data);
        }
        return tGFrame;
    }

    public void recycle() {
        this.data = null;
        this.byteBuffer = null;
    }

    public String toString() {
        return "TGFrame{data=***, width=" + this.width + ", height=" + this.height + ", rotation=" + this.rotation + ", frameMode=" + this.frameMode + ", frameType=" + this.frameType + '}';
    }
}
