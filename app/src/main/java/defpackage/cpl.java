package defpackage;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpl reason: default package */
/* compiled from: SaveBeanJsonUtils */
public final class cpl {
    private static POI a(JSONObject jSONObject, String str) {
        JSONObject jSONObject2;
        if (jSONObject == null) {
            return null;
        }
        try {
            jSONObject2 = jSONObject.getJSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject2 = null;
        }
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
    }

    public static bti a(int i, JSONObject jSONObject, String str) {
        bti bti;
        if (jSONObject == null) {
            return null;
        }
        bti bti2 = new bti();
        bti2.c = i;
        switch (i) {
            case 0:
                bti = b(jSONObject, bti2, str);
                bti.c = i;
                break;
            case 1:
                bti = d(jSONObject, bti2, str);
                bti.c = i;
                break;
            case 2:
                bti = c(jSONObject, bti2, str);
                bti.c = i;
                break;
            case 3:
                bti = e(jSONObject, bti2, str);
                bti.c = i;
                break;
            default:
                return null;
        }
        return bti;
    }

    private static bti b(JSONObject jSONObject, bti bti, String str) {
        f(jSONObject, bti, str);
        asy asy = (asy) a.a.a(asy.class);
        if (asy != null) {
            bti.a(0, asy.c().a(jSONObject));
        }
        return bti;
    }

    private static bti c(JSONObject jSONObject, bti bti, String str) {
        f(jSONObject, bti, str);
        atb atb = (atb) a.a.a(atb.class);
        if (atb != null) {
            bti.a(2, atb.e().a(jSONObject));
        }
        return bti;
    }

    private static bti d(JSONObject jSONObject, bti bti, String str) {
        f(jSONObject, bti, str);
        dhz dhz = (dhz) ank.a(dhz.class);
        if (dhz != null) {
            bti.a(1, dhz.a(jSONObject, true));
        }
        return bti;
    }

    private static bti e(JSONObject jSONObject, bti bti, String str) {
        if (jSONObject == null) {
            return null;
        }
        f(jSONObject, bti, str);
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            bti.a(3, avi.b().a(jSONObject, bti.w, bti.x, bti.l));
        }
        return bti;
    }

    public static void a(JSONObject jSONObject, bti bti, String str) {
        agd.a(jSONObject, (String) "type", 1);
        agd.a(jSONObject, (String) "version", bti.i);
        agd.a(jSONObject, (String) "id", str);
        agd.a(jSONObject, (String) "route_type", bti.c);
        Long valueOf = Long.valueOf(0);
        if (bti.s != null) {
            valueOf = bti.s;
        }
        agd.a(jSONObject, (String) "create_time", String.valueOf(((double) valueOf.longValue()) / 1000.0d));
        if (bti.c == 1) {
            agd.a(jSONObject, (String) "method", dhw.b(bti.h));
        } else {
            agd.a(jSONObject, (String) "method", bti.h);
        }
        agd.a(jSONObject, (String) "start_x", bti.d);
        agd.a(jSONObject, (String) "start_y", bti.e);
        agd.a(jSONObject, (String) "end_x", bti.f);
        agd.a(jSONObject, (String) "end_y", bti.g);
        agd.a(jSONObject, (String) "route_name", bti.j);
        agd.a(jSONObject, (String) "route_len", bti.l);
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, bti.u);
        agd.a(jSONObject, (String) "route_alias", bti.q);
        a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_FROM_POI, bti.a());
        a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_TO_POI, bti.b());
        try {
            jSONObject.put("has_mid_poi", bti.p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bti.p) {
            agd.a(jSONObject, (String) "mid_pois", bti.o);
        }
    }

    private static void f(JSONObject jSONObject, bti bti, String str) {
        bti.i = agd.e(jSONObject, "version");
        bti.a = str;
        if (bti.c == 1) {
            bti.h = dhw.a(agd.a(jSONObject, "method"));
        } else {
            bti.h = agd.e(jSONObject, "method");
        }
        bti.d = agd.a(jSONObject, "start_x");
        bti.e = agd.a(jSONObject, "start_y");
        bti.f = agd.a(jSONObject, "end_x");
        bti.g = agd.a(jSONObject, "end_y");
        bti.j = agd.e(jSONObject, "route_name");
        bti.s = Long.valueOf((long) (Math.max(0.0d, agd.b(jSONObject, "create_time")) * 1000.0d));
        bti.l = agd.a(jSONObject, "route_len");
        bti.u = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME);
        bti.q = agd.e(jSONObject, "route_alias");
        bti.a(a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_FROM_POI));
        bti.b(a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_TO_POI));
        bti.p = agd.d(jSONObject, "has_mid_poi");
        if (bti.p) {
            if (jSONObject.has("mid_pois")) {
                bti.o = agd.e(jSONObject, "mid_pois");
            } else if (jSONObject.has("mid_poi")) {
                ArrayList arrayList = new ArrayList();
                POI a = a(jSONObject, "mid_poi");
                if (a != null) {
                    arrayList.add(a);
                    bti.b(arrayList);
                }
            }
        }
    }

    private static void a(JSONObject jSONObject, String str, POI poi) {
        if (poi != null && jSONObject != null) {
            JSONObject jSONObject2 = new JSONObject();
            agd.a(jSONObject2, (String) "mId", poi.getId());
            agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
            agd.a(jSONObject2, (String) "mAddr", poi.getAddr());
            agd.a(jSONObject2, (String) "mCityCode", poi.getCityCode());
            agd.a(jSONObject2, (String) "mCityName", poi.getCityName());
            agd.a(jSONObject2, (String) "mx", poi.getPoint().x);
            agd.a(jSONObject2, (String) "my", poi.getPoint().y);
            agd.a(jSONObject2, (String) "mType", poi.getType());
            agd.a(jSONObject2, (String) "mEndPoiExtension", poi.getEndPoiExtension());
            agd.a(jSONObject2, (String) "mTransparent", poi.getTransparent());
            try {
                ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                if (entranceList != null && entranceList.size() > 0) {
                    JSONArray jSONArray = new JSONArray();
                    int size = entranceList.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject jSONObject3 = new JSONObject();
                        GeoPoint geoPoint = entranceList.get(i);
                        jSONObject3.put("mEntranceX", geoPoint.x);
                        jSONObject3.put("mEntranceY", geoPoint.y);
                        jSONArray.put(i, jSONObject3);
                    }
                    agd.a(jSONObject2, (String) "mEntranceList", jSONArray.toString());
                }
                jSONObject.put(str, jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
