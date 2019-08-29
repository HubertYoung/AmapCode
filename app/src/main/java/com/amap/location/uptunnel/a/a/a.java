package com.amap.location.uptunnel.a.a;

import com.amap.location.common.e.c;
import com.amap.location.uptunnel.core.b;
import com.google.flatbuffers.FlatBufferBuilder;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UploadTunnelCmdTask */
public class a implements Runnable {
    private b a;
    private c b;
    private com.amap.location.uptunnel.a.c c;

    public a(b bVar, com.amap.location.uptunnel.a.c cVar, c cVar2) {
        this.a = bVar;
        this.c = cVar;
        this.b = cVar2;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:83|84|85|86|87|88|89|90|91|(5:93|(2:96|94)|125|97|98)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:89:0x02bd */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r32 = this;
            r1 = r32
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4 = 0
            com.amap.location.uptunnel.core.b r5 = r1.a     // Catch:{ Throwable -> 0x02a9 }
            int r5 = r5.c()     // Catch:{ Throwable -> 0x02a9 }
            r6 = -1
            if (r5 != r6) goto L_0x002d
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x002c
        L_0x0017:
            int r3 = r2.size()
            if (r4 >= r3) goto L_0x0029
            java.lang.Object r3 = r2.get(r4)
            android.database.Cursor r3 = (android.database.Cursor) r3
            com.amap.location.common.f.g.a(r3)
            int r4 = r4 + 1
            goto L_0x0017
        L_0x0029:
            r2.clear()
        L_0x002c:
            return
        L_0x002d:
            com.amap.location.uptunnel.core.b r6 = r1.a     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.db.DBProvider r6 = r6.b()     // Catch:{ Throwable -> 0x02a9 }
            android.database.sqlite.SQLiteDatabase r7 = r6.c()     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.a.c r8 = r1.c     // Catch:{ Throwable -> 0x02a9 }
            int r8 = r8.c()     // Catch:{ Throwable -> 0x02a9 }
            android.net.Uri r14 = com.amap.location.uptunnel.core.b.b(r8)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.a.c r8 = r1.c     // Catch:{ Throwable -> 0x02a9 }
            int r8 = r8.c()     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r8 = com.amap.location.uptunnel.core.b.c(r8)     // Catch:{ Throwable -> 0x02a9 }
            long r9 = r6.a(r14)     // Catch:{ Throwable -> 0x02a9 }
            r15 = 0
            int r9 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r9 > 0) goto L_0x0074
            r32.a()     // Catch:{ Throwable -> 0x02a9 }
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0073
        L_0x005e:
            int r3 = r2.size()
            if (r4 >= r3) goto L_0x0070
            java.lang.Object r3 = r2.get(r4)
            android.database.Cursor r3 = (android.database.Cursor) r3
            com.amap.location.common.f.g.a(r3)
            int r4 = r4 + 1
            goto L_0x005e
        L_0x0070:
            r2.clear()
        L_0x0073:
            return
        L_0x0074:
            java.lang.String r9 = "select max(ID) from "
            java.lang.String r10 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = r9.concat(r10)     // Catch:{ Throwable -> 0x02a9 }
            r10 = 0
            android.database.Cursor r9 = r7.rawQuery(r9, r10)     // Catch:{ Throwable -> 0x02a9 }
            r2.add(r9)     // Catch:{ Throwable -> 0x02a9 }
            r9.moveToFirst()     // Catch:{ Throwable -> 0x02a9 }
            long r12 = r9.getLong(r4)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.common.f.g.a(r9)     // Catch:{ Throwable -> 0x02a9 }
            r17 = 400000(0x61a80, double:1.976263E-318)
            r19 = 1
            r21 = -1
            r11 = 1
            if (r5 != r11) goto L_0x00c7
            java.lang.String r5 = "ID"
            java.lang.String[] r9 = new java.lang.String[]{r5}     // Catch:{ Throwable -> 0x02a9 }
            r10 = 0
            r5 = 0
            r23 = 0
            java.lang.String r24 = "0,1"
            r7 = r6
            r8 = r14
            r3 = 1
            r11 = r5
            r25 = r12
            r12 = r23
            r13 = r24
            android.database.Cursor r5 = r7.b(r8, r9, r10, r11, r12, r13)     // Catch:{ Throwable -> 0x02a9 }
            r2.add(r5)     // Catch:{ Throwable -> 0x02a9 }
            r5.moveToFirst()     // Catch:{ Throwable -> 0x02a9 }
            long r7 = r5.getLong(r4)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.common.f.g.a(r5)     // Catch:{ Throwable -> 0x02a9 }
            r3 = r7
            r12 = r25
            goto L_0x0156
        L_0x00c7:
            r25 = r12
            r3 = 1
            if (r5 != 0) goto L_0x0152
            r12 = r25
            long r23 = r12 + r19
            r3 = r23
            r23 = r15
        L_0x00d4:
            int r5 = (r23 > r17 ? 1 : (r23 == r17 ? 0 : -1))
            if (r5 >= 0) goto L_0x014d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = "select min(ID) from (select * from "
            r5.<init>(r9)     // Catch:{ Throwable -> 0x02a9 }
            r5.append(r8)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = " where id < "
            r5.append(r9)     // Catch:{ Throwable -> 0x02a9 }
            r5.append(r3)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = " order by ID desc limit 0, 50)"
            r5.append(r9)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x02a9 }
            android.database.Cursor r5 = r7.rawQuery(r5, r10)     // Catch:{ Throwable -> 0x02a9 }
            r2.add(r5)     // Catch:{ Throwable -> 0x02a9 }
            if (r5 == 0) goto L_0x014a
            boolean r9 = r5.moveToFirst()     // Catch:{ Throwable -> 0x02a9 }
            if (r9 == 0) goto L_0x014a
            r9 = 0
            long r10 = r5.getLong(r9)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.common.f.g.a(r5)     // Catch:{ Throwable -> 0x02a9 }
            int r5 = (r10 > r15 ? 1 : (r10 == r15 ? 0 : -1))
            if (r5 <= 0) goto L_0x014d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = "select sum(size) from "
            r5.<init>(r9)     // Catch:{ Throwable -> 0x02a9 }
            r5.append(r8)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = " where ID >= "
            r5.append(r9)     // Catch:{ Throwable -> 0x02a9 }
            r5.append(r10)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r9 = " and ID < "
            r5.append(r9)     // Catch:{ Throwable -> 0x02a9 }
            r5.append(r3)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x02a9 }
            r4 = 0
            android.database.Cursor r3 = r7.rawQuery(r3, r4)     // Catch:{ Throwable -> 0x02a9 }
            r2.add(r3)     // Catch:{ Throwable -> 0x02a9 }
            r3.moveToFirst()     // Catch:{ Throwable -> 0x02a9 }
            r5 = 0
            long r25 = r3.getLong(r5)     // Catch:{ Throwable -> 0x02a9 }
            r5 = 0
            long r23 = r23 + r25
            com.amap.location.common.f.g.a(r3)     // Catch:{ Throwable -> 0x02a9 }
            r30 = r10
            r10 = r4
            r3 = r30
            goto L_0x00d4
        L_0x014a:
            com.amap.location.common.f.g.a(r5)     // Catch:{ Throwable -> 0x02a9 }
        L_0x014d:
            int r5 = (r23 > r15 ? 1 : (r23 == r15 ? 0 : -1))
            if (r5 <= 0) goto L_0x0154
            goto L_0x0156
        L_0x0152:
            r12 = r25
        L_0x0154:
            r3 = r21
        L_0x0156:
            int r5 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
            if (r5 <= 0) goto L_0x0289
            int r5 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0289
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Throwable -> 0x02a9 }
            r5.<init>()     // Catch:{ Throwable -> 0x02a9 }
            com.google.flatbuffers.FlatBufferBuilder r11 = new com.google.flatbuffers.FlatBufferBuilder     // Catch:{ Throwable -> 0x02a9 }
            r11.<init>()     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String[] r9 = com.amap.location.uptunnel.core.db.a.a.a     // Catch:{ Throwable -> 0x02a9 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r8 = " id >= "
            r7.<init>(r8)     // Catch:{ Throwable -> 0x02a9 }
            r7.append(r3)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r8 = " and id <= "
            r7.append(r8)     // Catch:{ Throwable -> 0x02a9 }
            r7.append(r12)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r10 = r7.toString()     // Catch:{ Throwable -> 0x02a9 }
            r23 = 0
            r24 = 0
            r7 = r6
            r8 = r14
            r15 = r11
            r11 = r23
            r25 = r12
            r12 = r24
            android.database.Cursor r7 = r7.a(r8, r9, r10, r11, r12)     // Catch:{ Throwable -> 0x02a9 }
            r2.add(r7)     // Catch:{ Throwable -> 0x02a9 }
            if (r7 == 0) goto L_0x0289
            int r8 = r7.getCount()     // Catch:{ Throwable -> 0x02a9 }
            if (r8 == 0) goto L_0x0289
            r10 = r3
            r3 = r21
            r12 = r3
            r8 = 0
        L_0x01a2:
            boolean r16 = r7.moveToNext()     // Catch:{ Throwable -> 0x02a9 }
            if (r16 == 0) goto L_0x01ec
            r27 = r6
            r6 = 0
            long r10 = r7.getLong(r6)     // Catch:{ Throwable -> 0x02a9 }
            r12 = 0
            long r10 = r10 + r19
            r12 = 1
            int r13 = r7.getInt(r12)     // Catch:{ Throwable -> 0x02a9 }
            r12 = 2
            byte[] r12 = r7.getBlob(r12)     // Catch:{ Throwable -> 0x02a9 }
            r6 = 3
            r28 = r10
            long r10 = r7.getLong(r6)     // Catch:{ Throwable -> 0x02a9 }
            int r6 = (r3 > r21 ? 1 : (r3 == r21 ? 0 : -1))
            if (r6 != 0) goto L_0x01c8
            r3 = r10
        L_0x01c8:
            r6 = 4
            int r6 = r7.getInt(r6)     // Catch:{ Throwable -> 0x02a9 }
            int r12 = com.amap.location.uptunnel.core.a.b.a(r15, r12)     // Catch:{ Throwable -> 0x02a9 }
            int r12 = com.amap.location.uptunnel.core.a.b.a(r15, r13, r12, r10)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Throwable -> 0x02a9 }
            r5.add(r12)     // Catch:{ Throwable -> 0x02a9 }
            long r12 = (long) r6     // Catch:{ Throwable -> 0x02a9 }
            long r8 = r8 + r12
            int r6 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            if (r6 < 0) goto L_0x01e6
            r8 = r3
            r3 = r28
            goto L_0x01f1
        L_0x01e6:
            r12 = r10
            r6 = r27
            r10 = r28
            goto L_0x01a2
        L_0x01ec:
            r27 = r6
            r8 = r3
            r3 = r10
            r10 = r12
        L_0x01f1:
            com.amap.location.common.f.g.a(r7)     // Catch:{ Throwable -> 0x02a9 }
            int r6 = r5.size()     // Catch:{ Throwable -> 0x02a9 }
            int[] r6 = new int[r6]     // Catch:{ Throwable -> 0x02a9 }
            r7 = 0
        L_0x01fb:
            int r12 = r5.size()     // Catch:{ Throwable -> 0x02a9 }
            if (r7 >= r12) goto L_0x0210
            java.lang.Object r12 = r5.get(r7)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.Integer r12 = (java.lang.Integer) r12     // Catch:{ Throwable -> 0x02a9 }
            int r12 = r12.intValue()     // Catch:{ Throwable -> 0x02a9 }
            r6[r7] = r12     // Catch:{ Throwable -> 0x02a9 }
            int r7 = r7 + 1
            goto L_0x01fb
        L_0x0210:
            com.amap.location.uptunnel.core.b r7 = r1.a     // Catch:{ Throwable -> 0x02a9 }
            android.content.Context r7 = r7.a()     // Catch:{ Throwable -> 0x02a9 }
            int r7 = com.amap.location.common.b.a.a(r15, r7)     // Catch:{ Throwable -> 0x02a9 }
            int r6 = com.amap.location.uptunnel.core.a.c.b(r15, r6)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.a.c.a(r15)     // Catch:{ Throwable -> 0x02a9 }
            r12 = 1
            com.amap.location.uptunnel.core.a.c.a(r15, r12)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.a.c.a(r15, r7)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.a.c.c(r15, r6)     // Catch:{ Throwable -> 0x02a9 }
            int r6 = com.amap.location.uptunnel.core.a.c.b(r15)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.a.c.d(r15, r6)     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.common.e.c r6 = r1.b     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.core.b r7 = r1.a     // Catch:{ Throwable -> 0x02a9 }
            com.amap.location.uptunnel.a.c r13 = r1.c     // Catch:{ Throwable -> 0x02a9 }
            int r13 = r13.c()     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r7 = r7.a(r13)     // Catch:{ Throwable -> 0x02a9 }
            byte[] r13 = r15.sizedByteArray()     // Catch:{ Throwable -> 0x02a9 }
            r15 = 120000(0x1d4c0, float:1.68156E-40)
            boolean r6 = com.amap.location.uptunnel.core.b.a.a(r6, r7, r13, r15)     // Catch:{ Throwable -> 0x02a9 }
            if (r6 != 0) goto L_0x0281
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r7 = "UpTunnel fail,条数是:"
            r6.<init>(r7)     // Catch:{ Throwable -> 0x02a9 }
            int r5 = r5.size()     // Catch:{ Throwable -> 0x02a9 }
            r6.append(r5)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r5 = ", 最后一条 id  是:"
            r6.append(r5)     // Catch:{ Throwable -> 0x02a9 }
            r6.append(r3)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r5 = ",第一条时间："
            r6.append(r5)     // Catch:{ Throwable -> 0x02a9 }
            r6.append(r8)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r5 = ",最后一条时间："
            r6.append(r5)     // Catch:{ Throwable -> 0x02a9 }
            r6.append(r10)     // Catch:{ Throwable -> 0x02a9 }
            java.lang.String r5 = r6.toString()     // Catch:{ Throwable -> 0x02a9 }
            byte[] r5 = r5.getBytes()     // Catch:{ Throwable -> 0x02a9 }
            r6 = 800001(0xc3501, float:1.12104E-39)
            com.amap.location.uptunnel.UpTunnel.reportEvent(r6, r5)     // Catch:{ Throwable -> 0x02a9 }
        L_0x0281:
            r12 = r25
            r6 = r27
            r15 = 0
            goto L_0x0156
        L_0x0289:
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x02dd
            r3 = 0
        L_0x0290:
            int r4 = r2.size()
            if (r3 >= r4) goto L_0x02a2
            java.lang.Object r4 = r2.get(r3)
            android.database.Cursor r4 = (android.database.Cursor) r4
            com.amap.location.common.f.g.a(r4)
            int r3 = r3 + 1
            goto L_0x0290
        L_0x02a2:
            r2.clear()
            return
        L_0x02a6:
            r0 = move-exception
            r3 = r0
            goto L_0x02de
        L_0x02a9:
            r0 = move-exception
            r3 = r0
            com.amap.location.common.d.a.a(r3)     // Catch:{ all -> 0x02a6 }
            java.lang.String r4 = android.util.Log.getStackTraceString(r3)     // Catch:{ Exception -> 0x02bd }
            byte[] r4 = r4.getBytes()     // Catch:{ Exception -> 0x02bd }
            r5 = 800001(0xc3501, float:1.12104E-39)
            com.amap.location.uptunnel.UpTunnel.reportEvent(r5, r4)     // Catch:{ Exception -> 0x02bd }
            goto L_0x02c0
        L_0x02bd:
            com.amap.location.common.d.a.a(r3)     // Catch:{ all -> 0x02a6 }
        L_0x02c0:
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x02dd
            r3 = 0
        L_0x02c7:
            int r4 = r2.size()
            if (r3 >= r4) goto L_0x02d9
            java.lang.Object r4 = r2.get(r3)
            android.database.Cursor r4 = (android.database.Cursor) r4
            com.amap.location.common.f.g.a(r4)
            int r3 = r3 + 1
            goto L_0x02c7
        L_0x02d9:
            r2.clear()
            return
        L_0x02dd:
            return
        L_0x02de:
            int r4 = r2.size()
            if (r4 <= 0) goto L_0x02fa
            r4 = 0
        L_0x02e5:
            int r5 = r2.size()
            if (r4 >= r5) goto L_0x02f7
            java.lang.Object r5 = r2.get(r4)
            android.database.Cursor r5 = (android.database.Cursor) r5
            com.amap.location.common.f.g.a(r5)
            int r4 = r4 + 1
            goto L_0x02e5
        L_0x02f7:
            r2.clear()
        L_0x02fa:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.uptunnel.a.a.a.run():void");
    }

    private void a() {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder();
        JSONObject jSONObject = new JSONObject();
        long currentTimeMillis = System.currentTimeMillis();
        try {
            jSONObject.put("time", com.amap.location.common.f.c.a(currentTimeMillis, null));
            jSONObject.put("command", this.c.a());
        } catch (JSONException e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
        int a2 = com.amap.location.common.b.a.a(flatBufferBuilder, this.a.a());
        int b2 = com.amap.location.uptunnel.core.a.c.b(flatBufferBuilder, new int[]{com.amap.location.uptunnel.core.a.b.a(flatBufferBuilder, 100003, com.amap.location.uptunnel.core.a.b.a(flatBufferBuilder, jSONObject.toString().getBytes()), currentTimeMillis)});
        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder);
        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder, 1);
        com.amap.location.uptunnel.core.a.c.a(flatBufferBuilder, a2);
        com.amap.location.uptunnel.core.a.c.c(flatBufferBuilder, b2);
        com.amap.location.uptunnel.core.a.c.d(flatBufferBuilder, com.amap.location.uptunnel.core.a.c.b(flatBufferBuilder));
        com.amap.location.uptunnel.core.b.a.a(this.b, this.a.a(2), flatBufferBuilder.sizedByteArray(), 60000);
    }
}
