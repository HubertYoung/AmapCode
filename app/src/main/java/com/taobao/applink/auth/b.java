package com.taobao.applink.auth;

import android.os.Handler.Callback;

class b implements Callback {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0050, code lost:
        if (r4.a.d != null) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        if (r4.a.d != null) goto L_0x005b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleMessage(android.os.Message r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r5.what
            r2 = 11802(0x2e1a, float:1.6538E-41)
            if (r1 == r2) goto L_0x000b
            goto L_0x006a
        L_0x000b:
            com.taobao.applink.auth.userinfo.TBAppLinkUserInfo r1 = new com.taobao.applink.auth.userinfo.TBAppLinkUserInfo
            r1.<init>()
            android.os.Bundle r5 = r5.getData()
            if (r5 == 0) goto L_0x0053
            java.lang.String r2 = "result"
            java.lang.String r2 = r5.getString(r2)
            java.lang.String r3 = "true"
            boolean r2 = r3.equals(r2)
            r1.result = r2
            boolean r2 = r1.result
            if (r2 == 0) goto L_0x004a
            java.lang.String r2 = "mixedNick"
            java.lang.String r2 = r5.getString(r2)
            r1.mixedNick = r2
            java.lang.String r2 = "icon"
            java.lang.String r5 = r5.getString(r2)
            r1.icon = r5
            com.taobao.applink.auth.a r5 = r4.a
            com.taobao.applink.auth.TBAppLinkAuthListener r5 = r5.d
            if (r5 == 0) goto L_0x0064
            com.taobao.applink.auth.a r5 = r4.a
            com.taobao.applink.auth.TBAppLinkAuthListener r5 = r5.d
            r5.onSuccess(r1)
            goto L_0x0064
        L_0x004a:
            com.taobao.applink.auth.a r5 = r4.a
            com.taobao.applink.auth.TBAppLinkAuthListener r5 = r5.d
            if (r5 == 0) goto L_0x0064
            goto L_0x005b
        L_0x0053:
            com.taobao.applink.auth.a r5 = r4.a
            com.taobao.applink.auth.TBAppLinkAuthListener r5 = r5.d
            if (r5 == 0) goto L_0x0064
        L_0x005b:
            com.taobao.applink.auth.a r5 = r4.a
            com.taobao.applink.auth.TBAppLinkAuthListener r5 = r5.d
            r5.onFailure()
        L_0x0064:
            com.taobao.applink.auth.a r5 = r4.a
            r1 = 0
            r5.d = r1
        L_0x006a:
            android.app.Application r5 = com.taobao.applink.util.TBAppLinkUtil.getApplication()
            if (r5 == 0) goto L_0x007b
            android.app.Application r5 = com.taobao.applink.util.TBAppLinkUtil.getApplication()
            android.content.ServiceConnection r1 = com.taobao.applink.auth.a.a
            r5.unbindService(r1)
        L_0x007b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.applink.auth.b.handleMessage(android.os.Message):boolean");
    }
}
