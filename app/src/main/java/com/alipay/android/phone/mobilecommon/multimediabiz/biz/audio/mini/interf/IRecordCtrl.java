package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState;

public interface IRecordCtrl {
    void excuteRecordNotifyAll();

    boolean excuteRecordWait();

    long getPauseDuration();

    AudioRecordState getRecordState();

    void refreshRecordState(int i);
}
