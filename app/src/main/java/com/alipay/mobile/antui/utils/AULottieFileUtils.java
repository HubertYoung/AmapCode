package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import com.alipay.mobile.antui.lottie.LottieCache;
import org.json.JSONException;
import org.json.JSONObject;

public class AULottieFileUtils {
    private static final String LOADING_FILE_NAME = "lottie/antui_loading.json";
    private static final String REFRESH_FILE_NAME = "lottie/antui_refresh.json";

    public static JSONObject getRefreshAnimation(Context context) {
        return getRefreshAnimation(context, Color.parseColor("#9AC7F9"), Color.parseColor("#D5D5D5"));
    }

    public static JSONObject getRefreshAnimation(Context context, @ColorInt int antColor, @ColorInt int holeColor) {
        return getRefreshAnimation(context, 100, antColor, 100, holeColor);
    }

    public static JSONObject getRefreshAnimation(Context context, int alpha, @ColorInt int antColor, int holeAlpha, @ColorInt int holeColor) {
        try {
            return new JSONObject(LottieCache.getFileJson(context, REFRESH_FILE_NAME).replace("\"###1#\"", String.valueOf(((float) Color.red(antColor)) / 255.0f)).replace("\"###2#\"", String.valueOf(((float) Color.green(antColor)) / 255.0f)).replace("\"###3#\"", String.valueOf(((float) Color.blue(antColor)) / 255.0f)).replace("\"###Alpha#\"", String.valueOf(alpha)).replace("\"###hole_1#\"", String.valueOf(((float) Color.red(holeColor)) / 255.0f)).replace("\"###hole_2#\"", String.valueOf(((float) Color.green(holeColor)) / 255.0f)).replace("\"###hole_3#\"", String.valueOf(((float) Color.blue(holeColor)) / 255.0f)).replace("\"###hole_Alpha#\"", String.valueOf(holeAlpha)));
        } catch (JSONException e) {
            AuiLogger.mtBizReport("AULottieFileUtils", e.toString());
            return null;
        }
    }

    public static JSONObject getLoadingAnimation(Context context) {
        return getLoadingAnimation(context, Color.parseColor("#108EE9"));
    }

    public static JSONObject getLoadingAnimation(Context context, @ColorInt int color) {
        return getLoadingAnimation(context, Color.red(color), Color.green(color), Color.blue(color));
    }

    private static JSONObject getLoadingAnimation(Context context, int red, int green, int blue) {
        try {
            return new JSONObject(LottieCache.getFileJson(context, LOADING_FILE_NAME).replace("\"###1#\"", String.valueOf(Float.valueOf(((float) red) / 255.0f))).replace("\"###2#\"", String.valueOf(Float.valueOf(((float) green) / 255.0f))).replace("\"###3#\"", String.valueOf(Float.valueOf(((float) blue) / 255.0f))));
        } catch (JSONException e) {
            AuiLogger.mtBizReport("AULottieFileUtils", e.toString());
            return null;
        }
    }
}
