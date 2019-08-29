package org.eclipse.mat.parser.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class SimpleBufferedRandomAccessInputStream extends InputStream {
    private int buf_end;
    private int buf_pos;
    private byte[] buffer;
    private RandomAccessFile raf;
    private byte[] readBuffer;
    private long real_pos;

    public boolean markSupported() {
        return false;
    }

    public SimpleBufferedRandomAccessInputStream(RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 8192);
    }

    public SimpleBufferedRandomAccessInputStream(RandomAccessFile randomAccessFile, int i) throws IOException {
        this.readBuffer = new byte[32];
        this.raf = randomAccessFile;
        invalidate();
        this.buffer = new byte[i];
    }

    private void invalidate() throws IOException {
        this.buf_end = 0;
        this.buf_pos = 0;
        this.real_pos = this.raf.getFilePointer();
    }

    public final int read() throws IOException {
        if ((this.buf_pos >= this.buf_end && fillBuffer() < 0) || this.buf_end == 0) {
            return -1;
        }
        byte[] bArr = this.buffer;
        int i = this.buf_pos;
        this.buf_pos = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        if ((this.buf_pos >= this.buf_end && fillBuffer() < 0) || this.buf_end == 0) {
            return -1;
        }
        while (i3 < i2) {
            if (this.buf_pos >= this.buf_end && fillBuffer() < 0) {
                return i3;
            }
            int min = Math.min(i2 - i3, this.buf_end - this.buf_pos);
            System.arraycopy(this.buffer, this.buf_pos, bArr, i + i3, min);
            this.buf_pos += min;
            i3 += min;
        }
        return i3;
    }

    private int fillBuffer() throws IOException {
        int read = this.raf.read(this.buffer);
        if (read >= 0) {
            this.real_pos += (long) read;
            this.buf_end = read;
            this.buf_pos = 0;
        }
        return read;
    }

    public void close() throws IOException {
        this.raf.close();
        this.buffer = null;
    }

    public void seek(long j) throws IOException {
        int i = (int) (this.real_pos - j);
        if (i < 0 || i > this.buf_end) {
            this.raf.seek(j);
            invalidate();
            return;
        }
        this.buf_pos = this.buf_end - i;
    }

    public long getFilePointer() {
        return (this.real_pos - ((long) this.buf_end)) + ((long) this.buf_pos);
    }

    public final int readInt() throws IOException {
        if (this.buf_pos + 4 < this.buf_end) {
            int readInt = readInt(this.buffer, this.buf_pos);
            this.buf_pos += 4;
            return readInt;
        } else if (read(this.readBuffer, 0, 4) == 4) {
            return readInt(this.readBuffer, 0);
        } else {
            throw new IOException();
        }
    }

    public final long readLong() throws IOException {
        if (this.buf_pos + 8 < this.buf_end) {
            long readLong = readLong(this.buffer, this.buf_pos);
            this.buf_pos += 8;
            return readLong;
        } else if (read(this.readBuffer, 0, 8) == 8) {
            return readLong(this.readBuffer, 0);
        } else {
            throw new IOException();
        }
    }

    public int readIntArray(int[] iArr) throws IOException {
        int i;
        byte[] bArr;
        int length = iArr.length * 4;
        if (this.buf_pos + length < this.buf_end) {
            bArr = this.buffer;
            i = this.buf_pos;
            this.buf_pos += length;
        } else {
            bArr = length > this.readBuffer.length ? new byte[length] : this.readBuffer;
            if (read(bArr, 0, length) != length) {
                throw new IOException();
            }
            i = 0;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = readInt(bArr, (i2 * 4) + i);
        }
        return iArr.length;
    }

    private static final int readInt(byte[] bArr, int i) throws IOException {
        byte b = bArr[i] & 255;
        byte b2 = bArr[i + 1] & 255;
        byte b3 = bArr[i + 2] & 255;
        byte b4 = bArr[i + 3] & 255;
        if ((b | b2 | b3 | b4) >= 0) {
            return (b << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0);
        }
        throw new EOFException();
    }

    public int readLongArray(long[] jArr) throws IOException {
        int i;
        byte[] bArr;
        int length = jArr.length * 8;
        if (this.buf_pos + length < this.buf_end) {
            bArr = this.buffer;
            i = this.buf_pos;
            this.buf_pos += length;
        } else {
            bArr = length > this.readBuffer.length ? new byte[length] : this.readBuffer;
            if (read(bArr, 0, length) != length) {
                throw new IOException();
            }
            i = 0;
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            jArr[i2] = readLong(bArr, (i2 * 8) + i);
        }
        return jArr.length;
    }

    private static final long readLong(byte[] bArr, int i) {
        return (((long) bArr[i]) << 56) + (((long) (bArr[i + 1] & 255)) << 48) + (((long) (bArr[i + 2] & 255)) << 40) + (((long) (bArr[i + 3] & 255)) << 32) + (((long) (bArr[i + 4] & 255)) << 24) + ((long) ((bArr[i + 5] & 255) << 16)) + ((long) ((bArr[i + 6] & 255) << 8)) + ((long) ((bArr[i + 7] & 255) << 0));
    }
}
