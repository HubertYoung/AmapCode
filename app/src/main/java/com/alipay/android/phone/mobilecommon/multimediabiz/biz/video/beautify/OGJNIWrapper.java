package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.beautify;

import java.nio.ByteBuffer;

public class OGJNIWrapper {
    public static final int ORIENTATION_DIAGONAL = 4;
    public static final int ORIENTATION_FLIPPED = 2;
    public static final int ORIENTATION_FLIPPED_MIRRORED = 3;
    public static final int ORIENTATION_NONE = -1;
    public static final int ORIENTATION_STD = 0;
    public static final int ORIENTATION_STD_MIRRORED = 1;
    public static final int RENDER_DISP_MODE_INPUT = 0;
    public static final int RENDER_DISP_MODE_OUTPUT = 1;

    public native void cleanup();

    public native int getOutputFrameH();

    public native int getOutputFrameW();

    public native int getOutputPixels(int i, int i2, long j);

    public native ByteBuffer getOutputPixels2(int i, int i2, long j);

    public native double[] getTimeMeasurements();

    public native void init(boolean z, boolean z2, boolean z3);

    public native void prepare(int i, int i2, boolean z);

    public native void process();

    public native void renderOutput();

    public native void setInputPixels(int[] iArr);

    public native void setInputTexture(int i, float[] fArr);

    public native void setRenderDisp(int i, int i2, int i3);

    public native void setRenderDispShowMode(int i);
}
