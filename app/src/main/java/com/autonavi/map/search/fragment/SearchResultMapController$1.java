package com.autonavi.map.search.fragment;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;

public class SearchResultMapController$1 implements Callback<IRouteBusLineResult> {
    final /* synthetic */ bxp a;

    public SearchResultMapController$1(bxp bxp) {
        this.a = bxp;
    }

    public void error(Throwable th, boolean z) {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.result_list_noresult));
    }

    public void callback(IRouteBusLineResult iRouteBusLineResult) {
        if (iRouteBusLineResult.getTotalPoiSize() <= 0) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.result_list_noresult));
        } else if (this.a.f != null) {
            PageBundle pageBundle = new PageBundle();
            if (iRouteBusLineResult.getResultType() == 1) {
                pageBundle.putObject("bundle_key_result_obj", iRouteBusLineResult);
                pageBundle.putString("bundle_key_keyword", this.a.C.b());
                this.a.f.startPage((String) "amap.extra.route.busline_station_map", pageBundle);
                return;
            }
            pageBundle.putObject("BusLineResultFragment.IBusLineResult", iRouteBusLineResult);
            pageBundle.putString("BusLineResultFragment.KEYWORD", this.a.C.b());
            this.a.f.startPage((String) "amap.extra.route.busline_result", pageBundle);
        }
    }
}
