package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class AURadioButton extends RadioButton implements AUViewInterface {
    private Boolean isAP;

    public AURadioButton(Context context) {
        super(context);
    }

    public AURadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AURadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
