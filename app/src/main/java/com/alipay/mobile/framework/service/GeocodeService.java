package com.alipay.mobile.framework.service;

import android.content.Context;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.map.exception.GeocodeException;
import com.alipay.mobile.map.model.LatLonPoint;
import com.alipay.mobile.map.model.SearchPoiRequest;
import com.alipay.mobile.map.model.geocode.GeocodeResult;
import com.alipay.mobile.map.model.geocode.ReGeocodeResult;
import java.util.List;

public abstract class GeocodeService extends ExternalService {
    public static final int AREALEVEL_BASIC_GEOFENCE = 12;
    public static final int AREALEVEL_CITY = 4;
    public static final int AREALEVEL_CONTINENT = 1;
    public static final int AREALEVEL_COUNTRY = 2;
    public static final int AREALEVEL_DEFAULT = 0;
    public static final int AREALEVEL_DISTRICT = 5;
    public static final int AREALEVEL_PROVINCE = 3;
    public static final int AREALEVEL_TOWN = 6;

    @Deprecated
    public abstract void cityKeywordSearchPoi(Context context, String str, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener);

    public abstract List<GeocodeResult> geocode(String str) throws GeocodeException;

    public abstract List<ReGeocodeResult> reGeocode(LatLonPoint latLonPoint, float f) throws GeocodeException;

    public abstract List<ReGeocodeResult> reGeocode(LatLonPoint latLonPoint, float f, String str) throws GeocodeException;

    public abstract ReGeocodeResult reverse(LatLonPoint latLonPoint, float f, String str) throws GeocodeException;

    public abstract ReGeocodeResult reverse(LatLonPoint latLonPoint, float f, String str, int i) throws GeocodeException;

    public abstract void reverse(LatLonPoint latLonPoint, float f, String str, int i, OnReGeocodeListener onReGeocodeListener) throws GeocodeException;

    public abstract void reverse(LatLonPoint latLonPoint, float f, String str, OnReGeocodeListener onReGeocodeListener) throws GeocodeException;

    public abstract void searchpoi(Context context, OnPoiSearchListener onPoiSearchListener, SearchPoiRequest searchPoiRequest);

    public abstract void searchpoi(Context context, LatLonPoint latLonPoint, String str, int i, int i2, OnPoiSearchListener onPoiSearchListener);

    public abstract void searchpoi(Context context, LatLonPoint latLonPoint, String str, int i, int i2, OnPoiSearchListener onPoiSearchListener, int i3);

    public abstract void searchpoi(Context context, String str, OnPoiSearchListener onPoiSearchListener, SearchPoiRequest searchPoiRequest);

    public abstract void searchpoi(Context context, String str, LatLonPoint latLonPoint, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener);

    public abstract void searchpoi(Context context, String str, LatLonPoint latLonPoint, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener, int i3);
}
