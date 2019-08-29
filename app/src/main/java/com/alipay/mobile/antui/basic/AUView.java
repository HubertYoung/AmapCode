package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class AUView extends View implements AUViewInterface {
    private Boolean isAP;

    public AUView(Context context) {
        super(context);
    }

    public AUView(Context context, AttributeSet set) {
        super(context, set);
    }

    public AUView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
