package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import org.json.JSONObject;

/* renamed from: bbl reason: default package */
/* compiled from: SearchAroundAction */
public class bbl extends wb {
    private final String a = "fromPage";
    private final int b = 0;
    private final int c = 1;

    public final boolean b() {
        return true;
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            POI a2 = kv.a(jSONObject.optJSONObject("poiInfo").toString());
            int optInt = jSONObject.optInt("fromPage", 0);
            if (!(a2 != null || a() == null || a().getBundle() == null)) {
                a2 = (POI) a().getBundle().getObject("POI");
            }
            PageBundle pageBundle = new PageBundle();
            if (optInt == 1) {
                LogManager.actionLogV25(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_GUIDE_BTN_VISIBLE, new SimpleEntry(TrafficUtil.POIID, a2 == null ? "" : a2.getId()), new SimpleEntry("type", Integer.valueOf(1)));
                pageBundle.putInt("key_source_type", 1);
            }
            pageBundle.putObject("POI", a2);
            if (a() != null) {
                a().mPageContext.startPage((String) "amap.search.action.category", pageBundle);
            }
        }
    }
}
