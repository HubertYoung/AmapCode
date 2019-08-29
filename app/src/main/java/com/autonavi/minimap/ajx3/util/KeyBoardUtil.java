package com.autonavi.minimap.ajx3.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtil {
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
                if (inputMethodManager != null && inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void setInputStateShow(@NonNull Window window, int i) {
        synchronized (KeyBoardUtil.class) {
            int i2 = window.getAttributes().softInputMode;
            int i3 = i & 15;
            if ((i2 & 15) != i3) {
                window.setSoftInputMode(i3 | (i2 & 240));
            }
        }
    }

    public static synchronized void setInputAdjust(@NonNull Window window, int i) {
        synchronized (KeyBoardUtil.class) {
            int i2 = window.getAttributes().softInputMode;
            int i3 = i & 240;
            if ((i2 & 240) != i3) {
                window.setSoftInputMode(i3 | (i2 & 15));
            }
        }
    }

    public static void setInputStateShow(@NonNull Activity activity, boolean z) {
        setInputStateShow(activity.getWindow(), z ? 4 : 2);
    }
}
