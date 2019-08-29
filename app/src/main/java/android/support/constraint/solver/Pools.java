package android.support.constraint.solver;

final class Pools {

    interface Pool<T> {
        T a();

        void a(T[] tArr, int i);

        boolean a(T t);
    }

    static class SimplePool<T> implements Pool<T> {
        private final Object[] a = new Object[256];
        private int b;

        SimplePool() {
        }

        public final T a() {
            if (this.b <= 0) {
                return null;
            }
            int i = this.b - 1;
            T t = this.a[i];
            this.a[i] = null;
            this.b--;
            return t;
        }

        public final boolean a(T t) {
            if (this.b >= this.a.length) {
                return false;
            }
            this.a[this.b] = t;
            this.b++;
            return true;
        }

        public final void a(T[] tArr, int i) {
            if (i > tArr.length) {
                i = tArr.length;
            }
            for (int i2 = 0; i2 < i; i2++) {
                T t = tArr[i2];
                if (this.b < this.a.length) {
                    this.a[this.b] = t;
                    this.b++;
                }
            }
        }
    }

    private Pools() {
    }
}
