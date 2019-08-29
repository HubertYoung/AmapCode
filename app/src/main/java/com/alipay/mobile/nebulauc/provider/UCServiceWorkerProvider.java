package com.alipay.mobile.nebulauc.provider;

import android.os.Bundle;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.provider.H5ServiceWorkerPushProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.uc.webview.export.extension.UCCore;
import java.util.HashMap;

public class UCServiceWorkerProvider implements H5ServiceWorkerPushProvider {
    private static final String TAG = "UCServiceWorkerProvider";

    public void sendServiceWorkerPushMessage(HashMap<String, String> hashMap) {
        sendServiceWorkerPushMessage(hashMap, null);
    }

    public void sendServiceWorkerPushMessage(HashMap<String, String> hashMap, final H5CallBack h5CallBack) {
        ValueCallback cb = new ValueCallback<Object>() {
            public void onReceiveValue(Object result) {
                if (result != null) {
                    Bundle info = (Bundle) result;
                    String appid = info.getString("appId");
                    String messageId = info.getString("messageId");
                    H5Log.d(UCServiceWorkerProvider.TAG, "sendServiceWorkerPushMessage onReceiveValue appid=" + appid + ", messageId=" + messageId + ", result=" + info.getString("result"));
                    if (h5CallBack != null) {
                        h5CallBack.onCallBack(H5Utils.toJSONObject(info));
                    }
                }
            }
        };
        Bundle bundle = new Bundle();
        for (String key : hashMap.keySet()) {
            bundle.putString(key, hashMap.get(key));
        }
        H5Log.d(TAG, "sendServiceWorkerPushMessage" + hashMap.toString());
        UCCore.notifyCoreEvent(2, bundle, cb);
    }

    public synchronized void clearServiceWorker(final String swHost) {
        String pid = null;
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            pid = h5EventHandlerService.getLitePid() + "";
        } else {
            H5Log.w(TAG, "h5EventHandlerService == null");
        }
        H5Log.d(TAG, "clearServiceWorker " + swHost + ", pid " + pid);
        H5Utils.runNotOnMain(H5ThreadType.URGENT, new Runnable() {
            public void run() {
                H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "clear.sw.start");
                UCCore.notifyCoreEvent(4, swHost);
                H5Utils.handleTinyAppKeyEvent((String) "uc_init", (String) "clear.sw.end");
            }
        });
    }

    public synchronized void clearServiceWorkerSync(final String swHost, final H5CallBack h5CallBack) {
        String pid = null;
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            pid = h5EventHandlerService.getLitePid() + "";
        } else {
            H5Log.w(TAG, "h5EventHandlerService == null");
        }
        H5Log.d(TAG, "clearServiceWorkerSync " + swHost + ", pid " + pid);
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                UCCore.notifyCoreEvent(9, swHost, new ValueCallback() {
                    public void onReceiveValue(Object callback) {
                        H5Log.d(UCServiceWorkerProvider.TAG, "clearServiceWorkerSync success? " + callback);
                        h5CallBack.onCallBack(new JSONObject());
                    }
                });
            }
        });
    }
}
