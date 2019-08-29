package com.alipay.android.phone.mobilecommon.multimedia.file;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;

public interface APFileDownCallback {
    void onDownloadBatchProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i, int i2, long j, long j2);

    void onDownloadError(APMultimediaTaskModel aPMultimediaTaskModel, APFileDownloadRsp aPFileDownloadRsp);

    void onDownloadFinished(APMultimediaTaskModel aPMultimediaTaskModel, APFileDownloadRsp aPFileDownloadRsp);

    void onDownloadProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i, long j, long j2);

    void onDownloadStart(APMultimediaTaskModel aPMultimediaTaskModel);
}
