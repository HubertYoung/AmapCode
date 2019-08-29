package com.alipay.mobile.nebulaappcenter.dbdao;

import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TinyDebugModeProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoExecutor;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5DevDBOpenHelper;
import com.alipay.mobile.nebulaappcenter.preset.H5PresetAppInfoUtil;
import com.alipay.mobile.nebulaappcenter.service.H5MemoryCache;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class H5NebulaAppDao extends H5DaoTemplate {
    private static H5NebulaAppDao a = new H5NebulaAppDao();

    public static synchronized H5NebulaAppDao c() {
        H5NebulaAppDao h5NebulaAppDao;
        synchronized (H5NebulaAppDao.class) {
            try {
                if (a == null) {
                    a = new H5NebulaAppDao();
                }
                h5NebulaAppDao = a;
            }
        }
        return h5NebulaAppDao;
    }

    private static boolean h() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equals(h5ConfigProvider.getConfigWithProcessCache("h5_enableUseCacheInTiny"))) {
            return true;
        }
        return false;
    }

    public final List<AppInfo> a(final String appId, boolean useDev) {
        List infoList = null;
        if (!TextUtils.isEmpty(appId) && !H5AppUtil.isNativeApp(appId)) {
            List<H5NebulaAppBean> h5AppInfoBeanList = (List) a(new H5DaoExecutor<List<H5NebulaAppBean>>() {
                /* access modifiers changed from: private */
                /* renamed from: b */
                public List<H5NebulaAppBean> a(H5BaseDBHelper dbHelper) {
                    QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                    H5AppDaoHelper.a(queryBuilder).eq("app_id", appId);
                    return queryBuilder.query();
                }
            }, useDev);
            if (h5AppInfoBeanList != null) {
                Collections.sort(h5AppInfoBeanList, new Comparator<H5NebulaAppBean>() {
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return a((H5NebulaAppBean) obj, (H5NebulaAppBean) obj2);
                    }

                    private static int a(H5NebulaAppBean s1, H5NebulaAppBean s2) {
                        return H5AppUtil.compareVersion(s1.getVersion(), s2.getVersion());
                    }
                });
                infoList = new ArrayList();
                for (H5NebulaAppBean appBean : h5AppInfoBeanList) {
                    infoList.add(H5AppInfoUtil.a(appBean));
                }
                if (useDev) {
                    H5Log.d("H5NebulaAppDao", "getAppInfoList useDevDB " + appId + " is not null");
                }
            }
        }
        return infoList;
    }

    public final AppInfo a(final String appId, final String ver) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        if (H5Utils.isDebuggable(H5Utils.getContext()) && H5DevConfig.getBooleanConfig(H5DevConfig.H5_USE_PRESET_PKG_INFO, false)) {
            AppInfo appInfo = H5PresetAppInfoUtil.a(appId);
            if (appInfo != null && !TextUtils.isEmpty(appInfo.version)) {
                H5NebulaAppBean h5AppInfoBean = H5AppInfoUtil.a(null, appInfo, false);
                H5Log.debug("H5NebulaAppDao", "getAppInfo from presetInfo : " + a() + ", " + appId + ", " + appInfo.version);
                AppInfo tem = H5AppInfoUtil.a(h5AppInfoBean);
                tem.fromPreset = "yes";
                return tem;
            }
        }
        boolean useDev = H5DevAppList.getInstance().contains(appId);
        AppInfo finalVersion = l(appId, ver);
        if (finalVersion != null) {
            if (!useDev) {
                H5MemoryCache.a().a(finalVersion);
            }
            return finalVersion;
        }
        if (!useDev) {
            AppInfo cache = H5MemoryCache.a().a(appId, ver);
            if (cache != null && (!H5Utils.isInTinyProcess() || h())) {
                return cache;
            }
        }
        if (TextUtils.isEmpty(ver)) {
            return null;
        }
        if (H5AppUtil.isNativeApp(appId)) {
            return null;
        }
        AppInfo info = (AppInfo) a(new H5DaoExecutor<AppInfo>() {
            /* access modifiers changed from: private */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x00f3, code lost:
                if (android.text.TextUtils.equals(r12, r0.version) != false) goto L_0x00f5;
             */
            /* JADX WARNING: Removed duplicated region for block: B:21:0x00bb  */
            /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
            /* renamed from: b */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public com.alipay.mobile.nebula.appcenter.model.AppInfo a(com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper r15) {
                /*
                    r14 = this;
                    r13 = 0
                    r6 = 0
                    com.j256.ormlite.dao.Dao r10 = r15.c()
                    com.j256.ormlite.stmt.QueryBuilder r5 = r10.queryBuilder()
                    com.j256.ormlite.stmt.Where r10 = com.alipay.mobile.nebulaappcenter.dbdao.H5AppDaoHelper.a(r5)
                    java.lang.String r11 = "app_id"
                    java.lang.String r12 = r11
                    com.j256.ormlite.stmt.Where r10 = r10.eq(r11, r12)
                    com.j256.ormlite.stmt.Where r10 = r10.and()
                    java.lang.String r11 = "version"
                    java.lang.String r12 = r12
                    r10.eq(r11, r12)
                    java.lang.Object r2 = r5.queryForFirst()
                    com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean r2 = (com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean) r2
                    if (r2 == 0) goto L_0x002e
                    com.alipay.mobile.nebula.appcenter.model.AppInfo r6 = com.alipay.mobile.nebulaappcenter.dbdao.H5AppInfoUtil.a(r2)
                L_0x002d:
                    return r6
                L_0x002e:
                    java.lang.String r10 = r11
                    com.alipay.mobile.nebula.appcenter.model.AppInfo r0 = com.alipay.mobile.nebulaappcenter.preset.H5PresetAppInfoUtil.a(r10)
                    if (r0 == 0) goto L_0x002d
                    java.lang.String r10 = r0.version
                    boolean r10 = android.text.TextUtils.isEmpty(r10)
                    if (r10 != 0) goto L_0x002d
                    r1 = 0
                    java.lang.String r10 = "H5NebulaAppDao"
                    java.lang.StringBuilder r11 = new java.lang.StringBuilder
                    java.lang.String r12 = "ver "
                    r11.<init>(r12)
                    java.lang.String r12 = r12
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = " version:"
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = r0.version
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = " appId"
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = r11
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r11 = r11.toString()
                    com.alipay.mobile.nebula.util.H5Log.d(r10, r11)
                    r8 = 0
                    java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r10 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
                    java.lang.String r10 = r10.getName()
                    java.lang.Object r3 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r10)
                    com.alipay.mobile.nebula.provider.H5ConfigProvider r3 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r3
                    if (r3 == 0) goto L_0x008b
                    java.lang.String r10 = "h5_ignorePresetVer"
                    java.lang.String r9 = r3.getConfigWithProcessCache(r10)
                    java.lang.String r10 = "no"
                    boolean r10 = r10.equalsIgnoreCase(r9)
                    if (r10 == 0) goto L_0x008b
                    r8 = 1
                L_0x008b:
                    if (r8 == 0) goto L_0x00f5
                    java.lang.String r10 = r12
                    java.lang.String r11 = "*"
                    boolean r10 = r10.contains(r11)
                    if (r10 == 0) goto L_0x00eb
                    java.lang.String r10 = r12
                    java.lang.String r11 = "*"
                    int r4 = r10.indexOf(r11)
                    java.lang.String r10 = r12
                    int r10 = r10.length()
                    r11 = 1
                    if (r10 == r11) goto L_0x00f5
                    java.lang.String r10 = r12
                    int r11 = r4 + -1
                    java.lang.String r7 = r10.substring(r13, r11)
                    java.lang.String r10 = r0.version
                    boolean r10 = r10.startsWith(r7)
                    if (r10 == 0) goto L_0x00b9
                    r1 = 1
                L_0x00b9:
                    if (r1 == 0) goto L_0x002d
                    com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean r2 = com.alipay.mobile.nebulaappcenter.dbdao.H5AppInfoUtil.a(r6, r0, r13)
                    java.lang.String r10 = "H5NebulaAppDao"
                    java.lang.StringBuilder r11 = new java.lang.StringBuilder
                    java.lang.String r12 = "h5PresetFroMemory get appInfo from preset "
                    r11.<init>(r12)
                    java.lang.String r12 = r11
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = " "
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r12 = r0.version
                    java.lang.StringBuilder r11 = r11.append(r12)
                    java.lang.String r11 = r11.toString()
                    com.alipay.mobile.nebula.util.H5Log.d(r10, r11)
                    com.alipay.mobile.nebula.appcenter.model.AppInfo r6 = com.alipay.mobile.nebulaappcenter.dbdao.H5AppInfoUtil.a(r2)
                    java.lang.String r10 = "yes"
                    r6.fromPreset = r10
                    goto L_0x002d
                L_0x00eb:
                    java.lang.String r10 = r12
                    java.lang.String r11 = r0.version
                    boolean r10 = android.text.TextUtils.equals(r10, r11)
                    if (r10 == 0) goto L_0x00b9
                L_0x00f5:
                    r1 = 1
                    goto L_0x00b9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappcenter.dbdao.H5NebulaAppDao.AnonymousClass12.a(com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper):com.alipay.mobile.nebula.appcenter.model.AppInfo");
            }
        }, useDev);
        if (useDev) {
            if (info != null) {
                H5Log.d("H5NebulaAppDao", "getAppInfo useDevDB" + appId + info.toString());
            } else {
                H5Log.d("H5NebulaAppDao", "getAppInfo useDevDB" + appId + " appInfo is null");
            }
        }
        if (!useDev) {
            H5MemoryCache.a().a(info);
        }
        if (info != null) {
            H5Log.d("H5NebulaAppDao", appId + " getNebulaAppInfo from getAppInfo  " + info.version);
        } else {
            H5Log.d("H5NebulaAppDao", appId + " getNebulaAppInfo from getAppInfo is null");
        }
        return info;
    }

    public final void a(String appId, String version, String column, Object value) {
        long time = System.currentTimeMillis();
        boolean useDev = k(appId, "");
        final String str = column;
        final Object obj = value;
        final String str2 = appId;
        final String str3 = version;
        Integer updated = (Integer) a(new H5DaoExecutor<Integer>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public Integer a(H5BaseDBHelper dbHelper) {
                UpdateBuilder updateBuilder = dbHelper.c().updateBuilder();
                updateBuilder.updateColumnValue(str, obj);
                H5AppDaoHelper.a(updateBuilder).eq("app_id", str2).and().eq("version", str3);
                return Integer.valueOf(updateBuilder.update());
            }
        }, useDev);
        H5Log.d("H5NebulaAppDao", "update column " + column + " to value: " + value + " with userId: " + a() + " appId: " + appId + Token.SEPARATOR + version + " affected: " + (updated == null ? 0 : updated.intValue()) + " cost: " + (System.currentTimeMillis() - time) + " useDev : " + useDev);
    }

    public final void a(final AppInfo appInfo) {
        if (appInfo != null) {
            long time = System.currentTimeMillis();
            boolean useDev = k(appInfo.app_id, appInfo.scene);
            a(new H5DaoExecutor<Object>() {
                public final Object a(H5BaseDBHelper dbHelper) {
                    Dao h5AppInfoDao = dbHelper.c();
                    QueryBuilder queryBuilder = h5AppInfoDao.queryBuilder();
                    H5AppDaoHelper.a(queryBuilder).eq("app_id", appInfo.app_id).and().eq("version", appInfo.version);
                    H5NebulaAppBean h5AppInfoBean = (H5NebulaAppBean) queryBuilder.queryForFirst();
                    boolean useDev = false;
                    if (dbHelper instanceof H5DevDBOpenHelper) {
                        useDev = true;
                    }
                    if (h5AppInfoBean != null) {
                        h5AppInfoDao.update(H5AppInfoUtil.a(h5AppInfoBean, appInfo, useDev));
                    } else {
                        h5AppInfoDao.create(H5AppInfoUtil.a(null, appInfo, useDev));
                    }
                    return null;
                }
            }, useDev);
            H5Log.d("H5NebulaAppDao", "saveAppInfo " + H5DaoTemplate.a() + Token.SEPARATOR + appInfo.app_id + Token.SEPARATOR + appInfo.version + " cost : " + (System.currentTimeMillis() - time) + " useDev : " + useDev);
        }
    }

    public final void a(List<AppInfo> appInfoList) {
        if (appInfoList != null && !appInfoList.isEmpty()) {
            List devList = null;
            List onlineList = null;
            for (AppInfo appInfo : appInfoList) {
                if (k(appInfo.app_id, appInfo.scene)) {
                    if (devList == null) {
                        devList = new ArrayList();
                    }
                    devList.add(appInfo);
                } else {
                    if (onlineList == null) {
                        onlineList = new ArrayList();
                    }
                    onlineList.add(appInfo);
                }
            }
            a(onlineList, false);
            a(devList, true);
        }
    }

    private static boolean k(String appId, String scene) {
        return H5DevAppList.getInstance().contains(appId) || H5DevAppList.getInstance().isDevAppInfo(scene);
    }

    private void a(final List<AppInfo> appInfoList, final boolean useDev) {
        if (appInfoList != null && !appInfoList.isEmpty()) {
            try {
                final Dao h5AppInfoDao = a(useDev).c();
                h5AppInfoDao.callBatchTasks(new Callable<Object>() {
                    public final Object call() {
                        for (AppInfo appInfo : appInfoList) {
                            QueryBuilder queryBuilder = h5AppInfoDao.queryBuilder();
                            H5AppDaoHelper.a(queryBuilder).eq("app_id", appInfo.app_id).and().eq("version", appInfo.version);
                            H5NebulaAppBean h5AppInfoBean = (H5NebulaAppBean) queryBuilder.queryForFirst();
                            if (h5AppInfoBean != null) {
                                h5AppInfoDao.update(H5AppInfoUtil.a(h5AppInfoBean, appInfo, useDev));
                            } else {
                                h5AppInfoDao.create(H5AppInfoUtil.a(null, appInfo, useDev));
                            }
                            H5Log.d("H5NebulaAppDao", "saveAppInfo " + H5DaoTemplate.a() + Token.SEPARATOR + appInfo.app_id + Token.SEPARATOR + appInfo.version + " useDev : " + useDev);
                        }
                        return null;
                    }
                });
            } catch (Throwable throwable) {
                H5Log.e((String) "H5NebulaAppDao", throwable);
            }
        }
    }

    public final void b(final String appId, final String version) {
        H5DaoExecutor executor = new H5DaoExecutor<Integer>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public Integer a(H5BaseDBHelper dbHelper) {
                Dao h5AppInfoDao = dbHelper.c();
                QueryBuilder queryBuilder = h5AppInfoDao.queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq("app_id", appId).and().eq("version", version);
                if (queryBuilder.queryForFirst() != null) {
                    H5NebulaAppBean h5AppInfoBean = (H5NebulaAppBean) queryBuilder.queryForFirst();
                    if (h5AppInfoBean != null) {
                        H5Log.d("H5NebulaAppDao", "deleteAppInfo:" + H5DaoTemplate.a() + Token.SEPARATOR + appId + Token.SEPARATOR + version);
                        h5AppInfoDao.delete(h5AppInfoBean);
                    }
                }
                return null;
            }
        };
        boolean useDev = H5DevAppList.getInstance().contains(appId);
        a(executor, useDev);
        if (!useDev) {
            H5MemoryCache.a().b(appId, version);
        }
    }

    public final List<H5NebulaAppBean> a(final String appId) {
        final String installVersion;
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        if (H5DBUtil.d()) {
            installVersion = "";
        } else {
            installVersion = H5AppInstallDao.c().a(appId);
        }
        H5DaoExecutor executor = new H5DaoExecutor<List<H5NebulaAppBean>>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public List<H5NebulaAppBean> a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                if (TextUtils.isEmpty(installVersion)) {
                    H5AppDaoHelper.a(queryBuilder).eq("app_id", appId).and().eq("is_mapping", Integer.valueOf(0));
                } else {
                    H5AppDaoHelper.a(queryBuilder).eq("app_id", appId).and().eq("is_mapping", Integer.valueOf(0)).and().not().eq("version", installVersion);
                }
                if (queryBuilder.queryForFirst() != null) {
                    return queryBuilder.query();
                }
                return null;
            }
        };
        boolean useDev = false;
        if (H5DevAppList.getInstance().contains(appId)) {
            useDev = true;
        }
        List<H5NebulaAppBean> listAppPool = (List) a(executor, useDev);
        if (listAppPool == null) {
            return null;
        }
        Collections.sort(listAppPool, new Comparator<H5NebulaAppBean>() {
            public final /* synthetic */ int compare(Object obj, Object obj2) {
                return a((H5NebulaAppBean) obj, (H5NebulaAppBean) obj2);
            }

            private static int a(H5NebulaAppBean s1, H5NebulaAppBean s2) {
                return H5AppUtil.compareVersion(s1.getVersion(), s2.getVersion());
            }
        });
        if (listAppPool.size() == 0) {
            return null;
        }
        String log = "";
        for (H5NebulaAppBean h5NebulaAppBean : listAppPool) {
            log = log + Token.SEPARATOR + h5NebulaAppBean.getVersion();
        }
        if (useDev) {
            H5TinyDebugModeProvider debugModeProvider = (H5TinyDebugModeProvider) H5Utils.getProvider(H5TinyDebugModeProvider.class.getName());
            if (debugModeProvider != null) {
                Map debugMap = debugModeProvider.getAppDebugModeAndVersion(appId);
                if (debugMap != null && !debugMap.isEmpty()) {
                    H5Log.d("H5NebulaAppDao", "H5TinyAppDebugMode appId : " + appId + " debugMap : " + debugMap.toString());
                    List debugAppPool = new ArrayList();
                    String devLog = "";
                    for (H5NebulaAppBean h5NebulaAppBean2 : listAppPool) {
                        if (!debugMap.containsValue(h5NebulaAppBean2.getVersion())) {
                            devLog = devLog + Token.SEPARATOR + h5NebulaAppBean2.getVersion();
                            debugAppPool.add(h5NebulaAppBean2);
                        }
                    }
                    H5Log.d("H5NebulaAppDao", appId + "H5TinyAppDebugMode getCanDeleteAppPooIdList : " + devLog);
                    return debugAppPool;
                }
            }
        }
        H5Log.d("H5NebulaAppDao", appId + " getCanDeleteAppPooIdList " + log);
        return listAppPool;
    }

    public final void c(String appId, String version) {
        H5Log.d("H5NebulaAppDao", "markNoDeleteAppVersion " + appId + Token.SEPARATOR + version);
        AppInfo appInfo = a(appId, version);
        if (appInfo != null) {
            appInfo.is_mapping = 1;
            a(appInfo);
        }
    }

    public final void d(String appId, String version) {
        H5Log.d("H5NebulaAppDao", "unMarkNoDeleteAppVersion " + appId + Token.SEPARATOR + version);
        AppInfo appInfo = a(appId, version);
        if (appInfo != null) {
            appInfo.is_mapping = 0;
            a(appInfo);
        }
    }

    public final Map<String, List<AppInfo>> d() {
        Map appList = null;
        List<H5NebulaAppBean> h5AppPoolBeanList = (List) a(new H5DaoExecutor<List<H5NebulaAppBean>>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static List<H5NebulaAppBean> b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.b(queryBuilder);
                return queryBuilder.query();
            }
        }, H5DevAppList.getInstance().contains(null));
        if (h5AppPoolBeanList != null) {
            appList = new HashMap();
            for (H5NebulaAppBean h5AppPoolBean : h5AppPoolBeanList) {
                if (appList.get(h5AppPoolBean.getApp_id()) != null) {
                    List versionList = (List) appList.get(h5AppPoolBean.getApp_id());
                    versionList.add(H5AppInfoUtil.a(h5AppPoolBean));
                    appList.put(h5AppPoolBean.getApp_id(), versionList);
                } else {
                    List versionList2 = new ArrayList();
                    versionList2.add(H5AppInfoUtil.a(h5AppPoolBean));
                    appList.put(h5AppPoolBean.getApp_id(), versionList2);
                }
            }
        }
        return appList;
    }

    public final Map<String, String> e() {
        long time = System.currentTimeMillis();
        List<H5NebulaAppBean> h5AppPoolBeanList = (List) a((H5DaoExecutor<T>) new H5DaoExecutor<List<H5NebulaAppBean>>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static List<H5NebulaAppBean> b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.b(queryBuilder);
                return queryBuilder.query();
            }
        });
        if (h5AppPoolBeanList == null) {
            return null;
        }
        Map appList = new HashMap();
        for (H5NebulaAppBean h5AppPoolBean : h5AppPoolBeanList) {
            if (h5AppPoolBean.getApp_id() != null) {
                if (appList.containsKey(h5AppPoolBean.getApp_id())) {
                    if (H5AppUtil.compareVersion(h5AppPoolBean.getVersion(), (String) appList.get(h5AppPoolBean.getApp_id())) > 0) {
                        appList.put(h5AppPoolBean.getApp_id(), h5AppPoolBean.getVersion());
                    }
                } else {
                    appList.put(h5AppPoolBean.getApp_id(), h5AppPoolBean.getVersion());
                }
            }
        }
        H5Log.d("H5NebulaAppDao", "getAllHighestAppVersion cost:" + (System.currentTimeMillis() - time));
        return appList;
    }

    public final Map<String, AppInfo> f() {
        long time = System.currentTimeMillis();
        List<H5NebulaAppBean> h5AppPoolBeanList = (List) a((H5DaoExecutor<T>) new H5DaoExecutor<List<H5NebulaAppBean>>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static List<H5NebulaAppBean> b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.b(queryBuilder);
                return queryBuilder.query();
            }
        });
        if (h5AppPoolBeanList == null) {
            return null;
        }
        Map appList = new HashMap();
        for (H5NebulaAppBean h5AppPoolBean : h5AppPoolBeanList) {
            if (h5AppPoolBean.getApp_id() != null) {
                if (appList.containsKey(h5AppPoolBean.getApp_id())) {
                    AppInfo mapVersion = (AppInfo) appList.get(h5AppPoolBean.getApp_id());
                    if (mapVersion != null && H5AppUtil.compareVersion(h5AppPoolBean.getVersion(), mapVersion.version) > 0) {
                        appList.put(h5AppPoolBean.getApp_id(), H5AppInfoUtil.a(h5AppPoolBean));
                    }
                } else {
                    appList.put(h5AppPoolBean.getApp_id(), H5AppInfoUtil.a(h5AppPoolBean));
                }
            }
        }
        H5Log.d("H5NebulaAppDao", "getAllHighestAppInfo cost:" + (System.currentTimeMillis() - time));
        return appList;
    }

    public final Map<String, String> g() {
        long time = System.currentTimeMillis();
        List<H5NebulaAppBean> h5AppPoolBeanList = (List) a((H5DaoExecutor<T>) new H5DaoExecutor<List<H5NebulaAppBean>>() {
            public final /* synthetic */ Object a(H5BaseDBHelper h5BaseDBHelper) {
                return b(h5BaseDBHelper);
            }

            private static List<H5NebulaAppBean> b(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq(H5NebulaAppBean.LOCAL_REPORT, Integer.valueOf(1));
                return queryBuilder.query();
            }
        });
        if (h5AppPoolBeanList == null) {
            return null;
        }
        Map appList = new HashMap();
        for (H5NebulaAppBean h5AppPoolBean : h5AppPoolBeanList) {
            if (h5AppPoolBean.getApp_id() != null) {
                if (appList.containsKey(h5AppPoolBean.getApp_id())) {
                    if (H5AppUtil.compareVersion(h5AppPoolBean.getVersion(), (String) appList.get(h5AppPoolBean.getApp_id())) > 0) {
                        appList.put(h5AppPoolBean.getApp_id(), h5AppPoolBean.getVersion());
                    }
                } else {
                    appList.put(h5AppPoolBean.getApp_id(), h5AppPoolBean.getVersion());
                }
            }
        }
        H5Log.d("H5NebulaAppDao", "getAllHighestLocalReportAppVersion cost:" + (System.currentTimeMillis() - time));
        return appList;
    }

    public final void e(String appId, String version) {
        AppInfo appInfo = a(appId, version);
        if (appInfo != null) {
            appInfo.is_limit = 1;
            a(appInfo);
        }
    }

    public final void f(String appId, String version) {
        AppInfo appInfo = a(appId, version);
        if (appInfo != null) {
            appInfo.is_limit = 0;
            a(appInfo);
        }
    }

    public final boolean g(String appId, String version) {
        boolean isLimit = false;
        AppInfo appInfo = a(appId, version);
        if (appInfo != null) {
            isLimit = appInfo.is_limit == 1;
        }
        H5Log.d("H5NebulaAppDao", "isLimitApp  appId:" + appId + " version:" + version + " isLimit:" + isLimit);
        return isLimit;
    }

    public final String h(String appId, String version) {
        AppInfo appInfo = a(appId, version);
        if (appInfo == null) {
            return null;
        }
        H5Log.d("H5NebulaAppDao", "getUpdateAppTime  appId:" + appId + " version:" + version + " updateTime:" + appInfo.update_app_time);
        return appInfo.update_app_time;
    }

    public static void a(String appId, String version, String path) {
        H5AppInstallDao.c().a(appId, version, path);
    }

    public final AppInfo b(final String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        if (H5AppUtil.isNativeApp(appId)) {
            return null;
        }
        H5DaoExecutor executor = new H5DaoExecutor<List<H5NebulaAppBean>>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public List<H5NebulaAppBean> a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq("app_id", appId);
                String result = "";
                for (H5NebulaAppBean bean : queryBuilder.query()) {
                    result = result + Token.SEPARATOR + bean.getVersion();
                }
                H5Log.d("H5NebulaAppDao", "getHighestAppVersion result version :" + result);
                return queryBuilder.query();
            }
        };
        boolean useDev = false;
        try {
            if (H5DevAppList.getInstance().contains(appId)) {
                useDev = true;
            }
            List listAppPool = (List) a(executor, useDev);
            if (listAppPool == null) {
                return null;
            }
            Collections.sort(listAppPool, new Comparator<H5NebulaAppBean>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return a((H5NebulaAppBean) obj, (H5NebulaAppBean) obj2);
                }

                private static int a(H5NebulaAppBean s1, H5NebulaAppBean s2) {
                    return H5AppUtil.compareVersion(s1.getVersion(), s2.getVersion());
                }
            });
            if (listAppPool.size() == 0) {
                return null;
            }
            AppInfo appInfo = H5AppInfoUtil.a((H5NebulaAppBean) listAppPool.get(listAppPool.size() - 1));
            H5Log.d("H5NebulaAppDao", appId + " getNebulaAppInfo from getHighestAppVersion  appId: version:" + appInfo.version);
            return appInfo;
        } catch (Exception e) {
            H5Log.e((String) "H5NebulaAppDao", (Throwable) e);
            return null;
        }
    }

    private AppInfo l(String appId, String version) {
        if (TextUtils.isEmpty(version)) {
            return b(appId);
        }
        if (TextUtils.isEmpty(version) || !version.contains("*")) {
            return null;
        }
        return i(appId, version);
    }

    public final AppInfo i(final String appId, String version) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        if (TextUtils.isEmpty(version)) {
            return b(appId);
        }
        if (!version.contains("*")) {
            H5Log.d("H5NebulaAppDao", "!version.contains *");
            return null;
        }
        int index = version.indexOf("*");
        if (version.length() == 1) {
            return b(appId);
        }
        String version2 = version.substring(0, index - 1);
        H5DaoExecutor executor = new H5DaoExecutor<List<H5NebulaAppBean>>() {
            /* access modifiers changed from: private */
            /* renamed from: b */
            public List<H5NebulaAppBean> a(H5BaseDBHelper dbHelper) {
                QueryBuilder queryBuilder = dbHelper.c().queryBuilder();
                H5AppDaoHelper.a(queryBuilder).eq("app_id", appId);
                return queryBuilder.query();
            }
        };
        try {
            ArrayList list = new ArrayList();
            List<H5NebulaAppBean> listAppPool = (List) a(executor, H5DevAppList.getInstance().contains(appId));
            if (listAppPool == null) {
                return null;
            }
            for (H5NebulaAppBean bean : listAppPool) {
                if (bean.getVersion() != null && !bean.getVersion().contains("*") && bean.getVersion().startsWith(version2)) {
                    list.add(bean);
                }
            }
            Collections.sort(list, new Comparator<H5NebulaAppBean>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return a((H5NebulaAppBean) obj, (H5NebulaAppBean) obj2);
                }

                private static int a(H5NebulaAppBean s1, H5NebulaAppBean s2) {
                    return H5AppUtil.compareVersion(s1.getVersion(), s2.getVersion());
                }
            });
            if (list.size() == 0) {
                return null;
            }
            AppInfo matchVersion = H5AppInfoUtil.a((H5NebulaAppBean) list.get(list.size() - 1));
            H5Log.d("H5NebulaAppDao", appId + " getNebulaAppInfo from getMatchHighestAppVersion " + matchVersion.version);
            return matchVersion;
        } catch (Throwable e) {
            H5Log.e((String) "H5NebulaAppDao", e);
            return null;
        }
    }

    public final void j(String appId, String version) {
        long time = System.currentTimeMillis();
        a(a(appId, version));
        H5Log.d("H5NebulaAppDao", appId + " updateUpdateTime cost: " + (System.currentTimeMillis() - time));
    }

    public final void c(final String appId) {
        if (!TextUtils.isEmpty(appId)) {
            long time = System.currentTimeMillis();
            a(new H5DaoExecutor<Object>() {
                public final Object a(H5BaseDBHelper dbHelper) {
                    Dao h5AppInfoDao = dbHelper.c();
                    QueryBuilder queryBuilder = h5AppInfoDao.queryBuilder();
                    H5AppDaoHelper.a(queryBuilder).eq("app_id", appId);
                    try {
                        List appBeanList = queryBuilder.query();
                        if (appBeanList != null && !appBeanList.isEmpty()) {
                            Collections.sort(appBeanList, new Comparator<H5NebulaAppBean>() {
                                public final /* synthetic */ int compare(Object obj, Object obj2) {
                                    return a((H5NebulaAppBean) obj, (H5NebulaAppBean) obj2);
                                }

                                private static int a(H5NebulaAppBean s1, H5NebulaAppBean s2) {
                                    return H5AppUtil.compareVersion(s1.getVersion(), s2.getVersion());
                                }
                            });
                            H5NebulaAppBean updateAppBean = (H5NebulaAppBean) appBeanList.get(appBeanList.size() - 1);
                            updateAppBean.setUpdate_app_time("0");
                            H5Log.d("H5NebulaAppDao", "clearUpdateTime appId : " + updateAppBean.getApp_id() + " version : " + updateAppBean.getVersion());
                            h5AppInfoDao.update(updateAppBean);
                            H5MemoryCache.a().b(appId, updateAppBean.getVersion());
                        }
                    } catch (Throwable e) {
                        H5Log.e((String) "H5NebulaAppDao", e);
                    }
                    return null;
                }
            }, false);
            H5Log.d("H5NebulaAppDao", "clearUpdateTime " + appId + " cost : " + (System.currentTimeMillis() - time));
        }
    }
}
