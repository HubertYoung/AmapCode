package com.alipay.mobile.antui.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class EmojiImageSpan extends ImageSpan {
    public EmojiImageSpan(Drawable d) {
        super(d);
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fontMetricsInt) {
        Rect rect = getDrawable().getBounds();
        if (fontMetricsInt != null) {
            FontMetricsInt fmPaint = paint.getFontMetricsInt();
            if (fmPaint != null) {
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;
                int top = (drHeight / 2) - (fontHeight / 4);
                int bottom = (drHeight / 2) + (fontHeight / 4);
                fontMetricsInt.ascent = -bottom;
                fontMetricsInt.top = -bottom;
                fontMetricsInt.bottom = top;
                fontMetricsInt.descent = top;
            }
        }
        return rect.right;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        canvas.translate(x, (float) ((((bottom - top) - drawable.getBounds().bottom) / 2) + top));
        drawable.draw(canvas);
        canvas.restore();
    }
}
