package defpackage;

/* renamed from: bwe reason: default package */
/* compiled from: State */
public class bwe implements Cloneable {

    /* renamed from: bwe$a */
    /* compiled from: State */
    public static abstract class a<T extends bwe> {
        protected T a;

        public a(T t) {
            if (t == null) {
                throw new IllegalArgumentException(String.format("parameter %s is null", new Object[]{"originalState"}));
            } else {
                this.a = t;
            }
        }

        protected static void a(String str) {
            throw new IllegalArgumentException(String.format("parameter %s is illegal", new Object[]{str}));
        }
    }

    /* access modifiers changed from: protected */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            try {
                return getClass().newInstance();
            } catch (InstantiationException e2) {
                e2.printStackTrace();
                return null;
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return null;
            }
        }
    }
}
