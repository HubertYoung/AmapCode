package com.amap.bundle.drive.voice.traffic;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.VoiceSearchRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import org.json.JSONArray;
import org.json.JSONObject;

public class VoiceTrafficManger {
    private static volatile VoiceTrafficManger a;

    public static class MyNetRequestCallback implements AosResponseCallbackOnUi<AosByteResponse> {
        private sc a;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                this.a.a((int) SDKFactory.getCoreType);
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult()));
                if (jSONObject.optInt("code") != 1) {
                    this.a.a((int) SDKFactory.getCoreType);
                    return;
                }
                JSONObject optJSONObject = jSONObject.optJSONObject("voice_result");
                String optString = optJSONObject.optString("voice_text");
                JSONObject optJSONObject2 = optJSONObject.optJSONObject("traffic");
                if (optJSONObject2 == null) {
                    this.a.a((int) UCMPackageInfo.expectDirFile1F);
                    return;
                }
                optJSONObject2.optDouble("longitude");
                optJSONObject2.optDouble("latitude");
                String[] strArr = new String[0];
                JSONArray optJSONArray = optJSONObject2.optJSONArray("descriptions");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    strArr = new String[optJSONArray.length()];
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        strArr[i] = optJSONArray.optString(i);
                    }
                }
                if (strArr.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < strArr.length - 1; i2++) {
                        sb.append(strArr[i2]);
                        sb.append("\n");
                    }
                    sb.append(strArr[strArr.length - 1]);
                    this.a.a(sb.toString());
                } else if (TextUtils.isEmpty(optString)) {
                    this.a.a((String) "您所查询的路线没有路况信息，请参考周边路况");
                } else {
                    this.a.a(optString);
                }
            } catch (Exception unused) {
                this.a.a(10008);
            }
        }

        public MyNetRequestCallback(sc scVar) {
            this.a = scVar;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            this.a.a(10008);
        }
    }

    private VoiceTrafficManger() {
    }

    public static VoiceTrafficManger a() {
        if (a == null) {
            synchronized (VoiceTrafficManger.class) {
                if (a == null) {
                    a = new VoiceTrafficManger();
                }
            }
        }
        return a;
    }

    public static void a(String str, sc scVar) {
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            dfm dfm = (dfm) ank.a(dfm.class);
            String str2 = null;
            String c = dfm != null ? dfm.c("RouteProtocol") : null;
            VoiceSearchRequest voiceSearchRequest = new VoiceSearchRequest();
            if (!TextUtils.isEmpty(c)) {
                voiceSearchRequest.C = c;
            }
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (iVoicePackageManager != null) {
                voiceSearchRequest.N = iVoicePackageManager.getCurrentTtsSubName();
            }
            voiceSearchRequest.l = AppManager.getInstance().getUserLocInfo();
            voiceSearchRequest.m = Integer.toString(LocationInstrument.getInstance().getLatestPosition().getAdCode());
            voiceSearchRequest.b = str;
            if (elc.l != null) {
                str2 = cfe.a(elc.l);
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = cfe.a(mapView.H());
            }
            if (!TextUtils.isEmpty(str2)) {
                voiceSearchRequest.h = str2;
            }
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (!(glGeoPoint2GeoPoint == null || latestPosition == null || glGeoPoint2GeoPoint.getAdCode() == latestPosition.getAdCode())) {
                voiceSearchRequest.K = String.valueOf(glGeoPoint2GeoPoint.getLongitude());
                voiceSearchRequest.L = String.valueOf(glGeoPoint2GeoPoint.getLatitude());
            }
            PoiRequestHolder.getInstance().sendVoiceSearch(voiceSearchRequest, new MyNetRequestCallback(scVar));
        }
    }
}
