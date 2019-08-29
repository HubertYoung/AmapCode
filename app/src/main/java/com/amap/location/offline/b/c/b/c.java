package com.amap.location.offline.b.c.b;

import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: RespOfflineCellWiFi */
public final class c extends Table {
    public static c a(ByteBuffer byteBuffer) {
        return a(byteBuffer, new c());
    }

    public static c a(ByteBuffer byteBuffer, c cVar) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return cVar.b(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public final void a(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final c b(int i, ByteBuffer byteBuffer) {
        a(i, byteBuffer);
        return this;
    }

    public final a a(int i) {
        return a(new a(), i);
    }

    public final a a(a aVar, int i) {
        int __offset = __offset(4);
        if (__offset != 0) {
            return aVar.b(__indirect(__vector(__offset) + (i * 4)), this.bb);
        }
        return null;
    }

    public final int a() {
        int __offset = __offset(4);
        if (__offset != 0) {
            return __vector_len(__offset);
        }
        return 0;
    }

    public final a b(int i) {
        return b(new a(), i);
    }

    public final a b(a aVar, int i) {
        int __offset = __offset(6);
        if (__offset != 0) {
            return aVar.b(__indirect(__vector(__offset) + (i * 4)), this.bb);
        }
        return null;
    }

    public final int b() {
        int __offset = __offset(6);
        if (__offset != 0) {
            return __vector_len(__offset);
        }
        return 0;
    }
}
