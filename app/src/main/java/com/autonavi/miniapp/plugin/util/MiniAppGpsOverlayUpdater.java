package com.autonavi.miniapp.plugin.util;

import android.location.Location;
import android.os.Bundle;
import com.amap.api.service.IndoorLocationProvider;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.suspend.refactor.gps.GpsOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.List;

public class MiniAppGpsOverlayUpdater {
    private static final String TAG = "MiniAppGpsOverlayUpdater";
    private LocatorCallback mLocatorCallback;
    private agl<GpsOverlay> mOverlaySet;

    public static class LocatorCallback implements Callback<Status> {
        WeakReference<MiniAppGpsOverlayUpdater> mUpdaterWeakReference;

        public LocatorCallback(MiniAppGpsOverlayUpdater miniAppGpsOverlayUpdater) {
            this.mUpdaterWeakReference = new WeakReference<>(miniAppGpsOverlayUpdater);
        }

        public void error(Throwable th, boolean z) {
            th.printStackTrace();
        }

        public void callback(Status status) {
            MiniAppGpsOverlayUpdater miniAppGpsOverlayUpdater = (MiniAppGpsOverlayUpdater) this.mUpdaterWeakReference.get();
            if (miniAppGpsOverlayUpdater == null) {
                LocationInstrument.getInstance().removeHighFrequencyStatusCallback(this);
            } else if (status == Status.ON_LOCATION_OK) {
                miniAppGpsOverlayUpdater.onGetLocationSuccess();
            } else {
                miniAppGpsOverlayUpdater.onGetLocationFailure();
            }
        }
    }

    static class MiniAppGpsOverlayUpdaterHolder {
        public static MiniAppGpsOverlayUpdater INSTANCE = new MiniAppGpsOverlayUpdater();

        private MiniAppGpsOverlayUpdaterHolder() {
        }
    }

    private MiniAppGpsOverlayUpdater() {
        this.mOverlaySet = new agl<>();
        this.mLocatorCallback = new LocatorCallback(this);
    }

    public static MiniAppGpsOverlayUpdater getInstance() {
        return MiniAppGpsOverlayUpdaterHolder.INSTANCE;
    }

    public void removeOverlay(GpsOverlay gpsOverlay) {
        if (gpsOverlay != null) {
            this.mOverlaySet.b(gpsOverlay);
            getGpsOverlaysWithCheckState();
        }
    }

    private List<GpsOverlay> getGpsOverlaysWithCheckState() {
        List<GpsOverlay> a = this.mOverlaySet.a();
        if (!a.isEmpty()) {
            return a;
        }
        if (this.mLocatorCallback != null) {
            LocationInstrument.getInstance().removeHighFrequencyStatusCallback(this.mLocatorCallback);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void onGetLocationSuccess() {
        Bundle bundle;
        int i;
        int i2;
        List<GpsOverlay> gpsOverlaysWithCheckState = getGpsOverlaysWithCheckState();
        if (gpsOverlaysWithCheckState != null) {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation != null) {
                GeoPoint offsetedPoint = LocationInstrument.getOffsetedPoint(latestLocation);
                if (offsetedPoint != null) {
                    String provider = latestLocation.getProvider();
                    Bundle extras = latestLocation.getExtras();
                    int accuracy = extras == null ? (int) latestLocation.getAccuracy() : (int) extras.getDouble("render_accuracy", (double) latestLocation.getAccuracy());
                    char c = 65535;
                    int hashCode = provider.hashCode();
                    if (hashCode != -1184229805) {
                        if (hashCode == 102570 && provider.equals(WidgetType.GPS)) {
                            c = 0;
                        }
                    } else if (provider.equals(IndoorLocationProvider.NAME)) {
                        c = 1;
                    }
                    switch (c) {
                        case 0:
                            bundle = null;
                            i2 = (int) latestLocation.getAltitude();
                            i = 0;
                            break;
                        case 1:
                            i2 = (int) latestLocation.getAltitude();
                            bundle = latestLocation.getExtras();
                            i = 2;
                            break;
                        default:
                            bundle = null;
                            i2 = 0;
                            i = 1;
                            break;
                    }
                    for (GpsOverlay next : gpsOverlaysWithCheckState) {
                        if (next.isVisible()) {
                            next.setItem(offsetedPoint.x, offsetedPoint.y, accuracy, i2, i, bundle);
                            next.setBearings(latestLocation.getBearing(), latestLocation.hasBearing(), latestLocation.getSpeed());
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onGetLocationFailure() {
        List<GpsOverlay> gpsOverlaysWithCheckState = getGpsOverlaysWithCheckState();
        if (gpsOverlaysWithCheckState != null) {
            for (GpsOverlay next : gpsOverlaysWithCheckState) {
                next.removeAll();
                next.clearFocus();
            }
        }
    }

    public void addOverlay(GpsOverlay gpsOverlay) {
        if (gpsOverlay != null) {
            LocationInstrument.getInstance().addHighFrequencyStatusCallback(this.mLocatorCallback, null);
            this.mOverlaySet.a(gpsOverlay);
        }
    }
}
