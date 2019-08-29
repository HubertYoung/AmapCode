package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: bpq reason: default package */
/* compiled from: StatisticsWrapInputStream */
public final class bpq extends InputStream {
    public long a = 0;
    private InputStream b;

    public final long skip(long j) throws IOException {
        if (this.b == null) {
            return 0;
        }
        return this.b.skip(j);
    }

    public final int available() throws IOException {
        if (this.b == null) {
            return 0;
        }
        return this.b.available();
    }

    public final synchronized void mark(int i) {
        if (this.b != null) {
            this.b.mark(i);
        }
    }

    public final synchronized void reset() throws IOException {
        if (this.b != null) {
            this.b.reset();
        }
    }

    public final boolean markSupported() {
        if (this.b == null) {
            return false;
        }
        return this.b.markSupported();
    }

    public bpq(InputStream inputStream) {
        this.b = inputStream;
    }

    public final int read() throws IOException {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read();
        if (read >= 0) {
            this.a += (long) read;
        }
        return read;
    }

    public final int read(byte[] bArr) throws IOException {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read(bArr);
        if (read >= 0) {
            this.a += (long) read;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read(bArr, i, i2);
        if (read >= 0) {
            this.a += (long) read;
        }
        return read;
    }

    public final void close() throws IOException {
        if (this.b != null) {
            this.b.close();
        }
    }
}
