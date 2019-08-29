package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: btf reason: default package */
/* compiled from: RouteHistory */
public class btf {
    public String a;
    public String b;
    public Integer c;
    public String d;
    public Integer e;
    public Integer f;
    public Integer g;
    public Integer h;
    public String i;
    public String j;
    public String k;
    public Long l;
    public POI m;
    public POI n;

    public static POI a(String str) {
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

    private static POI a(JSONObject jSONObject) {
        try {
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(agd.e(jSONObject, "mId"));
            createPOI.setPid(agd.e(jSONObject, "mPid"));
            createPOI.setName(agd.e(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            createPOI.setAddr(agd.e(jSONObject, "mAddr"));
            createPOI.setCityCode(agd.e(jSONObject, "mCityCode"));
            createPOI.setCityName(agd.e(jSONObject, "mCityName"));
            createPOI.setAdCode(agd.e(jSONObject, "mAdcode"));
            createPOI.setPoint(new GeoPoint(agd.a(jSONObject, "mx"), agd.a(jSONObject, "my")));
            String e2 = agd.e(jSONObject, "mEntranceList");
            createPOI.setType(agd.e(jSONObject, "poiType"));
            String e3 = agd.e(jSONObject, "floor_name");
            createPOI.setInoorFloorNoName(agd.e(jSONObject, "floorNoName"));
            createPOI.setEndPoiExtension(agd.e(jSONObject, "end_poi_extension"));
            createPOI.setTransparent(agd.e(jSONObject, H5Param.LONG_TRANSPARENT));
            if (jSONObject.has("IATA_CODE")) {
                createPOI.getPoiExtra().put("IATA_CODE", agd.e(jSONObject, "IATA_CODE"));
            }
            ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            if (!TextUtils.isEmpty(e3)) {
                iSearchPoiData.setFnona(e3);
            }
            String e4 = agd.e(jSONObject, "parent");
            if (!TextUtils.isEmpty(e4)) {
                iSearchPoiData.setParent(e4);
            }
            String e5 = agd.e(jSONObject, "childType");
            if (TextUtils.isEmpty(e5)) {
                e5 = agd.e(jSONObject, "childtype");
            }
            if (!TextUtils.isEmpty(e5)) {
                iSearchPoiData.setChildType(e5);
            }
            String e6 = agd.e(jSONObject, "towards_angle");
            if (!TextUtils.isEmpty(e6)) {
                iSearchPoiData.setTowardsAngle(e6);
            }
            if (e2 != null && !e2.equals("") && e2.length() > 0) {
                JSONArray jSONArray = new JSONArray(e2);
                ArrayList arrayList = new ArrayList();
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    arrayList.add(new GeoPoint(jSONObject2.getInt("mEntranceX"), jSONObject2.getInt("mEntranceY")));
                }
                createPOI.setEntranceList(arrayList);
            }
            String e7 = agd.e(jSONObject, "mExitList");
            if (e7 != null && !e7.equals("") && e7.length() > 0) {
                JSONArray jSONArray2 = new JSONArray(e7);
                ArrayList arrayList2 = new ArrayList();
                int length2 = jSONArray2.length();
                for (int i3 = 0; i3 < length2; i3++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                    arrayList2.add(new GeoPoint(jSONObject3.getInt("mExitX"), jSONObject3.getInt("mExitY")));
                }
                createPOI.setExitList(arrayList2);
            }
            return createPOI;
        } catch (JSONException e8) {
            e8.printStackTrace();
            return null;
        }
    }

    public static String a(POI poi) {
        if (poi == null) {
            return null;
        }
        JSONObject b2 = b(poi);
        if (b2 != null) {
            return b2.toString();
        }
        return null;
    }

    private static JSONObject b(POI poi) {
        try {
            JSONObject jSONObject = new JSONObject();
            agd.a(jSONObject, (String) "mId", poi.getId());
            agd.a(jSONObject, (String) "mPid", poi.getPid());
            agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
            agd.a(jSONObject, (String) "mAddr", poi.getAddr());
            agd.a(jSONObject, (String) "mCityCode", poi.getCityCode());
            agd.a(jSONObject, (String) "mCityName", poi.getCityName());
            agd.a(jSONObject, (String) "mAdcode", poi.getAdCode());
            agd.a(jSONObject, (String) "mx", poi.getPoint().x);
            agd.a(jSONObject, (String) "my", poi.getPoint().y);
            agd.a(jSONObject, (String) "poiType", poi.getType());
            agd.a(jSONObject, (String) "floorNoName", poi.getIndoorFloorNoName());
            agd.a(jSONObject, (String) "end_poi_extension", poi.getEndPoiExtension());
            agd.a(jSONObject, (String) H5Param.LONG_TRANSPARENT, poi.getTransparent());
            if (poi.getPoiExtra().get("IATA_CODE") != null) {
                agd.a(jSONObject, (String) "IATA_CODE", poi.getPoiExtra().get("IATA_CODE").toString());
            }
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            if (!TextUtils.isEmpty(iSearchPoiData.getFnona())) {
                agd.a(jSONObject, (String) "floor_name", iSearchPoiData.getFnona());
            }
            if (!TextUtils.isEmpty(iSearchPoiData.getChildType())) {
                agd.a(jSONObject, (String) "childType", iSearchPoiData.getChildType());
            }
            if (!TextUtils.isEmpty(iSearchPoiData.getParent())) {
                agd.a(jSONObject, (String) "parent", iSearchPoiData.getParent());
            }
            if (!TextUtils.isEmpty(iSearchPoiData.getTowardsAngle())) {
                agd.a(jSONObject, (String) "towards_angle", iSearchPoiData.getTowardsAngle());
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
            return jSONObject;
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
            return null;
        }
    }

    public static String a(List<POI> list) {
        JSONArray jSONArray = new JSONArray();
        for (POI next : list) {
            if (next != null) {
                JSONObject b2 = b(next);
                if (b2 != null) {
                    jSONArray.put(b2);
                }
            }
        }
        return jSONArray.toString();
    }

    public static ArrayList<POI> b(String str) {
        if (str == null) {
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
            }
        }
        return arrayList;
    }
}
