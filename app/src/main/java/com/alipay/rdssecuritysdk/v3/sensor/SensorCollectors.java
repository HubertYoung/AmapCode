package com.alipay.rdssecuritysdk.v3.sensor;

import android.content.Context;
import android.hardware.SensorManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorCollectors {
    SensorType[] a = {SensorType.ACCELEROMETER, SensorType.MAGNETIC, SensorType.GYROSCOPE, SensorType.GRAVITY};
    private List<SensorCollectWorker> b = new ArrayList();

    public enum SensorType {
        ACCELEROMETER(1, "Acceleration"),
        GRAVITY(9, "Gravity"),
        GYROSCOPE(4, "Gyroscope"),
        MAGNETIC(2, "Magnetic");
        
        private String mSensorName;
        private int mSensorType;

        private SensorType(int i, String str) {
            this.mSensorName = str;
            this.mSensorType = i;
        }

        public final String getSensorName() {
            return this.mSensorName;
        }

        public final int getmSensorType() {
            return this.mSensorType;
        }
    }

    public SensorCollectors(Context context) {
        a(context);
    }

    private void a(Context context) {
        this.b.clear();
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        for (SensorType sensorCollectWorker : this.a) {
            this.b.add(new SensorCollectWorker(sensorManager, sensorCollectWorker, 6));
        }
    }

    public final void a() {
        for (SensorCollectWorker a2 : this.b) {
            a2.a();
        }
    }

    public final void b() {
        for (SensorCollectWorker b2 : this.b) {
            b2.b();
        }
    }

    public final Map<String, List<String>> c() {
        HashMap hashMap = new HashMap();
        if (this.b != null) {
            for (SensorCollectWorker next : this.b) {
                if (next != null) {
                    List<String> d = next.d();
                    String c = next.c();
                    if (c != null && c.length() > 0) {
                        hashMap.put(c, d);
                    }
                }
            }
        }
        return hashMap;
    }
}
