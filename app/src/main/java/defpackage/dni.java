package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import org.json.JSONObject;

/* renamed from: dni reason: default package */
/* compiled from: StartNaviAction */
public class dni extends wb {
    public final boolean b() {
        return true;
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        JSONObject optJSONObject = jSONObject.optJSONObject("path");
        if (optJSONObject != null) {
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.a(optJSONObject.toString());
            }
            return;
        }
        if (a() != null) {
            POI a = kv.a(jSONObject.optJSONObject("poiInfo").toString());
            String optString = jSONObject.optString("navi_type");
            boolean optBoolean = jSONObject.optBoolean("need_backprev");
            if (!TextUtils.isEmpty(optString)) {
                if (!(a != null || a() == null || a().getBundle() == null)) {
                    a = (POI) a().getBundle().getObject("POI");
                }
                dfm dfm2 = (dfm) ank.a(dfm.class);
                if (dfm2 != null) {
                    dfm2.a(a, optString, optBoolean);
                }
                return;
            }
            if (!(a != null || a() == null || a().getBundle() == null)) {
                a = (POI) a().getBundle().getObject("POI");
            }
            dfm dfm3 = (dfm) ank.a(dfm.class);
            if (dfm3 != null) {
                dfm3.a(a);
            }
        }
    }
}
