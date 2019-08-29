package com.autonavi.miniapp.monitor.biz.logmonitor.util.upload;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask;
import java.util.Map;

public class HttpUpload implements Runnable {
    private static final int BUFFER_LENGTH = 8192;
    private static final String TAG = "HttpUpload";
    private String mAckDiagnoseMsg;
    private long mAlreadySentLength;
    private long mAlreadySentSpend;
    private UploadTaskStatus mCallback;
    private Context mContext;
    private DiagnoseTask mDiagnoseTask;
    private String mFilePath;
    private Map<String, String> mHeaderParameters;
    private String mUrl;

    public HttpUpload(String str, String str2, Context context, DiagnoseTask diagnoseTask, UploadTaskStatus uploadTaskStatus) {
        this.mFilePath = str;
        this.mUrl = str2;
        this.mContext = context;
        this.mDiagnoseTask = diagnoseTask;
        this.mCallback = uploadTaskStatus;
    }

    public void setHeaderParameters(Map<String, String> map) {
        this.mHeaderParameters = map;
    }

    public void setAckDiagnoseMessage(String str) {
        this.mAckDiagnoseMsg = str;
    }

    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v9, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r3v8, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v10, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r0v19, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r1v19, types: [java.io.OutputStream, java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r14v1 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r2v29 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r14v2 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r2v31 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r2v33 */
    /* JADX WARNING: type inference failed for: r2v35, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r14v4 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v34 */
    /* JADX WARNING: type inference failed for: r3v35 */
    /* JADX WARNING: type inference failed for: r3v36 */
    /* JADX WARNING: type inference failed for: r3v37 */
    /* JADX WARNING: type inference failed for: r2v44 */
    /* JADX WARNING: type inference failed for: r3v38 */
    /* JADX WARNING: type inference failed for: r2v45 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* JADX WARNING: type inference failed for: r4v17 */
    /* JADX WARNING: type inference failed for: r0v25 */
    /* JADX WARNING: type inference failed for: r0v26 */
    /* JADX WARNING: type inference failed for: r0v27 */
    /* JADX WARNING: type inference failed for: r4v18 */
    /* JADX WARNING: type inference failed for: r2v46 */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* JADX WARNING: type inference failed for: r2v47 */
    /* JADX WARNING: type inference failed for: r2v48 */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x02ae, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x02af, code lost:
        r2 = 0;
        r3 = r3;
        r4 = r4;
        r0 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:64:0x0285 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v10
      assigns: []
      uses: []
      mth insns count: 306
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02d2 A[SYNTHETIC, Splitter:B:101:0x02d2] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x02d7 A[SYNTHETIC, Splitter:B:105:0x02d7] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02dc A[SYNTHETIC, Splitter:B:109:0x02dc] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02e1 A[SYNTHETIC, Splitter:B:113:0x02e1] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x02ed A[SYNTHETIC, Splitter:B:121:0x02ed] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02f2 A[SYNTHETIC, Splitter:B:125:0x02f2] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x02f7 A[SYNTHETIC, Splitter:B:129:0x02f7] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x02fc A[SYNTHETIC, Splitter:B:133:0x02fc] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x028a A[SYNTHETIC, Splitter:B:68:0x028a] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x028f A[SYNTHETIC, Splitter:B:72:0x028f] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x02ae A[ExcHandler: all (th java.lang.Throwable), Splitter:B:24:0x009a] */
    /* JADX WARNING: Unknown variable types count: 22 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void upload() {
        /*
            r15 = this;
            java.lang.String r0 = r15.mFilePath
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x031f
            java.lang.String r0 = r15.mUrl
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0012
            goto L_0x031f
        L_0x0012:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r15.mFilePath
            r0.<init>(r1)
            long r1 = r0.length()
            int r1 = (int) r1
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x0300
            if (r1 != 0) goto L_0x0028
            goto L_0x0300
        L_0x0028:
            java.lang.String r2 = com.autonavi.miniapp.monitor.util.NetUtils.getNetworkType()
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask
            boolean r3 = r3.isForceUpload
            if (r3 == 0) goto L_0x003e
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = TAG
            java.lang.String r5 = "ForceUpload!"
            r3.info(r4, r5)
            goto L_0x0063
        L_0x003e:
            java.lang.String r3 = "WIFI"
            boolean r3 = r3.equals(r2)     // Catch:{ Throwable -> 0x0063 }
            if (r3 != 0) goto L_0x0063
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r3 = r15.mCallback     // Catch:{ Throwable -> 0x0063 }
            if (r3 == 0) goto L_0x0062
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r3 = r15.mCallback     // Catch:{ Throwable -> 0x0063 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NET_NOT_MATCH     // Catch:{ Throwable -> 0x0063 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0063 }
            r5.<init>()     // Catch:{ Throwable -> 0x0063 }
            r5.append(r2)     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r6 = " is no wifi network, can not upload"
            r5.append(r6)     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0063 }
            r3.onFail(r4, r5)     // Catch:{ Throwable -> 0x0063 }
        L_0x0062:
            return
        L_0x0063:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = TAG
            java.lang.String r5 = "upload begin"
            r3.info(r4, r5)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician r3 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.getInstance()
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r4 = r15.mDiagnoseTask
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r5 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.FILE_UPLOADING
            java.lang.String r6 = r15.mAckDiagnoseMsg
            r3.asyncAckResult(r4, r5, r6)
            r3 = 0
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x02c5, all -> 0x02c0 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x02c5, all -> 0x02c0 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x02c5, all -> 0x02c0 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x02c5, all -> 0x02c0 }
            long r5 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            java.net.URL r0 = new java.net.URL     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            java.lang.String r7 = r15.mUrl     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            r0.<init>(r7)     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            java.net.Proxy r7 = java.net.Proxy.NO_PROXY     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            java.net.URLConnection r0 = r0.openConnection(r7)     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Throwable -> 0x02bb, all -> 0x02b7 }
            long r7 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r9 = 0
            long r7 = r7 - r5
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r9.<init>()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r10 = "url: "
            r9.append(r10)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r10 = r15.mUrl     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r9.append(r10)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r10 = ", fileName: "
            r9.append(r10)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r10 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r10 = r10.fileName     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r9.append(r10)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r10 = ", connectSpend: "
            r9.append(r10)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r9.append(r7)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = TAG     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r7.info(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r7 = 1
            r0.setDoInput(r7)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.setDoOutput(r7)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r7 = 0
            r0.setUseCaches(r7)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "POST"
            r0.setRequestMethod(r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "userid"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.userID     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "account"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.accountName     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "taskType"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.taskType     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "FileName"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.fileName     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "FromType"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r9 = r9.fromType     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = "NetworkType"
            r0.addRequestProperty(r8, r2)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "ClientID"
            com.alipay.mobile.common.logging.api.LogContext r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = r8.getClientId()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "DeviceID"
            com.alipay.mobile.common.logging.api.LogContext r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = r8.getDeviceId()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "Process"
            com.alipay.mobile.common.logging.api.ProcessInfo r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = r8.getProcessAlias()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "ProductId"
            com.alipay.mobile.common.logging.api.LogContext r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = r8.getProductId()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "ProductVer"
            com.alipay.mobile.common.logging.api.LogContext r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r8 = r8.getProductVersion()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            boolean r2 = r2.isPositive     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            if (r2 != 0) goto L_0x0168
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r2 = r2.fromType     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r8 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_MANUAL     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            if (r2 != r8) goto L_0x0171
        L_0x0168:
            java.lang.String r2 = "type"
            java.lang.String r8 = "system"
            r0.addRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
        L_0x0171:
            java.util.Map<java.lang.String, java.lang.String> r2 = r15.mHeaderParameters     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            if (r2 == 0) goto L_0x01a9
            java.util.Map<java.lang.String, java.lang.String> r2 = r15.mHeaderParameters     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            int r2 = r2.size()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            if (r2 <= 0) goto L_0x01a9
            java.util.Map<java.lang.String, java.lang.String> r2 = r15.mHeaderParameters     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.util.Set r2 = r2.entrySet()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
        L_0x0187:
            boolean r8 = r2.hasNext()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            if (r8 == 0) goto L_0x01a9
            java.lang.Object r8 = r2.next()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.lang.Object r9 = r8.getKey()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.lang.Object r8 = r8.getValue()     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            if (r10 != 0) goto L_0x0187
            r0.addRequestProperty(r9, r8)     // Catch:{ Throwable -> 0x01a9, all -> 0x02ae }
            goto L_0x0187
        L_0x01a9:
            java.lang.String r2 = "Connection"
            java.lang.String r8 = "Keep-Alive"
            r0.setRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "Charset"
            java.lang.String r8 = "UTF-8"
            r0.setRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "Content-Type"
            java.lang.String r8 = "text/html"
            r0.setRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "Content-Length"
            java.lang.String r8 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.setRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.lang.String r2 = "Cache-Control"
            java.lang.String r8 = "no-cache"
            r0.setRequestProperty(r2, r8)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.setFixedLengthStreamingMode(r1)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r1 = 30000(0x7530, float:4.2039E-41)
            r0.setConnectTimeout(r1)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r1 = 60000(0xea60, float:8.4078E-41)
            r0.setReadTimeout(r1)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            long r1 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r15.mAlreadySentSpend = r1     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r0.connect()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            java.io.OutputStream r2 = r0.getOutputStream()     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x02b1, all -> 0x02ae }
            r2 = 8192(0x2000, float:1.14794E-41)
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
        L_0x01f3:
            int r8 = r4.read(r2)     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            if (r8 < 0) goto L_0x0203
            r1.write(r2, r7, r8)     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            long r9 = r15.mAlreadySentLength     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            long r11 = (long) r8     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            long r9 = r9 + r11
            r15.mAlreadySentLength = r9     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            goto L_0x01f3
        L_0x0203:
            r1.flush()     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            java.io.InputStream r2 = r0.getInputStream()     // Catch:{ Throwable -> 0x02a6, all -> 0x029f }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r3 = r3.bizType     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            boolean r7 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            if (r7 == 0) goto L_0x021c
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r3 = r3.fromType     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
        L_0x021c:
            r13 = r3
            com.alipay.mobile.common.logging.api.monitor.DataflowID r7 = com.alipay.mobile.common.logging.api.monitor.DataflowID.MONITOR     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r8 = r15.mUrl     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            long r9 = r15.mAlreadySentLength     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r11 = 0
            com.alipay.mobile.common.logging.api.monitor.DataflowModel r3 = com.alipay.mobile.common.logging.api.monitor.DataflowModel.obtain(r7, r8, r9, r11, r13)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r7 = "taskType"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r8 = r8.taskType     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r3.putParam(r7, r8)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r3.report()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            int r3 = r0.getResponseCode()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            long r7 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r9 = 0
            long r7 = r7 - r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r5.<init>()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r6 = "traficLength: "
            r5.append(r6)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            long r9 = r15.mAlreadySentLength     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r5.append(r9)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r6 = ", responseCode: "
            r5.append(r6)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r5.append(r3)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r6 = ", spendTime: "
            r5.append(r6)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r5.append(r7)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            java.lang.String r7 = TAG     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r6.info(r7, r5)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r3 != r6) goto L_0x027b
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r3 = r15.mCallback     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            if (r3 == 0) goto L_0x0282
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r3 = r15.mCallback     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r3.onSuccess(r5)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            goto L_0x0282
        L_0x027b:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
            r15.callbackOnError(r3)     // Catch:{ Throwable -> 0x0299, all -> 0x0293 }
        L_0x0282:
            r4.close()     // Catch:{ Throwable -> 0x0285 }
        L_0x0285:
            r1.close()     // Catch:{ Throwable -> 0x0288 }
        L_0x0288:
            if (r2 == 0) goto L_0x028d
            r2.close()     // Catch:{ Throwable -> 0x028d }
        L_0x028d:
            if (r0 == 0) goto L_0x02e5
            r0.disconnect()     // Catch:{ Throwable -> 0x0292 }
        L_0x0292:
            return
        L_0x0293:
            r3 = move-exception
            r14 = r3
            r3 = r1
            r1 = r14
            goto L_0x02eb
        L_0x0299:
            r3 = move-exception
            r14 = r4
            r4 = r0
            r0 = r1
            r1 = r3
            goto L_0x02ac
        L_0x029f:
            r2 = move-exception
            r14 = r3
            r3 = r1
            r1 = r2
            r2 = r14
            goto L_0x02eb
        L_0x02a6:
            r2 = move-exception
            r14 = r4
            r4 = r0
            r0 = r1
            r1 = r2
            r2 = r3
        L_0x02ac:
            r3 = r14
            goto L_0x02c9
        L_0x02ae:
            r1 = move-exception
            r2 = r3
            goto L_0x02eb
        L_0x02b1:
            r1 = move-exception
            r2 = r3
            r3 = r4
            r4 = r0
            r0 = r2
            goto L_0x02c9
        L_0x02b7:
            r1 = move-exception
            r0 = r3
            r2 = r0
            goto L_0x02eb
        L_0x02bb:
            r1 = move-exception
            r0 = r3
            r2 = r0
            r3 = r4
            goto L_0x02c8
        L_0x02c0:
            r1 = move-exception
            r0 = r3
            r2 = r0
            r4 = r2
            goto L_0x02eb
        L_0x02c5:
            r1 = move-exception
            r0 = r3
            r2 = r0
        L_0x02c8:
            r4 = r2
        L_0x02c9:
            java.lang.String r1 = android.util.Log.getStackTraceString(r1)     // Catch:{ all -> 0x02e6 }
            r15.callbackOnError(r1)     // Catch:{ all -> 0x02e6 }
            if (r3 == 0) goto L_0x02d5
            r3.close()     // Catch:{ Throwable -> 0x02d5 }
        L_0x02d5:
            if (r0 == 0) goto L_0x02da
            r0.close()     // Catch:{ Throwable -> 0x02da }
        L_0x02da:
            if (r2 == 0) goto L_0x02df
            r2.close()     // Catch:{ Throwable -> 0x02df }
        L_0x02df:
            if (r4 == 0) goto L_0x02e5
            r4.disconnect()     // Catch:{ Throwable -> 0x02e4 }
        L_0x02e4:
            return
        L_0x02e5:
            return
        L_0x02e6:
            r1 = move-exception
            r14 = r3
            r3 = r0
            r0 = r4
            r4 = r14
        L_0x02eb:
            if (r4 == 0) goto L_0x02f0
            r4.close()     // Catch:{ Throwable -> 0x02f0 }
        L_0x02f0:
            if (r3 == 0) goto L_0x02f5
            r3.close()     // Catch:{ Throwable -> 0x02f5 }
        L_0x02f5:
            if (r2 == 0) goto L_0x02fa
            r2.close()     // Catch:{ Throwable -> 0x02fa }
        L_0x02fa:
            if (r0 == 0) goto L_0x02ff
            r0.disconnect()     // Catch:{ Throwable -> 0x02ff }
        L_0x02ff:
            throw r1
        L_0x0300:
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mCallback
            if (r0 == 0) goto L_0x031e
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mCallback
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_TARGET_FILE
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r15.mFilePath
            r2.append(r3)
            java.lang.String r3 = " is not exist"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.onFail(r1, r2)
        L_0x031e:
            return
        L_0x031f:
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mCallback
            if (r0 == 0) goto L_0x032c
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mCallback
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.PARAM_INVALID
            java.lang.String r2 = "HttpUpload: mFilePath or mUrl is null"
            r0.onFail(r1, r2)
        L_0x032c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload.upload():void");
    }

    public void run() {
        try {
            upload();
        } catch (Throwable th) {
            callbackOnError(Log.getStackTraceString(th));
        }
    }

    private void callbackOnError(Object obj) {
        if (this.mCallback != null) {
            long uptimeMillis = this.mAlreadySentSpend == 0 ? -1 : SystemClock.uptimeMillis() - this.mAlreadySentSpend;
            StringBuilder sb = new StringBuilder();
            sb.append("sentLength: ");
            sb.append(this.mAlreadySentLength);
            sb.append(", alreadySentSpend: ");
            sb.append(uptimeMillis);
            sb.append(" [");
            sb.append(TAG);
            sb.append("] ");
            sb.append("fail: ");
            sb.append(obj);
            this.mCallback.onFail(Code.NETWORK_ERROR, sb.toString());
        }
    }
}
