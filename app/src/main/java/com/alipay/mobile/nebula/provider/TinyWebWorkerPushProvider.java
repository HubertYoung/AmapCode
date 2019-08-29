package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5CallBack;
import java.util.HashMap;

public interface TinyWebWorkerPushProvider {
    void sendWebWorkerPushMessage(HashMap<String, String> hashMap, H5CallBack h5CallBack);
}
