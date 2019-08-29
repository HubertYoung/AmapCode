package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: bkz reason: default package */
/* compiled from: GetPositionAction */
public class bkz extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("_action", waVar.b);
                    jSONObject2.put("longitude", a2.x);
                    jSONObject2.put("latitude", a2.y);
                    a.callJs(waVar.b, jSONObject2.toString());
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
