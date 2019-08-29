package com.amap.location.uptunnel.core.c;

import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import com.amap.location.common.a.a.C0014a;
import com.amap.location.common.f.g;
import com.amap.location.uptunnel.core.db.DBProvider;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.concurrent.Executor;

/* compiled from: CountTunnel */
public class b {
    /* access modifiers changed from: private */
    public Uri a;
    /* access modifiers changed from: private */
    public DBProvider b;
    /* access modifiers changed from: private */
    public com.amap.location.uptunnel.core.b c;
    /* access modifiers changed from: private */
    public com.amap.location.uptunnel.b.a d;
    /* access modifiers changed from: private */
    public com.amap.location.common.e.c e;
    /* access modifiers changed from: private */
    public int f;
    private com.amap.location.common.a.b g;
    private com.amap.location.common.a.a h;
    /* access modifiers changed from: private */
    public SparseIntArray i = new SparseIntArray();
    /* access modifiers changed from: private */
    public volatile long j;

    /* compiled from: CountTunnel */
    class a implements com.amap.location.common.a.a.b<c> {
        public void a() {
        }

        public void b() {
        }

        a() {
        }

        public long c() {
            return b.this.d.c();
        }

        public long d() {
            return b.this.d.e();
        }

        public boolean a(long j) {
            try {
                long a2 = (b.this.b.a(b.this.a) * 24) + (j * 24);
                if (a2 > b.this.d.g()) {
                    return b(a2 - b.this.d.g());
                }
                return true;
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
                return false;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x00ca, code lost:
            if (com.amap.location.uptunnel.core.c.b.c(r12.a).a(com.amap.location.uptunnel.core.c.b.b(r12.a), r9, "ID = ".concat(java.lang.String.valueOf(r6)), null) >= 0) goto L_0x00dd;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(java.util.ArrayList<com.amap.location.uptunnel.core.c.b.c> r13) {
            /*
                r12 = this;
                com.amap.location.uptunnel.core.c.b r13 = com.amap.location.uptunnel.core.c.b.this
                android.util.SparseIntArray r13 = r13.i
                int r13 = r13.size()
                r0 = 0
                r1 = 0
            L_0x000c:
                if (r1 >= r13) goto L_0x0123
                com.amap.location.uptunnel.core.c.b r2 = com.amap.location.uptunnel.core.c.b.this
                android.util.SparseIntArray r2 = r2.i
                int r2 = r2.keyAt(r1)
                com.amap.location.uptunnel.core.c.b r3 = com.amap.location.uptunnel.core.c.b.this
                android.util.SparseIntArray r3 = r3.i
                int r3 = r3.valueAt(r1)
                com.amap.location.uptunnel.core.c.b r4 = com.amap.location.uptunnel.core.c.b.this
                com.amap.location.uptunnel.core.db.DBProvider r5 = r4.b
                com.amap.location.uptunnel.core.c.b r4 = com.amap.location.uptunnel.core.c.b.this
                android.net.Uri r6 = r4.a
                java.lang.String[] r7 = com.amap.location.uptunnel.core.db.a.b.a
                java.lang.String r8 = "type = ? "
                r4 = 1
                java.lang.String[] r9 = new java.lang.String[r4]
                java.lang.String r10 = java.lang.String.valueOf(r2)
                r9[r0] = r10
                java.lang.String r10 = "time DESC"
                java.lang.String r11 = "0, 1"
                android.database.Cursor r5 = r5.b(r6, r7, r8, r9, r10, r11)
                if (r5 == 0) goto L_0x00dc
                boolean r6 = r5.moveToFirst()     // Catch:{ Exception -> 0x00cf }
                if (r6 == 0) goto L_0x00dc
                java.lang.String r6 = "time"
                int r6 = r5.getColumnIndex(r6)     // Catch:{ Exception -> 0x00cf }
                long r6 = r5.getLong(r6)     // Catch:{ Exception -> 0x00cf }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00cf }
                r10 = 0
                long r8 = r8 - r6
                r6 = 0
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 <= 0) goto L_0x00dc
                com.amap.location.uptunnel.core.c.b r6 = com.amap.location.uptunnel.core.c.b.this     // Catch:{ Exception -> 0x00cf }
                com.amap.location.uptunnel.b.a r6 = r6.d     // Catch:{ Exception -> 0x00cf }
                long r6 = r6.a()     // Catch:{ Exception -> 0x00cf }
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 >= 0) goto L_0x00dc
                java.lang.String r6 = "ID"
                int r6 = r5.getColumnIndex(r6)     // Catch:{ Exception -> 0x00cf }
                long r6 = r5.getLong(r6)     // Catch:{ Exception -> 0x00cf }
                com.amap.location.uptunnel.core.c.b r8 = com.amap.location.uptunnel.core.c.b.this     // Catch:{ Exception -> 0x00cf }
                long r8 = r8.j     // Catch:{ Exception -> 0x00cf }
                int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r8 > 0) goto L_0x0088
                r8 = 1
                goto L_0x0089
            L_0x0088:
                r8 = 0
            L_0x0089:
                if (r8 != 0) goto L_0x00dc
                java.lang.String r8 = "value"
                int r8 = r5.getColumnIndex(r8)     // Catch:{ Exception -> 0x00cf }
                int r8 = r5.getInt(r8)     // Catch:{ Exception -> 0x00cf }
                android.content.ContentValues r9 = new android.content.ContentValues     // Catch:{ Exception -> 0x00cf }
                r9.<init>()     // Catch:{ Exception -> 0x00cf }
                java.lang.String r10 = "ID"
                java.lang.Long r11 = java.lang.Long.valueOf(r6)     // Catch:{ Exception -> 0x00cf }
                r9.put(r10, r11)     // Catch:{ Exception -> 0x00cf }
                java.lang.String r10 = "value"
                int r8 = r8 + r3
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x00cf }
                r9.put(r10, r8)     // Catch:{ Exception -> 0x00cf }
                com.amap.location.uptunnel.core.c.b r8 = com.amap.location.uptunnel.core.c.b.this     // Catch:{ Exception -> 0x00cf }
                com.amap.location.uptunnel.core.db.DBProvider r8 = r8.b     // Catch:{ Exception -> 0x00cf }
                com.amap.location.uptunnel.core.c.b r10 = com.amap.location.uptunnel.core.c.b.this     // Catch:{ Exception -> 0x00cf }
                android.net.Uri r10 = r10.a     // Catch:{ Exception -> 0x00cf }
                java.lang.String r11 = "ID = "
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x00cf }
                java.lang.String r6 = r11.concat(r6)     // Catch:{ Exception -> 0x00cf }
                r7 = 0
                int r6 = r8.a(r10, r9, r6, r7)     // Catch:{ Exception -> 0x00cf }
                if (r6 < 0) goto L_0x00dc
                goto L_0x00dd
            L_0x00cd:
                r13 = move-exception
                goto L_0x00d8
            L_0x00cf:
                r4 = move-exception
                com.amap.location.common.d.a.a(r4)     // Catch:{ all -> 0x00cd }
                com.amap.location.common.f.g.a(r5)
                r4 = 0
                goto L_0x00e0
            L_0x00d8:
                com.amap.location.common.f.g.a(r5)
                throw r13
            L_0x00dc:
                r4 = 0
            L_0x00dd:
                com.amap.location.common.f.g.a(r5)
            L_0x00e0:
                if (r4 != 0) goto L_0x011f
                long r4 = java.lang.System.currentTimeMillis()
                android.content.ContentValues r6 = new android.content.ContentValues
                r6.<init>()
                java.lang.String r7 = "type"
                java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
                r6.put(r7, r2)
                java.lang.String r2 = "time"
                java.lang.Long r4 = java.lang.Long.valueOf(r4)
                r6.put(r2, r4)
                java.lang.String r2 = "value"
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                r6.put(r2, r3)
                com.amap.location.uptunnel.core.c.b r2 = com.amap.location.uptunnel.core.c.b.this
                com.amap.location.uptunnel.core.db.DBProvider r2 = r2.b
                com.amap.location.uptunnel.core.c.b r3 = com.amap.location.uptunnel.core.c.b.this
                android.net.Uri r3 = r3.a
                long r2 = r2.a(r3, r6)
                r4 = -1
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 == 0) goto L_0x0123
            L_0x011f:
                int r1 = r1 + 1
                goto L_0x000c
            L_0x0123:
                com.amap.location.uptunnel.core.c.b r13 = com.amap.location.uptunnel.core.c.b.this
                android.util.SparseIntArray r13 = r13.i
                r13.clear()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.location.uptunnel.core.c.b.a.a(java.util.ArrayList):void");
        }

        private boolean b(long j) {
            int e = e();
            if (e > 0) {
                j -= (long) (e * 24);
            }
            boolean z = true;
            long j2 = (j / 24) + ((long) (j % 24 > 0 ? 1 : 0));
            if (j2 <= 0) {
                return true;
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(j2 - 1);
                sb.append(", 1");
                Cursor a2 = b.this.b.a(b.this.a, new String[]{AutoJsonUtils.JSON_ID}, null, null, null, sb.toString());
                if (a2 != null) {
                    try {
                        if (a2.moveToFirst()) {
                            if (b.this.b.a(b.this.a, (String) "ID <= ?", new String[]{String.valueOf(a2.getLong(0))}) <= 0) {
                                z = false;
                            }
                            return z;
                        }
                    } catch (Exception e2) {
                        com.amap.location.common.d.a.a((Throwable) e2);
                        return false;
                    } finally {
                        g.a(a2);
                    }
                }
                g.a(a2);
                return false;
            } catch (Exception e3) {
                com.amap.location.common.d.a.a((Throwable) e3);
                return false;
            }
        }

        private int e() {
            return b.this.b.a(b.this.a, (String) "time < ?", new String[]{String.valueOf(System.currentTimeMillis() - b.this.d.h())});
        }
    }

    /* renamed from: com.amap.location.uptunnel.core.c.b$b reason: collision with other inner class name */
    /* compiled from: CountTunnel */
    class C0039b implements com.amap.location.common.a.b.a {
        public void a() {
        }

        public void b() {
        }

        public int d() {
            return 3;
        }

        public Executor h() {
            return null;
        }

        C0039b() {
        }

        public void g() {
            b.this.j = -1;
        }

        public boolean b(int i) {
            return b.this.d.c(i);
        }

        public long c(int i) {
            return b.this.d.b(i) - b.this.c.a(b.this.f, i);
        }

        public void a(int i, Object obj) {
            if (obj instanceof d) {
                b.this.c.a(b.this.f, i, ((d) obj).c);
            }
        }

        public long c() {
            try {
                return b.this.b.a(b.this.a) * 24;
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
                return 0;
            }
        }

        public Object a(long j) {
            d dVar = new d();
            long j2 = j / 24;
            if (j2 <= 0) {
                return null;
            }
            Cursor b = b.this.b.b(b.this.a, com.amap.location.uptunnel.core.db.a.b.a, null, null, null, "0, ".concat(String.valueOf(j2)));
            if (b != null) {
                try {
                    if (b.getCount() > 0) {
                        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder();
                        int a2 = com.amap.location.common.b.a.a(flatBufferBuilder, b.this.c.a());
                        int count = b.getCount();
                        int[] iArr = new int[count];
                        long j3 = -1;
                        int i = 0;
                        while (b.moveToNext()) {
                            j3 = b.getLong(b.getColumnIndex(AutoJsonUtils.JSON_ID));
                            iArr[i] = com.amap.location.uptunnel.core.a.a.a(flatBufferBuilder, b.getInt(b.getColumnIndex("type")), b.getInt(b.getColumnIndex("value")), b.getLong(b.getColumnIndex("time")));
                            i++;
                            count = count;
                        }
                        int a3 = com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder, iArr);
                        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder);
                        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder, 0);
                        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder, a2);
                        com.amap.location.uptunnel.core.a.c.b(flatBufferBuilder, a3);
                        com.amap.location.uptunnel.core.a.c.d(flatBufferBuilder, com.amap.location.uptunnel.core.a.c.b(flatBufferBuilder));
                        dVar.a = flatBufferBuilder.sizedByteArray();
                        dVar.b = j3;
                        dVar.c = (long) (count * 24);
                        b.this.j = j3;
                        g.a(b);
                        return dVar;
                    }
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    g.a(b);
                    throw th2;
                }
            }
            g.a(b);
            return null;
        }

        public boolean a(Object obj) {
            if (obj instanceof d) {
                return com.amap.location.uptunnel.core.b.a.a(b.this.e, b.this.c.a(b.this.f), ((d) obj).a, b.this.d.f());
            }
            return false;
        }

        public void b(Object obj) {
            if (obj instanceof d) {
                b.this.b.a(b.this.a, (String) "ID <= ? ", new String[]{String.valueOf(((d) obj).b)});
            }
        }

        public long d(int i) {
            return b.this.d.a(i);
        }

        public long e() {
            return b.this.d.d();
        }

        public int f() {
            return b.this.d.f();
        }

        public void a(int i) {
            b.this.j = -1;
        }
    }

    /* compiled from: CountTunnel */
    static class c implements C0014a {
        /* access modifiers changed from: private */
        public static final c a = new c();

        public long a() {
            return 1;
        }

        c() {
        }
    }

    /* compiled from: CountTunnel */
    static class d {
        byte[] a;
        long b;
        long c;

        d() {
        }
    }

    public void a(@NonNull com.amap.location.uptunnel.core.b bVar, @NonNull com.amap.location.uptunnel.b.a aVar, @NonNull com.amap.location.common.e.c cVar, @NonNull Looper looper) {
        this.c = bVar;
        this.f = 1;
        this.d = new a(aVar);
        this.e = cVar;
        this.b = bVar.b();
        this.a = com.amap.location.uptunnel.core.b.b(this.f);
        this.h = new com.amap.location.common.a.a();
        this.h.a((com.amap.location.common.a.a.b<Item>) new a<Item>(), looper);
        this.g = new com.amap.location.common.a.b();
        this.g.a(bVar.a(), (com.amap.location.common.a.b.a) new C0039b(), looper);
        this.g.a(20000);
    }

    public void a(int i2) {
        this.i.put(i2, this.i.get(i2) + 1);
        this.d.b();
        this.h.a(c.a);
    }

    public void b(int i2) {
        if (i2 != -1) {
            this.d.b();
            this.g.a(20000);
        }
    }

    public void a() {
        this.h.a();
        this.g.a();
    }
}
