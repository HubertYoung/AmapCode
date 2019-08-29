package com.alipay.android.phone.inside.offlinecode.engine.webkit;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alipay.android.phone.inside.common.util.StringUtils;
import com.alipay.android.phone.inside.offlinecode.engine.DPEMethodRouter;
import com.alipay.android.phone.inside.offlinecode.engine.DPETask;
import com.alipay.android.phone.inside.offlinecode.engine.DPETask.TimeOutListener;
import com.alipay.android.phone.inside.offlinecode.engine.DynamicCodePlugin;
import com.alipay.android.phone.inside.offlinecode.engine.IJSEngine;
import com.alipay.android.phone.inside.offlinecode.engine.JSEngineCallback;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public class WebkitJSEngine implements IJSEngine {
    private static final String BRIDGECODE = "function callNative(method, param) {\n    if (!method || typeof(method) != \"string\" || method.length == 0) { console.log(\"method invalid\");  return null; }\n    if (!param) { param = {}; }\n    if (typeof(param) != 'object') { console.log(\"param type invalid\"); return null; }\n    paramStr = JSON.stringify(param);\n    var result = dpe_method_router.methodRouter(method, paramStr);\n    return result;\n};";
    private static final String CALLCODE = "try {\n    var param = JSON.parse( %s );var result = getCode(param);\n    if (result) { %s.returnResultToJava(result.toString());} else { %s.returnErrorToJava(\"getCode return null\");}\n} catch (error) {%s.returnErrorToJava(\"getCode got error\" + error.message) ;}";
    private static final String ENGINE_CALLBACK_NAMESPACE = "DPE";
    private static final int ENGINE_MODE_LOAD_URL = 2;
    private static final int ENGINE_MODE_NORMAL = 1;
    private JSEngineCallback mCallback = null;
    /* access modifiers changed from: private */
    public DPETask mCurrentTask;
    private int mCurrentTaskId;
    private int mEngineMode = 1;
    private JSReturnCallback mJsCallback;
    private DPEMethodRouter mMethodRouter;
    private AtomicBoolean mPrepared = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public WebView mWebview;

    public void init(Context context) {
        this.mPrepared.set(false);
        if (VERSION.SDK_INT >= 19) {
            this.mEngineMode = 1;
        } else {
            this.mEngineMode = 2;
        }
        this.mMethodRouter = new DPEMethodRouter();
        initWebView(context);
    }

    public boolean hasPrepared() {
        return this.mPrepared.get();
    }

    public void destroy() {
        if (this.mWebview != null) {
            this.mWebview.removeJavascriptInterface(ENGINE_CALLBACK_NAMESPACE);
            this.mWebview.loadUrl("about:blank");
            this.mWebview.stopLoading();
            if (VERSION.SDK_INT < 19) {
                this.mWebview.freeMemory();
            }
            this.mWebview.clearHistory();
            this.mWebview.removeAllViews();
            this.mWebview.destroyDrawingCache();
            this.mWebview.destroy();
            this.mWebview = null;
        }
    }

    public void callJSMethod(String str, JSONObject jSONObject, JSEngineCallback jSEngineCallback) {
        if (jSEngineCallback == null || StringUtils.b(str)) {
            throw new InvalidParameterException();
        } else if (this.mCurrentTask != null) {
            throw new IllegalStateException("current engine has bee consumed.");
        } else {
            DPETask dPETask = new DPETask(this.mWebview);
            dPETask.script = str;
            dPETask.params = jSONObject;
            dPETask.callback = jSEngineCallback;
            callJSMethodByTask(dPETask);
        }
    }

    public void registerPlugin(DynamicCodePlugin dynamicCodePlugin) {
        if (this.mMethodRouter != null) {
            this.mMethodRouter.registerPlugin(dynamicCodePlugin);
        }
    }

    private void runTasks(DPETask dPETask, long j) {
        try {
            this.mCurrentTask = dPETask;
            this.mCurrentTask.taskId = this.mCurrentTaskId;
            this.mCurrentTaskId++;
            this.mCurrentTask.setTimeOut(new TimeOutListener() {
                public void onTimeOut() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            WebkitJSEngine.this.resetStatus();
                            WebkitJSEngine.this.onError("timeout", WebkitJSEngine.this.mCurrentTask);
                        }
                    });
                }
            }, j);
            if (this.mEngineMode == 2) {
                callJSMethodByLoadUrl(this.mCurrentTask);
            } else {
                callJSMethodByEvaluate(this.mCurrentTask);
            }
        } catch (Exception unused) {
        }
    }

    private void initWebView(Context context) {
        destroy();
        this.mWebview = new WebView(context);
        WebSettings settings = this.mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        this.mWebview.setWillNotDraw(true);
        this.mJsCallback = new JSReturnCallback(new JSResultInterface() {
            public void javascriptOnReturn(String str) {
                WebkitJSEngine.this.onCompletion(str, WebkitJSEngine.this.mCurrentTask);
            }

            public void javascriptOnError(String str) {
                WebkitJSEngine.this.onError(str, WebkitJSEngine.this.mCurrentTask);
            }
        });
        this.mWebview.addJavascriptInterface(this.mJsCallback, ENGINE_CALLBACK_NAMESPACE);
        this.mWebview.addJavascriptInterface(this.mMethodRouter, DPEMethodRouter.NAMESPACE_ROOTER);
        this.mPrepared.set(true);
    }

    private static String buildLoadUrlJsCode(String str, JSONObject jSONObject) {
        String format = String.format(CALLCODE, new Object[]{JSONObject.quote(jSONObject.toString()), ENGINE_CALLBACK_NAMESPACE, ENGINE_CALLBACK_NAMESPACE, ENGINE_CALLBACK_NAMESPACE});
        StringBuilder sb = new StringBuilder("function callNative(method, param) {\n    if (!method || typeof(method) != \"string\" || method.length == 0) { console.log(\"method invalid\");  return null; }\n    if (!param) { param = {}; }\n    if (typeof(param) != 'object') { console.log(\"param type invalid\"); return null; }\n    paramStr = JSON.stringify(param);\n    var result = dpe_method_router.methodRouter(method, paramStr);\n    return result;\n};;");
        sb.append(str);
        sb.append(";");
        sb.append(format);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public void onError(String str, DPETask dPETask) {
        if (dPETask != null) {
            synchronized (dPETask) {
                if (dPETask.status != 3) {
                    dPETask.done();
                    dPETask.callback.onError(new Throwable(str));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void onCompletion(String str, DPETask dPETask) {
        if (dPETask != null) {
            synchronized (dPETask) {
                if (dPETask.status != 3) {
                    dPETask.done();
                    dPETask.callback.onComplete(str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void resetStatus() {
        if (this.mWebview != null) {
            this.mWebview.loadUrl("about:blank");
        }
    }

    private void callJSMethodByLoadUrl(DPETask dPETask) {
        final String buildLoadUrlJsCode = buildLoadUrlJsCode(dPETask.script, dPETask.params);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    StringBuilder sb = new StringBuilder("<script>");
                    sb.append(buildLoadUrlJsCode);
                    sb.append("</script>");
                    WebkitJSEngine.this.mWebview.loadUrl("data:text/html;charset=utf-8;base64,".concat(String.valueOf(Base64.encodeToString(sb.toString().getBytes("UTF-8"), 2))));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    WebkitJSEngine.this.onError("callJSMethodByLoadUrl got exception.", null);
                }
            }
        });
    }

    private void callJSMethodByTask(DPETask dPETask) {
        runTasks(dPETask, 20000);
    }

    private void callJSMethodByEvaluate(final DPETask dPETask) {
        final String buildLoadUrlJsCode = buildLoadUrlJsCode(dPETask.script, dPETask.params);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @RequiresApi(api = 19)
            public void run() {
                WebkitJSEngine.this.resetStatus();
                WebkitJSEngine.this.mWebview.evaluateJavascript(buildLoadUrlJsCode, new ValueCallback<String>() {
                    public void onReceiveValue(String str) {
                        WebkitJSEngine.this.onError("unexcepted error", dPETask);
                    }
                });
            }
        });
    }
}
