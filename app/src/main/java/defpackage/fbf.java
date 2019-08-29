package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* renamed from: fbf reason: default package */
/* compiled from: PushClientThread */
public final class fbf {
    private static final Handler a = new Handler(Looper.getMainLooper());
    private static final HandlerThread b;
    private static final Handler c = new fbg(b.getLooper());

    static {
        HandlerThread handlerThread = new HandlerThread("push_client_thread");
        b = handlerThread;
        handlerThread.start();
    }

    public static void a(fbe fbe) {
        if (fbe == null) {
            fat.a((String) "PushClientThread", (String) "client thread error, task is null!");
            return;
        }
        int i = fbe.c;
        Message message = new Message();
        message.what = i;
        message.obj = fbe;
        c.sendMessageDelayed(message, 0);
    }

    public static void a(Runnable runnable) {
        c.removeCallbacks(runnable);
        c.postDelayed(runnable, 15000);
    }

    public static void b(Runnable runnable) {
        a.post(runnable);
    }
}
