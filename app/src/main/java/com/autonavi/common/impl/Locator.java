package com.autonavi.common.impl;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.os.Looper;
import com.amap.location.sdk.fusion.interfaces.LocationNmeaListener;
import com.amap.location.sdk.fusion.interfaces.LocationSatelliteListener;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.sdk.location.ILocationEventListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

public interface Locator {

    public enum LOCATION_SCENE {
        TYPE_APPLICATION_NONEXIST(-1),
        TYPE_DEFAULT(0),
        TYPE_MAINMAP(1),
        TYPE_DRIVE_PATH_PLAN(2),
        TYPE_ROUTE_PATH_PLAN(3),
        TYPE_FOOT_PATH_PLAN(4),
        TYPE_DRIVE_NAVIGATION(5),
        TYPE_ROUTE_NAVIGATION(6),
        TYPE_FOOT_NAVIGATION(7),
        TYPE_BUS_ROUTE(8),
        TYPE_ROUTE_EXPLORE(9),
        TYPE_COMMUTING(10);
        
        public final int type;

        private LOCATION_SCENE(int i) {
            this.type = i;
        }
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LocationPreference {
        boolean availableOnBackground() default false;
    }

    public enum Provider {
        PROVIDER_GPS(1),
        PROVIDER_NETWORK_COARSE(2),
        PROVIDER_NETWORK(6);
        
        private int value;

        private Provider(int i) {
            this.value = 0;
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum Status {
        ON_LOCATION_OK,
        ON_LOCATION_FAIL,
        ON_LOCATION_GPS_OK,
        ON_LOCATION_GPS_FAIL_LOOP
    }

    public interface a {
        boolean a();
    }

    public interface b {
        GeoPoint a();
    }

    void addGpsStatusListener(Listener listener);

    void addHighFrequencyStatusCallback(Callback<Status> callback, Object obj);

    void addLocationEventListener(ILocationEventListener iLocationEventListener);

    void addNmeaListener(LocationNmeaListener locationNmeaListener, Looper looper);

    void addOriginalLocation(ang<Status> ang);

    void addSatelliteListener(LocationSatelliteListener locationSatelliteListener, Looper looper);

    void addStatusCallback(Callback<Status> callback, Object obj);

    void doStartLocate();

    void doStopLocate();

    void fakeNetworkLocation(boolean z);

    GeoPoint getCacheLatestPosition();

    GpsStatus getGpsStatus(GpsStatus gpsStatus);

    List<Location> getLatestGpsLocations();

    Location getLatestLocation();

    GeoPoint getLatestPosition();

    GeoPoint getLatestPosition(int i);

    List<Float> getLatestSpeeds();

    LocInfo getLocInfo();

    String getLocationLog(Context context);

    Location getOriginalLocation();

    long getStartLocateSequence();

    void init();

    boolean isLocating();

    boolean isLocating2();

    boolean isProviderEnabled(Provider provider);

    void registerGnssStatusCallback(GnssStatus.Callback callback);

    void release();

    void removeGpsStatusListener(Listener listener);

    void removeHighFrequencyStatusCallback(Callback<Status> callback);

    void removeNmeaListener(LocationNmeaListener locationNmeaListener);

    void removeOriginalLocation(ang<Status> ang);

    void removeSatelliteListener(LocationSatelliteListener locationSatelliteListener);

    void removeStatusCallback(Callback<Status> callback);

    void setFeedbackTime(Context context, long j);

    void setMapRect(b bVar);

    void setProvider(Provider... providerArr);

    void subscribe(Context context, LOCATION_SCENE location_scene);

    void unregisterGnssStatusCallback(GnssStatus.Callback callback);

    void unsubscribe(Context context);
}
