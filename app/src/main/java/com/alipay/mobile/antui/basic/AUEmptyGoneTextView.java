package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView.BufferType;

public class AUEmptyGoneTextView extends AUTextView {
    public AUEmptyGoneTextView(Context context) {
        super(context);
    }

    public AUEmptyGoneTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUEmptyGoneTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (TextUtils.isEmpty(text)) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
    }
}
