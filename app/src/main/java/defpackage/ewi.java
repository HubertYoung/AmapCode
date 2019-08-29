package defpackage;

import android.content.Context;
import com.hat.autotrack.entrance.HATAgent;

/* renamed from: ewi reason: default package */
/* compiled from: HATUtils */
public class ewi {
    private static final String a = "ewi";
    private static Boolean b;

    private static boolean a() {
        try {
            Class.forName("com.hat.autotrack.entrance.HATAgent");
            return true;
        } catch (ClassNotFoundException unused) {
            euw.c();
            return false;
        }
    }

    public static void a(Context context) {
        if (context == null) {
            euw.a((String) "Cant initialize HAT Because of illegal parameter!!!");
            return;
        }
        if (b == null) {
            b = Boolean.valueOf(a());
        }
        if (b.booleanValue()) {
            if (a.c) {
                HATAgent.enableLog();
            }
            if (a.b) {
                HATAgent.enableHeats();
            }
            if (a.a) {
                HATAgent.enableAutoTrack();
            }
            HATAgent.setAutoTrackUrl(evd.k);
            HATAgent.setChannel(context, euw.e(context));
            HATAgent.getInstance(context, euw.d(context));
            return;
        }
        euw.a((String) "There is no HAT sdk !!!");
    }
}
