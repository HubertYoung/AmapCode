package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;

public interface IAudioHandler {

    public interface IRecordCallback {
        void onRecordCallback(int i, Info info);
    }

    void create(AudioConfig audioConfig);

    int getMaxAmplitude();

    boolean isRecording();

    void pause();

    void reset();

    void resume();

    void setCallback(IRecordCallback iRecordCallback);

    void start();

    void stop();
}
