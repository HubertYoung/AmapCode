package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUProgressDialog;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyapp.R;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.sina.weibo.sdk.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class H5SaveVideoPlugin extends H5SimplePlugin {
    private static final String ACTION_SAVE_VIDEO = "saveVideoToPhotosAlbum";
    private static final int ERROR_CODE_FILE_IO = 14;
    private static final int ERROR_CODE_FORBIDDEN = 4;
    private static final int ERROR_CODE_INVALID_FILE_TYPE = 15;
    private static final int ERROR_CODE_INVALID_PARAM = 2;
    private static final int ERROR_CODE_INVALID_SAVE_FOLDER = 13;
    private static final int ERROR_CODE_INVALID_SOURCE_DATA = 12;
    private static final int ERROR_CODE_NONE = 0;
    private static final int ERROR_CODE_NOT_FOUND = 1;
    private static final int ERROR_CODE_UNKNOWN_ERROR = 3;
    private static final String ERROR_MESSAGE_FILE_IO = "文件写入失败";
    private static final String ERROR_MESSAGE_FORBIDDEN = "forbidden!";
    private static final String ERROR_MESSAGE_INVALID_FILE_TYPE = "文件类型错误";
    private static final String ERROR_MESSAGE_INVALID_PARAM = "invalid parameter!";
    private static final String ERROR_MESSAGE_INVALID_SAVE_FOLDER = "创建目录失败";
    private static final String ERROR_MESSAGE_INVALID_SOURCE_DATA = "资源不存在";
    private static final String ERROR_MESSAGE_NONE = "none error!";
    private static final String ERROR_MESSAGE_NOT_FOUND = "not implemented!";
    private static final String ERROR_MESSAGE_UNKNOWN_ERROR = "unknown error!";
    private static final String EXT_DEFAULT = "mp4";
    private static final String FILE_SCHEME = "file://";
    private static final String PARAM_FILE_PATH = "filePath";
    private static final String PARAM_SRC = "src";
    private static final String SAVE_DIRECTORY = "/DCIM/Camera/";
    private static final String TAG = "H5SaveVideoPlugin";
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public H5Page h5Page;
    /* access modifiers changed from: private */
    public volatile boolean isCanceled;
    /* access modifiers changed from: private */
    public AUProgressDialog loadingDialog;
    /* access modifiers changed from: private */
    public AtomicBoolean loadingDialogShowing = new AtomicBoolean(false);

    private static class H5SaveVideoException extends Exception {
        private int errorCode;

        public H5SaveVideoException(int errorCode2) {
            this.errorCode = errorCode2;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }

    class LocalVideoSaveCommand implements Runnable {
        /* access modifiers changed from: private */
        public H5BridgeContext bridgeContext;
        /* access modifiers changed from: private */
        public int downloadCode;
        private String localPath;
        /* access modifiers changed from: private */
        public String toastPath;

        public LocalVideoSaveCommand(String localPath2, H5BridgeContext bridgeContext2) {
            this.localPath = localPath2;
            this.bridgeContext = bridgeContext2;
        }

        public void run() {
            String ext;
            try {
                this.downloadCode = 0;
                if (!TextUtils.isEmpty(this.localPath)) {
                    File src = new File(this.localPath);
                    int lastIndex = this.localPath.lastIndexOf(".");
                    if (lastIndex <= 0 || lastIndex >= this.localPath.length()) {
                        ext = H5SaveVideoPlugin.EXT_DEFAULT;
                    } else {
                        ext = this.localPath.substring(lastIndex + 1);
                    }
                    this.toastPath = Environment.getExternalStorageDirectory() + H5SaveVideoPlugin.SAVE_DIRECTORY;
                    String targetFilePath = this.toastPath + System.currentTimeMillis() + "." + ext;
                    File target = new File(targetFilePath);
                    if (target.exists()) {
                        H5Log.d(H5SaveVideoPlugin.TAG, "file is existed.");
                        this.downloadCode = 0;
                        H5SaveVideoPlugin.this.stopLoading();
                        String message = H5Utils.getContext().getString(R.string.h5_save_video_to, new Object[]{this.toastPath});
                        if (this.bridgeContext != null) {
                            this.bridgeContext.sendSuccess();
                        }
                        if (H5SaveVideoPlugin.this.activity != null) {
                            JSONObject obj = new JSONObject();
                            obj.put((String) "content", (Object) message);
                            H5SaveVideoPlugin.this.h5Page.sendEvent("toast", obj);
                            return;
                        }
                        return;
                    } else if (H5FileUtil.copyFile(src, target)) {
                        this.downloadCode = 0;
                        MediaScannerConnection.scanFile(H5Utils.getContext(), new String[]{targetFilePath}, new String[]{"video/*"}, null);
                    } else {
                        this.downloadCode = 14;
                    }
                } else {
                    this.downloadCode = 12;
                }
            } catch (Throwable e) {
                H5Log.e((String) H5SaveVideoPlugin.TAG, e);
                this.downloadCode = 3;
            }
            if (H5SaveVideoPlugin.this.isCanceled) {
                H5SaveVideoPlugin.this.sendBridgeResultError(this.bridgeContext, 3);
            } else {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        String message;
                        H5SaveVideoPlugin.this.stopLoading();
                        if (LocalVideoSaveCommand.this.downloadCode == 0) {
                            message = H5Utils.getContext().getString(R.string.h5_save_video_to, new Object[]{LocalVideoSaveCommand.this.toastPath});
                            if (LocalVideoSaveCommand.this.bridgeContext != null) {
                                LocalVideoSaveCommand.this.bridgeContext.sendSuccess();
                            }
                        } else {
                            message = H5Utils.getContext().getString(R.string.h5_save_video_failed);
                            if (LocalVideoSaveCommand.this.bridgeContext != null) {
                                H5SaveVideoPlugin.this.sendBridgeResultError(LocalVideoSaveCommand.this.bridgeContext, LocalVideoSaveCommand.this.downloadCode);
                            }
                        }
                        if (H5SaveVideoPlugin.this.activity != null) {
                            JSONObject obj = new JSONObject();
                            obj.put((String) "content", (Object) message);
                            H5SaveVideoPlugin.this.h5Page.sendEvent("toast", obj);
                        }
                    }
                });
            }
        }
    }

    class VideoSaveCommand implements Runnable {
        /* access modifiers changed from: private */
        public H5BridgeContext bridgeContext;
        private String filePath;
        /* access modifiers changed from: private */
        public String toastPath;
        private String url;

        public VideoSaveCommand(String filePath2, String url2, H5BridgeContext bridgeContext2) {
            this.filePath = filePath2;
            this.url = url2;
            this.bridgeContext = bridgeContext2;
        }

        public void run() {
            final int downloadCode = download();
            if (H5SaveVideoPlugin.this.isCanceled) {
                H5SaveVideoPlugin.this.sendBridgeResultError(this.bridgeContext, 3);
            } else {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        String message;
                        H5SaveVideoPlugin.this.stopLoading();
                        if (downloadCode == 0) {
                            message = H5Utils.getContext().getString(R.string.h5_save_video_to, new Object[]{VideoSaveCommand.this.toastPath});
                            if (VideoSaveCommand.this.bridgeContext != null) {
                                VideoSaveCommand.this.bridgeContext.sendSuccess();
                            }
                        } else {
                            message = H5Utils.getContext().getString(R.string.h5_save_video_failed);
                            if (VideoSaveCommand.this.bridgeContext != null) {
                                H5SaveVideoPlugin.this.sendBridgeResultError(VideoSaveCommand.this.bridgeContext, downloadCode);
                            }
                        }
                        if (H5SaveVideoPlugin.this.activity != null) {
                            JSONObject obj = new JSONObject();
                            obj.put((String) "content", (Object) message);
                            H5SaveVideoPlugin.this.h5Page.sendEvent("toast", obj);
                        }
                    }
                });
            }
        }

        private InputStream loadResponse(String url2) {
            UcService ucService = H5ServiceUtils.getUcService();
            WebResourceResponse response = null;
            if (ucService != null && H5Flag.ucReady) {
                response = ucService.getResponse(url2);
            }
            if (response == null || response.getData() == null) {
                if (!(H5SaveVideoPlugin.this.h5Page == null || H5SaveVideoPlugin.this.h5Page.getSession() == null)) {
                    H5ContentProvider h5ContentProvider = H5SaveVideoPlugin.this.h5Page.getSession().getWebProvider();
                    if (h5ContentProvider != null) {
                        WebResourceResponse webResourceResponse = h5ContentProvider.getContent(url2);
                        if (webResourceResponse != null) {
                            H5Log.d(H5SaveVideoPlugin.TAG, "get from H5pkg " + url2);
                            return webResourceResponse.getData();
                        }
                    }
                }
                H5Log.d(H5SaveVideoPlugin.TAG, "load response from net");
                HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                if (conn.getResponseCode() != 200) {
                    throw new H5SaveVideoException(12);
                }
                H5Log.d(H5SaveVideoPlugin.TAG, "load response length " + conn.getContentLength());
                String contentType = conn.getHeaderField("Content-Type");
                if (TextUtils.isEmpty(contentType) || contentType.startsWith(FileUtils.VIDEO_FILE_START)) {
                    return conn.getInputStream();
                }
                throw new H5SaveVideoException(15);
            }
            H5Log.d(H5SaveVideoPlugin.TAG, "load response from uc cache");
            return response.getData();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
            r6.flush();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0127, code lost:
            if (new java.io.File(r14.filePath).renameTo(new java.io.File(r8)) != false) goto L_0x0131;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0129, code lost:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7);
            r5 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
            r10 = copyDownloadedToAlbum(r8, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x0134, code lost:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7);
            r5 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
            return 14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
            return r10;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int download() {
            /*
                r14 = this;
                r7 = 0
                r5 = 0
                java.lang.String r4 = "/DCIM/Camera/"
                java.lang.String r3 = ""
                java.lang.String r10 = r14.url     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = "http"
                boolean r10 = r10.startsWith(r11)     // Catch:{ Exception -> 0x00fe }
                if (r10 == 0) goto L_0x0016
                java.lang.String r10 = r14.url     // Catch:{ Exception -> 0x00fe }
                java.lang.String r3 = android.webkit.MimeTypeMap.getFileExtensionFromUrl(r10)     // Catch:{ Exception -> 0x00fe }
            L_0x0016:
                boolean r10 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x00fe }
                if (r10 == 0) goto L_0x001e
                java.lang.String r3 = "mp4"
            L_0x001e:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fe }
                r10.<init>()     // Catch:{ Exception -> 0x00fe }
                java.io.File r11 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r4)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00fe }
                r14.toastPath = r10     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fe }
                r10.<init>()     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = r14.toastPath     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00fe }
                long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = "."
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r3)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r9 = r10.toString()     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fe }
                r10.<init>()     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = r14.filePath     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = ".ok"
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r8 = r10.toString()     // Catch:{ Exception -> 0x00fe }
                java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x00fe }
                r10.<init>(r8)     // Catch:{ Exception -> 0x00fe }
                boolean r10 = r10.exists()     // Catch:{ Exception -> 0x00fe }
                if (r10 == 0) goto L_0x007f
                int r10 = r14.copyDownloadedToAlbum(r8, r9)     // Catch:{ Exception -> 0x00fe }
                r11 = 0
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r11)
            L_0x007e:
                return r10
            L_0x007f:
                java.lang.String r10 = r14.url     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = "http"
                boolean r10 = r10.startsWith(r11)     // Catch:{ Exception -> 0x00fe }
                if (r10 == 0) goto L_0x008f
                java.lang.String r10 = r14.url     // Catch:{ Exception -> 0x00fe }
                java.io.InputStream r7 = r14.loadResponse(r10)     // Catch:{ Exception -> 0x00fe }
            L_0x008f:
                if (r7 != 0) goto L_0x0097
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 12
                goto L_0x007e
            L_0x0097:
                java.lang.String r10 = "H5SaveVideoPlugin"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fe }
                java.lang.String r12 = "filePath "
                r11.<init>(r12)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r12 = r14.filePath     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00fe }
                com.alipay.mobile.nebula.util.H5Log.d(r10, r11)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r10 = r14.filePath     // Catch:{ Exception -> 0x00fe }
                boolean r10 = com.alipay.mobile.nebula.util.H5FileUtil.create(r10)     // Catch:{ Exception -> 0x00fe }
                if (r10 != 0) goto L_0x00d1
                java.lang.String r10 = "H5SaveVideoPlugin"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fe }
                java.lang.String r12 = "failed to create file "
                r11.<init>(r12)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r12 = r14.filePath     // Catch:{ Exception -> 0x00fe }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00fe }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00fe }
                com.alipay.mobile.nebula.util.H5Log.w(r10, r11)     // Catch:{ Exception -> 0x00fe }
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 13
                goto L_0x007e
            L_0x00d1:
                java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ all -> 0x015b }
                java.lang.String r10 = r14.filePath     // Catch:{ all -> 0x015b }
                r6.<init>(r10)     // Catch:{ all -> 0x015b }
                r10 = 1024(0x400, float:1.435E-42)
                byte[] r0 = new byte[r10]     // Catch:{ all -> 0x00f8 }
            L_0x00dc:
                int r1 = r7.read(r0)     // Catch:{ all -> 0x00f8 }
                if (r1 <= 0) goto L_0x0111
                com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin r10 = com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin.this     // Catch:{ all -> 0x00f8 }
                boolean r10 = r10.isCanceled     // Catch:{ all -> 0x00f8 }
                if (r10 == 0) goto L_0x00f3
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 3
                r5 = r6
                goto L_0x007e
            L_0x00f3:
                r10 = 0
                r6.write(r0, r10, r1)     // Catch:{ all -> 0x00f8 }
                goto L_0x00dc
            L_0x00f8:
                r10 = move-exception
                r5 = r6
            L_0x00fa:
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r5)     // Catch:{ Exception -> 0x00fe }
                throw r10     // Catch:{ Exception -> 0x00fe }
            L_0x00fe:
                r2 = move-exception
            L_0x00ff:
                java.lang.String r10 = "H5SaveVideoPlugin"
                java.lang.String r11 = "save video exception"
                com.alipay.mobile.nebula.util.H5Log.e(r10, r11, r2)     // Catch:{ all -> 0x0150 }
                boolean r10 = r2 instanceof java.io.IOException     // Catch:{ all -> 0x0150 }
                if (r10 == 0) goto L_0x013b
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 14
                goto L_0x007e
            L_0x0111:
                r6.flush()     // Catch:{ all -> 0x00f8 }
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                java.lang.String r11 = r14.filePath     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                r10.<init>(r11)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                java.io.File r11 = new java.io.File     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                r11.<init>(r8)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                boolean r10 = r10.renameTo(r11)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                if (r10 != 0) goto L_0x0131
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 14
                r5 = r6
                goto L_0x007e
            L_0x0131:
                int r10 = r14.copyDownloadedToAlbum(r8, r9)     // Catch:{ Exception -> 0x0158, all -> 0x0155 }
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r5 = r6
                goto L_0x007e
            L_0x013b:
                boolean r10 = r2 instanceof com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin.H5SaveVideoException     // Catch:{ all -> 0x0150 }
                if (r10 == 0) goto L_0x014a
                com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin$H5SaveVideoException r2 = (com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin.H5SaveVideoException) r2     // Catch:{ all -> 0x0150 }
                int r10 = r2.getErrorCode()     // Catch:{ all -> 0x0150 }
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                goto L_0x007e
            L_0x014a:
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                r10 = 3
                goto L_0x007e
            L_0x0150:
                r10 = move-exception
            L_0x0151:
                com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
                throw r10
            L_0x0155:
                r10 = move-exception
                r5 = r6
                goto L_0x0151
            L_0x0158:
                r2 = move-exception
                r5 = r6
                goto L_0x00ff
            L_0x015b:
                r10 = move-exception
                goto L_0x00fa
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin.VideoSaveCommand.download():int");
        }

        private int copyDownloadedToAlbum(String downloadFile, String targetFile) {
            FileInputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                FileInputStream inputStream2 = new FileInputStream(downloadFile);
                try {
                    FileOutputStream outputStream2 = new FileOutputStream(targetFile);
                    try {
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int count = inputStream2.read(buffer);
                            if (count <= 0) {
                                outputStream2.flush();
                                MediaScannerConnection.scanFile(H5Utils.getContext(), new String[]{targetFile}, new String[]{"video/*"}, null);
                                H5IOUtils.closeQuietly(inputStream2);
                                H5IOUtils.closeQuietly(outputStream2);
                                return 0;
                            } else if (H5SaveVideoPlugin.this.isCanceled) {
                                H5IOUtils.closeQuietly(inputStream2);
                                H5IOUtils.closeQuietly(outputStream2);
                                return 3;
                            } else {
                                outputStream2.write(buffer, 0, count);
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        outputStream = outputStream2;
                        inputStream = inputStream2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    H5IOUtils.closeQuietly(inputStream);
                    H5IOUtils.closeQuietly(outputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                H5IOUtils.closeQuietly(inputStream);
                H5IOUtils.closeQuietly(outputStream);
                throw th;
            }
        }
    }

    public void onInitialize(H5CoreNode coreNode) {
        H5Log.d(TAG, "onInitialize");
        if (coreNode instanceof H5Page) {
            this.h5Page = (H5Page) coreNode;
        }
        if (this.h5Page != null) {
            Context context = this.h5Page.getContext().getContext();
            if (context instanceof Activity) {
                this.activity = (Activity) context;
            }
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_SAVE_VIDEO);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        H5Log.d(TAG, "handleEvent event is " + action);
        if (ACTION_SAVE_VIDEO.equals(action)) {
            if (this.loadingDialogShowing.get()) {
                sendBridgeResultError(context, 3);
            } else {
                JSONObject param = event.getParam();
                String url = param.getString(PARAM_SRC);
                if (TextUtils.isEmpty(url)) {
                    url = param.getString(PARAM_FILE_PATH);
                }
                if (TextUtils.isEmpty(url)) {
                    sendBridgeResultError(context, 2);
                } else if (url.startsWith("file://") || new File(url).exists()) {
                    H5Log.d(TAG, url + "### is local file ,notify invalid!");
                    sendBridgeResultError(context, 2);
                } else {
                    H5Page h5Page2 = event.getH5page();
                    if (h5Page2 != null) {
                        saveVideo(url, h5Page2, context);
                    }
                }
            }
        }
        return true;
    }

    public void onRelease() {
        H5Log.d(TAG, "onRelease");
        if (this.loadingDialogShowing.get()) {
            try {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        H5SaveVideoPlugin.this.dismissProgress();
                        H5SaveVideoPlugin.this.h5Page = null;
                        H5SaveVideoPlugin.this.activity = null;
                        H5SaveVideoPlugin.this.loadingDialogShowing.set(false);
                    }
                });
            } catch (Throwable e) {
                H5Log.e(TAG, "[onRelease] Exception: " + e.toString(), e);
            }
        } else {
            this.h5Page = null;
            this.activity = null;
        }
    }

    private void startLoading(final H5BridgeContext bridgeContext) {
        this.loadingDialogShowing.set(true);
        try {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5SaveVideoPlugin.this.showProgress(H5Utils.getContext().getString(R.string.h5_save_video_loading), bridgeContext);
                }
            });
        } catch (Throwable e) {
            H5Log.e(TAG, "[startLoading] Exception: " + e.toString(), e);
        }
    }

    /* access modifiers changed from: private */
    public void stopLoading() {
        this.loadingDialogShowing.set(false);
        try {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5SaveVideoPlugin.this.dismissProgress();
                }
            });
        } catch (Throwable e) {
            H5Log.e(TAG, "[stopLoading] Exception: " + e.toString(), e);
        }
    }

    /* access modifiers changed from: private */
    public void showProgress(String msg, final H5BridgeContext bridgeContext) {
        if (this.activity != null) {
            this.loadingDialog = new AUProgressDialog(this.activity);
            this.loadingDialog.setMessage(msg);
            this.loadingDialog.setProgressVisiable(true);
            this.loadingDialog.setCanceledOnTouchOutside(false);
            this.loadingDialog.setCancelable(true);
            this.loadingDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    H5Log.d(H5SaveVideoPlugin.TAG, "[ProgressOnCancelListener#onCancel] download will cancel.");
                    H5SaveVideoPlugin.this.isCanceled = true;
                    H5SaveVideoPlugin.this.loadingDialog = null;
                    H5SaveVideoPlugin.this.loadingDialogShowing.set(false);
                    bridgeContext.sendBridgeResult("error", Integer.valueOf(3));
                }
            });
            try {
                this.loadingDialog.show();
            } catch (Throwable e) {
                H5Log.e(TAG, "[showProgress] Exception: " + e.toString(), e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dismissProgress() {
        if (this.loadingDialog != null && this.activity != null && this.loadingDialog.isShowing() && !this.activity.isFinishing()) {
            try {
                this.loadingDialog.dismiss();
            } catch (Throwable e) {
                H5Log.e(TAG, "[dismissProgress] Exception: " + e.toString(), e);
            } finally {
                this.loadingDialog = null;
            }
        }
    }

    private void saveVideo(String url, H5Page h5Page2, H5BridgeContext bridgeContext) {
        this.isCanceled = false;
        startLoading(bridgeContext);
        if (url.startsWith("https://resource/")) {
            String localPath = null;
            if (url.startsWith("https://resource/")) {
                localPath = TinyappUtils.transferApPathToLocalPath(url);
            }
            if (!TextUtils.isEmpty(localPath)) {
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new LocalVideoSaveCommand(localPath, bridgeContext));
                return;
            }
            stopLoading();
            sendBridgeResultError(bridgeContext, 12);
            return;
        }
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new VideoSaveCommand(new FileCache(H5Utils.getContext(), H5Utils.getString(h5Page2.getParams(), (String) "appId")).getTempPath(H5Utils.getContext(), url), url, bridgeContext));
    }

    /* access modifiers changed from: private */
    public boolean sendBridgeResultError(H5BridgeContext context, int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case 0:
                errorMessage = "none error!";
                break;
            case 1:
                errorMessage = "not implemented!";
                break;
            case 2:
                errorMessage = "invalid parameter!";
                break;
            case 3:
                errorMessage = "unknown error!";
                break;
            case 4:
                errorMessage = "forbidden!";
                break;
            case 12:
                errorMessage = ERROR_MESSAGE_INVALID_SOURCE_DATA;
                break;
            case 13:
                errorMessage = ERROR_MESSAGE_INVALID_SAVE_FOLDER;
                break;
            case 14:
                errorMessage = ERROR_MESSAGE_FILE_IO;
                break;
            case 15:
                errorMessage = ERROR_MESSAGE_INVALID_FILE_TYPE;
                break;
            default:
                errorMessage = "unknown error!";
                break;
        }
        JSONObject data = new JSONObject();
        data.put((String) "message", (Object) errorMessage);
        data.put((String) "error", (Object) Integer.valueOf(errorCode));
        return context.sendBridgeResult(data);
    }
}
