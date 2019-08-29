package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.helper.ReadSettingServerUrl;
import com.alipay.mobile.common.rpc.RpcDefaultConfig;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.utils.LogCatUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.HttpTransportSevice;

public class DefaultConfig extends RpcDefaultConfig {
    public DefaultConfig() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getUrl() {
        return ReadSettingServerUrl.getInstance().getGWFURL(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public Transport getTransport() {
        return (Transport) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(HttpTransportSevice.class.getName());
    }

    public String getAppKey() {
        try {
            Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
            String appkey = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("appkey");
            if (!TextUtils.isEmpty(appkey)) {
                LogCatUtil.info("DefaultConfig", "Get appkey=[" + appkey + "] from metaData.");
                return appkey;
            }
            LogCatUtil.info("DefaultConfig", "Not exist appkey in metaData.");
            return "";
        } catch (Exception e) {
            LogCatUtil.warn((String) "DefaultConfig", (Throwable) e);
        }
    }

    public void giveResponseHeader(String operationType, HttpUrlHeader header) {
        super.giveResponseHeader(operationType, header);
    }
}
