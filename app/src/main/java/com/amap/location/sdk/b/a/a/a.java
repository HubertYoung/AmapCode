package com.amap.location.sdk.b.a.a;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: NFlatLocationInfo */
public final class a extends Table {
    public static void a(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(11);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, double d) {
        flatBufferBuilder.addDouble(0, d, 0.0d);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, double d) {
        flatBufferBuilder.addDouble(1, d, 0.0d);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(3, f, 0.0d);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(4, j, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(5, s, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(6, i, 0);
    }

    public static int b(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
