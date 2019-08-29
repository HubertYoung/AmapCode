package com.autonavi.minimap.route.run.beans;

import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIBase;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RunTraceHistory {
    private static final String[][] q = {new String[]{"RunStartPoiX", "startX"}, new String[]{"RunStartPoiY", "startY"}, new String[]{"RunStartPoiName", "startName"}, new String[]{"RunEndPoiX", "endX"}, new String[]{"RunEndPoiY", "endY"}, new String[]{"RunEndPoiName", "endName"}, new String[]{"RunGPSEndPoiX", "gpsEndX"}, new String[]{"RunGPSEndPoiY", "gpsEndY"}, new String[]{"RunGPSEndPoiName", "gpsEndName"}, new String[]{"RunIsFootExitShow", "exitShow"}, new String[]{"RunPonitList", "pointList"}, new String[]{"RunPonitX", DictionaryKeys.CTRLXY_X}, new String[]{"RunPonitY", DictionaryKeys.CTRLXY_Y}, new String[]{"RunPoiName", SuperId.BIT_1_MAIN_BUSSTATION}, new String[]{"RunPonitPause", SuperId.BIT_1_MAIN_VOICE_ASSISTANT}, new String[]{"RunSpeedList", "speedList"}, new String[]{"RunSpeed", "s"}};
    public String a;
    public int b;
    public int c;
    public int d;
    public double e;
    public long f;
    public long g;
    public String h;
    public b i;
    public RunType j;
    public int k;
    public int l;
    public int m;
    public int n;
    public String o;
    public int p;

    public enum RunType {
        RUN_TYPE(0),
        FOOT_TYPE(1);
        
        private int typeValue;

        private RunType(int i) {
            this.typeValue = i;
        }

        public final Integer getValue() {
            return Integer.valueOf(this.typeValue);
        }

        public final RunType getType(int i) {
            if (i == 0) {
                return RUN_TYPE;
            }
            if (i == 1) {
                return FOOT_TYPE;
            }
            return RUN_TYPE;
        }
    }

    public static class a {
        public POI a;
        public int b;
    }

    public static class b {
        public POI a;
        public POI b;
        public POI c;
        public boolean d;
        public ArrayList<a> e;
        public ArrayList<Double> f;
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
        int i2;
        int i3;
        try {
            JSONObject jSONObject = new JSONObject();
            ArrayList<a> arrayList = bVar.e;
            ArrayList<Double> arrayList2 = bVar.f;
            if (!(bVar.a == null || bVar.a.getPoint() == null)) {
                GeoPoint point = bVar.a.getPoint();
                agd.a(jSONObject, (String) "startX", point.x);
                agd.a(jSONObject, (String) "startY", point.y);
                agd.a(jSONObject, (String) "startName", bVar.a.getName());
            }
            if (!(bVar.b == null || bVar.b.getPoint() == null)) {
                GeoPoint point2 = bVar.b.getPoint();
                agd.a(jSONObject, (String) "endX", point2.x);
                agd.a(jSONObject, (String) "endY", point2.y);
                agd.a(jSONObject, (String) "endName", bVar.b.getName());
            }
            if (!(bVar.c == null || bVar.c.getPoint() == null)) {
                GeoPoint point3 = bVar.c.getPoint();
                agd.a(jSONObject, (String) "gpsEndX", point3.x);
                agd.a(jSONObject, (String) "gpsEndY", point3.y);
                agd.a(jSONObject, (String) "gpsEndName", bVar.c.getName());
            }
            if (bVar.d) {
                agd.a(jSONObject, (String) "exitShow", true);
            }
            if (arrayList != null && arrayList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int size = arrayList.size();
                if (size > 0) {
                    JSONObject jSONObject2 = new JSONObject();
                    a aVar = arrayList.get(0);
                    i3 = aVar.a.getPoint().x;
                    i2 = aVar.a.getPoint().y;
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, i3);
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, i2);
                    if (aVar.b != 0) {
                        jSONObject2.put(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, aVar.b);
                    }
                    jSONArray.put(0, jSONObject2);
                } else {
                    i3 = 0;
                    i2 = 0;
                }
                for (int i4 = 1; i4 < size; i4++) {
                    JSONObject jSONObject3 = new JSONObject();
                    a aVar2 = arrayList.get(i4);
                    jSONObject3.put(DictionaryKeys.CTRLXY_X, aVar2.a.getPoint().x - i3);
                    jSONObject3.put(DictionaryKeys.CTRLXY_Y, aVar2.a.getPoint().y - i2);
                    if (aVar2.b != 0) {
                        jSONObject3.put(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, aVar2.b);
                    }
                    jSONArray.put(i4, jSONObject3);
                }
                agd.a(jSONObject, (String) "pointList", jSONArray.toString());
            }
            if (arrayList2 != null && arrayList2.size() > 0) {
                DecimalFormat decimalFormat = new DecimalFormat(DiskFormatter.FORMAT);
                JSONArray jSONArray2 = new JSONArray();
                int size2 = arrayList2.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("s", decimalFormat.format(arrayList2.get(i5)));
                    jSONArray2.put(i5, jSONObject4);
                }
                agd.a(jSONObject, (String) "speedList", jSONArray2.toString());
            }
            return jSONObject;
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
            return null;
        }
    }

    private static b a(JSONObject jSONObject, boolean z) {
        int i2;
        int i3;
        try {
            b bVar = new b();
            POIBase pOIBase = new POIBase();
            GeoPoint geoPoint = new GeoPoint();
            int a2 = agd.a(jSONObject, "startX");
            int a3 = agd.a(jSONObject, "startY");
            String e2 = agd.e(jSONObject, "startName");
            geoPoint.x = a2;
            geoPoint.y = a3;
            pOIBase.setPoint(geoPoint);
            pOIBase.setName(e2);
            bVar.a = pOIBase;
            POIBase pOIBase2 = new POIBase();
            GeoPoint geoPoint2 = new GeoPoint();
            int a4 = agd.a(jSONObject, "endX");
            int a5 = agd.a(jSONObject, "endY");
            String e3 = agd.e(jSONObject, "endName");
            geoPoint2.x = a4;
            geoPoint2.y = a5;
            pOIBase2.setPoint(geoPoint2);
            pOIBase2.setName(e3);
            bVar.b = pOIBase2;
            POIBase pOIBase3 = new POIBase();
            GeoPoint geoPoint3 = new GeoPoint();
            int a6 = agd.a(jSONObject, "gpsEndX");
            int a7 = agd.a(jSONObject, "gpsEndY");
            String e4 = agd.e(jSONObject, "gpsEndName");
            geoPoint3.x = a6;
            geoPoint3.y = a7;
            pOIBase3.setPoint(geoPoint3);
            pOIBase3.setName(e4);
            bVar.c = pOIBase3;
            bVar.d = jSONObject.optBoolean("exitShow", false);
            String e5 = agd.e(jSONObject, "pointList");
            if (!TextUtils.isEmpty(e5)) {
                JSONArray jSONArray = new JSONArray(e5);
                ArrayList<a> arrayList = new ArrayList<>();
                int length = jSONArray.length();
                if (length > 0) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(0);
                    POIBase pOIBase4 = new POIBase();
                    i3 = jSONObject2.optInt(DictionaryKeys.CTRLXY_X, 0);
                    i2 = jSONObject2.optInt(DictionaryKeys.CTRLXY_Y, 0);
                    pOIBase4.setPoint(new GeoPoint(i3, i2));
                    a aVar = new a();
                    aVar.a = pOIBase4;
                    aVar.b = jSONObject2.optInt(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, 0);
                    arrayList.add(aVar);
                } else {
                    i3 = 0;
                    i2 = 0;
                }
                for (int i4 = 1; i4 < length; i4++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i4);
                    POIBase pOIBase5 = new POIBase();
                    if (z) {
                        pOIBase5.setPoint(new GeoPoint(jSONObject3.optInt(DictionaryKeys.CTRLXY_X, 0), jSONObject3.optInt(DictionaryKeys.CTRLXY_Y, 0)));
                    } else {
                        pOIBase5.setPoint(new GeoPoint(jSONObject3.optInt(DictionaryKeys.CTRLXY_X, 0) + i3, jSONObject3.optInt(DictionaryKeys.CTRLXY_Y, 0) + i2));
                    }
                    a aVar2 = new a();
                    aVar2.a = pOIBase5;
                    aVar2.b = jSONObject3.optInt(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, 0);
                    arrayList.add(aVar2);
                }
                bVar.e = arrayList;
            }
            String e6 = agd.e(jSONObject, "speedList");
            if (!TextUtils.isEmpty(e6)) {
                JSONArray jSONArray2 = new JSONArray(e6);
                ArrayList<Double> arrayList2 = new ArrayList<>();
                int length2 = jSONArray2.length();
                for (int i5 = 0; i5 < length2; i5++) {
                    arrayList2.add(Double.valueOf(jSONArray2.getJSONObject(i5).getDouble("s")));
                }
                bVar.f = arrayList2;
            }
            return bVar;
        } catch (JSONException e7) {
            e7.printStackTrace();
            return null;
        }
    }

    public static b a(String str) {
        String str2;
        String[][] strArr;
        if (str == null) {
            return null;
        }
        try {
            boolean z = true;
            if (TextUtils.isEmpty(str) || !str.contains("RunPonitX")) {
                str2 = str;
            } else {
                str2 = str;
                for (String[] strArr2 : q) {
                    str2 = str2.replace(strArr2[0], strArr2[1]);
                }
            }
            if (TextUtils.isEmpty(str2) || str2.equals(str)) {
                z = false;
            }
            return a(new JSONObject(str2), z);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
