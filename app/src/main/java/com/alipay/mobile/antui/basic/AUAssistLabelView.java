package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;

public class AUAssistLabelView extends AUTextView {
    private int bottomMargin;
    private int margin;
    private int topMargin;

    public AUAssistLabelView(Context context) {
        super(context);
        init(context, null);
    }

    public AUAssistLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUAssistLabelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet) {
        boolean isHead = false;
        if (VERSION.SDK_INT < 23) {
            super.setTextAppearance(getContext(), R.style.auAssitTextStyle);
        } else {
            super.setTextAppearance(R.style.auAssitTextStyle);
        }
        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.AUAssistLabelView);
            isHead = array.getBoolean(0, false);
            array.recycle();
        }
        if (isHead) {
            this.topMargin = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE4);
            this.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE2);
        } else {
            this.topMargin = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE2);
            this.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE4);
        }
        this.margin = getResources().getDimensionPixelOffset(R.dimen.AU_MARGIN_UNIVERSAL);
        setPadding(this.margin, this.topMargin, this.margin, this.bottomMargin);
    }
}
