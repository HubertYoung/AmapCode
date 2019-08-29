package com.sijla.g;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.sijla.g.a.a;
import com.sijla.g.a.d;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class i {
    public static String a(Context context) {
        return d(context);
    }

    public static String b(Context context) {
        String f = f(context);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("arch", 0);
            Editor edit = sharedPreferences.edit();
            if (b.a(f) || 32 != f.length()) {
                String string = sharedPreferences.getString("hbuid", "");
                if (b.a(string)) {
                    string = c(context);
                    edit.putString("hbuid", string).apply();
                }
                if (!a.d(context, (String) "android.permission.WRITE_EXTERNAL_STORAGE") || !a.a()) {
                    return string;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(string);
                arrayList.add(context.getPackageName());
                arrayList.add(String.valueOf(d.d()));
                b.a(context, (List<String>) arrayList);
                return string;
            }
            edit.putString("hbuid", f).apply();
            return f;
        } catch (Exception unused) {
            return c(context);
        }
    }

    private static String f(Context context) {
        String str = "";
        if (!a.d(context, (String) "android.permission.READ_EXTERNAL_STORAGE") || !a.a()) {
            return str;
        }
        for (String file : b.a()) {
            try {
                String str2 = b.b(b.a(new File(file))).split("\t")[0];
                try {
                    if (!TextUtils.isEmpty(str2)) {
                        return str2;
                    }
                } catch (Exception unused) {
                }
                str = str2;
            } catch (Exception unused2) {
            }
        }
        return str;
    }

    public static String c(Context context) {
        try {
            String q = a.q(context);
            StringBuilder sb = new StringBuilder("35");
            sb.append(Build.BOARD.length() % 10);
            sb.append(Build.BRAND.length() % 10);
            sb.append(Build.CPU_ABI.length() % 10);
            sb.append(Build.DEVICE.length() % 10);
            sb.append(Build.DISPLAY.length() % 10);
            sb.append(Build.HOST.length() % 10);
            sb.append(Build.ID.length() % 10);
            sb.append(Build.MANUFACTURER.length() % 10);
            sb.append(Build.MODEL.length() % 10);
            sb.append(Build.PRODUCT.length() % 10);
            sb.append(Build.TAGS.length() % 10);
            sb.append(Build.TYPE.length() % 10);
            sb.append(Build.USER.length() % 10);
            String sb2 = sb.toString();
            String k = a.k(context);
            String s = a.s(context);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(q);
            sb3.append(sb2);
            sb3.append(k);
            sb3.append(s);
            return d.a(sb3.toString()).toUpperCase();
        } catch (Exception unused) {
            return d.a(a.k(context)).toUpperCase();
        }
    }

    public static String d(Context context) {
        try {
            String q = a.q(context);
            StringBuilder sb = new StringBuilder();
            sb.append(a.j());
            sb.append(a.g());
            sb.append(Build.BRAND);
            sb.append(Build.CPU_ABI);
            sb.append(Build.DEVICE);
            sb.append(Build.MANUFACTURER);
            sb.append(Build.MODEL);
            sb.append(Build.PRODUCT);
            String sb2 = sb.toString();
            String string = Secure.getString(context.getContentResolver(), "android_id");
            String s = a.s(context);
            String x = a.x(context);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(q);
            sb3.append(sb2);
            sb3.append(string);
            sb3.append(s);
            sb3.append(x);
            return d.a(sb3.toString()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return d.a((String) "").toUpperCase();
        }
    }

    public static String e(Context context) {
        try {
            String q = a.q(context);
            StringBuilder sb = new StringBuilder();
            sb.append(a.j());
            sb.append(a.g());
            sb.append(Build.BRAND);
            sb.append(Build.CPU_ABI);
            sb.append(Build.DEVICE);
            sb.append(Build.MANUFACTURER);
            sb.append(Build.MODEL);
            sb.append(Build.PRODUCT);
            String sb2 = sb.toString();
            String string = Secure.getString(context.getContentResolver(), "android_id");
            String x = a.x(context);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(q);
            sb3.append(sb2);
            sb3.append(string);
            sb3.append(x);
            return d.a(sb3.toString()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return d.a((String) "").toUpperCase();
        }
    }
}
