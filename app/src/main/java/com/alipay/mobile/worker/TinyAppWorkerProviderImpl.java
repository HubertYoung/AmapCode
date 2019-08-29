package com.alipay.mobile.worker;

import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.nebula.provider.TinyWebWorkerPushProvider;
import java.util.HashMap;

public class TinyAppWorkerProviderImpl implements TinyWebWorkerPushProvider {
    public void sendWebWorkerPushMessage(HashMap<String, String> hashMap, H5CallBack h5CallBack) {
        WorkerManager.sendPushMessage(hashMap, h5CallBack);
    }
}
