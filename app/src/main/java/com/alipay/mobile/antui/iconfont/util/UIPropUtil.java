package com.alipay.mobile.antui.iconfont.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.lang.reflect.Array;

public class UIPropUtil {
    private static final String DP_SUFFIX = "dp";
    public static final float INVALID_DIMEN = -1.0f;
    private static final String PX_SUFFIX = "px";
    static final String SPLITER = " ";
    private static final String SP_SUFFIX = "sp";
    private static DisplayMetrics dm = null;

    public static int getColorByValue(String colorString) {
        String realColor;
        if (colorString.contains("rgb")) {
            int[] colors = new int[3];
            String[] allColor = colorString.substring(colorString.indexOf("(") + 1, colorString.indexOf(")")).split(",");
            int i = 0;
            while (i < allColor.length) {
                try {
                    colors[i] = Integer.parseInt(allColor[i].trim());
                    i++;
                } catch (NumberFormatException e) {
                    AuiLogger.error("UIPropUtil", "RGB格式错误: " + colorString + ", " + e.toString());
                }
            }
            return Color.rgb(colors[0], colors[1], colors[2]);
        }
        if (!colorString.startsWith(MetaRecord.LOG_SEPARATOR)) {
            realColor = new StringBuilder(MetaRecord.LOG_SEPARATOR).append(colorString).toString();
        } else {
            realColor = colorString;
        }
        return Color.parseColor(realColor);
    }

    public static ColorStateList genTextSelector(Context context, String... strs) {
        boolean isEmpty = true;
        if (strs != null && strs.length > 0) {
            int[][] states = (int[][]) Array.newInstance(Integer.TYPE, new int[]{5, 5});
            int[] colors = new int[4];
            for (int i = 0; i < strs.length; i++) {
                String s = strs[i];
                if (!TextUtils.isEmpty(s)) {
                    isEmpty = false;
                    switch (i) {
                        case 0:
                            states[0] = new int[]{16842910, -16842919, -16842908, -16842913};
                            colors[0] = getColorByValue(s);
                            break;
                        case 1:
                            states[1] = new int[]{16842910, 16842919};
                            colors[1] = getColorByValue(s);
                            states[2] = new int[]{16842910, 16842908};
                            colors[2] = getColorByValue(s);
                            states[3] = new int[]{16842910, 16842913};
                            colors[3] = getColorByValue(s);
                            break;
                        case 2:
                            states[4] = new int[]{-16842910};
                            colors[4] = getColorByValue(s);
                            break;
                    }
                }
            }
            if (!isEmpty) {
                return new ColorStateList(states, colors);
            }
        }
        return null;
    }

    public static float convertDipToPx(Context context, float dip) {
        return dip * getDensity(context, false);
    }

    public static float convertSpToPx(Context context, float sp) {
        return sp * getDensity(context, true);
    }

    public static float getPx(String dimenStr, Context context) {
        if (TextUtils.isEmpty(dimenStr) || context == null) {
            return -1.0f;
        }
        try {
            if (dimenStr.endsWith("dp")) {
                return convertDipToPx(context, Float.parseFloat(dimenStr.substring(0, dimenStr.lastIndexOf("dp"))));
            }
            if (dimenStr.endsWith("sp")) {
                return convertSpToPx(context, Float.parseFloat(dimenStr.substring(0, dimenStr.lastIndexOf("sp"))));
            }
            if (dimenStr.endsWith("px")) {
                return Float.parseFloat(dimenStr.substring(0, dimenStr.lastIndexOf("px")));
            }
            return convertDipToPx(context, Float.parseFloat(dimenStr));
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    public static synchronized float getDensity(Context context, boolean scaled) {
        float f;
        synchronized (UIPropUtil.class) {
            if (dm == null) {
                dm = new DisplayMetrics();
                ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(dm);
            }
            if (scaled) {
                f = dm.scaledDensity;
            } else {
                f = dm.density;
            }
        }
        return f;
    }
}
