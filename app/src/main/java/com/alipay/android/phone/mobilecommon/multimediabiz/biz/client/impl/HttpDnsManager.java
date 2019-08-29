package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import java.util.concurrent.ConcurrentHashMap;

public class HttpDnsManager {
    private static HttpDnsManager a = new HttpDnsManager();
    private ConcurrentHashMap<String, Long> b = new ConcurrentHashMap<>();

    private HttpDnsManager() {
    }

    public static HttpDnsManager getInstance() {
        return a;
    }

    public long getValue(String key) {
        if (TextUtils.isEmpty(key) || !this.b.containsKey(key)) {
            return 0;
        }
        return this.b.get(key).longValue();
    }

    public void putValue(String key, long val) {
        if (!TextUtils.isEmpty(key)) {
            this.b.put(key, Long.valueOf(val));
        }
    }

    public boolean isIpTimeOut(String key) {
        return Math.abs(System.currentTimeMillis() - getValue(key)) <= ((long) ConfigManager.getInstance().getCommonConfigItem().net.ipTimeout);
    }
}
