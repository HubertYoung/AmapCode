package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.inter.ISearchResultController;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import org.json.JSONObject;

/* renamed from: bcm reason: default package */
/* compiled from: InfoliteParserImpl */
public final class bcm implements bcf {
    public final boolean a(aea aea, JSONObject jSONObject, InfoliteParam infoliteParam) {
        if (((ISearchResultController) ank.a(ISearchResultController.class)) == null || infoliteParam == null) {
            return false;
        }
        a.a.b = infoliteParam;
        a.a.a(jSONObject);
        InfoliteResult infoliteResult = a.a.a;
        infoliteResult.originalJson = jSONObject.toString();
        List<aup> o = bcy.o(infoliteResult);
        if (o != null && o.size() > 0) {
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, LogConstant.MAIN_MAP_GUIDE_MAP_SHOW, new SimpleEntry(TrafficUtil.KEYWORD, bcy.f(infoliteResult) ? infoliteResult.mWrapper.keywords : ""));
        }
        a.a.a.mWrapper.keywords = infoliteParam.keywords;
        aea.callback(infoliteResult);
        return true;
    }

    public final InfoliteResult a(JSONObject jSONObject) {
        a.a.a(jSONObject);
        return a.a.a;
    }
}
