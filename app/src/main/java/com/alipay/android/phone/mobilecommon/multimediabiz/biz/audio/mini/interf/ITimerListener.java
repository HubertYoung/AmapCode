package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

public interface ITimerListener {
    void onErrorTimer();

    void onRecordAmplitudeChanged();

    void onRecordMaxTimeFinished();

    void onRecordProgressChanged();
}
