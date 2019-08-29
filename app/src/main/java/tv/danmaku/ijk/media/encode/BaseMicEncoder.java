package tv.danmaku.ijk.media.encode;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.lang.ref.WeakReference;

public abstract class BaseMicEncoder {
    private static final String TAG = "MicEncoder";
    protected volatile boolean mIsRecording;
    protected volatile boolean mMute = false;
    private WeakReference<OnRecordListener> mRecordListener;
    private WeakReference<VideoRecordListener> mVideoRecordListener;

    public abstract boolean isRecording();

    public abstract void startRecording();

    public abstract void stopRecording();

    public void setRecordListener(OnRecordListener listener) {
        this.mRecordListener = new WeakReference<>(listener);
    }

    public OnRecordListener getRecordListener() {
        if (this.mRecordListener != null) {
            return (OnRecordListener) this.mRecordListener.get();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void notifyError(int code) {
        Logger.E((String) TAG, (Throwable) new Exception("mic error"), "notifyError code: " + code, new Object[0]);
        if (this.mRecordListener != null && this.mRecordListener.get() != null) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = code;
            ((OnRecordListener) this.mRecordListener.get()).onError(rsp);
        }
    }

    public void setVideoRecordListener(VideoRecordListener listener) {
        this.mVideoRecordListener = new WeakReference<>(listener);
    }

    public VideoRecordListener getVideoRecordListener() {
        if (this.mVideoRecordListener != null) {
            return (VideoRecordListener) this.mVideoRecordListener.get();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void notifyAudioStart() {
        Logger.D(TAG, "notifyAudioStart", new Object[0]);
        VideoRecordListener listener = getVideoRecordListener();
        if (listener != null) {
            listener.onAudioStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onAudioTimeUpdate(long ts) {
        VideoRecordListener listener = getVideoRecordListener();
        if (listener != null) {
            listener.onAudioTimeUpdate(ts);
        }
    }

    public void setMute(boolean bMute) {
        this.mMute = bMute;
    }

    public boolean audioThreadReady() {
        return true;
    }
}
