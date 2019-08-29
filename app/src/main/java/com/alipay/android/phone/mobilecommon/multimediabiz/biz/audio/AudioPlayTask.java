package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public class AudioPlayTask extends AudioTask {
    private APAudioInfo a;
    private APAudioPlayCallback b;
    private AudioPlayWorker c;

    AudioPlayTask(APAudioInfo info, APRequestParam param, APAudioPlayCallback callback) {
        this.a = info;
        setRequestParam(param);
        this.b = callback;
        setState(0);
    }

    public void setPlayWorker(AudioPlayWorker worker) {
        this.c = worker;
    }

    public AudioPlayWorker getPlayWorker() {
        return this.c;
    }

    public APAudioInfo getAudioInfo() {
        return this.a;
    }

    public void setAudioInfo(APAudioInfo audioInfo) {
        this.a = audioInfo;
    }

    public APAudioPlayCallback getPlayCallback() {
        return this.b;
    }

    public void setPlayCallback(APAudioPlayCallback playCallback) {
        this.b = playCallback;
    }

    public void stop() {
        setState(3);
        AudioPlayWorker worker = getPlayWorker();
        if (worker != null) {
            worker.stop();
        }
        setPlayWorker(null);
    }

    public void pause() {
        AudioPlayWorker worker = getPlayWorker();
        if (worker != null) {
            worker.pause();
        }
    }

    public void resume() {
        AudioPlayWorker worker = getPlayWorker();
        if (worker != null) {
            worker.resume();
        }
    }

    public void updateAudioConfiguration(APAudioConfiguration configuration) {
        AudioPlayWorker worker = getPlayWorker();
        if (worker != null) {
            worker.updateAudioConfiguration(configuration);
        }
    }

    public long getCurrentPosition() {
        AudioPlayWorker worker = getPlayWorker();
        if (worker == null) {
            return -1;
        }
        return worker.getCurrentPosition();
    }
}
