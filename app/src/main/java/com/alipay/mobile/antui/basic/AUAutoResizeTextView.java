package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUAutoResizeTextView extends AUTextView implements TextWatcher {
    private float mOriginTextSize;
    private Paint mPaint;
    private boolean needRemeasure = false;
    private float width = 0.0f;

    public AUAutoResizeTextView(Context context) {
        super(context);
        init();
    }

    public AUAutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUAutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mPaint = new TextPaint();
        this.mOriginTextSize = getTextSize();
        this.mPaint.setTextSize(this.mOriginTextSize);
        addTextChangedListener(this);
    }

    public void setTextWidth(float width2) {
        this.width = width2;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        float textWidth = this.mPaint.measureText(s.toString()) + ((float) (s.length() * 2));
        if (this.width > 0.0f) {
            reSizeText(this.width, textWidth);
        } else {
            reSizeText((float) getMeasuredWidth(), textWidth);
        }
    }

    private void reSizeText(float viewWidth, float textWidth) {
        if (viewWidth == 0.0f) {
            AuiLogger.debug("AUAutoResizeTextView", " reSizeText viewWidth : 0");
            this.needRemeasure = true;
        } else if (textWidth > viewWidth) {
            float resultSize = (this.mOriginTextSize * viewWidth) / textWidth;
            AuiLogger.debug("AUAutoResizeTextView", " resultSize : " + resultSize);
            setTextSize(0, resultSize);
        } else {
            setTextSize(0, this.mOriginTextSize);
            AuiLogger.debug("AUAutoResizeTextView", " mOriginTextSize : " + this.mOriginTextSize);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.needRemeasure) {
            AuiLogger.debug("AUAutoResizeTextView", "onMeasure needRemeasure");
            reSizeText((float) getMeasuredWidth(), this.mPaint.measureText(getText().toString()));
            this.needRemeasure = false;
        }
    }
}
