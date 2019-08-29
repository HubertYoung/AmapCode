package com.standardar.common;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.SharedMemory;
import android.system.ErrnoException;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Datagram {
    private static final String SHAREMEMORY_FILE = "com.standardar.service.sharememory";
    private ByteBuffer mDataBuffer = null;
    private ParcelFileDescriptor mParceServiceShareFile;
    private MemoryFile mServiceShareMemory;
    private SharedMemory mSharedMemory;
    private ByteBuffer mSharedMemoryBuffer;

    public ByteBuffer createBufferNeed(int i) {
        if (this.mDataBuffer == null || this.mDataBuffer.capacity() <= i) {
            this.mDataBuffer = ByteBuffer.allocate(i);
            return this.mDataBuffer;
        }
        this.mDataBuffer.clear();
        this.mDataBuffer.rewind();
        return this.mDataBuffer;
    }

    public synchronized void createSharedMemoryV27(int i) {
        try {
            this.mSharedMemory = SharedMemory.create(SHAREMEMORY_FILE, i);
            this.mSharedMemoryBuffer = this.mSharedMemory.mapReadWrite();
        } catch (ErrnoException e) {
            e.printStackTrace();
        }
    }

    public synchronized SharedMemory getSharedMemory() {
        try {
        }
        return this.mSharedMemory;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void fillSharedMemoryBufferV27(byte[] r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.nio.ByteBuffer r0 = r1.mSharedMemoryBuffer     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0016
            android.os.SharedMemory r0 = r1.mSharedMemory     // Catch:{ all -> 0x0018 }
            if (r0 != 0) goto L_0x000a
            goto L_0x0016
        L_0x000a:
            java.nio.ByteBuffer r0 = r1.mSharedMemoryBuffer     // Catch:{ all -> 0x0018 }
            r0.rewind()     // Catch:{ all -> 0x0018 }
            java.nio.ByteBuffer r0 = r1.mSharedMemoryBuffer     // Catch:{ all -> 0x0018 }
            r0.put(r2)     // Catch:{ all -> 0x0018 }
            monitor-exit(r1)
            return
        L_0x0016:
            monitor-exit(r1)
            return
        L_0x0018:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.standardar.common.Datagram.fillSharedMemoryBufferV27(byte[]):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void releaseSharedMemory() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.nio.ByteBuffer r0 = r1.mSharedMemoryBuffer     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x001b
            android.os.SharedMemory r0 = r1.mSharedMemory     // Catch:{ all -> 0x001d }
            if (r0 != 0) goto L_0x000a
            goto L_0x001b
        L_0x000a:
            java.nio.ByteBuffer r0 = r1.mSharedMemoryBuffer     // Catch:{ all -> 0x001d }
            android.os.SharedMemory.unmap(r0)     // Catch:{ all -> 0x001d }
            android.os.SharedMemory r0 = r1.mSharedMemory     // Catch:{ all -> 0x001d }
            r0.close()     // Catch:{ all -> 0x001d }
            r0 = 0
            r1.mSharedMemory = r0     // Catch:{ all -> 0x001d }
            r1.mSharedMemoryBuffer = r0     // Catch:{ all -> 0x001d }
            monitor-exit(r1)
            return
        L_0x001b:
            monitor-exit(r1)
            return
        L_0x001d:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.standardar.common.Datagram.releaseSharedMemory():void");
    }

    public ByteBuffer packData(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer createBufferNeed = createBufferNeed(bArr.length);
        createBufferNeed.put(bArr);
        return createBufferNeed;
    }

    public ParcelFileDescriptor packDataShareMemory(byte[] bArr) throws IOException {
        if (bArr == null) {
            return null;
        }
        createMemFileNeed(bArr.length).writeBytes(bArr, 0, 0, bArr.length);
        return this.mParceServiceShareFile;
    }

    private MemoryFile createMemFileNeed(int i) {
        if (this.mServiceShareMemory != null && this.mServiceShareMemory.length() >= i) {
            return this.mServiceShareMemory;
        }
        releaseMemoryFile();
        try {
            this.mServiceShareMemory = new MemoryFile(SHAREMEMORY_FILE, i);
            this.mParceServiceShareFile = ParcelFileDescriptor.dup((FileDescriptor) MemoryFile.class.getDeclaredMethod("getFileDescriptor", new Class[0]).invoke(this.mServiceShareMemory, new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mServiceShareMemory;
    }

    public void release() {
        releaseMemoryFile();
    }

    private void releaseMemoryFile() {
        closeParceFile(this.mParceServiceShareFile);
        closeMemoryFile(this.mServiceShareMemory);
        this.mParceServiceShareFile = null;
        this.mServiceShareMemory = null;
    }

    private void closeParceFile(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeMemoryFile(MemoryFile memoryFile) {
        if (memoryFile != null) {
            memoryFile.close();
        }
    }
}
