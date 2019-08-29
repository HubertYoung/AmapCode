package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* renamed from: ebr reason: default package */
/* compiled from: SingleHandler */
public final class ebr {
    private static a a;
    private static a b;

    /* renamed from: ebr$a */
    /* compiled from: SingleHandler */
    public static class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void dispatchMessage(Message message) {
            try {
                super.dispatchMessage(message);
            } catch (Exception e) {
                String.valueOf(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static a a(boolean z) {
        if (z) {
            if (a == null) {
                a = new a(Looper.getMainLooper());
            }
            return a;
        }
        if (b == null) {
            HandlerThread handlerThread = new HandlerThread("SingleHandler-not-ui-thread");
            handlerThread.start();
            b = new a(handlerThread.getLooper());
        }
        return b;
    }
}
