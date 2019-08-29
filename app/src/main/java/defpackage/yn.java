package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import com.amap.bundle.mqtt.internal.MQTTConnection;
import org.json.JSONObject;

/* renamed from: yn reason: default package */
/* compiled from: MQTTBizWrapper */
public final class yn implements yg, ym {
    private static volatile long c;
    private a a;
    private yf b;

    /* renamed from: yn$a */
    /* compiled from: MQTTBizWrapper */
    public interface a {
        MQTTConnection a();

        MQTTConnectionStauts b();
    }

    public yn(yf yfVar, a aVar) {
        this.b = yfVar;
        this.a = aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r11, boolean r12) {
        /*
            r10 = this;
            yn$a r0 = r10.a
            if (r0 == 0) goto L_0x000b
            yn$a r0 = r10.a
            com.amap.bundle.mqtt.internal.MQTTConnection r0 = r0.a()
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 == 0) goto L_0x0146
            yf r1 = r10.b
            r1.getMQTTBizType()
            com.amap.bundle.mqtt.MQTTBizType r1 = com.amap.bundle.mqtt.MQTTBizType.TEAM
            yp r1 = new yp
            r1.<init>()
            long r2 = c
            r4 = 1
            long r2 = r2 + r4
            c = r2
            r1.a = r2
            yf r2 = r10.b
            com.amap.bundle.mqtt.MQTTBizType r2 = r2.getMQTTBizType()
            int r2 = r2.value()
            r1.b = r2
            r1.c = r11
            java.lang.String r11 = "base/json"
            boolean r2 = r0.b
            r3 = 0
            if (r2 == 0) goto L_0x00ef
            boolean r2 = defpackage.aaw.a()
            if (r2 == 0) goto L_0x00c3
            org.json.JSONObject r2 = r1.a()
            if (r2 == 0) goto L_0x0049
            java.lang.String r2 = r2.toString()
            goto L_0x004b
        L_0x0049:
            java.lang.String r2 = ""
        L_0x004b:
            java.lang.String r4 = "MQTTConnection. new publish. %s, %s %s"
            r5 = 3
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r6[r3] = r11
            r7 = 1
            r6[r7] = r2
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r12)
            r9 = 2
            r6[r9] = r8
            com.amap.bundle.mqtt.internal.service.MQTTService.a(r4, r6)
            boolean r4 = defpackage.bno.a
            if (r4 == 0) goto L_0x007c
            java.lang.String r4 = "AMAP*IM*MQTTConnection"
            java.lang.String r6 = "new publish. %s, %s %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r3] = r11
            r5[r7] = r2
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r12)
            r5[r9] = r7
            java.lang.String r5 = java.lang.String.format(r6, r5)
            java.lang.String r6 = "paas.mqtt"
            com.amap.bundle.logs.AMapLog.debug(r6, r4, r5)
        L_0x007c:
            boolean r4 = android.text.TextUtils.isEmpty(r11)
            if (r4 != 0) goto L_0x0125
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0125
            com.amap.bundle.mqtt.internal.MQTTConnection$ProtoInQueue r4 = new com.amap.bundle.mqtt.internal.MQTTConnection$ProtoInQueue
            r4.<init>(r3)
            long r5 = r1.a
            r4.a = r5
            r4.b = r12
            int r12 = r1.b
            r4.d = r12
            r4.e = r11
            r4.f = r2
            java.util.LinkedHashMap<java.lang.Long, com.amap.bundle.mqtt.internal.MQTTConnection$ProtoInQueue> r12 = r0.k
            monitor-enter(r12)
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c0 }
            r4.g = r5     // Catch:{ all -> 0x00c0 }
            java.util.LinkedHashMap<java.lang.Long, com.amap.bundle.mqtt.internal.MQTTConnection$ProtoInQueue> r3 = r0.k     // Catch:{ all -> 0x00c0 }
            long r5 = r4.a     // Catch:{ all -> 0x00c0 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x00c0 }
            r3.put(r5, r4)     // Catch:{ all -> 0x00c0 }
            monitor-exit(r12)     // Catch:{ all -> 0x00c0 }
            com.autonavi.mqtt.PushClient r12 = r0.a
            if (r12 == 0) goto L_0x0125
            com.autonavi.mqtt.PushClient r12 = r0.a
            long r3 = r4.a
            java.lang.Long r0 = java.lang.Long.valueOf(r3)
            r12.publishMessage(r11, r2, r0)
            goto L_0x0125
        L_0x00c0:
            r11 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x00c0 }
            throw r11
        L_0x00c3:
            org.json.JSONObject r12 = r1.a()
            if (r12 == 0) goto L_0x00ce
            java.lang.String r12 = r12.toString()
            goto L_0x00d0
        L_0x00ce:
            java.lang.String r12 = ""
        L_0x00d0:
            com.amap.bundle.mqtt.internal.service.MQTTService$LogType r2 = com.amap.bundle.mqtt.internal.service.MQTTService.LogType.ConsoleAndFile
            java.lang.String r4 = "上行消息. 网络不可用"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.amap.bundle.mqtt.internal.service.MQTTService.a(r2, r4, r3)
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x00e6
            java.lang.String r2 = "AMAP*IM*MQTTConnection"
            java.lang.String r3 = "上行消息. 网络不可用"
            java.lang.String r4 = "paas.mqtt"
            com.amap.bundle.logs.AMapLog.debug(r4, r2, r3)
        L_0x00e6:
            int r2 = r1.b
            com.amap.bundle.mqtt.internal.service.MQTTResponseCode r3 = com.amap.bundle.mqtt.internal.service.MQTTResponseCode.NETWORK
            int r3 = r3.value()
            goto L_0x0122
        L_0x00ef:
            org.json.JSONObject r12 = r1.a()
            if (r12 == 0) goto L_0x00fa
            java.lang.String r12 = r12.toString()
            goto L_0x00fc
        L_0x00fa:
            java.lang.String r12 = ""
        L_0x00fc:
            com.amap.bundle.mqtt.internal.service.MQTTService$LogType r2 = com.amap.bundle.mqtt.internal.service.MQTTService.LogType.ConsoleAndFile
            java.lang.String r4 = "上行消息. 连接不可用"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.amap.bundle.mqtt.internal.service.MQTTService.a(r2, r4, r3)
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x0112
            java.lang.String r2 = "AMAP*IM*MQTTConnection"
            java.lang.String r3 = "上行消息. 连接不可用"
            java.lang.String r4 = "paas.mqtt"
            com.amap.bundle.logs.AMapLog.debug(r4, r2, r3)
        L_0x0112:
            int r2 = r1.b
            boolean r3 = r0.b
            if (r3 == 0) goto L_0x011f
            com.amap.bundle.mqtt.internal.service.MQTTResponseCode r3 = com.amap.bundle.mqtt.internal.service.MQTTResponseCode.CONNECTION_CLOSED
        L_0x011a:
            int r3 = r3.value()
            goto L_0x0122
        L_0x011f:
            com.amap.bundle.mqtt.internal.service.MQTTResponseCode r3 = com.amap.bundle.mqtt.internal.service.MQTTResponseCode.NO_CONNECTION
            goto L_0x011a
        L_0x0122:
            r0.a(r2, r3, r11, r12)
        L_0x0125:
            boolean r11 = defpackage.bno.a
            if (r11 == 0) goto L_0x0146
            org.json.JSONObject r11 = r1.a()
            java.lang.String r12 = "MQTTBizWrapper"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "sendMessage: "
            r0.<init>(r1)
            java.lang.String r11 = java.lang.String.valueOf(r11)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            java.lang.String r0 = "paas.mqtt"
            com.amap.bundle.logs.AMapLog.debug(r0, r12, r11)
        L_0x0146:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yn.a(org.json.JSONObject, boolean):void");
    }

    public final MQTTConnectionStauts a() {
        return this.a != null ? this.a.b() : MQTTConnectionStauts.DISCONNECTED;
    }

    public final void a(MQTTConnectionStauts mQTTConnectionStauts) {
        if (this.b != null) {
            this.b.onConnectionStatusChanged(mQTTConnectionStauts);
        }
    }

    public final void a(JSONObject jSONObject) {
        if (this.b != null) {
            this.b.onMessageArrived(jSONObject);
            if (bno.a) {
                AMapLog.debug("paas.mqtt", "MQTTBizWrapper", "onMessageArrived: ".concat(String.valueOf(jSONObject)) == null ? "null" : jSONObject.toString());
            }
        }
    }

    public final void a(int i, JSONObject jSONObject) {
        if (this.b != null) {
            this.b.onResponse(i, jSONObject);
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onResponse, statusCode: ");
                sb.append(i);
                sb.append(", proto:");
                sb.append(jSONObject);
                AMapLog.debug("paas.mqtt", "MQTTBizWrapper", sb.toString() == null ? "null" : jSONObject.toString());
            }
        }
    }
}
