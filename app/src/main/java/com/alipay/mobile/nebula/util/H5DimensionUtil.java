package com.alipay.mobile.nebula.util;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class H5DimensionUtil {
    private static final String TAG = "H5DimensionUtil";
    private static float sScale;
    private static float sScaledDensity;
    private static int sScreenHeight;
    private static int sScreenWidth;

    public static int dip2px(Context context, float dpValue) {
        initScale(context);
        return (int) ((sScale * dpValue) + 0.5f);
    }

    private static void initScale(Context context) {
        try {
            if (sScale == 0.0f) {
                sScale = context.getResources().getDisplayMetrics().density;
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public static int px2dip(Context context, float pxValue) {
        initScale(context);
        return (int) ((pxValue / sScale) + 0.5f);
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
        return pxValue / sScaledDensity;
    }

    public static int sp2px(Context context, float spValue) {
        initScaledDensity(context);
        return (int) ((sScaledDensity * spValue) + 0.5f);
    }

    private static void initScaledDensity(Context context) {
        try {
            if (sScaledDensity == 0.0f) {
                sScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public static int getScreenWidth(Context context) {
        if (sScreenWidth != 0) {
            return sScreenWidth;
        }
        int width = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
        sScreenWidth = width;
        return width;
    }

    public static int getScreenHeight(Context context) {
        if (sScreenHeight != 0) {
            return sScreenHeight;
        }
        int height = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
        sScreenHeight = height;
        return height;
    }

    public static void resetScreenWidthHeight(Context context) {
        try {
            Display display = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
            sScreenHeight = display.getHeight();
            sScreenWidth = display.getWidth();
            sScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
            sScale = context.getResources().getDisplayMetrics().density;
            H5Log.debug(TAG, "reset width = " + sScreenWidth);
        } catch (Throwable tr) {
            H5Log.e(TAG, "resetScreenWidthHeight ", tr);
        }
    }
}
