package com.amap.location.b.a;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.location.b.a;
import com.amap.location.b.c.a.c;
import com.amap.location.b.c.a.h;
import com.amap.location.b.e.b;
import com.amap.location.b.f.d;
import com.amap.location.b.f.f;
import com.google.flatbuffers.FlatBufferBuilder;

/* compiled from: UploadBufferBuilder */
public class e extends a {
    public e() {
        super(5120);
    }

    @Nullable
    public byte[] a(@NonNull Context context, @NonNull a aVar, @NonNull b bVar) {
        try {
            byte[] a = f.a(com.amap.location.b.f.a.a(d.a(context)));
            byte[] a2 = a(context, aVar);
            int size = bVar.b.size();
            if (size <= 0 || a == null) {
                return null;
            }
            a();
            int a3 = c.a((FlatBufferBuilder) this.a, a);
            int[] iArr = new int[size];
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                com.amap.location.b.c.d dVar = bVar.b.get(i2);
                iArr[i2] = h.a(this.a, (byte) dVar.b(), h.a((FlatBufferBuilder) this.a, dVar.c()));
            }
            int a4 = c.a((FlatBufferBuilder) this.a, iArr);
            if (a2 != null) {
                i = c.b((FlatBufferBuilder) this.a, a2);
            }
            this.a.finish(c.a(this.a, a3, i, a4));
            return com.amap.location.common.f.f.a(this.a.sizedByteArray());
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return null;
        }
    }

    private byte[] a(Context context, a aVar) {
        super.a();
        this.a.finish(com.amap.location.common.b.b.a(this.a, aVar.a(), this.a.createString(d.a(context)), this.a.createString(aVar.b()), (byte) VERSION.SDK_INT, this.a.createString(com.amap.location.common.a.a()), this.a.createString(aVar.e()), this.a.createString(com.amap.location.b.f.h.a(com.amap.location.common.a.a(context))), this.a.createString(com.amap.location.b.f.h.a(com.amap.location.common.a.d(context))), com.amap.location.common.f.h.a(com.amap.location.common.a.f(context)), this.a.createString(com.amap.location.common.a.c()), this.a.createString(com.amap.location.common.a.b()), this.a.createString(aVar.c()), this.a.createString(aVar.d())));
        try {
            return com.amap.location.b.f.a.a(d.a(context), this.a.sizedByteArray());
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            return null;
        }
    }
}
