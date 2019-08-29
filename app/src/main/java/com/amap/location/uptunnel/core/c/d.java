package com.amap.location.uptunnel.core.c;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.amap.location.common.a.a.C0014a;
import com.amap.location.common.f.g;
import com.amap.location.uptunnel.core.db.DBProvider;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: DataTunnel */
public class d {
    private String a = "DataTunnel";
    /* access modifiers changed from: private */
    public String b = null;
    /* access modifiers changed from: private */
    public Uri c;
    /* access modifiers changed from: private */
    public DBProvider d;
    /* access modifiers changed from: private */
    public com.amap.location.uptunnel.core.b e;
    /* access modifiers changed from: private */
    public com.amap.location.uptunnel.b.b f;
    /* access modifiers changed from: private */
    public com.amap.location.common.e.c g;
    /* access modifiers changed from: private */
    public int h;
    private com.amap.location.common.a.a<c> i;
    private com.amap.location.common.a.b j;

    /* compiled from: DataTunnel */
    class a implements com.amap.location.common.a.a.b<c> {
        public void a() {
        }

        public void b() {
        }

        a() {
        }

        public long c() {
            return d.this.f.c();
        }

        public long d() {
            return d.this.f.e();
        }

        public boolean a(long j) {
            Cursor a2 = d.this.d.a(d.this.c, new String[]{"sum(size)"}, null, null, null);
            if (a2 != null) {
                try {
                    if (a2.moveToFirst()) {
                        long j2 = a2.getLong(0);
                        long j3 = j + j2;
                        if (j3 > d.this.f.g()) {
                            return a(j2, j3 - d.this.f.g());
                        }
                        g.a(a2);
                        return true;
                    }
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                    return false;
                } finally {
                    g.a(a2);
                }
            }
            g.a(a2);
            return false;
        }

        public void a(ArrayList<c> arrayList) {
            ContentValues[] contentValuesArr = new ContentValues[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                c cVar = arrayList.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("type", Integer.valueOf(cVar.a));
                contentValues.put("time", Long.valueOf(cVar.b));
                contentValues.put("size", Long.valueOf(cVar.a()));
                contentValues.put("value", cVar.c);
                contentValuesArr[i] = contentValues;
            }
            d.this.d.a(d.this.c, contentValuesArr);
        }

        private boolean a(long j, long j2) {
            if (e() > 0) {
                Cursor a2 = d.this.d.a(d.this.c, new String[]{"sum(size)"}, null, null, null);
                if (a2 != null) {
                    try {
                        if (a2.moveToFirst()) {
                            j2 -= j - a2.getLong(0);
                        }
                    } catch (Exception e) {
                        com.amap.location.common.d.a.a((Throwable) e);
                        return false;
                    } finally {
                        g.a(a2);
                    }
                }
                g.a(a2);
                return false;
            }
            SQLiteDatabase c = d.this.d.c();
            if (c == null) {
                return false;
            }
            while (j2 > 0) {
                try {
                    StringBuilder sb = new StringBuilder("select sum(size) from (select * from ");
                    sb.append(d.this.b);
                    sb.append(" limit 0, ");
                    sb.append(d.this.f.a());
                    sb.append(")");
                    Cursor rawQuery = c.rawQuery(sb.toString(), null);
                    if (rawQuery != null) {
                        try {
                            if (rawQuery.moveToFirst()) {
                                long j3 = rawQuery.getLong(0);
                                if (j3 <= 0) {
                                    g.a(rawQuery);
                                    return false;
                                }
                                try {
                                    StringBuilder sb2 = new StringBuilder("delete from ");
                                    sb2.append(d.this.b);
                                    sb2.append(" where ID < ( select ID from ");
                                    sb2.append(d.this.b);
                                    sb2.append(" limit ");
                                    sb2.append(d.this.f.a());
                                    sb2.append(", 1)");
                                    c.execSQL(sb2.toString());
                                    j2 -= j3;
                                } catch (Exception e2) {
                                    com.amap.location.common.d.a.a((Throwable) e2);
                                    g.a(rawQuery);
                                    return false;
                                }
                            }
                        } catch (Exception e3) {
                            com.amap.location.common.d.a.a((Throwable) e3);
                            return false;
                        } finally {
                            g.a(rawQuery);
                        }
                    }
                    g.a(rawQuery);
                    return false;
                } catch (Exception e4) {
                    com.amap.location.common.d.a.a((Throwable) e4);
                    return false;
                }
            }
            return true;
        }

        private int e() {
            return d.this.d.a(d.this.c, (String) "time < ?", new String[]{String.valueOf(System.currentTimeMillis() - d.this.f.h())});
        }
    }

    /* compiled from: DataTunnel */
    class b implements com.amap.location.common.a.b.a {
        public void a() {
        }

        public void a(int i) {
        }

        public void b() {
        }

        public int d() {
            return 3;
        }

        public void g() {
        }

        public Executor h() {
            return null;
        }

        b() {
        }

        public boolean b(int i) {
            return d.this.f.c(i);
        }

        public long c(int i) {
            return d.this.f.b(i) - d.this.e.a(d.this.h, i);
        }

        public void a(int i, Object obj) {
            if (obj instanceof C0040d) {
                d.this.e.a(d.this.h, i, ((C0040d) obj).c);
            }
        }

        public long c() {
            Cursor a2 = d.this.d.a(d.this.c, new String[]{"sum(size)"}, null, null, null);
            long j = 0;
            if (a2 != null) {
                try {
                    if (a2.moveToFirst()) {
                        j = a2.getLong(0);
                    }
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                } catch (Throwable th) {
                    g.a(a2);
                    throw th;
                }
            }
            g.a(a2);
            return j;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00cd, code lost:
            r7 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            com.amap.location.common.f.g.a(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d2, code lost:
            r0 = e;
         */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00e0 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00e1  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object a(long r29) {
            /*
                r28 = this;
                r1 = r28
                com.amap.location.uptunnel.core.c.d$d r4 = new com.amap.location.uptunnel.core.c.d$d
                r4.<init>()
                com.google.flatbuffers.FlatBufferBuilder r5 = new com.google.flatbuffers.FlatBufferBuilder
                r5.<init>()
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                r9 = -1
                r13 = r9
                r9 = 0
                r11 = 0
            L_0x0018:
                int r15 = (r9 > r29 ? 1 : (r9 == r29 ? 0 : -1))
                r16 = 0
                if (r15 >= 0) goto L_0x00da
                com.amap.location.uptunnel.core.c.d r15 = com.amap.location.uptunnel.core.c.d.this
                com.amap.location.uptunnel.core.db.DBProvider r17 = r15.d
                com.amap.location.uptunnel.core.c.d r15 = com.amap.location.uptunnel.core.c.d.this
                android.net.Uri r18 = r15.c
                java.lang.String[] r19 = com.amap.location.uptunnel.core.db.a.a.a
                r20 = 0
                r21 = 0
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                java.lang.String r8 = "ID limit "
                r15.<init>(r8)
                r15.append(r11)
                java.lang.String r8 = ", "
                r15.append(r8)
                com.amap.location.uptunnel.core.c.d r8 = com.amap.location.uptunnel.core.c.d.this
                com.amap.location.uptunnel.b.b r8 = r8.f
                int r8 = r8.a()
                r15.append(r8)
                java.lang.String r22 = r15.toString()
                android.database.Cursor r8 = r17.a(r18, r19, r20, r21, r22)
                if (r8 == 0) goto L_0x00cd
                int r15 = r8.getCount()     // Catch:{ Exception -> 0x00c9 }
                if (r15 != 0) goto L_0x005e
                goto L_0x00cd
            L_0x005e:
                com.amap.location.uptunnel.core.c.d r15 = com.amap.location.uptunnel.core.c.d.this
                com.amap.location.uptunnel.b.b r15 = r15.f
                int r15 = r15.a()
                r23 = r8
                long r7 = (long) r15
                long r11 = r11 + r7
                r7 = r23
            L_0x006e:
                boolean r8 = r7.moveToNext()     // Catch:{ Exception -> 0x00bc }
                if (r8 == 0) goto L_0x00ac
                r8 = 0
                long r17 = r7.getLong(r8)     // Catch:{ Exception -> 0x00bc }
                r15 = 1
                int r8 = r7.getInt(r15)     // Catch:{ Exception -> 0x00bc }
                r15 = 2
                byte[] r15 = r7.getBlob(r15)     // Catch:{ Exception -> 0x00bc }
                r24 = r11
                r11 = 3
                long r11 = r7.getLong(r11)     // Catch:{ Exception -> 0x00bc }
                r26 = r13
                r13 = 4
                int r13 = r7.getInt(r13)     // Catch:{ Exception -> 0x00bc }
                long r13 = (long) r13     // Catch:{ Exception -> 0x00bc }
                long r13 = r13 + r9
                int r19 = (r13 > r29 ? 1 : (r13 == r29 ? 0 : -1))
                if (r19 > 0) goto L_0x00b0
                int r9 = com.amap.location.uptunnel.core.a.b.a(r5, r15)     // Catch:{ Exception -> 0x00bc }
                int r8 = com.amap.location.uptunnel.core.a.b.a(r5, r8, r9, r11)     // Catch:{ Exception -> 0x00bc }
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x00bc }
                r6.add(r8)     // Catch:{ Exception -> 0x00bc }
                r9 = r13
                r13 = r17
                r11 = r24
                goto L_0x006e
            L_0x00ac:
                r24 = r11
                r26 = r13
            L_0x00b0:
                com.amap.location.common.f.g.a(r7)
                r11 = r24
                r13 = r26
                goto L_0x0018
            L_0x00b9:
                r0 = move-exception
                r2 = r0
                goto L_0x00c5
            L_0x00bc:
                r0 = move-exception
                r2 = r0
                com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x00b9 }
                com.amap.location.common.f.g.a(r7)
                return r16
            L_0x00c5:
                com.amap.location.common.f.g.a(r7)
                throw r2
            L_0x00c9:
                r0 = move-exception
                r7 = r8
            L_0x00cb:
                r2 = r0
                goto L_0x00d4
            L_0x00cd:
                r7 = r8
                com.amap.location.common.f.g.a(r7)     // Catch:{ Exception -> 0x00d2 }
                goto L_0x00da
            L_0x00d2:
                r0 = move-exception
                goto L_0x00cb
            L_0x00d4:
                com.amap.location.common.d.a.a(r2)
                com.amap.location.common.f.g.a(r7)
            L_0x00da:
                r2 = 0
                int r2 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
                if (r2 >= 0) goto L_0x00e1
                return r16
            L_0x00e1:
                int r2 = r6.size()
                int[] r2 = new int[r2]
                r3 = 0
            L_0x00e8:
                int r7 = r6.size()
                if (r3 >= r7) goto L_0x00fd
                java.lang.Object r7 = r6.get(r3)
                java.lang.Integer r7 = (java.lang.Integer) r7
                int r7 = r7.intValue()
                r2[r3] = r7
                int r3 = r3 + 1
                goto L_0x00e8
            L_0x00fd:
                com.amap.location.uptunnel.core.c.d r3 = com.amap.location.uptunnel.core.c.d.this
                com.amap.location.uptunnel.core.b r3 = r3.e
                android.content.Context r3 = r3.a()
                int r3 = com.amap.location.common.b.a.a(r5, r3)
                int r2 = com.amap.location.uptunnel.core.a.c.b(r5, r2)
                com.amap.location.uptunnel.core.a.c.a(r5)
                r6 = 1
                com.amap.location.uptunnel.core.a.c.a(r5, r6)
                com.amap.location.uptunnel.core.a.c.a(r5, r3)
                com.amap.location.uptunnel.core.a.c.c(r5, r2)
                int r2 = com.amap.location.uptunnel.core.a.c.b(r5)
                com.amap.location.uptunnel.core.a.c.d(r5, r2)
                byte[] r2 = r5.sizedByteArray()
                r4.a = r2
                r4.b = r13
                r4.c = r9
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.location.uptunnel.core.c.d.b.a(long):java.lang.Object");
        }

        public boolean a(Object obj) {
            if (obj instanceof C0040d) {
                return com.amap.location.uptunnel.core.b.a.a(d.this.g, d.this.e.a(d.this.h), ((C0040d) obj).a, d.this.f.f());
            }
            return false;
        }

        public void b(Object obj) {
            if (obj instanceof C0040d) {
                d.this.d.a(d.this.c, (String) "ID <= ? ", new String[]{String.valueOf(((C0040d) obj).b)});
            }
        }

        public long d(int i) {
            return d.this.f.a(i);
        }

        public long e() {
            return d.this.f.d();
        }

        public int f() {
            return d.this.f.f();
        }
    }

    /* compiled from: DataTunnel */
    static class c implements C0014a {
        int a;
        long b;
        byte[] c;

        c() {
        }

        public long a() {
            return (long) ((this.c == null ? 0 : this.c.length) + 24);
        }
    }

    /* renamed from: com.amap.location.uptunnel.core.c.d$d reason: collision with other inner class name */
    /* compiled from: DataTunnel */
    static class C0040d {
        byte[] a;
        long b;
        long c;

        C0040d() {
        }
    }

    public void a(@NonNull com.amap.location.uptunnel.core.b bVar, @NonNull com.amap.location.uptunnel.b.b bVar2, @NonNull com.amap.location.common.e.c cVar, int i2, @NonNull Looper looper) {
        this.b = com.amap.location.uptunnel.core.b.c(i2);
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(this.b);
        this.a = sb.toString();
        this.e = bVar;
        this.h = i2;
        this.f = new c(bVar2);
        this.g = cVar;
        this.d = bVar.b();
        this.c = com.amap.location.uptunnel.core.b.b(i2);
        this.i = new com.amap.location.common.a.a<>();
        this.j = new com.amap.location.common.a.b();
        this.i.a((com.amap.location.common.a.a.b<Item>) new a<Item>(), looper);
        this.j.a(bVar.a(), (com.amap.location.common.a.b.a) new b(), looper);
        this.j.a(20000);
    }

    public void a(int i2, byte[] bArr) {
        this.f.b();
        c cVar = new c();
        cVar.a = i2;
        cVar.b = System.currentTimeMillis();
        cVar.c = bArr;
        this.i.a(cVar);
    }

    public void a(int i2) {
        if (i2 != -1) {
            this.f.b();
            this.j.a(20000);
        }
    }

    public void a() {
        this.i.a();
        this.j.a();
    }

    public void b() {
        this.i.b();
    }
}
