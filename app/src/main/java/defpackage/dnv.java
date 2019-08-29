package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.life.travelguide.page.TransparentTitleWebPage;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import org.json.JSONObject;

/* renamed from: dnv reason: default package */
/* compiled from: OpenNewWebViewAction */
public class dnv extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("type");
            String optString2 = jSONObject.optString("url");
            if (TextUtils.isEmpty(optString2) || (!optString2.startsWith("http") && !optString2.startsWith("https"))) {
                bgx bgx = (bgx) a.a.a(bgx.class);
                if (bgx != null) {
                    optString2 = bgx.getUrl(optString2);
                }
            }
            if (H5Param.LONG_TRANSPARENT.equals(optString)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", optString2);
                a.mPageContext.startPage(TransparentTitleWebPage.class, pageBundle);
                return;
            }
            if (IPreloadManager.SIR_COMMON_TYPE.equals(optString) || "norotate".equals(optString)) {
                aja aja = new aja(optString2);
                aja.b = new ajf() {
                    public final boolean f() {
                        return true;
                    }
                };
                aix aix = (aix) a.a.a(aix.class);
                if (aix != null) {
                    aix.a(a.mPageContext, aja);
                }
            }
        }
    }
}
