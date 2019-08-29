package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler.IRecordCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioNsAgcProcess;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ICode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.base.SoLibLoader;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public abstract class AbstractAudioEncoder implements Runnable {
    protected Logger logger = Logger.getLogger(getClass().getSimpleName());
    protected AudioData mAudioData;
    protected IAudioNsAgcProcess mAudioNsAgcProcess;
    protected ICode mCoder;
    protected IEncodeOutputHandler mEncodeOutputHandler;
    protected EncodeConfig mParams;
    protected IRecordCallback mRecordCallback;
    protected IRecordCtrl mRecordCtrl;
    protected AtomicBoolean mRunning = new AtomicBoolean(false);

    /* access modifiers changed from: protected */
    public abstract void doEncode(byte[] bArr);

    static {
        IjkMediaPlayer.loadLibrariesOnce(new SoLibLoader());
    }

    public AbstractAudioEncoder(EncodeConfig params, AudioData data) {
        if (params == null) {
            throw new RuntimeException("Params can not be null,pls check again!!!");
        }
        this.mCoder = params.getCoder();
        this.mParams = params;
        this.mAudioNsAgcProcess = params.getAudioNsAgcProcess();
        this.mAudioNsAgcProcess.init(params);
        this.mEncodeOutputHandler = params.getEncodeOutputHandler();
        this.mAudioData = data;
        this.mRecordCtrl = params.getRecordCtrl();
        a();
    }

    private void a() {
        if (this.mRecordCtrl == null) {
            throw new NullPointerException("please init record ctroller");
        }
    }

    public void pause() {
        this.logger.d("encoder pause", new Object[0]);
    }

    public void resume() {
        this.logger.d("encoder resume", new Object[0]);
    }

    public void stop() {
        this.logger.d("encoder stop", new Object[0]);
        this.mRunning.set(false);
    }

    public void reset() {
        this.logger.d("encoder reset", new Object[0]);
        this.mRunning.set(false);
    }

    public void run() {
        b();
    }

    private void b() {
        this.mRunning.set(true);
        if (this.mCoder.openAudioEncoder(this.mParams) != 0) {
            notifyCallback(7, new Info(1, "failed to open audioEncoder"));
            return;
        }
        byte[] buf = this.mCoder.makeBuffer(this.mParams);
        try {
            doEncode(buf);
        } finally {
            a(buf);
            c();
        }
    }

    private void a(byte[] buf) {
        this.mCoder.closeAudioEncoder(buf, this.mEncodeOutputHandler);
    }

    private void c() {
        this.mAudioNsAgcProcess.release();
        if (this.mEncodeOutputHandler != null) {
            this.mEncodeOutputHandler.handleFinished();
        }
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
