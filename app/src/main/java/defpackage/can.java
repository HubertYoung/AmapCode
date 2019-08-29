package defpackage;

/* renamed from: can reason: default package */
/* compiled from: PhotoJSData */
public class can {
    private static volatile can h;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public wa g;

    public static can a() {
        if (h == null) {
            synchronized (can.class) {
                try {
                    if (h == null) {
                        h = new can();
                    }
                }
            }
        }
        return h;
    }
}
