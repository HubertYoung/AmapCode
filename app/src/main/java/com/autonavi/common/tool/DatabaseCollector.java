package com.autonavi.common.tool;

import android.app.Application;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.FileFilter;

public class DatabaseCollector {
    public static String getDatabaseInfo(Throwable th, Application application) {
        File file;
        final StringBuilder sb = new StringBuilder();
        try {
            sb.append("\nAppendInfo:\n");
            File dataDirectory = Environment.getDataDirectory();
            StatFs statFs = new StatFs(dataDirectory.getAbsolutePath());
            sb.append("\tData TotalBytes:");
            sb.append(((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()));
            sb.append(" AvailableBytes:");
            sb.append(((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize()));
            sb.append("\n");
            if (VERSION.SDK_INT >= 9) {
                sb.append("\tData TotalSpace:");
                sb.append(dataDirectory.getTotalSpace());
                sb.append(" UsableSpace:");
                sb.append(dataDirectory.getUsableSpace());
                sb.append("\n");
            }
            try {
                sb.append("\tNDK_getUsableSpace:");
                sb.append(dumpcrash.getUsableSpace(dataDirectory.getPath()));
                sb.append("\n");
            } catch (Throwable unused) {
            }
            if (bmx.a(th, SQLiteCantOpenDatabaseException.class)) {
                int limitFdCount = getLimitFdCount();
                int currentFdCount = getCurrentFdCount();
                sb.append("\tlimitCount:");
                sb.append(limitFdCount);
                sb.append(" currentCount:");
                sb.append(currentFdCount);
                sb.append("\n");
                if (currentFdCount <= limitFdCount && ((double) (((float) currentFdCount) / ((float) limitFdCount))) > 0.95d) {
                    bms.a().d();
                    sb.append("FDinfo:\n");
                    for (String append : bmx.a(false)) {
                        sb.append("\t");
                        sb.append(append);
                        sb.append("\n");
                    }
                }
            }
            sb.append("Mounts:\n");
            bmx.a(new File("/proc/self/mounts"), (a) new a() {
                public final boolean a(String str) {
                    StringBuilder sb = sb;
                    sb.append("\t");
                    sb.append(str);
                    sb.append("\n");
                    return false;
                }
            });
            sb.append("\nDumpDatabases:\n");
            File databasePath = application.getDatabasePath("aMap.db");
            if (databasePath == null) {
                file = new File(application.getFilesDir().getParentFile(), "databases");
            } else {
                file = databasePath.getParentFile();
            }
            for (File file2 = file; file2 != null; file2 = file2.getParentFile()) {
                sb.append("\t dir=");
                sb.append(file2.getPath());
                sb.append(" canRead:");
                sb.append(file2.canRead());
                sb.append(" canWrite:");
                sb.append(file2.canWrite());
                sb.append(" canExecute:");
                sb.append(file2.canExecute());
                sb.append("\n");
            }
            for (File databaseInfo : file.listFiles(new FileFilter() {
                public final boolean accept(File file) {
                    return !file.getName().endsWith(FilePathHelper.SUFFIX_JOURNAL);
                }
            })) {
                sb.append(getDatabaseInfo(databaseInfo));
            }
        } catch (Throwable unused2) {
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00de, code lost:
        if (r15 != null) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e0, code lost:
        r15.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x010b, code lost:
        if (r15 != null) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0112, code lost:
        return r0.toString();
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d5 A[Catch:{ all -> 0x00cd, all -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ec A[SYNTHETIC, Splitter:B:47:0x00ec] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getDatabaseInfo(java.io.File r15) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = r15.getAbsolutePath()
            r0.<init>(r1)
            java.lang.String r1 = "\n"
            r0.append(r1)
            java.lang.String r1 = "\tsize:"
            r0.append(r1)
            long r1 = r15.length()
            r0.append(r1)
            java.lang.String r1 = "\n"
            r0.append(r1)
            java.lang.String r1 = "\tcanRead:"
            r0.append(r1)
            boolean r1 = r15.canRead()
            r0.append(r1)
            java.lang.String r1 = "\tcanWrite:"
            r0.append(r1)
            boolean r1 = r15.canWrite()
            r0.append(r1)
            java.lang.String r1 = "\tcanExecute:"
            r0.append(r1)
            boolean r1 = r15.canExecute()
            r0.append(r1)
            java.lang.String r1 = "\n"
            r0.append(r1)
            r1 = 0
            java.lang.String r15 = r15.getAbsolutePath()     // Catch:{ Throwable -> 0x00f3, all -> 0x00f0 }
            r2 = 17
            android.database.sqlite.SQLiteDatabase r15 = android.database.sqlite.SQLiteDatabase.openDatabase(r15, r1, r2)     // Catch:{ Throwable -> 0x00f3, all -> 0x00f0 }
            if (r15 != 0) goto L_0x005f
            java.lang.String r1 = "\tcannot open database.\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x005c }
            goto L_0x00de
        L_0x005c:
            r1 = move-exception
            goto L_0x00f7
        L_0x005f:
            java.lang.String r2 = "\tversion:"
            r0.append(r2)     // Catch:{ Throwable -> 0x005c }
            int r2 = r15.getVersion()     // Catch:{ Throwable -> 0x005c }
            r0.append(r2)     // Catch:{ Throwable -> 0x005c }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x005c }
            java.lang.String r4 = "sqlite_master"
            java.lang.String r2 = "name"
            java.lang.String[] r5 = new java.lang.String[]{r2}     // Catch:{ all -> 0x00e6 }
            java.lang.String r6 = "type='table'"
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r3 = r15
            android.database.Cursor r2 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x00e6 }
            boolean r3 = r2.moveToFirst()     // Catch:{ all -> 0x00e4 }
            if (r3 == 0) goto L_0x00d9
            java.lang.String r3 = "count(*)"
            java.lang.String[] r11 = new java.lang.String[]{r3}     // Catch:{ all -> 0x00e4 }
        L_0x0091:
            r12 = 0
            java.lang.String r13 = r2.getString(r12)     // Catch:{ all -> 0x00e4 }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r3 = r15
            r4 = r13
            r5 = r11
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x00cf }
            boolean r4 = r3.moveToFirst()     // Catch:{ all -> 0x00cd }
            if (r4 == 0) goto L_0x00c1
            java.lang.String r4 = "\ttable:"
            r0.append(r4)     // Catch:{ all -> 0x00cd }
            r0.append(r13)     // Catch:{ all -> 0x00cd }
            java.lang.String r4 = " count:"
            r0.append(r4)     // Catch:{ all -> 0x00cd }
            long r4 = r3.getLong(r12)     // Catch:{ all -> 0x00cd }
            r0.append(r4)     // Catch:{ all -> 0x00cd }
            java.lang.String r4 = "\n"
            r0.append(r4)     // Catch:{ all -> 0x00cd }
        L_0x00c1:
            if (r3 == 0) goto L_0x00c6
            r3.close()     // Catch:{ all -> 0x00e4 }
        L_0x00c6:
            boolean r3 = r2.moveToNext()     // Catch:{ all -> 0x00e4 }
            if (r3 != 0) goto L_0x0091
            goto L_0x00d9
        L_0x00cd:
            r1 = move-exception
            goto L_0x00d3
        L_0x00cf:
            r3 = move-exception
            r14 = r3
            r3 = r1
            r1 = r14
        L_0x00d3:
            if (r3 == 0) goto L_0x00d8
            r3.close()     // Catch:{ all -> 0x00e4 }
        L_0x00d8:
            throw r1     // Catch:{ all -> 0x00e4 }
        L_0x00d9:
            if (r2 == 0) goto L_0x00de
            r2.close()     // Catch:{ Throwable -> 0x005c }
        L_0x00de:
            if (r15 == 0) goto L_0x010e
        L_0x00e0:
            r15.close()
            goto L_0x010e
        L_0x00e4:
            r1 = move-exception
            goto L_0x00ea
        L_0x00e6:
            r2 = move-exception
            r14 = r2
            r2 = r1
            r1 = r14
        L_0x00ea:
            if (r2 == 0) goto L_0x00ef
            r2.close()     // Catch:{ Throwable -> 0x005c }
        L_0x00ef:
            throw r1     // Catch:{ Throwable -> 0x005c }
        L_0x00f0:
            r0 = move-exception
            r15 = r1
            goto L_0x0114
        L_0x00f3:
            r15 = move-exception
            r14 = r1
            r1 = r15
            r15 = r14
        L_0x00f7:
            java.lang.String r2 = "\tcatch Throwable msg:"
            r0.append(r2)     // Catch:{ all -> 0x0113 }
            java.lang.String r2 = r1.getMessage()     // Catch:{ all -> 0x0113 }
            r0.append(r2)     // Catch:{ all -> 0x0113 }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ all -> 0x0113 }
            r1.printStackTrace()     // Catch:{ all -> 0x0113 }
            if (r15 == 0) goto L_0x010e
            goto L_0x00e0
        L_0x010e:
            java.lang.String r15 = r0.toString()
            return r15
        L_0x0113:
            r0 = move-exception
        L_0x0114:
            if (r15 == 0) goto L_0x0119
            r15.close()
        L_0x0119:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.DatabaseCollector.getDatabaseInfo(java.io.File):java.lang.String");
    }

    private static int getLimitFdCount() {
        final int[] iArr = new int[1];
        bmx.a(new File("/proc/self/limits"), (a) new a() {
            public final boolean a(String str) {
                if (!str.contains("Max open files")) {
                    return false;
                }
                String[] split = str.split("\\s+");
                if (split != null && split.length > 4) {
                    iArr[0] = Integer.valueOf(split[3]).intValue();
                }
                return true;
            }
        });
        return iArr[0];
    }

    private static int getCurrentFdCount() {
        try {
            File file = new File("/proc/self/fd");
            if (!file.exists() || !file.isDirectory()) {
                return 0;
            }
            return file.listFiles().length;
        } catch (Throwable unused) {
            return 0;
        }
    }
}
