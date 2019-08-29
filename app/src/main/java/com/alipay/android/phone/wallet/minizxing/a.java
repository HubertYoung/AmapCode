package com.alipay.android.phone.wallet.minizxing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public final class a extends s {
    static int a = 10;
    Paint b;
    Paint c;
    String d;
    String e;
    C0097a f;
    private int x;
    private int y;

    /* renamed from: com.alipay.android.phone.wallet.minizxing.a$a reason: collision with other inner class name */
    interface C0097a {
        void a(Canvas canvas);
    }

    private static String a(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        if (code.length() >= 20 || code.length() <= 12) {
            return code;
        }
        StringBuilder stringBuffer = new StringBuilder(code);
        stringBuffer.insert(12, Token.SEPARATOR);
        stringBuffer.insert(8, Token.SEPARATOR);
        stringBuffer.insert(4, Token.SEPARATOR);
        String ret = stringBuffer.toString();
        Log.d("facepayment", " format code " + code + " to " + ret);
        return ret;
    }

    public a(BitMatrix _bitMatrix, int _width, int _height, int _padding, boolean forceNoPadding, int _rotate, String content, String barcodeDisplayText, DrawCoreTypes s) {
        super(_bitMatrix, _width, _height, _rotate);
        if (this.l == 90) {
            int temp = this.j;
            this.j = this.k;
            this.k = temp;
        }
        this.e = content;
        this.s = this.i.getWidth();
        this.t = 1;
        this.u = Math.max((int) Math.ceil((double) ((((float) this.j) / ((float) (this.s + (a * 2)))) * ((float) a))), _padding);
        this.v = 0;
        if (forceNoPadding) {
            this.v = 0;
            this.u = 0;
        }
        this.o = (((float) this.j) - ((float) (this.u * 2))) / ((float) this.s);
        this.p = (((float) this.k) - ((float) (this.v * 2))) / ((float) this.t);
        this.q = (((float) this.j) - (((float) this.s) * this.o)) / 2.0f;
        this.r = (((float) this.k) - this.p) / 2.0f;
        this.b = new Paint();
        this.b.setColor(g);
        this.b.setXfermode(new PorterDuffXfermode(Mode.SRC));
        this.c = new Paint();
        this.c.setAntiAlias(true);
        this.y = Math.min(this.j, this.k) / 2;
        if (!TextUtils.equals(barcodeDisplayText, "DONT_DRAW_TEXT")) {
            if (!TextUtils.isEmpty(barcodeDisplayText)) {
                this.d = barcodeDisplayText;
                this.c.setTextSize((float) (this.j / 20));
                this.c.setColor(h);
                final float textWidth = this.c.measureText(this.d);
                this.f = new C0097a() {
                    public final void a(Canvas canvas) {
                        canvas.drawRect(0.0f, (float) ((a.this.k - (a.this.j / 16)) - 4), (float) a.this.j, (float) a.this.k, a.this.b);
                        canvas.drawText(a.this.d, (((float) a.this.j) - textWidth) / 2.0f, (float) (a.this.k - 10), a.this.c);
                    }
                };
            } else {
                this.d = a(content);
                this.c.setTextSize((float) (this.j / 16));
                this.c.setColor(h);
                final int textLength = this.d.length();
                final float padding = (float) (this.j / 6);
                final float space = (((float) this.j) - (2.0f * padding)) / ((float) textLength);
                this.f = new C0097a() {
                    public final void a(Canvas canvas) {
                        canvas.drawRect(0.0f, (float) ((a.this.k - (a.this.j / 16)) - 4), (float) a.this.j, (float) a.this.k, a.this.b);
                        for (int i = 0; i < textLength; i++) {
                            canvas.drawText(a.this.d.substring(i, i + 1), padding + (space * ((float) i)), (float) (a.this.k - 4), a.this.c);
                        }
                    }
                };
            }
        }
        a(s);
    }

    public final void draw(Canvas canvas) {
        if (this.l != 0) {
            this.x = canvas.save();
            canvas.rotate((float) this.l, (float) this.y, (float) this.y);
        }
        super.draw(canvas);
        if (this.f != null) {
            this.f.a(canvas);
        }
        if (this.l != 0) {
            canvas.restoreToCount(this.x);
        }
    }
}
