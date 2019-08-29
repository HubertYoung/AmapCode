package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpy reason: default package */
/* compiled from: ErrorReportAction */
public class cpy extends wb {
    public final boolean b() {
        return true;
    }

    private static void a(POI poi, JsAdapter jsAdapter, JSONObject jSONObject, String str) {
        if (jsAdapter != null && jsAdapter.mPageContext != null && jsAdapter.mPageContext.getActivity() != null && jsAdapter.getBundle() != null) {
            if (poi == null) {
                poi = (POI) jsAdapter.getBundle().getObject("POI");
            }
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                if (jsAdapter.getBundle().getBoolean("isFromIndoorMap")) {
                    iErrorReportStarter.startIndoorError(jsAdapter.mPageContext, poi);
                    return;
                }
                String type = poi.getType();
                if (type.equals("150500") || type.equals("150600") || type.equals("151200") || type.equals("150700") || type.equals("151300")) {
                    iErrorReportStarter.startStationError(jsAdapter.mPageContext, poi);
                } else if (str == null || str.equals("")) {
                    iErrorReportStarter.startPOIError(jsAdapter.mPageContext, poi, jSONObject);
                } else if (iErrorReportStarter != null) {
                    int i = -1;
                    if (TextUtils.equals(str, IErrorReportStarter.POI_TYPE_DETAIL_NORMAL)) {
                        i = 0;
                    } else if (TextUtils.equals(str, IErrorReportStarter.POI_TYPE_NON_EXIST)) {
                        i = 33;
                    } else if (TextUtils.equals(str, IErrorReportStarter.POI_TYPE_INVALID_POI)) {
                        i = 32;
                    }
                    new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~feedback_middle_page, start time = ").append(System.currentTimeMillis());
                    iErrorReportStarter.startPoiDetailFeedback(jsAdapter.mPageContext, poi, i, jSONObject);
                }
            }
        }
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        POI poi;
        JsAdapter a = a();
        if (a != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("detail");
                String string = jSONObject2.getString("new_type");
                String optString = jSONObject2.optString("type");
                if (!TextUtils.isEmpty(string) && string.contains(MergeUtil.SEPARATOR_KV)) {
                    string = string.split("\\|")[0];
                }
                poi = POIFactory.createPOI();
                try {
                    if (jSONObject2.has("name")) {
                        poi.setName(jSONObject2.getString("name"));
                    }
                    if (jSONObject2.has("address")) {
                        poi.setAddr(jSONObject2.getString("address"));
                    }
                    if (jSONObject2.has(LocationInstrument.LOCATION_EXTRAS_KEY_POIID)) {
                        poi.setId(jSONObject2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                    }
                    if (jSONObject2.has("telephone")) {
                        poi.setPhone(jSONObject2.getString("telephone"));
                    }
                    if (jSONObject2.has("end_poi_extension")) {
                        poi.setEndPoiExtension(jSONObject2.optString("end_poi_extension"));
                    }
                    if (jSONObject2.has(H5Param.LONG_TRANSPARENT)) {
                        poi.setTransparent(jSONObject2.optString(H5Param.LONG_TRANSPARENT));
                    }
                    if (jSONObject2.has("city_adcode")) {
                        poi.setAdCode(jSONObject2.optString("city_adcode"));
                    }
                    poi.setType(string);
                    if (jSONObject2.has(LocationParams.PARA_FLP_AUTONAVI_LON) && jSONObject2.has("lat")) {
                        poi.setPoint(new GeoPoint(jSONObject2.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject2.getDouble("lat")));
                    }
                    if (jSONObject2.optBoolean("is_gpspoint", false)) {
                        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                        if (iErrorReportStarter != null) {
                            iErrorReportStarter.startLocationError(poi);
                        }
                        return;
                    }
                    if (!string.equals("150500") && !string.equals("150600") && !string.equals("151200") && !string.equals("150700")) {
                        if (!string.equals("151300")) {
                            a(poi, a, jSONObject2, optString);
                            return;
                        }
                    }
                    IErrorReportStarter iErrorReportStarter2 = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter2 != null) {
                        iErrorReportStarter2.startStationError(a.mPageContext, poi, jSONObject2.getString("lines"));
                    }
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                    a(poi, a, jSONObject.optJSONObject("detail"), null);
                }
            } catch (JSONException e2) {
                e = e2;
                poi = null;
                e.printStackTrace();
                a(poi, a, jSONObject.optJSONObject("detail"), null);
            }
        }
    }
}
