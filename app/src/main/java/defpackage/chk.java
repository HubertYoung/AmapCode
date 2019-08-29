package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: chk reason: default package */
/* compiled from: SyncableRouteHistory */
public final class chk {
    public static a a;
    public String b;
    public String c;
    public String d;
    public String e;
    public int f;
    public int g;
    public int h;
    public int i;
    public JSONObject j;
    public JSONObject k;
    public String l;
    public long m;
    public POI n;
    public ArrayList<POI> o;
    public POI p;
    private int q = 1;
    private String r = "";

    /* renamed from: chk$a */
    /* compiled from: SyncableRouteHistory */
    public interface a {
        void a(POI poi, RouteType routeType);
    }

    public static List<chk> a(RouteType routeType) {
        ArrayList arrayList = new ArrayList();
        List<String> q2 = bin.a.q(b(routeType));
        if (q2 != null && q2.size() > 0) {
            for (String next : q2) {
                if (!TextUtils.isEmpty(next)) {
                    chk c2 = c(next);
                    if (c2 != null) {
                        arrayList.add(c2);
                    }
                }
            }
        }
        return arrayList;
    }

    public static void a(POI poi, List<POI> list, POI poi2, RouteType routeType) {
        POI clone = poi.clone();
        POI clone2 = poi2.clone();
        chk chk = new chk();
        chk.f = clone.getPoint().x;
        chk.g = clone.getPoint().y;
        chk.h = clone2.getPoint().x;
        chk.i = clone2.getPoint().y;
        chk.j = a(clone);
        chk.k = a(clone2);
        if (list != null && list.size() > 0) {
            chk.l = a(list);
        }
        chk.n = a(chk.j);
        chk.p = a(chk.k);
        chk.o = b(chk.l);
        chk.c = b(routeType);
        chk.d = d(chk);
        if (routeType == RouteType.TRAIN) {
            chk.b = c(chk);
        } else if (routeType == RouteType.ETRIP) {
            chk.b = b(chk);
        } else {
            chk.b = a(chk);
        }
        chk.m = System.currentTimeMillis() / 1000;
        if (routeType == RouteType.ETRIP) {
            bin.a.c(chk.c, chk.b, chk.b(), 1);
        } else {
            bin.a.c(chk.c, chk.b, chk.c(), 1);
        }
        if ((routeType == RouteType.BUS || routeType == RouteType.CAR || routeType == RouteType.ONFOOT || routeType == RouteType.RIDE || routeType == RouteType.TRUCK || routeType == RouteType.ETRIP) && !"地图选定位置".equals(clone2.getName()) && a != null) {
            a.a(clone2, routeType);
        }
    }

    public final String a() {
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "id", this.b);
        agd.a(jSONObject, (String) "type", (String) "307");
        agd.a(jSONObject, (String) "routeName", this.d);
        agd.a(jSONObject, (String) "method", this.e);
        agd.a(jSONObject, (String) "startX", this.f);
        agd.a(jSONObject, (String) "startY", this.g);
        agd.a(jSONObject, (String) "endX", this.h);
        agd.a(jSONObject, (String) "endY", this.i);
        try {
            jSONObject.put("from_poi_json", a(this.n));
            jSONObject.put("to_poi_json", a(this.p));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        agd.a(jSONObject, "mid_poi_json", this.l, "");
        agd.a(jSONObject, (String) "update_time", this.m);
        agd.a(jSONObject, (String) "version", this.q);
        agd.a(jSONObject, "cellIconMark", this.r, "");
        StringBuilder sb = new StringBuilder("toSyncedString, jo.toString() = ");
        sb.append(jSONObject.toString());
        AMapLog.e("Aragorn", sb.toString());
        return jSONObject.toString();
    }

    public final String b() {
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "id", this.b);
        agd.a(jSONObject, (String) "type", (String) "308");
        agd.a(jSONObject, (String) "routeName", this.d);
        agd.a(jSONObject, (String) "method", this.e);
        agd.a(jSONObject, (String) "startX", this.f);
        agd.a(jSONObject, (String) "startY", this.g);
        agd.a(jSONObject, (String) "endX", this.h);
        agd.a(jSONObject, (String) "endY", this.i);
        try {
            jSONObject.put("from_poi_json", a(this.n));
            jSONObject.put("to_poi_json", a(this.p));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        agd.a(jSONObject, (String) "update_time", this.m);
        agd.a(jSONObject, (String) "version", this.q);
        StringBuilder sb = new StringBuilder("toEtripSyncedString, jo.toString() = ");
        sb.append(jSONObject.toString());
        AMapLog.e("Aragorn", sb.toString());
        return jSONObject.toString();
    }

    public final String c() {
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "id", this.b);
        agd.a(jSONObject, (String) "type", this.c);
        agd.a(jSONObject, (String) "routeName", this.d);
        agd.a(jSONObject, (String) "method", this.e);
        agd.a(jSONObject, (String) "startX", this.f);
        agd.a(jSONObject, (String) "startY", this.g);
        agd.a(jSONObject, (String) "endX", this.h);
        agd.a(jSONObject, (String) "endY", this.i);
        try {
            jSONObject.put("from_poi_json", a(this.n));
            jSONObject.put("to_poi_json", a(this.p));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        agd.a(jSONObject, "mid_poi_json", this.l, "");
        agd.a(jSONObject, (String) "update_time", this.m);
        agd.a(jSONObject, (String) "version", this.q);
        agd.a(jSONObject, "cellIconMark", this.r, "");
        StringBuilder sb = new StringBuilder("toSyncedString, jo.toString() = ");
        sb.append(jSONObject.toString());
        AMapLog.e("Aragorn", sb.toString());
        return jSONObject.toString();
    }

    private static chk c(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            chk chk = new chk();
            chk.b = jSONObject.optString("id");
            chk.c = jSONObject.optString("type");
            chk.d = jSONObject.optString("routeName");
            chk.e = jSONObject.optString("method");
            String optString = jSONObject.optString("startX");
            int i2 = 0;
            chk.f = !TextUtils.isEmpty(optString) ? Integer.parseInt(optString) : 0;
            String optString2 = jSONObject.optString("startY");
            chk.g = !TextUtils.isEmpty(optString2) ? Integer.parseInt(optString2) : 0;
            String optString3 = jSONObject.optString("endX");
            chk.h = !TextUtils.isEmpty(optString3) ? Integer.parseInt(optString3) : 0;
            String optString4 = jSONObject.optString("endY");
            if (!TextUtils.isEmpty(optString4)) {
                i2 = Integer.parseInt(optString4);
            }
            chk.i = i2;
            chk.j = jSONObject.optJSONObject("from_poi_json");
            chk.k = jSONObject.optJSONObject("to_poi_json");
            chk.l = jSONObject.optString("mid_poi_json");
            chk.n = a(chk.j);
            chk.p = a(chk.k);
            chk.o = b(chk.l);
            String optString5 = jSONObject.optString("update_time");
            chk.m = !TextUtils.isEmpty(optString5) ? (long) Integer.parseInt(optString5) : 0;
            return chk;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(chk chk) {
        StringBuilder sb = new StringBuilder();
        POI d2 = chk.d();
        POI e2 = chk.e();
        sb.append(b(d2));
        sb.append(b(e2));
        ArrayList<POI> f2 = chk.f();
        if (f2 != null) {
            for (POI b2 : f2) {
                sb.append(b(b2));
            }
        }
        sb.append(d(chk.c));
        StringBuilder sb2 = new StringBuilder("==> Before md5, sb.toString() = ");
        sb2.append(sb.toString());
        AMapLog.e("Aragorn", sb2.toString());
        String a2 = agy.a(sb.toString());
        AMapLog.e("Aragorn", "==> After md5, md5String = ".concat(String.valueOf(a2)));
        return a2;
    }

    public static String b(chk chk) {
        POI d2 = chk.d();
        POI e2 = chk.e();
        StringBuilder sb = new StringBuilder();
        sb.append(b(d2));
        sb.append(b(e2));
        sb.append(d((String) "308"));
        StringBuilder sb2 = new StringBuilder("==> Before md5, sb.toString() = ");
        sb2.append(sb.toString());
        AMapLog.e("Aragorn", sb2.toString());
        String a2 = agy.a(sb.toString());
        AMapLog.e("Aragorn", "==> After md5, md5String = ".concat(String.valueOf(a2)));
        return a2;
    }

    public static String c(chk chk) {
        StringBuilder sb = new StringBuilder();
        String name = chk.d().getName();
        String name2 = chk.e().getName();
        sb.append(name);
        sb.append("+");
        sb.append(name2);
        sb.append("+");
        sb.append(d(chk.c));
        return agy.a(sb.toString());
    }

    private static int d(@NonNull String str) {
        if (str.equals("302")) {
            return 0;
        }
        if (str.equals("303")) {
            return 1;
        }
        if (str.equals("304")) {
            return 2;
        }
        if (str.equals("305")) {
            return 3;
        }
        if (str.equals("306")) {
            return 4;
        }
        if (str.equals("308")) {
            return 6;
        }
        if (str.equals("307")) {
            return 7;
        }
        if (str.equals("311")) {
            return 11;
        }
        return 0;
    }

    private static String b(@Nullable POI poi) {
        if (poi == null) {
            return "";
        }
        if (poi.getName().equals(AMapAppGlobal.getApplication().getString(R.string.my_location))) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.my_location));
            sb.append("+");
            return sb.toString();
        } else if (poi.getPoint() != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(poi.getPoint().x);
            sb2.append("+");
            sb2.append(poi.getPoint().y);
            sb2.append("+");
            sb2.append(poi.getName());
            sb2.append("+");
            return sb2.toString();
        } else if (TextUtils.isEmpty(poi.getName())) {
            return "";
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(poi.getName());
            sb3.append("+");
            return sb3.toString();
        }
    }

    public static String d(chk chk) {
        StringBuilder sb = new StringBuilder();
        if ("302".equals(chk.c) && chk.f() != null && chk.f().size() > 0) {
            Iterator<POI> it = chk.o.iterator();
            while (it.hasNext()) {
                POI next = it.next();
                if (next != null) {
                    sb.append(" → ");
                    sb.append(next.getName());
                }
            }
        }
        if (chk.d() == null || chk.e() == null) {
            return "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(chk.d().getName());
        sb2.append(sb.toString());
        sb2.append(" → ");
        sb2.append(chk.e().getName());
        return sb2.toString();
    }

    public final POI d() {
        if (this.n == null && this.j != null) {
            this.n = a(this.j);
        }
        return this.n;
    }

    public final POI e() {
        if (this.p == null && this.k != null) {
            this.p = a(this.k);
        }
        return this.p;
    }

    public final ArrayList<POI> f() {
        if (this.o == null && this.l != null) {
            this.o = b(this.l);
        }
        return this.o;
    }

    public static String b(RouteType routeType) {
        if (routeType != RouteType.CAR) {
            if (routeType == RouteType.BUS) {
                return "303";
            }
            if (routeType == RouteType.ONFOOT) {
                return "304";
            }
            if (routeType == RouteType.RIDE) {
                return "305";
            }
            if (routeType == RouteType.TRAIN) {
                return "306";
            }
            if (routeType == RouteType.TRUCK) {
                return "307";
            }
            if (routeType == RouteType.ETRIP) {
                return "308";
            }
            if (routeType == RouteType.MOTOR) {
                return "311";
            }
        }
        return "302";
    }

    public static POI a(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return a(new JSONObject(str));
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static POI a(@NonNull JSONObject jSONObject) {
        try {
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(agd.e(jSONObject, "mId"));
            createPOI.setType(agd.e(jSONObject, "mType"));
            createPOI.setName(agd.e(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            createPOI.setAddr(agd.e(jSONObject, "mAddr"));
            createPOI.setPoint(new GeoPoint(agd.a(jSONObject, "mX"), agd.a(jSONObject, "mY")));
            createPOI.setCityCode(agd.e(jSONObject, "mCityCode"));
            createPOI.setCityName(agd.e(jSONObject, "mCityName"));
            createPOI.setInoorFloorNoName(agd.e(jSONObject, "floorNoName"));
            createPOI.setEndPoiExtension(agd.e(jSONObject, "mEndPoiExtension"));
            createPOI.setTransparent(agd.e(jSONObject, "mTransparent"));
            createPOI.setPid(agd.e(jSONObject, "pid"));
            String e2 = agd.e(jSONObject, "floor_name");
            ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            if (!TextUtils.isEmpty(e2)) {
                iSearchPoiData.setFnona(e2);
            }
            String e3 = agd.e(jSONObject, "mEntranceList");
            if (!TextUtils.isEmpty(e3)) {
                JSONArray jSONArray = new JSONArray(e3);
                if (jSONArray.length() > 0) {
                    ArrayList arrayList = new ArrayList();
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        arrayList.add(new GeoPoint(jSONObject2.getInt("mEntranceX"), jSONObject2.getInt("mEntranceY")));
                    }
                    createPOI.setEntranceList(arrayList);
                }
            }
            String e4 = agd.e(jSONObject, "mExitList");
            if (!TextUtils.isEmpty(e4)) {
                JSONArray jSONArray2 = new JSONArray(e4);
                if (jSONArray2.length() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    int length2 = jSONArray2.length();
                    for (int i3 = 0; i3 < length2; i3++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                        arrayList2.add(new GeoPoint(jSONObject3.getInt("mExitX"), jSONObject3.getInt("mExitY")));
                    }
                    createPOI.setExitList(arrayList2);
                }
            }
            iSearchPoiData.setParent(agd.e(jSONObject, "mParent"));
            iSearchPoiData.setChildType(agd.e(jSONObject, "mChildType"));
            iSearchPoiData.setTowardsAngle(agd.e(jSONObject, "mTowardsAngle"));
            return createPOI;
        } catch (JSONException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static JSONObject a(@NonNull POI poi) {
        try {
            JSONObject jSONObject = new JSONObject();
            agd.a(jSONObject, "mId", poi.getId(), "");
            agd.a(jSONObject, "mType", poi.getType(), "");
            agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName(), "");
            agd.a(jSONObject, "mAddr", poi.getAddr(), "");
            if (poi.getPoint() != null) {
                agd.a(jSONObject, (String) "mX", poi.getPoint().x);
                agd.a(jSONObject, (String) "mY", poi.getPoint().y);
            }
            if (!TextUtils.isEmpty(poi.getCityCode())) {
                agd.a(jSONObject, (String) "mCityCode", poi.getCityCode());
            }
            if (!TextUtils.isEmpty(poi.getCityName())) {
                agd.a(jSONObject, (String) "mCityName", poi.getCityName());
            }
            if (!TextUtils.isEmpty(poi.getIndoorFloorNoName())) {
                agd.a(jSONObject, (String) "floorNoName", poi.getIndoorFloorNoName());
            }
            if (!TextUtils.isEmpty(poi.getEndPoiExtension())) {
                agd.a(jSONObject, (String) "mEndPoiExtension", poi.getEndPoiExtension());
            }
            if (!TextUtils.isEmpty(poi.getTransparent())) {
                agd.a(jSONObject, (String) "mTransparent", poi.getTransparent());
            }
            if (!TextUtils.isEmpty(poi.getPid())) {
                agd.a(jSONObject, (String) "pid", poi.getPid());
            }
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            if (!TextUtils.isEmpty(iSearchPoiData.getFnona())) {
                agd.a(jSONObject, (String) "floor_name", iSearchPoiData.getFnona());
            }
            ArrayList<GeoPoint> entranceList = poi.getEntranceList();
            if (entranceList != null && entranceList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int size = entranceList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    JSONObject jSONObject2 = new JSONObject();
                    GeoPoint geoPoint = entranceList.get(i2);
                    jSONObject2.put("mEntranceX", geoPoint.x);
                    jSONObject2.put("mEntranceY", geoPoint.y);
                    jSONArray.put(i2, jSONObject2);
                }
                agd.a(jSONObject, (String) "mEntranceList", jSONArray.toString());
            }
            ArrayList<GeoPoint> exitList = poi.getExitList();
            if (exitList != null && exitList.size() > 0) {
                JSONArray jSONArray2 = new JSONArray();
                int size2 = exitList.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    JSONObject jSONObject3 = new JSONObject();
                    GeoPoint geoPoint2 = exitList.get(i3);
                    jSONObject3.put("mExitX", geoPoint2.x);
                    jSONObject3.put("mExitY", geoPoint2.y);
                    jSONArray2.put(i3, jSONObject3);
                }
                agd.a(jSONObject, (String) "mExitList", jSONArray2.toString());
            }
            String parent = iSearchPoiData.getParent();
            String childType = iSearchPoiData.getChildType();
            if (!TextUtils.isEmpty(parent) && !TextUtils.isEmpty(childType)) {
                agd.a(jSONObject, (String) "mParent", parent);
                agd.a(jSONObject, (String) "mChildType", childType);
            }
            String towardsAngle = iSearchPoiData.getTowardsAngle();
            if (!TextUtils.isEmpty(towardsAngle)) {
                agd.a(jSONObject, (String) "mTowardsAngle", towardsAngle);
            }
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(@Nullable List<POI> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (POI next : list) {
            if (next != null) {
                JSONObject a2 = a(next);
                if (a2 != null) {
                    jSONArray.put(a2);
                }
            }
        }
        return jSONArray.toString();
    }

    public static ArrayList<POI> b(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<POI> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() > 0) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    POI a2 = a(jSONArray.optJSONObject(i2));
                    if (a2 != null) {
                        arrayList.add(a2);
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            try {
                POI a3 = a(new JSONObject(str));
                if (a3 != null) {
                    arrayList.add(a3);
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
                return null;
            }
        }
        return arrayList;
    }
}
