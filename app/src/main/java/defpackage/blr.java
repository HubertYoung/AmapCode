package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import org.json.JSONObject;

/* renamed from: blr reason: default package */
/* compiled from: SetSoftInputModeAction */
public class blr extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                int optInt = jSONObject.optInt("value", -1);
                if (optInt != -1) {
                    bid bid = a.mPageContext;
                    if (bid instanceof AbstractBasePage) {
                        ((AbstractBasePage) bid).setSoftInputMode(optInt);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
