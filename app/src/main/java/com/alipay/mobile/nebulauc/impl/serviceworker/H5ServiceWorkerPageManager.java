package com.alipay.mobile.nebulauc.impl.serviceworker;

import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.basebridge.H5BasePage;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;

public class H5ServiceWorkerPageManager {
    private static H5Page instance;

    public static synchronized H5Page getInstance(Context context) {
        H5Page h5Page;
        synchronized (H5ServiceWorkerPageManager.class) {
            if (context == null) {
                try {
                    H5Log.e((String) "", (String) "getTopActivity==null");
                }
            }
            if (instance == null) {
                Bundle bundle = new Bundle();
                bundle.putString("appId", "60000002");
                bundle.putString("url", "https://www.alipay.com");
                bundle.putString(H5Param.URL, "https://www.alipay.com");
                instance = new H5BasePage(context, null, bundle);
                initServicePlug();
            }
            if (instance instanceof H5BasePage) {
                ((H5BasePage) instance).setH5Context(context);
            }
            h5Page = instance;
        }
        return h5Page;
    }

    private static void initServicePlug() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            h5Service.initServicePlugin();
        }
    }
}
