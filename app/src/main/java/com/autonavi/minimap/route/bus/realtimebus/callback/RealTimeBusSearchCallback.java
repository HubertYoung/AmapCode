package com.autonavi.minimap.route.bus.realtimebus.callback;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.model.Bus;
import java.util.ArrayList;

public abstract class RealTimeBusSearchCallback implements Callback<IBusLineSearchResult> {
    public abstract void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult);

    public void error(Throwable th, boolean z) {
        ToastHelper.showToast(th.getMessage());
    }

    public void callback(IBusLineSearchResult iBusLineSearchResult) {
        ArrayList<Bus> buslines = iBusLineSearchResult.getBuslines();
        if (buslines == null || buslines.size() <= 0) {
            ToastHelper.showLongToast("未查找到结果");
            return;
        }
        String lineID = iBusLineSearchResult.getLineID();
        if (lineID != null && lineID.contains(",")) {
            String[] split = lineID.split(",");
            for (int i = 0; i < buslines.size(); i++) {
                if (buslines.get(i).id.equals(split[0])) {
                    iBusLineSearchResult.setFocusBusLineIndex(i);
                }
            }
        }
        iBusLineSearchResult.setCurPoiPage(1);
        Bus bus = buslines.get(0);
        if (bus.stations == null || bus.stations.length <= 0) {
            ToastHelper.showLongToast("未查找到结果");
        } else {
            onResultParseDoneCallback(iBusLineSearchResult);
        }
    }
}
