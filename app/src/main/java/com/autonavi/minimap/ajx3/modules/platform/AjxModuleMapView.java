package com.autonavi.minimap.ajx3.modules.platform;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleMapView;

public class AjxModuleMapView extends AbstractModuleMapView {
    public AjxModuleMapView(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void snapshot(final JsFunctionCallback jsFunctionCallback) {
        AMapLog.d("AjxModuleMapView", "snapshot ------ ");
        if (jsFunctionCallback != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                jsFunctionCallback.callback(new Object[0]);
            } else {
                cfc.a().a(mapManager, (a) new a() {
                    public void onPrepare() {
                    }

                    public void onFailure() {
                        jsFunctionCallback.callback(new Object[0]);
                    }

                    public void onScreenShotFinish(String str) {
                        if (TextUtils.isEmpty(str)) {
                            jsFunctionCallback.callback(new Object[0]);
                            return;
                        }
                        jsFunctionCallback.callback(str);
                    }
                });
            }
        }
    }

    public void setZoomCenterType(String str) {
        AMapLog.d("AjxModuleMapView", "setZoomCenterType ------ ".concat(String.valueOf(str)));
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null) {
            bty mapView = mapManager.getMapView();
            if (mapView != null) {
                try {
                    mapView.g(mapView.j(), Integer.parseInt(str));
                } catch (NumberFormatException unused) {
                    AMapLog.e("ModuleMapView", "type value parseInt failed: ".concat(String.valueOf(str)));
                }
            }
        }
    }
}
