package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class AUViewFlipper extends ViewFlipper implements AUViewInterface {
    private Boolean isAP;

    public AUViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUViewFlipper(Context context) {
        super(context);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
