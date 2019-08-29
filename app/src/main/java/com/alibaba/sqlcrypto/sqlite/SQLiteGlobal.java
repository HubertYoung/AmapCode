package com.alibaba.sqlcrypto.sqlite;

import android.os.Build.VERSION;
import android.os.StatFs;

public final class SQLiteGlobal {
    private static final String TAG = "SQLiteGlobal";
    private static long sDefaultPageSize;
    private static final Object sLock = new Object();

    private static native int nativeReleaseMemory();

    private SQLiteGlobal() {
    }

    public static int releaseMemory() {
        return nativeReleaseMemory();
    }

    public static long getDefaultPageSize() {
        long j;
        synchronized (sLock) {
            try {
                if (sDefaultPageSize == 0) {
                    if (VERSION.SDK_INT >= 18) {
                        sDefaultPageSize = new StatFs("/data").getBlockSizeLong();
                    } else {
                        sDefaultPageSize = (long) new StatFs("/data").getBlockSize();
                    }
                }
                j = sDefaultPageSize;
            }
        }
        return j;
    }

    public static String getDefaultJournalMode() {
        return "PERSIST";
    }

    public static int getJournalSizeLimit() {
        return 1048576;
    }

    public static String getDefaultSyncMode() {
        return "FULL";
    }

    public static String getWALSyncMode() {
        return "FULL";
    }

    public static int getWALAutoCheckpoint() {
        return 1000;
    }

    public static int getWALConnectionPoolSize() {
        return 4;
    }
}
