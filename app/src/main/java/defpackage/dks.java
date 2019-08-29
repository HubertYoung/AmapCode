package defpackage;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;

/* renamed from: dks reason: default package */
/* compiled from: HTTPPollingTimer */
public final class dks extends Handler {
    private static dks e;
    private static HandlerThread f;
    long a = 10000;
    private boolean b = true;
    private int c = 0;
    private ArrayList<dkt> d = new ArrayList<>();

    private dks(Looper looper) {
        super(looper);
    }

    public static dks a() {
        if (e == null) {
            HandlerThread handlerThread = new HandlerThread("HTTPPollingThread");
            f = handlerThread;
            handlerThread.start();
            e = new dks(f.getLooper());
        }
        return e;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        synchronized (this.d) {
            dkt dkt = (this.d.size() <= i || i < 0) ? null : this.d.get(i);
            if (dkt != null) {
                dkt.a();
                if (this.b) {
                    sendEmptyMessageDelayed(i, this.a);
                } else {
                    this.d.remove(i);
                }
            }
        }
    }

    public final void b() {
        removeCallbacksAndMessages(null);
        synchronized (this.d) {
            this.d.clear();
        }
        if (f != null) {
            if (VERSION.SDK_INT >= 18) {
                f.quitSafely();
            } else {
                f.quit();
            }
        }
        f = null;
        e = null;
    }

    public final void a(dkt dkt) {
        synchronized (this.d) {
            int i = 0;
            while (i < this.d.size()) {
                if (this.d.get(i) != dkt) {
                    i++;
                } else {
                    return;
                }
            }
            this.d.add(dkt);
        }
    }

    public final void b(dkt dkt) {
        synchronized (this.d) {
            for (int i = 0; i < this.d.size(); i++) {
                if (this.d.get(i) == dkt) {
                    this.d.remove(dkt);
                }
            }
        }
    }
}
