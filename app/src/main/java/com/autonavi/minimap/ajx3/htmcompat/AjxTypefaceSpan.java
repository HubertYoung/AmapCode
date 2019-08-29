package com.autonavi.minimap.ajx3.htmcompat;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.TypefaceSpan;
import android.util.LruCache;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;

public class AjxTypefaceSpan extends TypefaceSpan {
    private static final LruCache<String, Typeface> S_DYNAMIC_TYPEFACE_CACHE = new LruCache<>(16);

    public AjxTypefaceSpan(@Nullable String str) {
        super(str);
    }

    public void updateDrawState(TextPaint textPaint) {
        apply(textPaint, getFamily());
    }

    public void updateMeasureState(TextPaint textPaint) {
        apply(textPaint, getFamily());
    }

    private static void apply(Paint paint, String str) {
        int i;
        Typeface typeface = paint.getTypeface();
        if (typeface == null) {
            i = 0;
        } else {
            i = typeface.getStyle();
        }
        Typeface createCustomTypeface = createCustomTypeface(TextMeasurement.S_CUSTOM_FONTS.get(str));
        if (createCustomTypeface == null) {
            createCustomTypeface = Typeface.create(str, i);
        }
        int i2 = (~createCustomTypeface.getStyle()) & i;
        if ((i2 & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i2 & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(createCustomTypeface);
    }

    public static Typeface createCustomTypeface(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Typeface typeface = S_DYNAMIC_TYPEFACE_CACHE.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            Typeface createFromAsset = Typeface.createFromAsset(Ajx.getInstance().getAssets(), str);
            S_DYNAMIC_TYPEFACE_CACHE.put(str, createFromAsset);
            return createFromAsset;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
