package com.autonavi.widget.ui;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.TextView.BufferType;
import com.autonavi.minimap.R;

public class AdaptiveButton extends Button {
    private float mDefaultTextSize;

    public AdaptiveButton(Context context) {
        this(context, null);
    }

    public AdaptiveButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdaptiveButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mDefaultTextSize = getTextSize();
        setSingleLine();
        setIncludeFontPadding(false);
        setGravity(17);
        setupDefaultStyle();
    }

    private void setupDefaultStyle() {
        setPadding(getResources().getDimensionPixelSize(R.dimen.alert_view_button_padding_left), getResources().getDimensionPixelSize(R.dimen.alert_view_button_padding_top), getResources().getDimensionPixelSize(R.dimen.alert_view_button_padding_right), 0);
    }

    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        this.mDefaultTextSize = getTextSize();
    }

    private void setTextSizeInternal(float f) {
        super.setTextSize(0, f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        preMeasure(i);
        super.onMeasure(i, i2);
    }

    private void resetTextSize() {
        if (getTextSize() != this.mDefaultTextSize && this.mDefaultTextSize > 0.0f) {
            setTextSizeInternal(this.mDefaultTextSize);
        }
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(charSequence, bufferType);
        requestLayout();
    }

    private void preMeasure(int i) {
        resetTextSize();
        CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            int size = MeasureSpec.getSize(i) - (getPaddingLeft() + getPaddingRight());
            if (size > 0) {
                TextPaint paint = getPaint();
                float measureText = paint.measureText(text, 0, text.length());
                while (true) {
                    float f = (float) size;
                    if (measureText <= f || paint.getTextSize() <= 1.0f) {
                        break;
                    }
                    paint.setTextSize(paint.getTextSize() - 1.0f);
                    measureText = paint.measureText(text, 0, text.length());
                    if (measureText <= f) {
                        setTextSizeInternal(paint.getTextSize());
                        break;
                    }
                }
            }
        }
    }
}
