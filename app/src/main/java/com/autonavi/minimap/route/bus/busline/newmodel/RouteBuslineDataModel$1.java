package com.autonavi.minimap.route.bus.busline.newmodel;

import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;

public class RouteBuslineDataModel$1 extends RealTimeBusSearchCallback {
    final /* synthetic */ dun a;

    public RouteBuslineDataModel$1(dun dun) {
        this.a = dun;
    }

    public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
        if (iBusLineSearchResult != null) {
            this.a.c = iBusLineSearchResult.getBusLineArray(this.a.a.getCurPoiPage())[this.a.a.getFocusBusLineIndex()].copyObject();
            this.a.c();
        }
    }
}
