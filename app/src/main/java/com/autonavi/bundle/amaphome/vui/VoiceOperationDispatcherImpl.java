package com.autonavi.bundle.amaphome.vui;

import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoiceOperationDispatcher;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.util.ArrayList;
import org.json.JSONObject;

public class VoiceOperationDispatcherImpl implements IVoiceOperationDispatcher {
    private static boolean a(double d, double d2) {
        return -180.0d <= d && d <= 180.0d && -90.0d <= d2 && d2 <= 90.0d;
    }

    @IVoiceDispatchMethod(methodName = "getCurrentLocationInfo")
    public void getCurrentLocationInfo(int i, String str) {
        try {
            ((apu) ank.a(apu.class)).f(i);
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "setTraffic")
    public void setTraffic(int i, String str) {
        try {
            if (new JSONObject(str).optBoolean("open")) {
                ((apu) ank.a(apu.class)).a(i);
            } else {
                ((apu) ank.a(apu.class)).b(i);
            }
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "operateMap")
    public void operateMap(int i, String str) {
        try {
            int optInt = new JSONObject(str).optInt("type");
            apu apu = (apu) ank.a(apu.class);
            switch (optInt) {
                case 0:
                    apu.c(i);
                    return;
                case 1:
                    apu.d(i);
                    return;
                default:
                    a(i, (int) UCMPackageInfo.getLibFilter);
                    return;
            }
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "openFavoritePage")
    public void openFavoritePage(int i, String str) {
        a(i, 9004);
    }

    @IVoiceDispatchMethod(methodName = "setFavoritePoi")
    public void setFavoritePoi(int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("poiType");
            JSONObject optJSONObject = jSONObject.optJSONObject("favoritePoiModel");
            POI createPOI = POIFactory.createPOI();
            String optString = optJSONObject.optString(TrafficUtil.POIID);
            String optString2 = optJSONObject.optString("poiName");
            String optString3 = optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
            String optString4 = optJSONObject.optString("lat");
            String optString5 = optJSONObject.optString("entry_lon");
            String optString6 = optJSONObject.optString("entry_lat");
            POI poi = null;
            if (!TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString3) && !TextUtils.isEmpty(optString4)) {
                if (a(Double.parseDouble(optString3), Double.parseDouble(optString4))) {
                    createPOI.setId(optString);
                    createPOI.setName(optString2);
                    createPOI.getPoint().setLonLat(Double.parseDouble(optString3), Double.parseDouble(optString4));
                    if (!TextUtils.isEmpty(optString5) && !TextUtils.isEmpty(optString6)) {
                        if (!(Double.parseDouble(optString5) == -1000.0d && Double.parseDouble(optString6) == -1000.0d)) {
                            if (a(Double.parseDouble(optString5), Double.parseDouble(optString6))) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(new GeoPoint(Double.parseDouble(optString5), Double.parseDouble(optString6)));
                                createPOI.setEntranceList(arrayList);
                            }
                        }
                    }
                    poi = createPOI;
                }
            }
            if (poi == null) {
                a(i, 10001);
                return;
            }
            apu apu = (apu) ank.a(apu.class);
            switch (optInt) {
                case 0:
                    apu.a(i, poi);
                    return;
                case 1:
                    apu.b(i, poi);
                    return;
                case 2:
                    apu.c(i, poi);
                    return;
                default:
                    a(i, 10001);
                    return;
            }
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "setZoomDiff")
    public void setZoomDiff(int i, String str) {
        try {
            float optDouble = (float) new JSONObject(str).optDouble("diff");
            apu apu = (apu) ank.a(apu.class);
            if (apu != null) {
                if (optDouble >= 0.0f) {
                    apu.a(i, optDouble);
                    return;
                }
                apu.b(i, optDouble);
            }
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "moveMapView")
    public void moveMapView(int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("offsetX");
            int optInt2 = jSONObject.optInt("offsetY");
            apu apu = (apu) ank.a(apu.class);
            if (apu != null) {
                apu.a(optInt, optInt2);
                a(i, 10000);
            }
        } catch (Exception unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    private static void a(int i, int i2) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, i2, (Pair<String, Object>) null);
        }
    }
}
