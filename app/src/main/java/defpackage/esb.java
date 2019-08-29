package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IBundleInterfaceLoader;
import java.util.HashMap;

/* renamed from: esb reason: default package */
/* compiled from: BundleServiceManager */
public final class esb {
    public esj a;
    private HashMap<Class<? extends esc>, Object> b;

    /* renamed from: esb$a */
    /* compiled from: BundleServiceManager */
    public static class a {
        /* access modifiers changed from: private */
        public static esb a = new esb(0);
    }

    /* synthetic */ esb(byte b2) {
        this();
    }

    private esb() {
        this.b = new HashMap<>();
    }

    public final <T extends esc> T a(Class<T> cls) {
        T t;
        T t2 = this.b.get(cls);
        if (t2 != null) {
            return (esc) t2;
        }
        try {
            Class<? extends T> bundle = ((IBundleInterfaceLoader) bqn.a(IBundleInterfaceLoader.class)).getBundle(cls);
            if (bundle != null) {
                if (bie.class.isAssignableFrom(cls)) {
                    synchronized (cls) {
                        t = (esc) this.b.get(cls);
                        if (t == null) {
                            t = (esc) bundle.newInstance();
                            this.b.put(cls, t);
                        }
                    }
                } else {
                    t = (esc) bundle.newInstance();
                }
                if (esi.class.isAssignableFrom(cls)) {
                    ((esi) t).a(this.a);
                }
                return t;
            }
        } catch (InstantiationException e) {
            AMapLog.e("BundleServiceManager", e.getMessage());
        } catch (IllegalAccessException e2) {
            AMapLog.e("BundleServiceManager", e2.getMessage());
        }
        return null;
    }
}
