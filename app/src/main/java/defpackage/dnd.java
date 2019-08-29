package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.model.POI;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnd reason: default package */
/* compiled from: OpenMovieShowingsAction */
public class dnd extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null && a.getBundle() != null) {
            String movieId = a.getMovieId();
            if (!"".equals(movieId)) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("_action", waVar.b);
                    jSONObject2.put("movieID", movieId);
                    jSONObject2.put("poiID", ((POI) a.getBundle().getObject("POI")).getId());
                    jSONObject2.put("hasGroupBuy", a.getIsGroupBuy());
                    a.callJs(waVar.a, jSONObject2.toString());
                } catch (JSONException e) {
                    kf.a((Throwable) e);
                }
            }
        }
    }
}
