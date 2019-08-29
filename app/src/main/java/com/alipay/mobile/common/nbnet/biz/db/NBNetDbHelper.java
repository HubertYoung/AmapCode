package com.alipay.mobile.common.nbnet.biz.db;

import android.content.Context;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class NBNetDbHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "NBNet.db";
    public static final int DATABASE_VERSION = 3;
    public static final Class[] DATA_OBJECT_CLASSES = {DownloadTaskModel.class, UploadRecordDo.class};
    private static final String a = NBNetDbHelper.class.getSimpleName();

    public NBNetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    public NBNetDbHelper() {
        super(null, DATABASE_NAME, null, 3);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        NBNetLogCat.a(a, "onCreate:" + sqLiteDatabase);
        onCreateTool(connectionSource);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        NBNetLogCat.a(a, "onUpgrade:" + sqLiteDatabase + "(" + oldVersion + "->" + newVersion + ")");
        if (newVersion != oldVersion) {
            dropTableTool(connectionSource);
            try {
                onCreate(sqLiteDatabase, connectionSource);
            } catch (Throwable e) {
                NBNetLogCat.b(a, e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigure(SQLiteDatabase sqLiteDatabase) {
        NBNetLogCat.a(a, "onConfigure:" + sqLiteDatabase);
    }

    public static final void dropTableTool(ConnectionSource connectionSource) {
        for (Class dataObjecteClass : DATA_OBJECT_CLASSES) {
            try {
                TableUtils.dropTable(connectionSource, dataObjecteClass, true);
            } catch (SQLException e) {
                NBNetLogCat.b(a, (Throwable) e);
            }
        }
    }

    public static final void onCreateTool(ConnectionSource connectionSource) {
        for (Class dataObjectClass : DATA_OBJECT_CLASSES) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, dataObjectClass);
            } catch (SQLException e) {
                NBNetLogCat.b(a, (Throwable) e);
            }
        }
    }
}
