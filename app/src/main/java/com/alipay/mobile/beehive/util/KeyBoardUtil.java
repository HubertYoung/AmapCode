package com.alipay.mobile.beehive.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveConstant;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class KeyBoardUtil {
    private static final String BEEHIVE_KEYBOARD_HEIGHT = "BEEHIVE_KEYBOARD_HEIGHT";

    public static void hideKeyBoard(Context context, View view) {
        try {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, (Throwable) e);
        }
    }

    public static void showKeyBoard(Activity activity, View view) {
        try {
            activity.getWindow().setSoftInputMode(21);
            ((InputMethodManager) activity.getSystemService("input_method")).showSoftInput(view, 0);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, (Throwable) e);
        }
    }

    public static void showSoftInput(Context context, View view) {
        showSoftInput(context, view, 5);
    }

    public static void showSoftInput(Context context, View view, long delay) {
        showSoftInput(context, view, delay, 1);
    }

    public static void showSoftInput(final Context context, final View view, long delay, final int flags) {
        view.postDelayed(new Runnable() {
            public final void run() {
                view.requestFocus();
                ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, flags);
            }
        }, delay);
    }

    public static void toggleSoftInput(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static void setSoftInputAdjustResize(Activity activity, boolean isAdjustResize) {
        if (isAdjustResize) {
            activity.getWindow().setSoftInputMode(19);
        } else {
            activity.getWindow().setSoftInputMode(35);
        }
    }

    public static void setConfigKeyBoardHeight(int height) {
        SimpleSecurityCacheUtil.setString(BEEHIVE_KEYBOARD_HEIGHT, String.valueOf(height));
    }

    public static int getConfigKeyBoardHeight() {
        String valueStr = SimpleSecurityCacheUtil.getString(BEEHIVE_KEYBOARD_HEIGHT);
        if (TextUtils.isEmpty(valueStr)) {
            return 0;
        }
        return Integer.valueOf(valueStr).intValue();
    }

    public static int getDefaultKeyBoardHeight(Context context) {
        int height = getConfigKeyBoardHeight();
        int minH = (int) context.getResources().getDimension(R.dimen.emotion_default_height_min);
        int maxH = (int) context.getResources().getDimension(R.dimen.emotion_default_height_max);
        if (height < minH) {
            return minH;
        }
        if (height > maxH) {
            return maxH;
        }
        return height;
    }
}
