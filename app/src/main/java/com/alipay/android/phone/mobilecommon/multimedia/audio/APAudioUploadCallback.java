package com.alipay.android.phone.mobilecommon.multimedia.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;

public interface APAudioUploadCallback {
    void onUploadError(APAudioUploadRsp aPAudioUploadRsp);

    void onUploadFinished(APAudioUploadRsp aPAudioUploadRsp);

    void onUploadStart(APAudioInfo aPAudioInfo);
}
