package defpackage;

import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: abl reason: default package */
/* compiled from: Observer */
public class abl {
    private static abl c;
    public CopyOnWriteArrayList<a> a = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<b> b = new CopyOnWriteArrayList<>();

    private abl() {
    }

    public static abl a() {
        if (c == null) {
            synchronized (abl.class) {
                try {
                    if (c == null) {
                        c = new abl();
                    }
                }
            }
        }
        return c;
    }
}
