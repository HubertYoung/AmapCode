package com.alipay.mobile.nebulacore.dev.provider;

import android.content.Intent;
import android.text.TextUtils;
import android.util.LruCache;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.NebulaService;
import com.alipay.mobile.nebulacore.dev.ui.H5BugMeSettingsActivity;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.util.HashMap;

public class H5BugMeManagerImpl implements H5BugMeManager {
    private String[] a = null;
    private LruCache<String, Boolean> b = new LruCache<>(10);
    private boolean c = H5BugmeLogCollector.enabled();
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private H5DevPlugin g = null;
    private H5DevDebugProvider h = null;
    private boolean i = true;

    public void setUp() {
        if (!this.d) {
            H5Log.d("H5BugMeManagerImpl", "setUpBugMe");
            if (H5Utils.isMainProcess() && this.i) {
                H5DevConfig.resetBugMeSettings();
            }
            if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DEBUG_SWITCH, false) || this.c) {
                a();
            }
            this.d = true;
        }
    }

    private void a() {
        if (this.g == null) {
            H5Log.d("H5BugMeManagerImpl", "create DevPlugin & BugMeProvider");
            this.g = new H5DevPlugin();
            this.h = new H5WalletDevDebugProvider(this);
            H5Utils.getH5ProviderManager().setProvider(H5DevDebugProvider.class.getName(), this.h);
            Nebula.getService().getPluginManager().register((H5Plugin) this.g);
        }
    }

    private void b() {
        if (this.g != null) {
            H5Log.d("H5BugMeManagerImpl", "release DevPlugin & BugMeProvider");
            H5Utils.getH5ProviderManager().removeProvider(H5DevDebugProvider.class.getName());
            Nebula.getService().getPluginManager().unregister((H5Plugin) this.g);
            this.g = null;
            this.h = null;
        }
    }

    public void release() {
        if (!this.c) {
            b();
        }
        this.b.evictAll();
        this.a = null;
        H5DevConfig.resetBugMeSettings();
        this.d = false;
    }

    public void setWebViewDebugging(String url, APWebView apWebView) {
        if (!TextUtils.isEmpty(url) && apWebView != null) {
            boolean canDebug = Nebula.DEBUG;
            if (!canDebug && H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_WIRED_DEBUG, false)) {
                canDebug = hasAccessToDebug(url);
            }
            if (canDebug) {
                apWebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }

    public void setDomainWhiteList(String[] strings) {
        this.a = strings;
        this.b.evictAll();
    }

    public boolean hasAccessToDebug(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_SUPER_USER, false)) {
            return true;
        }
        String pureUrl = H5UrlHelper.purifyUrl(url);
        Boolean cached = this.b.get(pureUrl);
        if (cached != null) {
            return cached.booleanValue();
        }
        boolean isInWhiteList = false;
        if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DEBUG_SWITCH, false)) {
            int i2 = 0;
            while (this.a != null && i2 < this.a.length) {
                String domain = this.a[i2];
                if (!TextUtils.isEmpty(domain) && pureUrl.contains(domain)) {
                    isInWhiteList = true;
                }
                i2++;
            }
        }
        this.b.put(url, Boolean.valueOf(isInWhiteList));
        return isInWhiteList;
    }

    public void openSettingPanel(boolean fromOpenPlatform) {
        if (fromOpenPlatform) {
            H5DevConfig.setBooleanConfig(H5DevConfig.H5_BUG_ME_SHOW_ICON, true);
            H5DevConfig.setBooleanConfig(H5DevConfig.H5_BUG_ME_SUPER_USER, true);
        }
        try {
            Intent intent = new Intent(H5Environment.getContext(), H5BugMeSettingsActivity.class);
            intent.addFlags(268435456);
            H5Environment.startActivity(null, intent);
        } catch (Exception e2) {
            H5Log.e((String) "H5BugMeManagerImpl", (Throwable) e2);
        }
    }

    public void onBugMeSwitched(boolean debugSwitch) {
        this.i = false;
        if (debugSwitch) {
            a();
            this.b.evictAll();
            if (this.h != null) {
                this.h.welcome("NebulaSDK", "welcome", "NebulaSDK", "welcome to Hybrid Inspector");
            }
        } else {
            release();
        }
        H5Trace.setEnabled(debugSwitch);
    }

    public void logServiceWorkerOnReceiveValue(HashMap<String, String> value, String workerId) {
        boolean hasAccess = false;
        try {
            if (this.h != null) {
                hasAccess = hasAccessToDebug(value.get("url"));
            } else if (!H5Utils.isMainProcess()) {
                if (!this.e) {
                    H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                    if (h5EventHandlerService != null) {
                        this.f = ((H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class)).hasAccessToDebug(value.get("url"));
                        if (this.f) {
                            setUp();
                        }
                        this.e = true;
                    }
                } else {
                    hasAccess = this.f;
                }
            }
            if (hasAccess) {
                a(value, workerId);
            }
        } catch (Throwable throwable) {
            H5Log.e("H5BugMeManagerImpl", "CORE_EVENT_CONSOLE_CALLBACK", throwable);
        }
    }

    private void a(HashMap<String, String> result, String workerId) {
        if (this.h != null) {
            String event = result.get("event");
            String message = result.get("msg");
            String viewId = "worker_" + workerId;
            String url = result.get("url");
            String sessionId = "";
            String title = "";
            String userAgent = "";
            NebulaService nebulaService = Nebula.getService();
            if (nebulaService != null) {
                H5Page topPage = nebulaService.getTopH5Page();
                try {
                    title = topPage.getTitle();
                } catch (Throwable th) {
                    if (!(topPage.getH5TitleBar() == null || topPage.getH5TitleBar().getTitle() == null)) {
                        title = topPage.getH5TitleBar().getTitle().toString();
                    }
                }
                if (topPage.getWebView() != null) {
                    userAgent = topPage.getWebView().getSettings().getUserAgentString();
                }
                sessionId = H5BugmeIdGenerator.getSessionId(topPage);
            }
            this.h.setTitle(viewId, title);
            this.h.setUserAgent(viewId, userAgent);
            this.h.setPageUrl(viewId, url);
            this.h.setSessionId(viewId, sessionId);
            if ("OnReportConsoleMessage".equals(event)) {
                this.h.consoleLog(ReportManager.LOG_PATH, viewId, message, null);
            } else if ("OnStarted".equals(event)) {
                this.h.pageLog("create", viewId, userAgent, url, title, null);
            } else if ("onStopped".equals(event)) {
                this.h.pageLog("destroy", viewId, userAgent, url, title, null);
            } else if ("OnReportException".equals(event)) {
                H5Log.e((String) "H5BugMeManagerImpl", "Worker OnReportException: " + message);
                this.h.consoleLog("error", viewId, message, null);
            }
        }
    }
}
