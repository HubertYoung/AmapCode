package com.alipay.android.phone.mobilecommon.multimedia.audio.interf;

import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public interface IAudioUpload {
    void uploadRecord(APAudioInfo aPAudioInfo, APAudioUploadCallback aPAudioUploadCallback, String str);

    void uploadRecord(APAudioInfo aPAudioInfo, APRequestParam aPRequestParam, APAudioUploadCallback aPAudioUploadCallback, String str);

    APAudioUploadRsp uploadRecordSync(APAudioInfo aPAudioInfo, APRequestParam aPRequestParam, String str);

    APAudioUploadRsp uploadRecordSync(APAudioInfo aPAudioInfo, String str);
}
