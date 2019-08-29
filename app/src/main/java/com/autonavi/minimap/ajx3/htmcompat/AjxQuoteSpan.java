package com.autonavi.minimap.ajx3.htmcompat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

public class AjxQuoteSpan implements LeadingMarginSpan {
    private static int STRIPE_WIDTH = 50;

    AjxQuoteSpan() {
        STRIPE_WIDTH = DimensionUtils.dipToPixel(25.0f);
    }

    public int getLeadingMargin(boolean z) {
        return STRIPE_WIDTH;
    }

    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        Paint paint2 = paint;
        int i8 = i;
        Style style = paint2.getStyle();
        int color = paint2.getColor();
        paint2.setStyle(Style.FILL);
        paint2.setColor(0);
        Canvas canvas2 = canvas;
        canvas2.drawRect((float) i8, (float) i3, (float) (i8 + (STRIPE_WIDTH * i2)), (float) i5, paint2);
        paint2.setStyle(style);
        paint2.setColor(color);
    }
}
