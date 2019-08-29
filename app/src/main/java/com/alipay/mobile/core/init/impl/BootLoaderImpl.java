package com.alipay.mobile.core.init.impl;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.alipay.mobile.core.ApplicationManager;
import com.alipay.mobile.core.init.BootLoader;
import com.alipay.mobile.core.service.impl.ExternalServiceManagerImpl;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.StartupSafeguard;
import com.alipay.mobile.framework.service.ServicesLoader;
import com.alipay.mobile.framework.service.ext.ExternalServiceManager;
import com.alipay.mobile.quinox.startup.LaunchStrategy;
import com.alipay.mobile.quinox.utils.AppState;
import com.alipay.mobile.quinox.utils.Callback;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TimingLogger;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Collection;
import java.util.Set;

public class BootLoaderImpl implements BootLoader {
    public static final String LOAD_SERVICE_THREAD = "LauncherApplication.Init.LoadService";
    /* access modifiers changed from: private */
    public MicroApplicationContext a;
    /* access modifiers changed from: private */
    public BundleLoadHelper b;
    /* access modifiers changed from: private */
    public ServicesLoader c;
    private ExternalServiceManagerImpl d;
    private ApplicationInfo e;

    public BootLoaderImpl(MicroApplicationContext microApplicationContext) {
        this.a = microApplicationContext;
    }

    public void preload() {
        a(true);
    }

    public void load() {
        a(false);
    }

    private void a(boolean preload) {
        Application application = this.a.getApplicationContext();
        String agentCommonServiceLoad = null;
        String agentEntryPkgName = null;
        try {
            if (this.e == null) {
                this.e = application.getPackageManager().getApplicationInfo(application.getPackageName(), 128);
            }
            agentCommonServiceLoad = this.e.metaData.getString("agent.commonservice.load");
            agentEntryPkgName = this.e.metaData.getString("agent.entry.pkgname");
            Object entryAppId = this.e.metaData.get("agent.entry.appid");
            if (entryAppId == null) {
                entryAppId = "20000001";
            }
            ((ApplicationManager) this.a.findServiceByInterface(ApplicationManager.class.getName())).setEntryAppId(entryAppId.toString());
        } catch (Exception e1) {
            TraceLogger.w((String) BootLoader.TAG, (Throwable) e1);
        }
        if (TextUtils.isEmpty(agentCommonServiceLoad)) {
            agentCommonServiceLoad = "com.alipay.mobile.framework.service.common.loader.CommonServiceLoadAgent";
        }
        if (TextUtils.isEmpty(agentEntryPkgName)) {
            agentEntryPkgName = "com.alipay.android.launcher";
        } else {
            TraceLogger.i((String) BootLoader.TAG, "agentEntryPkgName from meta:" + agentEntryPkgName);
        }
        if (this.d == null) {
            this.d = new ExternalServiceManagerImpl();
            this.d.attachContext(this.a);
            this.a.registerService(ExternalServiceManager.class.getName(), this.d);
        }
        if (this.b == null) {
            this.b = new BundleLoadHelper(this, agentEntryPkgName, null);
        }
        if (!preload) {
            TimingLogger.getBootLogger().addSplit("t_bootload1");
            try {
                this.c = (ServicesLoader) application.getClassLoader().loadClass(agentCommonServiceLoad).newInstance();
                this.c.load();
            } catch (ClassNotFoundException e2) {
                TraceLogger.w((String) BootLoader.TAG, (Throwable) e2);
            } catch (InstantiationException e3) {
                TraceLogger.w((String) BootLoader.TAG, (Throwable) e3);
            } catch (IllegalAccessException e4) {
                TraceLogger.w((String) BootLoader.TAG, (Throwable) e4);
            }
            TimingLogger.getBootLogger().addSplit("t_bootload2");
            if (!LauncherApplicationAgent.NEED_PRELOAD && !LaunchStrategy.PRELOAD_DOWNGRADE && !StartupSafeguard.isShouldOptimizeBootFinishSpeed() && Runtime.getRuntime().availableProcessors() > 2 && !LiteProcessInfo.g(this.a.getApplicationContext()).isCurrentProcessALiteProcess()) {
                StartupSafeguard.setShouldOptimizeBootFinishSpeed(true);
                Thread loadServiceThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            CookieSyncManager.createInstance(BootLoaderImpl.this.a.getApplicationContext());
                        } catch (Throwable e) {
                            TraceLogger.w((String) BootLoader.TAG, e);
                        }
                        if (BootLoaderImpl.this.c != null) {
                            BootLoaderImpl.this.c.afterBootLoad();
                        }
                        try {
                            ReflectUtil.invokeMethod((String) "com.alipay.android.launcher.StartupPerformanceHelper", (String) "preloadOthers");
                        } catch (Throwable t) {
                            TraceLogger.w((String) BootLoader.TAG, t);
                        }
                    }
                });
                loadServiceThread.setName(LOAD_SERVICE_THREAD);
                loadServiceThread.start();
            }
            this.b.loadBundleDefinitions();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    ActivityHelper.startFrameworkSecondPipeLine();
                }
            }, 7000);
            return;
        }
        new Thread("LauncherApplication.Init.Preload.ComponentClass") {
            public void run() {
                Process.setThreadPriority(-18);
                DescriptionManager.getInstance().preload();
                Collection bundleNames = BundleInfoHelper.getBundleNames();
                if (bundleNames != null) {
                    for (String bundleName : bundleNames) {
                        if (!DescriptionManager.getInstance().isLazyBundle(bundleName)) {
                            BootLoaderImpl.this.a(bundleName);
                        }
                    }
                }
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void a(String bundleName) {
        ClassLoader classLoader = LauncherApplicationAgent.getInstance().getBundleContext().findClassLoaderByBundleName(bundleName);
        Set packages = BundleInfoHelper.getBundlePackageNames(bundleName);
        if (packages != null && !packages.isEmpty()) {
            for (String packageName : packages) {
                if (!TextUtils.isEmpty(packageName)) {
                    this.b.preloadBundleMetaInfoClass(bundleName, packageName);
                }
            }
        }
        TraceLogger.d((String) BootLoader.TAG, "getBundleMetaInfo(bundleName=" + bundleName + "), classLoader=" + classLoader + ", packages=" + StringUtil.collection2String(packages));
    }

    public MicroApplicationContext getContext() {
        return this.a;
    }

    public void loadBundle(String bundleName) {
        this.b.loadBundle(LauncherApplicationAgent.getInstance().getBundleContext(), bundleName);
    }

    public void postLoad() {
        try {
            CookieSyncManager.createInstance(this.a.getApplicationContext());
        } catch (Throwable e2) {
            TraceLogger.w((String) BootLoader.TAG, e2);
        }
        this.b.loadBundleImpl();
        if (LauncherApplicationAgent.NEED_SYNC) {
            if (this.c != null) {
                try {
                    this.c.afterBootLoad();
                } catch (Throwable e3) {
                    TraceLogger.w((String) BootLoader.TAG, e3);
                }
            }
            try {
                ReflectUtil.invokeMethod((String) "com.alipay.android.launcher.StartupPerformanceHelper", (String) "preloadOthers");
            } catch (Throwable t) {
                TraceLogger.w((String) BootLoader.TAG, t);
            }
        } else if (!LiteProcessInfo.g(this.a.getApplicationContext()).isCurrentProcessALiteProcess() && this.c != null) {
            try {
                this.c.afterBootLoad();
            } catch (Throwable e4) {
                TraceLogger.w((String) BootLoader.TAG, e4);
            }
        }
    }

    public void loadServices() {
        if (!LiteProcessInfo.g(this.a.getApplicationContext()).isCurrentProcessALiteProcess()) {
            synchronized (AppState.class) {
                if (!AppState.isPreloadingActivity()) {
                    this.b.loadBundleServices();
                } else {
                    AppState.addPreloadActivityFinishCallback(new Callback<Void>() {
                        public void onCallback(Void aVoid) {
                            new Thread("LauncherApplication.Init.ActPreload") {
                                public void run() {
                                    BootLoaderImpl.this.b.loadBundleServices();
                                }
                            }.start();
                        }
                    });
                }
            }
            TraceLogger.e((String) "Pipeline", (String) "PIPELINE_FRAMEWORK_INITED : start");
            if (!LauncherApplicationAgent.NEED_SYNC) {
                this.a.getPipelineByName("com.alipay.mobile.framework.INITED").start();
            }
        }
    }
}
