package com.alipay.android.phone.mobilesdk.storage.database.tinyapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import java.io.File;

public class TinyAppContext extends ContextWrapper {
    public TinyAppContext(Context base) {
        super(base);
    }

    public File getDatabasePath(String name) {
        String dbDir = getFilesDir() + File.separator + "tinyappdb";
        String dbPath = dbDir + File.separator + name;
        File dbDirFile = new File(dbDir);
        if (!dbDirFile.exists()) {
            dbDirFile.mkdirs();
        }
        boolean isFileCreateSuccess = false;
        File dbPathFile = new File(dbPath);
        if (!dbPathFile.exists()) {
            try {
                isFileCreateSuccess = dbPathFile.createNewFile();
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error(TinyAppStoragePlugin.TAG, "create tiny db " + name + " fail", e);
            }
        } else {
            isFileCreateSuccess = true;
        }
        if (isFileCreateSuccess) {
            return dbPathFile;
        }
        return null;
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}
