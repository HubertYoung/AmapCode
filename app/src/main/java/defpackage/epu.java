package defpackage;

import android.content.Context;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: epu reason: default package */
/* compiled from: LocationTimer */
public final class epu extends Thread {
    private static epu c;
    private volatile boolean a = false;
    private Context b;

    private epu(Context context) {
        super("LocationTimer");
        this.b = context.getApplicationContext();
    }

    public static synchronized void a(Context context) {
        synchronized (epu.class) {
            if (c == null || !c.a) {
                epu epu = new epu(context);
                c = epu;
                epu.start();
            }
        }
    }

    public static synchronized void a() {
        synchronized (epu.class) {
            if (c != null && c.a) {
                epu epu = c;
                epu.a = false;
                epu.interrupt();
            }
            c = null;
        }
    }

    public final void run() {
        if (!this.a) {
            this.a = true;
            try {
                sleep(20000);
                if (this.a) {
                    if (LocationInstrument.getInstance().getLatestPosition(5) == null && this.b != null) {
                        ToastHelper.showToast(this.b.getResources().getText(R.string.ic_loc_fail).toString());
                    }
                    this.a = false;
                    b();
                }
            } catch (InterruptedException unused) {
            }
        }
    }

    private synchronized void b() {
        if (this == c) {
            c = null;
        }
    }
}
