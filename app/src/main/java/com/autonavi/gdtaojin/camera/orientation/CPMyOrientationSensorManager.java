package com.autonavi.gdtaojin.camera.orientation;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextUtils;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;

public class CPMyOrientationSensorManager {
    private static final int AVERAGE_LIMIT_DEGREE = 45;
    private static final int G_LIST_SIZE = 5;
    private static final int MA_LIST_SIZE = 10;
    private static CPMyOrientationSensorManager mMySensorManager;
    private Sensor aMfSensor;
    /* access modifiers changed from: private */
    public boolean isHasAcSensor;
    /* access modifiers changed from: private */
    public boolean isHasGyroSensor;
    /* access modifiers changed from: private */
    public boolean isHasMfSensor;
    private Sensor mAcSensor;
    /* access modifiers changed from: private */
    public SensorData mAccelerData = new SensorData();
    private Context mContext;
    private float[] mDirValue = new float[3];
    private float[] mDirValues = new float[3];
    private float mDir_temp = -1.0f;
    private ArrayList<Integer> mGaccurate = new ArrayList<>();
    private ArrayList<Float> mGx = new ArrayList<>();
    /* access modifiers changed from: private */
    public SensorData mGyroData = new SensorData();
    private Sensor mGyroSensor;
    private float[] mGyroValue = new float[3];
    private float mGyro_temp = -1.0f;
    private boolean mIsLeftCompensate;
    private boolean mIsRightCompensate;
    private int mLeftY;
    private ArrayList<Integer> mMAaccurate = new ArrayList<>();
    private ArrayList<Float> mMAx = new ArrayList<>();
    /* access modifiers changed from: private */
    public SensorData mMagneticData = new SensorData();
    private Resources mResource;
    private int mRightY;
    private SensorManager mSensorManager;
    /* access modifiers changed from: private */
    public DirectionSensorListener myDirValueListener = null;
    final SensorEventListener mySensorListener;

    public interface DirectionSensorListener {
        void onDirValueChanged(float f, float f2, float f3);
    }

    class SensorData {
        public int accuracy;
        public long mTime;
        public float[] mValues;

        SensorData() {
        }

        public void clear() {
            this.mValues = null;
        }
    }

    private CPMyOrientationSensorManager(Context context) {
        boolean z = true;
        this.isHasGyroSensor = true;
        this.isHasMfSensor = true;
        this.isHasAcSensor = true;
        this.mySensorListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == 3) {
                    if (CPMyOrientationSensorManager.this.isHasGyroSensor) {
                        CPMyOrientationSensorManager.this.mGyroData.accuracy = sensorEvent.accuracy;
                        CPMyOrientationSensorManager.this.mGyroData.mTime = sensorEvent.timestamp;
                        CPMyOrientationSensorManager.this.mGyroData.mValues = sensorEvent.values;
                        CPMyOrientationSensorManager.this.calculateGyroSensorValue(CPMyOrientationSensorManager.this.mGyroData);
                    }
                } else if (CPMyOrientationSensorManager.this.isHasMfSensor && CPMyOrientationSensorManager.this.isHasAcSensor) {
                    if (sensorEvent.sensor.getType() == 2) {
                        CPMyOrientationSensorManager.this.mMagneticData.accuracy = sensorEvent.accuracy;
                        CPMyOrientationSensorManager.this.mMagneticData.mTime = sensorEvent.timestamp;
                        CPMyOrientationSensorManager.this.mMagneticData.mValues = sensorEvent.values;
                    } else if (sensorEvent.sensor.getType() == 1) {
                        CPMyOrientationSensorManager.this.mAccelerData.accuracy = sensorEvent.accuracy;
                        CPMyOrientationSensorManager.this.mAccelerData.mTime = sensorEvent.timestamp;
                        CPMyOrientationSensorManager.this.mAccelerData.mValues = sensorEvent.values;
                    }
                    if (!(CPMyOrientationSensorManager.this.mMagneticData.mValues == null || CPMyOrientationSensorManager.this.mAccelerData.mValues == null)) {
                        CPMyOrientationSensorManager.this.calculateDirSensorValue(CPMyOrientationSensorManager.this.mMagneticData, CPMyOrientationSensorManager.this.mAccelerData);
                    }
                }
                if (CPMyOrientationSensorManager.this.myDirValueListener != null) {
                    float[] directionDegree = CPMyOrientationSensorManager.this.getDirectionDegree();
                    CPMyOrientationSensorManager.this.myDirValueListener.onDirValueChanged(directionDegree[0], directionDegree[1], directionDegree[2]);
                }
            }
        };
        this.mContext = context;
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.mResource = context.getResources();
        initCompensate();
        if (this.mSensorManager != null) {
            this.mGyroSensor = this.mSensorManager.getDefaultSensor(3);
            this.aMfSensor = this.mSensorManager.getDefaultSensor(2);
            this.mAcSensor = this.mSensorManager.getDefaultSensor(1);
            this.isHasGyroSensor = this.mGyroSensor != null;
            this.isHasMfSensor = this.aMfSensor != null;
            this.isHasAcSensor = this.mAcSensor == null ? false : z;
            for (int i = 0; i < this.mGyroValue.length; i++) {
                this.mGyroValue[i] = -1.0f;
                this.mDirValue[i] = -1.0f;
            }
        }
    }

    public static synchronized CPMyOrientationSensorManager getInstance(Context context) {
        CPMyOrientationSensorManager cPMyOrientationSensorManager;
        synchronized (CPMyOrientationSensorManager.class) {
            try {
                if (mMySensorManager == null) {
                    mMySensorManager = new CPMyOrientationSensorManager(context);
                }
                cPMyOrientationSensorManager = mMySensorManager;
            }
        }
        return cPMyOrientationSensorManager;
    }

    /* access modifiers changed from: private */
    public void calculateGyroSensorValue(SensorData sensorData) {
        float f = sensorData.mValues[0];
        float f2 = sensorData.mValues[1];
        float f3 = sensorData.mValues[2];
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f) {
            if (f2 > -135.0f && f2 < -45.0f && f3 > -45.0f && f3 < 45.0f) {
                f = (f + 720.0f) % 360.0f;
            } else if (f3 > 45.0f && f3 < 90.0f) {
                f = (!this.mIsLeftCompensate || Math.abs(f2) <= ((float) this.mLeftY)) ? ((f + 90.0f) + 720.0f) % 360.0f : (((f + Math.abs(f2)) + 90.0f) + 720.0f) % 360.0f;
            } else if (f3 < -45.0f && f3 > -90.0f) {
                f = (!this.mIsRightCompensate || Math.abs(f2) <= ((float) this.mRightY)) ? ((f - 90.0f) + 720.0f) % 360.0f : (((f + Math.abs(f2)) - 90.0f) + 720.0f) % 360.0f;
            }
            if (this.mGx.size() < 5) {
                if (this.mGyro_temp == -1.0f) {
                    this.mGyro_temp = f;
                }
                if (this.mGyro_temp - f > 330.0f) {
                    f += 360.0f;
                } else if (this.mGyro_temp - f < -330.0f) {
                    f -= 360.0f;
                }
                this.mGx.add(Float.valueOf(f));
                this.mGaccurate.add(Integer.valueOf(sensorData.accuracy));
                return;
            }
            int i = 0;
            for (int i2 = 0; i2 < 5; i2++) {
                i += this.mGaccurate.get(i2).intValue();
            }
            float f4 = ((float) i) / 5.0f;
            int i3 = 0;
            float f5 = 0.0f;
            for (int i4 = 0; i4 < 5; i4++) {
                if (f4 <= ((float) this.mGaccurate.get(i4).intValue())) {
                    i3++;
                    f5 += this.mGx.get(i4).floatValue();
                }
            }
            if (i3 > 0) {
                this.mGyroValue[0] = f5 / ((float) i3);
                this.mGyroValue[0] = (this.mGyroValue[0] + 720.0f) % 360.0f;
            }
            this.mGyro_temp = -1.0f;
            this.mGx.clear();
            this.mGaccurate.clear();
            this.mGyroValue[1] = (f2 + 720.0f) % 360.0f;
            this.mGyroValue[2] = (f3 + 720.0f) % 360.0f;
        }
    }

    /* access modifiers changed from: private */
    public void calculateDirSensorValue(SensorData sensorData, SensorData sensorData2) {
        float[] fArr = new float[9];
        SensorManager.getRotationMatrix(fArr, null, sensorData2.mValues, sensorData.mValues);
        SensorManager.getOrientation(fArr, this.mDirValues);
        float degrees = (float) Math.toDegrees((double) this.mDirValues[0]);
        float degrees2 = (float) Math.toDegrees((double) this.mDirValues[1]);
        float degrees3 = (float) Math.toDegrees((double) this.mDirValues[2]);
        if (degrees != 0.0f || degrees2 != 0.0f || degrees3 != 0.0f) {
            float f = (degrees2 <= -135.0f || degrees2 >= -45.0f) ? (degrees3 <= 0.0f || degrees3 >= 90.0f) ? (degrees3 <= 90.0f || degrees3 >= 180.0f) ? (degrees3 >= 0.0f || degrees3 <= -90.0f) ? (degrees3 >= -90.0f || degrees3 <= -180.0f) ? (degrees + 720.0f) % 360.0f : ((degrees + 90.0f) + 720.0f) % 360.0f : ((degrees + 90.0f) + 720.0f) % 360.0f : (((degrees - 90.0f) + (degrees3 / 2.0f)) + 720.0f) % 360.0f : (((degrees - 90.0f) + (degrees3 / 2.0f)) + 720.0f) % 360.0f : Math.abs(degrees3) > 135.0f ? (((degrees + (Math.abs(degrees2) / 2.0f)) + 180.0f) + 720.0f) % 360.0f : ((degrees + (Math.abs(degrees2) / 2.0f)) + 720.0f) % 360.0f;
            if (this.mMAx.size() < 10) {
                if (this.mDir_temp == -1.0f) {
                    this.mDir_temp = f;
                }
                if (this.mDir_temp - f > 330.0f) {
                    f += 360.0f;
                } else if (this.mDir_temp - f < -330.0f) {
                    f -= 360.0f;
                }
                this.mMAx.add(Float.valueOf(f));
                this.mMAaccurate.add(Integer.valueOf(sensorData.accuracy));
                return;
            }
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < 10; i3++) {
                i = (int) (((float) i) + this.mMAx.get(i3).floatValue());
                i2 += this.mMAaccurate.get(i3).intValue();
            }
            int i4 = i / 10;
            float f2 = ((float) i2) / 10.0f;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < 10; i7++) {
                if (Math.abs(this.mMAx.get(i7).floatValue() - ((float) i4)) < 45.0f && f2 <= ((float) this.mMAaccurate.get(i7).intValue())) {
                    i6 = (int) (((float) i6) + this.mMAx.get(i7).floatValue());
                    i5++;
                }
            }
            this.mMAx.clear();
            this.mMAaccurate.clear();
            if (i5 > 0) {
                this.mDirValue[0] = (float) (i6 / i5);
                this.mDirValue[0] = (this.mDirValue[0] + 720.0f) % 360.0f;
            }
            this.mDirValue[1] = (degrees2 + 720.0f) % 360.0f;
            this.mDirValue[2] = (degrees3 + 720.0f) % 360.0f;
        }
    }

    public float[] getDirectionDegree() {
        if (this.mGyroSensor == null || this.mGyroValue[0] == -1.0f) {
            return this.mDirValue;
        }
        if (Math.abs(this.mGyroValue[0] - this.mDirValue[0]) > 40.0f) {
            return this.mGyroValue;
        }
        return new float[]{(this.mGyroValue[0] + this.mDirValue[0]) / 2.0f, (this.mGyroValue[1] + this.mDirValue[1]) / 2.0f, (this.mGyroValue[2] + this.mDirValue[2]) / 2.0f};
    }

    public void registerSensors() {
        if (this.mGyroSensor != null) {
            this.mSensorManager.registerListener(this.mySensorListener, this.mGyroSensor, 1);
        }
        if (this.aMfSensor != null) {
            this.mSensorManager.registerListener(this.mySensorListener, this.aMfSensor, 2);
        }
        if (this.mAcSensor != null) {
            this.mSensorManager.registerListener(this.mySensorListener, this.mAcSensor, 2);
        }
    }

    public void unRegisterSensors() {
        if (this.mGyroSensor != null) {
            this.mSensorManager.unregisterListener(this.mySensorListener);
        }
        if (this.aMfSensor != null) {
            this.mSensorManager.unregisterListener(this.mySensorListener);
        }
        if (this.mAcSensor != null) {
            this.mSensorManager.unregisterListener(this.mySensorListener);
        }
        clearData();
    }

    private void clearData() {
        mMySensorManager = null;
        this.mContext = null;
        this.mMagneticData.clear();
        this.mAccelerData.clear();
        this.mGaccurate.clear();
        this.mMAaccurate.clear();
        this.mGx.clear();
        this.mMAx.clear();
    }

    public void initCompensate() {
        int i;
        int i2;
        String str = Build.MODEL;
        int i3 = 0;
        if (!TextUtils.isEmpty(str)) {
            String[] stringArray = this.mContext.getResources().getStringArray(this.mResource.getIdentifier("left_horizontal_compensate_model", PoiLayoutTemplate.ARRAY, this.mContext.getPackageName()));
            int length = stringArray.length;
            int i4 = 0;
            i = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                i++;
                if (str.contains(stringArray[i4])) {
                    this.mIsLeftCompensate = true;
                    break;
                }
                i4++;
            }
            String[] stringArray2 = this.mContext.getResources().getStringArray(this.mResource.getIdentifier("right_horizontal_compensate_model", PoiLayoutTemplate.ARRAY, this.mContext.getPackageName()));
            int length2 = stringArray2.length;
            i2 = 0;
            while (true) {
                if (i3 >= length2) {
                    break;
                }
                i2++;
                if (str.contains(stringArray2[i3])) {
                    this.mIsRightCompensate = true;
                    break;
                }
                i3++;
            }
        } else {
            i2 = 0;
            i = 0;
        }
        this.mLeftY = this.mContext.getResources().getIntArray(this.mResource.getIdentifier("left_horizontal_compensate_y", PoiLayoutTemplate.ARRAY, this.mContext.getPackageName()))[i - 1];
        this.mRightY = this.mContext.getResources().getIntArray(this.mResource.getIdentifier("right_horizontal_compensate_y", PoiLayoutTemplate.ARRAY, this.mContext.getPackageName()))[i2 - 1];
    }

    public void setDirValueListener(DirectionSensorListener directionSensorListener) {
        this.myDirValueListener = directionSensorListener;
    }
}
