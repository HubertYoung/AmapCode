package com.autonavi.minimap.route.bus.realtimebus.presenter;

import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.model.BusLineSearchException;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage;
import java.util.Date;

public class RTBusSearchPresenter$1 implements Callback<duk> {
    final /* synthetic */ dyr a;

    public RTBusSearchPresenter$1(dyr dyr) {
        this.a = dyr;
    }

    public void error(Throwable th, boolean z) {
        this.a.c();
        if (th != null) {
            if (th instanceof BusLineSearchException) {
                this.a.mPage;
                RTBusSearchPage.b(((RTBusSearchPage) this.a.mPage).getString(R.string.ic_net_error_noresult_real_time_bus));
                return;
            }
            this.a.mPage;
            RTBusSearchPage.b(((RTBusSearchPage) this.a.mPage).getString(R.string.network_error_message));
        }
    }

    public void callback(duk duk) {
        this.a.c();
        IBusLineSearchResult iBusLineSearchResult = duk.a;
        iBusLineSearchResult.setSearchKeyword(((RTBusSearchPage) this.a.mPage).b());
        iBusLineSearchResult.setCityCode(this.a.b);
        if (iBusLineSearchResult.getTotalPoiSize() <= 0) {
            this.a.mPage;
            RTBusSearchPage.b(((RTBusSearchPage) this.a.mPage).getString(R.string.ic_net_error_noresult_real_time_bus));
            return;
        }
        if (duk.a.getResultType() == 1) {
            iBusLineSearchResult.setFocusedPoiIndex(0);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_result_obj", duk.a);
            asy asy = (asy) a.a.a(asy.class);
            if (asy != null) {
                asy.b().a(3, pageBundle);
            }
        } else {
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putObject("BusLineResultFragment.IBusLineResult", iBusLineSearchResult);
            pageBundle2.putString("from", "RTBusSearchPresenter");
            pageBundle2.putString("real_time_bus_adcode", this.a.b);
            asy asy2 = (asy) a.a.a(asy.class);
            if (asy2 != null) {
                asy2.b().a(2, pageBundle2);
            }
        }
        if (!TextUtils.isEmpty(((RTBusSearchPage) this.a.mPage).b()) && this.a.a != null) {
            this.a.a.historyType = 7;
            this.a.a.time = new Date();
            SearchHistoryHelper.getInstance(((RTBusSearchPage) this.a.mPage).getContext().getApplicationContext()).saveTipItem(this.a.a);
        }
    }
}
