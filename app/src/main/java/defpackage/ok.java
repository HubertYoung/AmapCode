package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: ok reason: default package */
/* compiled from: SpeedDetectManager */
public final class ok {
    public a a = null;
    private LocationManager b = null;
    private boolean c = false;
    private LocationListener d = new LocationListener() {
        public final void onProviderEnabled(String str) {
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public final void onLocationChanged(Location location) {
            if (!(ok.this.a == null || location == null)) {
                ok.this.a.a(location.getSpeed());
            }
            StringBuilder sb = new StringBuilder("onLocationChanged speed = ");
            sb.append(location == null ? "null" : Float.valueOf(location.getSpeed()));
            AMapLog.d("SpeedDetectManager", sb.toString());
        }

        public final void onProviderDisabled(String str) {
            ok.this.a();
        }
    };

    /* renamed from: ok$a */
    /* compiled from: SpeedDetectManager */
    public interface a {
        void a(float f);
    }

    @SuppressLint({"MissingPermission"})
    public final void a(Context context) {
        if (context != null) {
            if (this.b == null) {
                this.b = (LocationManager) context.getSystemService("location");
            }
            if (this.b == null || !LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                this.c = false;
            } else if (!this.c) {
                AMapLog.d("SpeedDetectManager", "start GPS isProviderEnabled = true");
                this.c = true;
                try {
                    this.b.requestLocationUpdates(WidgetType.GPS, 2, 0.0f, this.d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    public final void a() {
        if (!(this.d == null || this.b == null || !this.c)) {
            AMapLog.d("SpeedDetectManager", "stop GPS isProviderEnabled = true");
            this.c = false;
            try {
                this.b.removeUpdates(this.d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
