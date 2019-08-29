package com.alipay.zoloz.toyger.algorithm;

import java.nio.ShortBuffer;
import java.util.Arrays;

public class TGDepthFrame {
    public short[] data;
    public int height;
    public int rotation;
    public ShortBuffer shortBuffer;
    public int width;

    public TGDepthFrame() {
    }

    public TGDepthFrame(short[] sArr, int i, int i2, int i3) {
        this.data = sArr;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
    }

    public TGDepthFrame(ShortBuffer shortBuffer2, int i, int i2, int i3) {
        this.shortBuffer = shortBuffer2;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
    }

    public TGDepthFrame(TGDepthFrame tGDepthFrame) {
        if (tGDepthFrame != null) {
            if (tGDepthFrame.data != null) {
                this.data = (short[]) tGDepthFrame.data.clone();
            }
            this.width = tGDepthFrame.width;
            this.height = tGDepthFrame.height;
            this.rotation = tGDepthFrame.rotation;
            this.shortBuffer = tGDepthFrame.shortBuffer;
        }
    }

    public void assign(TGDepthFrame tGDepthFrame) {
        this.width = tGDepthFrame.width;
        this.height = tGDepthFrame.height;
        this.rotation = tGDepthFrame.rotation;
        if (tGDepthFrame.data != null) {
            this.data = Arrays.copyOf(tGDepthFrame.data, tGDepthFrame.data.length);
        } else if (tGDepthFrame.shortBuffer != null) {
            this.data = new short[tGDepthFrame.shortBuffer.remaining()];
            tGDepthFrame.shortBuffer.get(this.data);
        }
    }

    public void recycle() {
        this.data = null;
        this.shortBuffer = null;
    }

    public String toString() {
        return "TGDepthFrame{data=***, width=" + this.width + ", height=" + this.height + ", rotation=" + this.rotation + '}';
    }
}
