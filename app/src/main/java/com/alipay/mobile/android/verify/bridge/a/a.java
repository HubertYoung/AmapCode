package com.alipay.mobile.android.verify.bridge.a;

import com.alipay.mobile.android.verify.logger.AndroidLogAdapter;
import com.alipay.mobile.android.verify.logger.FormatStrategy;

/* compiled from: LogcatAdapter */
public class a extends AndroidLogAdapter {
    public boolean isLoggable(int i, String str) {
        return i >= 5;
    }

    public a(FormatStrategy formatStrategy) {
        super(formatStrategy);
    }
}
