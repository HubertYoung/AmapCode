package com.autonavi.minimap.basemap.route.presenter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import org.json.JSONObject;

public class CarLicenseScanPresenter$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ cqu a;

    public CarLicenseScanPresenter$1(cqu cqu) {
        this.a = cqu;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            a();
            return;
        }
        cqt cqt = new cqt();
        cqt.parser((byte[]) aosByteResponse.getResult());
        JSONObject jSONObject = null;
        if (cqt.isSuccessRequest()) {
            jSONObject = cqt.a;
        }
        ((CarLicenseScanPage) this.a.mPage).a((Object) jSONObject);
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a();
    }

    private void a() {
        ToastHelper.showLongToast(this.a.b().getString(R.string.network_error_message));
        ((CarLicenseScanPage) this.a.mPage).a((Object) null);
    }
}
