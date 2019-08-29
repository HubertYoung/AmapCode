package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ctn reason: default package */
/* compiled from: CancelFetchActivityAction */
public class ctn extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        ctl ctl = (ctl) a.a.a(ctl.class);
        if (a().mPageContext instanceof AbstractBasePage) {
            a();
            ctl.a(jSONObject.optString("type"));
        }
    }
}
