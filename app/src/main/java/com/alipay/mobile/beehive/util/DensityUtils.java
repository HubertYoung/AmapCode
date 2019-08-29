package com.alipay.mobile.beehive.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public final class DensityUtils extends DensityUtil {
    private static DisplayMetrics mMetrics;

    private DensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }

    public static int getScreenHeight() {
        return getScreenHeight(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public static int getScreenWidth() {
        return getScreenWidth(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public static DisplayMetrics getMetrics(Context ctx) {
        if (mMetrics == null) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) ctx.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(metrics);
            mMetrics = metrics;
        }
        return mMetrics;
    }
}
