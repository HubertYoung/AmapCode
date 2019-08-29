package com.standardar.common;

import android.content.Context;

public class Capability {
    public static native int checkSupported(String str, String str2);

    public static native boolean isSupportAR(Context context);
}
