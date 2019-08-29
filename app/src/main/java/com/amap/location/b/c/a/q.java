package com.amap.location.b.c.a;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: TWifiInfo */
public final class q extends Table {
    public static int a(FlatBufferBuilder flatBufferBuilder, boolean z, long j, short s, int i, short s2, short s3) {
        flatBufferBuilder.startObject(6);
        a(flatBufferBuilder, j);
        a(flatBufferBuilder, i);
        c(flatBufferBuilder, s3);
        b(flatBufferBuilder, s2);
        a(flatBufferBuilder, s);
        a(flatBufferBuilder, z);
        return a(flatBufferBuilder);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(0, z, false);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(1, j, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(2, s, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(4, s, 0);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(5, s, 0);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
