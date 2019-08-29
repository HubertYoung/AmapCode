package com.alipay.mobile.nebulacore.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5MainLinkMonitor;
import com.alipay.mobile.nebula.provider.H5BizProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.startParam.H5AppStartParam;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.H5ResContentList;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalDegradePkg;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.data.H5ParamHolder;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.H5AnimatorUtil;
import com.alipay.mobile.nebulacore.util.H5IntentUtil;
import com.alipay.mobile.nebulacore.util.H5TimeUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class H5Application extends ActivityApplication {
    public static final String TAG = "H5Application";
    private static int a = 0;
    private static AtomicInteger b = new AtomicInteger(0);
    public static boolean sNebulaReady = false;
    private String c;
    private Bundle d;
    private long e;
    private boolean f;
    private Bundle g;
    private Bundle h;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        b.getAndAdd(1);
        H5Utils.handleTinyAppKeyEvent(new String[]{"main", "uc_init", "package_prepare"}, (String) "H5Application.onCreate()");
        this.e = System.currentTimeMillis();
        if (!H5Flag.ucReady) {
            H5PageData.setInitScenario(this.e, 1);
        } else if (!sNebulaReady) {
            H5PageData.setInitScenario(this.e, 2);
        } else {
            H5PageData.setInitScenario(this.e, 3);
        }
        sNebulaReady = true;
        if (bundle != null && bundle.containsKey(Constants.SERVICE_SOURCE_ID)) {
            setSourceId(bundle.getString(Constants.SERVICE_SOURCE_ID));
        }
        e(bundle);
        d(bundle);
        this.c = getAppId();
        H5Log.d(TAG, "onCreate " + this.c + " @" + hashCode() + " param " + bundle);
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.d = bundle;
        this.d.putString("appId", this.c);
        if (H5Utils.isInTinyProcess()) {
            H5PageData.createAppTime = this.e;
            H5Log.d(TAG, "h5EventHandlerService " + ((H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName())));
        }
        a();
        this.h = Nebula.copyBundle(this.d);
        H5MainLinkMonitor.startMainLinkMonitor(this.c);
    }

    private static boolean a(Bundle bundle) {
        return H5Utils.getBoolean(bundle, (String) H5Param.LONG_PACKAGE_LOADING_SHOWN, false);
    }

    private void a() {
        Nebula.getService().getBugMeManager().setUp();
        boolean openBugme = "true".equals(this.d.getString("enableBugme"));
        boolean bugmeSwitchOpen = H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DEBUG_SWITCH, false);
        if (openBugme && !bugmeSwitchOpen) {
            b();
        }
    }

    private void b() {
        H5Log.d(TAG, "openBugMeByStartParam");
        this.f = true;
        String domainWhiteListStr = this.d.getString(H5TinyAppDebugMode.KEY_WHITE_LIST);
        if (!TextUtils.isEmpty(domainWhiteListStr)) {
            String[] split = domainWhiteListStr.split("\\|");
            List domainWhiteList = new ArrayList();
            for (String encodedDomain : split) {
                if (!TextUtils.isEmpty(encodedDomain)) {
                    String domain = H5UrlHelper.decode(encodedDomain);
                    domainWhiteList.add(domain);
                    H5Log.d(TAG, "Got domain whitelist: " + domain);
                }
            }
            Nebula.getH5BugMeManager().setDomainWhiteList((String[]) domainWhiteList.toArray(new String[0]));
        }
        H5DevConfig.debugSwitch(true, false, true, false, false);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        b.getAndAdd(-1);
        H5Log.d(TAG, "onDestroy " + this.c + " @" + hashCode() + " param " + bundle);
        H5MainLinkMonitor.cancelLinkMonitor(this.c);
        if (Nebula.getH5TinyAppService() != null) {
            Nebula.getH5TinyAppService().clear(this.c);
        }
        c();
        H5BugmeLogCollector.flushFile();
        if (this.f) {
            Nebula.getService().getBugMeManager().release();
        }
        H5StartParamManager.getInstance().clear(this.c);
        if (b.get() > 0) {
            H5GlobalPackage.clearResourcePackages(this.c);
        } else {
            H5GlobalPackage.clearAllResourcePackages();
        }
        H5GlobalDegradePkg.getInstance().clear(this.c);
        H5ResContentList.getInstance().clear();
        if (!TextUtils.equals("YES", H5Utils.getString(this.d, (String) H5Param.MULTI_APP_TAG))) {
            H5TabbarUtils.clearTabDataByAppId(this.c);
        }
        H5PreferAppList.getInstance().clearProcessCache(this.c);
        H5Flag.isUploadLog = true;
        d();
    }

    private void c() {
        if (this.d != null && this.d.containsKey(H5Param.AUTH_CODE_KEY)) {
            H5BizProvider bizProvider = (H5BizProvider) H5Utils.getProvider(H5BizProvider.class.getName());
            if (bizProvider != null) {
                bizProvider.triggerBizCallback(H5Utils.getString(this.d, (String) H5Param.AUTH_CODE_KEY));
            }
        }
    }

    private void d() {
        if (!H5Utils.isInTinyProcess() || H5Utils.isTinyMiniService(this.d)) {
            Nebula.clearServiceWork(this.d);
            return;
        }
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            h5EventHandlerService.stopSelfProcess();
        } else {
            H5Log.d(TAG, "onDestroy h5EventHandlerService == null");
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        try {
            H5Log.d(TAG, "onStart " + this.c + " @" + hashCode() + " sourceId:" + getSourceId() + " mSceneId:" + getSceneId());
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        H5AppUtil.secAppId = getSourceId();
        H5Environment.setContext(getMicroApplicationContext().getApplicationContext());
        c(this.d);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        H5Log.d(TAG, "onStop " + this.c + " @" + hashCode());
    }

    public boolean canRestart(Bundle params) {
        boolean multiApp = b(params);
        if (!multiApp) {
            this.g = params;
        }
        H5Log.d(TAG, "canRestart " + this.c + " @" + hashCode() + ", enableMultiApp " + multiApp);
        if (multiApp) {
            params.putString(H5Param.MULTI_APP_TAG, "YES");
        }
        return !multiApp;
    }

    public Bundle takeLastRestartParam() {
        Bundle tmp = this.g;
        this.g = null;
        return tmp;
    }

    private boolean b(Bundle startMultAppParam) {
        boolean multiApp = false;
        String startMultiApp = H5Utils.getString(startMultAppParam, (String) "startMultApp");
        if (!TextUtils.isEmpty(startMultiApp) && "YES".equalsIgnoreCase(startMultiApp)) {
            multiApp = true;
        }
        if (H5Utils.getBoolean(startMultAppParam, (String) "startMultApp", false)) {
            multiApp = true;
        }
        if (!multiApp || !H5Utils.getBoolean(this.d, (String) "isTinyApp", false) || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_tiny_multiApp"))) {
            return multiApp;
        }
        if (H5Utils.isDebug()) {
            Toast.makeText(H5Utils.getContext(), "小程序不支持MutliApp(只在测试包里弹)", 1).show();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        if (bundle != null) {
            String url = H5Utils.getString(bundle, (String) H5Param.URL);
            if (TextUtils.isEmpty(url)) {
                url = H5Utils.getString(bundle, (String) "url");
            }
            this.c = getAppId();
            bundle.putString("appId", this.c);
            H5Log.d(TAG, "onRestart " + this.c + " @" + hashCode() + " param " + bundle);
            String sessionId = this.d != null ? this.d.getString("sessionId") : null;
            H5Session session = null;
            if (!TextUtils.isEmpty(sessionId)) {
                session = Nebula.getService().getSession(sessionId, false);
            }
            if (session == null) {
                H5Log.d(TAG, "onRestart session not found by sessionId: " + sessionId);
                session = Nebula.getService().getTopSession();
            }
            String resumeParams = H5Utils.toJSONObject(bundle).toJSONString();
            if (b(bundle)) {
                H5Log.d(TAG, "startMultiApp");
                c(bundle);
                return;
            }
            if (!TextUtils.isEmpty(url) || session == null) {
                H5Log.w(TAG, "onRestart start page " + url);
                String canDestroyParam = H5Utils.getString(bundle, (String) H5Param.CAN_DESTROY);
                if (TextUtils.isEmpty(canDestroyParam)) {
                    canDestroyParam = H5Utils.getString(bundle, (String) H5Param.LONG_CAN_DESTROY);
                }
                if (TextUtils.isEmpty(canDestroyParam) && H5Utils.getBoolean(bundle, (String) H5Param.LONG_CAN_DESTROY, true)) {
                    canDestroyParam = "YES";
                }
                if ("YES".equalsIgnoreCase(canDestroyParam)) {
                    H5Log.d(TAG, "OnRestart -> destroy same app");
                    destroy(null);
                    if (bundle != null && bundle.containsKey(H5AppHandler.CHECK_KEY) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_delete_CHECK_KEY"))) {
                        bundle.remove(H5AppHandler.CHECK_KEY);
                    }
                    getMicroApplicationContext().startApp("20000067", this.c, bundle, getSceneParams(), null);
                } else {
                    c(bundle);
                    H5Log.d(TAG, "OnRestart -> startPage");
                }
            } else {
                H5Log.w(TAG, "onRestart set resumeParams " + resumeParams);
                session.getData().set(H5Param.H5_SESSION_RESUME_PARAM, resumeParams);
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_ReStart_Without_URL").param4().add(this.c, null));
                String value = H5Environment.getConfig("h5_optionConfig");
                if (!TextUtils.isEmpty(value)) {
                    JSONArray jsonArray = H5Utils.getJSONArray(H5Utils.parseObject(value), "reStartAppIdList", null);
                    if (jsonArray != null && !jsonArray.isEmpty() && jsonArray.contains(this.c)) {
                        Bundle copy = Nebula.copyBundle(bundle);
                        copy.putString("startMultApp", "YES");
                        copy.remove(H5AppHandler.CHECK_KEY);
                        getMicroApplicationContext().startApp(this.c, this.c, copy, getSceneParams(), null);
                        return;
                    }
                }
            }
            H5Log.d(TAG, "h5_app_restart appId={" + getAppId() + "} params={" + bundle.toString() + h.d);
        }
    }

    private void c(Bundle bundle) {
        String url = H5Utils.getString(bundle, (String) "url");
        if (TextUtils.isEmpty(url)) {
            url = H5Utils.getString(bundle, (String) H5Param.URL);
        }
        H5Trace.event("startPage", null, "appId", this.c);
        H5Log.d(TAG, "startPage " + this.c + Token.SEPARATOR + url);
        if (H5AppUtil.isH5ContainerAppId(this.c) && !TextUtils.isEmpty(url)) {
            String matchAppId = H5AppUtil.matchAppId(url);
            H5Log.d(TAG, "matchAppId:" + matchAppId);
            if (!TextUtils.isEmpty(matchAppId) && !H5AppUtil.isH5ContainerAppId(matchAppId)) {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(this.c, matchAppId, this.d, getSceneParams(), null);
                destroy(null);
                return;
            }
        }
        if (Nebula.enableOpenScheme(url, bundle)) {
            H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink");
            destroy(null);
            return;
        }
        if (H5Utils.isStripLandingURLEnable(url, "startAppNormal")) {
            String realUrl = H5Utils.getStripLandingURL(url);
            if (!TextUtils.equals(url, realUrl)) {
                H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
                if (h5EnvProvider != null) {
                    boolean result = h5EnvProvider.goToSchemeService(realUrl, bundle);
                    H5Utils.landingMonitor(url, realUrl, true, "startAppNormal", this.c, H5Utils.getString(bundle, (String) H5Param.PUBLIC_ID), H5Utils.getString(bundle, (String) H5Param.LONG_BIZ_SCENARIO));
                    if (result) {
                        H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink in landing");
                        destroy(null);
                        return;
                    }
                }
            }
        }
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service == null) {
            H5Log.e((String) TAG, (String) "failed to get h5 service!");
            destroy(null);
            return;
        }
        StringBuilder append = new StringBuilder().append(this.c).append("_");
        int i = a;
        a = i + 1;
        String asyncIndex = append.append(i).toString();
        H5ParamHolder.addPageParam(asyncIndex);
        Nebula.commonParamParse(bundle);
        bundle.putString(H5Param.FROM_TYPE, H5PageData.FROM_TYPE_START_APP);
        Context context = getMicroApplicationContext().getApplicationContext();
        WalletContext h5Context = new WalletContext(null);
        h5Context.setMicroApplication(this);
        Nebula.initSession(this.c, bundle, h5Context);
        String sessionId = H5Utils.getString(bundle, (String) "sessionId");
        H5AppStartParam.getInstance().put(sessionId, this.h);
        Intent intent = Nebula.commonStartActivity(context, bundle);
        if (intent == null) {
            destroy(null);
            return;
        }
        intent.putExtra(H5Param.ASYNCINDEX, asyncIndex);
        bundle.putString(H5Param.ASYNCINDEX, asyncIndex);
        a(bundle, h5Service);
        if (H5Utils.isInTinyProcess()) {
            H5IntentUtil.putParam(this.c, intent.getExtras());
        }
        getMicroApplicationContext().startActivity((MicroApplication) this, intent);
        if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_CLOSE_ALL_ACTIVITY__ANIMATION, false) && !"noAnimation".equalsIgnoreCase(H5Utils.getString(bundle, (String) "startAnimation", (String) ""))) {
            if (NebulaUtil.isShowTransAnimate(bundle)) {
                H5AnimatorUtil.setActivityFadingStart();
            } else if (!a(this.d) || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_newloadpage"))) {
                H5AnimatorUtil.setActivityStart(h5Context, bundle);
            } else {
                H5AnimatorUtil.setActivityNoAnimStart();
            }
        }
    }

    private void a(Bundle bundle, H5Service h5Service) {
        H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "H5Application.asyncStartPage()");
        H5Log.d(TAG, "asyncStartPage " + this.c);
        H5Bundle h5Bundle = new H5Bundle(bundle);
        h5Service.startPage(this, h5Bundle);
        H5Log.d(TAG, "h5_app_start appId={" + getAppId() + "} params={" + h5Bundle.toString() + h.d);
        H5TimeUtil.timeLog(H5TimeUtil.START_APP, this.e);
    }

    private static void d(Bundle params) {
        String fromExternal = "inner";
        if (params != null) {
            boolean isStartFromExternal = false;
            try {
                if (params.containsKey(MicroApplication.KEY_APP_START_FROM_EXTERNAL)) {
                    Object obj = params.get(MicroApplication.KEY_APP_START_FROM_EXTERNAL);
                    if (obj instanceof Boolean) {
                        isStartFromExternal = ((Boolean) obj).booleanValue();
                    } else if (obj instanceof String) {
                        isStartFromExternal = Boolean.parseBoolean((String) obj);
                    }
                }
                if (isStartFromExternal) {
                    fromExternal = "outer";
                } else {
                    fromExternal = "inner";
                }
            } catch (Throwable throwable) {
                H5Log.d(TAG, "throwable is " + throwable);
            }
            params.putString("app_startup_type", fromExternal);
        }
    }

    private void e(Bundle params) {
        Bundle sceneParam = getSceneParams();
        if (sceneParam != null && sceneParam.containsKey(H5Param.ORIGIN_FROM_EXTERNAL)) {
            params.putBoolean(H5Param.ORIGIN_FROM_EXTERNAL, H5Utils.getBoolean(sceneParam, (String) H5Param.ORIGIN_FROM_EXTERNAL, false));
        }
        if (sceneParam != null && sceneParam.containsKey(H5Param.SCENEPARAMS_SHARETOKEN)) {
            params.putString(H5Param.SCENEPARAMS_SHARETOKEN, H5Utils.getString(sceneParam, (String) H5Param.SCENEPARAMS_SHARETOKEN, (String) ""));
        }
    }
}
