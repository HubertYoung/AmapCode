package com.autonavi.minimap.life.sketchscenic.layer;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.Callback;

public class SketchScenicLayerHelper$1 implements Callback<SearchPoi> {
    final /* synthetic */ drg a;

    public SketchScenicLayerHelper$1(drg drg) {
        this.a = drg;
    }

    public void callback(SearchPoi searchPoi) {
        if (searchPoi != null) {
            this.a.c.a(searchPoi);
        }
    }

    public void error(Throwable th, boolean z) {
        if (th != null) {
            if (bno.a) {
                String b = drg.a;
                StringBuilder sb = new StringBuilder("MainMapSketchScenicManager.fetchAndShow: FetchPoiError! ");
                sb.append(th.getLocalizedMessage());
                AMapLog.e(b, sb.toString(), true);
            }
        } else if (bno.a) {
            AMapLog.e(drg.a, "MainMapSketchScenicManager.fetchAndShow: FetchPoiError! ", true);
        }
    }
}
