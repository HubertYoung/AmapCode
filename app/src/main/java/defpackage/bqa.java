package defpackage;

import android.text.TextUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: bqa reason: default package */
/* compiled from: ThreadPool */
public final class bqa {
    public b a;
    private int b = 0;

    /* renamed from: bqa$a */
    /* compiled from: ThreadPool */
    public static class a implements ThreadFactory {
        private String a;
        private AtomicInteger b = new AtomicInteger();

        public a(String str) {
            this.a = str;
        }

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("-pool-thread-");
            sb.append(this.b.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    }

    /* renamed from: bqa$b */
    /* compiled from: ThreadPool */
    public interface b {
        void a();

        void a(Runnable runnable);
    }

    /* renamed from: bqa$c */
    /* compiled from: ThreadPool */
    public static class c implements Runnable {
        public String a;
        public int b;
        public int c;
        private Runnable d;

        public c(Runnable runnable, int i, String str, int i2) {
            this.a = str;
            this.d = runnable;
            this.b = i;
            this.c = i2;
        }

        public final void run() {
            Thread currentThread = Thread.currentThread();
            String name = currentThread.getName();
            if (!TextUtils.isEmpty(this.a)) {
                currentThread.setName(this.a);
            }
            this.d.run();
            currentThread.setName(name);
        }
    }

    public bqa(String str, int i) {
        this.a = new bpx(str, i);
    }

    public bqa(String str, int[] iArr) {
        this.a = new bpy(str, iArr);
    }

    public final synchronized void a(Runnable runnable, int i, String str) {
        this.b++;
        b bVar = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.b);
        bVar.a(new c(runnable, i, sb.toString(), this.b));
    }
}
