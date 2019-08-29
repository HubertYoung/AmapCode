package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class AURadioGroup extends RadioGroup implements AUViewInterface {
    private Boolean isAP;

    public AURadioGroup(Context context) {
        super(context);
    }

    public AURadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
