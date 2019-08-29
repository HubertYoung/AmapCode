package org.altbeacon.beacon.service;

import android.os.SystemClock;
import java.io.Serializable;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;

/* compiled from: RegionMonitoringState */
public class i implements Serializable {
    private static final String a = i.class.getSimpleName();
    private boolean b = false;
    private long c = 0;
    private final b d;

    public i(b c2) {
        this.d = c2;
    }

    public final b a() {
        return this.d;
    }

    public final boolean b() {
        this.c = SystemClock.elapsedRealtime();
        if (this.b) {
            return false;
        }
        this.b = true;
        return true;
    }

    public final void c() {
        this.b = false;
        this.c = 0;
    }

    public final boolean d() {
        if (!this.b || this.c <= 0 || SystemClock.elapsedRealtime() - this.c <= g.a()) {
            return false;
        }
        d.a(a, "We are newly outside the region because the lastSeenTime of %s was %s seconds ago, and that is over the expiration duration of %s", Long.valueOf(this.c), Long.valueOf(SystemClock.elapsedRealtime() - this.c), Long.valueOf(g.a()));
        c();
        return true;
    }

    public final boolean e() {
        return this.b;
    }
}
