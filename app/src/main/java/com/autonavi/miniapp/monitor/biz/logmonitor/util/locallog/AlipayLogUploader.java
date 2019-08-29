package com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class AlipayLogUploader {
    private static final String COMPRESS_TYPE_7Z = "7z";
    private static final String COMPRESS_TYPE_KEY = "CompressType";
    private static final String COMPRESS_TYPE_ZIP = "zip";
    private static final String PREFIX_BAK_DIR = "ic";
    private static final String PREFIX_BAK_IN_ZIP = "_bak";
    private static final long SEVEN_ZIP_LENGTH = 3145728;
    private static final String SUFFIX_2ND_LOG = ".2nd";
    private static final String SUFFIX_NORMAL_ZIP = ".zip";
    private static final String SUFFIX_POSITIVE = ".pzt";
    private static final String SUFFIX_SEVEN_ZIP = ".7z";
    /* access modifiers changed from: private */
    public static final String TAG = "AlipayLogUploader";
    private Context mContext;
    /* access modifiers changed from: private */
    public SimpleDateFormat mDateHourFormat = new SimpleDateFormat("yyyyMMddHH");
    /* access modifiers changed from: private */
    public DiagnoseTask mDiagnoseTask;
    private long mFileMaxTime;
    private long mFileMinTime;
    private long mFileTotalLength;
    /* access modifiers changed from: private */
    public UploadTaskStatus mTaskCallBack;
    private Set<String> mUploadFileNames;
    /* access modifiers changed from: private */
    public ArrayList<File> mUploadFiles;

    public AlipayLogUploader(Context context, DiagnoseTask diagnoseTask) {
        this.mContext = context;
        this.mDiagnoseTask = diagnoseTask;
        if ("trafficLog".equals(diagnoseTask.taskType)) {
            this.mDateHourFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }

    public void setUploadTaskStatus(UploadTaskStatus uploadTaskStatus) {
        this.mTaskCallBack = uploadTaskStatus;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:123:0x03e7, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0133, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02d6, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00bf A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0216 A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0218 A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0246 A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0296 A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0298 A[Catch:{ Throwable -> 0x003f }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:89:0x02d7=Splitter:B:89:0x02d7, B:118:0x03cb=Splitter:B:118:0x03cb, B:104:0x034d=Splitter:B:104:0x034d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void uploadLog() {
        /*
            r15 = this;
            monitor-enter(r15)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0420 }
            r0.<init>()     // Catch:{ all -> 0x0420 }
            r15.mUploadFiles = r0     // Catch:{ all -> 0x0420 }
            java.util.HashSet r0 = new java.util.HashSet     // Catch:{ all -> 0x0420 }
            r0.<init>()     // Catch:{ all -> 0x0420 }
            r15.mUploadFileNames = r0     // Catch:{ all -> 0x0420 }
            r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r15.mFileMinTime = r0     // Catch:{ all -> 0x0420 }
            r2 = -9223372036854775808
            r15.mFileMaxTime = r2     // Catch:{ all -> 0x0420 }
            r4 = 0
            r15.mFileTotalLength = r4     // Catch:{ all -> 0x0420 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            java.lang.String r6 = "[AlipayLogUpload.uploadLog] "
            r12.<init>(r6)     // Catch:{ all -> 0x0420 }
            boolean r6 = com.autonavi.miniapp.monitor.util.TransUtils.isOfflineForExternalFile()     // Catch:{ all -> 0x0420 }
            r7 = 0
            if (r6 != 0) goto L_0x008d
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x003f }
            android.content.Context r8 = r15.mContext     // Catch:{ Throwable -> 0x003f }
            java.io.File r8 = r8.getFilesDir()     // Catch:{ Throwable -> 0x003f }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x003f }
            java.lang.String r9 = r9.taskType     // Catch:{ Throwable -> 0x003f }
            r6.<init>(r8, r9)     // Catch:{ Throwable -> 0x003f }
            r15.fillUploadFileList(r6)     // Catch:{ Throwable -> 0x003f }
            goto L_0x004c
        L_0x003f:
            r6 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r9 = TAG     // Catch:{ all -> 0x0420 }
            java.lang.String r10 = "uploadLog"
            r8.warn(r9, r10, r6)     // Catch:{ all -> 0x0420 }
        L_0x004c:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r6 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r6 = r6.isPositive     // Catch:{ all -> 0x0420 }
            if (r6 != 0) goto L_0x00b6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r6.<init>()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = r8.taskType     // Catch:{ all -> 0x0420 }
            r6.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = "ic"
            r6.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0420 }
            java.io.File r8 = com.autonavi.miniapp.monitor.util.TransUtils.getCommonExternalStorageDir()     // Catch:{ Throwable -> 0x007f }
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x007f }
            android.content.Context r10 = r15.mContext     // Catch:{ Throwable -> 0x007f }
            java.lang.String r10 = r10.getPackageName()     // Catch:{ Throwable -> 0x007f }
            r9.<init>(r8, r10)     // Catch:{ Throwable -> 0x007f }
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x007f }
            r8.<init>(r9, r6)     // Catch:{ Throwable -> 0x007f }
            r15.fillUploadFileList(r8)     // Catch:{ Throwable -> 0x007f }
            goto L_0x00b7
        L_0x007f:
            r8 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r10 = TAG     // Catch:{ all -> 0x0420 }
            java.lang.String r11 = "uploadLog"
            r9.warn(r10, r11, r8)     // Catch:{ all -> 0x0420 }
            goto L_0x00b7
        L_0x008d:
            java.io.File r6 = com.autonavi.miniapp.monitor.util.TransUtils.getCommonExternalStorageDir()     // Catch:{ Throwable -> 0x00a9 }
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x00a9 }
            android.content.Context r9 = r15.mContext     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r9 = r9.getPackageName()     // Catch:{ Throwable -> 0x00a9 }
            r8.<init>(r6, r9)     // Catch:{ Throwable -> 0x00a9 }
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x00a9 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r9 = r9.taskType     // Catch:{ Throwable -> 0x00a9 }
            r6.<init>(r8, r9)     // Catch:{ Throwable -> 0x00a9 }
            r15.fillUploadFileList(r6)     // Catch:{ Throwable -> 0x00a9 }
            goto L_0x00b6
        L_0x00a9:
            r6 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r9 = TAG     // Catch:{ all -> 0x0420 }
            java.lang.String r10 = "uploadLog"
            r8.warn(r9, r10, r6)     // Catch:{ all -> 0x0420 }
        L_0x00b6:
            r6 = r7
        L_0x00b7:
            java.util.ArrayList<java.io.File> r8 = r15.mUploadFiles     // Catch:{ all -> 0x0420 }
            boolean r8 = r8.isEmpty()     // Catch:{ all -> 0x0420 }
            if (r8 == 0) goto L_0x0134
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0420 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r2.<init>()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r3.taskType     // Catch:{ all -> 0x0420 }
            r2.append(r3)     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = " [no files to upload] "
            r2.append(r3)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r3 = r3.isPositive     // Catch:{ all -> 0x0420 }
            r2.append(r3)     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0420 }
            r0.warn(r1, r2)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            if (r0 == 0) goto L_0x0132
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r0 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r0 = r0.isPositive     // Catch:{ all -> 0x0420 }
            if (r0 != 0) goto L_0x0132
            java.text.SimpleDateFormat r0 = r15.mDateHourFormat     // Catch:{ all -> 0x0420 }
            java.util.Date r1 = new java.util.Date     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            long r2 = r2.fromTime     // Catch:{ all -> 0x0420 }
            r1.<init>(r2)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r0.format(r1)     // Catch:{ all -> 0x0420 }
            java.text.SimpleDateFormat r1 = r15.mDateHourFormat     // Catch:{ all -> 0x0420 }
            java.util.Date r2 = new java.util.Date     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            long r3 = r3.toTime     // Catch:{ all -> 0x0420 }
            r2.<init>(r3)     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = r1.format(r2)     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = "( "
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = " ~ "
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            r12.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = " ) "
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = "this period contains none file !"
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_TARGET_FILE     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = r12.toString()     // Catch:{ all -> 0x0420 }
            r0.onFail(r1, r2)     // Catch:{ all -> 0x0420 }
        L_0x0132:
            monitor-exit(r15)
            return
        L_0x0134:
            java.lang.String r8 = " files count:"
            r12.append(r8)     // Catch:{ all -> 0x0420 }
            java.util.ArrayList<java.io.File> r8 = r15.mUploadFiles     // Catch:{ all -> 0x0420 }
            int r8 = r8.size()     // Catch:{ all -> 0x0420 }
            r12.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = ", files length: "
            r12.append(r8)     // Catch:{ all -> 0x0420 }
            long r8 = r15.mFileTotalLength     // Catch:{ all -> 0x0420 }
            r12.append(r8)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician r8 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.getInstance()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r9 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r10 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.FILE_ZIPPING     // Catch:{ all -> 0x0420 }
            java.lang.String r11 = r12.toString()     // Catch:{ all -> 0x0420 }
            r8.asyncAckResult(r9, r10, r11)     // Catch:{ all -> 0x0420 }
            android.content.Context r8 = r15.mContext     // Catch:{ Throwable -> 0x016e }
            java.io.File r8 = r8.getCacheDir()     // Catch:{ Throwable -> 0x016e }
            r8.mkdirs()     // Catch:{ Throwable -> 0x016e }
            android.content.Context r8 = r15.mContext     // Catch:{ Throwable -> 0x016e }
            java.io.File r8 = r8.getExternalCacheDir()     // Catch:{ Throwable -> 0x016e }
            r8.mkdirs()     // Catch:{ Throwable -> 0x016e }
            goto L_0x0178
        L_0x016e:
            r8 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r10 = TAG     // Catch:{ all -> 0x0420 }
            r9.error(r10, r8)     // Catch:{ all -> 0x0420 }
        L_0x0178:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r8 = r8.isPositive     // Catch:{ all -> 0x0420 }
            r9 = 1
            r10 = 0
            if (r8 != 0) goto L_0x018b
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r8 = r8.fromType     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r11 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_MANUAL     // Catch:{ all -> 0x0420 }
            if (r8 != r11) goto L_0x0189
            goto L_0x018b
        L_0x0189:
            r8 = 0
            goto L_0x018c
        L_0x018b:
            r8 = 1
        L_0x018c:
            if (r8 == 0) goto L_0x01e4
            long r13 = r15.mFileMinTime     // Catch:{ all -> 0x0420 }
            int r0 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x01e4
            long r0 = r15.mFileMaxTime     // Catch:{ all -> 0x0420 }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x01e4
            java.text.SimpleDateFormat r0 = r15.mDateHourFormat     // Catch:{ all -> 0x0420 }
            java.util.Date r1 = new java.util.Date     // Catch:{ all -> 0x0420 }
            long r2 = r15.mFileMinTime     // Catch:{ all -> 0x0420 }
            r1.<init>(r2)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r0.format(r1)     // Catch:{ all -> 0x0420 }
            java.text.SimpleDateFormat r1 = r15.mDateHourFormat     // Catch:{ all -> 0x0420 }
            java.util.Date r2 = new java.util.Date     // Catch:{ all -> 0x0420 }
            long r13 = r15.mFileMaxTime     // Catch:{ all -> 0x0420 }
            r2.<init>(r13)     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = r1.format(r2)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r3.<init>()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = r8.userID     // Catch:{ all -> 0x0420 }
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = "-"
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = r8.taskType     // Catch:{ all -> 0x0420 }
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = "-"
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            r3.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = "-"
            r3.append(r0)     // Catch:{ all -> 0x0420 }
            r3.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0420 }
            r2.fileName = r0     // Catch:{ all -> 0x0420 }
        L_0x01e4:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r0.<init>()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r1 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = r1.fileName     // Catch:{ all -> 0x0420 }
            r0.append(r1)     // Catch:{ all -> 0x0420 }
            r1 = 95
            r0.append(r1)     // Catch:{ all -> 0x0420 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0420 }
            r0.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = ".zip"
            r0.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0420 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0420 }
            android.content.Context r2 = r15.mContext     // Catch:{ all -> 0x0420 }
            java.io.File r2 = r2.getCacheDir()     // Catch:{ all -> 0x0420 }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r0 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r0 = r0.isPositive     // Catch:{ all -> 0x0420 }
            if (r0 == 0) goto L_0x0218
            r0 = r7
            goto L_0x023a
        L_0x0218:
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x0420 }
            android.content.Context r2 = r15.mContext     // Catch:{ all -> 0x0420 }
            java.io.File r2 = r2.getExternalCacheDir()     // Catch:{ all -> 0x0420 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = "diagnose_"
            r3.<init>(r8)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r8 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = r8.taskType     // Catch:{ all -> 0x0420 }
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = ".zip"
            r3.append(r8)     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0420 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x0420 }
        L_0x023a:
            java.lang.String r2 = "applog"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r3.taskType     // Catch:{ all -> 0x0420 }
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ all -> 0x0420 }
            if (r2 != 0) goto L_0x0256
            java.lang.String r2 = "trafficLog"
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r3 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r3.taskType     // Catch:{ all -> 0x0420 }
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ all -> 0x0420 }
            if (r2 == 0) goto L_0x0254
            goto L_0x0256
        L_0x0254:
            r2 = r7
            goto L_0x025b
        L_0x0256:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader$1 r2 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader$1     // Catch:{ all -> 0x0420 }
            r2.<init>(r6)     // Catch:{ all -> 0x0420 }
        L_0x025b:
            long r13 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0420 }
            java.util.ArrayList<java.io.File> r3 = r15.mUploadFiles     // Catch:{ Throwable -> 0x03e8 }
            java.lang.String r6 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x03e8 }
            com.autonavi.miniapp.monitor.util.ZipUtils.zipFile(r3, r6, r7, r2)     // Catch:{ Throwable -> 0x03e8 }
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0420 }
            r6 = 0
            long r2 = r2 - r13
            java.lang.String r6 = ", zipping spend: "
            r12.append(r6)     // Catch:{ all -> 0x0420 }
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = ", zipped length: "
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            long r2 = r1.length()     // Catch:{ all -> 0x0420 }
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            boolean r2 = r1.exists()     // Catch:{ all -> 0x0420 }
            if (r2 == 0) goto L_0x03cb
            boolean r2 = r1.isFile()     // Catch:{ all -> 0x0420 }
            if (r2 == 0) goto L_0x03cb
            long r2 = r1.length()     // Catch:{ all -> 0x0420 }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x0298
            goto L_0x03cb
        L_0x0298:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r2 = r2.fromType     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r3 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TASK_BY_MANUAL     // Catch:{ all -> 0x0420 }
            if (r2 != r3) goto L_0x02d7
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            boolean r2 = r2.isForceUpload     // Catch:{ all -> 0x0420 }
            if (r2 == 0) goto L_0x02d7
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r2 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            long r2 = r2.zippedLenLimit     // Catch:{ all -> 0x0420 }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x02d7
            long r2 = r1.length()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r6 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            long r6 = r6.zippedLenLimit     // Catch:{ all -> 0x0420 }
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x02d7
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            if (r0 == 0) goto L_0x02d5
            java.lang.String r0 = ", zipped limit: "
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask r0 = r15.mDiagnoseTask     // Catch:{ all -> 0x0420 }
            long r0 = r0.zippedLenLimit     // Catch:{ all -> 0x0420 }
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.TRAFIC_LIMIT     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = r12.toString()     // Catch:{ all -> 0x0420 }
            r0.onFail(r1, r2)     // Catch:{ all -> 0x0420 }
        L_0x02d5:
            monitor-exit(r15)
            return
        L_0x02d7:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0420 }
            r2.<init>()     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = "CompressType"
            java.lang.String r6 = "zip"
            r2.put(r3, r6)     // Catch:{ all -> 0x0420 }
            com.alipay.mobile.common.logging.api.LogContext r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ all -> 0x0420 }
            boolean r3 = r3.isZipAndSevenZip()     // Catch:{ all -> 0x0420 }
            if (r3 == 0) goto L_0x03b7
            long r6 = r1.length()     // Catch:{ all -> 0x0420 }
            r13 = 3145728(0x300000, double:1.554196E-317)
            int r3 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r3 <= 0) goto L_0x03b7
            boolean r3 = com.autonavi.miniapp.monitor.util.NetUtils.isNetworkUseWifi()     // Catch:{ all -> 0x0420 }
            if (r3 != 0) goto L_0x03b7
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r3.<init>()     // Catch:{ all -> 0x0420 }
            java.lang.String r6 = r1.getAbsolutePath()     // Catch:{ all -> 0x0420 }
            r3.append(r6)     // Catch:{ all -> 0x0420 }
            java.lang.String r6 = ".7z"
            r3.append(r6)     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0420 }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x0420 }
            r6.<init>(r3)     // Catch:{ all -> 0x0420 }
            long r7 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0420 }
            java.lang.String r11 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x034b }
            boolean r3 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LzmaAlone.sevenZipFile(r9, r10, r11, r3)     // Catch:{ Throwable -> 0x034b }
            long r9 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x0349 }
            r11 = 0
            long r7 = r9 - r7
            java.lang.String r9 = ", 7Zing spend: "
            r12.append(r9)     // Catch:{ Throwable -> 0x0349 }
            r12.append(r7)     // Catch:{ Throwable -> 0x0349 }
            java.lang.String r9 = ", 7Zed length: "
            r12.append(r9)     // Catch:{ Throwable -> 0x0349 }
            long r9 = r6.length()     // Catch:{ Throwable -> 0x0349 }
            r12.append(r9)     // Catch:{ Throwable -> 0x0349 }
            java.lang.String r9 = ", 7Z success: "
            r12.append(r9)     // Catch:{ Throwable -> 0x0349 }
            r12.append(r3)     // Catch:{ Throwable -> 0x0349 }
            goto L_0x0380
        L_0x0349:
            r9 = move-exception
            goto L_0x034d
        L_0x034b:
            r9 = move-exception
            r3 = 0
        L_0x034d:
            long r10 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0420 }
            r13 = 0
            long r10 = r10 - r7
            java.lang.String r7 = ", 7Zing spend: "
            r12.append(r7)     // Catch:{ all -> 0x0420 }
            r12.append(r10)     // Catch:{ all -> 0x0420 }
            java.lang.String r7 = ", 7Zed length: "
            r12.append(r7)     // Catch:{ all -> 0x0420 }
            long r7 = r6.length()     // Catch:{ all -> 0x0420 }
            r12.append(r7)     // Catch:{ all -> 0x0420 }
            java.lang.String r7 = ", catch throwable: "
            r12.append(r7)     // Catch:{ all -> 0x0420 }
            java.lang.String r7 = android.util.Log.getStackTraceString(r9)     // Catch:{ all -> 0x0420 }
            r12.append(r7)     // Catch:{ all -> 0x0420 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0420 }
            java.lang.String r8 = TAG     // Catch:{ all -> 0x0420 }
            java.lang.String r9 = r12.toString()     // Catch:{ all -> 0x0420 }
            r7.error(r8, r9)     // Catch:{ all -> 0x0420 }
        L_0x0380:
            if (r3 == 0) goto L_0x03b4
            long r7 = r6.length()     // Catch:{ all -> 0x0420 }
            int r3 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x03b4
            com.autonavi.miniapp.monitor.util.FileUtils.deleteFileNotDir(r1)     // Catch:{ all -> 0x0420 }
            if (r0 == 0) goto L_0x03aa
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0420 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0420 }
            r3.<init>()     // Catch:{ all -> 0x0420 }
            java.io.File r0 = r0.getAbsoluteFile()     // Catch:{ all -> 0x0420 }
            r3.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = ".7z"
            r3.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0420 }
            r1.<init>(r0)     // Catch:{ all -> 0x0420 }
            r0 = r1
        L_0x03aa:
            java.lang.String r1 = "CompressType"
            java.lang.String r3 = "7z"
            r2.put(r1, r3)     // Catch:{ all -> 0x0420 }
            r9 = r0
            r1 = r6
            goto L_0x03b8
        L_0x03b4:
            com.autonavi.miniapp.monitor.util.FileUtils.deleteFileNotDir(r6)     // Catch:{ all -> 0x0420 }
        L_0x03b7:
            r9 = r0
        L_0x03b8:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader$2 r0 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader$2     // Catch:{ all -> 0x0420 }
            r6 = r0
            r7 = r15
            r8 = r12
            r10 = r1
            r11 = r2
            r6.<init>(r8, r9, r10, r11)     // Catch:{ all -> 0x0420 }
            java.lang.String r3 = r12.toString()     // Catch:{ all -> 0x0420 }
            r15.uploadCoreForRetry(r1, r0, r2, r3)     // Catch:{ all -> 0x0420 }
            monitor-exit(r15)
            return
        L_0x03cb:
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            if (r0 == 0) goto L_0x03e6
            java.lang.String r0 = ", not exist: "
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r1.getAbsolutePath()     // Catch:{ all -> 0x0420 }
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r0 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r1 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = r12.toString()     // Catch:{ all -> 0x0420 }
            r0.onFail(r1, r2)     // Catch:{ all -> 0x0420 }
        L_0x03e6:
            monitor-exit(r15)
            return
        L_0x03e8:
            r0 = move-exception
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0420 }
            r4 = 0
            long r2 = r2 - r13
            java.lang.String r4 = ", zipping spend: "
            r12.append(r4)     // Catch:{ all -> 0x0420 }
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            java.lang.String r2 = ", zipped length: "
            r12.append(r2)     // Catch:{ all -> 0x0420 }
            long r1 = r1.length()     // Catch:{ all -> 0x0420 }
            r12.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r1 = ", catch throwable: "
            r12.append(r1)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0420 }
            r12.append(r0)     // Catch:{ all -> 0x0420 }
            java.lang.String r0 = r12.toString()     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r1 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            if (r1 == 0) goto L_0x041e
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r1 = r15.mTaskCallBack     // Catch:{ all -> 0x0420 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r2 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0420 }
            r1.onFail(r2, r0)     // Catch:{ all -> 0x0420 }
        L_0x041e:
            monitor-exit(r15)
            return
        L_0x0420:
            r0 = move-exception
            monitor-exit(r15)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.locallog.AlipayLogUploader.uploadLog():void");
    }

    /* access modifiers changed from: private */
    public void uploadCoreForRetry(File file, UploadTaskStatus uploadTaskStatus, Map<String, String> map, String str) {
        LoggerFactory.getTraceLogger().info(TAG, "uploadCoreForRetry: ".concat(String.valueOf(file)));
        try {
            HttpUpload httpUpload = new HttpUpload(file.getAbsolutePath(), UploadConstants.getUploadFileUrl(this.mDiagnoseTask.fromType == Code.TASK_BY_MANUAL), this.mContext, this.mDiagnoseTask, uploadTaskStatus);
            httpUpload.setHeaderParameters(map);
            httpUpload.setAckDiagnoseMessage(str);
            APMTimer.getInstance().post(httpUpload);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "uploadCoreForRetry", th);
        }
    }

    private void fillUploadFileList(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                for (File file2 : listFiles) {
                    if (file2 != null) {
                        try {
                            if (file2.exists() && file2.isFile()) {
                                if (file2.length() != 0) {
                                    String name = file2.getName();
                                    if (!name.endsWith(".pzt.zip") && !name.endsWith(".pzt.2nd")) {
                                        if (!name.endsWith(SUFFIX_POSITIVE)) {
                                            long parseLong = Long.parseLong(name.split("_")[0]);
                                            if (parseLong >= this.mDiagnoseTask.fromTime && parseLong < this.mDiagnoseTask.toTime && !this.mUploadFileNames.contains(name)) {
                                                this.mUploadFiles.add(file2);
                                                this.mUploadFileNames.add(name);
                                                this.mFileTotalLength += file2.length();
                                                this.mFileMinTime = Math.min(parseLong, this.mFileMinTime);
                                                this.mFileMaxTime = Math.max(parseLong, this.mFileMaxTime);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Throwable unused) {
                            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                            String str = TAG;
                            StringBuilder sb = new StringBuilder("fillUploadFileList: ");
                            sb.append(file2.getAbsolutePath());
                            traceLogger.error(str, sb.toString());
                        }
                    }
                }
            }
        }
    }
}
