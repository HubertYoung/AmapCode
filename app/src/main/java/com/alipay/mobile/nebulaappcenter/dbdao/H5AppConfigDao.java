package com.alipay.mobile.nebulaappcenter.dbdao;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulaappcenter.app.H5NebulaDBService;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoExecutor;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppConfigBean;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class H5AppConfigDao extends H5DaoTemplate {
    private static H5AppConfigDao a = new H5AppConfigDao();

    public static synchronized H5AppConfigDao c() {
        H5AppConfigDao h5AppConfigDao;
        synchronized (H5AppConfigDao.class) {
            try {
                if (a == null) {
                    a = new H5AppConfigDao();
                }
                h5AppConfigDao = a;
            }
        }
        return h5AppConfigDao;
    }

    @Deprecated
    public final void a(Map<String, String> appMap) {
        if (appMap != null && !appMap.isEmpty()) {
            a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
                public final Object a(H5BaseDBHelper dbHelper) {
                    Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                    QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                    if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                        H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                        h5AppConfigBean.setFailed_request_app_list("");
                        h5AppConfigBeanIntegerDao.update(h5AppConfigBean);
                    }
                    return null;
                }
            });
        }
    }

    @Deprecated
    public final void b(Map<String, String> appMap) {
        final String failList = c(appMap);
        if (!TextUtils.isEmpty(failList)) {
            a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
                public final Object a(H5BaseDBHelper dbHelper) {
                    Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                    QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                    if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                        H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                        h5AppConfigBean.setFailed_request_app_list(failList);
                        H5Log.d("H5AppConfigDao", "setFailedRequestAppList:" + failList);
                        h5AppConfigBeanIntegerDao.update(h5AppConfigBean);
                    }
                    return null;
                }
            });
        }
    }

    @Deprecated
    public final Map<String, String> d() {
        String list = (String) a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static String b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    return null;
                }
                return ((H5AppConfigBean) queryBuilder.queryForFirst()).getFailed_request_app_list();
            }
        });
        H5Log.d("H5AppConfigDao", "getFailedRequestAppList:" + list + " cost:" + (System.currentTimeMillis() - System.currentTimeMillis()));
        return b(list);
    }

    public final void e() {
        a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static String b(H5BaseDBHelper dbHelper) {
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    h5AppConfigBean.setFailed_request_app_list("");
                    h5AppConfigBeanIntegerDao.update(h5AppConfigBean);
                }
                return null;
            }
        });
    }

    public final double f() {
        long time = System.currentTimeMillis();
        double rate = ((Double) a((H5DaoExecutor<T>) new H5DaoExecutor<Double>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static Double b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return Double.valueOf(h5AppConfigBean.getNormalReqRate());
                    }
                }
                return Double.valueOf(0.0d);
            }
        })).doubleValue();
        if (rate == 0.0d) {
            rate = 1800.0d;
        }
        H5Log.d("H5AppConfigDao", "getNormalReqRate limit:" + rate + " cost:" + (System.currentTimeMillis() - time));
        return rate;
    }

    public final double g() {
        double rate = ((Double) a((H5DaoExecutor<T>) new H5DaoExecutor<Double>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static Double b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return Double.valueOf(h5AppConfigBean.getLimitReqRate());
                    }
                }
                return Double.valueOf(0.0d);
            }
        })).doubleValue();
        if (rate == 0.0d) {
            rate = 600.0d;
        }
        H5Log.d("H5AppConfigDao", "getLimitReqRate limit:" + rate);
        return rate;
    }

    public final String h() {
        String outOfTime = (String) a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static String b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return h5AppConfigBean.getLast_update_date();
                    }
                }
                return "0";
            }
        });
        H5Log.d("H5AppConfigDao", "getLastAllUpdateTime :" + outOfTime);
        return outOfTime;
    }

    public final void a(final int limit) {
        H5Log.d("H5AppConfigDao", "updateAppPoolLimit:" + limit);
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                h5AppConfigBean.setApp_pool_limit(limit);
                h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                return null;
            }
        });
    }

    public final boolean i() {
        return a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    return null;
                }
                return (H5AppConfigBean) queryBuilder.queryForFirst();
            }
        }) != null;
    }

    public final void a(final double rate) {
        H5Log.d("H5AppConfigDao", "updateNormalReqRate rate:" + rate);
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                h5AppConfigBean.setNormalReqRate(rate);
                h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                return null;
            }
        });
    }

    public final void a(final H5AppConfigBean saveBean) {
        long time = System.currentTimeMillis();
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = H5AppConfigDao.b(null, saveBean);
                } else {
                    h5AppConfigBean = H5AppConfigDao.b((H5AppConfigBean) queryBuilder.queryForFirst(), saveBean);
                }
                h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                return null;
            }
        });
        H5Log.d("H5AppConfigDao", "createOrUpdateConfig cost " + (System.currentTimeMillis() - time));
    }

    /* access modifiers changed from: private */
    public static H5AppConfigBean b(H5AppConfigBean dbBean, H5AppConfigBean saveBean) {
        if (dbBean == null) {
            dbBean = new H5AppConfigBean();
        }
        dbBean.setApp_pool_limit(saveBean.getApp_pool_limit());
        dbBean.setNormalReqRate(saveBean.getNormalReqRate());
        dbBean.setLimitReqRate(saveBean.getLimitReqRate());
        dbBean.setExtra(saveBean.getExtra());
        return dbBean;
    }

    public final void b(final int rate) {
        H5Log.d("H5AppConfigDao", "createOrUpdateStrictReqRate rate:" + rate);
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                h5AppConfigBean.setStrictReqRate(rate);
                h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                return null;
            }
        });
    }

    public final String j() {
        String extra = (String) a((H5DaoExecutor<T>) new H5DaoExecutor<String>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static String b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return h5AppConfigBean.getExtra();
                    }
                }
                return "";
            }
        });
        H5Log.d("H5AppConfigDao", "getExtra :" + extra);
        return extra;
    }

    public final void a(final String extra) {
        long time = System.currentTimeMillis();
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                h5AppConfigBean.setExtra(extra);
                h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                return null;
            }
        });
        H5Log.d("H5AppConfigDao", "createOrUpdateExtra extra:" + extra + " cost:" + (System.currentTimeMillis() - time));
    }

    public final void b(final double rate) {
        H5Log.d("H5AppConfigDao", "updateLimitReqRate rate:" + rate);
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                if (h5AppConfigBean != null) {
                    h5AppConfigBean.setLimitReqRate(rate);
                    h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                }
                return null;
            }
        });
    }

    public final int k() {
        int limit = ((Integer) a((H5DaoExecutor<T>) new H5DaoExecutor<Integer>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static Integer b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return Integer.valueOf(h5AppConfigBean.getApp_pool_limit());
                    }
                }
                return Integer.valueOf(0);
            }
        })).intValue();
        if (limit == 0) {
            limit = 3;
        }
        H5Log.d("H5AppConfigDao", "getAppPoolLimit limit:" + limit);
        return limit;
    }

    public final int l() {
        int limit = ((Integer) a((H5DaoExecutor<T>) new H5DaoExecutor<Integer>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static Integer b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.d().queryBuilder();
                if (!(queryBuilder == null || queryBuilder.queryForFirst() == null)) {
                    H5AppConfigBean h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                    if (h5AppConfigBean != null) {
                        return Integer.valueOf(h5AppConfigBean.getStrictReqRate());
                    }
                }
                return Integer.valueOf(0);
            }
        })).intValue();
        H5Log.d("H5AppConfigDao", "getStrictReqRate :" + limit);
        return limit;
    }

    public final void m() {
        a((H5DaoExecutor<T>) new H5DaoExecutor<Object>() {
            public final Object a(H5BaseDBHelper dbHelper) {
                H5AppConfigBean h5AppConfigBean;
                Dao h5AppConfigBeanIntegerDao = dbHelper.d();
                QueryBuilder queryBuilder = h5AppConfigBeanIntegerDao.queryBuilder();
                if (queryBuilder == null || queryBuilder.queryForFirst() == null) {
                    h5AppConfigBean = new H5AppConfigBean();
                } else {
                    h5AppConfigBean = (H5AppConfigBean) queryBuilder.queryForFirst();
                }
                if (h5AppConfigBean != null) {
                    h5AppConfigBean.setApp_pool_limit(3);
                    h5AppConfigBean.setLimitReqRate(600.0d);
                    h5AppConfigBean.setNormalReqRate(1800.0d);
                    h5AppConfigBean.setStrictReqRate(0);
                    h5AppConfigBeanIntegerDao.createOrUpdate(h5AppConfigBean);
                }
                return null;
            }
        });
    }

    private static Map<String, String> b(String jsonStr) {
        JSONObject jsonObject;
        Map map = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                jsonObject = JSON.parseObject(jsonStr);
            } catch (Exception e) {
                H5Log.e((String) "H5AppConfigDao", (Throwable) e);
                jsonObject = null;
                H5NebulaDBService.getInstance().cleanAllFailList();
            }
            if (jsonObject != null && !jsonObject.isEmpty()) {
                map = new HashMap();
                for (String key : jsonObject.keySet()) {
                    map.put(key, jsonObject.get(key).toString());
                }
            }
        }
        return map;
    }

    private static String c(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Entry appEntry : map.entrySet()) {
            String appId = (String) appEntry.getKey();
            String version = (String) appEntry.getValue();
            if (!jsonObject.containsKey(appId)) {
                jsonObject.put(appId, (Object) version);
            }
        }
        if (jsonObject.isEmpty()) {
            return "";
        }
        return jsonObject.toJSONString();
    }
}
