package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dli reason: default package */
/* compiled from: IntentController */
public final class dli {
    private String a;
    private final Activity b;
    private List<dlh> c;
    private List<dlh> d;
    private List<dlh> e;

    public dli(Activity activity) {
        this.b = activity;
    }

    public final synchronized void a(Intent intent) {
        a(intent, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.content.Intent r5, com.autonavi.common.Callback<java.lang.Boolean> r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 != 0) goto L_0x000c
            if (r6 == 0) goto L_0x000a
            java.lang.Boolean r5 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0030 }
            r6.callback(r5)     // Catch:{ all -> 0x0030 }
        L_0x000a:
            monitor-exit(r4)
            return
        L_0x000c:
            android.net.Uri r0 = r5.getData()     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x002b
            java.lang.String r1 = r0.toString()     // Catch:{ all -> 0x0030 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0030 }
            r2.<init>()     // Catch:{ all -> 0x0030 }
            java.lang.String r3 = "url"
            r2.put(r3, r1)     // Catch:{ JSONException -> 0x0021 }
        L_0x0021:
            java.lang.String r1 = "P00395"
            java.lang.String r3 = "B003"
            com.amap.bundle.statistics.LogManager.actionLogV2(r1, r3, r2)     // Catch:{ all -> 0x0030 }
            a(r0)     // Catch:{ all -> 0x0030 }
        L_0x002b:
            r4.b(r5, r6)     // Catch:{ all -> 0x0030 }
            monitor-exit(r4)
            return
        L_0x0030:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dli.a(android.content.Intent, com.autonavi.common.Callback):void");
    }

    private static void a(Uri uri) {
        if (uri != null && "1".equals(uri.getQueryParameter("lbpclk"))) {
            String queryParameter = uri.getQueryParameter("lbpvia");
            vw vwVar = (vw) a.a.a(vw.class);
            if (vwVar != null) {
                vwVar.a(queryParameter);
            }
        }
    }

    private synchronized void c(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String uri = data.toString();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("url", uri);
            } catch (JSONException unused) {
            }
            LogManager.actionLogV2("P00395", "B002", jSONObject);
            a(data);
        }
        b(intent, null);
    }

    public final synchronized void b(Intent intent) {
        if (intent != null) {
            if (!intent.hasExtra("com.autonavi.bundle.notification.INTENT.KEY") || !"INTENT.FROM.PUSH".equals(intent.getStringExtra("com.autonavi.bundle.notification.INTENT.KEY"))) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    String queryParameter = data.getQueryParameter(DriveUtil.SOURCE_APPLICATION);
                    if (TextUtils.isEmpty(queryParameter)) {
                        queryParameter = "NoSa";
                    }
                    String uri = data.toString();
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("url", uri);
                        jSONObject.put("sourceapplication", queryParameter);
                    } catch (JSONException unused) {
                    }
                    LogManager.actionLogV2("P00395", "B001", jSONObject);
                    a(data);
                }
                b(intent, null);
                return;
            }
            c(intent);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ce, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x011b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(android.content.Intent r4, com.autonavi.common.Callback<java.lang.Boolean> r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 != 0) goto L_0x0005
            monitor-exit(r3)
            return
        L_0x0005:
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            if (r0 != 0) goto L_0x0062
            java.util.LinkedList r0 = new java.util.LinkedList     // Catch:{ all -> 0x011c }
            r0.<init>()     // Catch:{ all -> 0x011c }
            r3.c = r0     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dlz r1 = new dlz     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dma r1 = new dma     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dmd r1 = new dmd     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dme r1 = new dme     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dlw r1 = new dlw     // Catch:{ all -> 0x011c }
            android.app.Activity r2 = r3.b     // Catch:{ all -> 0x011c }
            r1.<init>(r2)     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dlx r1 = new dlx     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dls r1 = new dls     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            dmb r1 = new dmb     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
        L_0x0062:
            java.util.List<dlh> r0 = r3.d     // Catch:{ all -> 0x011c }
            if (r0 != 0) goto L_0x0085
            java.util.LinkedList r0 = new java.util.LinkedList     // Catch:{ all -> 0x011c }
            r0.<init>()     // Catch:{ all -> 0x011c }
            r3.d = r0     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.d     // Catch:{ all -> 0x011c }
            dlu r1 = new dlu     // Catch:{ all -> 0x011c }
            android.app.Activity r2 = r3.b     // Catch:{ all -> 0x011c }
            r1.<init>(r2)     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.d     // Catch:{ all -> 0x011c }
            dlv r1 = new dlv     // Catch:{ all -> 0x011c }
            android.app.Activity r2 = r3.b     // Catch:{ all -> 0x011c }
            r1.<init>(r2)     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
        L_0x0085:
            java.util.List<dlh> r0 = r3.e     // Catch:{ all -> 0x011c }
            if (r0 != 0) goto L_0x00a6
            java.util.LinkedList r0 = new java.util.LinkedList     // Catch:{ all -> 0x011c }
            r0.<init>()     // Catch:{ all -> 0x011c }
            r3.e = r0     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.e     // Catch:{ all -> 0x011c }
            dlt r1 = new dlt     // Catch:{ all -> 0x011c }
            r1.<init>()     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.e     // Catch:{ all -> 0x011c }
            dmc r1 = new dmc     // Catch:{ all -> 0x011c }
            android.app.Activity r2 = r3.b     // Catch:{ all -> 0x011c }
            r1.<init>(r2)     // Catch:{ all -> 0x011c }
            r0.add(r1)     // Catch:{ all -> 0x011c }
        L_0x00a6:
            java.lang.String r0 = "owner"
            java.lang.String r0 = r4.getStringExtra(r0)     // Catch:{ all -> 0x011c }
            r3.a = r0     // Catch:{ all -> 0x011c }
            java.util.List<dlh> r0 = r3.c     // Catch:{ all -> 0x011c }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x011c }
        L_0x00b4:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x00cf
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x011c }
            dlh r1 = (defpackage.dlh) r1     // Catch:{ all -> 0x011c }
            boolean r1 = r1.a(r4)     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x00b4
            if (r5 == 0) goto L_0x00cd
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x011c }
            r5.callback(r4)     // Catch:{ all -> 0x011c }
        L_0x00cd:
            monitor-exit(r3)
            return
        L_0x00cf:
            java.util.List<dlh> r0 = r3.d     // Catch:{ all -> 0x011c }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x011c }
        L_0x00d5:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x00fb
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x011c }
            dlh r1 = (defpackage.dlh) r1     // Catch:{ all -> 0x011c }
            boolean r2 = r1 instanceof defpackage.dlu     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x00ea
            r2 = r1
            dlu r2 = (defpackage.dlu) r2     // Catch:{ all -> 0x011c }
            r2.a = r5     // Catch:{ all -> 0x011c }
        L_0x00ea:
            boolean r2 = r1 instanceof defpackage.dlv     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x00f3
            r2 = r1
            dlv r2 = (defpackage.dlv) r2     // Catch:{ all -> 0x011c }
            r2.b = r5     // Catch:{ all -> 0x011c }
        L_0x00f3:
            boolean r1 = r1.a(r4)     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x00d5
            monitor-exit(r3)
            return
        L_0x00fb:
            java.util.List<dlh> r0 = r3.e     // Catch:{ all -> 0x011c }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x011c }
        L_0x0101:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x0113
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x011c }
            dlh r1 = (defpackage.dlh) r1     // Catch:{ all -> 0x011c }
            boolean r1 = r1.a(r4)     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x0101
        L_0x0113:
            if (r5 == 0) goto L_0x011a
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x011c }
            r5.callback(r4)     // Catch:{ all -> 0x011c }
        L_0x011a:
            monitor-exit(r3)
            return
        L_0x011c:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dli.b(android.content.Intent, com.autonavi.common.Callback):void");
    }

    public final boolean a() {
        if ("banner".equals(this.a) || BaseIntentDispatcher.INTENT_CALL_OWNER_UMENG_PUSH.equals(this.a) || BaseIntentDispatcher.INTENT_CALL_SPLASH.equals(this.a) || BaseIntentDispatcher.INTENT_CALL_HOTWORD.equals(this.a) || BaseIntentDispatcher.INTENT_CALL_DIRCTJUMP.equals(this.a) || "js".equals(this.a) || BaseIntentDispatcher.INTENT_CALL_FROMOWNER.equals(this.a) || BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE.equals(this.a) || BaseIntentDispatcher.INTENT_CALL_APP_XIAOMI.equals(NetworkParam.getSa()) || NetworkParam.getSa() == null) {
            return false;
        }
        return true;
    }
}
