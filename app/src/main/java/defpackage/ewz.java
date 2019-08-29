package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* renamed from: ewz reason: default package */
/* compiled from: Worker */
public abstract class ewz {
    protected Context a;
    protected Handler b;
    private final Object c = new Object();

    /* renamed from: ewz$a */
    /* compiled from: Worker */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            ewz.this.b(message);
        }
    }

    public abstract void b(Message message);

    public ewz() {
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName(), 1);
        handlerThread.start();
        this.b = new a(handlerThread.getLooper());
    }

    public final void a(Context context) {
        this.a = context;
    }

    public final void a(Message message) {
        synchronized (this.c) {
            if (this.b == null) {
                StringBuilder sb = new StringBuilder("Dead worker dropping a message: ");
                sb.append(message.what);
                String sb2 = sb.toString();
                String simpleName = getClass().getSimpleName();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(" (Thread ");
                sb3.append(Thread.currentThread().getId());
                sb3.append(")");
                fat.e(simpleName, sb3.toString());
            } else {
                this.b.sendMessage(message);
            }
        }
    }
}
