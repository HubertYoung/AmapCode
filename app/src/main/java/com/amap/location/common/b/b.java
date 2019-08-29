package com.amap.location.common.b;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: THeader */
public final class b extends Table {
    public static int a(FlatBufferBuilder flatBufferBuilder, byte b, int i, int i2, byte b2, int i3, int i4, int i5, int i6, long j, int i7, int i8, int i9, int i10) {
        flatBufferBuilder.startObject(13);
        a(flatBufferBuilder, j);
        j(flatBufferBuilder, i10);
        i(flatBufferBuilder, i9);
        h(flatBufferBuilder, i8);
        g(flatBufferBuilder, i7);
        f(flatBufferBuilder, i6);
        e(flatBufferBuilder, i5);
        d(flatBufferBuilder, i4);
        c(flatBufferBuilder, i3);
        b(flatBufferBuilder, i2);
        a(flatBufferBuilder, i);
        b(flatBufferBuilder, b2);
        a(flatBufferBuilder, b);
        return b(flatBufferBuilder);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(13);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(0, b, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(3, b, 0);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(5, i, 0);
    }

    public static void e(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(6, i, 0);
    }

    public static void f(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(7, i, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(8, j, 0);
    }

    public static void g(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(9, i, 0);
    }

    public static void h(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(10, i, 0);
    }

    public static void i(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(11, i, 0);
    }

    public static void j(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(12, i, 0);
    }

    public static int b(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
