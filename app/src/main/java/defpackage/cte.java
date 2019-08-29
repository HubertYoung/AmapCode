package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/* renamed from: cte reason: default package */
/* compiled from: InputStreamChannel */
public final class cte implements ReadableByteChannel {
    private InputStream a;

    public final boolean isOpen() {
        return true;
    }

    public cte(InputStream inputStream) {
        this.a = inputStream;
    }

    public final int read(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            return -1;
        }
        int capacity = byteBuffer.capacity();
        byte[] bArr = new byte[capacity];
        int i = 0;
        while (true) {
            int read = this.a.read(bArr, i, capacity - i);
            if (read > 0) {
                i += read;
                if (i >= capacity) {
                    break;
                }
            } else if (i == 0) {
                i = read;
            }
        }
        byteBuffer.clear();
        if (i > 0) {
            byteBuffer.put(bArr, 0, i);
        }
        byteBuffer.flip();
        return i;
    }

    public final void close() throws IOException {
        this.a.close();
    }
}
