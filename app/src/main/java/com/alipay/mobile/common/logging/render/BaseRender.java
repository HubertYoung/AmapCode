package com.alipay.mobile.common.logging.render;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;

public abstract class BaseRender {
    protected static long a = 0;
    protected LogContext b;

    public BaseRender(LogContext logContext) {
        this.b = logContext;
    }

    protected static String a(String target) {
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        return target.replaceAll("\\$\\$", "**");
    }

    protected static String a() {
        a++;
        return a;
    }
}
