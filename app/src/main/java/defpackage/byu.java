package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PolygonOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: byu reason: default package */
/* compiled from: MapAoiLayer */
public final class byu extends BaseLayer {
    public LineOverlay a = new LineOverlay(this, "MapAoiLine");
    public PolygonOverlay b = new PolygonOverlay(this, "MapAoiPolygon");
    public POI c;

    public byu(IVPageContext iVPageContext, POI poi) {
        super(iVPageContext);
        this.c = poi;
    }

    public static void a(POI poi) {
        if (poi != null) {
            ArrayList arrayList = (poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null) ? null : (ArrayList) poi.getPoiExtra().get("poi_polygon_bounds");
            if (arrayList != null && !arrayList.isEmpty()) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", poi.getType());
                    jSONObject.put(TrafficUtil.POIID, poi.getId());
                    jSONObject.put("poiName", poi.getName());
                    LogManager.actionLogV2("P00001", "B192", jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
