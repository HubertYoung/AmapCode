package com.autonavi.minimap.ajx3.layout;

import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class StaticLayoutHelper {
    private static final int CACHE_SIZE = 20;
    private static LinkedHashMap<LayoutModel, StaticLayout> LRU_ACCESS_ORDER = new LinkedHashMap<LayoutModel, StaticLayout>(20, 0.75f, true) {
        /* access modifiers changed from: protected */
        public final boolean removeEldestEntry(Entry entry) {
            return size() > 20;
        }
    };

    static class LayoutModel {
        private boolean isMult;
        private boolean isRichText;
        float lineHeight;
        private int maxLines;
        private String text;
        private float textSize;
        private Typeface typeface;
        private int width;

        LayoutModel(String str, boolean z, boolean z2, float f, int i, float f2, Typeface typeface2, int i2) {
            this.text = str;
            this.isRichText = z;
            this.isMult = z2;
            this.lineHeight = f;
            this.width = i;
            this.textSize = f2;
            this.typeface = typeface2;
            this.maxLines = i2;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LayoutModel)) {
                return false;
            }
            LayoutModel layoutModel = (LayoutModel) obj;
            return this.isMult == layoutModel.isMult && this.isRichText == layoutModel.isRichText && this.lineHeight == layoutModel.lineHeight && this.width == layoutModel.width && this.textSize == layoutModel.textSize && this.typeface == layoutModel.typeface && this.maxLines == layoutModel.maxLines && this.text.equals(layoutModel.text);
        }

        public int hashCode() {
            return this.text.hashCode();
        }
    }

    public static StaticLayout make(boolean z, float f, String str, boolean z2, int i, float f2, Typeface typeface, int i2) {
        LayoutModel layoutModel = new LayoutModel(str, z2, z, f, i, f2, typeface, i2);
        StaticLayout staticLayout = LRU_ACCESS_ORDER.get(layoutModel);
        if (staticLayout != null) {
            return staticLayout;
        }
        StaticLayout makeInner = makeInner(z, f, str, z2, i, f2, typeface, i2);
        LRU_ACCESS_ORDER.put(layoutModel, makeInner);
        return makeInner;
    }

    private static StaticLayout makeInner(boolean z, float f, String str, boolean z2, int i, float f2, Typeface typeface, int i2) {
        SpannableStringBuilder spannableStringBuilder;
        Spanned spanned;
        String str2 = str;
        float f3 = f2;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f3);
        textPaint.setTypeface(typeface);
        if (!z2) {
            spannableStringBuilder = new SpannableStringBuilder(str2);
        } else {
            if (VERSION.SDK_INT >= 24) {
                spanned = Html.fromHtml(str2, 0);
            } else {
                spanned = Html.fromHtml(str2);
            }
            spannableStringBuilder = (SpannableStringBuilder) spanned;
        }
        spannableStringBuilder.setSpan(new CssLineHeightSpan(z, f, f3, textPaint.getTypeface()), 0, spannableStringBuilder.length(), 33);
        if (StaticLayoutWithMaxLines.isSupported()) {
            return StaticLayoutWithMaxLines.create(spannableStringBuilder, 0, spannableStringBuilder.length(), textPaint, i, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true, null, 0, i2);
        }
        StaticLayout staticLayout = new StaticLayout(spannableStringBuilder, 0, spannableStringBuilder.length(), textPaint, i, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return staticLayout;
    }
}
