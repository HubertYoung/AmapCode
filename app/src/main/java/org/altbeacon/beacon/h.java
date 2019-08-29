package org.altbeacon.beacon;

import java.util.Map.Entry;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.service.BeaconService;

/* compiled from: BeaconManager */
final class h {
    final /* synthetic */ g a;

    /* synthetic */ h(g x0, byte b) {
        this(x0);
    }

    private h(g gVar) {
        this.a = gVar;
    }

    public final void a(BeaconService service) {
        d.a("BeaconManager", "we have a connection to the service now", new Object[0]);
        if (this.a.r == null) {
            this.a.r = Boolean.valueOf(false);
        }
        this.a.j = service.b();
        this.a.o();
        synchronized (this.a.i) {
            for (Entry entry : this.a.i.entrySet()) {
                if (!((i) entry.getValue()).a) {
                    ((d) entry.getKey()).onBeaconServiceConnect();
                    ((i) entry.getValue()).a = true;
                }
            }
        }
    }

    public final void a() {
        d.d("BeaconManager", "onServiceDisconnected", new Object[0]);
        this.a.j = null;
    }
}
