package com.alipay.mobile.common.nbnet.biz.io;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import java.io.File;
import java.io.RandomAccessFile;

public class UploadFileInputStream extends RandomAccessFile implements UploadInputStream {
    protected long a;
    protected long b;
    private File c;
    private boolean d = false;

    public UploadFileInputStream(File file) {
        super(file, UploadQueueMgr.MSGTYPE_REALTIME);
        this.c = file;
        this.b = file.length();
    }

    public UploadFileInputStream(File file, long offset, long length) {
        super(file, UploadQueueMgr.MSGTYPE_REALTIME);
        this.c = file;
        if (offset >= file.length()) {
            throw new NBNetException("offset >= file length: " + offset, -11);
        }
        seek(offset);
        this.b = offset + length > file.length() ? file.length() : offset + length;
    }

    public int read() {
        return super.read();
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        int readLen;
        IOUtils.a(buffer.length, byteOffset, byteCount);
        if (this.a >= this.b) {
            return -1;
        }
        if (byteCount == 0) {
            return 0;
        }
        int readableLength = (int) (this.b - this.a);
        if (readableLength < byteCount) {
            readLen = readableLength;
        } else {
            readLen = byteCount;
        }
        super.read(buffer, byteOffset, readLen);
        this.a += (long) readLen;
        return readLen;
    }

    public void seek(long offset) {
        super.seek(offset);
        this.a = offset;
        this.b = this.c.length();
    }

    public final boolean a() {
        return this.d;
    }

    public void close() {
        super.close();
        this.d = true;
    }
}
