package com.amap.location.b.c.a;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: TMainCellGSMHistory */
public final class n extends Table {
    public static int a(FlatBufferBuilder flatBufferBuilder, byte b, int i, int i2, short s) {
        flatBufferBuilder.startObject(4);
        b(flatBufferBuilder, i2);
        a(flatBufferBuilder, i);
        a(flatBufferBuilder, s);
        a(flatBufferBuilder, b);
        return a(flatBufferBuilder);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(0, b, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(1, i, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(2, i, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(3, s, 0);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
