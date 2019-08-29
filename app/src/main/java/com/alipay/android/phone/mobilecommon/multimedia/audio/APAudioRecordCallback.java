package com.alipay.android.phone.mobilecommon.multimedia.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;

public interface APAudioRecordCallback {
    void onRecordAmplitudeChange(APAudioInfo aPAudioInfo, int i);

    void onRecordCancel(APAudioInfo aPAudioInfo);

    void onRecordError(APAudioRecordRsp aPAudioRecordRsp);

    void onRecordFinished(APAudioInfo aPAudioInfo);

    void onRecordProgressUpdate(APAudioInfo aPAudioInfo, int i);

    void onRecordStart(APAudioInfo aPAudioInfo);
}
