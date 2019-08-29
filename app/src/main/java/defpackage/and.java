package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.text.TextPaint;
import java.nio.ByteBuffer;

/* renamed from: and reason: default package */
/* compiled from: TextTextureGenerator */
public final class and {
    public int a = -1;
    public int b = -1;
    public Paint c = null;
    private float d = 0.0f;
    private float e = 0.0f;

    public and() {
        a();
    }

    private void a() {
        float f;
        float f2;
        this.b = this.a - 2;
        this.c = a(this.b);
        float f3 = ((float) (this.a - this.b)) / 2.0f;
        this.e = f3;
        try {
            FontMetrics fontMetrics = this.c.getFontMetrics();
            f2 = fontMetrics.descent;
            try {
                f = fontMetrics.ascent;
                try {
                    float f4 = fontMetrics.top;
                    float f5 = fontMetrics.bottom;
                    float f6 = fontMetrics.leading;
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                f = -27.832031f;
                this.d = ((((float) this.b) - (f2 + f)) / 2.0f) + f3 + 0.5f;
            }
        } catch (Exception unused3) {
            f2 = 7.3242188f;
            f = -27.832031f;
            this.d = ((((float) this.b) - (f2 + f)) / 2.0f) + f3 + 0.5f;
        }
        this.d = ((((float) this.b) - (f2 + f)) / 2.0f) + f3 + 0.5f;
    }

    public final byte[] a(int i, int i2) {
        if (this.a != i2) {
            this.a = i2;
            a();
        }
        try {
            char c2 = (char) i;
            char[] cArr = {c2};
            float f = this.d;
            Bitmap createBitmap = Bitmap.createBitmap(this.a, this.a, Config.ALPHA_8);
            Canvas canvas = new Canvas(createBitmap);
            byte[] bArr = new byte[(this.a * this.a)];
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            float measureText = this.c.measureText(String.valueOf(c2));
            if (cArr[0] > 0 && cArr[0] < 256) {
                f -= 1.5f;
            }
            float f2 = f;
            Align textAlign = this.c.getTextAlign();
            float textSize = this.c.getTextSize();
            float f3 = measureText - ((float) this.b);
            if (textAlign == Align.CENTER || f3 < 4.0f) {
                canvas.drawText(cArr, 0, 1, this.e, f2, this.c);
            } else {
                this.c.setTextAlign(Align.CENTER);
                this.c.setTextSize(((float) this.b) - f3);
                canvas.drawText(cArr, 0, 1, (((float) this.b) - f3) / 2.0f, f2, this.c);
                this.c.setTextAlign(textAlign);
                this.c.setTextSize(textSize);
            }
            createBitmap.copyPixelsToBuffer(wrap);
            createBitmap.recycle();
            return bArr;
        } catch (Exception | OutOfMemoryError unused) {
            return null;
        }
    }

    private static Paint a(int i) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(-1);
        textPaint.setTextSize((float) i);
        textPaint.setAntiAlias(true);
        textPaint.setFilterBitmap(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Align.LEFT);
        return textPaint;
    }
}
