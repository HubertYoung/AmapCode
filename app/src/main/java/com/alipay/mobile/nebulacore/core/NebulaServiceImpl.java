package com.alipay.mobile.nebulacore.core;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Listener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageReadyListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5UcReadyCallBack;
import com.alipay.mobile.h5container.api.H5ViewCache;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5CacheProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5NebulaDebugProvider;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5TaskScheduleProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.H5UcInitReceiver;
import com.alipay.mobile.nebulacore.api.H5UcInitTask;
import com.alipay.mobile.nebulacore.api.NebulaService;
import com.alipay.mobile.nebulacore.appcenter.center.H5AppCenter;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalDegradePkg;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParserRn;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackagePreloader;
import com.alipay.mobile.nebulacore.biz.H5BizPlugin;
import com.alipay.mobile.nebulacore.config.H5PluginConfigManager;
import com.alipay.mobile.nebulacore.data.H5ParamHolder;
import com.alipay.mobile.nebulacore.dev.provider.H5BugMeSwitchPlugin;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.env.H5WebViewChoose;
import com.alipay.mobile.nebulacore.manager.H5NebulaAppManager;
import com.alipay.mobile.nebulacore.manager.H5NebulaCommonManager;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.plugin.H5ChannelPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ClipboardPlugin;
import com.alipay.mobile.nebulacore.plugin.H5CookiePlugin;
import com.alipay.mobile.nebulacore.plugin.H5DefaultPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ErrorPagePlugin;
import com.alipay.mobile.nebulacore.plugin.H5NetworkAnalysisPlugin;
import com.alipay.mobile.nebulacore.plugin.H5NetworkPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SecurePlugin;
import com.alipay.mobile.nebulacore.plugin.H5ShareDataPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SystemPlugin;
import com.alipay.mobile.nebulacore.pushbiz.H5PushBizPlugin;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.util.H5AnimatorUtil;
import com.alipay.mobile.nebulacore.web.H5BridgePolicy;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.mobile.worker.H5WorkerPlugin;
import com.alipay.sdk.util.h;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class NebulaServiceImpl extends H5CoreTarget implements NebulaService {
    public static final String TAG = "H5NebulaService";
    private Stack<H5Session> b = new Stack<>();
    private Map<String, List<H5Listener>> c = new ConcurrentHashMap();
    private boolean d = false;
    private H5WebDriverHelper e;
    private H5TaskScheduleProvider f;
    private H5UcInitReceiver g = null;
    private H5UcInitReceiver h = null;
    private BroadcastReceiver i = null;
    private NebulaAppManager j;
    private NebulaCommonManager k;
    private boolean l;

    public NebulaServiceImpl() {
        H5Log.d(TAG, "init nebula service");
    }

    private void a() {
        H5Log.d(TAG, "initPlugins");
        this.d = true;
        long time = System.currentTimeMillis();
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5ConfigService configService = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        if (!(configService == null || h5ConfigProvider == null || "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_disableConfigServiceOpt")))) {
            configService.addExternalPlugins();
        }
        H5PluginManager pluginManager = getPluginManager();
        pluginManager.register((H5Plugin) new H5ShareDataPlugin());
        pluginManager.register((H5Plugin) new H5NetworkPlugin());
        pluginManager.register((H5Plugin) new H5SystemPlugin());
        pluginManager.register((H5Plugin) new H5SecurePlugin());
        pluginManager.register((H5Plugin) new H5CookiePlugin());
        pluginManager.register((H5Plugin) new H5ClipboardPlugin());
        pluginManager.register((H5Plugin) new H5DefaultPlugin());
        pluginManager.register((H5Plugin) new H5NetworkAnalysisPlugin());
        pluginManager.register((H5Plugin) new H5ChannelPlugin());
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService != null && h5TinyAppService.isWebWorkerSupported()) {
            pluginManager.register((H5Plugin) new H5WorkerPlugin());
        }
        pluginManager.register((H5Plugin) new H5BugMeSwitchPlugin());
        pluginManager.register((H5Plugin) new H5PushBizPlugin());
        pluginManager.register((H5Plugin) new H5BizPlugin());
        pluginManager.register((H5Plugin) new H5ErrorPagePlugin());
        H5Plugin extServicePlugin = H5PluginConfigManager.getInstance().createPlugin("service", pluginManager);
        if (extServicePlugin != null) {
            pluginManager.register(extServicePlugin);
        }
        H5Log.d(TAG, "initPlugins delta " + (System.currentTimeMillis() - time));
    }

    public void onRelease() {
        super.onRelease();
    }

    public H5Page createPage(H5Context h5Context, H5Bundle h5Bundle) {
        long timeMillis = System.currentTimeMillis();
        if (h5Bundle == null) {
            h5Bundle = new H5Bundle();
        }
        if (h5Bundle.getParams() == null) {
            h5Bundle.setParams(new Bundle());
        }
        if (h5Context == null || h5Context.getContext() == null) {
            H5Log.e((String) TAG, (String) "invalid h5 context!");
            return null;
        } else if (!(h5Context.getContext() instanceof Activity)) {
            H5Log.e((String) TAG, (String) "not activity context!");
            return null;
        } else {
            String appId = H5Utils.getString(h5Bundle.getParams(), (String) "appId");
            if (!TextUtils.isEmpty(appId)) {
                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (h5AppProvider != null) {
                    h5AppProvider.getAppFromServerWhenAppIsEmpty(appId);
                }
            }
            Bundle bundle = a(h5Context, h5Bundle, true);
            H5Page page = new H5PageImpl((Activity) h5Context.getContext(), bundle, null);
            if (Nebula.DEBUG) {
                H5Log.d(TAG, "h5_app_start createPage appId={" + H5Utils.getString(bundle, (String) "appId") + "} params={" + bundle.toString() + h.d);
            }
            page.getWebView().getView().setBackgroundColor(H5Utils.getInt(page.getParams(), (String) "backgroundColor"));
            H5Log.d(TAG, "createPage cost " + (System.currentTimeMillis() - timeMillis));
            return page;
        }
    }

    public void createPageAsync(H5Context h5Context, H5Bundle h5Bundle, H5PageReadyListener h5PageReadyListener) {
        if (h5Bundle == null) {
            h5Bundle = new H5Bundle();
        }
        if (h5Bundle.getParams() == null) {
            h5Bundle.setParams(new Bundle());
        }
        if (h5Context == null || h5Context.getContext() == null) {
            H5Log.e((String) TAG, (String) "invalid h5 context!");
        } else if (!(h5Context.getContext() instanceof Activity)) {
            H5Log.e((String) TAG, (String) "not activity context!");
        } else if (H5WebViewChoose.notNeedInitUc(h5Bundle.getParams())) {
            H5Log.d(TAG, "createPageAsync notNeedInitUc");
            if (h5PageReadyListener != null) {
                h5PageReadyListener.getH5Page(createPage(h5Context, h5Bundle));
            }
        } else if (this.g == null) {
            H5Log.d(TAG, "createPageAsync !notNeedInitUc init ucPageReadyReceiver");
            this.g = new H5UcInitReceiver(true);
            this.g.addH5Bundle(h5Bundle);
            this.g.addH5Context(h5Context);
            this.g.addH5PageReadyListener(h5PageReadyListener);
            IntentFilter filter = new IntentFilter();
            filter.addAction(H5Param.H5_ACTION_UC_INIT_FINISH);
            LocalBroadcastManager.getInstance(H5Environment.getContext()).registerReceiver(this.g, filter);
            try {
                fireUrgentUcInit(h5Bundle.getParams());
            } catch (Exception e2) {
                H5Log.e((String) TAG, "Urgent uc init:" + e2);
            }
        } else {
            H5Log.d(TAG, "createPageAsync !notNeedInitUc add xxx");
            this.g.addH5Bundle(h5Bundle);
            this.g.addH5Context(h5Context);
            this.g.addH5PageReadyListener(h5PageReadyListener);
        }
    }

    public void ucIsReady(H5UcReadyCallBack h5UcReadyCallBack) {
        if (H5WebViewChoose.notNeedInitUc(null)) {
            H5Log.d(TAG, "ucIsReady notNeedInitUc");
            if (h5UcReadyCallBack != null) {
                h5UcReadyCallBack.usIsReady(true);
            }
        } else if (this.h == null) {
            H5Log.d(TAG, "ucIsReady !notNeedInitUc init ucIsReadyReceiver");
            this.h = new H5UcInitReceiver(false);
            this.h.addH5UcReadyCallBack(h5UcReadyCallBack);
            IntentFilter filter = new IntentFilter();
            filter.addAction(H5Param.H5_ACTION_UC_INIT_FINISH);
            LocalBroadcastManager.getInstance(H5Environment.getContext()).registerReceiver(this.h, filter);
            try {
                fireUrgentUcInit(null);
            } catch (Exception e2) {
                H5Log.e((String) TAG, " ucIsReady Urgent uc init:" + e2);
                h5UcReadyCallBack.usIsReady(false);
            }
        } else {
            H5Log.d(TAG, "ucIsReady !notNeedInitUc add xxx");
            this.h.addH5UcReadyCallBack(h5UcReadyCallBack);
        }
    }

    public H5BugMeManager getBugMeManager() {
        return Nebula.getH5BugMeManager();
    }

    public H5Page getH5PageByViewId(int viewId) {
        if (this.b != null) {
            synchronized (this.b) {
                Iterator it = this.b.iterator();
                while (it.hasNext()) {
                    H5Session session = (H5Session) it.next();
                    if (session != null) {
                        Stack pageStack = session.getPages();
                        if (pageStack != null) {
                            Iterator it2 = pageStack.iterator();
                            while (it2.hasNext()) {
                                H5Page page = (H5Page) it2.next();
                                if (page != null && page.getWebViewId() == viewId) {
                                    return page;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return null;
    }

    public H5Page getTopH5Page() {
        H5Session h5Session = getTopSession();
        if (h5Session != null) {
            return h5Session.getTopPage();
        }
        H5Log.d(TAG, "getTopH5Page h5Session == null");
        return null;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.h5container.api.H5Page getTopH5PageForTiny() {
        /*
            r10 = this;
            r6 = 0
            java.util.Stack r4 = r10.getSessions()     // Catch:{ Throwable -> 0x004d }
            if (r4 != 0) goto L_0x0009
            r0 = r6
        L_0x0008:
            return r0
        L_0x0009:
            monitor-enter(r10)     // Catch:{ Throwable -> 0x004d }
            int r7 = r4.size()     // Catch:{ all -> 0x004a }
            int r2 = r7 + -1
        L_0x0010:
            if (r2 < 0) goto L_0x005f
            java.lang.Object r3 = r4.get(r2)     // Catch:{ all -> 0x004a }
            com.alipay.mobile.h5container.api.H5Session r3 = (com.alipay.mobile.h5container.api.H5Session) r3     // Catch:{ all -> 0x004a }
            if (r3 == 0) goto L_0x005c
            java.lang.String r1 = r3.getId()     // Catch:{ all -> 0x004a }
            java.lang.String r7 = "H5NebulaService"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x004a }
            java.lang.String r9 = "sessionId:"
            r8.<init>(r9)     // Catch:{ all -> 0x004a }
            java.lang.StringBuilder r8 = r8.append(r1)     // Catch:{ all -> 0x004a }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x004a }
            com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ all -> 0x004a }
            boolean r7 = a(r1)     // Catch:{ all -> 0x004a }
            if (r7 != 0) goto L_0x005c
            com.alipay.mobile.h5container.api.H5Page r0 = r3.getTopPage()     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x005c
            android.os.Bundle r7 = r0.getParams()     // Catch:{ all -> 0x004a }
            boolean r7 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.isTinyWebView(r7)     // Catch:{ all -> 0x004a }
            if (r7 != 0) goto L_0x005c
            monitor-exit(r10)     // Catch:{ all -> 0x004a }
            goto L_0x0008
        L_0x004a:
            r7 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x004a }
            throw r7     // Catch:{ Throwable -> 0x004d }
        L_0x004d:
            r5 = move-exception
            java.lang.String r7 = "H5NebulaService"
            com.alipay.mobile.nebula.util.H5Log.e(r7, r5)
        L_0x0053:
            java.lang.String r7 = "H5NebulaService"
            java.lang.String r8 = "getTopH5PageForTiny page is null"
            com.alipay.mobile.nebula.util.H5Log.d(r7, r8)
            r0 = r6
            goto L_0x0008
        L_0x005c:
            int r2 = r2 + -1
            goto L_0x0010
        L_0x005f:
            monitor-exit(r10)     // Catch:{ all -> 0x004a }
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.core.NebulaServiceImpl.getTopH5PageForTiny():com.alipay.mobile.h5container.api.H5Page");
    }

    private static boolean a(String id) {
        return !TextUtils.isEmpty(id) && id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID);
    }

    public Fragment getTopH5Fragment() {
        return a(getTopH5Page());
    }

    private static Fragment a(H5Page h5Page) {
        if (h5Page == null || h5Page.getContext() == null || h5Page.getContext().getContext() == null) {
            return null;
        }
        Activity activity = (Activity) h5Page.getContext().getContext();
        if (activity instanceof H5Activity) {
            return ((H5Activity) activity).getCurrentFragment();
        }
        return null;
    }

    public H5BaseFragment getTopH5BaseFragment() {
        Fragment topFragment = getTopH5Fragment();
        if (topFragment == null || !(topFragment instanceof H5Fragment)) {
            return null;
        }
        return (H5BaseFragment) topFragment;
    }

    public H5BaseFragment getTopH5BaseFragmentByWorkerId(String workerId) {
        H5Session session = getSessionByWorkerId(workerId);
        if (session == null) {
            return null;
        }
        Fragment topFragment = a(session.getTopPage());
        if (topFragment == null || !(topFragment instanceof H5Fragment)) {
            return null;
        }
        return (H5BaseFragment) topFragment;
    }

    public H5BaseFragment getTopH5BaseFragmentByViewId(int viewId) {
        if (this.b != null) {
            synchronized (this.b) {
                Iterator it = this.b.iterator();
                while (it.hasNext()) {
                    H5Session session = (H5Session) it.next();
                    if (session != null) {
                        Stack pageStack = session.getPages();
                        if (pageStack != null) {
                            Iterator it2 = pageStack.iterator();
                            while (it2.hasNext()) {
                                H5Page page = (H5Page) it2.next();
                                if (page != null && page.getWebViewId() == viewId) {
                                    Fragment topFragment = a(page);
                                    if (topFragment != null && (topFragment instanceof H5Fragment)) {
                                        H5BaseFragment h5BaseFragment = (H5BaseFragment) topFragment;
                                        return h5BaseFragment;
                                    }
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void prepareRNApp(final String appId, final H5UpdateAppCallback h5UpdateAppCallback) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
            public void run() {
                Map appMap = new HashMap();
                H5AppProvider nebulaAppProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
                if (nebulaAppProvider == null) {
                    H5Log.e((String) NebulaServiceImpl.TAG, (String) "nebulaAppProvider==null");
                    return;
                }
                String walletConfigNebulaVersion = nebulaAppProvider.getWalletConfigNebulaVersion(appId);
                String version = nebulaAppProvider.getVersion(appId);
                if (!TextUtils.isEmpty(appId)) {
                    H5Log.d(NebulaServiceImpl.TAG, "prepareApp: send rpc appId:" + appId + " walletConfigNebulaVersion:" + walletConfigNebulaVersion + " version:" + version);
                    appMap.put(appId, walletConfigNebulaVersion);
                }
                nebulaAppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setAppMap(appMap).setUpdateCallback(h5UpdateAppCallback).build());
                H5AppUtil.prepare(appId, version, null);
            }
        });
    }

    public int parseRNPkg(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return 1;
        }
        return H5PackageParserRn.parseRNPackage(appId);
    }

    public void initServicePlugin() {
        if (!this.d) {
            a();
        }
    }

    public NebulaAppManager getNebulaAppManager() {
        if (this.j == null) {
            this.j = new H5NebulaAppManager();
        }
        return this.j;
    }

    public NebulaCommonManager getNebulaCommonManager() {
        if (this.k == null) {
            this.k = new H5NebulaCommonManager();
        }
        return this.k;
    }

    public void fireUrgentUcInit() {
        fireUrgentUcInit(null);
    }

    public boolean startPage(final H5Context h5Context, H5Bundle h5Bundle) {
        final Context context;
        if (h5Bundle == null) {
            H5Log.w(TAG, "invalid start page parameters!");
            return false;
        }
        if (h5Bundle.getParams() == null) {
            h5Bundle.setParams(new Bundle());
        }
        if (h5Context == null || h5Context.getContext() == null) {
            context = H5Environment.getContext();
        } else {
            context = h5Context.getContext();
        }
        Bundle params = h5Bundle.getParams();
        if (!TextUtils.isEmpty(H5Utils.getString(params, (String) "preRpc"))) {
            H5PreRpcProvider preRpcProvider = (H5PreRpcProvider) Nebula.getProviderManager().getProvider(H5PreRpcProvider.class.getName());
            if (preRpcProvider != null) {
                preRpcProvider.setStartParams(params);
                preRpcProvider.preRpc();
            }
        }
        final Bundle bundle = a(h5Context, h5Bundle, false);
        if (!a(bundle)) {
            H5Flag.isUploadLog = false;
        } else {
            H5Flag.isUploadLog = true;
        }
        String asyncIndex = H5Utils.getString(bundle, (String) H5Param.ASYNCINDEX);
        if (H5ParamHolder.hasPageParam(asyncIndex)) {
            H5ParamHolder.deliveryPageParam(asyncIndex, bundle);
        } else {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    Nebula.commonParamParse(bundle);
                    H5Environment.startActivity(h5Context, Nebula.commonStartActivity(context, bundle));
                    if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_CLOSE_ALL_ACTIVITY__ANIMATION, false) && !"noAnimation".equalsIgnoreCase(H5Utils.getString(bundle, (String) "startAnimation", (String) ""))) {
                        H5AnimatorUtil.setActivityStart(h5Context, bundle);
                    }
                }
            });
        }
        if (Nebula.DEBUG) {
            H5Log.d(TAG, "h5_app_start startPage appId={" + H5Utils.getString(bundle, (String) "appId") + "} params={" + h5Bundle.toString() + h.d);
        }
        return true;
    }

    private static boolean a(Bundle bundle) {
        if (bundle == null) {
            return true;
        }
        try {
            String enableStockSwitch = H5Environment.getConfigWithProcessCache("h5_enableStockLogSwitch");
            if (enableStockSwitch == null || !"YES".equalsIgnoreCase(enableStockSwitch) || !"NO".equalsIgnoreCase(H5Utils.getString(bundle, (String) "isStockTradeLog"))) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            H5Log.e((String) TAG, t);
            return true;
        }
    }

    private Bundle a(H5Context h5Context, H5Bundle h5Bundle, boolean createPage) {
        H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "NebulaServiceImpl.commonSet()");
        if (Nebula.DEBUG) {
            Nebula.h5_dev_uc = H5Utils.getConfigBoolean(H5Utils.getContext(), "h5_dev_uc");
        }
        try {
            fireUrgentUcInit(h5Bundle.getParams());
        } catch (Exception e2) {
            H5Log.e((String) TAG, "Urgent uc init:" + e2);
        }
        if (!this.d) {
            a();
        }
        if (!H5Utils.isInTinyProcess()) {
            H5PermissionManager permissionManager = (H5PermissionManager) H5Utils.getProvider(H5PermissionManager.class.getName());
            if (permissionManager != null) {
                permissionManager.setDefaultPermissionConfig();
            }
        }
        Bundle bundle = h5Bundle.getParams();
        String appId = H5Utils.getString(bundle, (String) "appId");
        if (!bundle.containsKey("sessionId")) {
            Nebula.initSession(appId, bundle, h5Context);
        }
        String sessionId = H5Utils.getString(bundle, (String) "sessionId");
        boolean hasCheck = H5AppHandler.hasCheckParam(bundle);
        H5Log.d(TAG, "appId " + appId + " hasCheck " + hasCheck);
        H5TabbarUtils.clearTabDataByAppId(appId);
        Nebula.isDSL = false;
        long startTime = System.currentTimeMillis();
        H5AppCenter.setupPage(bundle, hasCheck);
        H5Log.d(TAG, "startPage setupPage cost " + (System.currentTimeMillis() - startTime));
        H5Log.d(TAG, " session " + sessionId);
        List listeners = h5Bundle.getListeners();
        if (listeners != null && !listeners.isEmpty()) {
            this.c.put(sessionId, listeners);
        }
        H5AppUtil.currentPsd = !TextUtils.isEmpty(H5Utils.getString(bundle, (String) H5Param.OFFLINE_HOST)) ? BehavorReporter.PROVIDE_BY_LOCAL : "online";
        bundle.putBoolean("ifCreatePage", createPage);
        a(bundle, appId);
        return bundle;
    }

    private static void a(Bundle bundle, String appId) {
        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_SESSION_START").param1().add("diagnose", null).param2().add("appId", appId).add("version", H5Utils.getString(bundle, (String) "appVersion")).add(H5Param.PUBLIC_ID, H5Utils.getString(bundle, (String) H5Param.PUBLIC_ID)).add("url", H5Utils.getString(bundle, (String) "url")));
    }

    public boolean addSession(H5Session session) {
        if (session == null) {
            return false;
        }
        synchronized (this.b) {
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                if (((H5Session) it.next()).equals(session)) {
                    return false;
                }
            }
            session.setParent(this);
            this.b.add(session);
            return true;
        }
    }

    public H5Session getSession(String sessionId) {
        return getSession(sessionId, true);
    }

    public H5Session getSession(String sessionId, boolean allowCreate) {
        H5Session h5Session = null;
        synchronized (this.b) {
            Iterator it = this.b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                H5Session session = (H5Session) it.next();
                if (sessionId.equals(session.getId())) {
                    h5Session = session;
                    break;
                }
            }
        }
        if (allowCreate) {
            if (h5Session == null) {
                h5Session = new H5SessionImpl();
                h5Session.setId(sessionId);
                addSession(h5Session);
            }
            try {
                synchronized (this.c) {
                    if (this.c.containsKey(sessionId)) {
                        for (H5Listener l2 : this.c.remove(sessionId)) {
                            h5Session.addListener(l2);
                        }
                    }
                }
            } catch (Exception e2) {
                H5Log.e((String) TAG, (Throwable) e2);
            }
        }
        return h5Session;
    }

    public H5Session getSessionByWorkerId(String workerId) {
        if (this.b != null) {
            synchronized (this.b) {
                Iterator it = this.b.iterator();
                while (it.hasNext()) {
                    H5Session session = (H5Session) it.next();
                    if (session != null && TextUtils.equals(workerId, session.getServiceWorkerID())) {
                        return session;
                    }
                }
            }
        }
        return null;
    }

    public boolean removeSession(String sessionId) {
        boolean z = false;
        try {
            H5Log.d(TAG, "unregisterReceiver in removeSession");
            LocalBroadcastManager.getInstance(H5Environment.getContext()).unregisterReceiver(this.h);
            LocalBroadcastManager.getInstance(H5Environment.getContext()).unregisterReceiver(this.g);
            this.h = null;
            this.g = null;
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
        }
        if (!TextUtils.isEmpty(sessionId)) {
            synchronized (this.b) {
                Iterator iterator = this.b.iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    H5Session s = (H5Session) iterator.next();
                    if (sessionId.equals(s.getId())) {
                        this.c.remove(sessionId);
                        iterator.remove();
                        s.setParent(null);
                        s.onRelease();
                        z = true;
                        break;
                    }
                }
            }
        }
        return z;
    }

    public H5Session getTopSession() {
        H5Session peek;
        synchronized (this.b) {
            if (this.b.isEmpty()) {
                peek = null;
            } else {
                peek = this.b.peek();
            }
        }
        return peek;
    }

    public void onCreate(Context context) {
        long time = System.currentTimeMillis();
        H5Environment.setContext(context);
        H5NetworkUtil.getInstance().init(context);
        if (Nebula.DEBUG) {
            d();
        }
        b();
        H5Log.d(TAG, "onCreate delta " + (System.currentTimeMillis() - time));
    }

    private void b() {
        H5Log.d(TAG, "post init");
        if (Looper.myLooper() == null) {
            H5Log.d(TAG, "looper == null");
            Looper.prepare();
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                NebulaServiceImpl.this.c();
            }
        });
    }

    /* access modifiers changed from: private */
    public void c() {
        H5Log.d(TAG, "do init stuff");
        int time = 5;
        if (H5Utils.isInTinyProcess()) {
            time = 2;
        }
        H5Utils.getScheduledExecutor().schedule(new Runnable() {
            public void run() {
                H5NebulaAppConfigManager.initConfig();
                H5ThirdDisclaimerUtils.initConfig();
                try {
                    H5BridgePolicy.negotiate();
                } catch (Throwable throwable) {
                    H5Log.e((String) NebulaServiceImpl.TAG, throwable);
                }
                if (H5Utils.isInTinyProcess()) {
                    H5Log.d(NebulaServiceImpl.TAG, "preLoadInTinyProcess");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isTinyApp", true);
                    H5AppCenter.initTinyAppRes(bundle, true);
                    H5ViewCache.initViewCache();
                    H5PackagePreloader.preloadPackage();
                    H5PagePreloader.startPreload();
                    return;
                }
                H5Log.d(NebulaServiceImpl.TAG, "preLoadInMainProcess");
                H5AppCenterPresetProvider centerPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
                if (centerPresetProvider != null) {
                    Set<String> appSet = centerPresetProvider.getEnableDegradeApp();
                    if (appSet != null && !appSet.isEmpty()) {
                        for (String appId : appSet) {
                            try {
                                H5GlobalDegradePkg.getInstance().prepareContent(appId);
                            } catch (Throwable throwable2) {
                                H5Log.e((String) NebulaServiceImpl.TAG, throwable2);
                            }
                        }
                    }
                }
            }
        }, (long) time, TimeUnit.SECONDS);
    }

    private void d() {
        if (this.i == null) {
            this.i = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    H5Log.d(NebulaServiceImpl.TAG, "h5bugMeStartUpReceiver" + NebulaServiceImpl.this.hashCode());
                    if (intent.getAction().equals(H5Param.H5_BUG_ME_STARTUP)) {
                        try {
                            if (intent.getExtras() == null || !TextUtils.equals(H5Utils.getString(intent.getExtras(), (String) "h5devType"), "h5devH5App")) {
                                NebulaServiceImpl.this.getBugMeManager().openSettingPanel(true);
                                return;
                            }
                            H5NebulaDebugProvider provider = (H5NebulaDebugProvider) H5Utils.getProvider(H5NebulaDebugProvider.class.getName());
                            if (provider != null) {
                                provider.openDebugSetting();
                            } else if (Nebula.DEBUG) {
                                Toast.makeText(context, "找不到nebuladebug接口，请联系 @折溪", 0).show();
                            }
                        } catch (Exception e) {
                            H5Log.e((String) NebulaServiceImpl.TAG, (Throwable) e);
                        }
                    }
                }
            };
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Environment.getContext());
            IntentFilter filter = new IntentFilter();
            filter.addAction(H5Param.H5_BUG_ME_STARTUP);
            H5Log.d(TAG, "register h5bugMeStartUp");
            manager.registerReceiver(this.i, filter);
        }
    }

    public boolean sendEvent(H5Event event) {
        return Nebula.getDispatcher().dispatch(event) == Error.NONE;
    }

    public void addPluginConfig(H5PluginConfig config) {
        H5PluginConfigManager.getInstance().addConfig(config);
    }

    public H5ProviderManager getProviderManager() {
        return H5ProviderManagerImpl.getInstance();
    }

    public boolean isAliDomain(String url) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.isAliDomains(url);
        }
        H5Log.d(TAG, "not implement H5ConfigProvider.");
        return false;
    }

    public boolean permitLocation(String url) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.permitLocation(url);
        }
        H5Log.d(TAG, "not implement H5ConfigProvider.");
        return false;
    }

    private static boolean e() {
        if (H5Utils.isMainProcess()) {
            return H5DevConfig.getBooleanConfig("h5_dev_webDriver", false);
        }
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService == null) {
            return false;
        }
        try {
            H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
            if (h5IpcServer != null) {
                return h5IpcServer.getBooleanConfig("h5_dev_webDriver", false);
            }
            return false;
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return false;
        }
    }

    public H5WebDriverHelper getWebDriverHelper() {
        if (Nebula.DEBUG && this.e == null && !this.l && e()) {
            this.l = true;
            try {
                Class clazz = H5Environment.getClass("android-phone-wallet-birdnestdevtools", "com.alipay.archimedes.ArchimedesHelper");
                if (clazz != null) {
                    Field f2 = clazz.getDeclaredField("sharedInstance");
                    f2.setAccessible(true);
                    Object o = f2.get(null);
                    if (o instanceof H5WebDriverHelper) {
                        H5Log.d(TAG, "getWebDriverHelper " + o);
                        this.e = (H5WebDriverHelper) o;
                    }
                }
            } catch (Throwable e2) {
                H5Log.e((String) TAG, e2);
            }
        }
        return this.e == null ? H5WebDriverHelper.defaultHelper : this.e;
    }

    public void setWebDriverHelper(H5WebDriverHelper helper) {
        this.e = helper;
    }

    public void setH5TaskScheduleProvider(H5TaskScheduleProvider taskScheduleProvider) {
        this.f = taskScheduleProvider;
    }

    public H5TaskScheduleProvider getH5TaskScheduleProvider() {
        return this.f;
    }

    public synchronized void fireUrgentUcInit(Bundle bundle) {
        if (!H5Flag.ucReady) {
            H5Log.d(TAG, "fire urgent task to init uc service");
            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new H5UcInitTask(true, bundle));
        } else {
            H5Log.d(TAG, "uc isReady");
        }
    }

    public boolean exitService() {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((H5Session) it.next()).exitSession();
        }
        return true;
    }

    public Stack<H5Session> getSessions() {
        return this.b;
    }

    public H5Data getData() {
        if (this.a == null) {
            this.a = (H5Data) Nebula.getProviderManager().getProvider(H5CacheProvider.class.getName());
        }
        return this.a;
    }
}
