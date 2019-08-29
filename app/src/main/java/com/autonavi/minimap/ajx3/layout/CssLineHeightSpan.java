package com.autonavi.minimap.ajx3.layout;

import android.graphics.Paint.FontMetricsInt;
import android.graphics.Typeface;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LineHeightSpan;
import android.text.style.RelativeSizeSpan;

public class CssLineHeightSpan implements LineHeightSpan {
    private static final String TAG = "CssLineHeightSpan";
    private final float DEFAULT_ASCENT;
    private final float DEFAULT_DESCENT;
    private final boolean mIsMult;
    private final float mLineHeight;
    private TextPaint mTextPaint = new TextPaint();
    private float mTextSize;
    private Typeface mTypeface;

    CssLineHeightSpan(boolean z, float f, float f2, Typeface typeface) {
        this.mIsMult = z;
        this.mLineHeight = f;
        this.mTypeface = typeface;
        this.mTextSize = f2;
        this.mTextPaint.setTextSize(f2);
        this.mTextPaint.setTypeface(typeface);
        this.DEFAULT_DESCENT = this.mTextPaint.descent();
        this.DEFAULT_ASCENT = this.mTextPaint.ascent();
    }

    private float calculateLineHeight(CharSequence charSequence, int i, int i2) {
        float f;
        float f2;
        CharSequence subSequence = charSequence.subSequence(i, i2);
        this.mTextPaint.setTextSize(this.mTextSize);
        float f3 = -1.0f;
        if (subSequence instanceof Spanned) {
            RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) ((Spanned) subSequence).getSpans(0, subSequence.length(), RelativeSizeSpan.class);
            if (relativeSizeSpanArr != null && relativeSizeSpanArr.length > 0) {
                for (int i3 = 0; i3 < relativeSizeSpanArr.length; i3++) {
                    if (f3 < relativeSizeSpanArr[i3].getSizeChange()) {
                        f3 = relativeSizeSpanArr[i3].getSizeChange();
                    }
                }
                if (f3 > 0.0f) {
                    this.mTextPaint.setTextSize(this.mTextSize * f3);
                }
            }
        }
        float f4 = this.mLineHeight;
        if (f3 > 0.0f) {
            f = this.mTextPaint.descent();
            f2 = this.mTextPaint.ascent();
        } else {
            f = this.DEFAULT_DESCENT;
            f2 = this.DEFAULT_ASCENT;
        }
        return f4 * (f - f2);
    }

    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, FontMetricsInt fontMetricsInt) {
        float f;
        float f2;
        if (this.mIsMult) {
            f2 = calculateLineHeight(charSequence, i, i2);
            f = ((this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f) - this.mTextPaint.descent();
        } else {
            f2 = this.mLineHeight;
            f = ((this.DEFAULT_DESCENT - this.DEFAULT_ASCENT) / 2.0f) - this.DEFAULT_DESCENT;
        }
        int ceil = (int) Math.ceil((double) ((f2 / 2.0f) - f));
        fontMetricsInt.descent = ceil;
        fontMetricsInt.bottom = ceil;
        int ceil2 = (int) Math.ceil((double) (((float) fontMetricsInt.descent) - f2));
        fontMetricsInt.ascent = ceil2;
        fontMetricsInt.top = ceil2;
    }
}
