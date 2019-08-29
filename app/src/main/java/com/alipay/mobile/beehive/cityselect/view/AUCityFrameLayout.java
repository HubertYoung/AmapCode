package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.beehive.util.KeyBoardUtil;

public class AUCityFrameLayout extends AUFrameLayout {
    public AUCityFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AUCityFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUCityFrameLayout(Context context) {
        super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyBoardUtil.hideKeyBoard(getContext(), this);
        return super.dispatchTouchEvent(ev);
    }
}
