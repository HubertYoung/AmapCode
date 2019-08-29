package defpackage;

/* renamed from: bu reason: default package */
/* compiled from: StrategyCenter */
public class bu {
    private static volatile bq a;

    public static bq a() {
        if (a == null) {
            synchronized (bu.class) {
                try {
                    if (a == null) {
                        a = new bv();
                    }
                }
            }
        }
        return a;
    }

    private bu() {
    }
}
