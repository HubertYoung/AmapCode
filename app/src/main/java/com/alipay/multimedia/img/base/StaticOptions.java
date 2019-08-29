package com.alipay.multimedia.img.base;

import com.alipay.multimedia.img.utils.NativeSupportHelper;

public class StaticOptions {
    public static boolean jniDebug = false;
    public static final boolean supportNativeProcess = NativeSupportHelper.isSupportNativeProcess();
    public static int thumbnail_allow_delta = 20;
    public static boolean useRandomAccessFileExif = true;
    public static boolean useThumbnailData = true;

    public static String value() {
        return "StaticOptions [supportNativeProcess: " + supportNativeProcess + ", jniDebug: " + jniDebug + ", useRandomAccessFileExif: " + useRandomAccessFileExif + ", useThumbnailData: " + useThumbnailData + ", thumbnail_allow_delta: " + thumbnail_allow_delta + "]";
    }
}
