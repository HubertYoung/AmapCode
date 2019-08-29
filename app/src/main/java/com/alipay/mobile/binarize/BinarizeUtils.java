package com.alipay.mobile.binarize;

import android.os.Build.VERSION;

public class BinarizeUtils {
    public static final String KEY_ENABLE_RS_BINARIZE = "enableRsBinarize";

    public static boolean supportRsBinarize() {
        return VERSION.SDK_INT >= 24;
    }
}
