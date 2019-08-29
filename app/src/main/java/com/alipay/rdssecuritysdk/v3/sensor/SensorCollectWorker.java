package com.alipay.rdssecuritysdk.v3.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.rdssecuritysdk.v3.sensor.SensorCollectors.SensorType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class SensorCollectWorker implements SensorEventListener {
    String a;
    private TraceLogger b = LoggerFactory.f();
    private int c = 0;
    private volatile CollectStatus d = CollectStatus.COLLECT_NOT_START;
    private List<String> e = new ArrayList();
    private Sensor f = null;
    private SensorManager g;
    private ReentrantLock h = new ReentrantLock();

    enum CollectStatus {
        COLLECT_NOT_START,
        COLLECT_STARTED,
        COLLECT_FINISHED
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public SensorCollectWorker(SensorManager sensorManager, SensorType sensorType, int i) {
        if (sensorManager != null && sensorType != null) {
            this.g = sensorManager;
            this.f = sensorManager.getDefaultSensor(sensorType.getmSensorType());
            this.a = sensorType.getSensorName();
            this.c = i;
            if (this.f == null) {
                TraceLogger traceLogger = this.b;
                StringBuilder sb = new StringBuilder("SensorCollectWorker: ");
                sb.append(sensorType.getSensorName());
                sb.append(" 注册失败.［");
                sb.append(System.currentTimeMillis());
                sb.append("]");
                traceLogger.b((String) "RDSSensor", sb.toString());
                return;
            }
            TraceLogger traceLogger2 = this.b;
            StringBuilder sb2 = new StringBuilder("SensorCollectWorker: ");
            sb2.append(sensorType.getSensorName());
            sb2.append(" 注册成功.［");
            sb2.append(System.currentTimeMillis());
            sb2.append("]");
            traceLogger2.b((String) "RDSSensor", sb2.toString());
        }
    }

    public final void a() {
        if (this.f != null && this.g != null) {
            this.g.registerListener(this, this.f, 20000);
            TraceLogger traceLogger = this.b;
            StringBuilder sb = new StringBuilder("SensorCollectWorker: ");
            sb.append(this.a);
            sb.append(" 开始采集数据.［");
            sb.append(System.currentTimeMillis());
            sb.append("]");
            traceLogger.b((String) "RDSSensor", sb.toString());
        }
    }

    public final void b() {
        if (this.f != null && this.g != null) {
            this.g.unregisterListener(this, this.f);
            this.f = null;
            TraceLogger traceLogger = this.b;
            StringBuilder sb = new StringBuilder("SensorCollectWorker: ");
            sb.append(this.a);
            sb.append(" 停止采集数据.［");
            sb.append(System.currentTimeMillis());
            sb.append("]");
            traceLogger.b((String) "RDSSensor", sb.toString());
        }
    }

    public final String c() {
        if (this.f == null) {
            return "";
        }
        return this.a;
    }

    public final List<String> d() {
        try {
            this.h.lock();
            if (this.d == CollectStatus.COLLECT_NOT_START) {
                this.h.unlock();
                return null;
            }
            this.d = CollectStatus.COLLECT_FINISHED;
            this.h.unlock();
            TraceLogger traceLogger = this.b;
            StringBuilder sb = new StringBuilder("SensorCollectWorker: ");
            sb.append(this.a);
            sb.append(" 停止采集，采集到");
            sb.append(this.e.size());
            sb.append("条数据。［");
            sb.append(System.currentTimeMillis());
            sb.append("]");
            traceLogger.b((String) "RDSSensor", sb.toString());
            return this.e;
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.h.unlock();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r3.e.add(r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005a, code lost:
        if (r3.e.size() >= r3.c) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
        r3.d = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        r3.h.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006f, code lost:
        if (r3.e.size() >= r3.c) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0071, code lost:
        r3.d = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0075, code lost:
        r3.h.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007a, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0083, code lost:
        if (r3.e.size() < r3.c) goto L_0x0060;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0049 */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0066 A[ExcHandler: all (r4v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onSensorChanged(android.hardware.SensorEvent r4) {
        /*
            r3 = this;
            java.util.concurrent.locks.ReentrantLock r0 = r3.h     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            r0.lock()     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r0 = r3.d     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r1 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            if (r0 != r1) goto L_0x001f
            java.util.List<java.lang.String> r4 = r3.e
            int r4 = r4.size()
            int r0 = r3.c
            if (r4 < r0) goto L_0x0019
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r4 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED
            r3.d = r4
        L_0x0019:
            java.util.concurrent.locks.ReentrantLock r4 = r3.h
            r4.unlock()
            return
        L_0x001f:
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r0 = r3.d     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r1 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_NOT_START     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            if (r0 != r1) goto L_0x0029
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r0 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_STARTED     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            r3.d = r0     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
        L_0x0029:
            if (r4 == 0) goto L_0x0052
            float[] r0 = r4.values     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            if (r0 == 0) goto L_0x0052
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            r0.<init>()     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            r1 = 0
        L_0x0035:
            float[] r2 = r4.values     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            int r2 = r2.length     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            if (r1 >= r2) goto L_0x0049
            float[] r2 = r4.values     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            r2 = r2[r1]     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            r0.append(r2)     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            java.lang.String r2 = ","
            r0.append(r2)     // Catch:{ Throwable -> 0x0049, all -> 0x0066 }
            int r1 = r1 + 1
            goto L_0x0035
        L_0x0049:
            java.util.List<java.lang.String> r4 = r3.e     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
            r4.add(r0)     // Catch:{ Throwable -> 0x007b, all -> 0x0066 }
        L_0x0052:
            java.util.List<java.lang.String> r4 = r3.e
            int r4 = r4.size()
            int r0 = r3.c
            if (r4 < r0) goto L_0x0060
        L_0x005c:
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r4 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED
            r3.d = r4
        L_0x0060:
            java.util.concurrent.locks.ReentrantLock r4 = r3.h
            r4.unlock()
            return
        L_0x0066:
            r4 = move-exception
            java.util.List<java.lang.String> r0 = r3.e
            int r0 = r0.size()
            int r1 = r3.c
            if (r0 < r1) goto L_0x0075
            com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker$CollectStatus r0 = com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.CollectStatus.COLLECT_FINISHED
            r3.d = r0
        L_0x0075:
            java.util.concurrent.locks.ReentrantLock r0 = r3.h
            r0.unlock()
            throw r4
        L_0x007b:
            java.util.List<java.lang.String> r4 = r3.e
            int r4 = r4.size()
            int r0 = r3.c
            if (r4 < r0) goto L_0x0060
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.rdssecuritysdk.v3.sensor.SensorCollectWorker.onSensorChanged(android.hardware.SensorEvent):void");
    }
}
