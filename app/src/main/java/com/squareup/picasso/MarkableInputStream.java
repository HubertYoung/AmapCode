package com.squareup.picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

final class MarkableInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private long defaultMark;
    private final InputStream in;
    private long limit;
    private long offset;
    private long reset;

    public MarkableInputStream(InputStream inputStream) {
        this(inputStream, 4096);
    }

    public MarkableInputStream(InputStream inputStream, int i) {
        this.defaultMark = -1;
        this.in = !inputStream.markSupported() ? new BufferedInputStream(inputStream, i) : inputStream;
    }

    public final void mark(int i) {
        this.defaultMark = savePosition(i);
    }

    public final long savePosition(int i) {
        long j = this.offset + ((long) i);
        if (this.limit < j) {
            setLimit(j);
        }
        return this.offset;
    }

    private void setLimit(long j) {
        try {
            if (this.reset >= this.offset || this.offset > this.limit) {
                this.reset = this.offset;
                this.in.mark((int) (j - this.offset));
            } else {
                this.in.reset();
                this.in.mark((int) (j - this.reset));
                skip(this.reset, this.offset);
            }
            this.limit = j;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mark: ".concat(String.valueOf(e)));
        }
    }

    public final void reset() throws IOException {
        reset(this.defaultMark);
    }

    public final void reset(long j) throws IOException {
        if (this.offset > this.limit || j < this.reset) {
            throw new IOException("Cannot reset");
        }
        this.in.reset();
        skip(this.reset, j);
        this.offset = j;
    }

    private void skip(long j, long j2) throws IOException {
        while (j < j2) {
            long skip = this.in.skip(j2 - j);
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
        int read = this.in.read();
        if (read != -1) {
            this.offset++;
        }
        return read;
    }

    public final int read(byte[] bArr) throws IOException {
        int read = this.in.read(bArr);
        if (read != -1) {
            this.offset += (long) read;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read != -1) {
            this.offset += (long) read;
        }
        return read;
    }

    public final long skip(long j) throws IOException {
        long skip = this.in.skip(j);
        this.offset += skip;
        return skip;
    }

    public final int available() throws IOException {
        return this.in.available();
    }

    public final void close() throws IOException {
        this.in.close();
    }

    public final boolean markSupported() {
        return this.in.markSupported();
    }
}
