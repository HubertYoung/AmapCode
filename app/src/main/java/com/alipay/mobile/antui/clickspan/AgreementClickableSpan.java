package com.alipay.mobile.antui.clickspan;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

public class AgreementClickableSpan extends BaseClickableSpan {
    private UrlClickableSpanListener urlClickableSpanListener;
    private String urlPath;

    public AgreementClickableSpan(Context context, String urlPath2, UrlClickableSpanListener urlClickableSpanListener2) {
        super(context, -15692055);
        this.urlPath = urlPath2;
        this.urlClickableSpanListener = urlClickableSpanListener2;
    }

    public void onClick(View widget) {
        super.onClick(widget);
        if (!TextUtils.isEmpty(this.urlPath) && this.canClick && this.urlClickableSpanListener != null) {
            this.urlClickableSpanListener.onClick(this.mContext, this.urlPath);
        }
    }
}
