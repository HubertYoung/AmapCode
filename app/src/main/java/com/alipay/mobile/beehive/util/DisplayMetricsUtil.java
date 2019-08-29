package com.alipay.mobile.beehive.util;

import android.content.Context;
import android.util.DisplayMetrics;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class DisplayMetricsUtil {
    public static int scaleHeightbyWidthPixels(Context ctx, int width, int height) {
        return (height * ctx.getResources().getDisplayMetrics().widthPixels) / width;
    }

    public static int getWidthPixels(Context ctx) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels <= displayMetrics.heightPixels) {
            return displayMetrics.widthPixels;
        }
        Map extInfo = new HashMap();
        extInfo.put("widthPixels", String.valueOf(displayMetrics.widthPixels));
        extInfo.put("heightPixels", String.valueOf(displayMetrics.heightPixels));
        LoggerFactory.getMonitorLogger().mtBizReport("BIZ_BEEHIVE", "BIZ_BEEHIVE_GET_WIDTHPIXELS_EX", "500", extInfo);
        return displayMetrics.heightPixels;
    }
}
