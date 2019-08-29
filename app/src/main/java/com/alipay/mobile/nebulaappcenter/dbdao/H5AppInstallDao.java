package com.alipay.mobile.nebulaappcenter.dbdao;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoExecutor;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppInstallBean;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper;
import com.alipay.mobile.nebulaappcenter.util.H5AppGlobal;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H5AppInstallDao extends H5DaoTemplate {
    private static H5AppInstallDao a = new H5AppInstallDao();

    private H5AppInstallDao() {
    }

    public static synchronized H5AppInstallDao c() {
        H5AppInstallDao h5AppInstallDao;
        synchronized (H5AppInstallDao.class) {
            try {
                if (a == null) {
                    a = new H5AppInstallDao();
                }
                h5AppInstallDao = a;
            }
        }
        return h5AppInstallDao;
    }

    public final void a(String appId, String version, String path) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version)) {
            final long time = System.currentTimeMillis();
            final String str = appId;
            final String str2 = version;
            final String str3 = path;
            a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
                public final Object a(H5BaseDBHelper dbHelper) {
                    H5AppInstallBean h5AppInstallBean;
                    Dao h5AppInstallDao = dbHelper.e();
                    QueryBuilder queryBuilder = h5AppInstallDao.queryBuilder();
                    H5AppDaoHelper.a(queryBuilder).eq(H5AppInstallBean.COL_APP_ID, str);
                    if (queryBuilder.queryForFirst() != null) {
                        h5AppInstallBean = (H5AppInstallBean) queryBuilder.queryForFirst();
                    } else {
                        h5AppInstallBean = new H5AppInstallBean();
                    }
                    if (h5AppInstallBean != null) {
                        h5AppInstallBean.setInstall_userId(H5DaoTemplate.a());
                        h5AppInstallBean.setInstall_appId(str);
                        h5AppInstallBean.setInstall_version(str2);
                        h5AppInstallBean.setInstallPath(str3);
                        h5AppInstallDao.createOrUpdate(h5AppInstallBean);
                        H5Log.d("H5AppInstallDao", "updateAppInstalled: userId: " + H5DaoTemplate.a() + " appId:" + str + " version:" + str2 + Token.SEPARATOR + str3 + "  spend createOrUpdateAppInstalled " + (System.currentTimeMillis() - time));
                    }
                    return null;
                }
            });
        }
    }

    public final Map<String, String> d() {
        long time = System.currentTimeMillis();
        List<H5AppInstallBean> h5AppInstallBeanList = (List) a((H5DaoExecutor<T>) new H5DaoExecutor<List<H5AppInstallBean>>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static List<H5AppInstallBean> b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.e().queryBuilder();
                H5AppDaoHelper.b(queryBuilder);
                return queryBuilder.query();
            }
        });
        if (h5AppInstallBeanList == null) {
            return null;
        }
        long cost = System.currentTimeMillis() - time;
        Map installAppList = new HashMap();
        for (H5AppInstallBean h5AppInstallBean : h5AppInstallBeanList) {
            H5Log.d("H5AppInstallDao", "getInstalledApp: userId : " + a() + " appId:" + h5AppInstallBean.getInstall_appId() + " version:" + h5AppInstallBean.getInstall_version() + " allCost:" + cost);
            if (H5AppGlobal.a(h5AppInstallBean.getInstall_appId(), h5AppInstallBean.getInstall_version(), h5AppInstallBean.getInstallPath())) {
                installAppList.put(h5AppInstallBean.getInstall_appId(), h5AppInstallBean.getInstall_version());
            } else {
                H5Log.d("H5AppInstallDao", h5AppInstallBean.getInstall_appId() + " is not install delete form db");
                b(h5AppInstallBean.getInstall_appId());
            }
        }
        return installAppList;
    }

    public final String a(final String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        String installVersion = (String) a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public String a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.e().queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq(H5AppInstallBean.COL_APP_ID, appId);
                if (queryBuilder.queryForFirst() == null) {
                    return null;
                }
                H5AppInstallBean h5AppInstallBean = (H5AppInstallBean) queryBuilder.queryForFirst();
                if (h5AppInstallBean == null) {
                    return null;
                }
                String path = h5AppInstallBean.getInstallPath();
                String install_version = h5AppInstallBean.getInstall_version();
                if (TextUtils.isEmpty(path) || H5FileUtil.exists(path)) {
                    return install_version;
                }
                H5Log.d("H5AppInstallDao", "path " + path + " is not exist");
                H5AppInstallDao.this.b(appId);
                return null;
            }
        });
        H5Log.d("H5AppInstallDao", "findInstallAppVersion  userId:" + a() + " appId:" + appId + " installVersion:" + installVersion + " cost" + (System.currentTimeMillis() - System.currentTimeMillis()));
        return installVersion;
    }

    public final void b(final String appId) {
        if (!TextUtils.isEmpty(appId)) {
            final long time = System.currentTimeMillis();
            a((H5DaoExecutor<T>) new H5DaoExecutor<Integer>() {
                /* access modifiers changed from: private */
                /* renamed from: b */
                public Integer a(H5BaseDBHelper dbHelper) {
                    Dao h5AppPoolDao = dbHelper.e();
                    QueryBuilder queryBuilder = h5AppPoolDao.queryBuilder();
                    H5AppDaoHelper.a(queryBuilder).eq(H5AppInstallBean.COL_APP_ID, appId);
                    if (queryBuilder.queryForFirst() != null) {
                        H5AppInstallBean h5AppInstallBean = (H5AppInstallBean) queryBuilder.queryForFirst();
                        if (h5AppInstallBean != null) {
                            H5Log.d("H5AppInstallDao", "deleteAppInstall userId: " + H5DaoTemplate.a() + " appId:" + appId + " cost:" + (System.currentTimeMillis() - time));
                            h5AppPoolDao.delete(h5AppInstallBean);
                        }
                    }
                    return null;
                }
            });
        }
    }
}
