package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;

public class H5EmbedViewUtil {
    static int a(String ori) {
        return a(ori, 0);
    }

    private static int a(String ori, int defaultColor) {
        if (TextUtils.isEmpty(ori)) {
            return defaultColor;
        }
        try {
            return a(Color.parseColor(ori));
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "H5EmbedViewUtil", "error, ori=" + ori);
            return defaultColor;
        }
    }

    private static int a(int rgba) {
        return ((rgba & 255) << 24) | ((rgba >> 8) & ViewCompat.MEASURED_SIZE_MASK);
    }

    public static H5BorderDrawable generateBackgroundDrawable(Context context, JSONObject jsonObject, int defaultColor) {
        if (jsonObject == null) {
            return null;
        }
        H5BorderDrawable borderDrawable = new H5BorderDrawable();
        borderDrawable.setBackgroundColor(a(jsonObject.getString("backgroundColor"), defaultColor));
        float borderWidth = H5Utils.parseFloat(jsonObject.getString("borderWidth"));
        if (borderWidth <= 0.0f) {
            return borderDrawable;
        }
        borderDrawable.setBorder((float) H5DimensionUtil.dip2px(context, borderWidth), a(jsonObject.getString(MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR)));
        borderDrawable.setBorderRadius((float) H5DimensionUtil.dip2px(context, H5Utils.parseFloat(jsonObject.getString("borderRadius"))));
        borderDrawable.setBorderStyle(jsonObject.getString("borderStyle"));
        return borderDrawable;
    }
}
