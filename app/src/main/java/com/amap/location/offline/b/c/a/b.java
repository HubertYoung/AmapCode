package com.amap.location.offline.b.c.a;

import android.support.annotation.Nullable;
import com.amap.location.common.d.a;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.List;

/* compiled from: RequestBufferBuilder */
public class b extends a {
    public b() {
        super(1000);
    }

    @Nullable
    public byte[] a(byte b, String str, byte b2, String str2, String str3, byte b3, String str4, String str5, String str6, String str7, long j, String str8, String str9, String str10, String str11, List<Long> list, List<String> list2) {
        int i;
        List<Long> list3 = list;
        List<String> list4 = list2;
        super.a();
        try {
            int createString = this.a.createString(str);
            int createString2 = this.a.createString(str2);
            int createString3 = this.a.createString(str3);
            int createString4 = this.a.createString(str4);
            int createString5 = this.a.createString(str5);
            int createString6 = this.a.createString(str6);
            int createString7 = this.a.createString(str7);
            int createString8 = this.a.createString(str8);
            int createString9 = this.a.createString(str9);
            int createString10 = this.a.createString(str10);
            int createString11 = this.a.createString(str11);
            com.amap.location.common.b.b.a(this.a);
            int i2 = createString;
            com.amap.location.common.b.b.a((FlatBufferBuilder) this.a, b2);
            com.amap.location.common.b.b.a((FlatBufferBuilder) this.a, createString2);
            com.amap.location.common.b.b.b((FlatBufferBuilder) this.a, createString3);
            com.amap.location.common.b.b.b((FlatBufferBuilder) this.a, b3);
            com.amap.location.common.b.b.e(this.a, createString4);
            com.amap.location.common.b.b.f(this.a, createString5);
            com.amap.location.common.b.b.d(this.a, createString6);
            com.amap.location.common.b.b.c(this.a, createString7);
            com.amap.location.common.b.b.a((FlatBufferBuilder) this.a, j);
            com.amap.location.common.b.b.g(this.a, createString8);
            com.amap.location.common.b.b.h(this.a, createString9);
            com.amap.location.common.b.b.i(this.a, createString10);
            com.amap.location.common.b.b.j(this.a, createString11);
            int b4 = com.amap.location.common.b.b.b(this.a);
            int i3 = 0;
            if (list3 == null || list.size() <= 0) {
                i = 0;
            } else {
                long[] jArr = new long[list.size()];
                for (int i4 = 0; i4 < list.size(); i4++) {
                    Long l = list3.get(i4);
                    if (l != null) {
                        jArr[i4] = l.longValue();
                    } else {
                        jArr[i4] = 0;
                    }
                }
                i = com.amap.location.offline.b.c.b.b.a((FlatBufferBuilder) this.a, jArr);
            }
            if (list4 != null && list2.size() > 0) {
                int[] iArr = new int[list2.size()];
                while (i3 < list2.size()) {
                    iArr[i3] = this.a.createString(list4.get(i3));
                    i3++;
                }
                i3 = com.amap.location.offline.b.c.b.b.a((FlatBufferBuilder) this.a, iArr);
            }
            com.amap.location.offline.b.c.b.b.a(this.a);
            com.amap.location.offline.b.c.b.b.a((FlatBufferBuilder) this.a, b);
            com.amap.location.offline.b.c.b.b.a((FlatBufferBuilder) this.a, i2);
            com.amap.location.offline.b.c.b.b.a((FlatBufferBuilder) this.a, System.currentTimeMillis() / 1000);
            com.amap.location.offline.b.c.b.b.b(this.a, b4);
            if (i > 0) {
                com.amap.location.offline.b.c.b.b.c(this.a, i);
            }
            if (i3 > 0) {
                com.amap.location.offline.b.c.b.b.d(this.a, i3);
            }
            com.amap.location.offline.b.c.b.b.e(this.a, com.amap.location.offline.b.c.b.b.b(this.a));
            return this.a.sizedByteArray();
        } catch (Throwable th) {
            a.a(th);
            return null;
        }
    }
}
