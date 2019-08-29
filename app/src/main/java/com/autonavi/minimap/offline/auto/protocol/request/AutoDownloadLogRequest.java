package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.link.protocol.http.HttpClientHelper.IPostFileProgresser;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATDownloadLogRequest;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.utils.log.Logger;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AutoDownloadLogRequest extends UseCase<AutoDownloadLogParam, AutoDownloadResponse, Integer> {
    public static final String AUTO_KEY_DATE = "Date";
    public static final String AUTO_KEY_MD5 = "logkey";
    public static final String LOCAL_LOG_FILE_PREFIX = "auto";
    /* access modifiers changed from: private */
    public static volatile boolean isWriteLogError = false;
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger("AutoDownloadLogRequest");
    /* access modifiers changed from: private */
    public static long mAutoLogDate = 0;
    /* access modifiers changed from: private */
    public static String mAutoLogMd5 = "";
    private String mLogFileName;
    private FileOutputStream mOutputStream = null;
    private String mUrl;

    public static final class AutoDownloadLogParam implements RequestValues {
        String parms;

        public final String getParms() {
            try {
                ATDownloadLogRequest aTDownloadLogRequest = new ATDownloadLogRequest();
                aTDownloadLogRequest.setType(1);
                aTDownloadLogRequest.setLastDownloadTime(AutoDownloadLogRequest.mAutoLogDate);
                this.parms = JsonUtil.toString(aTDownloadLogRequest);
                Logger access$100 = AutoDownloadLogRequest.logger;
                StringBuilder sb = new StringBuilder("mAutoLogDate：");
                sb.append(AutoDownloadLogRequest.mAutoLogDate);
                access$100.e(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.parms;
        }
    }

    public static final class AutoDownloadResponse implements ResponseValue {
        byte[] bytes;
        int code;

        public final void setResult(int i) {
            this.code = i;
        }

        public final int getResult() {
            return this.code;
        }

        @SuppressFBWarnings({"EI_EXPOSE_REP"})
        public final byte[] getBytes() {
            return this.bytes;
        }

        @SuppressFBWarnings({"EI_EXPOSE_REP2"})
        public final void setBytes(byte[] bArr) {
            this.bytes = bArr;
        }
    }

    public AutoDownloadLogRequest() {
        StringBuilder sb = new StringBuilder("auto");
        sb.append(String.valueOf(System.currentTimeMillis()));
        this.mLogFileName = sb.toString();
        DiscoverInfo discoverInfo = null;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        discoverInfo = iAutoRemoteController != null ? iAutoRemoteController.getWifiDiscoverInfo() : discoverInfo;
        if (discoverInfo != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(discoverInfo.IP);
            sb2.append(":");
            sb2.append(discoverInfo.httpPort);
            this.mUrl = sb2.toString();
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoDownloadLogParam autoDownloadLogParam) {
        if (TextUtils.isEmpty(this.mUrl)) {
            getUseCaseCallback().onError(Integer.valueOf(201));
            return;
        }
        try {
            HttpClientHelper.getInstance().postBytes(this.mUrl, "/dataservice/downloadlog", null, autoDownloadLogParam.getParms().getBytes(), new IPostFileProgresser() {
                public final void onRequestReceived(Map<String, List<String>> map) {
                    if (map != null && map.containsKey(AutoDownloadLogRequest.AUTO_KEY_MD5)) {
                        List list = map.get(AutoDownloadLogRequest.AUTO_KEY_MD5);
                        if (list.size() > 0) {
                            AutoDownloadLogRequest.mAutoLogMd5 = (String) list.get(0);
                            Logger access$100 = AutoDownloadLogRequest.logger;
                            StringBuilder sb = new StringBuilder("header md5:");
                            sb.append(AutoDownloadLogRequest.mAutoLogMd5);
                            access$100.e(sb.toString());
                        }
                    }
                    if (map != null && map.containsKey(AutoDownloadLogRequest.AUTO_KEY_DATE)) {
                        List list2 = map.get(AutoDownloadLogRequest.AUTO_KEY_DATE);
                        if (list2.size() > 0) {
                            Date date = new Date((String) list2.get(0));
                            Logger access$1002 = AutoDownloadLogRequest.logger;
                            StringBuilder sb2 = new StringBuilder("isWriteLogError:");
                            sb2.append(AutoDownloadLogRequest.isWriteLogError);
                            access$1002.e(sb2.toString());
                            if (!AutoDownloadLogRequest.isWriteLogError) {
                                AutoDownloadLogRequest.mAutoLogDate = date.getTime();
                            } else {
                                AutoDownloadLogRequest.isWriteLogError = false;
                            }
                            Logger access$1003 = AutoDownloadLogRequest.logger;
                            StringBuilder sb3 = new StringBuilder("header date:");
                            sb3.append(AutoDownloadLogRequest.mAutoLogDate);
                            access$1003.e(sb3.toString());
                        }
                    }
                }

                public final boolean onDataReceived(byte[] bArr, int i, int i2) {
                    Logger access$100 = AutoDownloadLogRequest.logger;
                    StringBuilder sb = new StringBuilder("log readbytesCount:");
                    sb.append(i);
                    sb.append(",totalBytesCount:");
                    sb.append(i2);
                    access$100.e(sb.toString());
                    AutoDownloadLogRequest.this.writeCrashToFile(bArr, i >= i2);
                    return true;
                }

                public final void onRequestFinished() {
                    AutoDownloadLogRequest.logger.e("onRequestFinished");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0277, code lost:
        if (r8.mOutputStream != null) goto L_0x0279;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0279, code lost:
        r8.mOutputStream.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0288, code lost:
        if (mAutoLogMd5.equals(com.autonavi.minimap.offline.utils.OfflineUtil.getFileMD5(r2)) != false) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x028a, code lost:
        r2.renameTo(r4);
        com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.extract(r3, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0295, code lost:
        if (r2.exists() != false) goto L_0x0297;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0297, code lost:
        r0 = logger;
        r3 = new java.lang.StringBuilder("mTempFile ");
        r3.append(r2.getName());
        r0.e(r3.toString());
        r2.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x02b5, code lost:
        if (r4.exists() != false) goto L_0x02b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02b7, code lost:
        r0 = logger;
        r2 = new java.lang.StringBuilder("mZipFile ");
        r2.append(r4.getName());
        r0.e(r2.toString());
        r4.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x02d1, code lost:
        isWriteLogError = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02d3, code lost:
        r8.mLogFileName = "";
        mAutoLogMd5 = "";
        r8.mOutputStream = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02de, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02e0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
        r0.printStackTrace();
        isWriteLogError = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x02ec, code lost:
        if (r4.exists() != false) goto L_0x02ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x02ee, code lost:
        r10 = logger;
        r0 = new java.lang.StringBuilder("finally mZipFile ");
        r0.append(r4.getName());
        r10.e(r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0305, code lost:
        r4.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0311, code lost:
        r10 = logger;
        r0 = new java.lang.StringBuilder("finally mZipFile ");
        r0.append(r4.getName());
        r10.e(r0.toString());
        r4.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x032b, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0334, code lost:
        r10 = logger;
        r0 = new java.lang.StringBuilder("finally mZipFile ");
        r0.append(r4.getName());
        r10.e(r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x034c, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0115, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0147, code lost:
        if (r4.exists() != false) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0149, code lost:
        r10 = logger;
        r0 = new java.lang.StringBuilder("finally mZipFile ");
        r0.append(r4.getName());
        r10.e(r0.toString());
        r4.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0163, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x018a, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01fe, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0230, code lost:
        if (r4.exists() != false) goto L_0x0232;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0232, code lost:
        r10 = logger;
        r0 = new java.lang.StringBuilder("finally mZipFile ");
        r0.append(r4.getName());
        r10.e(r0.toString());
        r4.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x024c, code lost:
        throw r9;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:39:0x0118, B:64:0x018e, B:84:0x0201, B:125:0x02e1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void writeCrashToFile(byte[] r9, boolean r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.lang.String r0 = mAutoLogMd5     // Catch:{ all -> 0x034d }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x034d }
            r1 = 1
            if (r0 == 0) goto L_0x000e
            isWriteLogError = r1     // Catch:{ all -> 0x034d }
            monitor-exit(r8)
            return
        L_0x000e:
            java.lang.String r0 = r8.mLogFileName     // Catch:{ all -> 0x034d }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x034d }
            if (r0 == 0) goto L_0x0043
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r2 = "auto"
            r0.<init>(r2)     // Catch:{ all -> 0x034d }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x034d }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x034d }
            r0.append(r2)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r8.mLogFileName = r0     // Catch:{ all -> 0x034d }
            com.autonavi.minimap.offline.utils.log.Logger r0 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r3 = "mLogFileName:"
            r2.<init>(r3)     // Catch:{ all -> 0x034d }
            java.lang.String r3 = r8.mLogFileName     // Catch:{ all -> 0x034d }
            r2.append(r3)     // Catch:{ all -> 0x034d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x034d }
            r0.e(r2)     // Catch:{ all -> 0x034d }
        L_0x0043:
            java.io.File r0 = com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.getUploadCrashDir()     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ all -> 0x034d }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            r3.<init>()     // Catch:{ all -> 0x034d }
            r3.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = java.io.File.separator     // Catch:{ all -> 0x034d }
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = r8.mLogFileName     // Catch:{ all -> 0x034d }
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = ".temp"
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x034d }
            r2.<init>(r3)     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            r3.<init>()     // Catch:{ all -> 0x034d }
            r3.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = java.io.File.separator     // Catch:{ all -> 0x034d }
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = r8.mLogFileName     // Catch:{ all -> 0x034d }
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r4 = ".zip"
            r3.append(r4)     // Catch:{ all -> 0x034d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x034d }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x034d }
            r4.<init>(r3)     // Catch:{ all -> 0x034d }
            r5 = 0
            boolean r6 = r2.exists()     // Catch:{ IOException -> 0x018d }
            if (r6 != 0) goto L_0x0095
            r2.createNewFile()     // Catch:{ IOException -> 0x018d }
        L_0x0095:
            java.io.FileOutputStream r6 = r8.mOutputStream     // Catch:{ IOException -> 0x018d }
            if (r6 != 0) goto L_0x00a0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x018d }
            r6.<init>(r2)     // Catch:{ IOException -> 0x018d }
            r8.mOutputStream = r6     // Catch:{ IOException -> 0x018d }
        L_0x00a0:
            java.io.FileOutputStream r6 = r8.mOutputStream     // Catch:{ IOException -> 0x018d }
            r6.write(r9)     // Catch:{ IOException -> 0x018d }
            java.io.FileOutputStream r9 = r8.mOutputStream     // Catch:{ IOException -> 0x018d }
            r9.flush()     // Catch:{ IOException -> 0x018d }
            if (r10 == 0) goto L_0x0164
            java.io.FileOutputStream r9 = r8.mOutputStream     // Catch:{ Exception -> 0x0117 }
            if (r9 == 0) goto L_0x00b5
            java.io.FileOutputStream r9 = r8.mOutputStream     // Catch:{ Exception -> 0x0117 }
            r9.close()     // Catch:{ Exception -> 0x0117 }
        L_0x00b5:
            java.lang.String r9 = mAutoLogMd5     // Catch:{ Exception -> 0x0117 }
            java.lang.String r6 = com.autonavi.minimap.offline.utils.OfflineUtil.getFileMD5(r2)     // Catch:{ Exception -> 0x0117 }
            boolean r9 = r9.equals(r6)     // Catch:{ Exception -> 0x0117 }
            if (r9 == 0) goto L_0x00c8
            r2.renameTo(r4)     // Catch:{ Exception -> 0x0117 }
            com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.extract(r3, r0)     // Catch:{ Exception -> 0x0117 }
            goto L_0x010a
        L_0x00c8:
            boolean r9 = r2.exists()     // Catch:{ Exception -> 0x0117 }
            if (r9 == 0) goto L_0x00e8
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ Exception -> 0x0117 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0117 }
            java.lang.String r3 = "mTempFile "
            r0.<init>(r3)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r3 = r2.getName()     // Catch:{ Exception -> 0x0117 }
            r0.append(r3)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0117 }
            r9.e(r0)     // Catch:{ Exception -> 0x0117 }
            r2.delete()     // Catch:{ Exception -> 0x0117 }
        L_0x00e8:
            boolean r9 = r4.exists()     // Catch:{ Exception -> 0x0117 }
            if (r9 == 0) goto L_0x0108
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ Exception -> 0x0117 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0117 }
            java.lang.String r2 = "mZipFile "
            r0.<init>(r2)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r2 = r4.getName()     // Catch:{ Exception -> 0x0117 }
            r0.append(r2)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0117 }
            r9.e(r0)     // Catch:{ Exception -> 0x0117 }
            r4.delete()     // Catch:{ Exception -> 0x0117 }
        L_0x0108:
            isWriteLogError = r1     // Catch:{ Exception -> 0x0117 }
        L_0x010a:
            java.lang.String r9 = ""
            r8.mLogFileName = r9     // Catch:{ Exception -> 0x0117 }
            java.lang.String r9 = ""
            mAutoLogMd5 = r9     // Catch:{ Exception -> 0x0117 }
            r8.mOutputStream = r5     // Catch:{ Exception -> 0x0117 }
            goto L_0x0164
        L_0x0115:
            r9 = move-exception
            goto L_0x0141
        L_0x0117:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ all -> 0x0115 }
            isWriteLogError = r1     // Catch:{ all -> 0x0115 }
            if (r10 == 0) goto L_0x0188
            boolean r9 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r9 == 0) goto L_0x0188
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r0 = "finally mZipFile "
            r10.<init>(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r4.getName()     // Catch:{ all -> 0x034d }
            r10.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x034d }
            r9.e(r10)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
            monitor-exit(r8)
            return
        L_0x0141:
            if (r10 == 0) goto L_0x0163
            boolean r10 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r10 == 0) goto L_0x0163
            com.autonavi.minimap.offline.utils.log.Logger r10 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r1 = "finally mZipFile "
            r0.<init>(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r1 = r4.getName()     // Catch:{ all -> 0x034d }
            r0.append(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r10.e(r0)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
        L_0x0163:
            throw r9     // Catch:{ all -> 0x034d }
        L_0x0164:
            if (r10 == 0) goto L_0x0188
            boolean r9 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r9 == 0) goto L_0x0188
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r0 = "finally mZipFile "
            r10.<init>(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r4.getName()     // Catch:{ all -> 0x034d }
            r10.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x034d }
            r9.e(r10)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
            monitor-exit(r8)
            return
        L_0x0188:
            monitor-exit(r8)
            return
        L_0x018a:
            r9 = move-exception
            goto L_0x0273
        L_0x018d:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ all -> 0x018a }
            isWriteLogError = r1     // Catch:{ all -> 0x018a }
            if (r10 == 0) goto L_0x024d
            java.io.FileOutputStream r9 = r8.mOutputStream     // Catch:{ Exception -> 0x0200 }
            if (r9 == 0) goto L_0x019e
            java.io.FileOutputStream r9 = r8.mOutputStream     // Catch:{ Exception -> 0x0200 }
            r9.close()     // Catch:{ Exception -> 0x0200 }
        L_0x019e:
            java.lang.String r9 = mAutoLogMd5     // Catch:{ Exception -> 0x0200 }
            java.lang.String r6 = com.autonavi.minimap.offline.utils.OfflineUtil.getFileMD5(r2)     // Catch:{ Exception -> 0x0200 }
            boolean r9 = r9.equals(r6)     // Catch:{ Exception -> 0x0200 }
            if (r9 == 0) goto L_0x01b1
            r2.renameTo(r4)     // Catch:{ Exception -> 0x0200 }
            com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.extract(r3, r0)     // Catch:{ Exception -> 0x0200 }
            goto L_0x01f3
        L_0x01b1:
            boolean r9 = r2.exists()     // Catch:{ Exception -> 0x0200 }
            if (r9 == 0) goto L_0x01d1
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ Exception -> 0x0200 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0200 }
            java.lang.String r3 = "mTempFile "
            r0.<init>(r3)     // Catch:{ Exception -> 0x0200 }
            java.lang.String r3 = r2.getName()     // Catch:{ Exception -> 0x0200 }
            r0.append(r3)     // Catch:{ Exception -> 0x0200 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0200 }
            r9.e(r0)     // Catch:{ Exception -> 0x0200 }
            r2.delete()     // Catch:{ Exception -> 0x0200 }
        L_0x01d1:
            boolean r9 = r4.exists()     // Catch:{ Exception -> 0x0200 }
            if (r9 == 0) goto L_0x01f1
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ Exception -> 0x0200 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0200 }
            java.lang.String r2 = "mZipFile "
            r0.<init>(r2)     // Catch:{ Exception -> 0x0200 }
            java.lang.String r2 = r4.getName()     // Catch:{ Exception -> 0x0200 }
            r0.append(r2)     // Catch:{ Exception -> 0x0200 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0200 }
            r9.e(r0)     // Catch:{ Exception -> 0x0200 }
            r4.delete()     // Catch:{ Exception -> 0x0200 }
        L_0x01f1:
            isWriteLogError = r1     // Catch:{ Exception -> 0x0200 }
        L_0x01f3:
            java.lang.String r9 = ""
            r8.mLogFileName = r9     // Catch:{ Exception -> 0x0200 }
            java.lang.String r9 = ""
            mAutoLogMd5 = r9     // Catch:{ Exception -> 0x0200 }
            r8.mOutputStream = r5     // Catch:{ Exception -> 0x0200 }
            goto L_0x024d
        L_0x01fe:
            r9 = move-exception
            goto L_0x022a
        L_0x0200:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ all -> 0x01fe }
            isWriteLogError = r1     // Catch:{ all -> 0x01fe }
            if (r10 == 0) goto L_0x0271
            boolean r9 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r9 == 0) goto L_0x0271
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r0 = "finally mZipFile "
            r10.<init>(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r4.getName()     // Catch:{ all -> 0x034d }
            r10.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x034d }
            r9.e(r10)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
            monitor-exit(r8)
            return
        L_0x022a:
            if (r10 == 0) goto L_0x024c
            boolean r10 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r10 == 0) goto L_0x024c
            com.autonavi.minimap.offline.utils.log.Logger r10 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r1 = "finally mZipFile "
            r0.<init>(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r1 = r4.getName()     // Catch:{ all -> 0x034d }
            r0.append(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r10.e(r0)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
        L_0x024c:
            throw r9     // Catch:{ all -> 0x034d }
        L_0x024d:
            if (r10 == 0) goto L_0x0271
            boolean r9 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r9 == 0) goto L_0x0271
            com.autonavi.minimap.offline.utils.log.Logger r9 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r0 = "finally mZipFile "
            r10.<init>(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r4.getName()     // Catch:{ all -> 0x034d }
            r10.append(r0)     // Catch:{ all -> 0x034d }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x034d }
            r9.e(r10)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
            monitor-exit(r8)
            return
        L_0x0271:
            monitor-exit(r8)
            return
        L_0x0273:
            if (r10 == 0) goto L_0x032c
            java.io.FileOutputStream r6 = r8.mOutputStream     // Catch:{ Exception -> 0x02e0 }
            if (r6 == 0) goto L_0x027e
            java.io.FileOutputStream r6 = r8.mOutputStream     // Catch:{ Exception -> 0x02e0 }
            r6.close()     // Catch:{ Exception -> 0x02e0 }
        L_0x027e:
            java.lang.String r6 = mAutoLogMd5     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r7 = com.autonavi.minimap.offline.utils.OfflineUtil.getFileMD5(r2)     // Catch:{ Exception -> 0x02e0 }
            boolean r6 = r6.equals(r7)     // Catch:{ Exception -> 0x02e0 }
            if (r6 == 0) goto L_0x0291
            r2.renameTo(r4)     // Catch:{ Exception -> 0x02e0 }
            com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.extract(r3, r0)     // Catch:{ Exception -> 0x02e0 }
            goto L_0x02d3
        L_0x0291:
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x02e0 }
            if (r0 == 0) goto L_0x02b1
            com.autonavi.minimap.offline.utils.log.Logger r0 = logger     // Catch:{ Exception -> 0x02e0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r6 = "mTempFile "
            r3.<init>(r6)     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x02e0 }
            r3.append(r6)     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x02e0 }
            r0.e(r3)     // Catch:{ Exception -> 0x02e0 }
            r2.delete()     // Catch:{ Exception -> 0x02e0 }
        L_0x02b1:
            boolean r0 = r4.exists()     // Catch:{ Exception -> 0x02e0 }
            if (r0 == 0) goto L_0x02d1
            com.autonavi.minimap.offline.utils.log.Logger r0 = logger     // Catch:{ Exception -> 0x02e0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r3 = "mZipFile "
            r2.<init>(r3)     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r3 = r4.getName()     // Catch:{ Exception -> 0x02e0 }
            r2.append(r3)     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x02e0 }
            r0.e(r2)     // Catch:{ Exception -> 0x02e0 }
            r4.delete()     // Catch:{ Exception -> 0x02e0 }
        L_0x02d1:
            isWriteLogError = r1     // Catch:{ Exception -> 0x02e0 }
        L_0x02d3:
            java.lang.String r0 = ""
            r8.mLogFileName = r0     // Catch:{ Exception -> 0x02e0 }
            java.lang.String r0 = ""
            mAutoLogMd5 = r0     // Catch:{ Exception -> 0x02e0 }
            r8.mOutputStream = r5     // Catch:{ Exception -> 0x02e0 }
            goto L_0x032c
        L_0x02de:
            r9 = move-exception
            goto L_0x0309
        L_0x02e0:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x02de }
            isWriteLogError = r1     // Catch:{ all -> 0x02de }
            if (r10 == 0) goto L_0x034c
            boolean r10 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r10 == 0) goto L_0x034c
            com.autonavi.minimap.offline.utils.log.Logger r10 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r1 = "finally mZipFile "
            r0.<init>(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r1 = r4.getName()     // Catch:{ all -> 0x034d }
            r0.append(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r10.e(r0)     // Catch:{ all -> 0x034d }
        L_0x0305:
            r4.delete()     // Catch:{ all -> 0x034d }
            goto L_0x034c
        L_0x0309:
            if (r10 == 0) goto L_0x032b
            boolean r10 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r10 == 0) goto L_0x032b
            com.autonavi.minimap.offline.utils.log.Logger r10 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r1 = "finally mZipFile "
            r0.<init>(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r1 = r4.getName()     // Catch:{ all -> 0x034d }
            r0.append(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r10.e(r0)     // Catch:{ all -> 0x034d }
            r4.delete()     // Catch:{ all -> 0x034d }
        L_0x032b:
            throw r9     // Catch:{ all -> 0x034d }
        L_0x032c:
            if (r10 == 0) goto L_0x034c
            boolean r10 = r4.exists()     // Catch:{ all -> 0x034d }
            if (r10 == 0) goto L_0x034c
            com.autonavi.minimap.offline.utils.log.Logger r10 = logger     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            java.lang.String r1 = "finally mZipFile "
            r0.<init>(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r1 = r4.getName()     // Catch:{ all -> 0x034d }
            r0.append(r1)     // Catch:{ all -> 0x034d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x034d }
            r10.e(r0)     // Catch:{ all -> 0x034d }
            goto L_0x0305
        L_0x034c:
            throw r9     // Catch:{ all -> 0x034d }
        L_0x034d:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest.writeCrashToFile(byte[], boolean):void");
    }
}
