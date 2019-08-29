package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class DefaultEncodeOutputHandler implements IEncodeOutputHandler {
    private IEncodeOutputCallback a;
    private APAudioInfo b;

    public DefaultEncodeOutputHandler(APAudioInfo audioInfo) {
        this.b = audioInfo;
    }

    public void handle(byte[] encodeData, int length, boolean isLast) {
        if (isLast && this.a != null) {
            this.a.onOutputFrame(encodeData, isLast);
        }
    }

    public void handleFinished() {
        if (CompareUtils.in(Integer.valueOf(this.a != null ? this.a.getCurRecordState() : -1), Integer.valueOf(7), Integer.valueOf(4))) {
            Logger.D("DefaultEncodeOutputHandler", "there is a error, resulting to stop handleFinished ", new Object[0]);
        } else if (this.a != null) {
            this.a.onOutputFinished();
        }
    }

    public APAudioInfo getAudioInfo() {
        return this.b;
    }

    public boolean isFinished() {
        return true;
    }

    public void setEncodeOutputCallback(IEncodeOutputCallback callback) {
        this.a = callback;
    }
}
