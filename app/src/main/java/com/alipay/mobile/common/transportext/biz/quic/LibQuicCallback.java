package com.alipay.mobile.common.transportext.biz.quic;

import android.util.Log;

public class LibQuicCallback {
    private final String TAG = "libquic_callback";

    public void offerLog(String res) {
        Log.d("libquic_callback", res);
    }
}
