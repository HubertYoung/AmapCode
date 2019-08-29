package com.alipay.mobile.beehive.compositeui.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class GridViewWithoutScroll extends GridView {
    public GridViewWithoutScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewWithoutScroll(Context context) {
        super(context);
    }

    public GridViewWithoutScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}
