package defpackage;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.System;

/* renamed from: faz reason: default package */
/* compiled from: SettingsCache */
final class faz implements fai {
    private ContentResolver a;

    faz() {
    }

    public final boolean a(Context context) {
        if (!fao.b()) {
            return false;
        }
        this.a = context.getContentResolver();
        return true;
    }

    public final String a(String str, String str2) {
        try {
            return System.getString(this.a, str);
        } catch (Exception e) {
            e.printStackTrace();
            fat.b((String) "SettingsCache", "getString error by ".concat(String.valueOf(str)));
            return str2;
        }
    }

    public final void b(String str, String str2) {
        try {
            System.putString(this.a, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            fat.b((String) "SettingsCache", "putString error by ".concat(String.valueOf(str)));
        }
    }
}
