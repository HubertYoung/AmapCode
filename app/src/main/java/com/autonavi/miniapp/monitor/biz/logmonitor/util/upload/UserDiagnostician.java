package com.autonavi.miniapp.monitor.biz.logmonitor.util.upload;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;
import com.autonavi.miniapp.monitor.biz.logmonitor.TraceStubReceiver;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.logcat.LogcatDumpManager;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.AnrTracer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.StackTracer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.storage.FileRetriever;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.tracing.TracingUploader;
import com.autonavi.miniapp.monitor.util.MonitorUtils;
import com.autonavi.widget.ui.BalloonLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserDiagnostician {
    private static UserDiagnostician INSTANCE = null;
    public static final String KEY_RETRIEVE_FILE_PATH = "retrieveFilePath";
    public static final String KEY_STACK_TRACER_INTERVAL = "stackTracerInterval";
    public static final String KEY_STACK_TRACER_TIME = "stackTracerTime";
    public static final String KEY_TRACE_SIZE = "traceviewSize";
    public static final String KEY_TRACE_TIME = "traceviewTime";
    private static final long MANUAL_ZIPPED_LENGTH = 10485760;
    /* access modifiers changed from: private */
    public static final long POSITIVE_TRAFFIC_LOG_SPAN = TimeUnit.HOURS.toMillis(1);
    private static final long POSITIVE_UPLOAD_SPAN = TimeUnit.MINUTES.toMillis(10);
    private static final String TAG = "UserDiagnostician";
    /* access modifiers changed from: private */
    public static final long TASK_FROMTIME_ROLL_TIMESPAN = TimeUnit.HOURS.toMillis(1);
    /* access modifiers changed from: private */
    public static final long VALUE_STACK_TRACE_INTERVAL = TimeUnit.SECONDS.toMillis(10);
    /* access modifiers changed from: private */
    public static final long VALUE_STACK_TRACE_TIME = TimeUnit.MINUTES.toMillis(5);
    private static final int VALUE_TRACE_SIZE = 8388608;
    /* access modifiers changed from: private */
    public static final long VALUE_TRACE_TIME = TimeUnit.SECONDS.toMillis(20);
    private Context mContext;
    private long mLeaveHintTime;
    /* access modifiers changed from: private */
    public long mTrafficLogTime;

    public static final class DiagnoseTask {
        public String accountName;
        public String bizType;
        public String fileName;
        public long fromTime;
        public Code fromType;
        public boolean isForceUpload;
        public boolean isPositive;
        public String networkCondition;
        public String retrieveFilePath;
        public long stackTracerInterval = UserDiagnostician.VALUE_STACK_TRACE_INTERVAL;
        public long stackTracerTime = UserDiagnostician.VALUE_STACK_TRACE_TIME;
        public String taskID;
        public String taskType;
        public long toTime;
        public int traceSize = 8388608;
        public long traceTime = UserDiagnostician.VALUE_TRACE_TIME;
        public String userID;
        public long zippedLenLimit;

        public final String toString() {
            return MonitorUtils.concatArray(",", this.userID, this.taskID, this.taskType, this.fileName, this.networkCondition, Boolean.valueOf(this.isForceUpload), Long.valueOf(this.fromTime), Long.valueOf(this.toTime), this.fromType, Long.valueOf(this.traceTime), Integer.valueOf(this.traceSize), Long.valueOf(this.stackTracerTime), Long.valueOf(this.stackTracerInterval), this.retrieveFilePath, Boolean.valueOf(this.isPositive), this.accountName, Long.valueOf(this.zippedLenLimit));
        }
    }

    public static synchronized UserDiagnostician createInstance(Context context) {
        UserDiagnostician userDiagnostician;
        synchronized (UserDiagnostician.class) {
            if (INSTANCE == null) {
                INSTANCE = new UserDiagnostician(context);
            }
            userDiagnostician = INSTANCE;
        }
        return userDiagnostician;
    }

    public static UserDiagnostician getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new IllegalStateException("need createInstance befor use");
    }

    private UserDiagnostician(Context context) {
        this.mContext = context;
    }

    private boolean isForceUpload(String str) {
        return TextUtils.isEmpty(str) || "mobile".equalsIgnoreCase(str) || "any".equalsIgnoreCase(str);
    }

    public void asyncAckResult(final DiagnoseTask diagnoseTask, final Code code, final String str) {
        APMTimer.getInstance().post(new Runnable() {
            public void run() {
                UserDiagnostician.this.ackResult(diagnoseTask, code, str);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0215, code lost:
        if (r9 == null) goto L_0x024c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r9.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0249, code lost:
        if (r9 != null) goto L_0x0217;
     */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0241 A[SYNTHETIC, Splitter:B:69:0x0241] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0246 A[SYNTHETIC, Splitter:B:73:0x0246] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0250 A[SYNTHETIC, Splitter:B:81:0x0250] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0255 A[SYNTHETIC, Splitter:B:85:0x0255] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x025a A[SYNTHETIC, Splitter:B:89:0x025a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String ackResult(com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask r12, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code r13, java.lang.String r14) {
        /*
            r11 = this;
            if (r12 != 0) goto L_0x0018
            java.lang.String r12 = "diagnoseTask is null"
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r14 = "UserDiagnostician"
            java.lang.String r0 = "ackResult: "
            java.lang.String r1 = java.lang.String.valueOf(r12)
            java.lang.String r0 = r0.concat(r1)
            r13.error(r14, r0)
            return r12
        L_0x0018:
            boolean r0 = r12.isPositive
            if (r0 == 0) goto L_0x001f
            java.lang.String r12 = "isPositive is true"
            return r12
        L_0x001f:
            int[] r0 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.AnonymousClass9.$SwitchMap$com$autonavi$miniapp$monitor$api$analysis$diagnose$UploadTaskStatus$Code
            int r1 = r13.ordinal()
            r0 = r0[r1]
            switch(r0) {
                case 1: goto L_0x006d;
                case 2: goto L_0x006a;
                case 3: goto L_0x0067;
                case 4: goto L_0x005d;
                case 5: goto L_0x005a;
                case 6: goto L_0x0057;
                case 7: goto L_0x0054;
                case 8: goto L_0x0051;
                case 9: goto L_0x004d;
                case 10: goto L_0x004a;
                case 11: goto L_0x0040;
                case 12: goto L_0x0036;
                case 13: goto L_0x0033;
                case 14: goto L_0x0030;
                case 15: goto L_0x002d;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.String r0 = "-1"
            goto L_0x006f
        L_0x002d:
            java.lang.String r0 = "210"
            goto L_0x006f
        L_0x0030:
            java.lang.String r0 = "207"
            goto L_0x006f
        L_0x0033:
            java.lang.String r0 = "206"
            goto L_0x006f
        L_0x0036:
            java.lang.String r0 = "204"
            com.alipay.mobile.common.logging.api.LogContext r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            r1.adjustRequestSpanByReceived()
            goto L_0x006f
        L_0x0040:
            java.lang.String r0 = "203"
            com.alipay.mobile.common.logging.api.LogContext r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            r1.adjustRequestSpanByReceived()
            goto L_0x006f
        L_0x004a:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x004d:
            java.lang.String r0 = "true"
            goto L_0x006f
        L_0x0051:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x0054:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x0057:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x005a:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x005d:
            java.lang.String r0 = "205"
            com.alipay.mobile.common.logging.api.LogContext r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            r1.adjustRequestSpanByNetNotMatch()
            goto L_0x006f
        L_0x0067:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x006a:
            java.lang.String r0 = "false"
            goto L_0x006f
        L_0x006d:
            java.lang.String r0 = "false"
        L_0x006f:
            java.lang.String r1 = com.autonavi.miniapp.monitor.util.NetUtils.getNetworkType()
            com.alipay.mobile.common.logging.api.LogContext r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            java.lang.String r2 = r2.getProductVersion()
            com.alipay.mobile.common.logging.api.ProcessInfo r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()
            java.lang.String r3 = r3.getProcessAlias()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "type: "
            r4.append(r5)
            java.lang.String r5 = r12.taskType
            r4.append(r5)
            java.lang.String r5 = ", process: "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r5 = ", network: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = ", from: "
            r4.append(r5)
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r5 = r12.fromType
            r4.append(r5)
            java.lang.String r5 = ", product: "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = ", code: "
            r4.append(r5)
            r4.append(r13)
            java.lang.String r13 = ", status: "
            r4.append(r13)
            r4.append(r0)
            java.lang.String r13 = " # "
            r4.append(r13)
            r4.append(r14)
            java.lang.String r13 = r4.toString()
            r14 = 0
            r4.setLength(r14)
            java.lang.String r5 = "diagnoseStatus: "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = ", diagnoseMsg: "
            r4.append(r5)
            r4.append(r13)
            java.lang.String r5 = ", diagnoseTask: "
            r4.append(r5)
            r4.append(r12)
            java.lang.String r8 = r4.toString()
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            java.lang.String r6 = "ackResult: "
            java.lang.String r7 = java.lang.String.valueOf(r8)
            java.lang.String r6 = r6.concat(r7)
            r4.info(r5, r6)
            r4 = 0
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r5.<init>()     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r6 = "userId"
            java.lang.String r7 = r12.userID     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r5.put(r6, r7)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r6 = "taskId"
            java.lang.String r7 = r12.taskID     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r5.put(r6, r7)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r6 = "type"
            java.lang.String r7 = r12.taskType     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r5.put(r6, r7)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r6 = "isSuccess"
            r5.put(r6, r0)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r6 = "diagnoseMsg"
            r5.put(r6, r13)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r13 = "networkType"
            r5.put(r13, r1)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r13 = "fromType"
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = r12.fromType     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r5.put(r13, r1)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r13 = "productVer"
            r5.put(r13, r2)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r13 = "process"
            r5.put(r13, r3)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.lang.String r13 = com.autonavi.miniapp.monitor.util.NetUtils.formatParamStringForGET(r5)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = r12.fromType     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r2 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_MANUAL     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r3 = 1
            if (r1 != r2) goto L_0x0153
            r1 = 1
            goto L_0x0154
        L_0x0153:
            r1 = 0
        L_0x0154:
            java.lang.String r1 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants.getReportUrl(r1)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            java.net.URLConnection r1 = r2.openConnection()     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r9 = r1
            java.net.HttpURLConnection r9 = (java.net.HttpURLConnection) r9     // Catch:{ Throwable -> 0x0231, all -> 0x022d }
            r9.setDoInput(r3)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r9.setDoOutput(r3)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r9.setUseCaches(r14)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.lang.String r14 = "POST"
            r9.setRequestMethod(r14)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.lang.String r14 = "Connection"
            java.lang.String r1 = "Keep-Alive"
            r9.setRequestProperty(r14, r1)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.lang.String r14 = "Charset"
            java.lang.String r1 = "UTF-8"
            r9.setRequestProperty(r14, r1)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.lang.String r14 = "Cache-Control"
            java.lang.String r1 = "no-cache"
            r9.setRequestProperty(r14, r1)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r14 = 30000(0x7530, float:4.2039E-41)
            r9.setConnectTimeout(r14)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r14 = 60000(0xea60, float:8.4078E-41)
            r9.setReadTimeout(r14)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r9.connect()     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.io.DataOutputStream r14 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            java.io.OutputStream r2 = r9.getOutputStream()     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r14.<init>(r1)     // Catch:{ Throwable -> 0x022a, all -> 0x0227 }
            r14.writeBytes(r13)     // Catch:{ Throwable -> 0x0223, all -> 0x021f }
            r14.flush()     // Catch:{ Throwable -> 0x0223, all -> 0x021f }
            java.io.InputStream r13 = r9.getInputStream()     // Catch:{ Throwable -> 0x0223, all -> 0x021f }
            int r10 = r9.getResponseCode()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r1 = 200(0xc8, float:2.8E-43)
            if (r10 == r1) goto L_0x020d
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r2 = "UserDiagnostician"
            java.lang.String r3 = "ackResult responseCode: "
            java.lang.String r4 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r1.error(r2, r3)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r7.<init>()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r1 = "responseCode"
            java.lang.String r2 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r7.put(r1, r2)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            com.alipay.mobile.common.logging.api.monitor.MonitorLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getMonitorLogger()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r2 = r12.taskType     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r3 = "Diagnose"
            java.lang.String r4 = r12.taskID     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r5 = r12.userID     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r6 = r0
            r1.footprint(r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r2 = "UserDiagnostician"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r3.<init>()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r12 = r12.taskID     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r3.append(r12)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r12 = ", ackResult, "
            r3.append(r12)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r3.append(r0)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r12 = ", responseCode: "
            r3.append(r12)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r3.append(r10)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            java.lang.String r12 = r3.toString()     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
            r1.error(r2, r12)     // Catch:{ Throwable -> 0x021d, all -> 0x021b }
        L_0x020d:
            r14.close()     // Catch:{ Throwable -> 0x0210 }
        L_0x0210:
            if (r13 == 0) goto L_0x0215
            r13.close()     // Catch:{ Throwable -> 0x0215 }
        L_0x0215:
            if (r9 == 0) goto L_0x024c
        L_0x0217:
            r9.disconnect()     // Catch:{ Throwable -> 0x024c }
            goto L_0x024c
        L_0x021b:
            r12 = move-exception
            goto L_0x0221
        L_0x021d:
            r12 = move-exception
            goto L_0x0225
        L_0x021f:
            r12 = move-exception
            r13 = r4
        L_0x0221:
            r4 = r14
            goto L_0x024e
        L_0x0223:
            r12 = move-exception
            r13 = r4
        L_0x0225:
            r4 = r14
            goto L_0x0234
        L_0x0227:
            r12 = move-exception
            r13 = r4
            goto L_0x024e
        L_0x022a:
            r12 = move-exception
            r13 = r4
            goto L_0x0234
        L_0x022d:
            r12 = move-exception
            r13 = r4
            r9 = r13
            goto L_0x024e
        L_0x0231:
            r12 = move-exception
            r13 = r4
            r9 = r13
        L_0x0234:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r14 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x024d }
            java.lang.String r0 = "UserDiagnostician"
            java.lang.String r1 = "ackResult: http fail"
            r14.error(r0, r1, r12)     // Catch:{ all -> 0x024d }
            if (r4 == 0) goto L_0x0244
            r4.close()     // Catch:{ Throwable -> 0x0244 }
        L_0x0244:
            if (r13 == 0) goto L_0x0249
            r13.close()     // Catch:{ Throwable -> 0x0249 }
        L_0x0249:
            if (r9 == 0) goto L_0x024c
            goto L_0x0217
        L_0x024c:
            return r8
        L_0x024d:
            r12 = move-exception
        L_0x024e:
            if (r4 == 0) goto L_0x0253
            r4.close()     // Catch:{ Throwable -> 0x0253 }
        L_0x0253:
            if (r13 == 0) goto L_0x0258
            r13.close()     // Catch:{ Throwable -> 0x0258 }
        L_0x0258:
            if (r9 == 0) goto L_0x025d
            r9.disconnect()     // Catch:{ Throwable -> 0x025d }
        L_0x025d:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.ackResult(com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code, java.lang.String):java.lang.String");
    }

    public void processLeaveHint() {
        try {
            if (LoggerFactory.getLogContext().isPositiveDiagnose()) {
                final long currentTimeMillis = System.currentTimeMillis();
                if (Math.abs(currentTimeMillis - this.mLeaveHintTime) >= POSITIVE_UPLOAD_SPAN) {
                    this.mLeaveHintTime = currentTimeMillis;
                    APMTimer.getInstance().post(new Runnable() {
                        public void run() {
                            UserDiagnostician.this.processLeaveHintCore("applog", currentTimeMillis - UserDiagnostician.TASK_FROMTIME_ROLL_TIMESPAN);
                            if (Math.abs(currentTimeMillis - UserDiagnostician.this.mTrafficLogTime) > UserDiagnostician.POSITIVE_TRAFFIC_LOG_SPAN) {
                                UserDiagnostician.this.mTrafficLogTime = currentTimeMillis;
                                UserDiagnostician.this.processLeaveHintCore("trafficLog", currentTimeMillis);
                            }
                        }
                    });
                }
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "processLeaveHint", th);
        }
    }

    /* access modifiers changed from: private */
    public void processLeaveHintCore(String str, long j) {
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("processLeaveHint: ");
        sb.append(str);
        sb.append(", toTime: ");
        sb.append(j);
        traceLogger.info(TAG, sb.toString());
        try {
            DiagnoseTask diagnoseTask = new DiagnoseTask();
            diagnoseTask.userID = LoggerFactory.getLogContext().getUserId();
            diagnoseTask.taskID = "positive";
            diagnoseTask.taskType = str;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(diagnoseTask.userID);
            sb2.append("-");
            sb2.append(diagnoseTask.taskType);
            diagnoseTask.fileName = sb2.toString();
            diagnoseTask.networkCondition = "any";
            diagnoseTask.isForceUpload = isForceUpload(diagnoseTask.networkCondition);
            diagnoseTask.fromTime = 0;
            diagnoseTask.toTime = j;
            diagnoseTask.fromType = Code.TASK_BY_POSITIVE;
            diagnoseTask.isPositive = true;
            uploadLog(diagnoseTask, null);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "processLeaveHintCore", th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:109:0x021d  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0162  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processManualTrigger(android.os.Bundle r21, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r22) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            r3 = r22
            if (r2 != 0) goto L_0x0012
            java.lang.String r2 = "params is null"
            if (r3 == 0) goto L_0x0011
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x0011:
            return
        L_0x0012:
            com.alipay.mobile.common.logging.api.ProcessInfo r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()
            boolean r4 = r4.isMainProcess()
            if (r4 != 0) goto L_0x003f
            com.alipay.mobile.common.logging.api.ProcessInfo r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()
            java.lang.String r2 = r2.getProcessAlias()
            java.lang.String r4 = "processManualTrigger: not main process, "
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r4.concat(r2)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            r4.error(r5, r2)
            if (r3 == 0) goto L_0x003e
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x003e:
            return
        L_0x003f:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r4 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r4 = r4.getName()
            java.lang.Object r4 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)
            com.alipay.mobile.nebula.provider.H5ConfigProvider r4 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r4
            if (r4 == 0) goto L_0x006f
            java.lang.String r5 = "UserDiagnose_ManualDisable_All"
            java.lang.String r5 = r4.getConfig(r5)
            java.lang.String r6 = "true"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x006f
            java.lang.String r2 = "processManualTrigger: config is disable for all"
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            r4.error(r5, r2)
            if (r3 == 0) goto L_0x006e
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x006e:
            return
        L_0x006f:
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            java.lang.String r7 = ""
            java.lang.String r8 = ""
            java.lang.String r11 = "bizType"
            boolean r11 = r2.containsKey(r11)     // Catch:{ Throwable -> 0x00fa }
            if (r11 == 0) goto L_0x0086
            java.lang.String r11 = "bizType"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x00fa }
            r5 = r11
        L_0x0086:
            java.lang.String r11 = "taskType"
            boolean r11 = r2.containsKey(r11)     // Catch:{ Throwable -> 0x00fa }
            if (r11 == 0) goto L_0x0097
            java.lang.String r11 = "taskType"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x00fa }
            r6 = r11
        L_0x0097:
            java.lang.String r11 = "networkCondition"
            boolean r11 = r2.containsKey(r11)     // Catch:{ Throwable -> 0x00fa }
            if (r11 == 0) goto L_0x00a6
            java.lang.String r11 = "networkCondition"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x00fa }
            r7 = r11
        L_0x00a6:
            java.lang.String r11 = "fromTime"
            boolean r11 = r2.containsKey(r11)     // Catch:{ Throwable -> 0x00fa }
            if (r11 == 0) goto L_0x00b5
            java.lang.String r11 = "fromTime"
            long r11 = r2.getLong(r11)     // Catch:{ Throwable -> 0x00fa }
            goto L_0x00b7
        L_0x00b5:
            r11 = -1
        L_0x00b7:
            java.lang.String r13 = "toTime"
            boolean r13 = r2.containsKey(r13)     // Catch:{ Throwable -> 0x00f4 }
            if (r13 == 0) goto L_0x00c8
            java.lang.String r13 = "toTime"
            long r13 = r2.getLong(r13)     // Catch:{ Throwable -> 0x00f4 }
            goto L_0x00ca
        L_0x00c8:
            r13 = -1
        L_0x00ca:
            java.lang.String r15 = "accountName"
            boolean r15 = r2.containsKey(r15)     // Catch:{ Throwable -> 0x00f0 }
            if (r15 == 0) goto L_0x00d9
            java.lang.String r15 = "accountName"
            java.lang.String r15 = r2.getString(r15)     // Catch:{ Throwable -> 0x00f0 }
            r8 = r15
        L_0x00d9:
            java.lang.String r15 = "zippedLenLimit"
            boolean r15 = r2.containsKey(r15)     // Catch:{ Throwable -> 0x00f0 }
            if (r15 == 0) goto L_0x00eb
            java.lang.String r15 = "zippedLenLimit"
            long r15 = r2.getLong(r15)     // Catch:{ Throwable -> 0x00f0 }
            r9 = r15
            goto L_0x00ed
        L_0x00eb:
            r9 = -1
        L_0x00ed:
            r17 = r9
            goto L_0x0115
        L_0x00f0:
            r0 = move-exception
            r14 = r13
            r12 = r11
            goto L_0x00f8
        L_0x00f4:
            r0 = move-exception
            r12 = r11
            r14 = -1
        L_0x00f8:
            r11 = r8
            goto L_0x0100
        L_0x00fa:
            r0 = move-exception
            r11 = r8
            r12 = -1
            r14 = -1
        L_0x0100:
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "UserDiagnostician"
            r9.error(r10, r5)
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r11
            r11 = r12
            r13 = r14
            r17 = -1
        L_0x0115:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "processManualTrigger"
            r9.<init>(r10)
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            java.lang.String r15 = "yyyy-MM-dd HH:mm:ss:SSS"
            r10.<init>(r15)
            java.util.Date r15 = new java.util.Date     // Catch:{ Throwable -> 0x0149 }
            r15.<init>(r11)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r15 = r10.format(r15)     // Catch:{ Throwable -> 0x0149 }
            r19 = r8
            java.util.Date r8 = new java.util.Date     // Catch:{ Throwable -> 0x014b }
            r8.<init>(r13)     // Catch:{ Throwable -> 0x014b }
            java.lang.String r8 = r10.format(r8)     // Catch:{ Throwable -> 0x014b }
            java.lang.String r10 = ", fromTimeS: "
            r9.append(r10)     // Catch:{ Throwable -> 0x014b }
            r9.append(r15)     // Catch:{ Throwable -> 0x014b }
            java.lang.String r10 = ", toTimeS: "
            r9.append(r10)     // Catch:{ Throwable -> 0x014b }
            r9.append(r8)     // Catch:{ Throwable -> 0x014b }
            goto L_0x014b
        L_0x0149:
            r19 = r8
        L_0x014b:
            r8 = 0
            com.autonavi.miniapp.monitor.util.MonitorUtils.fillBufferWithParams(r9, r2, r8)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "UserDiagnostician"
            java.lang.String r9 = r9.toString()
            r2.info(r8, r9)
            boolean r2 = android.text.TextUtils.isEmpty(r5)
            if (r2 != 0) goto L_0x0267
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x0267
            r8 = 0
            int r2 = (r11 > r8 ? 1 : (r11 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0267
            int r2 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0267
            int r2 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r2 >= 0) goto L_0x0178
            goto L_0x0267
        L_0x0178:
            if (r4 == 0) goto L_0x01ac
            java.lang.String r2 = "UserDiagnose_ManualDisable_Biz_"
            java.lang.String r10 = java.lang.String.valueOf(r5)
            java.lang.String r2 = r2.concat(r10)
            java.lang.String r2 = r4.getConfig(r2)
            java.lang.String r10 = "true"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x01ac
            java.lang.String r2 = "processManualTrigger: config is disable for biz, "
            java.lang.String r4 = java.lang.String.valueOf(r5)
            java.lang.String r2 = r2.concat(r4)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            r4.error(r5, r2)
            if (r3 == 0) goto L_0x01ab
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x01ab:
            return
        L_0x01ac:
            boolean r2 = r1.isForceUpload(r7)
            if (r2 == 0) goto L_0x020d
            if (r4 == 0) goto L_0x01d6
            java.lang.String r10 = "UserDiagnose_ManualDisable_Force"
            java.lang.String r10 = r4.getConfig(r10)
            java.lang.String r15 = "true"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x01d6
            java.lang.String r2 = "processManualTrigger: config is disable for force"
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            r4.error(r5, r2)
            if (r3 == 0) goto L_0x01d5
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x01d5:
            return
        L_0x01d6:
            int r8 = (r17 > r8 ? 1 : (r17 == r8 ? 0 : -1))
            if (r8 > 0) goto L_0x020d
            r17 = 10485760(0xa00000, double:5.180654E-317)
            if (r4 == 0) goto L_0x020d
            java.lang.String r8 = "UserDiagnose_Manual_ZippedLenLimit"
            java.lang.String r4 = r4.getConfig(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r8 != 0) goto L_0x020d
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "UserDiagnostician"
            java.lang.String r10 = "processManualTrigger, config ZippedLenLimit: "
            java.lang.String r15 = java.lang.String.valueOf(r4)
            java.lang.String r10 = r10.concat(r15)
            r8.info(r9, r10)
            long r8 = java.lang.Long.parseLong(r4)     // Catch:{ Throwable -> 0x0203 }
            goto L_0x020f
        L_0x0203:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "UserDiagnostician"
            r4.error(r8, r0)
        L_0x020d:
            r8 = r17
        L_0x020f:
            com.alipay.mobile.common.logging.api.LogContext r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            java.lang.String r4 = r4.getUserId()
            boolean r10 = android.text.TextUtils.isEmpty(r4)
            if (r10 == 0) goto L_0x021f
            r4 = r19
        L_0x021f:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r10 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask
            r10.<init>()
            r10.userID = r4
            java.lang.String r4 = "manual"
            r10.taskID = r4
            r10.taskType = r6
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = r10.userID
            r4.append(r6)
            java.lang.String r6 = "-"
            r4.append(r6)
            java.lang.String r6 = r10.taskType
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r10.fileName = r4
            r10.networkCondition = r7
            r10.isForceUpload = r2
            r10.fromTime = r11
            r10.toTime = r13
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r2 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_MANUAL
            r10.fromType = r2
            r11 = r19
            r10.accountName = r11
            r10.zippedLenLimit = r8
            r10.bizType = r5
            com.autonavi.miniapp.monitor.biz.apm.util.APMTimer r2 = com.autonavi.miniapp.monitor.biz.apm.util.APMTimer.getInstance()
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$3 r4 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$3
            r4.<init>(r10, r3)
            r2.post(r4)
            return
        L_0x0267:
            java.lang.String r2 = "processManualTrigger: invalid params"
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "UserDiagnostician"
            r4.error(r5, r2)
            if (r3 == 0) goto L_0x0279
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR
            r3.onFail(r4, r2)
        L_0x0279:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.processManualTrigger(android.os.Bundle, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus):void");
    }

    public void processConfigMsg(String str, String str2) {
        JSONArray jSONArray;
        SimpleDateFormat simpleDateFormat;
        JSONArray jSONArray2;
        int i;
        String str3;
        long j;
        LoggerFactory.getTraceLogger().info(TAG, "processConfigMsg: ".concat(String.valueOf(str2)));
        ArrayList arrayList = new ArrayList();
        try {
            jSONArray = new JSONArray(str2);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
            jSONArray = null;
        }
        if (jSONArray != null && jSONArray.length() > 0) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int i2 = 0;
            while (i2 < jSONArray.length()) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    if (jSONObject != null) {
                        DiagnoseTask diagnoseTask = new DiagnoseTask();
                        try {
                            diagnoseTask.userID = str;
                            diagnoseTask.taskID = jSONObject.getString("taskId");
                            diagnoseTask.taskType = jSONObject.getString("type");
                            StringBuilder sb = new StringBuilder();
                            sb.append(diagnoseTask.taskID);
                            sb.append("_");
                            sb.append(diagnoseTask.taskType);
                            diagnoseTask.fileName = sb.toString();
                            diagnoseTask.networkCondition = jSONObject.getString("network");
                            diagnoseTask.isForceUpload = isForceUpload(diagnoseTask.networkCondition);
                            diagnoseTask.fromType = Code.TASK_BY_CONFIG;
                            diagnoseTask.fromTime = (simpleDateFormat2.parse(jSONObject.getString("from")).getTime() / TASK_FROMTIME_ROLL_TIMESPAN) * TASK_FROMTIME_ROLL_TIMESPAN;
                            diagnoseTask.toTime = simpleDateFormat2.parse(jSONObject.getString("to")).getTime();
                            long j2 = VALUE_TRACE_TIME;
                            long j3 = VALUE_STACK_TRACE_TIME;
                            long j4 = VALUE_STACK_TRACE_INTERVAL;
                            try {
                                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("config", ""));
                                try {
                                    jSONArray2 = jSONArray;
                                    simpleDateFormat = simpleDateFormat2;
                                    try {
                                        j2 = Long.parseLong(jSONObject2.getString(KEY_TRACE_TIME)) * TimeUnit.SECONDS.toMillis(1);
                                        i = Integer.parseInt(jSONObject2.getString(KEY_TRACE_SIZE)) * 1024 * 1024;
                                        try {
                                            j3 = Long.parseLong(jSONObject2.getString(KEY_STACK_TRACER_TIME)) * TimeUnit.MINUTES.toMillis(1);
                                            j4 = Long.parseLong(jSONObject2.getString(KEY_STACK_TRACER_INTERVAL)) * TimeUnit.SECONDS.toMillis(1);
                                        } catch (Throwable unused) {
                                        }
                                    } catch (Throwable unused2) {
                                        i = 8388608;
                                        str3 = jSONObject2.optString(KEY_RETRIEVE_FILE_PATH);
                                        j = j4;
                                        diagnoseTask.traceTime = j2;
                                        diagnoseTask.traceSize = i;
                                        diagnoseTask.stackTracerTime = j3;
                                        diagnoseTask.stackTracerInterval = j;
                                        diagnoseTask.retrieveFilePath = str3;
                                        arrayList.add(diagnoseTask);
                                        i2++;
                                        jSONArray = jSONArray2;
                                        simpleDateFormat2 = simpleDateFormat;
                                    }
                                } catch (Throwable unused3) {
                                    jSONArray2 = jSONArray;
                                    simpleDateFormat = simpleDateFormat2;
                                    i = 8388608;
                                    str3 = jSONObject2.optString(KEY_RETRIEVE_FILE_PATH);
                                    j = j4;
                                    diagnoseTask.traceTime = j2;
                                    diagnoseTask.traceSize = i;
                                    diagnoseTask.stackTracerTime = j3;
                                    diagnoseTask.stackTracerInterval = j;
                                    diagnoseTask.retrieveFilePath = str3;
                                    arrayList.add(diagnoseTask);
                                    i2++;
                                    jSONArray = jSONArray2;
                                    simpleDateFormat2 = simpleDateFormat;
                                }
                                try {
                                    str3 = jSONObject2.optString(KEY_RETRIEVE_FILE_PATH);
                                    j = j4;
                                } catch (Throwable unused4) {
                                    j = j4;
                                    str3 = null;
                                }
                            } catch (Throwable unused5) {
                                jSONArray2 = jSONArray;
                                simpleDateFormat = simpleDateFormat2;
                                j = j4;
                                str3 = null;
                                i = 8388608;
                            }
                            try {
                                diagnoseTask.traceTime = j2;
                                diagnoseTask.traceSize = i;
                                diagnoseTask.stackTracerTime = j3;
                                diagnoseTask.stackTracerInterval = j;
                                diagnoseTask.retrieveFilePath = str3;
                                arrayList.add(diagnoseTask);
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            jSONArray2 = jSONArray;
                            simpleDateFormat = simpleDateFormat2;
                            LoggerFactory.getTraceLogger().error((String) TAG, th);
                            i2++;
                            jSONArray = jSONArray2;
                            simpleDateFormat2 = simpleDateFormat;
                        }
                    } else {
                        String str4 = str;
                        jSONArray2 = jSONArray;
                        simpleDateFormat = simpleDateFormat2;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str5 = str;
                    jSONArray2 = jSONArray;
                    simpleDateFormat = simpleDateFormat2;
                    LoggerFactory.getTraceLogger().error((String) TAG, th);
                    i2++;
                    jSONArray = jSONArray2;
                    simpleDateFormat2 = simpleDateFormat;
                }
                i2++;
                jSONArray = jSONArray2;
                simpleDateFormat2 = simpleDateFormat;
            }
        }
        startDiagnose(arrayList);
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x012b A[SYNTHETIC, Splitter:B:73:0x012b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processPushMsg(java.lang.String r34) {
        /*
            r33 = this;
            r1 = r33
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r3 = "UserDiagnostician"
            java.lang.String r4 = "processPushMsg"
            java.lang.String r5 = java.lang.String.valueOf(r34)
            java.lang.String r4 = r4.concat(r5)
            r2.debug(r3, r4)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            long r3 = VALUE_TRACE_TIME
            long r5 = VALUE_STACK_TRACE_TIME
            long r7 = VALUE_STACK_TRACE_INTERVAL
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Throwable -> 0x002e }
            r11 = r34
            r10.<init>(r11)     // Catch:{ Throwable -> 0x002e }
            java.lang.String r11 = "p"
            org.json.JSONObject r10 = r10.getJSONObject(r11)     // Catch:{ Throwable -> 0x002e }
            goto L_0x003a
        L_0x002e:
            r0 = move-exception
            r10 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r12 = "UserDiagnostician"
            r11.error(r12, r10)
            r10 = 0
        L_0x003a:
            if (r10 != 0) goto L_0x003d
            return
        L_0x003d:
            java.lang.String r11 = "userID"
            java.lang.String r11 = r10.getString(r11)     // Catch:{ Throwable -> 0x0045 }
            goto L_0x0051
        L_0x0045:
            r0 = move-exception
            r11 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r12 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r13 = "UserDiagnostician"
            r12.error(r13, r11)
            r11 = 0
        L_0x0051:
            java.lang.String r12 = "taskID"
            java.lang.String r12 = r10.getString(r12)     // Catch:{ Throwable -> 0x0059 }
            goto L_0x0065
        L_0x0059:
            r0 = move-exception
            r12 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r14 = "UserDiagnostician"
            r13.error(r14, r12)
            r12 = 0
        L_0x0065:
            java.lang.String r13 = "config"
            org.json.JSONObject r13 = r10.getJSONObject(r13)     // Catch:{ Throwable -> 0x006c }
            goto L_0x0078
        L_0x006c:
            r0 = move-exception
            r13 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r14 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r15 = "UserDiagnostician"
            r14.error(r15, r13)
            r13 = 0
        L_0x0078:
            if (r13 == 0) goto L_0x00f9
            java.lang.String r15 = "traceviewTime"
            java.lang.String r15 = r13.getString(r15)     // Catch:{ Throwable -> 0x00e4 }
            long r15 = java.lang.Long.parseLong(r15)     // Catch:{ Throwable -> 0x00e4 }
            java.util.concurrent.TimeUnit r9 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ Throwable -> 0x00e4 }
            r19 = r15
            r14 = 1
            long r21 = r9.toMillis(r14)     // Catch:{ Throwable -> 0x00e4 }
            long r3 = r19 * r21
            java.lang.String r9 = "traceviewSize"
            java.lang.String r9 = r13.getString(r9)     // Catch:{ Throwable -> 0x00df }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Throwable -> 0x00df }
            int r9 = r9 * 1024
            int r9 = r9 * 1024
            java.lang.String r14 = "stackTracerTime"
            java.lang.String r14 = r13.getString(r14)     // Catch:{ Throwable -> 0x00d8 }
            long r14 = java.lang.Long.parseLong(r14)     // Catch:{ Throwable -> 0x00d8 }
            r23 = r3
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MINUTES     // Catch:{ Throwable -> 0x00da }
            r25 = r5
            r4 = 1
            long r18 = r3.toMillis(r4)     // Catch:{ Throwable -> 0x00dc }
            long r14 = r14 * r18
            java.lang.String r3 = "stackTracerInterval"
            java.lang.String r3 = r13.getString(r3)     // Catch:{ Throwable -> 0x00d3 }
            long r18 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x00d3 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ Throwable -> 0x00d3 }
            long r3 = r3.toMillis(r4)     // Catch:{ Throwable -> 0x00d3 }
            long r18 = r18 * r3
            r5 = r14
            r7 = r18
            r3 = r23
            r14 = r9
            goto L_0x00f0
        L_0x00d3:
            r18 = r9
            r25 = r14
            goto L_0x00ea
        L_0x00d8:
            r23 = r3
        L_0x00da:
            r25 = r5
        L_0x00dc:
            r18 = r9
            goto L_0x00ea
        L_0x00df:
            r23 = r3
            r25 = r5
            goto L_0x00e8
        L_0x00e4:
            r25 = r5
            r23 = r3
        L_0x00e8:
            r18 = 8388608(0x800000, float:1.17549435E-38)
        L_0x00ea:
            r14 = r18
            r3 = r23
            r5 = r25
        L_0x00f0:
            java.lang.String r9 = "retrieveFilePath"
            java.lang.String r9 = r13.optString(r9)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x00fe
        L_0x00f7:
            r9 = 0
            goto L_0x00fe
        L_0x00f9:
            r25 = r5
            r9 = 0
            r14 = 8388608(0x800000, float:1.17549435E-38)
        L_0x00fe:
            java.lang.String r13 = "tasklist"
            org.json.JSONArray r10 = r10.getJSONArray(r13)     // Catch:{ Throwable -> 0x0106 }
            goto L_0x0112
        L_0x0106:
            r0 = move-exception
            r10 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r15 = "UserDiagnostician"
            r13.error(r15, r10)
            r10 = 0
        L_0x0112:
            if (r10 == 0) goto L_0x022c
            int r13 = r10.length()
            if (r13 <= 0) goto L_0x022c
            java.text.SimpleDateFormat r13 = new java.text.SimpleDateFormat
            java.lang.String r15 = "yyyy-MM-dd HH:mm:ss"
            r13.<init>(r15)
            r15 = 0
            r27 = r2
        L_0x0125:
            int r2 = r10.length()
            if (r15 >= r2) goto L_0x022a
            org.json.JSONObject r2 = r10.getJSONObject(r15)     // Catch:{ Throwable -> 0x0207 }
            if (r2 == 0) goto L_0x01fc
            r28 = r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r10 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask     // Catch:{ Throwable -> 0x01f8 }
            r10.<init>()     // Catch:{ Throwable -> 0x01f8 }
            r10.userID = r11     // Catch:{ Throwable -> 0x01f8 }
            r10.taskID = r12     // Catch:{ Throwable -> 0x01f8 }
            r29 = r11
            java.lang.String r11 = "type"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x01f4 }
            r10.taskType = r11     // Catch:{ Throwable -> 0x01f4 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01f4 }
            r11.<init>()     // Catch:{ Throwable -> 0x01f4 }
            r30 = r12
            java.lang.String r12 = r10.taskID     // Catch:{ Throwable -> 0x01f0 }
            r11.append(r12)     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r12 = "_"
            r11.append(r12)     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r12 = r10.taskType     // Catch:{ Throwable -> 0x01f0 }
            r11.append(r12)     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x01f0 }
            r10.fileName = r11     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r11 = "network"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x01f0 }
            r10.networkCondition = r11     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r11 = r10.networkCondition     // Catch:{ Throwable -> 0x01f0 }
            boolean r11 = r1.isForceUpload(r11)     // Catch:{ Throwable -> 0x01f0 }
            r10.isForceUpload = r11     // Catch:{ Throwable -> 0x01f0 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r11 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_PUSH     // Catch:{ Throwable -> 0x01f0 }
            r10.fromType = r11     // Catch:{ Throwable -> 0x01f0 }
            r10.traceTime = r3     // Catch:{ Throwable -> 0x01f0 }
            r10.traceSize = r14     // Catch:{ Throwable -> 0x01f0 }
            r10.stackTracerTime = r5     // Catch:{ Throwable -> 0x01f0 }
            r10.stackTracerInterval = r7     // Catch:{ Throwable -> 0x01f0 }
            r10.retrieveFilePath = r9     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r11 = "new_from"
            boolean r11 = r2.has(r11)     // Catch:{ Throwable -> 0x01f0 }
            if (r11 == 0) goto L_0x01bc
            java.lang.String r11 = "new_to"
            boolean r11 = r2.has(r11)     // Catch:{ Throwable -> 0x01f0 }
            if (r11 == 0) goto L_0x01bc
            java.lang.String r11 = "new_from"
            java.lang.String r11 = r2.getString(r11)     // Catch:{ Throwable -> 0x01f0 }
            java.util.Date r11 = r13.parse(r11)     // Catch:{ Throwable -> 0x01f0 }
            long r11 = r11.getTime()     // Catch:{ Throwable -> 0x01f0 }
            long r16 = TASK_FROMTIME_ROLL_TIMESPAN     // Catch:{ Throwable -> 0x01f0 }
            long r11 = r11 / r16
            long r16 = TASK_FROMTIME_ROLL_TIMESPAN     // Catch:{ Throwable -> 0x01f0 }
            long r11 = r11 * r16
            r10.fromTime = r11     // Catch:{ Throwable -> 0x01f0 }
            java.lang.String r11 = "new_to"
            java.lang.String r2 = r2.getString(r11)     // Catch:{ Throwable -> 0x01f0 }
            java.util.Date r2 = r13.parse(r2)     // Catch:{ Throwable -> 0x01f0 }
            long r11 = r2.getTime()     // Catch:{ Throwable -> 0x01f0 }
            r10.toTime = r11     // Catch:{ Throwable -> 0x01f0 }
            r31 = r3
            goto L_0x01e6
        L_0x01bc:
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01f0 }
            r31 = r3
            java.lang.String r3 = "from"
            int r3 = r2.getInt(r3)     // Catch:{ Throwable -> 0x01ee }
            long r3 = (long) r3     // Catch:{ Throwable -> 0x01ee }
            long r16 = TASK_FROMTIME_ROLL_TIMESPAN     // Catch:{ Throwable -> 0x01ee }
            long r3 = r3 * r16
            r16 = 0
            long r11 = r11 + r3
            r10.fromTime = r11     // Catch:{ Throwable -> 0x01ee }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01ee }
            java.lang.String r11 = "to"
            int r2 = r2.getInt(r11)     // Catch:{ Throwable -> 0x01ee }
            long r11 = (long) r2     // Catch:{ Throwable -> 0x01ee }
            long r16 = TASK_FROMTIME_ROLL_TIMESPAN     // Catch:{ Throwable -> 0x01ee }
            long r11 = r11 * r16
            r2 = 0
            long r3 = r3 + r11
            r10.toTime = r3     // Catch:{ Throwable -> 0x01ee }
        L_0x01e6:
            r2 = r27
            r2.add(r10)     // Catch:{ Throwable -> 0x01ec }
            goto L_0x021c
        L_0x01ec:
            r0 = move-exception
            goto L_0x0212
        L_0x01ee:
            r0 = move-exception
            goto L_0x0210
        L_0x01f0:
            r0 = move-exception
            r31 = r3
            goto L_0x0210
        L_0x01f4:
            r0 = move-exception
            r31 = r3
            goto L_0x020e
        L_0x01f8:
            r0 = move-exception
            r31 = r3
            goto L_0x020c
        L_0x01fc:
            r31 = r3
            r28 = r10
            r29 = r11
            r30 = r12
            r2 = r27
            goto L_0x021c
        L_0x0207:
            r0 = move-exception
            r31 = r3
            r28 = r10
        L_0x020c:
            r29 = r11
        L_0x020e:
            r30 = r12
        L_0x0210:
            r2 = r27
        L_0x0212:
            r3 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "UserDiagnostician"
            r4.error(r10, r3)
        L_0x021c:
            int r15 = r15 + 1
            r27 = r2
            r10 = r28
            r11 = r29
            r12 = r30
            r3 = r31
            goto L_0x0125
        L_0x022a:
            r2 = r27
        L_0x022c:
            r1.startDiagnose(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.processPushMsg(java.lang.String):void");
    }

    private void startDiagnose(List<DiagnoseTask> list) {
        int size = list.size();
        if (size != 0) {
            for (DiagnoseTask next : list) {
                try {
                    asyncAckResult(next, next.fromType, "tasks count: ".concat(String.valueOf(size)));
                    if ("applog".equalsIgnoreCase(next.taskType)) {
                        uploadLog(next, null);
                    } else if ("trafficLog".equalsIgnoreCase(next.taskType)) {
                        uploadLog(next, null);
                    } else if ("logcat".equalsIgnoreCase(next.taskType)) {
                        LogcatDumpManager.getInstance(this.mContext).dumpLogUnfiltered();
                        SystemClock.sleep(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                        uploadLog(next, null);
                    } else if ("traceviewPush".equalsIgnoreCase(next.taskType)) {
                        traceview(next);
                    } else if ("traceviewWallet".equalsIgnoreCase(next.taskType)) {
                        if (LoggerFactory.getProcessInfo().isMainProcessExist()) {
                            sendDiagnoseTaskIntent(TraceStubReceiver.ACTION_DUMP_TRACEVIEW, next);
                        } else {
                            ackResult(next, Code.RESULT_FAILURE, "[UserDiagnostician.startDiagnose] wallet is not running");
                        }
                    } else if ("stacktracerPush".equalsIgnoreCase(next.taskType)) {
                        stackTracer(next);
                    } else if ("stacktracerWallet".equalsIgnoreCase(next.taskType)) {
                        if (LoggerFactory.getProcessInfo().isMainProcessExist()) {
                            sendDiagnoseTaskIntent(TraceStubReceiver.ACTION_DUMP_STACKTRACER, next);
                        } else {
                            ackResult(next, Code.RESULT_FAILURE, "[UserDiagnostician.startDiagnose] wallet is not running");
                        }
                    } else if ("anrLog".equalsIgnoreCase(next.taskType)) {
                        stackAnrTracer(next, true);
                    } else if ("anrtrace".equalsIgnoreCase(next.taskType)) {
                        stackAnrTracer(next, false);
                    } else if (!"storagetrace".equalsIgnoreCase(next.taskType) && "retrieveFile".equalsIgnoreCase(next.taskType)) {
                        startFileRetrieve(next);
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error(TAG, "startDiagnose", th);
                    ackResult(next, Code.RESULT_FAILURE, "[UserDiagnostician.startDiagnose] ".concat(String.valueOf(th)));
                }
            }
            list.clear();
        }
    }

    private void sendDiagnoseTaskIntent(String str, DiagnoseTask diagnoseTask) {
        try {
            Intent intent = new Intent(str);
            try {
                intent.setPackage(this.mContext.getPackageName());
            } catch (Throwable unused) {
            }
            intent.putExtra(LoggingSPCache.STORAGE_USERID, diagnoseTask.userID);
            intent.putExtra("taskID", diagnoseTask.taskID);
            intent.putExtra("type", diagnoseTask.taskType);
            intent.putExtra("fileName", diagnoseTask.fileName);
            intent.putExtra("networkCondition", diagnoseTask.networkCondition);
            intent.putExtra("isForceUpload", diagnoseTask.isForceUpload);
            intent.putExtra("fromTime", diagnoseTask.fromTime);
            intent.putExtra("toTime", diagnoseTask.toTime);
            intent.putExtra(H5Param.FROM_TYPE, diagnoseTask.fromType.toString());
            intent.putExtra(KEY_TRACE_TIME, diagnoseTask.traceTime);
            intent.putExtra(KEY_TRACE_SIZE, diagnoseTask.traceSize);
            intent.putExtra(KEY_STACK_TRACER_TIME, diagnoseTask.stackTracerTime);
            intent.putExtra(KEY_STACK_TRACER_INTERVAL, diagnoseTask.stackTracerInterval);
            intent.putExtra(KEY_RETRIEVE_FILE_PATH, diagnoseTask.retrieveFilePath);
            intent.putExtra("isPositive", diagnoseTask.isPositive);
            this.mContext.sendBroadcast(intent);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "sendDiagnoseTaskIntent: ".concat(String.valueOf(str)), th);
        }
    }

    /* access modifiers changed from: private */
    public void uploadLog(final DiagnoseTask diagnoseTask, final UploadTaskStatus uploadTaskStatus) {
        AnonymousClass4 r0 = new UploadTaskStatus() {
            public void onSuccess(String str) {
                String ackResult = UserDiagnostician.this.ackResult(diagnoseTask, Code.RESULT_SUCCESS, str);
                if (uploadTaskStatus != null) {
                    try {
                        uploadTaskStatus.onSuccess(ackResult);
                    } catch (Throwable th) {
                        LoggerFactory.getTraceLogger().error((String) UserDiagnostician.TAG, th);
                    }
                }
            }

            public void onFail(Code code, String str) {
                String ackResult = UserDiagnostician.this.ackResult(diagnoseTask, code, str);
                if (uploadTaskStatus != null) {
                    try {
                        uploadTaskStatus.onFail(code, ackResult);
                    } catch (Throwable th) {
                        LoggerFactory.getTraceLogger().error((String) UserDiagnostician.TAG, th);
                    }
                }
            }
        };
        AlipayLogUploader alipayLogUploader = new AlipayLogUploader(this.mContext, diagnoseTask);
        alipayLogUploader.setUploadTaskStatus(r0);
        alipayLogUploader.uploadLog();
    }

    private void traceview(final DiagnoseTask diagnoseTask) {
        StringBuilder sb = new StringBuilder();
        sb.append(diagnoseTask.taskID);
        sb.append("_");
        sb.append(LoggerFactory.getProcessInfo().getProcessTag());
        TracingUploader tracingUploader = new TracingUploader(this.mContext, sb.toString(), diagnoseTask);
        tracingUploader.setUploadTaskStatus(new UploadTaskStatus() {
            public void onSuccess(String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, Code.RESULT_SUCCESS, str);
            }

            public void onFail(Code code, String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, code, str);
            }
        });
        tracingUploader.tracingAndUpload();
    }

    private void stackTracer(final DiagnoseTask diagnoseTask) {
        StackTracer.getInstance().startStackTracer(this.mContext, diagnoseTask, new UploadTaskStatus() {
            public void onSuccess(String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, Code.RESULT_SUCCESS, str);
            }

            public void onFail(Code code, String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, code, str);
            }
        });
    }

    private void stackAnrTracer(final DiagnoseTask diagnoseTask, boolean z) {
        AnrTracer.getInstance().startAnrTracer(this.mContext, z, diagnoseTask, new UploadTaskStatus() {
            public void onSuccess(String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, Code.RESULT_SUCCESS, str);
            }

            public void onFail(Code code, String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, code, str);
            }
        });
    }

    private void startFileRetrieve(final DiagnoseTask diagnoseTask) {
        FileRetriever.getInstance().startFileRetrieve(this.mContext, diagnoseTask, new UploadTaskStatus() {
            public void onSuccess(String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, Code.RESULT_SUCCESS, str);
            }

            public void onFail(Code code, String str) {
                UserDiagnostician.this.ackResult(diagnoseTask, code, str);
            }
        });
    }
}
