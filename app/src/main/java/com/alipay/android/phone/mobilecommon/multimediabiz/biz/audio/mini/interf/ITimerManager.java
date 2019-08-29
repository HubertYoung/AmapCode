package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

public interface ITimerManager {
    void cancelTimer();

    void pauseTimer();

    void resumeTimer();

    void setTimerListener(ITimerListener iTimerListener);

    void setupTimer();
}
