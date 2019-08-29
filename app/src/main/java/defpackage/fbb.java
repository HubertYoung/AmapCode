package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* renamed from: fbb reason: default package */
/* compiled from: SpCache */
public final class fbb implements fai {
    public static String a = "SpCache";
    private static String c = "com.vivo.push.cache";
    public SharedPreferences b;

    public final boolean a(Context context) {
        if (this.b == null) {
            this.b = context.getSharedPreferences(c, 0);
        }
        return true;
    }

    public final String a(String str, String str2) {
        String string = this.b.getString(str, str2);
        String str3 = a;
        StringBuilder sb = new StringBuilder("getString ");
        sb.append(str);
        sb.append(" is ");
        sb.append(string);
        fat.d(str3, sb.toString());
        return string;
    }

    public final void b(String str, String str2) {
        Editor edit = this.b.edit();
        if (edit != null) {
            edit.putString(str, str2).apply();
            fat.d(a, "putString by ".concat(String.valueOf(str)));
            return;
        }
        fat.b(a, "putString error by ".concat(String.valueOf(str)));
    }
}
