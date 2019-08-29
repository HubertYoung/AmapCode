package defpackage;

/* renamed from: dbw reason: default package */
/* compiled from: AlipayScanInitHelper */
public final class dbw {
    private static dbw b;
    public boolean a = false;

    private dbw() {
    }

    public static synchronized dbw a() {
        dbw dbw;
        synchronized (dbw.class) {
            try {
                if (b == null) {
                    b = new dbw();
                }
                dbw = b;
            }
        }
        return dbw;
    }
}
