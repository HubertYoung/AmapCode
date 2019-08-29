package com.autonavi.minimap.ajx3;

import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;

public class StaticLayoutProxy {
    public static StaticLayout create(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, TruncateAt truncateAt, int i4, int i5) {
        try {
            StaticLayout staticLayout = new StaticLayout(charSequence, i, i2, textPaint, i3, alignment, textDirectionHeuristic, f, f2, z, truncateAt, i4, i5);
            return staticLayout;
        } catch (IllegalArgumentException e) {
            IllegalArgumentException illegalArgumentException = e;
            if (illegalArgumentException.getMessage().contains("utext_close")) {
                StaticLayout staticLayout2 = new StaticLayout(charSequence, i, i2, textPaint, i3, alignment, textDirectionHeuristic, f, f2, z, truncateAt, i4, i5);
                return staticLayout2;
            }
            throw illegalArgumentException;
        }
    }
}
