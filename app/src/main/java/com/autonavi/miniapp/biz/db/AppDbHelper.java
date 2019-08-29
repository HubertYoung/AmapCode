package com.autonavi.miniapp.biz.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.mobile.common.logging.LogCatLog;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.biz.db.model.RecentListEntity;
import com.autonavi.miniapp.biz.db.model.RecentSmallProModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppDbHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "my_amap_mini_apps.db";
    private static final int DATABASE_VERSION = 920;
    private static final String TAG = "AppDbHelper";
    private static final int VERSION_920 = 920;
    private Dao<RecentListEntity, Integer> recentListDao;

    static class DbHelperHolder {
        public static AppDbHelper instance = new AppDbHelper();

        private DbHelperHolder() {
        }
    }

    public static AppDbHelper getDbHelper() {
        return DbHelperHolder.instance;
    }

    private AppDbHelper() {
        super(LauncherApplicationAgent.getInstance().getApplicationContext(), DATABASE_NAME, null, 920);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecentListEntity.class);
            LogCatLog.d(TAG, "openPlatfrom start index, on onCreate !");
        } catch (SQLException e) {
            LogCatLog.e(AppDbHelper.class.getName(), "Can't create database".concat(String.valueOf(e)));
            throw new RuntimeException(e);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        StringBuilder sb = new StringBuilder("onUpgrade, oldVersion:");
        sb.append(i);
        sb.append(",newVersion:");
        sb.append(i2);
        LogCatLog.d(TAG, sb.toString());
        if (i < 2) {
            upgradeToVersion2();
        }
        if (i < 920) {
            upgradeToVersion920();
        }
        LogCatLog.d(H5SearchType.SEARCH, "openPlatfrom start index, on onUpgrade !");
    }

    private void upgradeToVersion920() {
        try {
            Dao dao = getDao(RecentListEntity.class);
            List<RecentListEntity> queryForAll = dao.queryForAll();
            if (queryForAll != null && !queryForAll.isEmpty()) {
                for (RecentListEntity recentListEntity : queryForAll) {
                    String adiu = NetworkParam.getAdiu();
                    if (adiu != null) {
                        recentListEntity.setUserId(adiu);
                        dao.update(recentListEntity);
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("upgrade database exception: ");
            sb.append(e.getMessage());
            H5Log.e((String) TAG, sb.toString());
        }
    }

    private void upgradeToVersion2() {
        try {
            Dao dao = getDao(RecentListEntity.class);
            List<RecentListEntity> queryForAll = dao.queryForAll();
            if (queryForAll != null && !queryForAll.isEmpty()) {
                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (h5AppProvider != null) {
                    for (RecentListEntity recentListEntity : queryForAll) {
                        ArrayList arrayList = new ArrayList();
                        for (String appInfo : JSON.parseArray(recentListEntity.getRecentList(), String.class)) {
                            AppInfo appInfo2 = h5AppProvider.getAppInfo(appInfo);
                            if (appInfo2 != null) {
                                RecentSmallProModel recentSmallProModel = new RecentSmallProModel();
                                recentSmallProModel.setAppId(appInfo2.app_id);
                                recentSmallProModel.setName(appInfo2.name);
                                recentSmallProModel.setIconUrl(appInfo2.icon_url);
                                recentSmallProModel.setSlogan(appInfo2.slogan);
                                arrayList.add(recentSmallProModel);
                            }
                        }
                        recentListEntity.setRecentList(JSON.toJSONString(arrayList));
                        dao.update(recentListEntity);
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("upgrade database exception: ");
            sb.append(e.getMessage());
            H5Log.e((String) TAG, sb.toString());
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("db onDowngrade oldVersion ");
        sb.append(i);
        sb.append(" newVersion:");
        sb.append(i2);
        LogCatLog.e((String) TAG, sb.toString());
        try {
            sQLiteDatabase.execSQL("TRUNCATE TABLE RecentListEntity;");
        } catch (Exception unused) {
            try {
                LogCatLog.e((String) TAG, "onDowngrade drop db table HomeAppEntity ErrorOldVersion =".concat(String.valueOf(i)));
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("onDowngrade reCreate, ");
                sb2.append(e.toString());
                LogCatLog.e((String) TAG, sb2.toString());
            }
        }
        LogCatLog.e((String) TAG, (String) "onDowngrade reCreate, ok");
    }

    public Dao<RecentListEntity, Integer> getRecentListEntityDao() throws SQLException {
        if (this.recentListDao == null) {
            this.recentListDao = getDao(RecentListEntity.class);
        }
        return this.recentListDao;
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        LogCatLog.d(TAG, "my_amap_mini_apps.db onOpen !");
    }

    public void close() {
        super.close();
        LogCatLog.d(TAG, "my_amap_mini_apps.db onClose !");
    }
}
