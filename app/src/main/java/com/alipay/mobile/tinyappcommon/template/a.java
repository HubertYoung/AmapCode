package com.alipay.mobile.tinyappcommon.template;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/* compiled from: TemplateDownloadManager */
public class a {
    /* access modifiers changed from: private */
    public static final String a = a.class.getSimpleName();
    private ConcurrentHashMap<String, Future> b;

    /* renamed from: com.alipay.mobile.tinyappcommon.template.a$a reason: collision with other inner class name */
    /* compiled from: TemplateDownloadManager */
    private static class C0102a {
        /* access modifiers changed from: private */
        public static final a a = new a(0);
    }

    /* synthetic */ a(byte b2) {
        this();
    }

    private a() {
    }

    public static a a() {
        return C0102a.a;
    }

    private boolean a(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            H5Log.w(a, "isDownloading..downloadUrl is null");
            return false;
        } else if (this.b == null || this.b.get(downloadUrl) == null) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean c(String appId, String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            H5Log.w(a, "isDownloaded..downloadUrl is null");
            return false;
        }
        try {
            File file = new File(b(appId, downloadUrl));
            if (!file.isFile() || !file.exists()) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            H5Log.e(a, "isDownloaded...e:" + e);
            return false;
        }
    }

    public final void a(String appId, final String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            H5Log.w(a, "addDownloadTask..downloadUrl is null");
        } else if (a(downloadUrl)) {
            H5Log.w(a, "addDownloadTask..isDownloading..." + downloadUrl);
        } else if (c(appId, downloadUrl)) {
            H5Log.w(a, "addDownloadTask..isDownloaded..." + downloadUrl);
        } else {
            DownloadService downloadService = (DownloadService) H5Utils.findServiceByInterface(DownloadService.class.getName());
            if (downloadService == null) {
                H5Log.e(a, (String) "downloadService == null");
                return;
            }
            d();
            DownloadRequest downloadRequest = new DownloadRequest(downloadUrl);
            downloadRequest.setPath(b(appId, downloadUrl));
            downloadRequest.setTransportCallback(new TransportCallback() {
                public final void onCancelled(Request request) {
                    H5Log.d(a.a, "onCancelled...url: " + downloadUrl);
                    a.this.b(downloadUrl);
                }

                public final void onPreExecute(Request request) {
                }

                public final void onPostExecute(Request request, Response response) {
                    H5Log.d(a.a, "onPostExecute...url: " + downloadUrl);
                    a.this.b(downloadUrl);
                }

                public final void onProgressUpdate(Request request, double percent) {
                }

                public final void onFailed(Request request, int code, String msg) {
                    H5Log.d(a.a, "onFailed...url: " + downloadUrl);
                    a.this.b(downloadUrl);
                }
            });
            Future task = downloadService.addDownload(downloadRequest);
            if (task != null) {
                if (this.b == null) {
                    this.b = new ConcurrentHashMap<>();
                }
                this.b.put(downloadUrl, task);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(String downloadUrl) {
        if (!TextUtils.isEmpty(downloadUrl) && this.b != null) {
            this.b.remove(downloadUrl);
        }
    }

    public static String b(String appId, String downloadUrl) {
        String cacheDir = c();
        if (TextUtils.isEmpty(cacheDir)) {
            return "";
        }
        return cacheDir + d(appId, downloadUrl);
    }

    private static String d(String appId, String downloadUrl) {
        return appId + "_" + H5SecurityUtil.getMD5(downloadUrl);
    }

    private static String c() {
        try {
            return H5Utils.getContext().getFilesDir().getAbsolutePath() + "/nebulaInstallApps/template_app_config/";
        } catch (Throwable throwable) {
            H5Log.e(a, throwable);
            return "";
        }
    }

    private static void d() {
        String templateConfigDiskCacheDir = c();
        if (!TextUtils.isEmpty(templateConfigDiskCacheDir)) {
            try {
                new File(templateConfigDiskCacheDir).mkdirs();
            } catch (Throwable e) {
                H5Log.e(a, "checkTemplateConfigDiskCacheDirExist..e:" + e);
            }
        }
    }
}
