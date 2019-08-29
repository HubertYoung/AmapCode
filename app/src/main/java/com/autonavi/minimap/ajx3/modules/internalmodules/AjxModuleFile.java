package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.h5container.api.H5PageData;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.MD5Util;
import com.autonavi.minimap.ajx3.util.PathUtil;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.file")
public class AjxModuleFile extends AbstractModule {
    private static final String DOMAIN_FILE = "file://";
    private static final String DOMAIN_PATH = "path://";
    public static final String MODULE_NAME = "ajx.file";
    private static Handler mHandler;
    private static HandlerThread mThread;
    public final String APP_PATH;
    private final int MSG_FILE_DELETE = 103;
    private final int MSG_FILE_READ = 101;
    private final int MSG_FILE_WRITE = 102;
    private final int MSG_ITEM_DELETE = 104;
    public final String SDCARD_PATH;
    private final SparseArray<DownloadOption> mDownloadList = new SparseArray<>();
    private int mDownloadRequestID = 1;

    static class Data {
        public int action;
        public JsFunctionCallback callback;
        public String content;
        public String path;

        public Data(int i, String str, String str2, JsFunctionCallback jsFunctionCallback) {
            this.action = i;
            this.path = str;
            this.content = str2;
            this.callback = jsFunctionCallback;
        }
    }

    static class DownloadOption {
        private static final String DIR_NAME = "ajxFileDownload";
        public boolean isNeedProgress = false;
        private final File mFileDownloadDir;
        /* access modifiers changed from: private */
        public int requestId = -1;
        public String savePath = null;
        public String url = null;

        public DownloadOption(Context context, String str) {
            this.url = str;
            this.mFileDownloadDir = new File(FileUtil.getExternalCacheDir(context), DIR_NAME);
            handleSavePath();
        }

        private void handleSavePath() {
            if (FileUtil.makesureExist(this.mFileDownloadDir) && !TextUtils.isEmpty(this.url)) {
                this.savePath = FileUtil.getAbsolutePath(this.mFileDownloadDir, getFileName(this.url));
            }
        }

        private String getFileName(String str) {
            return MD5Util.getStringMD5(str);
        }
    }

    static class ModuleAjxDownloadCallback implements bjf {
        private JsFunctionCallback mCallback;
        private JSONObject mData = new JSONObject();
        private DownloadOption mOption;

        public ModuleAjxDownloadCallback(DownloadOption downloadOption, JsFunctionCallback jsFunctionCallback) {
            this.mOption = downloadOption;
            this.mCallback = jsFunctionCallback;
            StringBuilder sb = new StringBuilder("file://");
            sb.append(downloadOption.savePath);
            putData(PhotoParam.SAVE_PATH, sb.toString());
            putData("fileSize", Integer.valueOf(0));
            putData("statusCode", Integer.valueOf(200));
            if (this.mOption.isNeedProgress) {
                putData("progress", Float.valueOf(0.0f));
            }
        }

        public void onStart(long j, Map<String, List<String>> map, int i) {
            putData("statusCode", Integer.valueOf(i));
            putData("fileSize", Long.valueOf(j));
            if (this.mCallback != null) {
                putData("eventName", H5PageData.KEY_UC_START);
                if (this.mOption.isNeedProgress) {
                    putData("progress", Float.valueOf(0.0f));
                }
                this.mCallback.callback(Integer.valueOf(this.mOption.requestId), this.mData);
            }
        }

        public void onProgressUpdate(long j, long j2) {
            if (this.mCallback != null && this.mOption.isNeedProgress) {
                putData("eventName", "progressUpdate");
                putData("progress", Float.valueOf((((float) j) * 1.0f) / ((float) j2)));
                this.mCallback.callback(Integer.valueOf(this.mOption.requestId), this.mData);
            }
        }

        public void onFinish(bpk bpk) {
            if (this.mCallback != null) {
                putData("eventName", "finished");
                putData("statusCode", Integer.valueOf(bpk.getStatusCode()));
                if (this.mOption.isNeedProgress) {
                    putData("progress", Float.valueOf(1.0f));
                }
                this.mCallback.callback(Integer.valueOf(this.mOption.requestId), this.mData);
            }
        }

        public void onError(int i, int i2) {
            if (this.mCallback != null) {
                putData("eventName", "error");
                putData("statusCode", Integer.valueOf(i2));
                if (this.mOption.isNeedProgress) {
                    putData("progress", Float.valueOf(1.0f));
                }
                this.mCallback.callback(Integer.valueOf(this.mOption.requestId), this.mData);
            }
        }

        private void putData(String str, Object obj) {
            try {
                this.mData.put(str, obj);
            } catch (JSONException unused) {
            }
        }
    }

    public AjxModuleFile(IAjxContext iAjxContext) {
        super(iAjxContext);
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStorageDirectory());
        sb.append("/");
        this.SDCARD_PATH = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(iAjxContext.getNativeContext().getFilesDir().getPath());
        sb2.append("/");
        this.APP_PATH = sb2.toString();
        initThread();
    }

    @AjxMethod("read")
    public void read(String str, JsFunctionCallback jsFunctionCallback) {
        Message obtainMessage = mHandler.obtainMessage();
        obtainMessage.obj = new Data(101, str, null, jsFunctionCallback);
        mHandler.sendMessage(obtainMessage);
    }

    @AjxMethod("write")
    public void write(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        Message obtainMessage = mHandler.obtainMessage();
        obtainMessage.obj = new Data(102, str, str2, jsFunctionCallback);
        mHandler.sendMessage(obtainMessage);
    }

    @AjxMethod("delete")
    public void delete(String str, JsFunctionCallback jsFunctionCallback) {
        Message obtainMessage = mHandler.obtainMessage();
        obtainMessage.obj = new Data(103, str, null, jsFunctionCallback);
        mHandler.sendMessage(obtainMessage);
    }

    @AjxMethod("deleteItem")
    public void deleteItem(String str, JsFunctionCallback jsFunctionCallback) {
        Message obtainMessage = mHandler.obtainMessage();
        obtainMessage.obj = new Data(104, str, null, jsFunctionCallback);
        mHandler.sendMessage(obtainMessage);
    }

    @AjxMethod(invokeMode = "sync", value = "download")
    public int download(String str, JsFunctionCallback jsFunctionCallback) {
        DownloadOption parseDownloadOption = parseDownloadOption(str);
        if (parseDownloadOption == null || TextUtils.isEmpty(parseDownloadOption.url) || TextUtils.isEmpty(parseDownloadOption.savePath)) {
            return -1;
        }
        parseDownloadOption.requestId = getRequestId();
        handleDownloadFile(parseDownloadOption, jsFunctionCallback);
        return parseDownloadOption.requestId;
    }

    @AjxMethod(invokeMode = "sync", value = "cancel")
    public boolean cancel(int i) {
        return handleCancelDownload(i) != null;
    }

    @AjxMethod(invokeMode = "sync", value = "isExists")
    public boolean isExists(String str) {
        String progressPath = progressPath(str);
        if (TextUtils.isEmpty(progressPath)) {
            return false;
        }
        if (progressPath.startsWith("path://")) {
            return AjxFileInfo.isFileExists(progressPath.substring(7));
        }
        return new File(progressPath).exists();
    }

    private int getRequestId() {
        int i = this.mDownloadRequestID;
        if (i == Integer.MAX_VALUE) {
            this.mDownloadRequestID = 0;
        } else {
            this.mDownloadRequestID++;
        }
        handleCancelDownload(i);
        return i;
    }

    public static void destroy() {
        mHandler = null;
        if (mThread != null) {
            mThread.quit();
            mThread = null;
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        clearDownloadTask();
    }

    private void initThread() {
        if (mHandler == null) {
            if (mThread == null) {
                HandlerThread handlerThread = new HandlerThread(AjxModuleFile.class.getSimpleName());
                mThread = handlerThread;
                handlerThread.start();
            }
            mHandler = new Handler(mThread.getLooper()) {
                public void handleMessage(Message message) {
                    Object obj = message.obj;
                    if (obj instanceof Data) {
                        Data data = (Data) obj;
                        switch (data.action) {
                            case 101:
                                AjxModuleFile.this.read(data);
                                return;
                            case 102:
                                AjxModuleFile.this.write(data);
                                return;
                            case 103:
                                AjxModuleFile.this.delete(data);
                                return;
                            case 104:
                                AjxModuleFile.this.deleteItem(data);
                                break;
                        }
                    }
                }
            };
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0065 A[SYNTHETIC, Splitter:B:28:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006b A[SYNTHETIC, Splitter:B:31:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void read(com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile.Data r7) {
        /*
            r6 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r7.path
            java.lang.String r1 = r6.progressPath(r1)
            r0.<init>(r1)
            boolean r1 = r0.exists()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0074
            boolean r1 = r0.isFile()
            if (r1 == 0) goto L_0x0074
            r1 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Exception -> 0x005e }
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ Exception -> 0x005e }
            r5.<init>(r0)     // Catch:{ Exception -> 0x005e }
            r4.<init>(r5)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            r0.<init>()     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
        L_0x0029:
            java.lang.String r1 = r4.readLine()     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            if (r1 == 0) goto L_0x0038
            r0.append(r1)     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            goto L_0x0029
        L_0x0038:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            java.lang.String r1 = "\n"
            boolean r1 = r0.endsWith(r1)     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            if (r1 == 0) goto L_0x004d
            int r1 = r0.length()     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
            int r1 = r1 - r2
            java.lang.String r0 = r0.substring(r3, r1)     // Catch:{ Exception -> 0x0058, all -> 0x0056 }
        L_0x004d:
            r4.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0077
        L_0x0051:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0077
        L_0x0056:
            r7 = move-exception
            goto L_0x0069
        L_0x0058:
            r0 = move-exception
            r1 = r4
            goto L_0x005f
        L_0x005b:
            r7 = move-exception
            r4 = r1
            goto L_0x0069
        L_0x005e:
            r0 = move-exception
        L_0x005f:
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x005b }
            if (r1 == 0) goto L_0x0077
            r1.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0077
        L_0x0069:
            if (r4 == 0) goto L_0x0073
            r4.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0073:
            throw r7
        L_0x0074:
            java.lang.String r0 = "文件不存在"
        L_0x0077:
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r7.callback
            if (r1 == 0) goto L_0x0084
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r7 = r7.callback
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r1[r3] = r0
            r7.callback(r1)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile.read(com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile$Data):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054 A[SYNTHETIC, Splitter:B:29:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0071 A[SYNTHETIC, Splitter:B:38:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile.Data r7) {
        /*
            r6 = this;
            r0 = 1
            r1 = 0
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x004e }
            java.lang.String r4 = r7.path     // Catch:{ Exception -> 0x004e }
            java.lang.String r4 = r6.progressPath(r4)     // Catch:{ Exception -> 0x004e }
            r3.<init>(r4)     // Catch:{ Exception -> 0x004e }
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x004e }
            if (r4 != 0) goto L_0x0026
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = r3.getParent()     // Catch:{ Exception -> 0x004e }
            r4.<init>(r5)     // Catch:{ Exception -> 0x004e }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x004e }
            if (r5 != 0) goto L_0x0026
            r4.mkdirs()     // Catch:{ Exception -> 0x004e }
        L_0x0026:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x004e }
            r4.<init>(r3)     // Catch:{ Exception -> 0x004e }
            java.lang.String r2 = r7.content     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            if (r2 == 0) goto L_0x0036
            java.lang.String r2 = r7.content     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
        L_0x0031:
            byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            goto L_0x0039
        L_0x0036:
            java.lang.String r2 = ""
            goto L_0x0031
        L_0x0039:
            r4.write(r2)     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r4.close()     // Catch:{ IOException -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0044:
            r2 = 1
            goto L_0x005d
        L_0x0046:
            r7 = move-exception
            r2 = r4
            goto L_0x006f
        L_0x0049:
            r3 = move-exception
            r2 = r4
            goto L_0x004f
        L_0x004c:
            r7 = move-exception
            goto L_0x006f
        L_0x004e:
            r3 = move-exception
        L_0x004f:
            r3.printStackTrace()     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ IOException -> 0x0058 }
            goto L_0x005c
        L_0x0058:
            r2 = move-exception
            r2.printStackTrace()
        L_0x005c:
            r2 = 0
        L_0x005d:
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r3 = r7.callback
            if (r3 == 0) goto L_0x006e
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r7 = r7.callback
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r0[r1] = r2
            r7.callback(r0)
        L_0x006e:
            return
        L_0x006f:
            if (r2 == 0) goto L_0x0079
            r2.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0079
        L_0x0075:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0079:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile.write(com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile$Data):void");
    }

    /* access modifiers changed from: private */
    public void delete(Data data) {
        File file = new File(progressPath(data.path));
        boolean z = file.exists() && file.isFile() && file.delete();
        if (data.callback != null) {
            data.callback.callback(Boolean.valueOf(z));
        }
    }

    /* access modifiers changed from: private */
    public void deleteItem(Data data) {
        File file = new File(progressPath(data.path));
        if (file.exists()) {
            boolean deleteFile = deleteFile(file);
            if (data.callback != null) {
                data.callback.callback(Boolean.valueOf(deleteFile), "");
            }
            return;
        }
        if (data.callback != null) {
            data.callback.callback(Boolean.FALSE, "");
        }
    }

    private boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File deleteFile : listFiles) {
                deleteFile(deleteFile);
            }
        }
        return file.delete();
    }

    private String progressPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String processPath = PathUtil.processPath(getContext(), str);
        return (!TextUtils.isEmpty(processPath) && processPath.startsWith("file://")) ? processPath.substring(7) : str;
    }

    private DownloadOption parseDownloadOption(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            DownloadOption downloadOption = new DownloadOption(getNativeContext(), jSONObject.getString("url"));
            downloadOption.isNeedProgress = jSONObject.optBoolean("needProgress", false);
            return downloadOption;
        } catch (JSONException e) {
            LogHelper.showErrorMsg(Log.getStackTraceString(e), getNativeContext());
            return null;
        }
    }

    private void handleDownloadFile(DownloadOption downloadOption, JsFunctionCallback jsFunctionCallback) {
        bjg bjg = new bjg(downloadOption.savePath);
        bjg.setUrl(downloadOption.url);
        bjh.a().a(bjg, (bjf) new ModuleAjxDownloadCallback(downloadOption, jsFunctionCallback));
        this.mDownloadList.put(downloadOption.requestId, downloadOption);
    }

    private DownloadOption handleCancelDownload(int i) {
        DownloadOption downloadOption = this.mDownloadList.get(i);
        if (downloadOption != null) {
            bjh.a().a(downloadOption.savePath);
            this.mDownloadList.remove(i);
        }
        return downloadOption;
    }

    private void clearDownloadTask() {
        for (int i = 0; i < this.mDownloadList.size(); i++) {
            bjh.a().a(this.mDownloadList.valueAt(i).savePath);
        }
        this.mDownloadList.clear();
    }
}
