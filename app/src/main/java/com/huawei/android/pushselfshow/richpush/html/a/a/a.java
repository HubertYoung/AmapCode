package com.huawei.android.pushselfshow.richpush.html.a.a;

import android.location.LocationManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.huawei.android.pushselfshow.richpush.html.a.j;

public class a extends c {
    public a(LocationManager locationManager, j jVar) {
        super(locationManager, jVar, "GPSListener");
    }

    public void a(long j, float f) {
        if (!this.b) {
            if (this.a.getProvider(WidgetType.GPS) != null) {
                this.b = true;
                this.a.requestLocationUpdates(WidgetType.GPS, j, f, this);
                return;
            }
            a(com.huawei.android.pushselfshow.richpush.html.api.d.a.POSITION_UNAVAILABLE_GPS);
        }
    }
}
