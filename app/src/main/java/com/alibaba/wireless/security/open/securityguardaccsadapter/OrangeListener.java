package com.alibaba.wireless.security.open.securityguardaccsadapter;

import com.alibaba.wireless.security.framework.IRouterComponent;
import com.taobao.orange.OConfigListener;
import java.util.Map;
import org.json.JSONObject;

public class OrangeListener implements OConfigListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "OrangeListener";
    private static IRouterComponent gGlobalRounterComponent;

    public void onConfigUpdate(String str, Map<String, String> map) {
        String str2;
        try {
            str2 = new JSONObject(map).toString();
        } catch (Exception unused) {
            str2 = null;
        }
        if (str2 != null) {
            IRouterComponent routerComponent = getRouterComponent();
            if (routerComponent != null) {
                routerComponent.doCommand(11153, str2);
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:4|5|(2:7|8)|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x001b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alibaba.wireless.security.framework.IRouterComponent getRouterComponent() {
        /*
            r2 = this;
            com.alibaba.wireless.security.framework.IRouterComponent r0 = gGlobalRounterComponent
            if (r0 != 0) goto L_0x0020
            java.lang.Class<com.taobao.orange.OConfigListener> r0 = com.taobao.orange.OConfigListener.class
            monitor-enter(r0)
            com.alibaba.wireless.security.framework.IRouterComponent r1 = gGlobalRounterComponent     // Catch:{ all -> 0x001d }
            if (r1 != 0) goto L_0x001b
            android.content.Context r1 = com.alibaba.wireless.security.open.securityguardaccsadapter.OrangeAdapter.gContext     // Catch:{ Exception -> 0x001b }
            com.alibaba.wireless.security.open.SecurityGuardManager r1 = com.alibaba.wireless.security.open.SecurityGuardManager.getInstance(r1)     // Catch:{ Exception -> 0x001b }
            com.alibaba.wireless.security.framework.ISGPluginManager r1 = r1.getSGPluginManager()     // Catch:{ Exception -> 0x001b }
            com.alibaba.wireless.security.framework.IRouterComponent r1 = r1.getRouter()     // Catch:{ Exception -> 0x001b }
            gGlobalRounterComponent = r1     // Catch:{ Exception -> 0x001b }
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            goto L_0x0020
        L_0x001d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r1
        L_0x0020:
            com.alibaba.wireless.security.framework.IRouterComponent r0 = gGlobalRounterComponent
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.open.securityguardaccsadapter.OrangeListener.getRouterComponent():com.alibaba.wireless.security.framework.IRouterComponent");
    }
}
