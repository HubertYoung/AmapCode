package com.amap.location.sdk.fusion.interfaces;

import com.amap.location.g.b.c;
import com.amap.location.g.b.d;
import java.util.List;

public abstract class LocationSatelliteListener {
    d mAmapSatelliteStatusListener = new d() {
        public void onStarted() {
            LocationSatelliteListener.this.onStarted();
        }

        public void onStopped() {
            LocationSatelliteListener.this.onStopped();
        }

        public void onFirstFix(int i) {
            LocationSatelliteListener.this.onFirstFix(i);
        }

        public void onGpsStatusListener(int i, int i2, float f, List<c> list) {
            LocationSatelliteListener.this.onSatelliteChanged(i, i2, f);
        }
    };

    public abstract void onFirstFix(int i);

    public abstract void onSatelliteChanged(int i, int i2, float f);

    public abstract void onStarted();

    public abstract void onStopped();

    public d getAmapSatelliteStatusListener() {
        return this.mAmapSatelliteStatusListener;
    }
}
