package com.xiaomi.mipush.sdk;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

public final class ap {
    public static void a(Editor editor) {
        if (VERSION.SDK_INT > 8) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
