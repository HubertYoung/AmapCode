package com.ant.phone.imu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.ant.phone.imu.math.OrientationEKF;
import com.ant.phone.imu.math.Vector3d;
import com.ant.phone.imu.sensor.Clock;
import com.ant.phone.imu.sensor.DeviceSensorLooper;
import com.ant.phone.imu.sensor.HeadTransform;
import com.ant.phone.imu.sensor.SensorEventProvider;
import com.ant.phone.imu.sensor.SystemClock;
import java.util.concurrent.TimeUnit;

public class VerticalTracker extends RotationTracker implements SensorEventListener {
    private static final float DEFAULT_NECK_HORIZONTAL_OFFSET = 0.08f;
    private static final boolean DEFAULT_NECK_MODEL_ENABLED = false;
    private static final float DEFAULT_NECK_VERTICAL_OFFSET = 0.075f;
    private Clock mClock;
    private final Display mDisplay;
    private float mDisplayRotation = Float.NaN;
    private final float[] mEkfToHeadTracker = new float[16];
    private final Vector3d mGyroBias = new Vector3d();
    private HeadTransform mHeadTransform = new HeadTransform();
    private final Vector3d mLatestAcc = new Vector3d();
    private final Vector3d mLatestGyro = new Vector3d();
    private long mLatestGyroEventClockTimeNs;
    private boolean mNeckModelEnabled = false;
    private final float[] mNeckModelTranslation = new float[16];
    private SensorEventProvider mSensorEventProvider;
    private final float[] mSensorToDisplay = new float[16];
    private final float[] mTmpHeadView = new float[16];
    private final float[] mTmpHeadView2 = new float[16];
    private OrientationEKF mTracker;
    private volatile boolean mTracking;
    private float[] mat = new float[16];

    public static VerticalTracker createFromContext(Context context) {
        return new VerticalTracker(new DeviceSensorLooper((SensorManager) context.getSystemService("sensor")), new SystemClock(), ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay());
    }

    public VerticalTracker(SensorEventProvider sensorEventProvider, Clock clock, Display display) {
        this.mClock = clock;
        this.mSensorEventProvider = sensorEventProvider;
        this.mTracker = new OrientationEKF();
        this.mDisplay = display;
        Matrix.setIdentityM(this.mNeckModelTranslation, 0);
        Matrix.translateM(this.mNeckModelTranslation, 0, 0.0f, -0.075f, DEFAULT_NECK_HORIZONTAL_OFFSET);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            this.mLatestAcc.a((double) event.values[0], (double) event.values[1], (double) event.values[2]);
            OrientationEKF orientationEKF = this.mTracker;
            Vector3d vector3d = this.mLatestAcc;
            long j = event.timestamp;
            orientationEKF.a(vector3d);
        } else if (event.sensor.getType() == 4) {
            this.mLatestGyroEventClockTimeNs = this.mClock.a();
            this.mLatestGyro.a((double) event.values[0], (double) event.values[1], (double) event.values[2]);
            Vector3d.a(this.mLatestGyro, this.mGyroBias, this.mLatestGyro);
            this.mTracker.a(this.mLatestGyro, event.timestamp);
        }
        if (this.listener != null) {
            float[] matrix = getLastMatrix();
            if (matrix != null) {
                synchronized (this) {
                    if (this.listener != null) {
                        this.listener.a(matrix);
                    }
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startTracking() {
        if (!this.mTracking) {
            this.mTracker.a();
            this.mSensorEventProvider.a(this);
            this.mSensorEventProvider.a();
            this.mTracking = true;
        }
    }

    public void stopTracking() {
        if (this.mTracking) {
            this.mSensorEventProvider.b(this);
            this.mSensorEventProvider.b();
            this.mTracking = false;
        }
    }

    public void setGyroBias(float[] gyroBias) {
        if (gyroBias == null) {
            this.mGyroBias.a();
        } else if (gyroBias.length != 3) {
            throw new IllegalArgumentException("Gyro bias should be an array of 3 values");
        } else {
            this.mGyroBias.a((double) gyroBias[0], (double) gyroBias[1], (double) gyroBias[2]);
        }
    }

    public void getLastHeadView(float[] headView, int offset) {
        float rotation;
        if (offset + 16 > headView.length) {
            throw new IllegalArgumentException("Not enough space to write the result");
        }
        switch (this.mDisplay.getRotation()) {
            case 0:
                rotation = 0.0f;
                break;
            case 1:
                rotation = 90.0f;
                break;
            case 2:
                rotation = 180.0f;
                break;
            case 3:
                rotation = 270.0f;
                break;
            default:
                rotation = 0.0f;
                break;
        }
        if (rotation != this.mDisplayRotation) {
            this.mDisplayRotation = rotation;
            Matrix.setRotateEulerM(this.mSensorToDisplay, 0, 0.0f, 0.0f, -rotation);
            Matrix.setRotateEulerM(this.mEkfToHeadTracker, 0, 0.0f, 0.0f, rotation);
        }
        synchronized (this.mTracker) {
            double[] mat2 = this.mTracker.a(((double) TimeUnit.NANOSECONDS.toSeconds(this.mClock.a() - this.mLatestGyroEventClockTimeNs)) + 0.03333333333333333d);
            for (int i = 0; i < headView.length; i++) {
                this.mTmpHeadView[i] = (float) mat2[i];
            }
        }
        Matrix.multiplyMM(this.mTmpHeadView2, 0, this.mSensorToDisplay, 0, this.mTmpHeadView, 0);
        Matrix.multiplyMM(headView, offset, this.mTmpHeadView2, 0, this.mEkfToHeadTracker, 0);
        if (this.mNeckModelEnabled) {
            Matrix.multiplyMM(this.mTmpHeadView, 0, this.mNeckModelTranslation, 0, headView, offset);
            Matrix.translateM(headView, offset, this.mTmpHeadView, 0, 0.0f, DEFAULT_NECK_VERTICAL_OFFSET, 0.0f);
        }
    }

    public void getEulerAngles(float[] eulerAngles) {
        getLastHeadView(this.mHeadTransform.a(), 0);
        this.mHeadTransform.a(eulerAngles);
    }

    public float[] getLastMatrix() {
        getLastHeadView(this.mat, 0);
        return this.mat;
    }

    public void getMatrix(float[] mat2) {
        HeadTransform headTransform = new HeadTransform();
        getLastHeadView(headTransform.a(), 0);
        System.arraycopy(headTransform.a(), 0, mat2, 0, mat2.length);
    }
}
