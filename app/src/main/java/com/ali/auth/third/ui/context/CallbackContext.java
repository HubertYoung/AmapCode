package com.ali.auth.third.ui.context;

import android.app.Activity;
import android.content.Intent;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.ui.support.ActivityResultHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class CallbackContext {
    private static final String a = "CallbackContext";
    public static volatile WeakReference<Activity> activity;
    public static volatile Object loginCallback;
    public static LoginCallback mGlobalLoginCallback;

    public static boolean onActivityResult(int i, int i2, Intent intent) {
        return onActivityResult(activity != null ? (Activity) activity.get() : null, i, i2, intent);
    }

    public static boolean onActivityResult(Activity activity2, int i, int i2, Intent intent) {
        String str = a;
        StringBuilder sb = new StringBuilder("onActivityResult requestCode=");
        sb.append(i);
        sb.append(" resultCode = ");
        sb.append(i2);
        sb.append(" authCode = ");
        sb.append(intent == null ? "" : intent.getStringExtra("result"));
        SDKLogger.d(str, sb.toString());
        HashMap hashMap = new HashMap();
        hashMap.put(ActivityResultHandler.REQUEST_CODE_KEY, String.valueOf(i));
        ActivityResultHandler activityResultHandler = (ActivityResultHandler) KernelContext.getService(ActivityResultHandler.class, hashMap);
        if (activityResultHandler == null) {
            SDKLogger.i(a, "No ActivityResultHandler handler to support the request code ".concat(String.valueOf(i)));
            return false;
        } else if (activity2 == null) {
            SDKLogger.e(a, "No active activity is set, ignore invoke ".concat(String.valueOf(activityResultHandler)));
            return false;
        } else {
            activityResultHandler.onActivityResult(1, i, i2, intent, activity2, null, null);
            return true;
        }
    }

    public static void setActivity(Activity activity2) {
        activity = new WeakReference<>(activity2);
    }
}
