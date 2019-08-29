package com.autonavi.jni.ae.gmap.glyph;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import java.nio.ByteBuffer;

public class GlyphLoader {
    private static native long nativeCreateGlyphLoader();

    private static native void nativeDestroyGlyphLoader(long j);

    private static GlyphRawInfo load(short s, FontStyle fontStyle, boolean z) {
        GlyphRawInfo glyphRawInfo = new GlyphRawInfo();
        if (fontStyle == null) {
            return glyphRawInfo;
        }
        String decodeUnicode = decodeUnicode(s);
        if (TextUtils.isEmpty(decodeUnicode)) {
            return glyphRawInfo;
        }
        try {
            TextPaint newTextPaint = newTextPaint(fontStyle);
            Rect rect = new Rect();
            newTextPaint.getTextBounds(decodeUnicode, 0, decodeUnicode.length(), rect);
            if (fontStyle.isStroke && fontStyle.lineWidth > 0.0f) {
                float f = fontStyle.lineWidth / 2.0f;
                rect.top = (int) (((float) rect.top) - f);
                rect.left = (int) (((float) rect.left) - f);
                rect.right = (int) (((float) rect.right) + f);
                rect.bottom = (int) (((float) rect.bottom) + f);
            }
            if (z && !rect.isEmpty()) {
                Bitmap createBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Config.ALPHA_8);
                new Canvas(createBitmap).drawText(decodeUnicode, (float) (0 - rect.left), (float) (0 - rect.top), newTextPaint);
                byte[] bArr = new byte[(rect.width() * rect.height())];
                createBitmap.copyPixelsToBuffer(ByteBuffer.wrap(bArr));
                createBitmap.recycle();
                glyphRawInfo.bitmapBuf = bArr;
            }
            glyphRawInfo.sucess = true;
            glyphRawInfo.width = rect.width();
            glyphRawInfo.height = rect.height();
            glyphRawInfo.left = (float) rect.left;
            glyphRawInfo.top = (float) rect.top;
            glyphRawInfo.advance = newTextPaint.measureText(decodeUnicode);
        } catch (Exception unused) {
            glyphRawInfo.sucess = false;
        }
        return glyphRawInfo;
    }

    private static GlyphRawInfo getGlyphMetrics(short s, FontStyle fontStyle) {
        return load(s, fontStyle, false);
    }

    private static GlyphRawInfo getGlyphRaster(short s, FontStyle fontStyle) {
        return load(s, fontStyle, true);
    }

    private static FontMetrics getFontMetrics(FontStyle fontStyle) {
        FontMetrics fontMetrics = newTextPaint(fontStyle).getFontMetrics();
        FontMetrics fontMetrics2 = new FontMetrics();
        fontMetrics2.ascent = fontMetrics.ascent;
        fontMetrics2.descent = fontMetrics.descent;
        fontMetrics2.leading = fontMetrics.leading;
        fontMetrics2.top = fontMetrics.top;
        fontMetrics2.bottom = fontMetrics.bottom;
        return fontMetrics2;
    }

    private static String decodeUnicode(short s) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append((char) s);
        return stringBuffer.toString();
    }

    private static TextPaint newTextPaint(FontStyle fontStyle) {
        boolean z;
        boolean z2;
        Typeface typeface;
        TextPaint textPaint = new TextPaint();
        if (fontStyle == null) {
            return textPaint;
        }
        textPaint.setColor(-1);
        int i = 1;
        textPaint.setAntiAlias(true);
        textPaint.setFilterBitmap(true);
        textPaint.setTextSize(fontStyle.size);
        textPaint.setTextAlign(Align.LEFT);
        if (fontStyle.isStroke) {
            textPaint.setStyle(Style.STROKE);
            textPaint.setStrokeWidth(fontStyle.lineWidth);
        } else {
            textPaint.setStyle(Style.FILL);
        }
        switch (fontStyle.style) {
            case 1:
            case 2:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        switch (fontStyle.weight) {
            case 500:
            case 600:
            case 700:
            case 800:
            case 900:
            case 1000:
                z2 = true;
                break;
            default:
                z2 = false;
                break;
        }
        if (z2 && z) {
            i = 3;
        } else if (!z2) {
            i = z ? 2 : 0;
        }
        if (fontStyle.family.isEmpty()) {
            typeface = Typeface.create(Typeface.DEFAULT, i);
        } else {
            typeface = Typeface.create(fontStyle.family, i);
        }
        textPaint.setTypeface(typeface);
        if (fontStyle.variant == 16 && VERSION.SDK_INT >= 21) {
            textPaint.setFontFeatureSettings("smcp");
        }
        return textPaint;
    }

    public static long createGlyphLoader() {
        return nativeCreateGlyphLoader();
    }

    public static void destroyGlyphLoader(long j) {
        nativeDestroyGlyphLoader(j);
    }
}
