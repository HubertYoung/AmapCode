package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dui reason: default package */
/* compiled from: IBusLineDataUtilImpl */
public final class dui implements asz {

    /* renamed from: dui$a */
    /* compiled from: IBusLineDataUtilImpl */
    static class a {
        static dui a = new dui();
    }

    public final void a(JSONObject jSONObject, Object obj) {
        if (obj != null && (obj instanceof Bus)) {
            Bus bus = (Bus) obj;
            agd.a(jSONObject, (String) "key_name", bus.key_name);
            agd.a(jSONObject, (String) "startName", bus.startName);
            agd.a(jSONObject, (String) "endName", bus.endName);
            agd.a(jSONObject, (String) "areacode", bus.areacode);
            agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, bus.startTime);
            agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, bus.endTime);
            agd.a(jSONObject, (String) "busline_id", bus.id);
            agd.a(jSONObject, (String) "basePrice", bus.basic_price);
            agd.a(jSONObject, (String) "name", bus.name);
        }
    }

    public final Object a(JSONObject jSONObject) {
        Bus bus = new Bus();
        bus.name = agd.e(jSONObject, "name");
        bus.key_name = agd.e(jSONObject, "key_name");
        bus.startName = agd.e(jSONObject, "startName");
        bus.endName = agd.e(jSONObject, "endName");
        bus.areacode = agd.e(jSONObject, "areacode");
        bus.startTime = agd.a(jSONObject, "startTime");
        bus.endTime = agd.a(jSONObject, AppInitCallback.SP_KEY_endTime);
        String e = agd.e(jSONObject, "busline_id");
        if (TextUtils.isEmpty(e)) {
            e = agd.e(jSONObject, "bus_line_id");
        }
        bus.id = e;
        bus.basic_price = agd.e(jSONObject, "basePrice");
        return bus;
    }

    public final Object b(JSONObject jSONObject) {
        Bus bus = new Bus();
        bus.name = agd.e(jSONObject, "name");
        bus.key_name = agd.e(jSONObject, "key_name");
        bus.startName = agd.e(jSONObject, "startName");
        bus.endName = agd.e(jSONObject, "endName");
        bus.startTime = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME);
        bus.stationStartTime = agd.a(jSONObject, "stationStartTime");
        bus.stationEndTime = agd.a(jSONObject, "stationEndTime");
        bus.status = agd.a(jSONObject, "status");
        bus.areacode = agd.e(jSONObject, "areacode");
        bus.endTime = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME);
        bus.length = agd.a(jSONObject, "length");
        bus.id = agd.e(jSONObject, "busline_id");
        bus.color = agd.e(jSONObject, "color");
        bus.returnId = agd.e(jSONObject, "return_id");
        bus.point_num = agd.a(jSONObject, "point_num");
        if (jSONObject.has("irregular_time")) {
            bus.irregulartime = a(axr.e(jSONObject, "irregular_time"));
        }
        bus.basic_price = agd.e(jSONObject, "basePrice");
        bus.total_price = agd.e(jSONObject, "total_price");
        bus.point_num = Math.max(bus.point_num, 0);
        if (jSONObject.has("coordS")) {
            JSONArray f = agd.f(jSONObject, "coordS");
            if (f != null) {
                int length = f.length();
                if (bus.point_num != length) {
                    bus.point_num = length;
                }
                bus.coordX = new int[length];
                bus.coordY = new int[length];
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject jSONObject2 = f.getJSONObject(i);
                        bus.coordX[i] = agd.a(jSONObject2, DictionaryKeys.CTRLXY_X);
                        bus.coordY[i] = agd.a(jSONObject2, DictionaryKeys.CTRLXY_Y);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        bus.station_num = agd.a(jSONObject, "station_num");
        if (jSONObject.has("stations")) {
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("stations");
                int length2 = jSONArray.length();
                bus.station_num = length2;
                bus.stations = new String[length2];
                bus.stationX = new int[length2];
                bus.stationY = new int[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
                    bus.stations[i2] = agd.e(jSONObject3, "name");
                    bus.stationX[i2] = agd.a(jSONObject3, DictionaryKeys.CTRLXY_X);
                    bus.stationY[i2] = agd.a(jSONObject3, DictionaryKeys.CTRLXY_Y);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        JSONArray f2 = agd.f(jSONObject, "stationIds");
        if (f2 != null) {
            String[] strArr = new String[f2.length()];
            for (int i3 = 0; i3 < f2.length(); i3++) {
                try {
                    strArr[i3] = f2.getJSONObject(i3).getString("stationId");
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
            bus.stationIds = strArr;
        }
        JSONArray f3 = agd.f(jSONObject, "stationstatus");
        if (f3 != null) {
            int[] iArr = new int[f3.length()];
            for (int i4 = 0; i4 < f3.length(); i4++) {
                try {
                    iArr[i4] = f3.getJSONObject(i4).getInt("stationstatu");
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
            }
            bus.stationstatus = iArr;
        }
        JSONArray f4 = agd.f(jSONObject, "stationpoiid1s");
        if (f4 != null) {
            String[] strArr2 = new String[f4.length()];
            for (int i5 = 0; i5 < f4.length(); i5++) {
                try {
                    strArr2[i5] = f4.getJSONObject(i5).getString("stationpoiid1");
                } catch (JSONException e5) {
                    e5.printStackTrace();
                }
            }
            bus.stationpoiid2 = strArr2;
        }
        return bus;
    }

    public final String a(Object obj) throws JSONException {
        if (obj == null) {
            return null;
        }
        Bus bus = (Bus) obj;
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "name", bus.name);
        agd.a(jSONObject, (String) "key_name", bus.key_name);
        agd.a(jSONObject, (String) "startName", bus.startName);
        agd.a(jSONObject, (String) "endName", bus.endName);
        agd.a(jSONObject, (String) "areacode", bus.areacode);
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, bus.startTime);
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, bus.endTime);
        agd.a(jSONObject, (String) "busline_id", bus.id);
        agd.a(jSONObject, (String) "color", bus.color);
        agd.a(jSONObject, (String) "return_id", bus.returnId);
        agd.a(jSONObject, (String) "status", bus.status);
        agd.a(jSONObject, (String) "length", bus.length);
        if (bus.coordX != null) {
            bus.point_num = bus.coordX.length;
        }
        agd.a(jSONObject, (String) "point_num", bus.point_num);
        agd.a(jSONObject, (String) "basePrice", bus.basic_price);
        agd.a(jSONObject, (String) "total_price", bus.total_price);
        if (bus.irregulartime != null) {
            JSONObject jSONObject2 = new JSONObject();
            agd.a(jSONObject2, (String) "normalday", bus.irregulartime.normalday);
            agd.a(jSONObject2, (String) "workday", bus.irregulartime.workday);
            agd.a(jSONObject2, (String) "holiday", bus.irregulartime.holiday);
            jSONObject.put("irregular_time", jSONObject2);
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < bus.point_num; i++) {
            JSONObject jSONObject3 = new JSONObject();
            agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_X, bus.coordX[i]);
            agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_Y, bus.coordY[i]);
            jSONArray.put(jSONObject3);
        }
        jSONObject.put("coordS", jSONArray);
        if (bus.stations != null) {
            bus.station_num = bus.stations.length;
        }
        agd.a(jSONObject, (String) "station_num", bus.station_num);
        JSONArray jSONArray2 = new JSONArray();
        for (int i2 = 0; i2 < bus.station_num; i2++) {
            JSONObject jSONObject4 = new JSONObject();
            agd.a(jSONObject4, (String) "name", bus.stations[i2]);
            agd.a(jSONObject4, (String) DictionaryKeys.CTRLXY_X, bus.stationX[i2]);
            agd.a(jSONObject4, (String) DictionaryKeys.CTRLXY_Y, bus.stationY[i2]);
            jSONArray2.put(jSONObject4);
        }
        jSONObject.put("stations", jSONArray2);
        String[] strArr = bus.stationIds;
        JSONArray jSONArray3 = new JSONArray();
        if (strArr != null) {
            for (String put : strArr) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("stationId", put);
                jSONArray3.put(jSONObject5);
            }
        }
        jSONObject.put("stationIds", jSONArray3);
        int[] iArr = bus.stationstatus;
        JSONArray jSONArray4 = new JSONArray();
        if (iArr != null) {
            for (int put2 : iArr) {
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("stationstatu", put2);
                jSONArray4.put(jSONObject6);
            }
        }
        jSONObject.put("stationstatus", jSONArray4);
        return jSONObject.toString();
    }

    public final String b(Object obj) {
        if (obj == null) {
            return null;
        }
        Bus bus = (Bus) obj;
        StringBuilder sb = new StringBuilder();
        sb.append(bus.name);
        sb.append("+");
        sb.append(bus.startName);
        sb.append("+");
        sb.append(bus.endName);
        return sb.toString();
    }

    public final String a(Object obj, int i) {
        if (obj == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(((Bus) obj).id);
        sb.append("+");
        sb.append(i);
        return sb.toString();
    }

    public final void a(cor cor) {
        Uri uri;
        Bus bus = (Bus) cor.d();
        if (bus != null && !TextUtils.isEmpty(bus.id)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (bus == null || bus.id == null) {
                uri = null;
            } else {
                StringBuilder sb = new StringBuilder("amapuri://realtimeBus/detail?param={");
                if (!TextUtils.isEmpty(bus.id)) {
                    sb.append("lineId:");
                    sb.append(bus.id);
                }
                sb.append(",from:favorites}");
                uri = Uri.parse(sb.toString());
            }
            intent.setData(uri);
            esf.a().a(new ese(intent));
        }
    }

    private static IrregularTime a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            IrregularTime irregularTime = new IrregularTime();
            irregularTime.normalday = axr.e(jSONObject, "normalday");
            irregularTime.workday = axr.e(jSONObject, "workday");
            irregularTime.holiday = axr.e(jSONObject, "holiday");
            return irregularTime;
        } catch (JSONException unused) {
            return null;
        }
    }
}
