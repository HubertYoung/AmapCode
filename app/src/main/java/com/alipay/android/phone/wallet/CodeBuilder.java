package com.alipay.android.phone.wallet;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.alipay.android.phone.wallet.minizxing.BarcodeFormat;
import com.alipay.android.phone.wallet.minizxing.BitMatrix;
import com.alipay.android.phone.wallet.minizxing.DrawCoreTypes;
import com.alipay.android.phone.wallet.minizxing.EncodeHintType;
import com.alipay.android.phone.wallet.minizxing.ErrorCorrectionLevel;
import com.alipay.android.phone.wallet.minizxing.MultiFormatWriter;
import com.alipay.android.phone.wallet.minizxing.a;
import com.alipay.android.phone.wallet.minizxing.p;
import com.alipay.android.phone.wallet.minizxing.s;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CodeBuilder {
    static Boolean a = null;
    static Boolean b = null;
    public static GenCodeLogger genCodeLogger;
    private String c;
    private boolean d = true;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private String j;
    private BarcodeFormat k;
    private int l;
    private ErrorCorrectionLevel m = ErrorCorrectionLevel.H;
    private Bitmap n;
    private String o;
    private DrawCoreTypes p;
    private boolean q = false;
    private String r;
    private boolean s;
    private boolean t = true;

    public CodeBuilder(String content, BarcodeFormat format) {
        this.j = content;
        this.k = format;
        this.p = DrawCoreTypes.DrawCoreType_FullBitmap;
        this.e = 0;
    }

    public Bitmap createBitmap(int width, int height) {
        Bitmap ret;
        this.f = width;
        this.g = height;
        BitMatrix bitMatrix = a();
        if (bitMatrix == null) {
            return null;
        }
        if (isMeizuPro6Plus()) {
            this.p = DrawCoreTypes.DrawCoreType_Drawable;
        }
        Drawable drawable = a(bitMatrix, this.p);
        if (this.l == 0) {
            ret = Bitmap.createBitmap(width, height, Config.RGB_565);
        } else {
            ret = Bitmap.createBitmap(height, width, Config.RGB_565);
        }
        drawable.draw(new Canvas(ret));
        a(bitMatrix.getWidth());
        return ret;
    }

    public void genCodeToImageView(ImageView imageView) {
        if (imageView.getVisibility() != 0) {
            Log.d("CodeBuilder", "imageView not visible");
            return;
        }
        if (isMeizuPro6Plus()) {
            this.p = DrawCoreTypes.DrawCoreType_Drawable;
        }
        this.f = (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
        this.g = (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
        if (this.f <= 0 || this.g <= 0) {
            if (this.h <= 0 || this.i <= 0) {
                Log.d("CodeBuilder", "size is incorrect " + this.f + Token.SEPARATOR + this.g);
                return;
            } else {
                this.f = this.h;
                this.g = this.i;
            }
        }
        BitMatrix bitMatrix = a();
        if (this.p == DrawCoreTypes.DrawCoreType_FullBitmap) {
            if (this.k == BarcodeFormat.QR_CODE) {
                if (this.f > this.g) {
                    this.f = this.g;
                } else {
                    this.g = this.f;
                }
            }
            s drawable = a(bitMatrix, DrawCoreTypes.DrawCoreType_Bitmap);
            Bitmap ret = Bitmap.createBitmap(this.f, this.g, Config.RGB_565);
            drawable.draw(new Canvas(ret));
            imageView.setImageMatrix(null);
            imageView.setImageBitmap(ret);
        } else {
            imageView.setImageDrawable(a(bitMatrix, this.p));
        }
        a(bitMatrix.getWidth());
    }

    private void a(int mapVersionWidth) {
        try {
            if (genCodeLogger != null) {
                Map extParams = new HashMap();
                extParams.put("bizType", this.r);
                extParams.put("ecLevel", this.m.name());
                extParams.put("width", String.valueOf(this.f));
                extParams.put("height", String.valueOf(this.g));
                extParams.put("strCode", this.j);
                extParams.put("mapVersionWidth", String.valueOf(mapVersionWidth));
                extParams.put("padding", String.valueOf(this.e));
                extParams.put("forceNoPadding", String.valueOf(this.q));
                extParams.put("haslogo", this.n == null ? "false" : "true");
                genCodeLogger.logGenCode(this.k.name(), this.r, this.m.name(), extParams);
            }
        } catch (Exception e2) {
            Log.e("CodeBuilder", "logGenCode error", e2);
        }
    }

    private s a(BitMatrix bitMatrix, DrawCoreTypes drawCoreType) {
        if (this.k == BarcodeFormat.QR_CODE) {
            return new p(bitMatrix, this.n, this.f, this.g, this.e, this.q, this.l, this.s, this.t, drawCoreType);
        }
        return new a(bitMatrix, this.f, this.g, this.e, this.q, this.l, this.j, this.o, drawCoreType);
    }

    public CodeBuilder setRotate(boolean r2) {
        return setRotateAngle(r2 ? 90 : 0);
    }

    public CodeBuilder setRotateAngle(int rotate) {
        this.l = rotate;
        return this;
    }

    public CodeBuilder setErrorCorrectionLevel(ErrorCorrectionLevel ec) {
        this.m = ec;
        return this;
    }

    public CodeBuilder setAvatar(Bitmap avatar) {
        this.n = avatar;
        return this;
    }

    public CodeBuilder setLogoMargin(boolean logoMargin) {
        this.s = logoMargin;
        return this;
    }

    public CodeBuilder setBarcodeDisplayText(String barcodeDisplayText) {
        this.o = barcodeDisplayText;
        return this;
    }

    public CodeBuilder setPadding(int padding) {
        this.e = padding;
        return this;
    }

    public CodeBuilder setMixedMode(boolean mixedMode) {
        this.d = mixedMode;
        return this;
    }

    public CodeBuilder setCharSet(String charSet) {
        this.c = charSet;
        return this;
    }

    public CodeBuilder setCoreType(DrawCoreTypes _drawCoreType) {
        this.p = _drawCoreType;
        return this;
    }

    public CodeBuilder setHintSize(int hintWidth, int hintHeight) {
        this.i = hintHeight;
        this.h = hintWidth;
        return this;
    }

    public CodeBuilder setForceNoPadding() {
        this.q = true;
        return this;
    }

    public CodeBuilder setFixedSize(boolean val) {
        this.t = val;
        return this;
    }

    public void setBizType(String bizType) {
        this.r = bizType;
    }

    private BitMatrix a() {
        BitMatrix bitMatrix = null;
        if (this.j == null || this.f > 10000 || this.g > 10000 || this.f <= 0 || this.g <= 0) {
            Log.d("CodeBuilder", "out encodeAsBitmapDecimal too big");
            return bitMatrix;
        }
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, this.m);
        hints.put(EncodeHintType.CHARACTER_SET, TextUtils.isEmpty(this.c) ? "ISO-8859-1" : this.c);
        hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
        hints.put(EncodeHintType.MIXED_ENCODING, Boolean.valueOf(this.d));
        try {
            return new MultiFormatWriter().encode(this.j, this.k, 1, 1, hints);
        } catch (Exception iae) {
            Log.e("CodeBuilder", "out 2 encodeAsBitmapDecimal", iae);
            return bitMatrix;
        }
    }

    public static boolean isMeizu() {
        if (a != null) {
            return a.booleanValue();
        }
        Boolean valueOf = Boolean.valueOf((Build.MANUFACTURER + Build.MODEL).toUpperCase().contains("MEIZU"));
        a = valueOf;
        return valueOf.booleanValue();
    }

    public static boolean isMeizuPro6Plus() {
        if (b == null) {
            boolean ret = (Build.MANUFACTURER + Build.MODEL).equals("MeizuPRO 6 Plus");
            b = new Boolean(ret);
            Log.d("CodeBuilder", "init is MeizuPro6P,isMeizuPro6Plus: " + ret);
        }
        Log.d("CodeBuilder", "isMeizuPro6Plus: " + b);
        return b.booleanValue();
    }
}
