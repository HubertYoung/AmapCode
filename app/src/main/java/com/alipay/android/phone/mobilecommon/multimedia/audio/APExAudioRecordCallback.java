package com.alipay.android.phone.mobilecommon.multimedia.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;

public interface APExAudioRecordCallback extends APAudioRecordCallback {
    void onFrameRecorded(byte[] bArr, boolean z);

    void onRecordPause(APAudioInfo aPAudioInfo);

    void onRecordStop(APAudioInfo aPAudioInfo);
}
