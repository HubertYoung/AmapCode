package defpackage;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpk reason: default package */
/* compiled from: RouteItemJsonUtils */
public final class cpk {
    public static void a(JSONObject jSONObject, RouteItem routeItem) {
        routeItem.version = agd.e(jSONObject, "version");
        routeItem.id = agd.e(jSONObject, "id");
        if (routeItem.routeType == 1) {
            routeItem.method = dhw.a(agd.a(jSONObject, "method"));
        } else {
            routeItem.method = agd.e(jSONObject, "method");
        }
        routeItem.startX = agd.a(jSONObject, "start_x");
        routeItem.startY = agd.a(jSONObject, "start_y");
        routeItem.endX = agd.a(jSONObject, "end_x");
        routeItem.endY = agd.a(jSONObject, "end_y");
        routeItem.routeName = agd.e(jSONObject, "route_name");
        routeItem.routeLength = agd.a(jSONObject, "route_len");
        routeItem.routeNote = agd.e(jSONObject, "route_alias");
        routeItem.fromPoi = a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_FROM_POI);
        routeItem.toPoi = a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_TO_POI);
        routeItem.hasMidPoi = agd.d(jSONObject, "has_mid_poi");
        if (routeItem.hasMidPoi) {
            routeItem.midPoi = a(jSONObject, (String) "mid_poi");
        }
    }

    private static POI a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            if (jSONObject2 == null) {
                return null;
            }
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(agd.e(jSONObject2, "mId"));
            createPOI.setType(agd.e(jSONObject2, "mType"));
            createPOI.setName(agd.e(jSONObject2, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            createPOI.setAddr(agd.e(jSONObject2, "mAddr"));
            createPOI.setCityCode(agd.e(jSONObject2, "mCityCode"));
            createPOI.setCityName(agd.e(jSONObject2, "mCityName"));
            createPOI.setEndPoiExtension(agd.e(jSONObject2, "mEndPoiExtension"));
            createPOI.setTransparent(agd.e(jSONObject2, "mTransparent"));
            createPOI.setPoint(new GeoPoint());
            createPOI.getPoint().x = agd.a(jSONObject2, "mx");
            createPOI.getPoint().y = agd.a(jSONObject2, "my");
            return createPOI;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
