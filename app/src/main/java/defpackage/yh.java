package defpackage;

import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.internal.MQTTConnection;
import com.amap.bundle.mqtt.internal.service.MQTTService;
import com.amap.bundle.mqtt.internal.service.MQTTService.LogType;

/* renamed from: yh reason: default package */
/* compiled from: MQTT */
public final class yh {
    private static MQTTService a = new MQTTService();

    public static yg a(yf yfVar) {
        return a.a(yfVar);
    }

    public static boolean a(MQTTBizType mQTTBizType) {
        return a.a(mQTTBizType);
    }

    public static void a() {
        MQTTConnection mQTTConnection = a.a;
        if (mQTTConnection.b && !mQTTConnection.c) {
            MQTTService.a((String) "MQTTConnection. tryReconnect.", new Object[0]);
            MQTTService.a(LogType.ConsoleAndFile, "尝试主动重连", new Object[0]);
            mQTTConnection.a();
        }
    }

    public static void a(boolean z) {
        MQTTService.a(z);
    }

    public static void a(yi yiVar) {
        a.a(yiVar);
    }
}
