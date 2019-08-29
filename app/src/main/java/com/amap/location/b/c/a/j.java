package com.amap.location.b.c.a;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: TGpsStatus */
public final class j extends Table {
    public static int a(FlatBufferBuilder flatBufferBuilder, byte b, byte b2, byte b3, short s, byte b4) {
        flatBufferBuilder.startObject(6);
        a(flatBufferBuilder, s);
        d(flatBufferBuilder, b4);
        c(flatBufferBuilder, b3);
        b(flatBufferBuilder, b2);
        a(flatBufferBuilder, b);
        return a(flatBufferBuilder);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(0, b, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(1, b, 0);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(2, b, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(3, s, 0);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(4, b, 0);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
