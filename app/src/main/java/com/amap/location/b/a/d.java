package com.amap.location.b.a;

import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.location.b.c.a.b;
import com.amap.location.b.c.a.i;
import com.amap.location.b.c.g;
import com.amap.location.b.c.j;
import com.amap.location.b.f.a;
import com.amap.location.common.f.f;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.List;

/* compiled from: TrackBufferBuilder */
public class d extends a {
    public d() {
        super(500);
    }

    public byte[] a(@NonNull Context context, @NonNull g gVar, @NonNull List<j> list, byte b) {
        a();
        try {
            this.a.finish(b.a(this.a, a(gVar), a(list), b));
            return a.a(com.amap.location.b.f.d.a(context), f.a(this.a.sizedByteArray()));
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }

    private int a(@NonNull g gVar) {
        return i.a(this.a, gVar.a, gVar.b, gVar.c, gVar.d, gVar.e, gVar.f, gVar.g, gVar.h, gVar.i, gVar.j);
    }

    private int a(@NonNull List<j> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            j jVar = list.get(i);
            iArr[i] = com.amap.location.b.c.a.j.a(this.a, jVar.a, jVar.b, jVar.c, jVar.d, jVar.e);
        }
        return b.a((FlatBufferBuilder) this.a, iArr);
    }
}
