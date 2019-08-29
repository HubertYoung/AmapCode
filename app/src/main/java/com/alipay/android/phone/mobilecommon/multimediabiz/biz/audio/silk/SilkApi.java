package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class SilkApi {
    public static final int COMPRESSION_HIGH = 2;
    public static final int COMPRESSION_LOW = 0;
    public static final int COMPRESSION_NORMAL = 1;
    public static final int SAMPLE_RATE_12K = 12000;
    public static final int SAMPLE_RATE_16K = 16000;
    public static final int SAMPLE_RATE_24K = 24000;
    public static final int SAMPLE_RATE_32K = 32000;
    public static final int SAMPLE_RATE_44_1K = 44100;
    public static final int SAMPLE_RATE_48K = 48000;
    public static final int SAMPLE_RATE_8K = 8000;
    public static final byte[] SILK_END = SilkUtils.convertToLittleEndian(-1);
    public static final short SILK_END_SHORT = -1;
    public static final String SILK_HEAD = "#!SILK_V3";
    public static final int TARGET_RATE_12K = 12000;
    public static final int TARGET_RATE_16K = 16000;
    public static final int TARGET_RATE_25K = 25000;
    public static final int TARGET_RATE_44K = 44000;
    public static final int TARGET_RATE_48K = 48000;
    public static final int TARGET_RATE_8K = 8000;
    private static final String a = SilkApi.class.getSimpleName();

    public native void closeDecoder();

    public native void closeEncoder();

    public native int decode(byte[] bArr, short[] sArr, int i);

    public native int encode(short[] sArr, int i, byte[] bArr, int i2);

    public native int openDecoder(int i);

    public native int openEncoder(int i, int i2, int i3);

    static {
        AppUtils.loadLibrary("silk_jni");
        Logger.D(a, "loadLibrary finish", new Object[0]);
    }
}
