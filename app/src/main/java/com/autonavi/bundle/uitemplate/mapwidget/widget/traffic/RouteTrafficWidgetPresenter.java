package com.autonavi.bundle.uitemplate.mapwidget.widget.traffic;

import android.view.View;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteTrafficWidgetPresenter extends BaseMapWidgetPresenter<RouteTrafficMapWidget> {
    public static final String traffic = "traffic";

    public void initialize(RouteTrafficMapWidget routeTrafficMapWidget) {
        super.initialize(routeTrafficMapWidget);
        routeTrafficMapWidget.startLottie(getTrafficFromLocal());
    }

    public void internalClickListener(View view) {
        if (this.mBindWidget != null && ((RouteTrafficMapWidget) this.mBindWidget).getContentView() == view) {
            freshTrafficState();
        }
    }

    private void freshTrafficState() {
        boolean trafficFromLocal = getTrafficFromLocal();
        boolean s = DoNotUseTool.getMapView().s();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (trafficFromLocal == s) {
            if (!trafficFromLocal) {
                DoNotUseTool.getMapView().t();
            }
            if (bqx != null) {
                bqx.a(!trafficFromLocal, true, DoNotUseTool.getMapManager(), AMapPageUtil.getAppContext());
            }
            ((RouteTrafficMapWidget) this.mBindWidget).startLottie(!trafficFromLocal);
        } else {
            if (!s) {
                DoNotUseTool.getMapView().t();
            }
            if (bqx != null) {
                bqx.a(!s, true, DoNotUseTool.getMapManager(), AMapPageUtil.getAppContext());
            }
            ((RouteTrafficMapWidget) this.mBindWidget).startLottie(!s);
        }
        JSONObject jSONObject = new JSONObject();
        if (!trafficFromLocal) {
            try {
                jSONObject.put("type", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jSONObject.put("type", "0");
        }
        jSONObject.put("status", 1);
        LogManager.actionLogV2("P00001", "B014", jSONObject);
    }

    public boolean getTrafficFromLocal() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (mapSharePreference.contains("traffic")) {
            return mapSharePreference.getBooleanValue("traffic", false);
        }
        return true;
    }
}
