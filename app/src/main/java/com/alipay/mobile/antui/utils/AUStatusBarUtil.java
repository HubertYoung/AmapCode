package com.alipay.mobile.antui.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import com.alipay.mobile.antui.BuildConfig;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.map.core.MapCustomizeManager;

public class AUStatusBarUtil {
    private static int statusBarHeight = 0;
    private static int titleBarAUId = 0;

    public static boolean isSupport() {
        if (VERSION.SDK_INT >= 21) {
            return true;
        }
        return false;
    }

    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, int color) {
        if (isSupport() && activity != null) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            window.setStatusBarColor(color);
        }
    }

    @TargetApi(21)
    public static boolean adjustTitleBarTranslucent(Activity activity, int color) {
        return adjustTitleBarTranslucent(activity, findTitleBarView(activity), color);
    }

    @TargetApi(21)
    public static boolean adjustTitleBarTranslucent(Activity activity, View statusBarView, int color) {
        if (!isSupport() || activity == null || statusBarView == null) {
            return false;
        }
        LayoutParams layoutParams = statusBarView.getLayoutParams();
        layoutParams.height = getStatusBarHeight(activity);
        statusBarView.setLayoutParams(layoutParams);
        statusBarView.setVisibility(0);
        setFullScreenTranslucent(activity, color);
        return true;
    }

    @TargetApi(21)
    public static void setFullScreenTranslucent(Activity activity, int color) {
        if (isSupport() && activity != null) {
            Window window = activity.getWindow();
            window.clearFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            window.addFlags(Integer.MIN_VALUE);
            window.getDecorView().setSystemUiVisibility(1280);
            window.setStatusBarColor(color);
        }
    }

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android"));
        }
        return statusBarHeight;
    }

    private static View findTitleBarView(Activity activity) {
        if (initAUTitlebarId(activity) != 0) {
            return activity.findViewById(titleBarAUId);
        }
        return null;
    }

    private static int initAUTitlebarId(Activity activity) {
        if (titleBarAUId == 0) {
            titleBarAUId = activity.getResources().getIdentifier("title_bar_status_bar", "id", BuildConfig.APPLICATION_ID);
        }
        return titleBarAUId;
    }
}
