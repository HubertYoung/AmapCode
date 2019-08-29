package com.alipay.mobile.tinyappcommon.h5plugin;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class H5FilePlugin extends H5SimplePlugin {
    private static final String DOWNLOAD_FILE = "downloadFile";
    private static final String GET_FILE_INFO = "getFileInfo";
    private static final String GET_SAVED_FILE_INFO = "getSavedFileInfo";
    private static final String GET_SAVED_FILE_LIST = "getSavedFileList";
    private static final int MAX_SAVE_FILE_SIZE = 10485760;
    private static final String OPERATE_DOWNLOAD_TASK = "operateDownloadTask";
    private static final String REMOVE_SAVED_FILE = "removeSavedFile";
    private static final String SAVE_FILE = "saveFile";
    private static final String TAG = "H5FilePlugin";
    private String appId;
    private FileCache cache;
    /* access modifiers changed from: private */
    public Map<String, DownloadFileHandle> downloadFileHandles = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public H5Page h5Page;
    private boolean useDownload = false;

    private class DownloadFileHandle {
        public volatile boolean abort;
        public String downloadTaskId;
        public volatile Future<Response> future;
        public float progress;
        public String tempFilePath;
        public long totalBytesExpectedToWrite;
        public long totalBytesWritten;

        private DownloadFileHandle() {
            this.abort = false;
        }

        /* access modifiers changed from: 0000 */
        public void interrupt() {
            this.abort = true;
            if (this.future != null) {
                this.future.cancel(true);
            }
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (CommonEvents.H5_PAGE_CLOSE.equals(event.getAction())) {
            H5Log.d(TAG, "--H5_PAGE_CLOSE");
            if (this.useDownload) {
                H5Log.d(TAG, "--clear tempDir:" + H5Utils.getContext());
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        if (H5FilePlugin.this.getFileCache() != null) {
                            H5FilePlugin.this.getFileCache().clearTempPath(H5Utils.getContext());
                        }
                    }
                });
            }
        }
        return false;
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SAVE_FILE);
        filter.addAction(DOWNLOAD_FILE);
        filter.addAction(GET_SAVED_FILE_LIST);
        filter.addAction(GET_SAVED_FILE_INFO);
        filter.addAction(REMOVE_SAVED_FILE);
        filter.addAction(CommonEvents.H5_PAGE_CLOSE);
        filter.addAction(GET_FILE_INFO);
        filter.addAction(OPERATE_DOWNLOAD_TASK);
    }

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
        if (coreNode instanceof H5Page) {
            this.h5Page = (H5Page) coreNode;
            this.appId = H5Utils.getString(this.h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(this.appId)) {
                this.appId = "20000067";
            }
        }
    }

    public void onRelease() {
        super.onRelease();
        this.h5Page = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (SAVE_FILE.equals(event.getAction())) {
            saveFile(event, context);
            return true;
        } else if (DOWNLOAD_FILE.equals(event.getAction())) {
            this.useDownload = true;
            downloadFile(event, context);
            return true;
        } else if (GET_SAVED_FILE_LIST.equals(event.getAction())) {
            getSavedFileList(context);
            return true;
        } else if (GET_SAVED_FILE_INFO.equals(event.getAction())) {
            getSavedFileInfo(event, context);
            return true;
        } else if (REMOVE_SAVED_FILE.equals(event.getAction())) {
            removeSavedFile(event, context);
            return true;
        } else if (GET_FILE_INFO.equals(event.getAction())) {
            getFileInfo(event, context);
            return true;
        } else if (!OPERATE_DOWNLOAD_TASK.equals(event.getAction())) {
            return false;
        } else {
            operateDownloadTask(event, context);
            return true;
        }
    }

    private void downloadFile(final H5Event event, final H5BridgeContext h5BridgeContext) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                JSONObject jsonObject = event.getParam();
                String url = H5Utils.getString(jsonObject, (String) "url");
                String downloadTaskId = "";
                try {
                    downloadTaskId = String.valueOf(H5Utils.getInt(jsonObject, (String) "downloadTaskId"));
                } catch (Throwable e) {
                    H5Log.e((String) H5FilePlugin.TAG, e);
                }
                H5Log.d(H5FilePlugin.TAG, "downloadFile, url is " + url);
                H5Log.d(H5FilePlugin.TAG, "downloadFile, downloadTaskId is " + downloadTaskId);
                if (TextUtils.isEmpty(url)) {
                    h5BridgeContext.sendError(event, Error.INVALID_PARAM);
                } else if (!url.startsWith("http")) {
                    H5Log.d(H5FilePlugin.TAG, "url type is base64");
                    JSONObject base64Object = new JSONObject();
                    String extension = H5FilePlugin.this.getExtension(H5ImageUtil.getMimeType(url));
                    Bitmap bitmap = H5ImageUtil.base64ToBitmap(url);
                    if (bitmap == null) {
                        h5BridgeContext.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                    String filePath = H5FilePlugin.this.getFileCache().getTempPath(H5Utils.getContext(), url, extension);
                    try {
                        if (H5FileUtil.create(filePath, true)) {
                            H5ImageUtil.writeImage(bitmap, CompressFormat.PNG, filePath);
                            long size = 0;
                            if (bitmap != null) {
                                size = (long) (bitmap.getRowBytes() * bitmap.getHeight());
                            }
                            JSONObject params = new JSONObject();
                            JSONObject downloadTaskStateChange = new JSONObject();
                            params.put((String) "data", (Object) downloadTaskStateChange);
                            downloadTaskStateChange.put((String) "downloadTaskId", (Object) downloadTaskId);
                            downloadTaskStateChange.put((String) "progress", (Object) Integer.valueOf(100));
                            downloadTaskStateChange.put((String) "totalBytesWritten", (Object) Long.valueOf(size));
                            downloadTaskStateChange.put((String) "totalBytesExpectedToWrite", (Object) Long.valueOf(size));
                            if (!(H5FilePlugin.this.h5Page == null || H5FilePlugin.this.h5Page.getBridge() == null)) {
                                H5FilePlugin.this.h5Page.getBridge().sendToWeb("downloadTaskStateChange", params, null);
                            }
                            String apFilePath = H5ResourceHandlerUtil.localIdToUrl(TinyappUtils.encodeToLocalId(filePath), "image");
                            base64Object.put((String) "tempFilePath", (Object) filePath);
                            base64Object.put((String) Constant.AL_MEDIA_FILE, (Object) apFilePath);
                        }
                    } catch (Throwable e2) {
                        H5Log.e((String) H5FilePlugin.TAG, e2);
                    }
                    if (base64Object.size() == 0) {
                        base64Object.put((String) "error", (Object) Integer.valueOf(12));
                        base64Object.put((String) "errorMessage", (Object) "保存失败");
                    }
                    h5BridgeContext.sendBridgeResult(base64Object);
                } else {
                    H5FilePlugin.this.download(downloadTaskId, url, h5BridgeContext);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public String getExtension(String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return "png";
        }
        if (mimeType.startsWith("image/gif")) {
            return "gif";
        }
        if (mimeType.startsWith("image/png")) {
            return "png";
        }
        if (mimeType.startsWith("image/jpg")) {
            return "jpg";
        }
        if (mimeType.startsWith("image/x-icon")) {
            return "x-icon";
        }
        return "png";
    }

    /* access modifiers changed from: private */
    public void setError(H5BridgeContext context, String errorMessage) {
        if (context != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            if (errorMessage != null) {
                jsonObject.put((String) "errorMessage", (Object) errorMessage);
            }
            context.sendBridgeResult(jsonObject);
        }
    }

    /* access modifiers changed from: private */
    public void download(final String downloadTaskId, final String url, final H5BridgeContext context) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:113:0x0459, code lost:
                r11 = e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:150:0x0539, code lost:
                r11 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x01dd, code lost:
                r11 = e;
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Removed duplicated region for block: B:113:0x0459 A[ExcHandler: CancellationException (e java.util.concurrent.CancellationException), Splitter:B:1:0x0004] */
            /* JADX WARNING: Removed duplicated region for block: B:118:0x0479 A[Catch:{ all -> 0x05b6 }] */
            /* JADX WARNING: Removed duplicated region for block: B:120:0x04a5 A[SYNTHETIC, Splitter:B:120:0x04a5] */
            /* JADX WARNING: Removed duplicated region for block: B:123:0x04aa A[SYNTHETIC, Splitter:B:123:0x04aa] */
            /* JADX WARNING: Removed duplicated region for block: B:127:0x04c5  */
            /* JADX WARNING: Removed duplicated region for block: B:155:0x0556 A[SYNTHETIC, Splitter:B:155:0x0556] */
            /* JADX WARNING: Removed duplicated region for block: B:158:0x055b A[SYNTHETIC, Splitter:B:158:0x055b] */
            /* JADX WARNING: Removed duplicated region for block: B:162:0x0576  */
            /* JADX WARNING: Removed duplicated region for block: B:177:0x05b9 A[SYNTHETIC, Splitter:B:177:0x05b9] */
            /* JADX WARNING: Removed duplicated region for block: B:180:0x05be A[SYNTHETIC, Splitter:B:180:0x05be] */
            /* JADX WARNING: Removed duplicated region for block: B:184:0x05d9  */
            /* JADX WARNING: Removed duplicated region for block: B:224:? A[RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:229:? A[RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:42:0x01dd A[ExcHandler: InterruptedException (e java.lang.InterruptedException), Splitter:B:1:0x0004] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r41 = this;
                    r27 = 0
                    r22 = 0
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = new com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    r36 = 0
                    r0 = r17
                    r1 = r35
                    r2 = r36
                    r0.<init>()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    boolean r35 = android.text.TextUtils.isEmpty(r35)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 != 0) goto L_0x0048
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    r0 = r35
                    r1 = r17
                    r1.downloadTaskId = r0     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r36 = r0
                    r0 = r35
                    r1 = r36
                    r2 = r17
                    r0.put(r1, r2)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x0048:
                    java.lang.String r34 = ""
                    java.lang.String r12 = ""
                    java.lang.String r26 = "GET"
                    r6 = 0
                    com.alipay.mobile.common.transport.h5.H5NetworkManager r16 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    android.content.Context r35 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r16
                    r1 = r35
                    r0.<init>(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    com.alipay.mobile.common.transport.h5.H5HttpUrlRequest r15 = new com.alipay.mobile.common.transport.h5.H5HttpUrlRequest     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    java.lang.String r0 = r4     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    r0 = r35
                    r15.<init>(r0)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r26
                    r15.setRequestMethod(r0)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = 0
                    r0 = r35
                    r15.setReqData(r0)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r36 = 60000(0xea60, double:2.9644E-319)
                    r0 = r36
                    r15.setTimeout(r0)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = 2
                    r0 = r35
                    r15.linkType = r0     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x01cd
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    android.os.Bundle r35 = r35.getParams()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x009e:
                    r0 = r41
                    java.lang.String r0 = r4     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r36 = r0
                    java.lang.String r9 = com.alipay.mobile.nebula.util.H5CookieUtil.getCookie(r35, r36)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    boolean r35 = android.text.TextUtils.isEmpty(r9)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 != 0) goto L_0x00d1
                    java.lang.String r35 = "Cookie"
                    r0 = r35
                    r15.addHeader(r0, r9)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r35 = "H5FilePlugin"
                    java.lang.StringBuilder r36 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r37 = "add cookie:"
                    r36.<init>(r37)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r36
                    java.lang.StringBuilder r36 = r0.append(r9)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r37 = " , for h5HttpUrlRequest"
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r36 = r36.toString()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    com.alipay.mobile.nebula.util.H5Log.d(r35, r36)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x00d1:
                    r0 = r16
                    java.util.concurrent.Future r14 = r0.enqueue(r15)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r17
                    r0.future = r14     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.Object r21 = r14.get()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r21 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r21     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r21 == 0) goto L_0x0447
                    com.alipay.mobile.common.transport.http.HttpUrlHeader r35 = r21.getHeader()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x0447
                    com.alipay.mobile.common.transport.http.HttpUrlHeader r35 = r21.getHeader()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    org.apache.http.Header[] r37 = r35.getAllHeaders()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r37
                    int r0 = r0.length     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r38 = r0
                    r35 = 0
                    r36 = r35
                L_0x00fa:
                    r0 = r36
                    r1 = r38
                    if (r0 >= r1) goto L_0x026c
                    r18 = r37[r36]     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r19 = r18.getName()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r19 == 0) goto L_0x01c7
                    java.lang.String r20 = r18.getValue()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r35 = "H5FilePlugin"
                    java.lang.StringBuilder r39 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r40 = "name:"
                    r39.<init>(r40)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r39
                    r1 = r19
                    java.lang.StringBuilder r39 = r0.append(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r40 = " - value:"
                    java.lang.StringBuilder r39 = r39.append(r40)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r39
                    r1 = r20
                    java.lang.StringBuilder r39 = r0.append(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r39 = r39.toString()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r35
                    r1 = r39
                    com.alipay.mobile.nebula.util.H5Log.d(r0, r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r35 = "Content-Type"
                    r0 = r19
                    r1 = r35
                    boolean r35 = r0.equalsIgnoreCase(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x0152
                    r8 = r20
                    boolean r35 = android.text.TextUtils.isEmpty(r20)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 != 0) goto L_0x0152
                    java.lang.String r34 = com.alipay.mobile.tinyappcommon.TinyappUtils.getTypeFromMimeType(r8)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r12 = com.alipay.mobile.nebula.util.H5FileUtil.getExtensionFromMimeType(r8)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x0152:
                    java.lang.String r35 = "set-cookie"
                    r0 = r19
                    r1 = r35
                    boolean r35 = r0.equalsIgnoreCase(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x01b3
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x01d1
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    android.os.Bundle r35 = r35.getParams()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x0178:
                    r0 = r41
                    java.lang.String r0 = r4     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r39 = r0
                    r0 = r35
                    r1 = r39
                    r2 = r20
                    com.alipay.mobile.nebula.util.H5CookieUtil.setCookie(r0, r1, r2)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r35 = "H5FilePlugin"
                    java.lang.StringBuilder r39 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r40 = "insert cookie:"
                    r39.<init>(r40)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r39
                    r1 = r20
                    java.lang.StringBuilder r39 = r0.append(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r40 = " , for "
                    java.lang.StringBuilder r39 = r39.append(r40)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    java.lang.String r0 = r4     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r40 = r0
                    java.lang.StringBuilder r39 = r39.append(r40)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.lang.String r39 = r39.toString()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r35
                    r1 = r39
                    com.alipay.mobile.nebula.util.H5Log.d(r0, r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                L_0x01b3:
                    java.lang.String r35 = "Content-Length"
                    r0 = r19
                    r1 = r35
                    boolean r35 = r0.equalsIgnoreCase(r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    if (r35 == 0) goto L_0x01c7
                    java.lang.String r35 = r18.getValue()     // Catch:{ Throwable -> 0x01d4, InterruptedException -> 0x01dd, CancellationException -> 0x0459 }
                    long r6 = java.lang.Long.parseLong(r35)     // Catch:{ Throwable -> 0x01d4, InterruptedException -> 0x01dd, CancellationException -> 0x0459 }
                L_0x01c7:
                    int r35 = r36 + 1
                    r36 = r35
                    goto L_0x00fa
                L_0x01cd:
                    r35 = 0
                    goto L_0x009e
                L_0x01d1:
                    r35 = 0
                    goto L_0x0178
                L_0x01d4:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    goto L_0x01c7
                L_0x01dd:
                    r11 = move-exception
                L_0x01de:
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ all -> 0x05b6 }
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ all -> 0x05b6 }
                    r36 = r0
                    java.lang.Object r17 = r35.get(r36)     // Catch:{ all -> 0x05b6 }
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17     // Catch:{ all -> 0x05b6 }
                    if (r17 == 0) goto L_0x0227
                    java.lang.String r35 = "H5FilePlugin"
                    java.lang.StringBuilder r36 = new java.lang.StringBuilder     // Catch:{ all -> 0x05b6 }
                    java.lang.String r37 = "downloadTaskId="
                    r36.<init>(r37)     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ all -> 0x05b6 }
                    r37 = r0
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    java.lang.String r37 = " is abort"
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ all -> 0x05b6 }
                    r37 = r0
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    java.lang.String r36 = r36.toString()     // Catch:{ all -> 0x05b6 }
                    com.alipay.mobile.nebula.util.H5Log.e(r35, r36)     // Catch:{ all -> 0x05b6 }
                L_0x0227:
                    if (r22 == 0) goto L_0x022c
                    r22.close()     // Catch:{ Throwable -> 0x0507 }
                L_0x022c:
                    if (r27 == 0) goto L_0x0231
                    r27.close()     // Catch:{ Throwable -> 0x0511 }
                L_0x0231:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles
                    r0 = r41
                    java.lang.String r0 = r3
                    r36 = r0
                    java.lang.Object r17 = r35.remove(r36)
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17
                    if (r17 == 0) goto L_0x026b
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ Throwable -> 0x051b }
                    r35 = r0
                    if (r35 == 0) goto L_0x026b
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x051b }
                    r35 = r0
                    boolean r35 = android.text.TextUtils.isEmpty(r35)     // Catch:{ Throwable -> 0x051b }
                    if (r35 != 0) goto L_0x026b
                    java.io.File r35 = new java.io.File     // Catch:{ Throwable -> 0x051b }
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x051b }
                    r36 = r0
                    r35.<init>(r36)     // Catch:{ Throwable -> 0x051b }
                    r35.delete()     // Catch:{ Throwable -> 0x051b }
                L_0x026b:
                    return
                L_0x026c:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    com.alipay.mobile.nebula.filecache.FileCache r35 = r35.getFileCache()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    android.content.Context r36 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r41
                    java.lang.String r0 = r4     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r37 = r0
                    r0 = r35
                    r1 = r36
                    r2 = r37
                    java.lang.String r31 = r0.getTempPath(r1, r2, r12)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = 1
                    r0 = r31
                    r1 = r35
                    com.alipay.mobile.nebula.util.H5FileUtil.create(r0, r1)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r31
                    r1 = r17
                    r1.tempFilePath = r0     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.io.File r13 = new java.io.File     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r31
                    r13.<init>(r0)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.io.FileOutputStream r28 = new java.io.FileOutputStream     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r0 = r28
                    r0.<init>(r13)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    java.io.InputStream r22 = r21.getInputStream()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = 0
                    int r35 = (r6 > r36 ? 1 : (r6 == r36 ? 0 : -1))
                    if (r35 > 0) goto L_0x02b8
                    int r35 = r22.available()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    long r6 = (long) r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                L_0x02b8:
                    r35 = 1024(0x400, float:1.435E-42)
                    r0 = r35
                    byte[] r5 = new byte[r0]     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r30 = 0
                    r32 = 0
                    com.alibaba.fastjson.JSONObject r10 = new com.alibaba.fastjson.JSONObject     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r10.<init>()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    com.alibaba.fastjson.JSONObject r29 = new com.alibaba.fastjson.JSONObject     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r29.<init>()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r25 = 0
                L_0x02ce:
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    if (r35 != 0) goto L_0x03b8
                    r0 = r22
                    int r23 = r0.read(r5)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = -1
                    r0 = r23
                    r1 = r35
                    if (r0 == r1) goto L_0x03b8
                    r35 = 0
                    r0 = r28
                    r1 = r35
                    r2 = r23
                    r0.write(r5, r1, r2)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r23
                    long r0 = (long) r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = r0
                    long r32 = r32 + r36
                    r0 = r32
                    r2 = r17
                    r2.totalBytesWritten = r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r17
                    r0.totalBytesExpectedToWrite = r6     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = 0
                    int r35 = (r6 > r36 ? 1 : (r6 == r36 ? 0 : -1))
                    if (r35 <= 0) goto L_0x0333
                    r0 = r32
                    float r0 = (float) r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    float r0 = (float) r6     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = r0
                    float r35 = r35 / r36
                    r36 = 1120403456(0x42c80000, float:100.0)
                    float r35 = r35 * r36
                    int r35 = java.lang.Math.round(r35)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    float r0 = (float) r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r30 = r0
                    int r35 = (r30 > r25 ? 1 : (r30 == r25 ? 0 : -1))
                    if (r35 > 0) goto L_0x0331
                    r0 = r17
                    long r0 = r0.totalBytesWritten     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = r0
                    r0 = r17
                    long r0 = r0.totalBytesExpectedToWrite     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r38 = r0
                    int r35 = (r36 > r38 ? 1 : (r36 == r38 ? 0 : -1))
                    if (r35 != 0) goto L_0x02ce
                L_0x0331:
                    r25 = r30
                L_0x0333:
                    r0 = r30
                    r1 = r17
                    r1.progress = r0     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "downloadTaskId"
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r36 = r0
                    r0 = r35
                    r1 = r36
                    r10.put(r0, r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "progress"
                    java.lang.Float r36 = java.lang.Float.valueOf(r30)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    r1 = r36
                    r10.put(r0, r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "totalBytesWritten"
                    java.lang.Long r36 = java.lang.Long.valueOf(r32)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    r1 = r36
                    r10.put(r0, r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "totalBytesExpectedToWrite"
                    java.lang.Long r36 = java.lang.Long.valueOf(r6)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    r1 = r36
                    r10.put(r0, r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "data"
                    r0 = r29
                    r1 = r35
                    r0.put(r1, r10)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    if (r35 == 0) goto L_0x02ce
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    com.alipay.mobile.h5container.api.H5Bridge r35 = r35.getBridge()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    if (r35 == 0) goto L_0x02ce
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    com.alipay.mobile.h5container.api.H5Page r35 = r35.h5Page     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    com.alipay.mobile.h5container.api.H5Bridge r35 = r35.getBridge()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r36 = "downloadTaskStateChange"
                    r37 = 0
                    r0 = r35
                    r1 = r36
                    r2 = r29
                    r3 = r37
                    r0.sendToWeb(r1, r2, r3)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    goto L_0x02ce
                L_0x03b3:
                    r11 = move-exception
                    r27 = r28
                    goto L_0x01de
                L_0x03b8:
                    r28.flush()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    if (r35 != 0) goto L_0x03f5
                    java.lang.String r35 = com.alipay.mobile.tinyappcommon.TinyappUtils.encodeToLocalId(r31)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r35
                    r1 = r34
                    java.lang.String r4 = com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil.localIdToUrl(r0, r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    com.alibaba.fastjson.JSONObject r24 = new com.alibaba.fastjson.JSONObject     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r24.<init>()     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "tempFilePath"
                    r0 = r24
                    r1 = r35
                    r2 = r31
                    r0.put(r1, r2)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    java.lang.String r35 = "apFilePath"
                    r0 = r24
                    r1 = r35
                    r0.put(r1, r4)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r0 = r41
                    com.alipay.mobile.h5container.api.H5BridgeContext r0 = r5     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                    r35 = r0
                    r0 = r35
                    r1 = r24
                    r0.sendBridgeResult(r1)     // Catch:{ InterruptedException -> 0x03b3, CancellationException -> 0x0620, Throwable -> 0x061b, all -> 0x0617 }
                L_0x03f5:
                    r27 = r28
                L_0x03f7:
                    if (r22 == 0) goto L_0x03fc
                    r22.close()     // Catch:{ Throwable -> 0x04f3 }
                L_0x03fc:
                    if (r27 == 0) goto L_0x0401
                    r27.close()     // Catch:{ Throwable -> 0x04fd }
                L_0x0401:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles
                    r0 = r41
                    java.lang.String r0 = r3
                    r36 = r0
                    java.lang.Object r17 = r35.remove(r36)
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17
                    if (r17 == 0) goto L_0x026b
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ Throwable -> 0x043d }
                    r35 = r0
                    if (r35 == 0) goto L_0x026b
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x043d }
                    r35 = r0
                    boolean r35 = android.text.TextUtils.isEmpty(r35)     // Catch:{ Throwable -> 0x043d }
                    if (r35 != 0) goto L_0x026b
                    java.io.File r35 = new java.io.File     // Catch:{ Throwable -> 0x043d }
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x043d }
                    r36 = r0
                    r35.<init>(r36)     // Catch:{ Throwable -> 0x043d }
                    r35.delete()     // Catch:{ Throwable -> 0x043d }
                    goto L_0x026b
                L_0x043d:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x026b
                L_0x0447:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r35 = r0
                    r0 = r41
                    com.alipay.mobile.h5container.api.H5BridgeContext r0 = r5     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    r36 = r0
                    java.lang.String r37 = "the response is null"
                    r35.setError(r36, r37)     // Catch:{ InterruptedException -> 0x01dd, CancellationException -> 0x0459, Throwable -> 0x0539 }
                    goto L_0x03f7
                L_0x0459:
                    r11 = move-exception
                L_0x045a:
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ all -> 0x05b6 }
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ all -> 0x05b6 }
                    r36 = r0
                    java.lang.Object r17 = r35.get(r36)     // Catch:{ all -> 0x05b6 }
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17     // Catch:{ all -> 0x05b6 }
                    if (r17 == 0) goto L_0x04a3
                    java.lang.String r35 = "H5FilePlugin"
                    java.lang.StringBuilder r36 = new java.lang.StringBuilder     // Catch:{ all -> 0x05b6 }
                    java.lang.String r37 = "downloadTaskId="
                    r36.<init>(r37)     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    java.lang.String r0 = r3     // Catch:{ all -> 0x05b6 }
                    r37 = r0
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    java.lang.String r37 = " is abort"
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ all -> 0x05b6 }
                    r37 = r0
                    java.lang.StringBuilder r36 = r36.append(r37)     // Catch:{ all -> 0x05b6 }
                    java.lang.String r36 = r36.toString()     // Catch:{ all -> 0x05b6 }
                    com.alipay.mobile.nebula.util.H5Log.e(r35, r36)     // Catch:{ all -> 0x05b6 }
                L_0x04a3:
                    if (r22 == 0) goto L_0x04a8
                    r22.close()     // Catch:{ Throwable -> 0x0525 }
                L_0x04a8:
                    if (r27 == 0) goto L_0x04ad
                    r27.close()     // Catch:{ Throwable -> 0x052f }
                L_0x04ad:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles
                    r0 = r41
                    java.lang.String r0 = r3
                    r36 = r0
                    java.lang.Object r17 = r35.remove(r36)
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17
                    if (r17 == 0) goto L_0x026b
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ Throwable -> 0x04e9 }
                    r35 = r0
                    if (r35 == 0) goto L_0x026b
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x04e9 }
                    r35 = r0
                    boolean r35 = android.text.TextUtils.isEmpty(r35)     // Catch:{ Throwable -> 0x04e9 }
                    if (r35 != 0) goto L_0x026b
                    java.io.File r35 = new java.io.File     // Catch:{ Throwable -> 0x04e9 }
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x04e9 }
                    r36 = r0
                    r35.<init>(r36)     // Catch:{ Throwable -> 0x04e9 }
                    r35.delete()     // Catch:{ Throwable -> 0x04e9 }
                    goto L_0x026b
                L_0x04e9:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x026b
                L_0x04f3:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x03fc
                L_0x04fd:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x0401
                L_0x0507:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x022c
                L_0x0511:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x0231
                L_0x051b:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x026b
                L_0x0525:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x04a8
                L_0x052f:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x04ad
                L_0x0539:
                    r11 = move-exception
                L_0x053a:
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)     // Catch:{ all -> 0x05b6 }
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this     // Catch:{ all -> 0x05b6 }
                    r35 = r0
                    r0 = r41
                    com.alipay.mobile.h5container.api.H5BridgeContext r0 = r5     // Catch:{ all -> 0x05b6 }
                    r36 = r0
                    java.lang.String r37 = r11.toString()     // Catch:{ all -> 0x05b6 }
                    r35.setError(r36, r37)     // Catch:{ all -> 0x05b6 }
                    if (r22 == 0) goto L_0x0559
                    r22.close()     // Catch:{ Throwable -> 0x05a4 }
                L_0x0559:
                    if (r27 == 0) goto L_0x055e
                    r27.close()     // Catch:{ Throwable -> 0x05ad }
                L_0x055e:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this
                    r35 = r0
                    java.util.Map r35 = r35.downloadFileHandles
                    r0 = r41
                    java.lang.String r0 = r3
                    r36 = r0
                    java.lang.Object r17 = r35.remove(r36)
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17
                    if (r17 == 0) goto L_0x026b
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ Throwable -> 0x059a }
                    r35 = r0
                    if (r35 == 0) goto L_0x026b
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x059a }
                    r35 = r0
                    boolean r35 = android.text.TextUtils.isEmpty(r35)     // Catch:{ Throwable -> 0x059a }
                    if (r35 != 0) goto L_0x026b
                    java.io.File r35 = new java.io.File     // Catch:{ Throwable -> 0x059a }
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x059a }
                    r36 = r0
                    r35.<init>(r36)     // Catch:{ Throwable -> 0x059a }
                    r35.delete()     // Catch:{ Throwable -> 0x059a }
                    goto L_0x026b
                L_0x059a:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x026b
                L_0x05a4:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x0559
                L_0x05ad:
                    r11 = move-exception
                    java.lang.String r35 = "H5FilePlugin"
                    r0 = r35
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x055e
                L_0x05b6:
                    r35 = move-exception
                L_0x05b7:
                    if (r22 == 0) goto L_0x05bc
                    r22.close()     // Catch:{ Throwable -> 0x05fc }
                L_0x05bc:
                    if (r27 == 0) goto L_0x05c1
                    r27.close()     // Catch:{ Throwable -> 0x0605 }
                L_0x05c1:
                    r0 = r41
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.this
                    r36 = r0
                    java.util.Map r36 = r36.downloadFileHandles
                    r0 = r41
                    java.lang.String r0 = r3
                    r37 = r0
                    java.lang.Object r17 = r36.remove(r37)
                    com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin$DownloadFileHandle r17 = (com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.DownloadFileHandle) r17
                    if (r17 == 0) goto L_0x05fb
                    r0 = r17
                    boolean r0 = r0.abort     // Catch:{ Throwable -> 0x060e }
                    r36 = r0
                    if (r36 == 0) goto L_0x05fb
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x060e }
                    r36 = r0
                    boolean r36 = android.text.TextUtils.isEmpty(r36)     // Catch:{ Throwable -> 0x060e }
                    if (r36 != 0) goto L_0x05fb
                    java.io.File r36 = new java.io.File     // Catch:{ Throwable -> 0x060e }
                    r0 = r17
                    java.lang.String r0 = r0.tempFilePath     // Catch:{ Throwable -> 0x060e }
                    r37 = r0
                    r36.<init>(r37)     // Catch:{ Throwable -> 0x060e }
                    r36.delete()     // Catch:{ Throwable -> 0x060e }
                L_0x05fb:
                    throw r35
                L_0x05fc:
                    r11 = move-exception
                    java.lang.String r36 = "H5FilePlugin"
                    r0 = r36
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x05bc
                L_0x0605:
                    r11 = move-exception
                    java.lang.String r36 = "H5FilePlugin"
                    r0 = r36
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x05c1
                L_0x060e:
                    r11 = move-exception
                    java.lang.String r36 = "H5FilePlugin"
                    r0 = r36
                    com.alipay.mobile.nebula.util.H5Log.e(r0, r11)
                    goto L_0x05fb
                L_0x0617:
                    r35 = move-exception
                    r27 = r28
                    goto L_0x05b7
                L_0x061b:
                    r11 = move-exception
                    r27 = r28
                    goto L_0x053a
                L_0x0620:
                    r11 = move-exception
                    r27 = r28
                    goto L_0x045a
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin.AnonymousClass3.run():void");
            }
        });
    }

    /* access modifiers changed from: private */
    public FileCache getFileCache() {
        if (this.cache == null) {
            this.cache = new FileCache(H5Utils.getContext(), this.appId);
        }
        return this.cache;
    }

    private long getFolderSize(File fileDir) {
        File[] listFiles;
        long length;
        if (fileDir == null) {
            return 0;
        }
        if (!fileDir.isDirectory()) {
            return fileDir.length();
        }
        long folderSize = 0;
        try {
            for (File file : fileDir.listFiles()) {
                if (file.isDirectory()) {
                    length = getFolderSize(file);
                } else {
                    length = file.length();
                }
                folderSize += length;
            }
            return folderSize;
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getFolderSize...e = " + e);
            return 0;
        }
    }

    private long getFolderSize(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return 0;
        }
        return getFolderSize(new File(dir));
    }

    private boolean checkFolderSizeLimited(String appendFile) {
        if (TextUtils.isEmpty(appendFile)) {
            return false;
        }
        long folderSize = getFolderSize(getFileCache().getCacheDir(H5Utils.getContext()));
        long appendSize = new File(appendFile).length();
        H5Log.d(TAG, "checkFolderSizeLimited appendSize=" + appendSize + ",folderSize=" + folderSize);
        if (folderSize + appendSize <= HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD) {
            return false;
        }
        H5Log.d(TAG, "checkFolderSizeLimited file exceed limited size");
        return true;
    }

    private void saveFile(H5Event event, H5BridgeContext context) {
        JSONObject jsonObject = new JSONObject();
        String tempFilePath = getFilePath(event, "tempFilePath");
        if (TextUtils.isEmpty(tempFilePath)) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
        } else if (checkFolderSizeLimited(tempFilePath)) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(19));
            jsonObject.put((String) "errorMessage", (Object) "文件存储大小限制为 10M");
            context.sendBridgeResult(jsonObject);
        } else if (!hasRightForSave(tempFilePath)) {
            context.sendError(11, (String) "not have permission to save");
        } else {
            String savedFilePath = getFileCache().getCachePath(H5Utils.getContext(), tempFilePath);
            boolean result = H5FileUtil.copy(tempFilePath, savedFilePath, true);
            H5Log.d(TAG, "saveFile..result:" + result);
            if (result) {
                String apFilePath = getAPFilePath(savedFilePath, getFileSuffix(H5Utils.getString(event.getParam(), (String) Constant.AL_MEDIA_FILE)));
                H5Log.d(TAG, "apFilePath " + apFilePath + " savedFilePath " + savedFilePath);
                jsonObject.put((String) Constant.AL_MEDIA_FILE, (Object) apFilePath);
                context.sendBridgeResult(jsonObject);
                return;
            }
            jsonObject.put((String) "error", (Object) Integer.valueOf(13));
            jsonObject.put((String) "errorMessage", (Object) "保存失败");
            context.sendBridgeResult(jsonObject);
        }
    }

    private boolean hasRightForSave(String tempFilePath) {
        return H5FSManagePlugin.hasRightForSaveFile(tempFilePath);
    }

    private String getAPFilePath(String savedFilePath, String reservedMimeType) {
        String localId = TinyappUtils.encodeToLocalId(savedFilePath);
        String mimeType = H5FileUtil.getMimeType(savedFilePath);
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = reservedMimeType;
        }
        if (mimeType != null && mimeType.startsWith("image")) {
            return H5ResourceHandlerUtil.localIdToUrl(localId, "image");
        }
        if (mimeType != null && mimeType.startsWith("audio")) {
            return H5ResourceHandlerUtil.localIdToUrl(localId, "audio");
        }
        if (mimeType == null || !mimeType.startsWith("video")) {
            return H5ResourceHandlerUtil.localIdToUrl(localId, H5ResourceHandlerUtil.OTHER);
        }
        return H5ResourceHandlerUtil.localIdToUrl(localId, "video");
    }

    private String getFilePath(H5Event event, String filePath) {
        String tempFilePath = H5Utils.getString(event.getParam(), filePath);
        if (TextUtils.isEmpty(tempFilePath)) {
            tempFilePath = H5Utils.getString(event.getParam(), (String) Constant.AL_MEDIA_FILE);
            if (!TextUtils.isEmpty(tempFilePath)) {
                tempFilePath = TinyappUtils.getLocalPathFromId(tempFilePath);
            }
        }
        if (!TextUtils.isEmpty(tempFilePath) && tempFilePath.startsWith("file://")) {
            tempFilePath = tempFilePath.replaceAll("file://", "");
        }
        if (TextUtils.isEmpty(tempFilePath) || !H5FileUtil.exists(tempFilePath)) {
            return null;
        }
        return tempFilePath;
    }

    private void getSavedFileList(H5BridgeContext context) {
        JSONObject jsonObject = new JSONObject();
        String savedFileDir = getFileCache().getCacheDir(H5Utils.getContext());
        H5Log.d(TAG, "getSavedFileList..." + savedFileDir);
        File savedFile = new File(savedFileDir);
        JSONArray jsonArray = new JSONArray();
        if (savedFile.isDirectory()) {
            File[] files = savedFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    String localFilePath = file.getPath();
                    String filePath = getAPFilePath(localFilePath, getFileSuffix(localFilePath));
                    JSONObject singleJsonObject = new JSONObject();
                    long size = H5FileUtil.size(file);
                    long createTime = H5FileUtil.getCreateTime(localFilePath);
                    singleJsonObject.put((String) "size", (Object) Long.valueOf(size));
                    singleJsonObject.put((String) Constant.AL_MEDIA_FILE, (Object) filePath);
                    singleJsonObject.put((String) "createTime", (Object) Long.valueOf(createTime));
                    jsonArray.add(singleJsonObject);
                }
                jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                jsonObject.put((String) "fileList", (Object) jsonArray);
                context.sendBridgeResult(jsonObject);
                return;
            }
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
            return;
        }
        jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
        jsonObject.put((String) "fileList", (Object) jsonArray);
        context.sendBridgeResult(jsonObject);
    }

    private String getFileSuffix(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int index = filePath.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        String apFileSuffix = filePath.substring(index + 1);
        H5Log.d(TAG, "filePath suffix: " + apFileSuffix);
        return apFileSuffix;
    }

    private void getSavedFileInfo(H5Event event, H5BridgeContext context) {
        JSONObject jsonObject = new JSONObject();
        String filePath = getFilePath(event, "filePath");
        if (TextUtils.isEmpty(filePath)) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
        } else if (!filePath.contains(getFileCache().getSubPath())) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
        } else {
            long size = H5FileUtil.size(filePath);
            long createTime = H5FileUtil.getCreateTime(filePath);
            H5Log.d(TAG, "filePath:" + filePath + "size : " + size + ", createTime: " + createTime);
            if (size == 0 || createTime == 0) {
                jsonObject.put((String) "error", (Object) Integer.valueOf(12));
                context.sendBridgeResult(jsonObject);
                return;
            }
            jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
            jsonObject.put((String) "size", (Object) Long.valueOf(size));
            jsonObject.put((String) "createTime", (Object) Long.valueOf(createTime));
            context.sendBridgeResult(jsonObject);
        }
    }

    private void removeSavedFile(H5Event event, H5BridgeContext context) {
        JSONObject jsonObject = new JSONObject();
        String filePath = getFilePath(event, "filePath");
        if (TextUtils.isEmpty(filePath)) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
        } else if (!filePath.contains(getFileCache().getSubPath())) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
        } else {
            boolean delResult = H5FileUtil.delete(filePath);
            if (delResult) {
                H5Log.d(TAG, "filePath is " + filePath + ", result is " + delResult);
                jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                context.sendBridgeResult(jsonObject);
                return;
            }
            jsonObject.put((String) "error", (Object) Integer.valueOf(15));
            jsonObject.put((String) "errorMessage", (Object) "删除文件失败");
            context.sendBridgeResult(jsonObject);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isValidFilePath(String path) {
        return FileCache.containCachePath(path);
    }

    private void getFileInfo(H5Event event, H5BridgeContext context) {
        String fileDigest;
        String filePath = getFilePath(event, "filePath");
        if (TextUtils.isEmpty(filePath)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "文件不存在");
            context.sendBridgeResult(jsonObject);
            return;
        }
        String digestAlgorithm = H5Utils.getString(event.getParam(), (String) "digestAlgorithm", (String) "md5");
        if ("md5".equals(digestAlgorithm)) {
            fileDigest = H5FileUtil.getFileMD5(filePath);
        } else if ("sha1".equals(digestAlgorithm)) {
            fileDigest = H5FileUtil.getFileSHA1(filePath);
        } else {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put((String) "error", (Object) Integer.valueOf(16));
            jsonObject2.put((String) "errorMessage", (Object) "digestAlgorithm 参数只支持 md5 或 sha1");
            context.sendBridgeResult(jsonObject2);
            return;
        }
        JSONObject jsonObject3 = new JSONObject();
        if (TextUtils.isEmpty(fileDigest)) {
            jsonObject3.put((String) "error", (Object) Integer.valueOf(17));
            jsonObject3.put((String) "errorMessage", (Object) "计算文件摘要信息错误");
        } else {
            jsonObject3.put((String) "size", (Object) Long.valueOf(H5FileUtil.size(filePath)));
            jsonObject3.put((String) "digest", (Object) fileDigest);
        }
        context.sendBridgeResult(jsonObject3);
        H5Log.d(TAG, "getFileInfo...");
    }

    private void operateDownloadTask(H5Event event, H5BridgeContext context) {
        if (event != null) {
            JSONObject eventParams = event.getParam();
            String downloadTaskId = "";
            try {
                downloadTaskId = String.valueOf(H5Utils.getInt(eventParams, (String) "downloadTaskId"));
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
            String operationType = H5Utils.getString(eventParams, (String) TransportConstants.KEY_OPERATION_TYPE);
            if (!TextUtils.isEmpty(downloadTaskId)) {
                DownloadFileHandle handle = this.downloadFileHandles.get(downloadTaskId);
                if (handle != null && TextUtils.equals(operationType, "abort")) {
                    handle.interrupt();
                    JSONObject result = new JSONObject();
                    result.put((String) "success", (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(result);
                    return;
                }
            }
        }
        JSONObject result2 = new JSONObject();
        result2.put((String) "success", (Object) Boolean.valueOf(false));
        context.sendBridgeResult(result2);
    }
}
