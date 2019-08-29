package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ded reason: default package */
/* compiled from: ConnectionDataHelper */
public final class ded {
    private static ded a;
    private Context b;

    private ded(Context context) {
        this.b = context;
    }

    public static synchronized ded a(Context context) {
        ded ded;
        synchronized (ded.class) {
            try {
                if (a == null) {
                    a = new ded(context);
                }
                ded = a;
            }
        }
        return ded;
    }

    public final void a(final fbl fbl) {
        ahn.b().execute(new Runnable() {
            public final void run() {
                String str;
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    String a2 = com2.a();
                    if (a2 != null) {
                        List arrayList = new ArrayList();
                        if (com2.b(a2) != null) {
                            List<FavoritePOI> f = com2.b(a2).f();
                            FavoritePOI c = com2.b(a2).c();
                            FavoritePOI d = com2.b(a2).d();
                            if (f.size() > 0) {
                                arrayList = f;
                            }
                            if (c != null) {
                                arrayList.add(c);
                            }
                            if (d != null) {
                                arrayList.add(d);
                            }
                        }
                        if (arrayList.size() > 0) {
                            str = ded.a(arrayList);
                            String a3 = ded.a(ded.a());
                            String a4 = ded.a(ded.this.b());
                            fbl.b(2, str);
                            fbl.b(3, a3);
                            fbl.b(5, a4);
                            StringBuilder sb = new StringBuilder("sendDataToAliCar, preference=");
                            sb.append(a3);
                            sb.append(", savePoint=");
                            sb.append(str);
                            sb.append(", searchAndNavi= ");
                            sb.append(a4);
                        }
                    }
                }
                str = "";
                String a32 = ded.a(ded.a());
                String a42 = ded.a(ded.this.b());
                fbl.b(2, str);
                fbl.b(3, a32);
                fbl.b(5, a42);
                StringBuilder sb2 = new StringBuilder("sendDataToAliCar, preference=");
                sb2.append(a32);
                sb2.append(", savePoint=");
                sb2.append(str);
                sb2.append(", searchAndNavi= ");
                sb2.append(a42);
            }
        });
    }

    static JSONObject a() {
        String str;
        JSONObject jSONObject = new JSONObject();
        bax bax = (bax) a.a.a(bax.class);
        if (bax == null) {
            str = null;
        } else {
            str = bax.b();
        }
        if (str != null && str.length() > 0) {
            try {
                jSONObject.put("EscapeCrowding", str.contains("2"));
                jSONObject.put("AvoidTolls", str.contains("4"));
                jSONObject.put("NoHighway", str.contains("8"));
                jSONObject.put("PreferHighway", str.contains("16"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    /* access modifiers changed from: 0000 */
    public final JSONObject b() {
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("routeHistory", b(chk.a(RouteType.CAR)));
            List<TipItem> tipItems = SearchHistoryHelper.getInstance(this.b).getTipItems(0);
            if (tipItems == null) {
                jSONArray = new JSONArray();
            } else {
                jSONArray = c(tipItems);
            }
            jSONObject.put("searchHistory", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONArray b(List<chk> list) {
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        while (i < list.size()) {
            try {
                if (list.get(i) != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("time", list.get(i).m * 1000);
                    POI e = list.get(i).e();
                    GeoPoint geoPoint = null;
                    if (e != null) {
                        geoPoint = e.getPoint();
                    }
                    if (geoPoint != null) {
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, geoPoint.getLongitude());
                        jSONObject.put("lat", geoPoint.getLatitude());
                    }
                    JSONObject jSONObject2 = list.get(i).k;
                    if (jSONObject2 != null) {
                        jSONObject.put(TrafficUtil.POIID, jSONObject2.optString("mId"));
                        jSONObject.put("addr", jSONObject2.optString("mAddr"));
                        jSONObject.put("name", jSONObject2.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
                        DPoint a2 = cob.a(Long.valueOf(Long.parseLong(jSONObject2.optString("mX"))).longValue(), Long.valueOf(Long.parseLong(jSONObject2.optString("mY"))).longValue(), 20);
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, a2.x);
                        jSONObject.put("lat", a2.y);
                    }
                    jSONArray.put(jSONObject);
                }
                i++;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray;
    }

    static String a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("msgId", UUID.randomUUID());
            jSONObject2.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2.toString();
    }

    private static String a(JSONArray jSONArray) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("msgId", UUID.randomUUID());
            jSONObject.put("data", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private static JSONArray c(List<TipItem> list) {
        JSONArray jSONArray = new JSONArray();
        try {
            new JSONObject();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(TrafficUtil.POIID, list.get(i).poiid);
                    jSONObject.put("name", list.get(i).name);
                    jSONObject.put("addr", list.get(i).addr);
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, list.get(i).x);
                    jSONObject.put("lat", list.get(i).y);
                    if (list.get(i).time != null) {
                        jSONObject.put("time", list.get(i).time.getTime());
                    } else {
                        jSONObject.put("time", 0);
                    }
                    jSONArray.put(jSONObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONArray;
    }

    static String a(List<FavoritePOI> list) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    JSONObject jSONObject = new JSONObject();
                    if (list.get(i).getCommonName() != null && list.get(i).getCommonName().equals("家")) {
                        jSONObject.put("type", 1);
                    } else if (list.get(i).getCommonName() == null || !list.get(i).getCommonName().equals("公司")) {
                        jSONObject.put("type", 0);
                    } else {
                        jSONObject.put("type", 2);
                    }
                    jSONObject.put("userId", list.get(i).getUserId());
                    jSONObject.put(TrafficUtil.POIID, list.get(i).getId());
                    jSONObject.put("time", list.get(i).getCreateTime());
                    if (!TextUtils.isEmpty(list.get(i).getPoiJson())) {
                        JSONObject jSONObject2 = new JSONObject(list.get(i).getPoiJson());
                        jSONObject.put("addr", jSONObject2.optString("address", ""));
                        jSONObject.put("name", jSONObject2.optString("name", ""));
                        DPoint a2 = cob.a(Long.valueOf(Long.parseLong(jSONObject2.optString(GirfFavoritePoint.JSON_FIELD_POI_POINT_X))).longValue(), Long.valueOf(Long.parseLong(jSONObject2.optString(GirfFavoritePoint.JSON_FIELD_POI_POINT_Y))).longValue(), 20);
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, a2.x);
                        jSONObject.put("lat", a2.y);
                    }
                    jSONArray.put(jSONObject);
                }
            }
            return a(jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
