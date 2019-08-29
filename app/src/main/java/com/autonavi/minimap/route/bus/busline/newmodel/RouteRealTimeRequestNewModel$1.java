package com.autonavi.minimap.route.bus.busline.newmodel;

import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.route.net.base.resp.BusJsonRespCallback;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import com.autonavi.minimap.route.net.module.RTBusLocationSet;
import java.util.HashMap;
import java.util.Map.Entry;

public class RouteRealTimeRequestNewModel$1 extends BusJsonRespCallback {
    final /* synthetic */ dup a;
    private boolean b;

    public RouteRealTimeRequestNewModel$1(dup dup) {
        this.a = dup;
    }

    public final Class<?> a() {
        return RTBusLocationSet.class;
    }

    public final void a(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof RTBusLocationSet)) {
            z = false;
        } else {
            RTBusLocationSet rTBusLocationSet = (RTBusLocationSet) obj;
            z = rTBusLocationSet.getCode() == 1;
            HashMap<String, RTBusLocation> buses = rTBusLocationSet.getBuses();
            if (buses != null) {
                RTBusLocation rTBusLocation = null;
                for (Entry<String, RTBusLocation> value : buses.entrySet()) {
                    rTBusLocation = (RTBusLocation) value.getValue();
                    if (rTBusLocation != null) {
                        break;
                    }
                }
                if (rTBusLocation != null) {
                    this.a.a.clear();
                    this.a.a.put(rTBusLocation.getLine(), rTBusLocation);
                }
            }
        }
        if (z) {
            this.b = true;
            this.a.c = 0;
        } else {
            this.a.c = this.a.c + 1;
        }
        if (this.a.d) {
            ebj.b(1);
            this.a.d = false;
        }
        if (this.a.b != null) {
            this.a.b.b();
        }
    }

    public final void a(AosResponseException aosResponseException) {
        if (this.b) {
            this.a.c = this.a.c + 1;
        }
        if (this.a.d) {
            if (aosResponseException != null && "network error".equalsIgnoreCase(aosResponseException.getMessage())) {
                ebj.b(3);
            } else {
                ebj.b(2);
            }
            this.a.d = false;
        }
        if (this.a.b != null) {
            this.a.b.b();
        }
    }
}
