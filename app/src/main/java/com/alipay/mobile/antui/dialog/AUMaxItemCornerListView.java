package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCornerListView;

public class AUMaxItemCornerListView extends AUCornerListView {
    private float maxItems;
    private float singleItemHeight;

    public AUMaxItemCornerListView(Context context) {
        this(context, null);
    }

    public AUMaxItemCornerListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUMaxItemCornerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AUInputBox);
        this.maxItems = (float) a.getInt(0, 5);
        this.singleItemHeight = a.getDimension(1, getResources().getDimension(R.dimen.AU_SPACE12));
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min((int) (((float) getResources().getDisplayMetrics().heightPixels) * 0.8f), (int) ((this.maxItems * this.singleItemHeight) + ((this.maxItems - 1.0f) * ((float) getDividerHeight())))), Integer.MIN_VALUE));
    }

    public void setMaxItems(float maxItems2) {
        this.maxItems = maxItems2;
    }

    public void setSingleItemHeight(float singleItemHeight2) {
        this.singleItemHeight = singleItemHeight2;
    }
}
