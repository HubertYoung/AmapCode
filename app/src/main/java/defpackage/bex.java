package defpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: bex reason: default package */
/* compiled from: RoundBackGroundColorSpan */
public final class bex extends ReplacementSpan {
    private int a;
    private int b = -1;
    private boolean c;

    public bex(int i, boolean z) {
        this.a = i;
        this.c = z;
    }

    private static int a(int i) {
        Context appContext = AMapPageUtil.getAppContext();
        return appContext != null ? bet.a(appContext, i) : i * 2;
    }

    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt fontMetricsInt) {
        return ((int) paint.measureText(charSequence, i, i2)) + a(6);
    }

    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        float f2 = f;
        Paint paint2 = paint;
        int color = paint.getColor();
        paint2.setColor(this.a);
        CharSequence charSequence2 = charSequence;
        int i6 = i;
        int i7 = i2;
        Canvas canvas2 = canvas;
        canvas2.drawRoundRect(new RectF(f2, (float) (i3 + a(1) + 1), ((float) (((int) paint2.measureText(charSequence2, i6, i7)) + a(4))) + f2, (float) (i5 - a(1))), 5.0f, 5.0f, paint2);
        paint2.setColor(this.b);
        canvas2.drawText(charSequence2, i6, i7, f2 + ((float) a(2)), (float) (this.c ? i4 + 1 : i4 - 2), paint2);
        paint2.setColor(color);
    }
}
