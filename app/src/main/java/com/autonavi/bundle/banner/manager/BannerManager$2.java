package com.autonavi.bundle.banner.manager;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.common.Callback;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerManager$2 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ Callback a;

    public BannerManager$2(Callback callback) {
        this.a = callback;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            a(this.a, new Throwable("server response is null!"), true);
            return;
        }
        try {
            JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
            new asi();
            final BannerResult a2 = asi.a(aosByteResponseToJSONObject);
            if (this.a != null) {
                UiExecutor.post(new Runnable() {
                    public final void run() {
                        BannerManager$2.this.a.callback(a2);
                    }
                });
            }
        } catch (JSONException e) {
            a(this.a, e, true);
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(this.a, aosResponseException, false);
    }

    private void a(final Callback<BannerResult> callback, final Throwable th, final boolean z) {
        if (callback != null) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    callback.error(th, z);
                }
            });
        }
    }
}
