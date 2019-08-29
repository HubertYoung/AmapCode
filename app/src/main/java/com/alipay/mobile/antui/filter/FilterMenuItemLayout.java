package com.alipay.mobile.antui.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class FilterMenuItemLayout extends LinearLayout {
    public FilterMenuItemLayout(Context context) {
        super(context);
    }

    public FilterMenuItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterMenuItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
            case 1:
                return true;
            default:
                return super.onInterceptTouchEvent(ev);
        }
    }
}
