package com.alibaba.analytics.utils;

import android.annotation.TargetApi;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

public class SPHelper {
    @TargetApi(9)
    public static void apply(Editor editor) {
        editor.apply();
    }

    public static void fastCommit(Editor editor) {
        Logger.i((String) "4.3.8 cacheLog [fastCommit]", "");
        if (editor != null) {
            if (VERSION.SDK_INT >= 9) {
                apply(editor);
                return;
            }
            editor.commit();
        }
    }
}
