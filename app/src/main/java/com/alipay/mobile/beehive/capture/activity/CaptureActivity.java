package com.alipay.mobile.beehive.capture.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaMaterialService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFalconAbility;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APDetectionResult;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FaceDetectionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnScrollListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureMPListener;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.basic.AUView;
import com.alipay.mobile.antui.utils.AUStatusBarUtil;
import com.alipay.mobile.antui.utils.ToolUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.modle.Effect;
import com.alipay.mobile.beehive.capture.modle.Filter;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.impl.CaptureServiceImpl;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.beehive.capture.utils.CameraUtils;
import com.alipay.mobile.beehive.capture.utils.CameraUtils.CameraHelperListener;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.PhotoBehavior;
import com.alipay.mobile.beehive.capture.utils.PreferenceManager;
import com.alipay.mobile.beehive.capture.utils.ServiceFactory;
import com.alipay.mobile.beehive.capture.views.CaptureBtn;
import com.alipay.mobile.beehive.capture.views.CaptureBtn.Type;
import com.alipay.mobile.beehive.capture.views.CustomRecordImageView;
import com.alipay.mobile.beehive.capture.views.CustomRecordImageView.RecordListener;
import com.alipay.mobile.beehive.capture.views.EffectSelectView;
import com.alipay.mobile.beehive.capture.views.EffectSelectView.EffectSelectListener;
import com.alipay.mobile.beehive.capture.views.FilterSelectView;
import com.alipay.mobile.beehive.capture.views.FilterSelectView.FilterSelectListener;
import com.alipay.mobile.beehive.capture.views.GuideView;
import com.alipay.mobile.beehive.capture.views.GuideView.GuideType;
import com.alipay.mobile.beehive.capture.views.TitleBar;
import com.alipay.mobile.beehive.capture.views.TouchInterceptFrameLayout;
import com.alipay.mobile.beehive.capture.views.TouchInterceptFrameLayout.TapListener;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.util.DensityUtils;
import com.alipay.mobile.beehive.util.GPSUtils;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.widget.ui.BalloonLayout;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaptureActivity extends BeehiveBaseActivity implements OnClickListener, OnScrollListener {
    /* access modifiers changed from: private */
    public static int CAMERA_FACING_BACK = 0;
    /* access modifiers changed from: private */
    public static int CAMERA_FACING_FRONT = 1;
    private static final int CODE_NO_FACE = 5003;
    public static final int DEFAULT_BEAUTY_LEVEL = 50;
    /* access modifiers changed from: private */
    public static int FLUSH_MODE_CLOSE = 0;
    /* access modifiers changed from: private */
    public static int FLUSH_MODE_OPEN = 1;
    public static final int MODE_CHANGING = 2;
    public static final int MODE_PHOTO = 1;
    public static final int MODE_VIDEO = 0;
    private static final int MSG_DISMISS_COMMON_TIPS = 2;
    private static final int MSG_DISMISS_TIPS = 1;
    private static final int MSG_DISMISS_TOP_TIPS = 3;
    private static final String SP_KEY_FIRST_SELECT_EFFECT = "select_effect";
    public static final String TAG = "CaptureFragment";
    private int beautyModePercent = -1;
    private AUImageView btnEffectSelect;
    private AUImageView btnEffectSelect2;
    protected AUImageView btnFilter;
    private AUImageView btnLatestRecord;
    protected CaptureBtn btnRecord;
    /* access modifiers changed from: private */
    public Camera camera;
    protected TouchInterceptFrameLayout cameraContainer;
    /* access modifiers changed from: private */
    public int cameraFacing;
    private CameraHelperListener cameraHelperListener = new CameraHelperListener() {
        public final void onCameraFacingChanged(Camera camera, int facing, int mode) {
            CaptureActivity.this.camera = camera;
            CaptureActivity.this.cameraFacing = facing;
            CaptureActivity.this.currentMode = mode;
            if (CaptureActivity.this.currentMode == 0) {
                CaptureActivity.this.initPreViewVideoMode();
            } else {
                CaptureActivity.this.initPreViewPhotoMode();
            }
            CaptureActivity.this.flushMode = CaptureActivity.FLUSH_MODE_CLOSE;
            CaptureActivity.this.toggleFlash();
            if (CaptureActivity.this.cameraFacing == CaptureActivity.CAMERA_FACING_BACK) {
                CaptureActivity.this.mTitle.ivFlash.setVisibility(0);
                CaptureActivity.this.mTitle.ivCamera.setContentDescription(CaptureActivity.this.getString(R.string.switch_to_front_camera));
                return;
            }
            CaptureActivity.this.mTitle.ivFlash.setVisibility(8);
            if (!CaptureActivity.this.isBeautyModeOn && CaptureActivity.this.mEnableBeauty) {
                CaptureActivity.this.mTitle.ivBeauty.callOnClick();
            }
            CaptureActivity.this.mTitle.ivCamera.setContentDescription(CaptureActivity.this.getString(R.string.switch_to_back_camera));
        }

        public final void onVideoModeReached(Camera camera) {
            Logger.i(CaptureActivity.TAG, "切换视频模式成功-----------");
            CaptureActivity.this.camera = camera;
            CaptureActivity.this.initPreViewVideoMode();
        }

        public final void onCaptureModeReached(Camera camera) {
            Logger.i(CaptureActivity.TAG, "切换相机模式成功-----------");
            CaptureActivity.this.camera = camera;
            CaptureActivity.this.initPreViewPhotoMode();
        }
    };
    protected SightCameraView cameraView;
    OnClickListener captureClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (CaptureActivity.this.currentMode != 2) {
                CaptureActivity.this.currentMode = 2;
                Logger.i(CaptureActivity.TAG, "点击拍摄 时间 " + System.currentTimeMillis());
                CaptureActivity.this.cameraView.takePicture(CaptureActivity.this.mTakePictureListener, Looper.getMainLooper(), CaptureActivity.this.mTakePictureOption);
            }
        }
    };
    protected AUTextView captureIndicator;
    protected AURelativeLayout controlPanel;
    OnTouchListener coverBlockTouch = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };
    protected AUView coverView;
    /* access modifiers changed from: private */
    public int currentMode = 0;
    private EffectSelectView effectSelectView;
    private String extraExif;
    private FilterSelectView filterSelectView;
    /* access modifiers changed from: private */
    public int flushMode;
    private GuideView guideView;
    private ViewStub guideViewStub;
    /* access modifiers changed from: private */
    public Handler handler;
    private int indicatorMode = 0;
    /* access modifiers changed from: private */
    public boolean isBeautyModeOn = false;
    private boolean isCaptureSessionNotified;
    private boolean isRecordStopByTimeLimit;
    /* access modifiers changed from: private */
    public boolean isRecording;
    /* access modifiers changed from: private */
    public boolean isStopped = false;
    /* access modifiers changed from: private */
    public ImageView ivNoFace;
    protected AUImageView ivPrepare;
    private String mCaptureBusinessId;
    private int mCaptureMode;
    private int mCapturePictureSize;
    /* access modifiers changed from: private */
    public boolean mEnableBeauty;
    private boolean mEnableFilter;
    private boolean mEnableMultiTimeRecord;
    private boolean mEnableShowLatestRecordEntry;
    private boolean mEnableSwitchCamera;
    private boolean mEnableWaterMark;
    private String mEntryIconPath;
    FaceDetectionListener mFaceDetectionListener = new FaceDetectionListener() {
        public final void onFaceDetection(final APDetectionResult apDetectionResult) {
            CaptureActivity.this.runOnUiThread(new Runnable() {
                public final void run() {
                    CaptureActivity.this.ivNoFace.setVisibility(apDetectionResult.code == CaptureActivity.CODE_NO_FACE ? 0 : 8);
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public boolean mFinishAfterCallback;
    private MultimediaImageService mImageService;
    /* access modifiers changed from: private */
    public int mInitCameraFacing;
    private int mInitType;
    private int mMaxRecordDuration;
    private Bundle mParams;
    private boolean mSaveVideoToAlbum;
    /* access modifiers changed from: private */
    public TakePictureMPListener mTakePictureListener = new TakePictureMPListener() {
        public final void onPictureTaken(byte[] bytes, Camera camera) {
        }

        public final void onPictureTakenError(int i, Camera camera) {
            Logger.error((String) CaptureActivity.TAG, "onPictureTakenError errorCode = " + i);
            CaptureActivity.this.toast(CaptureActivity.this.getString(R.string.tips_take_pic_error), 0);
            CaptureActivity.this.notifyFail();
            CaptureActivity.this.finish();
            CaptureActivity.this.reportTakePictureError(i);
        }

        public final void onPictureProcessStart() {
            CaptureActivity.this.showProgressDialog("");
        }

        public final void onPictureProcessFinish(String s, int i, int i1, int i2) {
            CaptureActivity.this.onPictureProcessFinish(s, i, i1, i2, null);
        }

        public final void onPictureProcessFinish(String s, int width, int height, int rotation, Bundle extra) {
            CaptureActivity.this.onPictureProcessFinish(s, width, height, rotation, extra);
        }

        public final void onPictureProcessError(int i, byte[] bytes) {
            Logger.error((String) CaptureActivity.TAG, "onPictureProcessError errorCode = " + i);
            CaptureActivity.this.dismissProgressDialog();
            CaptureActivity.this.toast(CaptureActivity.this.getString(R.string.tips_take_pic_error), 0);
            CaptureActivity.this.notifyFail();
            CaptureActivity.this.finish();
        }
    };
    /* access modifiers changed from: private */
    public APTakePictureOption mTakePictureOption = new APTakePictureOption();
    private Size mTargetSize;
    protected TitleBar mTitle;
    private String mToastOnRecordDone;
    private RecordListener mVideoRecordListener = new RecordListener() {
        public final void onTouchOutside(boolean isOutside) {
            if (isOutside) {
                CaptureActivity.this.showCommonTips(CaptureActivity.this.getString(R.string.tips_leave_to_cancel), R.color.colorWhite, R.drawable.video_tips_waring_bg);
            } else {
                CaptureActivity.this.showCommonTips(CaptureActivity.this.getString(R.string.tips_up_to_cancel), R.color.colorWhite, R.drawable.video_tips_common_bg);
            }
        }

        public final void onRecordFinish(boolean isTimeDurationLimitReached) {
            Logger.debug(CustomRecordImageView.TAG, "Record finish called.");
            CaptureActivity.this.stopRecord(isTimeDurationLimitReached);
        }

        public final void onRecordCanceled() {
            Logger.debug(CustomRecordImageView.TAG, "Record cancel called,perform cancel record.");
            CaptureActivity.this.isRecording = false;
            CameraUtils.cancelRecord(CaptureActivity.this.cameraView);
        }

        public final void onRecordStart() {
            Logger.debug(CustomRecordImageView.TAG, "Record start called.");
            CaptureActivity.this.startRecord();
            CaptureActivity.this.showCommonTips(CaptureActivity.this.getString(R.string.tips_up_to_cancel), R.color.colorWhite, R.drawable.video_tips_common_bg);
            CaptureActivity.this.btnRecord.setInterceptUserRecordAction(true);
        }

        public final void onRecordTimeUnSatisfied() {
            Logger.debug(CustomRecordImageView.TAG, "Record time too short,cancel record.");
            CaptureActivity.this.showBottomTips(CaptureActivity.this.getString(R.string.tips_record_too_short), R.color.textColorRed);
            CaptureActivity.this.isRecording = false;
            CameraUtils.cancelRecord(CaptureActivity.this.cameraView);
        }
    };
    private boolean needPreview;
    private OnClickListener onFacingClickListener = new OnClickListener() {
        public final void onClick(View v) {
            if (!CaptureActivity.this.isRecording && CaptureActivity.this.currentMode != 2) {
                CaptureActivity.this.switchCameraFacing(CaptureActivity.this.currentMode);
            }
        }
    };
    private OnClickListener onFlushClickListener = new OnClickListener() {
        public final void onClick(View v) {
            String string;
            if (CaptureActivity.this.currentMode != 2) {
                CaptureActivity.this.flushMode = Math.abs(CaptureActivity.this.flushMode - 1);
                try {
                    CaptureActivity.this.toggleFlash();
                    CaptureActivity captureActivity = CaptureActivity.this;
                    if (CaptureActivity.this.flushMode == CaptureActivity.FLUSH_MODE_OPEN) {
                        string = CaptureActivity.this.getString(R.string.flashlight_opend);
                    } else {
                        string = CaptureActivity.this.getString(R.string.flashlight_closed);
                    }
                    captureActivity.showFlashTips(string);
                } catch (Exception e) {
                    CaptureActivity.this.onFlushError();
                }
            }
        }
    };
    OnRecordListener onRecordListener = new OnRecordListener() {
        public final void onStart() {
        }

        public final void onError(APVideoRecordRsp rsp) {
            String memo = "相机错误";
            switch (rsp.mRspCode) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    memo = CaptureActivity.this.getString(R.string.tips_mic_error);
                    break;
                case 100:
                    memo = CaptureActivity.this.getString(R.string.tips_camera_error);
                    break;
                case 200:
                    memo = CaptureActivity.this.getString(R.string.tips_sdcard_error);
                    break;
                case 300:
                    memo = CaptureActivity.this.getString(R.string.tips_sdcard_not_enough);
                    break;
            }
            CaptureActivity.this.runOnUiThread(new Runnable() {
                public final void run() {
                    Logger.debug(CaptureActivity.TAG, "Camera error, call record button \"performCancelRecord\",to reset!");
                    CaptureActivity.this.btnRecord.performCancelRecord();
                }
            });
            CaptureActivity.this.alert(null, memo, CaptureActivity.this.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialog, int which) {
                    CaptureActivity.this.notifyFail();
                    CaptureActivity.this.finish();
                }
            }, null, null, Boolean.valueOf(false), Boolean.valueOf(false));
            CaptureActivity.this.reportTakeVideoError(rsp, memo);
        }

        public final void onFinish(APVideoRecordRsp rsp) {
            CaptureActivity.this.enableRecordBtn("Camera onFinish called.");
            CaptureActivity.this.onRecordFinish(rsp);
        }

        public final void onCancel() {
            CaptureActivity.this.enableRecordBtn("Camera onCancel called.");
            if (!CaptureActivity.this.isStopped) {
                CaptureActivity.this.resetState();
            }
        }

        public final void onInfo(int i, Bundle bundle) {
        }

        public final void onPrepared(APVideoRecordRsp rsp) {
            CaptureActivity.this.preparedCount = CaptureActivity.this.preparedCount + 1;
            if (CaptureActivity.this.preparedCount > 1) {
                Logger.debug(CaptureActivity.TAG, "Prepared count = " + CaptureActivity.this.preparedCount + ",ignore it!");
                return;
            }
            CaptureActivity.this.setupPreviewType(rsp);
            CaptureActivity.this.handler.post(new Runnable() {
                public final void run() {
                    CaptureActivity.this.ivPrepare.setVisibility(8);
                    CaptureActivity.this.showStartTips();
                    if (!CaptureActivity.this.isBeautyModeOn && CaptureActivity.this.mEnableBeauty && CaptureActivity.this.mInitCameraFacing == CaptureActivity.CAMERA_FACING_FRONT) {
                        CaptureActivity.this.mTitle.ivBeauty.callOnClick();
                    }
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public int preparedCount;
    private Animation progressAnimation;
    private View recordProgressView;
    protected AUTextView tipsText;
    protected AUTextView tipsText2;
    protected AUTextView topTips;
    protected AUTextView videoIndicator;
    private MultimediaVideoService videoService;

    static class a extends Handler {
        private WeakReference<CaptureActivity> a;

        public a(CaptureActivity context) {
            this.a = new WeakReference<>(context);
        }

        public final void handleMessage(Message msg) {
            CaptureActivity context = (CaptureActivity) this.a.get();
            if (context == null) {
                return;
            }
            if (msg.what == 1) {
                context.tipsText.setText(null);
                context.tipsText.setVisibility(8);
            } else if (msg.what == 2) {
                context.tipsText2.setText(null);
                context.tipsText2.setVisibility(8);
            } else if (msg.what == 3) {
                context.topTips.setText(null);
            }
        }
    }

    private void parseConfig() {
        this.mTakePictureOption.saveToPrivateDir = this.mParams.getBoolean(CaptureParam.SAVE_FILE_TO_PRIVATE_DIRECTORY, false);
        this.mTakePictureOption.setQuality(this.mParams.getInt(CaptureParam.KEY_CAPTURE_QUALITY, 100));
        this.mMaxRecordDuration = this.mParams.getInt(CaptureParam.KEY_MAX_DURATION, 6000);
        this.mEnableSwitchCamera = this.mParams.getBoolean(CaptureParam.ENABLE_SWITCH_CAMERA, true);
        this.mEnableMultiTimeRecord = this.mParams.getBoolean(CaptureParam.ENABLE_MULTI_TIME_RECORD, false);
        this.mEnableShowLatestRecordEntry = this.mParams.getBoolean(CaptureParam.ENABLE_SHOW_LATEST_RECORD_ENTRY, false);
        this.mEntryIconPath = this.mParams.getString(CaptureParam.LATEST_RECORD_ENTRY_ICON_PATH);
        this.mToastOnRecordDone = this.mParams.getString(CaptureParam.TOAST_WHEN_RECORD_DONE_ONE_TIME);
        this.mSaveVideoToAlbum = this.mParams.getBoolean(CaptureParam.SAVE_VIDEO_TO_ALBUM, true);
        this.mCaptureMode = this.mParams.getInt(CaptureParam.CAPTURE_MODE, 3);
        this.mInitType = this.mParams.getInt(CaptureParam.INIT_TYPE, 0);
        this.mInitCameraFacing = this.mParams.getInt(CaptureParam.INIT_CAMERA_FACING, 0);
        if (this.mInitCameraFacing == 1 && !hasFrontCamera()) {
            Logger.debug(TAG, "Front camera is not exist,force set to back camera");
            this.mInitCameraFacing = 0;
        }
        this.cameraFacing = this.mInitCameraFacing;
        this.mCapturePictureSize = getIntent().getIntExtra(CaptureParam.CAPTURE_PICTURE_SIZE, 0);
        String packageName = getPackageName();
        Logger.debug(TAG, "parseConfig, packageName=" + packageName);
        if (TextUtils.isEmpty(packageName) || !packageName.equals("com.autonavi.minimap")) {
            this.mEnableWaterMark = this.mParams.getBoolean(CaptureParam.ENABLE_SET_WATER_MARK, true);
            this.mEnableFilter = this.mParams.getBoolean(CaptureParam.ENABLE_SET_FILTER, true);
            this.mEnableBeauty = this.mParams.getBoolean(CaptureParam.ENABLE_SET_BEAUTY, true);
        } else {
            this.mEnableWaterMark = this.mParams.getBoolean(CaptureParam.ENABLE_SET_WATER_MARK, false);
            this.mEnableFilter = this.mParams.getBoolean(CaptureParam.ENABLE_SET_FILTER, false);
            this.mEnableBeauty = this.mParams.getBoolean(CaptureParam.ENABLE_SET_BEAUTY, false);
        }
        checkWhiteList();
        this.isBeautyModeOn = this.mParams.getBoolean(Constants.EXTRA_BEAUTY_STATE, false);
        this.beautyModePercent = this.mParams.getInt(Constants.EXTRA_BEAUTY_VALUE, -1);
        this.mFinishAfterCallback = this.mParams.getBoolean(CaptureParam.FINISH_AFTER_CALLBACK, true);
        this.extraExif = this.mParams.getString(CaptureParam.KEY_EXTRA_EXIF);
        Object[] objArr = new Object[6];
        objArr[0] = Integer.valueOf(this.mCaptureMode);
        objArr[1] = this.mInitType == 0 ? "video" : "photo";
        objArr[2] = Boolean.valueOf(this.mEnableWaterMark);
        objArr[3] = Boolean.valueOf(this.mFinishAfterCallback);
        objArr[4] = this.mInitCameraFacing == 0 ? H5Param.DEFAULT_LONG_BACK_BEHAVIOR : "front";
        objArr[5] = Boolean.valueOf(this.mEnableMultiTimeRecord);
        Logger.debug(TAG, String.format("mCaptureMode = %d,initType = %s,mEnableWaterMark = %s,finishAfterCallback = %s,initCameraFacing = %s,EnableMultiTimeRecord = %s", objArr));
    }

    private boolean hasFrontCamera() {
        int count = Camera.getNumberOfCameras();
        for (int i = 0; i < count; i++) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 1) {
                return true;
            }
        }
        return false;
    }

    private void checkWhiteList() {
        String info = "checkWhiteList: ";
        APFalconAbility ability = ((MultimediaMaterialService) MicroServiceUtil.getMicroService(MultimediaMaterialService.class)).getAbility();
        if (ability == null) {
            info = info + "No config found !";
        } else if (!ability.deviceSupport) {
            info = info + "Device is Not in support List,Disable filter waterMark and beauty!";
            this.mEnableWaterMark = false;
            this.mEnableFilter = false;
            this.mEnableBeauty = false;
        } else {
            this.mEnableWaterMark &= ability.falconSwitch;
            this.mEnableFilter &= true;
            this.mEnableBeauty &= true;
        }
        Logger.debug(TAG, info + " Enable waterMark =" + this.mEnableWaterMark + ",Enable filter = " + this.mEnableFilter + ",Enable beauty = " + this.mEnableBeauty);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        concaveScreenCompactFullScreen();
        setContentView(R.layout.activity_capture);
        int wh = DensityUtils.dip2px(this, 125.0f);
        this.mTargetSize = ((MultimediaImageService) ServiceFactory.getAliService(MultimediaImageService.class)).getDjangoNearestImageSize(new Size(wh, wh));
        this.mParams = getIntent().getExtras();
        if (this.mParams == null) {
            this.mParams = new Bundle();
            Logger.debug(TAG, "Activity extras is null!");
        }
        this.mCaptureBusinessId = this.mParams.getString("businessId");
        parseConfig();
        initViews();
        concaveScreenCompactAdjustTitleBarTopMargin();
    }

    private void concaveScreenCompactFullScreen() {
        if (ToolUtils.isConcaveScreen(this)) {
            Logger.debug(TAG, "concaveScreenCompact:full screen.");
            requestWindowFeature(1);
            getWindow().setFlags(1024, 1024);
        }
    }

    private void concaveScreenCompactAdjustTitleBarTopMargin() {
        if (ToolUtils.isConcaveScreen(this)) {
            Logger.debug(TAG, "concaveScreenCompact:adjust top place holder height.");
            View vPlaceHolder = findViewById(R.id.v_full_screen_top_place_holder);
            vPlaceHolder.setVisibility(0);
            LayoutParams lp = (LayoutParams) vPlaceHolder.getLayoutParams();
            lp.height = AUStatusBarUtil.getStatusBarHeight(this);
            vPlaceHolder.setLayoutParams(lp);
        }
    }

    private void initViews() {
        int i;
        int i2;
        int i3 = 4;
        int i4 = 8;
        this.coverView = (AUView) findViewById(R.id.coverView);
        this.cameraContainer = (TouchInterceptFrameLayout) findViewById(R.id.cameraContainer);
        this.topTips = (AUTextView) findViewById(R.id.top_tips);
        this.captureIndicator = (AUTextView) findViewById(R.id.indicator_capture);
        this.captureIndicator.setVisibility(this.mCaptureMode == 1 ? 4 : 0);
        this.ivPrepare = (AUImageView) findViewById(R.id.ivCameraPrepare);
        this.controlPanel = (AURelativeLayout) findViewById(R.id.control_panel);
        this.tipsText = (AUTextView) findViewById(R.id.tips);
        this.videoIndicator = (AUTextView) findViewById(R.id.indicator_video);
        AUTextView aUTextView = this.videoIndicator;
        if (this.mCaptureMode != 2) {
            i3 = 0;
        }
        aUTextView.setVisibility(i3);
        this.btnFilter = (AUImageView) findViewById(R.id.btnFilter);
        this.btnEffectSelect = (AUImageView) findViewById(R.id.btnEffectSelect);
        AUImageView aUImageView = this.btnEffectSelect;
        if (this.mEnableWaterMark) {
            i = 0;
        } else {
            i = 8;
        }
        aUImageView.setVisibility(i);
        this.btnEffectSelect2 = (AUImageView) findViewById(R.id.btnEffectSelect2);
        this.btnEffectSelect2.setOnClickListener(this);
        this.btnLatestRecord = (AUImageView) findViewById(R.id.bt_latest_record_entry);
        this.btnLatestRecord.setOnClickListener(this);
        updateShowEntry();
        this.btnFilter.setOnClickListener(this);
        AUImageView aUImageView2 = this.btnFilter;
        if (this.mEnableFilter) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        aUImageView2.setVisibility(i2);
        this.btnEffectSelect.setOnClickListener(this);
        this.tipsText2 = (AUTextView) findViewById(R.id.tips_common);
        this.mTitle = (TitleBar) findViewById(R.id.title);
        ImageView imageView = this.mTitle.ivFlash;
        if (this.mInitCameraFacing != 1) {
            i4 = 0;
        }
        imageView.setVisibility(i4);
        this.btnRecord = (CaptureBtn) findViewById(R.id.btnRecord);
        this.effectSelectView = (EffectSelectView) findViewById(R.id.effectSelectView);
        this.filterSelectView = (FilterSelectView) findViewById(R.id.filterSelectView);
        this.recordProgressView = findViewById(R.id.recordProgress);
        this.btnRecord.takePictureBtn.setOnClickListener(this.captureClickListener);
        this.btnRecord.setOnVideoRecordListener(this.mVideoRecordListener);
        this.mTitle.ivFlash.setOnClickListener(this.onFlushClickListener);
        this.btnRecord.setViewType(initAsPhotoType() ? Type.PICTURE : Type.VIDEO);
        this.mTitle.setTitleText(initAsPhotoType() ? getString(R.string.capture) : getString(R.string.video));
        this.guideViewStub = (ViewStub) findViewById(R.id.guideViewStub);
        this.ivNoFace = (ImageView) findViewById(R.id.ivNoFace);
        setUpZOrder();
        initProgressAnimation();
        afterViews();
    }

    private void updateShowEntry() {
        int i = 0;
        if (!this.mEnableShowLatestRecordEntry || TextUtils.isEmpty(this.mEntryIconPath)) {
            AUImageView aUImageView = this.btnEffectSelect;
            if (!this.mEnableWaterMark) {
                i = 8;
            }
            aUImageView.setVisibility(i);
            this.btnEffectSelect2.setVisibility(8);
            this.btnLatestRecord.setVisibility(8);
            return;
        }
        this.btnEffectSelect2.setVisibility(this.mEnableWaterMark ? 0 : 8);
        this.btnLatestRecord.setVisibility(0);
        if (this.btnEffectSelect2.getVisibility() == 0) {
            this.btnEffectSelect.setVisibility(8);
        }
        ((MultimediaVideoService) ServiceFactory.getAliService(MultimediaVideoService.class)).loadAlbumVideo(this.mEntryIconPath, this.btnLatestRecord, Integer.valueOf(this.mTargetSize.getWidth()), Integer.valueOf(this.mTargetSize.getHeight()), null, null, this.mCaptureBusinessId);
    }

    private boolean initAsPhotoType() {
        if (this.mCaptureMode != 2) {
            return this.mCaptureMode == 3 && this.mInitType == 1;
        }
        return true;
    }

    private void setUpZOrder() {
        this.coverView.bringToFront();
        this.ivPrepare.bringToFront();
        this.ivPrepare.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.guideViewStub.bringToFront();
    }

    private void afterViews() {
        this.handler = new a(this);
        this.handler.postDelayed(new Runnable() {
            public final void run() {
                AudioUtils.pauseSystemAudio();
            }
        }, 1500);
        this.videoService = (MultimediaVideoService) ServiceFactory.getAliService(MultimediaVideoService.class);
        this.mImageService = (MultimediaImageService) ServiceFactory.getAliService(MultimediaImageService.class);
        if (this.videoService == null || this.mImageService == null) {
            notifyFail();
            finish();
            return;
        }
        this.mTitle.ivBack.setOnClickListener(this);
        if (this.mEnableBeauty) {
            this.mTitle.ivBeauty.setVisibility(0);
            this.mTitle.ivBeauty.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    if (CaptureActivity.this.currentMode != 2) {
                        CaptureActivity.this.toggleBeautyMode("click");
                    }
                }
            });
        } else {
            this.mTitle.ivBeauty.setVisibility(8);
        }
        setupEffectAndFilter();
    }

    private void setupEffectAndFilter() {
        this.effectSelectView.setEffectSelectedListener(new EffectSelectListener() {
            public final void onEffectSelected(Effect effect) {
                Logger.debug(CaptureActivity.TAG, "Selected effect id = " + effect.effectId);
                CaptureActivity.this.cameraView.setSelectedMaterial(effect.effectId);
            }

            public final void onPanelGone() {
                CaptureActivity.this.toggleControlPanel(true);
            }
        });
        this.filterSelectView.setSelectListener(new FilterSelectListener() {
            public final void onFilterSelectd(Filter filter) {
                Logger.debug(CaptureActivity.TAG, "Selected filter id = " + filter.filterId);
                CaptureActivity.this.cameraView.setSelectedFilter(filter.filterId);
            }

            public final void onPanelGone() {
                CaptureActivity.this.toggleControlPanel(true);
            }
        });
        this.cameraContainer.setOnTapListener(new TapListener() {
            public final void onTap(View v) {
                CaptureActivity.this.hideEffectPanel();
            }
        });
    }

    /* access modifiers changed from: private */
    public void hideEffectPanel() {
        this.effectSelectView.setVisibility(8);
        this.filterSelectView.setVisibility(8);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.handler.post(new Runnable() {
                public final void run() {
                    CaptureActivity.this.mainThreadUnFriendlyJob();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void mainThreadUnFriendlyJob() {
        if (this.cameraView == null) {
            this.filterSelectView.setUpData();
            this.effectSelectView.setupData(this.mCaptureBusinessId);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            CameraParams cameraParams = new CameraParams();
            cameraParams.setDefaultCameraFront(this.mInitCameraFacing == 1);
            this.mTitle.ivCamera.setContentDescription(this.mInitCameraFacing == 1 ? getString(R.string.switch_to_back_camera) : getString(R.string.switch_to_front_camera));
            cameraParams.recordType = 2;
            cameraParams.mActivityRotation = 0;
            cameraParams.enableBeauty(true);
            if (this.mCaptureMode == 2) {
                cameraParams.mMode = 1;
            }
            addLocationInfo(cameraParams, this, this.extraExif, this.mCapturePictureSize);
            this.cameraView = this.videoService.createCameraView(this, this, cameraParams);
            this.cameraView.setLayoutParams(layoutParams);
            this.cameraView.setOnScrollListener(this);
            this.cameraView.setOnRecordListener(this.onRecordListener);
            setInitBeautyLevel();
            this.cameraContainer.addView(this.cameraView);
            this.cameraView.setFaceDetectionListener(this.mFaceDetectionListener);
        }
    }

    public static void addLocationInfo(CameraParams cameraParams, Context context, String extraExif2, int capturePictureSize) {
        LBSLocation latestLocation = getLatestPosition(context);
        if (latestLocation != null) {
            try {
                HashMap exif = new HashMap();
                String latitudeRef = GPSUtils.latitudeRef(latestLocation.getLatitude());
                String longitudeRef = GPSUtils.longitudeRef(latestLocation.getLongitude());
                String latitudeInDMS = GPSUtils.convert2DMS(latestLocation.getLatitude());
                String longitudeInDMS = GPSUtils.convert2DMS(latestLocation.getLongitude());
                exif.put("GPSLatitudeRef", latitudeRef);
                exif.put("GPSLongitudeRef", longitudeRef);
                exif.put("GPSLatitude", latitudeInDMS);
                exif.put("GPSLongitude", longitudeInDMS);
                if (!TextUtils.isEmpty(extraExif2)) {
                    exif.put(Constants.EXIF_KEY_EXTRA, extraExif2);
                }
                if (capturePictureSize > 0) {
                    exif.put("minPictureHeight", "1920");
                }
                cameraParams.exif = exif;
                Logger.debug(TAG, "Before covert: latitude = " + latestLocation.getLatitude() + ", longitude = " + latestLocation.getLongitude());
                Logger.debug(TAG, "After covert passed to multimedia: latitudeRef = " + latitudeRef + ", latitude =  " + latitudeInDMS + ", longitudeRef = " + longitudeRef + ", longitude = " + longitudeInDMS);
            } catch (Exception e) {
                Logger.warn(TAG, "Covert location into DMS Exception." + e.getMessage());
            }
        } else {
            Logger.debug(TAG, "Failed to get latest location.");
        }
    }

    private void setInitBeautyLevel() {
        if (PreferenceManager.getBeauty()) {
            this.mTitle.ivBeauty.callOnClick();
            Logger.debug(TAG, "Init set beauty On.");
        }
    }

    /* access modifiers changed from: private */
    public void showStartTips() {
        showCommonTips(getString(R.string.video_tips_double_click), R.color.colorWhite, R.drawable.video_tips_common_bg);
    }

    private GuideView getGuideView() {
        if (this.guideView == null) {
            this.guideView = (GuideView) this.guideViewStub.inflate();
        }
        return this.guideView;
    }

    /* access modifiers changed from: private */
    public static LBSLocation getLatestPosition(Context context) {
        return LBSLocationManagerProxy.getInstance().getLastKnownLocation(context);
    }

    /* access modifiers changed from: private */
    public void toggleFlash() {
        if (this.cameraFacing == CAMERA_FACING_FRONT) {
            this.flushMode = FLUSH_MODE_CLOSE;
        } else if (this.flushMode == FLUSH_MODE_CLOSE) {
            try {
                if (this.camera != null) {
                    Parameters parameters = this.camera.getParameters();
                    parameters.setFlashMode(CameraParams.FLASH_MODE_OFF);
                    this.camera.setParameters(parameters);
                }
                this.mTitle.ivFlash.setImageResource(R.drawable.ic_flash_off);
                this.mTitle.ivFlash.setContentDescription(getString(R.string.turn_on_flash));
            } catch (Exception e) {
                Logger.warn(TAG, "Shutdown flash error! camera.setParameters exception ," + e.getMessage());
            }
        } else {
            openFlash(this.camera, this.currentMode);
        }
    }

    private void openFlash(Camera camera2, int currentMode2) {
        if (camera2 == null) {
            onFlushError();
            return;
        }
        Parameters parameters = camera2.getParameters();
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null) {
            onFlushError();
            return;
        }
        if (currentMode2 == 0) {
            if (supportedFlashModes.contains("torch")) {
                parameters.setFlashMode("torch");
            } else if (supportedFlashModes.contains(CameraParams.FLASH_MODE_ON)) {
                parameters.setFlashMode(CameraParams.FLASH_MODE_ON);
            } else {
                onFlushError();
                return;
            }
        } else if (currentMode2 == 1) {
            if (!supportedFlashModes.contains(CameraParams.FLASH_MODE_ON) || !CameraUtils.isSupportCaptureFlush()) {
                onFlushError();
                return;
            }
            parameters.setFlashMode(CameraParams.FLASH_MODE_ON);
        }
        try {
            camera2.setParameters(parameters);
            this.mTitle.ivFlash.setImageResource(R.drawable.ic_flash_on);
            this.mTitle.ivFlash.setContentDescription(getString(R.string.turn_off_flash));
        } catch (Exception e) {
            Logger.warn(TAG, "camera.setParameters exception," + e.getMessage());
            onFlushError();
        }
    }

    /* access modifiers changed from: private */
    public void onFlushError() {
        toast(getString(R.string.tips_unable_to_flush), 0);
        this.flushMode = FLUSH_MODE_CLOSE;
        this.mTitle.ivFlash.setImageResource(R.drawable.ic_flash_off);
        this.mTitle.ivFlash.setContentDescription(getString(R.string.turn_on_flash));
    }

    /* access modifiers changed from: private */
    public void initPreViewVideoMode() {
        this.btnRecord.setViewType(Type.VIDEO);
        initPreviewModeTitle();
        this.currentMode = 0;
        if (!(this.effectSelectView.getVisibility() == 0 || this.filterSelectView.getVisibility() == 0)) {
            this.controlPanel.setVisibility(0);
            this.btnRecord.setVisibility(0);
        }
        this.mTitle.setTitleText(getString(R.string.video));
        this.flushMode = FLUSH_MODE_CLOSE;
        toggleFlash();
    }

    private void changeToVideoIndicator(boolean isInitChange) {
        long j = 0;
        if (this.indicatorMode != 0) {
            int delta = (this.videoIndicator.getWidth() + this.captureIndicator.getWidth()) / 2;
            ObjectAnimator.ofFloat(this.videoIndicator, "translationX", new float[]{(float) (-delta), 0.0f}).setDuration(isInitChange ? 0 : 500).start();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.captureIndicator, "translationX", new float[]{(float) (-delta), 0.0f});
            if (!isInitChange) {
                j = 500;
            }
            ofFloat.setDuration(j).start();
            this.captureIndicator.setTextColor(getResources().getColor(R.color.colorWhite));
            this.videoIndicator.setTextColor(getResources().getColor(R.color.captureBlue));
            this.indicatorMode = 0;
        }
    }

    /* access modifiers changed from: private */
    public void initPreViewPhotoMode() {
        initPreviewModeTitle();
        this.currentMode = 1;
        this.btnRecord.setViewType(Type.PICTURE);
        if (!(this.effectSelectView.getVisibility() == 0 || this.filterSelectView.getVisibility() == 0)) {
            this.controlPanel.setVisibility(0);
            this.btnRecord.setVisibility(0);
        }
        this.mTitle.setTitleText(getString(R.string.capture));
        this.flushMode = FLUSH_MODE_CLOSE;
        toggleFlash();
    }

    private void changeToCaptureIndicator(boolean isInitChange) {
        long j = 0;
        if (this.indicatorMode != 1) {
            int delta = (this.videoIndicator.getWidth() + this.captureIndicator.getWidth()) / 2;
            ObjectAnimator.ofFloat(this.videoIndicator, "translationX", new float[]{0.0f, (float) (-delta)}).setDuration(isInitChange ? 0 : 500).start();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.captureIndicator, "translationX", new float[]{0.0f, (float) (-delta)});
            if (!isInitChange) {
                j = 500;
            }
            ofFloat.setDuration(j).start();
            this.videoIndicator.setTextColor(getResources().getColor(R.color.colorWhite));
            this.captureIndicator.setTextColor(getResources().getColor(R.color.captureBlue));
            this.indicatorMode = 1;
        }
    }

    private void initPreviewModeTitle() {
        this.mTitle.llOptions.setVisibility(0);
        if (Camera.getNumberOfCameras() < 2 || !this.mEnableSwitchCamera) {
            this.mTitle.ivCamera.setVisibility(8);
            return;
        }
        this.mTitle.ivCamera.setVisibility(0);
        this.mTitle.ivCamera.setOnClickListener(this.onFacingClickListener);
    }

    /* access modifiers changed from: private */
    public void switchCameraFacing(int mode) {
        this.currentMode = 2;
        CameraUtils.switchCameraFacing(this.cameraView, this.cameraFacing, this.cameraHelperListener, mode);
    }

    /* access modifiers changed from: private */
    public void toggleControlPanel(boolean isVisiable) {
        if (isVisiable) {
            this.controlPanel.setVisibility(0);
            this.btnRecord.setVisibility(0);
            return;
        }
        this.controlPanel.setVisibility(8);
        this.btnRecord.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void stopRecord(boolean isTimeDurationLimitReached) {
        this.isRecordStopByTimeLimit = isTimeDurationLimitReached;
        Logger.debug(TAG, "Stop record by" + (this.isRecordStopByTimeLimit ? " time limit" : " User action."));
        this.isRecording = false;
        clearProgressAnimation();
        this.currentMode = 2;
        CameraUtils.stopRecord(this.cameraView);
    }

    /* access modifiers changed from: private */
    public void startRecord() {
        this.isRecording = true;
        showRecordingAnimation();
        this.cameraView.startRecord(Constants.CAPTURE_BUSINESS_ID);
        this.controlPanel.setVisibility(4);
    }

    private void showRecordingAnimation() {
        this.progressAnimation.reset();
        this.recordProgressView.setVisibility(0);
        this.recordProgressView.startAnimation(this.progressAnimation);
    }

    private void clearProgressAnimation() {
        this.recordProgressView.setVisibility(8);
        this.recordProgressView.clearAnimation();
    }

    /* access modifiers changed from: private */
    public void revertRecordViewState() {
        this.isRecording = false;
        clearProgressAnimation();
        this.controlPanel.setVisibility(0);
    }

    private void initProgressAnimation() {
        this.progressAnimation = AnimationUtils.loadAnimation(this.btnRecord.getContext(), R.anim.record_count_down_bar);
        this.progressAnimation.setDuration((long) this.mMaxRecordDuration);
        this.progressAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (CaptureActivity.this.isRecording) {
                    Logger.debug(CaptureActivity.TAG, "Record animation finish, call \"performFinishRecord\" func .");
                    CaptureActivity.this.btnRecord.performFinsihRecord();
                }
            }

            public final void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (this.mCaptureMode == 3 && Math.abs(e1.getX() - e2.getX()) >= Math.abs(e1.getY() - e2.getY())) {
            hideEffectPanel();
            switch (this.currentMode) {
                case 0:
                    if (e1.getX() - e2.getX() > 0.0f) {
                        changeToPhotoState(false);
                        break;
                    }
                    break;
                case 1:
                    if (e1.getX() - e2.getX() < 0.0f) {
                        changeToVideoState(false);
                        break;
                    }
                    break;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void changeToVideoState(boolean isInitChange) {
        this.currentMode = 2;
        Logger.i(TAG, "切换到视频模式------------------");
        changeToVideoIndicator(isInitChange);
        CameraUtils.openRecordMode(this.camera, this.cameraHelperListener);
    }

    /* access modifiers changed from: private */
    public void changeToPhotoState(boolean isInitChange) {
        Logger.i(TAG, "切换到相机模式------------------");
        this.currentMode = 2;
        changeToCaptureIndicator(isInitChange);
        CameraUtils.openCaptureMode(this.camera, this.cameraHelperListener);
    }

    /* access modifiers changed from: 0000 */
    public void publishImageAction(final MediaInfo mediaInfo) {
        runOnUiThread(new Runnable() {
            public final void run() {
                mediaInfo.location = null;
                CaptureActivity.this.gotoImagePreview(mediaInfo);
                if (CaptureActivity.this.mFinishAfterCallback) {
                    CaptureActivity.this.finish();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void gotoImagePreview(MediaInfo mediaInfo) {
        this.needPreview = true;
        Intent previewIntent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), RecordPreviewActivity.class);
        previewIntent.putExtras(this.mParams);
        previewIntent.putExtra("EXTRA_KEY_MEDIA_INFO", mediaInfo);
        previewIntent.putExtra(CaptureParam.INIT_CAMERA_FACING, this.cameraFacing);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), previewIntent);
        overridePendingTransition(17432576, 17432577);
    }

    public void onStop() {
        super.onStop();
        this.isStopped = true;
        AudioUtils.resumeSystemAudio();
        this.handler.removeCallbacksAndMessages(null);
        if (this.isRecording) {
            Logger.debug(TAG, "cameraView.cancelRecord");
            this.cameraView.cancelRecord(true);
        }
        if (!this.isCaptureSessionNotified) {
            notifyFail();
        } else {
            Logger.debug(TAG, "Notify called before,do nothing.");
        }
        Logger.debug(TAG, "onStop reached,call finish to release recourse.");
        needManuallyReleaseCamera(this.cameraView);
        finish();
    }

    public static void needManuallyReleaseCamera(SightCameraView cameraView2) {
        Logger.debug(TAG, "needManuallyReleaseCamera:###");
        if (VERSION.SDK_INT > 23) {
            try {
                long start = System.currentTimeMillis();
                cameraView2.releaseCamera();
                Logger.debug(TAG, "Manually release camera.");
                Logger.d(TAG, "cost time =" + (System.currentTimeMillis() - start));
            } catch (Throwable tr) {
                Logger.debug(TAG, "Manually release camera exception," + tr.getMessage());
            }
        }
    }

    public void onClick(View view) {
        if (view == this.mTitle.ivBack) {
            notifyFail();
            finish();
        } else if (view == this.btnFilter) {
            if (this.currentMode != 2) {
                toggleControlPanel(false);
                this.filterSelectView.setVisibility(0);
            }
        } else if (view == this.btnEffectSelect || view == this.btnEffectSelect2) {
            if (this.currentMode != 2) {
                toggleControlPanel(false);
                this.effectSelectView.setVisibility(0);
                changeToFrontCamera();
                shouldShowGuide();
            }
        } else if (view == this.btnLatestRecord) {
            CaptureServiceImpl.notifyLatestRecordEntryClicked(this, view);
            finish();
        }
    }

    private void changeToFrontCamera() {
        Logger.debug(TAG, "changeToFrontCamera:");
        if (this.cameraFacing == CAMERA_FACING_BACK) {
            Logger.debug(TAG, "Perform changing");
            switchCameraFacing(this.currentMode);
            return;
        }
        Logger.debug(TAG, "Now is front camera,no need to change.");
    }

    private void shouldShowGuide() {
        APSharedPreferences preferences = PreferenceManager.getPreference();
        if (!preferences.getBoolean(SP_KEY_FIRST_SELECT_EFFECT, false)) {
            getGuideView().setmGuideType(GuideType.EFFECT);
            preferences.putBoolean(SP_KEY_FIRST_SELECT_EFFECT, true);
            preferences.commit();
        }
    }

    /* access modifiers changed from: private */
    public void toggleBeautyMode(String changeReason) {
        boolean z = true;
        if ("click".equals(changeReason)) {
            this.isBeautyModeOn = !this.isBeautyModeOn;
            if (PreferenceManager.getBeauty() == this.isBeautyModeOn) {
                z = false;
            }
            if (z) {
                Logger.debug(TAG, "Show beauty toast.");
                toast(this.isBeautyModeOn ? getString(R.string.tips_beauty_on) : getString(R.string.tips_beauty_off), 0);
            }
            this.beautyModePercent = this.isBeautyModeOn ? 50 : -1;
            this.cameraView.setBeautyValue(this.beautyModePercent);
            this.mTitle.ivBeauty.setImageResource(this.isBeautyModeOn ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off);
            this.mTitle.ivBeauty.setContentDescription(this.isBeautyModeOn ? getString(R.string.turn_off_beauty) : getString(R.string.turn_on_beauty));
            PreferenceManager.recordBeauty(this.isBeautyModeOn);
        }
    }

    /* access modifiers changed from: private */
    public void enableRecordBtn(final String caller) {
        runOnUiThread(new Runnable() {
            public final void run() {
                Logger.debug(CaptureActivity.TAG, caller);
                CaptureActivity.this.btnRecord.setInterceptUserRecordAction(false);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupPreviewType(APVideoRecordRsp rsp) {
        this.camera = rsp.mCamera;
        this.captureIndicator.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (CaptureActivity.this.currentMode != 2 && CaptureActivity.this.currentMode == 0) {
                    CaptureActivity.this.changeToPhotoState(false);
                }
            }
        });
        this.videoIndicator.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (CaptureActivity.this.currentMode != 2 && CaptureActivity.this.currentMode == 1) {
                    CaptureActivity.this.changeToVideoState(false);
                }
            }
        });
        this.btnRecord.setVisibility(0);
        if (initAsPhotoType()) {
            changeToPhotoState(true);
        } else {
            changeToVideoState(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onRecordFinish(final APVideoRecordRsp rsp) {
        runOnUiThread(new Runnable() {
            public final void run() {
                boolean z = true;
                MediaInfo mediaInfo = new MediaInfo();
                mediaInfo.mediaType = 1;
                mediaInfo.path = rsp.mId;
                mediaInfo.heightPx = rsp.mHeight;
                mediaInfo.widthPx = rsp.mWidth;
                mediaInfo.location = CaptureActivity.getLatestPosition(CaptureActivity.this);
                mediaInfo.durationMs = rsp.duration;
                mediaInfo.rotation = rsp.mOrientation;
                mediaInfo.mediaFileSize = rsp.size;
                CaptureActivity captureActivity = CaptureActivity.this;
                if (CaptureActivity.this.continueRecord()) {
                    z = false;
                }
                captureActivity.notifyCaptureAction(false, mediaInfo, z);
                CaptureActivity.this.maySaveVideo2Album(mediaInfo.path);
                if (CaptureActivity.this.continueRecord()) {
                    CaptureActivity.this.resetToRecordState(mediaInfo);
                } else if (CaptureActivity.this.mFinishAfterCallback) {
                    CaptureActivity.this.finish();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetToRecordState(MediaInfo mediaInfo) {
        this.mEntryIconPath = mediaInfo.path;
        updateShowEntry();
        if (!TextUtils.isEmpty(this.mToastOnRecordDone)) {
            toast(this.mToastOnRecordDone, 0);
        }
        resetState();
        CameraUtils.reopenCameraIntoVideoMode(this.cameraView, this.cameraHelperListener);
        this.coverView.setOnTouchListener(null);
    }

    /* access modifiers changed from: private */
    public boolean continueRecord() {
        return this.mEnableMultiTimeRecord && this.isRecordStopByTimeLimit;
    }

    /* access modifiers changed from: private */
    public void maySaveVideo2Album(final String videoId) {
        if (this.mSaveVideoToAlbum) {
            Logger.debug(TAG, "Save short video.");
            BackgroundExecutor.execute((Runnable) new Runnable() {
                public final void run() {
                    try {
                        ((MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class)).saveVideo(videoId, null);
                    } catch (Exception e) {
                        Logger.warn(CaptureActivity.TAG, "VideoId = " + videoId + " Save video file exceptioon," + e.getMessage());
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void resetState() {
        runOnUiThread(new Runnable() {
            public final void run() {
                CaptureActivity.this.removeAllTips();
                CaptureActivity.this.revertRecordViewState();
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeAllTips() {
        this.handler.removeMessages(1);
        this.handler.removeMessages(2);
        this.tipsText2.setVisibility(8);
        this.handler.sendEmptyMessageDelayed(1, 1000);
    }

    /* access modifiers changed from: private */
    public void onPictureProcessFinish(String s, int width, int height, int rotation, Bundle extra) {
        dismissProgressDialog();
        if (s.startsWith(File.separator)) {
            s = "file://" + s;
        }
        MediaInfo mediaInfo = new MediaInfo(0, s, width, height, rotation, 0, getLatestPosition(this));
        if (extra != null) {
            mediaInfo.mediaFileSize = extra.getLong("picSize");
        }
        mediaInfo.isTakenByFrontCamera = this.cameraFacing == CAMERA_FACING_FRONT;
        publishImageAction(mediaInfo);
    }

    /* access modifiers changed from: private */
    public void reportTakePictureError(int i) {
        Map ext = new HashMap();
        ext.put(PhotoBehavior.PARAM_1, String.valueOf(i));
        PhotoBehavior.event("event", "capture", "UC-BC-160703-01", ext);
    }

    /* access modifiers changed from: private */
    public void reportTakeVideoError(APVideoRecordRsp rsp, String memo) {
        Map ext = new HashMap();
        ext.put(PhotoBehavior.PARAM_1, String.valueOf(rsp.mRspCode));
        ext.put(PhotoBehavior.PARAM_2, memo);
        PhotoBehavior.event("event", "capture", "UC-BC-160703-02", ext);
    }

    /* access modifiers changed from: private */
    public void showBottomTips(String text, int colorResource) {
        this.handler.removeMessages(1);
        this.tipsText.setVisibility(0);
        this.tipsText.setText(text);
        this.tipsText.setTextColor(getResources().getColor(colorResource));
        this.handler.sendEmptyMessageDelayed(1, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    /* access modifiers changed from: private */
    public void showCommonTips(String text, int colorResource, int bgResource) {
        this.handler.removeMessages(2);
        this.tipsText2.setBackgroundResource(bgResource);
        this.tipsText2.setVisibility(0);
        this.tipsText2.setText(text);
        this.tipsText2.setTextColor(getResources().getColor(colorResource));
        this.handler.sendEmptyMessageDelayed(2, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    /* access modifiers changed from: private */
    public void showFlashTips(String text) {
        this.handler.removeMessages(3);
        this.topTips.setVisibility(0);
        this.topTips.setText(text);
        this.handler.sendEmptyMessageDelayed(3, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    @SuppressLint({"Override"})
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.cameraView != null) {
            this.cameraView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* access modifiers changed from: private */
    public void notifyCaptureAction(boolean isCancel, MediaInfo mediaInfo, boolean releaseSession) {
        this.coverView.setOnTouchListener(this.coverBlockTouch);
        this.isCaptureSessionNotified = true;
        if (this.needPreview) {
            Logger.debug(TAG, "Need preview ,not notify at \"notifyCaptureAction\"");
        } else {
            CaptureServiceImpl.notifyAction(isCancel, mediaInfo, releaseSession);
        }
    }

    /* access modifiers changed from: private */
    public void notifyFail() {
        if (this.needPreview) {
            Logger.debug(TAG, "Need preview ,not notify at \"notifyFail\"");
            return;
        }
        this.isCaptureSessionNotified = true;
        CaptureServiceImpl.notifyAction(true, null, true);
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3481";
    }
}
