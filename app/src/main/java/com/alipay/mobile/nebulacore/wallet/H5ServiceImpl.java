package com.alipay.mobile.nebulacore.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Listener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageReadyListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SsoFlagHolder;
import com.alipay.mobile.h5container.api.H5UcReadyCallBack;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.log.H5MainLinkMonitor;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewConfig;
import com.alipay.mobile.nebula.provider.H5AppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5ServiceWorkerPushProvider;
import com.alipay.mobile.nebula.provider.TinyWebWorkerPushProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.TestDataUtils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.H5UcInitTask;
import com.alipay.mobile.nebulacore.appcenter.H5IApplicationInstallerImpl;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalDegradePkg;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.config.H5PluginConfigManager;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.embedview.H5EmbededViewConfigManager;
import com.alipay.mobile.nebulacore.embedview.H5NewEmbedViewConfigManager;
import com.alipay.mobile.nebulacore.manager.H5PluginManagerImpl;
import com.alipay.mobile.nebulacore.plugin.H5ActionSheetPlugin;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5HttpPlugin;
import com.alipay.mobile.nebulacore.plugin.H5LoadingPlugin;
import com.alipay.mobile.nebulacore.plugin.H5NotifyPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SystemPlugin;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.web.H5HardwarePolicy;
import com.alipay.mobileappconfig.core.model.hybirdPB.PackInfoReq;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class H5ServiceImpl extends H5Service {
    public static final String TAG = "H5ServiceImpl";
    public IApplicationInstaller iApplicationInstaller;

    class WalletListener implements H5Listener {
        private Bundle b;

        public WalletListener(Bundle bundle) {
            this.b = bundle;
        }

        public void onSessionCreated(H5Session session) {
            if (session instanceof H5SessionImpl) {
                ((H5SessionImpl) session).setParams(this.b);
            }
            String appId = H5Utils.getString(this.b, (String) "appId");
            H5Log.d(H5ServiceImpl.TAG, "onSessionCreated " + appId);
            H5MainLinkMonitor.triggerSessionCreateLink(session, appId);
        }

        public void onSessionDestroyed(H5Session session) {
            H5SsoFlagHolder.setFlag("laiwangDomains", true);
        }

        public void onPageCreated(H5Page page) {
        }

        public void onPageDestroyed(H5Page page) {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        H5Log.d(TAG, "onCreate");
        Nebula.getService().setH5TaskScheduleProvider(new H5TaskScheduleProviderImpl());
        Nebula.getService().onCreate(LauncherApplicationAgent.getInstance().getApplicationContext());
        getMicroApplicationContext().registerApplicationEngine("H5App", new H5Engine());
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        H5Log.d(TAG, "onDestroy");
    }

    public void setSharedData(String key, String value) {
        H5Data h5DataProvider = Nebula.getService().getData();
        if (h5DataProvider != null) {
            h5DataProvider.set(key, value);
        }
    }

    public String getSharedData(String key) {
        H5Data h5DataProvider = Nebula.getService().getData();
        if (h5DataProvider != null) {
            return h5DataProvider.get(key);
        }
        return null;
    }

    public void removeSharedData(String key) {
        H5Data h5DataProvider = Nebula.getService().getData();
        if (h5DataProvider != null) {
            h5DataProvider.remove(key);
        }
    }

    public boolean sendEvent(H5Event event) {
        return Nebula.getService().sendEvent(event);
    }

    public void setWebDriverHelper(H5WebDriverHelper helper) {
        Nebula.getService().setWebDriverHelper(helper);
    }

    public H5WebDriverHelper getWebDriverHelper() {
        return Nebula.getService().getWebDriverHelper();
    }

    public void startWebActivity(MicroApplication app, Bundle param) {
        startWebActivity(app, param, null);
    }

    public void startWebActivity(MicroApplication app, Bundle param, List<Object> arg3) {
        H5Bundle bundle = new H5Bundle();
        if (param == null) {
            param = new Bundle();
        }
        bundle.setParams(param);
        startPage(app, bundle);
    }

    private static void a() {
        if (H5PageData.sCreateTimestamp < 0) {
            long time = System.currentTimeMillis();
            if (!H5Flag.ucReady) {
                H5PageData.setInitScenario(time, 1);
            } else if (!H5Application.sNebulaReady) {
                H5PageData.setInitScenario(time, 2);
            } else {
                H5PageData.setInitScenario(time, 3);
            }
            H5Application.sNebulaReady = true;
        }
    }

    public void startPage(MicroApplication app, H5Bundle bundle) {
        if (Nebula.DEBUG) {
            TestDataUtils.storeJSParams("pageLoad|startPagePoint", Long.valueOf(System.currentTimeMillis()));
        }
        a();
        if (bundle == null) {
            H5Log.w(TAG, "invalid start page parameters!");
            return;
        }
        Bundle params = bundle.getParams();
        if (params == null) {
            params = new Bundle();
            bundle.setParams(params);
        }
        params.remove(H5Param.CREATEPAGESENCE);
        String url = H5Utils.getString(params, (String) "url");
        if (TextUtils.isEmpty(url)) {
            url = H5Utils.getString(params, (String) H5Param.URL);
        }
        if (Nebula.enableOpenScheme(url, params)) {
            H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink");
            return;
        }
        if (H5Utils.isStripLandingURLEnable(url, "startAppNormal")) {
            String realUrl = H5Utils.getStripLandingURL(url);
            if (!TextUtils.equals(url, realUrl)) {
                H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
                if (h5EnvProvider != null) {
                    boolean result = h5EnvProvider.goToSchemeService(realUrl, params);
                    H5Utils.landingMonitor(url, realUrl, true, "startAppNormal", H5Utils.getString(params, (String) "appId"), H5Utils.getString(params, (String) H5Param.PUBLIC_ID), H5Utils.getString(params, (String) H5Param.LONG_BIZ_SCENARIO));
                    if (result) {
                        H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink in landing");
                        return;
                    }
                }
            }
        }
        String appId = null;
        if (app != null) {
            appId = app.getAppId();
        }
        H5Log.d(TAG, "startPage appId " + appId);
        try {
            H5Log.d(TAG, "in H5ServiceImpl, startParams is " + params.toString());
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        bundle.addListener(new WalletListener(params));
        final WalletContext h5Context = new WalletContext(null);
        h5Context.setMicroApplication(app);
        H5Log.d(TAG, "startPage execute runnable");
        final H5Bundle h5Bundle = bundle;
        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
            public void run() {
                Nebula.getService().startPage(h5Context, h5Bundle);
            }
        });
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                h5EventHandlerService.preConnectSpdy();
            }
        }
        a(appId);
    }

    private static void a(String appId) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService != null) {
            h5TinyAppService.doPreloadJob(appId);
        }
    }

    public H5Page createPage(Activity activity, H5Bundle bundle) {
        H5Log.d(TAG, "createPage " + activity);
        if (!(activity == null || (activity instanceof H5Activity) || bundle == null || bundle.getParams() == null)) {
            bundle.getParams().remove(H5Param.CREATEPAGESENCE);
        }
        if (Nebula.DEBUG) {
            TestDataUtils.storeJSParams("pageLoad|createPagePoint", Long.valueOf(System.currentTimeMillis()));
        }
        a();
        WalletContext h5Context = new WalletContext(activity);
        return Nebula.getService().createPage(h5Context, a(h5Context, bundle));
    }

    public void createPageAsync(Activity activity, H5Bundle bundle, H5PageReadyListener h5PageReadyListener) {
        H5Log.d(TAG, "createPageAsync " + activity);
        if (!(activity == null || (activity instanceof H5Activity) || bundle == null || bundle.getParams() == null)) {
            bundle.getParams().remove(H5Param.CREATEPAGESENCE);
        }
        if (Nebula.DEBUG) {
            TestDataUtils.storeJSParams("pageLoad|createPageAsyncPoint", Long.valueOf(System.currentTimeMillis()));
        }
        a();
        WalletContext h5Context = new WalletContext(activity);
        Nebula.getService().createPageAsync(h5Context, a(h5Context, bundle), h5PageReadyListener);
    }

    private H5Bundle a(WalletContext h5Context, H5Bundle bundle) {
        if (bundle == null) {
            bundle = new H5Bundle();
        }
        if (bundle.getParams() == null) {
            bundle.setParams(new Bundle());
        }
        MicroApplication app = H5WalletWrapper.a(h5Context);
        h5Context.setMicroApplication(app);
        String appId = null;
        if (app != null) {
            appId = app.getAppId();
        }
        H5Log.d(TAG, "createPage appId " + appId);
        bundle.addListener(new WalletListener(bundle.getParams()));
        return bundle;
    }

    public H5PluginManager getPluginManager() {
        return Nebula.getService().getPluginManager();
    }

    public void addPluginConfig(H5PluginConfig config) {
        H5PluginConfigManager.getInstance().addConfig(config);
    }

    public void addH5PluginConfigList(List<H5PluginConfig> list) {
        H5PluginConfigManager.getInstance().addH5PluginConfigList(list);
    }

    public void addEmbedViewConfig(H5EmbedViewConfig h5EmbedViewConfig) {
        H5EmbededViewConfigManager.getInstance().addTypeConfig(h5EmbedViewConfig);
    }

    public void addEmbedViewConfig(List<H5EmbedViewConfig> list) {
        H5EmbededViewConfigManager.getInstance().addTypeConfigs(list);
    }

    public void addNewEmbedViewConfig(H5NewEmbedViewConfig config) {
        H5NewEmbedViewConfigManager.getInstance().addTypeConfig(config);
    }

    public void addNewEmbedViewConfig(List<H5NewEmbedViewConfig> configs) {
        H5NewEmbedViewConfigManager.getInstance().addTypeConfigs(configs);
    }

    public H5ProviderManager getProviderManager() {
        return Nebula.getProviderManager();
    }

    public boolean isAliDomain(String url) {
        return Nebula.getService().isAliDomain(url);
    }

    public boolean permitLocation(String url) {
        return Nebula.getService().permitLocation(url);
    }

    public void prepareApp(String appId, String version, H5UpdateAppCallback process) {
        H5AppUtil.updateApp(appId, process);
    }

    public void ucIsReady(H5UcReadyCallBack h5UcReadyCallBack) {
        Nebula.getService().ucIsReady(h5UcReadyCallBack);
    }

    public H5BugMeManager getBugMeManager() {
        return Nebula.getService().getBugMeManager();
    }

    public H5Page getH5PageByViewId(int viewId) {
        return Nebula.getService().getH5PageByViewId(viewId);
    }

    public H5Page getTopH5Page() {
        return Nebula.getService().getTopH5Page();
    }

    public H5Page getTopH5PageForTiny() {
        return Nebula.getService().getTopH5PageForTiny();
    }

    public Fragment getTopH5Fragment() {
        return Nebula.getService().getTopH5Fragment();
    }

    public H5BaseFragment getTopH5BaseFragment() {
        return Nebula.getService().getTopH5BaseFragment();
    }

    public H5BaseFragment getTopH5BaseFragmentByWorkerId(String workerId) {
        return Nebula.getService().getTopH5BaseFragmentByWorkerId(workerId);
    }

    public H5BaseFragment getTopH5BaseFragmentByViewId(int viewId) {
        return Nebula.getService().getTopH5BaseFragmentByViewId(viewId);
    }

    public H5Session getSessionByWorkerId(String workerId) {
        return Nebula.getService().getSessionByWorkerId(workerId);
    }

    public H5Session getTopSession() {
        return Nebula.getService().getTopSession();
    }

    public void prepareRNApp(String appId, H5UpdateAppCallback h5UpdateAppCallback) {
        Nebula.getService().prepareRNApp(appId, h5UpdateAppCallback);
    }

    public int parseRNPkg(String appId) {
        return Nebula.getService().parseRNPkg(appId);
    }

    public void initServicePlugin() {
        Nebula.getService().initServicePlugin();
    }

    public boolean sendEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        return Nebula.getDispatcher().dispatch(h5Event, h5BridgeContext) == Error.NONE;
    }

    public H5CoreNode getItsOwnNode() {
        return Nebula.getService();
    }

    public H5Plugin createPlugin(String s, H5PluginManager h5PluginManager) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5ConfigService configService = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        if (!(configService == null || h5ConfigProvider == null || "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_disableConfigServiceOpt")))) {
            configService.addExternalPlugins();
        }
        if ("page".equals(s)) {
            h5PluginManager.register((H5Plugin) new H5HttpPlugin());
            h5PluginManager.register((H5Plugin) new H5NotifyPlugin());
            h5PluginManager.register((H5Plugin) new H5LoggerPlugin());
        }
        return H5PluginConfigManager.getInstance().createPlugin(s, h5PluginManager);
    }

    public H5Plugin createPlugin(String s, H5Page h5Page, H5PluginManager h5PluginManager) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5ConfigService configService = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        if (!(configService == null || h5ConfigProvider == null || "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_disableConfigServiceOpt")))) {
            configService.addExternalPlugins();
        }
        if ("page".equals(s)) {
            h5PluginManager.register((H5Plugin) new H5HttpPlugin());
            h5PluginManager.register((H5Plugin) new H5SystemPlugin());
            h5PluginManager.register((H5Plugin) new H5NotifyPlugin());
            h5PluginManager.register((H5Plugin) new H5LoggerPlugin());
            h5PluginManager.register((H5Plugin) new H5AlertPlugin());
            h5PluginManager.register((H5Plugin) new H5LoadingPlugin(h5Page));
            h5PluginManager.register((H5Plugin) new H5ActionSheetPlugin());
        }
        return H5PluginConfigManager.getInstance().createPlugin(s, h5PluginManager);
    }

    public H5PluginManager createPluginManager(H5CoreNode h5CoreNode) {
        return new H5PluginManagerImpl(h5CoreNode);
    }

    public NebulaAppManager getNebulaAppManager() {
        return Nebula.getService().getNebulaAppManager();
    }

    public NebulaCommonManager getNebulaCommonManager() {
        return Nebula.getService().getNebulaCommonManager();
    }

    public Class[] getProcessH5Activity() {
        return Nebula.LITE_PROCESS_H5_ACTIVITY;
    }

    public Class[] getProcessH5TransActivity() {
        return Nebula.LITE_PROCESS_H5TRANS_ACTIVITY;
    }

    public void sendServiceWorkerPushMessage(HashMap<String, String> hashMap) {
        sendServiceWorkerPushMessage(hashMap, null);
    }

    public void sendServiceWorkerPushMessage(final HashMap<String, String> hashMap, final H5CallBack h5CallBack) {
        if (H5Flag.useSysWebView) {
            TinyWebWorkerPushProvider provider = (TinyWebWorkerPushProvider) getProviderManager().getProvider(TinyWebWorkerPushProvider.class.getName());
            if (provider != null) {
                provider.sendWebWorkerPushMessage(hashMap, h5CallBack);
            }
        } else if (!H5Flag.ucReady) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    UcService ucService = H5ServiceUtils.getUcService();
                    if (ucService == null) {
                        H5Log.e((String) H5ServiceImpl.TAG, (String) "ucService == null");
                        return;
                    }
                    if (Nebula.disableHWACByUCStyle()) {
                        ucService.init(!H5HardwarePolicy.disableHardwareAccelerate(null, null));
                    } else {
                        ucService.init(H5HardwarePolicy.isAbove14Level());
                    }
                    H5ServiceImpl.this.a(hashMap, h5CallBack);
                }
            });
        } else {
            a(hashMap, h5CallBack);
        }
    }

    /* access modifiers changed from: private */
    public void a(HashMap<String, String> hashMap, H5CallBack h5CallBack) {
        H5ServiceWorkerPushProvider provider = (H5ServiceWorkerPushProvider) getProviderManager().getProvider(H5ServiceWorkerPushProvider.class.getName());
        if (provider == null) {
            H5Log.w(TAG, "sendServiceWorkerPushMessage provider == null");
        } else if (h5CallBack == null) {
            provider.sendServiceWorkerPushMessage(hashMap);
        } else {
            provider.sendServiceWorkerPushMessage(hashMap, h5CallBack);
        }
    }

    public void clearServiceWorker(final String swHost) {
        try {
            if (!H5Flag.ucReady) {
                H5Log.w(TAG, "clearServiceWorker !H5Flag.ucReady");
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public void run() {
                        UcService ucService = H5ServiceUtils.getUcService();
                        if (ucService == null) {
                            H5Log.e((String) H5ServiceImpl.TAG, (String) "ucService == null");
                            return;
                        }
                        if (Nebula.disableHWACByUCStyle()) {
                            ucService.init(!H5HardwarePolicy.disableHardwareAccelerate(null, null));
                        } else {
                            ucService.init(H5HardwarePolicy.isAbove14Level());
                        }
                        H5ServiceImpl.this.b(swHost);
                    }
                });
                return;
            }
            b(swHost);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public void clearServiceWorkerSync(final String swHost, final H5CallBack h5CallBack) {
        try {
            if (!H5Flag.ucReady) {
                H5Log.w(TAG, "clearServiceWorkerSync !H5Flag.ucReady");
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public void run() {
                        UcService ucService = H5ServiceUtils.getUcService();
                        if (ucService == null) {
                            H5Log.e((String) H5ServiceImpl.TAG, (String) "ucService == null");
                            return;
                        }
                        if (Nebula.disableHWACByUCStyle()) {
                            ucService.init(!H5HardwarePolicy.disableHardwareAccelerate(null, null));
                        } else {
                            ucService.init(H5HardwarePolicy.isAbove14Level());
                        }
                        H5ServiceImpl.this.a(swHost, h5CallBack);
                    }
                });
                return;
            }
            a(swHost, h5CallBack);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    /* access modifiers changed from: private */
    public void b(String swHost) {
        H5ServiceWorkerPushProvider provider = (H5ServiceWorkerPushProvider) getProviderManager().getProvider(H5ServiceWorkerPushProvider.class.getName());
        if (provider != null) {
            provider.clearServiceWorker(swHost);
        } else {
            H5Log.w(TAG, "clearWork provider == null");
        }
    }

    /* access modifiers changed from: private */
    public void a(String swHost, H5CallBack h5CallBack) {
        H5ServiceWorkerPushProvider provider = (H5ServiceWorkerPushProvider) getProviderManager().getProvider(H5ServiceWorkerPushProvider.class.getName());
        if (provider != null) {
            provider.clearServiceWorkerSync(swHost, h5CallBack);
        } else {
            H5Log.w(TAG, "clearWorkSync provider == null");
        }
    }

    public Stack<H5Session> getSessions() {
        return Nebula.getService().getSessions();
    }

    public void preLoadInTinyProcess() {
    }

    public void sendToWebFromMainProcess(final String appId, final String action, final JSONObject jsonObject) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                H5AppProvider h5AppProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
                if (h5AppProvider != null) {
                    boolean openMulti = h5AppProvider.isXiaoChengXu(appId);
                    H5Log.d(H5ServiceImpl.TAG, "appId " + appId + " action:" + action + " jsonObject:" + jsonObject + " openMulti " + openMulti);
                    if (!openMulti) {
                        H5Page h5Page = H5ServiceImpl.this.getTopH5PageForTiny();
                        if (h5Page != null) {
                            H5Bridge h5Bridge = h5Page.getBridge();
                            if (h5Bridge != null) {
                                h5Bridge.sendToWeb(action, jsonObject, null);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                    if (h5EventHandlerService != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("func", action);
                        if (jsonObject != null) {
                            bundle.putString("param", jsonObject.toString());
                        }
                        h5EventHandlerService.sendDataToTinyProcess(appId, bundle);
                    }
                }
            }
        });
    }

    public void startPageFromActivity(final Activity activity, final H5Bundle h5Bundle) {
        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
            public void run() {
                Nebula.getService().startPage(new H5Context(activity), h5Bundle);
            }
        });
    }

    public void savePackJson(String json) {
        H5Log.d(TAG, "savePackJson " + json);
        if (Nebula.DEBUG && H5Utils.isMain()) {
            throw new RuntimeException("not invoke on ui thread!!!");
        } else if (!TextUtils.isEmpty(json)) {
            H5AppBizRpcProvider h5AppBizRpcProvider = (H5AppBizRpcProvider) Nebula.getProviderManager().getProvider(H5AppBizRpcProvider.class.getName());
            if (h5AppBizRpcProvider != null) {
                AppRes appRes = h5AppBizRpcProvider.handlerPKgJson(json);
                H5AppCenterService h5AppCenterService = H5ServiceUtils.getAppCenterService();
                if (h5AppCenterService != null) {
                    h5AppCenterService.setUpInfo(appRes, true, false);
                }
            }
        }
    }

    public PackInfoReq generatePackInfoReq(List<String> list) {
        if (!Nebula.DEBUG || !H5Utils.isMain()) {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            AppReq appReq = new AppReq();
            if (h5AppProvider != null) {
                appReq.stableRpc = "NO";
                appReq = h5AppProvider.makeAppReq(H5UpdateAppParam.newBuilder().setForceRpc(true).setAppReq(appReq).build());
            }
            if (appReq == null) {
                H5Log.e((String) TAG, (String) "appReq == null ");
                appReq = new AppReq();
            }
            JSONObject queryJson = new JSONObject();
            for (String appId : list) {
                JSONObject entryJson = new JSONObject();
                entryJson.put((String) "app_id", (Object) appId);
                String walletVersion = "";
                if (h5AppProvider != null) {
                    walletVersion = h5AppProvider.getWalletConfigNebulaVersion(appId);
                }
                entryJson.put((String) "version", (Object) walletVersion);
                queryJson.put(appId, (Object) entryJson);
            }
            if (!queryJson.isEmpty()) {
                appReq.query = queryJson.toJSONString();
            }
            H5Log.d(TAG, "appReq.query : " + appReq.query);
            return H5AppUtil.getPkgReqFromAppReq(appReq);
        }
        throw new RuntimeException("not invoke on ui thread!!!");
    }

    public IApplicationInstaller H5IApplicationInstaller() {
        if (this.iApplicationInstaller == null) {
            this.iApplicationInstaller = new H5IApplicationInstallerImpl();
        }
        return this.iApplicationInstaller;
    }

    public byte[] getH5GlobalDegradePkg(String s) {
        byte[] bytes = H5GlobalDegradePkg.getInstance().getContent(s);
        return bytes != null ? bytes : H5GlobalPackage.getContent(s, false);
    }

    public Runnable getUcInitTask() {
        return new H5UcInitTask(true, new Bundle());
    }
}
