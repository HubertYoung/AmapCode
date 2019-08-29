package defpackage;

import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.inter.ISearchResultController;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: byl reason: default package */
/* compiled from: BrandIconRequestAction */
public class byl extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (!(a() == null || jSONObject == null)) {
            String obj = jSONObject.opt("isBrand").toString();
            String obj2 = jSONObject.opt("names").toString();
            InfoliteParam a = bbv.a(AppManager.getInstance().getUserLocInfo(), obj2, LocationInstrument.getInstance().getLatestPosition(), null);
            a.isBrand = obj;
            ISearchResultController iSearchResultController = (ISearchResultController) ank.a(ISearchResultController.class);
            if (iSearchResultController != null) {
                new ekv().a(a, 1, iSearchResultController.getSearchCallBackEx(obj2));
            }
        }
    }
}
