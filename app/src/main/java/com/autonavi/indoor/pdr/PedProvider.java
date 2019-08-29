package com.autonavi.indoor.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import com.autonavi.indoor.entity.PressData;
import com.autonavi.indoor.provider.IProvider;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MessageHelper;
import java.lang.ref.WeakReference;

public class PedProvider extends IProvider implements SensorEventListener {
    private static final float GRAVITY_EARTH = 9.80665f;
    private static final long SENSOR_TIMESTAMP_ERROR_TIME = 4000;
    private static final long SENSOR_UPDATE_ERROR_TIME = 1000;
    private static volatile PedProvider instance;
    private final long INTERVAL_NORMAL = 195;
    private final long INTERVAL_UI = 55;
    private boolean enable_mat_filter_ = false;
    private float[] gra_datas_ = new float[3];
    private boolean mAccTimeStampUsed = true;
    private long mAccUpdateSysTime = -1;
    private boolean mAccUpdateWork = false;
    /* access modifiers changed from: private */
    public float mCurrentPressure;
    private boolean mEnablePDR = true;
    private long mGravitySensorTimestamp = -1;
    private boolean mGravityTimeStampUsed = true;
    private long mGravityUpdateSysTime = -1;
    private boolean mGravityUpdateWork = false;
    private long mGyroSensorTimestamp = -1;
    private boolean mGyroTimeStampUsed = false;
    private long mGyroUpdateSysTime = -1;
    private boolean mGyroUpdateWork = false;
    Handler mInnerHandler = null;
    private boolean mIsAccelerationAvailable = false;
    private boolean mIsGravityAvailable = false;
    private boolean mIsGyroAvailable = false;
    private boolean mIsMagneticAvailable = false;
    private boolean mIsPressureAvailable = false;
    private boolean mIsPublishOther = false;
    private long mLastAccTime = -1;
    private long mLastGraTime = -1;
    private long mLastGyrTime = -1;
    private long mLastMagTime = -1;
    MatStepData mLastMatStepRecord = null;
    private long mLastStepUpdateSysTime;
    private long mMagneticSensorTimestamp = -1;
    private boolean mMagneticTimeStampUsed = false;
    private long mMagneticUpdateSysTime = -1;
    private boolean mMagneticUpdateWork = false;
    private float mOrientation = 0.0f;
    private final int mPedReportInterval = 200;
    Handler mSensorHandler = null;
    SensorManager mSensorManager = null;
    private float[] mat_datas_ = new float[3];
    private float[] rotationMatrix = new float[16];
    private long start_timeInMillis = 0;

    static class InnerHandler extends Handler {
        private final WeakReference<PedProvider> mParent;

        public InnerHandler(PedProvider pedProvider) {
            this.mParent = new WeakReference<>(pedProvider);
        }

        public void handleMessage(Message message) {
            PedProvider pedProvider = (PedProvider) this.mParent.get();
            if (pedProvider != null) {
                try {
                    if (message.what == 1200) {
                        MessageHelper.publishMessage(pedProvider.mOutterHandlers, 100, (Object) new PressData(System.currentTimeMillis(), (double) pedProvider.mCurrentPressure));
                        if (pedProvider.mInnerHandler != null) {
                            pedProvider.mInnerHandler.sendEmptyMessageDelayed(1200, 100);
                        }
                    }
                } catch (Throwable th) {
                    if (L.isLogging) {
                        L.d(th);
                    }
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static PedProvider getInstance() {
        if (instance == null) {
            synchronized (PedProvider.class) {
                try {
                    if (instance == null) {
                        instance = new PedProvider();
                    }
                }
            }
        }
        return instance;
    }

    protected PedProvider() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        if (com.autonavi.indoor.util.L.isLogging != false) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0033, code lost:
        com.autonavi.indoor.util.L.d((java.lang.String) "Can't getSystemService of SENSOR_SERVICE, PED not work!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        if (com.autonavi.indoor.util.L.isLogging != false) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        com.autonavi.indoor.util.L.d(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003f, code lost:
        r2.mSensorManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0042, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0050, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:2:0x0003, B:14:0x0025] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int init(android.content.Context r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != 0) goto L_0x000d
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x000b }
            java.lang.String r0 = "PedProvider context can not be initialized with null"
            r3.<init>(r0)     // Catch:{ all -> 0x000b }
            throw r3     // Catch:{ all -> 0x000b }
        L_0x000b:
            r3 = move-exception
            goto L_0x004f
        L_0x000d:
            android.content.Context r0 = r2.mContext     // Catch:{ all -> 0x000b }
            if (r0 != 0) goto L_0x0043
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = "Initialize PedProvider with mContext"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x000b }
        L_0x001a:
            r2.mContext = r3     // Catch:{ all -> 0x000b }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x000b }
            r2.mLastStepUpdateSysTime = r0     // Catch:{ all -> 0x000b }
            java.lang.String r0 = "sensor"
            java.lang.Object r3 = r3.getSystemService(r0)     // Catch:{ Throwable -> 0x002e }
            android.hardware.SensorManager r3 = (android.hardware.SensorManager) r3     // Catch:{ Throwable -> 0x002e }
            r2.mSensorManager = r3     // Catch:{ Throwable -> 0x002e }
            goto L_0x004c
        L_0x002e:
            r3 = move-exception
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x0038
            java.lang.String r0 = "Can't getSystemService of SENSOR_SERVICE, PED not work!"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x000b }
        L_0x0038:
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r0 == 0) goto L_0x003f
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x000b }
        L_0x003f:
            r0 = 0
            r2.mSensorManager = r0     // Catch:{ all -> 0x000b }
            throw r3     // Catch:{ all -> 0x000b }
        L_0x0043:
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x000b }
            if (r3 == 0) goto L_0x004c
            java.lang.String r3 = "Try to initialize PedProvider which had already been initialized before. To re-init PedProvider with new mConfiguration call PedProvider.destroy() at first."
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x000b }
        L_0x004c:
            r3 = 0
            monitor-exit(r2)
            return r3
        L_0x004f:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.pdr.PedProvider.init(android.content.Context):int");
    }

    public Handler getSensorHandler() {
        return this.mSensorHandler;
    }

    public void setSensorHandler(Handler handler) {
        this.mSensorHandler = handler;
    }

    public void setPublishOther(boolean z) {
        this.mIsPublishOther = z;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent sensorEvent2 = sensorEvent;
        if (this.mIsListening) {
            long j = sensorEvent2.timestamp / 1000000;
            long currentTimeMillis = System.currentTimeMillis();
            int type = sensorEvent2.sensor.getType();
            if (type != 6) {
                if (type != 9) {
                    switch (type) {
                        case 1:
                            this.mAccUpdateSysTime = currentTimeMillis;
                            JNIWrapper.jniUpdateAcceleration(j, sensorEvent2.values[0] - (this.rotationMatrix[8] * GRAVITY_EARTH), sensorEvent2.values[1] - (this.rotationMatrix[9] * GRAVITY_EARTH), sensorEvent2.values[2] - (this.rotationMatrix[10] * GRAVITY_EARTH), sensorEvent2.values[0], sensorEvent2.values[1], sensorEvent2.values[2]);
                            updateStep(currentTimeMillis);
                            checkError(currentTimeMillis);
                            if (!(this.mGravityUpdateWork && this.mIsGravityAvailable)) {
                                this.mGravityTimeStampUsed = Math.abs(currentTimeMillis - j) <= SENSOR_TIMESTAMP_ERROR_TIME;
                                if (!this.mGyroTimeStampUsed || !this.mMagneticTimeStampUsed || !this.mGravityTimeStampUsed) {
                                    j = currentTimeMillis;
                                }
                                if (j <= this.mLastAccTime || j - this.mLastAccTime >= 55) {
                                    this.mLastAccTime = j;
                                    JNIWrapper.jniUpdateGravity(j, sensorEvent2.values[0], sensorEvent2.values[1], sensorEvent2.values[2]);
                                    this.gra_datas_[0] = sensorEvent2.values[0];
                                    this.gra_datas_[1] = sensorEvent2.values[1];
                                    this.gra_datas_[2] = sensorEvent2.values[2];
                                    return;
                                }
                            }
                            break;
                        case 2:
                            this.mMagneticSensorTimestamp = j;
                            this.mMagneticUpdateSysTime = currentTimeMillis;
                            this.mMagneticTimeStampUsed = Math.abs(this.mMagneticSensorTimestamp - this.mGyroSensorTimestamp) <= SENSOR_TIMESTAMP_ERROR_TIME;
                            if (!this.mGyroTimeStampUsed || !this.mMagneticTimeStampUsed || !this.mGravityTimeStampUsed) {
                                j = currentTimeMillis;
                            }
                            if (j <= this.mLastMagTime || j - this.mLastMagTime >= 195) {
                                this.mLastMagTime = j;
                                checkError(currentTimeMillis);
                                if (this.mGyroUpdateWork && this.mIsGyroAvailable) {
                                    JNIWrapper.jniUpdateMagnetic(j, sensorEvent2.values[0], sensorEvent2.values[1], sensorEvent2.values[2]);
                                }
                                this.mat_datas_[0] = sensorEvent2.values[0];
                                this.mat_datas_[1] = sensorEvent2.values[1];
                                this.mat_datas_[2] = sensorEvent2.values[2];
                                return;
                            }
                        case 3:
                            this.mOrientation = sensorEvent2.values[0];
                            break;
                        case 4:
                            this.mGyroSensorTimestamp = j;
                            this.mGyroUpdateSysTime = currentTimeMillis;
                            this.mGyroTimeStampUsed = Math.abs(this.mMagneticSensorTimestamp - this.mGyroSensorTimestamp) <= SENSOR_TIMESTAMP_ERROR_TIME;
                            if (!this.mGyroTimeStampUsed || !this.mMagneticTimeStampUsed || !this.mGravityTimeStampUsed) {
                                j = currentTimeMillis;
                            }
                            if (j <= this.mLastGyrTime || j - this.mLastGyrTime >= 55) {
                                this.mLastGyrTime = j;
                                checkError(currentTimeMillis);
                                if (this.mMagneticUpdateWork && this.mIsMagneticAvailable) {
                                    JNIWrapper.jniUpdateGyro(j, sensorEvent2.values[0], sensorEvent2.values[1], sensorEvent2.values[2]);
                                    return;
                                }
                            }
                    }
                } else {
                    this.mGravitySensorTimestamp = j;
                    this.mGravityUpdateSysTime = currentTimeMillis;
                    this.mGravityTimeStampUsed = Math.abs(this.mGravitySensorTimestamp - this.mGyroSensorTimestamp) <= SENSOR_TIMESTAMP_ERROR_TIME;
                    if (!this.mGyroTimeStampUsed || !this.mMagneticTimeStampUsed || !this.mGravityTimeStampUsed) {
                        j = currentTimeMillis;
                    }
                    if (j <= this.mLastGraTime || j - this.mLastGraTime >= 195) {
                        this.mLastGraTime = j;
                        checkError(currentTimeMillis);
                        this.gra_datas_[0] = sensorEvent2.values[0];
                        this.gra_datas_[1] = sensorEvent2.values[1];
                        this.gra_datas_[2] = sensorEvent2.values[2];
                        if (this.mGyroUpdateWork && this.mIsGyroAvailable && this.mMagneticUpdateWork && this.mIsMagneticAvailable) {
                            JNIWrapper.jniUpdateGravity(j, sensorEvent2.values[0], sensorEvent2.values[1], sensorEvent2.values[2]);
                            return;
                        }
                    }
                }
                return;
            }
            updatePressure(sensorEvent);
        }
    }

    public float getOrientation() {
        return this.mOrientation;
    }

    public int getSensorType() {
        checkConfiguration();
        int i = 0;
        if (!this.mEnablePDR) {
            return 0;
        }
        if (this.mSensorManager.getDefaultSensor(1) != null) {
            this.mIsAccelerationAvailable = true;
        }
        if (this.mSensorManager.getDefaultSensor(2) != null) {
            this.mIsMagneticAvailable = true;
        }
        if (this.mIsAccelerationAvailable && this.mIsMagneticAvailable) {
            if (this.mSensorManager.getDefaultSensor(4) != null) {
                this.enable_mat_filter_ = true;
                this.mIsGyroAvailable = true;
                i = 2;
            } else {
                i = 1;
            }
        }
        if (L.isLogging) {
            L.d("SensorTypep[0(None),2(All)]:".concat(String.valueOf(i)));
        }
        return i;
    }

    public String getDebugMessage() {
        return JNIWrapper.jniLocGetDebugString();
    }

    public boolean isPressureAvailable() {
        return this.mIsPressureAvailable;
    }

    public float getLastPressure() {
        return this.mCurrentPressure;
    }

    public boolean isPdrEnabled() {
        return this.mEnablePDR;
    }

    public void enablePdr(boolean z) {
        if (L.isLogging) {
            L.d("enablePdr:".concat(String.valueOf(z)));
        }
        this.mEnablePDR = z;
    }

    public long getErrorCode() {
        long j = !this.mIsAccelerationAvailable ? 1 : 0;
        if (!this.mIsGravityAvailable) {
            j |= 2;
        }
        if (!this.mIsGyroAvailable) {
            j |= 4;
        }
        if (!this.mIsMagneticAvailable) {
            j |= 8;
        }
        if (!this.mIsPressureAvailable) {
            j |= 16;
        }
        if (System.currentTimeMillis() - this.start_timeInMillis <= 2000) {
            return j;
        }
        if (!this.mAccUpdateWork) {
            j |= 32;
        }
        if (!this.mGravityUpdateWork) {
            j |= 64;
        }
        if (!this.mGyroUpdateWork) {
            j |= 128;
        }
        if (!this.mMagneticUpdateWork) {
            j |= 256;
        }
        if (!this.mAccTimeStampUsed) {
            j |= 1024;
        }
        if (!this.mGravityTimeStampUsed) {
            j |= 2048;
        }
        if (!this.mGyroTimeStampUsed) {
            j |= 4096;
        }
        return !this.mMagneticTimeStampUsed ? j | 8192 : j;
    }

    private void checkError(long j) {
        boolean z = false;
        this.mAccUpdateWork = j - this.mAccUpdateSysTime <= 1000;
        this.mGyroUpdateWork = j - this.mGyroUpdateSysTime <= 1000;
        this.mGravityUpdateWork = j - this.mGravityUpdateSysTime <= 1000;
        if (j - this.mMagneticUpdateSysTime <= 1000) {
            z = true;
        }
        this.mMagneticUpdateWork = z;
    }

    /* JADX INFO: finally extract failed */
    private void updateStep(long j) {
        long j2 = j;
        if (this.mEnablePDR && j2 - this.mLastStepUpdateSysTime >= 200) {
            this.mLastStepUpdateSysTime = j2;
            JNIWrapper.jniGetStepData();
            float jniLocGetFilterSquareAngle = JNIWrapper.jniLocGetFilterSquareAngle();
            if (jniLocGetFilterSquareAngle == 3600.0f) {
                float[] fArr = new float[3];
                SensorManager.getRotationMatrix(this.rotationMatrix, null, this.gra_datas_, this.mat_datas_);
                SensorManager.getOrientation(this.rotationMatrix, fArr);
                jniLocGetFilterSquareAngle = (float) (((double) (fArr[0] * 180.0f)) / 3.141592653589793d);
            }
            if (!this.mIsGyroAvailable) {
                jniLocGetFilterSquareAngle = this.mOrientation;
            }
            synchronized (this) {
                try {
                    JNIWrapper.jniGetMag8Param10Cali(this.mat_datas_[0], this.mat_datas_[1], this.mat_datas_[2]);
                    JNIWrapper.jniLocGetDirectionState();
                    if (this.mIsPublishOther) {
                        JniMagCaliResult jniMagCaliResult = new JniMagCaliResult((double) this.mat_datas_[0], (double) this.mat_datas_[1], (double) this.mat_datas_[2]);
                        MessageHelper.publishMessage(this.mOutterHandlers, 105, (Object) jniMagCaliResult);
                    }
                    if (this.mLastMatStepRecord == null) {
                        MatStepData matStepData = new MatStepData(JNIWrapper.mStepNum, JNIWrapper.mAngleYaw, JNIWrapper.mMagCaliResultX, JNIWrapper.mMagCaliResultY, JNIWrapper.mMagCaliResultZ, JNIWrapper.mQ1, JNIWrapper.mQ2, JNIWrapper.mQ3, JNIWrapper.mQ4, JNIWrapper.mDeltaX, JNIWrapper.mDeltaY, JNIWrapper.mDeltaZ, JNIWrapper.mStepLength, JNIWrapper.mFrequency, JNIWrapper.mV, JNIWrapper.mMoveDirection, JNIWrapper.mAngleNoMag, JNIWrapper.mMoveStateScore);
                        this.mLastMatStepRecord = matStepData;
                        this.mLastMatStepRecord.timestamp_ = j2;
                        this.mLastMatStepRecord.showangle = (double) jniLocGetFilterSquareAngle;
                        this.mLastMatStepRecord.pressure = (double) this.mCurrentPressure;
                    } else if (this.mLastMatStepRecord.step_ == JNIWrapper.mStepNum) {
                        if (j2 - this.mLastMatStepRecord.timestamp_ < 2000) {
                            this.mLastMatStepRecord.AddMatData(JNIWrapper.mMagCaliResultX, JNIWrapper.mMagCaliResultY, JNIWrapper.mMagCaliResultZ, JNIWrapper.mQ1, JNIWrapper.mQ2, JNIWrapper.mQ3, JNIWrapper.mQ4, JNIWrapper.mDeltaX, JNIWrapper.mDeltaY, JNIWrapper.mDeltaZ);
                        } else {
                            this.mLastMatStepRecord.showangle = (double) jniLocGetFilterSquareAngle;
                            this.mLastMatStepRecord.pressure = (double) this.mCurrentPressure;
                            MessageHelper.publishMessage(this.mOutterHandlers, 111, (Object) this.mLastMatStepRecord);
                            this.mLastMatStepRecord.update(JNIWrapper.mStepNum, JNIWrapper.mAngleYaw, JNIWrapper.mMagCaliResultX, JNIWrapper.mMagCaliResultY, JNIWrapper.mMagCaliResultZ, JNIWrapper.mQ1, JNIWrapper.mQ2, JNIWrapper.mQ3, JNIWrapper.mQ4, JNIWrapper.mDeltaX, JNIWrapper.mDeltaY, JNIWrapper.mDeltaZ, JNIWrapper.mStepLength, JNIWrapper.mFrequency, JNIWrapper.mV, JNIWrapper.mMoveDirection, JNIWrapper.mAngleNoMag, JNIWrapper.mMoveStateScore);
                            this.mLastMatStepRecord.timestamp_ = j2;
                        }
                    } else if (this.mLastMatStepRecord.step_ != JNIWrapper.mStepNum) {
                        this.mLastMatStepRecord.showangle = (double) jniLocGetFilterSquareAngle;
                        this.mLastMatStepRecord.pressure = (double) this.mCurrentPressure;
                        MessageHelper.publishMessage(this.mOutterHandlers, 111, (Object) this.mLastMatStepRecord);
                        this.mLastMatStepRecord.update(JNIWrapper.mStepNum, JNIWrapper.mAngleYaw, JNIWrapper.mMagCaliResultX, JNIWrapper.mMagCaliResultY, JNIWrapper.mMagCaliResultZ, JNIWrapper.mQ1, JNIWrapper.mQ2, JNIWrapper.mQ3, JNIWrapper.mQ4, JNIWrapper.mDeltaX, JNIWrapper.mDeltaY, JNIWrapper.mDeltaZ, JNIWrapper.mStepLength, JNIWrapper.mFrequency, JNIWrapper.mV, JNIWrapper.mMoveDirection, JNIWrapper.mAngleNoMag, JNIWrapper.mMoveStateScore);
                        this.mLastMatStepRecord.timestamp_ = j2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void updatePressure(SensorEvent sensorEvent) {
        if (sensorEvent != null) {
            this.mCurrentPressure = sensorEvent.values[0];
            if (!this.mEnablePDR && this.mInnerHandler == null) {
                this.mInnerHandler = new InnerHandler(this);
                this.mInnerHandler.sendEmptyMessage(1200);
            }
        }
    }

    public int start() {
        if (this.mIsListening) {
            return 0;
        }
        if (this.mSensorManager == null) {
            if (L.isLogging) {
                L.d((String) "Can't getSystemService of SENSOR_SERVICE, PED not work!");
            }
            return 0;
        }
        this.mLastMatStepRecord = null;
        JNIWrapper.jniPDRStart("");
        this.start_timeInMillis = System.currentTimeMillis();
        if (getSensorType() != 0) {
            Sensor defaultSensor = this.mSensorManager.getDefaultSensor(1);
            if (defaultSensor != null) {
                this.mIsAccelerationAvailable = true;
                this.mSensorManager.registerListener(this, defaultSensor, 2, this.mSensorHandler);
            }
            Sensor defaultSensor2 = this.mSensorManager.getDefaultSensor(2);
            if (defaultSensor2 != null) {
                this.mIsMagneticAvailable = true;
                this.mSensorManager.registerListener(this, defaultSensor2, 3, this.mSensorHandler);
            }
            Sensor defaultSensor3 = this.mSensorManager.getDefaultSensor(9);
            if (defaultSensor3 != null) {
                this.mIsGravityAvailable = true;
                this.mSensorManager.registerListener(this, defaultSensor3, 3, this.mSensorHandler);
            }
            Sensor defaultSensor4 = this.mSensorManager.getDefaultSensor(4);
            if (defaultSensor4 != null) {
                this.mIsGyroAvailable = true;
                this.mSensorManager.registerListener(this, defaultSensor4, 2, this.mSensorHandler);
            }
        } else if (L.isLogging) {
            L.d((String) "getSensorType() == 0");
        }
        if (this.mSensorManager.getDefaultSensor(6) != null) {
            this.mIsPressureAvailable = true;
            this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(6), 3, this.mSensorHandler);
        }
        if (getSensorType() == 0 || !this.mIsGyroAvailable) {
            this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(3), 3, this.mSensorHandler);
        }
        this.mIsListening = true;
        return 0;
    }

    public boolean stop() {
        if (!this.mIsListening) {
            return true;
        }
        JNIWrapper.jniPDRStop();
        this.mSensorManager.unregisterListener(this);
        this.mIsListening = false;
        this.mSensorHandler = null;
        if (L.isLogging) {
            L.d((String) "Stop Ped scan!");
        }
        return true;
    }
}
