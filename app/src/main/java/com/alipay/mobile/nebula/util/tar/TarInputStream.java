package com.alipay.mobile.nebula.util.tar;

import com.alipay.mobile.nebula.util.H5IOUtils;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TarInputStream extends FilterInputStream {
    private static final int SKIP_BUFFER_SIZE = 2048;
    private long bytesRead = 0;
    private TarEntry currentEntry;
    private long currentFileSize = 0;
    private boolean defaultSkip = false;

    public TarInputStream(InputStream in) {
        super(in);
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

    public int read(byte[] b, int off, int len) {
        if (this.currentEntry != null) {
            if (this.currentFileSize == this.currentEntry.getSize()) {
                return -1;
            }
            if (this.currentEntry.getSize() - this.currentFileSize < ((long) len)) {
                len = (int) (this.currentEntry.getSize() - this.currentFileSize);
            }
        }
        int br = super.read(b, off, len);
        if (br == -1) {
            return br;
        }
        if (this.currentEntry != null) {
            this.currentFileSize += (long) br;
        }
        this.bytesRead += (long) br;
        return br;
    }

    public TarEntry getNextEntry() {
        int i = 0;
        closeCurrentEntry();
        byte[] header = H5IOUtils.getBuf(512);
        byte[] theader = H5IOUtils.getBuf(512);
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
        int length = header.length;
        while (true) {
            if (i >= length) {
                break;
            } else if (header[i] != 0) {
                eof = false;
                break;
            } else {
                i++;
            }
        }
        if (!eof) {
            this.currentEntry = new TarEntry(header);
        }
        H5IOUtils.returnBuf(header);
        H5IOUtils.returnBuf(theader);
        return this.currentEntry;
    }

    public long getCurrentOffset() {
        return this.bytesRead;
    }

    /* access modifiers changed from: protected */
    public void closeCurrentEntry() {
        if (this.currentEntry != null) {
            if (this.currentEntry.getSize() > this.currentFileSize) {
                long bs = 0;
                while (bs < this.currentEntry.getSize() - this.currentFileSize) {
                    long res = skip((this.currentEntry.getSize() - this.currentFileSize) - bs);
                    if (res != 0 || this.currentEntry.getSize() - this.currentFileSize <= 0) {
                        bs += res;
                    } else {
                        throw new IOException("Possible tar file corruption");
                    }
                }
            }
            this.currentEntry = null;
            this.currentFileSize = 0;
            skipPad();
        }
    }

    /* access modifiers changed from: protected */
    public void skipPad() {
        if (this.bytesRead > 0) {
            int extra = (int) (this.bytesRead % 512);
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
        if (this.defaultSkip) {
            long bs = super.skip(n);
            this.bytesRead += bs;
            return bs;
        } else if (n <= 0) {
            return 0;
        } else {
            long left = n;
            byte[] buffer = H5IOUtils.getBuf(2048);
            while (left > 0) {
                if (left < 2048) {
                    j = left;
                } else {
                    j = 2048;
                }
                int res = read(buffer, 0, (int) j);
                if (res < 0) {
                    break;
                }
                left -= (long) res;
            }
            H5IOUtils.returnBuf(buffer);
            return n - left;
        }
    }

    public boolean isDefaultSkip() {
        return this.defaultSkip;
    }

    public void setDefaultSkip(boolean defaultSkip2) {
        this.defaultSkip = defaultSkip2;
    }
}
