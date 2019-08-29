package defpackage;

import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: ed reason: default package */
/* compiled from: InterceptorManager */
public final class ed {
    private static final CopyOnWriteArrayList<ec> a = new CopyOnWriteArrayList<>();

    public static ec a(int i) {
        return a.get(i);
    }

    public static int a() {
        return a.size();
    }
}
