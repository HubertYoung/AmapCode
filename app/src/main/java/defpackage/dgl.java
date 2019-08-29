package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dgl reason: default package */
/* compiled from: VoiceInMemory */
public final class dgl {
    public tw a;
    public ua b;

    /* renamed from: dgl$a */
    /* compiled from: VoiceInMemory */
    public static class a {
        public static String a(dgl dgl) throws JSONException {
            if (dgl == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", dgl.a.c);
            jSONObject.put("name2", dgl.a.l);
            jSONObject.put("subname", dgl.a.f);
            jSONObject.put("desc", dgl.a.n);
            jSONObject.put("type", dgl.a.o);
            jSONObject.put("dataSize", dgl.a.g);
            jSONObject.put("image", dgl.a.k);
            jSONObject.put("dataPath", dgl.b.f);
            return jSONObject.toString();
        }
    }

    public dgl(tw twVar, ua uaVar) {
        this.a = twVar;
        if (uaVar == null) {
            this.b = new ua();
            this.b.b = twVar.f;
            this.b.c = 0;
            this.b.d = twVar.g;
            this.b.e = 0;
            this.b.f = a(twVar.d);
            this.b.n = twVar.e;
            return;
        }
        this.b = uaVar;
    }

    public static dgl a(tw twVar, ua uaVar) {
        return new dgl(twVar, uaVar);
    }

    public static boolean a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                if (TextUtils.isEmpty(str2)) {
                    return true;
                }
                return str2.equalsIgnoreCase(dhd.a(file));
            }
        }
        return false;
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return null;
        }
        return str.substring(lastIndexOf);
    }

    public final String a() {
        if (this.b == null) {
            return null;
        }
        if (c()) {
            return this.b.f;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dgu.a().d());
        sb.append(this.b.f);
        return sb.toString();
    }

    public final String b() {
        if (this.b == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append(FilePathHelper.SUFFIX_DOT_TMP);
        return sb.toString();
    }

    public final boolean c() {
        return this.a.o == 50;
    }

    public final boolean d() {
        return this.a.h == 1;
    }

    public final boolean e() {
        return !TextUtils.isEmpty(this.a.b) && "1".equals(this.a.b);
    }

    public final long f() {
        if (this.b != null) {
            return this.b.e;
        }
        return 0;
    }

    public final void a(long j) {
        if (this.b != null) {
            this.b.e = j;
            h();
        }
    }

    public final int g() {
        if (this.b != null) {
            return this.b.c;
        }
        return 0;
    }

    public final void a(int i) {
        this.b.c = i;
        h();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void h() {
        /*
            r4 = this;
            monitor-enter(r4)
            dgm r0 = defpackage.dgm.a()     // Catch:{ all -> 0x004e }
            tw r1 = r4.a     // Catch:{ all -> 0x004e }
            r0.a(r1)     // Catch:{ all -> 0x004e }
            ua r0 = r4.b     // Catch:{ all -> 0x004e }
            if (r0 != 0) goto L_0x0010
            monitor-exit(r4)
            return
        L_0x0010:
            ua r0 = r4.b     // Catch:{ all -> 0x004e }
            int r0 = r0.c     // Catch:{ all -> 0x004e }
            if (r0 != 0) goto L_0x003c
            ua r0 = r4.b     // Catch:{ all -> 0x004e }
            java.lang.Long r0 = r0.a     // Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x004c
            ua r0 = r4.b     // Catch:{ all -> 0x004e }
            java.lang.Long r0 = r0.a     // Catch:{ all -> 0x004e }
            long r0 = r0.longValue()     // Catch:{ all -> 0x004e }
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x004c
            dgm r0 = defpackage.dgm.a()     // Catch:{ all -> 0x004e }
            ua r1 = r4.b     // Catch:{ all -> 0x004e }
            java.util.concurrent.ExecutorService r2 = r0.c     // Catch:{ all -> 0x004e }
            dgm$2 r3 = new dgm$2     // Catch:{ all -> 0x004e }
            r3.<init>(r1)     // Catch:{ all -> 0x004e }
            r2.execute(r3)     // Catch:{ all -> 0x004e }
            monitor-exit(r4)
            return
        L_0x003c:
            dgm r0 = defpackage.dgm.a()     // Catch:{ all -> 0x004e }
            ua r1 = r4.b     // Catch:{ all -> 0x004e }
            java.util.concurrent.ExecutorService r2 = r0.c     // Catch:{ all -> 0x004e }
            dgm$1 r3 = new dgm$1     // Catch:{ all -> 0x004e }
            r3.<init>(r1)     // Catch:{ all -> 0x004e }
            r2.execute(r3)     // Catch:{ all -> 0x004e }
        L_0x004c:
            monitor-exit(r4)
            return
        L_0x004e:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgl.h():void");
    }

    public final void i() {
        String a2 = a();
        if (!TextUtils.isEmpty(a2)) {
            dgu.a(new File(a2), true);
        }
        String b2 = b();
        if (!TextUtils.isEmpty(b2)) {
            dgu.a(new File(b2), true);
        }
        a(0);
    }
}
