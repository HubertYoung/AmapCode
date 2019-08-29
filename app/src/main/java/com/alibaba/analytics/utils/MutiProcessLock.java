package com.alibaba.analytics.utils;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class MutiProcessLock {
    private static final String TAG = "MutiProcessLock";
    static FileChannel fChannel;
    static FileLock mLock;
    static File mLockFile;

    public static synchronized boolean lock(Context context) {
        FileLock fileLock;
        synchronized (MutiProcessLock.class) {
            if (mLockFile == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getFilesDir());
                sb.append(File.separator);
                sb.append("Analytics.Lock");
                mLockFile = new File(sb.toString());
            }
            boolean exists = mLockFile.exists();
            if (!exists) {
                try {
                    exists = mLockFile.createNewFile();
                } catch (IOException unused) {
                }
            }
            if (!exists) {
                return true;
            }
            if (fChannel == null) {
                try {
                    fChannel = new RandomAccessFile(mLockFile, "rw").getChannel();
                } catch (Exception unused2) {
                    return false;
                }
            }
            try {
                fileLock = fChannel.tryLock();
                if (fileLock != null) {
                    mLock = fileLock;
                    return true;
                }
            } catch (Throwable unused3) {
                fileLock = null;
            }
            new StringBuilder("mLock:").append(fileLock);
            return false;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x000d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void release() {
        /*
            java.lang.Class<com.alibaba.analytics.utils.MutiProcessLock> r0 = com.alibaba.analytics.utils.MutiProcessLock.class
            monitor-enter(r0)
            java.nio.channels.FileLock r1 = mLock     // Catch:{ all -> 0x002b }
            r2 = 0
            if (r1 == 0) goto L_0x0014
            java.nio.channels.FileLock r1 = mLock     // Catch:{ IOException -> 0x000d, all -> 0x0010 }
            r1.release()     // Catch:{ IOException -> 0x000d, all -> 0x0010 }
        L_0x000d:
            mLock = r2     // Catch:{ all -> 0x002b }
            goto L_0x0014
        L_0x0010:
            r1 = move-exception
            mLock = r2     // Catch:{ all -> 0x002b }
            throw r1     // Catch:{ all -> 0x002b }
        L_0x0014:
            java.nio.channels.FileChannel r1 = fChannel     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0029
            java.nio.channels.FileChannel r1 = fChannel     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            r1.close()     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            fChannel = r2     // Catch:{ all -> 0x002b }
            monitor-exit(r0)
            return
        L_0x0021:
            r1 = move-exception
            fChannel = r2     // Catch:{ all -> 0x002b }
            throw r1     // Catch:{ all -> 0x002b }
        L_0x0025:
            fChannel = r2     // Catch:{ all -> 0x002b }
            monitor-exit(r0)
            return
        L_0x0029:
            monitor-exit(r0)
            return
        L_0x002b:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.MutiProcessLock.release():void");
    }
}
