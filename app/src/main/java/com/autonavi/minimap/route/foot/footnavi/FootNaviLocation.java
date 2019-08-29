package com.autonavi.minimap.route.foot.footnavi;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.os.Build.VERSION;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Iterator;

public class FootNaviLocation extends ecb implements ang<Status>, Callback<Status> {
    private static String TAG = "FootNaviLocation";
    private a mListener;

    public interface a {
        void a(int i);

        void a(Location location);
    }

    public void error(Throwable th, boolean z) {
    }

    public void startLocation(a aVar) {
        this.mListener = aVar;
        try {
            LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS);
            LocationInstrument.getInstance().addOriginalLocation(this);
            onStartLocation();
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public int getGpsNumber() {
        return getSatCount();
    }

    public void manualUpdateGpsStatus() {
        updateGpsStatus();
    }

    public void callback(Status status) {
        if (getSatCount() >= 4) {
            if (status == Status.ON_LOCATION_OK) {
                defpackage.eao.a.a((String) "FootNaviLocation_callback_ok");
                if (LocationInstrument.getInstance().getLatestLocation() != null) {
                    Location location = new Location(LocationInstrument.getInstance().getLatestLocation());
                    defpackage.eao.a.a("FootNaviLocation_callback_location: ".concat(String.valueOf(location)));
                    if (location.getProvider().equals(WidgetType.GPS)) {
                        defpackage.eao.a.a((String) "FootNaviLocation_callback_gps_provider_true");
                        if (this.mListener != null) {
                            this.mListener.a(location);
                        }
                    }
                }
                return;
            }
            if (status == Status.ON_LOCATION_FAIL) {
                defpackage.eao.a.a((String) "FootNaviLocation_callback_fail");
            }
        }
    }

    public void onGpsStatusChanged(int i) {
        switch (i) {
            case 3:
                return;
            case 4:
                updateGpsStatus();
                break;
        }
    }

    public void onNewSatCount(int i) {
        if (this.mListener != null) {
            this.mListener.a(i);
        }
    }

    public void stopLocation() {
        LocationInstrument.getInstance().removeOriginalLocation(this);
        onStopLocation();
        LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS, Provider.PROVIDER_NETWORK);
        this.mListener = null;
    }

    public void onOriginalLocationChange(Status status) {
        Location originalLocation = LocationInstrument.getInstance().getOriginalLocation();
        if ((originalLocation != null && VERSION.SDK_INT >= 18 && originalLocation.isFromMockProvider()) || getSatCount() >= 4) {
            if (status == Status.ON_LOCATION_OK && originalLocation != null && originalLocation.getProvider().equals(WidgetType.GPS) && this.mListener != null) {
                this.mListener.a(originalLocation);
            }
            return;
        }
        defpackage.eao.a.a("FootNaviLocation", "Location not satisfied, return");
    }

    private void updateGpsStatus() {
        GpsStatus gpsStatus = LocationInstrument.getInstance().getGpsStatus(null);
        if (gpsStatus != null) {
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();
            int i = 0;
            while (it.hasNext() && i <= maxSatellites) {
                if (it.next().usedInFix()) {
                    i++;
                }
            }
            setSatCount(i);
            onNewSatCount(i);
        }
    }
}
