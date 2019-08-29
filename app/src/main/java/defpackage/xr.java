package defpackage;

import android.util.SparseArray;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager.a;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: xr reason: default package */
/* compiled from: DeepInfoOverlayManager */
public final class xr implements a {
    public bbp a;
    public POI b;
    public SparseArray<POI> c = new SparseArray<>();
    private boolean d;

    public xr(bty bty) {
        this.a = new bbp(bty);
    }

    public final void a(POI poi) {
        this.b = poi;
        this.a.a(poi, false);
        this.a.a(!this.d);
        ArrayList arrayList = null;
        if (poi != null) {
            if (!(poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null)) {
                arrayList = (ArrayList) poi.getPoiExtra().get("poi_polygon_bounds");
            }
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

    public final void a(int i, POI poi) {
        this.c.put(i, poi);
    }

    public final POI a(int i) {
        POI poi = this.c.get(i);
        this.c.remove(i);
        this.b = null;
        return poi;
    }

    public final void b(int i) {
        this.c.remove(i);
        a(this.c.get(i));
    }

    public final void a() {
        this.a.a();
    }
}
