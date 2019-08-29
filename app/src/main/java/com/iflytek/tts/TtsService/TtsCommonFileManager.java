package com.iflytek.tts.TtsService;

import android.os.Build.VERSION;
import android.os.StatFs;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.device.ConnectivityMonitor;
import com.amap.bundle.utils.device.ConnectivityMonitor.a;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.util.List;
import java.util.Map;

public class TtsCommonFileManager {
    private static final String COMMON_FILE_DIR = TtsManagerUtil.getCommonFilePath();
    private static final String TEMP_COMMONFILE_NAME = "common_temp.irf";
    /* access modifiers changed from: private */
    public static final String TEMP_COMMON_FILE_PATH;
    private static TtsCommonFileManager mInstance;
    private final long COMMON_FILE_SIZE = 14;
    private final int STATE_CHECKED = 3;
    private final int STATE_CHECKING = 1;
    private final int STATE_DOWNLOADING = 2;
    private final int STATE_IDLE = 0;
    private final String TAG = "TtsCommonFileManager";
    private a mConnectivityObserver;
    bjf mDownloadCallback = new bjf() {
        public void onProgressUpdate(long j, long j2) {
        }

        public void onStart(long j, Map<String, List<String>> map, int i) {
            TtsCommonFileManager.this.log("  DownloadCallback  onStart ");
        }

        public void onFinish(bpk bpk) {
            TtsCommonFileManager.this.log("  DownloadCallback  onFinish ");
            if (TtsCommonFileManager.this.checkFileIsValid(TtsCommonFileManager.TEMP_COMMON_FILE_PATH)) {
                TtsCommonFileManager.this.copyTempCommonToUserData();
                String commonFileFullName = TtsManagerUtil.getCommonFileFullName();
                if (!TtsCommonFileManager.this.checkFileIsValid(commonFileFullName)) {
                    File file = new File(commonFileFullName);
                    if (file.exists()) {
                        file.delete();
                    }
                    TtsManagerUtil.checkCommonTtsFile();
                    TtsCommonFileManager.this.log("   *********** new common.irf is error unzip Resource_6.0_common.png ******* ");
                } else {
                    TtsCommonFileManager.this.log("   *********** new common.irf is ok ******* ");
                }
                TtsCommonFileManager.this.deleteTempCommonFile();
            } else {
                TtsCommonFileManager.this.deleteTempCommonFile();
            }
            TtsCommonFileManager.this.setEnd();
        }

        public void onError(int i, int i2) {
            TtsCommonFileManager.this.log("  DownloadCallback  onError ");
            TtsCommonFileManager.this.setEnd();
        }
    };
    /* access modifiers changed from: private */
    public int mState = 0;

    class VoiceCommonIrfThread extends a<Void> {
        private VoiceCommonIrfThread() {
        }

        /* access modifiers changed from: protected */
        public Void doBackground() throws Exception {
            TtsCommonFileManager ttsCommonFileManager = TtsCommonFileManager.this;
            StringBuilder sb = new StringBuilder("  VoiceCommonIrfThread  doBackground ");
            sb.append(Thread.currentThread().getName());
            ttsCommonFileManager.log(sb.toString());
            String commonFileFullName = TtsManagerUtil.getCommonFileFullName();
            TtsCommonFileManager.this.log("  VoiceCommonIrfThread commonFilePath:".concat(String.valueOf(commonFileFullName)));
            if (!TtsCommonFileManager.this.checkFileIsValid(commonFileFullName)) {
                if (TtsCommonFileManager.this.checkFileIsValid(TtsCommonFileManager.TEMP_COMMON_FILE_PATH)) {
                    TtsCommonFileManager.this.copyTempCommonToUserData();
                } else {
                    String string = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("voiceCommonUrl", "");
                    TtsCommonFileManager.this.log("  VoiceCommonIrfThread newCommonUrl:".concat(String.valueOf(string)));
                    if (!TextUtils.isEmpty(string)) {
                        TtsCommonFileManager.this.deleteTempCommonFile();
                        TtsCommonFileManager.this.startDownloadCommonFile(string);
                    }
                }
            }
            return null;
        }

        public void onError(Throwable th) {
            TtsCommonFileManager.this.log("  VoiceCommonIrfThread  onError:".concat(String.valueOf(th)));
            TtsCommonFileManager.this.setEnd();
        }

        /* access modifiers changed from: protected */
        public void onFinished(Void voidR) {
            if (TtsCommonFileManager.this.mState != 2) {
                TtsCommonFileManager.this.setEnd();
            } else {
                TtsCommonFileManager.this.log("  VoiceCommonIrfThread  onFinished,  downloading common file ...");
            }
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMON_FILE_DIR);
        sb.append("/common_temp.irf");
        TEMP_COMMON_FILE_PATH = sb.toString();
    }

    public static synchronized TtsCommonFileManager getInstance() {
        TtsCommonFileManager ttsCommonFileManager;
        synchronized (TtsCommonFileManager.class) {
            try {
                if (mInstance == null) {
                    mInstance = new TtsCommonFileManager();
                }
                ttsCommonFileManager = mInstance;
            }
        }
        return ttsCommonFileManager;
    }

    private TtsCommonFileManager() {
    }

    public void checkUpdateVoiceCommon() {
        if (this.mState != 0 && this.mState != 3) {
            return;
        }
        if (!NetworkReachability.a()) {
            deleteTempCommonFile();
            return;
        }
        this.mState = 1;
        regestConnectivityChangeObserver();
        ahl.b(new VoiceCommonIrfThread());
    }

    /* access modifiers changed from: private */
    public void setEnd() {
        log("   =========== setEnd ");
        this.mState = 3;
        unregestConnectivityChangeObserver();
    }

    /* access modifiers changed from: private */
    public boolean checkFileIsValid(String str) {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return false;
        }
        String string = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("voiceCommonMd5", "");
        String a = agy.a(new File(str), null, true);
        log("  checkFileIsValid  localCommonMd5:".concat(String.valueOf(a)));
        log("  checkFileIsValid serverCommonMd5:".concat(String.valueOf(string)));
        if (a == null || !a.equalsIgnoreCase(string)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void deleteTempCommonFile() {
        File file = new File(TEMP_COMMON_FILE_PATH);
        if (file.exists()) {
            log("  deleteTempCommonFile ");
            file.delete();
        }
    }

    /* access modifiers changed from: private */
    public void startDownloadCommonFile(String str) {
        log("  downloadCommonFile  url:".concat(String.valueOf(str)));
        if (!isStorageEnough()) {
            log("  downloadCommonFile  storage is not enough");
            return;
        }
        bjg bjg = new bjg(TEMP_COMMON_FILE_PATH);
        bjg.setUrl(str);
        bjh.a().a(bjg, this.mDownloadCallback);
        this.mState = 2;
    }

    /* access modifiers changed from: private */
    public void stopDownload() {
        log("   stopDownload ");
        bjh.a().a(TEMP_COMMON_FILE_PATH);
        deleteTempCommonFile();
        setEnd();
    }

    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r10v0, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r11v0, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r10v2, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r11v1 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r11v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r10v5, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v7, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r10v7 */
    /* JADX WARNING: type inference failed for: r10v8 */
    /* JADX WARNING: type inference failed for: r10v9, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r11v5, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.nio.channels.ReadableByteChannel] */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: type inference failed for: r10v16 */
    /* JADX WARNING: type inference failed for: r11v6 */
    /* JADX WARNING: type inference failed for: r11v7 */
    /* JADX WARNING: type inference failed for: r11v8 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v3
      assigns: []
      uses: []
      mth insns count: 132
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ad A[SYNTHETIC, Splitter:B:58:0x00ad] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00b7 A[SYNTHETIC, Splitter:B:63:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00c1 A[SYNTHETIC, Splitter:B:68:0x00c1] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00c9 A[Catch:{ IOException -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00d6 A[SYNTHETIC, Splitter:B:79:0x00d6] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x00e0 A[SYNTHETIC, Splitter:B:84:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00ea A[SYNTHETIC, Splitter:B:89:0x00ea] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x00f2 A[Catch:{ IOException -> 0x00ee }] */
    /* JADX WARNING: Unknown variable types count: 22 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void copyTempCommonToUserData() {
        /*
            r12 = this;
            java.lang.String r0 = "   copyTempCommonToUserData "
            r12.log(r0)
            r0 = 0
            java.lang.String r1 = com.iflytek.tts.TtsService.TtsManagerUtil.getCommonFileFullName()     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            boolean r1 = r2.exists()     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            if (r1 == 0) goto L_0x0018
            r2.delete()     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
        L_0x0018:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            java.lang.String r3 = TEMP_COMMON_FILE_PATH     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            boolean r3 = r1.exists()     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            if (r3 == 0) goto L_0x007d
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x00d1, all -> 0x00a7 }
            java.nio.channels.FileChannel r10 = r3.getChannel()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            r11.<init>(r2)     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            java.nio.channels.FileChannel r2 = r11.getChannel()     // Catch:{ Exception -> 0x006c, all -> 0x006a }
            r6 = 0
            long r8 = r10.size()     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            r4 = r2
            r5 = r10
            r4.transferFrom(r5, r6, r8)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            long r0 = r1.length()     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            com.amap.bundle.mapstorage.MapSharePreference r4 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            java.lang.String r5 = "SharedPreferences"
            r4.<init>(r5)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            java.lang.String r5 = "tts_common"
            r4.putLongValue(r5, r0)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            java.lang.String r4 = "new common file size:"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            java.lang.String r0 = r4.concat(r0)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            r12.log(r0)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            java.lang.String r0 = "copyTempCommonToUserData end"
            r12.log(r0)     // Catch:{ Exception -> 0x006d, all -> 0x0067 }
            r0 = r3
            goto L_0x0080
        L_0x0067:
            r1 = move-exception
            r0 = r2
            goto L_0x00ab
        L_0x006a:
            r1 = move-exception
            goto L_0x00ab
        L_0x006c:
            r2 = r0
        L_0x006d:
            r0 = r11
            goto L_0x00d4
        L_0x0070:
            r1 = move-exception
            r11 = r0
            goto L_0x00ab
        L_0x0073:
            r2 = r0
            goto L_0x00d4
        L_0x0076:
            r1 = move-exception
            r10 = r0
            goto L_0x00aa
        L_0x0079:
            r2 = r0
            r10 = r2
            goto L_0x00d4
        L_0x007d:
            r2 = r0
            r10 = r2
            r11 = r10
        L_0x0080:
            if (r0 == 0) goto L_0x008a
            r0.close()     // Catch:{ IOException -> 0x0086 }
            goto L_0x008a
        L_0x0086:
            r0 = move-exception
            r0.printStackTrace()
        L_0x008a:
            if (r11 == 0) goto L_0x0094
            r11.close()     // Catch:{ IOException -> 0x0090 }
            goto L_0x0094
        L_0x0090:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0094:
            if (r10 == 0) goto L_0x009c
            r10.close()     // Catch:{ IOException -> 0x009a }
            goto L_0x009c
        L_0x009a:
            r0 = move-exception
            goto L_0x00a2
        L_0x009c:
            if (r2 == 0) goto L_0x00a6
            r2.close()     // Catch:{ IOException -> 0x009a }
            goto L_0x00a6
        L_0x00a2:
            r0.printStackTrace()
            return
        L_0x00a6:
            return
        L_0x00a7:
            r1 = move-exception
            r3 = r0
            r10 = r3
        L_0x00aa:
            r11 = r10
        L_0x00ab:
            if (r3 == 0) goto L_0x00b5
            r3.close()     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00b5
        L_0x00b1:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00b5:
            if (r11 == 0) goto L_0x00bf
            r11.close()     // Catch:{ IOException -> 0x00bb }
            goto L_0x00bf
        L_0x00bb:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00bf:
            if (r10 == 0) goto L_0x00c7
            r10.close()     // Catch:{ IOException -> 0x00c5 }
            goto L_0x00c7
        L_0x00c5:
            r0 = move-exception
            goto L_0x00cd
        L_0x00c7:
            if (r0 == 0) goto L_0x00d0
            r0.close()     // Catch:{ IOException -> 0x00c5 }
            goto L_0x00d0
        L_0x00cd:
            r0.printStackTrace()
        L_0x00d0:
            throw r1
        L_0x00d1:
            r2 = r0
            r3 = r2
            r10 = r3
        L_0x00d4:
            if (r3 == 0) goto L_0x00de
            r3.close()     // Catch:{ IOException -> 0x00da }
            goto L_0x00de
        L_0x00da:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00de:
            if (r0 == 0) goto L_0x00e8
            r0.close()     // Catch:{ IOException -> 0x00e4 }
            goto L_0x00e8
        L_0x00e4:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00e8:
            if (r10 == 0) goto L_0x00f0
            r10.close()     // Catch:{ IOException -> 0x00ee }
            goto L_0x00f0
        L_0x00ee:
            r0 = move-exception
            goto L_0x00f6
        L_0x00f0:
            if (r2 == 0) goto L_0x00fa
            r2.close()     // Catch:{ IOException -> 0x00ee }
            goto L_0x00fa
        L_0x00f6:
            r0.printStackTrace()
            return
        L_0x00fa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.TtsCommonFileManager.copyTempCommonToUserData():void");
    }

    private boolean isStorageEnough() {
        return getAvailableSizeMB() > 14;
    }

    private long getAvailableSizeMB() {
        long j;
        try {
            StatFs statFs = new StatFs(COMMON_FILE_DIR);
            if (VERSION.SDK_INT >= 18) {
                j = (statFs.getAvailableBytes() / 1024) / 1024;
            } else {
                j = ((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1024) / 1024;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            j = 0;
        }
        log("  getAvailableSizeMB  ".concat(String.valueOf(j)));
        return j;
    }

    private void regestConnectivityChangeObserver() {
        if (this.mConnectivityObserver == null) {
            this.mConnectivityObserver = getConnectivityObserver();
            ConnectivityMonitor.a().a(this.mConnectivityObserver);
        }
    }

    private void unregestConnectivityChangeObserver() {
        if (this.mConnectivityObserver != null) {
            ConnectivityMonitor.a().b(this.mConnectivityObserver);
        }
    }

    private a getConnectivityObserver() {
        return new a() {
            public void onConnectivityChanged(int i, int i2) {
                if (i != 4) {
                    TtsCommonFileManager.this.log("  onConnectivityChanged  切到非wifi网络，停止下载");
                    TtsCommonFileManager.this.stopDownload();
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "TtsCommonFileManager   ".concat(String.valueOf(str)));
        }
    }
}
