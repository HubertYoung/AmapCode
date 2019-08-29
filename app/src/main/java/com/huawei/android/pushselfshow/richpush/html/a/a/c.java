package com.huawei.android.pushselfshow.richpush.html.a.a;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.huawei.android.pushselfshow.richpush.html.a.j;
import com.huawei.android.pushselfshow.richpush.html.api.d.a;

public class c implements LocationListener {
    protected LocationManager a;
    protected boolean b = false;
    private j c;
    private String d = "PLocationListener";

    public c(LocationManager locationManager, j jVar, String str) {
        this.a = locationManager;
        this.c = jVar;
        this.d = str;
    }

    private void a(Location location) {
        this.c.b(location);
        if (!this.c.b) {
            com.huawei.android.pushagent.a.a.c.a(this.d, "Stopping global listener");
            b();
        }
    }

    public void a() {
        b();
    }

    public void a(long j, float f) {
        try {
            if (!this.b) {
                if (this.a.getProvider("network") != null) {
                    this.b = true;
                    this.a.requestLocationUpdates("network", j, f, this);
                    return;
                }
                a(a.POSITION_UNAVAILABLE_NETOWRK);
            }
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.d(this.d, "start postion error ", e);
        }
    }

    /* access modifiers changed from: protected */
    public void a(a aVar) {
        this.c.a(aVar);
        if (!this.c.b) {
            com.huawei.android.pushagent.a.a.c.a(this.d, "Stopping global listener");
            b();
        }
    }

    public void b() {
        try {
            if (this.b) {
                this.a.removeUpdates(this);
                this.b = false;
            }
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.d(this.d, "stop postion error ", e);
        }
    }

    public void onLocationChanged(Location location) {
        com.huawei.android.pushagent.a.a.c.a(this.d, "The location has been updated!");
        a(location);
    }

    public void onProviderDisabled(String str) {
        String str2 = this.d;
        StringBuilder sb = new StringBuilder("Location provider '");
        sb.append(str);
        sb.append("' disabled.");
        com.huawei.android.pushagent.a.a.c.a(str2, sb.toString());
        a(a.POSITION_UNAVAILABLE_GPS);
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        String str2 = this.d;
        StringBuilder sb = new StringBuilder("The status of the provider ");
        sb.append(str);
        sb.append(" has changed");
        com.huawei.android.pushagent.a.a.c.a(str2, sb.toString());
        if (i == 0) {
            String str3 = this.d;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" is OUT OF SERVICE");
            com.huawei.android.pushagent.a.a.c.a(str3, sb2.toString());
            if ("network".equals(str)) {
                a(a.POSTION_OUT_OF_SERVICE_NETOWRK);
            } else {
                a(a.POSTION_OUT_OF_SERVICE_GPS);
            }
        } else if (i == 1) {
            String str4 = this.d;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" is TEMPORARILY_UNAVAILABLE");
            com.huawei.android.pushagent.a.a.c.a(str4, sb3.toString());
        } else {
            String str5 = this.d;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(" is AVAILABLE");
            com.huawei.android.pushagent.a.a.c.a(str5, sb4.toString());
        }
    }
}
