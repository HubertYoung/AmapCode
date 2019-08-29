package com.amap.location.b.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.location.b.c.a.a;
import com.amap.location.b.c.a.i;
import com.amap.location.b.c.a.p;
import com.amap.location.b.c.a.q;
import com.amap.location.b.c.c;
import com.amap.location.b.c.g;
import com.amap.location.b.c.h;
import com.amap.location.b.c.k;
import com.amap.location.b.c.l;
import com.amap.location.b.f.d;
import com.amap.location.common.f.f;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: FpsBufferBuilder */
public class b extends a {
    public b() {
        super(2048);
    }

    @Nullable
    public byte[] a(@NonNull Context context, @NonNull g gVar, @Nullable com.amap.location.b.c.b bVar, long j, @Nullable List<l> list) {
        super.a();
        try {
            int a = a(gVar);
            int i = -1;
            int a2 = (bVar == null || bVar.c.size() <= 0) ? -1 : a(bVar);
            if (list != null && list.size() > 0) {
                i = a(j, list);
            }
            a.a(this.a);
            a.a(this.a, a);
            if (a2 > 0) {
                a.c(this.a, a2);
            }
            if (i > 0) {
                a.b(this.a, i);
            }
            this.a.finish(a.b(this.a));
            return com.amap.location.b.f.a.a(d.a(context), f.a(this.a.sizedByteArray()));
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }

    private int a(@NonNull g gVar) {
        return i.a(this.a, gVar.a, gVar.b, gVar.c, gVar.d, gVar.e, gVar.f, gVar.g, gVar.h, gVar.i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00eb A[LOOP:0: B:3:0x0014->B:27:0x00eb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ff A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(@android.support.annotation.NonNull com.amap.location.b.c.b r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            java.util.ArrayList<com.amap.location.b.c.c> r2 = r1.c
            r0.a(r2)
            java.util.ArrayList<com.amap.location.b.c.c> r2 = r1.c
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0180
            int[] r4 = new int[r2]
            r6 = 0
        L_0x0014:
            r7 = 2
            r8 = 1
            if (r6 >= r2) goto L_0x0100
            java.util.ArrayList<com.amap.location.b.c.c> r9 = r1.c
            java.lang.Object r9 = r9.get(r6)
            com.amap.location.b.c.c r9 = (com.amap.location.b.c.c) r9
            byte r10 = r9.a
            if (r10 != r8) goto L_0x0060
            T r7 = r9.f
            com.amap.location.b.c.h r7 = (com.amap.location.b.c.h) r7
            byte r8 = r9.c
            if (r8 != 0) goto L_0x003e
            com.amap.location.b.a.c r8 = r0.a
            int r10 = r7.c
            int r11 = r7.d
            int r12 = r7.e
            int r7 = r7.i
            int r7 = com.amap.location.b.c.a.k.a(r8, r10, r11, r12, r7)
            r15 = r7
        L_0x003b:
            r3 = -1
            goto L_0x00e9
        L_0x003e:
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r7.a
            int r12 = r7.b
            int r13 = r7.c
            int r14 = r7.d
            int r15 = r7.e
            int r8 = r7.f
            int r5 = r7.g
            int r3 = r7.h
            int r7 = r7.i
            r16 = r8
            r17 = r5
            r18 = r3
            r19 = r7
            int r3 = com.amap.location.b.c.a.k.a(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
        L_0x005e:
            r15 = r3
            goto L_0x003b
        L_0x0060:
            byte r3 = r9.a
            r5 = 3
            if (r3 != r5) goto L_0x0086
            T r3 = r9.f
            com.amap.location.b.c.i r3 = (com.amap.location.b.c.i) r3
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r3.a
            int r12 = r3.b
            int r13 = r3.c
            int r14 = r3.d
            int r15 = r3.e
            int r5 = r3.f
            int r7 = r3.g
            int r3 = r3.h
            r16 = r5
            r17 = r7
            r18 = r3
            int r3 = com.amap.location.b.c.a.l.a(r10, r11, r12, r13, r14, r15, r16, r17, r18)
            goto L_0x005e
        L_0x0086:
            byte r3 = r9.a
            if (r3 != r7) goto L_0x00c0
            T r3 = r9.f
            com.amap.location.b.c.a r3 = (com.amap.location.b.c.a) r3
            byte r5 = r9.c
            if (r5 != 0) goto L_0x00a7
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r3.a
            int r12 = r3.b
            int r13 = r3.c
            int r14 = r3.d
            int r15 = r3.e
            int r3 = r3.f
            r16 = r3
            int r3 = com.amap.location.b.c.a.d.a(r10, r11, r12, r13, r14, r15, r16)
            goto L_0x005e
        L_0x00a7:
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r3.a
            int r12 = r3.b
            int r13 = r3.c
            int r14 = r3.d
            int r15 = r3.e
            int r5 = r3.f
            int r3 = r3.g
            r16 = r5
            r17 = r3
            int r3 = com.amap.location.b.c.a.d.a(r10, r11, r12, r13, r14, r15, r16, r17)
            goto L_0x005e
        L_0x00c0:
            byte r3 = r9.a
            r5 = 4
            if (r3 != r5) goto L_0x00e7
            T r3 = r9.f
            com.amap.location.b.c.k r3 = (com.amap.location.b.c.k) r3
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r3.a
            int r12 = r3.b
            int r13 = r3.c
            int r14 = r3.d
            int r15 = r3.e
            int r5 = r3.f
            int r7 = r3.g
            int r3 = r3.h
            r16 = r5
            r17 = r7
            r18 = r3
            int r3 = com.amap.location.b.c.a.o.a(r10, r11, r12, r13, r14, r15, r16, r17, r18)
            goto L_0x005e
        L_0x00e7:
            r3 = -1
            r15 = -1
        L_0x00e9:
            if (r15 == r3) goto L_0x00ff
            com.amap.location.b.a.c r10 = r0.a
            byte r11 = r9.b
            byte r12 = r9.c
            short r13 = r9.d
            byte r14 = r9.a
            int r3 = com.amap.location.b.c.a.g.a(r10, r11, r12, r13, r14, r15)
            r4[r6] = r3
            int r6 = r6 + 1
            goto L_0x0014
        L_0x00ff:
            return r3
        L_0x0100:
            com.amap.location.b.a.c r2 = r0.a
            java.lang.String r3 = r1.b
            int r2 = r2.createString(r3)
            com.amap.location.b.a.c r3 = r0.a
            int r3 = com.amap.location.b.c.a.e.a(r3, r4)
            java.util.List<com.amap.location.common.model.CellStatus$HistoryCell> r4 = r1.d
            int r4 = r4.size()
            int[] r5 = new int[r4]
            r6 = 0
        L_0x0117:
            if (r6 >= r4) goto L_0x0171
            java.util.List<com.amap.location.common.model.CellStatus$HistoryCell> r9 = r1.d
            java.lang.Object r9 = r9.get(r6)
            com.amap.location.common.model.CellStatus$HistoryCell r9 = (com.amap.location.common.model.CellStatus.HistoryCell) r9
            long r10 = android.os.SystemClock.elapsedRealtime()
            long r12 = r9.lastUpdateTimeMills
            long r10 = r10 - r12
            r12 = 1000(0x3e8, double:4.94E-321)
            long r10 = r10 / r12
            r12 = 32767(0x7fff, double:1.6189E-319)
            int r14 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r14 > 0) goto L_0x0139
            r14 = 0
            int r14 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r14 >= 0) goto L_0x0138
            goto L_0x0139
        L_0x0138:
            r12 = r10
        L_0x0139:
            int r10 = r9.type
            if (r10 != r7) goto L_0x0157
            com.amap.location.b.a.c r14 = r0.a
            r15 = 2
            int r10 = r9.sid
            int r11 = r9.nid
            int r9 = r9.bid
            int r12 = (int) r12
            short r12 = (short) r12
            r16 = r10
            r17 = r11
            r18 = r9
            r19 = r12
            int r9 = com.amap.location.b.c.a.m.a(r14, r15, r16, r17, r18, r19)
            r10 = r9
            r9 = 2
            goto L_0x0165
        L_0x0157:
            com.amap.location.b.a.c r10 = r0.a
            int r11 = r9.lac
            int r9 = r9.cid
            int r12 = (int) r12
            short r12 = (short) r12
            int r9 = com.amap.location.b.c.a.n.a(r10, r8, r11, r9, r12)
            r10 = r9
            r9 = 1
        L_0x0165:
            com.amap.location.b.a.c r11 = r0.a
            byte r9 = (byte) r9
            int r9 = com.amap.location.b.c.a.f.a(r11, r9, r10)
            r5[r6] = r9
            int r6 = r6 + 1
            goto L_0x0117
        L_0x0171:
            com.amap.location.b.a.c r4 = r0.a
            int r4 = com.amap.location.b.c.a.e.b(r4, r5)
            com.amap.location.b.a.c r5 = r0.a
            byte r1 = r1.a
            int r3 = com.amap.location.b.c.a.e.a(r5, r2, r1, r3, r4)
            goto L_0x0181
        L_0x0180:
            r3 = -1
        L_0x0181:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.a.b.a(com.amap.location.b.c.b):int");
    }

    private int a(long j, @NonNull List<l> list) {
        List<l> list2 = list;
        a(list2);
        int size = list.size();
        if (size <= 0) {
            return -1;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            l lVar = list2.get(i);
            iArr[i] = q.a(this.a, lVar.a == j && lVar.a != -1, lVar.a, lVar.b, this.a.createString(lVar.c), lVar.d, lVar.f);
        }
        return p.a((FlatBufferBuilder) this.a, p.a((FlatBufferBuilder) this.a, iArr));
    }

    private void a(ArrayList<c> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<c> it = arrayList.iterator();
            while (it.hasNext()) {
                c next = it.next();
                if (next.a == 1) {
                    h hVar = (h) next.f;
                    next.d = com.amap.location.b.d.a.a(com.amap.location.b.d.a.a(hVar.c, hVar.d));
                } else if (next.a == 3) {
                    com.amap.location.b.c.i iVar = (com.amap.location.b.c.i) next.f;
                    next.d = com.amap.location.b.d.a.a(com.amap.location.b.d.a.a(iVar.c, iVar.d));
                } else if (next.a == 4) {
                    k kVar = (k) next.f;
                    next.d = com.amap.location.b.d.a.a(com.amap.location.b.d.a.a(kVar.c, kVar.d));
                } else if (next.a == 2) {
                    com.amap.location.b.c.a aVar = (com.amap.location.b.c.a) next.f;
                    next.d = com.amap.location.b.d.a.a(com.amap.location.b.d.a.a(aVar.b, aVar.c));
                }
            }
        }
    }

    private void a(@NonNull List<l> list) {
        for (l next : list) {
            next.d = com.amap.location.b.d.a.b(next.a);
        }
    }
}
