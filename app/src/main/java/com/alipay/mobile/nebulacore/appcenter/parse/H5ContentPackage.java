package com.alipay.mobile.nebulacore.appcenter.parse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5AppUrlMapProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.tiny.H5TinyFallBackData;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.H5ResContentList;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.H5ParseResult;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalDegradePkg;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabObserver.H5SessionTabListener;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class H5ContentPackage extends ConcurrentHashMap<String, byte[]> {
    /* access modifiers changed from: private */
    public String a = "H5ContentPackage";
    private ConditionVariable b;
    private CountDownLatch c;
    public String currentUseVersion;
    /* access modifiers changed from: private */
    public Bundle d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;
    private int h = 0;
    /* access modifiers changed from: private */
    public H5AppProvider i;
    public boolean isResPackage;
    private boolean j = false;
    /* access modifiers changed from: private */
    public String k;
    private String l;
    private BroadcastReceiver m = null;
    private boolean n = false;
    private boolean o;
    /* access modifiers changed from: private */
    public boolean p = false;
    /* access modifiers changed from: private */
    public boolean q;
    private H5AppUrlMapProvider r;

    private class InstallCallback implements H5AppInstallCallback {
        private InstallCallback() {
        }

        /* synthetic */ InstallCallback(H5ContentPackage x0, byte b) {
            this();
        }

        public void onResult(boolean success, boolean isPatch) {
            H5Log.d(H5ContentPackage.this.a, "install result: " + success + " isPatch: " + isPatch);
            if (H5ContentPackage.this.p) {
                H5Log.d(H5ContentPackage.this.a, "already released!");
            } else {
                H5ContentPackage.this.b((String) null);
            }
        }
    }

    private abstract class ParseRunnable implements Runnable {
        String f;

        ParseRunnable(String sid) {
            this.f = sid;
        }
    }

    public H5ContentPackage(Bundle params, boolean isResPackage2) {
        this.f = H5Utils.getString(params, (String) "appId");
        this.d = params;
        this.i = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
        this.isResPackage = isResPackage2;
        this.o = c(this.f);
        this.r = (H5AppUrlMapProvider) Nebula.getProviderManager().getProvider(H5AppUrlMapProvider.class.getName());
    }

    public void setPreload(boolean preload) {
        this.q = preload;
    }

    public void prepareContent(boolean needLock) {
        if (this.i == null) {
            H5Log.e(this.a, (String) "failed to get app provider!");
            return;
        }
        if (needLock) {
            H5Log.e(this.a, (String) "prepareContent with lock!");
            this.b = new ConditionVariable();
        }
        this.e = H5Utils.getString(this.d, (String) "appVersion");
        if (TextUtils.isEmpty(this.e)) {
            if (!this.isResPackage || !c()) {
                this.e = this.i.getVersion(this.f);
            } else {
                this.e = a(this.f);
            }
            if (!Nebula.DEBUG && H5AppUtil.isTinyResAppId(this.f)) {
                String presetVersion = H5AppUtil.getTinyResPresetVersion();
                if (presetVersion != null && H5AppUtil.compareVersion(this.e, presetVersion) < 0 && H5AppUtil.isTinyResPresetForceCheck()) {
                    this.e = presetVersion;
                }
            }
        }
        refreshLogTag(this.f, this.e);
        boolean versionChanged = !TextUtils.isEmpty(this.currentUseVersion) && !TextUtils.isEmpty(this.e) && !this.e.equalsIgnoreCase(this.currentUseVersion);
        boolean installed = this.i.isInstalled(this.f, this.e);
        H5Log.d(this.a, "prepareContent appId:" + this.f + " appVersion " + this.e + " canDegrade:" + this.n + " installed:" + installed + " currentUseVersion:" + this.currentUseVersion);
        if (versionChanged || isEmpty()) {
            H5ContentPackage h5ContentPackage = H5GlobalDegradePkg.getInstance().getH5ContentPackage(this.f);
            if (h5ContentPackage != null) {
                H5Log.d(this.a, "H5GlobalDegradePkg contain " + this.f + Token.SEPARATOR + h5ContentPackage.currentUseVersion);
                if (!TextUtils.isEmpty(this.e) && TextUtils.equals(this.e, h5ContentPackage.currentUseVersion)) {
                    H5Log.d(this.a, "H5GlobalDegradePkg contain contain this version not parse");
                    return;
                }
            }
            if (!installed) {
                final boolean isAvailable = this.i.isAvailable(this.f, this.e);
                H5Log.d(this.a, "handle not installed. amr available: " + isAvailable);
                a(false, this.d, (String) null);
                d();
                if (!this.i.isNebulaApp(this.f)) {
                    e();
                    this.k = "isNotNebulaApp";
                } else if (H5AppUtil.isTinyResAppId(this.f) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_use_preset_tinyCommonRes"))) {
                    H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                        public void run() {
                            H5ContentPackage.this.a();
                        }
                    });
                } else if (c()) {
                    H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                        public void run() {
                            H5ContentPackage.this.a(isAvailable);
                        }
                    });
                } else {
                    b(isAvailable);
                }
            } else {
                a(false, this.i.getInstallPath(this.f, this.e), false);
            }
        } else {
            H5Log.d(this.a, "!versionChanged return");
        }
    }

    /* access modifiers changed from: private */
    public void a(final boolean isAvailable) {
        if (this.c == null) {
            this.c = new CountDownLatch(1);
        }
        long degradeStart = System.currentTimeMillis();
        boolean haveDegradePkg = H5GlobalDegradePkg.getInstance().prepareContent(this.f);
        H5Log.d(this.a, "[prepareForDegrade] haveDegradePkg: " + haveDegradePkg);
        a(degradeStart);
        if (haveDegradePkg) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    H5ContentPackage.this.b(isAvailable);
                }
            });
        } else {
            b(isAvailable);
        }
    }

    public void refreshLogTag(String appId, String appVersion) {
        if (this.isResPackage) {
            this.a = "H5ContentPackage_res_";
        } else {
            this.a = "H5ContentPackage_";
        }
        this.a += appId + "_" + appVersion;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.c == null) {
            this.c = new CountDownLatch(1);
        }
        long time = System.currentTimeMillis();
        H5GlobalDegradePkg.getInstance().prepareContent(this.f);
        a(time);
        H5Log.d(this.a, "66666692 tinyResApp prepareContent cost " + (System.currentTimeMillis() - time));
        if (this.q) {
            if (H5Utils.isInTinyProcess()) {
                b();
            }
            String processName = H5Utils.getProcessName();
            if (processName != null && processName.contains("lite1")) {
                H5Log.d(this.a, "66666692 tinyResApp in preLoadLite1 not prepare");
                return;
            }
        }
        H5Utils.getScheduledExecutor().schedule(new Runnable() {
            public void run() {
                H5Log.d(H5ContentPackage.this.a, "66666692 tinyResApp prepare preload:" + H5ContentPackage.this.q);
                H5AppUtil.prepare(H5ContentPackage.this.f, H5ContentPackage.this.e, null);
            }
        }, 5, TimeUnit.SECONDS);
    }

    /* access modifiers changed from: private */
    public void b() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
        Intent intent = new Intent();
        H5Log.d(this.a, "sendPreLoadAppx " + this.f + " , appVersion is " + this.e);
        intent.setAction(H5Param.APPX_PRELOAD_SUCCESS);
        manager.sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void b(boolean isAvailable) {
        long time = System.currentTimeMillis();
        if (this.isResPackage) {
            H5AppUtil.prepare(this.f, this.e, new InstallCallback(this, 0));
        } else if (isAvailable) {
            a(false, this.i.getInstallPath(this.f, this.e), true);
        } else {
            this.k = "notDownload_" + H5NetworkUtil.getInstance().getNetworkString();
            AppInfo appInfo = this.i.getAppInfo(this.f, this.e);
            this.l = appInfo != null ? appInfo.unAvailableReason : "7";
            H5AppUtil.prepare(this.f, this.e, new InstallCallback(this, 0));
        }
        H5Log.d(this.a, "prepareNotInstalledApp cost " + (System.currentTimeMillis() - time));
    }

    public void setCanDegrade(boolean degrade) {
        this.n = degrade;
    }

    private boolean c() {
        if (!this.n) {
            return false;
        }
        return NebulaUtil.enableResDegrade();
    }

    private static String a(String appId) {
        H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
        if (h5AppDBService != null) {
            return h5AppDBService.getHighestAppVersion(appId);
        }
        return "";
    }

    private void d() {
        if (!this.isResPackage && H5Utils.getBoolean(this.d, (String) "isTinyApp", false)) {
            byte[] apiByte = H5TinyFallBackData.removeApiPermission(this.f, this.e);
            byte[] configByte = H5TinyFallBackData.removeAppConfigByte(this.f, this.e);
            if (!(apiByte == null || Nebula.getH5TinyAppService() == null)) {
                Nebula.getH5TinyAppService().put(this.f, apiByte);
            }
            if (configByte != null) {
                H5StartParamManager.getInstance().put(this.f, configByte);
            }
        }
    }

    public void releaseContent() {
        H5Log.d(this.a, "releaseContent appId " + this.f + " version " + this.e);
        this.p = true;
        f();
        clear();
        h();
    }

    private void e() {
        if (this.i == null) {
            return;
        }
        if (this.h >= 3) {
            H5Log.w(this.a, "abort retry to download app.");
            return;
        }
        this.h++;
        H5Log.w(this.a, "downloadContentForAppCenter " + this.f);
        g();
        this.i.downloadApp(this.f, this.e);
    }

    /* access modifiers changed from: private */
    public void b(String hotVersion) {
        if (this.i == null) {
            H5Log.e(this.a, (String) "failed to get app provider!");
            return;
        }
        H5Log.d(this.a, "hotUpdateApp hotVersion: " + hotVersion + " appVersion:" + this.e);
        if ((hotVersion == null || this.isResPackage || TextUtils.equals(hotVersion, this.e)) && this.i.isInstalled(this.f, this.e)) {
            a(true, this.i.getInstallPath(this.f, this.e), false);
        }
    }

    private void a(boolean hotUpdate, String installedPath, boolean needInstall) {
        String sessionId = H5Utils.getString(this.d, (String) "sessionId");
        H5Log.d(this.a, "parseContent appId:" + this.f + " sessionId: " + sessionId + " installPath:" + installedPath);
        if (this.i == null) {
            H5Log.e(this.a, (String) "failed to get app provider!");
            return;
        }
        String offlineHost = null;
        try {
            if (!TextUtils.isEmpty(installedPath)) {
                offlineHost = "file://" + installedPath;
                if (!offlineHost.endsWith("/")) {
                    offlineHost = offlineHost + "/";
                }
            }
            if (!TextUtils.isEmpty(offlineHost)) {
                Uri uri = H5UrlHelper.parseUrl(offlineHost);
                if (uri != null) {
                    this.g = uri.getPath() + "/" + this.f + ".tar";
                }
                if (this.d.containsKey(H5Param.OFFLINE_HOST)) {
                    this.d.remove(H5Param.OFFLINE_HOST);
                }
                this.d.putString(H5Param.OFFLINE_HOST, offlineHost);
            }
            final long startTime = System.currentTimeMillis();
            if (this.b == null && !hotUpdate && this.c == null) {
                this.c = new CountDownLatch(1);
            }
            final Bundle copyBundle = Nebula.copyBundle(this.d);
            final boolean z = needInstall;
            final boolean z2 = hotUpdate;
            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new ParseRunnable(sessionId) {
                public void run() {
                    H5ParseResult errorCode;
                    boolean parseSucceed = true;
                    if (z) {
                        long installTime = System.currentTimeMillis();
                        H5ContentPackage.this.i.installApp(H5ContentPackage.this.f, H5ContentPackage.this.e, true);
                        H5Log.d(H5ContentPackage.this.a, "installApp spend allCoast " + (System.currentTimeMillis() - installTime));
                    }
                    H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "parsePackage.start");
                    ConcurrentHashMap content = null;
                    if (H5Utils.isInTinyProcess()) {
                        content = H5PackagePreloader.getParsedPackageContent(copyBundle);
                    }
                    if (content != null) {
                        errorCode = new H5ParseResult();
                        errorCode.code = 0;
                        H5Log.d(H5ContentPackage.this.a, "load h5 package from preloaded package cache");
                    } else {
                        content = new ConcurrentHashMap();
                        errorCode = H5PackageParser.parsePackage(copyBundle, content);
                    }
                    H5Log.d(H5ContentPackage.this.a, "parse appId " + H5ContentPackage.this.f + " errorCode " + errorCode.code);
                    H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "parsePackage.end");
                    if (errorCode.code != 0) {
                        parseSucceed = false;
                    }
                    if (parseSucceed) {
                        H5ContentPackage.this.clear();
                        H5ContentPackage.this.putAll(content);
                        H5ContentPackage.this.currentUseVersion = H5ContentPackage.this.e;
                        H5ContentPackage.this.a(startTime);
                        if (H5AppUtil.isTinyResAppId(H5ContentPackage.this.f) && H5Utils.isInTinyProcess()) {
                            H5ContentPackage.this.b();
                        }
                    } else {
                        H5ContentPackage.this.k = "verifyFail_" + errorCode.msg;
                        H5ContentPackage.this.a(startTime);
                        String errMsg = errorCode.msg;
                        if (z) {
                            errMsg = errMsg + "_patchFail";
                            H5ContentPackage.this.k = H5ContentPackage.this.k + "_patchFail";
                        }
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_VERIFY").param2().add("errorMsg", errMsg).param3().add(H5Param.IS_NEBULA_APP, Boolean.valueOf(H5ContentPackage.this.i.isNebulaApp(H5ContentPackage.this.f))).param4().add("appId", H5ContentPackage.this.f).add("version", H5ContentPackage.this.e).add("step", UploadDataResult.FAIL_MSG).add("errorCode", Integer.valueOf(errorCode.code)));
                    }
                    H5Log.d(H5ContentPackage.this.a, "parseContent inner sessionid " + this.f);
                    H5ContentPackage.this.a(parseSucceed, H5ContentPackage.this.d, this.f);
                    if (parseSucceed && z2 && TextUtils.equals(H5PageLoader.mainUrl, H5Utils.getString(H5ContentPackage.this.d, (String) "url"))) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_SESSION_UPDATEPACKAGE_SUCCESS").param1().add("diagnose", null).param2().add("appId", H5ContentPackage.this.f).add("version", H5ContentPackage.this.e).add(H5Param.PUBLIC_ID, H5Utils.getString(H5ContentPackage.this.d, (String) H5Param.PUBLIC_ID)).add("url", H5Utils.getString(H5ContentPackage.this.d, (String) "url")).param4().add("appId", H5ContentPackage.this.f).add("version", H5ContentPackage.this.e));
                    }
                    if (errorCode.code == 6) {
                        String url = H5Utils.getString(H5ContentPackage.this.d, (String) "url");
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_SESSION_VERIFYTAR_FAIL").param1().add("diagnose", null).param2().add("appId", H5ContentPackage.this.f).add("version", H5ContentPackage.this.e).add(H5Param.PUBLIC_ID, H5Utils.getString(H5ContentPackage.this.d, (String) H5Param.PUBLIC_ID)).add("url", url).add("errorCode", errorCode).param3().add("tarPath", H5ContentPackage.this.g).param4().add("appId", H5ContentPackage.this.f).add("version", H5ContentPackage.this.e));
                        Map map = new HashMap();
                        map.put("url", url);
                        map.put("tarPath", H5ContentPackage.this.g);
                        H5Logger.mtBizReport(H5Logger.MTBIZ_H5, "H5_VERIFYTAR_FAILED", "1", map);
                    }
                    H5Trace.event("h5PageReady", null, new String[0]);
                }
            });
            if (this.b != null) {
                this.b.close();
                this.b.block(5000);
                H5Log.d(this.a, "prepareContent block " + (System.currentTimeMillis() - startTime) + " ms");
            }
        } catch (Exception e2) {
            H5Log.e(this.a, (Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public void a(long startTime) {
        if (this.c != null) {
            try {
                this.c.countDown();
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
            }
            this.c = null;
            H5Log.d(this.a, "parseLatch block " + (System.currentTimeMillis() - startTime));
        }
        if (this.b != null) {
            this.b.open();
            this.b = null;
            H5Log.d(this.a, "conditionVariable block " + (System.currentTimeMillis() - startTime));
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(boolean isReady, Bundle params, String sid) {
        String sessionId;
        if (!this.j) {
            this.j = true;
            String tabDataStr = null;
            H5Log.d(this.a, "##tabbar## handleSessionTabData isH5GlobalPackage " + this.isResPackage + ", isReady " + isReady);
            if (!this.isResPackage) {
                if (TextUtils.isEmpty(sid)) {
                    sessionId = H5Utils.getString(params, (String) "sessionId");
                } else {
                    sessionId = sid;
                }
                H5Session h5Session = Nebula.getService().getSession(sessionId);
                if ((h5Session instanceof H5SessionImpl) && ((H5SessionImpl) h5Session).getH5SessionTabObserver() != null) {
                    if (!isReady) {
                        String cdnHost = H5Utils.getString(params, (String) H5Param.CDN_HOST);
                        if (TextUtils.isEmpty(cdnHost)) {
                            H5Log.w(this.a, "cdn url empty!");
                        }
                        if (cdnHost != null && !cdnHost.endsWith("/")) {
                            cdnHost = cdnHost + "/";
                        }
                        String finalUrl = cdnHost + H5PackageParser.ENTRY_TABBAR_JSON;
                        H5Log.d(this.a, "##tabbar## handleSessionTabData !isReady fallback final url " + finalUrl);
                        String tabDataStr2 = finalUrl;
                        H5Log.d(this.a, "##tabbar## handleSessionTabData !isReady tabDataStr " + tabDataStr2);
                        if (TextUtils.isEmpty(tabDataStr2)) {
                            tabDataStr2 = "stupid";
                        }
                        H5SessionTabListener listener = ((H5SessionImpl) h5Session).getH5SessionTabObserver().setData(tabDataStr2);
                        H5Log.d(this.a, "##tabbar## handleSessionTabData !isReady listener " + listener);
                        if (listener != null) {
                            H5Log.d(this.a, "##tabbar## handleSessionTabData !isReady onDataParsed");
                            listener.onDataParsed(tabDataStr2);
                        }
                    } else {
                        byte[] tabbarJSON = H5TabbarUtils.getTabDataByAppId(this.f);
                        if (tabbarJSON != null) {
                            tabDataStr = new String(tabbarJSON);
                        }
                        H5Log.d(this.a, "##tabbar## handleSessionTabData isReady tabBarData " + tabDataStr);
                        if (TextUtils.isEmpty(tabDataStr)) {
                            tabDataStr = "stupid";
                        }
                        H5SessionTabListener listener2 = ((H5SessionImpl) h5Session).getH5SessionTabObserver().setData(tabDataStr);
                        H5Log.d(this.a, "##tabbar## handleSessionTabData isReady listener " + listener2);
                        if (listener2 != null) {
                            H5Log.d(this.a, "##tabbar## handleSessionTabData isReady onDataParsed");
                            listener2.onDataParsed(tabDataStr);
                        }
                    }
                }
            }
        }
    }

    private void f() {
        if (this.c != null) {
            try {
                this.c.countDown();
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
            }
            this.c = null;
        }
    }

    public byte[] get(String pureUrl) {
        try {
            if (this.c != null && this.c.getCount() > 0) {
                long time = System.currentTimeMillis();
                H5Log.d(this.a, "begin wait for " + pureUrl);
                if (this.c != null) {
                    this.c.await(3, TimeUnit.SECONDS);
                }
                long waitTime = System.currentTimeMillis() - time;
                H5Log.d(this.a, "wait parse elapse " + waitTime);
                if (waitTime >= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                    this.k = "wait_appParse_timeout";
                    f();
                }
            }
            if (this.r != null) {
                pureUrl = this.r.mapUrl(pureUrl, this.f, this.e);
            }
            byte[] content = (byte[]) super.get(pureUrl);
            if (content == null && pureUrl.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_match_httpRes"))) {
                content = (byte[]) super.get(pureUrl.replace(AjxHttpLoader.DOMAIN_HTTP, AjxHttpLoader.DOMAIN_HTTPS));
                if (content != null) {
                    H5LogUtil.logNebulaTech(H5LogData.seedId("h5_match_httpRes").param1().add(this.f, null).param2().add(pureUrl, null));
                }
            }
            if (content != null) {
                H5Log.d(this.a, "package " + this.f + " target " + pureUrl);
                if (!H5ResContentList.enableResHttpCache() || !this.o) {
                    return content;
                }
                H5ResContentList.getInstance().add(pureUrl, content);
                return null;
            } else if (!TextUtils.isEmpty(this.k)) {
                return content;
            } else {
                this.k = "notMatch";
                return content;
            }
        } catch (Throwable e2) {
            H5Log.e(this.a, "latch exception:" + e2);
            this.k = e2.toString();
            f();
            return null;
        }
    }

    private boolean c(String appId) {
        if (TextUtils.isEmpty(appId) || this.i == null) {
            return false;
        }
        return this.i.isResourceApp(appId);
    }

    public String getAppId() {
        return this.f;
    }

    public Bundle getParams() {
        return this.d;
    }

    public String getVersion() {
        return this.e;
    }

    public String getFallbackReason() {
        return this.k;
    }

    public String getUnAvailableReason() {
        return this.l;
    }

    private synchronized void g() {
        if (this.m == null) {
            this.m = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null) {
                        String receivedId = H5Utils.getString(intent.getExtras(), (String) "app_id");
                        String source = H5Utils.getString(intent.getExtras(), (String) "source");
                        final String installVersion = H5Utils.getString(intent.getExtras(), (String) "version");
                        H5Log.d(H5ContentPackage.this.a, "installReceiver receivedId:" + receivedId + " hotVersion：" + installVersion + " currentAppId:" + H5ContentPackage.this.f);
                        if (H5ContentPackage.this.f != null && H5ContentPackage.this.f.equals(receivedId)) {
                            H5Log.d(H5ContentPackage.this.a, "received installedReceiver " + H5ContentPackage.this.f + " to parseContent " + source);
                            if (H5ContentPackage.this.h()) {
                                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                    public void run() {
                                        H5ContentPackage.this.b(installVersion);
                                    }
                                });
                            }
                        }
                    }
                }
            };
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.alipay.mobile.android.h5app.installstatus");
            H5Log.d(this.a, "registerInstallReceiver ：appId:" + this.f + " version:" + this.e);
            manager.registerReceiver(this.m, filter);
        }
    }

    /* access modifiers changed from: private */
    public synchronized boolean h() {
        boolean z;
        if (this.m == null) {
            z = false;
        } else {
            H5Log.d(this.a, this.f + " unregisterInstallReceiver");
            LocalBroadcastManager.getInstance(H5Utils.getContext()).unregisterReceiver(this.m);
            this.m = null;
            z = true;
        }
        return z;
    }
}
