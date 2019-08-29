package defpackage;

import java.util.ArrayList;

/* renamed from: ddn reason: default package */
/* compiled from: ShareListenerManager */
public class ddn {
    private static volatile ddn b;
    public ArrayList<Object> a = new ArrayList<>();

    private ddn() {
    }

    public static ddn a() {
        if (b == null) {
            synchronized (ddn.class) {
                try {
                    if (b == null) {
                        b = new ddn();
                    }
                }
            }
        }
        return b;
    }
}
