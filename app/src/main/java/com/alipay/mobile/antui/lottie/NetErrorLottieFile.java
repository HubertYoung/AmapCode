package com.alipay.mobile.antui.lottie;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.antui.excutor.AntUIExecutorManager;
import com.alipay.mobile.antui.excutor.ConfigExecutor;
import com.alipay.mobile.antui.excutor.FileLoadExecutor;
import com.alipay.mobile.antui.excutor.FileLoadRequest;
import com.alipay.mobile.antui.model.NetErrorConfigModel;
import com.alipay.mobile.antui.utils.AuiLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class NetErrorLottieFile {
    public static final String NET_ERROR_EMPTY_FILE_NAME = "lottie_empty.json";
    public static final String NET_ERROR_EMPTY_SIMPLE_FILE_NAME = "lottie_empty_lite.json";
    private static final String NET_ERROR_LOTTIE_FILES_ID = "z6Ptk_4-QxCV-BLlxFPzUgAAACMAAQED";
    private static final String NET_ERROR_LOTTIE_FILES_MD5 = "bec14320f074b489b76ce8c8d05f5a5d";
    public static final String NET_ERROR_OVERFLOW_FILE_NAME = "lottie_limit.json";
    public static final String NET_ERROR_SIGNAL_FILE_NAME = "lottie_networkerror.json";
    public static final String NET_ERROR_WARNING_FILE_NAME = "lottie_alert.json";
    private static final String SWITCH_CONFIG_KEY = "AUNetError_LOTTIE_SWITCH";
    private static final String TAG = "AUNetErrorView";
    private static NetErrorLottieFile instance;
    private FileLoadExecutor fileLoadExecutor;
    private boolean isGetConfigSuccess = false;
    private NetErrorConfigModel netErrorConfigModel;

    public static synchronized NetErrorLottieFile getInstance() {
        NetErrorLottieFile netErrorLottieFile;
        synchronized (NetErrorLottieFile.class) {
            try {
                if (instance == null) {
                    instance = new NetErrorLottieFile();
                }
                netErrorLottieFile = instance;
            }
        }
        return netErrorLottieFile;
    }

    public JSONObject getNetErrorAnimation(String fileName, int errorType, LoadLottieCallback callback) {
        if (isNoLottie(fileName)) {
            return null;
        }
        String json = LottieCache.getInstance().getFileJson(fileName);
        if (!TextUtils.isEmpty(json)) {
            try {
                AuiLogger.debug(TAG, "get lottie from cache");
                return new JSONObject(json);
            } catch (JSONException e) {
                AuiLogger.mtBizReport(TAG, "String can not case to JSONObject");
                return null;
            }
        } else if (isFileExecutorInvalid()) {
            return null;
        } else {
            FileLoadRequest request = new FileLoadRequest();
            request.fileId = NET_ERROR_LOTTIE_FILES_ID;
            request.fileMd5 = NET_ERROR_LOTTIE_FILES_MD5;
            request.fileName = fileName;
            request.zipName = "LottieFiles.zip";
            request.docPath = "lottie";
            request.onlyWifi = true;
            this.fileLoadExecutor.download(request, new a(this, callback, errorType));
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
        if (r6.equals(NET_ERROR_SIGNAL_FILE_NAME) != false) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isNoLottie(java.lang.String r6) {
        /*
            r5 = this;
            r1 = 1
            r2 = 0
            boolean r3 = r5.isGetConfigSuccess
            if (r3 != 0) goto L_0x0009
            r5.getConfig()
        L_0x0009:
            int r3 = android.os.Build.VERSION.SDK_INT
            com.alipay.mobile.antui.model.NetErrorConfigModel r4 = r5.netErrorConfigModel
            int r4 = r4.version
            if (r3 > r4) goto L_0x0012
        L_0x0011:
            return r1
        L_0x0012:
            com.alipay.mobile.antui.model.NetErrorConfigModel r3 = r5.netErrorConfigModel
            java.util.List<java.lang.String> r3 = r3.types
            if (r3 != 0) goto L_0x001a
            r1 = r2
            goto L_0x0011
        L_0x001a:
            r3 = -1
            int r4 = r6.hashCode()
            switch(r4) {
                case -1735486501: goto L_0x004e;
                case -1045527159: goto L_0x003a;
                case -787612394: goto L_0x0044;
                case 221135132: goto L_0x0031;
                case 1933751002: goto L_0x0058;
                default: goto L_0x0022;
            }
        L_0x0022:
            r2 = r3
        L_0x0023:
            switch(r2) {
                case 0: goto L_0x0062;
                case 1: goto L_0x0065;
                case 2: goto L_0x0065;
                case 3: goto L_0x0068;
                case 4: goto L_0x006b;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.String r0 = ""
        L_0x0028:
            com.alipay.mobile.antui.model.NetErrorConfigModel r1 = r5.netErrorConfigModel
            java.util.List<java.lang.String> r1 = r1.types
            boolean r1 = r1.contains(r0)
            goto L_0x0011
        L_0x0031:
            java.lang.String r1 = "lottie_networkerror.json"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0022
            goto L_0x0023
        L_0x003a:
            java.lang.String r2 = "lottie_empty.json"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x0022
            r2 = r1
            goto L_0x0023
        L_0x0044:
            java.lang.String r1 = "lottie_empty_lite.json"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0022
            r2 = 2
            goto L_0x0023
        L_0x004e:
            java.lang.String r1 = "lottie_limit.json"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0022
            r2 = 3
            goto L_0x0023
        L_0x0058:
            java.lang.String r1 = "lottie_alert.json"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0022
            r2 = 4
            goto L_0x0023
        L_0x0062:
            java.lang.String r0 = "networkerror"
            goto L_0x0028
        L_0x0065:
            java.lang.String r0 = "empty"
            goto L_0x0028
        L_0x0068:
            java.lang.String r0 = "limit"
            goto L_0x0028
        L_0x006b:
            java.lang.String r0 = "alert"
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.lottie.NetErrorLottieFile.isNoLottie(java.lang.String):boolean");
    }

    private void getConfig() {
        ConfigExecutor configExecutor = AntUIExecutorManager.getInstance().getConfigExecutor();
        if (configExecutor != null) {
            this.isGetConfigSuccess = true;
            try {
                this.netErrorConfigModel = (NetErrorConfigModel) JSON.parseObject(configExecutor.getConfig(SWITCH_CONFIG_KEY), NetErrorConfigModel.class);
                if (this.netErrorConfigModel != null && this.netErrorConfigModel.version == 0) {
                    this.netErrorConfigModel.version = 21;
                }
            } catch (Exception e) {
                AuiLogger.error(TAG, "Exception e = " + e);
            }
        } else {
            AuiLogger.error(TAG, "ConfigExecutor is null");
        }
        if (this.netErrorConfigModel == null) {
            this.netErrorConfigModel = new NetErrorConfigModel();
            this.netErrorConfigModel.version = 21;
        }
    }

    private boolean isFileExecutorInvalid() {
        if (this.fileLoadExecutor == null) {
            this.fileLoadExecutor = AntUIExecutorManager.getInstance().getFileLoadExecutor();
        }
        return this.fileLoadExecutor == null;
    }
}
