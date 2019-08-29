package com.autonavi.gdtaojin.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.gdtaojin.basemap.SimplePictureDialog;
import com.autonavi.gdtaojin.camera.CameraSettingsMenu.OnSettingChangeListener;
import com.autonavi.gdtaojin.camera.photocompress.ImageCompressUtil;
import com.autonavi.gdtaojin.camera.photocompress.ImageCompressUtil.Param;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.Timer;
import java.util.TimerTask;

public class CameraActivity extends Activity implements OnGestureListener, Callback, OnClickListener {
    public static final long GXDTAOJIN_LOCATION_VALID_TIME = 500;
    public static final int MSG_HIDE_FOCUS_UI = 4;
    public static final int MSG_RETAKE_PICTURE = 5;
    public static final int MSG_SET_MOVING_FOCUS_STRATEGY = 3;
    public static final String MSG_SHOOTED_TYPE = "mShootedTypeFlag";
    public static final int MSG_UPDATE_CAMERA_UI = 2;
    public static final int MSG_UPDATE_UI = 1;
    public static int RES_ID_USEPICTURE = 0;
    private static final String TAG = "gxd_camera";
    public View CameraFocusView;
    private final float VOLUM_ZOOM_RATIO = 0.25f;
    public int XTouch;
    public int YTouch;
    private final float ZERO = 1.0f;
    private CameraSettingsMenu cameraSettingsMenu;
    private TextView cancleCameraBtn;
    private RelativeLayout cancleCameraBtnLayout;
    private View captureBtn;
    private boolean isConatinLocationJar = false;
    private boolean isEverStartCamera = false;
    private boolean isOpenCameraError = false;
    /* access modifiers changed from: private */
    public boolean isVolumeTakePicture;
    private ImageView ivSettingsMenu;
    /* access modifiers changed from: private */
    public Camera mCamera;
    /* access modifiers changed from: private */
    public RelativeLayout mCameraCaptureLayout;
    /* access modifiers changed from: private */
    public CameraControllerManager mCameraController = null;
    private Context mContext;
    private ImageView mFlashButton;
    private int mFlashClose_id;
    private int mFlashOpen_id;
    /* access modifiers changed from: private */
    public GestureDetector mGesture = null;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    CameraActivity.this.attachAndShowCameraPic();
                    return;
                case 2:
                    CameraActivity.this.updateCameraUI();
                    return;
                case 3:
                    if (!CameraActivity.this.mPausing) {
                        CameraActivity.this.mCameraController.hideFocusView();
                        CameraActivity.this.mCameraController.setMovingAutoFocusStrategy();
                        return;
                    }
                    break;
                case 4:
                    if (!CameraActivity.this.mPausing) {
                        CameraActivity.this.mCameraController.hideFocusView();
                        return;
                    }
                    break;
                case 5:
                    if (!CameraActivity.this.mPausing) {
                        CameraActivity.this.mUsePicPreviewLayout.setVisibility(8);
                        if (CameraActivity.this.mCamera != null) {
                            CameraActivity.this.updateCameraUI();
                        }
                        CameraActivity.this.mCameraController.setPicTaked(false);
                        CameraActivity.this.mCameraController.setCommandEvent(CommandEvent.IDLE);
                        CameraActivity.this.mCameraController.setCameraState(CameraState.IDLE);
                        CameraActivity.this.initCameraState(true);
                        break;
                    }
                    break;
            }
        }
    };
    public boolean mIsNeedTracePoint = false;
    /* access modifiers changed from: private */
    public int mMaxZoom;
    private String mParameterString;
    /* access modifiers changed from: private */
    public boolean mPausing;
    /* access modifiers changed from: private */
    public Bitmap mPreviewBitmap;
    private Resources mResouse = null;
    private TextView mRetakeBtn;
    /* access modifiers changed from: private */
    public ScaleGestureDetector mScaleDetector = null;
    private SurfaceViewScaleGestureListener mScaleGestureListener = null;
    private ImageView mShowCameraPic;
    private SurfaceHolder mSurfaceHolder;
    private View mSurfaceParentView;
    private Toast mToast;
    private RelativeLayout mUseBtnParLayout;
    private View mUsePicBtn;
    private RelativeLayout mUsePicParLayout;
    /* access modifiers changed from: private */
    public RelativeLayout mUsePicPreviewLayout;
    /* access modifiers changed from: private */
    public int mZoomProgress;
    /* access modifiers changed from: private */
    public SeekBar mZoomSeekBar;
    /* access modifiers changed from: private */
    public int paddingTop;
    private SurfaceView surfaceSv;

    public interface REQUEST_CODE {
        public static final int REQUEST_CODE_NEWPOI_PAGE = 21;
        public static final int REQUEST_CODE_RETAKE = 22;
        public static final int REQUEST_CODE_SHOW_PICTURE = 20;
    }

    class SurfaceViewGestureListener extends SimpleOnGestureListener {
        SurfaceViewGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return super.onDown(motionEvent);
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!CameraActivity.this.mCameraController.isSupportAutoFocus() || !ApiChecker.AT_LEAST_14) {
                return false;
            }
            CameraActivity.this.XTouch = (int) motionEvent.getX();
            CameraActivity.this.YTouch = ((int) motionEvent.getY()) + (CameraActivity.this.paddingTop / 2);
            try {
                if (CameraActivity.this.mCameraController.getCommandEvent() != CommandEvent.CLICK_TAKE_PIC) {
                    CameraActivity.this.mCameraController.setCommandEvent(CommandEvent.TOUCH_SCREEN);
                    CameraActivity.this.touchScreen(motionEvent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.onSingleTapUp(motionEvent);
        }
    }

    class SurfaceViewScaleGestureListener implements OnScaleGestureListener {
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        SurfaceViewScaleGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                return false;
            }
            if (scaleFactor < 1.0f) {
                CameraActivity.this.mZoomProgress = CameraActivity.this.mZoomProgress - 1;
            } else {
                CameraActivity.this.mZoomProgress = CameraActivity.this.mZoomProgress + 1;
            }
            if (CameraActivity.this.mZoomProgress <= 0) {
                CameraActivity.this.mZoomProgress = 0;
            } else if (CameraActivity.this.mZoomProgress >= CameraActivity.this.mMaxZoom) {
                CameraActivity.this.mZoomProgress = CameraActivity.this.mMaxZoom;
            }
            CameraActivity.this.mCameraController.setCameraZoom(CameraActivity.this.mZoomProgress);
            CameraActivity.this.mZoomSeekBar.setProgress(CameraActivity.this.mZoomProgress);
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            if (!CameraActivity.this.mPausing && CameraActivity.this.mCameraController.getIsSupportContinuousFocus() && CameraActivity.this.mCameraController.getCameraState() == CameraState.IDLE) {
                CameraActivity.this.mHandler.removeMessages(3);
                CameraActivity.this.mHandler.sendEmptyMessageDelayed(3, 700);
            }
        }
    }

    public void onActivityWillReturn(Bitmap bitmap) {
    }

    public void onClick(View view) {
    }

    public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
    }

    public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
    }

    public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
    }

    public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
    }

    public void onImageCompressParamsCreated(Param param) {
    }

    public void onPreviewBitmapCreated(Bitmap bitmap) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public void showSampleDlg() {
        final SimplePictureDialog simplePictureDialog = new SimplePictureDialog(this, this.mParameterString);
        simplePictureDialog.show();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                simplePictureDialog.dismiss();
                timer.cancel();
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.mSurfaceHolder.getSurface() != null) {
            try {
                this.mSurfaceHolder = surfaceHolder;
                if (this.mCamera != null && !this.mPausing) {
                    if (!isFinishing()) {
                        if (!this.mCameraController.isPreviewing() || !surfaceHolder.isCreating()) {
                            this.mCameraController.restartPreview(this.mCamera, this.mSurfaceHolder);
                        } else {
                            this.mCameraController.setStartPreview(this.mCamera, surfaceHolder);
                        }
                        if (this.mCameraController.isStart_preview_failed()) {
                            showToast("预览失败，请稍后重试");
                            finish();
                            return;
                        }
                        if (this.surfaceSv != null) {
                            this.surfaceSv.setEnabled(true);
                        }
                        if (!(this.mZoomSeekBar == null || this.captureBtn == null || this.cancleCameraBtn == null)) {
                            this.mZoomSeekBar.setVisibility(0);
                            this.captureBtn.setVisibility(0);
                            this.cancleCameraBtn.setBackgroundResource(this.mResouse.getIdentifier("gxdcam_camera_cancle_btn", ResUtils.DRAWABLE, getPackageName()));
                        }
                        initCameraState(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.surfaceSv = null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mParameterString = getIntent().getStringExtra(CameraInterface.CAMERA_PARAMETER);
        this.mContext = this;
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.mResouse = getResources();
        setContentView(this.mResouse.getIdentifier("gxdcam_activity_camera", ResUtils.LAYOUT, getPackageName()));
        initView();
        initSurfaceHolder();
        setDensity();
        this.mCameraController = new CameraControllerManager(this, this.mHandler, this.mSurfaceHolder, this.mResouse);
        this.mCameraController.startOrientationEventListener();
        this.mCameraController.setIsContainLocationJar(CameraConst.IS_CONTAIN_LOCATION_JAR);
        this.mGesture = new GestureDetector(this, new SurfaceViewGestureListener());
        this.mScaleGestureListener = new SurfaceViewScaleGestureListener();
        this.mScaleDetector = new ScaleGestureDetector(this, this.mScaleGestureListener);
        this.isVolumeTakePicture = this.mCameraController.isVolumeKeyTakePicture();
    }

    public void onResume() {
        super.onResume();
        this.mPausing = false;
        if (this.mCamera == null && this.mCameraController != null) {
            this.mCamera = this.mCameraController.openCameraAndSetParameters();
            this.paddingTop = this.mCameraController.getCurrPreviewSize(CameraConst.widthPixels, CameraConst.heightPixels, CameraConst.picWidthPixels, CameraConst.picHeightPixels);
            LayoutParams layoutParams = this.mCameraCaptureLayout.getLayoutParams();
            if (layoutParams != null) {
                int i = layoutParams.height;
            }
            if (this.paddingTop > 0 && this.mSurfaceParentView != null) {
                this.mSurfaceParentView.setPadding(0, this.paddingTop / 2, 0, this.paddingTop / 2);
            }
        }
        if (this.mCamera == null) {
            this.isOpenCameraError = true;
            showToast("相机无法启动，请打开手机系统权限或重新启动手机");
            finish();
        } else if (this.mCameraController != null) {
            boolean isFlashFunctionOn = isFlashFunctionOn();
            changeFlashViewByFunction(isFlashFunctionOn);
            if (isFlashFunctionOn) {
                this.mCameraController.setCameraFlash(true);
            } else {
                this.mCameraController.setCameraFlash(false);
            }
            if (this.isEverStartCamera) {
                updateCameraUI();
            }
            this.mMaxZoom = this.mCameraController.getMaxCameraZoom();
            this.mZoomSeekBar.setMax(this.mMaxZoom);
            this.mCameraController.enableOrientationEventListener();
            this.mCameraController.RegisterSensor();
        }
    }

    public void onPause() {
        super.onPause();
        if (this.isOpenCameraError) {
            this.mCameraController.disableOrientationEventListener();
            return;
        }
        this.mPausing = true;
        removeAllMessageInHandlerQueue();
        this.mCameraController.cancelAutoFocusStrategy();
        this.mCameraController.stopAndReleaseCamera();
        this.mCameraController.unRegisterSensor();
        this.mCameraController.disableOrientationEventListener();
        this.mCamera = null;
        this.isEverStartCamera = true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.isOpenCameraError) {
            this.isOpenCameraError = false;
            this.mCameraController.stopAndReleaseCamera();
            return;
        }
        this.mCameraController.stopAndReleaseCamera();
        releasePreivewBitmap();
        this.mHandler = null;
        this.mResouse = null;
        this.isEverStartCamera = false;
        resetCameraConst();
    }

    /* access modifiers changed from: private */
    public void releasePreivewBitmap() {
        if (this.mShowCameraPic != null) {
            this.mShowCameraPic.setImageBitmap(null);
        }
        if (this.mPreviewBitmap != null && !this.mPreviewBitmap.isRecycled()) {
            this.mPreviewBitmap.recycle();
            this.mPreviewBitmap = null;
        }
    }

    /* access modifiers changed from: private */
    public void touchScreen(MotionEvent motionEvent) {
        if (this.mCamera != null) {
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    CameraActivity.this.mCameraController.startAndShowFocusView(CameraActivity.this.XTouch, CameraActivity.this.YTouch);
                }
            }, 0);
            if (this.mCameraController.getCameraState() == CameraState.AUTO_FOCUSING) {
                this.mCameraController.cancelAutoFocusStrategy();
            }
            if (this.mCameraController.getCameraState() != CameraState.TAKING_PICTURE) {
                this.mCameraController.executeAutoFocusStrategy(motionEvent);
            }
        }
    }

    /* access modifiers changed from: private */
    public void clickTakeBtn() {
        if (!CameraConst.IS_HAS_CAPTURE_PERMISSION) {
            showToast(CameraConst.PERMISSION_REASON);
            return;
        }
        if (CameraConst.CAPTURE_LISTENER != null) {
            CameraConst.CAPTURE_LISTENER.onCapture();
        }
        this.mHandler.removeMessages(3);
        this.mCameraController.capture();
    }

    private void initView() {
        int identifier = this.mResouse.getIdentifier("id_process_btns_ll", "id", getPackageName());
        int identifier2 = this.mResouse.getIdentifier("camera_cancle_btn", "id", getPackageName());
        int identifier3 = this.mResouse.getIdentifier("camera_ok_btn", "id", getPackageName());
        int identifier4 = this.mResouse.getIdentifier("id_switch_camera_btn", "id", getPackageName());
        int identifier5 = this.mResouse.getIdentifier("id_capture_btn", "id", getPackageName());
        int identifier6 = this.mResouse.getIdentifier("id_cancle_btn_layout", "id", getPackageName());
        int identifier7 = this.mResouse.getIdentifier("usepic_layout", "id", getPackageName());
        int identifier8 = this.mResouse.getIdentifier("camera_pic_activity", "id", getPackageName());
        int identifier9 = this.mResouse.getIdentifier("zoom_seekbar_def", "id", getPackageName());
        int identifier10 = this.mResouse.getIdentifier("id_area_sv", "id", getPackageName());
        int identifier11 = this.mResouse.getIdentifier("ivAutoTake", "id", getPackageName());
        this.mFlashOpen_id = this.mResouse.getIdentifier("gxdcam_btn_flash_open_change", ResUtils.DRAWABLE, getPackageName());
        this.mFlashClose_id = this.mResouse.getIdentifier("gxdcam_btn_flush_change", ResUtils.DRAWABLE, getPackageName());
        int identifier12 = this.mResouse.getIdentifier("id_area_sv_parent", "id", getPackageName());
        int i = identifier10;
        int identifier13 = this.mResouse.getIdentifier("camera_choose_layout", "id", getPackageName());
        int identifier14 = this.mResouse.getIdentifier("camera_pic_parent", "id", getPackageName());
        RES_ID_USEPICTURE = identifier7;
        this.mSurfaceParentView = findViewById(identifier12);
        this.mRetakeBtn = (TextView) findViewById(identifier2);
        this.mUsePicBtn = findViewById(identifier3);
        this.cancleCameraBtn = (TextView) findViewById(identifier4);
        this.captureBtn = findViewById(identifier5);
        this.cancleCameraBtnLayout = (RelativeLayout) findViewById(identifier6);
        this.mUsePicPreviewLayout = (RelativeLayout) findViewById(identifier7);
        this.mShowCameraPic = (ImageView) this.mUsePicPreviewLayout.findViewById(identifier8);
        this.mZoomSeekBar = (SeekBar) findViewById(identifier9);
        this.mFlashButton = (ImageView) findViewById(identifier11);
        this.mCameraCaptureLayout = (RelativeLayout) findViewById(identifier);
        this.mUseBtnParLayout = (RelativeLayout) findViewById(identifier13);
        this.mUsePicParLayout = (RelativeLayout) findViewById(identifier14);
        this.mUsePicPreviewLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.cameraSettingsMenu = new CameraSettingsMenu(this, new OnSettingChangeListener() {
            public void onSettingChange(int i, boolean z) {
                if (i == 1) {
                    CameraActivity.this.mCameraController.setCameraFlash(z);
                    return;
                }
                if (i != 2 && i == 3) {
                    CameraActivity.this.isVolumeTakePicture = z;
                }
            }
        }, this.mCameraController, this.mResouse);
        this.mFlashButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CameraActivity.this.mCameraController != null && !CameraActivity.this.mCameraController.getPicTaked()) {
                    boolean isFlashFunctionOn = CameraActivity.this.isFlashFunctionOn();
                    CameraActivity.this.setFlashFunctionOn(!isFlashFunctionOn);
                    CameraActivity.this.changeFlashViewByFunction(!isFlashFunctionOn);
                    CameraActivity.this.mCameraController.setCameraFlash(!isFlashFunctionOn);
                }
            }
        });
        this.surfaceSv = (SurfaceView) findViewById(i);
        this.surfaceSv.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (CameraActivity.this.mCameraController != null) {
                    Parameters currentParameters = CameraActivity.this.mCameraController.getCurrentParameters();
                    if (currentParameters != null && currentParameters.isZoomSupported() && ApiChecker.AT_LEAST_8) {
                        CameraActivity.this.mScaleDetector.onTouchEvent(motionEvent);
                    }
                }
                CameraActivity.this.mGesture.onTouchEvent(motionEvent);
                return true;
            }
        });
        SurfaceHolder holder = this.surfaceSv.getHolder();
        if (!ApiChecker.AT_LEAST_11) {
            holder.setType(3);
        }
        this.mZoomSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (CameraActivity.this.mCamera != null) {
                    CameraActivity.this.mZoomProgress = seekBar.getProgress();
                    if (CameraActivity.this.mZoomProgress < 0 || CameraActivity.this.mZoomProgress >= CameraActivity.this.mMaxZoom - 1) {
                        CameraActivity.this.mCameraController.setCameraZoom(CameraActivity.this.mMaxZoom - 1);
                    } else {
                        CameraActivity.this.mCameraController.setCameraZoom(CameraActivity.this.mZoomProgress);
                    }
                }
            }
        });
        this.captureBtn.setOnClickListener(new OnClickListenerProxy(new OnClickListener() {
            public void onClick(View view) {
                CameraActivity.this.clickTakeBtn();
            }
        }));
        this.cancleCameraBtnLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CameraActivity.this.mCameraController.hideFocusView();
                CameraActivity.this.mCameraController.deletePicFile();
                CameraActivity.this.finish();
            }
        });
        this.mRetakeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CameraActivity.this.mCameraCaptureLayout.setVisibility(0);
                CameraActivity.this.mUsePicPreviewLayout.setVisibility(8);
                CameraActivity.this.releasePreivewBitmap();
                if (CameraActivity.this.mCamera != null) {
                    CameraActivity.this.updateCameraUI();
                }
                CameraActivity.this.mCameraController.setPicTaked(false);
                CameraActivity.this.mCameraController.setCommandEvent(CommandEvent.IDLE);
                CameraActivity.this.mCameraController.setCameraState(CameraState.IDLE);
                CameraActivity.this.initCameraState(true);
            }
        });
        this.mUsePicBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Bitmap access$1300 = CameraActivity.this.mPreviewBitmap;
                    if (access$1300 != null) {
                        CameraActivity.this.onActivityWillReturn(access$1300);
                        CameraActivity.this.mCameraController.returnResult(access$1300.getWidth(), access$1300.getHeight());
                        return;
                    }
                    CameraActivity.this.showToast("拍照失败，请重试");
                    CameraActivity.this.mUsePicPreviewLayout.setVisibility(8);
                    if (CameraActivity.this.mCamera != null) {
                        CameraActivity.this.updateCameraUI();
                    }
                    CameraActivity.this.mCameraController.setPicTaked(false);
                    CameraActivity.this.mCameraController.setCommandEvent(CommandEvent.IDLE);
                    CameraActivity.this.mCameraController.setCameraState(CameraState.IDLE);
                    CameraActivity.this.initCameraState(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (!TextUtils.isEmpty(this.mParameterString)) {
            this.surfaceSv.post(new Runnable() {
                public void run() {
                    CameraActivity.this.showSampleDlg();
                }
            });
        }
    }

    public void updateCameraUI() {
        if (this.mCameraController != null) {
            this.mCameraController.setStartPreview(this.mCamera, this.mSurfaceHolder);
        }
        if (this.mCameraController.isStart_preview_failed()) {
            showToast("预览失败，请稍后重试");
            finish();
            return;
        }
        if (this.surfaceSv != null) {
            this.surfaceSv.setEnabled(true);
        }
        this.mZoomSeekBar.setVisibility(0);
        this.captureBtn.setVisibility(0);
        this.cancleCameraBtn.setBackgroundResource(this.mResouse.getIdentifier("gxdcam_camera_cancle_btn", ResUtils.DRAWABLE, getPackageName()));
    }

    private void initSurfaceHolder() {
        this.mSurfaceHolder = this.surfaceSv.getHolder();
        this.mSurfaceHolder.setKeepScreenOn(true);
        this.mSurfaceHolder.addCallback(this);
    }

    private void removeAllMessageInHandlerQueue() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.removeMessages(5);
        }
    }

    /* access modifiers changed from: private */
    public void initCameraState(boolean z) {
        this.mCameraController.setCommandEvent(CommandEvent.FIRST_IN_FOCUS);
        this.mCameraController.setCameraState(CameraState.IDLE);
        this.mCameraController.setPicTaked(false);
        int i = z ? 300 : 700;
        if (this.mCameraController.isSupportAutoFocus() && this.mUsePicPreviewLayout.getVisibility() != 0 && this.mCameraController.getCommandEvent() == CommandEvent.FIRST_IN_FOCUS) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!CameraActivity.this.mPausing && CameraActivity.this.mCameraController.getCommandEvent() == CommandEvent.FIRST_IN_FOCUS && CameraActivity.this.mCamera != null) {
                        CameraActivity.this.mCameraController.executeAutoFocus();
                    }
                }
            }, (long) i);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 20 && i2 == 22) {
            Bitmap bitmap = this.mPreviewBitmap;
            onActivityWillReturn(bitmap);
            if (bitmap != null) {
                this.mCameraController.returnResult(bitmap.getWidth(), bitmap.getHeight());
                return;
            }
            this.mCameraController.returnResult(-1, -1);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 2) {
            if (this.mCamera != null) {
                this.mCameraController.setDisplayOrientation(0);
            }
        } else if (this.mCamera != null) {
            this.mCameraController.setDisplayOrientation(90);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            this.mCameraController.deletePicFile();
        } else if (i == 27 && keyEvent.getAction() == 0) {
            if (this.mUsePicPreviewLayout.getVisibility() != 0) {
                clickTakeBtn();
            }
        } else if (i == 24) {
            if (!this.isVolumeTakePicture) {
                if (this.mCameraController.getCurrentParameters() != null && this.mCameraController.getCurrentParameters().isZoomSupported()) {
                    this.mZoomProgress = (int) (((float) this.mZoomProgress) + (((float) this.mMaxZoom) * 0.25f));
                    if (this.mZoomProgress <= 0) {
                        this.mZoomProgress = 0;
                    } else if (this.mZoomProgress >= this.mMaxZoom) {
                        this.mZoomProgress = this.mMaxZoom;
                    }
                    this.mCameraController.setCameraZoom(this.mZoomProgress);
                    this.mZoomSeekBar.setProgress(this.mZoomProgress);
                    return true;
                }
            } else if (this.mUsePicPreviewLayout.getVisibility() != 0) {
                clickTakeBtn();
                return true;
            }
            return true;
        } else if (i == 25) {
            if (!this.isVolumeTakePicture) {
                if (this.mCameraController.getCurrentParameters() != null && this.mCameraController.getCurrentParameters().isZoomSupported()) {
                    this.mZoomProgress = (int) (((float) this.mZoomProgress) - (((float) this.mMaxZoom) * 0.25f));
                    if (this.mZoomProgress <= 0) {
                        this.mZoomProgress = 0;
                    } else if (this.mZoomProgress >= this.mMaxZoom) {
                        this.mZoomProgress = this.mMaxZoom;
                    }
                    this.mCameraController.setCameraZoom(this.mZoomProgress);
                    this.mZoomSeekBar.setProgress(this.mZoomProgress);
                    return true;
                }
            } else if (this.mUsePicPreviewLayout.getVisibility() != 0) {
                clickTakeBtn();
                return true;
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void attachAndShowCameraPic() {
        this.mCameraCaptureLayout.setVisibility(8);
        this.mUsePicPreviewLayout.setVisibility(0);
        Param param = new Param();
        param.filePath = this.mCameraController.getPicFilePath();
        param.maxSize = Math.max(CameraConst.widthPixels, CameraConst.heightPixels);
        param.quality = CameraConst.PICTURE_QUALITY;
        param.readFileDegree = true;
        onImageCompressParamsCreated(param);
        this.mPreviewBitmap = ImageCompressUtil.handleImage(param);
        if (this.mPreviewBitmap != null) {
            onPreviewBitmapCreated(this.mPreviewBitmap);
            this.mShowCameraPic.setImageBitmap(this.mPreviewBitmap);
        } else {
            showToast("获取图片取得异常，请重拍");
            this.mZoomSeekBar.setVisibility(8);
            this.mUsePicPreviewLayout.setVisibility(8);
            this.mCameraCaptureLayout.setVisibility(0);
            if (this.mCamera != null) {
                updateCameraUI();
            }
            this.mCameraController.setPicTaked(false);
            this.mCameraController.setCommandEvent(CommandEvent.IDLE);
            this.mCameraController.setCameraState(CameraState.IDLE);
            initCameraState(true);
        }
        if (CameraConst.CAPTURE_LISTENER != null) {
            CameraConst.CAPTURE_LISTENER.onCaptureEnd();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        return true;
    }

    public void showToast(String str) {
        if (this.mToast == null) {
            this.mToast = Toast.makeText(this, str, 0);
        } else {
            this.mToast.setText(str);
            this.mToast.setDuration(1);
        }
        this.mToast.show();
    }

    public void setDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        CameraConst.density = displayMetrics.density;
        CameraConst.heightPixels = displayMetrics.heightPixels;
        CameraConst.widthPixels = displayMetrics.widthPixels;
    }

    public void resetCameraConst() {
        CameraConst.FOLDER_NAME = "/CameraDemo/Image/";
        CameraConst.MAX_PICTURE_SIZE = 1920;
        CameraConst.MAX_PICTURE_COMPRESS_SIZE_VALUE = 1280;
        CameraConst.PICTURE_QUALITY = 92;
        CameraConst.IS_HAS_FLASH = true;
        CameraConst.IS_HAS_TOUCH_CAPTURE = false;
        CameraConst.IS_HAS_VOLUME_CAPTURE = false;
        CameraConst.IS_DEVELOP_PICTURESIZE = false;
        CameraConst.IS_HAS_CAPTURE_PERMISSION = true;
        CameraConst.PERMISSION_REASON = "";
        CameraConst.CAPTURE_LISTENER = null;
        CameraConst.IS_PICTURE_COMPRESSED = true;
    }

    public boolean isFlashFunctionOn() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("SharedPreferences", 0);
        try {
            return sharedPreferences.getInt(CameraSettingsMenu.PREF_KEY_FLASHLIGHT, 0) == 1;
        } catch (Exception unused) {
            return sharedPreferences.getBoolean(CameraSettingsMenu.PREF_KEY_FLASHLIGHT, false);
        }
    }

    public void setFlashFunctionOn(boolean z) {
        Editor edit = this.mContext.getSharedPreferences("SharedPreferences", 0).edit();
        edit.putInt(CameraSettingsMenu.PREF_KEY_FLASHLIGHT, z ? 1 : 0);
        edit.commit();
    }

    public void changeFlashViewByFunction(boolean z) {
        if (this.mFlashButton != null) {
            if (z) {
                this.mFlashButton.setBackgroundResource(this.mFlashOpen_id);
                return;
            }
            this.mFlashButton.setBackgroundResource(this.mFlashClose_id);
        }
    }
}
