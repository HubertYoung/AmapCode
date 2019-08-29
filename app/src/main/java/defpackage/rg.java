package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: rg reason: default package */
/* compiled from: MitFormalUtil */
public final class rg {
    private static POI b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        try {
            JSONObject jSONObject = new JSONObject(str);
            createPOI.setDistance(jSONObject.optInt(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            createPOI.setId(jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            createPOI.setCityName(jSONObject.optString("cname"));
            createPOI.setName(jSONObject.optString("name"));
            createPOI.setAddr(jSONObject.optString("dname"));
            createPOI.setPhone(jSONObject.optString("tel"));
            createPOI.setType(jSONObject.optString("typecode"));
            createPOI.setInoorFloorNoName(jSONObject.optString("floor"));
            createPOI.setPid(jSONObject.optString("parentID"));
            if (jSONObject.has("poi_type")) {
                createPOI.getPoiExtra().put("poi_type", jSONObject.optString("poi_type"));
            }
            if (jSONObject.has("is_gpspoint")) {
                createPOI.getPoiExtra().put("is_gpspoint", Boolean.valueOf(jSONObject.optBoolean("is_gpspoint")));
            }
            if (jSONObject.has("IATA_CODE")) {
                createPOI.getPoiExtra().put("IATA_CODE", jSONObject.optString("IATA_CODE"));
            }
            int optInt = jSONObject.optInt(DictionaryKeys.CTRLXY_X);
            int optInt2 = jSONObject.optInt(DictionaryKeys.CTRLXY_Y);
            double optDouble = jSONObject.has(LocationParams.PARA_FLP_AUTONAVI_LON) ? jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d) : 0.0d;
            if (jSONObject.has("longitude")) {
                optDouble = jSONObject.optDouble("longitude", 0.0d);
            }
            double optDouble2 = jSONObject.has("lat") ? jSONObject.optDouble("lat", 0.0d) : 0.0d;
            if (jSONObject.has("latitude")) {
                optDouble2 = jSONObject.optDouble("latitude", 0.0d);
            }
            GeoPoint point = createPOI.getPoint();
            if (optInt != 0 && optInt2 != 0) {
                if (point == null) {
                    point = new GeoPoint();
                    createPOI.setPoint(point);
                }
                point.x = optInt;
                point.y = optInt2;
            } else if (!(optDouble2 == 0.0d || optDouble == 0.0d)) {
                if (point == null) {
                    point = new GeoPoint();
                    createPOI.setPoint(point);
                }
                point.setLonLat(optDouble, optDouble2);
            }
            if (jSONObject.has("entry_lon") && jSONObject.has("entry_lat")) {
                ArrayList arrayList = new ArrayList();
                double optDouble3 = jSONObject.optDouble("entry_lon", 0.0d);
                double optDouble4 = jSONObject.optDouble("entry_lat", 0.0d);
                if (!(optDouble3 == 0.0d || optDouble4 == 0.0d)) {
                    arrayList.add(new GeoPoint(optDouble3, optDouble4));
                }
                createPOI.setEntranceList(arrayList);
            }
            if (jSONObject.has("exitList")) {
                ArrayList arrayList2 = new ArrayList();
                JSONArray optJSONArray = jSONObject.optJSONArray("exitList");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            arrayList2.add(new GeoPoint(optJSONObject.optInt(DictionaryKeys.CTRLXY_X, 0), optJSONObject.optInt(DictionaryKeys.CTRLXY_Y, 0)));
                        }
                    }
                }
                createPOI.setExitList(arrayList2);
            }
            if (jSONObject.has("businfo_lineids")) {
                createPOI.getPoiExtra().put("businfo_lineids", jSONObject.optString("businfo_lineids"));
            }
            createPOI.setCityCode(jSONObject.optString("cityCode"));
            createPOI.setAdCode(jSONObject.optString(AutoJsonUtils.JSON_ADCODE));
            createPOI.setIndustry(jSONObject.optString("industry"));
            createPOI.setEndPoiExtension(jSONObject.optString("end_poi_extension"));
            createPOI.setTransparent(jSONObject.optString(H5Param.LONG_TRANSPARENT));
            FavoritePOI favoritePOI = (FavoritePOI) createPOI.as(FavoritePOI.class);
            favoritePOI.setTowardsAngle(jSONObject.optString("towards_angle"));
            favoritePOI.setParent(jSONObject.optString("parent"));
            if (jSONObject.has("childType")) {
                favoritePOI.setChildType(jSONObject.optString("childType"));
            } else if (jSONObject.has("childtype")) {
                favoritePOI.setChildType(jSONObject.optString("childtype"));
            }
            favoritePOI.setFnona(jSONObject.optString("f_nona"));
            ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            iSearchPoiData.setTowardsAngle(jSONObject.optString("towards_angle"));
            iSearchPoiData.setParent(jSONObject.optString("parent"));
            if (jSONObject.has("childType")) {
                iSearchPoiData.setChildType(jSONObject.optString("childType"));
            } else if (jSONObject.has("childtype")) {
                iSearchPoiData.setChildType(jSONObject.optString("childtype"));
            }
            iSearchPoiData.setFnona(jSONObject.optString("f_nona"));
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
        return createPOI;
    }

    public static POI a(JSONObject jSONObject, String str) {
        String e = agd.e(jSONObject, str);
        POI poi = null;
        if (TextUtils.isEmpty(e)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(e);
            if (jSONArray.length() <= 0) {
                return null;
            }
            POI b = b(jSONArray.get(0).toString());
            try {
                if (c(b)) {
                    if (DriveUtil.getPOIHome() != null) {
                        poi = DriveUtil.getPOIHome();
                        poi.getPoiExtra().put("poi_type", "1");
                        return poi;
                    }
                } else if (d(b) && DriveUtil.getPOICompany() != null) {
                    POI pOICompany = DriveUtil.getPOICompany();
                    try {
                        pOICompany.getPoiExtra().put("poi_type", "2");
                        return pOICompany;
                    } catch (JSONException e2) {
                        e = e2;
                        poi = pOICompany;
                        e.printStackTrace();
                        return poi;
                    }
                }
                return b;
            } catch (JSONException e3) {
                poi = b;
                e = e3;
                e.printStackTrace();
                return poi;
            }
        } catch (JSONException e4) {
            e = e4;
        }
    }

    public static ArrayList<POI> b(JSONObject jSONObject, String str) {
        ArrayList<POI> arrayList = new ArrayList<>();
        String e = agd.e(jSONObject, str);
        if (!TextUtils.isEmpty(e)) {
            try {
                JSONArray jSONArray = new JSONArray(e);
                if (jSONArray.length() > 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        POI b = b(jSONArray.get(i).toString());
                        if (b != null) {
                            if (c(b)) {
                                if (DriveUtil.getPOIHome() != null) {
                                    b = DriveUtil.getPOIHome();
                                    b.getPoiExtra().put("poi_type", "1");
                                }
                            } else if (d(b) && DriveUtil.getPOICompany() != null) {
                                b = DriveUtil.getPOICompany();
                                b.getPoiExtra().put("poi_type", "2");
                            }
                            arrayList.add(b);
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public static JSONObject a(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
            try {
                String e = agd.e(jSONObject.getJSONObject("voiceCommandResponse"), "voiceResult");
                if (!TextUtils.isEmpty(e)) {
                    return new JSONObject(e);
                }
                bfp.a(a.a, 1, "voiceResult空");
                return null;
            } catch (JSONException e2) {
                e = e2;
                bfq bfq = a.a;
                StringBuilder sb = new StringBuilder("获取串异常");
                sb.append(e.getMessage());
                bfp.a(bfq, 1, sb.toString());
                e.printStackTrace();
                return jSONObject;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            bfq bfq2 = a.a;
            StringBuilder sb2 = new StringBuilder("获取串异常");
            sb2.append(e.getMessage());
            bfp.a(bfq2, 1, sb2.toString());
            e.printStackTrace();
            return jSONObject;
        }
    }

    public static boolean a(POI poi) {
        return poi != null && c(poi) && DriveUtil.getPOIHome() == null;
    }

    public static boolean b(POI poi) {
        return poi != null && d(poi) && DriveUtil.getPOICompany() == null;
    }

    public static boolean a(POI poi, POI poi2, List<POI> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                POI poi3 = list.get(i);
                if (DriveUtil.isSamePoi(poi, poi3) || DriveUtil.isSamePoi(poi2, poi3)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean a(List<POI> list) {
        int i = 0;
        while (i < list.size() - 1) {
            POI poi = list.get(i);
            i++;
            int i2 = i;
            while (true) {
                if (i2 < list.size()) {
                    if (DriveUtil.isSamePoi(poi, list.get(i2))) {
                        return true;
                    }
                    i2++;
                }
            }
        }
        return false;
    }

    private static boolean c(POI poi) {
        if (poi != null && poi.getPoiExtra() != null && poi.getPoiExtra().size() > 0 && poi.getPoiExtra().containsKey("poi_type") && poi.getPoiExtra().get("poi_type").equals("1")) {
            return true;
        }
        return false;
    }

    private static boolean d(POI poi) {
        if (poi != null && poi.getPoiExtra() != null && poi.getPoiExtra().size() > 0 && poi.getPoiExtra().containsKey("poi_type") && poi.getPoiExtra().get("poi_type").equals("2")) {
            return true;
        }
        return false;
    }
}
