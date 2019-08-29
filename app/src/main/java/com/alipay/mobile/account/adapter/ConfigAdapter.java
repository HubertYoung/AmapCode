package com.alipay.mobile.account.adapter;

import android.os.Bundle;
import com.alipay.android.phone.inside.config.plugin.ConfigPlugin;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.mpaas.nebula.plugin.H5ServicePlugin;

public class ConfigAdapter {
    private static ConfigAdapter a;

    private ConfigAdapter() {
    }

    public static ConfigAdapter a() {
        if (a == null) {
            synchronized (ConfigAdapter.class) {
                if (a == null) {
                    a = new ConfigAdapter();
                }
            }
        }
        return a;
    }

    public static String a(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("configName", str);
        String str2 = "";
        try {
            Bundle bundle2 = (Bundle) ServiceExecutor.b(ConfigPlugin.SERVICE_DYNAMI_CCONFIG_LOAD, bundle);
            if (bundle2 != null) {
                str2 = bundle2.getString("configValue");
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getConfig key:");
            sb.append(str);
            sb.append(",value:");
            sb.append(str2);
            f.c((String) "ConfigAdapter", sb.toString());
        } catch (Throwable th) {
            LoggerFactory.f().a("ConfigAdapter", H5ServicePlugin.GET_CONFIG, th);
        }
        return str2;
    }
}
