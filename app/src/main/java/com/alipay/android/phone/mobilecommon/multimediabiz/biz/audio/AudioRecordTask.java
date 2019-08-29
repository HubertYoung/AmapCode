package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import java.lang.ref.WeakReference;

public class AudioRecordTask extends AudioTask {
    private APAudioInfo a;
    private APAudioRecordCallback b;
    private WeakReference<AudioRecordWorker> c;

    AudioRecordTask(APAudioInfo audioInfo, APRequestParam param, APAudioRecordCallback callback) {
        this.a = audioInfo;
        setRequestParam(param);
        this.b = callback;
        setState(0);
    }

    public APAudioInfo getAudioInfo() {
        return this.a;
    }

    public APAudioRecordCallback getAudioRecordUploadCallback() {
        return this.b;
    }

    public void setAudioRecordUploadCallback(APAudioRecordUploadCallback audioRecordCallback) {
        this.b = audioRecordCallback;
    }

    public void cancel() {
        cancel(false);
    }

    public void cancel(boolean autoCancel) {
        if (!autoCancel || 2 == getState() || getState() == 0) {
            setState(3);
            AudioRecordWorker worker = this.c == null ? null : (AudioRecordWorker) this.c.get();
            if (worker != null) {
                worker.cancel();
            }
        }
    }

    public void stop() {
        AudioRecordWorker worker = this.c == null ? null : (AudioRecordWorker) this.c.get();
        if (worker != null) {
            worker.stop();
        }
        setState(4);
    }

    /* access modifiers changed from: protected */
    public void setRecordWorker(AudioRecordWorker worker) {
        this.c = new WeakReference<>(worker);
    }
}
