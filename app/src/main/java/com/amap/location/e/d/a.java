package com.amap.location.e.d;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/* compiled from: AlarmWrapper */
public class a {
    private C0018a a;

    /* renamed from: com.amap.location.e.d.a$a reason: collision with other inner class name */
    /* compiled from: AlarmWrapper */
    static abstract class C0018a {
        protected Runnable a;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void a(long j);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        protected C0018a(Runnable runnable) {
            this.a = runnable;
        }
    }

    /* compiled from: AlarmWrapper */
    static class b extends C0018a {
        private Handler b;

        protected b(Looper looper, Runnable runnable) {
            super(runnable);
            this.b = new Handler(looper);
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            this.b.removeCallbacks(this.a);
            this.b.postDelayed(this.a, j);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.removeCallbacks(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.b.removeCallbacks(this.a);
        }
    }

    public a(@NonNull Looper looper, @NonNull Runnable runnable) {
        this.a = new b(looper, runnable);
    }

    public void a(long j) {
        this.a.a(j);
    }

    public void a() {
        this.a.a();
    }

    public void b() {
        this.a.b();
    }
}
