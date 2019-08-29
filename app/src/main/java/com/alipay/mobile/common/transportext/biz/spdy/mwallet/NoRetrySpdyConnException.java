package com.alipay.mobile.common.transportext.biz.spdy.mwallet;

import android.annotation.TargetApi;
import java.io.IOException;

public class NoRetrySpdyConnException extends IOException {
    private static final long serialVersionUID = -413304000393375431L;

    public NoRetrySpdyConnException() {
    }

    public NoRetrySpdyConnException(String exMsg) {
        super(exMsg);
    }

    @TargetApi(9)
    public NoRetrySpdyConnException(String exMsg, Throwable throwable) {
        super(exMsg, throwable);
    }
}
