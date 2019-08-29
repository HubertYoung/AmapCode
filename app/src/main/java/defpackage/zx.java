package defpackage;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zx reason: default package */
/* compiled from: StartMonitor */
public final class zx {
    private static zx c;
    private List<String> a = new LinkedList();
    private boolean b;

    private zx() {
    }

    public static synchronized zx a() {
        zx zxVar;
        synchronized (zx.class) {
            try {
                if (c == null) {
                    c = new zx();
                }
                zxVar = c;
            }
        }
        return zxVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0057, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.b     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r4)
            return
        L_0x0007:
            r0 = 2
            boolean r1 = defpackage.bpv.a(r0)     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0026
            java.lang.String r1 = "StartMonitor"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            java.lang.String r3 = "addUrl ["
            r2.<init>(r3)     // Catch:{ all -> 0x0058 }
            r2.append(r5)     // Catch:{ all -> 0x0058 }
            java.lang.String r3 = "]"
            r2.append(r3)     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0058 }
            defpackage.bpv.a(r1, r2)     // Catch:{ all -> 0x0058 }
        L_0x0026:
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0058 }
            if (r1 != 0) goto L_0x0056
            java.lang.String r1 = "://"
            java.lang.String[] r5 = r5.split(r1, r0)     // Catch:{ all -> 0x0058 }
            int r1 = r5.length     // Catch:{ all -> 0x0058 }
            int r1 = r1 + -1
            r5 = r5[r1]     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = "\\?"
            java.lang.String[] r5 = r5.split(r1)     // Catch:{ all -> 0x0058 }
            r1 = 0
            r5 = r5[r1]     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = "/"
            java.lang.String[] r5 = r5.split(r1, r0)     // Catch:{ all -> 0x0058 }
            int r0 = r5.length     // Catch:{ all -> 0x0058 }
            int r0 = r0 + -1
            r5 = r5[r0]     // Catch:{ all -> 0x0058 }
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x0056
            java.util.List<java.lang.String> r0 = r4.a     // Catch:{ all -> 0x0058 }
            r0.add(r5)     // Catch:{ all -> 0x0058 }
        L_0x0056:
            monitor-exit(r4)
            return
        L_0x0058:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zx.a(java.lang.String):void");
    }

    public final synchronized void b() {
        this.a.clear();
        this.b = true;
    }

    public final synchronized void c() {
        this.b = false;
        d();
    }

    private synchronized void d() {
        if (this.a.size() > 0) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.a.size(); i++) {
                jSONArray.put(this.a.get(i));
            }
            try {
                jSONObject.put("url", jSONArray);
                aaf.a((String) "2000", (String) "B012", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
