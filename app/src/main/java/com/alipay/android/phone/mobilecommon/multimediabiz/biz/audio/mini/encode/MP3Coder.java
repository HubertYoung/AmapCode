package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ICode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.audio.AudioRecoderParams;

public class MP3Coder implements ICode {
    private MMNativeEngineApi a = null;

    public byte[] makeBuffer(EncodeConfig params) {
        int size = params.getFrameSize() * 4;
        a("frameSize:" + params.getFrameSize() + ">>>makeBufferSize:" + size);
        return new byte[size];
    }

    public int openAudioEncoder(EncodeConfig encodeConfig) {
        try {
            MMNativeEngineApi.loadLibrariesOnce(null);
            this.a = new MMNativeEngineApi();
            AudioRecoderParams params = AudioRecoderParams.createMP3(7);
            params.numberOfChannels = encodeConfig.getNumberOfChannel();
            params.setSampleRate(encodeConfig.getSampleRate());
            params.setFrameSize(encodeConfig.getFrameSize());
            params.setNumberOfChannels(encodeConfig.getNumberOfChannel());
            params.setEncodeBitRate(encodeConfig.getEncodeBitRate());
            return this.a.audioEncoderInit(params);
        } catch (MMNativeException e) {
            Logger.D("MP3Coder", "openAudioEncoder e=" + e.toString(), new Object[0]);
            return -1;
        }
    }

    public int encodeAudio(short[] lin, int offset, byte[] encoded, int size, long pauseTime) {
        try {
            if (this.a == null) {
                return 0;
            }
            int retLength = this.a.audioEncoder4Mp3(lin, size, encoded);
            a("retLength:" + retLength + ">>>>encodedLength:" + encoded.length);
            return retLength;
        } catch (Exception e) {
            Logger.D("MP3Coder", "encodeAudio exp codesize=" + size, new Object[0]);
            return 0;
        }
    }

    public void closeAudioEncoder(byte[] encodeBuf, IEncodeOutputHandler handler) {
        if (this.a != null) {
            try {
                int outputMp3bufLen = this.a.audioMp3Flush(encodeBuf);
                a("last  retLength:" + outputMp3bufLen + ">>>>encodedLength:" + encodeBuf.length);
                if (handler != null) {
                    handler.handle(encodeBuf, outputMp3bufLen, true);
                }
            } catch (MMNativeException e) {
                e.printStackTrace();
            }
            a();
        }
    }

    private void a() {
        if (this.a != null) {
            try {
                this.a.audioEncoderDestory();
            } catch (MMNativeException e) {
                Logger.D("MP3Coder", "closeAudioEncoder e=" + e.toString(), new Object[0]);
            }
        }
    }

    private static void a(String msg) {
        Logger.P("MP3Coder", msg, new Object[0]);
    }
}
