package defpackage;

import android.os.SystemClock;
import com.autonavi.inter.IRouterLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: esf reason: default package */
/* compiled from: RouterManager */
public final class esf {
    private static final String b = "esf";
    public esj a;
    private Map<String, List<esk>> c;

    /* renamed from: esf$a */
    /* compiled from: RouterManager */
    static class a {
        static esf a = new esf(0);
    }

    /* synthetic */ esf(byte b2) {
        this();
    }

    private esf() {
        this.c = new HashMap();
    }

    public static esf a() {
        return a.a;
    }

    public final List<esk> a(String str) {
        List<esk> list = this.c.get(str);
        if (list == null) {
            IRouterLoader iRouterLoader = (IRouterLoader) bqn.a(IRouterLoader.class);
            if (iRouterLoader == null) {
                return null;
            }
            List<Class> findRouterClass = iRouterLoader.findRouterClass(str);
            if (findRouterClass == null) {
                return null;
            }
            List<esk> arrayList = new ArrayList<>();
            for (Class next : findRouterClass) {
                if (esk.class.isAssignableFrom(next)) {
                    try {
                        esk esk = (esk) next.newInstance();
                        esk.attachWingContext(this.a);
                        arrayList.add(esk);
                    } catch (IllegalAccessException | InstantiationException unused) {
                    }
                }
            }
            if (arrayList.size() <= 0) {
                return null;
            }
            this.c.put(str, arrayList);
            list = arrayList;
        }
        return list;
    }

    public final boolean a(ese ese) {
        boolean z = false;
        if (!ese.c) {
            return false;
        }
        List<esk> a2 = a(ese.a());
        if (a2 == null) {
            return false;
        }
        for (esk start : a2) {
            SystemClock.currentThreadTimeMillis();
            z |= start.start(ese);
        }
        return z;
    }
}
