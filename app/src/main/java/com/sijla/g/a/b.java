package com.sijla.g.a;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.sijla.c.c;
import java.io.File;

public class b {
    private static String a = "";

    public static String a() {
        String str;
        try {
            str = Environment.getExternalStorageDirectory().getAbsolutePath();
        } catch (Throwable unused) {
            str = "/sdcard";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(File.separator);
        return sb.toString();
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        return b(context);
    }

    public static String b(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append(a(true));
        sb.append(File.separator);
        return sb.toString();
    }

    public static String c(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(a(context));
        sb.append("Qt");
        sb.append(File.separator);
        return sb.toString();
    }

    public static String d(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(a(context));
        sb.append("QD");
        sb.append(File.separator);
        return sb.toString();
    }

    public static String e(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(b(context));
        sb.append("config");
        sb.append(File.separator);
        return sb.toString();
    }

    public static String f(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(b(context));
        sb.append("qt");
        sb.append(File.separator);
        sb.append("data");
        sb.append(File.separator);
        return sb.toString();
    }

    public static StringBuilder a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append(str);
        sb.append(File.separator);
        return sb;
    }

    private static String a(boolean z) {
        String optString = z ? ".qmt" : c.a.optString("rootdir", ".qmt");
        return com.sijla.g.b.a(optString) ? ".qmt" : optString;
    }
}
