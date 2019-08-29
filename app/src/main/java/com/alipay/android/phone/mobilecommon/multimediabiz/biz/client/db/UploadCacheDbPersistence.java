package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.config.MultimediaDb;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbPersistence;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

public class UploadCacheDbPersistence extends DbPersistence<UploadCacheModel> {
    private static UploadCacheDbPersistence a;

    private UploadCacheDbPersistence(Context context, DbHelper dbHelper, Class<UploadCacheModel> clazz) {
        super(context, dbHelper, clazz);
    }

    public static synchronized UploadCacheDbPersistence get() {
        UploadCacheDbPersistence uploadCacheDbPersistence;
        synchronized (UploadCacheDbPersistence.class) {
            if (a == null) {
                Context context = AppUtils.getApplicationContext();
                try {
                    a = new UploadCacheDbPersistence(context, MultimediaDb.getInstance().getDbHelper(context), UploadCacheModel.class);
                } catch (Exception e) {
                    Logger.E((String) "UploadCacheDb", (Throwable) e, "create UploadCacheDbPersistence error, " + e, new Object[0]);
                }
            }
            uploadCacheDbPersistence = a;
        }
        return uploadCacheDbPersistence;
    }

    public UploadCacheModel queryByMd5(String md5) {
        UploadCacheModel model = null;
        try {
            QueryBuilder query = getDao().queryBuilder();
            query.where().eq("md5", new SelectArg((Object) md5));
            return (UploadCacheModel) getDao().queryForFirst(query.prepare());
        } catch (Exception e) {
            Logger.E((String) "UploadCacheDb", (Throwable) e, "queryByMd5 error, " + e, new Object[0]);
            return model;
        }
    }

    public synchronized void trimDbTableSize(long max, long trim) {
        try {
            if (getDao().countOf() > max) {
                String sql = String.format("DELETE FROM `%s` WHERE `%s` IN (SELECT `%s` FROM `%s` ORDER BY `%s` ASC LIMIT %d)", new Object[]{getTableName(), UploadCacheModel.FIELD_ID, UploadCacheModel.FIELD_ID, getTableName(), UploadCacheModel.FIELD_ID, Long.valueOf(trim)});
                Logger.D("UploadCacheDb", "trimDbTableSize sql: " + sql + "\nmax: " + max + ", trim: " + trim, new Object[0]);
                Logger.D("UploadCacheDb", "trimDbTableSize effected: " + getDao().executeRawNoArgs(sql), new Object[0]);
            }
        } catch (Exception e) {
            Logger.W("UploadCacheDb", "trimDbTableSize error: " + e, new Object[0]);
        }
        return;
    }

    public String getTableName() {
        return "tbl_upload_cache";
    }
}
