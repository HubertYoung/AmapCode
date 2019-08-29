package com.autonavi.minimap.route.bus.realtimebus;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;

public class BusRadarViewManager$16$1 extends RealTimeBusSearchCallback {
    final /* synthetic */ AnonymousClass8 a;

    public BusRadarViewManager$16$1(AnonymousClass8 r1) {
        this.a = r1;
    }

    public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
        LogManager.actionLogV2("P00367", "B013");
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("BusLineDetailFragment.IBusLineResult", iBusLineSearchResult);
        asy asy = (asy) a.a.a(asy.class);
        if (asy != null) {
            dxs.this.d.startPageForResult(asy.b().a(1), pageBundle, 100);
        }
    }
}
