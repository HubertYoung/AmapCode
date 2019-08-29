package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d;
import com.huawei.android.pushselfshow.utils.b.b;
import java.io.File;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements SensorEventListener, h {
    public SoundPool a = null;
    public boolean b = false;
    private SensorManager c;
    private Sensor d;
    /* access modifiers changed from: private */
    public Activity e;
    private String f;
    private NativeToJsMessageQueue g;
    private float h = 0.0f;
    private float i = 0.0f;
    private float j = 0.0f;
    private long k = 0;
    private int l;
    private int m = 0;
    private int n;
    private String o;
    private int p;
    private int q;
    private int r;
    private long s = 0;
    private Handler t = null;
    private Runnable u = new b(this);

    public a(Activity activity) {
        a(0);
        this.c = (SensorManager) activity.getSystemService("sensor");
        this.e = activity;
    }

    private void a(int i2) {
        this.l = i2;
    }

    private void a(com.huawei.android.pushselfshow.richpush.html.api.d.a aVar) {
        try {
            StringBuilder sb = new StringBuilder(" accelListener fail,the status is ");
            sb.append(d.c()[aVar.ordinal()]);
            c.e("PushSelfShowLog", sb.toString());
            this.g.a(this.f, aVar, "error", null);
        } catch (Exception | RuntimeException unused) {
            c.e("PushSelfShowLog", " accelListener fail error");
        }
    }

    private void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has("minAccuracy")) {
                    this.n = jSONObject.getInt("minAccuracy");
                }
            } catch (Exception unused) {
                c.e("PushSelfShowLog", "read OPTIONS_MIN_ACCURACY options error");
            }
            try {
                if (jSONObject.has("minAccelX")) {
                    this.p = jSONObject.getInt("minAccelX");
                }
                if (jSONObject.has("minAccelY")) {
                    this.q = jSONObject.getInt("minAccelY");
                }
                if (jSONObject.has("minAccelZ")) {
                    this.r = jSONObject.getInt("minAccelZ");
                }
            } catch (JSONException unused2) {
                c.e("PushSelfShowLog", "read OPTIONS_MIN_ACCEL_X_Y_Z options error");
            }
        }
        c.b((String) "PushSelfShowLog", (String) "start with optins and the minAccuracy is %s  , minAccelX is %s ,minAccelY is %s ,minAccelZ is %s", Integer.valueOf(this.n), Integer.valueOf(this.p), Integer.valueOf(this.q), Integer.valueOf(this.r));
        if (this.l != 2 && this.l != 1) {
            a(1);
            this.b = true;
            List<Sensor> sensorList = this.c.getSensorList(1);
            if (sensorList == null || sensorList.size() <= 0) {
                a(3);
                a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_NO_SENSORS);
                return;
            }
            this.d = sensorList.get(0);
            this.c.registerListener(this, this.d, 2);
            a(1);
            c.e("PushSelfShowLog", "this.setStatus(AccelListener.STARTING);");
            e();
            this.t = new Handler(Looper.getMainLooper());
            this.t.postDelayed(this.u, 2000);
        }
    }

    private void b(JSONObject jSONObject) {
        if (System.currentTimeMillis() - this.s >= 2000) {
            this.s = System.currentTimeMillis();
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("soundType")) {
                        this.o = jSONObject.getString("soundType");
                    }
                } catch (Exception unused) {
                    c.e("PushSelfShowLog", "read OPTIONS_PLAY_MUSIC ,SRC options error");
                }
            }
            c.b((String) "PushSelfShowLog", (String) "playSound with optins and the  soundType is %s ", this.o);
            try {
                if ("TYPE_SHAKE".equals(this.o)) {
                    String c2 = b.c(this.e);
                    if (c2 == null) {
                        a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_NO_SDCARD);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(c2);
                    sb.append(File.separator);
                    sb.append("pushresources/shake.mp3".substring(0, "pushresources/shake.mp3".indexOf("/")));
                    String sb2 = sb.toString();
                    if (!new File(sb2).exists() && new File(sb2).mkdirs()) {
                        c.e("PushSelfShowLog", "resource mkdir true");
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(c2);
                    sb3.append(File.separator);
                    sb3.append("pushresources/shake.mp3");
                    String sb4 = sb3.toString();
                    com.huawei.android.pushselfshow.utils.a.b(this.e, "pushresources/shake.mp3", sb4);
                    if (!new File(sb4).exists()) {
                        a(com.huawei.android.pushselfshow.richpush.html.api.d.a.IO_EXCEPTION);
                        return;
                    }
                    this.a = new SoundPool(1, 3, 0);
                    this.a.setOnLoadCompleteListener(new c(this));
                    this.a.load(sb4, 1);
                }
            } catch (Exception e2) {
                c.c("PushSelfShowLog", " error", e2);
            }
        }
    }

    private void e() {
        if (this.t != null) {
            this.t.removeCallbacks(this.u);
        }
    }

    private void f() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
        this.b = false;
        e();
        StringBuilder sb = new StringBuilder(" stop this.status");
        sb.append(this.l);
        c.e("PushSelfShowLog", sb.toString());
        if (this.c != null) {
            this.c.unregisterListener(this);
        }
        a(0);
        this.m = 0;
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.l == 1) {
            a(3);
            a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_CAN_NOT_START);
        }
    }

    private void h() {
        c.e("PushSelfShowLog", " accelListener success");
        this.g.a(this.f, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", i());
    }

    private JSONObject i() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(DictionaryKeys.CTRLXY_X, (double) this.h);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, (double) this.i);
            jSONObject.put("z", (double) this.j);
            jSONObject.put("timestamp", this.k);
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return jSONObject;
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a() {
        c.e("PushSelfShowLog", "call acclListener init()");
        this.n = 2;
        this.o = "TYPE_SHAKE";
        this.p = 10;
        this.q = 10;
        this.r = 10;
    }

    public void a(int i2, int i3, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
            return;
        }
        this.g = nativeToJsMessageQueue;
        if (H5PageData.KEY_UC_START.equals(str)) {
            c.e("PushSelfShowLog", "call acclListener exec and the method is start");
            d();
            if (str2 != null) {
                this.f = str2;
                if (this.l != 2) {
                    a(jSONObject);
                }
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if (AudioUtils.CMDSTOP.equals(str)) {
            f();
        } else if ("playSound".equals(str)) {
            b(jSONObject);
        } else {
            a(com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION);
        }
    }

    public void b() {
        if (this.l != 0 && this.l != 3) {
            f();
            a((JSONObject) null);
        }
    }

    public void c() {
        d();
    }

    public void d() {
        c.e("PushSelfShowLog", "accel reset");
        f();
        a();
    }

    public void onAccuracyChanged(Sensor sensor, int i2) {
        if (sensor.getType() == 1 && this.l != 0) {
            this.m = i2;
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1 && this.b && this.l != 0) {
            a(2);
            if (this.m >= this.n) {
                this.h = sensorEvent.values[0];
                this.i = sensorEvent.values[1];
                this.j = sensorEvent.values[2];
                this.k = System.currentTimeMillis();
                if (Math.abs(this.h) > ((float) this.p) || Math.abs(this.i) > ((float) this.q) || Math.abs(this.j) > ((float) this.r)) {
                    c.b((String) "PushSelfShowLog", (String) "onSensorChanged and x = %s , y=%s , z=%s ", Float.valueOf(this.h), Float.valueOf(this.i), Float.valueOf(this.j));
                    h();
                }
            }
        }
    }
}
