package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUSwitch extends Switch {
    private String textOff;
    private String textOn;

    public AUSwitch(Context context) {
        super(context);
        init();
    }

    public AUSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setThumbResource(R.drawable.au_switch_thumb);
        setTrackResource(R.drawable.au_switch_track);
        setSwitchMinWidth(DensityUtil.dip2px(getContext(), 70.0f));
        setTextOn("   ");
        setTextOff("   ");
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
}
