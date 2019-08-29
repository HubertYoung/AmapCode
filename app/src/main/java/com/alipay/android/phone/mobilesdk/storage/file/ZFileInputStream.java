package com.alipay.android.phone.mobilesdk.storage.file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

public class ZFileInputStream extends FileInputStream {
    public ZFileInputStream(File file) {
        super(file);
    }

    public ZFileInputStream(FileDescriptor fd) {
        super(fd);
    }

    public ZFileInputStream(String path) {
        super(path);
    }

    public int read() {
        return super.read();
    }

    public int read(byte[] buffer) {
        return super.read(buffer);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        return super.read(buffer, byteOffset, byteCount);
    }
}
