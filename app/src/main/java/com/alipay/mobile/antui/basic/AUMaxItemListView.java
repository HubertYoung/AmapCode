package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.alipay.mobile.antui.R;

public class AUMaxItemListView extends AUListView {
    public static final int DEFAULT_MAX_ITEMS = 5;
    private int maxItems;
    private float singleItemHeight;

    public AUMaxItemListView(Context context) {
        this(context, null);
    }

    public AUMaxItemListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUMaxItemListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AUInputBox);
        this.maxItems = a.getInteger(0, 5);
        this.singleItemHeight = a.getDimension(1, getResources().getDimension(R.dimen.AU_SPACE12));
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min((int) (((float) getResources().getDisplayMetrics().heightPixels) * 0.8f), (int) ((((float) this.maxItems) * this.singleItemHeight) + ((float) ((this.maxItems - 1) * getDividerHeight())))), Integer.MIN_VALUE));
    }
}
