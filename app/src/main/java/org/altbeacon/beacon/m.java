package org.altbeacon.beacon;

import android.content.Context;
import android.content.Intent;
import java.util.Collection;
import java.util.Set;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.service.MonitoringStatus;
import org.altbeacon.beacon.service.e;
import org.altbeacon.beacon.service.h;

/* compiled from: IntentHandler */
class m {
    private static final String a = m.class.getSimpleName();

    m() {
    }

    public static void a(Context context, Intent intent) {
        int i;
        e monitoringData = null;
        h rangingData = null;
        if (!(intent == null || intent.getExtras() == null)) {
            if (intent.getExtras().getBundle("monitoringData") != null) {
                monitoringData = e.a(intent.getExtras().getBundle("monitoringData"));
            }
            if (intent.getExtras().getBundle("rangingData") != null) {
                rangingData = h.a(intent.getExtras().getBundle("rangingData"));
            }
        }
        if (rangingData != null) {
            d.a(a, "got ranging data", new Object[0]);
            if (rangingData.a() == null) {
                d.c(a, "Ranging data has a null beacons collection", new Object[0]);
            }
            Set<o> notifiers = g.a(context).q();
            Collection beacons = rangingData.a();
            if (notifiers != null) {
                for (o didRangeBeaconsInRegion : notifiers) {
                    didRangeBeaconsInRegion.didRangeBeaconsInRegion(beacons, rangingData.b());
                }
            } else {
                d.a(a, "but ranging notifier is null, so we're dropping it.", new Object[0]);
            }
            o dataNotifier = g.a(context).w();
            if (dataNotifier != null) {
                dataNotifier.didRangeBeaconsInRegion(beacons, rangingData.b());
            }
        }
        if (monitoringData != null) {
            d.a(a, "got monitoring data", new Object[0]);
            Set<n> notifiers2 = g.a(context).p();
            if (notifiers2 != null) {
                for (n notifier : notifiers2) {
                    d.a(a, "Calling monitoring notifier: %s", notifier);
                    Region region = monitoringData.b();
                    if (monitoringData.a()) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    Integer state = Integer.valueOf(i);
                    state.intValue();
                    MonitoringStatus.a(context).a(region, state);
                }
            }
        }
    }
}
