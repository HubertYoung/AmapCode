package com.alipay.android.phone.mobilecommon.multimedia.video;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUploadRsp;

public interface APVideoUploadCallback {
    void onUploadError(APVideoUploadRsp aPVideoUploadRsp);

    void onUploadFinished(APVideoUploadRsp aPVideoUploadRsp);

    void onUploadProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i);

    void onUploadStart(APMultimediaTaskModel aPMultimediaTaskModel);
}
