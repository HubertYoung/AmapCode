package com.autonavi.minimap.route.coach.net;

import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import org.json.JSONObject;

public abstract class CoachArrivalCacheCallback<T> extends FalconAosPrepareResponseCallback<JSONObject> {
    protected Callback<T> a;
    protected String b;

    public CoachArrivalCacheCallback(Callback<T> callback, String str) {
        this.a = callback;
        this.b = str;
    }
}
