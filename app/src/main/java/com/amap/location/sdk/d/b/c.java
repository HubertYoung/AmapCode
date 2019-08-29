package com.amap.location.sdk.d.b;

import android.location.Location;
import android.support.annotation.NonNull;
import com.amap.location.sdk.d.a;

/* compiled from: BaseOutdoorLocationProvider */
public abstract class c {
    protected a a;

    protected c(@NonNull a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    public void a(Location location) {
        this.a.onLocationChanged(location);
    }
}
