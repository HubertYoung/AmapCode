package com.alipay.mobile.nebulacore.appcenter.parse;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.api.H5ParseResult;
import com.alipay.mobile.nebulacore.appcenter.center.H5AppCenter;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class H5PackagePreloader {
    private static LinkedList<PackagePreloadThread> a;

    private static class PackagePreloadThread extends Thread {
        boolean a;
        String b;
        String c;
        H5ParseResult d;
        ConcurrentHashMap<String, byte[]> e;

        PackagePreloadThread(String appId) {
            super(appId);
            this.b = appId;
        }

        public void run() {
            super.run();
            try {
                preload(this.b);
            } catch (Throwable thr) {
                this.a = false;
                H5Log.e("H5PackagePreloader", "preload package error!", thr);
            }
        }

        public void preload(String appId) {
            boolean z = true;
            H5Log.e((String) "H5PackagePreloader", "do preload enter appId = " + appId);
            H5AppProvider appProvider = (H5AppProvider) H5ProviderManagerImpl.getInstance().getProvider(H5AppProvider.class.getName());
            if (appProvider != null && appProvider != null) {
                Bundle params = new Bundle();
                this.c = appProvider.getVersion(appId);
                if (appProvider.isAvailable(appId, this.c)) {
                    params.putString("appVersion", this.c);
                    params.putString("appId", appId);
                    String installPath = appProvider.getInstallPath(appId, this.c);
                    if (!TextUtils.isEmpty(installPath)) {
                        String offlineHost = "file://" + installPath;
                        if (!offlineHost.endsWith("/")) {
                            offlineHost = offlineHost + "/";
                        }
                        params.putString(H5Param.OFFLINE_HOST, offlineHost);
                    }
                    String host = appProvider.getExtra(appId, this.c).get("host");
                    if (!TextUtils.isEmpty(host)) {
                        params.putString("host", host);
                        H5AppCenter.initAppHost(host, params);
                    }
                    boolean isNebulaApp = false;
                    if (appProvider != null && appProvider.isNebulaApp(appId)) {
                        isNebulaApp = true;
                    }
                    params.putBoolean(H5Param.IS_NEBULA_APP, isNebulaApp);
                    params.putInt("appType", 1);
                    params.putBoolean("fromPreload", true);
                    ConcurrentHashMap content = new ConcurrentHashMap();
                    this.d = H5PackageParser.parsePackage(params, content);
                    H5Log.d("H5PackagePreloader", "parse appId " + appId + " errorCode " + this.d.code);
                    if (this.d.code != 0) {
                        z = false;
                    }
                    this.a = z;
                    if (this.a) {
                        this.e = content;
                    }
                }
            }
        }
    }

    public static void preloadPackage() {
        String[] split;
        try {
            if (ActivityHelper.isBackgroundRunning()) {
                H5Log.d("H5PackagePreloader", "preloadPackage enter");
                String recentApps = LauncherApplicationAgent.getInstance().getApplicationContext().getSharedPreferences("LITE_PROCESS_0", 0).getString("recent_tiny_apps", null);
                if (!TextUtils.isEmpty(recentApps)) {
                    if (a == null) {
                        a = new LinkedList<>();
                    }
                    for (String id : recentApps.split(";")) {
                        if (!TextUtils.isEmpty(id)) {
                            PackagePreloadThread thread = new PackagePreloadThread(id);
                            a.add(thread);
                            thread.setPriority(3);
                            thread.start();
                            H5Log.d("H5PackagePreloader", "preloadPackage id = " + id);
                        }
                    }
                }
            }
        } catch (Throwable thr) {
            H5Log.e("H5PackagePreloader", "preloadPackage error!", thr);
        }
    }

    public static ConcurrentHashMap<String, byte[]> getParsedPackageContent(Bundle params) {
        try {
            if (a == null || a.size() == 0) {
                return null;
            }
            String appId = H5Utils.getString(params, (String) "appId");
            String version = H5Utils.getString(params, (String) "appVersion");
            Iterator it = a.iterator();
            while (it.hasNext()) {
                PackagePreloadThread thread = (PackagePreloadThread) it.next();
                if (thread.b.equals(appId) && thread.c.equals(version)) {
                    if (thread.isAlive()) {
                        a(false);
                        return null;
                    } else if (thread.a) {
                        a = null;
                        a(true);
                        return thread.e;
                    }
                }
            }
            a(false);
            return null;
        } catch (Throwable thr) {
            H5Log.e("H5PackagePreloader", "getParsedPackageContent error!", thr);
            a(false);
            return null;
        }
    }

    private static void a(boolean isPreloadOnTarget) {
        try {
            Method logMethod = Class.forName("com.alipay.mobile.liteprocess.perf.PerformanceLogger").getDeclaredMethod("onPackagePrelodResult", new Class[]{Boolean.TYPE, Boolean.TYPE});
            logMethod.setAccessible(true);
            logMethod.invoke(null, new Object[]{Boolean.valueOf(true), Boolean.valueOf(isPreloadOnTarget)});
        } catch (Throwable th) {
        }
    }
}
