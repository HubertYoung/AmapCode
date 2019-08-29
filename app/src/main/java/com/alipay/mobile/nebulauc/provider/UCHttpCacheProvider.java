package com.alipay.mobile.nebulauc.provider;

import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.nebula.provider.H5HttpCacheProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.uc.webview.export.extension.UCCore;

public class UCHttpCacheProvider implements H5HttpCacheProvider {
    public static final String TAG = "UCHttpCacheProvider";

    public long cleanHttpCache() {
        if (!H5Flag.ucReady) {
            return 0;
        }
        Long size = (Long) UCCore.notifyCoreEvent(1, null);
        H5Log.d(TAG, "cleanHttpCache size is " + size.toString());
        UCCore.notifyCoreEvent(3, null);
        H5Log.d(TAG, "cleanHttpCache CORE_EVENT_CLEAR_HTTP_CACHE");
        return size.longValue();
    }
}
