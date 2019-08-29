package com.alipay.mobile.antui.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public class AUPopupWindow extends PopupWindow implements AUViewInterface {
    private Boolean isAP;

    public AUPopupWindow(Context context) {
        super(context);
    }

    public AUPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUPopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint({"NewApi"})
    public AUPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AUPopupWindow() {
    }

    public AUPopupWindow(View contentView) {
        super(contentView);
    }

    public AUPopupWindow(int width, int height) {
        super(width, height);
    }

    public AUPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public AUPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
