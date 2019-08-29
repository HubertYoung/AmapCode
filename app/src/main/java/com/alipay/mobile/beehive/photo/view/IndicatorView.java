package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;

public class IndicatorView extends LinearLayout {
    private Drawable indicatorNormal;
    private int indicatorPadding;
    private Drawable indicatorSelected;
    private int indicatorWidth;
    private int selected;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet as) {
        super(context, as);
        this.indicatorWidth = (int) getContext().getResources().getDimension(R.dimen.dot_width);
        this.indicatorPadding = this.indicatorWidth;
        this.selected = 0;
        setOrientation(0);
        this.indicatorNormal = getResources().getDrawable(R.drawable.indicator_normal);
        this.indicatorSelected = getResources().getDrawable(R.drawable.indicator_selected);
    }

    public void setCount(int count) {
        removeAllViews();
        if (count <= 50) {
            for (int index = 0; index < count; index++) {
                ImageView iv = new ImageView(getContext());
                LayoutParams param = new LayoutParams(this.indicatorWidth, this.indicatorWidth);
                if (index > 0) {
                    param.setMargins(this.indicatorPadding, 0, 0, 0);
                }
                iv.setImageDrawable(this.indicatorNormal);
                addView(iv, index, param);
            }
            return;
        }
        PhotoLogger.warn((String) "IndicatorView", "Count overflow! count = " + count);
    }

    public void setSelection(int index) {
        if (index >= 0 && index < getChildCount()) {
            ImageView old = (ImageView) getChildAt(this.selected);
            if (old != null) {
                old.setImageDrawable(this.indicatorNormal);
            }
            ((ImageView) getChildAt(index)).setImageDrawable(this.indicatorSelected);
            this.selected = index;
        }
    }
}
