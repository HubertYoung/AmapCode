package com.autonavi.minimap.life.marketdetail;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;

public class MarketDetailManager$1 implements Callback<dpi> {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ boolean c;
    final /* synthetic */ bid d;

    public MarketDetailManager$1(String str, String str2, boolean z, bid bid) {
        this.a = str;
        this.b = str2;
        this.c = z;
        this.d = bid;
    }

    public final void callback(dpi dpi) {
        if (dpi != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundleParentPoiid", this.a);
            pageBundle.putObject("bundleMarketName", this.b);
            pageBundle.putObject("showIndoorMap", Boolean.valueOf(this.c));
            pageBundle.putObject("bundleResponseModel", dpi);
            this.d.startPage(MarketDetailPage.class, pageBundle);
        }
    }

    public final void error(Throwable th, boolean z) {
        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.market_detail_net_error));
    }
}
