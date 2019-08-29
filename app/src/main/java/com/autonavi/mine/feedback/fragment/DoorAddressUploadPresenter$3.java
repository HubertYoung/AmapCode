package com.autonavi.mine.feedback.fragment;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.io.File;

public class DoorAddressUploadPresenter$3 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ cgf a;

    DoorAddressUploadPresenter$3(cgf cgf) {
        this.a = cgf;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        boolean a2 = b.a((byte[]) ((AosByteResponse) aosResponse).getResult());
        b bVar = this.a.h;
        if (a2) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.thanks_for_user));
            DoorAddressUploadPage doorAddressUploadPage = (DoorAddressUploadPage) cgf.this.mPage;
            if (doorAddressUploadPage.k != null) {
                doorAddressUploadPage.k.hide();
            }
            doorAddressUploadPage.finish();
            return;
        }
        DoorAddressUploadPage doorAddressUploadPage2 = (DoorAddressUploadPage) cgf.this.mPage;
        if (doorAddressUploadPage2.k != null) {
            doorAddressUploadPage2.k.hide();
        }
        ToastHelper.showLongToast(((DoorAddressUploadPage) cgf.this.mPage).getContext().getString(R.string.ic_net_error_tipinfo));
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a.d != null && this.a.d.length() > 0) {
            try {
                new File(this.a.d).delete();
                this.a.d = "";
            } catch (Throwable unused) {
            }
        }
    }
}
