package com.alipay.mobile.nebulaappcenter.dbdao;

import android.support.annotation.Nullable;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoExecutor;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.alipay.mobile.nebulaappcenter.dbbean.H5UrlAppMapBean;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class H5UrlMapDao extends H5DaoTemplate {
    private static H5UrlMapDao a = new H5UrlMapDao();

    public static synchronized H5UrlMapDao c() {
        H5UrlMapDao h5UrlMapDao;
        synchronized (H5UrlMapDao.class) {
            try {
                if (a == null) {
                    a = new H5UrlMapDao();
                }
                h5UrlMapDao = a;
            }
        }
        return h5UrlMapDao;
    }

    public final void a(final AppInfo appInfo) {
        H5Log.d("H5UrlMapDao", "updateUrlMap for appInfo: " + appInfo.extend_info_jo);
        final JSONObject extendObject = H5Utils.parseObject(appInfo.extend_info_jo);
        if (extendObject != null) {
            Integer result = (Integer) a((H5DaoExecutor<T>) new H5DaoExecutor<Integer>() {
                /* access modifiers changed from: private */
                /* renamed from: b */
                public Integer a(H5BaseDBHelper dbHelper) {
                    final Dao h5UrlMapDao = dbHelper.f();
                    QueryBuilder deleteBuilder = h5UrlMapDao.queryBuilder();
                    H5AppDaoHelper.a(deleteBuilder).eq("app_id", appInfo.app_id);
                    List beanList = deleteBuilder.query();
                    if (beanList != null) {
                        H5Log.d("H5UrlMapDao", "delete old urlMap for " + appInfo.app_id + " count " + h5UrlMapDao.delete((Collection<T>) beanList));
                    }
                    final JSONArray publicUrls = H5Utils.getJSONArray(extendObject, "publicUrls", null);
                    if (publicUrls == null || publicUrls.size() == 0) {
                        return Integer.valueOf(0);
                    }
                    return (Integer) h5UrlMapDao.callBatchTasks(new Callable<Integer>() {
                        /* access modifiers changed from: private */
                        /* renamed from: a */
                        public Integer call() {
                            int create;
                            int size = publicUrls.size();
                            int affected = 0;
                            for (int i = 0; i < size; i++) {
                                String pureUrl = H5UrlHelper.purifyUrl(publicUrls.getString(i));
                                QueryBuilder queryBuilder = h5UrlMapDao.queryBuilder();
                                H5AppDaoHelper.a(queryBuilder).eq(H5UrlAppMapBean.COL_PUBLICURL, pureUrl);
                                H5UrlAppMapBean bean = (H5UrlAppMapBean) queryBuilder.queryForFirst();
                                if (bean != null) {
                                    bean.setAppId(appInfo.app_id);
                                    create = h5UrlMapDao.update(bean);
                                } else {
                                    H5UrlAppMapBean bean2 = new H5UrlAppMapBean();
                                    bean2.setUserId(H5DaoTemplate.a());
                                    bean2.setPublicUrl(pureUrl);
                                    bean2.setAppId(appInfo.app_id);
                                    create = h5UrlMapDao.create(bean2);
                                }
                                affected += create;
                            }
                            return Integer.valueOf(affected);
                        }
                    });
                }
            });
            H5Log.d("H5UrlMapDao", "updateUrlMap for appInfo affected: " + (result == null ? 0 : result.intValue()));
        }
    }

    @Nullable
    public final String a(String url) {
        final String pureUrl = H5UrlHelper.purifyUrl(url);
        String result = (String) a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public String a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.f().queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq(H5UrlAppMapBean.COL_PUBLICURL, pureUrl);
                H5UrlAppMapBean bean = (H5UrlAppMapBean) queryBuilder.queryForFirst();
                if (bean != null) {
                    return bean.getAppId();
                }
                return null;
            }
        });
        H5Log.d("H5UrlMapDao", "findUrlMappedAppId " + url + " => " + result);
        return result;
    }
}
