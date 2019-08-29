package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

/* renamed from: cqp reason: default package */
/* compiled from: DecodePageThread */
public final class cqp extends Thread {
    public Handler a;
    private final WeakReference<CarLicenseScanPage> b;
    private final CountDownLatch c = new CountDownLatch(1);

    public cqp(CarLicenseScanPage carLicenseScanPage) {
        this.b = new WeakReference<>(carLicenseScanPage);
    }

    public final Handler a() {
        try {
            this.c.await();
        } catch (InterruptedException unused) {
        }
        return this.a;
    }

    public final void run() {
        CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.b.get();
        if (carLicenseScanPage != null && carLicenseScanPage.isAlive()) {
            Looper.prepare();
            this.a = new cqo(carLicenseScanPage);
            this.c.countDown();
            Looper.loop();
        }
    }
}
