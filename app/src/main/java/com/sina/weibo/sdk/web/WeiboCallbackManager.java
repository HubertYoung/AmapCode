package com.sina.weibo.sdk.web;

import android.text.TextUtils;
import com.sina.weibo.sdk.auth.WbAuthListener;
import java.util.HashMap;
import java.util.Map;

public class WeiboCallbackManager {
    private static WeiboCallbackManager sInstance;
    private Map<String, WbAuthListener> mWeiboAuthListenerMap = new HashMap();

    private WeiboCallbackManager() {
    }

    public static synchronized WeiboCallbackManager getInstance() {
        WeiboCallbackManager weiboCallbackManager;
        synchronized (WeiboCallbackManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new WeiboCallbackManager();
                }
                weiboCallbackManager = sInstance;
            }
        }
        return weiboCallbackManager;
    }

    public synchronized WbAuthListener getWeiboAuthListener(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mWeiboAuthListenerMap.get(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setWeiboAuthListener(java.lang.String r2, com.sina.weibo.sdk.auth.WbAuthListener r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0013 }
            if (r0 != 0) goto L_0x0011
            if (r3 != 0) goto L_0x000a
            goto L_0x0011
        L_0x000a:
            java.util.Map<java.lang.String, com.sina.weibo.sdk.auth.WbAuthListener> r0 = r1.mWeiboAuthListenerMap     // Catch:{ all -> 0x0013 }
            r0.put(r2, r3)     // Catch:{ all -> 0x0013 }
            monitor-exit(r1)
            return
        L_0x0011:
            monitor-exit(r1)
            return
        L_0x0013:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.web.WeiboCallbackManager.setWeiboAuthListener(java.lang.String, com.sina.weibo.sdk.auth.WbAuthListener):void");
    }

    public synchronized void removeWeiboAuthListener(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mWeiboAuthListenerMap.remove(str);
        }
    }

    public String genCallbackKey() {
        return String.valueOf(System.currentTimeMillis());
    }
}
