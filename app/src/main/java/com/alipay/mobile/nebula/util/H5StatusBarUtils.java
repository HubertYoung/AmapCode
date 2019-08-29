package com.alipay.mobile.nebula.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Window;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.autonavi.map.core.MapCustomizeManager;

public class H5StatusBarUtils {
    private static final String TSBS = "TSBS";
    private static final String TSBSOFF = "TSBSOFF";
    private static int statusBarHeight = 0;

    public static boolean isSupport() {
        if (VERSION.SDK_INT >= 21) {
            return true;
        }
        return false;
    }

    @TargetApi(21)
    public static void setTransparentColor(Activity activity, int color) {
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

    public static boolean isConfigSupport() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            if (TextUtils.equals(h5ConfigProvider.getConfig(TSBS), "0")) {
                return false;
            }
            String model = h5ConfigProvider.getConfig(TSBSOFF);
            String currentModel = Build.MODEL;
            if (!TextUtils.isEmpty(model) && model.contains(currentModel)) {
                return false;
            }
        }
        return true;
    }
}
