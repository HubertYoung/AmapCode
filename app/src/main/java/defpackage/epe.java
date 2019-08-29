package defpackage;

import com.autonavi.sdk.location.monitor.flatbuffers.tools.FlatBufferBuilder;
import java.util.List;

/* renamed from: epe reason: default package */
/* compiled from: FBDataBuilder */
public final class epe {
    public static byte[] a(eoz eoz, List<epa> list, List<epd> list2, epc epc) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        FlatBufferBuilder flatBufferBuilder;
        int i8;
        epc epc2;
        eoz eoz2 = eoz;
        List<epd> list3 = list2;
        epc epc3 = epc;
        try {
            FlatBufferBuilder flatBufferBuilder2 = new FlatBufferBuilder(0);
            int a = flatBufferBuilder2.a((CharSequence) eoz2.a);
            int a2 = flatBufferBuilder2.a((CharSequence) eoz2.b);
            int a3 = flatBufferBuilder2.a((CharSequence) eoz2.i);
            int a4 = flatBufferBuilder2.a((CharSequence) eoz2.c);
            int a5 = flatBufferBuilder2.a((CharSequence) eoz2.d);
            int a6 = flatBufferBuilder2.a((CharSequence) eoz2.p);
            if (list.size() > 0) {
                int size = list.size();
                int[] iArr = new int[size];
                int i9 = 0;
                while (i9 < size) {
                    epa epa = list.get(i9);
                    int i10 = i9;
                    int[] iArr2 = iArr;
                    int i11 = a6;
                    int i12 = a4;
                    int i13 = a2;
                    int[] iArr3 = iArr2;
                    iArr3[i10] = epf.a(flatBufferBuilder2, epa.a, epa.b, epa.c, epa.d, epa.e, epa.f, epa.g, epa.h, epa.i, epa.j, epa.k, epa.l, epa.m, epa.n, epa.o, epa.p, epa.q, epa.r);
                    i9 = i10 + 1;
                    iArr = iArr3;
                    size = size;
                    a6 = i11;
                    a4 = i12;
                    a5 = a5;
                    a2 = i13;
                    a3 = a3;
                    a = a;
                    flatBufferBuilder2 = flatBufferBuilder2;
                    eoz eoz3 = eoz;
                    List<epd> list4 = list2;
                    epc epc4 = epc;
                }
                i6 = a6;
                i5 = a4;
                i4 = a5;
                i3 = a2;
                i2 = a3;
                i = a;
                flatBufferBuilder = flatBufferBuilder2;
                i7 = epg.a(flatBufferBuilder, iArr);
                list3 = list2;
            } else {
                i6 = a6;
                i5 = a4;
                i4 = a5;
                i3 = a2;
                i2 = a3;
                i = a;
                flatBufferBuilder = flatBufferBuilder2;
                i7 = -1;
            }
            if (list3 == null || list2.size() <= 0) {
                epc2 = epc;
                i8 = -1;
            } else {
                int size2 = list2.size();
                int[] iArr4 = new int[size2];
                for (int i14 = 0; i14 < size2; i14++) {
                    epd epd = list3.get(i14);
                    iArr4[i14] = epi.a(flatBufferBuilder, epd.a, epd.b);
                }
                i8 = epg.b(flatBufferBuilder, iArr4);
                epc2 = epc;
            }
            int i15 = i7;
            short s = (short) epc2.l;
            short s2 = (short) epc2.m;
            boolean z = epc2.n;
            short s3 = (short) epc2.o;
            short s4 = (short) epc2.p;
            short s5 = (short) epc2.q;
            short s6 = (short) epc2.s;
            int i16 = i8;
            short s7 = s;
            int a7 = eph.a(flatBufferBuilder, epc2.a, (byte) epc2.b, (byte) epc2.c, (byte) epc2.d, (byte) epc2.e, (byte) epc2.f, (byte) epc2.g, (byte) epc2.h, (byte) epc2.i, (byte) epc2.j, (byte) epc2.k, s7, s2, z, s3, s4, s5, (short) epc2.r, s6);
            epg.a(flatBufferBuilder);
            epg.a(flatBufferBuilder, i);
            epg.b(flatBufferBuilder, i3);
            epg.c(flatBufferBuilder, i5);
            epg.d(flatBufferBuilder, i4);
            eoz eoz4 = eoz;
            epg.a(flatBufferBuilder, eoz4.e);
            epg.b(flatBufferBuilder, eoz4.f);
            epg.c(flatBufferBuilder, eoz4.g);
            epg.d(flatBufferBuilder, eoz4.h);
            epg.e(flatBufferBuilder, i2);
            epg.a(flatBufferBuilder, eoz4.j);
            epg.e(flatBufferBuilder, eoz4.k);
            epg.b(flatBufferBuilder, eoz4.l);
            epg.c(flatBufferBuilder, eoz4.m);
            int i17 = i15;
            if (i17 > 0) {
                epg.f(flatBufferBuilder, i17);
            }
            int i18 = i16;
            if (i18 > 0) {
                epg.g(flatBufferBuilder, i18);
            }
            if (a7 > 0) {
                epg.h(flatBufferBuilder, a7);
            }
            epg.d(flatBufferBuilder, eoz4.n);
            epg.e(flatBufferBuilder, eoz4.o);
            epg.i(flatBufferBuilder, i6);
            int b = epg.b(flatBufferBuilder);
            flatBufferBuilder.a(flatBufferBuilder.d, 4);
            flatBufferBuilder.a(b);
            flatBufferBuilder.a.position(flatBufferBuilder.b);
            flatBufferBuilder.h = true;
            int i19 = flatBufferBuilder.b;
            int capacity = flatBufferBuilder.a.capacity() - flatBufferBuilder.b;
            if (!flatBufferBuilder.h) {
                throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
            }
            byte[] bArr = new byte[capacity];
            flatBufferBuilder.a.position(i19);
            flatBufferBuilder.a.get(bArr);
            return bArr;
        } catch (Throwable th) {
            epk.a(th);
            return null;
        }
    }
}
