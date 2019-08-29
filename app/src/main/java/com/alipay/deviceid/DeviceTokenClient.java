package com.alipay.deviceid;

import android.content.Context;
import com.alipay.deviceid.module.x.ba;
import com.alipay.deviceid.module.x.e;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeviceTokenClient {
    private static DeviceTokenClient deviceTokenClientInstance;
    /* access modifiers changed from: private */
    public Context context = null;
    private AtomicBoolean isRepInitializing = new AtomicBoolean(false);

    public interface InitResultListener {
        void onResult(String str, int i);
    }

    private DeviceTokenClient(Context context2) {
        if (context2 == null) {
            throw new IllegalArgumentException("DeviceTokenClient initialization error: context is null.");
        }
        this.context = context2;
    }

    public static DeviceTokenClient getInstance(Context context2) {
        if (deviceTokenClientInstance == null) {
            synchronized (DeviceTokenClient.class) {
                if (deviceTokenClientInstance == null) {
                    deviceTokenClientInstance = new DeviceTokenClient(context2);
                }
            }
        }
        return deviceTokenClientInstance;
    }

    private void initializeSo() {
        if (!this.isRepInitializing.getAndSet(true)) {
            new Thread(new a(this)).start();
        }
    }

    public void initToken(String str, String str2, InitResultListener initResultListener) {
        initializeSo();
        if (e.a(str)) {
            if (initResultListener != null) {
                initResultListener.onResult("", 2);
            }
        } else if (e.a(str2)) {
            if (initResultListener != null) {
                initResultListener.onResult("", 3);
            }
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("appName", str);
            hashMap.put("appKeyClient", str2);
            hashMap.put(TransportConstants.KEY_RPC_VERSION, "6");
            hashMap.put("appchannel", "openapi");
            ba.a().a((Runnable) new b(this, hashMap, initResultListener, str));
        }
    }
}
