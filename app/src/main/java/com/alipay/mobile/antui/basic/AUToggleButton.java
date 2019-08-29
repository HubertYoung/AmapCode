package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import com.alipay.mobile.antui.R;

public class AUToggleButton extends ToggleButton implements AUViewInterface {
    private Boolean isAP;
    private String textOff;
    private String textOn;

    public AUToggleButton(Context context) {
        super(context);
        init();
    }

    public AUToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.textOn = getResources().getString(R.string.opened);
        this.textOff = getResources().getString(R.string.closed);
    }

    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked) {
            setContentDescription(this.textOn);
        } else {
            setContentDescription(this.textOff);
        }
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
