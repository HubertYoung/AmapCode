package com.alipay.mobile.common.lbs;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.LBSLocationProxyInterface;
import java.util.concurrent.TimeUnit;

public final class LBSLocationManagerProxy {
    public static final String COUNTRY_CHANGE_BROADCAST_ACTION = "com.eg.android.alipay.mobile.common.lbs.countrychanged";
    private static final long DEFAULT_LOCATION_INTERVAL = TimeUnit.SECONDS.toMillis(30);
    private static final boolean IS_NEED_ADDRESS = true;
    public static final String LOCATION_CHANGE_BROADCAST_ACTION = ".com.alipay.mobile.common.lbs.locationchanged";
    private static final long LOCATION_TIME_OUT = TimeUnit.SECONDS.toMillis(31);
    private static final String TAG = "LBSLocationManagerProxy";
    private static LBSLocationManagerProxy instance;
    private LBSLocationProxyInterface mLocationProxyInterface = reflectProxyInterface();

    private LBSLocationManagerProxy() {
    }

    private LBSLocationProxyInterface reflectProxyInterface() {
        try {
            LBSLocationProxyInterface lBSLocationProxyInterface = (LBSLocationProxyInterface) getClass().getClassLoader().loadClass("com.alipay.mobilelbs.biz.deprecated.LBSLocationProxyInner").newInstance();
            LoggerFactory.getTraceLogger().info(TAG, "reflectProxyInterface, got inner");
            return lBSLocationProxyInterface;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
            return null;
        }
    }

    public static LBSLocationManagerProxy getInstance() {
        if (instance == null) {
            synchronized (LBSLocationManagerProxy.class) {
                try {
                    if (instance == null) {
                        instance = new LBSLocationManagerProxy();
                    }
                }
            }
        }
        return instance;
    }

    @Deprecated
    public final void requestLocationUpdates(LBSLocationListener lBSLocationListener, boolean z, long j, Context context) {
        doRequestLocationUpdates(context, false, lBSLocationListener, j, LOCATION_TIME_OUT, z, null);
    }

    @Deprecated
    public final void requestLocationUpdates(LBSLocationListener lBSLocationListener, boolean z, Context context) {
        doRequestLocationUpdates(context, false, lBSLocationListener, DEFAULT_LOCATION_INTERVAL, LOCATION_TIME_OUT, z, null);
    }

    @Deprecated
    public final void requestLocationUpdates(Context context, LBSLocationListener lBSLocationListener) {
        doRequestLocationUpdates(context, false, lBSLocationListener, DEFAULT_LOCATION_INTERVAL, LOCATION_TIME_OUT, true, null);
    }

    @Deprecated
    public final void requestLocationUpdates(Context context, long j, LBSLocationListener lBSLocationListener) {
        doRequestLocationUpdates(context, false, lBSLocationListener, j, LOCATION_TIME_OUT, true, null);
    }

    @Deprecated
    public final void requestLocationUpdates(Context context, LBSLocationListener lBSLocationListener, long j) {
        doRequestLocationUpdates(context, false, lBSLocationListener, DEFAULT_LOCATION_INTERVAL, j, true, null);
    }

    @Deprecated
    public final void requestLocationUpdates(Context context, LBSLocationListener lBSLocationListener, boolean z) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "requestLocationUpdates, mLocationProxyInterface == null");
            return;
        }
        this.mLocationProxyInterface.setSdkLocationFailedisFromAPP(z);
        doRequestLocationUpdates(context, false, lBSLocationListener, DEFAULT_LOCATION_INTERVAL, LOCATION_TIME_OUT, true, null);
    }

    @Deprecated
    public final void requestLocationUpdates(Context context, boolean z, LBSLocationListener lBSLocationListener) {
        doRequestLocationUpdates(context, false, lBSLocationListener, DEFAULT_LOCATION_INTERVAL, LOCATION_TIME_OUT, true, null);
    }

    @Deprecated
    public final void doRequestLocationUpdates(Context context, boolean z, LBSLocationListener lBSLocationListener, long j, long j2, boolean z2, String str) {
        doRequestLocationUpdates(context, z, lBSLocationListener, j, j2, z2, str, false, "F");
    }

    public final void doRequestLocationUpdates(Context context, boolean z, LBSLocationListener lBSLocationListener, long j, long j2, boolean z2, String str, boolean z3, String str2) {
        doRequestLocationUpdates(context, z, lBSLocationListener, j, j2, z2, str, z3, str2, false);
    }

    public final void doRequestLocationUpdates(Context context, boolean z, LBSLocationListener lBSLocationListener, long j, long j2, boolean z2, String str, boolean z3, String str2, boolean z4) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "doRequestLocationUpdates, mLocationProxyInterface == null");
        } else {
            this.mLocationProxyInterface.doRequestLocationUpdates(context, z, lBSLocationListener, j, j2, z2, str, z3, str2, z4);
        }
    }

    public final void removeUpdates(Context context, LBSLocationListener lBSLocationListener) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "removeUpdates, mLocationProxyInterface == null");
        } else {
            this.mLocationProxyInterface.removeUpdates(context, lBSLocationListener);
        }
    }

    @Deprecated
    public final synchronized LBSLocation getLastKnownLocation(Context context) {
        try {
            if (this.mLocationProxyInterface == null) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "getLastKnownLocation, mLocationProxyInterface == null");
                return null;
            }
            return this.mLocationProxyInterface.getLastKnownLocation(context);
        }
    }

    @Deprecated
    public final void requestLocationUpdatesContinuous(Context context, boolean z, long j, float f, LBSLocationListener lBSLocationListener) {
        requestLocationUpdatesContinuous(context, z, j, f, lBSLocationListener, lBSLocationListener != null ? lBSLocationListener.getClass().getName() : null);
    }

    @Deprecated
    public final void requestLocationUpdatesContinuous(Context context, boolean z, long j, float f, LBSLocationListener lBSLocationListener, String str, boolean z2, String str2) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "requestLocationUpdatesContinuous, mLocationProxyInterface == null");
        } else {
            this.mLocationProxyInterface.requestLocationUpdatesContinuous(context, z, j, f, lBSLocationListener, str, z2, str2);
        }
    }

    @Deprecated
    public final void requestLocationUpdatesContinuous(Context context, boolean z, long j, float f, LBSLocationListener lBSLocationListener, String str) {
        requestLocationUpdatesContinuous(context, z, j, f, lBSLocationListener, str, false, "F");
    }

    @Deprecated
    public final void removeUpdatesContinuous(Context context, LBSLocationListener lBSLocationListener) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "removeUpdatesContinuous, mLocationProxyInterface == null");
        } else {
            this.mLocationProxyInterface.removeUpdatesContinuous(context, lBSLocationListener);
        }
    }

    public final boolean isEnable() {
        if (this.mLocationProxyInterface != null) {
            return this.mLocationProxyInterface.isEnable();
        }
        LoggerFactory.getTraceLogger().error((String) TAG, (String) "isEnable, mLocationProxyInterface == null");
        return true;
    }

    public final void setEnable(boolean z) {
        if (this.mLocationProxyInterface == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "setEnable, mLocationProxyInterface == null");
        } else {
            this.mLocationProxyInterface.setEnable(z);
        }
    }
}
