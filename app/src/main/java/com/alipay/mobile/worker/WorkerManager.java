package com.alipay.mobile.worker;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.worker.WebWorker.WorkerReadyListener;
import com.alipay.mobile.worker.remotedebug.RemoteDebugWorker;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;
import java.util.HashMap;

public final class WorkerManager {
    private static WebWorker a;
    private static HashMap<String, WebWorker> b;
    public static Bundle sParams;

    private WorkerManager() {
    }

    public static void destroyWorker(String workerId) {
        WebWorker worker = b.remove(workerId);
        if (worker != null && worker.getWebView() != null) {
            try {
                worker.getWebView().destroy();
            } catch (Throwable thr) {
                Log.e("WorkerManager", "destroyWorker error!", thr);
            }
        }
    }

    public static void preStartWorker(String appId, final Bundle params) {
        if (!"normal".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_workerType"))) {
            H5Log.d("WorkerManager", "preStartWorker sPreloadedWorker == null ? " + (a == null));
            final String workerId = "https://[APPID].hybrid.alipay-eco.com/index.worker.js".replace("[APPID]", appId);
            if (a != null || Looper.myLooper() == Looper.getMainLooper()) {
                if (a == null) {
                    preloadWorker();
                }
                b(workerId, params);
                return;
            }
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    WorkerManager.preloadWorker();
                    WorkerManager.b(workerId, params);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void b(String workerId, Bundle params) {
        if (a != null) {
            a.preStart(workerId, params);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void preloadWorker() {
        /*
            boolean r0 = com.alipay.mobile.nebula.util.H5Utils.isInTinyProcess()
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            com.alipay.mobile.worker.WebWorker r0 = a
            if (r0 != 0) goto L_0x0006
            java.util.HashMap<java.lang.String, com.alipay.mobile.worker.WebWorker> r0 = b
            if (r0 != 0) goto L_0x0006
            java.lang.String r0 = "WorkerManager"
            java.lang.String r1 = "preloadWorker"
            com.alipay.mobile.nebula.util.H5Log.d(r0, r1)
            java.lang.Class<com.alipay.mobile.worker.WorkerManager> r1 = com.alipay.mobile.worker.WorkerManager.class
            monitor-enter(r1)
            com.alipay.mobile.worker.WebWorker r0 = a     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0021
            java.util.HashMap<java.lang.String, com.alipay.mobile.worker.WebWorker> r0 = b     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0026
        L_0x0021:
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            goto L_0x0006
        L_0x0023:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            throw r0
        L_0x0026:
            com.alipay.mobile.worker.WebWorker r0 = new com.alipay.mobile.worker.WebWorker     // Catch:{ all -> 0x0023 }
            r0.<init>()     // Catch:{ all -> 0x0023 }
            a = r0     // Catch:{ all -> 0x0023 }
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.worker.WorkerManager.preloadWorker():void");
    }

    public static WebWorker registerWorker(String workerId, Bundle params) {
        H5Log.d("WorkerManager", "registerWorker");
        if (b == null) {
            b = new HashMap<>();
        }
        WebWorker worker = null;
        try {
            if (a == null || a.getWorkerId() == null || !workerId.startsWith(a.getWorkerId())) {
                if (a != null) {
                    worker = a;
                } else if (TinyAppRemoteDebugInterceptorManager.get().isRemoteDebug()) {
                    worker = new RemoteDebugWorker();
                } else {
                    worker = new WebWorker();
                }
                worker.setWorkerId(workerId);
                worker.setStartupParams(params);
                if (!TinyAppRemoteDebugInterceptorManager.get().isRemoteDebug()) {
                    worker.tryToInjectStartupParamsAndPushWorker();
                }
            } else {
                worker = a;
                worker.setWorkerId(workerId);
                a = null;
            }
            b.put(workerId, worker);
        } catch (Throwable e) {
            H5Log.d("WorkerManager", "registerWorker...e=" + e);
            WebWorkerUtils.workerErrorLogMonitor(e.getMessage());
        }
        return worker;
    }

    public static WebWorker getWorker(String workerId) {
        if (b != null) {
            return b.get(workerId);
        }
        return null;
    }

    public static void sendPushMessage(HashMap<String, String> hashMap, H5CallBack h5CallBack) {
        if (hashMap != null) {
            final String workerId = hashMap.get("appId");
            if (TextUtils.isEmpty(workerId)) {
                H5Log.e((String) "WorkerManager", (String) "sendPushMessage error, worker id is invalid");
                a(hashMap, h5CallBack);
                return;
            }
            final WebWorker worker = getWorker(workerId);
            if (worker == null) {
                H5Log.e((String) "WorkerManager", (String) "sendPushMessage error, worker is null");
                a(hashMap, h5CallBack);
                return;
            }
            JSONObject data = JSON.parseObject(hashMap.get("message"));
            final JSONObject jo = new JSONObject();
            jo.put((String) "handlerName", (Object) "push");
            jo.put((String) "data", (Object) data);
            if (worker.isWorkerReady()) {
                worker.sendMessageToWorker(workerId, hashMap.get("messageId"), jo.toJSONString(), h5CallBack);
                return;
            }
            final HashMap<String, String> hashMap2 = hashMap;
            final H5CallBack h5CallBack2 = h5CallBack;
            worker.registerWorkerReadyListener(new WorkerReadyListener() {
                public final void onWorkerReady() {
                    worker.sendMessageToWorker(workerId, (String) hashMap2.get("messageId"), jo.toJSONString(), h5CallBack2);
                }
            });
            return;
        }
        a(hashMap, h5CallBack);
    }

    private static void a(HashMap<String, String> data, H5CallBack h5CallBack) {
        if (h5CallBack != null && data != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "appId", (Object) data.get("appId"));
            jsonObject.put((String) "result", (Object) "2");
            jsonObject.put((String) "messageId", (Object) data.get("messageId"));
            h5CallBack.onCallBack(jsonObject);
        }
    }

    public static void onPageParamReady(Bundle params) {
        if ("prestart".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_workerType"))) {
            Log.d("WorkerManager", "onPageParamReady");
            sParams = params;
            preStartWorker(H5Utils.getString(params, (String) "appId"), params);
        }
    }
}
