package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.entity.ENV;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: m reason: default package */
/* compiled from: GlobalAppRuntimeInfo */
public final class m {
    private static Context a = null;
    private static ENV b = ENV.ONLINE;
    private static String c = "";
    private static String d = "";
    private static String e = null;
    private static String f = null;
    private static String g = null;
    private static volatile boolean h = true;
    private static SharedPreferences i;
    private static volatile CopyOnWriteArrayList<String> j;

    public static void a(Context context) {
        a = context;
        if (context != null) {
            if (TextUtils.isEmpty(d)) {
                d = db.a(context, Process.myPid());
            }
            if (TextUtils.isEmpty(c)) {
                c = db.b(context);
            }
            if (i == null) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                i = defaultSharedPreferences;
                f = defaultSharedPreferences.getString("UserId", null);
            }
            cl.d("awcn.GlobalAppRuntimeInfo", "", null, "CurrentProcess", d, "TargetProcess", c);
        }
    }

    public static Context a() {
        return a;
    }

    public static boolean b() {
        if (TextUtils.isEmpty(c) || TextUtils.isEmpty(d)) {
            return true;
        }
        return c.equalsIgnoreCase(d);
    }

    public static String c() {
        return d;
    }

    public static void a(ENV env) {
        b = env;
    }

    public static ENV d() {
        return b;
    }

    public static void a(String str) {
        e = str;
        try {
            if (!TextUtils.isEmpty(str)) {
                int indexOf = str.indexOf(AUScreenAdaptTool.PREFIX_ID);
                String str2 = null;
                String substring = indexOf != -1 ? str.substring(0, indexOf) : null;
                String substring2 = str.substring(indexOf + 1);
                int lastIndexOf = substring2.lastIndexOf("_");
                if (lastIndexOf != -1) {
                    String substring3 = substring2.substring(0, lastIndexOf);
                    str2 = substring2.substring(lastIndexOf + 1);
                    substring2 = substring3;
                }
                bz.a(substring2, str2, substring);
            }
        } catch (Exception unused) {
        }
    }

    public static String e() {
        return e;
    }

    public static void b(String str) {
        if (f == null || !f.equals(str)) {
            f = str;
            bu.a().d(cb.a());
            if (i != null) {
                i.edit().putString("UserId", str).apply();
            }
        }
    }

    public static String f() {
        return f;
    }

    public static void c(String str) {
        if (g == null || !g.equals(str)) {
            g = str;
        }
    }

    public static String g() {
        if (g == null && a != null) {
            g = db.a(a);
        }
        return g;
    }

    public static void a(boolean z) {
        h = z;
    }

    public static boolean h() {
        if (a == null) {
            return true;
        }
        return h;
    }

    public static CopyOnWriteArrayList<String> i() {
        return j;
    }
}
