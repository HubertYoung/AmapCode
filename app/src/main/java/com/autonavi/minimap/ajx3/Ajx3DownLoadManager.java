package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.core.network.inter.response.StringResponse;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3SpUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.UriUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Ajx3DownLoadManager {
    public static final String DOWNLOAD_AJX_FILE_NAME = "all_ajx.zip";
    public static final String DOWNLOAD_AJX_TAR_FILE_NAME = "all_ajx.tar.gz";
    private static final String TAG = "Ajx3FileDownloadThread";
    /* access modifiers changed from: private */
    public String mAjxPageTextPath;
    /* access modifiers changed from: private */
    public AtomicInteger mCurFileIndex;
    /* access modifiers changed from: private */
    public DownloadListener mDownloadListener;
    /* access modifiers changed from: private */
    public String mJsRelativePath;
    /* access modifiers changed from: private */
    public String mJsUrl;
    /* access modifiers changed from: private */
    public String mLocalPath;
    /* access modifiers changed from: private */
    public int mTotal;
    /* access modifiers changed from: private */
    public LinkedList<String> urlQueue = new LinkedList<>();

    class DepFile {
        public String localPath;
        public String md5;
        public String path;
        public String url;

        private DepFile() {
        }
    }

    public interface DownloadListener {
        void onFinish(String str);

        void onProgress(String str, int i, int i2);

        void setDownloadUrl(String str);
    }

    class GetJsonCallback implements bpl<StringResponse> {
        private final String mBaseUrl;
        private final String mCacheDir;
        private final String mJsonUrl;

        public GetJsonCallback(String str, String str2, String str3) {
            this.mCacheDir = str3;
            this.mBaseUrl = str;
            this.mJsonUrl = str2;
        }

        public void onSuccess(StringResponse stringResponse) {
            String responseBodyString = stringResponse.getResponseBodyString();
            try {
                AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#GetJsonCallback onSuccess:".concat(String.valueOf(responseBodyString)));
                HashMap hashMap = new HashMap();
                StringBuilder sb = new StringBuilder();
                sb.append(emx.b(this.mJsonUrl));
                sb.append(".txt");
                File file = new File(this.mCacheDir, sb.toString());
                if (file.exists()) {
                    StringBuilder sb2 = new StringBuilder();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb2.append(readLine);
                        }
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#GetJsonCallback onSuccess cache:".concat(String.valueOf(sb2)));
                    JSONObject jSONObject = new JSONObject(sb2.toString());
                    Ajx3DownLoadManager.this.mAjxPageTextPath = jSONObject.optString("ajxPageTextPath");
                    JSONArray optJSONArray = jSONObject.optJSONArray("resourceMap");
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        String optString = optJSONObject.optString("path");
                        DepFile depFile = new DepFile();
                        depFile.md5 = optJSONObject.optString("md5");
                        depFile.path = optString;
                        depFile.url = cnz.a(this.mBaseUrl, optString);
                        File file2 = new File(this.mCacheDir, UriUtils.removeParams(optString));
                        if (file2.exists() && file2.isFile() && file2.length() > 0) {
                            depFile.localPath = file2.getPath();
                            hashMap.put(depFile.path, depFile.md5);
                        }
                    }
                }
                JSONObject jSONObject2 = new JSONObject(responseBodyString);
                if (jSONObject2.has("ajxPageTextPath")) {
                    Ajx3DownLoadManager.this.mAjxPageTextPath = jSONObject2.optString("ajxPageTextPath");
                }
                JSONArray optJSONArray2 = jSONObject2.optJSONArray("resourceMap");
                for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                    JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                    String optString2 = optJSONObject2.optString("path");
                    String optString3 = optJSONObject2.optString("md5");
                    String str = (String) hashMap.get(optString2);
                    if (str == null || !str.equals(optString3)) {
                        Ajx3DownLoadManager.this.urlQueue.addLast(cnz.a(this.mBaseUrl, optString2));
                    }
                }
                Ajx3DownLoadManager.this.mTotal = Ajx3DownLoadManager.this.urlQueue.size();
                if (Ajx3DownLoadManager.this.mTotal > 0) {
                    Ajx3DownLoadManager.this.startJsDependentDownLoad(this.mCacheDir);
                } else if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                    StringBuilder sb3 = new StringBuilder("path:/");
                    sb3.append(Ajx3DownLoadManager.this.mJsRelativePath);
                    String sb4 = sb3.toString();
                    Context appContext = AMapPageUtil.getAppContext();
                    StringBuilder sb5 = new StringBuilder("file://");
                    sb5.append(cnz.a(this.mCacheDir, Ajx3DownLoadManager.this.mAjxPageTextPath));
                    Ajx3SpUtil.setJsAjxPageConfig(appContext, sb4, sb5.toString());
                    Ajx3DownLoadManager.this.mDownloadListener.onFinish(sb4);
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getPath(), false));
                bufferedWriter.write(responseBodyString);
                bufferedWriter.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public void onFailure(bph bph, ResponseException responseException) {
            if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                StringBuilder sb = new StringBuilder("file://");
                sb.append(cnz.a(this.mCacheDir, Ajx3DownLoadManager.this.mJsRelativePath));
                access$000.onFinish(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder("Ajx3FileDownloadThread#AjxDownLoadListener#onError#errorCode:");
            sb2.append(responseException.errorCode);
            sb2.append("#errmsg:");
            sb2.append(responseException.getMessage());
            AMapLog.d("ajx_down_load", sb2.toString());
        }
    }

    class JsDepenceDownLoadListener implements bjf {
        private final String mCacheDir;
        private final bjg mRequest;

        public JsDepenceDownLoadListener(bjg bjg, String str) {
            this.mRequest = bjg;
            this.mCacheDir = str;
        }

        public void onStart(long j, Map<String, List<String>> map, int i) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#JsDepenceDownLoadListener#onStart");
        }

        public void onProgressUpdate(long j, long j2) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#JsDepenceDownLoadListener#onProgressUpdate");
        }

        public void onFinish(bpk bpk) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#AjxDepenceDownLoadListener#onFinish");
            onLoaded();
        }

        public void onError(int i, int i2) {
            StringBuilder sb = new StringBuilder("文件下载失败:");
            sb.append(this.mRequest.getUrl());
            LogHelper.log(sb.toString());
            onLoaded();
            StringBuilder sb2 = new StringBuilder("Ajx3FileDownloadThread#AjxDepenceDownLoadListener#onError#errorCode:");
            sb2.append(i);
            sb2.append("#statusCode:");
            sb2.append(i2);
            AMapLog.d("ajx_down_load", sb2.toString());
        }

        private void onLoaded() {
            int addAndGet = Ajx3DownLoadManager.this.mCurFileIndex.addAndGet(1);
            StringBuilder sb = new StringBuilder("Ajx3FileDownloadThread#startJsDependentDownLoad#url:");
            sb.append(this.mRequest.getUrl());
            sb.append(" mDownloadListener:");
            sb.append(Ajx3DownLoadManager.this.mDownloadListener);
            sb.append(" mCurFileIndex:");
            sb.append(Ajx3DownLoadManager.this.mCurFileIndex);
            sb.append(" mTotal:");
            sb.append(Ajx3DownLoadManager.this.mTotal);
            AMapLog.d("ajx_down_load", sb.toString());
            if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                if (addAndGet == Ajx3DownLoadManager.this.mTotal) {
                    StringBuilder sb2 = new StringBuilder("path:/");
                    sb2.append(Ajx3DownLoadManager.this.mJsRelativePath);
                    String sb3 = sb2.toString();
                    Context appContext = AMapPageUtil.getAppContext();
                    StringBuilder sb4 = new StringBuilder("file://");
                    sb4.append(cnz.a(this.mCacheDir, Ajx3DownLoadManager.this.mAjxPageTextPath));
                    Ajx3SpUtil.setJsAjxPageConfig(appContext, sb3, sb4.toString());
                    Ajx3DownLoadManager.this.mDownloadListener.onFinish(sb3);
                    return;
                }
                DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                StringBuilder sb5 = new StringBuilder("正在下载");
                sb5.append(this.mRequest.getUrl());
                access$000.onProgress(sb5.toString(), addAndGet, Ajx3DownLoadManager.this.mTotal);
            }
        }
    }

    class JsDownloadListener implements bjf {
        private final String mAjxCacheDir;
        private final String mAjxFilePath;
        private final String mBaseUrl;
        private final WeakReference<Ajx3DownLoadManager> mWeakInstance;

        public JsDownloadListener(Ajx3DownLoadManager ajx3DownLoadManager, String str, String str2, String str3) {
            this.mWeakInstance = new WeakReference<>(ajx3DownLoadManager);
            this.mBaseUrl = str;
            this.mAjxCacheDir = str2;
            this.mAjxFilePath = str3;
        }

        public void onStart(long j, Map<String, List<String>> map, int i) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#AjxDownLoadListener#onStart");
        }

        public void onProgressUpdate(long j, long j2) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#AjxDownLoadListener#onProgressUpdate");
        }

        public void onFinish(bpk bpk) {
            AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#AjxDownLoadListener#onFinish");
            Ajx3DownLoadManager ajx3DownLoadManager = (Ajx3DownLoadManager) this.mWeakInstance.get();
            if (ajx3DownLoadManager != null) {
                ajx3DownLoadManager.onDownloadJsFinish(this.mBaseUrl, this.mAjxCacheDir, this.mAjxFilePath);
            }
            cnz.a();
        }

        public void onError(int i, int i2) {
            ToastHelper.showLongToast("js文件下载失败");
            if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                StringBuilder sb = new StringBuilder("js文件下载失败(");
                sb.append(Ajx3DownLoadManager.this.mJsUrl);
                sb.append("),errorCode:");
                sb.append(i);
                sb.append(",statusCode:");
                sb.append(i2);
                sb.append(",请退出重试");
                access$000.onProgress(sb.toString(), 0, 100);
            }
            StringBuilder sb2 = new StringBuilder("Ajx3FileDownloadThread#AjxDownLoadListener#onError#errorCode:");
            sb2.append(i);
            sb2.append("#statusCode:");
            sb2.append(i2);
            AMapLog.d("ajx_down_load", sb2.toString());
        }
    }

    public Ajx3DownLoadManager(String str) {
        this.mJsUrl = str;
        this.mLocalPath = a.a;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
        if (this.mDownloadListener != null) {
            this.mDownloadListener.setDownloadUrl(this.mJsUrl);
        }
    }

    public void startDownloadAjx() {
        String str = DOWNLOAD_AJX_FILE_NAME;
        if (this.mJsUrl.endsWith(".tar.gz")) {
            str = DOWNLOAD_AJX_TAR_FILE_NAME;
        }
        bjg bjg = new bjg(cnz.a(this.mLocalPath, str));
        bjg.setUrl(formatUrl(this.mJsUrl));
        if (this.mDownloadListener != null) {
            DownloadListener downloadListener = this.mDownloadListener;
            StringBuilder sb = new StringBuilder("正在下载:");
            sb.append(this.mJsUrl);
            downloadListener.onProgress(sb.toString(), 0, 100);
        }
        bjh.a().a(bjg, (bjf) new bjf() {
            public void onStart(long j, Map<String, List<String>> map, int i) {
            }

            public void onProgressUpdate(long j, long j2) {
                StringBuilder sb = new StringBuilder("--Ajx3DownLoadManager.onProgressUpdate :");
                sb.append(j);
                sb.append(" : ");
                sb.append(j2);
                if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                    DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                    StringBuilder sb2 = new StringBuilder("正在下载:");
                    sb2.append(Ajx3DownLoadManager.this.mJsUrl);
                    access$000.onProgress(sb2.toString(), (int) j, (int) j2);
                }
            }

            public void onFinish(bpk bpk) {
                if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                    Ajx3DownLoadManager.this.mDownloadListener.onFinish(Ajx3DownLoadManager.this.mLocalPath);
                }
            }

            public void onError(int i, int i2) {
                StringBuilder sb = new StringBuilder("--Ajx3DownLoadManager.onError :");
                sb.append(i);
                sb.append(" : ");
                sb.append(i2);
            }
        });
    }

    public void startDownload() {
        Uri parse = Uri.parse(this.mJsUrl);
        this.mJsRelativePath = parse.getPath();
        bjg bjg = new bjg(cnz.a(this.mLocalPath, this.mJsRelativePath));
        bjg.setUrl(formatUrl(this.mJsUrl));
        StringBuilder sb = new StringBuilder("Ajx3FileDownloadThread#startDownLoadAjx#ajxUrl:");
        sb.append(this.mJsUrl);
        AMapLog.d("ajx_down_load", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(parse.getScheme());
        sb2.append("://");
        sb2.append(parse.getAuthority());
        String sb3 = sb2.toString();
        if (this.mDownloadListener != null) {
            DownloadListener downloadListener = this.mDownloadListener;
            StringBuilder sb4 = new StringBuilder("正在下载:");
            sb4.append(this.mJsUrl);
            downloadListener.onProgress(sb4.toString(), 0, 100);
        }
        bjh a = bjh.a();
        JsDownloadListener jsDownloadListener = new JsDownloadListener(this, sb3, this.mLocalPath, this.mJsRelativePath);
        a.a(bjg, (bjf) jsDownloadListener);
    }

    public void startDownloadDebugSo() {
        if (!TextUtils.isEmpty(AjxConstant.AAR_VERSION)) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getFilesDir().getPath());
            sb.append("/libajx_v3.so");
            final String sb2 = sb.toString();
            bjg bjg = new bjg(sb2);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(AjxConstant.HTTP_DEBUG_SO_HOST);
            sb3.append("debugso/");
            sb3.append(AjxConstant.AAR_VERSION);
            sb3.append(".so");
            bjg.setUrl(sb3.toString());
            if (this.mDownloadListener != null) {
                this.mDownloadListener.onProgress("正在下载js调试引擎,将在自动重启后生效。", 0, 100);
            }
            bjh.a().a(bjg, (bjf) new bjf() {
                private boolean succeed = false;

                public void onStart(long j, Map<String, List<String>> map, int i) {
                    this.succeed = j > 0;
                }

                public void onProgressUpdate(long j, long j2) {
                    StringBuilder sb = new StringBuilder("--Ajx3DownLoadManager.onProgressUpdate :");
                    sb.append(j);
                    sb.append(" : ");
                    sb.append(j2);
                    if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                        if (this.succeed) {
                            Ajx3DownLoadManager.this.mDownloadListener.onProgress("正在下载js调试引擎,将在自动重启后生效。", (int) j, (int) j2);
                            return;
                        }
                        DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                        StringBuilder sb2 = new StringBuilder("AJX调试so文件长度为0，请联系AJX支撑组\n当前aar version");
                        sb2.append(AjxConstant.AAR_VERSION);
                        access$000.onProgress(sb2.toString(), 0, 1);
                    }
                }

                public void onFinish(bpk bpk) {
                    if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                        if (this.succeed) {
                            Ajx3DownLoadManager.this.mDownloadListener.onFinish(Ajx3DownLoadManager.this.mLocalPath);
                            return;
                        }
                        File file = new File(sb2);
                        if (file.exists()) {
                            file.delete();
                        }
                        DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                        StringBuilder sb = new StringBuilder("AJX调试so文件长度为0，请联系AJX支撑组\n当前aar version");
                        sb.append(AjxConstant.AAR_VERSION);
                        access$000.onProgress(sb.toString(), 0, 1);
                    }
                }

                public void onError(int i, int i2) {
                    StringBuilder sb = new StringBuilder("--Ajx3DownLoadManager.onError :");
                    sb.append(i);
                    sb.append(" : ");
                    sb.append(i2);
                    if (Ajx3DownLoadManager.this.mDownloadListener != null) {
                        DownloadListener access$000 = Ajx3DownLoadManager.this.mDownloadListener;
                        StringBuilder sb2 = new StringBuilder("下载失败\n1.请确认是否连接inc内网\n2.请确认是否为普通测试包（地址消毒剂不可调试）\n3.确认1&2无误后请联系支撑组解决\n当前aar version");
                        sb2.append(AjxConstant.AAR_VERSION);
                        access$000.onProgress(sb2.toString(), 0, 1);
                    }
                }
            });
            return;
        }
        if (this.mDownloadListener != null) {
            DownloadListener downloadListener = this.mDownloadListener;
            StringBuilder sb4 = new StringBuilder("此安装包不支持调试，请更换测试包\n当前aar version: null");
            sb4.append(AjxConstant.AAR_VERSION);
            downloadListener.onProgress(sb4.toString(), 0, 1);
        }
    }

    private void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File deleteFile : listFiles) {
                    deleteFile(deleteFile);
                }
            }
            file.delete();
        }
    }

    /* access modifiers changed from: private */
    public void onDownloadJsFinish(String str, String str2, String str3) {
        String appendToPath = UriUtils.appendToPath(this.mJsUrl, ".json");
        AMapLog.d("ajx_down_load", "Ajx3FileDownloadThread#onDownloadJsFinish:".concat(String.valueOf(appendToPath)));
        bpf bpf = new bpf();
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        bpf.setUrl(UriUtils.addParam(appendToPath, "timestamp", sb.toString()));
        if (this.mDownloadListener != null) {
            this.mDownloadListener.onProgress("正在获取依赖文件...", 0, 100);
        }
        box.a();
        box.a((bph) bpf, (bpl<T>) new GetJsonCallback<T>(str, appendToPath, str2));
    }

    /* access modifiers changed from: private */
    public void startJsDependentDownLoad(String str) {
        this.mCurFileIndex = new AtomicInteger(0);
        Iterator it = this.urlQueue.iterator();
        while (it.hasNext()) {
            startJsDependentDownLoad((String) it.next(), str);
        }
        this.urlQueue.clear();
    }

    private void startJsDependentDownLoad(String str, String str2) {
        bjg bjg = new bjg(new File(str2, Uri.parse(str).getPath()).getAbsolutePath());
        bjg.setUrl(str);
        bjh.a().a(bjg, (bjf) new JsDepenceDownLoadListener(bjg, str2));
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
    public static java.lang.String toUtf8String(java.lang.String r8) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0007:
            int r3 = r8.length()
            if (r2 >= r3) goto L_0x0054
            char r3 = r8.charAt(r2)
            if (r3 < 0) goto L_0x001b
            r4 = 255(0xff, float:3.57E-43)
            if (r3 > r4) goto L_0x001b
            r0.append(r3)
            goto L_0x0051
        L_0x001b:
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0027 }
            java.lang.String r4 = "utf-8"
            byte[] r3 = r3.getBytes(r4)     // Catch:{ Exception -> 0x0027 }
            goto L_0x002b
        L_0x0027:
            java.io.PrintStream r3 = java.lang.System.out
            byte[] r3 = new byte[r1]
        L_0x002b:
            r4 = 0
        L_0x002c:
            int r5 = r3.length
            if (r4 >= r5) goto L_0x0051
            byte r5 = r3[r4]
            if (r5 >= 0) goto L_0x0035
            int r5 = r5 + 256
        L_0x0035:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "%"
            r6.<init>(r7)
            java.lang.String r5 = java.lang.Integer.toHexString(r5)
            java.lang.String r5 = r5.toUpperCase()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r0.append(r5)
            int r4 = r4 + 1
            goto L_0x002c
        L_0x0051:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0054:
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.Ajx3DownLoadManager.toUtf8String(java.lang.String):java.lang.String");
    }
}
