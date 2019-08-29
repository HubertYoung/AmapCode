package com.alipay.streammedia.mmengine.picture.gif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class NSGifDecoder {
    public static final int DEFAULT_DELAY = 100;
    private static final String TAG = "GifDecoder";
    private Frame curFrame = null;
    private int decoderHandler = 0;
    private int frameCount;
    private int imageHeight;
    private int imageWidth;

    public class Frame {
        public Bitmap bitmap = null;
        public int delay = 0;
        public int index = 0;

        public Frame() {
        }
    }

    private native int nDestory(int i);

    private native int nGetFrameBitmap(int i, Object obj, int i2);

    private native int nInitByPath(String str, int[] iArr);

    public int load(String path) {
        int[] params = new int[4];
        int ret = nInitByPath(path, params);
        if (ret >= 0) {
            this.frameCount = params[0];
            this.imageWidth = params[1];
            this.imageHeight = params[2];
            this.decoderHandler = params[3];
            this.curFrame = new Frame();
            this.curFrame.bitmap = Bitmap.createBitmap(this.imageWidth, this.imageHeight, Config.ARGB_8888);
            this.curFrame.index = 0;
        }
        return ret;
    }

    public void recycle() {
        if (!(this.curFrame == null || this.curFrame.bitmap == null)) {
            this.curFrame.bitmap.recycle();
        }
        if (this.decoderHandler > 0 && nDestory(this.decoderHandler) != 0) {
            throw new RuntimeException("native destory failed");
        }
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public int getWidth() {
        return this.imageWidth;
    }

    public int getHeight() {
        return this.imageHeight;
    }

    public Frame getFirstFrame() {
        return getFrame(0);
    }

    public Frame getCurrentFrame() {
        return getFrame(this.curFrame.index);
    }

    public Frame getNextFrame() {
        return getFrame((this.curFrame.index + 1) % this.frameCount);
    }

    public Frame getFrame(int index) {
        if (index < 0 || index >= this.frameCount) {
            throw new ArrayIndexOutOfBoundsException(index);
        } else if (this.curFrame.bitmap != null) {
            int delay = nGetFrameBitmap(index, this.curFrame.bitmap, this.decoderHandler);
            if (delay > 0) {
                this.curFrame.delay = delay;
            } else {
                this.curFrame.delay = 100;
            }
            this.curFrame.index = index;
            return this.curFrame;
        } else {
            throw new NullPointerException("Bitmap is null");
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        recycle();
        super.finalize();
    }

    static {
        System.loadLibrary("ijkengine");
    }
}
