package com.alipay.mobile.liteprocess.rpc;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.helper.ReadSettingServerUrl;
import com.alipay.mobile.common.rpc.RpcDefaultConfig;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.utils.LogCatUtil;
import com.alipay.mobile.framework.service.common.HttpTransportSevice;
import com.alipay.mobile.liteprocess.Util;

public class LiteRpcDefaultConfig extends RpcDefaultConfig {
    public String getUrl() {
        return ReadSettingServerUrl.getInstance().getGWFURL(Util.getContext());
    }

    public Transport getTransport() {
        return (Transport) Util.getMicroAppContext().findServiceByInterface(HttpTransportSevice.class.getName());
    }

    public String getAppKey() {
        try {
            Context context = Util.getContext();
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
