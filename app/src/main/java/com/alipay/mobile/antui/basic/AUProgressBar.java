package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class AUProgressBar extends ProgressBar implements AUViewInterface {
    private Boolean isAP;

    public AUProgressBar(Context context) {
        super(context);
    }

    public AUProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
