package com.amap.location.g.b.a.c;

import android.content.Context;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import com.amap.location.common.d.a;
import java.util.List;

/* compiled from: SystemGpsProvider */
public class b implements a {
    private LocationManager a;

    public b(Context context) {
        this.a = (LocationManager) context.getSystemService("location");
    }

    public void a(String str, long j, float f, LocationListener locationListener, Looper looper) {
        try {
            if (this.a != null) {
                this.a.requestLocationUpdates(str, j, f, locationListener, looper);
            }
        } catch (SecurityException e) {
            a.a("sysgps", "", e);
        }
    }

    public void a(LocationListener locationListener) {
        if (this.a != null) {
            try {
                this.a.removeUpdates(locationListener);
            } catch (Exception e) {
                a.a("sysgps", "", e);
            }
        }
    }

    public boolean a(NmeaListener nmeaListener, Looper looper) {
        if (this.a == null) {
            return false;
        }
        try {
            return this.a.addNmeaListener(nmeaListener);
        } catch (SecurityException e) {
            a.a("sysgps", "", e);
            return false;
        }
    }

    public void a(NmeaListener nmeaListener) {
        if (this.a != null) {
            this.a.removeNmeaListener(nmeaListener);
        }
    }

    public boolean a(OnNmeaMessageListener onNmeaMessageListener, Looper looper) {
        if (this.a == null) {
            return false;
        }
        try {
            if (VERSION.SDK_INT >= 24) {
                return this.a.addNmeaListener(onNmeaMessageListener);
            }
            return false;
        } catch (SecurityException e) {
            a.a("sysgps", "", e);
            return false;
        }
    }

    public void a(OnNmeaMessageListener onNmeaMessageListener) {
        if (VERSION.SDK_INT >= 24 && this.a != null) {
            this.a.removeNmeaListener(onNmeaMessageListener);
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public boolean a(Callback callback) {
        if (this.a != null && VERSION.SDK_INT >= 24) {
            try {
                return this.a.registerGnssStatusCallback(callback);
            } catch (SecurityException e) {
                a.a("sysgps", "", e);
            }
        }
        return false;
    }

    public void b(Callback callback) {
        if (this.a != null && VERSION.SDK_INT >= 24) {
            this.a.unregisterGnssStatusCallback(callback);
        }
    }

    public boolean a(Listener listener) {
        if (this.a == null) {
            return false;
        }
        try {
            return this.a.addGpsStatusListener(listener);
        } catch (SecurityException e) {
            a.a("sysgps", "", e);
            return false;
        }
    }

    public void b(Listener listener) {
        if (this.a != null) {
            this.a.removeGpsStatusListener(listener);
        }
    }

    public boolean a(String str) {
        if (this.a == null) {
            return false;
        }
        try {
            return this.a.isProviderEnabled(str);
        } catch (Exception e) {
            a.a("sysgps", "", e);
            return false;
        }
    }

    public GpsStatus a(GpsStatus gpsStatus) {
        if (this.a == null) {
            return null;
        }
        try {
            return this.a.getGpsStatus(gpsStatus);
        } catch (SecurityException e) {
            a.a("sysgps", "", e);
            return null;
        }
    }

    public List<String> a() {
        if (this.a == null) {
            return null;
        }
        return this.a.getAllProviders();
    }
}
