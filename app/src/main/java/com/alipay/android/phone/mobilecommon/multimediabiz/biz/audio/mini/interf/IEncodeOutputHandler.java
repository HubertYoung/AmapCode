package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;

public interface IEncodeOutputHandler {
    APAudioInfo getAudioInfo();

    void handle(byte[] bArr, int i, boolean z);

    void handleFinished();

    boolean isFinished();

    void setEncodeOutputCallback(IEncodeOutputCallback iEncodeOutputCallback);
}
