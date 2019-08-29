package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SilentDownloadService;
import com.alipay.mobile.framework.service.common.SilentDownloadService.SilentDownloadCallback;
import com.alipay.mobile.util.wifichecker.WifiChecker;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class SilentDownloadServiceImpl extends SilentDownloadService {
    /* access modifiers changed from: private */
    public final String a = SilentDownloadService.class.getSimpleName();
    /* access modifiers changed from: private */
    public HashMap<String, Future<?>> b;
    private final DownloadManager c = new DownloadManager(LauncherApplicationAgent.getInstance().getApplicationContext());
    /* access modifiers changed from: private */
    public Future<?> d;
    WifiChecker mWifiChecker;

    public SilentDownloadServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void startSilentDownload(String url, String path, SilentDownloadCallback callback) {
        LoggerFactory.getTraceLogger().info(this.a, "url : " + url + " path : " + path);
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            LoggerFactory.getTraceLogger().error(this.a, (String) "invalid params");
            return;
        }
        if (this.b == null) {
            this.b = new HashMap<>();
        } else if (this.b.get(url + path) != null) {
            LoggerFactory.getTraceLogger().info(this.a, "exist future");
            return;
        } else {
            LoggerFactory.getTraceLogger().info(this.a, "future need execute");
        }
        final AtomicInteger count = new AtomicInteger();
        final String str = url;
        final String str2 = path;
        final SilentDownloadCallback silentDownloadCallback = callback;
        this.d = this.c.addDownload(url, path, null, new TransportCallback() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void onPostExecute(Request request, Response response) {
                LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "onPostExecute, remove future = " + str + str2);
                if (SilentDownloadServiceImpl.this.b != null) {
                    SilentDownloadServiceImpl.this.b.remove(str + str2);
                }
                if (silentDownloadCallback != null) {
                    silentDownloadCallback.onComplete(request, response, str2);
                }
                LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "下载成功, response = " + response.getContentType());
            }

            public void onProgressUpdate(Request request, double percent) {
                LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "下载进度, percent:" + percent);
                if (silentDownloadCallback != null) {
                    silentDownloadCallback.onProgress(request, percent);
                }
                if (percent > ((double) count.get()) * 0.02d) {
                    if (count.get() == 0) {
                        count.set((int) (percent / 0.02d));
                        LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "初始化情况 ,count:" + count.get());
                    }
                    LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "盲点检查,percent:" + percent);
                    if (!SilentDownloadServiceImpl.this.isWifi()) {
                        LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "执行中断,检测到当前运行网络发生变化");
                        throw new RuntimeException("当前网络环境不满足下载条件");
                    }
                    LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "isWifi");
                    count.incrementAndGet();
                    if (SilentDownloadServiceImpl.this.a()) {
                        LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "WifiValid");
                    } else {
                        LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "WifiInValid");
                        throw new RuntimeException("假wifi不满足下载条件");
                    }
                }
            }

            public void onFailed(Request request, int code, String msg) {
                if (SilentDownloadServiceImpl.this.b != null) {
                    SilentDownloadServiceImpl.this.b.remove(str + str2);
                }
                if (silentDownloadCallback != null) {
                    silentDownloadCallback.onFailed(request, code, msg, str2);
                }
                LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "onFailed, remove future = " + str + str2);
            }

            public void onCancelled(Request request) {
                if (SilentDownloadServiceImpl.this.b != null) {
                    SilentDownloadServiceImpl.this.b.remove(str + str2);
                }
                if (silentDownloadCallback != null) {
                    silentDownloadCallback.onCancel(request, str2);
                }
                LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "onCancelled, remove future = " + str + str2);
            }

            public void onPreExecute(Request request) {
                if (SilentDownloadServiceImpl.this.b != null) {
                    SilentDownloadServiceImpl.this.b.put(str + str2, SilentDownloadServiceImpl.this.d);
                }
                if (silentDownloadCallback != null) {
                    silentDownloadCallback.onStart(request, str2);
                }
                if (!SilentDownloadServiceImpl.this.isWifi() || !SilentDownloadServiceImpl.this.a()) {
                    LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "执行中断,当前非wifi: isWifi? = " + SilentDownloadServiceImpl.this.isWifi() + " checkWifiValid? = " + SilentDownloadServiceImpl.this.a());
                    throw new RuntimeException("当前网络环境不满足下载条件");
                } else {
                    LoggerFactory.getTraceLogger().info(SilentDownloadServiceImpl.this.a, "onPreExecute");
                }
            }
        });
    }

    public void stopSilentDownload(String url, String path) {
        if (this.b == null) {
            LoggerFactory.getTraceLogger().info(this.a, "silentFuture == null");
        } else if (this.b.size() <= 0) {
            LoggerFactory.getTraceLogger().info(this.a, "silentFuture.size() <= 0");
        } else {
            Future future = this.b.get(url + path);
            if (future != null) {
                LoggerFactory.getTraceLogger().info(this.a, "future.cancel(true) : " + url + path);
                future.cancel(true);
                this.b.remove(url + path);
            }
            if (!future.isCancelled()) {
                throw new RuntimeException("停止下载");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
    }

    /* access modifiers changed from: private */
    public boolean a() {
        if (this.mWifiChecker == null) {
            Context context = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext();
            if (context == null) {
                return true;
            }
            this.mWifiChecker = WifiChecker.a(context);
        }
        return this.mWifiChecker.a();
    }

    public boolean isWifi() {
        Context context = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext();
        if (PermissionChecker.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") == -1) {
            return false;
        }
        NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null || !info.isAvailable() || info.getType() != 1) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044 A[SYNTHETIC, Splitter:B:15:0x0044] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMd5ByFile(java.lang.String r12) {
        /*
            r8 = 0
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0039, all -> 0x0041 }
            r7.<init>(r12)     // Catch:{ Exception -> 0x0039, all -> 0x0041 }
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0039, all -> 0x0041 }
            r9.<init>(r7)     // Catch:{ Exception -> 0x0039, all -> 0x0041 }
            java.nio.channels.FileChannel r0 = r9.getChannel()     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r2 = 0
            long r4 = r7.length()     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            java.nio.MappedByteBuffer r6 = r0.map(r1, r2, r4)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            java.lang.String r0 = "MD5"
            java.security.MessageDigest r10 = java.security.MessageDigest.getInstance(r0)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r10.update(r6)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            java.math.BigInteger r0 = new java.math.BigInteger     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r1 = 1
            byte[] r2 = r10.digest()     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r1 = 16
            java.lang.String r11 = r0.toString(r1)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            r9.close()     // Catch:{ IOException -> 0x0048 }
        L_0x0037:
            r8 = r9
        L_0x0038:
            return r11
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            if (r8 == 0) goto L_0x003f
            r8.close()     // Catch:{ IOException -> 0x004a }
        L_0x003f:
            r11 = 0
            goto L_0x0038
        L_0x0041:
            r0 = move-exception
        L_0x0042:
            if (r8 == 0) goto L_0x0047
            r8.close()     // Catch:{ IOException -> 0x004c }
        L_0x0047:
            throw r0
        L_0x0048:
            r0 = move-exception
            goto L_0x0037
        L_0x004a:
            r0 = move-exception
            goto L_0x003f
        L_0x004c:
            r1 = move-exception
            goto L_0x0047
        L_0x004e:
            r0 = move-exception
            r8 = r9
            goto L_0x0042
        L_0x0051:
            r0 = move-exception
            r8 = r9
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.service.common.impl.SilentDownloadServiceImpl.getMd5ByFile(java.lang.String):java.lang.String");
    }

    public boolean isDownloading(String url, String path) {
        if (this.b == null || this.b.get(url + path) == null) {
            return false;
        }
        return true;
    }
}
