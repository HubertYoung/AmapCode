package com.alibaba.sdk.want;

import com.alibaba.sdk.trade.container.AlibcContainer;
import com.alibaba.sdk.trade.container.AlibcContainerEventListener;
import com.alibaba.sdk.trade.container.AlibcContainerEventManager;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;

public class AlibcWantEnv {
    private static final String TAG = "AlibcWantEnv";
    private static AlibcContainerEventListener mWantEventListener;

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void prepare(android.content.Context r2) {
        /*
            java.lang.Class<com.alibaba.sdk.want.AlibcWantEnv> r0 = com.alibaba.sdk.want.AlibcWantEnv.class
            monitor-enter(r0)
            if (r2 != 0) goto L_0x0010
            java.lang.String r2 = "init_error"
            java.lang.String r1 = "context is null"
            com.alibaba.sdk.want.AlibcWantEventDispatch.onEvent(r2, r1)     // Catch:{ all -> 0x000e }
            monitor-exit(r0)
            return
        L_0x000e:
            r2 = move-exception
            goto L_0x0023
        L_0x0010:
            com.alibaba.sdk.trade.container.AlibcContainerEventListener r1 = mWantEventListener     // Catch:{ all -> 0x000e }
            if (r1 != 0) goto L_0x0021
            com.alibaba.sdk.want.AlibcWantEnv$1 r1 = new com.alibaba.sdk.want.AlibcWantEnv$1     // Catch:{ all -> 0x000e }
            r1.<init>()     // Catch:{ all -> 0x000e }
            mWantEventListener = r1     // Catch:{ all -> 0x000e }
            com.alibaba.sdk.trade.container.AlibcContainerEventManager.registListener(r1)     // Catch:{ all -> 0x000e }
            com.alibaba.sdk.trade.container.AlibcContainer.init(r2)     // Catch:{ all -> 0x000e }
        L_0x0021:
            monitor-exit(r0)
            return
        L_0x0023:
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.want.AlibcWantEnv.prepare(android.content.Context):void");
    }

    public static synchronized void destroy() {
        synchronized (AlibcWantEnv.class) {
            AlibcComponentLog.d(TAG, "destroy");
            AlibcContainer.destroy();
            if (mWantEventListener != null) {
                AlibcContainerEventManager.unregistListener(mWantEventListener);
            }
            mWantEventListener = null;
        }
    }
}
