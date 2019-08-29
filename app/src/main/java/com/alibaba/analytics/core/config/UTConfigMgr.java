package com.alibaba.analytics.core.config;

import android.content.Context;
import android.content.Intent;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.utils.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UTConfigMgr {
    static final String INTENT_CONFIG_CHANGE = "com.alibaba.analytics.config.change";
    static final String INTENT_EXTRA_KEY = "key";
    static final String INTENT_EXTRA_VALUE = "value";
    private static final String TAG = "UTConfigMgr";
    private static Map<String, String> configMap = new HashMap();

    public static synchronized void postServerConfig(String str, String str2) {
        synchronized (UTConfigMgr.class) {
            try {
                Context context = Variables.getInstance().getContext();
                if (context == null) {
                    context = ClientVariables.getInstance().getContext();
                }
                if (context != null) {
                    configMap.put(str, str2);
                    String packageName = context.getPackageName();
                    Logger.d((String) TAG, "postServerConfig packageName", packageName, "key", str, "value", str2);
                    Intent intent = new Intent(INTENT_CONFIG_CHANGE);
                    intent.setPackage(packageName);
                    intent.putExtra("key", str);
                    intent.putExtra("value", str2);
                    context.sendBroadcast(intent);
                }
            } catch (Throwable th) {
                Logger.e(TAG, th, new Object[0]);
            }
        }
    }

    public static synchronized void postAllServerConfig() {
        synchronized (UTConfigMgr.class) {
            for (Entry next : configMap.entrySet()) {
                postServerConfig((String) next.getKey(), (String) next.getValue());
            }
        }
    }
}
