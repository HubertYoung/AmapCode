package com.alibaba.baichuan.android.trade.c.a.a;

import android.os.Looper;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.c.a.a.a.c;
import com.alibaba.baichuan.android.trade.c.a.a.a.d;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.b.f;
import com.alibaba.baichuan.android.trade.c.a.b.h;
import com.alibaba.baichuan.android.trade.c.a.b.i;
import com.alibaba.baichuan.android.trade.c.a.b.k;
import com.alibaba.baichuan.android.trade.constants.UrlConstants;
import com.alibaba.baichuan.android.trade.utils.g;
import com.alibaba.baichuan.android.trade.utils.j;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class e {
    private static final e a = new e();
    private volatile Map b = new LinkedHashMap();
    private volatile Map c = new LinkedHashMap();
    private d d;
    private volatile boolean e;
    private LinkedHashMap f = new LinkedHashMap();
    private LinkedHashMap g = new LinkedHashMap();

    static class a {
        public Boolean a;

        private a() {
        }

        /* synthetic */ a(f fVar) {
            this();
        }
    }

    private d a(boolean z) {
        d[] dVarArr = (d[]) this.g.values().toArray(new d[0]);
        if (z) {
            j.a(dVarArr);
        }
        com.alibaba.baichuan.android.trade.c.a.a.b.d[] dVarArr2 = (com.alibaba.baichuan.android.trade.c.a.a.b.d[]) this.f.values().toArray(new com.alibaba.baichuan.android.trade.c.a.a.b.d[0]);
        if (z) {
            j.a(dVarArr2);
        }
        d dVar = new d();
        dVar.a = 0;
        dVar.c = new LinkedHashMap();
        dVar.b = new LinkedHashMap();
        for (d dVar2 : dVarArr) {
            dVar.c.put(dVar2.a, dVar2);
        }
        for (com.alibaba.baichuan.android.trade.c.a.a.b.d dVar3 : dVarArr2) {
            dVar.b.put(dVar3.a, dVar3);
        }
        return dVar;
    }

    public static e a() {
        return a;
    }

    private void a(c cVar) {
        for (Entry value : this.c.entrySet()) {
            com.alibaba.baichuan.android.trade.c.a.a.a.a aVar = (com.alibaba.baichuan.android.trade.c.a.a.a.a) value.getValue();
            if (a(cVar.b(), aVar.a().g)) {
                StringBuilder sb = new StringBuilder("skip_");
                sb.append(aVar.a().a);
                if (!"true".equals(cVar.b(sb.toString())) && a(cVar.e(), aVar.a().h)) {
                    try {
                        if (aVar.a(cVar)) {
                            cVar.h();
                            if (!aVar.a().i) {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } catch (Exception e2) {
                        StringBuilder sb2 = new StringBuilder("Fail to execute the Filter ");
                        sb2.append((aVar == null || aVar.a() == null) ? null : aVar.a().a);
                        AlibcLogger.e("ui", sb2.toString(), e2);
                    }
                }
            }
        }
    }

    private boolean a(int i, int[] iArr) {
        if (iArr == null) {
            return true;
        }
        for (int i2 : iArr) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    private boolean a(com.alibaba.baichuan.android.trade.c.a.a.b.a aVar, com.alibaba.baichuan.android.trade.c.a.a.b.c cVar, boolean z) {
        String str = null;
        if (!aVar.a().l || z) {
            try {
                return aVar.a(cVar);
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("fail to execute the Handler ");
                if (!(aVar == null || aVar.a() == null)) {
                    str = aVar.a().a;
                }
                sb.append(str);
                AlibcLogger.e("ui", sb.toString(), th);
                return false;
            }
        } else {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            a aVar2 = new a(null);
            g gVar = AlibcContext.executorService;
            f fVar = new f(this, aVar2, aVar, cVar, countDownLatch);
            gVar.b(fVar);
            try {
                countDownLatch.await(3, TimeUnit.SECONDS);
            } catch (Exception unused) {
            }
            if (aVar2.a == null) {
                return false;
            }
            return aVar2.a.booleanValue();
        }
    }

    private boolean a(com.alibaba.baichuan.android.trade.c.a.a.b.c cVar) {
        boolean z = Looper.myLooper() == Looper.getMainLooper();
        for (Entry value : this.b.entrySet()) {
            com.alibaba.baichuan.android.trade.c.a.a.b.a aVar = (com.alibaba.baichuan.android.trade.c.a.a.b.a) value.getValue();
            if (a(cVar.b(), aVar.a().g)) {
                StringBuilder sb = new StringBuilder("skip_");
                sb.append(aVar.a().a);
                if (!"true".equals(cVar.b(sb.toString())) && a(cVar.e(), aVar.a().h) && a(aVar, cVar, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(String str, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return true;
        }
        if (str == null) {
            return false;
        }
        for (String equals : strArr) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private void c() {
        com.alibaba.baichuan.android.trade.c.a.a.a.e a2 = com.alibaba.baichuan.android.trade.c.a.a.a.e.a((String) "addAllParamsFilter");
        a2.a("addAllParamsUrlsStartMatches", "pattern", f.a, UrlConstants.RE_ADD_PARAM_URLS);
        a2.a(new int[]{2, 1});
        HashMap hashMap = new HashMap();
        hashMap.put("key", "addAllParams");
        hashMap.put("value", "${ddAllParams}");
        hashMap.put(Constants.KEY_MODE, "addIfAbsent");
        a2.a("addAllParams", "updateParameter", hashMap);
        a.a(a2.a());
    }

    private void d() {
        a.a(com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "loginHandler").a("login", "pattern", f.a, UrlConstants.RE_LOGIN_URLS).a((b) new com.alibaba.baichuan.android.trade.c.a.b.d()).a(new int[]{2}).a());
        a.a(com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "logoutHandler").a("logout", "pattern", f.a, UrlConstants.RE_LOGOUT_URLS).a((b) new com.alibaba.baichuan.android.trade.c.a.b.f()).a(new int[]{2}).a());
        a.a(com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "alipayHandlerInfo").a(BehavorReporter.PROVIDE_BY_ALIPAY, "pattern", f.a, UrlConstants.RE_ALIPAY_URLS).a((b) new com.alibaba.baichuan.android.trade.c.a.b.c()).a(new int[]{2}).a());
        a.a(com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "tbopenHandlerInfo").a("tbopen", "pattern", f.b, UrlConstants.RE_TBOPEN_URLS).a((b) new k()).a(new int[]{2}).a());
        com.alibaba.baichuan.android.trade.c.a.a.b.e a2 = com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "addCartHandler");
        a2.a("addCartUrlMatches", "pattern", f.a, UrlConstants.RE_ADDCART_URLS);
        a2.a((b) new com.alibaba.baichuan.android.trade.c.a.b.a());
        a2.a(true);
        a.a(a2.a());
        com.alibaba.baichuan.android.trade.c.a.a.b.e a3 = com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "nativeTaobaoShopHandlerInfoBuilder");
        a3.a("showShopNativeMatches", "pattern", f.a, UrlConstants.RE_SHOP_URLS);
        a3.a(new String[]{"noForceH5"});
        a3.a((b) new i());
        a3.a(new int[]{2});
        a.a(a3.a());
        com.alibaba.baichuan.android.trade.c.a.a.b.e a4 = com.alibaba.baichuan.android.trade.c.a.a.b.e.a((String) "nativeTaobaoDetailHandlerInfoBuilder");
        a4.a(new String[]{"noForceH5"});
        a4.a("showDetailNativeMatches", "pattern", f.a, UrlConstants.RE_DETAIL_URLS);
        a4.a((b) new h());
        a4.a(new int[]{2});
        a.a(a4.a());
    }

    private void e() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Entry entry : this.d.c.entrySet()) {
                linkedHashMap.put(entry.getKey(), ((d) entry.getValue()).a());
            }
            this.c = linkedHashMap;
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Entry entry2 : this.d.b.entrySet()) {
                linkedHashMap2.put(entry2.getKey(), ((com.alibaba.baichuan.android.trade.c.a.a.b.d) entry2.getValue()).a());
            }
            this.b = linkedHashMap2;
        } catch (Exception e2) {
            AlibcLogger.e("ui", "fail to initialize filter/handler", e2);
        }
    }

    private void f() {
        AlibcLogger.d("InterceptionManager", "InterceptionManager info is not found in disk, use default");
        this.d = a(true);
    }

    public String a(com.alibaba.baichuan.android.trade.c.a.a.c.a aVar) {
        if (aVar.d == null) {
            return null;
        }
        c cVar = new c(aVar.e, aVar.d, aVar.i);
        cVar.c(aVar.f);
        if (aVar.j != null) {
            for (String valueOf : aVar.j) {
                cVar.a("skip_".concat(String.valueOf(valueOf)), "true");
            }
        }
        a(cVar);
        return cVar.d();
    }

    public void a(d dVar) {
        this.g.put(dVar.a, dVar);
    }

    public void a(com.alibaba.baichuan.android.trade.c.a.a.b.d dVar) {
        this.f.put(dVar.a, dVar);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.baichuan.android.trade.c.a.a.b b(com.alibaba.baichuan.android.trade.c.a.a.c.a r9) {
        /*
            r8 = this;
            java.lang.String r0 = r9.d
            r1 = 0
            if (r0 != 0) goto L_0x000d
            com.alibaba.baichuan.android.trade.c.a.a.b r0 = new com.alibaba.baichuan.android.trade.c.a.a.b
            java.lang.String r9 = r9.d
            r0.<init>(r1, r9)
            return r0
        L_0x000d:
            boolean r0 = r8.e
            if (r0 != 0) goto L_0x0014
            r8.b()
        L_0x0014:
            com.alibaba.baichuan.android.trade.c.a.a.a.c r0 = new com.alibaba.baichuan.android.trade.c.a.a.a.c
            int r2 = r9.e
            java.lang.String r3 = r9.d
            java.util.Map r4 = r9.i
            r0.<init>(r2, r3, r4)
            java.lang.String r2 = r9.f
            r0.c(r2)
            r8.a(r0)
            com.alibaba.baichuan.android.trade.c.a.a.b.c r2 = new com.alibaba.baichuan.android.trade.c.a.a.b.c
            int r3 = r9.e
            java.lang.String r4 = r0.d()
            java.util.Map r5 = r9.i
            r2.<init>(r3, r4, r5)
            java.lang.String r3 = r9.f
            r2.c(r3)
            int r3 = r9.e
            r4 = 1
            if (r3 == r4) goto L_0x0054
            int r3 = r9.e
            r5 = 2
            if (r3 != r5) goto L_0x0044
            goto L_0x0054
        L_0x0044:
            int r3 = r9.e
            r5 = 3
            if (r3 != r5) goto L_0x004a
            goto L_0x005c
        L_0x004a:
            int r3 = r9.e
            r5 = 4
            if (r3 != r5) goto L_0x0060
            com.alibaba.baichuan.android.trade.ui.a.b r3 = r9.c
            r2.f = r3
            goto L_0x0060
        L_0x0054:
            android.webkit.WebView r3 = r9.a
            r2.d = r3
            com.alibaba.baichuan.android.trade.c.b.d r3 = r9.b
            r2.e = r3
        L_0x005c:
            android.app.Activity r3 = r9.g
            r2.g = r3
        L_0x0060:
            java.util.Map r3 = r2.b
            java.util.Map r0 = r0.b
            r3.putAll(r0)
            java.lang.String[] r0 = r9.h
            if (r0 == 0) goto L_0x00d7
            android.os.Looper r0 = android.os.Looper.getMainLooper()
            android.os.Looper r3 = android.os.Looper.myLooper()
            if (r0 != r3) goto L_0x0077
            r0 = 1
            goto L_0x0078
        L_0x0077:
            r0 = 0
        L_0x0078:
            java.lang.String[] r9 = r9.h
            int r3 = r9.length
        L_0x007b:
            if (r1 >= r3) goto L_0x00d7
            r5 = r9[r1]
            java.util.Map r6 = r8.b
            java.lang.Object r5 = r6.get(r5)
            com.alibaba.baichuan.android.trade.c.a.a.b.a r5 = (com.alibaba.baichuan.android.trade.c.a.a.b.a) r5
            if (r5 == 0) goto L_0x00d4
            java.lang.String r6 = r2.e()
            com.alibaba.baichuan.android.trade.c.a.a.b.d r7 = r5.a()
            java.lang.String[] r7 = r7.h
            boolean r6 = r8.a(r6, r7)
            if (r6 == 0) goto L_0x00b9
            int r6 = r2.b()
            com.alibaba.baichuan.android.trade.c.a.a.b.d r7 = r5.a()
            int[] r7 = r7.g
            boolean r6 = r8.a(r6, r7)
            if (r6 == 0) goto L_0x00b9
            boolean r6 = r8.a(r5, r2, r0)
            if (r6 == 0) goto L_0x00b9
            com.alibaba.baichuan.android.trade.c.a.a.b r9 = new com.alibaba.baichuan.android.trade.c.a.a.b
            java.lang.String r0 = r2.d()
            r9.<init>(r4, r0)
            return r9
        L_0x00b9:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "skip_"
            r6.<init>(r7)
            com.alibaba.baichuan.android.trade.c.a.a.b.d r5 = r5.a()
            java.lang.String r5 = r5.a
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            java.lang.String r6 = "true"
            r2.a(r5, r6)
        L_0x00d4:
            int r1 = r1 + 1
            goto L_0x007b
        L_0x00d7:
            boolean r9 = r8.a(r2)
            com.alibaba.baichuan.android.trade.c.a.a.b r0 = new com.alibaba.baichuan.android.trade.c.a.a.b
            java.lang.String r1 = r2.d()
            r0.<init>(r9, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.c.a.a.e.b(com.alibaba.baichuan.android.trade.c.a.a.c.a):com.alibaba.baichuan.android.trade.c.a.a.b");
    }

    public void b() {
        if (!this.e) {
            synchronized (this) {
                if (!this.e) {
                    c();
                    d();
                    f();
                    e();
                    this.e = true;
                }
            }
        }
    }
}
