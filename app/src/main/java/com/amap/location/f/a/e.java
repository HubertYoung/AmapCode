package com.amap.location.f.a;

import com.amap.location.common.f.c;
import com.amap.location.common.model.AmapLoc;
import org.json.JSONObject;

/* compiled from: LocationCacheItem */
class e {
    private long a;
    private AmapLoc b = null;
    private g c = null;
    private String d = null;
    private long e;
    private long f;

    e() {
    }

    /* access modifiers changed from: 0000 */
    public long a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        this.a = j;
    }

    /* access modifiers changed from: 0000 */
    public AmapLoc b() {
        if (this.b == null && this.d != null) {
            try {
                JSONObject jSONObject = new JSONObject(c.b(this.d));
                if (jSONObject.has("type")) {
                    jSONObject.put("type", AmapLoc.TYPE_CACHE);
                }
                this.b = new AmapLoc(jSONObject);
            } catch (Exception unused) {
            }
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(AmapLoc amapLoc) {
        this.b = amapLoc;
    }

    /* access modifiers changed from: 0000 */
    public g c() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public void a(g gVar) {
        this.c = gVar;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        this.d = str;
    }

    /* access modifiers changed from: 0000 */
    public long d() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public void b(long j) {
        this.e = j;
    }

    /* access modifiers changed from: 0000 */
    public long e() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public void c(long j) {
        this.f = j;
    }

    public String toString() {
        return a(false);
    }

    /* access modifiers changed from: 0000 */
    public String a(boolean z) {
        b();
        int i = z ? 3 : 1;
        StringBuilder sb = new StringBuilder("id:");
        sb.append(this.a);
        sb.append(",location:");
        sb.append(this.b == null ? "" : this.b.toJSONStr(i));
        sb.append(",nearby:");
        sb.append(this.c.toString());
        sb.append(",lastUsedTime:");
        sb.append(this.e);
        sb.append(",insertTime:");
        sb.append(this.f);
        return sb.toString();
    }
}
