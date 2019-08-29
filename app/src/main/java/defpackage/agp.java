package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;
import com.amap.bundle.utils.device.DisplayType;

/* renamed from: agp reason: default package */
/* compiled from: DisplayTypeHelper */
public class agp {
    private static final String a;
    private static volatile DisplayType b;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.BRAND);
        sb.append(":");
        sb.append(Build.MODEL);
        sb.append("_2");
        a = sb.toString();
    }

    public static DisplayType a(Context context) {
        if (b == null) {
            synchronized (agp.class) {
                try {
                    if (b == null) {
                        b = b(context);
                    }
                }
            }
        }
        return b;
    }

    private static SharedPreferences c(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0);
    }

    private static DisplayType b(Context context) {
        SharedPreferences c = c(context);
        String str = a;
        if (!TextUtils.equals(c.getString("sharped_screen_fp", null), str)) {
            Editor edit = c.edit();
            edit.remove("sharped_screen_fp");
            edit.remove("sharped_screen_dpt");
            edit.putString("sharped_screen_fp", str);
            edit.apply();
            b = null;
        }
        SharedPreferences c2 = c(context);
        if (c2.contains("sharped_screen_dpt")) {
            DisplayType valueOf = DisplayType.valueOf(c2.getInt("sharped_screen_dpt", 0));
            if (valueOf == null) {
                valueOf = DisplayType.NORMAL;
            }
            b = valueOf;
        } else {
            try {
                b = ago.a(context);
                c2.edit().putInt("sharped_screen_dpt", b.value()).apply();
            } catch (IllegalStateException unused) {
                b = DisplayType.NORMAL;
            }
        }
        return b;
    }
}
