package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.config;

import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.CommonSharedPreference;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.BaseDb;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.OnDbCreateUpgradeHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.j256.ormlite.support.ConnectionSource;

public class MultimediaTaskDb extends BaseDb {
    private static MultimediaTaskDb a;
    private final String b = "MultiMediaTask.db";
    private final int c = 2;

    public static BaseDb getInstance() {
        if (a == null) {
            synchronized (MultimediaTaskDb.class) {
                try {
                    if (a == null) {
                        a = new MultimediaTaskDb();
                    }
                }
            }
        }
        return a;
    }

    private MultimediaTaskDb() {
    }

    public String getDbName() {
        return "MultiMediaTask.db";
    }

    public int getDbVersion() {
        return 2;
    }

    public OnDbCreateUpgradeHandler getOnDbCreateUpgradeHandler() {
        return new OnDbCreateUpgradeHandler() {
            public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
                Logger.D("MultimediaDb", "DbHelper onCreate dbName: " + MultimediaTaskDb.this.getDbName(), new Object[0]);
                CommonSharedPreference.get().setMultimediaTaskDbCreateTime(System.currentTimeMillis());
                MultimediaTaskDb.this.createTableIfNotExists(connectionSource, APMultimediaTaskModel.class);
            }

            public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
                Logger.D("MultimediaDb", "DbHelper onUpgrade dbName : " + MultimediaTaskDb.this.getDbName() + ", oldVer: " + oldVer + ", newVer:" + newVer, new Object[0]);
                if (oldVer <= 1 && newVer >= 2) {
                    CommonSharedPreference.get().setMultimediaTaskDbCreateTime(System.currentTimeMillis());
                    MultimediaTaskDb.this.a(sqLiteDatabase);
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void a(SQLiteDatabase sqLiteDatabase) {
        addCacheTableInfo(sqLiteDatabase, "multi_media_task");
    }
}
