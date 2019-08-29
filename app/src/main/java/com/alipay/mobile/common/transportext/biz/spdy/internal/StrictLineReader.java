package com.alipay.mobile.common.transportext.biz.spdy.internal;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StrictLineReader implements Closeable {
    private static final byte CR = 13;
    private static final byte LF = 10;
    private byte[] buf;
    /* access modifiers changed from: private */
    public final Charset charset;
    private int end;
    private final InputStream in;
    private int pos;

    public StrictLineReader(InputStream in2, Charset charset2) {
        this(in2, 8192, charset2);
    }

    public StrictLineReader(InputStream in2, int capacity, Charset charset2) {
        if (in2 == null || charset2 == null) {
            throw new NullPointerException();
        } else if (capacity < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (!charset2.equals(Util.US_ASCII)) {
            throw new IllegalArgumentException("Unsupported encoding");
        } else {
            this.in = in2;
            this.charset = charset2;
            this.buf = new byte[capacity];
        }
    }

    public void close() {
        synchronized (this.in) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
        }
    }

    public String readLine() {
        int i;
        String res;
        synchronized (this.in) {
            try {
                if (this.buf == null) {
                    throw new IOException("LineReader is closed");
                }
                if (this.pos >= this.end) {
                    fillBuf();
                }
                int i2 = this.pos;
                while (true) {
                    if (i2 == this.end) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream((this.end - this.pos) + 80) {
                            public String toString() {
                                try {
                                    return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + -1] != 13) ? this.count : this.count - 1, StrictLineReader.this.charset.name());
                                } catch (UnsupportedEncodingException e) {
                                    throw new AssertionError(e);
                                }
                            }
                        };
                        loop1:
                        while (true) {
                            out.write(this.buf, this.pos, this.end - this.pos);
                            this.end = -1;
                            fillBuf();
                            i = this.pos;
                            while (true) {
                                if (i != this.end) {
                                    if (this.buf[i] == 10) {
                                        break loop1;
                                    }
                                    i++;
                                }
                            }
                        }
                        if (i != this.pos) {
                            out.write(this.buf, this.pos, i - this.pos);
                        }
                        this.pos = i + 1;
                        res = out.toString();
                    } else if (this.buf[i2] == 10) {
                        res = new String(this.buf, this.pos, ((i2 == this.pos || this.buf[i2 + -1] != 13) ? i2 : i2 - 1) - this.pos, this.charset.name());
                        this.pos = i2 + 1;
                    } else {
                        i2++;
                    }
                }
            }
        }
        return res;
    }

    public int readInt() {
        String intString = readLine();
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new IOException("expected an int but was \"" + intString + "\"");
        }
    }

    private void fillBuf() {
        int result = this.in.read(this.buf, 0, this.buf.length);
        if (result == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = result;
    }
}
