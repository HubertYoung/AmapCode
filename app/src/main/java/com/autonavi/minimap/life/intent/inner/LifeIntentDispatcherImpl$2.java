package com.autonavi.minimap.life.intent.inner;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.inter.impl.OpenLifeFragmentImpl;

public class LifeIntentDispatcherImpl$2 implements Callback<GeoPoint> {
    final /* synthetic */ ProgressDlg a;
    final /* synthetic */ dpd b;

    public LifeIntentDispatcherImpl$2(dpd dpd, ProgressDlg progressDlg) {
        this.b = dpd;
        this.a = progressDlg;
    }

    public void callback(GeoPoint geoPoint) {
        this.a.dismiss();
        new OpenLifeFragmentImpl().a(AMapPageUtil.getPageContext(), 1, null);
    }

    public void error(Throwable th, boolean z) {
        this.a.dismiss();
        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.location_fail));
    }
}
