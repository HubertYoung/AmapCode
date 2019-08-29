package defpackage;

/* renamed from: dzi reason: default package */
/* compiled from: CoachDBSaveManager */
public class dzi {
    private static volatile dzi d;
    public boolean a;
    public boolean b;
    public boolean c;

    public static dzi a() {
        if (d == null) {
            synchronized (dzi.class) {
                try {
                    if (d == null) {
                        d = new dzi();
                    }
                }
            }
        }
        return d;
    }

    public static void b() {
        d = null;
    }
}
