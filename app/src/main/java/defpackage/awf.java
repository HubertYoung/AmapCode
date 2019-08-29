package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: awf reason: default package */
/* compiled from: ReverseGeocodeParser */
public final class awf {
    public static awg a(JSONObject jSONObject) {
        awg awg = new awg();
        if (jSONObject != null) {
            try {
                awg.h = jSONObject.optString("pos");
                awg.d = jSONObject.optString("province");
                awg.e = jSONObject.optString("city");
                awg.f = jSONObject.optString(AutoJsonUtils.JSON_ADCODE);
                awg.i = jSONObject.optString("district");
                awg.c = jSONObject.optString("desc");
                awg.a = jSONObject.optString("cityadcode");
                awg.g = jSONObject.optString("areacode");
                if (jSONObject.has("poi_list")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("poi_list");
                    if (jSONArray != null) {
                        int length = jSONArray.length();
                        if (length > 0) {
                            for (int i = 0; i < length; i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                POI createPOI = POIFactory.createPOI(jSONObject2.getString("name"), new GeoPoint(jSONObject2.getDouble("longitude"), jSONObject2.getDouble("latitude")));
                                createPOI.setId(jSONObject2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                                createPOI.setEndPoiExtension(jSONObject2.optString("end_poi_extension"));
                                createPOI.setTransparent(jSONObject2.optString(H5Param.LONG_TRANSPARENT));
                                awg.b.add(createPOI);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return awg;
    }
}
