package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* renamed from: cvd reason: default package */
/* compiled from: Loopers */
public final class cvd {

    /* renamed from: cvd$a */
    /* compiled from: Loopers */
    public static class a {
        public static Looper a;
        public static Handler b = new Handler(a);

        static {
            HandlerThread handlerThread = new HandlerThread("Telescope_Report_Thread");
            handlerThread.start();
            a = handlerThread.getLooper();
        }
    }

    /* renamed from: cvd$b */
    /* compiled from: Loopers */
    public static class b {
        public static Looper a;
        public static Handler b = new Handler(a);

        static {
            HandlerThread handlerThread = new HandlerThread("Telescope_Main_Thread");
            handlerThread.start();
            a = handlerThread.getLooper();
        }
    }
}
