package org.eclipse.mat.parser.io;

import java.io.Closeable;
import java.io.EOFException;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream implements Closeable, Flushable {
    public static final int DEFAULT_BUFFER_SIZE = 16384;
    private int avail;
    private byte[] buffer = new byte[16384];
    private int current;
    private int fill;
    private InputStream is;
    private int pos;

    public BitInputStream(InputStream inputStream) {
        this.is = inputStream;
    }

    public void flush() {
        this.avail = 0;
        this.pos = 0;
        this.fill = 0;
    }

    public void close() throws IOException {
        this.is.close();
        this.is = null;
        this.buffer = null;
    }

    private int read() throws IOException {
        if (this.avail == 0) {
            this.avail = this.is.read(this.buffer);
            if (this.avail == -1) {
                this.avail = 0;
                throw new EOFException();
            }
            this.pos = 0;
        }
        this.avail--;
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i] & 255;
    }

    private int readFromCurrent(int i) throws IOException {
        if (i == 0) {
            return 0;
        }
        if (this.fill == 0) {
            this.current = read();
            this.fill = 8;
        }
        int i2 = this.current;
        int i3 = this.fill - i;
        this.fill = i3;
        return ((1 << i) - 1) & (i2 >>> i3);
    }

    public int readBit() throws IOException {
        return readFromCurrent(1);
    }

    public int readInt(int i) throws IOException {
        if (i <= this.fill) {
            return readFromCurrent(i);
        }
        int i2 = i - this.fill;
        int readFromCurrent = readFromCurrent(this.fill);
        int i3 = i2 >> 3;
        while (true) {
            int i4 = i3 - 1;
            if (i3 != 0) {
                readFromCurrent = (readFromCurrent << 8) | read();
                i3 = i4;
            } else {
                int i5 = i2 & 7;
                return readFromCurrent(i5) | (readFromCurrent << i5);
            }
        }
    }
}
