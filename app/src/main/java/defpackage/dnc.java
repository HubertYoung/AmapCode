package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnc reason: default package */
/* compiled from: OpenMovieDetailAction */
public class dnc extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String movieId = a.getMovieId();
            if (!"".equals(movieId)) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("_action", waVar.b);
                    jSONObject2.put("movieID", movieId);
                    a.callJs(waVar.a, jSONObject2.toString());
                } catch (JSONException e) {
                    kf.a((Throwable) e);
                }
            }
        }
    }
}
