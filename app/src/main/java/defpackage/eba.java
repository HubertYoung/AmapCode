package defpackage;

/* renamed from: eba reason: default package */
/* compiled from: RouteActivitiesManager */
public class eba {
    private static volatile eba b;
    public boolean a = true;
    private boolean c = true;

    public static eba a() {
        if (b == null) {
            synchronized (eba.class) {
                try {
                    if (b == null) {
                        b = new eba();
                    }
                }
            }
        }
        return b;
    }
}
