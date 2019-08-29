package com.autonavi.minimap.nativesupport.platform;

import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.autonavi.minimap.ackor.ackorplatform.IDeviceService.INativeFont;

public class NativeFont implements INativeFont {
    private TextPaint mTextPaint = new TextPaint();

    public NativeFont(int i, int i2) {
        this.mTextPaint.setTextSize((float) i);
        this.mTextPaint.setTypeface(Typeface.DEFAULT);
        switch (i2) {
            case 1:
                this.mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
                return;
            case 2:
                this.mTextPaint.setTypeface(Typeface.create(null, 2));
                return;
            default:
                this.mTextPaint.setTypeface(Typeface.DEFAULT);
                return;
        }
    }

    public int getFontHeight() {
        FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        return (int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
    }

    public int getStringWrapWidth(String str) {
        return (int) this.mTextPaint.measureText(str);
    }

    public int getStringWrapHeight(String str, int i) {
        StaticLayout staticLayout = new StaticLayout(str, this.mTextPaint, i, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return staticLayout.getLineCount() * getFontHeight();
    }
}
