package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;

public interface ICode {
    void closeAudioEncoder(byte[] bArr, IEncodeOutputHandler iEncodeOutputHandler);

    int encodeAudio(short[] sArr, int i, byte[] bArr, int i2, long j);

    byte[] makeBuffer(EncodeConfig encodeConfig);

    int openAudioEncoder(EncodeConfig encodeConfig);
}
