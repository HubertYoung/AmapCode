package com.alipay.mobile.security.bio.sensor;

import android.content.Context;
import android.hardware.SensorManager;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.ArrayList;
import java.util.List;

public class SensorCollectors {
    SensorType[] a = {SensorType.ACCELEROMETER, SensorType.MAGNETIC, SensorType.GYROSCOPE};
    private List<a> b = new ArrayList();

    public enum SensorType {
        ACCELEROMETER(1, "Acceleration"),
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
        reset(context);
    }

    public void reset(Context context) {
        if (context != null) {
            try {
                this.b.clear();
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                for (SensorType aVar : this.a) {
                    this.b.add(new a(sensorManager, aVar));
                }
            } catch (Exception e) {
                BioLog.e(e.toString());
            }
        }
    }

    public void startListening() {
        for (a next : this.b) {
            if (!(next.a == null || next.b == null)) {
                next.b.registerListener(next, next.a, 50000);
                BioLog.i("SensorCollectWorker: " + next.c + " 开始采集数据.［" + System.currentTimeMillis() + "]");
            }
        }
    }

    public void destroy() {
        for (a next : this.b) {
            if (!(next.a == null || next.b == null)) {
                next.b.unregisterListener(next, next.a);
                next.a = null;
                BioLog.i("SensorCollectWorker: " + next.c + " 停止采集数据.［" + System.currentTimeMillis() + "]");
            }
        }
    }

    public SensorData getData() {
        SensorData sensorData = new SensorData();
        if (this.b != null) {
            for (a next : this.b) {
                if (next != null) {
                    String b2 = next.b();
                    if (next.a() == 1) {
                        sensorData.a = b2;
                    }
                    if (next.a() == 2) {
                        sensorData.c = b2;
                    }
                    if (next.a() == 4) {
                        sensorData.b = b2;
                    }
                }
            }
        }
        return sensorData;
    }
}
