package com.alipay.android.phone.mobilecommon.multimedia.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;

public interface APAudioDownloadCallback {
    void onDownloadError(APAudioInfo aPAudioInfo, APAudioDownloadRsp aPAudioDownloadRsp);

    void onDownloadFinished(APAudioInfo aPAudioInfo);

    void onDownloadStart(APAudioInfo aPAudioInfo);
}
