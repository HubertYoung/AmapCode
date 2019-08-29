package com.alipay.mobile.nebula.process;

import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ProcessLock {
    public static final String TAG = "H5ProcessLock";
    private FileLock cacheLock;
    private FileChannel lockChannel;
    private File lockFile;
    private RandomAccessFile lockRaf;

    public ProcessLock(String lockFilePath) {
        this.lockFile = new File(lockFilePath);
    }

    public ProcessLock(File lockFile2) {
        this.lockFile = lockFile2;
    }

    public void lock() {
        try {
            this.lockRaf = new RandomAccessFile(this.lockFile, "rw");
            if (this.lockRaf == null || this.lockFile == null) {
                H5Log.e((String) "H5ProcessLock", "lock error lockRaf = " + this.lockRaf + " lockFile = " + this.lockFile);
                return;
            }
            this.lockChannel = this.lockRaf.getChannel();
            H5Log.d("H5ProcessLock", "Blocking on lock " + this.lockFile.getPath());
            try {
                this.cacheLock = this.lockChannel.lock();
                H5Log.d("H5ProcessLock", this.lockFile.getPath() + " locked");
            } catch (IOException e) {
                H5Log.e("H5ProcessLock", "lock error ", e);
            }
        } catch (FileNotFoundException e2) {
            H5Log.e("H5ProcessLock", "ProcessLock error", e2);
        }
    }

    public void unlock() {
        if (this.cacheLock != null) {
            try {
                this.cacheLock.release();
            } catch (IOException e) {
                H5Log.e((String) "H5ProcessLock", "Failed to release lock on " + (this.lockFile != null ? this.lockFile.getPath() : ""));
            }
        }
        H5IOUtils.closeQuietly(this.lockChannel);
        H5IOUtils.closeQuietly(this.lockRaf);
        if (this.lockFile != null) {
            H5Log.d("H5ProcessLock", this.lockFile.getPath() + " unlocked");
        }
    }
}
