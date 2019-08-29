package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.inter.ISearchManager;
import org.json.JSONObject;

/* renamed from: byt reason: default package */
/* compiled from: ShowOnMapAction */
public class byt extends wb {
    public final boolean b() {
        return true;
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            POI a2 = kv.a(jSONObject.optJSONObject("poiInfo").toString());
            if (((ISearchManager) ank.a(ISearchManager.class)) != null && a.getBundle() != null) {
                new ekz().a(a2, a.getBundle().getString("fromSource"), (InfoliteResult) a.getBundle().getObject("mSearchResultData"), (IRouteBusLineResult) a.getBundle().getObject("mBuslineResultData"), (SuperId) a.getBundle().getObject("superID"));
            }
        }
    }
}
