package com.alipay.mobile.common.patch.dir.tar;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class TarConstants {
    public static final int DATA_BLOCK = 512;
    public static final int EOF_BLOCK = 1024;
    public static final int HEADER_BLOCK = 512;

    public TarConstants() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
