package defpackage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: rq reason: default package */
/* compiled from: PressureHelper */
public class rq implements SensorEventListener {
    private static boolean d = false;
    boolean a = false;
    HandlerThread b;
    private Handler c;

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static boolean a() {
        try {
            if (!TextUtils.isEmpty(a("ro.build.hw_emui_api_level")) || !TextUtils.isEmpty(a("ro.build.version.emui")) || !TextUtils.isEmpty(a("ro.confg.hw_systemversion")) || !TextUtils.isEmpty(a("ro.config.hw_simpleui_enable"))) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private static String a(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getMethod("get", new Class[]{String.class});
            method.setAccessible(true);
            return (String) method.invoke(cls, new Object[]{str});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        r7 = "";
        return "";
    }

    public static boolean b() {
        return b("ro.config.huawei_navi_extend");
    }

    private static boolean b(String str) {
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("getBoolean", new Class[]{String.class, Boolean.TYPE});
            method.setAccessible(true);
            return ((Boolean) method.invoke(null, new Object[]{str, Boolean.FALSE})).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean c() {
        if (!this.a) {
            try {
                SensorManager sensorManager = (SensorManager) AMapAppGlobal.getApplication().getSystemService("sensor");
                Sensor defaultSensor = sensorManager.getDefaultSensor(6);
                if (this.b == null || !this.b.isAlive()) {
                    this.b = new HandlerThread("PressSensorT");
                    this.b.start();
                    this.c = new Handler(this.b.getLooper());
                    ku a2 = ku.a();
                    String simpleName = rq.class.getSimpleName();
                    StringBuilder sb = new StringBuilder("registerPressureLister mHandlerThread   thread name:");
                    sb.append(this.b.getName());
                    sb.append("  id:");
                    sb.append(this.b.getThreadId());
                    a2.c(simpleName, sb.toString());
                }
                this.a = sensorManager.registerListener(this, defaultSensor, 3, this.c);
                ku a3 = ku.a();
                String simpleName2 = rq.class.getSimpleName();
                StringBuilder sb2 = new StringBuilder("registerPressureLister isRegister:");
                sb2.append(this.a);
                a3.c(simpleName2, sb2.toString());
            } catch (Exception unused) {
                this.a = false;
            }
        }
        return this.a;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent != null && sensorEvent.sensor != null && sensorEvent.sensor.getType() == 6) {
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.a(sensorEvent);
            }
        }
    }
}
