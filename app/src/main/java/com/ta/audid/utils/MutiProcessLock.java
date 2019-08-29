package com.ta.audid.utils;

import com.ta.audid.upload.UtdidKeyFile;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class MutiProcessLock {
    private static FileChannel fChannel;
    private static FileChannel fUploadChannel;
    private static FileLock mLock;
    private static File mLockFile;
    private static FileLock mUploadLock;
    private static File mUploadLockFile;

    public static synchronized void lockUtdidFile() {
        synchronized (MutiProcessLock.class) {
            UtdidLogger.d();
            if (mLockFile == null) {
                mLockFile = new File(UtdidKeyFile.getFileLockPath());
            }
            if (!mLockFile.exists()) {
                try {
                    mLockFile.createNewFile();
                } catch (Exception unused) {
                    return;
                }
            }
            if (fChannel == null) {
                try {
                    fChannel = new RandomAccessFile(mLockFile, "rw").getChannel();
                } catch (Exception unused2) {
                    return;
                }
            }
            try {
                mLock = fChannel.lock();
            } catch (Throwable unused3) {
            }
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0010 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void releaseUtdidFile() {
        /*
            java.lang.Class<com.ta.audid.utils.MutiProcessLock> r0 = com.ta.audid.utils.MutiProcessLock.class
            monitor-enter(r0)
            com.ta.audid.utils.UtdidLogger.d()     // Catch:{ all -> 0x002e }
            java.nio.channels.FileLock r1 = mLock     // Catch:{ all -> 0x002e }
            r2 = 0
            if (r1 == 0) goto L_0x0017
            java.nio.channels.FileLock r1 = mLock     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
            r1.release()     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
        L_0x0010:
            mLock = r2     // Catch:{ all -> 0x002e }
            goto L_0x0017
        L_0x0013:
            r1 = move-exception
            mLock = r2     // Catch:{ all -> 0x002e }
            throw r1     // Catch:{ all -> 0x002e }
        L_0x0017:
            java.nio.channels.FileChannel r1 = fChannel     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            java.nio.channels.FileChannel r1 = fChannel     // Catch:{ Exception -> 0x0028, all -> 0x0024 }
            r1.close()     // Catch:{ Exception -> 0x0028, all -> 0x0024 }
            fChannel = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r0)
            return
        L_0x0024:
            r1 = move-exception
            fChannel = r2     // Catch:{ all -> 0x002e }
            throw r1     // Catch:{ all -> 0x002e }
        L_0x0028:
            fChannel = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r0)
            return
        L_0x002c:
            monitor-exit(r0)
            return
        L_0x002e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.MutiProcessLock.releaseUtdidFile():void");
    }

    public static synchronized boolean trylockUpload() {
        synchronized (MutiProcessLock.class) {
            UtdidLogger.d();
            if (mUploadLockFile == null) {
                mUploadLockFile = new File(UtdidKeyFile.getUploadFileLockPath());
            }
            if (!mUploadLockFile.exists()) {
                try {
                    mUploadLockFile.createNewFile();
                } catch (Exception unused) {
                    return false;
                }
            }
            if (fUploadChannel == null) {
                try {
                    fUploadChannel = new RandomAccessFile(mUploadLockFile, "rw").getChannel();
                } catch (Exception unused2) {
                    return false;
                }
            }
            try {
                FileLock tryLock = fUploadChannel.tryLock();
                if (tryLock != null) {
                    mUploadLock = tryLock;
                    return true;
                }
            } catch (Throwable unused3) {
            }
        }
        return false;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0010 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void releaseUpload() {
        /*
            java.lang.Class<com.ta.audid.utils.MutiProcessLock> r0 = com.ta.audid.utils.MutiProcessLock.class
            monitor-enter(r0)
            com.ta.audid.utils.UtdidLogger.d()     // Catch:{ all -> 0x002e }
            java.nio.channels.FileLock r1 = mUploadLock     // Catch:{ all -> 0x002e }
            r2 = 0
            if (r1 == 0) goto L_0x0017
            java.nio.channels.FileLock r1 = mUploadLock     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
            r1.release()     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
        L_0x0010:
            mUploadLock = r2     // Catch:{ all -> 0x002e }
            goto L_0x0017
        L_0x0013:
            r1 = move-exception
            mUploadLock = r2     // Catch:{ all -> 0x002e }
            throw r1     // Catch:{ all -> 0x002e }
        L_0x0017:
            java.nio.channels.FileChannel r1 = fUploadChannel     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            java.nio.channels.FileChannel r1 = fUploadChannel     // Catch:{ Exception -> 0x0028, all -> 0x0024 }
            r1.close()     // Catch:{ Exception -> 0x0028, all -> 0x0024 }
            fUploadChannel = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r0)
            return
        L_0x0024:
            r1 = move-exception
            fUploadChannel = r2     // Catch:{ all -> 0x002e }
            throw r1     // Catch:{ all -> 0x002e }
        L_0x0028:
            fUploadChannel = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r0)
            return
        L_0x002c:
            monitor-exit(r0)
            return
        L_0x002e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.MutiProcessLock.releaseUpload():void");
    }
}
