package com.alipay.mobile.nebulaappcenter.app;

import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService.ClearTableCallback;
import com.alipay.mobile.nebula.appcenter.db.H5GetAppInfoListen;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppConfigBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean;
import com.alipay.mobile.nebulaappcenter.dbdao.H5AppConfigDao;
import com.alipay.mobile.nebulaappcenter.dbdao.H5AppDaoHelper;
import com.alipay.mobile.nebulaappcenter.dbdao.H5AppInstallDao;
import com.alipay.mobile.nebulaappcenter.dbdao.H5NebulaAppDao;
import com.alipay.mobile.nebulaappcenter.dbdao.H5UrlMapDao;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil;
import com.alipay.mobile.nebulaappcenter.preset.H5PresetAppInfoUtil;
import java.util.List;
import java.util.Map;

public class H5NebulaDBService implements H5AppDBService {
    public static int LIMIT_CODE = 1024;
    private static H5NebulaDBService a;
    private double b;
    private double c;
    private int d = 0;
    private Boolean e = null;
    private String f = null;

    private class AppInfoRunnable implements Runnable {
        private AppInfo b;
        private H5GetAppInfoListen c;

        AppInfoRunnable(AppInfo app, H5GetAppInfoListen listen) {
            this.b = app;
            this.c = listen;
        }

        public void run() {
            if (this.c != null) {
                this.c.getAppInfoReady(this.b);
            }
        }
    }

    public static synchronized H5NebulaDBService getInstance() {
        H5NebulaDBService h5NebulaDBService;
        synchronized (H5NebulaDBService.class) {
            try {
                if (a == null) {
                    a = new H5NebulaDBService();
                }
                h5NebulaDBService = a;
            }
        }
        return h5NebulaDBService;
    }

    public List<AppInfo> getAppInfoList(String appId, boolean useDev) {
        return H5NebulaAppDao.c().a(appId, useDev);
    }

    public AppInfo getAppInfo(String appId, String version) {
        return H5NebulaAppDao.c().a(appId, version);
    }

    public void getAppInfoAsync(final String appId, final String version, final H5GetAppInfoListen h5GetAppInfoListen) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
            public void run() {
                H5Utils.runOnMain(new AppInfoRunnable(H5NebulaDBService.this.getAppInfo(appId, version), h5GetAppInfoListen));
            }
        });
    }

    public Map<String, String> getInstalledApp() {
        return H5AppInstallDao.c().d();
    }

    public Map<String, List<AppInfo>> getAllApp() {
        return H5NebulaAppDao.c().d();
    }

    public Map<String, String> getAllHighestAppVersion() {
        return H5NebulaAppDao.c().e();
    }

    public Map<String, String> getAllHighestLocalReportAppVersion() {
        return H5NebulaAppDao.c().g();
    }

    public double getLimitReqRate() {
        if (this.b <= 0.0d) {
            this.b = H5AppConfigDao.c().g();
        }
        return this.b;
    }

    public void createOrUpdateLimitReqRate(double rate) {
        this.b = rate;
        H5AppConfigDao.c().b(rate);
    }

    public int getStrictReqRate() {
        return H5AppConfigDao.c().l();
    }

    public String getLastAllUpdateTime() {
        return H5AppConfigDao.c().h();
    }

    public String getHighestAppVersion(String appId) {
        AppInfo appInfo = H5NebulaAppDao.c().b(appId);
        if (appInfo != null) {
            return appInfo.version;
        }
        return null;
    }

    public String getMatchHighestAppVersion(String appId, String version) {
        AppInfo appInfo = H5NebulaAppDao.c().i(appId, version);
        if (appInfo != null) {
            return appInfo.version;
        }
        return null;
    }

    public void updateAppLimit(String appId, String version) {
        H5NebulaAppDao.c().e(appId, version);
    }

    public void cleanAppLimit(String appId, String version) {
        H5NebulaAppDao.c().f(appId, version);
    }

    public boolean isLimitApp(String appId, String version) {
        return H5NebulaAppDao.c().g(appId, version);
    }

    public String getUpdateAppTime(String appId, String version) {
        return H5NebulaAppDao.c().h(appId, version);
    }

    public void saveAppInfo(AppInfo appInfo, boolean forceSync) {
        H5NebulaAppDao.c().a(appInfo);
        if (!"NO".equalsIgnoreCase(H5Utils.getString(H5Utils.parseObject(H5WalletWrapper.getConfigWithProcessCache("h5_resManifest")), (String) "parsePublicUrl", (String) null))) {
            H5UrlMapDao.c().a(appInfo);
        }
    }

    public void updateUnavailableReason(String appId, String version, String value) {
        H5NebulaAppDao.c().a(appId, version, H5NebulaAppBean.COL_UNAVAIL_REASON, value + "." + System.currentTimeMillis());
    }

    public void saveAppInfoList(List<AppInfo> appInfoList) {
        H5NebulaAppDao.c().a(appInfoList);
    }

    public void insertInstalledAppInfo(String appId, String version, String path) {
        H5NebulaAppDao.c();
        H5NebulaAppDao.a(appId, version, path);
    }

    public double getNormalReqRate() {
        if (this.c <= 0.0d) {
            this.c = H5AppConfigDao.c().f();
        }
        return this.c;
    }

    public void createOrUpdateNormalReqRate(double rate) {
        this.c = rate;
        H5AppConfigDao.c().a(rate);
    }

    public void cleanFailedRequestAppList(Map<String, String> appMap) {
        H5AppConfigDao.c().a(appMap);
    }

    public void setFailedRequestAppList(Map<String, String> appMap) {
        H5AppConfigDao.c().b(appMap);
    }

    public Map<String, String> getFailedRequestAppList() {
        return H5AppConfigDao.c().d();
    }

    public void createOrUpdateAppPoolLimit(int limit) {
        this.d = limit;
        H5AppConfigDao.c().a(limit);
    }

    public int getAppPoolLimit() {
        if (this.d <= 0) {
            this.d = H5AppConfigDao.c().k();
        }
        return this.d;
    }

    public String findInstallAppVersion(String appId) {
        return H5AppInstallDao.c().a(appId);
    }

    public void deleteAppInfo(String appId, String version) {
        H5NebulaAppDao.c().b(appId, version);
    }

    public void clearAllTable(ClearTableCallback clearTableCallback) {
        H5DBUtil.a().g();
        if (H5DBUtil.c()) {
            H5DBUtil.b().g();
        }
        if (clearTableCallback != null) {
            clearTableCallback.getCleared();
        }
    }

    public void updateUpdateTime(String appId, String version) {
        H5NebulaAppDao.c().j(appId, version);
    }

    public void onSwitchAccount() {
        H5DaoTemplate.b();
        H5AppDaoHelper.b();
    }

    public void markNoDeleteAppVersion(String appId, String version) {
        H5NebulaAppDao.c().c(appId, version);
    }

    public void unMarkNoDeleteAppVersion(String appId, String version) {
        H5NebulaAppDao.c().d(appId, version);
    }

    public void clearPresetMemory() {
        H5PresetAppInfoUtil.a();
    }

    public List<H5NebulaAppBean> getCanDeleteAppPooIdList(String appId) {
        return H5NebulaAppDao.c().a(appId);
    }

    public void setDefaultConfig() {
        H5AppConfigDao.c().m();
    }

    public void cleanAllFailList() {
        H5AppConfigDao.c().e();
    }

    public boolean hasSetConfig() {
        return H5AppConfigDao.c().i();
    }

    public void deleteAppInstall(String appId) {
        H5AppInstallDao.c().b(appId);
    }

    public void setRpcIsLimit(boolean isLimit) {
        this.e = Boolean.valueOf(isLimit);
        H5AppConfigDao.c().b(isLimit ? LIMIT_CODE : 0);
    }

    public boolean rpcIsLimit() {
        if (this.e == null) {
            this.e = Boolean.valueOf(H5AppConfigDao.c().l() == LIMIT_CODE);
        }
        return this.e.booleanValue();
    }

    public void updateCurrentAppUpdateTime(String appId, String version) {
        H5NebulaAppDao.c().j(appId, version);
    }

    public Map<String, AppInfo> getAllHighestAppInfo() {
        return H5NebulaAppDao.c().f();
    }

    public void clearUpdateTime(String appId) {
        H5NebulaAppDao.c().c(appId);
    }

    public void createOrUpdateConfig(H5AppConfigBean saveBean) {
        this.d = saveBean.getApp_pool_limit();
        this.c = saveBean.getNormalReqRate();
        this.f = saveBean.getExtra();
        this.b = saveBean.getLimitReqRate();
        H5AppConfigDao.c().a(saveBean);
    }

    public String getConfigExtra() {
        if (this.f == null) {
            this.f = H5AppConfigDao.c().j();
        }
        return this.f;
    }

    public void createOrUpdateExtra(String extra) {
        this.f = extra;
        H5AppConfigDao.c().a(extra);
    }
}
