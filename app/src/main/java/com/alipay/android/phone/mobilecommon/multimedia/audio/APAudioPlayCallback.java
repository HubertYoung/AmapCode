package com.alipay.android.phone.mobilecommon.multimedia.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioPlayRsp;

public interface APAudioPlayCallback {
    void onPlayCancel(APAudioInfo aPAudioInfo);

    void onPlayCompletion(APAudioInfo aPAudioInfo);

    void onPlayError(APAudioPlayRsp aPAudioPlayRsp);

    void onPlayStart(APAudioInfo aPAudioInfo);
}
