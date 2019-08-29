package com.autonavi.minimap.ajx3.platform.impl;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.LruCache;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.layout.TextLayoutBuilder;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.ITextMeasurement;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TextMeasurement implements ITextMeasurement {
    public static final HashMap<String, String> S_CUSTOM_FONTS = new HashMap<>();
    private static final LruCache<String, Typeface> S_DYNAMIC_TYPEFACE_CACHE = new LruCache<>(16);
    private static ConcurrentHashMap<Long, Layout> sLayouts = new ConcurrentHashMap<>();
    private Context mContext;

    public static boolean isFontBold(int i) {
        return i == 1056964735 || i == 1056964741;
    }

    TextMeasurement(@NonNull Context context) {
        this.mContext = context;
    }

    public float[] measure(String str, int i, int i2, int i3, boolean z, float f, int i4, boolean z2, float f2, int i5, float f3, int i6, long j, int i7, String str2, int i8) {
        float f4 = f2;
        int i9 = i5;
        if (TextUtils.isEmpty(str)) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        if (f4 <= 0.0f) {
            sLayouts.remove(Long.valueOf(j));
            return new float[]{f4, f3, 0.0f};
        } else if (i9 == 1 && i6 == 1) {
            return new float[]{f4, f3};
        } else {
            Layout createTextLayout = createTextLayout(str, i, i2, i3, z, f, i4, z2, f4, i9 == 1 ? 2 : i9, i7, str2, i8 == 1056964609 ? Alignment.ALIGN_CENTER : Alignment.ALIGN_NORMAL);
            if (createTextLayout == null) {
                return new float[]{0.0f, 0.0f, 0.0f};
            }
            int width = createTextLayout.getWidth();
            int height = createTextLayout.getHeight();
            sLayouts.put(Long.valueOf(j), createTextLayout);
            int ceil = (int) Math.ceil((double) DimensionUtils.pixelToStandardUnit((float) width));
            int ceil2 = (int) Math.ceil((double) DimensionUtils.pixelToStandardUnit((float) height));
            int lineCount = createTextLayout.getLineCount();
            if (((float) ceil) >= f4) {
                ceil = (int) f4;
            }
            return new float[]{(float) ceil, (float) ceil2, (float) lineCount};
        }
    }

    public void onRelease(long j) {
        sLayouts.remove(Long.valueOf(j));
    }

    public float getBaselineOfFirstLine(String str, int i, int i2, int i3, boolean z, float f, int i4, boolean z2, float f2, int i5, float f3, int i6, long j, int i7, String str2, int i8) {
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        Layout createTextLayout = createTextLayout(str, i, i2, i3, z, f, i4, z2, f2, i5, i7, str2, i8 == 1056964609 ? Alignment.ALIGN_CENTER : Alignment.ALIGN_NORMAL);
        if (createTextLayout == null) {
            return 0.0f;
        }
        return (float) ((int) DimensionUtils.pixelToStandardUnit((float) createTextLayout.getLineBaseline(0)));
    }

    public static Typeface generateTypeface(Context context, int i, int i2, String str) {
        Typeface defaultTypeface = Ajx.getInstance().getDefaultTypeface(0);
        if (i == 1056964735 || i == 1056964741) {
            if (i2 == 1056964737) {
                defaultTypeface = Ajx.getInstance().getDefaultTypeface(3);
            }
        } else if (i2 == 1056964737) {
            defaultTypeface = Ajx.getInstance().getDefaultTypeface(2);
        }
        return !TextUtils.isEmpty(str) ? createCustomTypeface(context, S_CUSTOM_FONTS.get(str), defaultTypeface) : defaultTypeface;
    }

    private Layout createTextLayout(String str, int i, int i2, int i3, boolean z, float f, int i4, boolean z2, float f2, int i5, int i6, String str2, Alignment alignment) {
        TextMeasurement textMeasurement;
        float f3;
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(f2);
        int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel((float) i);
        if (!z) {
            textMeasurement = this;
            f3 = (float) DimensionUtils.standardUnitToPixel(f);
        } else {
            textMeasurement = this;
            f3 = f;
        }
        Typeface generateTypeface = generateTypeface(textMeasurement.mContext, i2, i3, str2);
        Layout buildTextLayout = buildTextLayout(str, z2, generateTypeface, standardUnitToPixel2, standardUnitToPixel, i5, i4, z, f3, i6, alignment, null);
        if (buildTextLayout != null && generateTypeface == Ajx.getInstance().getDefaultTypeface(0) && isFontBold(i2)) {
            buildTextLayout.getPaint().setFakeBoldText(true);
        }
        return buildTextLayout;
    }

    private static Typeface createCustomTypeface(Context context, String str, Typeface typeface) {
        if (TextUtils.isEmpty(str)) {
            return typeface;
        }
        Typeface typeface2 = S_DYNAMIC_TYPEFACE_CACHE.get(str);
        if (typeface2 != null) {
            return typeface2;
        }
        try {
            Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), str);
            S_DYNAMIC_TYPEFACE_CACHE.put(str, createFromAsset);
            return createFromAsset;
        } catch (Exception e) {
            e.printStackTrace();
            return typeface;
        }
    }

    public static Layout buildTextLayout(String str, boolean z, Typeface typeface, int i, int i2, int i3, int i4, boolean z2, float f, int i5, Alignment alignment) {
        return buildTextLayout(str, z, typeface, i, i2, i3, i4, z2, f, i5, alignment, null);
    }

    public static Layout buildTextLayout(String str, boolean z, Typeface typeface, int i, int i2, int i3, int i4, boolean z2, float f, int i5, Alignment alignment, View view) {
        int i6;
        TruncateAt truncateAt = null;
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder("Layout: ");
            sb.append(i2);
            sb.append(" < 0");
            LogHelper.loge(sb.toString());
            return null;
        }
        switch (i3) {
            case 0:
                i6 = 0;
                break;
            case 1:
                i6 = 1;
                break;
            case 2:
                i6 = 2;
                break;
            default:
                throw new IllegalStateException("Unexpected size mode: ".concat(String.valueOf(i3)));
        }
        switch (i5) {
            case Property.NODE_PROPERTY_TEXT_OVERFLOW_ELLIPSIS /*1056964725*/:
                truncateAt = TruncateAt.END;
                break;
            case Property.NODE_PROPERTY_TEXT_OVERFLOW_MIDDLE /*1056964726*/:
                truncateAt = TruncateAt.MIDDLE;
                break;
            case Property.NODE_PROPERTY_TEXT_OVERFLOW_HEAD /*1056964727*/:
                truncateAt = TruncateAt.START;
                break;
        }
        TextLayoutBuilder textLayoutBuilder = new TextLayoutBuilder();
        textLayoutBuilder.setMaxLines(i4).setText(str).setTextSize(i).setWidth(i2, i6).setTypeface(typeface).setIsRichText(z).setLineHeight(f, z2).setEllipsize(truncateAt).setLayoutAlignment(alignment).setView(view);
        return textLayoutBuilder.build();
    }

    public static Layout fetchLayout(long j) {
        return sLayouts.remove(Long.valueOf(j));
    }

    public static void clearCache() {
        sLayouts.clear();
    }
}
