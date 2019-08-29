package com.autonavi.gdtaojin.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MovingAutoFocusStrategy implements IFocusStrategy {
    private static final String TAG = "gxd_camera";
    /* access modifiers changed from: private */
    public CameraControllerManager mCameraController;
    public PhotoModule mCameraModule;
    private Context mContext;
    /* access modifiers changed from: private */
    public long mFocusEndTime;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public boolean mPreviousMoving;
    /* access modifiers changed from: private */
    public FocusUI mfocusUI;

    final class MyAutoFocusMoveCallback implements AutoFocusMoveCallback {
        private MyAutoFocusMoveCallback() {
        }

        public final void onAutoFocusMoving(boolean z, Camera camera) {
            MovingAutoFocusStrategy.this.mCameraController.setCameraState(CameraState.IDLE);
            if (z != MovingAutoFocusStrategy.this.mPreviousMoving) {
                if (!z || MovingAutoFocusStrategy.this.mPreviousMoving) {
                    if (!z) {
                        MovingAutoFocusStrategy.this.mfocusUI.onFocusSucceeded();
                        MovingAutoFocusStrategy.this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                MovingAutoFocusStrategy.this.mfocusUI.clearFocus();
                            }
                        }, 500);
                        MovingAutoFocusStrategy.this.mFocusEndTime = System.currentTimeMillis();
                    }
                } else if (!MovingAutoFocusStrategy.this.isUsePicPreviewLayoutShow()) {
                    MovingAutoFocusStrategy.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            MovingAutoFocusStrategy.this.mfocusUI.onFocusStarted();
                        }
                    }, 0);
                    MovingAutoFocusStrategy.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (MovingAutoFocusStrategy.this.mCameraController.getCameraState() == CameraState.IDLE && MovingAutoFocusStrategy.this.mCameraController.getCommandEvent() == CommandEvent.IDLE) {
                                MovingAutoFocusStrategy.this.mfocusUI.clearFocus();
                            }
                        }
                    }, 1000);
                }
                MovingAutoFocusStrategy.this.mPreviousMoving = z;
            }
        }
    }

    public void executeFocus(MotionEvent motionEvent) {
    }

    public MovingAutoFocusStrategy(Context context, PhotoModule photoModule, FocusUI focusUI, CameraControllerManager cameraControllerManager, Handler handler) {
        this.mCameraModule = photoModule;
        this.mfocusUI = focusUI;
        this.mContext = context;
        this.mCameraController = cameraControllerManager;
        this.mHandler = handler;
    }

    public void operateFocus() {
        if (!this.mCameraController.getPicTaked() && this.mCameraModule != null && this.mCameraController.getIsSupportContinuousFocus()) {
            Parameters currentParameters = this.mCameraController.getCurrentParameters();
            if (currentParameters != null) {
                try {
                    currentParameters.setFocusMode("continuous-picture");
                    this.mCameraController.trySetParameters(currentParameters);
                    this.mCameraModule.setAutoFocusMoveCallBack(new MyAutoFocusMoveCallback());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cancelFocus() {
        if (!this.mCameraController.getPicTaked() && this.mCameraModule != null) {
            this.mCameraModule.setAutoFocusMoveCallBack(null);
        }
    }

    public long getFocusEndTime() {
        return this.mFocusEndTime;
    }

    /* access modifiers changed from: private */
    public boolean isUsePicPreviewLayoutShow() {
        return ((RelativeLayout) ((Activity) this.mContext).findViewById(CameraActivity.RES_ID_USEPICTURE)).getVisibility() == 0;
    }
}
