package com.autonavi.minimap.offline.JsRequest;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsAlongWayRouteCityHelper;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import org.json.JSONException;
import org.json.JSONObject;

public class AlongWayRouteCityRequest extends UseCase<QueryCityParam, QueryResponse, String> {
    private static final String ERROR_CODE_UNKNOWN = "2";
    private static final String SUCCESS_CODE = "1";

    public static final class QueryCityParam implements RequestValues {
        private int mEndAdcode;
        private int mStartAdcode;

        public QueryCityParam(int i, int i2) {
            this.mStartAdcode = i;
            this.mEndAdcode = i2;
        }

        public final int getStartAdcode() {
            return this.mStartAdcode;
        }

        public final int getEndAdcode() {
            return this.mEndAdcode;
        }
    }

    public static final class QueryResponse implements ResponseValue {
        private String alongRouteCities;

        public final String getAlongRouteCities() {
            return this.alongRouteCities;
        }

        public final void setAlongRouteCities(String str) {
            this.alongRouteCities = str;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(QueryCityParam queryCityParam) {
        dhz dhz = (dhz) ank.a(dhz.class);
        if (dhz == null) {
            onError();
            return;
        }
        try {
            dhz.a(createRequestParam(queryCityParam.getStartAdcode(), queryCityParam.getEndAdcode()), (Callback<int[]>) new Callback<int[]>() {
                public void callback(int[] iArr) {
                    if (iArr == null || iArr.length == 0) {
                        AlongWayRouteCityRequest.this.onError();
                        return;
                    }
                    try {
                        JSONObject convertSuccessJO = JsAlongWayRouteCityHelper.convertSuccessJO(JsNativeFacade.getInstance().getCityInfos(iArr), "1");
                        if (convertSuccessJO == null) {
                            AlongWayRouteCityRequest.this.onError();
                            return;
                        }
                        QueryResponse queryResponse = new QueryResponse();
                        queryResponse.setAlongRouteCities(convertSuccessJO.toString());
                        AlongWayRouteCityRequest.this.onSuccess(queryResponse);
                    } catch (JSONException unused) {
                        AlongWayRouteCityRequest.this.onError();
                    }
                }

                public void error(Throwable th, boolean z) {
                    AlongWayRouteCityRequest.this.onError();
                }
            });
        } catch (Exception unused) {
            onError();
        }
    }

    /* access modifiers changed from: private */
    public void onError() {
        try {
            getUseCaseCallback().onError(JsAlongWayRouteCityHelper.convertErrorJoStr("2"));
        } catch (JSONException unused) {
            AMapLog.w("AlongWayRouteCityRequest", "JSONException !!!");
        }
    }

    /* access modifiers changed from: private */
    public void onSuccess(QueryResponse queryResponse) {
        getUseCaseCallback().onSuccess(queryResponse);
    }

    private dhx createRequestParam(int i, int i2) throws Exception {
        lj a = li.a().a((int) ((long) i));
        lj a2 = li.a().a((int) ((long) i2));
        dhx dhx = new dhx(POIFactory.createPOI(a.a, new GeoPoint(a.f, a.g)), POIFactory.createPOI(a2.a, new GeoPoint(a2.f, a2.g)));
        dhx.d = false;
        return dhx;
    }
}
