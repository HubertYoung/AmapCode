package com.autonavi.minimap.route.common;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

public abstract class RouteRequestCallBack<T> implements AosResponseCallback<AosByteResponse> {
    public Callback<T> a;
    protected POI b;
    protected POI c;
    protected ArrayList<POI> d = null;
    protected String e;
    protected long f;

    public RouteRequestCallBack(Callback<T> callback, POI poi, POI poi2, String str, long j) {
        this.a = callback;
        this.b = poi;
        this.c = poi2;
        this.e = str;
        this.f = j;
    }
}
