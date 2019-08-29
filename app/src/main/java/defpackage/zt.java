package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zt reason: default package */
/* compiled from: SceneManager */
public final class zt {
    public static boolean a = bno.a;
    private static zt b;
    private long c;
    private long d;
    private Stack<String> e = new Stack<>();
    private int f = 0;
    private boolean g = true;
    private List<zv> h = new ArrayList();
    private i i;

    private zt() {
    }

    public static synchronized zt a() {
        zt ztVar;
        synchronized (zt.class) {
            try {
                if (b == null) {
                    b = new zt();
                }
                ztVar = b;
            }
        }
        return ztVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(defpackage.zv r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.g     // Catch:{ all -> 0x002b }
            if (r0 == 0) goto L_0x0029
            int r0 = r6.f     // Catch:{ all -> 0x002b }
            if (r0 != 0) goto L_0x000a
            goto L_0x0029
        L_0x000a:
            boolean r0 = r6.c()     // Catch:{ all -> 0x002b }
            if (r0 == 0) goto L_0x0015
            java.util.List<zv> r0 = r6.h     // Catch:{ all -> 0x002b }
            r0.add(r7)     // Catch:{ all -> 0x002b }
        L_0x0015:
            long r0 = r6.c     // Catch:{ all -> 0x002b }
            long r2 = r7.c     // Catch:{ all -> 0x002b }
            long r4 = r7.b     // Catch:{ all -> 0x002b }
            r7 = 0
            long r2 = r2 + r4
            long r0 = r0 + r2
            r6.c = r0     // Catch:{ all -> 0x002b }
            long r0 = r6.d     // Catch:{ all -> 0x002b }
            r2 = 1
            long r0 = r0 + r2
            r6.d = r0     // Catch:{ all -> 0x002b }
            monitor-exit(r6)
            return
        L_0x0029:
            monitor-exit(r6)
            return
        L_0x002b:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zt.a(zv):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r4, boolean r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = a     // Catch:{ all -> 0x0070 }
            if (r0 == 0) goto L_0x0025
            java.lang.String r0 = "SceneManagerhttplog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0070 }
            java.lang.String r2 = "changeScene ["
            r1.<init>(r2)     // Catch:{ all -> 0x0070 }
            r1.append(r4)     // Catch:{ all -> 0x0070 }
            java.lang.String r2 = "] enterScene ["
            r1.append(r2)     // Catch:{ all -> 0x0070 }
            r1.append(r5)     // Catch:{ all -> 0x0070 }
            java.lang.String r2 = "]"
            r1.append(r2)     // Catch:{ all -> 0x0070 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0070 }
            com.amap.bundle.logs.AMapLog.e(r0, r1)     // Catch:{ all -> 0x0070 }
        L_0x0025:
            if (r5 == 0) goto L_0x004e
            java.lang.String r5 = ""
            java.util.Stack<java.lang.String> r0 = r3.e     // Catch:{ all -> 0x0070 }
            int r0 = r0.size()     // Catch:{ all -> 0x0070 }
            if (r0 <= 0) goto L_0x0039
            java.util.Stack<java.lang.String> r5 = r3.e     // Catch:{ all -> 0x0070 }
            java.lang.Object r5 = r5.peek()     // Catch:{ all -> 0x0070 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x0070 }
        L_0x0039:
            boolean r5 = android.text.TextUtils.equals(r4, r5)     // Catch:{ all -> 0x0070 }
            if (r5 != 0) goto L_0x004c
            java.util.Stack<java.lang.String> r5 = r3.e     // Catch:{ all -> 0x0070 }
            r5.push(r4)     // Catch:{ all -> 0x0070 }
            java.util.Stack<java.lang.String> r4 = r3.e     // Catch:{ all -> 0x0070 }
            int r4 = r4.size()     // Catch:{ all -> 0x0070 }
            r3.f = r4     // Catch:{ all -> 0x0070 }
        L_0x004c:
            monitor-exit(r3)
            return
        L_0x004e:
            r3.b()     // Catch:{ all -> 0x0070 }
            java.util.List<zv> r5 = r3.h     // Catch:{ all -> 0x0070 }
            r5.clear()     // Catch:{ all -> 0x0070 }
            r0 = 0
            r3.c = r0     // Catch:{ all -> 0x0070 }
            r3.d = r0     // Catch:{ all -> 0x0070 }
            java.util.Stack<java.lang.String> r5 = r3.e     // Catch:{ all -> 0x0070 }
            r5.removeElement(r4)     // Catch:{ all -> 0x0070 }
            java.util.Stack<java.lang.String> r5 = r3.e     // Catch:{ all -> 0x0070 }
            r5.remove(r4)     // Catch:{ all -> 0x0070 }
            java.util.Stack<java.lang.String> r4 = r3.e     // Catch:{ all -> 0x0070 }
            int r4 = r4.size()     // Catch:{ all -> 0x0070 }
            r3.f = r4     // Catch:{ all -> 0x0070 }
            monitor-exit(r3)
            return
        L_0x0070:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zt.a(java.lang.String, boolean):void");
    }

    private void b() {
        if (this.e.size() != 0) {
            JSONObject jSONObject = new JSONObject();
            String pop = this.e.pop();
            long j = this.c;
            long j2 = this.d;
            if (!TextUtils.isEmpty(pop) && j > 0) {
                try {
                    jSONObject.put("itemid", pop);
                    jSONObject.put("text", j);
                    if (c()) {
                        JSONArray jSONArray = new JSONArray();
                        for (zv a2 : this.h) {
                            jSONArray.put(a2.a());
                        }
                        jSONObject.put("status", jSONArray);
                    }
                    jSONObject.put("action", j2);
                    if (a) {
                        AMapLog.e("SceneManagerhttplog", jSONObject.toString());
                    }
                    aaf.a((String) "2000", (String) "B013", jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean c() {
        boolean a2;
        if (this.i != null) {
            return this.i.a();
        }
        synchronized (this) {
            if (this.i == null) {
                this.i = aaf.a == null ? null : aaf.a.k();
            }
            a2 = this.i == null ? false : this.i.a();
        }
        return a2;
    }
}
