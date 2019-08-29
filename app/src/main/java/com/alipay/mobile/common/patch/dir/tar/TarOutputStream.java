package com.alipay.mobile.common.patch.dir.tar;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class TarOutputStream extends OutputStream {
    private final OutputStream a;
    private long b;
    private long c;
    private TarEntry d;

    public TarOutputStream(OutputStream out) {
        this.a = out;
        this.b = 0;
        this.c = 0;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public TarOutputStream(File fout) {
        this.a = new BufferedOutputStream(new FileOutputStream(fout));
        this.b = 0;
        this.c = 0;
    }

    public TarOutputStream(File fout, boolean append) {
        RandomAccessFile raf = new RandomAccessFile(fout, "rw");
        long fileSize = fout.length();
        if (append && fileSize > 1024) {
            raf.seek(fileSize - 1024);
        }
        this.a = new BufferedOutputStream(new FileOutputStream(raf.getFD()));
    }

    public void close() {
        closeCurrentEntry();
        write(new byte[1024]);
        this.a.close();
    }

    public void write(int b2) {
        this.a.write(b2);
        this.b++;
        if (this.d != null) {
            this.c++;
        }
    }

    public void write(byte[] b2, int off, int len) {
        if (this.d == null || this.d.isDirectory() || this.d.getSize() >= this.c + ((long) len)) {
            this.a.write(b2, off, len);
            this.b += (long) len;
            if (this.d != null) {
                this.c += (long) len;
                return;
            }
            return;
        }
        throw new IOException("The current entry[" + this.d.getName() + "] size[" + this.d.getSize() + "] is smaller than the bytes[" + (this.c + ((long) len)) + "] being written.");
    }

    public void putNextEntry(TarEntry entry) {
        closeCurrentEntry();
        byte[] header = new byte[512];
        entry.writeEntryHeader(header);
        write(header);
        this.d = entry;
    }

    /* access modifiers changed from: protected */
    public void closeCurrentEntry() {
        if (this.d == null) {
            return;
        }
        if (this.d.getSize() > this.c) {
            throw new IOException("The current entry[" + this.d.getName() + "] of size[" + this.d.getSize() + "] has not been fully written.");
        }
        this.d = null;
        this.c = 0;
        pad();
    }

    /* access modifiers changed from: protected */
    public void pad() {
        if (this.b > 0) {
            int extra = (int) (this.b % 512);
            if (extra > 0) {
                write(new byte[(512 - extra)]);
            }
        }
    }
}
