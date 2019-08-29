package com.alipay.mobile.common.logging.uploader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.common.logging.MdapLogUploadManager;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.encrypt.LogEncryptClient;
import com.alipay.mobile.common.logging.api.http.BaseHttpClient;
import com.alipay.mobile.common.logging.http.LoggingHttpClientFactory;
import com.alipay.mobile.common.logging.http.MdapTrafficController;
import com.alipay.mobile.common.logging.util.FileUtil;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseUploader {
    public static String a = "MdapLogUploadManager";
    protected File b;
    protected Context c;

    public BaseUploader(File uploadFileDir, Context context) {
        this.b = uploadFileDir;
        this.c = context;
    }

    /* access modifiers changed from: protected */
    public final void a() {
        if (FileUtil.getFolderSize(this.b) >= ((long) MdapLogUploadManager.a)) {
            File[] files = null;
            try {
                files = this.b.listFiles();
            } catch (Throwable t) {
                Log.e(a, "cleanExpiresFile", t);
            }
            if (files != null && files.length >= 4) {
                Arrays.sort(files, MdapLogUploadManager.c);
                int len = files.length / 4;
                for (int i = 0; i < len; i++) {
                    File file = files[i];
                    if (file != null && file.exists() && file.isFile()) {
                        try {
                            file.delete();
                            LoggerFactory.getTraceLogger().error(a, "cleanExpiresFile: " + file.getName() + " is too large or too old, total: " + files.length);
                        } catch (Throwable e) {
                            LoggerFactory.getTraceLogger().warn(a, file.getName() + " cleanExpiresFile", e);
                        }
                    }
                }
            }
        }
    }

    private static void a(Context context, String cls) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, cls), 1, 1);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error(a, "setComponentEnabled: " + t);
        }
    }

    /* access modifiers changed from: protected */
    public final Pair<Long, Long> a(File logFile, String logCategory, String uploadUrl, String event) {
        String uploadLogUrl;
        if (logFile == null) {
            throw new IllegalStateException("file object is NULL");
        }
        try {
            String logContent = FileUtil.readFile(logFile);
            if (TextUtils.isEmpty(logContent)) {
                logFile.delete();
                throw new IllegalStateException("file content is empty");
            }
            String logContent2 = b(logContent);
            if (TextUtils.isEmpty(logContent2)) {
                throw new IllegalStateException("decode file content is empty");
            }
            String logHost = LoggerFactory.getLogContext().getLogHost();
            if (TextUtils.isEmpty(logHost)) {
                throw new IllegalStateException("log host is empty");
            }
            if (LoggingUtil.isOfflineMode()) {
                a(this.c, "com.alipay.mobile.logmonitor.ClientMonitorExtReceiver");
                Intent intent = new Intent(this.c.getPackageName() + ".monitor.action.UPLOAD_MDAP_LOG");
                intent.setPackage(this.c.getPackageName());
                intent.putExtra("file", logFile.getName());
                try {
                    this.c.sendBroadcast(intent);
                } catch (Throwable t) {
                    Log.e(a, "uploadFile", t);
                }
            }
            byte[] zippedData = LoggingUtil.gzipDataByString(logContent2);
            MdapTrafficController.a(this.c, logCategory, zippedData.length);
            Map headerData = new HashMap();
            headerData.put("Content-type", "text/xml");
            headerData.put("ProcessName", LoggerFactory.getProcessInfo().getProcessAlias());
            headerData.put("bizCode", logCategory);
            headerData.put("userId", LoggerFactory.getLogContext().getUserId());
            headerData.put("productId", LoggerFactory.getLogContext().getProductId());
            headerData.put("event", event);
            headerData.put(LoggingSPCache.STORAGE_PRODUCTVERSION, LoggerFactory.getLogContext().getProductVersion());
            headerData.put("utdId", LoggerFactory.getLogContext().getDeviceId());
            if (!TextUtils.isEmpty(uploadUrl)) {
                uploadLogUrl = uploadUrl;
                LoggerFactory.getTraceLogger().info(a, "uploadUrl = " + uploadUrl + " logCategory = " + logCategory);
            } else {
                uploadLogUrl = logHost + "/loggw/logUpload.do";
            }
            BaseHttpClient httpClient = LoggingHttpClientFactory.a(logCategory, uploadLogUrl, this.c);
            long uploadStart = SystemClock.elapsedRealtime();
            try {
                if (httpClient.synchronousRequestByPOST(zippedData, headerData) == null) {
                    httpClient.closeStreamForNextExecute();
                    throw new IllegalStateException("http response is NULL");
                }
                int responseCode = httpClient.getResponseCode();
                String responseContent = httpClient.getResponseContent();
                long requestLength = httpClient.getRequestLength();
                long responseLength = httpClient.getResponseLength();
                httpClient.closeStreamForNextExecute();
                if (responseCode != 200) {
                    a(logCategory, String.valueOf(responseCode), "unknown", "http response code error", String.valueOf(zippedData.length));
                    throw new IllegalStateException("response code is " + responseCode);
                }
                try {
                    JSONObject jSONObject = new JSONObject(responseContent);
                    int code = jSONObject.getInt("code");
                    if (code != 200) {
                        a(logCategory, String.valueOf(responseCode), String.valueOf(code), "mdap response code error", String.valueOf(zippedData.length));
                        throw new IllegalStateException("responseContent code is " + code);
                    }
                    long uploadCost = SystemClock.elapsedRealtime() - uploadStart;
                    if (uploadCost > 0) {
                        a(logCategory, String.valueOf(zippedData.length), uploadCost);
                    }
                    try {
                        logFile.delete();
                        if (LoggingUtil.isDebuggable(this.c) && !TextUtils.isEmpty(responseContent)) {
                            String logSwitch = null;
                            try {
                                int index = responseContent.indexOf("logSwitch=");
                                if (index > 0) {
                                    logSwitch = responseContent.substring(index + 10);
                                }
                            } catch (Throwable e) {
                                LoggerFactory.getTraceLogger().warn(a, e);
                            }
                            Log.v(a, "logswitch: " + logSwitch);
                        }
                        return Pair.create(Long.valueOf(requestLength), Long.valueOf(responseLength));
                    } catch (Throwable t2) {
                        IllegalStateException illegalStateException = new IllegalStateException("delete file error: " + t2, t2);
                        throw illegalStateException;
                    }
                } catch (JSONException e2) {
                    a(logCategory, String.valueOf(responseCode), "unknown", "get responseContent code error,JSONException", String.valueOf(zippedData.length));
                    throw new IllegalStateException("get responseContent code error,JSONException");
                }
            } catch (Throwable t3) {
                IllegalStateException illegalStateException2 = new IllegalStateException("POST request error: " + t3, t3);
                throw illegalStateException2;
            }
        } catch (Throwable e3) {
            IllegalStateException illegalStateException3 = new IllegalStateException("read file error: " + e3, e3);
            throw illegalStateException3;
        }
    }

    protected static String a(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        String[] loginfo = fileName.split("_");
        if (loginfo.length >= 3) {
            return loginfo[2];
        }
        return null;
    }

    private static String b(String content) {
        String[] logs;
        LogEncryptClient client = LoggerFactory.getLogContext().getLogEncryptClient();
        if (client == null) {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        for (String item : content.split("\\$\\$")) {
            if (!TextUtils.isEmpty(item)) {
                if (item.startsWith("1_")) {
                    try {
                        String deItem = client.decrypt(item.substring(2));
                        if (!TextUtils.isEmpty(deItem)) {
                            sb.append(deItem).append("$$");
                        }
                    } catch (Throwable th) {
                    }
                } else {
                    sb.append(item).append("$$");
                }
            }
        }
        return sb.toString();
    }

    private static void a(String logCategory, String zipLength, long cost) {
        if (!LogCategory.CATEGORY_LOGMONITOR.equals(logCategory)) {
            Behavor behavor = new Behavor();
            behavor.setBehaviourPro(LogCategory.CATEGORY_LOGMONITOR);
            behavor.setSeedID("LogUploadCost");
            behavor.setParam1(logCategory);
            behavor.setParam2(zipLength);
            behavor.setParam3(String.valueOf(cost));
            behavor.setLoggerLevel(1);
            LoggerFactory.getBehavorLogger().event(null, behavor);
        }
    }

    private static void a(String logCategory, String httpCode, String mdapCode, String errorMsg, String zipLength) {
        if (!LogCategory.CATEGORY_LOGMONITOR.equals(logCategory)) {
            Behavor behavor = new Behavor();
            behavor.setBehaviourPro(LogCategory.CATEGORY_LOGMONITOR);
            behavor.setSeedID("LogUpload");
            behavor.setParam1(logCategory);
            behavor.setParam2(httpCode);
            behavor.setParam3(mdapCode);
            behavor.addExtParam("errorMsg", errorMsg);
            behavor.addExtParam("zipLength", zipLength);
            LoggerFactory.getBehavorLogger().event(null, behavor);
        }
    }
}
