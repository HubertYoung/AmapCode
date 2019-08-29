package tv.danmaku.ijk.media.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp.RecordPhase;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.LiveData;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoRecordParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FramePreprocessor;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.netdet.NetDetect;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.LiveStatistic;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.HardwareHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.StringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.MediaConst;
import tv.danmaku.ijk.media.encode.LiveCounter;
import tv.danmaku.ijk.media.encode.SessionConfig;
import tv.danmaku.ijk.media.encode.VideoRecordListener;

@TargetApi(15)
public abstract class CameraView extends TextureView implements AutoFocusCallback, SurfaceTextureListener, VideoRecordListener {
    private static final int CAMERA_CHECK_MSG = 1;
    protected static final int CAMERA_INIT_FAILED = 1;
    protected static final int CAMERA_INIT_SUCCESS = 2;
    protected static final int CAMERA_NOT_INIT = 0;
    private static final int CAMERA_PAUSE_RECORD = 5;
    private static final int CAMERA_RESET_FOCUS = 2;
    private static final int CAMERA_RETRY_RECORD = 4;
    private static final int CAMERA_STOP_AND_RETRY_RECORD = 3;
    private static final int MSG_PING_IN = 1;
    private static final int MSG_PING_OUT = 2;
    private static final int MSG_PING_QUIT = 3;
    public static final String TAG = "CameraView";
    public static int mMode = 0;
    public static long sCreateTime = 0;
    protected static final AtomicBoolean sRequirePermissions = new AtomicBoolean(false);
    protected WeakReference<Object> activityOrFragment;
    protected long audioCurTimeStamp = 0;
    private boolean autoFocusEnable = true;
    private volatile boolean bLastNotify = false;
    private volatile boolean bNeedNotify = false;
    protected CameraParams cameraParams;
    private volatile int countInterval = (ConfigManager.getInstance().getCommonConfigItem().liveConf.rCountInterval * 1000);
    private int countSwith = ConfigManager.getInstance().getCommonConfigItem().liveConf.rCountSwitch;
    private int curRetryIndex = 0;
    protected volatile boolean hasStart = false;
    protected boolean initCameraError = false;
    private boolean isAudioStart = false;
    private AtomicBoolean isPause = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean isRetrying = new AtomicBoolean(false);
    protected boolean isSwitching = false;
    protected String lastChannelId;
    protected String lastPublishUrl;
    protected volatile long logPrepareTime = 0;
    protected volatile int logRet = 0;
    protected volatile long logStartTime = 0;
    protected volatile int loseCount = 0;
    protected volatile int mBeautyValue = -1;
    protected Camera mCamera;
    protected int mCameraFacing = 0;
    private int mCameraId = -1;
    protected Object mCameraInitLock = new Object();
    protected int mCameraStatus = 0;
    private Context mContext;
    protected String mCrf = null;
    private int mDisplayOrientation = 90;
    private boolean mFocusAreaSupported;
    private volatile int mFullInterval = (ConfigManager.getInstance().getCommonConfigItem().liveConf.fullInterval * 1000);
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CameraView.this.notifyOpenCameraError();
                    return;
                case 2:
                    CameraView.this.mIsFocusing = false;
                    return;
                case 3:
                    CameraView.this.handleStopAndRetryRecord();
                    return;
                case 4:
                    CameraView.this.handleRetryRecord();
                    return;
                case 5:
                    CameraView.this.handlePauseLiveRecord();
                    return;
                default:
                    return;
            }
        }
    };
    protected boolean mHasReqPermissionTime = false;
    protected boolean mInitCameraAsync = false;
    /* access modifiers changed from: private */
    public boolean mIsFocusing = false;
    protected boolean mIsOpened = false;
    private volatile long mLastCountTime = 0;
    private LiveCounter mLastCounter = null;
    private volatile long mLastFullTime = 0;
    private volatile long mLastSucTime = 0;
    protected int mLevel = 4;
    protected OnRecordListener mListener;
    private boolean mMeteringAreaSupported;
    protected volatile boolean mMute = false;
    private PingHandler mPingHandler;
    protected String mPreset = null;
    protected Size mPreviewSize;
    private VideoRecordParams mRecordParams = null;
    /* access modifiers changed from: private */
    public RecordPhase mRecordPhase = RecordPhase.INIT;
    protected int mRotation = 90;
    private int mScreenHeight = 0;
    private int mScreenRotation = -1;
    private int mScreenWidth = 0;
    private volatile int mSucInterval = (ConfigManager.getInstance().getCommonConfigItem().liveConf.sucInterval * 1000);
    protected SurfaceTexture mSurfaceTexture;
    private HandlerThread mThread;
    private volatile int pingSwitch = ConfigManager.getInstance().getCommonConfigItem().liveConf.pingSwitch;
    private final String pingUrl = ConfigManager.getInstance().getCommonConfigItem().liveConf.pingUrl;
    private float preRate = ConfigManager.getInstance().getCommonConfigItem().videoConf.prerate;
    private final Object releaseLock = new Object();
    private long retryInterval = 5000;
    private int retryMaxCount = 3;
    private float sizeRate = ConfigManager.getInstance().getCommonConfigItem().videoConf.sizerate;
    protected volatile long traceId = 0;
    protected long videoCurTimeStamp = 0;
    private int weakNetSwith = ConfigManager.getInstance().getCommonConfigItem().liveConf.weakNetSwitch;

    private class PingHandler extends Handler {
        private Looper mLooper;
        private WeakReference<CameraView> mReference;

        PingHandler(CameraView view, Looper looper) {
            super(looper);
            this.mLooper = looper;
            this.mReference = new WeakReference<>(view);
        }

        public void handleMessage(Message msg) {
            CameraView cv = (CameraView) this.mReference.get();
            if (cv != null || msg.what == 3) {
                switch (msg.what) {
                    case 1:
                        cv.handPingIn();
                        return;
                    case 2:
                        cv.handPingOut();
                        return;
                    case 3:
                        try {
                            this.mLooper.quit();
                            return;
                        } catch (Exception e) {
                            return;
                        }
                    default:
                        return;
                }
            } else {
                Logger.W(CameraView.TAG, "outter class is null", new Object[0]);
            }
        }
    }

    private class WrapperListener implements OnRecordListener {
        private OnRecordListener listener;

        public WrapperListener(OnRecordListener listener2) {
            this.listener = listener2;
        }

        public void onStart() {
            if (this.listener != null) {
                this.listener.onStart();
            }
            CameraView.this.logStartTime = System.currentTimeMillis();
        }

        public void onError(APVideoRecordRsp rsp) {
            if (CameraView.this.needRetry(rsp.mRspCode)) {
                Logger.D(CameraView.TAG, "onError isRetrying: " + CameraView.this.isRetrying.get(), new Object[0]);
                CameraView.this.mHandler.sendEmptyMessage(3);
                return;
            }
            Logger.D(CameraView.TAG, "onError code: " + rsp.mRspCode, new Object[0]);
            rsp.recordPhase = CameraView.this.mRecordPhase;
            if (this.listener != null) {
                this.listener.onError(rsp);
            }
            long time = 0;
            if (CameraView.this.logStartTime != 0) {
                time = System.currentTimeMillis() - CameraView.this.logStartTime;
            }
            CameraView cameraView = CameraView.this;
            CameraView cameraView2 = CameraView.this;
            int i = rsp.mRspCode;
            cameraView2.logRet = i;
            cameraView.behaviorLog(i, time, LogItem.MM_C21_TP_RE, LogItem.MM_C21_ST_RE_ERR, CameraView.this.traceId == 0 ? System.currentTimeMillis() : CameraView.this.traceId, "record error");
        }

        public void onFinish(APVideoRecordRsp rsp) {
            if (this.listener != null) {
                this.listener.onFinish(rsp);
            }
            CameraView.this.logRet = 0;
        }

        public void onCancel() {
            if (this.listener != null) {
                this.listener.onCancel();
            }
            CameraView.this.logRet = 0;
        }

        public void onInfo(int code, Bundle extra) {
            Logger.D(CameraView.TAG, "onInfo code=" + code, new Object[0]);
            if (this.listener != null) {
                this.listener.onInfo(code, extra);
            }
        }

        public void onPrepared(APVideoRecordRsp rsp) {
            if (this.listener != null) {
                this.listener.onPrepared(rsp);
            }
            if (CameraView.this.logPrepareTime == 0) {
                CameraView.this.logPrepareTime = System.currentTimeMillis();
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void afterReopen();

    /* access modifiers changed from: protected */
    public abstract String getOutputId();

    /* access modifiers changed from: protected */
    public abstract String getOutputPath();

    /* access modifiers changed from: protected */
    public abstract void handleOnSurfaceTextureAvailable(SurfaceTexture surfaceTexture);

    /* access modifiers changed from: protected */
    public abstract Camera reopenCamera(int i);

    /* access modifiers changed from: protected */
    public abstract void setMute();

    /* access modifiers changed from: protected */
    public abstract void setup();

    /* access modifiers changed from: protected */
    public abstract int startRecord();

    /* access modifiers changed from: protected */
    public abstract void stopRecord(boolean z);

    /* access modifiers changed from: protected */
    public abstract Camera switchCamera();

    public CameraView(Context context) {
        super(context);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).clear();
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_VIEW_CREATE, System.nanoTime());
        sCreateTime = System.currentTimeMillis();
        this.mContext = context;
        if (ConfigManager.getInstance().isCameraOptSwitchOn() && PermissionHelper.checkVideoPermission(mMode)) {
            initCameraAsync();
        }
        setSurfaceTextureListener(this);
        Logger.D(TAG, "CameraView construct!", new Object[0]);
    }

    public CameraView(Context context, AttributeSet set) {
        super(context, set);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).clear();
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_VIEW_CREATE, System.nanoTime());
        sCreateTime = System.currentTimeMillis();
        this.mContext = context;
        if (ConfigManager.getInstance().isCameraOptSwitchOn() && PermissionHelper.checkVideoPermission(mMode)) {
            initCameraAsync();
        }
        setSurfaceTextureListener(this);
    }

    public CameraView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).clear();
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_VIEW_CREATE, System.nanoTime());
        sCreateTime = System.currentTimeMillis();
        this.mContext = context;
        if (ConfigManager.getInstance().isCameraOptSwitchOn() && PermissionHelper.checkVideoPermission(mMode)) {
            initCameraAsync();
        }
        setSurfaceTextureListener(this);
    }

    private void initCameraAsync() {
        Logger.D(TAG, "initCameraAsync...", new Object[0]);
        this.mInitCameraAsync = true;
        TaskScheduleManager.get().commonHandler().postAtFrontOfQueue(new Runnable() {
            /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r5 = this;
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ Exception -> 0x001c }
                    r2 = 0
                    r1.initCamera(r2)     // Catch:{ Exception -> 0x001c }
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this
                    java.lang.Object r2 = r1.mCameraInitLock
                    monitor-enter(r2)
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0019 }
                    r3 = 2
                    r1.mCameraStatus = r3     // Catch:{ all -> 0x0019 }
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0019 }
                    java.lang.Object r1 = r1.mCameraInitLock     // Catch:{ all -> 0x0019 }
                    r1.notifyAll()     // Catch:{ all -> 0x0019 }
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                L_0x0018:
                    return
                L_0x0019:
                    r1 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                    throw r1
                L_0x001c:
                    r0 = move-exception
                    java.lang.String r1 = "CameraView"
                    java.lang.String r2 = "initCamera error"
                    r3 = 0
                    java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0045 }
                    com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r1, r2, r0, r3)     // Catch:{ all -> 0x0045 }
                    r1 = -1
                    java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0045 }
                    com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C16(r1, r2)     // Catch:{ all -> 0x0045 }
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this
                    java.lang.Object r2 = r1.mCameraInitLock
                    monitor-enter(r2)
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0042 }
                    r3 = 1
                    r1.mCameraStatus = r3     // Catch:{ all -> 0x0042 }
                    tv.danmaku.ijk.media.widget.CameraView r1 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0042 }
                    java.lang.Object r1 = r1.mCameraInitLock     // Catch:{ all -> 0x0042 }
                    r1.notifyAll()     // Catch:{ all -> 0x0042 }
                    monitor-exit(r2)     // Catch:{ all -> 0x0042 }
                    goto L_0x0018
                L_0x0042:
                    r1 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0042 }
                    throw r1
                L_0x0045:
                    r1 = move-exception
                    tv.danmaku.ijk.media.widget.CameraView r2 = tv.danmaku.ijk.media.widget.CameraView.this
                    java.lang.Object r2 = r2.mCameraInitLock
                    monitor-enter(r2)
                    tv.danmaku.ijk.media.widget.CameraView r3 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0059 }
                    r4 = 1
                    r3.mCameraStatus = r4     // Catch:{ all -> 0x0059 }
                    tv.danmaku.ijk.media.widget.CameraView r3 = tv.danmaku.ijk.media.widget.CameraView.this     // Catch:{ all -> 0x0059 }
                    java.lang.Object r3 = r3.mCameraInitLock     // Catch:{ all -> 0x0059 }
                    r3.notifyAll()     // Catch:{ all -> 0x0059 }
                    monitor-exit(r2)     // Catch:{ all -> 0x0059 }
                    throw r1
                L_0x0059:
                    r1 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0059 }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.widget.CameraView.AnonymousClass2.run():void");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setOnRecordListener(OnRecordListener listener) {
        this.mListener = new WrapperListener(listener);
    }

    /* access modifiers changed from: protected */
    public void setActivityOrFragment(WeakReference<Object> activityOrFragment2) {
        this.activityOrFragment = activityOrFragment2;
    }

    public void setRecordPhase(RecordPhase phase) {
        this.mRecordPhase = phase;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int w, int h) {
        Logger.D(TAG, this + "###onSurfaceTextureAvailable w:" + w + ", h:" + h, new Object[0]);
        Logger.D(TAG, "onSurfaceTextureAvailable activityOrFragment: " + (this.activityOrFragment != null ? this.activityOrFragment.get() : null), new Object[0]);
        this.mSurfaceTexture = surfaceTexture;
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_BUFFER_PREPARED, System.nanoTime());
        if (PermissionHelper.checkVideoPermission(mMode) || this.activityOrFragment == null || this.activityOrFragment.get() == null) {
            handleOnSurfaceTextureAvailable(this.mSurfaceTexture);
            return;
        }
        this.mHasReqPermissionTime = true;
        PermissionHelper.requireVideoPermission(this.activityOrFragment.get(), mMode, 2);
    }

    /* access modifiers changed from: protected */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean granted = true;
        int i = 0;
        while (true) {
            if (i >= grantResults.length) {
                break;
            }
            boolean grantResult = grantResults[i] == 0;
            granted &= grantResult;
            if (!grantResult) {
                if ("android.permission.CAMERA".equals(permissions[i])) {
                    notifyOpenCameraError();
                    break;
                } else if ("android.permission.RECORD_AUDIO".equals(permissions[i])) {
                    notifyMicError();
                    break;
                }
            }
            i++;
        }
        if (!granted) {
            return;
        }
        if (requestCode == 4) {
            afterReopen();
        } else {
            handleOnSurfaceTextureAvailable(this.mSurfaceTexture);
        }
    }

    /* access modifiers changed from: protected */
    public void setCameraParams(CameraParams params) {
        this.cameraParams = params;
        if (params != null) {
            this.mCameraFacing = params.isDefaultCameraFront() ? 1 : 0;
            mMode = params.mMode;
        }
    }

    private boolean hasUserSet() {
        if (this.cameraParams == null || StringUtils.isEmptyOrNullStr(this.cameraParams.getFlashMode())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void setExposureCompensation(int percent) {
        Parameters parameters = this.mCamera.getParameters();
        int max = parameters.getMaxExposureCompensation();
        int min = parameters.getMinExposureCompensation();
        float step = parameters.getExposureCompensationStep();
        int cur = parameters.getExposureCompensation();
        Logger.D(TAG, "setExposureCompensation max: " + max + " min: " + min + " step: " + step + " cur: " + cur + " lock: " + parameters.getAutoExposureLock(), new Object[0]);
        if (!(max == 0 && min == 0) && max >= 0 && min <= 0) {
            int value = min + (((max - min) * (percent + 100)) / 200);
            Logger.D(TAG, "setExposureCompensation percent: " + percent + " value: " + value, new Object[0]);
            parameters.setExposureCompensation(value);
            this.mCamera.setParameters(parameters);
        }
    }

    /* access modifiers changed from: protected */
    public void setRecordParamas(VideoRecordParams params) {
        this.mRecordParams = params;
        if (this.mRecordParams != null) {
            this.countInterval = this.mRecordParams.mSampleTimeInterval * 1000;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isSwitching() {
        Logger.D(TAG, "isSwitching " + this.isSwitching, new Object[0]);
        return this.isSwitching;
    }

    /* access modifiers changed from: protected */
    public int getRecordType() {
        if (this.cameraParams == null) {
            return 0;
        }
        return this.cameraParams.recordType;
    }

    /* access modifiers changed from: private */
    public boolean needRetry(int code) {
        return code == 8 && this.curRetryIndex < this.retryMaxCount && isLive();
    }

    /* access modifiers changed from: protected */
    public void stopRetryRecord() {
        this.mHandler.removeMessages(4);
        this.isRetrying.set(false);
        this.curRetryIndex = 0;
    }

    /* access modifiers changed from: private */
    public void handleRetryRecord() {
        if (this.isPause.get() || !this.isRetrying.get() || !isAvailable()) {
            Logger.D(TAG, "retryRecord return, isRetrying:" + this.isRetrying.get(), new Object[0]);
            return;
        }
        Logger.D(TAG, "retryRecord curRetryIndex: " + this.curRetryIndex + ", retryMaxCount:" + this.retryMaxCount, new Object[0]);
        if (this.curRetryIndex < this.retryMaxCount) {
            this.curRetryIndex++;
            if (isLive()) {
                setLive(this.lastChannelId, this.lastPublishUrl);
            }
            if (!CommonUtils.isActiveNetwork(AppUtils.getApplicationContext())) {
                this.mHandler.removeMessages(4);
                this.mHandler.sendEmptyMessageDelayed(4, this.retryInterval);
                behaviorLog(8, System.currentTimeMillis() - this.logStartTime, LogItem.MM_C21_TP_RE, LogItem.MM_C21_ST_RE_TO, this.traceId, "retry record");
                return;
            }
            int ret = startRecord();
            if (ret == 0) {
                stopRetryRecord();
                notifyBufferingEnd();
            } else {
                this.mHandler.removeMessages(4);
                this.mHandler.sendEmptyMessageDelayed(4, this.retryInterval);
            }
            behaviorLog(ret, System.currentTimeMillis() - this.logStartTime, LogItem.MM_C21_TP_RE, LogItem.MM_C21_ST_RE_TO, this.traceId, "retry record");
            return;
        }
        notifyEncodeError(8);
        stopRetryRecord();
    }

    /* access modifiers changed from: protected */
    public void stopAndRetryLiveRecord() {
        this.logRet = 0;
        if (isLive()) {
            Logger.D(TAG, "stopAndRetryLiveRecord start", new Object[0]);
            this.isPause.set(false);
            this.curRetryIndex = 0;
            this.mHandler.removeMessages(4);
            this.mHandler.removeMessages(3);
            this.isRetrying.set(false);
            this.mHandler.sendEmptyMessage(3);
            return;
        }
        notifyOperationError();
    }

    /* access modifiers changed from: protected */
    public void pauseLiveRecord() {
        if (isLive()) {
            Logger.D(TAG, "pauseLiveRecord start", new Object[0]);
            this.isPause.set(true);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessage(5);
            return;
        }
        notifyOperationError();
    }

    /* access modifiers changed from: private */
    public synchronized void handleStopAndRetryRecord() {
        Logger.D(TAG, "handleStopAndRetryRecord start isRetrying=" + this.isRetrying.get(), new Object[0]);
        if (this.isRetrying.compareAndSet(false, true)) {
            notifyBufferingStart();
            stopRecord(false);
            setup();
            Logger.D(TAG, "handleStopAndRetryRecord end isRetrying=" + this.isRetrying.get(), new Object[0]);
            this.mHandler.sendEmptyMessageDelayed(4, this.retryInterval);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void handlePauseLiveRecord() {
        stopRecord(false);
    }

    /* access modifiers changed from: protected */
    public void setRetryParam(long interval, int count) {
        if (interval >= 0 && count >= 0) {
            this.retryInterval = interval;
            this.retryMaxCount = count;
        }
    }

    /* access modifiers changed from: protected */
    public long getCurTime() {
        return this.videoCurTimeStamp / 1000;
    }

    public boolean isLive() {
        return getRecordType() == 1;
    }

    /* access modifiers changed from: protected */
    public void setLive(String channelId, String publishUrl) {
        Logger.D(TAG, "setLive cid=" + channelId + ";uri=" + publishUrl, new Object[0]);
        if (!TextUtils.isEmpty(this.lastPublishUrl) && !this.lastPublishUrl.equals(publishUrl)) {
            setAudioCurTimeStamp(0);
            setVideoCurTimeStamp(0);
            Logger.D(TAG, "setLive setAudioCurTimeStamp and setVideoCurTimeStamp with 0", new Object[0]);
        }
        this.lastChannelId = channelId;
        this.lastPublishUrl = publishUrl;
    }

    /* access modifiers changed from: protected */
    public boolean isSupportLiveBeauty() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean checkAudioPermission(int mode) {
        boolean granted = true;
        if (mMode == 1 && mode == 0) {
            acquirePermissions(mode);
        }
        if (PermissionHelper.checkVideoPermission(mode) || this.activityOrFragment == null || this.activityOrFragment.get() == null) {
            Logger.D(TAG, "Device is below android 6.0 or permission is granted before.", new Object[0]);
        } else {
            this.mHasReqPermissionTime = true;
            PermissionHelper.requireVideoPermission(this.activityOrFragment.get(), mode, 4);
            granted = false;
        }
        mMode = mode;
        return granted;
    }

    /* access modifiers changed from: protected */
    public void initCamera(boolean relayout) {
        int[] r;
        long ts = System.currentTimeMillis();
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_CAMERA_INIT_BEGIN, System.nanoTime());
        if (!Environment.getExternalStorageState().equals("mounted")) {
            notifySdcardError();
        }
        CameraInfo cameraInfo = new CameraInfo();
        CameraInfo curCameraInfo = null;
        int numcameras = Camera.getNumberOfCameras();
        int i = 0;
        while (true) {
            if (i >= numcameras) {
                break;
            }
            Camera.getCameraInfo(i, cameraInfo);
            this.mCameraId = i;
            if (cameraInfo.facing == this.mCameraFacing || numcameras == 1) {
                try {
                    this.mCamera = Camera.open(i);
                } catch (RuntimeException e) {
                    Logger.D(TAG, "open camera error exp=" + e.getMessage(), new Object[0]);
                    this.mCamera = Camera.open(i);
                }
                if (this.mCamera == null) {
                    Logger.D(TAG, "open camera error", new Object[0]);
                    throw new RuntimeException("open camera error");
                } else {
                    this.mCameraFacing = cameraInfo.facing;
                    curCameraInfo = cameraInfo;
                }
            } else {
                i++;
            }
        }
        if (this.mCamera == null) {
            Logger.D(TAG, "open camera error", new Object[0]);
            throw new RuntimeException("open camera error");
        }
        Parameters parameters = this.mCamera.getParameters();
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes != null && supportedFlashModes.contains(CameraParams.FLASH_MODE_OFF) && !hasUserSet()) {
            parameters.setFlashMode(CameraParams.FLASH_MODE_OFF);
        } else if (hasUserSet()) {
            parameters.setFlashMode(this.cameraParams.getFlashMode());
        }
        if (parameters.getMaxNumFocusAreas() > 0) {
            this.mFocusAreaSupported = true;
        }
        if (parameters.getMaxNumMeteringAreas() > 0) {
            this.mMeteringAreaSupported = true;
        }
        if (mMode == 0) {
            chooseVideoPreviewSize(parameters);
        } else {
            choosePhotoPreviewSize(parameters);
        }
        parameters.setPreviewFormat(17);
        List focusMode = parameters.getSupportedFocusModes();
        if (this.autoFocusEnable) {
            if (focusMode.contains("auto") && getRecordType() == 3 && mMode == 0) {
                parameters.setFocusMode("auto");
            } else if (focusMode.contains("continuous-video") && mMode == 0) {
                parameters.setFocusMode("continuous-video");
            } else if (focusMode.contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
            }
        }
        List fpsRange = parameters.getSupportedPreviewFpsRange();
        AnonymousClass3 r0 = new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return b[0] - a[0];
            }
        };
        Collections.sort(fpsRange, r0);
        int minfps = 0;
        int maxfps = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= fpsRange.size()) {
                break;
            }
            r = fpsRange.get(i2);
            minfps = r[0];
            maxfps = r[1];
            Logger.D(TAG, "camera fpsRange minfps=" + minfps + ";maxfps=" + maxfps, new Object[0]);
            if (r[1] > 30000 || (i2 < fpsRange.size() - 1 && r[0] >= r[1] && fpsRange.get(i2 + 1)[1] >= 20000)) {
                i2++;
            }
        }
        if (r[1] < 18000 && i2 - 1 >= 0) {
            minfps = fpsRange.get(i2 - 1)[0];
            maxfps = fpsRange.get(i2 - 1)[1];
            Logger.D(TAG, "camera fpsRange minfps=" + minfps + ";maxfps=" + maxfps, new Object[0]);
        }
        parameters.setPreviewFpsRange(minfps, maxfps);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putString(VideoBenchmark.KEY_CAMERA_FPS, String.valueOf(minfps) + "," + String.valueOf(maxfps));
        Logger.D(TAG, "camera current scene mode : " + parameters.getSceneMode() + "; min fps:" + minfps + ", max fps" + maxfps, new Object[0]);
        if (VERSION.SDK_INT >= 15 && ConfigManager.getInstance().getVideoStabilizationSwitch(parameters.isVideoStabilizationSupported())) {
            parameters.setVideoStabilization(true);
        }
        this.mRotation = setCameraDisplayOrientation((Activity) this.mContext, cameraInfo.facing, curCameraInfo);
        parameters.setRotation(this.mRotation);
        this.mCamera.setParameters(parameters);
        if (relayout && (getParent() instanceof SightCameraViewImpl)) {
            AnonymousClass4 r02 = new Runnable() {
                public void run() {
                    CameraView.this.reLayout();
                }
            };
            post(r02);
        }
        this.mIsFocusing = false;
        Logger.D(TAG, "Camera Time init camera took " + (System.currentTimeMillis() - ts) + "ms;mRotation=" + this.mRotation, new Object[0]);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong(VideoBenchmark.KEY_CAMERA_INIT_END, System.nanoTime());
        notifyCameraOpen();
    }

    public void setAutoFocusMode(boolean mode) {
        this.autoFocusEnable = mode;
        if (this.mCamera != null) {
            Parameters parameters = this.mCamera.getParameters();
            List focusMode = parameters.getSupportedFocusModes();
            if (!this.autoFocusEnable) {
                parameters.setFocusMode("fixed");
            } else if (focusMode.contains("auto") && getRecordType() == 3 && mMode == 0) {
                parameters.setFocusMode("auto");
            } else if (focusMode.contains("continuous-video") && mMode == 0) {
                parameters.setFocusMode("continuous-video");
            } else if (focusMode.contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
            }
            this.mCamera.setParameters(parameters);
        }
    }

    public void startPreview() {
        this.mCamera.startPreview();
        this.mIsOpened = true;
    }

    private int setCameraDisplayOrientation(Activity activity, int cameraId, CameraInfo curInfo) {
        int result;
        int def;
        CameraInfo info = curInfo;
        if (info == null) {
            try {
                Camera.getCameraInfo(cameraId, info);
            } catch (Exception e) {
                info = curInfo;
            }
        }
        updateScreenRotation(activity);
        Logger.D(TAG, "getDefaultDisplay().getRotation(): " + this.mScreenRotation, new Object[0]);
        int degrees = 0;
        switch (this.mScreenRotation) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = 270;
                break;
        }
        if (info.facing == 1) {
            result = (360 - ((info.orientation + degrees) % 360)) % 360;
            def = 270;
        } else {
            result = ((info.orientation - degrees) + 360) % 360;
            def = 90;
        }
        this.mCamera.setDisplayOrientation(result);
        this.mDisplayOrientation = result;
        Logger.D(TAG, "############ mCamera.setDisplayOrientation:" + result + ", cur camera orientation=" + info.orientation, new Object[0]);
        return (info.orientation > 270 || info.orientation <= 0) ? def : info.orientation;
    }

    /* access modifiers changed from: protected */
    public void releaseCamera() {
        this.mIsOpened = false;
        Logger.D(TAG, "releaseCamera -- enter initCameraError=" + this.initCameraError, new Object[0]);
        try {
            synchronized (this.releaseLock) {
                if (this.mCamera != null && !this.initCameraError) {
                    this.mCamera.release();
                    this.mCamera = null;
                    Logger.D(TAG, "releaseCamera -- done", new Object[0]);
                }
            }
            HardwareHelper.get().releaseVideo();
        } catch (Exception e) {
            try {
                Logger.E((String) TAG, (String) "releaseCamera error", (Throwable) e, new Object[0]);
            } finally {
                HardwareHelper.get().releaseVideo();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void reLayout() {
        LayoutParams layoutParams = new FrameLayout.LayoutParams(this.mScreenWidth + 1, this.mScreenHeight);
        if (mMode != 1) {
            updateScreenSize();
            Logger.D(TAG, "reLayout display w:" + this.mScreenWidth + ",h:" + this.mScreenHeight, new Object[0]);
            int width = (this.mScreenHeight * this.mPreviewSize.height) / this.mPreviewSize.width;
            if (VideoUtils.isActivityLandscape((Activity) getContext())) {
                Logger.D(TAG, "isActivityLandscape", new Object[0]);
                width = (this.mScreenHeight * this.mPreviewSize.width) / this.mPreviewSize.height;
            }
            if (width < this.mScreenWidth && this.mScreenWidth - width <= ((int) (((float) this.mScreenWidth) * this.preRate))) {
                width = this.mScreenWidth;
            }
            layoutParams.width = width + 1;
            layoutParams.height = this.mScreenHeight;
        }
        setLayoutParams(layoutParams);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void focusOnTouch(MotionEvent event) {
        if (this.mCamera != null && this.mIsOpened && !this.mIsFocusing) {
            try {
                this.mCamera.cancelAutoFocus();
            } catch (Exception e) {
                Logger.W(TAG, "focusOnTouch cancelAutoFocus error, e: " + e, new Object[0]);
            }
            Rect focusRect = calculateTapArea(event.getX(), event.getY(), 1.0f);
            Rect meteringRect = calculateTapArea(event.getX(), event.getY(), 1.5f);
            Parameters parameters = null;
            try {
                parameters = this.mCamera.getParameters();
            } catch (Exception e2) {
                Logger.E((String) TAG, "focusOnTouch getParameters exp" + e2.getMessage(), (Throwable) e2, new Object[0]);
            }
            if (parameters != null) {
                List focusMode = parameters.getSupportedFocusModes();
                if (focusMode != null && focusMode.contains("auto")) {
                    parameters.setFocusMode("auto");
                    if (this.mFocusAreaSupported) {
                        ArrayList list = new ArrayList(1);
                        list.add(new Area(focusRect, 1000));
                        parameters.setFocusAreas(list);
                    }
                    if (this.mMeteringAreaSupported) {
                        ArrayList list2 = new ArrayList(1);
                        list2.add(new Area(meteringRect, 1000));
                        parameters.setMeteringAreas(list2);
                    }
                    try {
                        this.mCamera.setParameters(parameters);
                        this.mCamera.autoFocus(this);
                        this.mIsFocusing = true;
                        this.mHandler.sendEmptyMessageDelayed(2, 5000);
                    } catch (Exception e3) {
                        Logger.E((String) TAG, "setParameters exp:" + e3.getMessage(), (Throwable) e3, new Object[0]);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        Logger.E(TAG, this + "\tonAttachedToWindow 1", new Object[0]);
        acquirePermissions(mMode);
        super.onAttachedToWindow();
        Logger.E(TAG, this + "\tonAttachedToWindow 2", new Object[0]);
        if (!HardwareHelper.get().requestVideo()) {
            APVideoRecordRsp rsp = new APVideoRecordRsp();
            rsp.mRspCode = 11;
            notifyError(rsp);
            return;
        }
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        setScaleX(1.00001f);
        setScaleY(1.00001f);
        OrientationDetector.getInstance(getContext()).register();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.D(TAG, "onDetachedFromWindow", new Object[0]);
        OrientationDetector.getInstance(getContext()).unregister();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        Logger.D(TAG, "onFinishInflate", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void destroyHardwareResources() {
    }

    private Rect calculateTapArea(float x, float y, float coefficient) {
        int areaSize = Float.valueOf(((float) ((int) ((72.0f * getContext().getResources().getDisplayMetrics().density) + 0.5f))) * coefficient).intValue();
        int centerX = (int) (((x / ((float) getWidth())) * 2000.0f) - 1000.0f);
        int centerY = (int) (((y / ((float) getHeight())) * 2000.0f) - 1000.0f);
        return new Rect(clamp(centerX - (areaSize / 2), -1000, 1000), clamp(centerY - (areaSize / 2), -1000, 1000), clamp((areaSize / 2) + centerX, -1000, 1000), clamp((areaSize / 2) + centerY, -1000, 1000));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public void onAutoFocus(boolean success, Camera camera) {
        Logger.D(TAG, "onAutoFocus result: " + success, new Object[0]);
        this.mHandler.removeMessages(2);
        this.mIsFocusing = false;
    }

    /* access modifiers changed from: protected */
    public void zoom() {
        if (this.mCamera != null && this.mIsOpened) {
            if (this.cameraParams == null || this.cameraParams.bZoomEnable) {
                Parameters parameters = null;
                try {
                    parameters = this.mCamera.getParameters();
                } catch (Exception e) {
                    Logger.E((String) TAG, "zoom getParameters exp:" + e.getMessage(), (Throwable) e, new Object[0]);
                }
                if (parameters != null && parameters.isZoomSupported()) {
                    int max = parameters.getMaxZoom();
                    int curr = parameters.getZoom();
                    Logger.D(TAG, "curr: " + curr, new Object[0]);
                    if (curr == 0) {
                        parameters.setZoom(max / 2);
                    } else {
                        parameters.setZoom(0);
                    }
                    try {
                        this.mCamera.setParameters(parameters);
                    } catch (Exception e2) {
                        Logger.E((String) TAG, "zoom setParameters exp:" + e2.getMessage(), (Throwable) e2, new Object[0]);
                    }
                }
            }
        }
    }

    private void chooseVideoPreviewSize(Parameters parameters) {
        Size previewSize;
        List previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, new Comparator<Size>() {
            public int compare(Size a, Size b) {
                return a.height - b.height;
            }
        });
        updateScreenSize();
        int w = this.mScreenWidth;
        int h = this.mScreenHeight;
        if (VideoUtils.isActivityLandscape((Activity) getContext())) {
            Logger.D(TAG, "isActivityLandscape", new Object[0]);
            w = this.mScreenHeight;
            h = this.mScreenWidth;
        }
        int i = 0;
        while (true) {
            if (i >= previewSizes.size()) {
                break;
            }
            previewSize = previewSizes.get(i);
            Logger.D(TAG, "video preview size  width:" + previewSize.width + " height:" + previewSize.height + ";w=" + w + ";h=" + h, new Object[0]);
            if (previewSize.height < SessionConfig.VIDEO_HARDENCODE_PRE_W || previewSize.width < SessionConfig.VIDEO_HARDENCODE_PRE_H || previewSize.width * w > ((int) (((float) (previewSize.height * h)) * this.sizeRate)) || (VideoUtils.isActivityLandscape((Activity) getContext()) && previewSize.width * 2 <= previewSize.height * 3)) {
                i++;
            }
        }
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        this.mPreviewSize = previewSize;
        if (this.mPreviewSize == null || !VideoDeviceWrapper.getVideoConfig().isHardEncode() || MediaConst.isBeauty(getCameraType())) {
            chooseMinVideoPreviewSize(parameters);
        }
    }

    private void chooseMinVideoPreviewSize(Parameters parameters) {
        Size previewSize;
        List previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, new Comparator<Size>() {
            public int compare(Size a, Size b) {
                return a.width - b.width;
            }
        });
        updateScreenSize();
        int w = this.mScreenWidth;
        int h = this.mScreenHeight;
        if (VideoUtils.isActivityLandscape((Activity) getContext())) {
            Logger.D(TAG, "isActivityLandscape", new Object[0]);
            w = this.mScreenHeight;
            h = this.mScreenWidth;
        }
        int i = 0;
        while (true) {
            if (i >= previewSizes.size()) {
                break;
            }
            previewSize = previewSizes.get(i);
            Logger.D(TAG, "mini camera preview size  width:" + previewSize.width + " height:" + previewSize.height + ";w=" + w + ";h=" + h, new Object[0]);
            if (previewSize.height < 544 || previewSize.width < 960 || previewSize.width * w > ((int) (((float) (previewSize.height * h)) * this.sizeRate)) || (VideoUtils.isActivityLandscape((Activity) getContext()) && previewSize.width * 2 <= previewSize.height * 3)) {
                i++;
            }
        }
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        this.mPreviewSize = previewSize;
        if (this.mPreviewSize == null) {
            throw new RuntimeException("broken camera!");
        }
    }

    private void choosePhotoPreviewSize(Parameters parameters) {
        updateScreenSize();
        Point size = VideoUtils.findBestPreviewSizeValue(parameters, new Point(this.mScreenWidth, this.mScreenHeight));
        parameters.setPreviewSize(size.x, size.y);
        this.mPreviewSize = parameters.getPreviewSize();
    }

    /* access modifiers changed from: protected */
    public int getCameraId() {
        return this.mCameraId;
    }

    public int getCameraFacing() {
        return this.mCameraFacing;
    }

    public int getDisplayOrientation() {
        return this.mDisplayOrientation;
    }

    /* access modifiers changed from: protected */
    public boolean needCheckSDCardSpace() {
        return !isLive();
    }

    public void notifyOpenCameraError() {
        this.initCameraError = true;
        APVideoRecordRsp rsp = new APVideoRecordRsp();
        rsp.mRspCode = 100;
        notifyError(rsp);
        Logger.E((String) TAG, (String) "take it easy, only use to fix camera on dev", (Throwable) new IllegalStateException("notifyOpenCameraError"), new Object[0]);
    }

    public void notifyEncodeError(int code) {
        APVideoRecordRsp rsp = new APVideoRecordRsp();
        rsp.mRspCode = code;
        notifyError(rsp);
        Logger.E((String) TAG, (String) "take it easy, only use to fix camera on dev", (Throwable) new IllegalStateException("notifyEncodeError"), new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void notifySdcardError() {
        APVideoRecordRsp rsp = new APVideoRecordRsp();
        rsp.mRspCode = 200;
        notifyError(rsp);
        Logger.E((String) TAG, (String) "take it easy, only use to fix sdcard on dev", (Throwable) new IllegalStateException("notifySdcardError"), new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void notifyCameraOpen() {
        notifyInfo(APVideoRecordRsp.CODE_INFO_CAMERA_OPEN, null);
    }

    /* access modifiers changed from: protected */
    public void notifyMicOpen() {
        notifyInfo(APVideoRecordRsp.CODE_INFO_MIC_OPEN, null);
    }

    private void notifyBufferingStart() {
        new Bundle().putInt("retryIndex", this.curRetryIndex);
        notifyInfo(7001, null);
    }

    private void notifyBufferingEnd() {
        notifyInfo(7002, null);
    }

    /* access modifiers changed from: protected */
    public void notifyInfo(int code, Bundle bundle) {
        if (this.mListener != null) {
            this.mListener.onInfo(code, bundle);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyError(APVideoRecordRsp rsp) {
        Logger.E(TAG, "notifyError, rsp: " + rsp, new Object[0]);
        if (this.mListener != null) {
            rsp.recordPhase = this.mRecordPhase;
            this.mListener.onError(rsp);
            UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_COLLECT_VR, String.valueOf(rsp.mRspCode));
        }
    }

    /* access modifiers changed from: protected */
    public void notifyOperationError() {
        APVideoRecordRsp rsp = new APVideoRecordRsp();
        rsp.mRspCode = 10;
        rsp.recordPhase = this.mRecordPhase;
        if (this.mListener != null) {
            this.mListener.onError(rsp);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyPrepared() {
        if (needCheckSDCardSpace()) {
            long enoughDiskSpace = enoughDiskSpace();
            if (!FileUtils.isSDcardAvailableSpace(enoughDiskSpace)) {
                Logger.E(TAG, "before notifyPrepared, disk space is less than " + enoughDiskSpace + ", current: " + com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.getSdAvailableSize(), new Object[0]);
                APVideoRecordRsp rsp = new APVideoRecordRsp();
                rsp.mRspCode = 300;
                notifyError(rsp);
                return;
            }
        }
        Logger.D(TAG, "notifyPrepared", new Object[0]);
        if (this.mListener != null) {
            APVideoRecordRsp rsp2 = new APVideoRecordRsp();
            rsp2.mCamera = this.mCamera;
            rsp2.mPreviewH = this.mPreviewSize.height;
            rsp2.mPreviewW = this.mPreviewSize.width;
            rsp2.mDisplayOrientation = this.mDisplayOrientation;
            this.mListener.onPrepared(rsp2);
        }
        UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_COLLECT_VR, "0");
    }

    /* access modifiers changed from: protected */
    public void notifyMicError() {
        Logger.D(TAG, "notifyMicError permission denied", new Object[0]);
        Logger.E((String) TAG, (String) "take it easy, only use to fix mic on dev", (Throwable) new IllegalStateException("notifyMicError"), new Object[0]);
        APVideoRecordRsp rsp = new APVideoRecordRsp();
        rsp.mRspCode = 2;
        notifyError(rsp);
    }

    private void notifyCount(LiveData data) {
        if (data == null) {
            Logger.D(TAG, "notifyCount data = null", new Object[0]);
        } else {
            Logger.D(TAG, "notifyCount data=" + data.toString() + ";speed=" + data.getUploadRealTimeSpeed() + "KB/S", new Object[0]);
        }
    }

    private void notifyWeakNet(boolean bWeak) {
        if (this.bLastNotify != bWeak) {
            Logger.D(TAG, "notifyWeakNet bWeak=" + bWeak + ";bLastNotify=" + this.bLastNotify + ";loseCount=" + this.loseCount, new Object[0]);
            this.bLastNotify = bWeak;
            if (bWeak && this.pingSwitch == 1) {
                getPingHandler().removeMessages(1);
                getPingHandler().sendEmptyMessageDelayed(1, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                if (!TextUtils.isEmpty(this.pingUrl)) {
                    getPingHandler().removeMessages(2);
                    getPingHandler().sendEmptyMessageDelayed(2, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                }
            }
            notifyInfo(bWeak ? APVideoRecordRsp.CODE_INFO_CONGESTION_START : APVideoRecordRsp.CODE_INFO_CONGESTION_END, null);
        }
    }

    private Handler getPingHandler() {
        if (this.mPingHandler == null) {
            this.mThread = new HandlerThread("live_ping");
            this.mThread.start();
            this.mPingHandler = new PingHandler(this, this.mThread.getLooper());
        }
        return this.mPingHandler;
    }

    /* access modifiers changed from: private */
    public void handPingIn() {
        try {
            NetDetect.nativePing(new URI(this.lastPublishUrl).getHost());
        } catch (Exception e) {
            Logger.E((String) TAG, "handPingIn exp=" + e.getMessage(), (Throwable) e, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void handPingOut() {
        try {
            NetDetect.nativePing(new URI(this.pingUrl).getHost());
        } catch (Exception e) {
            Logger.E((String) TAG, "handPingOut exp=" + e.getMessage(), (Throwable) e, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void pingQuit() {
        if (isLive() && this.pingSwitch == 1) {
            getPingHandler().sendEmptyMessage(3);
        }
    }

    private long enoughDiskSpace() {
        return ConfigManager.getInstance().diskConf().videoNeedSpace;
    }

    public void setVideoCurTimeStamp(long ts) {
        this.videoCurTimeStamp = ts;
    }

    private void setAudioCurTimeStamp(long ts) {
        this.audioCurTimeStamp = ts;
    }

    public void onPutError(int code) {
        Logger.D(TAG, "onPutError code=" + code, new Object[0]);
        notifyEncodeError(VideoUtils.convertMuxToRspCode(code));
    }

    public void onGetCount(LiveCounter data, long time, int ret, boolean bIframe) {
        netWorkAnalyse(time, ret, bIframe);
        if (this.countSwith != 0) {
            long interval = Math.abs(time - this.mLastCountTime);
            if (interval > ((long) this.countInterval)) {
                this.mLastCountTime = time;
                LiveData counter = LiveStatistic.convertToLiveStatsItem(data, this.mLastCounter, interval);
                this.mLastCounter = data;
                notifyCount(counter);
            }
        }
    }

    private void netWorkAnalyse(long time, int ret, boolean bIframe) {
        if (this.weakNetSwith != 0) {
            if (ret == 2) {
                long interval = Math.abs(time - this.mLastFullTime);
                if (this.bNeedNotify && (interval > ((long) this.mFullInterval) || bIframe)) {
                    this.bNeedNotify = false;
                    notifyWeakNet(true);
                    this.mLastFullTime = time;
                }
                this.loseCount++;
                this.mLastSucTime = time;
            } else if (ret == 0) {
                this.mLastFullTime = time;
                this.bNeedNotify = true;
                if (Math.abs(time - this.mLastSucTime) > ((long) this.mSucInterval)) {
                    this.mLastSucTime = time;
                    notifyWeakNet(false);
                }
            }
        }
    }

    public void onAudioTimeUpdate(long ts) {
        this.audioCurTimeStamp = ts;
    }

    public void onVideoTimeUpdate(long ts) {
        this.videoCurTimeStamp = ts;
    }

    public void onAudioStart() {
        this.isAudioStart = true;
    }

    public boolean isAudioStart() {
        return this.isAudioStart;
    }

    private void acquirePermissions(int mode) {
        if (mode == 1 || ((this instanceof SightCameraGLESView) && getRecordType() == 0)) {
            Logger.D(TAG, "acquire audio permission but mode being photo or using mic hardware encoding, just skip.", new Object[0]);
        } else if (!sRequirePermissions.get()) {
            synchronized (sRequirePermissions) {
                if (!sRequirePermissions.get()) {
                    PermissionHelper.acquirePermissions(1);
                    sRequirePermissions.set(true);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void behaviorLog(int ret, long time, String type, String state, long traceid, String err) {
        if (isLive() && !TextUtils.isEmpty(this.lastChannelId)) {
            int count = 0;
            if (LogItem.MM_C21_ST_RE_E.equalsIgnoreCase(state)) {
                count = this.loseCount;
            }
            UCLogUtil.UC_MM_C21(ret, time, this.lastChannelId, type, state, this.mBeautyValue, traceid, err, count);
        }
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    private void updateScreenSize() {
        if (isLive()) {
            if (this.mScreenHeight <= 0 || this.mScreenWidth <= 0) {
                this.mScreenHeight = VideoUtils.getScreenSize(getContext()).y;
                this.mScreenWidth = VideoUtils.getScreenSize(getContext()).x;
            } else {
                return;
            }
        } else if (this.mScreenHeight <= 0 || this.mScreenWidth <= 0 || (hasWindowFocus() && isShown())) {
            this.mScreenHeight = VideoUtils.getScreenSize(getContext()).y;
            this.mScreenWidth = VideoUtils.getScreenSize(getContext()).x;
        }
        Logger.D(TAG, "screen property updateScreenSize.mScreenWidth=" + this.mScreenWidth + ",mScreenHeight=" + this.mScreenHeight, new Object[0]);
    }

    @SuppressLint({"WrongConstant"})
    private void updateScreenRotation(Activity activity) {
        if (isLive()) {
            if (this.mScreenRotation <= 0) {
                this.mScreenRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            } else {
                return;
            }
        } else if (this.mScreenRotation < 0 || (hasWindowFocus() && isShown())) {
            this.mScreenRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        }
        Logger.D(TAG, "screen property updateScreenRotation.mScreenRotation=" + this.mScreenRotation, new Object[0]);
    }

    public int getCameraType() {
        return 1;
    }

    public void setBeautyValue(int value) {
    }

    public void setPreprocessor(FramePreprocessor preprocessor) {
    }

    public void enableRtBeautify(boolean enable) {
    }

    public void enableEventbus(boolean enable) {
    }

    public boolean isOMX() {
        return false;
    }
}
