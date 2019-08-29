package org.altbeacon.beacon.service;

import android.os.SystemClock;
import java.io.Serializable;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.b.d;

/* compiled from: RangedBeacon */
public final class g implements Serializable {
    public static long a = 5000;
    private static long e = 20000;
    protected long b = 0;
    Beacon c;
    protected transient j d = null;
    private boolean f = true;

    public g(Beacon beacon) {
        a(beacon);
    }

    public final void a(Beacon beacon) {
        this.c = beacon;
        a(Integer.valueOf(this.c.f()));
    }

    public final boolean a() {
        return this.f;
    }

    public final void b() {
        this.f = false;
    }

    public final Beacon c() {
        return this.c;
    }

    public final void d() {
        k.a(e);
        if (!h().a()) {
            double runningAverage = h().b();
            this.c.a(runningAverage);
            d.a("RangedBeacon", "calculated new runningAverageRssi: %s", Double.valueOf(runningAverage));
            return;
        }
        d.a("RangedBeacon", "No measurements available to calculate running average", new Object[0]);
    }

    private void a(Integer rssi) {
        if (rssi.intValue() != 127) {
            this.f = true;
            this.b = SystemClock.elapsedRealtime();
            h().a(rssi);
        }
    }

    public final boolean e() {
        return h().a();
    }

    private long g() {
        return SystemClock.elapsedRealtime() - this.b;
    }

    public final boolean f() {
        return g() > a;
    }

    private j h() {
        if (this.d == null) {
            try {
                this.d = (j) org.altbeacon.beacon.g.u().getConstructors()[0].newInstance(new Object[0]);
            } catch (Exception e2) {
                d.d("RangedBeacon", "Could not construct RssiFilterImplClass %s", org.altbeacon.beacon.g.u().getName());
            }
        }
        return this.d;
    }
}
