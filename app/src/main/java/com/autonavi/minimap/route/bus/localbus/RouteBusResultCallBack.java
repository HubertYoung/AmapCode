package com.autonavi.minimap.route.bus.localbus;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.RouteRequestCallBack;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import org.json.JSONException;

public class RouteBusResultCallBack extends RouteRequestCallBack {
    private Context g;

    public RouteBusResultCallBack(Context context, Callback<dvz> callback, POI poi, POI poi2, String str, long j) {
        super(callback, poi, poi2, str, j);
        this.g = context;
    }

    public void onSuccess(AosResponse aosResponse) {
        long j;
        RouteBusResultData routeBusResultData = new RouteBusResultData();
        if (this.f == 0 || this.f == -1 || this.f == -2) {
            Calendar instance = Calendar.getInstance();
            if (new MapSharePreference(SharePreferenceName.user_route_method_info).sharedPrefs().getLong("bus_time_lastset", -1) <= 0) {
                j = instance.getTimeInMillis();
            } else {
                int i = instance.get(6);
                int i2 = instance.get(1);
                long a = dwk.a();
                if (a <= 0) {
                    j = a;
                } else {
                    instance.setTimeInMillis(a);
                    int i3 = instance.get(1);
                    int i4 = instance.get(6);
                    if (i3 > i2 || i4 >= i) {
                        j = instance.getTimeInMillis();
                    } else {
                        j = Calendar.getInstance().getTimeInMillis();
                    }
                }
            }
            this.f = j;
        }
        POI poi = this.b;
        if (poi != null) {
            if (poi.getName().equals(AMapAppGlobal.getApplication().getString(R.string.route_my_position))) {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                if (latestPosition != null) {
                    poi.setPoint(latestPosition);
                }
            }
        }
        routeBusResultData.setReqTime(this.f);
        routeBusResultData.setFromPOI(poi);
        routeBusResultData.setToPOI(this.c);
        routeBusResultData.setMethod(this.e);
        dvz dvz = new dvz(routeBusResultData);
        try {
            dvz.parser(aosResponse.getResponseBodyData());
        } catch (UnsupportedEncodingException | JSONException e) {
            kf.a(e);
        }
        if (this.a != null) {
            this.a.callback(dvz);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
        }
    }
}
