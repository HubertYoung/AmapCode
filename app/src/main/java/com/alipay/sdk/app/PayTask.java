package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.sdk.data.c;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.i;
import com.alipay.sdk.util.j;
import com.alipay.sdk.util.l;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class PayTask {
    static final Object a = e.class;
    private Activity b;
    private com.alipay.sdk.widget.a c;
    private String d = "wappaygw.alipay.com/service/rest.htm";
    private String e = "mclient.alipay.com/service/rest.htm";
    private String f = "mclient.alipay.com/home/exterfaceAssign.htm";
    private Map<String, a> g = new HashMap();

    class a {
        String a;
        String b;

        private a() {
            this.a = "";
            this.b = "";
        }

        /* synthetic */ a(PayTask payTask, byte b2) {
            this();
        }

        private String a() {
            return this.a;
        }

        private void a(String str) {
            this.a = str;
        }

        private String b() {
            return this.b;
        }

        private void b(String str) {
            this.b = str;
        }
    }

    public String getVersion() {
        return com.alipay.sdk.cons.a.f;
    }

    public PayTask(Activity activity) {
        this.b = activity;
        b a2 = b.a();
        Activity activity2 = this.b;
        c.a();
        a2.a((Context) activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.c = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.b);
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0141 */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0094 A[Catch:{ Throwable -> 0x011d, Throwable -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0096 A[Catch:{ Throwable -> 0x011d, Throwable -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0117 A[Catch:{ Throwable -> 0x011d, Throwable -> 0x0141 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String pay(java.lang.String r11, boolean r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            if (r12 == 0) goto L_0x000a
            r10.b()     // Catch:{ all -> 0x0007 }
            goto L_0x000a
        L_0x0007:
            r11 = move-exception
            goto L_0x0178
        L_0x000a:
            java.lang.String r12 = "service=alipay.acquire.mr.ord.createandpay"
            boolean r12 = r11.contains(r12)     // Catch:{ all -> 0x0007 }
            r0 = 1
            if (r12 == 0) goto L_0x0016
            com.alipay.sdk.cons.a.r = r0     // Catch:{ all -> 0x0007 }
        L_0x0016:
            boolean r12 = com.alipay.sdk.cons.a.r     // Catch:{ all -> 0x0007 }
            if (r12 == 0) goto L_0x0043
            java.lang.String r12 = "https://wappaygw.alipay.com/home/exterfaceAssign.htm?"
            boolean r12 = r11.startsWith(r12)     // Catch:{ all -> 0x0007 }
            if (r12 == 0) goto L_0x002f
            java.lang.String r12 = "https://wappaygw.alipay.com/home/exterfaceAssign.htm?"
            int r12 = r11.indexOf(r12)     // Catch:{ all -> 0x0007 }
            int r12 = r12 + 53
            java.lang.String r11 = r11.substring(r12)     // Catch:{ all -> 0x0007 }
            goto L_0x0043
        L_0x002f:
            java.lang.String r12 = "https://mclient.alipay.com/home/exterfaceAssign.htm?"
            boolean r12 = r11.startsWith(r12)     // Catch:{ all -> 0x0007 }
            if (r12 == 0) goto L_0x0043
            java.lang.String r12 = "https://mclient.alipay.com/home/exterfaceAssign.htm?"
            int r12 = r11.indexOf(r12)     // Catch:{ all -> 0x0007 }
            int r12 = r12 + 52
            java.lang.String r11 = r11.substring(r12)     // Catch:{ all -> 0x0007 }
        L_0x0043:
            com.alipay.sdk.sys.a r12 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x0141 }
            android.app.Activity r1 = r10.b     // Catch:{ Throwable -> 0x0141 }
            r12.<init>(r1)     // Catch:{ Throwable -> 0x0141 }
            java.lang.String r12 = r12.a(r11)     // Catch:{ Throwable -> 0x0141 }
            java.lang.String r1 = "paymethod=\"expressGateway\""
            boolean r1 = r12.contains(r1)     // Catch:{ Throwable -> 0x0141 }
            r2 = 0
            if (r1 != 0) goto L_0x0084
            android.app.Activity r1 = r10.b     // Catch:{ Throwable -> 0x0141 }
            boolean r1 = com.alipay.sdk.util.l.c(r1)     // Catch:{ Throwable -> 0x0141 }
            if (r1 == 0) goto L_0x0084
            com.alipay.sdk.util.e r1 = new com.alipay.sdk.util.e     // Catch:{ Throwable -> 0x0141 }
            android.app.Activity r3 = r10.b     // Catch:{ Throwable -> 0x0141 }
            com.alipay.sdk.app.h r4 = new com.alipay.sdk.app.h     // Catch:{ Throwable -> 0x0141 }
            r4.<init>(r10)     // Catch:{ Throwable -> 0x0141 }
            r1.<init>(r3, r4)     // Catch:{ Throwable -> 0x0141 }
            java.lang.String r3 = r1.a(r12)     // Catch:{ Throwable -> 0x0141 }
            r1.a = r2     // Catch:{ Throwable -> 0x0141 }
            java.lang.String r1 = "failed"
            boolean r1 = android.text.TextUtils.equals(r3, r1)     // Catch:{ Throwable -> 0x0141 }
            if (r1 != 0) goto L_0x0084
            boolean r12 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0141 }
            if (r12 == 0) goto L_0x0088
            java.lang.String r3 = com.alipay.sdk.app.i.a()     // Catch:{ Throwable -> 0x0141 }
            goto L_0x0088
        L_0x0084:
            java.lang.String r3 = r10.b(r12)     // Catch:{ Throwable -> 0x0141 }
        L_0x0088:
            android.app.Activity r12 = r10.b     // Catch:{ Throwable -> 0x0141 }
            android.content.Context r12 = r12.getApplicationContext()     // Catch:{ Throwable -> 0x0141 }
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x011d }
            if (r1 == 0) goto L_0x0096
            goto L_0x0111
        L_0x0096:
            java.lang.String r1 = ";"
            java.lang.String[] r1 = r3.split(r1)     // Catch:{ Throwable -> 0x011d }
            r4 = 0
            r5 = r2
            r2 = 0
        L_0x009f:
            int r6 = r1.length     // Catch:{ Throwable -> 0x011d }
            if (r2 >= r6) goto L_0x0110
            r6 = r1[r2]     // Catch:{ Throwable -> 0x011d }
            java.lang.String r7 = "result={"
            boolean r6 = r6.startsWith(r7)     // Catch:{ Throwable -> 0x011d }
            if (r6 == 0) goto L_0x010d
            r6 = r1[r2]     // Catch:{ Throwable -> 0x011d }
            java.lang.String r7 = "}"
            boolean r6 = r6.endsWith(r7)     // Catch:{ Throwable -> 0x011d }
            if (r6 == 0) goto L_0x010d
            r6 = r1[r2]     // Catch:{ Throwable -> 0x011d }
            r7 = 8
            r8 = r1[r2]     // Catch:{ Throwable -> 0x011d }
            int r8 = r8.length()     // Catch:{ Throwable -> 0x011d }
            int r8 = r8 - r0
            java.lang.String r6 = r6.substring(r7, r8)     // Catch:{ Throwable -> 0x011d }
            java.lang.String r7 = "&"
            java.lang.String[] r6 = r6.split(r7)     // Catch:{ Throwable -> 0x011d }
            r7 = 0
        L_0x00ce:
            int r8 = r6.length     // Catch:{ Throwable -> 0x011d }
            if (r7 >= r8) goto L_0x010d
            r8 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            java.lang.String r9 = "trade_token=\""
            boolean r8 = r8.startsWith(r9)     // Catch:{ Throwable -> 0x011d }
            if (r8 == 0) goto L_0x00f6
            r8 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            java.lang.String r9 = "\""
            boolean r8 = r8.endsWith(r9)     // Catch:{ Throwable -> 0x011d }
            if (r8 == 0) goto L_0x00f6
            r5 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            r8 = 13
            r6 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            int r6 = r6.length()     // Catch:{ Throwable -> 0x011d }
            int r6 = r6 - r0
            java.lang.String r5 = r5.substring(r8, r6)     // Catch:{ Throwable -> 0x011d }
            goto L_0x010d
        L_0x00f6:
            r8 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            java.lang.String r9 = "trade_token="
            boolean r8 = r8.startsWith(r9)     // Catch:{ Throwable -> 0x011d }
            if (r8 == 0) goto L_0x010a
            r5 = r6[r7]     // Catch:{ Throwable -> 0x011d }
            r6 = 12
            java.lang.String r5 = r5.substring(r6)     // Catch:{ Throwable -> 0x011d }
            goto L_0x010d
        L_0x010a:
            int r7 = r7 + 1
            goto L_0x00ce
        L_0x010d:
            int r2 = r2 + 1
            goto L_0x009f
        L_0x0110:
            r2 = r5
        L_0x0111:
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x011d }
            if (r0 != 0) goto L_0x0125
            java.lang.String r0 = "pref_trade_token"
            com.alipay.sdk.util.i.a(r12, r0, r2)     // Catch:{ Throwable -> 0x011d }
            goto L_0x0125
        L_0x011d:
            r12 = move-exception
            java.lang.String r0 = "biz"
            java.lang.String r1 = "SaveTradeTokenError"
            com.alipay.sdk.app.statistic.a.a(r0, r1, r12)     // Catch:{ Throwable -> 0x0141 }
        L_0x0125:
            com.alipay.sdk.data.a r12 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x0007 }
            android.app.Activity r0 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ all -> 0x0007 }
            r12.a(r0)     // Catch:{ all -> 0x0007 }
            r10.c()     // Catch:{ all -> 0x0007 }
            android.app.Activity r12 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r12 = r12.getApplicationContext()     // Catch:{ all -> 0x0007 }
        L_0x013b:
            com.alipay.sdk.app.statistic.a.b(r12, r11)     // Catch:{ all -> 0x0007 }
            goto L_0x015c
        L_0x013f:
            r12 = move-exception
            goto L_0x015e
        L_0x0141:
            java.lang.String r3 = com.alipay.sdk.app.i.a()     // Catch:{ all -> 0x013f }
            com.alipay.sdk.data.a r12 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x0007 }
            android.app.Activity r0 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ all -> 0x0007 }
            r12.a(r0)     // Catch:{ all -> 0x0007 }
            r10.c()     // Catch:{ all -> 0x0007 }
            android.app.Activity r12 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r12 = r12.getApplicationContext()     // Catch:{ all -> 0x0007 }
            goto L_0x013b
        L_0x015c:
            monitor-exit(r10)
            return r3
        L_0x015e:
            com.alipay.sdk.data.a r0 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x0007 }
            android.app.Activity r1 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ all -> 0x0007 }
            r0.a(r1)     // Catch:{ all -> 0x0007 }
            r10.c()     // Catch:{ all -> 0x0007 }
            android.app.Activity r0 = r10.b     // Catch:{ all -> 0x0007 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ all -> 0x0007 }
            com.alipay.sdk.app.statistic.a.b(r0, r11)     // Catch:{ all -> 0x0007 }
            throw r12     // Catch:{ all -> 0x0007 }
        L_0x0178:
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.pay(java.lang.String, boolean):java.lang.String");
    }

    public synchronized Map<String, String> payV2(String str, boolean z) {
        return j.a(pay(str, z));
    }

    public synchronized String fetchTradeToken() {
        return i.b(this.b.getApplicationContext(), h.a, "");
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z, H5PayCallback h5PayCallback) {
        String fetchOrderInfoFromH5PayUrl;
        fetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
        if (!TextUtils.isEmpty(fetchOrderInfoFromH5PayUrl)) {
            new Thread(new g(this, fetchOrderInfoFromH5PayUrl, z, h5PayCallback)).start();
        }
        return !TextUtils.isEmpty(fetchOrderInfoFromH5PayUrl);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00c8, code lost:
        if (r0.startsWith(r1.toString()) != false) goto L_0x00ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x015b, code lost:
        if (r0.startsWith(r1.toString()) != false) goto L_0x015d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0035, code lost:
        if (r0.startsWith(r1.toString()) != false) goto L_0x0037;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String fetchOrderInfoFromH5PayUrl(java.lang.String r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            boolean r0 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0346 }
            if (r0 != 0) goto L_0x0346
            java.lang.String r0 = r10.trim()     // Catch:{ Throwable -> 0x0346 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "https://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.d     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x0037
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "http://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.d     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 == 0) goto L_0x009e
        L_0x0037:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "(http|https)://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.d     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "\\?"
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = ""
            java.lang.String r1 = r0.replaceFirst(r1, r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0346 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r2 != 0) goto L_0x009e
            java.util.Map r10 = com.alipay.sdk.util.l.a(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "req_data"
            java.lang.Object r10 = r10.get(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "<request_token>"
            java.lang.String r1 = "</request_token>"
            java.lang.String r10 = com.alipay.sdk.util.l.a(r0, r1, r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "_input_charset=\"utf-8\"&ordertoken=\""
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0346 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\""
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            com.alipay.sdk.sys.a r10 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x0346 }
            android.app.Activity r1 = r9.b     // Catch:{ Throwable -> 0x0346 }
            r10.<init>(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "sc"
            java.lang.String r2 = "h5tonative"
            java.lang.String r10 = r10.a(r1, r2)     // Catch:{ Throwable -> 0x0346 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = "\""
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = r0.toString()     // Catch:{ Throwable -> 0x0346 }
            monitor-exit(r9)
            return r10
        L_0x009e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "https://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.e     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x00ca
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "http://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.e     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 == 0) goto L_0x0131
        L_0x00ca:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "(http|https)://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.e     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "\\?"
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = ""
            java.lang.String r1 = r0.replaceFirst(r1, r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0346 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r2 != 0) goto L_0x0131
            java.util.Map r10 = com.alipay.sdk.util.l.a(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "req_data"
            java.lang.Object r10 = r10.get(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "<request_token>"
            java.lang.String r1 = "</request_token>"
            java.lang.String r10 = com.alipay.sdk.util.l.a(r0, r1, r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "_input_charset=\"utf-8\"&ordertoken=\""
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0346 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\""
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            com.alipay.sdk.sys.a r10 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x0346 }
            android.app.Activity r1 = r9.b     // Catch:{ Throwable -> 0x0346 }
            r10.<init>(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "sc"
            java.lang.String r2 = "h5tonative"
            java.lang.String r10 = r10.a(r1, r2)     // Catch:{ Throwable -> 0x0346 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = "\""
            r0.append(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = r0.toString()     // Catch:{ Throwable -> 0x0346 }
            monitor-exit(r9)
            return r10
        L_0x0131:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "https://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.f     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x015d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "http://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.f     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 == 0) goto L_0x01c6
        L_0x015d:
            java.lang.String r1 = "alipay.wap.create.direct.pay.by.user"
            boolean r1 = r0.contains(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x016d
            java.lang.String r1 = "create_forex_trade_wap"
            boolean r1 = r0.contains(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 == 0) goto L_0x01c6
        L_0x016d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "(http|https)://"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = r9.f     // Catch:{ Throwable -> 0x0346 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "\\?"
            r1.append(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = ""
            java.lang.String r1 = r0.replaceFirst(r1, r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x01c6
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x01c6 }
            r1.<init>()     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r2 = "url"
            r1.put(r2, r10)     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r2 = "bizcontext"
            com.alipay.sdk.sys.a r3 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x01c6 }
            android.app.Activity r4 = r9.b     // Catch:{ Throwable -> 0x01c6 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r4 = "sc"
            java.lang.String r5 = "h5tonative"
            java.lang.String r3 = r3.a(r4, r5)     // Catch:{ Throwable -> 0x01c6 }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01c6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r3 = "new_external_info=="
            r2.<init>(r3)     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x01c6 }
            r2.append(r1)     // Catch:{ Throwable -> 0x01c6 }
            java.lang.String r1 = r2.toString()     // Catch:{ Throwable -> 0x01c6 }
            monitor-exit(r9)
            return r1
        L_0x01c6:
            java.lang.String r1 = "^(http|https)://(maliprod\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mali\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mclient\\.alipay\\.com\\/w\\/trade_pay\\.do.?)"
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ Throwable -> 0x0346 }
            java.util.regex.Matcher r1 = r1.matcher(r10)     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = r1.find()     // Catch:{ Throwable -> 0x0346 }
            r2 = 0
            if (r1 == 0) goto L_0x02fd
            java.lang.String r1 = "?"
            java.lang.String r3 = ""
            java.lang.String r10 = com.alipay.sdk.util.l.a(r1, r3, r10)     // Catch:{ Throwable -> 0x0346 }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0346 }
            if (r1 != 0) goto L_0x02fd
            java.util.Map r10 = com.alipay.sdk.util.l.a(r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            r1.<init>()     // Catch:{ Throwable -> 0x0346 }
            r3 = 0
            r4 = 1
            java.lang.String r5 = "trade_no"
            java.lang.String r6 = "trade_no"
            java.lang.String r7 = "alipay_trade_no"
            java.lang.String[] r8 = new java.lang.String[]{r6, r7}     // Catch:{ Throwable -> 0x0346 }
            r6 = r1
            r7 = r10
            boolean r3 = a(r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0346 }
            if (r3 == 0) goto L_0x02fd
            r3 = 1
            r4 = 0
            java.lang.String r5 = "pay_phase_id"
            java.lang.String r0 = "payPhaseId"
            java.lang.String r6 = "pay_phase_id"
            java.lang.String r7 = "out_relation_id"
            java.lang.String[] r8 = new java.lang.String[]{r0, r6, r7}     // Catch:{ Throwable -> 0x0346 }
            r6 = r1
            r7 = r10
            a(r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "&biz_sub_type=\"TRADE\""
            r1.append(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "&biz_type=\"trade\""
            r1.append(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "app_name"
            java.lang.Object r0 = r10.get(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0346 }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0346 }
            if (r3 == 0) goto L_0x0240
            java.lang.String r3 = "cid"
            java.lang.Object r3 = r10.get(r3)     // Catch:{ Throwable -> 0x0346 }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ Throwable -> 0x0346 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0346 }
            if (r3 != 0) goto L_0x0240
            java.lang.String r0 = "ali1688"
            goto L_0x0267
        L_0x0240:
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0346 }
            if (r3 == 0) goto L_0x0267
            java.lang.String r3 = "sid"
            java.lang.Object r3 = r10.get(r3)     // Catch:{ Throwable -> 0x0346 }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ Throwable -> 0x0346 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0346 }
            if (r3 == 0) goto L_0x0264
            java.lang.String r3 = "s_id"
            java.lang.Object r3 = r10.get(r3)     // Catch:{ Throwable -> 0x0346 }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ Throwable -> 0x0346 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0346 }
            if (r3 != 0) goto L_0x0267
        L_0x0264:
            java.lang.String r0 = "tb"
        L_0x0267:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r4 = "&app_name=\""
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0346 }
            r3.append(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "\""
            r3.append(r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x0346 }
            r1.append(r0)     // Catch:{ Throwable -> 0x0346 }
            r3 = 1
            r4 = 1
            java.lang.String r5 = "extern_token"
            java.lang.String r0 = "extern_token"
            java.lang.String r6 = "cid"
            java.lang.String r7 = "sid"
            java.lang.String r8 = "s_id"
            java.lang.String[] r8 = new java.lang.String[]{r0, r6, r7, r8}     // Catch:{ Throwable -> 0x0346 }
            r6 = r1
            r7 = r10
            boolean r0 = a(r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0346 }
            if (r0 != 0) goto L_0x029b
            java.lang.String r10 = ""
            monitor-exit(r9)
            return r10
        L_0x029b:
            r3 = 1
            r4 = 0
            java.lang.String r5 = "appenv"
            java.lang.String r0 = "appenv"
            java.lang.String[] r8 = new java.lang.String[]{r0}     // Catch:{ Throwable -> 0x0346 }
            r6 = r1
            r7 = r10
            a(r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "&pay_channel_id=\"alipay_sdk\""
            r1.append(r0)     // Catch:{ Throwable -> 0x0346 }
            com.alipay.sdk.app.PayTask$a r0 = new com.alipay.sdk.app.PayTask$a     // Catch:{ Throwable -> 0x0346 }
            r0.<init>(r9, r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "return_url"
            java.lang.Object r2 = r10.get(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0346 }
            r0.a = r2     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "pay_order_id"
            java.lang.Object r10 = r10.get(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Throwable -> 0x0346 }
            r0.b = r10     // Catch:{ Throwable -> 0x0346 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0346 }
            r10.<init>()     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            r10.append(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "&bizcontext=\""
            r10.append(r1)     // Catch:{ Throwable -> 0x0346 }
            com.alipay.sdk.sys.a r1 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x0346 }
            android.app.Activity r2 = r9.b     // Catch:{ Throwable -> 0x0346 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r2 = "sc"
            java.lang.String r3 = "h5tonative"
            java.lang.String r1 = r1.a(r2, r3)     // Catch:{ Throwable -> 0x0346 }
            r10.append(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "\""
            r10.append(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0346 }
            java.util.Map<java.lang.String, com.alipay.sdk.app.PayTask$a> r1 = r9.g     // Catch:{ Throwable -> 0x0346 }
            r1.put(r10, r0)     // Catch:{ Throwable -> 0x0346 }
            monitor-exit(r9)
            return r10
        L_0x02fd:
            java.lang.String r10 = "mclient.alipay.com/cashier/mobilepay.htm"
            boolean r10 = r0.contains(r10)     // Catch:{ Throwable -> 0x0346 }
            if (r10 != 0) goto L_0x0313
            boolean r10 = com.alipay.sdk.app.EnvUtils.isSandBox()     // Catch:{ Throwable -> 0x0346 }
            if (r10 == 0) goto L_0x0346
            java.lang.String r10 = "mobileclientgw.alipaydev.com/cashier/mobilepay.htm"
            boolean r10 = r0.contains(r10)     // Catch:{ Throwable -> 0x0346 }
            if (r10 == 0) goto L_0x0346
        L_0x0313:
            com.alipay.sdk.sys.a r10 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x0346 }
            android.app.Activity r1 = r9.b     // Catch:{ Throwable -> 0x0346 }
            r10.<init>(r1)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = "sc"
            java.lang.String r3 = "h5tonative"
            java.lang.String r10 = r10.a(r1, r3)     // Catch:{ Throwable -> 0x0346 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0346 }
            r1.<init>()     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r3 = "url"
            r1.put(r3, r0)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r0 = "bizcontext"
            r1.put(r0, r10)     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = "new_external_info==%s"
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0346 }
            r0[r2] = r1     // Catch:{ Throwable -> 0x0346 }
            java.lang.String r10 = java.lang.String.format(r10, r0)     // Catch:{ Throwable -> 0x0346 }
            monitor-exit(r9)
            return r10
        L_0x0344:
            r10 = move-exception
            goto L_0x034a
        L_0x0346:
            java.lang.String r10 = ""
            monitor-exit(r9)
            return r10
        L_0x034a:
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.fetchOrderInfoFromH5PayUrl(java.lang.String):java.lang.String");
    }

    private static boolean a(boolean z, boolean z2, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2 = "";
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str3 = strArr[i];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i++;
        }
        if (TextUtils.isEmpty(str2)) {
            if (z2) {
                return false;
            }
        } else if (z) {
            sb.append("&");
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
        } else {
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
        }
        return true;
    }

    public synchronized H5PayResultModel h5Pay(String str, boolean z) {
        H5PayResultModel h5PayResultModel;
        h5PayResultModel = new H5PayResultModel();
        try {
            str.trim();
            String[] split = pay(str, z).split(";");
            HashMap hashMap = new HashMap();
            for (String str2 : split) {
                String substring = str2.substring(0, str2.indexOf("={"));
                StringBuilder sb = new StringBuilder();
                sb.append(substring);
                sb.append("={");
                String sb2 = sb.toString();
                hashMap.put(substring, str2.substring(str2.indexOf(sb2) + sb2.length(), str2.lastIndexOf(h.d)));
            }
            if (hashMap.containsKey(j.a)) {
                h5PayResultModel.setResultCode((String) hashMap.get(j.a));
            }
            if (hashMap.containsKey(Constants.SERVICE_CALLBACK)) {
                h5PayResultModel.setReturnUrl((String) hashMap.get(Constants.SERVICE_CALLBACK));
            } else if (hashMap.containsKey("result")) {
                String str3 = (String) hashMap.get("result");
                if (str3.length() > 15) {
                    a aVar = this.g.get(str);
                    if (aVar != null) {
                        if (TextUtils.isEmpty(aVar.b)) {
                            h5PayResultModel.setReturnUrl(aVar.a);
                        } else {
                            h5PayResultModel.setReturnUrl(com.alipay.sdk.data.a.b().j.replace("$OrderId$", aVar.b));
                        }
                        this.g.remove(str);
                        return h5PayResultModel;
                    }
                    String a2 = l.a((String) "&callBackUrl=\"", (String) "\"", str3);
                    if (TextUtils.isEmpty(a2)) {
                        a2 = l.a((String) "&call_back_url=\"", (String) "\"", str3);
                        if (TextUtils.isEmpty(a2)) {
                            a2 = l.a((String) com.alipay.sdk.cons.a.o, (String) "\"", str3);
                            if (TextUtils.isEmpty(a2)) {
                                a2 = URLDecoder.decode(l.a((String) com.alipay.sdk.cons.a.p, (String) "&", str3), "utf-8");
                                if (TextUtils.isEmpty(a2)) {
                                    a2 = URLDecoder.decode(l.a((String) "&callBackUrl=", (String) "&", str3), "utf-8");
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(a2) && !TextUtils.isEmpty(str3) && str3.contains("call_back_url")) {
                        a2 = l.b("call_back_url=\"", "\"", str3);
                    }
                    if (TextUtils.isEmpty(a2)) {
                        a2 = com.alipay.sdk.data.a.b().j;
                    }
                    h5PayResultModel.setReturnUrl(a2);
                } else {
                    a aVar2 = this.g.get(str);
                    if (aVar2 != null) {
                        h5PayResultModel.setReturnUrl(aVar2.a);
                        this.g.remove(str);
                        return h5PayResultModel;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return h5PayResultModel;
    }

    private static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("={");
        String sb2 = sb.toString();
        return str.substring(str.indexOf(sb2) + sb2.length(), str.lastIndexOf(h.d));
    }

    private com.alipay.sdk.util.e.a a() {
        return new h(this);
    }

    private void b() {
        if (this.c != null) {
            this.c.a();
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
    }

    private String a(String str) {
        String a2 = new com.alipay.sdk.sys.a(this.b).a(str);
        if (a2.contains("paymethod=\"expressGateway\"")) {
            return b(a2);
        }
        if (!l.c((Context) this.b)) {
            return b(a2);
        }
        e eVar = new e(this.b, new h(this));
        String a3 = eVar.a(a2);
        eVar.a = null;
        if (TextUtils.equals(a3, e.b)) {
            return b(a2);
        }
        return TextUtils.isEmpty(a3) ? i.a() : a3;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x009b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(java.lang.String r10) {
        /*
            r9 = this;
            r9.b()
            com.alipay.sdk.packet.impl.e r0 = new com.alipay.sdk.packet.impl.e     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r0.<init>()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            android.app.Activity r1 = r9.b     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.packet.b r10 = r0.a(r1, r10)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            org.json.JSONObject r10 = r10.a()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            java.lang.String r0 = "form"
            org.json.JSONObject r10 = r10.optJSONObject(r0)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            java.lang.String r0 = "onload"
            org.json.JSONObject r10 = r10.optJSONObject(r0)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            java.util.List r10 = com.alipay.sdk.protocol.b.a(r10)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r0 = 0
            r1 = 0
        L_0x0028:
            int r2 = r10.size()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r1 >= r2) goto L_0x00a7
            java.lang.Object r2 = r10.get(r1)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.b r2 = (com.alipay.sdk.protocol.b) r2     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.a r2 = r2.a     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.a r3 = com.alipay.sdk.protocol.a.Update     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r2 != r3) goto L_0x00a4
            java.lang.Object r2 = r10.get(r1)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.b r2 = (com.alipay.sdk.protocol.b) r2     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            java.lang.String[] r2 = r2.b     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            int r3 = r2.length     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r4 = 3
            if (r3 != r4) goto L_0x00a4
            java.lang.String r3 = "tid"
            r4 = r2[r0]     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r3 == 0) goto L_0x00a4
            com.alipay.sdk.sys.b r3 = com.alipay.sdk.sys.b.a()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            android.content.Context r3 = r3.a     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.tid.b r4 = com.alipay.sdk.tid.b.a()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.sys.b r5 = com.alipay.sdk.sys.b.a()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            android.content.Context r5 = r5.a     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.tid.c r5 = com.alipay.sdk.tid.c.a(r5)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r6 = 1
            r7 = r2[r6]     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r7 != 0) goto L_0x00a4
            r7 = 2
            r8 = r2[r7]     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r8 == 0) goto L_0x0078
            goto L_0x00a4
        L_0x0078:
            r6 = r2[r6]     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r2 = r2[r7]     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r5.a(r6, r2)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.tid.a r2 = new com.alipay.sdk.tid.a     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r2.<init>(r3)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.util.a r5 = com.alipay.sdk.util.a.a(r3)     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            java.lang.String r5 = r5.a()     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            com.alipay.sdk.util.a r3 = com.alipay.sdk.util.a.a(r3)     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            java.lang.String r3 = r3.b()     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            java.lang.String r6 = r4.a     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            java.lang.String r4 = r4.b     // Catch:{ Exception -> 0x009b, all -> 0x009f }
            r2.a(r5, r3, r6, r4)     // Catch:{ Exception -> 0x009b, all -> 0x009f }
        L_0x009b:
            r2.close()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            goto L_0x00a4
        L_0x009f:
            r10 = move-exception
            r2.close()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            throw r10     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
        L_0x00a4:
            int r1 = r1 + 1
            goto L_0x0028
        L_0x00a7:
            r9.c()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
        L_0x00aa:
            int r1 = r10.size()     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r0 >= r1) goto L_0x00d7
            java.lang.Object r1 = r10.get(r0)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.b r1 = (com.alipay.sdk.protocol.b) r1     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.a r1 = r1.a     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.a r2 = com.alipay.sdk.protocol.a.WapPay     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            if (r1 != r2) goto L_0x00ca
            java.lang.Object r10 = r10.get(r0)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            com.alipay.sdk.protocol.b r10 = (com.alipay.sdk.protocol.b) r10     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            java.lang.String r10 = r9.a(r10)     // Catch:{ IOException -> 0x00dc, Throwable -> 0x00cf }
            r9.c()
            return r10
        L_0x00ca:
            int r0 = r0 + 1
            goto L_0x00aa
        L_0x00cd:
            r10 = move-exception
            goto L_0x0103
        L_0x00cf:
            r10 = move-exception
            java.lang.String r0 = "biz"
            java.lang.String r1 = "H5PayDataAnalysisError"
            com.alipay.sdk.app.statistic.a.a(r0, r1, r10)     // Catch:{ all -> 0x00cd }
        L_0x00d7:
            r9.c()
            r10 = 0
            goto L_0x00ee
        L_0x00dc:
            r10 = move-exception
            com.alipay.sdk.app.j r0 = com.alipay.sdk.app.j.NETWORK_ERROR     // Catch:{ all -> 0x00cd }
            int r0 = r0.h     // Catch:{ all -> 0x00cd }
            com.alipay.sdk.app.j r0 = com.alipay.sdk.app.j.a(r0)     // Catch:{ all -> 0x00cd }
            java.lang.String r1 = "net"
            com.alipay.sdk.app.statistic.a.a(r1, r10)     // Catch:{ all -> 0x00cd }
            r9.c()
            r10 = r0
        L_0x00ee:
            if (r10 != 0) goto L_0x00f8
            com.alipay.sdk.app.j r10 = com.alipay.sdk.app.j.FAILED
            int r10 = r10.h
            com.alipay.sdk.app.j r10 = com.alipay.sdk.app.j.a(r10)
        L_0x00f8:
            int r0 = r10.h
            java.lang.String r10 = r10.i
            java.lang.String r1 = ""
            java.lang.String r10 = com.alipay.sdk.app.i.a(r0, r10, r1)
            return r10
        L_0x0103:
            r9.c()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.b(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:14|15|16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003c, code lost:
        if (android.text.TextUtils.isEmpty(r5) == false) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003e, code lost:
        r5 = com.alipay.sdk.app.i.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0042, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0 = com.alipay.sdk.app.i.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0036, code lost:
        r5 = com.alipay.sdk.app.i.a;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0045 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(com.alipay.sdk.protocol.b r5) {
        /*
            r4 = this;
            java.lang.String[] r5 = r5.b
            android.content.Intent r0 = new android.content.Intent
            android.app.Activity r1 = r4.b
            java.lang.Class<com.alipay.sdk.app.H5PayActivity> r2 = com.alipay.sdk.app.H5PayActivity.class
            r0.<init>(r1, r2)
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            r2 = 0
            r2 = r5[r2]
            java.lang.String r3 = "url"
            r1.putString(r3, r2)
            int r2 = r5.length
            r3 = 2
            if (r2 != r3) goto L_0x0025
            r2 = 1
            r5 = r5[r2]
            java.lang.String r2 = "cookie"
            r1.putString(r2, r5)
        L_0x0025:
            r0.putExtras(r1)
            android.app.Activity r5 = r4.b
            r5.startActivity(r0)
            java.lang.Object r5 = a
            monitor-enter(r5)
            java.lang.Object r0 = a     // Catch:{ InterruptedException -> 0x0045 }
            r0.wait()     // Catch:{ InterruptedException -> 0x0045 }
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
            java.lang.String r5 = com.alipay.sdk.app.i.a
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x0042
            java.lang.String r5 = com.alipay.sdk.app.i.a()
        L_0x0042:
            return r5
        L_0x0043:
            r0 = move-exception
            goto L_0x004b
        L_0x0045:
            java.lang.String r0 = com.alipay.sdk.app.i.a()     // Catch:{ all -> 0x0043 }
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
            return r0
        L_0x004b:
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(com.alipay.sdk.protocol.b):java.lang.String");
    }
}
