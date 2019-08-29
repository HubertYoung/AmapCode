package com.amap.location.b.a;

import com.amap.location.common.d.a;
import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;

/* compiled from: RobustFlatBufferBuilder */
public class c extends FlatBufferBuilder {
    public c() {
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
