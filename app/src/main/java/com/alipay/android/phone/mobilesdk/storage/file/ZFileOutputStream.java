package com.alipay.android.phone.mobilesdk.storage.file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

public class ZFileOutputStream extends FileOutputStream {
    public ZFileOutputStream(File file) {
        super(file);
    }

    public ZFileOutputStream(File file, boolean append) {
        super(file, append);
    }

    public ZFileOutputStream(FileDescriptor fd) {
        super(fd);
    }

    public ZFileOutputStream(String path) {
        super(path);
    }

    public ZFileOutputStream(String path, boolean append) {
        super(path, append);
    }

    public void write(byte[] buffer) {
        super.write(buffer);
    }

    public void write(byte[] buffer, int byteOffset, int byteCount) {
        super.write(buffer, byteOffset, byteCount);
    }

    public void write(int oneByte) {
        super.write(oneByte);
    }

    public void close() {
        super.close();
    }
}
