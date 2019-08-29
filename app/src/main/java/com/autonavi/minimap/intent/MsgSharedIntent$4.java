package com.autonavi.minimap.intent;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.R;

public class MsgSharedIntent$4 implements Callback<IRouteBusLineResult> {
    final /* synthetic */ dlm a;

    public MsgSharedIntent$4(dlm dlm) {
        this.a = dlm;
    }

    public void error(Throwable th, boolean z) {
        ToastHelper.showLongToast(MapApplication.getApplication().getString(R.string.ic_net_error_tipinfo));
    }

    public void callback(IRouteBusLineResult iRouteBusLineResult) {
        if (this.a.g) {
            this.a.g = false;
        } else if (iRouteBusLineResult.getTotalPoiSize() == 0) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.not_find_result));
        } else {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("BusLineDetailFragment.FOCUS_BUSLINE_INDEX", 0);
                pageBundle.putInt("BusLineDetailFragment.CUR_POI_PAGE", 1);
                pageBundle.putObject("BusLineDetailFragment.IBusLineResult", iRouteBusLineResult);
                pageContext.startPage((String) "amap.extra.route.busline_detail", pageBundle);
            }
        }
    }
}
