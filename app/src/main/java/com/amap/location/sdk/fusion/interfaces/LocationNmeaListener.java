package com.amap.location.sdk.fusion.interfaces;

import com.amap.location.g.b.b;

public abstract class LocationNmeaListener {
    private b mAmapNmeaListener = new b() {
        public void onNmeaReceived(long j, String str) {
            LocationNmeaListener.this.onNmeaStringReceived(j, str);
        }
    };

    public abstract void onNmeaStringReceived(long j, String str);

    public b getAmapNmeaListener() {
        return this.mAmapNmeaListener;
    }
}
