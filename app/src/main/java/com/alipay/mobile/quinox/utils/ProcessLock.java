package com.alipay.mobile.quinox.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ProcessLock {
    public static final String TAG = "ProcessLock";
    private FileLock cacheLock;
    private FileChannel lockChannel;
    private File lockFile;
    private RandomAccessFile lockRaf;

    public ProcessLock(String str) {
        this.lockFile = new File(str);
    }

    public ProcessLock(File file) {
        this.lockFile = file;
    }

    public void lock() {
        try {
            this.lockRaf = new RandomAccessFile(this.lockFile, "rw");
            if (this.lockRaf == null || this.lockFile == null) {
                StringBuilder sb = new StringBuilder("lock error lockRaf = ");
                sb.append(this.lockRaf);
                sb.append(" lockFile = ");
                sb.append(this.lockFile);
                TraceLogger.e((String) TAG, sb.toString());
                return;
            }
            this.lockChannel = this.lockRaf.getChannel();
            try {
                this.cacheLock = this.lockChannel.lock();
            } catch (Throwable th) {
                TraceLogger.e(TAG, "lock error ", th);
            }
        } catch (FileNotFoundException e) {
            TraceLogger.e(TAG, "ProcessLock error", e);
        }
    }

    public void unlock() {
        if (this.cacheLock != null) {
            try {
                this.cacheLock.release();
            } catch (Throwable unused) {
                StringBuilder sb = new StringBuilder("Failed to release lock on ");
                sb.append(this.lockFile != null ? this.lockFile.getPath() : "");
                TraceLogger.e((String) TAG, sb.toString());
            }
        }
        if (this.lockChannel != null) {
            closeQuietly(this.lockChannel);
        }
        closeQuietly(this.lockRaf);
    }

    private void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                TraceLogger.w(TAG, "Failed to close resource", th);
            }
        }
    }
}
