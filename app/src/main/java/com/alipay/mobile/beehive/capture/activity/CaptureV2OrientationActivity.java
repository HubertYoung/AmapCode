package com.alipay.mobile.beehive.capture.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureListener;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.edgedetect.EdgeDetector;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.modle.MediaInfo.Bounds;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.impl.CaptureServiceImpl;
import com.alipay.mobile.beehive.capture.utils.CameraUtils;
import com.alipay.mobile.beehive.capture.utils.CameraUtils.CameraHelperListener;
import com.alipay.mobile.beehive.capture.utils.CloudConfig;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.OtherUtils;
import com.alipay.mobile.beehive.capture.utils.PhotoBehavior;
import com.alipay.mobile.beehive.capture.utils.ServiceFactory;
import com.alipay.mobile.beehive.capture.views.CaptureV2MaskView;
import com.alipay.mobile.beehive.capture.views.CaptureV2MaskView.MaskOptions;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.util.GPSUtils;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CaptureV2OrientationActivity extends BeehiveBaseActivity {
    public static final String ACTION_UPDATE_CAPTURE_DISPLAY = "updateCaptureDisplay";
    /* access modifiers changed from: private */
    public static int CAMERA_FACING_BACK = 0;
    /* access modifiers changed from: private */
    public static int CAMERA_FACING_FRONT = 1;
    public static String KEY_ARGS_CAPTURE_CMD = "captureCMDArgs";
    public static final int MODE_CHANGING = 2;
    public static final int MODE_PHOTO = 1;
    private static final String TAG = "CaptureV2OrientationActivity";
    public static int maskMode;
    /* access modifiers changed from: private */
    public int cameraFacing;
    private CameraHelperListener cameraHelperListener = new CameraHelperListener() {
        public final void onCameraFacingChanged(Camera camera, int facing, int mode) {
            CaptureV2OrientationActivity.this.mCurrentCamera = camera;
            CaptureV2OrientationActivity.this.cameraFacing = facing;
            CaptureV2OrientationActivity.this.mCurrentMode = mode;
            if (CaptureV2OrientationActivity.this.cameraFacing == CaptureV2OrientationActivity.CAMERA_FACING_BACK) {
                CaptureV2OrientationActivity.this.mSwitchCameraImageView.setContentDescription(CaptureV2OrientationActivity.this.getString(R.string.switch_to_front_camera));
            } else {
                CaptureV2OrientationActivity.this.mSwitchCameraImageView.setContentDescription(CaptureV2OrientationActivity.this.getString(R.string.switch_to_back_camera));
            }
        }

        public final void onVideoModeReached(Camera camera) {
            CaptureV2OrientationActivity.this.mCurrentCamera = camera;
            CaptureV2OrientationActivity.this.mCurrentMode = 0;
        }

        public final void onCaptureModeReached(Camera camera) {
            CaptureV2OrientationActivity.this.mCurrentCamera = camera;
            CaptureV2OrientationActivity.this.mCurrentMode = 1;
        }
    };
    private CameraParams cameraParams;
    private boolean enableCropFrame;
    private String extraExif;
    protected float heightPercent;
    private boolean isCaptureSessionNotified;
    private boolean isNeedCrop;
    /* access modifiers changed from: private */
    public boolean isNeedPreview;
    private boolean isReleaseByStop = false;
    /* access modifiers changed from: private */
    public boolean isTakingPicture;
    private ImageView ivFlash;
    protected AUIconView mBackImageView;
    protected SightCameraView mCameraView;
    protected AUFrameLayout mCameraViewContainer;
    private String mCaptureBusinessId;
    protected AUButton mCaptureButton;
    private int mCapturePictureSize;
    private TextView mCenterTipsView;
    /* access modifiers changed from: private */
    public Camera mCurrentCamera;
    /* access modifiers changed from: private */
    public int mCurrentMode = 1;
    /* access modifiers changed from: private */
    public Drawable mDefaultRecordBtnBg;
    /* access modifiers changed from: private */
    public Drawable mDisabledRecordBtnBg;
    private BroadcastReceiver mDisplayUpdateReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            Bundle args = intent.getExtras();
            if (args != null) {
                CaptureV2OrientationActivity.this.handleCMD(args);
                if (args.containsKey(CaptureParam.UPDATE_UI_ENABLE_RECORD_BTN)) {
                    CaptureV2OrientationActivity.this.mCaptureButton.setEnabled(args.getBoolean(CaptureParam.UPDATE_UI_ENABLE_RECORD_BTN));
                    CaptureV2OrientationActivity.this.mCaptureButton.setBackgroundDrawable(CaptureV2OrientationActivity.this.mCaptureButton.isEnabled() ? CaptureV2OrientationActivity.this.mDefaultRecordBtnBg : CaptureV2OrientationActivity.this.mDisabledRecordBtnBg);
                }
                CaptureV2OrientationActivity.this.updateFlashBtnStatus(args);
                CaptureV2OrientationActivity.this.updateTips(args);
                CaptureV2OrientationActivity.this.handleQuitCMD(args);
                CaptureV2OrientationActivity.this.handleAIScanEffect(args);
                CaptureV2OrientationActivity.this.dispatchUpdateUI(args);
            }
        }
    };
    protected EdgeDetector mEdgeDetector;
    protected boolean mEdgeDetectorEnabled = false;
    /* access modifiers changed from: private */
    public Rect mInnerWindowRect;
    protected Rect mMaskRect;
    protected CaptureV2MaskView mMaskView;
    /* access modifiers changed from: private */
    public View mPureTipsContainer;
    private int mRecordType;
    protected ImageView mSwitchCameraImageView;
    private APTakePictureOption mTakePictureOption = new APTakePictureOption();
    private Handler mTipDismissHandler = new Handler(Looper.getMainLooper());
    private String mTips;
    protected TextView mTipsTextView;
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
                    memo = CaptureV2OrientationActivity.this.getString(R.string.tips_mic_error);
                    break;
                case 100:
                    memo = CaptureV2OrientationActivity.this.getString(R.string.tips_camera_error);
                    break;
                case 200:
                    memo = CaptureV2OrientationActivity.this.getString(R.string.tips_sdcard_error);
                    break;
                case 300:
                    memo = CaptureV2OrientationActivity.this.getString(R.string.tips_sdcard_not_enough);
                    break;
            }
            CaptureV2OrientationActivity.this.alert(null, memo, CaptureV2OrientationActivity.this.getString(R.string.confirm), new OnClickListener() {
                public final void onClick(DialogInterface dialog, int which) {
                    CaptureV2OrientationActivity.this.notifyFail();
                    CaptureV2OrientationActivity.this.finish();
                }
            }, null, null, Boolean.valueOf(false), Boolean.valueOf(false));
            CaptureV2OrientationActivity.this.reportTakeVideoError(rsp, memo);
        }

        public final void onFinish(APVideoRecordRsp rsp) {
        }

        public final void onCancel() {
        }

        public final void onInfo(int i, Bundle bundle) {
        }

        public final void onPrepared(APVideoRecordRsp rsp) {
            CaptureV2OrientationActivity.this.mCurrentCamera = rsp.mCamera;
            Bundle args = new Bundle();
            CaptureV2OrientationActivity.this.calPreviewFrameCutRegion(rsp, args);
            CaptureServiceImpl.notifyRecorderEvent(Constants.JS_METHOD_ON_RECORDER_PREPARED, args, false);
        }
    };
    private boolean showSwitch;
    protected float whRatio = -1.0f;
    protected float widthPercent;

    class a implements TakePictureListener {
        private boolean b;

        public a(boolean isCalledByCMD) {
            this.b = isCalledByCMD;
        }

        public final void onPictureTaken(byte[] bytes, Camera camera) {
        }

        public final void onPictureTakenError(int i, Camera camera) {
            Logger.error((String) CaptureV2OrientationActivity.TAG, "onPictureTakenError errorCode = " + i);
            CaptureV2OrientationActivity.this.toast(CaptureV2OrientationActivity.this.getString(R.string.tips_take_pic_error), 0);
            CaptureV2OrientationActivity.this.isTakingPicture = false;
            CaptureV2OrientationActivity.this.notifyFail();
            CaptureV2OrientationActivity.this.finish();
            CaptureV2OrientationActivity.this.reportTakePictureError(i);
        }

        public final void onPictureProcessStart() {
            CaptureV2OrientationActivity.this.showProgressDialog("");
        }

        public final void onPictureProcessFinish(String s, int width, int height, int rotation) {
            CaptureV2OrientationActivity.this.isTakingPicture = false;
            CaptureV2OrientationActivity.this.dismissProgressDialog();
            if (s.startsWith(File.separator)) {
                s = "file://" + s;
            }
            MediaInfo mediaInfo = new MediaInfo(0, s, width, height, rotation, 0, CaptureV2OrientationActivity.this.getLatestPosition(), CaptureV2OrientationActivity.this.mMaskRect == null ? null : new Bounds(CaptureV2OrientationActivity.this.mMaskRect), CaptureV2OrientationActivity.this.mInnerWindowRect == null ? null : new Bounds(CaptureV2OrientationActivity.this.mInnerWindowRect), CaptureV2OrientationActivity.this.calculateCutBounds(width, height));
            mediaInfo.isTakenByCMD = this.b;
            mediaInfo.isTakenByFrontCamera = CaptureV2OrientationActivity.this.cameraFacing == CaptureV2OrientationActivity.CAMERA_FACING_FRONT;
            CaptureV2OrientationActivity.this.publishImageAction(mediaInfo);
        }

        public final void onPictureProcessError(int i, byte[] bytes) {
            Logger.error((String) CaptureV2OrientationActivity.TAG, "onPictureProcessError errorCode = " + i);
            CaptureV2OrientationActivity.this.isTakingPicture = false;
            CaptureV2OrientationActivity.this.dismissProgressDialog();
            CaptureV2OrientationActivity.this.toast(CaptureV2OrientationActivity.this.getString(R.string.tips_take_pic_error), 0);
            CaptureV2OrientationActivity.this.notifyFail();
            CaptureV2OrientationActivity.this.finish();
        }
    }

    /* access modifiers changed from: protected */
    public abstract MaskOptions calcMaskOptions(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract int getActivityRotation();

    /* access modifiers changed from: protected */
    public abstract int getLayoutResId();

    /* access modifiers changed from: protected */
    public abstract Class<?> getPreviewClass();

    private void makeFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    /* access modifiers changed from: protected */
    public boolean isNeedFullScreen() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedFullScreen()) {
            makeFullScreen();
        }
        if (getIntent() == null) {
            finish();
            return;
        }
        setContentView(getLayoutResId());
        parseConfig();
        initViews();
        bindEvents();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mDisplayUpdateReceiver, new IntentFilter(ACTION_UPDATE_CAPTURE_DISPLAY));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mCameraView != null && this.isReleaseByStop) {
            this.mCurrentCamera = this.mCameraView.reopenCamera(this.mCurrentMode);
            this.isReleaseByStop = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.mCameraView != null) {
            closeFlash();
            this.mCameraView.releaseCamera();
            this.isReleaseByStop = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDisplayUpdateReceiver);
    }

    private void parseConfig() {
        boolean z = true;
        this.mTakePictureOption.saveToPrivateDir = getIntent().getBooleanExtra(CaptureParam.SAVE_FILE_TO_PRIVATE_DIRECTORY, false);
        this.mTakePictureOption.setQuality(getIntent().getIntExtra(CaptureParam.KEY_CAPTURE_QUALITY, 100));
        this.mCaptureBusinessId = getIntent().getStringExtra("businessId");
        this.mTips = getIntent().getStringExtra(CaptureParam.CAPTURE_TIP);
        this.showSwitch = getIntent().getBooleanExtra(CaptureParam.KEY_SHOW_SWITCH_CAMERA, true);
        this.widthPercent = getIntent().getFloatExtra(CaptureParam.CAPTURE_MASK_WIDTH_PERCENT, 0.0f);
        this.heightPercent = getIntent().getFloatExtra(CaptureParam.CAPTURE_MASK_HEIGHT_PERCENT, 0.0f);
        this.whRatio = getIntent().getFloatExtra(CaptureParam.CAPTURE_MASK_WIDTH_HEIGHT_RATIO, -1.0f);
        this.isNeedCrop = getIntent().getBooleanExtra(CaptureParam.CAPTURE_NEED_CROP, false);
        this.isNeedPreview = getIntent().getBooleanExtra(CaptureParam.NEED_PREVIEW, true);
        this.mRecordType = getIntent().getIntExtra(CaptureParam.KEY_RECORD_TYPE, 0);
        this.extraExif = getIntent().getStringExtra(CaptureParam.KEY_EXTRA_EXIF);
        Intent intent = getIntent();
        if (maskMode != 0) {
            z = false;
        }
        this.enableCropFrame = intent.getBooleanExtra(CaptureParam.ENABLE_CROP_FRAME, z);
        this.mEdgeDetectorEnabled = getIntent().getBooleanExtra(CaptureParam.ENABLE_AI_SCAN_EFFECT, false);
        if (CloudConfig.isDisableEdgeDetectorByCloudConfig()) {
            Logger.debug(TAG, "CloudConfig to disable edgeDetector!");
            this.mEdgeDetectorEnabled = false;
        }
        this.mCapturePictureSize = getIntent().getIntExtra(CaptureParam.CAPTURE_PICTURE_SIZE, 0);
        Logger.debug(TAG, "parseConfig, extra: " + getIntent().getExtras() + ", business: " + this.mCaptureBusinessId);
    }

    private void bindEvents() {
        this.mCaptureButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View v) {
                CaptureV2OrientationActivity.this.doTakePicture(false);
            }
        });
        this.mBackImageView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View v) {
                CaptureV2OrientationActivity.this.notifyFail();
                CaptureV2OrientationActivity.this.finish();
            }
        });
        if (this.mSwitchCameraImageView != null) {
            this.mSwitchCameraImageView.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View v) {
                    CaptureV2OrientationActivity.this.switchCamera();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void doTakePicture(boolean isCalledByCMD) {
        if (this.mCameraView == null) {
            Logger.w(TAG, "doTakePicture called when cameraView null!");
        } else if (!this.isTakingPicture) {
            this.isTakingPicture = true;
            this.mCameraView.takePicture(new a(isCalledByCMD), null, this.mTakePictureOption);
        }
    }

    /* access modifiers changed from: protected */
    public void initViews() {
        this.mCameraViewContainer = (AUFrameLayout) findViewById(R.id.cameraContainer);
        this.mCaptureButton = (AUButton) findViewById(R.id.btn_capture);
        this.mDefaultRecordBtnBg = this.mCaptureButton.getBackground();
        this.mDisabledRecordBtnBg = getResources().getDrawable(R.drawable.ic_record_disabled);
        this.mMaskView = (CaptureV2MaskView) findViewById(R.id.capture_mask);
        this.mBackImageView = (AUIconView) findViewById(R.id.ivBack);
        this.mSwitchCameraImageView = (ImageView) findViewById(R.id.ivChangeCamera);
        this.mTipsTextView = (TextView) findViewById(R.id.tv_tips);
        this.mPureTipsContainer = findViewById(R.id.pure_tip_panel);
        this.ivFlash = (ImageView) findViewById(R.id.iv_flash);
        updateViewsWithConfig();
    }

    private void updateViewsWithConfig() {
        int i = 8;
        renderTipsView();
        if (!this.showSwitch && this.mSwitchCameraImageView != null) {
            this.mSwitchCameraImageView.setVisibility(8);
        }
        CaptureV2MaskView captureV2MaskView = this.mMaskView;
        if (this.enableCropFrame) {
            i = 0;
        }
        captureV2MaskView.setVisibility(i);
        updateFlashIcon();
    }

    private void updateFlashIcon() {
        if (this.ivFlash != null) {
            this.ivFlash.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View v) {
                    CaptureV2OrientationActivity.this.toggleFlash();
                }
            });
            if (2 == maskMode) {
                this.ivFlash.setVisibility(0);
            } else {
                this.ivFlash.setVisibility(8);
            }
        }
    }

    private void renderTipsView() {
        if (this.mTipsTextView == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.mTips)) {
            if (this.mPureTipsContainer != null) {
                this.mPureTipsContainer.setVisibility(0);
            }
            this.mTipsTextView.setText(this.mTips);
        } else if (this.mPureTipsContainer != null) {
            this.mPureTipsContainer.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void mainThreadUnFriendlyJob() {
        if (this.mCameraView == null) {
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            this.cameraParams = new CameraParams();
            this.cameraParams.recordType = this.mRecordType;
            buildExif(this.cameraParams);
            MaskOptions options = calcMaskOptions(this.mMaskView.getWidth(), this.mMaskView.getHeight());
            this.cameraParams.mActivityRotation = getActivityRotation();
            pendingCrop(this.cameraParams, options);
            this.mCameraView = ((MultimediaVideoService) ServiceFactory.getAliService(MultimediaVideoService.class)).createCameraView(this, this, this.cameraParams);
            this.mCameraView.setLayoutParams(layoutParams);
            this.mCameraViewContainer.addView(this.mCameraView);
            Logger.debug(TAG, "mainThreadUnFriendlyJob calcMaskOptions: " + options);
            this.mMaskView.setMaskOptions(options);
            this.mCameraView.setOnRecordListener(this.onRecordListener);
            if (this.mEdgeDetectorEnabled) {
                this.mEdgeDetector = new EdgeDetector(LauncherApplicationAgent.getInstance().getApplicationContext());
                this.mEdgeDetector.setEnabled(true);
                this.mCameraView.setFramePreprocessor(this.mEdgeDetector);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void renderCenterTip(Bundle args, RelativeLayout root) {
        String ct = args.getString("centerTip");
        String centerTip = null;
        int position = 0;
        if (!TextUtils.isEmpty(ct)) {
            try {
                JSONObject j = JSONObject.parseObject(ct);
                centerTip = j.getString("tipString");
                position = j.getIntValue("position");
            } catch (Exception e) {
                Logger.debug(TAG, "Parse center tip exception," + e.getMessage());
            }
        }
        doRenderCenterTip(root, centerTip, position);
    }

    /* access modifiers changed from: protected */
    public void doRenderCenterTip(RelativeLayout root, String tip, int position) {
        if (root != null) {
            int containerHeight = root.getMeasuredHeight();
            if (!TextUtils.isEmpty(tip)) {
                int tipViewHeight = (int) getResources().getDimension(R.dimen.di_center_tip_height);
                int tipViewMargin = (int) getResources().getDimension(R.dimen.di_center_tip_margin);
                if (this.mCenterTipsView == null) {
                    this.mCenterTipsView = (TextView) LayoutInflater.from(this).inflate(R.layout.view_center_tips, root, false);
                    root.addView(this.mCenterTipsView);
                }
                this.mCenterTipsView.setVisibility(0);
                this.mCenterTipsView.setText(tip);
                RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, -2);
                adjustRightMarginWhenLandscape(rlp);
                int topMargin = (containerHeight - tipViewHeight) / 2;
                if (this.mInnerWindowRect != null && this.mMaskView.getVisibility() == 0) {
                    switch (position) {
                        case -1:
                            topMargin = this.mInnerWindowRect.bottom + tipViewMargin;
                            break;
                        case 0:
                            topMargin = (((this.mInnerWindowRect.bottom - this.mInnerWindowRect.top) - tipViewHeight) / 2) + this.mInnerWindowRect.top;
                            break;
                        case 1:
                            topMargin = (this.mInnerWindowRect.top - tipViewHeight) - tipViewMargin;
                            break;
                    }
                } else {
                    Logger.debug(TAG, "Mask not valid,set to center.");
                }
                rlp.topMargin = topMargin;
                this.mCenterTipsView.setLayoutParams(rlp);
                return;
            }
            this.mCenterTipsView.setVisibility(8);
        }
    }

    private void adjustRightMarginWhenLandscape(RelativeLayout.LayoutParams rlp) {
        if ((this instanceof LandscapeCaptureActivity) || (this instanceof LandscapeCaptureForCarInsuranceActivity)) {
            View rightControlPanel = findViewById(R.id.control_panel);
            if (rightControlPanel != null) {
                rlp.rightMargin = rightControlPanel.getMeasuredWidth();
            }
        }
    }

    private void pendingCrop(CameraParams cameraParams2, MaskOptions options) {
        if (this.mMaskView.getVisibility() != 0) {
            Logger.debug(TAG, "INVISIBLE,pendingCrop### return");
            return;
        }
        this.mMaskRect = new Rect(0, 0, this.mMaskView.getWidth(), this.mMaskView.getHeight());
        this.mInnerWindowRect = options.rect;
        if (this.isNeedCrop) {
            cameraParams2.mSrcRect = this.mMaskRect;
            cameraParams2.mCropRect = this.mInnerWindowRect;
            Logger.debug(TAG, "Need crop, srcRect=" + cameraParams2.mSrcRect + ",mCropRect=" + cameraParams2.mCropRect);
        }
    }

    private void buildExif(CameraParams cameraParams2) {
        LBSLocation latestLocation = getLatestPosition();
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
                if (!TextUtils.isEmpty(this.extraExif)) {
                    exif.put(Constants.EXIF_KEY_EXTRA, this.extraExif);
                }
                if (this.mCapturePictureSize > 0) {
                    exif.put("minPictureHeight", "1920");
                }
                cameraParams2.exif = exif;
                Logger.debug(TAG, "Before covert: latitude = " + latestLocation.getLatitude() + ", longitude = " + latestLocation.getLongitude());
                Logger.debug(TAG, "After covert passed to multimedia: latitudeRef = " + latitudeRef + ", latitude =  " + latitudeInDMS + ", longitudeRef = " + longitudeRef + ", longitude = " + longitudeInDMS);
            } catch (Exception e) {
                Logger.warn(TAG, "Covert location into DMS Exception." + e.getMessage());
            }
        } else {
            Logger.debug(TAG, "Failed to get latest location.");
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && this.mCameraView == null) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    CaptureV2OrientationActivity.this.mainThreadUnFriendlyJob();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public LBSLocation getLatestPosition() {
        return LBSLocationManagerProxy.getInstance().getLastKnownLocation(this);
    }

    public int dip2px(float dpValue) {
        return (int) ((dpValue * getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: private */
    public void calPreviewFrameCutRegion(APVideoRecordRsp rsp, Bundle args) {
        try {
            int w = rsp.mPreviewW;
            int h = rsp.mPreviewH;
            if (rsp.mDisplayOrientation == 90 || rsp.mDisplayOrientation == 270) {
                w = rsp.mPreviewH;
                h = rsp.mPreviewW;
            }
            Bounds bounds = getBounds(w, h);
            if (bounds != null) {
                args.putSerializable("cropRect", bounds);
            }
            JSONObject object = new JSONObject();
            object.put((String) "width", (Object) Integer.valueOf(w));
            object.put((String) "height", (Object) Integer.valueOf(h));
            args.putSerializable("cameraSize", object);
        } catch (Exception e) {
            Logger.debug(TAG, "calPreviewFrameCutRegion:### Should not be here!");
        }
    }

    @Nullable
    private Bounds getBounds(int w, int h) {
        if (this.mMaskRect == null || this.mInnerWindowRect == null || w <= 0 || h <= 0) {
            return null;
        }
        Bounds ret = new Bounds();
        float scaleX = (((float) w) * 1.0f) / ((float) (this.mMaskRect.right - this.mMaskRect.left));
        float scaleY = (((float) h) * 1.0f) / ((float) (this.mMaskRect.bottom - this.mMaskRect.top));
        int cropLeft = (int) (((float) this.mInnerWindowRect.left) * scaleX);
        int cropTop = (int) (((float) this.mInnerWindowRect.top) * scaleY);
        ret.left = cropLeft;
        ret.top = cropTop;
        ret.right = cropLeft + ((int) (((float) (this.mInnerWindowRect.right - this.mInnerWindowRect.left)) * scaleX));
        ret.bottom = cropTop + ((int) (((float) (this.mInnerWindowRect.bottom - this.mInnerWindowRect.top)) * scaleY));
        return ret;
    }

    /* access modifiers changed from: private */
    public Bounds calculateCutBounds(int w, int h) {
        if (!this.isNeedCrop) {
            return getBounds(w, h);
        }
        Bounds ret = new Bounds();
        ret.left = 0;
        ret.top = 0;
        ret.right = w;
        ret.bottom = h;
        return ret;
    }

    /* access modifiers changed from: private */
    public void publishImageAction(final MediaInfo mediaInfo) {
        Logger.debug(TAG, "publishImageAction: " + mediaInfo);
        this.isCaptureSessionNotified = true;
        runOnUiThread(new Runnable() {
            public final void run() {
                if (!CaptureV2OrientationActivity.this.isNeedPreview) {
                    CaptureV2OrientationActivity.this.directlyFinish(mediaInfo);
                    return;
                }
                Logger.debug(CaptureV2OrientationActivity.TAG, "Do preview.");
                mediaInfo.location = null;
                CaptureV2OrientationActivity.this.gotoImagePreview(mediaInfo);
            }
        });
    }

    /* access modifiers changed from: private */
    public void directlyFinish(MediaInfo mediaInfo) {
        Logger.debug(TAG, "No preview need.");
        CaptureServiceImpl.notifyAction(false, mediaInfo, false);
        OtherUtils.scanMediaFile(OtherUtils.getAbsPath(mediaInfo.path));
        if (!mediaInfo.isTakenByCMD) {
            Logger.debug(TAG, "CMD take photo,not finish here.");
            finish();
        }
    }

    public void finish() {
        CaptureActivity.needManuallyReleaseCamera(this.mCameraView);
        super.finish();
        CaptureServiceImpl.notifyRecorderEvent(Constants.JS_METHOD_ON_RECORDER_EXIT, null, false);
    }

    /* access modifiers changed from: private */
    public void gotoImagePreview(MediaInfo mediaInfo) {
        Intent previewIntent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), getPreviewClass());
        previewIntent.putExtras(getIntent().getExtras());
        previewIntent.putExtra("EXTRA_KEY_MEDIA_INFO", mediaInfo);
        previewIntent.putExtra(CaptureParam.INIT_CAMERA_FACING, this.cameraFacing);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), previewIntent);
        overridePendingTransition(17432576, 17432577);
        finish();
    }

    /* access modifiers changed from: private */
    public void switchCamera() {
        Logger.debug(TAG, "switchCamera:" + this.mCurrentMode);
        if (this.mCurrentMode != 2) {
            int mode = this.mCurrentMode;
            this.mCurrentMode = 2;
            CameraUtils.switchCameraFacing(this.mCameraView, this.cameraFacing, this.cameraHelperListener, mode);
        }
    }

    @SuppressLint({"Override"})
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.mCameraView != null) {
            this.mCameraView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* access modifiers changed from: private */
    public void notifyFail() {
        if (!this.isCaptureSessionNotified) {
            this.isCaptureSessionNotified = true;
            CaptureServiceImpl.notifyAction(true, null, true);
        }
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

    public static void startCaptureV2Activity(MicroApplication app, MicroApplicationContext appCtx, Context context, Bundle params) {
        int orientation = 1;
        if (context != null && appCtx != null && app != null) {
            if (params != null) {
                orientation = params.getInt(CaptureParam.ORIENTATION_MODE, 1);
            }
            Intent intent = new Intent(context, findTargetActivity(params, orientation));
            intent.putExtras(params);
            appCtx.startActivity(app, intent);
        }
    }

    @NonNull
    private static Class<? extends Activity> findTargetActivity(Bundle params, int orientation) {
        int i = 0;
        if (params != null) {
            i = params.getInt(CaptureParam.MASK_MODE, 0);
        }
        maskMode = i;
        if (orientation == 2) {
            if (maskMode == 1 || maskMode == 2 || maskMode == 3) {
                return LandscapeCaptureForCarInsuranceActivity.class;
            }
            return LandscapeCaptureActivity.class;
        } else if (maskMode == 1 || maskMode == 2 || maskMode == 3) {
            return PortraitCaptureExtendActivity.class;
        } else {
            return PortraitCaptureActivity.class;
        }
    }

    /* access modifiers changed from: private */
    public void handleQuitCMD(Bundle args) {
        if (args.getBoolean(CaptureParam.CMD_QUIT_STARTED, false)) {
            notifyFail();
            finish();
        }
    }

    /* access modifiers changed from: private */
    public void handleAIScanEffect(Bundle args) {
        if (args.containsKey(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM)) {
            boolean isShow = args.getBoolean(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM);
            if (args.getInt(CaptureParam.CAPTURE_SCAN_EFFECT) == 1 && this.mEdgeDetectorEnabled && this.mEdgeDetector != null) {
                this.mEdgeDetector.setEnabled(isShow);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateFlashBtnStatus(Bundle args) {
        if (args.containsKey(CaptureParam.UPDATE_UI_SHOW_FLASH_BTN)) {
            boolean isShow = args.getBoolean(CaptureParam.UPDATE_UI_SHOW_FLASH_BTN);
            if (this.ivFlash != null) {
                this.ivFlash.setVisibility(isShow ? 0 : 8);
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleCMD(Bundle args) {
        JSONObject cmds = (JSONObject) args.getSerializable(KEY_ARGS_CAPTURE_CMD);
        if (cmds != null) {
            Logger.warn(TAG, "Receive capture cmd : " + cmds);
            if (cmds.containsKey("action")) {
                String val = cmds.getString("action");
                if (TextUtils.equals("quit", val)) {
                    notifyFail();
                    finish();
                } else if (TextUtils.equals("takePhoto", val)) {
                    doTakePicture(true);
                } else {
                    Logger.warn(TAG, "Unsupported capture action : " + val);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateTips(Bundle args) {
        if (args.containsKey(CaptureParam.UPDATE_UI_TIP_TEXT)) {
            this.mTipDismissHandler.removeCallbacksAndMessages(null);
            this.mTips = args.getString(CaptureParam.UPDATE_UI_TIP_TEXT);
            renderTipsView();
            tipsAutoDismiss(args);
        }
    }

    private void tipsAutoDismiss(Bundle args) {
        if (this.mPureTipsContainer != null && this.mPureTipsContainer.getVisibility() == 0 && args.containsKey(CaptureParam.UPDATE_UI_TIP_DURATION)) {
            float duration = args.getFloat(CaptureParam.UPDATE_UI_TIP_DURATION);
            if (duration > 0.0f) {
                this.mTipDismissHandler.postDelayed(new Runnable() {
                    public final void run() {
                        CaptureV2OrientationActivity.this.mPureTipsContainer.setVisibility(8);
                    }
                }, (long) (1000.0f * duration));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchUpdateUI(Bundle args) {
    }

    /* access modifiers changed from: protected */
    public void loadImageDepends(String path, ImageView target) {
        if (TextUtils.isEmpty(path)) {
            target.setVisibility(8);
            return;
        }
        target.setVisibility(0);
        MultimediaImageService imageService = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
        if (imageService == null) {
            Logger.warn(TAG, "get MultimediaImageService return null.");
        } else {
            imageService.loadOriginalImage(path, target, null, null, "");
        }
    }

    /* access modifiers changed from: private */
    public void toggleFlash() {
        if (TextUtils.equals(this.ivFlash.getContentDescription(), getString(R.string.turn_off_flash))) {
            closeFlash();
        } else {
            openFlash(this.mCurrentCamera, this.mCurrentMode);
        }
    }

    private void closeFlash() {
        try {
            if (this.mCurrentCamera != null) {
                Parameters parameters = this.mCurrentCamera.getParameters();
                parameters.setFlashMode(CameraParams.FLASH_MODE_OFF);
                this.mCurrentCamera.setParameters(parameters);
            }
            this.ivFlash.setImageResource(R.drawable.ic_flash_off);
            this.ivFlash.setContentDescription(getString(R.string.turn_on_flash));
        } catch (Exception e) {
            Logger.warn(TAG, "Shutdown flash error! camera.setParameters exception ," + e.getMessage());
        }
    }

    private void openFlash(Camera camera, int currentMode) {
        Logger.debug(TAG, "currentMode:" + currentMode);
        if (camera == null) {
            try {
                onFlushError();
            } catch (Exception e) {
                Logger.warn(TAG, "camera.setParameters exception," + e.getMessage());
                onFlushError();
            }
        } else {
            Parameters parameters = camera.getParameters();
            List supportedFlashModes = parameters.getSupportedFlashModes();
            if (supportedFlashModes == null) {
                onFlushError();
                return;
            }
            if (supportedFlashModes.contains("torch")) {
                parameters.setFlashMode("torch");
            } else if (!supportedFlashModes.contains(CameraParams.FLASH_MODE_ON) || !CameraUtils.isSupportCaptureFlush()) {
                onFlushError();
                return;
            } else {
                parameters.setFlashMode(CameraParams.FLASH_MODE_ON);
            }
            camera.setParameters(parameters);
            this.ivFlash.setImageResource(R.drawable.ic_flash_on);
            this.ivFlash.setContentDescription(getString(R.string.turn_off_flash));
        }
    }

    private void onFlushError() {
        toast(getString(R.string.tips_unable_to_flush), 0);
        this.ivFlash.setImageResource(R.drawable.ic_flash_off);
        this.ivFlash.setContentDescription(getString(R.string.turn_on_flash));
    }

    public void onBackPressed() {
        super.onBackPressed();
        notifyFail();
    }

    /* access modifiers changed from: protected */
    public void resetCameraViewRotation(int rotation) {
        Logger.debug(TAG, "resetCameraViewRotation : rotation = " + rotation);
        if (this.mCameraView == null) {
            Logger.debug(TAG, "resetCameraViewRotation : cameraView NULL.");
        } else if (this.cameraParams == null) {
            Logger.debug(TAG, "resetCameraViewRotation : cameraParams NULL.");
        } else {
            int oldRotation = this.cameraParams.mActivityRotation;
            this.cameraParams.mActivityRotation = rotation;
            Logger.debug(TAG, "resetCameraViewRotation : Set rotation from " + oldRotation + " to " + rotation);
            this.mCameraView.reopenCamera(this.mCurrentMode, this.cameraParams);
        }
    }
}
