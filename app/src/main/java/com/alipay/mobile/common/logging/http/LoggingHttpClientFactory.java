package com.alipay.mobile.common.logging.http;

import android.content.Context;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.MdapLogUploadManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.http.BaseHttpClient;
import com.alipay.tianyan.mobilesdk.TianyanLoggingHolder;

public class LoggingHttpClientFactory {
    private static String a = "LoggingHttpClientFactory";

    public static synchronized BaseHttpClient a(String logCategory, String url, Context context) {
        BaseHttpClient client;
        synchronized (LoggingHttpClientFactory.class) {
            if (!LoggerFactory.getProcessInfo().isToolsProcess() && a() && TianyanLoggingHolder.getInstance().getLoggingHttpClientGetter() != null) {
                MdapLogUploadManager.a();
                if (!MdapLogUploadManager.a(logCategory)) {
                    client = TianyanLoggingHolder.getInstance().getLoggingHttpClientGetter().getHttpClient();
                    client.setContext(context);
                    client.setUrl(url);
                    LoggerFactory.getTraceLogger().info(a, "use logging HttpClient");
                }
            }
            LoggerFactory.getTraceLogger().info(a, "use HttpClient");
            client = new HttpClient(url, context);
        }
        return client;
    }

    private static boolean a() {
        String currVal = LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences("UseLogHttpClientConfig", 4).getString("UseLogHttpClientConfig", BQCCameraParam.VALUE_NO);
        LoggerFactory.getTraceLogger().info(a, "currVal = " + currVal);
        if ("yes".equals(currVal)) {
            return true;
        }
        return false;
    }
}
