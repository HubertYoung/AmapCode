package com.alipay.mobile.common.patch.dir.tar;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TarInputStream extends FilterInputStream {
    private TarEntry a;
    private long b = 0;
    private long c = 0;
    private boolean d = false;

    public TarInputStream(InputStream in) {
        super(in);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean markSupported() {
        return false;
    }

    public synchronized void mark(int readlimit) {
    }

    public synchronized void reset() {
        throw new IOException("mark/reset not supported");
    }

    public int read() {
        byte[] buf = new byte[1];
        int res = read(buf, 0, 1);
        if (res != -1) {
            return buf[0] & 255;
        }
        return res;
    }

    public int read(byte[] b2, int off, int len) {
        if (this.a != null) {
            if (this.b == this.a.getSize()) {
                return -1;
            }
            if (this.a.getSize() - this.b < ((long) len)) {
                len = (int) (this.a.getSize() - this.b);
            }
        }
        int br = super.read(b2, off, len);
        if (br == -1) {
            return br;
        }
        if (this.a != null) {
            this.b += (long) br;
        }
        this.c += (long) br;
        return br;
    }

    public TarEntry getNextEntry() {
        int i = 0;
        closeCurrentEntry();
        byte[] header = new byte[512];
        byte[] theader = new byte[512];
        int tr = 0;
        while (tr < 512) {
            int res = read(theader, 0, 512 - tr);
            if (res < 0) {
                break;
            }
            System.arraycopy(theader, 0, header, tr, res);
            tr += res;
        }
        boolean eof = true;
        while (true) {
            if (i >= 512) {
                break;
            } else if (header[i] != 0) {
                eof = false;
                break;
            } else {
                i++;
            }
        }
        if (!eof) {
            this.a = new TarEntry(header);
        }
        return this.a;
    }

    public long getCurrentOffset() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void closeCurrentEntry() {
        if (this.a != null) {
            if (this.a.getSize() > this.b) {
                long bs = 0;
                while (bs < this.a.getSize() - this.b) {
                    long res = skip((this.a.getSize() - this.b) - bs);
                    if (res != 0 || this.a.getSize() - this.b <= 0) {
                        bs += res;
                    } else {
                        throw new IOException("Possible tar file corruption");
                    }
                }
            }
            this.a = null;
            this.b = 0;
            skipPad();
        }
    }

    /* access modifiers changed from: protected */
    public void skipPad() {
        if (this.c > 0) {
            int extra = (int) (this.c % 512);
            if (extra > 0) {
                long bs = 0;
                while (bs < ((long) (512 - extra))) {
                    bs += skip(((long) (512 - extra)) - bs);
                }
            }
        }
    }

    public long skip(long n) {
        long j;
        if (this.d) {
            long bs = super.skip(n);
            this.c += bs;
            return bs;
        } else if (n <= 0) {
            return 0;
        } else {
            long left = n;
            byte[] sBuff = new byte[2048];
            while (left > 0) {
                if (left < 2048) {
                    j = left;
                } else {
                    j = 2048;
                }
                int res = read(sBuff, 0, (int) j);
                if (res < 0) {
                    break;
                }
                left -= (long) res;
            }
            return n - left;
        }
    }

    public boolean isDefaultSkip() {
        return this.d;
    }

    public void setDefaultSkip(boolean defaultSkip) {
        this.d = defaultSkip;
    }
}
