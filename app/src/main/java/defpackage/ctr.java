package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ctr reason: default package */
/* compiled from: ShowActivityAction */
public class ctr extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        ctl ctl = (ctl) a.a.a(ctl.class);
        bid bid = a().mPageContext;
        if ((bid instanceof AbstractBasePage) && ctl != null) {
            ctl.a((AbstractBasePage) bid, jSONObject.optString("type"), jSONObject.optString("scheme"));
        }
    }
}
