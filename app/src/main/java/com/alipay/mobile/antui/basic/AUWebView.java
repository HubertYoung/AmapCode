package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class AUWebView extends WebView implements AUViewInterface {
    private Boolean isAP;

    public AUWebView(Context context) {
        super(context);
    }

    public AUWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
