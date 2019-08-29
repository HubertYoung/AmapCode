package com.alibaba.analytics.core.sync;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.SpSetting;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

public class HttpsHostPortMgr implements IKVChangeListener {
    public static final String TAG_HTTPS_HOST_PORT = "utanalytics_https_host";
    public static HttpsHostPortMgr instance;
    private String mHttpsUrl = "https://h-adashx.ut.taobao.com/upload";

    public static synchronized HttpsHostPortMgr getInstance() {
        HttpsHostPortMgr httpsHostPortMgr;
        synchronized (HttpsHostPortMgr.class) {
            try {
                if (instance == null) {
                    instance = new HttpsHostPortMgr();
                }
                httpsHostPortMgr = instance;
            }
        }
        return httpsHostPortMgr;
    }

    HttpsHostPortMgr() {
        try {
            parseConifg(AppInfoUtil.getString(Variables.getInstance().getContext(), TAG_HTTPS_HOST_PORT));
            parseConifg(SpSetting.get(Variables.getInstance().getContext(), TAG_HTTPS_HOST_PORT));
            parseConifg(SystemConfigMgr.getInstance().get(TAG_HTTPS_HOST_PORT));
            SystemConfigMgr.getInstance().register(TAG_HTTPS_HOST_PORT, this);
        } catch (Throwable unused) {
        }
    }

    public void onChange(String str, String str2) {
        parseConifg(str2);
    }

    private void parseConifg(String str) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb.append(str);
            sb.append("/upload");
            this.mHttpsUrl = sb.toString();
        }
    }

    public String getHttpsUrl() {
        Logger.d((String) "", "mHttpsUrl", this.mHttpsUrl);
        return this.mHttpsUrl;
    }
}
