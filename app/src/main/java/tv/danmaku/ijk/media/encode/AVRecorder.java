package tv.danmaku.ijk.media.encode;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import tv.danmaku.ijk.media.widget.CameraView;

public class AVRecorder {
    private static final String TAG = "AVRecorder";
    protected CameraEncoder mCamEncoder;
    private volatile boolean mIsRecording;
    protected BaseMicEncoder mMicEncoder;
    private SessionConfig sessionConfig;

    public AVRecorder(SessionConfig config) {
        this.sessionConfig = config;
        init(config);
    }

    /* access modifiers changed from: protected */
    public void init(SessionConfig config) {
        if (config.isLiveConfig()) {
            this.mCamEncoder = FalconFactory.INS.createBeautyCameraEncoder(config);
            this.mMicEncoder = new FFmpegMicEncoder(config.getFFmpegSessionConfig());
        } else if (config.isOMXVideo()) {
            this.mCamEncoder = new CameraEncoder(config);
            this.mCamEncoder.mUseVideoEncoderNative = true;
            this.mMicEncoder = new FFmpegMicEncoder(config.getFFmpegSessionConfig());
        } else {
            this.mCamEncoder = new CameraEncoder(config);
            this.mMicEncoder = new MicrophoneEncoder(config);
        }
        this.mIsRecording = false;
    }

    public void setRecordListener(OnRecordListener recordListener) {
        if (this.mMicEncoder != null) {
            this.mMicEncoder.setRecordListener(recordListener);
        }
    }

    public void setVideoRecordListener(VideoRecordListener listener) {
        if (this.mMicEncoder != null) {
            this.mMicEncoder.setVideoRecordListener(listener);
        }
    }

    public void setPreviewDisplay(CameraView display, SurfaceTexture surfaceTexture) {
        this.mCamEncoder.setPreviewDisplay(display);
        this.mCamEncoder.onSurfaceTextureAvailable(surfaceTexture);
    }

    public void setCamera(Camera camera) {
        this.mCamEncoder.setCamera(camera);
    }

    public int startRecording() {
        if (!this.mCamEncoder.checkSurface()) {
            Logger.D(TAG, "check camera encoder surface fail, skip...", new Object[0]);
            return 3;
        }
        int ret = this.sessionConfig.initFFmpegMuxer();
        if (ret != 0) {
            return ret;
        }
        this.mIsRecording = true;
        this.sessionConfig.updateLiveInitTimeStamp();
        if (this.mMicEncoder.audioThreadReady()) {
            this.mMicEncoder.startRecording();
        }
        this.mCamEncoder.startRecording();
        return ret;
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    public void stopRecording() {
        this.mIsRecording = false;
        this.mMicEncoder.stopRecording();
        this.mCamEncoder.stopRecording();
        this.sessionConfig.uninitFFmpegMuxer();
    }

    public void release() {
        this.mMicEncoder.stopRecording();
        this.mCamEncoder.release();
    }

    public void releaseGl() {
        if (this.mCamEncoder != null) {
            this.mCamEncoder.releaseGL();
        }
    }

    public void setMute(boolean bMute) {
        if (this.mMicEncoder != null) {
            this.mMicEncoder.setMute(bMute);
        }
    }

    public void setBeautyValue(int value) {
        if (this.mCamEncoder.isBeautyCameraEncoder()) {
            this.mCamEncoder.setBeautyValue(value);
        }
    }
}
