package com.alibaba.sdk.trade.component;

import com.alibaba.baichuan.android.jsbridge.plugin.AlibcPluginManager;
import com.alibaba.sdk.trade.component.cart.AlibcCart;
import com.alibaba.sdk.trade.component.coupon.AlibcActivityBridge;
import com.alibaba.sdk.trade.component.coupon.AlibcCoupon;
import com.alibaba.sdk.trade.container.AlibcContainer;
import com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager;
import java.util.List;

public class AlibcComponentManager {
    public static void initPlugin() {
        List<String> license = AlibcContainerLicenseManager.getLicense();
        if (license == null || license.size() <= 0) {
            initAllPlugins();
            return;
        }
        updataPlugin();
        AlibcPluginManager.registerPlugin(AlibcActivityBridge.API_NAME, AlibcActivityBridge.class, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void updataPlugin() {
        /*
            java.lang.Class<com.alibaba.sdk.trade.component.AlibcComponentManager> r0 = com.alibaba.sdk.trade.component.AlibcComponentManager.class
            monitor-enter(r0)
            java.util.List r1 = com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager.getLicense()     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0049
            int r2 = r1.size()     // Catch:{ all -> 0x004b }
            if (r2 > 0) goto L_0x0010
            goto L_0x0049
        L_0x0010:
            com.alibaba.sdk.trade.container.AlibcContainer.removeAll()     // Catch:{ all -> 0x004b }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x004b }
        L_0x0017:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x004b }
            if (r2 == 0) goto L_0x0047
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x004b }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x004b }
            com.alibaba.sdk.trade.component.cart.AlibcCart r3 = com.alibaba.sdk.trade.component.cart.AlibcCart.instance     // Catch:{ all -> 0x004b }
            java.lang.String r3 = r3.getBizId()     // Catch:{ all -> 0x004b }
            boolean r3 = android.text.TextUtils.equals(r2, r3)     // Catch:{ all -> 0x004b }
            if (r3 == 0) goto L_0x0035
            com.alibaba.sdk.trade.component.cart.AlibcCart r2 = com.alibaba.sdk.trade.component.cart.AlibcCart.instance     // Catch:{ all -> 0x004b }
            com.alibaba.sdk.trade.container.AlibcContainer.registComponent(r2)     // Catch:{ all -> 0x004b }
            goto L_0x0017
        L_0x0035:
            com.alibaba.sdk.trade.component.coupon.AlibcCoupon r3 = com.alibaba.sdk.trade.component.coupon.AlibcCoupon.instance     // Catch:{ all -> 0x004b }
            java.lang.String r3 = r3.getBizId()     // Catch:{ all -> 0x004b }
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ all -> 0x004b }
            if (r2 == 0) goto L_0x0017
            com.alibaba.sdk.trade.component.coupon.AlibcCoupon r2 = com.alibaba.sdk.trade.component.coupon.AlibcCoupon.instance     // Catch:{ all -> 0x004b }
            com.alibaba.sdk.trade.container.AlibcContainer.registComponent(r2)     // Catch:{ all -> 0x004b }
            goto L_0x0017
        L_0x0047:
            monitor-exit(r0)
            return
        L_0x0049:
            monitor-exit(r0)
            return
        L_0x004b:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.trade.component.AlibcComponentManager.updataPlugin():void");
    }

    private static synchronized void initAllPlugins() {
        synchronized (AlibcComponentManager.class) {
            AlibcContainer.registComponent(AlibcCart.instance);
            AlibcContainer.registComponent(AlibcCoupon.instance);
        }
    }
}
