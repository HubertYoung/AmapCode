package org.android.spdy;

import java.nio.ByteBuffer;
import java.util.ArrayList;

class HTTPHeaderPool {
    private static HTTPHeaderPool c = new HTTPHeaderPool();
    private ArrayList<ByteBufferAndString>[] a = new ArrayList[256];
    private ByteBufferAndStringMinHeap b = new ByteBufferAndStringMinHeap();

    private HTTPHeaderPool() {
    }

    public final String a(ByteBuffer byteBuffer) {
        String str;
        int hashCode = new ByteBufferAndString(byteBuffer).hashCode() & 255;
        ArrayList<ByteBufferAndString> arrayList = this.a[hashCode];
        ByteBufferAndString byteBufferAndString = null;
        if (arrayList != null) {
            str = null;
            for (int i = 0; i < arrayList.size(); i++) {
                ByteBufferAndString byteBufferAndString2 = arrayList.get(i);
                if (byteBufferAndString2.b.equals(byteBuffer)) {
                    str = byteBufferAndString2.toString();
                    byteBufferAndString2.c = System.currentTimeMillis();
                    ByteBufferAndStringMinHeap byteBufferAndStringMinHeap = this.b;
                    if (byteBufferAndString2.a != -1) {
                        byteBufferAndStringMinHeap.a(byteBufferAndString2.a, 0);
                        byteBufferAndStringMinHeap.a();
                    }
                }
            }
        } else {
            str = null;
        }
        if (str != null) {
            return str;
        }
        int limit = byteBuffer.limit() - byteBuffer.position();
        byte[] bArr = new byte[limit];
        System.arraycopy(byteBuffer.array(), byteBuffer.position(), bArr, 0, limit);
        ByteBufferAndString byteBufferAndString3 = new ByteBufferAndString(ByteBuffer.wrap(bArr));
        ByteBufferAndStringMinHeap byteBufferAndStringMinHeap2 = this.b;
        if (byteBufferAndStringMinHeap2.b < byteBufferAndStringMinHeap2.a.length) {
            byteBufferAndStringMinHeap2.a[byteBufferAndStringMinHeap2.b] = byteBufferAndString3;
            byteBufferAndStringMinHeap2.a[byteBufferAndStringMinHeap2.b].a = byteBufferAndStringMinHeap2.b;
            int i2 = byteBufferAndStringMinHeap2.b;
            byteBufferAndStringMinHeap2.b = i2 + 1;
            byteBufferAndStringMinHeap2.a(0, i2);
            byteBufferAndStringMinHeap2.a();
        } else {
            byteBufferAndString = byteBufferAndStringMinHeap2.a[0];
            ByteBufferAndString[] byteBufferAndStringArr = byteBufferAndStringMinHeap2.a;
            ByteBufferAndString[] byteBufferAndStringArr2 = byteBufferAndStringMinHeap2.a;
            int i3 = byteBufferAndStringMinHeap2.b - 1;
            byteBufferAndStringMinHeap2.b = i3;
            byteBufferAndStringArr[0] = byteBufferAndStringArr2[i3];
            byteBufferAndStringMinHeap2.a[0].a = 0;
            byteBufferAndStringMinHeap2.a();
            byteBufferAndString.a = -1;
        }
        if (arrayList == null) {
            this.a[hashCode] = new ArrayList<>();
        }
        this.a[hashCode].add(byteBufferAndString3);
        if (byteBufferAndString != null) {
            this.a[byteBufferAndString.hashCode() & 255].remove(byteBufferAndString);
        }
        return byteBufferAndString3.toString();
    }

    public static HTTPHeaderPool a() {
        return c;
    }
}
