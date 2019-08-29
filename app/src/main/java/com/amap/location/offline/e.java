package com.amap.location.offline;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import com.amap.location.common.d.a;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import com.amap.location.offline.e.b;
import java.util.LinkedList;
import java.util.List;

/* compiled from: OfflineRemoteProxy */
public class e {
    private Context a;
    private a b;
    private List<String> c = new LinkedList();
    private ProviderInfo d;
    private ContentValues e = new ContentValues();

    public e(Context context, c cVar, a aVar) {
        this.a = context;
        this.b = aVar;
        a(cVar, aVar);
    }

    public void a(c cVar) {
        a(cVar, this.b);
    }

    private void a(c cVar, a aVar) {
        this.c.clear();
        int i = 0;
        if (aVar == null || aVar.f() == null) {
            if (!(cVar == null || cVar.l == null)) {
                String[] strArr = cVar.l;
                int length = strArr.length;
                while (i < length) {
                    this.c.add(strArr[i]);
                    i++;
                }
            }
            return;
        }
        String[] f = aVar.f();
        int length2 = f.length;
        while (i < length2) {
            this.c.add(f[i]);
            i++;
        }
    }

    public boolean a(String str) {
        if (this.d != null) {
            if (str == null || !str.equals(this.d.packageName)) {
                return true;
            }
            a();
        }
        while (!this.c.isEmpty()) {
            try {
                ProviderInfo resolveContentProvider = this.a.getPackageManager().resolveContentProvider(this.c.get(0), 0);
                if (resolveContentProvider != null && (str == null || !str.equals(resolveContentProvider.packageName))) {
                    this.d = resolveContentProvider;
                    return true;
                }
            } catch (Exception unused) {
            }
            this.c.remove(0);
        }
        return false;
    }

    private void a() {
        this.d = null;
        if (!this.c.isEmpty()) {
            this.c.remove(0);
        }
    }

    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v3, types: [com.amap.location.offline.e.b$a] */
    /* JADX WARNING: type inference failed for: r1v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v6, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r2v7, types: [com.amap.location.offline.e.b$a] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], com.amap.location.offline.e.b$a, android.database.Cursor]
      mth insns count: 39
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0044 A[SYNTHETIC, Splitter:B:19:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0056 A[SYNTHETIC, Splitter:B:30:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004e A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amap.location.offline.e.b.a a(com.amap.location.common.model.FPS r9, int r10, java.lang.String r11) {
        /*
            r8 = this;
        L_0x0000:
            boolean r0 = r8.a(r11)
            if (r0 == 0) goto L_0x005a
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String r2 = "content://"
            r1.<init>(r2)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            android.content.pm.ProviderInfo r2 = r8.d     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String r2 = r2.authority     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            r1.append(r2)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            android.net.Uri r3 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String[] r6 = com.amap.location.offline.e.b.a(r11, r9, r0, r10)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            android.content.Context r1 = r8.a     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            r4 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            com.amap.location.offline.e.b$a r2 = com.amap.location.offline.e.b.a(r1)     // Catch:{ Exception -> 0x0039 }
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            r0 = r2
            goto L_0x0047
        L_0x0039:
            r2 = move-exception
            goto L_0x003f
        L_0x003b:
            r9 = move-exception
            goto L_0x0054
        L_0x003d:
            r2 = move-exception
            r1 = r0
        L_0x003f:
            com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Exception -> 0x0047 }
        L_0x0047:
            if (r0 == 0) goto L_0x004e
            boolean r1 = r0.a
            if (r1 == 0) goto L_0x004e
            return r0
        L_0x004e:
            r8.a()
            goto L_0x0000
        L_0x0052:
            r9 = move-exception
            r0 = r1
        L_0x0054:
            if (r0 == 0) goto L_0x0059
            r0.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0059:
            throw r9
        L_0x005a:
            com.amap.location.offline.e.b$a r9 = new com.amap.location.offline.e.b$a
            r9.<init>()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.e.a(com.amap.location.common.model.FPS, int, java.lang.String):com.amap.location.offline.e.b$a");
    }

    public boolean a(FPS fps, AmapLoc amapLoc, String str) {
        boolean z = false;
        if (!a(str)) {
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder("content://");
            sb.append(this.d.authority);
            if (this.a.getContentResolver().update(Uri.parse(sb.toString()), this.e, null, b.a(str, fps, amapLoc, 0)) == 1) {
                z = true;
            }
        } catch (Exception e2) {
            a.a((Throwable) e2);
        }
        if (z) {
            return true;
        }
        a();
        return a(str);
    }
}
