package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnh reason: default package */
/* compiled from: ShowPanellistAction */
public class dnh extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("list");
            String optString = jSONObject.optString("action");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                int length = optJSONArray.length();
                ArrayList arrayList = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    try {
                        if (optJSONArray.optString(i).contains("title")) {
                            String string = optJSONArray.getJSONObject(i).getString("content");
                            StringBuilder sb = new StringBuilder();
                            sb.append(optJSONArray.getJSONObject(i).getString("title"));
                            sb.append("$");
                            String sb2 = sb.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append(string);
                            arrayList.add(sb3.toString());
                        } else {
                            arrayList.add(optJSONArray.optString(i));
                        }
                    } catch (JSONException e) {
                        kf.a((Throwable) e);
                    }
                }
                POI poi = null;
                try {
                    if (jSONObject.has("poiInfo")) {
                        JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
                        POI createPOI = POIFactory.createPOI();
                        createPOI.setName(optJSONObject.optString("name", ""));
                        createPOI.setId(optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, ""));
                        createPOI.setType(optJSONObject.optString("new_type", ""));
                        createPOI.setEndPoiExtension(optJSONObject.optString("end_poi_extension", ""));
                        createPOI.setTransparent(optJSONObject.optString(H5Param.LONG_TRANSPARENT, ""));
                        poi = createPOI;
                    }
                } catch (Exception unused) {
                }
                if ("showPanellist".equals(optString) && DoNotUseTool.getActivity() != null) {
                    bnz.a(poi, arrayList, DoNotUseTool.getActivity(), LogConstant.SEARCH_POI_DETAIL);
                }
            }
        }
    }
}
