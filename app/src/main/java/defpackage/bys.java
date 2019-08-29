package defpackage;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.js.action.SearchSuggestAction$1;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.server.request.AosInputSuggestionParam;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bys reason: default package */
/* compiled from: SearchSuggestAction */
public class bys extends wb {
    public final boolean b() {
        return true;
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        GeoPoint geoPoint;
        boolean optBoolean = jSONObject.optBoolean("is_from_around");
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            geoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
        } else {
            geoPoint = new GeoPoint();
        }
        AosInputSuggestionParam aosInputSuggestionParam = new AosInputSuggestionParam(jSONObject.optString(TrafficUtil.KEYWORD), jSONObject.optString("city_code"), AppManager.getInstance().getUserLocInfo(), String.valueOf(SearchUtils.getLatestPositionAdCode()), null, "poi|bus", optBoolean, DoNotUseTool.getPixel20Bound(), geoPoint.x, geoPoint.y);
        aosInputSuggestionParam.filter_result_type = 1;
        AosPostRequest b = aax.b(aosInputSuggestionParam);
        b.addReqParam("version", "2.13");
        yq.a();
        yq.a((AosRequest) b, (AosResponseCallback<T>) new SearchSuggestAction$1<T>(this, waVar));
    }

    public static /* synthetic */ void a(bys bys, JSONObject jSONObject, wa waVar) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("status", 1);
            jSONObject2.put("content", jSONObject);
            bys.a().callJs(waVar.a, jSONObject2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ void a(bys bys, Exception exc, wa waVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("status", 2);
            jSONObject.put("error", exc.getMessage());
            bys.a().callJs(waVar.a, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
