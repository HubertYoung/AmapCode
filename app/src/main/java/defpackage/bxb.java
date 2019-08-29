package defpackage;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* renamed from: bxb reason: default package */
/* compiled from: SearchPoiDetailPageStackController */
public final class bxb {
    private static List<WeakReference<bxj>> a = new LinkedList();

    public static void a(bxj bxj) {
        a.add(new WeakReference(bxj));
        if ((((((int) Runtime.getRuntime().maxMemory()) - ((int) Runtime.getRuntime().totalMemory())) + ((int) Runtime.getRuntime().freeMemory()) < 31457280) && a.size() > 1) || a.size() > 6) {
            WeakReference weakReference = a.get(0);
            if (!(weakReference == null || weakReference.get() == null)) {
                ((bxj) weakReference.get()).a();
            }
            a.remove(weakReference);
        }
    }

    public static void b(bxj bxj) {
        Iterator<WeakReference<bxj>> it = a.iterator();
        while (it.hasNext()) {
            if (bxj.equals(it.next().get())) {
                it.remove();
            }
        }
    }
}
