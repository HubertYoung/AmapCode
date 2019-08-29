package defpackage;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* renamed from: boi reason: default package */
/* compiled from: StrictLineReader */
final class boi implements Closeable {
    final Charset a;
    private final InputStream b;
    private byte[] c;
    private int d;
    private int e;

    public boi(InputStream inputStream, Charset charset) {
        this(inputStream, charset, 0);
    }

    private boi(InputStream inputStream, Charset charset, byte b2) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (!charset.equals(boj.a)) {
            throw new IllegalArgumentException("Unsupported encoding");
        } else {
            this.b = inputStream;
            this.a = charset;
            this.c = new byte[8192];
        }
    }

    public final void close() throws IOException {
        synchronized (this.b) {
            if (this.c != null) {
                this.c = null;
                this.b.close();
            }
        }
    }

    public final String a() throws IOException {
        int i;
        int i2;
        synchronized (this.b) {
            try {
                if (this.c == null) {
                    throw new IOException("LineReader is closed");
                }
                if (this.d >= this.e) {
                    b();
                }
                for (int i3 = this.d; i3 != this.e; i3++) {
                    if (this.c[i3] == 10) {
                        if (i3 != this.d) {
                            i2 = i3 - 1;
                            if (this.c[i2] == 13) {
                                String str = new String(this.c, this.d, i2 - this.d, this.a.name());
                                this.d = i3 + 1;
                                return str;
                            }
                        }
                        i2 = i3;
                        String str2 = new String(this.c, this.d, i2 - this.d, this.a.name());
                        this.d = i3 + 1;
                        return str2;
                    }
                }
                AnonymousClass1 r1 = new ByteArrayOutputStream((this.e - this.d) + 80) {
                    public final String toString() {
                        try {
                            return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + -1] != 13) ? this.count : this.count - 1, boi.this.a.name());
                        } catch (UnsupportedEncodingException e) {
                            throw new AssertionError(e);
                        }
                    }
                };
                loop1:
                while (true) {
                    r1.write(this.c, this.d, this.e - this.d);
                    this.e = -1;
                    b();
                    i = this.d;
                    while (true) {
                        if (i != this.e) {
                            if (this.c[i] == 10) {
                                break loop1;
                            }
                            i++;
                        }
                    }
                }
                if (i != this.d) {
                    r1.write(this.c, this.d, i - this.d);
                }
                this.d = i + 1;
                String byteArrayOutputStream = r1.toString();
                return byteArrayOutputStream;
            }
        }
    }

    private void b() throws IOException {
        int read = this.b.read(this.c, 0, this.c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.d = 0;
        this.e = read;
    }
}
