package com.autonavi.minimap.ajx3.platform.impl;

import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.autonavi.minimap.ajx3.layout.StaticLayoutHelper;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.INativeFont;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

public class NativeFont implements INativeFont {
    private boolean mIsRichtext;
    private TextPaint mTextPaint = new TextPaint();

    NativeFont(int i, int i2, boolean z) {
        this.mIsRichtext = z;
        this.mTextPaint.setTextSize((float) DimensionUtils.standardUnitToPixel((float) i));
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
        return (int) Math.ceil((double) DimensionUtils.pixelToStandardUnit(this.mTextPaint.descent() - this.mTextPaint.ascent()));
    }

    public int getStringWrapWidth(String str) {
        float f;
        Spanned spanned;
        if (this.mIsRichtext) {
            if (VERSION.SDK_INT >= 24) {
                spanned = Html.fromHtml(str, 0);
            } else {
                spanned = Html.fromHtml(str);
            }
            f = this.mTextPaint.measureText(spanned, 0, spanned.length());
        } else {
            f = this.mTextPaint.measureText(str, 0, str.length());
        }
        return (int) Math.ceil(((double) DimensionUtils.pixelToStandardUnit(f)) * 1.1d);
    }

    public int getStringWrapHeight(String str, int i, float f, boolean z, int i2) {
        return (int) DimensionUtils.pixelToStandardUnit((float) createTextLayout(str, i, f, z, i2, this.mIsRichtext).getHeight());
    }

    public int getBaselineOfFirstLine(String str, int i, float f, boolean z, int i2) {
        return (int) DimensionUtils.pixelToStandardUnit((float) createTextLayout(str, i, f, z, i2, this.mIsRichtext).getLineBaseline(0));
    }

    private StaticLayout createTextLayout(String str, int i, float f, boolean z, int i2, boolean z2) {
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel((float) i);
        if (!z) {
            f = (float) DimensionUtils.standardUnitToPixel(f);
        }
        return StaticLayoutHelper.make(z, f, str, z2, standardUnitToPixel, this.mTextPaint.getTextSize(), Typeface.DEFAULT, i2);
    }
}
