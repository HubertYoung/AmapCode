package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.view.View;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;

public class DensityUtil {
    private static float scale;
    private static float scaledDensity;

    public static int dip2px(Context context, float dpValue) {
        initScale(context);
        if (AUScreenUtils.checkApFlag(context, null, null)) {
            return AUScreenAdaptTool.getApFromDp(context, dpValue);
        }
        return (int) ((scale * dpValue) + 0.5f);
    }

    private static void initScale(Context context) {
        try {
            if (scale == 0.0f) {
                scale = context.getResources().getDisplayMetrics().density;
            }
        } catch (Throwable th) {
        }
    }

    public static int px2dip(Context context, float pxValue) {
        initScale(context);
        return (int) ((pxValue / scale) + 0.5f);
    }

    public static int getRelativeTop(View myView) {
        if (myView.getId() == 16908290) {
            return myView.getTop();
        }
        return getRelativeTop((View) myView.getParent()) + myView.getTop();
    }

    public static int getRelativeLeft(View myView) {
        if (myView.getId() == 16908290) {
            return myView.getLeft();
        }
        return getRelativeLeft((View) myView.getParent()) + myView.getLeft();
    }

    public static float getTextSize(float defaultSize, int gear) {
        return getScale(gear) * defaultSize;
    }

    public static float getScale(int pos) {
        switch (pos) {
            case 0:
                return 0.875f;
            case 1:
                return 1.0f;
            case 2:
                return 1.125f;
            case 3:
                return 1.25f;
            case 4:
                return 1.375f;
            default:
                return 1.0f;
        }
    }

    public static float getFontSize(float paramFloat) {
        if (paramFloat == 0.875f) {
            return 14.0f;
        }
        if (paramFloat == 1.0f) {
            return 16.0f;
        }
        if (paramFloat == 1.125f) {
            return 18.0f;
        }
        if (paramFloat == 1.25f) {
            return 20.0f;
        }
        if (paramFloat == 1.375f) {
            return 22.0f;
        }
        return 16.0f;
    }

    public static boolean isValueEqule(float v1, float v2) {
        return ((int) v1) == ((int) v2);
    }

    public static float px2sp(Context context, float pxValue) {
        initScaledDensity(context);
        return pxValue / scaledDensity;
    }

    public static int sp2px(Context context, float spValue) {
        initScaledDensity(context);
        return (int) ((scaledDensity * spValue) + 0.5f);
    }

    private static void initScaledDensity(Context context) {
        try {
            if (scaledDensity == 0.0f) {
                scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
            }
        } catch (Throwable th) {
        }
    }
}
