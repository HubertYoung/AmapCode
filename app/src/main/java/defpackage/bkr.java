package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: bkr reason: default package */
/* compiled from: CloseCurrentWebviewAction */
public class bkr extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (!(a == null || a.mPageContext == null)) {
            if (a.mViewLayer != null) {
                a.mViewLayer.a();
                return;
            }
            a.mPageContext.finish();
        }
    }
}
