package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.DataPacket;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioNsAgcProcess;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;

public class AudioNsAgcProcessImpl implements IAudioNsAgcProcess {
    private static final String a = AudioNsAgcProcessImpl.class.getSimpleName();

    public void init(EncodeConfig params) {
        try {
            MMNativeEngineApi.audioNsAgcProcessInit(params.getSampleRate(), (params.getSampleRate() * 20) / 1000, 1);
        } catch (MMNativeException e) {
            Logger.E(a, (Throwable) e, "SilkEncoder audioNsAgcProcessInit exp code=" + e.getCode(), new Object[0]);
        }
    }

    public DataPacket process(DataPacket packet) {
        if (packet == null) {
            return packet;
        }
        try {
            if (packet.data == null) {
                return packet;
            }
            byte[] out = MMNativeEngineApi.audioNsAgcProcess(packet.data);
            return new DataPacket(out, out.length);
        } catch (MMNativeException e) {
            Logger.E(a, (Throwable) e, (String) "processVoice error", new Object[0]);
            return packet;
        }
    }

    public void release() {
        try {
            MMNativeEngineApi.audioNsAgcProcessDestory();
        } catch (MMNativeException e) {
            Logger.E(a, (Throwable) e, "audioNsAgcProcessDestory error code=" + e.getCode(), new Object[0]);
        }
    }
}
