package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

@TargetApi(14)
public class OrientationDetector {
    private static OrientationDetector a = null;
    /* access modifiers changed from: private */
    public static int d = 0;
    /* access modifiers changed from: private */
    public int b = 0;
    private OrientationListener c = null;

    private class MyOrientationEventListener extends OrientationEventListener implements OrientationListener {
        public MyOrientationEventListener(Context context) {
            super(context);
        }

        public void onOrientationChanged(int orientation) {
            int orientation2;
            if (orientation == -1) {
                Logger.D("OrientationDetector", "ORIENTATION_UNKNOWN", new Object[0]);
                return;
            }
            if (orientation > 340 || orientation < 20) {
                orientation2 = 0;
            } else if (orientation > 70 && orientation < 110) {
                orientation2 = 90;
            } else if (orientation > 160 && orientation < 200) {
                orientation2 = 180;
            } else if (orientation > 250 && orientation < 290) {
                orientation2 = 270;
            } else {
                return;
            }
            if (orientation2 != OrientationDetector.this.b) {
                Logger.D("OrientationDetector", "old phone onOrientationChanged:" + orientation2, new Object[0]);
            }
            OrientationDetector.this.b = orientation2;
        }

        public void register() {
            enable();
        }

        public void unregister() {
            disable();
        }

        public boolean isAvailable() {
            return true;
        }
    }

    private class MySensorEventListener implements OrientationListener {
        final SensorEventListener a = new SensorEventListener() {
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == 2) {
                    MySensorEventListener.this.f = sensorEvent.values;
                }
                if (sensorEvent.sensor.getType() == 1) {
                    MySensorEventListener.this.e = sensorEvent.values;
                }
                MySensorEventListener.this.a();
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        private Sensor c;
        private Sensor d;
        /* access modifiers changed from: private */
        public float[] e = new float[3];
        /* access modifiers changed from: private */
        public float[] f = new float[3];
        private SensorManager g;

        public MySensorEventListener(Context context) {
            this.g = (SensorManager) context.getApplicationContext().getSystemService("sensor");
            this.c = this.g.getDefaultSensor(1);
            this.d = this.g.getDefaultSensor(2);
            if (this.c == null || this.d == null) {
                Logger.E("OrientationDetector", "get mMSensor or mASensor failed!", new Object[0]);
            }
            Logger.D("OrientationDetector", "MySensorEventListener constructed.", new Object[0]);
        }

        /* access modifiers changed from: private */
        public void a() {
            float[] values = new float[3];
            float[] R = new float[9];
            SensorManager.getRotationMatrix(R, null, this.e, this.f);
            SensorManager.getOrientation(R, values);
            float x = (float) Math.toDegrees((double) values[1]);
            float y = (float) Math.toDegrees((double) values[2]);
            int orientation = OrientationDetector.this.b;
            if (x < -30.0f && x > -150.0f) {
                orientation = 0;
            } else if (x > 30.0f && x < 150.0f) {
                orientation = 180;
            } else if (y > 30.0f) {
                orientation = 90;
            } else if (y < -30.0f) {
                orientation = 270;
            }
            if (orientation != OrientationDetector.this.b) {
                Logger.D("OrientationDetector", "phone orientation changed to " + orientation, new Object[0]);
            }
            OrientationDetector.this.b = orientation;
        }

        public void register() {
            if (!this.g.registerListener(this.a, this.c, 3)) {
                Logger.E("OrientationDetector", "registerListener mASensor failed!", new Object[0]);
            }
            if (!this.g.registerListener(this.a, this.d, 3)) {
                Logger.E("OrientationDetector", "registerListener mMSensor failed!", new Object[0]);
            }
        }

        public void unregister() {
            try {
                if (this.c != null && this.d != null) {
                    this.g.unregisterListener(this.a);
                    Logger.D("OrientationDetector", "OrientationDetector unregister.", new Object[0]);
                }
            } catch (Exception e2) {
                Logger.D("OrientationDetector", "unregister exp=" + e2.toString(), new Object[0]);
            }
        }

        public boolean isAvailable() {
            return (this.c == null || this.d == null) ? false : true;
        }
    }

    private class MyTabOrientationListener implements OrientationListener {
        final SensorEventListener a = new SensorEventListener() {
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == 3) {
                    MyTabOrientationListener.this.d = sensorEvent.values;
                    MyTabOrientationListener.this.a();
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        private Sensor c;
        /* access modifiers changed from: private */
        public float[] d = new float[3];
        private SensorManager e;

        public MyTabOrientationListener(Context context) {
            this.e = (SensorManager) context.getApplicationContext().getSystemService("sensor");
            this.c = this.e.getDefaultSensor(3);
            if (this.c == null) {
                Logger.E("OrientationDetector", "get mSensor failed!", new Object[0]);
            }
            Logger.D("OrientationDetector", "MyTabOrientationListener constructed.", new Object[0]);
        }

        /* access modifiers changed from: private */
        public void a() {
            float axisX = this.d[1];
            float axisY = this.d[2];
            switch (OrientationDetector.d) {
                case 0:
                    axisX = this.d[1];
                    axisY = this.d[2];
                    break;
                case 1:
                    axisX = this.d[2];
                    axisY = -this.d[1];
                    break;
                case 2:
                    axisX = -this.d[2];
                    axisY = -this.d[1];
                    break;
                case 3:
                    axisX = -this.d[2];
                    axisY = this.d[1];
                    break;
            }
            int orientation = OrientationDetector.this.b;
            if (axisX > 30.0f) {
                orientation = 0;
            } else if (axisX < -30.0f) {
                orientation = 180;
            } else if (axisY < -30.0f) {
                orientation = 270;
            } else if (axisY > 30.0f) {
                orientation = 90;
            }
            if (orientation != OrientationDetector.this.b) {
                Logger.D("OrientationDetector", "tablet orientation changed to " + orientation, new Object[0]);
            }
            OrientationDetector.this.b = orientation;
        }

        public void register() {
            if (!this.e.registerListener(this.a, this.c, 3)) {
                Logger.E("OrientationDetector", "registerListener mSensor failed!", new Object[0]);
            }
        }

        public void unregister() {
            if (this.c != null) {
                this.e.unregisterListener(this.a);
                Logger.D("OrientationDetector", "OrientationDetector unregister.", new Object[0]);
            }
        }

        public boolean isAvailable() {
            return true;
        }
    }

    private interface OrientationListener {
        boolean isAvailable();

        void register();

        void unregister();
    }

    private OrientationDetector(Context context) {
        try {
            if (!isTablet(context)) {
                this.c = new MySensorEventListener(context);
                if (this.c == null || !this.c.isAvailable()) {
                    Logger.D("OrientationDetector", "MySensorEventListener not available.", new Object[0]);
                    this.c = new MyOrientationEventListener(context);
                    return;
                }
                return;
            }
            this.c = new MyTabOrientationListener(context);
        } catch (Exception e) {
            Logger.E((String) "OrientationDetector", (Throwable) e, (String) "OrientationDetector constrcuction exception:", new Object[0]);
        }
    }

    public static OrientationDetector getInstance(Context context) {
        if (context instanceof Activity) {
            d = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
        }
        Context context2 = context.getApplicationContext();
        if (a == null) {
            synchronized (OrientationDetector.class) {
                try {
                    if (a == null) {
                        a = new OrientationDetector(context2);
                    }
                }
            }
        }
        return a;
    }

    public void register() {
        if (this.c != null) {
            this.c.register();
        }
    }

    public void unregister() {
        if (this.c != null) {
            this.c.unregister();
        }
    }

    public int getDevOrientation() {
        Logger.D("OrientationDetector", "getDevOrientation:" + this.b, new Object[0]);
        return this.b;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 4 && !DeviceWrapper.isDetectOrientationBlackList();
    }
}
