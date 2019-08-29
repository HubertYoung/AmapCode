package defpackage;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: bbx reason: default package */
/* compiled from: SearchSinglePageController */
public final class bbx {
    private static Map<Class, WeakReference<bid>> a = new HashMap();

    public static void a(bid bid) {
        WeakReference weakReference = a.get(bid.getClass());
        if (weakReference != null) {
            bid bid2 = (bid) weakReference.get();
            if (bid2 != null) {
                bid2.finish();
            }
        }
        a.put(bid.getClass(), new WeakReference(bid));
    }

    public static void a(Class<? extends bid> cls) {
        WeakReference weakReference = a.get(cls);
        if (weakReference != null) {
            bid bid = (bid) weakReference.get();
            if (bid != null) {
                bid.finish();
            }
        }
    }

    public static void b(bid bid) {
        if (bid != null) {
            Iterator<WeakReference<bid>> it = a.values().iterator();
            while (it.hasNext()) {
                if (bid.equals(it.next().get())) {
                    it.remove();
                }
            }
        }
    }
}
