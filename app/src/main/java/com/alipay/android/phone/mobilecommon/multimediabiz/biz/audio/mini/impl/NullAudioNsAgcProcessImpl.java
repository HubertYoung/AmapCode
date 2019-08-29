package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.DataPacket;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioNsAgcProcess;

public class NullAudioNsAgcProcessImpl implements IAudioNsAgcProcess {
    public void init(EncodeConfig params) {
    }

    public DataPacket process(DataPacket packet) {
        return packet;
    }

    public void release() {
    }
}
