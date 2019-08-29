package com.amap.location.g.b;

import java.util.List;

/* compiled from: AmapSatelliteStatusListener */
public interface d {
    void onFirstFix(int i);

    void onGpsStatusListener(int i, int i2, float f, List<c> list);

    void onStarted();

    void onStopped();
}
