package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.basemap.save.page.SaveEditPointPage;
import org.json.JSONObject;

/* renamed from: cpx reason: default package */
/* compiled from: EditFavoriteInfoAction */
public class cpx extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null && a.getBundle() != null) {
            a.mPageContext.startPageForResult(SaveEditPointPage.class, new PageBundle(), 1000);
        }
    }
}
