package defpackage;

import com.autonavi.sdk.location.monitor.flatbuffers.tools.FlatBufferBuilder;

/* renamed from: epg reason: default package */
/* compiled from: FBLMLocationRecord */
public final class epg extends epj {
    public static void a(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.b(19);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(0, i);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(1, i);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(2, i);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(3, i);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.a(4, b, -1);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.a(5, b, -1);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.a(6, b, -1);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.a(7, b, -1);
    }

    public static void e(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(8, i);
    }

    public static void a(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.a(9, j);
    }

    public static void e(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.a(10, b, -1);
    }

    public static void b(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.a(11, j);
    }

    public static void c(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.a(12, j);
    }

    public static void f(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(13, i);
    }

    public static int a(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.a(iArr[length]);
        }
        return flatBufferBuilder.a();
    }

    public static void g(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(14, i);
    }

    public static int b(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.a(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.a(iArr[length]);
        }
        return flatBufferBuilder.a();
    }

    public static void h(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(15, i);
    }

    public static void d(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.a(16, j);
    }

    public static void e(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.a(17, j);
    }

    public static void i(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.b(18, i);
    }

    public static int b(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.b();
    }
}
