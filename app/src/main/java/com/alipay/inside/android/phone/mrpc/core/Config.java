package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;

public interface Config {
    String getAppid();

    Context getApplicationContext();

    RpcParams getRpcParams();

    Transport getTransport();

    String getUrl();

    boolean isGzip();

    boolean isResetCookie();
}
