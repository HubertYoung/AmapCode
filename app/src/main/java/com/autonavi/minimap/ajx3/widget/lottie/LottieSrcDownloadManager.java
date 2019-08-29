package com.autonavi.minimap.ajx3.widget.lottie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.bundle.uitemplate.util.LottieDownloadUtil.LottieProperty;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottieSrcDownloadManager {
    private static final String LOTTIE_IMAGE_PATH_NAME = "/images";
    /* access modifiers changed from: private */
    public static final String LOTTIE_SD_PARENT_FOLDER;
    private static final String LOTTIE_ZIP_NAME = "/source.zip";
    private static LottieSrcDownloadManager instance = new LottieSrcDownloadManager();
    private Map<String, List<LottieSrcListener>> mLottieSrcListenerMap = new HashMap();

    interface LottieSrcListener {
        void onDealSrcFailed();

        void onDealSrcFinish(String str, String str2);
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append(LottieProperty.LOTTIE_SD_PARENT_FOLDER);
        LOTTIE_SD_PARENT_FOLDER = sb.toString();
    }

    private LottieSrcDownloadManager() {
    }

    public static LottieSrcDownloadManager getInstance() {
        return instance;
    }

    /* access modifiers changed from: private */
    public boolean dealCacheFile(@NonNull String str, @NonNull File file) {
        if (!file.exists()) {
            return false;
        }
        File lottieJson = getLottieJson(file.getAbsolutePath());
        if (lottieJson == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lottieJson.getParent());
        sb.append("/images");
        notifyDealSrcFinish(str, lottieJson.getAbsolutePath(), sb.toString());
        return true;
    }

    /* access modifiers changed from: private */
    public void dealZipFile(@NonNull final String str, @Nullable File file) {
        if (file != null && file.exists()) {
            final String parent = file.getParent();
            try {
                ahf.a(file, file.getParent(), (a) new a() {
                    public void onFinishProgress(long j) {
                        if (j == 100) {
                            File access$000 = LottieSrcDownloadManager.this.getLottieJson(parent);
                            if (access$000 == null || !access$000.exists()) {
                                LottieSrcDownloadManager.this.notifyDealSrcFail(str);
                            } else {
                                LottieSrcDownloadManager lottieSrcDownloadManager = LottieSrcDownloadManager.this;
                                String str = str;
                                String absolutePath = access$000.getAbsolutePath();
                                StringBuilder sb = new StringBuilder();
                                sb.append(access$000.getParent());
                                sb.append("/images");
                                lottieSrcDownloadManager.notifyDealSrcFinish(str, absolutePath, sb.toString());
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(parent);
                            sb2.append("/source.zip");
                            File file = new File(sb2.toString());
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                notifyDealSrcFail(str);
            }
        } else if (file == null || !dealCacheFile(str, file.getParentFile())) {
            notifyDealSrcFail(str);
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public File getLottieJson(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                File lottieJson = getLottieJson(file2.getAbsolutePath());
                if (lottieJson != null) {
                    return lottieJson;
                }
            } else if (file2.getAbsolutePath().toLowerCase().endsWith(".json") && !file2.getName().toLowerCase().startsWith(".")) {
                return file2;
            }
        }
        return null;
    }

    private String formatUrl(String str) {
        if (str.startsWith("﻿")) {
            str = str.substring(1);
        }
        return toUtf8String(str);
    }

    /* JADX WARNING: type inference failed for: r3v2, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String toUtf8String(java.lang.String r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0007:
            int r3 = r8.length()
            if (r2 >= r3) goto L_0x0047
            char r3 = r8.charAt(r2)
            r4 = 255(0xff, float:3.57E-43)
            if (r3 > r4) goto L_0x0019
            r0.append(r3)
            goto L_0x0044
        L_0x0019:
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0025 }
            java.lang.String r4 = "utf-8"
            byte[] r3 = r3.getBytes(r4)     // Catch:{ Exception -> 0x0025 }
            goto L_0x0027
        L_0x0025:
            byte[] r3 = new byte[r1]
        L_0x0027:
            int r4 = r3.length
            r5 = 0
        L_0x0029:
            if (r5 >= r4) goto L_0x0044
            byte r6 = r3[r5]
            if (r6 >= 0) goto L_0x0031
            int r6 = r6 + 256
        L_0x0031:
            java.lang.String r7 = "%"
            r0.append(r7)
            java.lang.String r6 = java.lang.Integer.toHexString(r6)
            java.lang.String r6 = r6.toUpperCase()
            r0.append(r6)
            int r5 = r5 + 1
            goto L_0x0029
        L_0x0044:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0047:
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.lottie.LottieSrcDownloadManager.toUtf8String(java.lang.String):java.lang.String");
    }

    /* access modifiers changed from: private */
    public void notifyDealSrcFinish(String str, String str2, String str3) {
        for (LottieSrcListener onDealSrcFinish : this.mLottieSrcListenerMap.get(str)) {
            onDealSrcFinish.onDealSrcFinish(str2, str3);
        }
        this.mLottieSrcListenerMap.remove(str);
    }

    /* access modifiers changed from: private */
    public void notifyDealSrcFail(String str) {
        for (LottieSrcListener onDealSrcFailed : this.mLottieSrcListenerMap.get(str)) {
            onDealSrcFailed.onDealSrcFailed();
        }
        this.mLottieSrcListenerMap.remove(str);
    }

    /* access modifiers changed from: 0000 */
    public void dealSrc(@NonNull String str, @NonNull LottieSrcListener lottieSrcListener) {
        String a = agy.a(str);
        List list = this.mLottieSrcListenerMap.get(a);
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(lottieSrcListener);
            this.mLottieSrcListenerMap.put(a, arrayList);
            StringBuilder sb = new StringBuilder();
            sb.append(LOTTIE_SD_PARENT_FOLDER);
            sb.append("/");
            sb.append(a);
            if (!dealCacheFile(a, new File(sb.toString()))) {
                startDownloadLottie(str);
            }
            return;
        }
        list.add(lottieSrcListener);
    }

    private void startDownloadLottie(@NonNull String str) {
        final String a = agy.a(str);
        StringBuilder sb = new StringBuilder();
        sb.append(LOTTIE_SD_PARENT_FOLDER);
        sb.append("/");
        sb.append(a);
        sb.append("/source.zip");
        bjg bjg = new bjg(sb.toString());
        bjg.setUrl(formatUrl(str));
        bjh.a().a(bjg, (bjf) new bjf() {
            public void onProgressUpdate(long j, long j2) {
            }

            public void onStart(long j, Map<String, List<String>> map, int i) {
            }

            public void onFinish(bpk bpk) {
                LottieSrcDownloadManager lottieSrcDownloadManager = LottieSrcDownloadManager.this;
                String str = a;
                StringBuilder sb = new StringBuilder();
                sb.append(LottieSrcDownloadManager.LOTTIE_SD_PARENT_FOLDER);
                sb.append("/");
                sb.append(a);
                sb.append("/source.zip");
                lottieSrcDownloadManager.dealZipFile(str, new File(sb.toString()));
            }

            public void onError(int i, int i2) {
                LottieSrcDownloadManager lottieSrcDownloadManager = LottieSrcDownloadManager.this;
                String str = a;
                StringBuilder sb = new StringBuilder();
                sb.append(LottieSrcDownloadManager.LOTTIE_SD_PARENT_FOLDER);
                sb.append("/");
                sb.append(a);
                if (!lottieSrcDownloadManager.dealCacheFile(str, new File(sb.toString()))) {
                    LottieSrcDownloadManager.this.notifyDealSrcFail(a);
                }
            }
        });
    }
}
