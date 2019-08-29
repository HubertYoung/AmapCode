package com.alibaba.sqlcrypto.sqlite;

import android.util.Printer;
import java.util.ArrayList;

public final class SQLiteDebug {
    public static final boolean DEBUG_LOG_SLOW_QUERIES = false;
    public static final boolean DEBUG_SQL_LOG = false;
    public static final boolean DEBUG_SQL_STATEMENTS = false;
    public static final boolean DEBUG_SQL_TIME = false;

    public class DbStats {
        public String cache;
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String dbName2, long pageCount, long pageSize2, int lookaside2, int hits, int misses, int cachesize) {
            this.dbName = dbName2;
            this.pageSize = pageSize2 / 1024;
            this.dbSize = (pageCount * pageSize2) / 1024;
            this.lookaside = lookaside2;
            this.cache = hits + "/" + misses + "/" + cachesize;
        }
    }

    public class PagerStats {
        public ArrayList<DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

    private static native void nativeGetPagerStats(PagerStats pagerStats);

    private SQLiteDebug() {
    }

    public static final boolean shouldLogSlowQuery(long elapsedTimeMillis) {
        return false;
    }

    public static PagerStats getDatabaseInfo() {
        PagerStats stats = new PagerStats();
        nativeGetPagerStats(stats);
        stats.dbStats = SQLiteDatabase.getDbStats();
        return stats;
    }

    public static void dump(Printer printer, String[] args) {
        boolean verbose = false;
        String[] arr$ = args;
        int len$ = args.length;
        for (int i$ = 0; i$ < len$; i$++) {
            if (arr$[i$].equals("-v")) {
                verbose = true;
            }
        }
        SQLiteDatabase.dumpAll(printer, verbose);
    }
}
