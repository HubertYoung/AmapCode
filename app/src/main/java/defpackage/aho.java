package defpackage;

import android.os.Handler;
import android.os.Looper;

/* renamed from: aho reason: default package */
/* compiled from: UiExecutor */
public final class aho {
    private static final Handler a = new Handler(Looper.getMainLooper());

    public static void a(Runnable runnable) {
        if (runnable != null) {
            a.post(runnable);
        }
    }

    public static void a(Runnable runnable, long j) {
        if (runnable != null) {
            a.postDelayed(runnable, j);
        }
    }

    public static void b(Runnable runnable) {
        if (runnable != null) {
            a.removeCallbacks(runnable);
        }
    }
}
