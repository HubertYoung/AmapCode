package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import java.io.IOException;
import tv.danmaku.ijk.media.encode.FFmpegCameraEncoder;
import tv.danmaku.ijk.media.encode.FFmpegMicEncoder;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

@TargetApi(11)
public class SightCameraTextureView extends CameraView {
    private FFmpegCameraEncoder mCameraEncoder;
    private FFmpegMicEncoder mMicEncoder;
    private FFmpegSessionConfig mSessionConfig;

    public SightCameraTextureView(Context context) {
        super(context);
    }

    public SightCameraTextureView(Context context, int level, String crf, String preset) {
        super(context);
        this.mLevel = level;
        this.mCrf = crf;
        this.mPreset = preset;
    }

    public SightCameraTextureView(Context context, AttributeSet set) {
        super(context, set);
    }

    public SightCameraTextureView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
    }

    /* access modifiers changed from: protected */
    public void handleOnSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        if (this.mInitCameraAsync) {
            if (this.mCameraStatus == 0) {
                synchronized (this.mCameraInitLock) {
                    if (this.mCameraStatus == 0) {
                        try {
                            this.mCameraInitLock.wait();
                        } catch (InterruptedException e) {
                            Logger.E(CameraView.TAG, "InterruptedException:" + e.getMessage(), new Object[0]);
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
                        SightCameraTextureView.this.reLayout();
                    }
                });
            }
        } else {
            try {
                initCamera(true);
            } catch (Exception e2) {
                Logger.E((String) CameraView.TAG, (String) "handleOnSurfaceTextureAvailable", (Throwable) e2, new Object[0]);
                notifyOpenCameraError();
                return;
            }
        }
        if (!this.mHasReqPermissionTime) {
            VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_CAMERA_SURFACE_READY, System.nanoTime());
        }
        Logger.D(CameraView.TAG, "Camera Time get surfaceTexture and init camera cost=" + (System.currentTimeMillis() - sCreateTime), new Object[0]);
        if (VideoUtils.previewRunning(this.mCamera)) {
            Logger.D(CameraView.TAG, "preview is running, stop it.", new Object[0]);
            this.mCamera.stopPreview();
        }
        try {
            this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            startPreview();
            this.mSessionConfig = createConfig();
            this.mSessionConfig.cpu_level = this.mLevel;
            this.mSessionConfig.crf = this.mCrf;
            this.mSessionConfig.preset = this.mPreset;
            this.mCameraEncoder = new FFmpegCameraEncoder(this.mCamera, this.mSessionConfig, this, this.mCameraFacing, this.mRotation);
            try {
                this.mMicEncoder = new FFmpegMicEncoder(this.mSessionConfig);
                this.mMicEncoder.setRecordListener(this.mListener);
                this.mMicEncoder.setVideoRecordListener(this);
                this.mMicEncoder.setMute(this.mMute);
                notifyMicOpen();
                if (VideoDeviceWrapper.dynPermissionCheck()) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public void run() {
                            SightCameraTextureView.this.notifyPrepared();
                        }
                    }, 150);
                } else {
                    notifyPrepared();
                }
            } catch (Exception e3) {
                notifyMicError();
            }
        } catch (Exception e4) {
            Logger.E((String) CameraView.TAG, "onSurfaceTextureAvailable exp:" + e4.getMessage(), (Throwable) e4, new Object[0]);
            notifyOpenCameraError();
        }
    }

    public Camera switchCamera() {
        if (this.isSwitching) {
            Logger.D(CameraView.TAG, this + " switchCamera isSwitching return", new Object[0]);
            return null;
        }
        this.isSwitching = true;
        boolean needResume = this.mCameraEncoder.isRecording();
        Logger.D(CameraView.TAG, this + " switchCamera needResume " + needResume, new Object[0]);
        if (needResume) {
            this.mCameraEncoder.setIsRecording(false);
        }
        releaseCamera();
        if (this.mCameraFacing == 0) {
            this.mCameraFacing = 1;
        } else {
            this.mCameraFacing = 0;
        }
        try {
            initCamera(true);
            try {
                this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            } catch (IOException e) {
                Logger.D(CameraView.TAG, "setPreviewTexture: " + e, new Object[0]);
            }
            startPreview();
            if (needResume) {
                this.mCameraEncoder.switchCamera(this.mCamera, this.mCameraFacing);
                this.mCameraEncoder.setIsRecording(true);
            } else {
                setup();
            }
            this.mCameraEncoder.setOrientation(this.mCameraFacing);
            this.isSwitching = false;
            return this.mCamera;
        } catch (Exception e2) {
            Logger.E((String) CameraView.TAG, (String) CaptureParam.KEY_SHOW_SWITCH_CAMERA, (Throwable) e2, new Object[0]);
            this.isSwitching = false;
            notifyOpenCameraError();
            return null;
        }
    }

    public Camera reopenCamera(int mode) {
        releaseCamera();
        try {
            initCamera(true);
            try {
                this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            } catch (IOException e) {
                Logger.D(CameraView.TAG, "setPreviewTexture: " + e, new Object[0]);
            }
            startPreview();
            if (checkAudioPermission(mode)) {
                afterReopen();
            }
            return this.mCamera;
        } catch (Exception e2) {
            Logger.E((String) CameraView.TAG, (String) "reopenCamera", (Throwable) e2, new Object[0]);
            notifyOpenCameraError();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void afterReopen() {
        setup();
        this.mCameraEncoder.setOrientation(this.mCameraFacing);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.D(CameraView.TAG, this + "###onSurfaceTextureSizeChanged w:" + w + ", h:" + h, new Object[0]);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Logger.D(CameraView.TAG, this + "###onSurfaceTextureDestroyed", new Object[0]);
        stopRecord(true);
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
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
        int ret = 0;
        if (!isRecording()) {
            if (isLive()) {
                Logger.D(CameraView.TAG, "startRecord audioCurTimeStamp " + this.audioCurTimeStamp + ";videoCurTimeStamp=" + this.videoCurTimeStamp, new Object[0]);
                this.mSessionConfig.audioInitTimeStamp = 0;
                this.mSessionConfig.videoInitTimeStamp = 0;
            } else {
                this.mSessionConfig.audioInitTimeStamp = 0;
                this.mSessionConfig.videoInitTimeStamp = 0;
                if (!this.cameraParams.mIgnoreOrientation) {
                    this.mSessionConfig.rotate = OrientationDetector.getInstance(getContext()).getDevOrientation();
                }
            }
            ret = this.mCameraEncoder.start();
            if (ret != 0) {
                notifyEncodeError(ret);
                return ret;
            }
            this.mMicEncoder.startRecording();
        }
        return ret;
    }

    public boolean isRecording() {
        return this.mCameraEncoder.isRecording();
    }

    public void stopRecord(boolean release) {
        if (this.mMicEncoder != null) {
            this.mMicEncoder.stopRecording();
        }
        if (this.mCameraEncoder != null) {
            Logger.D(CameraView.TAG, "stopRecord " + this.mCameraEncoder.stop(), new Object[0]);
        }
        if (release) {
            releaseCamera();
        }
    }

    public void setup() {
        this.mSessionConfig = createConfig();
        this.mSessionConfig.cpu_level = this.mLevel;
        this.mSessionConfig.crf = this.mCrf;
        this.mSessionConfig.preset = this.mPreset;
        this.mCameraEncoder = new FFmpegCameraEncoder(this.mCamera, this.mSessionConfig, this, this.mCameraFacing, this.mRotation);
        try {
            if (this.mMicEncoder != null) {
                this.mMicEncoder.stopRecording();
            }
            this.mMicEncoder = new FFmpegMicEncoder(this.mSessionConfig);
            this.mMicEncoder.setRecordListener(this.mListener);
            this.mMicEncoder.setVideoRecordListener(this);
            this.mMicEncoder.setMute(this.mMute);
            notifyMicOpen();
        } catch (Exception e) {
            notifyMicError();
        }
    }

    public void setMute() {
        this.mMute = !this.mMute;
        Logger.D(CameraView.TAG, "setMute mMute=" + this.mMute, new Object[0]);
        if (this.mMicEncoder != null) {
            this.mMicEncoder.setMute(this.mMute);
        }
    }

    public void setLive(String channelId, String publishUrl) {
        super.setLive(channelId, publishUrl);
        if (this.mSessionConfig != null) {
            this.mSessionConfig.vPublishUrl = publishUrl;
        }
    }

    public String getOutputPath() {
        return this.mSessionConfig == null ? "" : this.mSessionConfig.vPublishUrl;
    }

    public String getOutputId() {
        return this.mSessionConfig == null ? "" : this.mSessionConfig.getVideoId();
    }

    private FFmpegSessionConfig createConfig() {
        FFmpegSessionConfig config = FFmpegSessionConfig.create(getRecordType());
        if (this.cameraParams.mLandscapeVideo) {
            config.mLandscape = true;
            config.rotate = 90;
        }
        return config;
    }
}
