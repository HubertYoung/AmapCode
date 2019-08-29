package com.amap.location.offline.b.c.a;

import com.amap.location.common.d.a;
import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;

/* compiled from: RobustFlatBufferBuilder */
public class c extends FlatBufferBuilder {
    public c(int i) {
        super(i);
    }

    public c() {
        this(1024);
    }

    public c(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    public int createString(CharSequence charSequence) {
        try {
            return super.createString(charSequence);
        } catch (Throwable th) {
            a.a(th);
            return super.createString((CharSequence) "");
        }
    }
}
