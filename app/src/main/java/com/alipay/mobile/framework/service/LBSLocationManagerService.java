package com.alipay.mobile.framework.service;

import android.os.Bundle;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.concurrent.TimeUnit;

public abstract class LBSLocationManagerService extends ExternalService {
    public static final int ALIPAY_ERRCODE_DISABLE_NOTLOGIN = 80;
    @Deprecated
    public static final int AREALEVEL_BASIC_GEOFENCE = 12;
    public static final int AREALEVEL_CITY = 4;
    public static final int AREALEVEL_CONTINENT = 1;
    public static final int AREALEVEL_COUNTRY = 2;
    @Deprecated
    public static final int AREALEVEL_DEFAULT = 0;
    public static final int AREALEVEL_DISTRICT = 5;
    public static final int AREALEVEL_PROVINCE = 3;
    public static final int AREALEVEL_STREET = 7;
    public static final int AREALEVEL_STREET_WITH_POIS = 8;
    public static final int AREALEVEL_TOWN = 6;
    public static final String EXTRA_INFO_LOCATION_LATEST_KEY = "EXTRA_INFO_LOCATION_LATEST";
    public static long LAST_LOCATION_CACHETIME = TimeUnit.DAYS.toMillis(30);
    public static final int RESULT_WRAPPER_ERRORCODE = 81;

    @Deprecated
    public abstract LBSLocation getLastKnownLocation();

    public abstract LBSLocation getLastKnownLocation(LBSLocationRequest lBSLocationRequest);

    public abstract void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnLBSLocationListener onLBSLocationListener);

    public abstract void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnLBSLocationListener onLBSLocationListener, OnReGeocodeListener onReGeocodeListener);

    public abstract void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnReGeocodeListener onReGeocodeListener);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public abstract void stopLocation(OnLBSLocationListener onLBSLocationListener);
}
