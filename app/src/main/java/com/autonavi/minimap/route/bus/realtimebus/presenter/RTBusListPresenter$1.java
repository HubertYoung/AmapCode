package com.autonavi.minimap.route.bus.realtimebus.presenter;

import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusListPage;

public class RTBusListPresenter$1 extends RealTimeBusSearchCallback {
    final /* synthetic */ dyp a;

    public RTBusListPresenter$1(dyp dyp) {
        this.a = dyp;
    }

    public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
        if (iBusLineSearchResult != null) {
            this.a.b = iBusLineSearchResult.getBusLineArray(this.a.e.getCurPoiPage())[this.a.e.getFocusBusLineIndex()].copyObject();
            ((RTBusListPage) this.a.mPage).a();
        }
    }
}
