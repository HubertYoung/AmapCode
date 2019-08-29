package defpackage;

import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.js.action.OpenPoiAction$1;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.DetailMpsRequest;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: byp reason: default package */
/* compiled from: OpenPoiAction */
public class byp extends wb {
    public final boolean b() {
        return true;
    }

    private static void a(String str) {
        WebStorage a = bic.a((String) "poi_info");
        a.beginTransaction();
        a.set("CURRENT_BUS_ALIAS", str);
        a.commit();
    }

    /* access modifiers changed from: private */
    public static void b(JsAdapter jsAdapter, int i, PageBundle pageBundle) {
        if (i == 1) {
            pageBundle.putInt("poi_detail_page_type", 1);
        } else {
            pageBundle.putInt("poi_detail_page_type", 4);
        }
        if (jsAdapter != null && jsAdapter.mPageContext != null) {
            jsAdapter.mPageContext.startPage((String) "amap.search.action.poidetail", pageBundle);
        }
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                int optInt = jSONObject.optInt("HTML5_TYPE");
                String optString = jSONObject.optString("HTML5_POI_INFO");
                boolean z = false;
                int optInt2 = jSONObject.optInt("status", 0);
                int optInt3 = jSONObject.optInt("floor");
                int optInt4 = jSONObject.optInt(IndoorLocationProvider.NAME, 0);
                boolean optBoolean = jSONObject.optBoolean("showIndoorMap");
                JSONObject jSONObject2 = jSONObject.getJSONObject("poiInfo");
                POI createPOI = POIFactory.createPOI();
                if (jSONObject2 != null) {
                    createPOI = kv.a(jSONObject2.toString());
                }
                if (a() != null && a().mPageContext != null && aaw.c(a().mPageContext.getContext()) && !TextUtils.isEmpty(createPOI.getId()) && (TextUtils.isEmpty(createPOI.getName()) || TextUtils.isEmpty(createPOI.getType()))) {
                    z = true;
                }
                if (!z) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("isFromWeb", true);
                    pageBundle.putInt("status", optInt2);
                    pageBundle.putInt("floor", optInt3);
                    pageBundle.putBoolean("showIndoorMap", optBoolean);
                    pageBundle.putInt("html_type", optInt);
                    if (optInt == 1) {
                        JSONObject jSONObject3 = new JSONObject(optString);
                        ChildStationPoiData childStationPoiData = (ChildStationPoiData) POIFactory.createPOI(ChildStationPoiData.class);
                        childStationPoiData.setBuslineIds(jSONObject3.optString("businfo_lineids"));
                        childStationPoiData.getPoiExtra().put("businfo_lineids", childStationPoiData.getBuslineIds());
                        childStationPoiData.setBuslineName(jSONObject3.optString("businfo_line_name"));
                        childStationPoiData.setName(jSONObject3.optString("name"));
                        childStationPoiData.setBuslineKey(jSONObject3.optString("businfo_line_key"));
                        childStationPoiData.setAddr(childStationPoiData.getBuslineKey());
                        childStationPoiData.setBusAngle(jSONObject3.optString("businfo_angle"));
                        childStationPoiData.setBusinfoAlias(jSONObject3.optString("bus_alias"));
                        a(jSONObject3.optString("bus_alias"));
                        childStationPoiData.setBusinfoStationids(jSONObject3.optString("businfo_stationids"));
                        childStationPoiData.setType(jSONObject3.optString("new_type"));
                        childStationPoiData.setPoint(new GeoPoint(Integer.parseInt(jSONObject3.optString(DictionaryKeys.CTRLXY_X)), Integer.parseInt(jSONObject3.optString(DictionaryKeys.CTRLXY_Y))));
                        childStationPoiData.setPoiId(jSONObject3.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                        childStationPoiData.setId(childStationPoiData.getPoiId());
                        childStationPoiData.setPoiId2(jSONObject3.optString("poiid2"));
                        childStationPoiData.getPoiExtra().put("child_stations", new JSONArray().put(jSONObject3).toString());
                        pageBundle.putSerializable("POI", childStationPoiData);
                    } else {
                        pageBundle.putSerializable("POI", createPOI);
                        pageBundle.putInt(IndoorLocationProvider.NAME, optInt4);
                        a("");
                    }
                    b(a, optInt2, pageBundle);
                } else if (createPOI != null) {
                    DetailMpsRequest detailMpsRequest = new DetailMpsRequest();
                    detailMpsRequest.b = createPOI.getId();
                    detailMpsRequest.d = "true";
                    detailMpsRequest.n = "2.16";
                    detailMpsRequest.d = "true";
                    detailMpsRequest.f = "301000";
                    detailMpsRequest.e = "5";
                    detailMpsRequest.h = AppManager.getInstance().getUserLocInfo();
                    detailMpsRequest.i = String.valueOf(LocationInstrument.getInstance().getLatestPosition().getAdCode());
                    PoiRequestHolder instance = PoiRequestHolder.getInstance();
                    OpenPoiAction$1 openPoiAction$1 = new OpenPoiAction$1(this, optInt2, optInt4, optInt3, optBoolean);
                    instance.sendDetailMps(detailMpsRequest, openPoiAction$1);
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
