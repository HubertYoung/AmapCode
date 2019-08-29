package tv.danmaku.ijk.media.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import tv.danmaku.ijk.media.encode.AVRecorder;
import tv.danmaku.ijk.media.encode.SessionConfig;

public class SightCameraGLESView extends CameraView {
    protected AVRecorder mAVRecorder;
    private SessionConfig mSessionConfig;

    public SightCameraGLESView(Context context) {
        super(context);
    }

    public SightCameraGLESView(Context context, AttributeSet set) {
        super(context, set);
    }

    public SightCameraGLESView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
    }

    public AVRecorder createAVRecorder(SessionConfig sessionConfig) {
        return new AVRecorder(sessionConfig);
    }

    /* access modifiers changed from: protected */
    public void handleOnSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        if (this.mInitCameraAsync) {
            this.mSessionConfig = getSessionConfig();
            try {
                this.mAVRecorder = createAVRecorder(this.mSessionConfig);
                this.mAVRecorder.setRecordListener(this.mListener);
                this.mAVRecorder.setVideoRecordListener(this);
                this.mAVRecorder.setMute(this.mMute);
                this.mAVRecorder.setBeautyValue(this.mBeautyValue);
                if (this.mSessionConfig.isLiveConfig() || this.mSessionConfig.isOMXVideo()) {
                    this.mSessionConfig.getmFFmpegMuxer().setVideoRecordListener(this);
                }
                notifyMicOpen();
            } catch (Exception e) {
                Logger.E((String) CameraView.TAG, "onSurfaceTextureAvailable exp:" + e.getMessage(), (Throwable) e, new Object[0]);
                notifyMicError();
                if (this.mSessionConfig.isLiveConfig() || this.mSessionConfig.isOMXVideo()) {
                    this.mSessionConfig.getmFFmpegMuxer().uninit();
                    return;
                } else {
                    this.mSessionConfig.getMuxer().clean();
                    return;
                }
            }
        }
        if (this.mInitCameraAsync) {
            if (this.mCameraStatus == 0) {
                synchronized (this.mCameraInitLock) {
                    if (this.mCameraStatus == 0) {
                        try {
                            this.mCameraInitLock.wait();
                        } catch (InterruptedException e2) {
                            Logger.E(CameraView.TAG, "InterruptedException:" + e2.getMessage(), new Object[0]);
                        }
                    }
                }
            }
            if (this.mCameraStatus == 1) {
                notifyOpenCameraError();
                return;
            } else if (getParent() instanceof SightCameraViewImpl) {
                post(new Runnable() {
                    public void run() {
                        SightCameraGLESView.this.reLayout();
                    }
                });
            }
        } else {
            try {
                initCamera(true);
            } catch (Exception e3) {
                Logger.E((String) CameraView.TAG, (String) "initCamera error", (Throwable) e3, new Object[0]);
                notifyOpenCameraError();
                UCLogUtil.UC_MM_C16(-1, e3.getMessage());
                return;
            }
        }
        if (!this.mHasReqPermissionTime) {
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_CAMERA_SURFACE_READY, System.nanoTime());
        }
        Logger.D(CameraView.TAG, "Camera Time get surfaceTexture and init camera cost=" + (System.currentTimeMillis() - sCreateTime), new Object[0]);
        if (!this.mInitCameraAsync) {
            this.mSessionConfig = getSessionConfig();
            try {
                this.mAVRecorder = createAVRecorder(this.mSessionConfig);
                this.mAVRecorder.setRecordListener(this.mListener);
                this.mAVRecorder.setVideoRecordListener(this);
                this.mAVRecorder.setMute(this.mMute);
                this.mAVRecorder.setBeautyValue(this.mBeautyValue);
                if (this.mSessionConfig.isLiveConfig() || this.mSessionConfig.isOMXVideo()) {
                    this.mSessionConfig.getmFFmpegMuxer().setVideoRecordListener(this);
                }
                notifyMicOpen();
            } catch (Exception e4) {
                Logger.E((String) CameraView.TAG, "onSurfaceTextureAvailable exp:" + e4.getMessage(), (Throwable) e4, new Object[0]);
                notifyMicError();
                if (this.mSessionConfig.isLiveConfig() || this.mSessionConfig.isOMXVideo()) {
                    this.mSessionConfig.getmFFmpegMuxer().uninit();
                } else {
                    this.mSessionConfig.getMuxer().clean();
                }
            }
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Logger.D(CameraView.TAG, "onWindowFocusChanged hasWindowFocus: " + hasWindowFocus, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.D(CameraView.TAG, "onDetachedFromWindow", new Object[0]);
    }

    public int startRecord() {
        int ret = -1;
        this.hasStart = true;
        this.logStartTime = System.currentTimeMillis();
        this.loseCount = 0;
        if (this.traceId == 0) {
            this.traceId = this.logStartTime;
        }
        if (this.mAVRecorder != null && !this.mAVRecorder.isRecording()) {
            if (isLive()) {
                Logger.D(CameraView.TAG, "startRecord audioCurTimeStamp " + this.audioCurTimeStamp + ";videoCurTimeStamp=" + this.videoCurTimeStamp, new Object[0]);
                this.mSessionConfig.audioInitTimeStamp = 0;
                this.mSessionConfig.videoInitTimeStamp = 0;
            } else {
                this.mSessionConfig.audioInitTimeStamp = 0;
                this.mSessionConfig.videoInitTimeStamp = 0;
                if (!this.cameraParams.mIgnoreOrientation) {
                    int rotation = OrientationDetector.getInstance(getContext()).getDevOrientation();
                    if (rotation != 0) {
                        try {
                            this.mSessionConfig.setOrientaion(rotation);
                        } catch (Exception e) {
                            Logger.E(CameraView.TAG, "setOrientaion exception:" + e.getMessage(), new Object[0]);
                        }
                    }
                }
            }
            try {
                ret = this.mAVRecorder.startRecording();
            } catch (RuntimeException e2) {
                notifyMicError();
            }
            notifyMicOpen();
        }
        Logger.D(CameraView.TAG, "startRecord ret=" + ret, new Object[0]);
        behaviorLog(ret, System.currentTimeMillis() - this.logStartTime, LogItem.MM_C21_TP_REH, LogItem.MM_C21_ST_RE_S, this.traceId, null);
        return ret;
    }

    public void stopRecord(boolean release) {
        stopRecordInner(release);
        if (release && this.hasStart) {
            this.hasStart = false;
            behaviorLog(this.logRet, System.currentTimeMillis() - this.logStartTime, LogItem.MM_C21_TP_REH, LogItem.MM_C21_ST_RE_E, this.traceId, null);
        }
    }

    private void stopRecordInner(boolean release) {
        if (this.mAVRecorder != null) {
            if (this.mAVRecorder.isRecording()) {
                this.mAVRecorder.stopRecording();
            }
            Logger.D(CameraView.TAG, "mAVRecorder release", new Object[0]);
            this.mAVRecorder.release();
        }
        if (release) {
            releaseCamera();
        }
    }

    public void setup() {
        Logger.I(CameraView.TAG, "setup", new Object[0]);
        this.mSessionConfig = getSessionConfig();
        try {
            this.mAVRecorder = createAVRecorder(this.mSessionConfig);
            this.mAVRecorder.setRecordListener(this.mListener);
            this.mAVRecorder.setVideoRecordListener(this);
            this.mAVRecorder.setMute(this.mMute);
            this.mAVRecorder.setBeautyValue(this.mBeautyValue);
            if (this.mSessionConfig.isLiveConfig() || this.mSessionConfig.isOMXVideo()) {
                this.mSessionConfig.getmFFmpegMuxer().setVideoRecordListener(this);
            }
            notifyMicOpen();
            this.mAVRecorder.setCamera(this.mCamera);
            this.mAVRecorder.setPreviewDisplay(this, this.mSurfaceTexture);
            Logger.I(CameraView.TAG, "setup end", new Object[0]);
        } catch (Exception e) {
            notifyMicError();
        }
    }

    public void setMute() {
        this.mMute = !this.mMute;
        Logger.D(CameraView.TAG, "setMute mMute=" + this.mMute, new Object[0]);
        if (this.mAVRecorder != null) {
            this.mAVRecorder.setMute(this.mMute);
        }
    }

    /* access modifiers changed from: protected */
    public void setLive(String channelId, String publishUrl) {
        super.setLive(channelId, publishUrl);
        if (this.mSessionConfig != null) {
            this.mSessionConfig.setLiveUrl(publishUrl);
        }
    }

    public boolean isSupportLiveBeauty() {
        return isLive();
    }

    public void setBeautyValue(int value) {
        this.mBeautyValue = value;
        if (this.mAVRecorder != null) {
            this.mAVRecorder.setBeautyValue(value);
        }
    }

    public int getCameraType() {
        return 3;
    }

    public String getOutputPath() {
        if (this.mSessionConfig != null) {
            return isLive() ? this.mSessionConfig.getLiveUrl() : this.mSessionConfig.getOutputFile().getAbsolutePath();
        }
        return null;
    }

    public String getOutputId() {
        if (this.mSessionConfig == null || isLive()) {
            return null;
        }
        return this.mSessionConfig.getVideoId();
    }

    public Camera switchCamera() {
        if (this.isSwitching) {
            Logger.D(CameraView.TAG, this + " switchCamera isSwitching return", new Object[0]);
            return null;
        }
        this.isSwitching = true;
        Logger.D(CameraView.TAG, CaptureParam.KEY_SHOW_SWITCH_CAMERA, new Object[0]);
        boolean bLiveRecording = false;
        if (!this.mSessionConfig.isLiveConfig() || !this.mAVRecorder.isRecording()) {
            stopRecordInner(true);
        } else {
            bLiveRecording = true;
            releaseCamera();
        }
        if (this.mCameraFacing == 0) {
            this.mCameraFacing = 1;
        } else {
            this.mCameraFacing = 0;
        }
        try {
            initCamera(true);
            if (!bLiveRecording) {
                this.mSessionConfig = getSessionConfig();
                try {
                    this.mAVRecorder = createAVRecorder(this.mSessionConfig);
                    this.mAVRecorder.setRecordListener(this.mListener);
                    this.mAVRecorder.setVideoRecordListener(this);
                    this.mAVRecorder.setMute(this.mMute);
                    this.mAVRecorder.setBeautyValue(this.mBeautyValue);
                    notifyMicOpen();
                } catch (Exception e) {
                    notifyMicError();
                    return null;
                }
            }
            this.mAVRecorder.setCamera(this.mCamera);
            this.mAVRecorder.setPreviewDisplay(this, this.mSurfaceTexture);
            this.isSwitching = false;
            return this.mCamera;
        } catch (Exception e2) {
            Logger.E((String) CameraView.TAG, (String) CaptureParam.KEY_SHOW_SWITCH_CAMERA, (Throwable) e2, new Object[0]);
            notifyOpenCameraError();
            return null;
        }
    }

    public Camera reopenCamera(int mode) {
        Logger.D(CameraView.TAG, "reopenCamera: " + mode, new Object[0]);
        stopRecordInner(true);
        try {
            initCamera(true);
            if (checkAudioPermission(mode)) {
                afterReopen();
            }
            return this.mCamera;
        } catch (Exception e) {
            Logger.E((String) CameraView.TAG, (String) "reopenCamera", (Throwable) e, new Object[0]);
            notifyOpenCameraError();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void afterReopen() {
        this.mSessionConfig = getSessionConfig();
        try {
            this.mAVRecorder = createAVRecorder(this.mSessionConfig);
            this.mAVRecorder.setRecordListener(this.mListener);
            this.mAVRecorder.setVideoRecordListener(this);
            this.mAVRecorder.setMute(this.mMute);
            this.mAVRecorder.setBeautyValue(this.mBeautyValue);
        } catch (Exception e) {
            notifyMicError();
        }
        notifyMicOpen();
        this.mAVRecorder.setCamera(this.mCamera);
        this.mAVRecorder.setPreviewDisplay(this, this.mSurfaceTexture);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.D(CameraView.TAG, this + "###onSurfaceTextureSizeChanged w:" + w + ", h:" + h, new Object[0]);
        if (this.mAVRecorder != null) {
            this.mAVRecorder.setCamera(this.mCamera);
            this.mAVRecorder.setPreviewDisplay(this, this.mSurfaceTexture);
            notifyPrepared();
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Logger.D(CameraView.TAG, this + "###onSurfaceTextureDestroyed", new Object[0]);
        stopRecordInner(true);
        pingQuit();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    /* access modifiers changed from: protected */
    public SessionConfig getSessionConfig() {
        SessionConfig config = new SessionConfig(getRecordType());
        if (this.cameraParams.mLandscapeVideo) {
            config.setLandscape(true);
        }
        return config;
    }
}
