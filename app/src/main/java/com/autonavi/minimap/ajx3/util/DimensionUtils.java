package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import java.lang.ref.WeakReference;

public class DimensionUtils {
    private static WeakReference<Context> mContext;
    private static float mDensity;
    private static int mHeightPixels;
    private static int mWidthPixels;

    private static int rectifyLeftOrTop(float f) {
        int i = (int) (0.5f + f);
        float f2 = ((float) i) - f;
        return (f2 >= 0.001f || f2 <= 0.0f) ? (int) f : i;
    }

    public static void initDensity(Context context) {
        mContext = new WeakReference<>(context);
        update();
    }

    private static void update() {
        if (mContext != null) {
            Context context = (Context) mContext.get();
            if (context != null) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                mDensity = displayMetrics.density;
                mWidthPixels = displayMetrics.widthPixels;
                mHeightPixels = displayMetrics.heightPixels;
            }
        }
    }

    public static float getDensisty() {
        update();
        return mDensity;
    }

    public static int getWidthPixels() {
        update();
        return mWidthPixels;
    }

    public static int getHeightPixels() {
        update();
        return mHeightPixels;
    }

    private static float dipToPixelPrecise(float f) {
        return f * mDensity;
    }

    public static int standardUnitToPixelOfLayout(float f) {
        return rectifyLeftOrTop(dipToPixelFloat(f / 2.0f, 0.0f));
    }

    public static int standardUnitToPixel(float f) {
        return dipToPixel(f / 2.0f);
    }

    public static float standardUnitToPixelPrecise(float f) {
        return dipToPixelPrecise(f / 2.0f);
    }

    public static float pixelToStandardUnit(float f) {
        return pixelToDip(f) * 2.0f;
    }

    public static int dipToPixel(float f) {
        return dipToPixel(f, 0.5f);
    }

    public static float dipToPixelFloat(float f, float f2) {
        try {
            float f3 = mDensity * f;
            if (f <= 0.0f || f3 >= 1.0f) {
                return f3 + f2;
            }
            return 1.0f;
        } catch (Exception e) {
            e.printStackTrace();
            return f;
        }
    }

    public static int dipToPixel(float f, float f2) {
        try {
            float f3 = mDensity * f;
            if (f <= 0.0f || f3 >= 1.0f) {
                return (int) (f3 + f2);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return (int) f;
        }
    }

    public static float pixelToDip(float f) {
        if (mDensity == 0.0f) {
            return f;
        }
        return f / mDensity;
    }

    @Deprecated
    public static int standardUnitToPixel(@NonNull Context context, float f) {
        return dipToPixel(f / 2.0f);
    }

    @Deprecated
    public static float pixelToStandardUnit(@NonNull Context context, float f) {
        return pixelToDip(f) * 2.0f;
    }

    @Deprecated
    public static int dipToPixel(Context context, float f) {
        return dipToPixel(f, 0.5f);
    }
}
