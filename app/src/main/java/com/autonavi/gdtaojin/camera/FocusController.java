package com.autonavi.gdtaojin.camera;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;

public class FocusController {
    public static final int AUTO_FOCUS = 3;
    public static final int MOVING_AUTO_FOCUS = 1;
    public static final int SENSOR_AUTO_FOCUS = 2;
    private CameraControllerManager mCameraController;
    private PhotoModule mCameraModule;
    private Context mContext;
    private IFocusStrategy mFocusStrategy;
    private FocusUI mFocusUI;
    private Handler mHandler;

    public FocusController(Context context, PhotoModule photoModule, FocusUI focusUI, CameraControllerManager cameraControllerManager, Handler handler) {
        this.mContext = context;
        this.mFocusUI = focusUI;
        this.mCameraModule = photoModule;
        this.mCameraController = cameraControllerManager;
        this.mHandler = handler;
    }

    public IFocusStrategy getFocusStrategy(int i) {
        if (this.mFocusStrategy != null) {
            this.mFocusStrategy.cancelFocus();
        }
        if (i == 1) {
            MovingAutoFocusStrategy movingAutoFocusStrategy = new MovingAutoFocusStrategy(this.mContext, this.mCameraModule, this.mFocusUI, this.mCameraController, this.mHandler);
            this.mFocusStrategy = movingAutoFocusStrategy;
        } else if (i == 3) {
            this.mFocusStrategy = new ManualAutoFocusStrategy(this.mCameraModule, this.mFocusUI, this.mCameraController, this.mHandler);
        }
        return this.mFocusStrategy;
    }

    public void operateFocus() {
        if (this.mFocusStrategy != null) {
            this.mFocusStrategy.operateFocus();
        }
    }

    public void executeFocus(MotionEvent motionEvent) {
        if (this.mFocusStrategy != null) {
            this.mFocusStrategy.executeFocus(motionEvent);
        }
    }

    public void cancelFocus() {
        if (this.mFocusStrategy != null) {
            this.mFocusStrategy.cancelFocus();
        }
    }

    public long getFocusEndTime() {
        if (this.mFocusStrategy != null) {
            return this.mFocusStrategy.getFocusEndTime();
        }
        return 1100;
    }
}
