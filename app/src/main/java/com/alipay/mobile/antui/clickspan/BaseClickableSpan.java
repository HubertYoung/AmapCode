package com.alipay.mobile.antui.clickspan;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public abstract class BaseClickableSpan extends ClickableSpan {
    public static final int CLICK_ENABLE_TIME = 350;
    public boolean canClick = true;
    protected long lastClickTime;
    public int linkTextColor;
    protected Context mContext;

    public BaseClickableSpan(Context context, int linkTextColor2) {
        this.mContext = context;
        this.linkTextColor = linkTextColor2;
    }

    public void updateDrawState(TextPaint ds) {
        ds.setColor(this.linkTextColor);
        ds.setUnderlineText(false);
    }

    public void onClick(View widget) {
        if (Math.abs(System.currentTimeMillis() - this.lastClickTime) > 350) {
            this.canClick = true;
        } else {
            this.canClick = false;
        }
        this.lastClickTime = System.currentTimeMillis();
    }
}
