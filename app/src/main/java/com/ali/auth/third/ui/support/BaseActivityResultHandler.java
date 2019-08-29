package com.ali.auth.third.ui.support;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.ui.webview.BaseWebViewActivity;
import java.util.Map;

public abstract class BaseActivityResultHandler implements ActivityResultHandler {
    public void onActivityResult(int i, int i2, int i3, Intent intent, Activity activity, Map<Class<?>, Object> map, WebView webView) {
        if (i == 1) {
            onCallbackContext(i2, i3, intent, activity, map, webView);
        } else if (i != 2) {
            SDKLogger.e("system", "Unrecognized source ".concat(String.valueOf(i)));
        } else if (activity instanceof BaseWebViewActivity) {
            onTaeSDKActivity(i2, i3, intent, (BaseWebViewActivity) activity, map, webView);
        } else {
            StringBuilder sb = new StringBuilder("OnActivityResult is invoked from unsupported activity type ");
            sb.append(activity.getClass().getName());
            SDKLogger.e("system", sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onCallbackContext(int i, int i2, Intent intent, Activity activity, Map<Class<?>, Object> map, WebView webView);

    /* access modifiers changed from: protected */
    public abstract void onTaeSDKActivity(int i, int i2, Intent intent, BaseWebViewActivity baseWebViewActivity, Map<Class<?>, Object> map, WebView webView);
}
