package org.eclipse.mat.parser.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import org.eclipse.mat.collect.HashMapLongObject;

public class BufferedRandomAccessInputStream extends InputStream {
    int bufsize;
    Page current;
    long fileLength;
    HashMapLongObject<SoftReference<Page>> pages;
    RandomAccessFile raf;
    long real_pos;
    long reported_pos;

    class Page {
        int buf_end;
        byte[] buffer;
        long real_pos_start;

        public Page() {
            this.buffer = new byte[BufferedRandomAccessInputStream.this.bufsize];
        }
    }

    public boolean markSupported() {
        return false;
    }

    public BufferedRandomAccessInputStream(RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 1024);
    }

    public BufferedRandomAccessInputStream(RandomAccessFile randomAccessFile, int i) throws IOException {
        this.pages = new HashMapLongObject<>();
        this.bufsize = i;
        this.raf = randomAccessFile;
        this.fileLength = randomAccessFile.length();
    }

    public final int read() throws IOException {
        if (this.reported_pos == this.fileLength) {
            return -1;
        }
        if (this.current == null || this.reported_pos - this.current.real_pos_start >= ((long) this.current.buf_end)) {
            this.current = getPage(this.reported_pos);
        }
        byte[] bArr = this.current.buffer;
        long j = this.reported_pos;
        this.reported_pos = 1 + j;
        return bArr[(int) (j - this.current.real_pos_start)] & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            throw new NullPointerException();
        }
        if (i >= 0 && i <= bArr.length && i2 >= 0) {
            int i3 = i + i2;
            if (i3 <= bArr.length && i3 >= 0) {
                int i4 = 0;
                if (i2 == 0) {
                    return 0;
                }
                if (this.reported_pos == this.fileLength) {
                    return -1;
                }
                while (i4 < i2 && this.reported_pos != this.fileLength) {
                    if (this.current == null || this.reported_pos - this.current.real_pos_start >= ((long) this.current.buf_end)) {
                        this.current = getPage(this.reported_pos);
                    }
                    int i5 = (int) (this.reported_pos - this.current.real_pos_start);
                    int min = Math.min(i2 - i4, this.current.buf_end - i5);
                    System.arraycopy(this.current.buffer, i5, bArr, i + i4, min);
                    this.reported_pos += (long) min;
                    i4 += min;
                }
                return i4;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private Page getPage(long j) throws IOException {
        Page page;
        long j2 = j / ((long) this.bufsize);
        SoftReference softReference = (SoftReference) this.pages.get(j2);
        if (softReference == null) {
            page = null;
        } else {
            page = (Page) softReference.get();
        }
        if (page != null) {
            return page;
        }
        long j3 = ((long) this.bufsize) * j2;
        if (j3 != this.real_pos) {
            this.raf.seek(j3);
            this.real_pos = j3;
        }
        Page page2 = new Page();
        int read = this.raf.read(page2.buffer);
        if (read >= 0) {
            page2.real_pos_start = this.real_pos;
            page2.buf_end = read;
            this.real_pos += (long) read;
        }
        this.pages.put(j2, new SoftReference(page2));
        return page2;
    }

    public void close() throws IOException {
        this.raf.close();
    }

    public void seek(long j) throws IOException {
        this.reported_pos = j;
        this.current = null;
    }

    public long getFilePointer() {
        return this.reported_pos;
    }
}
