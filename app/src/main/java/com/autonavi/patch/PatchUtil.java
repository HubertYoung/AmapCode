package com.autonavi.patch;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class PatchUtil {
    public static native int bsdiff(String str, String str2, String str3);

    public static native int bspatch(String str, String str2, String str3);

    static {
        System.loadLibrary("PatchUtil");
    }
}
