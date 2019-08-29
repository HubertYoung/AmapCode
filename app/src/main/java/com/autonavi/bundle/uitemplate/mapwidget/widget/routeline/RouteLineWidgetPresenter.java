package com.autonavi.bundle.uitemplate.mapwidget.widget.routeline;

import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.minimap.autosec.UTAnalyticsUtils;
import java.util.HashMap;
import java.util.Map;

public class RouteLineWidgetPresenter extends BaseMapWidgetPresenter<RouteLineMapWidget> {
    public void internalClickListener(View view) {
        routeLineClick();
    }

    public void routeLineClick() {
        logSearchClickForUT(String.valueOf(System.currentTimeMillis()));
        LogManager.actionLogV2("P00001", "B004", getLogVersionStateParam());
        IRouteLineEventDelegate iRouteLineEventDelegate = (IRouteLineEventDelegate) getEventDelegate();
        if (iRouteLineEventDelegate != null) {
            iRouteLineEventDelegate.startRoutePlanPage();
        }
    }

    private void logSearchClickForUT(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("name", "RouteButton");
        hashMap.put("timestamp", str);
        UTAnalyticsUtils.getInstance().userDefinedTracker("DefaultPage", hashMap);
        kd.a((String) "amap.P00001.0.B004", (Map<String, String>) null);
    }
}
