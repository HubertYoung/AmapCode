package com.amap.location.sdk.e;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;

/* compiled from: FileUtils */
public class c {
    private static String a;

    public static boolean a() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (RuntimeException unused) {
            return false;
        }
    }

    public static String a(Context context) {
        if (a != null) {
            return a;
        }
        if (a()) {
            File file = new File(context.getExternalFilesDir(null), "amaplocation");
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            if (file.exists() || file.mkdirs()) {
                a = file.getAbsolutePath();
            }
        }
        return a;
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(a)) {
            a = str;
        }
    }
}
