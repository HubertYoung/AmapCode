package com.autonavi.gdtaojin.camera;

import android.app.Activity;
import android.os.Handler;
import android.view.SurfaceHolder;
import com.autonavi.gdtaojin.camera.orientation.CPMyOrientationSensorManager;
import com.autonavi.gdtaojin.camera.orientation.CPMyOrientationSensorManager.DirectionSensorListener;

public abstract class AbstractCameraControllerManager implements DirectionSensorListener {
    public static final long GXDTAOJIN_LOCATION_VALID_TIME = 500;
    public static final String TAG = "gxd_camera";
    public Activity mContext = null;
    public long mDerectionTime;
    public Handler mHandler = null;
    public boolean mIsNeedTracePoint = false;
    public Double mLat;
    public Double mLng;
    public SurfaceHolder mSurfaceHolder;
    public Double mTelAddrLat;
    public Double mTelAddrLng;
    public float mXCaptureDirection;
    public float mXDirection;
    public float mYCaptureDirection;
    public float mYDirection;
    public float mZCaptureDirection;
    public float mZDirection;
    private CPMyOrientationSensorManager myOrientationSMS;

    public AbstractCameraControllerManager(Activity activity, Handler handler, SurfaceHolder surfaceHolder) {
        this.mContext = activity;
        this.mHandler = handler;
        this.mSurfaceHolder = surfaceHolder;
    }

    public void RegisterSensor() {
        this.myOrientationSMS = CPMyOrientationSensorManager.getInstance(this.mContext);
        this.myOrientationSMS.setDirValueListener(this);
        this.myOrientationSMS.registerSensors();
    }

    public void unRegisterSensor() {
        if (this.myOrientationSMS != null) {
            this.myOrientationSMS.unRegisterSensors();
            this.myOrientationSMS.setDirValueListener(null);
        }
    }

    public void onDirValueChanged(float f, float f2, float f3) {
        this.mXDirection = f;
        this.mYDirection = f2;
        this.mZDirection = f3;
        this.mDerectionTime = System.currentTimeMillis();
    }
}
