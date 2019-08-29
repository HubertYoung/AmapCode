package defpackage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* renamed from: cgk reason: default package */
/* compiled from: DrivingHistoryEntity */
public final class cgk {
    public Long a;
    public Integer b;
    public String c;
    public String d;
    public List<GeoPoint> e;
    public Integer f;
    public Long g;
    public Integer h;
    public Integer i;
    public Double j;
    public Integer k;
    public List<GeoPoint> l;
    public List<GeoPoint> m;
    public List<GeoPoint> n;

    public cgk(JSONObject jSONObject) {
        this.a = Long.valueOf(kg.a(jSONObject, "id"));
        this.b = Integer.valueOf(kg.b(jSONObject, "score"));
        this.c = jSONObject.getString("startPoi");
        this.d = jSONObject.getString("endPoi");
        this.f = Integer.valueOf(kg.b(jSONObject, "overSpeed"));
        JSONObject jSONObject2 = jSONObject.getJSONObject("ubiData");
        this.h = Integer.valueOf(kg.b(jSONObject2, "travelDist"));
        this.i = Integer.valueOf(kg.b(jSONObject2, "travelTime"));
        this.g = Long.valueOf(kg.a(jSONObject2, "startUTC"));
        this.f = Integer.valueOf(kg.b(jSONObject2, "overSpeed"));
        a(jSONObject.getJSONArray("coords"));
        c(jSONObject2.getJSONArray("eventList"));
        if (jSONObject2 != null) {
            this.j = Double.valueOf((kg.c(jSONObject2, "travelDist") / 1000.0d) / (kg.c(jSONObject2, "travelTime") / 3600.0d));
        }
        b(jSONObject2.getJSONArray("speedDistribute"));
    }

    private void a(JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                this.e = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.size(); i2 += 2) {
                    this.e.add(new GeoPoint(jSONArray.getDoubleValue(i2), jSONArray.getDoubleValue(i2 + 1)));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void b(JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                int size = jSONArray.size() - 1;
                while (size >= 0) {
                    double doubleValue = jSONArray.getDoubleValue(size);
                    if (Double.compare(doubleValue, 0.0d) == 0) {
                        size--;
                    } else if (Double.compare(doubleValue, 0.01d) > 0) {
                        this.k = Integer.valueOf(new Random().nextInt(5) + (size * 10) + 5);
                        return;
                    } else {
                        this.k = Integer.valueOf(new Random().nextInt(5) + (size * 10));
                        return;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void c(JSONArray jSONArray) {
        if (jSONArray != null) {
            this.l = new ArrayList();
            this.m = new ArrayList();
            this.n = new ArrayList();
            for (int i2 = 0; i2 < jSONArray.size(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                new GeoPoint(kg.c(jSONObject, LocationParams.PARA_FLP_AUTONAVI_LON), kg.c(jSONObject, "lat"));
                kg.b(jSONObject, "type");
            }
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof cgk)) {
            return false;
        }
        return ((cgk) obj).a.equals(this.a);
    }

    public final int hashCode() {
        return this.a == null ? super.hashCode() : this.a.hashCode();
    }
}
