package defpackage;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: wf reason: default package */
/* compiled from: ActionPermissionRule */
public final class wf {
    private boolean a = true;
    private String b = "";
    private final LinkedList<String> c = new LinkedList<>();
    private final LinkedList<String> d = new LinkedList<>();
    private final LinkedList<String> e = new LinkedList<>();
    private final HashMap<String, LinkedList<String>> f = new HashMap<>();
    private boolean g = false;

    public final synchronized void a() {
        b(false);
    }

    private synchronized void b(boolean z) {
        String str;
        if (!this.g) {
            this.a = wh.a();
            boolean z2 = false;
            if (z) {
                try {
                    str = wh.c();
                    if (!TextUtils.isEmpty(str)) {
                        z2 = a(str);
                    }
                } catch (Exception e2) {
                    e();
                    e2.printStackTrace();
                }
            } else {
                str = wh.b();
                if (!TextUtils.isEmpty(str)) {
                    z2 = a(str);
                }
                if (!z2) {
                    wh.e();
                    str = wh.c();
                    if (!TextUtils.isEmpty(str)) {
                        z2 = a(str);
                    }
                }
            }
            if (z2) {
                this.b = agy.a(str).toUpperCase();
            }
            this.g = true;
        }
    }

    public final synchronized void b() {
        this.g = false;
        e();
        b(true);
    }

    private void e() {
        this.b = "";
        this.c.clear();
        this.e.clear();
        this.d.clear();
        this.f.clear();
    }

    private boolean a(String str) {
        boolean z = false;
        try {
            JSONObject jSONObject = new JSONObject(wh.b(str));
            JSONArray jSONArray = jSONObject.getJSONArray("AMapDomains");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                this.c.add(jSONArray.getString(i));
            }
            JSONArray jSONArray2 = jSONObject.getJSONArray("AlibabaDomains");
            int length2 = jSONArray2.length();
            for (int i2 = 0; i2 < length2; i2++) {
                this.d.add(jSONArray2.getString(i2));
            }
            JSONArray jSONArray3 = jSONObject.getJSONArray("AMapOnlyActions");
            int length3 = jSONArray3.length();
            for (int i3 = 0; i3 < length3; i3++) {
                this.e.add(jSONArray3.getString(i3));
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("whiteList");
            JSONArray names = jSONObject2.names();
            int length4 = names.length();
            for (int i4 = 0; i4 < length4; i4++) {
                String string = names.getString(i4);
                JSONArray jSONArray4 = jSONObject2.getJSONArray(string);
                int length5 = jSONArray4.length();
                LinkedList linkedList = new LinkedList();
                for (int i5 = 0; i5 < length5; i5++) {
                    linkedList.add(jSONArray4.getString(i5));
                }
                this.f.put(string, linkedList);
            }
            z = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (!z) {
            e();
        }
        return z;
    }

    public final synchronized boolean a(String str, String str2) {
        try {
            e();
            if (!a(str)) {
                return false;
            }
            this.b = str2;
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String c() {
        try {
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean d() {
        try {
        }
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(boolean z) {
        this.a = z;
        wh.a(z);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0041, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean b(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0042 }
            r1 = 0
            if (r0 != 0) goto L_0x0040
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0042 }
            if (r0 == 0) goto L_0x000f
            goto L_0x0040
        L_0x000f:
            java.lang.String r4 = defpackage.wg.a(r4)     // Catch:{ all -> 0x0042 }
            java.util.LinkedList<java.lang.String> r0 = r3.c     // Catch:{ all -> 0x0042 }
            boolean r0 = a(r4, r0)     // Catch:{ all -> 0x0042 }
            r2 = 1
            if (r0 == 0) goto L_0x001e
            monitor-exit(r3)
            return r2
        L_0x001e:
            java.util.LinkedList<java.lang.String> r0 = r3.e     // Catch:{ all -> 0x0042 }
            boolean r0 = b(r5, r0)     // Catch:{ all -> 0x0042 }
            if (r0 == 0) goto L_0x002c
            boolean r4 = r3.c(r4, r5)     // Catch:{ all -> 0x0042 }
            monitor-exit(r3)
            return r4
        L_0x002c:
            java.util.LinkedList<java.lang.String> r0 = r3.d     // Catch:{ all -> 0x0042 }
            boolean r0 = a(r4, r0)     // Catch:{ all -> 0x0042 }
            if (r0 == 0) goto L_0x0036
            monitor-exit(r3)
            return r2
        L_0x0036:
            boolean r4 = r3.c(r4, r5)     // Catch:{ all -> 0x0042 }
            if (r4 == 0) goto L_0x003e
            monitor-exit(r3)
            return r2
        L_0x003e:
            monitor-exit(r3)
            return r1
        L_0x0040:
            monitor-exit(r3)
            return r1
        L_0x0042:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wf.b(java.lang.String, java.lang.String):boolean");
    }

    private static boolean a(String str, LinkedList<String> linkedList) {
        Iterator it = linkedList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z = str.equalsIgnoreCase((String) it.next());
            if (z) {
                break;
            }
        }
        return z;
    }

    private static boolean b(String str, LinkedList<String> linkedList) {
        Iterator it = linkedList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z = ((String) it.next()).equals(str);
            if (z) {
                break;
            }
        }
        return z;
    }

    private boolean c(String str, String str2) {
        LinkedList linkedList;
        Iterator<Entry<String, LinkedList<String>>> it = this.f.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                linkedList = null;
                break;
            }
            Entry next = it.next();
            if (str.equalsIgnoreCase((String) next.getKey())) {
                linkedList = (LinkedList) next.getValue();
                break;
            }
        }
        return linkedList != null && b(str2, linkedList);
    }
}
