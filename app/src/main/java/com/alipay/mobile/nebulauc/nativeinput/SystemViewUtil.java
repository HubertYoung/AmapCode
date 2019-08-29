package com.alipay.mobile.nebulauc.nativeinput;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.lang.reflect.Method;

public class SystemViewUtil {
    private static final String NAVIGATION_GESTURE = "navigation_gesture_on";
    private static final int NAVIGATION_GESTURE_OFF = 0;
    private static final String TAG = "SystemViewUtil";
    private static int sScreenHeight = 0;

    public static int getNavigationBarHeight(Context context) {
        if (VERSION.SDK_INT < 17) {
            return 0;
        }
        if ((Build.BRAND.toLowerCase().equals(DeviceProperty.ALIAS_VIVO) || Build.MANUFACTURER.toLowerCase().equals(DeviceProperty.ALIAS_VIVO)) && (!deviceHasNavigationBar() || vivoNavigationGestureEnabled(context))) {
            return 0;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
        if (windowManager == null) {
            return 0;
        }
        Display display = windowManager.getDefaultDisplay();
        if (display == null) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        int realHeight = dm.heightPixels;
        display.getMetrics(dm);
        int height = dm.heightPixels;
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        display.getRealSize(realSize);
        if (realHeight > height) {
            return realHeight - height;
        }
        return 0;
    }

    public static boolean deviceHasNavigationBar() {
        boolean haveNav = false;
        try {
            Method getWmServiceMethod = Class.forName("android.view.WindowManagerGlobal").getDeclaredMethod("getWindowManagerService", new Class[0]);
            getWmServiceMethod.setAccessible(true);
            Object iWindowManager = getWmServiceMethod.invoke(null, new Object[0]);
            Method hasNavBarMethod = iWindowManager.getClass().getDeclaredMethod("hasNavigationBar", new Class[0]);
            hasNavBarMethod.setAccessible(true);
            return ((Boolean) hasNavBarMethod.invoke(iWindowManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return haveNav;
        }
    }

    private static boolean vivoNavigationGestureEnabled(Context context) {
        if (Secure.getInt(context.getContentResolver(), NAVIGATION_GESTURE, 0) != 0) {
            return true;
        }
        return false;
    }

    public static int getStatusBarHeight() {
        Resources res = LauncherApplicationAgent.getInstance().getApplicationContext().getResources();
        try {
            int resourceId = res.getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
            if (resourceId > 0) {
                return res.getDimensionPixelSize(resourceId);
            }
            return 0;
        } catch (Exception e) {
            H5Log.e(TAG, "getStatusBarHeight ", e);
            return 0;
        }
    }

    public static int getScreenHeight() {
        if (sScreenHeight != 0) {
            return sScreenHeight;
        }
        int height = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) H5Utils.getContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
        if (windowManager == null) {
            return 0;
        }
        Display display = windowManager.getDefaultDisplay();
        try {
            if (VERSION.SDK_INT >= 17) {
                display.getRealMetrics(metrics);
                height = metrics.heightPixels;
            } else {
                height = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(display, new Object[0])).intValue();
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getScreenHeight", e);
        }
        sScreenHeight = height;
        return height;
    }
}
