package de.greenrobot.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbUtils {
    private static final long busyWaitTryCount = 6000;
    private static final long busyWaitUnit = 50;

    public static void vacuum(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("VACUUM");
    }

    public static int executeSqlScript(Context context, SQLiteDatabase sQLiteDatabase, String str) throws IOException {
        return executeSqlScript(context, sQLiteDatabase, str, true);
    }

    public static int executeSqlScript(Context context, SQLiteDatabase sQLiteDatabase, String str, boolean z) throws IOException {
        int i;
        String[] split = new String(readAsset(context, str), "UTF-8").split(";(\\s)*[\n\r]");
        if (z) {
            i = executeSqlStatementsInTx(sQLiteDatabase, split);
        } else {
            i = executeSqlStatements(sQLiteDatabase, split);
        }
        StringBuilder sb = new StringBuilder("Executed ");
        sb.append(i);
        sb.append(" statements from SQL script '");
        sb.append(str);
        sb.append("'");
        DaoLog.i(sb.toString());
        return i;
    }

    public static int executeSqlStatementsInTx(SQLiteDatabase sQLiteDatabase, String[] strArr) {
        beginTransactionBusyWait(sQLiteDatabase);
        try {
            int executeSqlStatements = executeSqlStatements(sQLiteDatabase, strArr);
            sQLiteDatabase.setTransactionSuccessful();
            return executeSqlStatements;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static int executeSqlStatements(SQLiteDatabase sQLiteDatabase, String[] strArr) {
        int i = 0;
        for (String trim : strArr) {
            String trim2 = trim.trim();
            if (trim2.length() > 0) {
                sQLiteDatabase.execSQL(trim2);
                i++;
            }
        }
        return i;
    }

    public static int copyAllBytes(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return i;
            }
            outputStream.write(bArr, 0, read);
            i += read;
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyAllBytes(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAsset(Context context, String str) throws IOException {
        InputStream open = context.getResources().getAssets().open(str);
        try {
            return readAllBytes(open);
        } finally {
            open.close();
        }
    }

    public static void logTableDump(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor query = sQLiteDatabase.query(str, null, null, null, null, null, null);
        try {
            DaoLog.d(DatabaseUtils.dumpCursorToString(query));
        } finally {
            query.close();
        }
    }

    public static void beginTransactionBusyWait(SQLiteDatabase sQLiteDatabase) {
        long j = busyWaitTryCount;
        while (true) {
            try {
                sQLiteDatabase.beginTransaction();
                return;
            } catch (SQLiteException e) {
                if (checkException(e, SQLiteDatabaseLockedException.class)) {
                    long j2 = j - 1;
                    if (j > 0) {
                        try {
                            Thread.sleep(busyWaitUnit);
                        } catch (InterruptedException unused) {
                        }
                        j = j2;
                    } else {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static SQLiteDatabase getWritableDatabaseBusyWait(SQLiteOpenHelper sQLiteOpenHelper) {
        long j = busyWaitTryCount;
        while (true) {
            try {
                return sQLiteOpenHelper.getWritableDatabase();
            } catch (SQLiteException e) {
                if (checkException(e, SQLiteDatabaseLockedException.class)) {
                    long j2 = j - 1;
                    if (j > 0) {
                        try {
                            Thread.sleep(busyWaitUnit);
                        } catch (InterruptedException unused) {
                        }
                        j = j2;
                    } else {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static SQLiteDatabase getReadableDatabaseBusyWait(SQLiteOpenHelper sQLiteOpenHelper) {
        long j = busyWaitTryCount;
        while (true) {
            try {
                return sQLiteOpenHelper.getReadableDatabase();
            } catch (SQLiteException e) {
                if (checkException(e, SQLiteDatabaseLockedException.class)) {
                    long j2 = j - 1;
                    if (j > 0) {
                        try {
                            Thread.sleep(busyWaitUnit);
                        } catch (InterruptedException unused) {
                        }
                        j = j2;
                    } else {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static boolean checkException(Throwable th, Class<?> cls) {
        while (!cls.isInstance(th)) {
            th = th.getCause();
            if (th == null) {
                return false;
            }
        }
        return true;
    }
}
