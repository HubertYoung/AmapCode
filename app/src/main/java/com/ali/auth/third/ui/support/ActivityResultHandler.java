package com.ali.auth.third.ui.support;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import java.util.Map;

public interface ActivityResultHandler {
    public static final int CALLBACK_CONTEXT = 1;
    public static final String REQUEST_CODE_KEY = "requestCodeKey";
    public static final int TAE_SDK_ACTIVITY = 2;

    void onActivityResult(int i, int i2, int i3, Intent intent, Activity activity, Map<Class<?>, Object> map, WebView webView);
}
