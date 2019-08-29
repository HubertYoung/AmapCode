package com.autonavi.miniapp.plugin.lbs;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.geo.GeoRequestHolder;
import com.autonavi.minimap.geo.param.ReverseCodeRequest;
import com.autonavi.minimap.map.DPoint;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class MiniAppReverseGeocodeUtil {
    public static void getMiniAppReverseGeocodeResult(GeoPoint geoPoint, Callback<MiniappReverseGeocodeResponser> callback) {
        getMiniAppReverseGeocodeResult(geoPoint, 5, callback);
    }

    private static void getMiniAppReverseGeocodeResult(GeoPoint geoPoint, int i, final Callback<MiniappReverseGeocodeResponser> callback) {
        ReverseCodeRequest reverseCodeRequest = new ReverseCodeRequest();
        if (geoPoint != null) {
            DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
            reverseCodeRequest.c = String.valueOf(a.y);
            reverseCodeRequest.b = String.valueOf(a.x);
            reverseCodeRequest.g = i;
            reverseCodeRequest.d = true;
        }
        GeoRequestHolder.getInstance().sendReverseCode(reverseCodeRequest, new AosResponseCallback<AosByteResponse>() {
            public final void onSuccess(AosByteResponse aosByteResponse) {
                if (aosByteResponse != null && aosByteResponse.getResult() != null) {
                    final MiniappReverseGeocodeResponser miniappReverseGeocodeResponser = new MiniappReverseGeocodeResponser();
                    try {
                        miniappReverseGeocodeResponser.parser((byte[]) aosByteResponse.getResult());
                        aho.a(new Runnable() {
                            public void run() {
                                if (callback != null) {
                                    callback.callback(miniappReverseGeocodeResponser);
                                }
                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        kf.a((Throwable) e);
                    } catch (JSONException e2) {
                        kf.a((Throwable) e2);
                    }
                }
            }

            public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                if (callback != null) {
                    callback.error(aosResponseException, false);
                }
            }
        });
    }

    private static MiniappReverseGeocodeResponser GPoiResult2ReverseGeocodeMiniAppResponser(GPoiResult gPoiResult) {
        MiniappReverseGeocodeResponser miniappReverseGeocodeResponser = new MiniappReverseGeocodeResponser();
        if (!(gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0)) {
            awe awe = (awe) ank.a(awe.class);
            if (awe != null) {
                miniappReverseGeocodeResponser.setPoiList(awe.a(gPoiResult));
                miniappReverseGeocodeResponser.errorCode = 1;
            }
        }
        return miniappReverseGeocodeResponser;
    }
}
