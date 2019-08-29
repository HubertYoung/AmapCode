package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5CallBack;
import java.util.HashMap;

public interface H5ServiceWorkerPushProvider {
    void clearServiceWorker(String str);

    void clearServiceWorkerSync(String str, H5CallBack h5CallBack);

    void sendServiceWorkerPushMessage(HashMap<String, String> hashMap);

    void sendServiceWorkerPushMessage(HashMap<String, String> hashMap, H5CallBack h5CallBack);
}
