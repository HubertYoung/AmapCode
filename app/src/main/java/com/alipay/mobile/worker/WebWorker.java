package com.alipay.mobile.worker;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.android.AndroidWebView;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.Iterator;
import java.util.LinkedList;

public class WebWorker {
    protected H5WorkerControllerProvider a;
    Handler b;
    private final String c = "WebWorker";
    private Bundle d;
    private String e;
    /* access modifiers changed from: private */
    public APWebView f;
    private boolean g = false;
    private boolean h = false;
    private LinkedList<WorkerReadyListener> i;
    private boolean j = false;
    private boolean k = false;
    private boolean l;

    public interface WorkerReadyListener {
        void onWorkerReady();
    }

    public WebWorker() {
        HandlerThread ht = new HandlerThread("worker-jsapi");
        ht.start();
        this.b = new Handler(ht.getLooper());
        e();
    }

    private void e() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    WebWorker.this.a();
                }
            });
        } else {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
        long time = System.currentTimeMillis();
        f();
        this.a = new H5WorkerControllerProvider(this);
        this.f.setWebViewClient(new WorkerWebViewClient(this));
        this.f.setWebChromeClient(new WorkerWebChromeClient(this));
        H5Log.d("WebWorker", "start loading worker js bridge");
        this.f.loadUrl("https://alipay.worker.com/worker");
        H5Log.d("WebWorker", "doInitWorker cost = " + (System.currentTimeMillis() - time));
    }

    public H5WorkerControllerProvider getWorkerControllerProvider() {
        return this.a;
    }

    public void setWorkerId(String workerId) {
        this.e = workerId;
    }

    public String getWorkerId() {
        return this.e;
    }

    public APWebView getWebView() {
        return this.f;
    }

    public void setStartupParams(Bundle params) {
        this.d = params;
    }

    public boolean isWorkerReady() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public final void b() {
        H5Log.d("WebWorker", "setWorkerReady");
        this.g = true;
        if (this.g && this.i != null) {
            Iterator it = this.i.iterator();
            while (it.hasNext()) {
                ((WorkerReadyListener) it.next()).onWorkerReady();
            }
            this.i.clear();
        }
    }

    public void registerWorkerReadyListener(WorkerReadyListener listener) {
        if (this.i == null) {
            this.i = new LinkedList<>();
        }
        if (listener != null && !this.i.contains(listener)) {
            this.i.add(listener);
        }
    }

    public void onAlipayJSBridgeReady() {
        H5Log.d("WebWorker", "onAlipayJSBridgeReady");
        this.h = true;
        tryToInjectStartupParamsAndPushWorker();
    }

    public void tryToInjectStartupParamsAndPushWorker() {
        if (!this.j && this.d != null && this.h && this.e != null) {
            this.j = true;
            if (Looper.myLooper() != Looper.getMainLooper() || !"yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_postWebOnUi"))) {
                d();
            } else {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        WebWorker.this.d();
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public final String c() {
        StringBuilder injectParams = new StringBuilder();
        if (H5Utils.isDebug()) {
            this.d.putString("debug", "framework");
        }
        injectParams.append("javascript:__appxStartupParams=").append(H5Utils.toJSONObject(this.d).toJSONString()).append(";var scriptData = \"self.__appxStartupParams=\" + JSON.stringify(__appxStartupParams);scriptData += \";console.log('worker success set __appxStartupParams ' + self.__appxStartupParams)\";").append("scriptData += \";importScripts('" + this.e + "')\";").append("scriptData += \";console.log('index.worker.js.end')\";window.worker.postMessage({\"action\": \"exec\", \"data\": scriptData});");
        return injectParams.toString();
    }

    /* access modifiers changed from: protected */
    public void d() {
        long time = System.currentTimeMillis();
        try {
            this.f.loadUrl(c());
        } catch (Throwable thr) {
            H5Log.e("WebWorker", "tryToInjectStartupParams error!", thr);
        }
        H5Log.d("WebWorker", "doInjectStartupParamsAndPushWorker cost = " + (System.currentTimeMillis() - time));
        b();
    }

    public void sendMessageToWorker(String appId, String messageId, String message) {
        sendMessageToWorker(appId, messageId, message, null);
    }

    public void sendMessageToWorker(String appId, String messageId, String message, H5CallBack h5CallBack) {
        sendToWorker(appId, messageId, "callBridge", message, h5CallBack);
    }

    protected static String a(String action, String message) {
        return "javascript:window.worker.postMessage({\"action\": \"" + action + "\", \"data\":" + message + "})";
    }

    public void sendToWorker(String appId, String messageId, String action, String message, H5CallBack h5CallBack) {
        if (this.k || Looper.myLooper() == Looper.getMainLooper()) {
            try {
                this.f.loadUrl(a(action, message));
                a(appId, messageId, h5CallBack);
            } catch (Throwable thr) {
                H5Log.e("WebWorker", "sendToWorker error:", thr);
            }
        } else {
            final String str = action;
            final String str2 = message;
            final String str3 = appId;
            final String str4 = messageId;
            final H5CallBack h5CallBack2 = h5CallBack;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        WebWorker.this.f.loadUrl(WebWorker.a(str, str2));
                        WebWorker.this.a(str3, str4, h5CallBack2);
                    } catch (Throwable thr) {
                        H5Log.e("WebWorker", "sendToWorker.error...", thr);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String appId, String messageId, H5CallBack h5CallBack) {
        if (h5CallBack != null) {
            JSONObject jsonObject = new JSONObject();
            if (TextUtils.isEmpty(appId)) {
                appId = this.e;
            }
            jsonObject.put((String) "appId", (Object) appId);
            jsonObject.put((String) "result", (Object) "0");
            if (TextUtils.isEmpty(messageId)) {
                messageId = System.currentTimeMillis();
            }
            jsonObject.put((String) "messageId", (Object) messageId);
            H5Log.d("WebWorker", "sendPushCallBack..." + jsonObject.toJSONString());
            h5CallBack.onCallBack(jsonObject);
        }
    }

    public void preStart(String workerId, Bundle params) {
        if (!this.l) {
            this.l = true;
            setWorkerId(workerId);
            setStartupParams(params);
            tryToInjectStartupParamsAndPushWorker();
        }
    }

    private void f() {
        try {
            if (H5Flag.ucReady) {
                this.f = H5ServiceUtils.getUcService().createWebView(H5Utils.getContext(), true);
                this.k = true;
                if (this.f == null) {
                    this.f = new AndroidWebView(H5Utils.getContext());
                    this.k = false;
                }
            } else {
                this.f = new AndroidWebView(H5Utils.getContext());
                this.k = false;
            }
        } catch (Throwable e2) {
            this.f = new AndroidWebView(H5Utils.getContext());
            this.k = false;
            H5Log.d("WebWorker", "createWebView...e=" + e2);
        }
        this.f.setWebContentsDebuggingEnabled(H5Utils.isDebug());
        this.f.getSettings().setJavaScriptEnabled(true);
    }
}
