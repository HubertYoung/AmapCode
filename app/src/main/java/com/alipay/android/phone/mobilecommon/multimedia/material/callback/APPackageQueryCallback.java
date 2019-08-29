package com.alipay.android.phone.mobilecommon.multimedia.material.callback;

import com.alipay.android.phone.mobilecommon.multimedia.material.response.APPackageQueryComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APPackageQueryError;

public interface APPackageQueryCallback {
    void onQueryComplete(APPackageQueryComplete aPPackageQueryComplete);

    void onQueryError(APPackageQueryError aPPackageQueryError);
}
