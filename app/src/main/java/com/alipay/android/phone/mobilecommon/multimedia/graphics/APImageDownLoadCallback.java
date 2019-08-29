package com.alipay.android.phone.mobilecommon.multimedia.graphics;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;

public interface APImageDownLoadCallback {
    void onError(APImageDownloadRsp aPImageDownloadRsp, Exception exc);

    void onProcess(String str, int i);

    void onSucc(APImageDownloadRsp aPImageDownloadRsp);
}
