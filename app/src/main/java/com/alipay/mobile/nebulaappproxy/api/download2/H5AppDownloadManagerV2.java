package com.alipay.mobile.nebulaappproxy.api.download2;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.download.H5ExternalDownloadManager;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

public class H5AppDownloadManagerV2 implements H5ExternalDownloadManager {
    private final Handler a;
    /* access modifiers changed from: private */
    public final Map<String, Future> b = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public final Map<String, List<H5DownloadCallback>> c = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public H5PendingTaskPool d = new H5PendingTaskPool();
    private boolean e = true;
    private final Set<String> f = new HashSet();

    private class TransportCallbackWrapper implements TransportCallback {
        private int b;
        private H5DownloadRequest c;
        private String d;
        private String e;

        /* synthetic */ TransportCallbackWrapper(H5AppDownloadManagerV2 x0, H5DownloadRequest x1, String x2, byte b2) {
            this(x1, x2);
        }

        private TransportCallbackWrapper(H5DownloadRequest request, @NonNull String downloadPath) {
            this.b = 0;
            this.c = request;
            this.d = request.getDownloadUrl();
            this.e = downloadPath;
        }

        public void onPreExecute(Request request) {
            List<H5DownloadCallback> callbackList = (List) H5AppDownloadManagerV2.this.c.get(this.d);
            if (callbackList != null) {
                for (H5DownloadCallback onPrepare : callbackList) {
                    onPrepare.onPrepare(this.c);
                }
            }
        }

        public void onProgressUpdate(Request request, double v) {
            if (this.c.isNeedProgress()) {
                int percent = (int) (100.0d * v);
                if (percent - this.b > 30 && percent > 0 && percent < 100) {
                    List<H5DownloadCallback> callbackList = (List) H5AppDownloadManagerV2.this.c.get(this.d);
                    if (callbackList != null) {
                        for (H5DownloadCallback onProgress : callbackList) {
                            onProgress.onProgress(this.c, percent);
                        }
                    }
                    this.b = percent;
                }
            }
        }

        public void onCancelled(Request request) {
            File file = new File(this.e);
            if (file.exists() && this.e != null && this.e.contains(H5DownloadRequest.nebulaDownload)) {
                H5FileUtil.delete(file);
            }
            List<H5DownloadCallback> callbackList = (List) H5AppDownloadManagerV2.this.c.get(this.d);
            if (callbackList != null) {
                for (H5DownloadCallback onCancel : callbackList) {
                    onCancel.onCancel(this.c);
                }
            }
            a();
        }

        public void onPostExecute(Request request, Response response) {
            List<H5DownloadCallback> callbackList = (List) H5AppDownloadManagerV2.this.c.get(this.d);
            if (callbackList != null) {
                for (H5DownloadCallback onFinish : callbackList) {
                    onFinish.onFinish(this.c, this.d);
                }
            }
            a();
        }

        public void onFailed(Request request, int errorCode, String errorMsg) {
            List<H5DownloadCallback> callbackList = (List) H5AppDownloadManagerV2.this.c.get(this.d);
            if (callbackList != null) {
                for (H5DownloadCallback onFailed : callbackList) {
                    onFailed.onFailed(this.c, errorCode, errorMsg);
                }
            }
            a();
        }

        private void a() {
            H5AppDownloadManagerV2.this.b.remove(this.d);
            H5AppDownloadManagerV2.this.d.a(this.d);
            H5AppDownloadManagerV2.this.c.remove(this.d);
            H5AppDownloadManagerV2.this.a();
        }
    }

    H5AppDownloadManagerV2() {
        HandlerThread handlerThread = new HandlerThread("H5AppDownloadManagerV2");
        handlerThread.start();
        this.a = new Handler(handlerThread.getLooper());
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null) {
            a(configProvider.getConfigWithNotifyChange("h5_pkgpredownload", new OnConfigChangeListener() {
                public void onChange(String value) {
                    H5AppDownloadManagerV2.this.a(value);
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public void a(String cfgStr) {
        H5Log.d("H5AppDownloadManagerV2", "applyPreDownloadControl: " + cfgStr);
        JSONObject jo = H5Utils.parseObject(cfgStr);
        if (jo != null) {
            this.e = "yes".equalsIgnoreCase(H5Utils.getString(jo, (String) FunctionSupportConfiger.SWITCH_TAG, (String) "yes"));
            if (!this.e) {
                JSONArray array = H5Utils.getJSONArray(jo, H5PermissionManager.whitelist, null);
                if (array != null) {
                    synchronized (this.f) {
                        this.f.clear();
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            try {
                                this.f.add(array.getString(i));
                            } catch (Throwable t) {
                                H5Log.e((String) "H5AppDownloadManagerV2", t);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean b(String appId) {
        return this.e || this.f.contains(appId);
    }

    public void addDownload(final H5DownloadRequest request, final H5DownloadCallback callback) {
        if (request != null && request.getDownloadUrl() != null) {
            String downloadUrl = request.getDownloadUrl();
            H5Log.d("H5AppDownloadManagerV2", "addDownload-appId:" + request.getAppId() + " scene:" + request.getScene() + " description: " + request.getDescription() + " downloadUrl:" + downloadUrl + " version:" + request.getVersion());
            boolean fromPreDownload = request.isFromPreDownload();
            if (!fromPreDownload || b(request.getAppId())) {
                final boolean isUrgentResource = !fromPreDownload;
                boolean needTriggerDownload = false;
                boolean needCancelDownloading = "YES".equalsIgnoreCase(H5WalletWrapper.getConfigWithProcessCache("h5_needCancelDownloading"));
                if (!isUrgentResource || !needCancelDownloading) {
                    Future future = this.b.get(downloadUrl);
                    if (future != null && !future.isCancelled() && !future.isDone()) {
                        a(downloadUrl, callback);
                    } else if (isUrgentResource || this.b.size() <= 20) {
                        needTriggerDownload = true;
                    } else {
                        this.d.a(request, callback);
                    }
                } else {
                    needTriggerDownload = true;
                    cancel(downloadUrl);
                }
                if (needTriggerDownload) {
                    this.a.post(new Runnable() {
                        public void run() {
                            H5AppDownloadManagerV2.this.a(request, callback, isUrgentResource);
                        }
                    });
                    return;
                }
                return;
            }
            H5Log.w("H5AppDownloadManagerV2", "not enable predownload!");
        }
    }

    private void a(String url, H5DownloadCallback callback) {
        if (callback != null) {
            List callbackList = this.c.get(url);
            if (callbackList == null) {
                H5Log.d("H5AppDownloadManagerV2", "add callback for new task: " + url);
                List callbackList2 = new CopyOnWriteArrayList();
                callbackList2.add(callback);
                this.c.put(url, callbackList2);
                return;
            }
            H5Log.d("H5AppDownloadManagerV2", "add callback for downloading task: " + url + Token.SEPARATOR + callbackList.size());
            callbackList.add(callback);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        final Pair pair = this.d.a();
        if (pair != null) {
            this.a.post(new Runnable() {
                public void run() {
                    H5AppDownloadManagerV2.this.a((H5DownloadRequest) pair.first, (H5DownloadCallback) pair.second, false);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public void a(H5DownloadRequest request, H5DownloadCallback callback, boolean isUrgentResource) {
        String fileName;
        DownloadService downloadService = (DownloadService) H5Utils.findServiceByInterface(DownloadService.class.getName());
        if (downloadService == null) {
            H5Log.e((String) "H5AppDownloadManagerV2", (String) "downloadService == null");
            return;
        }
        String downloadUrl = request.getDownloadUrl();
        String fileName2 = request.getFileName();
        if (fileName2 == null || "".equals(fileName2.trim())) {
            fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        } else {
            fileName = "/" + fileName2;
        }
        String downloadDir = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext());
        if (TextUtils.isEmpty(downloadDir) || "/".equals(downloadDir)) {
            callback.onFailed(request, 0, "save path can not create");
            return;
        }
        String filePath = downloadDir + fileName;
        a(downloadUrl, callback);
        TransportCallback wrappedCallback = new TransportCallbackWrapper(this, request, filePath, 0);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUrl);
        downloadRequest.setPath(filePath);
        downloadRequest.setTransportCallback(wrappedCallback);
        downloadRequest.addTags("bizId", "nebula_app");
        H5Log.d("H5AppDownloadManagerV2", "addDownloadInner-appId: " + request.getAppId() + " url: " + request.getDownloadUrl() + " isUrgentResource: " + isUrgentResource + " downloadDir: " + downloadDir);
        if (isUrgentResource) {
            downloadRequest.setUrgentResource(true);
        }
        Future task = downloadService.addDownload(downloadRequest);
        if (task != null) {
            this.b.put(downloadUrl, task);
        }
    }

    public void cancel(String downloadUrl) {
        if (downloadUrl != null) {
            Future future = this.b.get(downloadUrl);
            if (future != null && !future.isDone()) {
                try {
                    future.cancel(false);
                } catch (Throwable throwable) {
                    H5Log.e((String) "H5AppDownloadManagerV2", throwable);
                }
                this.c.remove(downloadUrl);
                this.b.remove(downloadUrl);
                this.d.a(downloadUrl);
            }
        }
    }

    public boolean isDownloading(String downloadUrl) {
        if (downloadUrl != null) {
            Future future = this.b.get(downloadUrl);
            if (future != null && !future.isDone()) {
                return true;
            }
        }
        return false;
    }
}
