package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record;

import android.media.AudioRecord;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.RecordConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecordPermissionDeniedException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecorderInUsingException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler.IRecordCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.HardwareHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public abstract class AbstractAudioRecorder implements Runnable {
    private Object a = new Object();
    protected Logger logger = Logger.getLogger(getClass().getSimpleName());
    protected AudioData mAudioData;
    protected IRecordCallback mRecordCallback;
    protected RecordConfig mRecordConfig;
    protected IRecordCtrl mRecordCtrl;
    protected volatile AudioRecord mRecorder;

    /* access modifiers changed from: protected */
    public abstract void doRecord();

    public AbstractAudioRecorder(RecordConfig params, AudioData data) {
        this.mRecordConfig = params;
        this.mAudioData = data;
        this.mRecordCtrl = params.getRecordCtrl();
        a();
        this.mRecordCtrl.refreshRecordState(1);
        prepareAudioRecorder();
    }

    private void a() {
        if (this.mRecordCtrl == null) {
            throw new NullPointerException("please init record ctroller");
        }
    }

    /* access modifiers changed from: protected */
    public void prepareAudioRecorder() {
        int bufferSize = Math.max(this.mRecordConfig.getFrameSize(), AudioRecord.getMinBufferSize(this.mRecordConfig.getFrequency(), this.mRecordConfig.getChannelConfig(), this.mRecordConfig.getAudioEncoding()));
        if (!PermissionHelper.hasPermission("android.permission.RECORD_AUDIO")) {
            this.logger.e("android m permission denied", new Object[0]);
            throwPermissionDenied();
        }
        if (!HardwareHelper.get().requestMic()) {
            throwMicInUsingException();
        }
        int source = this.mRecordConfig.getAudioSource();
        this.mRecorder = new AudioRecord(source, this.mRecordConfig.getFrequency(), this.mRecordConfig.getChannelConfig(), this.mRecordConfig.getAudioEncoding(), bufferSize);
        this.logger.d("prepareAudioRecorder bufferSize: " + bufferSize + "，mRecordConfig: " + this.mRecordConfig + ", state: " + this.mRecorder.getState() + ", source: " + source, new Object[0]);
        if (this.mRecorder.getState() != 1) {
            throwPermissionDenied();
        }
    }

    public void run() {
        record();
    }

    /* access modifiers changed from: protected */
    public final void record() {
        if (!isRecording()) {
            this.mRecordCtrl.refreshRecordState(2);
            try {
                this.mRecorder.startRecording();
            } catch (Exception e) {
                throw new RecordPermissionDeniedException("Record Permission denied, maybe 360 refused!!");
            }
        }
        this.logger.d("start, recordState: %s, recordingState: %s", Integer.valueOf(this.mRecorder.getState()), Integer.valueOf(this.mRecorder.getRecordingState()));
        if (this.mRecorder.getState() != 3 && this.mRecorder.getRecordingState() == 1) {
            throwPermissionDenied();
        }
        try {
            doRecord();
        } catch (Exception e2) {
            this.logger.e("doRecord error:" + e2.getMessage(), new Object[0]);
        }
    }

    public void stop() {
        this.logger.d("recorder stop", new Object[0]);
        this.mRecordCtrl.refreshRecordState(3);
    }

    public void pause() {
        this.logger.d("recorder pause", new Object[0]);
        this.mRecordCtrl.refreshRecordState(5);
    }

    public void resume() {
        this.logger.d("recorder resume", new Object[0]);
        this.mRecordCtrl.refreshRecordState(6);
    }

    public void reset() {
        this.logger.d("recorder reset", new Object[0]);
        synchronized (this.a) {
            stop();
            release();
        }
    }

    /* access modifiers changed from: protected */
    public void release() {
        if (this.mRecorder != null && this.mRecorder.getState() == 1) {
            this.logger.d("recorder release", new Object[0]);
            HardwareHelper.get().releaseMic();
            this.mRecorder.release();
            this.mRecorder = null;
            this.mRecordCallback = null;
        }
    }

    public int getMaxAmplitude() {
        if (alreadyRecording()) {
            return this.mRecordConfig.getMaxAmplitude();
        }
        this.mRecordConfig.updateMaxAmplitude(0);
        return this.mRecordConfig.getMaxAmplitude();
    }

    public boolean alreadyRecording() {
        return this.mRecorder != null && this.mRecorder.getRecordingState() == 3;
    }

    /* access modifiers changed from: protected */
    public void stopAudio() {
        if (this.mRecorder != null && this.mRecorder.getRecordingState() == 3) {
            this.logger.d("recorder stopAudio", new Object[0]);
            this.mRecorder.stop();
        }
    }

    /* access modifiers changed from: protected */
    public void calcVolume(short[] tempBuffer, int bufferRead) {
        this.mRecordConfig.updateMaxAmplitude(0);
        for (int i = 0; i < bufferRead; i++) {
            if (this.mRecordConfig.getMaxAmplitude() < tempBuffer[i]) {
                this.mRecordConfig.updateMaxAmplitude(tempBuffer[i]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void throwMicInUsingException() {
        reset();
        throw new RecorderInUsingException("Mic is in using, open error");
    }

    /* access modifiers changed from: protected */
    public void throwPermissionDenied() {
        reset();
        HardwareHelper.get().releaseMic();
        throw new RecordPermissionDeniedException("Record Permission denied");
    }

    /* access modifiers changed from: protected */
    public boolean isRecording() {
        AudioRecordState state = this.mRecordCtrl.getRecordState();
        return state.isStarted() || state.isPaused() || state.isResumed();
    }

    public void setRecordCallback(IRecordCallback callback) {
        this.mRecordCallback = callback;
    }

    /* access modifiers changed from: protected */
    public void notifyCallback(int status) {
        notifyCallback(status, null);
    }

    /* access modifiers changed from: protected */
    public void notifyCallback(int status, Info msg) {
        if (this.mRecordCallback != null) {
            this.mRecordCallback.onRecordCallback(status, msg);
        }
    }
}
