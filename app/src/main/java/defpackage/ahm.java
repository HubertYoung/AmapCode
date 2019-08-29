package defpackage;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.RejectedExecutionException;

/* renamed from: ahm reason: default package */
/* compiled from: ThreadExecutor */
public final class ahm {
    /* access modifiers changed from: private */
    public static final Handler a = new Handler(Looper.getMainLooper());

    /* renamed from: ahm$a */
    /* compiled from: ThreadExecutor */
    static class a implements Runnable {
        private Runnable a;

        public a(Runnable runnable) {
            this.a = runnable;
        }

        public final void run() {
            if (this.a != null) {
                try {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(this.a);
                } catch (RejectedExecutionException unused) {
                    ahm.a.post(this);
                    return;
                }
            }
            this.a = null;
        }
    }

    public static void a(Runnable runnable) {
        a(runnable, 0);
    }

    public static void a(Runnable runnable, long j) {
        if (runnable != null) {
            Message obtain = Message.obtain(a, new a(runnable));
            obtain.obj = runnable;
            obtain.what = runnable.hashCode();
            a.sendMessageDelayed(obtain, j);
        }
    }

    public static void b(Runnable runnable) {
        if (runnable != null) {
            a.removeMessages(runnable.hashCode(), runnable);
        }
    }

    public static void c(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runnable.run();
        } else {
            a(runnable, 0);
        }
    }
}
