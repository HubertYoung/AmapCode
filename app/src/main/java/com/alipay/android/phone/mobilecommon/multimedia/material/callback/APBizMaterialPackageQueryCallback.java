package com.alipay.android.phone.mobilecommon.multimedia.material.callback;

import com.alipay.android.phone.mobilecommon.multimedia.material.response.APBizMaterialPackageQueryComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APBizMaterialPackageQueryError;

public interface APBizMaterialPackageQueryCallback {
    void onQueryComplete(APBizMaterialPackageQueryComplete aPBizMaterialPackageQueryComplete);

    void onQueryError(APBizMaterialPackageQueryError aPBizMaterialPackageQueryError);
}
