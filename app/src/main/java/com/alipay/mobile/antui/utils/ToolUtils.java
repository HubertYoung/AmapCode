package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class ToolUtils {
    public static boolean isAlipayApp(Context context) {
        try {
            String name = context.getPackageName();
            if (!TextUtils.isEmpty(name) && name.contains(SsoLoginInfo.TYPE_ALIPAY)) {
                return true;
            }
        } catch (Exception ex) {
            AuiLogger.error("AUTitleBar", "isAlipayApp" + ex.toString());
        }
        return false;
    }

    public static int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (ParseException e) {
            return -1;
        }
    }

    public static boolean isConcaveScreen(Context context) {
        if (VERSION.SDK_INT < 26) {
            return false;
        }
        String model = Build.BRAND;
        if (context == null || context.getPackageManager() == null || TextUtils.isEmpty(model)) {
            return false;
        }
        String model2 = model.toLowerCase();
        if (TextUtils.equals(model2, DeviceProperty.ALIAS_OPPO)) {
            return ConcaveUtils.checkOppoConcave(context);
        }
        if (TextUtils.equals(model2, DeviceProperty.ALIAS_VIVO)) {
            return ConcaveUtils.checkVivoConcave();
        }
        if (TextUtils.equals(model2, DeviceProperty.ALIAS_HUAWEI) || TextUtils.equals(model2, "honor")) {
            return ConcaveUtils.checkHuaweiConcave(context);
        }
        if (TextUtils.equals(model2, "xiaomi")) {
            return ConcaveUtils.checkXiaomiConcave();
        }
        return false;
    }

    public static int parseDimen(Context context, String dimen) {
        try {
            return DensityUtil.dip2px(context, Float.parseFloat(dimen));
        } catch (ParseException e) {
            return -1;
        }
    }

    public static int[] getScreenWidth_Height(Context context) {
        Display display = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        return new int[]{display.getWidth(), display.getHeight()};
    }

    public static String judgeRes(int resId) {
        switch (16711680 & resId) {
            case 131072:
                return ResUtils.DRAWABLE;
            case 458752:
                return ResUtils.STRING;
            default:
                return "";
        }
    }
}
