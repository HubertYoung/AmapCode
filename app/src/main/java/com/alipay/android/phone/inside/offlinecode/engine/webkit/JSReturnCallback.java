package com.alipay.android.phone.inside.offlinecode.engine.webkit;

import android.webkit.JavascriptInterface;

public class JSReturnCallback {
    private JSResultInterface callBack;

    public JSReturnCallback(JSResultInterface jSResultInterface) {
        this.callBack = jSResultInterface;
    }

    @JavascriptInterface
    public void returnResultToJava(String str) {
        if (this.callBack != null) {
            this.callBack.javascriptOnReturn(str);
        }
    }

    @JavascriptInterface
    public void returnErrorToJava(String str) {
        if (this.callBack != null) {
            this.callBack.javascriptOnError(str);
        }
    }
}
