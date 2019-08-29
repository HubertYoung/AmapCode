package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: cn reason: default package */
/* compiled from: ByteCounterInputStream */
public final class cn extends InputStream {
    public long a = 0;
    private InputStream b = null;

    public cn(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("input stream cannot be null");
        }
        this.b = inputStream;
    }

    public final int read() throws IOException {
        this.a++;
        return this.b.read();
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.b.read(bArr, i, i2);
        if (read != -1) {
            this.a += (long) read;
        }
        return read;
    }
}
