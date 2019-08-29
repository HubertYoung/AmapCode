package defpackage;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: cm reason: default package */
/* compiled from: AppLifecycle */
public final class cm {
    public static volatile long a;
    /* access modifiers changed from: private */
    public static CopyOnWriteArraySet<a> b = new CopyOnWriteArraySet<>();
    private static ActivityLifecycleCallbacks c = new ActivityLifecycleCallbacks() {
        public final void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public final void onActivityDestroyed(Activity activity) {
        }

        public final void onActivityPaused(Activity activity) {
        }

        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public final void onActivityStarted(Activity activity) {
        }

        public final void onActivityStopped(Activity activity) {
        }

        public final void onActivityResumed(Activity activity) {
            cm.b();
        }
    };
    private static ComponentCallbacks2 d = new ComponentCallbacks2() {
        public final void onConfigurationChanged(Configuration configuration) {
        }

        public final void onLowMemory() {
        }

        public final void onTrimMemory(int i) {
            cl.b("awcn.ComponentCallbacks2", "onTrimMemory", null, H5PermissionManager.level, Integer.valueOf(i));
            if (i == 20) {
                cm.c();
            }
        }
    };

    /* renamed from: cm$a */
    /* compiled from: AppLifecycle */
    public interface a {
        void a();

        void b();
    }

    public static void a() {
        if (VERSION.SDK_INT >= 14 && j.l()) {
            ((Application) m.a().getApplicationContext()).registerActivityLifecycleCallbacks(c);
            m.a().registerComponentCallbacks(d);
        }
    }

    public static void a(a aVar) {
        b.add(aVar);
    }

    public static void b(a aVar) {
        b.remove(aVar);
    }

    public static void b() {
        if (m.h()) {
            m.a(false);
            a(true);
        }
    }

    public static void c() {
        if (!m.h()) {
            m.a(true);
            a = System.currentTimeMillis();
            a(false);
        }
    }

    private static void a(final boolean z) {
        cl.b("awcn.AppLifeCycle", "notifyListener", null, "foreground", Boolean.valueOf(z));
        ck.a(new Runnable() {
            public final void run() {
                Iterator it = cm.b.iterator();
                while (it.hasNext()) {
                    a aVar = (a) it.next();
                    if (z) {
                        aVar.a();
                    } else {
                        aVar.b();
                    }
                }
            }
        });
    }
}
