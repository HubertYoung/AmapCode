package com.amap.location.a;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CloudCommand */
public class a {
    protected long a = 43200000;
    protected String b;
    protected int c;
    protected long d;
    protected C0009a e;
    private final long f = 300000;
    private final long g = 259200000;

    /* renamed from: com.amap.location.a.a$a reason: collision with other inner class name */
    /* compiled from: CloudCommand */
    public class C0009a {
        private JSONObject b;

        C0009a(String str) throws JSONException {
            this.b = new JSONObject(str);
        }
    }

    protected a() {
    }

    public String a() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 1
            java.lang.String r1 = "$"
            int r1 = r7.indexOf(r1)     // Catch:{ Exception -> 0x0043 }
            int r1 = r1 + r0
            int r2 = r7.length()     // Catch:{ Exception -> 0x0043 }
            java.lang.String r7 = r7.substring(r1, r2)     // Catch:{ Exception -> 0x0043 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0044 }
            r1.<init>(r7)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r2 = "p"
            r3 = 43200000(0x2932e00, double:2.1343636E-316)
            long r2 = r1.optLong(r2, r3)     // Catch:{ Exception -> 0x0044 }
            r4 = 259200000(0xf731400, double:1.280618154E-315)
            long r2 = java.lang.Math.min(r2, r4)     // Catch:{ Exception -> 0x0044 }
            r6.a = r2     // Catch:{ Exception -> 0x0044 }
            long r2 = r6.a     // Catch:{ Exception -> 0x0044 }
            r4 = 300000(0x493e0, double:1.482197E-318)
            long r2 = java.lang.Math.max(r2, r4)     // Catch:{ Exception -> 0x0044 }
            r6.a = r2     // Catch:{ Exception -> 0x0044 }
            java.lang.String r2 = "v"
            int r1 = r1.optInt(r2)     // Catch:{ Exception -> 0x0044 }
            r6.c = r1     // Catch:{ Exception -> 0x0044 }
            com.amap.location.a.a$a r1 = new com.amap.location.a.a$a     // Catch:{ Exception -> 0x0044 }
            r1.<init>(r7)     // Catch:{ Exception -> 0x0044 }
            r6.e = r1     // Catch:{ Exception -> 0x0044 }
            goto L_0x0045
        L_0x0043:
            r7 = 0
        L_0x0044:
            r0 = 0
        L_0x0045:
            if (r0 == 0) goto L_0x0049
            r6.b = r7
        L_0x0049:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.a.a.a(java.lang.String):boolean");
    }
}
