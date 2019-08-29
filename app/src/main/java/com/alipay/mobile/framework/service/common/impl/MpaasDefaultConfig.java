package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.rpc.RpcDefaultConfig;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;

public class MpaasDefaultConfig extends RpcDefaultConfig {
    private Context context;

    public MpaasDefaultConfig(Context context2) {
        if (context2 == null) {
            throw new IllegalArgumentException("context parameter can't be null ");
        }
        this.context = context2;
    }

    public String getUrl() {
        return ReadSettingServerUrl.getInstance().getGWFURL(this.context);
    }

    public Transport getTransport() {
        return MpaasHttpTransportSevice.getInstance(this.context);
    }

    public String getAppKey() {
        try {
            String appkey = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128).metaData.getString("appkey");
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
