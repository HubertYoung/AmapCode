package com.autonavi.minimap.basemap.errorback.inter.impl;

import android.app.Activity;
import android.app.Dialog;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ITrafficViewForFeed;
import com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController;
import com.autonavi.minimap.basemap.traffic.TrafficGroupViewForFeed;
import com.autonavi.minimap.basemap.traffic.TrafficItemDialog;
import com.autonavi.minimap.basemap.traffic.TrafficReportResultDialog;

public class TrafficReportControllerImpl implements ITrafficReportController {
    private static TrafficItemDialog a;
    private static TrafficReportResultDialog b;

    public final void a(bid bid) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("from_type", 2);
        if (bid == null) {
            bid = AMapPageUtil.getPageContext();
        }
        if (bid != null) {
            bid.startPageForResult((String) "amap.basemap.action.mainmap_traffic_report", pageBundle, 1021);
        }
    }

    public final Dialog a(Activity activity, PageBundle pageBundle, MapManager mapManager) {
        TrafficItemDialog trafficItemDialog = new TrafficItemDialog(activity, pageBundle, mapManager);
        a = trafficItemDialog;
        return trafficItemDialog;
    }

    public final Dialog a(Activity activity) {
        TrafficReportResultDialog trafficReportResultDialog = new TrafficReportResultDialog(activity);
        b = trafficReportResultDialog;
        return trafficReportResultDialog;
    }

    public final void a() {
        if (a != null) {
            a.dismiss();
        }
        if (b != null) {
            b.dismiss();
        }
    }

    public final /* synthetic */ ITrafficViewForFeed a(Activity activity, PageBundle pageBundle, MapManager mapManager, a aVar) {
        if (activity != null) {
            return new TrafficGroupViewForFeed(activity, pageBundle, mapManager, aVar);
        }
        return null;
    }
}
