package com.autonavi.map.mapinterface;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.network.request.param.builder.URLBuilder.ResultProperty;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepImplementations
@KeepPublicClassMembers
@KeepName
public interface IMapRequestManager {

    @ResultProperty(parser = b.class)
    public static class a {
        public String a;
        public HashMap<String, ArrayList<POI>> b = new HashMap<>();
        public HashMap<String, String> c = new HashMap<>();
    }

    public static class b {
        public static a a(JSONObject jSONObject) {
            int i;
            a aVar = new a();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("codepoint");
                if (jSONObject2.has("category")) {
                    aVar.a = jSONObject2.getString("category");
                }
                if (jSONObject2.has(Performance.KEY_LOG_HEADER)) {
                    JSONObject jSONObject3 = new JSONObject(new JSONObject(new JSONObject(jSONObject2.optString(Performance.KEY_LOG_HEADER)).optString("resource")).optString("pic_dict"));
                    Iterator<String> keys = jSONObject3.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        aVar.c.put(next, jSONObject3.optString(next));
                    }
                }
                if (jSONObject2.has("blocks")) {
                    JSONArray jSONArray = jSONObject2.getJSONArray("blocks");
                    int i2 = 0;
                    while (i2 < jSONArray.length()) {
                        JSONObject jSONObject4 = jSONArray.getJSONObject(i2);
                        String str = "";
                        if (jSONObject4.has("name")) {
                            str = jSONObject4.getString("name");
                        }
                        if (jSONObject4.has("poilist")) {
                            JSONArray jSONArray2 = jSONObject4.getJSONArray("poilist");
                            ArrayList arrayList = new ArrayList();
                            double d = 0.0d;
                            double d2 = 0.0d;
                            int i3 = 0;
                            while (i3 < jSONArray2.length()) {
                                JSONObject jSONObject5 = jSONArray2.getJSONObject(i3);
                                String e = agd.e(jSONObject5, "longitude");
                                if (!TextUtils.isEmpty(e)) {
                                    d2 = Double.parseDouble(e);
                                }
                                String e2 = agd.e(jSONObject5, "latitude");
                                if (!TextUtils.isEmpty(e2)) {
                                    d = Double.parseDouble(e2);
                                }
                                POI createPOI = POIFactory.createPOI(agd.e(jSONObject5, "name"), new GeoPoint(d2, d));
                                if (jSONObject5.has("id")) {
                                    createPOI.setId(jSONObject5.getString("id"));
                                }
                                if (jSONObject5.has("name")) {
                                    createPOI.setName(jSONObject5.getString("name"));
                                }
                                if (jSONObject5.has("end_poi_extension")) {
                                    createPOI.setEndPoiExtension(jSONObject5.optString("end_poi_extension"));
                                }
                                if (jSONObject5.has(H5Param.LONG_TRANSPARENT)) {
                                    createPOI.setTransparent(jSONObject5.optString(H5Param.LONG_TRANSPARENT));
                                }
                                int i4 = i2;
                                double d3 = d2;
                                double optDouble = jSONObject5.optDouble("longitude", -1.0d);
                                double d4 = d;
                                double optDouble2 = jSONObject5.optDouble("latitude", -1.0d);
                                if (!(optDouble == -1.0d || optDouble2 == -1.0d)) {
                                    createPOI.getPoint().setLonLat(optDouble, optDouble2);
                                }
                                if (jSONObject5.has("brand_icon") && !jSONObject5.getString("brand_icon").trim().equals("")) {
                                    ((ISearchPoiData) createPOI.as(ISearchPoiData.class)).setMarkerBGOnline(aVar.c.get(jSONObject5.getString("brand_icon")));
                                }
                                arrayList.add(createPOI);
                                i3++;
                                i2 = i4;
                                d2 = d3;
                                d = d4;
                            }
                            i = i2;
                            aVar.b.put(str, arrayList);
                        } else {
                            i = i2;
                        }
                        i2 = i + 1;
                    }
                }
                return aVar;
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    public static class c {
    }

    @ResultProperty(parser = c.class)
    public static class d {
        public String a;
        public String b;
        public String c;
        public int d;
        public String e;
        public int f;
        public String g;
        public int h;
    }

    AosRequest authDevice(String str, String str2, int i, int i2, Callback<d> callback);

    AosRequest getReverseGeocodeResult(GeoPoint geoPoint, Callback<awg> callback);

    AosRequest idPoi(String str, long j, int i, Callback<List<POI>> callback);

    AosRequest idPoi(String str, GeoPoint geoPoint, long j, int i, SuperId superId, Callback<List<POI>> callback);

    AosRequest poiMark(String str, int i, String str2, String str3, String str4, Callback<a> callback);

    AosRequest xyPoi(String str, GeoPoint geoPoint, Callback<List<POI>> callback);
}
