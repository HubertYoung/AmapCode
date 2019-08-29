package defpackage;

import android.content.Context;
import java.lang.reflect.Method;

/* renamed from: ezl reason: default package */
/* compiled from: ConfigManagerFactory */
public final class ezl {
    private static final Object a = new Object();
    private static volatile ezl b;
    private ezn c;

    private ezl() {
    }

    public static ezl a() {
        if (b == null) {
            synchronized (a) {
                try {
                    if (b == null) {
                        b = new ezl();
                    }
                }
            }
        }
        return b;
    }

    public final ezn a(Context context) {
        if (this.c != null) {
            return this.c;
        }
        try {
            String str = fbd.a(context) ? "com.vivo.push.cache.ServerConfigManagerImpl" : "com.vivo.push.cache.ClientConfigManagerImpl";
            Method method = Class.forName(str).getMethod("getInstance", new Class[]{Context.class});
            fat.d("ConfigManagerFactory", "createConfig success is ".concat(String.valueOf(str)));
            this.c = (ezn) method.invoke(null, new Object[]{context});
            return this.c;
        } catch (Exception e) {
            e.printStackTrace();
            fat.b("ConfigManagerFactory", "createConfig error", e);
            return null;
        }
    }
}
