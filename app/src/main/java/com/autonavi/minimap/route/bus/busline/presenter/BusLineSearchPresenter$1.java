package com.autonavi.minimap.route.bus.busline.presenter;

import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.model.BusLineSearchException;
import com.autonavi.minimap.route.bus.busline.page.BusLineResultPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineSearchPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.uc.webview.export.internal.SDKFactory;
import java.util.Date;

public class BusLineSearchPresenter$1 implements Callback<IBusLineSearchResult> {
    final /* synthetic */ duu a;

    public BusLineSearchPresenter$1(duu duu) {
        this.a = duu;
    }

    public void error(Throwable th, boolean z) {
        if (th != null) {
            if (th instanceof BusLineSearchException) {
                ToastHelper.showToast(((BusLineSearchPage) this.a.mPage).getString(R.string.ic_net_error_noresult));
                eko.a((int) SDKFactory.getCoreType);
                return;
            }
            ToastHelper.showToast(((BusLineSearchPage) this.a.mPage).getString(R.string.network_error_message));
            eko.a(10008);
        }
    }

    public void callback(IBusLineSearchResult iBusLineSearchResult) {
        iBusLineSearchResult.setSearchKeyword(this.a.a);
        iBusLineSearchResult.setCityCode(this.a.b);
        if (iBusLineSearchResult.getTotalPoiSize() <= 0) {
            ToastHelper.showLongToast(((BusLineSearchPage) this.a.mPage).getString(R.string.ic_net_error_noresult));
            eko.a((int) SDKFactory.getCoreType);
            return;
        }
        if (iBusLineSearchResult.getResultType() == 1) {
            iBusLineSearchResult.setFocusedPoiIndex(0);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_result_obj", iBusLineSearchResult);
            ((BusLineSearchPage) this.a.mPage).startPage(BusLineStationMapPage.class, pageBundle);
        } else {
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putObject("BusLineResultFragment.IBusLineResult", iBusLineSearchResult);
            ((BusLineSearchPage) this.a.mPage).startPage(BusLineResultPage.class, pageBundle2);
            eko.a(10000);
        }
        if (!TextUtils.isEmpty(this.a.a) && this.a.c != null) {
            this.a.c.historyType = 1;
            this.a.c.time = new Date();
            SearchHistoryHelper.getInstance(((BusLineSearchPage) this.a.mPage).getContext().getApplicationContext()).saveTipItem(this.a.c);
        }
    }
}
