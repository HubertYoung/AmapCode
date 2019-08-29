package com.autonavi.minimap.route.ride.beans;

import com.amap.bundle.datamodel.poi.POIBase;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RideTraceHistory {
    public String a;
    public int b;
    public int c;
    public int d;
    public double e;
    public double f;
    public long g;
    public long h;
    public String i;
    public b j;
    public RideType k;
    public String l;
    public String m;

    public enum RideType {
        RIDE_TYPE(0),
        DEST_TYPE(1),
        SHARE_RIDE_TYPE(2);
        
        private int typeValue;

        private RideType(int i) {
            this.typeValue = i;
        }

        public final Integer getValue() {
            return Integer.valueOf(this.typeValue);
        }

        public final RideType getType(int i) {
            if (i == 0) {
                return RIDE_TYPE;
            }
            if (i == 1) {
                return DEST_TYPE;
            }
            if (i == 2) {
                return SHARE_RIDE_TYPE;
            }
            return RIDE_TYPE;
        }
    }

    public static class a {
        public POI a;
        public int b;
        public int c;

        public final int a() {
            if (this.a == null || this.a.getPoint() == null) {
                return 0;
            }
            return this.a.getPoint().x;
        }

        public final int b() {
            if (this.a == null || this.a.getPoint() == null) {
                return 0;
            }
            return this.a.getPoint().y;
        }
    }

    public static class b {
        public POI a;
        public POI b;
        public POI c;
        public boolean d;
        public ArrayList<a> e;
    }

    public static String a(b bVar) {
        if (bVar == null) {
            return null;
        }
        JSONObject b2 = b(bVar);
        if (b2 != null) {
            return b2.toString();
        }
        return null;
    }

    private static JSONObject b(b bVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            ArrayList<a> arrayList = bVar.e;
            if (!(bVar.a == null || bVar.a.getPoint() == null)) {
                GeoPoint point = bVar.a.getPoint();
                agd.a(jSONObject, (String) "RideStartPoiX", point.x);
                agd.a(jSONObject, (String) "RideStartPoiY", point.y);
                agd.a(jSONObject, (String) "RideStartPoiName", bVar.a.getName());
            }
            if (!(bVar.b == null || bVar.b.getPoint() == null)) {
                GeoPoint point2 = bVar.b.getPoint();
                agd.a(jSONObject, (String) "RideEndPoiX", point2.x);
                agd.a(jSONObject, (String) "RideEndPoiY", point2.y);
                agd.a(jSONObject, (String) "RideEndPoiName", bVar.b.getName());
            }
            if (!(bVar.c == null || bVar.c.getPoint() == null)) {
                GeoPoint point3 = bVar.c.getPoint();
                agd.a(jSONObject, (String) "RideGPSEndPoiX", point3.x);
                agd.a(jSONObject, (String) "RideGPSEndPoiY", point3.y);
                agd.a(jSONObject, (String) "RideGPSEndPoiName", bVar.c.getName());
            }
            agd.a(jSONObject, (String) "RideIsFootExitShow", bVar.d);
            if (arrayList != null && arrayList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    JSONObject jSONObject2 = new JSONObject();
                    a aVar = arrayList.get(i2);
                    jSONObject2.put("RidePonitX", aVar.a());
                    jSONObject2.put("RidePonitY", aVar.b());
                    jSONObject2.put("RidePonitPause", aVar.b);
                    jSONObject2.put("RideSpeed", aVar.c);
                    jSONArray.put(i2, jSONObject2);
                }
                agd.a(jSONObject, (String) "RidePonitList", jSONArray.toString());
            }
            return jSONObject;
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
            return null;
        }
    }

    public static b a(String str) {
        if (str == null) {
            return null;
        }
        try {
            return a(new JSONObject(str));
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static b a(JSONObject jSONObject) {
        try {
            b bVar = new b();
            POIBase pOIBase = new POIBase();
            GeoPoint geoPoint = new GeoPoint();
            int a2 = agd.a(jSONObject, "RideStartPoiX");
            int a3 = agd.a(jSONObject, "RideStartPoiY");
            String e2 = agd.e(jSONObject, "RideStartPoiName");
            geoPoint.x = a2;
            geoPoint.y = a3;
            pOIBase.setPoint(geoPoint);
            pOIBase.setName(e2);
            bVar.a = pOIBase;
            POIBase pOIBase2 = new POIBase();
            GeoPoint geoPoint2 = new GeoPoint();
            int a4 = agd.a(jSONObject, "RideEndPoiX");
            int a5 = agd.a(jSONObject, "RideEndPoiY");
            String e3 = agd.e(jSONObject, "RideEndPoiName");
            geoPoint2.x = a4;
            geoPoint2.y = a5;
            pOIBase2.setPoint(geoPoint2);
            pOIBase2.setName(e3);
            bVar.b = pOIBase2;
            POIBase pOIBase3 = new POIBase();
            GeoPoint geoPoint3 = new GeoPoint();
            int a6 = agd.a(jSONObject, "RideGPSEndPoiX");
            int a7 = agd.a(jSONObject, "RideGPSEndPoiY");
            String e4 = agd.e(jSONObject, "RideGPSEndPoiName");
            geoPoint3.x = a6;
            geoPoint3.y = a7;
            pOIBase3.setPoint(geoPoint3);
            pOIBase3.setName(e4);
            bVar.c = pOIBase3;
            bVar.d = agd.d(jSONObject, "RideIsFootExitShow");
            String e5 = agd.e(jSONObject, "RidePonitList");
            if (e5 != null && !e5.equals("") && e5.length() > 0) {
                JSONArray jSONArray = new JSONArray(e5);
                ArrayList<a> arrayList = new ArrayList<>();
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    POIBase pOIBase4 = new POIBase();
                    pOIBase4.setPoint(new GeoPoint(jSONObject2.optInt("RidePonitX", 0), jSONObject2.optInt("RidePonitY", 0)));
                    int optInt = jSONObject2.optInt("RidePonitPause", 0);
                    int optInt2 = jSONObject2.optInt("RideSpeed", 0);
                    a aVar = new a();
                    aVar.a = pOIBase4;
                    aVar.b = optInt;
                    aVar.c = optInt2;
                    arrayList.add(aVar);
                }
                bVar.e = arrayList;
            }
            return bVar;
        } catch (JSONException e6) {
            e6.printStackTrace();
            return null;
        }
    }
}
