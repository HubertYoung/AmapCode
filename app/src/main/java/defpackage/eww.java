package defpackage;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;

/* renamed from: eww reason: default package */
/* compiled from: PushClient */
public final class eww {
    private static final Object a = new Object();
    private static volatile eww b;

    private eww(Context context) {
        ezv.a().a(context);
    }

    public static eww a(Context context) {
        if (b == null) {
            synchronized (a) {
                if (b == null) {
                    b = new eww(context.getApplicationContext());
                }
            }
        }
        return b;
    }

    public static void a() {
        ezv.a().a((fbh) new exg());
    }

    public static void a(ewu ewu) {
        ezv a2 = ezv.a();
        if (a2.b == null) {
            ewu.a(102);
            return;
        }
        a2.e = a2.c();
        if (!TextUtils.isEmpty(a2.e)) {
            ewu.a(0);
        } else if (!ezv.a(a2.a)) {
            ewu.a(1002);
        } else {
            a2.a = SystemClock.elapsedRealtime();
            String packageName = a2.b.getPackageName();
            a aVar = null;
            if (a2.b == null) {
                ewu.a(102);
            } else {
                exc exc = new exc(packageName);
                exc.e = null;
                exc.b = null;
                exc.a = null;
                exc.d = 100;
                if (!a2.g) {
                    a2.a((fbh) exc);
                    ewu.a(0);
                } else if (!a2.d()) {
                    ewu.a(101);
                } else {
                    aVar = new a(exc, ewu);
                    String a3 = a2.a(aVar);
                    exc.c = a3;
                    aVar.b = new ezx(a2, exc, a3);
                }
            }
            if (aVar != null) {
                aVar.a = new ezw(a2, aVar);
                if (aVar.b == null) {
                    fat.a((String) "PushClientManager", (String) "task is null");
                    return;
                }
                aVar.b.run();
            }
        }
    }

    public static boolean b() {
        return ezv.a().b();
    }
}
