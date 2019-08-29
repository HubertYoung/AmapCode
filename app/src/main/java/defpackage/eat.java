package defpackage;

/* renamed from: eat reason: default package */
/* compiled from: DefaultRouteLineConfig */
final class eat extends eap {
    private static eat a;

    private eat() {
    }

    public static synchronized eat g() {
        eat eat;
        synchronized (eat.class) {
            try {
                if (a == null) {
                    a = new eat();
                }
                eat = a;
            }
        }
        return eat;
    }
}
