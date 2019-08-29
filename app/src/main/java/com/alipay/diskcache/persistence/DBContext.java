package com.alipay.diskcache.persistence;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Build.VERSION;
import android.os.Environment;
import com.alipay.diskcache.utils.FileUtils;
import com.alipay.diskcache.utils.LogHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.IOException;

public class DBContext extends ContextWrapper {
    private static final String TAG = "DBContext";
    private String mDir = null;
    private boolean mIsAlipay = true;
    private String mPkgName;

    public DBContext(Context base, String dir) {
        super(base);
        this.mPkgName = base.getPackageName();
        this.mIsAlipay = "com.eg.android.AlipayGphone".equals(this.mPkgName);
        this.mDir = dir;
    }

    private File getDbPathForAlipay(String name) {
        boolean isFileCreateSuccess;
        String dbDir = this.mDir;
        String dbPath = dbDir + File.separator + name;
        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File dbFile = new File(dbPath);
        String new_dbDir = this.mDir + File.separator + this.mPkgName;
        String new_dbPath = new_dbDir + File.separator + name;
        File new_dirFile = new File(new_dbDir);
        if (!new_dirFile.exists()) {
            new_dirFile.mkdirs();
        }
        File new_dbFile = new File(new_dbPath);
        if (new_dbFile.exists()) {
            LogHelper.i(TAG, "new_dbFile.exists()");
            return new_dbFile;
        }
        if (dbFile.exists()) {
            long t = System.currentTimeMillis();
            isFileCreateSuccess = FileUtils.cpFile(dbFile, new_dbFile);
            LogHelper.i(TAG, "copy db took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
            long t2 = System.currentTimeMillis();
            File dbFile_journal = new File(dbPath + FilePathHelper.SUFFIX_JOURNAL);
            if (dbFile_journal.exists()) {
                FileUtils.cpFile(dbFile_journal, new File(new_dbFile.getAbsolutePath() + FilePathHelper.SUFFIX_JOURNAL));
            }
            LogHelper.i(TAG, "copy db-journal took " + (System.currentTimeMillis() - t2) + RPCDataParser.TIME_MS);
        } else {
            LogHelper.i(TAG, "new_dbFile and dbFile both not exist");
            try {
                isFileCreateSuccess = new_dbFile.createNewFile();
            } catch (IOException e) {
                LogHelper.e(TAG, "new_dbFile.createNewFile() error, ", e);
                isFileCreateSuccess = false;
            }
        }
        if (!isFileCreateSuccess) {
            return null;
        }
        return new_dbFile;
    }

    public File getDatabasePath(String name) {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            LogHelper.e(TAG, "no sdcard!");
            return null;
        } else if (this.mIsAlipay) {
            return getDbPathForAlipay(name);
        } else {
            String dbDir = this.mDir;
            String dbPath = dbDir + File.separator + name;
            File dirFile = new File(dbDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            boolean isFileCreateSuccess = false;
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();
                } catch (IOException e) {
                    LogHelper.e(TAG, "getDatabasePath error, ", e);
                }
            } else {
                isFileCreateSuccess = true;
            }
            if (!isFileCreateSuccess) {
                return null;
            }
            return dbFile;
        }
    }

    private SQLiteDatabase openOrCreateDB(String dbname) {
        File dbFile = getDatabasePath(dbname);
        if (dbFile == null) {
            return null;
        }
        String dbPath = dbFile.getAbsolutePath();
        if (VERSION.SDK_INT < 11) {
            return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null, null);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDB(name);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
        return openOrCreateDB(name);
    }
}
