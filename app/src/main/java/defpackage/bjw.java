package defpackage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: bjw reason: default package */
/* compiled from: MarkableInputStream */
final class bjw extends InputStream {
    boolean a;
    private final InputStream b;
    private long c;
    private long d;
    private long e;
    private long f;
    private int g;

    public bjw(InputStream inputStream) {
        this(inputStream, 0);
    }

    private bjw(InputStream inputStream, byte b2) {
        this(inputStream, 0);
    }

    private bjw(InputStream inputStream, char c2) {
        this.f = -1;
        this.a = true;
        this.g = -1;
        this.b = !inputStream.markSupported() ? new BufferedInputStream(inputStream, 4096) : inputStream;
        this.g = 1024;
    }

    public final void mark(int i) {
        this.f = a(i);
    }

    public final long a(int i) {
        long j = this.c + ((long) i);
        if (this.e < j) {
            b(j);
        }
        return this.c;
    }

    private void b(long j) {
        try {
            if (this.d >= this.c || this.c > this.e) {
                this.d = this.c;
                this.b.mark((int) (j - this.c));
            } else {
                this.b.reset();
                this.b.mark((int) (j - this.d));
                a(this.d, this.c);
            }
            this.e = j;
        } catch (IOException e2) {
            throw new IllegalStateException("Unable to mark: ".concat(String.valueOf(e2)));
        }
    }

    public final void reset() throws IOException {
        a(this.f);
    }

    public final void a(long j) throws IOException {
        if (this.c > this.e || j < this.d) {
            throw new IOException("Cannot reset");
        }
        this.b.reset();
        a(this.d, j);
        this.c = j;
    }

    private void a(long j, long j2) throws IOException {
        while (j < j2) {
            long skip = this.b.skip(j2 - j);
            if (skip == 0) {
                if (read() != -1) {
                    skip = 1;
                } else {
                    return;
                }
            }
            j += skip;
        }
    }

    public final int read() throws IOException {
        if (!this.a && this.c + 1 > this.e) {
            b(this.e + ((long) this.g));
        }
        int read = this.b.read();
        if (read != -1) {
            this.c++;
        }
        return read;
    }

    public final int read(byte[] bArr) throws IOException {
        if (!this.a && this.c + ((long) bArr.length) > this.e) {
            b(this.c + ((long) bArr.length) + ((long) this.g));
        }
        int read = this.b.read(bArr);
        if (read != -1) {
            this.c += (long) read;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.a) {
            long j = (long) i2;
            if (this.c + j > this.e) {
                b(this.c + j + ((long) this.g));
            }
        }
        int read = this.b.read(bArr, i, i2);
        if (read != -1) {
            this.c += (long) read;
        }
        return read;
    }

    public final long skip(long j) throws IOException {
        if (!this.a && this.c + j > this.e) {
            b(this.c + j + ((long) this.g));
        }
        long skip = this.b.skip(j);
        this.c += skip;
        return skip;
    }

    public final int available() throws IOException {
        return this.b.available();
    }

    public final void close() throws IOException {
        this.b.close();
    }

    public final boolean markSupported() {
        return this.b.markSupported();
    }
}
