package com.autonavi.bundle.searchresult.service.callback;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Callback.PrepareCallback;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import org.json.JSONObject;

public abstract class NetWorkCallBack implements PrepareCallback<JSONObject, InfoliteResult> {
    public void error(int i, String str) {
    }

    public void error(Throwable th, boolean z) {
    }

    private void logOnRecommendWordResponse(InfoliteResult infoliteResult) {
        List<aup> a = bcs.a(infoliteResult);
        if (a != null && a.size() > 0) {
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, LogConstant.MAIN_MAP_GUIDE_MAP_SHOW, new SimpleEntry(TrafficUtil.KEYWORD, bcs.b(infoliteResult) ? infoliteResult.mWrapper.keywords : ""));
        }
    }

    public void callback(InfoliteResult infoliteResult) {
        if (infoliteResult == null) {
            error(0, (String) null);
        }
    }

    public InfoliteResult prepare(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            InfoliteResult a = ((bck) a.a.a(bck.class)).a().a(jSONObject);
            a.responseHeader.f = true;
            logOnRecommendWordResponse(a);
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
