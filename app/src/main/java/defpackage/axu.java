package defpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/* renamed from: axu reason: default package */
/* compiled from: VerticalImageSpan */
public final class axu extends ImageSpan {
    private int a;
    private int b;

    public axu(Context context, int i) {
        super(context, i);
    }

    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return 0;
        }
        Rect bounds = drawable.getBounds();
        if (fontMetricsInt != null) {
            this.a = fontMetricsInt.bottom - fontMetricsInt.top;
            int i3 = (bounds.bottom - bounds.top) / 2;
            int i4 = (fontMetricsInt.bottom - fontMetricsInt.top) / 2;
            int i5 = fontMetricsInt.top + i4;
            fontMetricsInt.ascent = i5 - i3;
            fontMetricsInt.descent = i5 + i3;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.descent;
            this.b = i4 - i3;
        }
        return bounds.right;
    }

    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Drawable drawable = getDrawable();
        if (drawable != null && ((float) drawable.getBounds().width()) + f <= ((float) canvas.getWidth())) {
            canvas.save();
            canvas.translate(f, (float) ((i3 + this.b) - (((this.a - (i5 - i3)) + 1) / 2)));
            drawable.draw(canvas);
            canvas.restore();
        }
    }
}
