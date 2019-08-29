package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ICode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.audio.AudioRecoderParams;

public class AACCoder implements ICode {
    private MMNativeEngineApi a = null;
    private EncodeConfig b;

    public byte[] makeBuffer(EncodeConfig params) {
        return new byte[4096];
    }

    public int openAudioEncoder(EncodeConfig encodeConfig) {
        try {
            MMNativeEngineApi.loadLibrariesOnce(null);
            this.a = new MMNativeEngineApi();
            this.b = encodeConfig;
            AudioRecoderParams params = AudioRecoderParams.createAAC(encodeConfig.getSavePath());
            params.numberOfChannels = encodeConfig.getNumberOfChannel();
            params.sampleRate = encodeConfig.getSampleRate();
            params.encodeBitRate = encodeConfig.getEncodeBitRate();
            return this.a.audioEncoderInit(params);
        } catch (MMNativeException e) {
            Logger.D("AACCoder", "openAudioEncoder e=" + e.toString(), new Object[0]);
            return -1;
        }
    }

    public int encodeAudio(short[] lin, int offset, byte[] encoded, int size, long pauseTime) {
        try {
            if (this.a != null) {
                if (pauseTime > 0) {
                    this.a.audioEncoder4AAC(lin, size, pauseTime);
                } else {
                    this.a.audioEncoder4AAC(lin, size);
                }
            }
        } catch (Exception e) {
            Logger.D("AACCoder", "encodeAudio exp codesize=" + size, new Object[0]);
        }
        return size;
    }

    public void closeAudioEncoder(byte[] encodeBuf, IEncodeOutputHandler handler) {
        if (this.a != null) {
            try {
                this.a.audioEncoderDestory();
                if (handler != null && this.b != null) {
                    byte[] buf = FileUtils.file2Bytes(this.b.getSavePath());
                    if (buf != null) {
                        handler.handle(buf, buf.length, true);
                    }
                }
            } catch (Exception e) {
                Logger.D("AACCoder", "closeAudioEncoder e=" + e.toString(), new Object[0]);
            }
        }
    }
}
