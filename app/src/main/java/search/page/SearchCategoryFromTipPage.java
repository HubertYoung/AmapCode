package search.page;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.searcharound.ajx.ModuleSearchAround;
import com.autonavi.bundle.searcharound.ajx.ModuleSearchAround.a;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.search.action.category")
public class SearchCategoryFromTipPage extends Ajx3Page implements a {
    private String a;

    public void onCreate(Context context) {
        bbx.a((bid) this);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String a2 = a(context);
            this.a = arguments.getString("feed_transparent");
            arguments.putString("url", "path://amap_bundle_search_around/src/app.js");
            arguments.putString("jsData", a2);
        }
        super.onCreate(context);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        ModuleSearchAround moduleSearchAround = (ModuleSearchAround) this.mAjxView.getJsModule(ModuleSearchAround.MODULE_NAME);
        if (moduleSearchAround != null) {
            moduleSearchAround.setModuleProxy(this);
        }
    }

    public void destroy() {
        super.destroy();
        bbx.b(this);
    }

    private String a(Context context) {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            return null;
        }
        POI poi = (POI) arguments.getObject("POI");
        if (poi == null) {
            return null;
        }
        int i = arguments.getInt("key_source_type", 0);
        boolean z = !TextUtils.isEmpty(poi.getId());
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
            if (poi.getPoiExtra().get("is_gpspoint") == null || !((Boolean) poi.getPoiExtra().get("is_gpspoint")).booleanValue()) {
                jSONObject2.put("name", poi.getName());
            } else {
                jSONObject2.put("name", context.getString(R.string.search_from_around_gps_name));
            }
            if (poi.getPoint() != null) {
                jSONObject2.put(DictionaryKeys.CTRLXY_X, poi.getPoint().getLongitude());
                jSONObject2.put(DictionaryKeys.CTRLXY_Y, poi.getPoint().getLatitude());
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, poi.getPoint().getAdCode());
            }
            jSONObject.put("poiInfo", jSONObject2);
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                jSONObject.put("user_x", latestPosition.getLatitude());
                jSONObject.put("user_y", latestPosition.getLongitude());
                jSONObject.put("user_city", latestPosition.getAdCode());
            }
            jSONObject.put("geoobj", bbw.a(getMapView().H()));
            jSONObject.put("source_type", i);
            jSONObject.put("need_request", z ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a(String str) {
        POI b = b(str);
        if (b != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_type", 1);
            pageBundle.putString("from_page", "220000");
            pageBundle.putObject("POI", b);
            pageBundle.putObject("feed", this.a);
            pageBundle.putBoolean("draw_center", true);
            startPage((String) "amap.search.action.arround", pageBundle);
        }
    }

    private static POI b(String str) {
        POI createPOI = POIFactory.createPOI();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(DictionaryKeys.CTRLXY_X) && jSONObject.has(DictionaryKeys.CTRLXY_Y) && jSONObject.has(LocationInstrument.LOCATION_EXTRAS_KEY_POIID)) {
                if (jSONObject.has("name")) {
                    createPOI.getPoint().setLonLat(jSONObject.getDouble(DictionaryKeys.CTRLXY_X), jSONObject.getDouble(DictionaryKeys.CTRLXY_Y));
                    createPOI.setId(jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                    createPOI.setName(jSONObject.optString("name"));
                    return createPOI;
                }
            }
            return null;
        } catch (JSONException unused) {
            return null;
        }
    }
}
