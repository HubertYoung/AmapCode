package com.amap.bundle.mqtt.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mqtt.internal.service.MQTTResponseCode;
import com.amap.bundle.mqtt.internal.service.MQTTService;
import com.amap.bundle.mqtt.internal.service.MQTTService.LogType;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.mqtt.IPushCallback;
import com.autonavi.mqtt.PushClient;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public final class MQTTConnection {
    public volatile PushClient a;
    public volatile boolean b = false;
    public volatile boolean c = false;
    long d = 0;
    public agl<a> e = new agl<>();
    yj f = new yj();
    NetworkChangeReceiver g = new NetworkChangeReceiver(this);
    b h = null;
    c i = null;
    final Object j = new Object();
    public final LinkedHashMap<Long, ProtoInQueue> k = new LinkedHashMap<>();
    final LinkedHashMap<Integer, ProtoInQueue> l = new LinkedHashMap<>();
    final LinkedHashMap<Long, ProtoInQueue> m = new LinkedHashMap<>();
    final Object n = new Object();
    Timer o = null;
    TimerTask p = null;
    private final boolean q = true;

    static class NetworkChangeReceiver extends BroadcastReceiver {
        Integer a = null;
        private WeakReference<MQTTConnection> b;

        public NetworkChangeReceiver(MQTTConnection mQTTConnection) {
            this.b = new WeakReference<>(mQTTConnection);
        }

        public void onReceive(Context context, Intent intent) {
            MQTTService.a((String) "MQTTConnection. NetworkChangeReceiver. onReceive.", new Object[0]);
            try {
                if (aaw.c(context)) {
                    int b2 = aaw.b(AMapAppGlobal.getApplication());
                    if (this.a != null) {
                        if (this.a.intValue() == b2) {
                            MQTTService.a((String) "MQTTConnection. NetworkChangeReceiver. Igore first broadcast. network type: %s", MQTTConnection.a(b2));
                        }
                    }
                    MQTTConnection mQTTConnection = (MQTTConnection) this.b.get();
                    if (mQTTConnection != null && !mQTTConnection.c && mQTTConnection.b) {
                        MQTTService.a(LogType.ConsoleAndFile, "网络状态改变. 网络类型: %s", MQTTConnection.a(b2));
                        if (mQTTConnection.i != null) {
                            mQTTConnection.i.b();
                        }
                        mQTTConnection.a();
                    }
                } else {
                    MQTTService.a(LogType.ConsoleAndFile, "网络断开.", new Object[0]);
                }
            } finally {
                this.a = null;
            }
        }
    }

    public static class ProtoInQueue {
        public long a;
        public boolean b;
        int c;
        public int d;
        public String e;
        public String f;
        public long g;
        TIMEOUT_TYPE h;

        public enum TIMEOUT_TYPE {
            UNKNOWN,
            PUBLISH,
            DELIVERY,
            RESPONSE
        }

        private ProtoInQueue() {
            this.a = 0;
            this.b = false;
            this.c = 0;
            this.d = 0;
            this.g = 0;
            this.h = TIMEOUT_TYPE.UNKNOWN;
        }

        public /* synthetic */ ProtoInQueue(byte b2) {
            this();
        }
    }

    public interface a {
        void a();

        void a(int i);

        void a(int i, int i2, String str, String str2);

        void a(int i, String str, String str2);

        void a(String str);
    }

    static class b implements IPushCallback {
        private WeakReference<MQTTConnection> a;

        public final void onUnInited() {
        }

        b(MQTTConnection mQTTConnection) {
            this.a = new WeakReference<>(mQTTConnection);
        }

        public final void onError(final int i) {
            MQTTService.a((String) "MQTTConnection. InternalCallback. onError. code: %s", Integer.valueOf(i));
            MQTTService.a(LogType.ConsoleAndFile, "长链发生错误", new Object[0]);
            AMapLog.error("paas.mqtt", "AMAP*IM*MQTTConnection", String.format("长链发生错误:%d", new Object[]{Integer.valueOf(i)}));
            if (bno.a) {
                ye.b().a("MQTT：onError\n".concat(String.valueOf(i)));
            }
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (mQTTConnection != null) {
                mQTTConnection.c = false;
                if (mQTTConnection.i != null) {
                    mQTTConnection.i.a();
                }
                if (mQTTConnection.e != null && mQTTConnection.b) {
                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                        public final /* synthetic */ void onNotify(Object obj) {
                            ((a) obj).a(i);
                        }
                    });
                }
            }
        }

        public final void connectionLost(final String str) {
            MQTTService.a((String) "MQTTConnection. InternalCallback. connectionLost. cause: %s", str);
            if (bno.a) {
                ye.b().a("MQTT：connectionLost \n".concat(String.valueOf(str)));
            }
            aaw.b(AMapAppGlobal.getApplication());
            MQTTService.a(LogType.ConsoleAndFile, "服务器连接断开.", new Object[0]);
            AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", "服务器连接断开.");
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (mQTTConnection != null) {
                mQTTConnection.c = false;
                mQTTConnection.a();
                if (mQTTConnection.e != null && mQTTConnection.b) {
                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                        public final /* synthetic */ void onNotify(Object obj) {
                            ((a) obj).a(str);
                        }
                    });
                }
            }
        }

        public final void messagePublished(String str, String str2, int i, Object obj) {
            ProtoInQueue protoInQueue;
            MQTTService.a((String) "MQTTConnection. InternalCallback. messagePublished. topic: %s, message: %s, msgId: %s, token: %s", str, str2, Integer.valueOf(i), obj);
            MQTTService.a(LogType.File, "上行消息. %s", str2);
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (!(mQTTConnection == null || obj == null)) {
                synchronized (mQTTConnection.k) {
                    protoInQueue = (ProtoInQueue) mQTTConnection.k.remove(obj);
                }
                if (protoInQueue != null) {
                    if (i > 0) {
                        MQTTService.a(LogType.ConsoleAndFile, "上行消息. 发送成功", new Object[0]);
                        synchronized (mQTTConnection.l) {
                            protoInQueue.g = System.currentTimeMillis();
                            mQTTConnection.l.put(Integer.valueOf(i), protoInQueue);
                        }
                        return;
                    }
                    MQTTService.a(LogType.ConsoleAndFile, "上行消息. 发送失败", new Object[0]);
                    mQTTConnection.a(protoInQueue.d, MQTTResponseCode.INTERNAL.value(), protoInQueue.e, protoInQueue.f);
                }
            }
        }

        public final void messageCleared(String str, String str2, Object obj) {
            ProtoInQueue protoInQueue;
            MQTTService.a((String) "MQTTConnection. InternalCallback. messageCleared. topic: %s, message: %s, token: %s", str, str2, obj);
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (!(mQTTConnection == null || obj == null)) {
                synchronized (mQTTConnection.k) {
                    protoInQueue = (ProtoInQueue) mQTTConnection.k.remove(obj);
                }
                if (protoInQueue != null) {
                    MQTTService.a(LogType.ConsoleAndFile, "上行消息. 被移除出发送队列", new Object[0]);
                    mQTTConnection.a(protoInQueue.d, MQTTResponseCode.INTERNAL.value(), protoInQueue.e, protoInQueue.f);
                }
            }
        }

        public final void deliveryComplete(int i) {
            ProtoInQueue protoInQueue;
            MQTTService.a((String) "MQTTConnection. InternalCallback. deliveryComplete. msgId: %s", Integer.valueOf(i));
            if (bno.a) {
                ye.b().a("MQTT：deliveryComplete \n".concat(String.valueOf(i)));
            }
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (mQTTConnection != null) {
                mQTTConnection.c = true;
                if (i > 0) {
                    MQTTService.a(LogType.ConsoleAndFile, "上行回调. 服务器已收到.", new Object[0]);
                    synchronized (mQTTConnection.l) {
                        protoInQueue = (ProtoInQueue) mQTTConnection.l.remove(Integer.valueOf(i));
                    }
                    if (protoInQueue != null && protoInQueue.a > 0) {
                        synchronized (mQTTConnection.m) {
                            protoInQueue.g = System.currentTimeMillis();
                            mQTTConnection.m.put(Long.valueOf(protoInQueue.a), protoInQueue);
                        }
                        return;
                    }
                    return;
                }
                MQTTService.a(LogType.ConsoleAndFile, "上行回调. 服务器未收到.", new Object[0]);
            }
        }

        public final void messageArrived(final String str, String str2) {
            JSONObject jSONObject;
            ProtoInQueue protoInQueue;
            MQTTService.a((String) "MQTTConnection. InternalCallback. messageArrived. topic: %s, data: %s", str, str2);
            if (bno.a) {
                ye b = ye.b();
                StringBuilder sb = new StringBuilder("MQTT：messageArrived \n");
                sb.append(str);
                sb.append("\n");
                sb.append(str2);
                b.a(sb.toString());
            }
            MQTTService.a(LogType.File, "服务器下行消息. %s", str2);
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (mQTTConnection != null) {
                mQTTConnection.c = true;
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    try {
                        jSONObject = new JSONObject(str2);
                    } catch (JSONException unused) {
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        if (str.equals("base/json")) {
                            final yl ylVar = new yl();
                            ylVar.a = jSONObject.optLong("protoId", 0);
                            ylVar.b = jSONObject.optInt("bizType", 0);
                            ylVar.c = jSONObject.optInt("prototype", 0);
                            ylVar.d = jSONObject.optString(LongLinkMsgConstants.LONGLINK_APPDATA, "");
                            ylVar.e = jSONObject.optInt("tcode", -1);
                            if (ylVar.c == 1) {
                                if (mQTTConnection.e != null && mQTTConnection.b) {
                                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                                        public final /* synthetic */ void onNotify(Object obj) {
                                            ((a) obj).a(ylVar.b, str, ylVar.d);
                                        }
                                    });
                                    return;
                                }
                            } else if (ylVar.c == 2) {
                                if (ylVar.a > 0) {
                                    synchronized (mQTTConnection.m) {
                                        protoInQueue = (ProtoInQueue) mQTTConnection.m.remove(Long.valueOf(ylVar.a));
                                    }
                                    if (protoInQueue != null) {
                                        mQTTConnection.a(ylVar.b, MQTTConnection.b(ylVar.e), str, !TextUtils.isEmpty(ylVar.d) ? ylVar.d : protoInQueue.f);
                                    }
                                    return;
                                }
                                mQTTConnection.a(ylVar.b, MQTTConnection.b(ylVar.e), str, ylVar.d);
                            }
                            return;
                        }
                        str.equals("amapauto/location");
                    }
                }
            }
        }

        public final void onConnected() {
            MQTTService.a((String) "MQTTConnection. InternalCallback. onConnected.", new Object[0]);
            if (bno.a) {
                ye.b().a((String) "MQTT：onConnected");
            }
            MQTTService.a(LogType.ConsoleAndFile, "服务器连接成功.", new Object[0]);
            AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", "服务器连接成功.");
            MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
            if (mQTTConnection != null) {
                mQTTConnection.c = true;
                if (mQTTConnection.i != null) {
                    mQTTConnection.i.b();
                }
                if (mQTTConnection.e != null && mQTTConnection.b) {
                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                        public final /* synthetic */ void onNotify(Object obj) {
                            ((a) obj).a();
                        }
                    });
                }
                if (bno.a && mQTTConnection != null) {
                    StringBuilder sb = new StringBuilder("连接耗时：");
                    sb.append(System.currentTimeMillis() - mQTTConnection.d);
                    AMapLog.e("AMAP*IM*MQTTConnection", sb.toString());
                }
            }
        }

        public final void onLog(String str) {
            if (MQTTService.a()) {
                AMapLog.e("AMAP*IM*MQTTConnection", "MQTT log:".concat(String.valueOf(str)));
            }
        }
    }

    static class c {
        WeakReference<MQTTConnection> a;
        int b = 0;
        private HandlerThread c = null;
        private Handler d = null;
        private final Object e = new Object();
        private Runnable f = new Runnable() {
            public final void run() {
                c.this.b++;
                MQTTConnection mQTTConnection = (MQTTConnection) c.this.a.get();
                if (mQTTConnection != null && !mQTTConnection.c) {
                    if (bno.a) {
                        ye b = ye.b();
                        StringBuilder sb = new StringBuilder("MQTT：daemonStart \n");
                        sb.append(c.this.b);
                        b.a(sb.toString());
                    }
                    MQTTService.a((String) "MQTTConnection. MQTTDaemon. reconnect. mRetryTimes: %s", Integer.valueOf(c.this.b));
                    MQTTService.a(LogType.ConsoleAndFile, "重连计时器触发. 重连次数: %s", Integer.valueOf(c.this.b));
                    mQTTConnection.a();
                }
            }
        };

        c(MQTTConnection mQTTConnection) {
            this.a = new WeakReference<>(mQTTConnection);
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void a() {
            MQTTService.a((String) "MQTTConnection. MQTTDaemon. daemonStart.", new Object[0]);
            if (this.c == null) {
                synchronized (this.e) {
                    if (this.c == null) {
                        MQTTService.a((String) "MQTTConnection. MQTTDaemon. daemonStart. init thread.", new Object[0]);
                        MQTTService.a(LogType.ConsoleAndFile, "开启重连记时器", new Object[0]);
                        this.c = new HandlerThread("MQTTDaemonThread");
                        this.c.start();
                        if (this.d == null) {
                            this.d = new Handler(this.c.getLooper());
                        }
                    }
                }
            }
            if (!(this.c == null || this.d == null)) {
                int i = this.b + 1;
                long j = (long) i;
                MQTTConnection mQTTConnection = (MQTTConnection) this.a.get();
                long j2 = 5000;
                if (!(mQTTConnection == null || mQTTConnection.f == null || mQTTConnection.f.a <= 0)) {
                    j2 = (long) (mQTTConnection.f.a * 1000);
                }
                long j3 = j * j2;
                if (j3 >= 60000) {
                    j3 = 60000;
                }
                MQTTService.a((String) "MQTTConnection. MQTTDaemon. daemonStart. postDelayed. mRetryTimes: %s, planTimes: %s, retryPeriod: %s", Integer.valueOf(this.b), Integer.valueOf(i), Long.valueOf(j3));
                MQTTService.a(LogType.ConsoleAndFile, "规划重连计时器. 重连次数: %s, 下次重连: %s ms后", Integer.valueOf(this.b), Long.valueOf(j3));
                this.d.removeCallbacks(this.f);
                this.d.postDelayed(this.f, j3);
            }
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void b() {
            if (bno.a) {
                ye b2 = ye.b();
                StringBuilder sb = new StringBuilder("MQTT：deamonQuit \n");
                sb.append(this.b);
                b2.a(sb.toString());
            }
            if (this.c != null) {
                synchronized (this.e) {
                    if (this.c != null) {
                        MQTTService.a((String) "MQTTConnection. MQTTDaemon. daemonQuit. mRetryTimes: %s", Integer.valueOf(this.b));
                        MQTTService.a(LogType.ConsoleAndFile, "关闭重连记时器. 重连次数: %s.", Integer.valueOf(this.b));
                        if (VERSION.SDK_INT >= 18) {
                            this.c.quitSafely();
                        } else {
                            this.c.quit();
                        }
                        this.c = null;
                        if (this.d != null) {
                            this.d.removeCallbacks(this.f);
                            this.d = null;
                        }
                        this.b = 0;
                    }
                }
            }
        }
    }

    class d extends TimerTask {
        d() {
        }

        public final void run() {
            ArrayList<ProtoInQueue> arrayList;
            long j;
            long j2;
            ArrayList arrayList2;
            ArrayList arrayList3;
            try {
                if (MQTTConnection.this.b) {
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList<ProtoInQueue> arrayList5 = new ArrayList<>();
                    if (MQTTConnection.this.k.size() > 0) {
                        synchronized (MQTTConnection.this.k) {
                            if (MQTTConnection.this.k.size() > 0) {
                                long currentTimeMillis = System.currentTimeMillis();
                                ArrayList<Long> arrayList6 = new ArrayList<>();
                                Iterator<Entry<Long, ProtoInQueue>> it = MQTTConnection.this.k.entrySet().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        arrayList2 = arrayList4;
                                        break;
                                    }
                                    Entry next = it.next();
                                    long longValue = ((Long) next.getKey()).longValue();
                                    arrayList2 = arrayList4;
                                    if (Math.abs(currentTimeMillis - ((ProtoInQueue) next.getValue()).g) < 10000) {
                                        break;
                                    }
                                    arrayList6.add(Long.valueOf(longValue));
                                    arrayList4 = arrayList2;
                                }
                                for (Long longValue2 : arrayList6) {
                                    ProtoInQueue protoInQueue = (ProtoInQueue) MQTTConnection.this.k.remove(Long.valueOf(longValue2.longValue()));
                                    if (protoInQueue != null) {
                                        protoInQueue.h = TIMEOUT_TYPE.PUBLISH;
                                        arrayList3 = arrayList2;
                                        arrayList3.add(protoInQueue);
                                    } else {
                                        arrayList3 = arrayList2;
                                    }
                                    arrayList2 = arrayList3;
                                }
                                arrayList = arrayList2;
                            } else {
                                arrayList = arrayList4;
                            }
                        }
                    } else {
                        arrayList = arrayList4;
                    }
                    if (MQTTConnection.this.l.size() > 0) {
                        synchronized (MQTTConnection.this.l) {
                            if (MQTTConnection.this.l.size() > 0) {
                                long currentTimeMillis2 = System.currentTimeMillis();
                                ArrayList<Integer> arrayList7 = new ArrayList<>();
                                ArrayList<Integer> arrayList8 = new ArrayList<>();
                                for (Entry next2 : MQTTConnection.this.l.entrySet()) {
                                    int intValue = ((Integer) next2.getKey()).intValue();
                                    ProtoInQueue protoInQueue2 = (ProtoInQueue) next2.getValue();
                                    if (Math.abs(currentTimeMillis2 - protoInQueue2.g) < 5000) {
                                        break;
                                    } else if (!protoInQueue2.b || protoInQueue2.c >= 3) {
                                        arrayList7.add(Integer.valueOf(intValue));
                                    } else {
                                        arrayList8.add(Integer.valueOf(intValue));
                                    }
                                }
                                for (Integer intValue2 : arrayList8) {
                                    ProtoInQueue protoInQueue3 = (ProtoInQueue) MQTTConnection.this.l.remove(Integer.valueOf(intValue2.intValue()));
                                    if (protoInQueue3 != null) {
                                        arrayList5.add(protoInQueue3);
                                    }
                                }
                                for (Integer intValue3 : arrayList7) {
                                    ProtoInQueue protoInQueue4 = (ProtoInQueue) MQTTConnection.this.l.remove(Integer.valueOf(intValue3.intValue()));
                                    if (protoInQueue4 != null) {
                                        protoInQueue4.h = TIMEOUT_TYPE.DELIVERY;
                                        arrayList.add(protoInQueue4);
                                    }
                                }
                            }
                        }
                    }
                    if (MQTTConnection.this.m.size() > 0) {
                        synchronized (MQTTConnection.this.m) {
                            if (MQTTConnection.this.m.size() > 0) {
                                long currentTimeMillis3 = System.currentTimeMillis();
                                ArrayList<Long> arrayList9 = new ArrayList<>();
                                for (Entry next3 : MQTTConnection.this.m.entrySet()) {
                                    long longValue3 = ((Long) next3.getKey()).longValue();
                                    long abs = Math.abs(currentTimeMillis3 - ((ProtoInQueue) next3.getValue()).g);
                                    MQTTConnection mQTTConnection = MQTTConnection.this;
                                    if (mQTTConnection.f == null || mQTTConnection.f.d <= 0) {
                                        j = currentTimeMillis3;
                                        j2 = 10000;
                                    } else {
                                        j = currentTimeMillis3;
                                        j2 = (long) (mQTTConnection.f.d * 1000);
                                    }
                                    if (abs < j2) {
                                        break;
                                    }
                                    arrayList9.add(Long.valueOf(longValue3));
                                    currentTimeMillis3 = j;
                                }
                                for (Long longValue4 : arrayList9) {
                                    ProtoInQueue protoInQueue5 = (ProtoInQueue) MQTTConnection.this.m.remove(Long.valueOf(longValue4.longValue()));
                                    if (protoInQueue5 != null) {
                                        protoInQueue5.h = TIMEOUT_TYPE.RESPONSE;
                                        arrayList.add(protoInQueue5);
                                    }
                                }
                            }
                        }
                    }
                    for (ProtoInQueue a2 : arrayList5) {
                        MQTTConnection.a(MQTTConnection.this, a2);
                    }
                    for (ProtoInQueue protoInQueue6 : arrayList) {
                        LogType logType = LogType.ConsoleAndFile;
                        Object[] objArr = new Object[1];
                        String str = "未知";
                        switch (protoInQueue6.h) {
                            case PUBLISH:
                                str = "客户端发送失败";
                                break;
                            case DELIVERY:
                                str = "服务器未下发ack";
                                break;
                            case RESPONSE:
                                str = "服务器未下发response";
                                break;
                        }
                        objArr[0] = str;
                        MQTTService.a(logType, "上行消息. 发送超时. 原因: %s", objArr);
                        MQTTConnection.this.a(protoInQueue6.d, MQTTResponseCode.TIMEOUT.value(), protoInQueue6.e, protoInQueue6.f);
                    }
                }
            } catch (Throwable unused) {
                MQTTService.b("MQTTConnection. mCheckTimer. error.", new Object[0]);
            }
        }
    }

    static /* synthetic */ String a(int i2) {
        switch (i2) {
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            case 4:
                return "WIFI";
            default:
                return "UNKNOWN";
        }
    }

    public final void a(final yk ykVar) {
        if (!this.b) {
            ahm.a(new Runnable() {
                public final void run() {
                    synchronized (MQTTConnection.this.j) {
                        if (!MQTTConnection.this.b) {
                            MQTTConnection mQTTConnection = MQTTConnection.this;
                            yk ykVar = ykVar;
                            if (!mQTTConnection.b) {
                                MQTTService.a((String) "MQTTConnection. init. config: %s %s %s %s", ykVar.a, ykVar.d, ykVar.b, ykVar.c);
                                MQTTService.a(LogType.File, "发起长链建立请求. clientID: %s, host: %s, 心跳: %s", ykVar.a, ykVar.d, Integer.valueOf(mQTTConnection.c()));
                                AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", String.format("发起长链建立请求. clientID: %s, host: %s, 心跳: %s", new Object[]{ykVar.a, ykVar.d, Integer.valueOf(mQTTConnection.c())}));
                                if (bno.a) {
                                    if (ykVar == null) {
                                        throw new IllegalArgumentException("MQTT配置不能为空");
                                    } else if (TextUtils.isEmpty(ykVar.a)) {
                                        throw new IllegalArgumentException("MQTT配置错误:ClinetID为空");
                                    } else if (TextUtils.isEmpty(ykVar.d)) {
                                        throw new IllegalArgumentException("MQTT配置错误:ServerHost为空");
                                    } else if (TextUtils.isEmpty(ykVar.b)) {
                                        throw new IllegalArgumentException("MQTT配置错误:MtqqPassword为空");
                                    }
                                }
                                if (TextUtils.isEmpty(ykVar.c)) {
                                    ykVar.c = "";
                                }
                                yj yjVar = mQTTConnection.f;
                                String a2 = lo.a().a((String) "amap_basemap_config");
                                MQTTService.a((String) "MQTTConnection. MQTTConnectionAocsConfig. parse. raw config: %s", a2);
                                yjVar.a(a2);
                                mQTTConnection.a = PushClient.getInstance();
                                mQTTConnection.h = new b(mQTTConnection);
                                mQTTConnection.i = new c(mQTTConnection);
                                mQTTConnection.a.registerCallback(mQTTConnection.h);
                                mQTTConnection.a.init(ykVar.a, ykVar.d, ykVar.b, mQTTConnection.c(), ykVar.c);
                                NetworkChangeReceiver networkChangeReceiver = mQTTConnection.g;
                                if (aaw.a()) {
                                    networkChangeReceiver.a = Integer.valueOf(aaw.b(AMapAppGlobal.getApplication()));
                                } else {
                                    networkChangeReceiver.a = Integer.valueOf(0);
                                }
                                AMapAppGlobal.getApplication().registerReceiver(mQTTConnection.g, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                                if (mQTTConnection.o == null) {
                                    synchronized (mQTTConnection.n) {
                                        if (mQTTConnection.o == null) {
                                            mQTTConnection.o = new Timer("MQTTConnection#CheckTimer");
                                        }
                                        if (mQTTConnection.p == null) {
                                            mQTTConnection.p = new d();
                                        }
                                    }
                                }
                                if (!(mQTTConnection.o == null || mQTTConnection.p == null)) {
                                    mQTTConnection.o.scheduleAtFixedRate(mQTTConnection.p, 2000, 2000);
                                }
                                mQTTConnection.b = true;
                                if (bno.a) {
                                    mQTTConnection.d = System.currentTimeMillis();
                                }
                                if (mQTTConnection.e != null) {
                                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                                        public final /* bridge */ /* synthetic */ void onNotify(Object obj) {
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public final boolean a() {
        if (this.a != null) {
            if (aaw.a()) {
                MQTTService.a((String) "MQTTConnection. reconnectMQTTClient.", new Object[0]);
                MQTTService.a(LogType.ConsoleAndFile, "发起重连.", new Object[0]);
                AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", "发起重连.");
                this.a.setKeepAliveInterval(c());
                this.a.reconnect();
                if (bno.a) {
                    this.d = System.currentTimeMillis();
                }
                return true;
            }
            MQTTService.a(LogType.ConsoleAndFile, "网络未连接, 取消重连.", new Object[0]);
            AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", "网络未连接, 取消重连.");
            if (this.i != null) {
                this.i.a();
            }
        }
        return false;
    }

    public final void b() {
        if (this.b) {
            ahm.a(new Runnable() {
                public final void run() {
                    LinkedHashMap linkedHashMap;
                    LinkedHashMap linkedHashMap2;
                    LinkedHashMap linkedHashMap3;
                    synchronized (MQTTConnection.this.j) {
                        if (MQTTConnection.this.b) {
                            MQTTConnection mQTTConnection = MQTTConnection.this;
                            if (mQTTConnection.b) {
                                mQTTConnection.b = false;
                                mQTTConnection.c = false;
                                MQTTService.a((String) "MQTTConnection. destroy.", new Object[0]);
                                MQTTService.a(LogType.ConsoleAndFile, "长链销毁", new Object[0]);
                                AMapLog.info("paas.mqtt", "AMAP*IM*MQTTConnection", "长链销毁");
                                mQTTConnection.a.unInit();
                                if (mQTTConnection.o != null) {
                                    synchronized (mQTTConnection.n) {
                                        if (mQTTConnection.o != null) {
                                            mQTTConnection.o.cancel();
                                            mQTTConnection.o = null;
                                        }
                                        if (mQTTConnection.p != null) {
                                            mQTTConnection.p.cancel();
                                            mQTTConnection.p = null;
                                        }
                                    }
                                }
                                AMapAppGlobal.getApplication().unregisterReceiver(mQTTConnection.g);
                                mQTTConnection.a = null;
                                if (mQTTConnection.i != null) {
                                    mQTTConnection.i.b();
                                }
                                mQTTConnection.i = null;
                                mQTTConnection.h = null;
                                synchronized (mQTTConnection.k) {
                                    linkedHashMap = new LinkedHashMap(mQTTConnection.k);
                                    mQTTConnection.k.clear();
                                }
                                synchronized (mQTTConnection.l) {
                                    linkedHashMap2 = new LinkedHashMap(mQTTConnection.l);
                                    mQTTConnection.l.clear();
                                }
                                synchronized (mQTTConnection.m) {
                                    linkedHashMap3 = new LinkedHashMap(mQTTConnection.m);
                                    mQTTConnection.m.clear();
                                }
                                mQTTConnection.a(linkedHashMap);
                                mQTTConnection.a(linkedHashMap2);
                                mQTTConnection.a(linkedHashMap3);
                                if (mQTTConnection.e != null) {
                                    mQTTConnection.e.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                                        public final /* bridge */ /* synthetic */ void onNotify(Object obj) {
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final int c() {
        int i2 = 45;
        int i3 = 30;
        if (this.f != null) {
            if (this.f.c > 0) {
                i2 = this.f.c;
            }
            if (this.f.b > 0) {
                i3 = this.f.b;
            }
        }
        switch (aaw.b(AMapAppGlobal.getApplication())) {
            case 3:
            case 4:
                return i3;
            default:
                return i2;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(LinkedHashMap linkedHashMap) {
        for (Entry value : linkedHashMap.entrySet()) {
            ProtoInQueue protoInQueue = (ProtoInQueue) value.getValue();
            MQTTService.a((String) "MQTTConnection. responseClearResendQueue. topic: %s, data: %s", protoInQueue.e, protoInQueue.f);
            MQTTService.a(LogType.ConsoleAndFile, "上行消息. 被移除出重发队列", new Object[0]);
            a(protoInQueue.d, MQTTResponseCode.INTERNAL.value(), protoInQueue.e, protoInQueue.f);
        }
    }

    public final void a(int i2, int i3, String str, String str2) {
        if (this.e != null && this.b) {
            agl<a> agl = this.e;
            final int i4 = i2;
            final int i5 = i3;
            final String str3 = str;
            final String str4 = str2;
            AnonymousClass5 r1 = new defpackage.agl.a<a>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((a) obj).a(i4, i5, str3, str4);
                }
            };
            agl.a((defpackage.agl.a<T>) r1);
        }
    }

    static /* synthetic */ int b(int i2) {
        if (i2 == 0) {
            return MQTTResponseCode.SUCCEED.value();
        }
        switch (i2) {
            case 101:
                return MQTTResponseCode.TIMEOUT.value();
            case 102:
                return MQTTResponseCode.SERVER_BUSY.value();
            case 103:
                return MQTTResponseCode.INTERNAL.value();
            default:
                return MQTTResponseCode.UNKNOWN.value();
        }
    }

    static /* synthetic */ void a(MQTTConnection mQTTConnection, ProtoInQueue protoInQueue) {
        if (protoInQueue != null) {
            if (mQTTConnection.b) {
                protoInQueue.c++;
                MQTTService.a((String) "MQTTConnection. republish. %s, %s, retryed: %s", protoInQueue.e, protoInQueue.f, Integer.valueOf(protoInQueue.c));
                MQTTService.a(LogType.File, "消息重发. 重发次数: %s. 数据: %s", Integer.valueOf(protoInQueue.c), protoInQueue.f);
                if (bno.a) {
                    AMapLog.debug("paas.mqtt", "AMAP*IM*MQTTConnection", String.format("消息重发. 重发次数: %s. 数据: %s", new Object[]{Integer.valueOf(protoInQueue.c), protoInQueue.f}));
                }
                synchronized (mQTTConnection.k) {
                    protoInQueue.g = System.currentTimeMillis();
                    mQTTConnection.k.put(Long.valueOf(protoInQueue.a), protoInQueue);
                }
                if (mQTTConnection.a != null) {
                    mQTTConnection.a.publishMessage(protoInQueue.e, protoInQueue.f, Long.valueOf(protoInQueue.a));
                }
            } else {
                MQTTService.a(LogType.ConsoleAndFile, "重发. 连接不可用", new Object[0]);
                mQTTConnection.a(protoInQueue.d, (mQTTConnection.b ? MQTTResponseCode.CONNECTION_CLOSED : MQTTResponseCode.NO_CONNECTION).value(), protoInQueue.e, protoInQueue.f);
            }
        }
    }
}
