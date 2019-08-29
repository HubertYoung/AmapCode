package com.alipay.android.phone.mobilecommon.multimedia.material.callback;

import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadTaskAdd;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadTaskAddError;

public interface APOnDownloadTaskAddListener {
    void onAddError(APDownloadTaskAddError aPDownloadTaskAddError);

    void onAddSuccess(APDownloadTaskAdd aPDownloadTaskAdd);
}
