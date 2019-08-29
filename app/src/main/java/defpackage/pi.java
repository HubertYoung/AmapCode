package defpackage;

import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drivecommon.model.LongDistnceSceneData;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.jni.ae.route.model.AvoidTrafficJamInfo;
import com.autonavi.jni.ae.route.model.RestrictionInfoDetail;
import com.autonavi.jni.ae.route.model.RouteIncident;
import com.autonavi.jni.ae.route.model.TipInfo;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: pi reason: default package */
/* compiled from: CarRouteResultParser */
public final class pi {
    public int a;

    public final ph a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ph phVar = new ph();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("id");
            int optInt2 = jSONObject.optInt("token");
            JSONArray optJSONArray = jSONObject.optJSONArray("routes");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                phVar.a = new long[optJSONArray.length()];
                for (int i = 0; i < optJSONArray.length(); i++) {
                    phVar.a[i] = optJSONArray.optLong(i);
                }
            }
            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("summary"));
            phVar.b = jSONObject2.optInt("pathCount");
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = jSONObject2.getJSONArray("pathArray");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
                pg pgVar = new pg();
                pgVar.a = jSONObject3.optLong("pathID");
                pgVar.b = jSONObject3.optString("naviID");
                pgVar.c = jSONObject3.optInt("length");
                pgVar.g = jSONObject3.optInt("time");
                pgVar.h = jSONObject3.optString("highlightRoadName");
                pgVar.d = jSONObject3.optInt("isOnline") == 1;
                pgVar.e = jSONObject3.optInt("isTruckPath") == 1;
                JSONObject optJSONObject = jSONObject3.optJSONObject("restriction");
                if (optJSONObject != null) {
                    pgVar.o = a(optJSONObject);
                }
                pgVar.f = jSONObject3.optInt("taxiFee");
                if (jSONObject3.has("depotCnt")) {
                    pgVar.s = jSONObject3.optInt("depotCnt");
                }
                JSONArray optJSONArray2 = jSONObject3.optJSONArray("trafficJams");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    int i3 = 0;
                    while (i3 < optJSONArray2.length()) {
                        sn snVar = new sn(b(optJSONArray2.getJSONObject(i3)));
                        try {
                            snVar.q = this.a;
                            snVar.a = optJSONArray2.getJSONObject(i3).optInt("id");
                            arrayList2.add(snVar);
                            i3++;
                        } catch (JSONException e) {
                            e = e;
                            e.printStackTrace();
                            return phVar;
                        }
                    }
                    pgVar.j = arrayList2;
                }
                JSONArray optJSONArray3 = jSONObject3.optJSONArray("incidentsOutOfPath");
                if (optJSONArray3 != null && optJSONArray3.length() > 0) {
                    ArrayList arrayList3 = new ArrayList();
                    for (int i4 = 0; i4 < optJSONArray3.length(); i4++) {
                        arrayList3.add(c(optJSONArray3.getJSONObject(i4)));
                    }
                    pgVar.l = arrayList3;
                }
                JSONArray optJSONArray4 = jSONObject3.optJSONArray("incidentsOnPath");
                if (optJSONArray4 != null && optJSONArray4.length() > 0) {
                    ArrayList arrayList4 = new ArrayList();
                    for (int i5 = 0; i5 < optJSONArray4.length(); i5++) {
                        arrayList4.add(c(optJSONArray4.getJSONObject(i5)));
                    }
                    pgVar.k = arrayList4;
                }
                JSONArray optJSONArray5 = jSONObject3.optJSONArray("forbiddens");
                if (optJSONArray5 != null && optJSONArray5.length() > 0) {
                    ArrayList arrayList5 = new ArrayList();
                    for (int i6 = 0; i6 < optJSONArray5.length(); i6++) {
                        JSONObject jSONObject4 = optJSONArray5.getJSONObject(i6);
                        pk pkVar = new pk();
                        pkVar.a = jSONObject4.optInt("id");
                        pkVar.d = jSONObject4.optInt("vehicleType");
                        pkVar.b = jSONObject4.optInt("type");
                        pkVar.c = jSONObject4.optString("timeDescription");
                        pkVar.e = jSONObject4.optString("roadNameString");
                        pkVar.f = jSONObject4.optString("nextRoadNameString");
                        arrayList5.add(pkVar);
                    }
                    pgVar.m = arrayList5;
                }
                JSONArray optJSONArray6 = jSONObject3.optJSONArray("avoidLimitReasonArray");
                if (optJSONArray6 != null && optJSONArray6.length() > 0) {
                    ArrayList arrayList6 = new ArrayList();
                    for (int i7 = 0; i7 < optJSONArray6.length(); i7++) {
                        JSONObject optJSONObject2 = optJSONArray6.optJSONObject(i7);
                        if (optJSONObject2 != null) {
                            pe d = d(optJSONObject2);
                            if (d != null) {
                                arrayList6.add(d);
                            }
                        }
                    }
                    if (!arrayList6.isEmpty()) {
                        pgVar.i = arrayList6;
                    }
                }
                JSONArray optJSONArray7 = jSONObject3.optJSONArray("roadFacilityArray");
                if (optJSONArray7 != null && optJSONArray7.length() > 0) {
                    ArrayList arrayList7 = new ArrayList();
                    for (int i8 = 0; i8 < optJSONArray7.length(); i8++) {
                        JSONObject optJSONObject3 = optJSONArray7.optJSONObject(i8);
                        if (optJSONObject3 != null) {
                            pp ppVar = new pp();
                            ppVar.a = optJSONObject3.optInt("type");
                            ppVar.b = optJSONObject3.optInt("id");
                            ppVar.c = optJSONObject3.optString("nameString");
                            arrayList7.add(ppVar);
                        }
                    }
                    if (!arrayList7.isEmpty()) {
                        pgVar.n = arrayList7;
                    }
                }
                JSONObject optJSONObject4 = jSONObject3.optJSONObject("tipInfo");
                if (optJSONObject4 != null) {
                    TipInfo tipInfo = new TipInfo();
                    tipInfo.tipInfo = optJSONObject4.optString("tip");
                    tipInfo.type = optJSONObject4.optInt("type");
                    pgVar.p = tipInfo;
                }
                LongDistnceSceneData longDistnceSceneData = new LongDistnceSceneData();
                JSONArray optJSONArray8 = jSONObject3.optJSONArray("cityCodes");
                if (optJSONArray8 != null && optJSONArray8.length() > 0) {
                    int[] iArr = new int[optJSONArray8.length()];
                    for (int i9 = 0; i9 < optJSONArray8.length(); i9++) {
                        iArr[i9] = optJSONArray8.getInt(i9);
                    }
                    longDistnceSceneData.a(iArr);
                }
                pgVar.q = longDistnceSceneData;
                int optInt3 = jSONObject3.optInt("isHolidayFree");
                boolean z = true;
                if (optInt3 != 1) {
                    z = false;
                }
                pgVar.r = z;
                arrayList.add(pgVar);
            }
            phVar.f = arrayList;
            phVar.c = (long) optInt;
            phVar.d = optInt2;
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return phVar;
        }
        return phVar;
    }

    private static sq a(JSONObject jSONObject) throws JSONException {
        sq sqVar = new sq();
        sqVar.a = jSONObject.optString("title");
        sqVar.f = jSONObject.optString(ModulePoi.TIPS);
        sqVar.d = jSONObject.optInt("cityCode");
        sqVar.e = jSONObject.optInt("titleType");
        JSONArray optJSONArray = jSONObject.optJSONArray("infoArray");
        RestrictionInfoDetail[] restrictionInfoDetailArr = new RestrictionInfoDetail[optJSONArray.length()];
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
            RestrictionInfoDetail restrictionInfoDetail = new RestrictionInfoDetail();
            restrictionInfoDetail.low = (short) jSONObject2.optInt("low");
            restrictionInfoDetail.high = (short) jSONObject2.optInt("high");
            restrictionInfoDetail.hitTime = (short) jSONObject2.optInt("hitTime");
            restrictionInfoDetail.headX = jSONObject2.optDouble("headX");
            restrictionInfoDetail.headY = jSONObject2.optDouble("headY");
            restrictionInfoDetail.tailX = jSONObject2.optDouble("tailX");
            restrictionInfoDetail.tailY = jSONObject2.optDouble("tailY");
            if (jSONObject2.optInt("valid") == 1) {
                restrictionInfoDetail.valid = true;
            } else {
                restrictionInfoDetail.valid = false;
            }
            restrictionInfoDetailArr[i] = restrictionInfoDetail;
        }
        sqVar.g = restrictionInfoDetailArr;
        JSONArray optJSONArray2 = jSONObject.optJSONArray("tailNumsArray");
        byte[] bArr = new byte[optJSONArray2.length()];
        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
            bArr[i2] = (byte) optJSONArray2.optInt(i2);
        }
        sqVar.h = bArr;
        return sqVar;
    }

    private static AvoidTrafficJamInfo b(JSONObject jSONObject) {
        AvoidTrafficJamInfo avoidTrafficJamInfo = new AvoidTrafficJamInfo();
        JSONObject optJSONObject = jSONObject.optJSONObject("pos");
        avoidTrafficJamInfo.lon = (float) optJSONObject.optDouble(DictionaryKeys.CTRLXY_X);
        avoidTrafficJamInfo.lat = (float) optJSONObject.optDouble(DictionaryKeys.CTRLXY_Y);
        avoidTrafficJamInfo.roadName = jSONObject.optString("roadName");
        avoidTrafficJamInfo.length = jSONObject.optInt("length");
        avoidTrafficJamInfo.state = jSONObject.optInt("status");
        avoidTrafficJamInfo.travelTime = jSONObject.optInt("travelTime");
        avoidTrafficJamInfo.averageSpeed = jSONObject.optInt("averageSpeed");
        avoidTrafficJamInfo.segCnt = jSONObject.optInt("segCnt");
        JSONArray optJSONArray = jSONObject.optJSONArray("coorList");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            int[] iArr = new int[optJSONArray.length()];
            for (int i = 0; i < optJSONArray.length(); i++) {
                iArr[i] = optJSONArray.optInt(i);
            }
            avoidTrafficJamInfo.coorList = iArr;
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("segStartCoorIndexs");
        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
            int[] iArr2 = new int[optJSONArray2.length()];
            for (int i2 = 0; i2 < iArr2.length; i2++) {
                iArr2[i2] = optJSONArray2.optInt(i2);
            }
            avoidTrafficJamInfo.segStartCoorIndexs = iArr2;
        }
        JSONArray optJSONArray3 = jSONObject.optJSONArray("statusList");
        if (optJSONArray3 != null && optJSONArray3.length() > 0) {
            int[] iArr3 = new int[optJSONArray3.length()];
            for (int i3 = 0; i3 < iArr3.length; i3++) {
                iArr3[i3] = optJSONArray3.optInt(i3);
            }
            avoidTrafficJamInfo.statusList = iArr3;
        }
        return avoidTrafficJamInfo;
    }

    private static RouteIncident c(JSONObject jSONObject) {
        RouteIncident routeIncident = new RouteIncident();
        JSONObject optJSONObject = jSONObject.optJSONObject("pos");
        if (optJSONObject != null) {
            routeIncident.longitude = (float) optJSONObject.optDouble(DictionaryKeys.CTRLXY_X);
            routeIncident.latitude = (float) optJSONObject.optDouble(DictionaryKeys.CTRLXY_Y);
        }
        routeIncident.title = jSONObject.optString("title");
        routeIncident.type = jSONObject.optInt("type");
        routeIncident.priority = jSONObject.optInt("priority");
        routeIncident.credibillity = (byte) jSONObject.optInt("credibillity");
        routeIncident.source = (byte) jSONObject.optInt("source");
        routeIncident.eventType = jSONObject.optInt("eventType");
        routeIncident.layerId = jSONObject.optInt("layerId");
        routeIncident.layerTag = jSONObject.optInt("layerTag");
        routeIncident.segIndex = jSONObject.optInt("segIndex");
        routeIncident.linkIndex = jSONObject.optInt("linkIndex");
        routeIncident.tipsType = (byte) jSONObject.optInt("titleType");
        routeIncident.lane = (short) jSONObject.optInt("lane");
        routeIncident.roadClass = (byte) jSONObject.optInt("roadClass");
        routeIncident.id = jSONObject.optInt("id");
        return routeIncident;
    }

    private static pe d(JSONObject jSONObject) {
        pe peVar = new pe();
        JSONArray optJSONArray = jSONObject.optJSONArray("roadNameArray");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            peVar.a = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                String optString = optJSONArray.optString(i);
                if (!TextUtils.isEmpty(optString)) {
                    peVar.a.add(optString);
                }
            }
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("limitPointArray");
        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
            peVar.b = new ArrayList();
            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                JSONObject optJSONObject = optJSONArray2.optJSONObject(i2);
                if (optJSONObject != null) {
                    a aVar = new a();
                    aVar.a = optJSONObject.optInt("type");
                    aVar.b = optJSONObject.optInt("id");
                    aVar.c = optJSONObject.optString("roadName", "");
                    aVar.d = optJSONObject.optString("inRoadName");
                    aVar.e = optJSONObject.optString("outRoadName");
                    aVar.f = optJSONObject.optString("timeDescription");
                    aVar.g = optJSONObject.optInt("vehicleType");
                    peVar.b.add(aVar);
                }
            }
        }
        if (peVar.b == null || peVar.b.isEmpty()) {
            return null;
        }
        return peVar;
    }
}
