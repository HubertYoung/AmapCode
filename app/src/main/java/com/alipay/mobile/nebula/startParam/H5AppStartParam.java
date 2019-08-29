package com.alipay.mobile.nebula.startParam;

import android.os.Bundle;
import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5AppStartParam {
    private static H5AppStartParam instance;
    private Map<String, Bundle> appStartParam = new ConcurrentHashMap();

    public static synchronized H5AppStartParam getInstance() {
        H5AppStartParam h5AppStartParam;
        synchronized (H5AppStartParam.class) {
            try {
                if (instance == null) {
                    instance = new H5AppStartParam();
                }
                h5AppStartParam = instance;
            }
        }
        return h5AppStartParam;
    }

    private H5AppStartParam() {
    }

    public void put(String appId, Bundle bundle) {
        if (!TextUtils.isEmpty(appId)) {
            this.appStartParam.put(appId, bundle);
        }
    }

    public void remove(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            this.appStartParam.remove(appId);
        }
    }

    public Bundle get(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        return this.appStartParam.get(appId);
    }
}
