package com.alipay.android.phone.mobilecommon.multimedia.file;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;

public interface APFileUploadCallback {
    void onUploadError(APMultimediaTaskModel aPMultimediaTaskModel, APFileUploadRsp aPFileUploadRsp);

    void onUploadFinished(APMultimediaTaskModel aPMultimediaTaskModel, APFileUploadRsp aPFileUploadRsp);

    void onUploadProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i, long j, long j2);

    void onUploadStart(APMultimediaTaskModel aPMultimediaTaskModel);
}
