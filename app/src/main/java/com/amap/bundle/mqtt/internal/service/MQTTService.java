package com.amap.bundle.mqtt.internal.service;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import com.amap.bundle.mqtt.internal.MQTTConnection;
import com.amap.bundle.mqtt.internal.MQTTConnection.a;
import com.amap.location.sdk.fusion.LocationParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class MQTTService {
    private static boolean f = false;
    public MQTTConnection a;
    /* access modifiers changed from: 0000 */
    public MQTTConnectionStauts b = MQTTConnectionStauts.DISCONNECTED;
    /* access modifiers changed from: private */
    public yo c = new yo();
    private yi d;
    private a e = new a() {
        public final void a(int i) {
            MQTTService.a((String) "MQTTService. mMQTTConnectionCallback. onError. %s", Integer.valueOf(i));
        }

        public final void a(String str) {
            MQTTService.a((String) "MQTTService. mMQTTConnectionCallback. connectionLost. %s", str);
            MQTTConnectionStauts mQTTConnectionStauts = MQTTConnectionStauts.DISCONNECTED;
            MQTTService.this.b = mQTTConnectionStauts;
            MQTTService.this.c.a(mQTTConnectionStauts);
        }

        public final void a(int i, int i2, String str, String str2) {
            JSONObject jSONObject;
            MQTTService.a((String) "MQTTService. mMQTTConnectionCallback. onResponse. %s %s %s %s", Integer.valueOf(i), Integer.valueOf(i2), str, str2);
            MQTTService.a(LogType.File, "上行回调. 业务码: %s, 状态码: %s, 数据：%s", Integer.valueOf(i), MQTTResponseCode.getDesc(i2), str2);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                try {
                    jSONObject = new JSONObject(str2);
                } catch (JSONException unused) {
                    jSONObject = null;
                }
                if (jSONObject != null) {
                    MQTTService.this.c.a(i, i2, jSONObject);
                }
            }
        }

        public final void a(int i, String str, String str2) {
            JSONObject jSONObject;
            MQTTService.a((String) "MQTTService. mMQTTConnectionCallback. messageArrived. %s %s %s", Integer.valueOf(i), str, str2);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                try {
                    jSONObject = new JSONObject(str2);
                } catch (JSONException unused) {
                    jSONObject = null;
                }
                if (jSONObject != null) {
                    MQTTService.this.c.a(i, jSONObject);
                }
            }
        }

        public final void a() {
            MQTTService.a((String) "MQTTService. mMQTTConnectionCallback. onConnected.", new Object[0]);
            MQTTService.this.c.a(MQTTConnectionStauts.CONNECTED);
        }
    };

    public enum LogType {
        Console,
        File,
        ConsoleAndFile
    }

    public MQTTService() {
        f = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("mqtt_log_switch", false);
        this.a = new MQTTConnection();
        MQTTConnection mQTTConnection = this.a;
        mQTTConnection.e.a(this.e);
    }

    public final synchronized yg a(yf yfVar) {
        if (yfVar == null) {
            return null;
        }
        AMapLog.info("paas.mqtt", "MQTTService", String.format("registerBiz. bizType: %s", new Object[]{yfVar.getMQTTBizType().name()}));
        a((String) "MQTTService. registerBiz. bizType: %s", yfVar.getMQTTBizType().name());
        b(LogType.ConsoleAndFile, "业务注册长链. 业务类型: %s", yfVar.getMQTTBizType().name());
        yn ynVar = new yn(yfVar, new a() {
            public final MQTTConnection a() {
                return MQTTService.this.a;
            }

            public final MQTTConnectionStauts b() {
                return MQTTService.this.b;
            }
        });
        yo yoVar = this.c;
        if (((ym) yoVar.a.put(yfVar.getMQTTBizType(), ynVar)) != null) {
            a((String) "MQTTProtoDispatcher. addListener. replaced", new Object[0]);
        }
        b();
        return ynVar;
    }

    public final synchronized boolean a(MQTTBizType mQTTBizType) {
        AMapLog.info("paas.mqtt", "MQTTService", String.format("unregisterBiz. bizType: %s", new Object[]{mQTTBizType.name()}));
        a((String) "MQTTService. unregisterBiz. bizType: %s", mQTTBizType.name());
        b(LogType.ConsoleAndFile, "业务注销长链. 业务类型: %s", mQTTBizType.name());
        ym ymVar = (ym) this.c.a.remove(mQTTBizType);
        b();
        if (ymVar != null) {
            return true;
        }
        return false;
    }

    private void b() {
        boolean c2 = c();
        a((String) "MQTTConnectionManager. refreshConnetionStatus. shouldAlive: %s, isInited: %s", Boolean.valueOf(c2), Boolean.valueOf(this.a.b));
        if (c2 != this.a.b) {
            if (c2) {
                this.a.a(d());
                return;
            }
            this.a.b();
        }
    }

    private boolean c() {
        int size = this.c.a.size();
        a((String) "MQTTService. mMQTTConnectionManagerController. shouldAlive(). listenerCount: %s, mMQTTConfig: %s", Integer.valueOf(size), String.valueOf(this.d));
        return size > 0 && this.d != null;
    }

    private yk d() {
        yk ykVar = new yk();
        ykVar.a = this.d.a;
        ykVar.d = this.d.b;
        ykVar.b = this.d.c;
        ykVar.c = e();
        return ykVar;
    }

    private String e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tid", this.d.d);
            jSONObject.put("channel", this.d.e);
            jSONObject.put(LocationParams.PARA_COMMON_DIP, this.d.f);
            jSONObject.put(LocationParams.PARA_COMMON_DIC, this.d.g);
            jSONObject.put(LocationParams.PARA_COMMON_DIU, this.d.h);
            jSONObject.put(LocationParams.PARA_COMMON_ADIU, this.d.i);
            jSONObject.put(LocationParams.PARA_COMMON_AUTODIV, this.d.j);
            jSONObject.put(Oauth2AccessToken.KEY_UID, this.d.a);
            jSONObject.put("sessionid", this.d.k);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final synchronized void a(yi yiVar) {
        Object[] objArr = new Object[1];
        objArr[0] = yiVar == null ? "null" : yiVar.toString();
        AMapLog.info("paas.mqtt", "MQTTService", String.format("setConfig %s", objArr));
        this.d = yiVar;
        b();
    }

    public static void a(String str, Object... objArr) {
        b(LogType.Console, str, objArr);
    }

    public static void a(LogType logType, String str, Object... objArr) {
        b(logType, str, objArr);
    }

    public static void b(String str, Object... objArr) {
        b(LogType.Console, str, objArr);
    }

    private static void b(LogType logType, String str, Object... objArr) {
        if (f) {
            try {
                String format = String.format(str, objArr);
                if (logType != LogType.Console) {
                    LogType logType2 = LogType.ConsoleAndFile;
                }
                if (logType == LogType.File || logType == LogType.ConsoleAndFile) {
                    String.format("[%s]---%s", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), format});
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void a(boolean z) {
        f = z;
    }

    public static boolean a() {
        return f;
    }
}
