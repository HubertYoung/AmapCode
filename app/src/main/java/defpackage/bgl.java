package defpackage;

import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: bgl reason: default package */
/* compiled from: VUIModelFactory */
public final class bgl {
    private static ArrayList<bgd> a = new ArrayList<>();

    public static bgd a(String str) {
        Iterator<bgd> it = a.iterator();
        while (it.hasNext()) {
            bgd next = it.next();
            if (next.a().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public static void a(bgd bgd) {
        boolean z;
        if (!a.contains(bgd)) {
            Iterator<bgd> it = a.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().a().equals(bgd.a())) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                a.add(bgd);
            }
        }
    }
}
