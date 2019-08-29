package com.alipay.mobile.tinyappcustom.h5plugin.ui;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;

public class H5OpenAuthClickableSpan extends URLSpan {
    public H5OpenAuthClickableSpan(String url) {
        super(url);
    }

    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#108EE9"));
        ds.setUnderlineText(false);
        ds.bgColor = 0;
    }
}
