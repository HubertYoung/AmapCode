package com.alipay.android.phone.inside.log.api;

import android.content.Context;

public interface LogContext {
    Context getContext();

    String getInfo(String str);
}
