package com.alipay.mobile.worker.multiworker;

import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.android.AndroidWebView;
import java.util.Iterator;
import java.util.LinkedList;

public class MultiJsWorker {
    public static final String MULTI_WORKER_READY = "MultiWorkerReady";
    private String a;
    private String b;
    /* access modifiers changed from: private */
    public APWebView c;
    private boolean d = false;
    private LinkedList<MultiWorkerReadyListener> e;
    private boolean f;
    private MultiJsWorkerMessageTransponder g;

    public interface MultiWorkerReadyListener {
        void onMultiWorkerReady();
    }

    public MultiJsWorker(String workerUrl, String workerId) {
        this.a = workerUrl;
        this.b = workerId;
        a();
    }

    private void a() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    MultiJsWorker.this.b();
                }
            });
        } else {
            b();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        long time = System.currentTimeMillis();
        c();
        this.c.setWebViewClient(new MultiJsWorkerWebViewClient(this));
        this.c.setWebChromeClient(new MultiJsWorkerWebChromeClient(this));
        this.g = new MultiJsWorkerMessageTransponder(this);
        if (this.f) {
            this.c.loadUrl("javascript:window.worker = new Worker(\"" + this.a + "\");\nconsole.log(\"MultiWorkerReady\");");
        } else {
            this.c.loadUrl("https://alipay.worker.com/create_worker");
        }
        H5Log.d("MultiJsWorker", "doInitWorker cost = " + (System.currentTimeMillis() - time));
    }

    private void c() {
        try {
            if (H5Flag.ucReady) {
                this.c = H5ServiceUtils.getUcService().createWebView(H5Utils.getContext(), true);
                this.f = true;
                if (this.c == null) {
                    this.c = new AndroidWebView(H5Utils.getContext());
                    this.f = false;
                }
            } else {
                this.c = new AndroidWebView(H5Utils.getContext());
                this.f = false;
            }
        } catch (Throwable e2) {
            this.c = new AndroidWebView(H5Utils.getContext());
            this.f = false;
            H5Log.d("MultiJsWorker", "createWebView...e=" + e2);
        }
        this.c.setWebContentsDebuggingEnabled(H5Utils.isDebug());
        this.c.getSettings().setJavaScriptEnabled(true);
    }

    public void setMultiWorkerReady() {
        H5Log.d("MultiJsWorker", "setMultiWorkerReady...");
        this.d = true;
        if (this.e != null) {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                ((MultiWorkerReadyListener) it.next()).onMultiWorkerReady();
            }
        }
    }

    private void a(MultiWorkerReadyListener listener) {
        if (this.e == null) {
            this.e = new LinkedList<>();
        }
        if (!this.e.contains(listener)) {
            this.e.add(listener);
        }
    }

    /* access modifiers changed from: private */
    public static String b(String message) {
        return "javascript:window.worker.postMessage('" + message + "')";
    }

    public void sendToWorker(final String message) {
        H5Log.d("MultiJsWorker", "sendToWorker...workerReady=" + this.d);
        if (this.d) {
            c(b(message));
        } else {
            a((MultiWorkerReadyListener) new MultiWorkerReadyListener() {
                public void onMultiWorkerReady() {
                    MultiJsWorker.this.c(MultiJsWorker.b(message));
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void c(final String message) {
        if (TextUtils.isEmpty(message)) {
            H5Log.d("MultiJsWorker", "sendToWorker..message is null");
        } else if (this.f || Looper.myLooper() == Looper.getMainLooper()) {
            try {
                this.c.loadUrl(message);
            } catch (Throwable thr) {
                H5Log.e("MultiJsWorker", "sendToWorker error:", thr);
            }
        } else {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        MultiJsWorker.this.c.loadUrl(message);
                    } catch (Throwable thr) {
                        H5Log.e("MultiJsWorker", "sendToWorker.error...", thr);
                    }
                }
            });
        }
    }

    public String getWorkerUrl() {
        return this.a;
    }

    public String getWorkerId() {
        return this.b;
    }

    public MultiJsWorkerMessageTransponder getMultiJsWorkerMessageTransponder() {
        return this.g;
    }

    public void onRelease() {
        if (this.c != null) {
            c("javascript:window.worker.terminate();");
            this.c.setWebChromeClient(null);
            this.c.setWebViewClient(null);
            this.c.freeMemory();
            this.c.destroy();
            this.c = null;
        }
    }
}
