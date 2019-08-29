package com.autonavi.gdtaojin.camera;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.view.MotionEvent;

public class ManualAutoFocusStrategy implements IFocusStrategy {
    private static final String TAG = "gxd_camera";
    private AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
        public void onAutoFocus(boolean z, Camera camera) {
            ManualAutoFocusStrategy.this.mHandler.removeMessages(4);
            if (z) {
                ManualAutoFocusStrategy.this.mFocusEndTime = System.currentTimeMillis();
                if (ManualAutoFocusStrategy.this.mCameraController.getCommandEvent() == CommandEvent.CLICK_TAKE_PIC) {
                    ManualAutoFocusStrategy.this.mfocusUI.clearFocus();
                    ManualAutoFocusStrategy.this.takePicInSuccessAutoFocus();
                } else if (ManualAutoFocusStrategy.this.mCameraController.getCommandEvent() == CommandEvent.TOUCH_SCREEN) {
                    ManualAutoFocusStrategy.this.showAutoFocusSucess();
                    if (ManualAutoFocusStrategy.this.mCameraController.isTouchTakingPic()) {
                        ManualAutoFocusStrategy.this.takePicInSuccessAutoFocus();
                        return;
                    }
                    ManualAutoFocusStrategy.this.mCameraController.setCameraState(CameraState.IDLE);
                    if (ManualAutoFocusStrategy.this.mCameraController.getIsSupportContinuousFocus()) {
                        ManualAutoFocusStrategy.this.mCameraController.restoreContinuousFocus();
                    }
                } else if (ManualAutoFocusStrategy.this.mCameraController.getCommandEvent() == CommandEvent.FIRST_IN_FOCUS) {
                    ManualAutoFocusStrategy.this.showAutoFocusSucess();
                    ManualAutoFocusStrategy.this.mCameraController.setCameraState(CameraState.IDLE);
                    if (ManualAutoFocusStrategy.this.mCameraController.getIsSupportContinuousFocus()) {
                        ManualAutoFocusStrategy.this.mCameraController.restoreContinuousFocus();
                    }
                }
            } else {
                ManualAutoFocusStrategy.this.mHandler.sendEmptyMessageDelayed(4, 500);
                ManualAutoFocusStrategy.this.takePicInFailedAutoFocus();
            }
        }
    };
    /* access modifiers changed from: private */
    public CameraControllerManager mCameraController;
    public PhotoModule mCameraModule;
    /* access modifiers changed from: private */
    public long mFocusEndTime;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private MotionEvent mMotionEvent;
    /* access modifiers changed from: private */
    public FocusUI mfocusUI;
    private int touch_x;
    private int touch_y;

    public void operateFocus() {
    }

    public ManualAutoFocusStrategy(PhotoModule photoModule, FocusUI focusUI, CameraControllerManager cameraControllerManager, Handler handler) {
        this.mCameraModule = photoModule;
        this.mfocusUI = focusUI;
        this.mCameraController = cameraControllerManager;
        this.mHandler = handler;
    }

    public void cancelFocus() {
        if (!this.mCameraController.getPicTaked() && this.mCameraModule != null) {
            this.mCameraModule.onCancelAutoFocus();
        }
    }

    public void executeFocus(MotionEvent motionEvent) {
        if (!this.mCameraController.getPicTaked() && this.mCameraModule != null) {
            this.mMotionEvent = motionEvent;
            if (isSupportAutoFocus()) {
                if (this.mCameraController.getCameraState() == CameraState.AUTO_FOCUSING) {
                    cancelFocus();
                    setAutoFocus();
                } else if (this.mCameraController.getCameraState() == CameraState.IDLE) {
                    setAutoFocus();
                } else if (this.mCameraController.getCameraState() == CameraState.TAKING_PICTURE) {
                }
            }
        }
    }

    private void setAutoFocus() {
        Parameters currentParameters = this.mCameraController.getCurrentParameters();
        currentParameters.setFocusMode("auto");
        this.mCameraController.trySetParameters(currentParameters);
        this.mCameraController.setCameraState(CameraState.AUTO_FOCUSING);
        try {
            this.mCameraModule.onAutoFocus(this.autoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getFocusEndTime() {
        return this.mFocusEndTime;
    }

    /* access modifiers changed from: private */
    public void takePicInSuccessAutoFocus() {
        if (this.mCameraController.getCameraState() != CameraState.TAKING_PICTURE) {
            this.mCameraController.setCameraState(CameraState.TAKING_PICTURE);
            this.mCameraController.takePicture();
        }
    }

    /* access modifiers changed from: private */
    public void takePicInFailedAutoFocus() {
        if ((!this.mCameraController.isTouchTakingPic() || this.mCameraController.getCommandEvent() != CommandEvent.TOUCH_SCREEN) && this.mCameraController.getCommandEvent() != CommandEvent.CLICK_TAKE_PIC) {
            this.mCameraController.setCameraState(CameraState.IDLE);
            if (this.mCameraController.getIsSupportContinuousFocus()) {
                this.mCameraController.restoreContinuousFocus();
            }
        } else if (this.mCameraController.getCameraState() != CameraState.TAKING_PICTURE) {
            this.mCameraController.setCameraState(CameraState.TAKING_PICTURE);
            this.mCameraController.takePicture();
        }
    }

    /* access modifiers changed from: private */
    public void showAutoFocusSucess() {
        if (this.mMotionEvent == null) {
            this.mfocusUI.onFocusStarted();
        }
        this.mfocusUI.onFocusSucceeded();
        this.mHandler.sendEmptyMessageDelayed(4, 500);
    }

    private boolean isSupportAutoFocus() {
        return this.mCameraController.getCurrentParameters().getSupportedFocusModes().contains("auto");
    }
}
