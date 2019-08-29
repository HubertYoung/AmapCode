package defpackage;

/* renamed from: dmd reason: default package */
/* compiled from: SplashIntentInterceptor */
public final class dmd implements dlh {
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Intent r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = 1
            if (r8 == 0) goto L_0x003b
            java.lang.String r2 = "afp_webview_url_flag"
            java.lang.String r2 = r8.getStringExtra(r2)
            java.lang.String r3 = "afp_external_url_flag"
            java.lang.String r3 = r8.getStringExtra(r3)
            java.lang.String r4 = "afp_invalid_url_flag"
            java.lang.String r4 = r8.getStringExtra(r4)
            java.lang.String r5 = "afp_webview_url"
            boolean r2 = android.text.TextUtils.equals(r2, r5)
            if (r2 == 0) goto L_0x0020
        L_0x001e:
            r2 = 1
            goto L_0x003c
        L_0x0020:
            java.lang.String r2 = "afp_external_url"
            boolean r2 = android.text.TextUtils.equals(r3, r2)
            if (r2 == 0) goto L_0x0029
            goto L_0x001e
        L_0x0029:
            java.lang.String r2 = "afp_external_url"
            boolean r2 = android.text.TextUtils.equals(r3, r2)
            if (r2 == 0) goto L_0x0032
            goto L_0x001e
        L_0x0032:
            java.lang.String r2 = "afp_invalid_url"
            boolean r2 = android.text.TextUtils.equals(r4, r2)
            if (r2 == 0) goto L_0x003b
            goto L_0x001e
        L_0x003b:
            r2 = 0
        L_0x003c:
            if (r2 != 0) goto L_0x003f
            return r0
        L_0x003f:
            java.lang.String r2 = "afp_webview_url_flag"
            java.lang.String r2 = r8.getStringExtra(r2)
            java.lang.String r3 = "afp_external_url_flag"
            java.lang.String r3 = r8.getStringExtra(r3)
            java.lang.String r4 = "afp_invalid_url_flag"
            java.lang.String r4 = r8.getStringExtra(r4)
            java.lang.String r5 = "afp_webview_url"
            boolean r2 = android.text.TextUtils.equals(r2, r5)
            if (r2 == 0) goto L_0x009e
            if (r8 != 0) goto L_0x005d
            r2 = 0
            goto L_0x009b
        L_0x005d:
            android.net.Uri r2 = r8.getData()
            java.lang.String r2 = r2.toString()
            ajn r5 = new ajn
            r5.<init>()
            boolean r5 = defpackage.ajn.a(r2)
            if (r5 != 0) goto L_0x0079
            ajn r5 = new ajn
            r5.<init>()
            java.lang.String r2 = defpackage.ajn.b(r2)
        L_0x0079:
            aja r5 = new aja
            r5.<init>(r2)
            ajf r2 = new ajf
            r2.<init>()
            r5.b = r2
            esb r2 = defpackage.esb.a.a
            java.lang.Class<aix> r6 = defpackage.aix.class
            esc r2 = r2.a(r6)
            aix r2 = (defpackage.aix) r2
            if (r2 == 0) goto L_0x009a
            bid r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r2.a(r6, r5)
        L_0x009a:
            r2 = 1
        L_0x009b:
            if (r2 == 0) goto L_0x009e
            return r1
        L_0x009e:
            java.lang.String r2 = "afp_external_url"
            boolean r2 = android.text.TextUtils.equals(r3, r2)
            if (r2 == 0) goto L_0x00ec
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            android.net.Uri r2 = r8.getData()
            jr r3 = defpackage.jr.a()
            java.lang.String r4 = r2.toString()
            boolean r3 = r3.a(r4)
            if (r3 != 0) goto L_0x00d5
            java.lang.String r8 = "IntentController"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "非法的外部链接："
            r0.<init>(r3)
            java.lang.String r2 = r2.toString()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.amap.bundle.logs.AMapLog.e(r8, r0)
            goto L_0x00eb
        L_0x00d5:
            java.lang.String r8 = r8.getAction()
            r3 = 268435456(0x10000000, float:2.5243549E-29)
            r0.setFlags(r3)
            r0.setAction(r8)
            r0.setData(r2)
            android.content.Context r8 = com.autonavi.minimap.MapApplication.getContext()
            r8.startActivity(r0)
        L_0x00eb:
            return r1
        L_0x00ec:
            java.lang.String r8 = "afp_invalid_url"
            boolean r8 = android.text.TextUtils.equals(r4, r8)
            if (r8 == 0) goto L_0x00f5
            return r1
        L_0x00f5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dmd.a(android.content.Intent):boolean");
    }
}
