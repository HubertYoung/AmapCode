package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.config;

import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db.UploadCacheModel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.CommonSharedPreference;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.BaseDb;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.OnDbCreateUpgradeHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoCacheModel;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Date;

public class MultimediaDb extends BaseDb {
    private static MultimediaDb a;

    public static MultimediaDb getInstance() {
        if (a == null) {
            synchronized (MultimediaDb.class) {
                try {
                    if (a == null) {
                        a = new MultimediaDb();
                    }
                }
            }
        }
        return a;
    }

    private MultimediaDb() {
    }

    public String getDbName() {
        return "multimedia.db";
    }

    public int getDbVersion() {
        return 9;
    }

    public OnDbCreateUpgradeHandler getOnDbCreateUpgradeHandler() {
        return new OnDbCreateUpgradeHandler() {
            public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
                Logger.D("MultimediaDb", "DbHelper onCreate dbName: " + MultimediaDb.this.getDbName(), new Object[0]);
                CommonSharedPreference.get().setMultimediaDbCreateTime(System.currentTimeMillis());
                Logger.D("MultimediaDb", "DbHelper onCreate recordTime: " + new Date(CommonSharedPreference.get().getMultimediaDbCreateTime()), new Object[0]);
                MultimediaDb.this.createTableIfNotExists(connectionSource, VideoCacheModel.class);
                MultimediaDb.this.createTableIfNotExists(connectionSource, UploadCacheModel.class);
                MultimediaDb.this.a(sqLiteDatabase);
            }

            public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
                Logger.D("MultimediaDb", "DbHelper onUpgrade dbName : " + MultimediaDb.this.getDbName() + ", oldVer: " + oldVer + ", newVer:" + newVer, new Object[0]);
                if (newVer < oldVer) {
                    onCreate(sqLiteDatabase, connectionSource);
                    return;
                }
                if (newVer >= 5) {
                    MultimediaDb.this.createTableIfNotExists(connectionSource, VideoCacheModel.class);
                }
                if (oldVer <= 5 && newVer >= 6) {
                    MultimediaDb.this.a(sqLiteDatabase);
                }
                if (oldVer <= 6 && newVer >= 7) {
                    MultimediaDb.this.b(sqLiteDatabase);
                }
                MultimediaDb.this.a(sqLiteDatabase, connectionSource, oldVer, newVer);
            }
        };
    }

    /* access modifiers changed from: private */
    public void a(SQLiteDatabase db) {
        execSQL(db, "CREATE INDEX tbl_video_cache_cloud_id_idx ON tbl_video_cache (cloud_id);");
        execSQL(db, "CREATE INDEX tbl_video_cache_local_id_idx ON tbl_video_cache (local_id);");
        execSQL(db, "CREATE INDEX tbl_video_cache_path_idx ON tbl_video_cache (path);");
        Logger.D("MultimediaDb", "createVideoCacheIndex finish", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void b(SQLiteDatabase db) {
        execSQL(db, "DELETE FROM tbl_video_cache");
        execSQL(db, "UPDATE sqlite_sequence SET seq = 0 WHERE name ='tbl_video_cache'");
        Logger.D("MultimediaDb", "clearVideoCacheTable finish", new Object[0]);
    }

    private static void a(ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, UploadCacheModel.class);
        } catch (SQLException e) {
            Logger.E((String) "MultimediaDb", (Throwable) e, (String) "createUploadCacheTable error", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void a(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        if (oldVer <= 7 && newVer >= 8) {
            CommonSharedPreference.get().setMultimediaDbCreateTime(System.currentTimeMillis());
            Logger.D("MultimediaDb", "DbHelper doUpgradeV8 recordTime: " + new Date(CommonSharedPreference.get().getMultimediaDbCreateTime()), new Object[0]);
            c(sqLiteDatabase);
            a(connectionSource);
        }
    }

    private void c(SQLiteDatabase db) {
        addCacheTableInfo(db, "tbl_video_cache");
        addColumn(db, "tbl_video_cache", VideoCacheModel.FIELD_STORE_TYPE, "int");
        db.execSQL("update `tbl_video_cache` SET store_type = 0 WHERE `type` = 1 AND `path` LIKE '%.tdat'");
        db.execSQL("update `tbl_video_cache` SET store_type = 1 WHERE `type` = 1 AND `path` LIKE '%.vdat'");
        db.execSQL("update `tbl_video_cache` SET store_type = 2 WHERE `type` = 0 AND `path` LIKE '%.tdat'");
        db.execSQL("update `tbl_video_cache` SET store_type = 3 WHERE `type` = 0 AND `path` LIKE '%.vdat'");
    }
}
