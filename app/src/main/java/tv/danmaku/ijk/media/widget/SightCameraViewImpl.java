package tv.danmaku.ijk.media.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp.RecordPhase;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoRecordParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FaceDetectionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FramePreprocessor;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnScrollListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.FalconConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.VideoConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LiveConfigItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.processor.TakePictureProcessor;
import com.alipay.mobile.beehive.plugins.capture.H5CaptureView;
import com.alipay.streammedia.encode.utils.OMXConfig;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import java.io.File;
import java.lang.ref.WeakReference;
import tv.danmaku.ijk.media.MediaConst;

@TargetApi(14)
public class SightCameraViewImpl extends SightCameraView {
    private static final String TAG = "SightCameraViewImpl";
    private WeakReference<Object> activityOrFragment;
    protected CameraParams cameraParams = new CameraParams();
    private boolean mBeautyEnable = false;
    private int mBeautyValue = -1;
    private String mBusiness = APConstants.DEFAULT_BUSINESS;
    /* access modifiers changed from: private */
    public CameraView mCameraView;
    private GestureDetectorCompat mDetector;
    private CameraFrontSightView mFrontSightView;
    private OnRecordListener mListener;
    /* access modifiers changed from: private */
    public int mOffset = 0;
    /* access modifiers changed from: private */
    public OnScrollListener mScrollListener;

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            Logger.D(SightCameraViewImpl.TAG, "onDown", new Object[0]);
            e.offsetLocation((float) SightCameraViewImpl.this.mOffset, 0.0f);
            SightCameraViewImpl.this.drawFocusArea(e.getX(), e.getY());
            SightCameraViewImpl.this.mCameraView.focusOnTouch(e);
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            Logger.D(SightCameraViewImpl.TAG, "onDoubleTap", new Object[0]);
            SightCameraViewImpl.this.mCameraView.zoom();
            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (SightCameraViewImpl.this.mScrollListener != null) {
                SightCameraViewImpl.this.mScrollListener.onScroll(e1, e2, distanceX, distanceY);
            }
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (SightCameraViewImpl.this.mScrollListener != null) {
                SightCameraViewImpl.this.mScrollListener.onFling(e1, e2, velocityX, velocityY);
            }
            return false;
        }
    }

    public SightCameraViewImpl(Context context) {
        super(context);
        init();
    }

    public SightCameraViewImpl(Context context, CameraParams params) {
        super(context);
        this.cameraParams = params;
        if (params != null) {
            this.mBeautyEnable = params.isBeautyEnabled();
        }
        init();
    }

    public void setActivityOrFragment(Object activityOrFragment2) {
        if ((activityOrFragment2 instanceof Activity) || (activityOrFragment2 instanceof Fragment)) {
            this.activityOrFragment = new WeakReference<>(activityOrFragment2);
            if (this.mCameraView != null) {
                this.mCameraView.setActivityOrFragment(this.activityOrFragment);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("params is not instanceof Activity or fragment");
    }

    public void setOnRecordListener(OnRecordListener listener) {
        this.mListener = listener;
        if (this.mCameraView != null) {
            this.mCameraView.setOnRecordListener(this.mListener);
        }
    }

    public void setBeautyValue(int value) {
        Logger.D(TAG, "setBeautyValue:" + value, new Object[0]);
        this.mBeautyValue = value;
        if (MediaConst.camereTypeIn(this.mCameraView.getCameraType(), 2, 3)) {
            if (value > 100) {
                value = 100;
            }
            this.mCameraView.setBeautyValue(value);
        }
    }

    public void setRecordParamas(VideoRecordParams params) {
        if (this.mCameraView != null) {
            this.mCameraView.setRecordParamas(params);
        }
    }

    public boolean isSupportLiveBeauty() {
        if (this.mCameraView == null || !this.mCameraView.isLive()) {
            return false;
        }
        return this.mCameraView.isSupportLiveBeauty();
    }

    private void init() {
        Logger.D(TAG, "camera view init~~~ " + this.cameraParams, new Object[0]);
        VideoConfig videoConfig = VideoDeviceWrapper.getVideoConfig();
        if (this.cameraParams != null && this.cameraParams.recordType == 1) {
            initLiveView();
        } else if (this.cameraParams != null && 2 == this.cameraParams.recordType) {
            FalconConfig config = ConfigManager.getInstance().getFalconConfig();
            if (!AppUtils.getMaterialService().getAbility().deviceSupport || !config.isFalconSwitchOn()) {
                createNormalCameraView(videoConfig);
            } else {
                this.mCameraView = FalconFactory.INS.createFalconCameraView(getContext(), config, this.mBeautyValue);
            }
        } else if (this.cameraParams != null && 4 == this.cameraParams.recordType) {
            this.cameraParams.mMode = 1;
            this.mCameraView = FalconFactory.INS.createBeautyCameraView(getContext(), 3, videoConfig.crf, videoConfig.preset);
            this.mCameraView.enableEventbus(true);
        } else if (this.mBeautyEnable) {
            this.mCameraView = FalconFactory.INS.createBeautyCameraView(getContext(), 3, videoConfig.crf, videoConfig.preset);
            this.mCameraView.setBeautyValue(this.mBeautyValue);
        } else {
            createNormalCameraView(videoConfig);
        }
        this.mCameraView.setCameraParams(this.cameraParams);
        this.mCameraView.setActivityOrFragment(this.activityOrFragment);
        this.mCameraView.setOnRecordListener(this.mListener);
        addView(this.mCameraView, 0);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                SightCameraViewImpl.this.mOffset = (SightCameraViewImpl.this.mCameraView.getWidth() - SightCameraViewImpl.this.getWidth()) / 2;
                Logger.D(SightCameraViewImpl.TAG, "offset: " + SightCameraViewImpl.this.mOffset, new Object[0]);
                SightCameraViewImpl.this.scrollTo(SightCameraViewImpl.this.mOffset, 0);
                Logger.D(SightCameraViewImpl.TAG, "mCameraView:" + SightCameraViewImpl.this.mCameraView.getWidth() + "," + SightCameraViewImpl.this.mCameraView.getHeight(), new Object[0]);
                Logger.D(SightCameraViewImpl.TAG, "sightcameraview:" + SightCameraViewImpl.this.getWidth() + "," + SightCameraViewImpl.this.getHeight(), new Object[0]);
            }
        });
        this.mDetector = new GestureDetectorCompat(getContext(), new GestureListener());
        this.mFrontSightView = new CameraFrontSightView(getContext());
        addView(this.mFrontSightView, 1);
        this.mFrontSightView.init(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
    }

    private void initLiveView() {
        LiveConfigItem liveConfig = VideoDeviceWrapper.getLiveConfig();
        if (!this.mBeautyEnable) {
            this.mCameraView = new SightCameraTextureView(getContext(), liveConfig.cpuLevel, liveConfig.crf, liveConfig.preset);
        } else if (VERSION.SDK_INT < 18 || !liveConfig.isHardEncode() || !VideoDeviceWrapper.isEncoderSupportHard()) {
            this.mCameraView = FalconFactory.INS.createBeautyCameraView(getContext(), liveConfig.cpuLevel, liveConfig.crf, liveConfig.preset);
            this.mCameraView.setBeautyValue(this.mBeautyValue);
            if (VERSION.SDK_INT >= liveConfig.rtbtapi && VideoUtils.supportGles30(getContext())) {
                this.mCameraView.enableRtBeautify(true);
            }
        } else {
            this.mCameraView = new SightCameraGLESView(getContext());
            this.mCameraView.setBeautyValue(this.mBeautyValue);
        }
        this.mCameraView.setAutoFocusMode(this.cameraParams.autoFucus);
    }

    private void createNormalCameraView(VideoConfig config) {
        if (VERSION.SDK_INT >= 18) {
            OMXConfig omxConfig = VideoDeviceWrapper.getVideoOMXConfig();
            if (FalconUtil.omxSwitch(omxConfig)) {
                this.mCameraView = new SightCameraOMXView(getContext(), omxConfig);
            } else if (config.encodeType == VideoConfig.ENCODE_HARD) {
                this.mCameraView = new SightCameraGLESView(getContext());
            }
        }
        if (this.mCameraView == null) {
            this.mCameraView = new SightCameraTextureView(getContext(), config.level, config.crf, config.preset);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public int startRecord(String business) {
        Logger.D(TAG, H5CaptureView.ACTION_START_RECORD, new Object[0]);
        this.mBusiness = business;
        this.mCameraView.stopRetryRecord();
        this.mCameraView.setRecordPhase(RecordPhase.STARTING);
        int ret = this.mCameraView.startRecord();
        if (ret != 0) {
            if (ret == -1 && !PermissionHelper.checkVideoPermission(CameraView.mMode) && this.mListener != null) {
                APVideoRecordRsp rsp = new APVideoRecordRsp();
                rsp.mRspCode = 100;
                rsp.recordPhase = RecordPhase.CANCELING;
                this.mListener.onError(rsp);
            }
            if (ret == 3 && this.mListener != null) {
                APVideoRecordRsp rsp2 = new APVideoRecordRsp();
                rsp2.mRspCode = 10;
                rsp2.recordPhase = RecordPhase.STARTING;
                this.mListener.onError(rsp2);
            }
        } else {
            if (this.mListener != null) {
                this.mListener.onStart();
            }
            this.mCameraView.setRecordPhase(RecordPhase.RECORDING);
        }
        return ret;
    }

    public void stopRecord() {
        Logger.D(TAG, H5CaptureView.ACTION_STOP_RECORD, new Object[0]);
        if (this.mCameraView != null && PermissionHelper.hasPermission("android.permission.CAMERA")) {
            this.mCameraView.setRecordPhase(RecordPhase.STOPPING);
            this.mCameraView.stopRetryRecord();
            this.mCameraView.stopRecord(true);
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            String path = getOutputPath();
            if (TextUtils.isEmpty(path)) {
                Logger.D(TAG, "stopRecord path is empty", new Object[0]);
                rsp.mRspCode = 9;
                rsp.recordPhase = RecordPhase.STOPPING;
                if (this.mListener != null) {
                    this.mListener.onError(rsp);
                    return;
                }
                return;
            }
            if (PathUtils.isRtmp(path) || PathUtils.isHttp(path) || isLive()) {
                rsp.mId = path;
                rsp.mRspCode = 0;
                rsp.mOrientation = VideoUtils.getVideoRotation(path);
            } else if (!MediaConst.isBeauty(this.mCameraView.getCameraType()) || (this.mCameraView instanceof FalconLooksViewInterface)) {
                String id = getOutputId();
                String videoPath = VideoFileManager.getInstance().generateVideoPath(id);
                if (TextUtils.isEmpty(videoPath)) {
                    Logger.D(TAG, "videoPath is null", new Object[0]);
                    rsp.mRspCode = 9;
                    rsp.recordPhase = RecordPhase.STOPPING;
                    if (this.mListener != null) {
                        this.mListener.onError(rsp);
                        return;
                    }
                    return;
                } else if (!new File(videoPath).exists()) {
                    Logger.D(TAG, "video file not exist..", new Object[0]);
                    rsp.mRspCode = 9;
                    rsp.recordPhase = RecordPhase.STOPPING;
                    if (this.mListener != null) {
                        this.mListener.onError(rsp);
                        return;
                    }
                    return;
                } else {
                    VideoFileManager.getInstance().insertRecord("", id, 2, 49, this.mBusiness);
                    VideoFileManager.getInstance().insertRecord("", id + "_thumb", 1, 20, this.mBusiness);
                    rsp.mId = id;
                    rsp.mRspCode = 0;
                    APVideoInfo info = new APVideoInfo();
                    if (!VideoUtils.parseVideoInfoByMediaMeta(path, info)) {
                        info = VideoUtils.parseVideoInfo(path);
                    }
                    if (info != null) {
                        rsp.mOrientation = info.rotation;
                        rsp.mWidth = info.width;
                        rsp.mHeight = info.height;
                        rsp.duration = (long) info.duration;
                    } else {
                        Logger.D(TAG, "Recorded file is invalid", new Object[0]);
                    }
                    rsp.size = FileUtils.fileSize(path);
                }
            } else {
                rsp.mOrientation = VideoUtils.getVideoRotation(path);
                rsp.mRspCode = 0;
                rsp.mTmpPath = path;
                rsp.mDestVideoPath = new File(new File(VideoFileManager.mBaseDir), String.format("%d.mp4", new Object[]{Long.valueOf(System.currentTimeMillis())})).getAbsolutePath();
                rsp.mDestThumbPath = rsp.mDestVideoPath.substring(0, rsp.mDestVideoPath.lastIndexOf(46)) + ".jpg";
            }
            Logger.D(TAG, "stopRecord rsp: " + rsp, new Object[0]);
            if (this.mListener != null) {
                this.mListener.onFinish(rsp);
            }
        }
    }

    public void retryLiveRecord() {
        Logger.I(TAG, "retryLiveRecord", new Object[0]);
        if (this.mCameraView != null) {
            this.mCameraView.setRecordPhase(RecordPhase.RETRYING);
            this.mCameraView.stopAndRetryLiveRecord();
        } else if (this.mListener != null) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = 10;
            rsp.recordPhase = RecordPhase.RETRYING;
            this.mListener.onError(rsp);
        }
    }

    public void pauseLiveRecord() {
        if (this.mCameraView != null) {
            this.mCameraView.setRecordPhase(RecordPhase.PAUSING);
            this.mCameraView.pauseLiveRecord();
        } else if (this.mListener != null) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = 10;
            rsp.recordPhase = RecordPhase.PAUSING;
            this.mListener.onError(rsp);
        }
    }

    public void cancelRecord(boolean releaseCamera) {
        Logger.D(TAG, "cancelRecord", new Object[0]);
        if (this.mCameraView.getCamera() != null) {
            this.mCameraView.setRecordPhase(RecordPhase.CANCELING);
            String path = getOutputPath();
            this.mCameraView.stopRecord(releaseCamera);
            boolean isSwitchingCam = this.mCameraView.isSwitching();
            if (releaseCamera || isSwitchingCam) {
                Logger.D(TAG, "camera is switching? " + isSwitchingCam + ", releaseCamera: " + releaseCamera, new Object[0]);
            } else {
                this.mCameraView.setup();
            }
            if (TextUtils.isEmpty(path)) {
                Logger.D(TAG, "cancelRecord path is empty", new Object[0]);
            } else if (PathUtils.isRtmp(path) || PathUtils.isHttp(path)) {
                Logger.D(TAG, "cancelRecord  " + path, new Object[0]);
            } else {
                File v = new File(path);
                if (v.exists()) {
                    Logger.D(TAG, "cancelRecord delete " + path + ", ret: " + v.delete(), new Object[0]);
                }
                File t = new File(VideoFileManager.getInstance().generateThumbPath(getOutputId() + "_thumb"));
                if (t.exists()) {
                    Logger.D(TAG, "cancelRecord delete " + t.getAbsolutePath() + ", ret: " + t.delete(), new Object[0]);
                }
            }
            if (this.mListener != null) {
                this.mListener.onCancel();
            }
        }
    }

    public String getOutputPath() {
        if (this.mCameraView != null) {
            return this.mCameraView.getOutputPath();
        }
        return null;
    }

    public String getOutputId() {
        if (this.mCameraView != null) {
            return this.mCameraView.getOutputId();
        }
        return null;
    }

    public void setLive(String channelId, String publishUrl) {
        if (this.mCameraView != null) {
            this.mCameraView.setLive(channelId, publishUrl);
        }
    }

    public boolean isLive() {
        if (this.mCameraView != null) {
            return this.mCameraView.isLive();
        }
        return false;
    }

    public long getCurTime() {
        if (this.mCameraView != null) {
            return this.mCameraView.getCurTime();
        }
        return 0;
    }

    public void setRetryParam(long interval, int count) {
        if (this.mCameraView != null) {
            this.mCameraView.setRetryParam(interval, count);
        }
    }

    public void setExposureCompensation(int percent) {
        if (this.mCameraView != null) {
            this.mCameraView.setExposureCompensation(percent);
        }
    }

    public void switchMute() {
        if (this.mCameraView != null) {
            this.mCameraView.setMute();
        }
    }

    /* access modifiers changed from: private */
    public void drawFocusArea(float x, float y) {
        if (this.mFrontSightView != null) {
            LayoutParams layoutParams = (LayoutParams) this.mFrontSightView.getLayoutParams();
            layoutParams.leftMargin = ((int) x) - (this.mFrontSightView.mWidth / 2);
            layoutParams.topMargin = ((int) y) - (this.mFrontSightView.mHeight / 2);
            this.mFrontSightView.setLayoutParams(layoutParams);
            this.mFrontSightView.startDraw();
            this.mFrontSightView.requestLayout();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mDetector.onTouchEvent(event);
    }

    public Camera switchCamera() {
        this.mCameraView.setRecordPhase(RecordPhase.SWITCHCAMERA);
        return this.mCameraView.switchCamera();
    }

    public Camera reopenCamera(int mode) {
        if (this.mCameraView == null || !hasCameraPermission(false)) {
            return null;
        }
        this.mCameraView.setRecordPhase(RecordPhase.REOPENCAMERA);
        return this.mCameraView.reopenCamera(mode);
    }

    public Camera reopenCamera(int mode, CameraParams params) {
        if (this.mCameraView == null || !hasCameraPermission(false)) {
            return null;
        }
        this.mCameraView.setCameraParams(params);
        this.mCameraView.setRecordPhase(RecordPhase.REOPENCAMERA);
        return this.mCameraView.reopenCamera(mode);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.mScrollListener = listener;
    }

    public int getCameraId() {
        return this.mCameraView.getCameraId();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.mCameraView != null) {
            this.mCameraView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setSelectedFilter(int filterId) {
        if (this.mCameraView instanceof FalconLooksViewInterface) {
            ((FalconLooksViewInterface) this.mCameraView).setFilter(filterId);
        }
    }

    public void setSelectedMaterial(String materialId) {
        if (this.mCameraView instanceof FalconLooksViewInterface) {
            ((FalconLooksViewInterface) this.mCameraView).setMaterial(materialId);
        }
    }

    public void setFaceDetectionListener(FaceDetectionListener listener) {
        if (this.mCameraView instanceof FalconLooksViewInterface) {
            ((FalconLooksViewInterface) this.mCameraView).setFaceDetectionListener(listener);
        }
    }

    private boolean hasCameraPermission(boolean isSendErrorMessage) {
        if (PermissionHelper.hasPermission("android.permission.CAMERA")) {
            return true;
        }
        if (this.mListener != null && isSendErrorMessage) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = 100;
            rsp.recordPhase = RecordPhase.CANCELING;
            this.mListener.onError(rsp);
        }
        return false;
    }

    public void takePicture(TakePictureListener listener, Looper looper) {
        if (hasCameraPermission(true)) {
            if (this.mCameraView != null) {
                this.mCameraView.setRecordPhase(RecordPhase.SNAPSHOTING);
            }
            if (this.mCameraView instanceof FalconLooksViewInterface) {
                ((FalconLooksViewInterface) this.mCameraView).takePicture(listener, looper);
            } else if (this.mCameraView != null) {
                try {
                    new TakePictureProcessor().takePicture(this.mCameraView.getCamera(), this.mCameraView.getCameraId(), listener, looper, this.mCameraView.cameraParams, null);
                } catch (Throwable e) {
                    Logger.E((String) TAG, "takePicture error! listener: " + listener + ", looper: " + looper + ", params: " + this.mCameraView.cameraParams, e, new Object[0]);
                    if (listener != null) {
                        listener.onPictureProcessError(1, null);
                    }
                }
            } else {
                throw new IllegalStateException("Sorry, please check camera view init!!!");
            }
        }
    }

    public void takePicture(TakePictureListener listener, Looper looper, APTakePictureOption option) {
        if (hasCameraPermission(true)) {
            if (this.mCameraView != null) {
                this.mCameraView.setRecordPhase(RecordPhase.SNAPSHOTING);
            }
            if (this.mCameraView instanceof FalconLooksViewInterface) {
                ((FalconLooksViewInterface) this.mCameraView).takePicture(listener, looper, option);
            } else if (this.mCameraView != null) {
                try {
                    new TakePictureProcessor().takePicture(this.mCameraView.getCamera(), this.mCameraView.getCameraId(), listener, looper, this.mCameraView.cameraParams, option);
                } catch (Throwable e) {
                    Logger.E((String) TAG, "takePicture error! listener: " + listener + ", looper: " + looper + ", params: " + this.mCameraView.cameraParams, e, new Object[0]);
                    if (listener != null) {
                        listener.onPictureProcessError(1, null);
                    }
                }
            } else {
                throw new IllegalStateException("Sorry, please check camera view init!!!");
            }
        }
    }

    public void releaseCamera() {
        if (this.mCameraView != null) {
            this.mCameraView.setRecordPhase(RecordPhase.RELEASECAMERA);
            this.mCameraView.releaseCamera();
        }
    }

    public void setFramePreprocessor(FramePreprocessor preprocessor) {
        if (this.cameraParams != null && 4 == this.cameraParams.recordType && MediaConst.isBeauty(this.mCameraView.getCameraType())) {
            this.mCameraView.setPreprocessor(preprocessor);
        }
    }

    public Camera getCamera() {
        if (this.mCameraView != null) {
            return this.mCameraView.getCamera();
        }
        return null;
    }
}
