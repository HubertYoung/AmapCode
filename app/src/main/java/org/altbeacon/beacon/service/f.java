package org.altbeacon.beacon.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.b.d;

/* compiled from: RangeState */
public final class f implements Serializable {
    private static boolean c = false;
    private b a;
    private Map<Beacon, g> b = new HashMap();

    public f(b c2) {
        this.a = c2;
    }

    public final void a(Beacon beacon) {
        if (this.b.containsKey(beacon)) {
            g rangedBeacon = this.b.get(beacon);
            d.a("RangeState", "adding %s to existing range for: %s", beacon, rangedBeacon);
            rangedBeacon.a(beacon);
            return;
        }
        d.a("RangeState", "adding %s to new rangedBeacon", beacon);
        this.b.put(beacon, new g(beacon));
    }

    public final synchronized Collection<Beacon> a() {
        ArrayList finalizedBeacons;
        boolean z;
        Map newRangedBeacons = new HashMap();
        finalizedBeacons = new ArrayList();
        synchronized (this.b) {
            for (Beacon beacon : this.b.keySet()) {
                g rangedBeacon = this.b.get(beacon);
                if (rangedBeacon.a()) {
                    rangedBeacon.d();
                    if (!rangedBeacon.e()) {
                        finalizedBeacons.add(rangedBeacon.c());
                    }
                }
                if (!rangedBeacon.e()) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    if (!c || rangedBeacon.f()) {
                        rangedBeacon.b();
                    }
                    newRangedBeacons.put(beacon, rangedBeacon);
                } else {
                    d.a("RangeState", "Dumping beacon from RangeState because it has no recent measurements.", new Object[0]);
                }
            }
            this.b = newRangedBeacons;
        }
        return finalizedBeacons;
    }

    public static void a(boolean useTrackingCache) {
        c = useTrackingCache;
    }

    public static boolean b() {
        return c;
    }
}
