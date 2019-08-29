package com.alipay.mobile.nebulaappcenter.dbhelp;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.app.H5NebulaDBService;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppConfigBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppInfoBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppInstallBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppPoolBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5UrlAppMapBean;
import com.alipay.mobile.nebulaappcenter.dbdao.H5AppDaoHelper;
import com.alipay.mobile.nebulaappcenter.service.H5MemoryCache;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public abstract class H5BaseDBHelper extends OrmLiteSqliteOpenHelper {
    private final String a = a();
    private Dao<H5AppConfigBean, Integer> b;
    private Dao<H5AppInstallBean, Integer> c;
    private Dao<H5NebulaAppBean, Integer> d;
    private Dao<H5UrlAppMapBean, Integer> e;
    private String f;

    public abstract String a();

    public abstract String b();

    H5BaseDBHelper(Context context, String databaseName) {
        super(context, databaseName, null, 14);
        H5Log.d(this.a, "construct db + " + databaseName + " with version: 14");
    }

    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        H5Log.d(this.a, "onCreate");
        try {
            TableUtils.createTable(connectionSource, H5NebulaAppBean.class);
            H5Log.d(this.a, "createTable H5NebulaAppBean");
        } catch (Exception e2) {
            H5Log.e(this.a, "Can't create database" + e2);
            a(e2.toString());
        }
        try {
            TableUtils.createTable(connectionSource, H5UrlAppMapBean.class);
            H5Log.d(this.a, "createTable H5UrlAppMapBean");
        } catch (Exception e3) {
            H5Log.e(this.a, "Can't create database" + e3);
            a(e3.toString());
        }
        try {
            TableUtils.createTable(connectionSource, H5AppConfigBean.class);
            H5Log.d(this.a, "createTable H5AppConfigBean");
        } catch (Exception e4) {
            H5Log.e(this.a, "Can't create database" + e4);
            a(e4.toString());
        }
        try {
            TableUtils.createTable(connectionSource, H5AppInstallBean.class);
            H5Log.d(this.a, "createTable H5AppInstallBean");
        } catch (Exception e5) {
            H5Log.e(this.a, "Can't create database" + e5);
            a(e5.toString());
        }
        try {
            H5NebulaDBService.getInstance().setDefaultConfig();
            H5Log.d(this.a, "createTable setDefaultConfig");
        } catch (Exception e6) {
            H5Log.e(this.a, "Can't create database" + e6);
            a(e6.toString());
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        H5Log.d(this.a, "数据库降级 onDowngrade oldVersion " + oldVersion + " newVersion:" + newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        H5Log.d(this.a, "数据库升级 onUpgrade, oldVersion:" + oldVersion + ",newVersion:" + newVersion);
        if (oldVersion != newVersion && !a.a(db, connectionSource, oldVersion, newVersion)) {
            a(db, connectionSource);
        }
    }

    private void a(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.dropTable(connectionSource, H5AppInfoBean.class, true);
            H5Log.d(this.a, "dropTable H5AppInfoBean");
        } catch (Exception e2) {
            H5Log.e(this.a, "Can't dropTable database" + e2);
            a(e2.toString());
        }
        try {
            TableUtils.dropTable(connectionSource, H5AppPoolBean.class, true);
            H5Log.d(this.a, "dropTable H5AppPoolBean");
        } catch (Exception e3) {
            H5Log.e(this.a, "Can't dropTable database" + e3);
            a(e3.toString());
        }
        try {
            TableUtils.dropTable(connectionSource, H5AppConfigBean.class, true);
            H5Log.d(this.a, "dropTable H5AppConfigBean");
        } catch (Exception e4) {
            H5Log.e(this.a, "Can't dropTable database" + e4);
            a(e4.toString());
        }
        try {
            TableUtils.dropTable(connectionSource, H5AppInstallBean.class, true);
            H5Log.d(this.a, "dropTable H5AppInstallBean");
        } catch (Exception e5) {
            H5Log.e(this.a, "Can't dropTable database" + e5);
            a(e5.toString());
        }
        try {
            TableUtils.dropTable(connectionSource, H5NebulaAppBean.class, true);
            H5Log.d(this.a, "dropTable H5NebulaAppBean");
        } catch (Exception e6) {
            H5Log.e(this.a, (Throwable) e6);
            a(e6.toString());
        }
        try {
            TableUtils.dropTable(connectionSource, H5UrlAppMapBean.class, true);
            H5Log.d(this.a, "dropTable H5UrlAppMapBean");
        } catch (Exception e7) {
            H5Log.e(this.a, "Can't create database" + e7);
            a(e7.toString());
        }
        try {
            onCreate(db, connectionSource);
        } catch (Exception e8) {
            H5Log.e(this.a, (Throwable) e8);
            a(e8.toString());
        }
    }

    public final Dao<H5NebulaAppBean, Integer> c() {
        if (this.d == null) {
            try {
                this.d = getDao(H5NebulaAppBean.class);
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
                a(throwable.toString());
            }
        }
        return this.d;
    }

    public final Dao<H5AppConfigBean, Integer> d() {
        if (this.b == null) {
            try {
                this.b = getDao(H5AppConfigBean.class);
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
                a(throwable.toString());
            }
        }
        return this.b;
    }

    public final Dao<H5AppInstallBean, Integer> e() {
        if (this.c == null) {
            try {
                this.c = getDao(H5AppInstallBean.class);
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
                a(throwable.toString());
            }
        }
        return this.c;
    }

    public final Dao<H5UrlAppMapBean, Integer> f() {
        if (this.e == null) {
            try {
                this.e = getDao(H5UrlAppMapBean.class);
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
                a(throwable.toString());
            }
        }
        return this.e;
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        H5Log.d(this.a, b() + " onOpen !");
    }

    public void close() {
        super.close();
        H5Log.d(this.a, b() + " onClose !");
        this.d = null;
        this.c = null;
        this.b = null;
    }

    public final synchronized void g() {
        try {
            boolean needClearTable = !H5AppDaoHelper.a();
            String currentGWFUrl = null;
            if (H5Utils.isDebug()) {
                currentGWFUrl = H5NetworkUtil.getGWFURL(H5Utils.getContext());
                String lastGWFUrl = H5DevConfig.getStringConfig(this.f, null);
                H5Log.d(this.a, "needClearTable currentGWFUrl: " + currentGWFUrl + ", lastGWFUrl: " + lastGWFUrl);
                needClearTable = !TextUtils.isEmpty(lastGWFUrl) && !TextUtils.isEmpty(currentGWFUrl) && !lastGWFUrl.equals(currentGWFUrl);
            }
            if (needClearTable) {
                TableUtils.clearTable((ConnectionSource) this.connectionSource, H5NebulaAppBean.class);
                TableUtils.clearTable((ConnectionSource) this.connectionSource, H5AppInstallBean.class);
                TableUtils.clearTable((ConnectionSource) this.connectionSource, H5UrlAppMapBean.class);
                H5Log.d(this.a, "clearAllTable");
                if (H5Utils.isDebug() && !TextUtils.isEmpty(currentGWFUrl)) {
                    H5DevConfig.setStringConfig(this.f, currentGWFUrl);
                }
            }
            H5Log.d(this.a, "clearMemory");
            H5MemoryCache.a().b();
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null) {
                h5AppProvider.clearResourceAppCache();
            }
        } catch (Exception e2) {
            H5Log.e(this.a, "clearAllTable exception", e2);
        }
        return;
    }

    private static void a(String exception) {
        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_nebula_db_exception").param4().add(LogCategory.CATEGORY_EXCEPTION, exception));
    }
}
