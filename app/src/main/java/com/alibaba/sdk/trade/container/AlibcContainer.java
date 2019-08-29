package com.alibaba.sdk.trade.container;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.sdk.trade.component.AlibcComponentManager;
import com.alibaba.sdk.trade.container.auth.AlibcContainerAuth;
import com.alibaba.sdk.trade.container.auth.AlibcContainerHintManager;
import com.alibaba.sdk.trade.container.license.AlibcContainerLicenseListener;
import com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlibcContainer {
    private static Map<String, AlibcBaseComponent> mWantContainer2Id = new ConcurrentHashMap();
    private static Map<Integer, String> mWantContainer2Type = new ConcurrentHashMap();

    static class ContainerLicenseListener implements AlibcContainerLicenseListener {
        ContainerLicenseListener() {
        }

        public void updataLicense() {
            AlibcComponentManager.updataPlugin();
        }
    }

    public static synchronized void init(Context context) {
        synchronized (AlibcContainer.class) {
            try {
                AlibcTradeSDK.asyncInit(context, new AlibcTradeInitCallback() {
                    public final void onSuccess() {
                        AlibcLogger.d("alibc", "初始化成功");
                        AlibcContainerLicenseManager.initLicense(new ContainerLicenseListener());
                        AlibcComponentManager.initPlugin();
                        AlibcContainerHintManager.init();
                        AlibcContainerEventManager.sendEvent(2, "");
                        AlibcComponentTrack.sendUseabilitySuccess("BCPCSDK", AlibcComponentTrack.MONITOR_POINT_COMPONENT_INIT);
                    }

                    public final void onFailure(int i, String str) {
                        AlibcLogger.d("alibc", "初始化失败");
                        AlibcContainerEventManager.sendEvent(3, "");
                        AlibcComponentTrack.sendUseabilityFailure("BCPCSDK", AlibcComponentTrack.MONITOR_POINT_COMPONENT_INIT, AlibcComponentTrack.ERRNO_NBSDK_INIT_FAIL, "NBSDK初始化失败");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized AlibcBaseComponent getComponentById(String str) {
        AlibcBaseComponent alibcBaseComponent;
        synchronized (AlibcContainer.class) {
            alibcBaseComponent = mWantContainer2Id.get(str);
            StringBuilder sb = new StringBuilder("want getComponentById bizid");
            sb.append(str);
            sb.append(" plugin = ");
            sb.append(alibcBaseComponent == null ? "null" : alibcBaseComponent.getWantName());
            AlibcLogger.d("alibc", sb.toString());
        }
        return alibcBaseComponent;
    }

    public static synchronized AlibcBaseComponent getComponentByType(int i) {
        AlibcBaseComponent alibcBaseComponent;
        synchronized (AlibcContainer.class) {
            try {
                String str = mWantContainer2Type.get(Integer.valueOf(i));
                alibcBaseComponent = null;
                if (!TextUtils.isEmpty(str)) {
                    alibcBaseComponent = mWantContainer2Id.get(str);
                }
                StringBuilder sb = new StringBuilder("want getComponentByType type");
                sb.append(i);
                sb.append(" bizid");
                sb.append(str);
                sb.append(" plugin = ");
                sb.append(alibcBaseComponent == null ? "null" : alibcBaseComponent.getWantName());
                AlibcLogger.d("alibc", sb.toString());
            }
        }
        return alibcBaseComponent;
    }

    public static synchronized boolean registComponent(AlibcBaseComponent alibcBaseComponent) {
        synchronized (AlibcContainer.class) {
            String bizId = alibcBaseComponent == null ? "" : alibcBaseComponent.getBizId();
            boolean z = false;
            if (TextUtils.isEmpty(bizId)) {
                AlibcLogger.d("alibc", "want registComponent isRegist error (wantComponent is null or wantComponent not bizId)");
                return false;
            }
            StringBuilder sb = new StringBuilder("want registComponent bizid");
            sb.append(bizId);
            sb.append(" wantWidget is ");
            sb.append(alibcBaseComponent.getWantName());
            AlibcLogger.d("alibc", sb.toString());
            if (mWantContainer2Id.get(bizId) == null) {
                mWantContainer2Id.put(bizId, alibcBaseComponent);
                z = true;
            }
            if (alibcBaseComponent.getType() != -1 && mWantContainer2Type.get(Integer.valueOf(alibcBaseComponent.getType())) == null) {
                mWantContainer2Type.put(Integer.valueOf(alibcBaseComponent.getType()), bizId);
            }
            AlibcContainerAuth.registHint(bizId, alibcBaseComponent.getHintList());
            AlibcLogger.d("alibc", "want registComponent isRegist = ".concat(String.valueOf(z)));
            return z;
        }
    }

    public static synchronized boolean checkLicense(String str) {
        boolean checkLicense;
        synchronized (AlibcContainer.class) {
            checkLicense = AlibcContainerLicenseManager.checkLicense(str);
        }
        return checkLicense;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0072, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean unregistComponent(com.alibaba.sdk.trade.container.AlibcBaseComponent r6) {
        /*
            java.lang.Class<com.alibaba.sdk.trade.container.AlibcContainer> r0 = com.alibaba.sdk.trade.container.AlibcContainer.class
            monitor-enter(r0)
            if (r6 != 0) goto L_0x000a
            java.lang.String r1 = ""
            goto L_0x000e
        L_0x0008:
            r6 = move-exception
            goto L_0x0073
        L_0x000a:
            java.lang.String r1 = r6.getBizId()     // Catch:{ all -> 0x0008 }
        L_0x000e:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0008 }
            r3 = 0
            if (r2 == 0) goto L_0x001f
            java.lang.String r6 = "alibc"
            java.lang.String r1 = "want unregistComponent isRegist error (wantComponent is null or wantComponent not bizId)"
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.d(r6, r1)     // Catch:{ all -> 0x0008 }
            monitor-exit(r0)
            return r3
        L_0x001f:
            java.lang.String r2 = "alibc"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0008 }
            java.lang.String r5 = "want unregistComponent bizid"
            r4.<init>(r5)     // Catch:{ all -> 0x0008 }
            r4.append(r1)     // Catch:{ all -> 0x0008 }
            java.lang.String r5 = " wantWidget is "
            r4.append(r5)     // Catch:{ all -> 0x0008 }
            java.lang.String r5 = r6.getWantName()     // Catch:{ all -> 0x0008 }
            r4.append(r5)     // Catch:{ all -> 0x0008 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0008 }
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.d(r2, r4)     // Catch:{ all -> 0x0008 }
            java.util.Map<java.lang.String, com.alibaba.sdk.trade.container.AlibcBaseComponent> r2 = mWantContainer2Id     // Catch:{ all -> 0x0008 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0008 }
            if (r2 != 0) goto L_0x004d
            java.util.Map<java.lang.String, com.alibaba.sdk.trade.container.AlibcBaseComponent> r2 = mWantContainer2Id     // Catch:{ all -> 0x0008 }
            r2.remove(r1)     // Catch:{ all -> 0x0008 }
            r3 = 1
        L_0x004d:
            int r1 = r6.getType()     // Catch:{ all -> 0x0008 }
            r2 = -1
            if (r1 == r2) goto L_0x0071
            java.util.Map<java.lang.Integer, java.lang.String> r1 = mWantContainer2Type     // Catch:{ all -> 0x0008 }
            int r2 = r6.getType()     // Catch:{ all -> 0x0008 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0008 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0008 }
            if (r1 != 0) goto L_0x0071
            java.util.Map<java.lang.Integer, java.lang.String> r1 = mWantContainer2Type     // Catch:{ all -> 0x0008 }
            int r6 = r6.getType()     // Catch:{ all -> 0x0008 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0008 }
            r1.remove(r6)     // Catch:{ all -> 0x0008 }
        L_0x0071:
            monitor-exit(r0)
            return r3
        L_0x0073:
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.trade.container.AlibcContainer.unregistComponent(com.alibaba.sdk.trade.container.AlibcBaseComponent):boolean");
    }

    public static synchronized void removeAll() {
        synchronized (AlibcContainer.class) {
            mWantContainer2Id.clear();
            mWantContainer2Type.clear();
        }
    }

    public static void destroy() {
        AlibcContainerLicenseManager.destroy();
    }
}
