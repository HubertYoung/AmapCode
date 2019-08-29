package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

public interface IEncodeOutputCallback {
    int getCurRecordState();

    void onOutputError(int i, String str);

    void onOutputFinished();

    void onOutputFrame(byte[] bArr, boolean z);
}
