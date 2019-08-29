package defpackage;

/* renamed from: acl reason: default package */
/* compiled from: PlanHomeUIManager */
public class acl {
    private static acl b;
    public axd a;

    public static acl a() {
        if (b == null) {
            synchronized (acl.class) {
                try {
                    if (b == null) {
                        b = new acl();
                    }
                }
            }
        }
        return b;
    }

    public static void b() {
        b = null;
    }
}
