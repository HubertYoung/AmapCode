package com.amap.location.b.c.a;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

/* compiled from: TGps */
public final class i extends Table {
    public static int a(FlatBufferBuilder flatBufferBuilder, long j, long j2, int i, int i2, int i3, int i4, int i5, short s, byte b, short s2) {
        flatBufferBuilder.startObject(10);
        b(flatBufferBuilder, j2);
        a(flatBufferBuilder, j);
        e(flatBufferBuilder, i5);
        d(flatBufferBuilder, i4);
        c(flatBufferBuilder, i3);
        b(flatBufferBuilder, i2);
        a(flatBufferBuilder, i);
        b(flatBufferBuilder, s2);
        a(flatBufferBuilder, s);
        a(flatBufferBuilder, b);
        return a(flatBufferBuilder);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder, long j, long j2, int i, int i2, int i3, int i4, int i5, short s, byte b) {
        flatBufferBuilder.startObject(10);
        b(flatBufferBuilder, j2);
        a(flatBufferBuilder, j);
        e(flatBufferBuilder, i5);
        d(flatBufferBuilder, i4);
        c(flatBufferBuilder, i3);
        b(flatBufferBuilder, i2);
        a(flatBufferBuilder, i);
        a(flatBufferBuilder, s);
        a(flatBufferBuilder, b);
        return a(flatBufferBuilder);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(0, j, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addLong(1, j, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(2, i, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(3, i, 0);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(4, i, 0);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(5, i, 0);
    }

    public static void e(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(6, i, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(7, s, 0);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(8, b, 0);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, short s) {
        flatBufferBuilder.addShort(9, s, 0);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
