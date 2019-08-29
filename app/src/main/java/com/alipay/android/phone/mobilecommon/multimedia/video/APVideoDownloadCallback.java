package com.alipay.android.phone.mobilecommon.multimedia.video;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoDownloadRsp;

public interface APVideoDownloadCallback {
    void onDownloadError(APVideoDownloadRsp aPVideoDownloadRsp);

    void onDownloadFinished(APVideoDownloadRsp aPVideoDownloadRsp);

    void onDownloadProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i);

    void onDownloadStart(APMultimediaTaskModel aPMultimediaTaskModel);

    void onThumbDownloadFinished(APVideoDownloadRsp aPVideoDownloadRsp);
}
