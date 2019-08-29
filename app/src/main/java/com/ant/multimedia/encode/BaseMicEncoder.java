package com.ant.multimedia.encode;

import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import java.lang.ref.WeakReference;

public abstract class BaseMicEncoder {
    private WeakReference<OnRecordListener> a;
    private WeakReference<VideoRecordListener> b;
    protected volatile boolean mIsRecording;
    protected volatile boolean mMute = false;

    public abstract boolean isRecording();

    public abstract void startRecording();

    public abstract void stopRecording();

    public void setRecordListener(OnRecordListener listener) {
        this.a = new WeakReference<>(listener);
    }

    public OnRecordListener getRecordListener() {
        if (this.a != null) {
            return (OnRecordListener) this.a.get();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void a(int code) {
        Log.e("MicEncoder", "notifyError code: " + code, new Exception("mic error"));
        if (this.a != null && this.a.get() != null) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = code;
            ((OnRecordListener) this.a.get()).onError(rsp);
        }
    }

    public void setVideoRecordListener(VideoRecordListener listener) {
        this.b = new WeakReference<>(listener);
    }

    public VideoRecordListener getVideoRecordListener() {
        if (this.b != null) {
            return (VideoRecordListener) this.b.get();
        }
        return null;
    }

    public void setMute(boolean bMute) {
        this.mMute = bMute;
    }

    public boolean audioThreadReady() {
        return true;
    }
}
