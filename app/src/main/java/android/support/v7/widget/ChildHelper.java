package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

class ChildHelper {
    final Callback a;
    final Bucket b = new Bucket();
    final List<View> c = new ArrayList();

    static class Bucket {
        long a = 0;
        Bucket b;

        Bucket() {
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i) {
            Bucket bucket = this;
            while (i >= 64) {
                bucket.a();
                bucket = bucket.b;
                i -= 64;
            }
            bucket.a |= 1 << i;
        }

        private void a() {
            if (this.b == null) {
                this.b = new Bucket();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b(int i) {
            Bucket bucket = this;
            while (i >= 64) {
                if (bucket.b != null) {
                    bucket = bucket.b;
                    i -= 64;
                } else {
                    return;
                }
            }
            bucket.a &= ~(1 << i);
        }

        /* access modifiers changed from: 0000 */
        public final boolean c(int i) {
            Bucket bucket = this;
            while (i >= 64) {
                bucket.a();
                bucket = bucket.b;
                i -= 64;
            }
            return (bucket.a & (1 << i)) != 0;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i, boolean z) {
            boolean z2 = z;
            Bucket bucket = this;
            while (true) {
                if (i >= 64) {
                    bucket.a();
                    bucket = bucket.b;
                    i -= 64;
                } else {
                    boolean z3 = (bucket.a & Long.MIN_VALUE) != 0;
                    long j = (1 << i) - 1;
                    bucket.a = (bucket.a & j) | (((~j) & bucket.a) << 1);
                    if (z2) {
                        bucket.a(i);
                    } else {
                        bucket.b(i);
                    }
                    if (z3 || bucket.b != null) {
                        bucket.a();
                        bucket = bucket.b;
                        z2 = z3;
                        i = 0;
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final boolean d(int i) {
            Bucket bucket = this;
            while (i >= 64) {
                bucket.a();
                bucket = bucket.b;
                i -= 64;
            }
            long j = 1 << i;
            boolean z = (bucket.a & j) != 0;
            bucket.a &= ~j;
            long j2 = j - 1;
            bucket.a = (bucket.a & j2) | Long.rotateRight((~j2) & bucket.a, 1);
            if (bucket.b != null) {
                if (bucket.b.c(0)) {
                    bucket.a(63);
                }
                bucket.b.d(0);
            }
            return z;
        }

        /* access modifiers changed from: 0000 */
        public final int e(int i) {
            if (this.b == null) {
                if (i >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else if (i < 64) {
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else {
                return this.b.e(i - 64) + Long.bitCount(this.a);
            }
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.b.toString());
            sb.append("xx");
            sb.append(Long.toBinaryString(this.a));
            return sb.toString();
        }
    }

    interface Callback {
        int a();

        int a(View view);

        void a(int i);

        void a(View view, int i);

        void a(View view, int i, LayoutParams layoutParams);

        ViewHolder b(View view);

        View b(int i);

        void b();

        void c(int i);

        void c(View view);

        void d(View view);
    }

    ChildHelper(Callback callback) {
        this.a = callback;
    }

    /* access modifiers changed from: 0000 */
    public final void a(View view) {
        this.c.add(view);
        this.a.c(view);
    }

    /* access modifiers changed from: 0000 */
    public final boolean b(View view) {
        if (!this.c.remove(view)) {
            return false;
        }
        this.a.d(view);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void a(View view, int i, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = e(i);
        }
        this.b.a(i2, z);
        if (z) {
            a(view);
        }
        this.a.a(view, i2);
    }

    private int e(int i) {
        if (i < 0) {
            return -1;
        }
        int a2 = this.a.a();
        int i2 = i;
        while (i2 < a2) {
            int e = i - (i2 - this.b.e(i2));
            if (e == 0) {
                while (this.b.c(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += e;
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i) {
        int e = e(i);
        View b2 = this.a.b(e);
        if (b2 != null) {
            if (this.b.d(e)) {
                b(b2);
            }
            this.a.a(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final View b(int i) {
        return this.a.b(e(i));
    }

    /* access modifiers changed from: 0000 */
    public final void a(View view, int i, LayoutParams layoutParams, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = e(i);
        }
        this.b.a(i2, z);
        if (z) {
            a(view);
        }
        this.a.a(view, i2, layoutParams);
    }

    /* access modifiers changed from: 0000 */
    public final int a() {
        return this.a.a() - this.c.size();
    }

    /* access modifiers changed from: 0000 */
    public final int b() {
        return this.a.a();
    }

    /* access modifiers changed from: 0000 */
    public final View c(int i) {
        return this.a.b(i);
    }

    /* access modifiers changed from: 0000 */
    public final void d(int i) {
        int e = e(i);
        this.b.d(e);
        this.a.c(e);
    }

    /* access modifiers changed from: 0000 */
    public final int c(View view) {
        int a2 = this.a.a(view);
        if (a2 != -1 && !this.b.c(a2)) {
            return a2 - this.b.e(a2);
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final boolean d(View view) {
        return this.c.contains(view);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.toString());
        sb.append(", hidden list:");
        sb.append(this.c.size());
        return sb.toString();
    }
}
