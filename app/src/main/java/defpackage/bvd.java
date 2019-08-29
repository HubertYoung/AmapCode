package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiWebImageTemplate;
import com.autonavi.bundle.searchservice.utils.PoiArrayTemplate;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bvd reason: default package */
/* compiled from: SearchResultAjxBundle */
public final class bvd {
    static final /* synthetic */ boolean a = true;

    public static JSONObject a(InfoliteResult infoliteResult, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        a(infoliteResult);
        jSONObject.put(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, b(infoliteResult));
        jSONObject.put("initState", str);
        jSONObject.put("title", infoliteResult.mKeyword);
        jSONObject.put("buildingName", elc.e);
        int i = infoliteResult.responseHeader.f ? -1 : aey.d() ? 1 : 2;
        jSONObject.put("offlineType", i);
        return jSONObject;
    }

    public static JSONObject a(InfoliteResult infoliteResult, AbstractBaseMapPage abstractBaseMapPage) throws JSONException {
        a(infoliteResult);
        JSONObject jSONObject = new JSONObject();
        SearchPoi searchPoi = (SearchPoi) infoliteResult.searchInfo.l.get(0).as(SearchPoi.class);
        boolean n = bcy.n(infoliteResult);
        auk auk = infoliteResult.searchInfo.a;
        JSONObject jSONObject2 = new JSONObject();
        HashMap<String, Serializable> poiExtra = searchPoi.getPoiExtra();
        if (poiExtra != null) {
            Serializable serializable = poiExtra.get("is_gpspoint");
            if (serializable != null) {
                jSONObject2.put("isGpspoint", serializable);
            }
            Serializable serializable2 = poiExtra.get("businfo_lineids");
            if (serializable2 != null) {
                jSONObject2.put("businfoLineids", serializable2);
            }
        }
        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, searchPoi.getId());
        jSONObject2.put("name", searchPoi.getName());
        jSONObject2.put("address", searchPoi.getAddr());
        jSONObject2.put("phoneNumbers", searchPoi.getPhone());
        jSONObject2.put("new_type", searchPoi.getType());
        if (n) {
            jSONObject2.put("distance", SearchUtils.formatDistance(AMapAppGlobal.getApplication(), searchPoi));
        }
        GeoPoint point = searchPoi.getPoint();
        if (point != null) {
            jSONObject2.put(DictionaryKeys.CTRLXY_X, point.x);
            jSONObject2.put(DictionaryKeys.CTRLXY_Y, point.y);
            jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, point.getLongitude());
            jSONObject2.put("lat", point.getLatitude());
        }
        ArrayList<GeoPoint> entranceList = searchPoi.getEntranceList();
        GeoPoint geoPoint = entranceList != null && !entranceList.isEmpty() ? searchPoi.getEntranceList().get(0) : null;
        if (geoPoint != null) {
            jSONObject2.put("navi_lng", geoPoint.getLongitude());
            jSONObject2.put("navi_lat", geoPoint.getLatitude());
        }
        jSONObject2.put("towards_angle", searchPoi.getTowardsAngle());
        jSONObject2.put("end_poi_extension", searchPoi.getEndPoiExtension());
        jSONObject2.put(H5Param.LONG_TRANSPARENT, searchPoi.getTransparent());
        jSONObject2.put("parent", searchPoi.getParent());
        jSONObject2.put("childType", searchPoi.getChildType());
        jSONObject2.put("fNona", searchPoi.getFnona());
        jSONObject2.put("cityCode", searchPoi.getCityCode());
        jSONObject2.put("industry", searchPoi.getIndustry());
        jSONObject2.put(AutoJsonUtils.JSON_ADCODE, searchPoi.getAdCode());
        jSONObject2.put("isCurrentCity", auk.p);
        jSONObject.put("poi", jSONObject2);
        JSONObject a2 = a(searchPoi);
        if (a2 != null) {
            jSONObject.put("tipDomainList", a2);
        }
        jSONObject.put("clientData", new bxw(searchPoi).a(new PageBundle(), abstractBaseMapPage, searchPoi));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("modelNames", infoliteResult.searchInfo.y);
        jSONObject3.put("interfaceResultList", infoliteResult.searchInfo.z);
        jSONObject.put("industry", jSONObject3);
        return jSONObject;
    }

    private static void a(InfoliteResult infoliteResult) {
        if (!a && infoliteResult == null) {
            throw new AssertionError();
        } else if (!a && infoliteResult.searchInfo == null) {
            throw new AssertionError();
        } else if (!a && infoliteResult.searchInfo.a == null) {
            throw new AssertionError();
        } else if (!a && infoliteResult.searchInfo.l == null) {
            throw new AssertionError();
        } else if (!a && infoliteResult.searchInfo.l.size() <= 0) {
            throw new AssertionError();
        }
    }

    private static JSONObject a(SearchPoi searchPoi) throws JSONException {
        String str;
        List<PoiLayoutTemplate> templateData = searchPoi.getTemplateData();
        if (templateData == null || templateData.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (PoiLayoutTemplate poiLayoutTemplate : templateData) {
            if (poiLayoutTemplate != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("id", poiLayoutTemplate.getId());
                String name = poiLayoutTemplate.getName();
                if (!TextUtils.isEmpty(name)) {
                    jSONObject2.put("name", name);
                }
                String type = poiLayoutTemplate.getType();
                if (!TextUtils.isEmpty(type)) {
                    jSONObject2.put("type", type);
                }
                if (poiLayoutTemplate instanceof PoiWebImageTemplate) {
                    str = ((PoiWebImageTemplate) poiLayoutTemplate).getOriginalValue();
                } else {
                    str = poiLayoutTemplate.getValue();
                }
                if (!TextUtils.isEmpty(str)) {
                    jSONObject2.put("value", str);
                }
                if (poiLayoutTemplate instanceof PoiButtonTemplate) {
                    String action = ((PoiButtonTemplate) poiLayoutTemplate).getAction();
                    if (!TextUtils.isEmpty(action)) {
                        jSONObject2.put("action", action);
                    }
                }
                if (poiLayoutTemplate instanceof PoiImageTemplate) {
                    String srcValue = ((PoiImageTemplate) poiLayoutTemplate).getSrcValue();
                    if (!TextUtils.isEmpty(srcValue)) {
                        jSONObject2.put("src", srcValue);
                    }
                }
                if (poiLayoutTemplate instanceof PoiArrayTemplate) {
                    PoiArrayTemplate poiArrayTemplate = (PoiArrayTemplate) poiLayoutTemplate;
                    String poiids = poiArrayTemplate.getPoiids();
                    if (!TextUtils.isEmpty(poiids)) {
                        jSONObject2.put("poiids", poiids);
                    }
                    String tags = poiArrayTemplate.getTags();
                    if (!TextUtils.isEmpty(tags)) {
                        jSONObject2.put("tags", tags);
                    }
                    String childType = poiArrayTemplate.getChildType();
                    if (!TextUtils.isEmpty(childType)) {
                        jSONObject2.put("childtype", childType);
                    }
                    String poiName = poiArrayTemplate.getPoiName();
                    if (!TextUtils.isEmpty(poiName)) {
                        jSONObject2.put("poiname", poiName);
                    }
                    String tagColors = poiArrayTemplate.getTagColors();
                    if (!TextUtils.isEmpty(tagColors)) {
                        jSONObject2.put("tag_colors", tagColors);
                    }
                    String poiids2 = poiArrayTemplate.getPoiids();
                    if (!TextUtils.isEmpty(poiids2)) {
                        jSONObject2.put("shortname", poiids2);
                    }
                }
                jSONObject.put(String.valueOf(poiLayoutTemplate.getId()), jSONObject2);
            }
        }
        return jSONObject;
    }

    private static JSONObject b(InfoliteResult infoliteResult) {
        if (!bcy.b(infoliteResult)) {
            return null;
        }
        try {
            if (infoliteResult.responseHeader.f) {
                return new JSONObject(infoliteResult.originalJson);
            }
            return c(infoliteResult);
        } catch (JSONException unused) {
            return null;
        }
    }

    private static JSONObject c(InfoliteResult infoliteResult) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator<POI> it = infoliteResult.searchInfo.l.iterator();
        while (it.hasNext()) {
            POI next = it.next();
            if (next != null) {
                SearchPoi searchPoi = (SearchPoi) next.as(SearchPoi.class);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("name", searchPoi.getName());
                jSONObject2.put("id", searchPoi.getId());
                jSONObject2.put("address", searchPoi.getAddr());
                jSONObject2.put(AutoJsonUtils.JSON_ADCODE, searchPoi.getAdCode());
                jSONObject2.put("type_code", searchPoi.getType());
                jSONObject2.put("tel", searchPoi.getPhone());
                if (searchPoi.getPoint() != null) {
                    jSONObject2.put("latitude", searchPoi.getPoint().getLatitude());
                    jSONObject2.put("longitude", searchPoi.getPoint().getLongitude());
                }
                bxz.a(AMapAppGlobal.getApplication(), searchPoi);
                if (searchPoi.getTemplateDataMap() != null) {
                    JSONArray jSONArray2 = new JSONArray();
                    for (Entry value : searchPoi.getTemplateDataMap().entrySet()) {
                        JSONObject jSONObject3 = new JSONObject();
                        PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) value.getValue();
                        if (poiLayoutTemplate != null) {
                            jSONObject3.put("id", poiLayoutTemplate.getId());
                            jSONObject3.put("name", poiLayoutTemplate.getName());
                            jSONObject3.put("type", poiLayoutTemplate.getType());
                            jSONObject3.put("value", poiLayoutTemplate.getValue());
                            if (poiLayoutTemplate instanceof PoiButtonTemplate) {
                                jSONObject3.put("action", ((PoiButtonTemplate) poiLayoutTemplate).getAction());
                            }
                            jSONArray2.put(jSONObject3);
                        }
                    }
                    jSONObject2.put("domain_list", jSONArray2);
                }
                jSONArray.put(jSONObject2);
            }
        }
        jSONObject.put("poi_list", jSONArray);
        return jSONObject;
    }
}
