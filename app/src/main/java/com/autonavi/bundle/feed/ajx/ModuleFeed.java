package com.autonavi.bundle.feed.ajx;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import org.json.JSONObject;

@AjxModule("feed")
public final class ModuleFeed extends AbstractModule {
    static final String MODULE_NAME = "feed";
    private static final String TAG = "ModuleFeed";

    public ModuleFeed(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("openInput")
    public final void openInput(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String str2 = "";
            String str3 = "";
            String str4 = "";
            POI poi = null;
            if (jSONObject.has("title")) {
                str4 = jSONObject.getString("title");
            }
            if (jSONObject.has("centername")) {
                str3 = jSONObject.getString("centername");
            }
            boolean z = jSONObject.has("canSearchHint") ? jSONObject.getBoolean("canSearchHint") : false;
            if (z) {
                str2 = str4;
            }
            boolean z2 = jSONObject.getBoolean("isCache");
            if (z2) {
                poi = POIFactory.createPOI(str3, new GeoPoint(((IMainMapService) ank.a(IMainMapService.class)).g().n()));
            } else if (jSONObject.has("center_coords")) {
                String[] split = jSONObject.getString("center_coords").split(",");
                poi = POIFactory.createPOI(str3, new GeoPoint().setLonLat(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
            }
            ((bbi) a.a.a(bbi.class)).a().b((String) "620000").b(z2).c(true).b().a().c(str2).a(z).a(poi).a(AMapPageUtil.getPageContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
