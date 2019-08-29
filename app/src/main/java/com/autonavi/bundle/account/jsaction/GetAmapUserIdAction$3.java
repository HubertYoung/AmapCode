package com.autonavi.bundle.account.jsaction;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public class GetAmapUserIdAction$3 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ JsAdapter a;
    final /* synthetic */ anw b;

    public GetAmapUserIdAction$3(anw anw, JsAdapter jsAdapter) {
        this.b = anw;
        this.a = jsAdapter;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a.mViewLayer != null) {
            this.a.mViewLayer.a();
        } else {
            this.a.mPageContext.finish();
        }
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.thanks_for_feedback));
        if (this.a.mViewLayer != null) {
            this.a.mViewLayer.a();
        } else {
            this.a.mPageContext.finish();
        }
    }
}
