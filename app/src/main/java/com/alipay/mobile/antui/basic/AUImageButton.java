package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class AUImageButton extends ImageButton implements AUViewInterface {
    private Boolean isAP;

    public AUImageButton(Context context) {
        super(context);
    }

    public AUImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
