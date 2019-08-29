package com.autonavi.minimap.ajx3.layout;

import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import com.autonavi.minimap.ajx3.StaticLayoutProxy;

public class AjxStaticLayout {
    public static final int P = 28;

    public static StaticLayout create(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, TruncateAt truncateAt, int i4, int i5) {
        if (VERSION.SDK_INT > 28) {
            return Builder.obtain(charSequence, i, i2, textPaint, i3).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setIncludePad(z).setEllipsize(truncateAt).setEllipsizedWidth(i4).setMaxLines(i5).build();
        }
        return StaticLayoutProxy.create(charSequence, i, i2, textPaint, i3, alignment, textDirectionHeuristic, f, f2, z, truncateAt, i4, i5);
    }
}
