package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.DataPacket;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;

public interface IAudioNsAgcProcess {
    void init(EncodeConfig encodeConfig);

    DataPacket process(DataPacket dataPacket);

    void release();
}
