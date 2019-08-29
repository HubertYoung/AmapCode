package com.alibaba.baichuan.android.jsbridge;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.baichuan.android.jsbridge.AlibcJsBridgeService.AlibcJSAPIAuthCheck;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcPluginEntryManager;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class AlibcJsBridge implements Callback {
    public static final int CALL_EXECUTE = 0;
    public static final int CLOSED = 4;
    public static final int NO_METHOD = 2;
    public static final int NO_PERMISSION = 3;
    private static AlibcJsBridge a;
    private static Handler b;
    private boolean c = true;
    private boolean d;
    private boolean e = false;

    private AlibcJsBridge() {
        b = new Handler(Looper.getMainLooper(), this);
    }

    private AlibcJsCallMethodContext a(String str) {
        if (str == null || !str.startsWith("bchybrid://")) {
            return null;
        }
        try {
            AlibcJsCallMethodContext alibcJsCallMethodContext = new AlibcJsCallMethodContext();
            int indexOf = str.indexOf(58, 11);
            alibcJsCallMethodContext.objectName = str.substring(11, indexOf);
            int indexOf2 = str.indexOf(47, indexOf);
            alibcJsCallMethodContext.token = str.substring(indexOf + 1, indexOf2);
            int indexOf3 = str.indexOf(63, indexOf2);
            if (indexOf3 > 0) {
                alibcJsCallMethodContext.methodName = str.substring(indexOf2 + 1, indexOf3);
                alibcJsCallMethodContext.params = str.substring(indexOf3 + 1);
            } else {
                alibcJsCallMethodContext.methodName = str.substring(indexOf2 + 1);
            }
            if (alibcJsCallMethodContext.objectName.length() <= 0 || alibcJsCallMethodContext.token.length() <= 0 || alibcJsCallMethodContext.methodName.length() <= 0) {
                return null;
            }
            return alibcJsCallMethodContext;
        } catch (StringIndexOutOfBoundsException unused) {
        }
    }

    /* access modifiers changed from: private */
    public void a(AlibcJsCallMethodContext alibcJsCallMethodContext, String str) {
        AlibcLogger.d("AlibcJsBridge", String.format("callMethod-obj:%s method:%s param:%s sid:%s", new Object[]{alibcJsCallMethodContext.objectName, alibcJsCallMethodContext.methodName, alibcJsCallMethodContext.params, alibcJsCallMethodContext.token}));
        if (!this.c || alibcJsCallMethodContext.webview == null) {
            AlibcLogger.w("AlibcJsBridge", "jsbridge is closed.");
            startCall(4, alibcJsCallMethodContext);
            return;
        }
        if (!this.d && AlibcJsBridgeService.getJSBridgePreprocessors() != null && !AlibcJsBridgeService.getJSBridgePreprocessors().isEmpty()) {
            for (AlibcJSAPIAuthCheck apiAuthCheck : AlibcJsBridgeService.getJSBridgePreprocessors()) {
                if (!apiAuthCheck.apiAuthCheck(str, alibcJsCallMethodContext.objectName, alibcJsCallMethodContext.methodName, alibcJsCallMethodContext.params)) {
                    AlibcLogger.w("AlibcJsBridge", "preprocessor call fail, callMethod cancel.");
                    startCall(3, alibcJsCallMethodContext);
                    return;
                }
            }
        }
        aftercallMethod(alibcJsCallMethodContext, str);
    }

    public static void aftercallMethod(AlibcJsCallMethodContext alibcJsCallMethodContext, String str) {
        int i;
        Object jsObject = alibcJsCallMethodContext.webview.getJsObject(alibcJsCallMethodContext.objectName);
        if (jsObject == null || !(jsObject instanceof AlibcApiPlugin)) {
            StringBuilder sb = new StringBuilder("callMethod: Plugin ");
            sb.append(alibcJsCallMethodContext.objectName);
            sb.append(" didn't found, you should call WVPluginManager.registerPlugin first.");
            AlibcLogger.w("AlibcJsBridge", sb.toString());
            i = 2;
        } else {
            AlibcLogger.i("AlibcJsBridge", "call new method execute.");
            alibcJsCallMethodContext.classinstance = jsObject;
            i = 0;
        }
        startCall(i, alibcJsCallMethodContext);
    }

    public static synchronized AlibcJsBridge getInstance() {
        AlibcJsBridge alibcJsBridge;
        synchronized (AlibcJsBridge.class) {
            if (a == null) {
                a = new AlibcJsBridge();
            }
            alibcJsBridge = a;
        }
        return alibcJsBridge;
    }

    public static void startCall(int i, AlibcJsCallMethodContext alibcJsCallMethodContext) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = alibcJsCallMethodContext;
        b.sendMessage(obtain);
    }

    public void callMethod(AlibcWebview alibcWebview, String str) {
        AlibcLogger.d("AlibcJsBridge", "callMethod: url=".concat(String.valueOf(str)));
        if (!this.e) {
            AlibcLogger.w("AlibcJsBridge", "jsbridge is not init.");
            return;
        }
        AlibcJsCallMethodContext a2 = a(str);
        if (a2 == null) {
            AlibcLogger.w("AlibcJsBridge", "url format error and call canceled. url=".concat(String.valueOf(str)));
            return;
        }
        a2.webview = alibcWebview;
        new a(this, a2, alibcWebview.getWebView().getUrl()).execute(new Void[0]);
    }

    public void destroy() {
        this.e = false;
    }

    public void exCallMethod(AlibcPluginEntryManager alibcPluginEntryManager, AlibcJsCallMethodContext alibcJsCallMethodContext) {
        if (alibcJsCallMethodContext != null && alibcJsCallMethodContext.objectName != null) {
            alibcJsCallMethodContext.classinstance = alibcPluginEntryManager.getEntry(alibcJsCallMethodContext.objectName);
            if (alibcJsCallMethodContext.classinstance instanceof AlibcApiPlugin) {
                AlibcLogger.i("AlibcJsBridge", "call new method execute.");
                startCall(0, alibcJsCallMethodContext);
            }
        }
    }

    public boolean handleMessage(Message message) {
        AlibcJsResult alibcJsResult;
        AlibcJsCallMethodContext alibcJsCallMethodContext = (AlibcJsCallMethodContext) message.obj;
        if (alibcJsCallMethodContext == null) {
            AlibcLogger.e("AlibcJsBridge", "CallMethodContext is null, and do nothing.");
            return false;
        }
        AlibcJsCallbackContext alibcJsCallbackContext = new AlibcJsCallbackContext(alibcJsCallMethodContext.webview, alibcJsCallMethodContext.token, alibcJsCallMethodContext.objectName, alibcJsCallMethodContext.methodName);
        int i = message.what;
        if (i != 0) {
            switch (i) {
                case 2:
                    alibcJsResult = AlibcJsResult.RET_NO_METHOD;
                    break;
                case 3:
                    alibcJsResult = AlibcJsResult.RET_NO_PERMISSION;
                    break;
                case 4:
                    alibcJsResult = AlibcJsResult.RET_CLOSED;
                    break;
                default:
                    return false;
            }
            alibcJsCallbackContext.error(alibcJsResult);
            return true;
        }
        if (!((AlibcApiPlugin) alibcJsCallMethodContext.classinstance).execute(alibcJsCallMethodContext.methodName, TextUtils.isEmpty(alibcJsCallMethodContext.params) ? bny.c : alibcJsCallMethodContext.params, alibcJsCallbackContext)) {
            StringBuilder sb = new StringBuilder("AlibcApiPlugin execute failed. method: ");
            sb.append(alibcJsCallMethodContext.methodName);
            AlibcLogger.w("AlibcJsBridge", sb.toString());
            startCall(2, alibcJsCallMethodContext);
        }
        return true;
    }

    public synchronized void init() {
        this.e = true;
    }

    public void setEnabled(boolean z) {
        this.c = z;
    }

    public void skipPreprocess() {
        this.d = true;
    }
}
