package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: wi reason: default package */
/* compiled from: JsAuthorizeManager */
public final class wi {
    private static boolean a = false;
    /* access modifiers changed from: private */
    public static wf b = new wf();
    /* access modifiers changed from: private */
    public static wj c;
    private static lp d = new lp() {
        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("[Manager] configCallback status = ");
            sb.append(i);
            sb.append(", from = ");
            sb.append(wi.c != null ? "doUpdate" : "auto");
            AMapLog.i("jsauth", sb.toString());
            wi.f();
            if (wi.c != null) {
                wi.c.a(i, str);
            } else {
                new wj(null, wi.b.c(), new b(null, 0)).a(i, str);
            }
        }

        public final void onConfigCallBack(int i) {
            wi.f();
            if (wi.c != null) {
                wi.c.a(i, (String) null);
            }
        }
    };

    /* renamed from: wi$a */
    /* compiled from: JsAuthorizeManager */
    public interface a {
        void a(int i, String str);
    }

    /* renamed from: wi$b */
    /* compiled from: JsAuthorizeManager */
    static class b implements defpackage.wj.a {
        private final a a;

        /* synthetic */ b(a aVar, byte b) {
            this(aVar);
        }

        private b(a aVar) {
            this.a = aVar;
        }

        public final void a(int i) {
            wi.b(this.a, i);
            wi.c = null;
        }

        public final void a(boolean z) {
            if (wi.b != null) {
                wi.b.a(z);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
            if (defpackage.wh.a(r6.d) != false) goto L_0x002f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(defpackage.wj.b r6) {
            /*
                r5 = this;
                defpackage.wi.c = null
                boolean r0 = r6.c
                r1 = 1
                r2 = 0
                if (r0 != 0) goto L_0x000a
                goto L_0x0036
            L_0x000a:
                wf r0 = defpackage.wi.b
                if (r0 == 0) goto L_0x0035
                wf r0 = defpackage.wi.b
                java.lang.String r3 = r6.d
                java.lang.String r4 = r6.b
                boolean r0 = r0.a(r3, r4)
                if (r0 == 0) goto L_0x0027
                java.lang.String r6 = r6.d
                boolean r6 = defpackage.wh.a(r6)
                if (r6 == 0) goto L_0x002e
                goto L_0x002f
            L_0x0027:
                wf r6 = defpackage.wi.b
                r6.b()
            L_0x002e:
                r1 = 0
            L_0x002f:
                if (r1 != 0) goto L_0x0036
                defpackage.wh.e()
                goto L_0x0036
            L_0x0035:
                r1 = 0
            L_0x0036:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.wi.b.a(wj$b):boolean");
        }
    }

    private static String a(int i) {
        switch (i) {
            case 0:
                return GenBusCodeService.CODE_SUCESS;
            case 1:
                return "NO_NETWORK";
            case 2:
                return "NETWORK_ERROR";
            case 3:
                return "SERVER_ERROR";
            case 4:
                return "DOWNLOAD_FAIL";
            case 5:
                return "PARSE_FAIL";
            case 6:
                return "REQUEST_TOO_MORE";
            case 7:
                return "NOT_NEED";
            case 8:
                return "CANCELED";
            default:
                return "unknown";
        }
    }

    public static void a() {
        lo.a().a((String) "h5_white_list", d);
    }

    public static synchronized void a(String str, String str2, a aVar) {
        synchronized (wi.class) {
            f();
            String d2 = wh.d();
            if (!TextUtils.isEmpty(str2) && str2.equals(d2)) {
                b(aVar, 7);
            } else if (!aaw.c(AMapPageUtil.getAppContext())) {
                b(aVar, 1);
            } else {
                String a2 = wg.a(str);
                if (!wj.a(a2)) {
                    b(aVar, 6);
                    return;
                }
                AMapLog.i("jsauth", "[Manager] doUpdate ".concat(String.valueOf(a2)));
                if (c != null) {
                    wj wjVar = c;
                    StringBuilder sb = new StringBuilder("[UpdateTask] cancelRequest call from = ");
                    sb.append(wjVar.b);
                    AMapLog.d("jsauth", sb.toString());
                    if (wjVar.a != null) {
                        wjVar.a.cancel();
                        wjVar.a = null;
                    }
                    wjVar.a(8);
                }
                c = new wj(a2, b.c(), new b(aVar, 0));
                lo.a().d("h5_white_list");
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void f() {
        synchronized (wi.class) {
            if (!a) {
                b.a();
                a = true;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x008c, code lost:
        if (com.autonavi.minimap.ajx3.loader.AjxAssetLoader.ANDROID_ASSET.equalsIgnoreCase(r1.get(0)) != false) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d9, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0091 A[Catch:{ all -> 0x00da }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009d A[Catch:{ all -> 0x00da }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bd A[Catch:{ all -> 0x00da }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean a(defpackage.wm r7, java.lang.String r8) {
        /*
            java.lang.Class<wi> r0 = defpackage.wi.class
            monitor-enter(r0)
            boolean r1 = defpackage.bno.a     // Catch:{ all -> 0x00da }
            r2 = 1
            if (r1 == 0) goto L_0x0011
            java.lang.String r7 = "jsauth"
            java.lang.String r8 = "[Manager] Config.js_auth_enable = false"
            com.amap.bundle.logs.AMapLog.d(r7, r8)     // Catch:{ all -> 0x00da }
            monitor-exit(r0)
            return r2
        L_0x0011:
            f()     // Catch:{ all -> 0x00da }
            wf r1 = b     // Catch:{ all -> 0x00da }
            boolean r1 = r1.d()     // Catch:{ all -> 0x00da }
            if (r1 != 0) goto L_0x001e
            monitor-exit(r0)
            return r2
        L_0x001e:
            java.lang.String r1 = "jsAuthorizeWhiteListUpdate"
            boolean r1 = r1.equals(r8)     // Catch:{ all -> 0x00da }
            if (r1 == 0) goto L_0x0028
            monitor-exit(r0)
            return r2
        L_0x0028:
            java.lang.String r7 = r7.c()     // Catch:{ all -> 0x00da }
            boolean r1 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x00da }
            r3 = 0
            if (r1 == 0) goto L_0x0035
            monitor-exit(r0)
            return r3
        L_0x0035:
            android.net.Uri r1 = android.net.Uri.parse(r7)     // Catch:{ all -> 0x00da }
            java.lang.String r4 = r1.getHost()     // Catch:{ all -> 0x00da }
            if (r1 != 0) goto L_0x0041
        L_0x003f:
            r1 = 0
            goto L_0x008f
        L_0x0041:
            java.lang.String r5 = "file"
            java.lang.String r6 = r1.getScheme()     // Catch:{ all -> 0x00da }
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ all -> 0x00da }
            if (r5 == 0) goto L_0x003f
            java.util.List r1 = r1.getPathSegments()     // Catch:{ all -> 0x00da }
            if (r1 == 0) goto L_0x003f
            int r5 = r1.size()     // Catch:{ all -> 0x00da }
            if (r5 <= 0) goto L_0x003f
            java.lang.String r5 = "data"
            java.lang.Object r6 = r1.get(r3)     // Catch:{ all -> 0x00da }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00da }
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ all -> 0x00da }
            if (r5 == 0) goto L_0x0080
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00da }
        L_0x006b:
            boolean r5 = r1.hasNext()     // Catch:{ all -> 0x00da }
            if (r5 == 0) goto L_0x003f
            java.lang.Object r5 = r1.next()     // Catch:{ all -> 0x00da }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x00da }
            java.lang.String r6 = "com.autonavi.minimap"
            boolean r5 = r6.equalsIgnoreCase(r5)     // Catch:{ all -> 0x00da }
            if (r5 == 0) goto L_0x006b
            goto L_0x008e
        L_0x0080:
            java.lang.String r5 = "android_asset"
            java.lang.Object r1 = r1.get(r3)     // Catch:{ all -> 0x00da }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00da }
            boolean r1 = r5.equalsIgnoreCase(r1)     // Catch:{ all -> 0x00da }
            if (r1 == 0) goto L_0x003f
        L_0x008e:
            r1 = 1
        L_0x008f:
            if (r1 != 0) goto L_0x009b
            wf r1 = b     // Catch:{ all -> 0x00da }
            boolean r1 = r1.b(r4, r8)     // Catch:{ all -> 0x00da }
            if (r1 == 0) goto L_0x009a
            goto L_0x009b
        L_0x009a:
            r2 = 0
        L_0x009b:
            if (r2 != 0) goto L_0x00bd
            java.lang.String r1 = "jsauth"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00da }
            java.lang.String r5 = "[Manager] declined "
            r3.<init>(r5)     // Catch:{ all -> 0x00da }
            r3.append(r7)     // Catch:{ all -> 0x00da }
            java.lang.String r7 = ", action = "
            r3.append(r7)     // Catch:{ all -> 0x00da }
            r3.append(r8)     // Catch:{ all -> 0x00da }
            java.lang.String r7 = r3.toString()     // Catch:{ all -> 0x00da }
            com.amap.bundle.logs.AMapLog.e(r1, r7)     // Catch:{ all -> 0x00da }
            r7 = 0
            a(r4, r7, r7)     // Catch:{ all -> 0x00da }
            goto L_0x00d8
        L_0x00bd:
            java.lang.String r1 = "jsauth"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00da }
            java.lang.String r4 = "[Manager] permitted "
            r3.<init>(r4)     // Catch:{ all -> 0x00da }
            r3.append(r7)     // Catch:{ all -> 0x00da }
            java.lang.String r7 = ", action = "
            r3.append(r7)     // Catch:{ all -> 0x00da }
            r3.append(r8)     // Catch:{ all -> 0x00da }
            java.lang.String r7 = r3.toString()     // Catch:{ all -> 0x00da }
            com.amap.bundle.logs.AMapLog.d(r1, r7)     // Catch:{ all -> 0x00da }
        L_0x00d8:
            monitor-exit(r0)
            return r2
        L_0x00da:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wi.a(wm, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public static void b(a aVar, int i) {
        StringBuilder sb = new StringBuilder("[Manager] update callbackResult = ");
        sb.append(i);
        sb.append(", ");
        sb.append(a(i));
        AMapLog.i("jsauth", sb.toString());
        if (aVar != null) {
            aVar.a(i, wh.d());
        }
    }
}
