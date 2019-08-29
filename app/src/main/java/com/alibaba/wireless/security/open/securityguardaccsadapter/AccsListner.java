package com.alibaba.wireless.security.open.securityguardaccsadapter;

import com.alibaba.wireless.security.framework.IRouterComponent;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ConnectInfo;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;

public class AccsListner extends AccsAbstractDataListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "CallbackListener";
    private static IRouterComponent gGlobalRounterComponent;

    public void onAntiBrush(boolean z, ExtraInfo extraInfo) {
    }

    public void onBind(String str, int i, ExtraInfo extraInfo) {
    }

    public void onConnected(ConnectInfo connectInfo) {
    }

    public void onDisconnected(ConnectInfo connectInfo) {
    }

    public void onResponse(String str, String str2, int i, byte[] bArr, ExtraInfo extraInfo) {
    }

    public void onSendData(String str, String str2, int i, ExtraInfo extraInfo) {
    }

    public void onUnbind(String str, int i, ExtraInfo extraInfo) {
    }

    public void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo) {
        if (bArr != null) {
            String str4 = new String(bArr);
            IRouterComponent routerComponent = getRouterComponent();
            if (routerComponent != null) {
                routerComponent.doCommand(11152, str4);
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
            java.lang.Class<com.alibaba.wireless.security.open.securityguardaccsadapter.AccsListner> r0 = com.alibaba.wireless.security.open.securityguardaccsadapter.AccsListner.class
            monitor-enter(r0)
            com.alibaba.wireless.security.framework.IRouterComponent r1 = gGlobalRounterComponent     // Catch:{ all -> 0x001d }
            if (r1 != 0) goto L_0x001b
            android.content.Context r1 = com.alibaba.wireless.security.open.securityguardaccsadapter.AccsAdapter.gContext     // Catch:{ Exception -> 0x001b }
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
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.open.securityguardaccsadapter.AccsListner.getRouterComponent():com.alibaba.wireless.security.framework.IRouterComponent");
    }
}
