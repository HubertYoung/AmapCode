package defpackage;

import android.app.Activity;

/* renamed from: dlw reason: default package */
/* compiled from: AppUpdateIntentInterceptor */
public final class dlw implements dlh {
    private jj a;

    public dlw(Activity activity) {
        this.a = new jj(activity);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0063, code lost:
        if ("DownloadApp".equalsIgnoreCase(r1.getQueryParameter("featureName")) != false) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f3, code lost:
        r2 = r1;
        r1 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f9, code lost:
        if (android.text.TextUtils.isEmpty(r1) != false) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ff, code lost:
        if (android.text.TextUtils.isEmpty(r2) != false) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0101, code lost:
        r0.a(r1, r2, r3, 1, r5);
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0069  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Intent r9) {
        /*
            r8 = this;
            jj r0 = r8.a
            r6 = 0
            r7 = 1
            if (r9 == 0) goto L_0x0066
            java.lang.String r1 = r9.getAction()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x0011
            goto L_0x0066
        L_0x0011:
            java.lang.String r1 = r9.getAction()
            java.lang.String r2 = "action.autonavi.checkappupdate"
            boolean r2 = android.text.TextUtils.equals(r1, r2)
            if (r2 == 0) goto L_0x001f
        L_0x001d:
            r1 = 1
            goto L_0x0067
        L_0x001f:
            java.lang.String r2 = "appDownloadfail"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x0028
            goto L_0x001d
        L_0x0028:
            java.lang.String r1 = "appDownloadName"
            java.lang.String r1 = r9.getStringExtra(r1)
            java.lang.String r2 = "appDownloadUrl"
            java.lang.String r2 = r9.getStringExtra(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0041
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0041
            goto L_0x001d
        L_0x0041:
            android.net.Uri r1 = r9.getData()
            if (r1 == 0) goto L_0x0066
            android.net.Uri r2 = r9.getData()
            java.lang.String r2 = r2.getHost()
            java.lang.String r3 = "openFeature"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0066
            java.lang.String r2 = "featureName"
            java.lang.String r1 = r1.getQueryParameter(r2)
            java.lang.String r2 = "DownloadApp"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0066
            goto L_0x001d
        L_0x0066:
            r1 = 0
        L_0x0067:
            if (r1 == 0) goto L_0x0167
            java.lang.String r1 = r9.getAction()
            java.lang.String r2 = "action.autonavi.checkappupdate"
            boolean r2 = android.text.TextUtils.equals(r1, r2)
            if (r2 == 0) goto L_0x0088
            com.amap.bundle.appupgrade.AppUpgradeController r9 = r0.d
            if (r9 != 0) goto L_0x0082
            com.amap.bundle.appupgrade.AppUpgradeController r9 = new com.amap.bundle.appupgrade.AppUpgradeController
            android.app.Activity r1 = r0.a
            r9.<init>(r1)
            r0.d = r9
        L_0x0082:
            com.amap.bundle.appupgrade.AppUpgradeController r9 = r0.d
            r9.c()
            return r7
        L_0x0088:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0106
            java.lang.String r2 = "appDownloadfail"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x0106
            java.lang.String r1 = r9.getAction()
            if (r1 == 0) goto L_0x0105
            int r2 = r1.length()
            int r2 = r2 - r7
            java.lang.String r1 = r1.substring(r2)
            int r1 = java.lang.Integer.parseInt(r1)
            java.lang.String r2 = ""
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            switch(r1) {
                case 0: goto L_0x00e3;
                case 1: goto L_0x00db;
                case 2: goto L_0x00b5;
                default: goto L_0x00b2;
            }
        L_0x00b2:
            r1 = r4
            r5 = 0
            goto L_0x00f5
        L_0x00b5:
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "appDownloadUrl"
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r1, r6)
            java.lang.String r1 = "SplashAppName"
            java.lang.String r2 = ""
            java.lang.String r1 = r9.getString(r1, r2)
            java.lang.String r2 = "SplashAppPckName"
            java.lang.String r3 = ""
            java.lang.String r2 = r9.getString(r2, r3)
            java.lang.String r3 = "SplashAppUrl"
            java.lang.String r4 = ""
            java.lang.String r9 = r9.getString(r3, r4)
            r3 = 2
            r3 = r2
            r5 = 2
            goto L_0x00f3
        L_0x00db:
            jg r9 = defpackage.jg.a()
            r9.b()
            goto L_0x0105
        L_0x00e3:
            java.lang.String r1 = "appDownloadName"
            java.lang.String r1 = r9.getStringExtra(r1)
            java.lang.String r2 = ""
            java.lang.String r3 = "appDownloadUrl"
            java.lang.String r9 = r9.getStringExtra(r3)
            r3 = r2
            r5 = 0
        L_0x00f3:
            r2 = r1
            r1 = r9
        L_0x00f5:
            boolean r9 = android.text.TextUtils.isEmpty(r1)
            if (r9 != 0) goto L_0x0105
            boolean r9 = android.text.TextUtils.isEmpty(r2)
            if (r9 != 0) goto L_0x0105
            r4 = 1
            r0.a(r1, r2, r3, r4, r5)
        L_0x0105:
            return r7
        L_0x0106:
            java.lang.String r1 = "appDownloadName"
            java.lang.String r2 = r9.getStringExtra(r1)
            java.lang.String r3 = ""
            java.lang.String r1 = "appDownloadUrl"
            java.lang.String r1 = r9.getStringExtra(r1)
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 != 0) goto L_0x0126
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0126
            r4 = 3
            r5 = 0
            r0.a(r1, r2, r3, r4, r5)
            return r7
        L_0x0126:
            android.net.Uri r1 = r9.getData()
            if (r1 == 0) goto L_0x0163
            android.net.Uri r9 = r9.getData()
            java.lang.String r9 = r9.getHost()
            java.lang.String r2 = "openFeature"
            boolean r9 = r2.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x0163
            java.lang.String r9 = "featureName"
            java.lang.String r9 = r1.getQueryParameter(r9)
            java.lang.String r2 = "DownloadApp"
            boolean r9 = r2.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x0163
            java.lang.String r9 = "AppName"
            java.lang.String r2 = r1.getQueryParameter(r9)
            java.lang.String r9 = "PkgName"
            java.lang.String r3 = r1.getQueryParameter(r9)
            java.lang.String r9 = "DownloadURL"
            java.lang.String r1 = r1.getQueryParameter(r9)
            r4 = 3
            r5 = 2
            r0.a(r1, r2, r3, r4, r5)
            r9 = 1
            goto L_0x0164
        L_0x0163:
            r9 = 0
        L_0x0164:
            if (r9 == 0) goto L_0x0167
            return r7
        L_0x0167:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlw.a(android.content.Intent):boolean");
    }
}
