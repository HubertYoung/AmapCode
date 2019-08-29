package com.alipay.mobile.nebulaappcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebulaappcenter.dbdao.H5UrlMapDao;
import com.alipay.mobile.nebulaappcenter.service.NebulaAppCenterService;
import java.io.InputStream;
import java.util.List;

public class H5AppCenterServiceImpl extends H5AppCenterService {
    private NebulaAppCenterService a = new NebulaAppCenterService();

    public void onCreate(Bundle bundle) {
        this.a.a();
    }

    public void loadPresetAppList(InputStream inputStream) {
        this.a.a(inputStream);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        NebulaAppCenterService.b();
    }

    public H5AppDBService getAppDBService() {
        return NebulaAppCenterService.c();
    }

    @Nullable
    public String findUrlMappedAppId(String url) {
        return H5UrlMapDao.c().a(url);
    }

    public H5BaseApp getH5App() {
        return NebulaAppCenterService.d();
    }

    public String getSDKVersion() {
        return NebulaAppCenterService.e();
    }

    public void loadPresetApp(List<H5PreSetPkgInfo> list) {
        NebulaAppCenterService.a(list);
    }

    public void loadPresetAppNow(List<H5PreSetPkgInfo> list, H5LoadPresetListen h5LoadPresetListen) {
        NebulaAppCenterService.a(list, h5LoadPresetListen);
    }

    public void clearAppAmrPackage() {
        this.a.f();
    }

    public void clearAppUnzipPackage(long expiredTime) {
        NebulaAppCenterService.a(expiredTime);
    }

    public void setUpInfo(AppRes appRes, boolean forceSync) {
        this.a.a(appRes, forceSync);
    }

    public void setUpInfo(AppRes appRes, boolean forceSync, boolean downLoadAmr) {
        this.a.a(appRes, forceSync, downLoadAmr);
    }

    public void setUpInfo(AppRes appRes, boolean forceSync, boolean downLoadAmr, boolean downloadRandom) {
        this.a.a(appRes, forceSync, downLoadAmr, downloadRandom);
    }

    public void setUpInfo(AppRes appRes, boolean forceSync, boolean downLoadAmr, boolean downloadRandom, String scene) {
        this.a.a(appRes, forceSync, downLoadAmr, downloadRandom, scene);
    }

    public synchronized void setUpInfo(AppInfo appInfo, boolean forceSync) {
        this.a.a(appInfo, forceSync);
    }

    public void setUpInfo(AppInfo appInfo, boolean forceSync, boolean downLoadAmr) {
        this.a.a(appInfo, forceSync, downLoadAmr);
    }

    public void setUpInfo(AppInfo appInfo, boolean forceSync, boolean downLoadAmr, boolean downloadRandom) {
        this.a.a(appInfo, forceSync, downLoadAmr, downloadRandom);
    }
}
