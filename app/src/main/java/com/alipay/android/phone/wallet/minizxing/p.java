package com.alipay.android.phone.wallet.minizxing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public final class p extends s {
    private static int a = 3;
    private static float b = 0.17391305f;
    private static float c = 0.265f;
    private static float d = 0.236f;
    private RectF A;
    private int B;
    private Bitmap e;
    private Paint f;
    private Paint x;
    private Rect y;
    private RectF z;

    public p(BitMatrix _bitMatrix, Bitmap _avatar, int _width, int _height, int _padding, boolean forceNoPadding, int _rotate, boolean logoMargin, boolean isFixedSize, DrawCoreTypes s) {
        int padding;
        super(_bitMatrix, _width, _height, _rotate);
        this.e = _avatar;
        int dimension = this.i.getWidth();
        if (dimension > 57) {
            a = 4;
        } else if (dimension > 33) {
            a = 3;
        } else {
            a = 2;
        }
        int minPaddingInPix = (int) Math.ceil((double) ((((float) Math.min(this.j, this.k)) / ((float) ((a * 2) + dimension))) * ((float) a)));
        if (_padding < minPaddingInPix) {
            Log.e("QRCodeDrawable", String.format("padding is not enough. origin %d min is %d, set to min now.", new Object[]{Integer.valueOf(0), Integer.valueOf(minPaddingInPix)}));
            padding = minPaddingInPix;
        } else {
            padding = _padding;
        }
        padding = forceNoPadding ? 0 : padding;
        float scaleTimes = Math.min((((float) this.j) - ((float) (padding * 2))) / ((float) dimension), (((float) this.k) - ((float) (padding * 2))) / ((float) dimension));
        this.q = (((float) this.j) - (((float) dimension) * scaleTimes)) / 2.0f;
        this.r = (((float) this.k) - (((float) dimension) * scaleTimes)) / 2.0f;
        if (!isFixedSize) {
            double oldScale = (double) scaleTimes;
            scaleTimes = (float) Math.floor((double) scaleTimes);
            int d2 = (int) Math.ceil(((oldScale - ((double) scaleTimes)) * ((double) dimension)) / 2.0d);
            padding += d2;
            this.q += (float) d2;
            this.r += (float) d2;
        }
        if (this.e != null) {
            this.f = new Paint();
            this.f = new Paint(1);
            this.y = new Rect(0, 0, this.e.getWidth(), this.e.getHeight());
            if (logoMargin) {
                this.x = new Paint(1);
                this.x.setColor(-1);
                int AVATAR_SIZE_BLOCK = (int) Math.floor((double) (((float) dimension) * d));
                this.z = new RectF((((float) this.j) - (((float) AVATAR_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.k) - (((float) AVATAR_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.j) + (((float) AVATAR_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.k) + (((float) AVATAR_SIZE_BLOCK) * scaleTimes)) / 2.0f);
                int AVATAR_WITH_MARGIN_SIZE_BLOCK = (int) Math.floor((double) (((float) dimension) * c));
                this.A = new RectF((((float) this.j) - (((float) AVATAR_WITH_MARGIN_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.k) - (((float) AVATAR_WITH_MARGIN_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.j) + (((float) AVATAR_WITH_MARGIN_SIZE_BLOCK) * scaleTimes)) / 2.0f, (((float) this.k) + (((float) AVATAR_WITH_MARGIN_SIZE_BLOCK) * scaleTimes)) / 2.0f);
            } else {
                int AVATAR_SIZE_BLOCK2 = (int) Math.floor((double) (((float) dimension) * b));
                this.z = new RectF((((float) this.j) - (((float) AVATAR_SIZE_BLOCK2) * scaleTimes)) / 2.0f, (((float) this.k) - (((float) AVATAR_SIZE_BLOCK2) * scaleTimes)) / 2.0f, (((float) this.j) + (((float) AVATAR_SIZE_BLOCK2) * scaleTimes)) / 2.0f, (((float) this.k) + (((float) AVATAR_SIZE_BLOCK2) * scaleTimes)) / 2.0f);
            }
        }
        this.t = dimension;
        this.s = dimension;
        this.p = scaleTimes;
        this.o = scaleTimes;
        this.v = padding;
        this.u = padding;
        a(s);
    }

    public final void draw(Canvas canvas) {
        if (this.l != 0) {
            this.B = canvas.save();
            canvas.rotate((float) this.l, (float) (this.j / 2), (float) (this.k / 2));
        }
        super.draw(canvas);
        if (this.e != null) {
            if (this.A != null) {
                canvas.drawRoundRect(this.A, 5.0f, 5.0f, this.x);
                int saved = canvas.saveLayer(null, null, 31);
                Paint logoPaint = new Paint(1);
                logoPaint.setColor(-1);
                canvas.drawRoundRect(this.z, 5.0f, 5.0f, logoPaint);
                logoPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                canvas.drawBitmap(this.e, this.y, this.z, logoPaint);
                logoPaint.setXfermode(null);
                canvas.restoreToCount(saved);
            } else {
                canvas.drawBitmap(this.e, this.y, this.z, this.f);
            }
        }
        if (this.l != 0) {
            canvas.restoreToCount(this.B);
        }
    }
}
