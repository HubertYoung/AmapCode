package com.alipay.mobile.core.init.impl;

import android.os.Handler;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.core.ApplicationManager;
import com.alipay.mobile.core.app.impl.LocalBroadcastManagerWrapper;
import com.alipay.mobile.core.impl.MicroApplicationContextImpl;
import com.alipay.mobile.core.init.BootLoader;
import com.alipay.mobile.core.pipeline.impl.PipelineManager;
import com.alipay.mobile.framework.BaseMetaInfo;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.framework.service.ext.ExternalServiceManager;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class BundleLoadHelper {
    public static final String META_INFO = ".MetaInfo";
    private String a;
    private ApplicationManager b;
    private ExternalServiceManager c;
    private PipelineManager d;
    private Map<String, AtomicBoolean> e;
    private final ConcurrentHashMap<String, BaseMetaInfo> f = new ConcurrentHashMap<>();
    private Handler g;
    private Set<String> h;
    LocalBroadcastManagerWrapper mLocalBroadcastManagerWrapper;
    MicroApplicationContext mMicroApplicationContext;

    public BundleLoadHelper(BootLoader bootLoader, String entryPkgName, Handler handler) {
        this.a = entryPkgName;
        MicroApplicationContext microApplicationContext = bootLoader.getContext();
        this.mMicroApplicationContext = microApplicationContext;
        this.b = (ApplicationManager) microApplicationContext.findServiceByInterface(ApplicationManager.class.getName());
        this.c = (ExternalServiceManager) microApplicationContext.findServiceByInterface(ExternalServiceManager.class.getName());
        this.mLocalBroadcastManagerWrapper = (LocalBroadcastManagerWrapper) microApplicationContext.findServiceByInterface(LocalBroadcastManagerWrapper.class.getName());
        this.d = ((MicroApplicationContextImpl) microApplicationContext).getPipelineManager();
        this.e = new ConcurrentHashMap();
        this.g = handler;
    }

    public void setHelperHandler(Handler handler) {
        this.g = handler;
    }

    public void loadBundleServices() {
        Collection allBundle = BundleInfoHelper.getBundleNames();
        String[] loadBundles = new String[allBundle.size()];
        allBundle.toArray(loadBundles);
        for (String bundleName : loadBundles) {
            if (!DescriptionManager.getInstance().isLazyBundle(bundleName)) {
                a(bundleName);
            } else if (LogUtil.isDebug()) {
                TraceLogger.i((String) BootLoader.TAG, "loadBundleServices,skip lazy bundle:" + bundleName);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String bundleName) {
        List serviceDescriptions = DescriptionManager.getInstance().getDescription(bundleName, false, true, false, false);
        if (serviceDescriptions != null) {
            if (LogUtil.isDebug()) {
                TraceLogger.i((String) BootLoader.TAG, bundleName + ":doLoadBundleService, size=" + serviceDescriptions.size());
            }
            BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
            for (MicroDescription serviceDescription : serviceDescriptions) {
                if ((serviceDescription instanceof ServiceDescription) && !((ServiceDescription) serviceDescription).isLazy()) {
                    if (serviceDescription.getClassLoader() == null) {
                        serviceDescription.setClassLoader(bundleContext.findClassLoaderByBundleName(bundleName));
                    }
                    if (LogUtil.isDebug()) {
                        TraceLogger.i((String) BootLoader.TAG, "createExternalService:" + ((ServiceDescription) serviceDescription).getInterfaceClass());
                    }
                    this.c.createExternalService((ServiceDescription) serviceDescription);
                }
            }
        }
    }

    public void loadBundleImpl() {
        if (!this.mLocalBroadcastManagerWrapper.isSupportSubThreadBroadcast()) {
            for (BroadcastReceiverDescription broadcastReceiver : DescriptionManager.getInstance().findBroadcastReceiverDescription(null)) {
                a(broadcastReceiver);
            }
        }
        for (ValveDescription valve : DescriptionManager.getInstance().findValveDescription(null)) {
            a(valve);
        }
    }

    public void loadBundleDefinitions() {
        AtomicBoolean finish;
        Collection bundleNames = BundleInfoHelper.getBundleNames();
        try {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                this.h = (Set) ReflectUtil.invokeMethod((String) "com.alipay.android.launcher.StartupPerformanceHelper", (String) "getPreloadServices");
            }
        } catch (Throwable t) {
            TraceLogger.e(BootLoader.TAG, "getPreloadServices error", t);
        }
        if (bundleNames == null) {
            TraceLogger.w((String) BootLoader.TAG, (Throwable) new RuntimeException("null == bundleNames"));
            return;
        }
        String[] mapList = LiteProcessInfo.getLiteBundleList();
        Set mList = new HashSet();
        for (int i = 0; i < mapList.length; i++) {
            mList.add(mapList[i]);
        }
        boolean isLite = LiteProcessInfo.g(this.mMicroApplicationContext.getApplicationContext()).isCurrentProcessALiteProcess();
        for (final String bundleName : bundleNames) {
            if (!isLite || mList.contains(bundleName)) {
                if (!DescriptionManager.getInstance().isLazyBundle(bundleName)) {
                    long start = System.currentTimeMillis();
                    BundleContext bundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
                    Set packages = BundleInfoHelper.getBundlePackageNames(bundleName);
                    if (packages != null && !packages.isEmpty()) {
                        ClassLoader classLoader = bundleContext.findClassLoaderByBundleName(bundleName);
                        if (classLoader == null) {
                            continue;
                        } else {
                            if (LogUtil.isDebug()) {
                                TraceLogger.d((String) BootLoader.TAG, "loadBundle(bundleName=" + bundleName + "), classLoader=" + classLoader + ", packages=" + StringUtil.collection2String(packages));
                            }
                            for (String packageName : packages) {
                                if (!TextUtils.isEmpty(packageName)) {
                                    String key = bundleName + AUScreenAdaptTool.PREFIX_ID + packageName;
                                    synchronized (this.e) {
                                        finish = this.e.get(key);
                                        if (finish == null) {
                                            finish = new AtomicBoolean(false);
                                            this.e.put(key, finish);
                                        }
                                    }
                                    if (finish.get()) {
                                        TraceLogger.e((String) "BootLoader.MetaInfo", "Bundle has already loaded: " + key);
                                    } else {
                                        synchronized (finish) {
                                            if (finish.get()) {
                                                TraceLogger.e((String) "BootLoader.MetaInfo", "Bundle has already loaded(synchronized): " + key);
                                                return;
                                            }
                                            DescriptionManager.getInstance().addDescriptionsFromMetaInfo(bundleName, a(bundleName, packageName));
                                            finish.set(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (this.g != null) {
                        Handler handler = this.g;
                        AnonymousClass1 r0 = new Runnable() {
                            public void run() {
                                BundleLoadHelper.this.a(bundleName);
                            }
                        };
                        handler.post(r0);
                    }
                    if (LogUtil.isDebug()) {
                        LogUtil.i(BootLoader.TAG, "loadBundle(bundleName=" + bundleName + "). cost:" + (System.currentTimeMillis() - start) + " ms.");
                    }
                } else if (LogUtil.isDebug()) {
                    TraceLogger.i((String) BootLoader.TAG, "loadBundleDefinitions,skip lazy bundle:" + bundleName);
                }
            }
        }
    }

    public Class<?> preloadBundleMetaInfoClass(String bundleName, String pkgName) {
        try {
            Class clazz = LauncherApplicationAgent.getInstance().getBundleContext().findClassLoaderByBundleName(bundleName).loadClass(pkgName + META_INFO);
            TraceLogger.i((String) "BootLoader.MetaInfo", "Load MetaInfo Class Success: BundleName=" + bundleName + " [" + pkgName + ".MetaInfo]");
            return clazz;
        } catch (Throwable e2) {
            TraceLogger.e("BootLoader.MetaInfo", "Failed to load MetaInfo class: BundleName=" + bundleName + " [" + pkgName + ".MetaInfo]", e2);
            return null;
        }
    }

    public BaseMetaInfo getBundleMetaInfo(String bundleName, String pkgName) {
        String key = bundleName + AUScreenAdaptTool.PREFIX_ID + pkgName;
        BaseMetaInfo cache = this.f.get(key);
        if (cache == null) {
            ClassLoader classLoader = LauncherApplicationAgent.getInstance().getBundleContext().findClassLoaderByBundleName(bundleName);
            try {
                cache = (BaseMetaInfo) classLoader.loadClass(pkgName + META_INFO).newInstance();
                cache.setClassLoader(classLoader);
                TraceLogger.i((String) "BootLoader.MetaInfo", "Load MetaInfo Success: BundleName=" + bundleName + " [" + pkgName + ".MetaInfo]");
            } catch (Throwable e2) {
                TraceLogger.e("BootLoader.MetaInfo", "Failed to load MetaInfo: BundleName=" + bundleName + " [" + pkgName + ".MetaInfo]", e2);
            }
            if (cache != null) {
                this.f.put(key, cache);
            }
        }
        return cache;
    }

    public void loadBundle(BundleContext bundleContext, String bundleName) {
        ClassLoader classLoader = bundleContext.findClassLoaderByBundleName(bundleName);
        Set packages = BundleInfoHelper.getBundlePackageNames(bundleName);
        if (LogUtil.isDebug()) {
            TraceLogger.d((String) BootLoader.TAG, "loadBundle(bundleName=" + bundleName + "), classLoader=" + classLoader + ", packages=" + StringUtil.collection2String(packages));
        }
        if (packages != null && !packages.isEmpty()) {
            for (String packageName : packages) {
                if (!TextUtils.isEmpty(packageName)) {
                    a(classLoader, bundleName, packageName);
                }
            }
        }
    }

    private void a(ClassLoader classLoader, String bundleName, String pkgName) {
        AtomicBoolean finish;
        String key = bundleName + AUScreenAdaptTool.PREFIX_ID + pkgName;
        if (classLoader == null) {
            TraceLogger.e((String) "BootLoader.MetaInfo", "loadBundle(null == classLoader): " + key);
            FrameworkMonitor.getInstance(null).handleLoadBundleFail(bundleName, "1002");
            return;
        }
        synchronized (this.e) {
            finish = this.e.get(key);
            if (finish == null) {
                finish = new AtomicBoolean(false);
                this.e.put(key, finish);
            }
        }
        if (finish.get()) {
            TraceLogger.e((String) "BootLoader.MetaInfo", "Bundle has already loaded: " + key);
            return;
        }
        synchronized (finish) {
            if (finish.get()) {
                TraceLogger.e((String) "BootLoader.MetaInfo", "Bundle has already loaded(synchronized): " + key);
                return;
            }
            BaseMetaInfo metaInfo = a(bundleName, pkgName);
            DescriptionManager.getInstance().addDescriptionsFromMetaInfo(bundleName, metaInfo);
            finish.set(true);
            a(metaInfo);
            b(metaInfo);
        }
    }

    private BaseMetaInfo a(String bundleName, String pkgName) {
        BaseMetaInfo baseMetaInfo = getBundleMetaInfo(bundleName, pkgName);
        if (baseMetaInfo == null) {
            return null;
        }
        if (LogUtil.isDebug()) {
            TraceLogger.i((String) "BootLoader.MetaInfo", "Success to load MetaInfo: BundleName=" + bundleName + " [" + baseMetaInfo.getClass().getName() + "]");
        }
        List apps = baseMetaInfo.getApplications();
        if (apps != null && !apps.isEmpty()) {
            for (ApplicationDescription appDescription : apps) {
                if (appDescription != null) {
                    if (LogUtil.isDebug()) {
                        LogUtil.v("BootLoader.MetaInfo", appDescription.toString());
                    }
                    this.b.addDescription(appDescription);
                }
            }
            if (this.a.equalsIgnoreCase(pkgName)) {
                String entry = baseMetaInfo.getEntry();
                TraceLogger.i((String) BootLoader.TAG, "EntryAppName:" + entry);
                if (entry != null) {
                    this.b.setEntryAppName(entry);
                }
            }
        }
        List services = baseMetaInfo.getServices();
        if (services == null || services.isEmpty()) {
            return baseMetaInfo;
        }
        for (ServiceDescription serviceDescription : services) {
            if (serviceDescription != null) {
                String className = serviceDescription.getInterfaceClass();
                if (this.h != null && this.h.contains(className)) {
                    serviceDescription.setLazy(false);
                }
                this.c.registerExternalServiceOnly(serviceDescription);
            }
        }
        return baseMetaInfo;
    }

    private void a(BaseMetaInfo baseMetaInfo) {
        if (baseMetaInfo != null) {
            List broadcastReceivers = baseMetaInfo.getBroadcastReceivers();
            if (broadcastReceivers != null && !broadcastReceivers.isEmpty()) {
                for (BroadcastReceiverDescription broadcastReceiverDescription : broadcastReceivers) {
                    if (broadcastReceiverDescription != null) {
                        if (broadcastReceiverDescription.getMsgCode() == null || broadcastReceiverDescription.getMsgCode().length <= 0) {
                            if (LogUtil.isDebug()) {
                                LogUtil.w((String) "BootLoader.MetaInfo", broadcastReceiverDescription + ": msgCode is empty.");
                            }
                        } else if (this.mLocalBroadcastManagerWrapper.isSupportSubThreadBroadcast()) {
                            this.mLocalBroadcastManagerWrapper.registerReceiverDescription(broadcastReceiverDescription);
                        } else {
                            a(broadcastReceiverDescription);
                        }
                    }
                }
            }
            List valves = baseMetaInfo.getValves();
            if (valves != null && !valves.isEmpty()) {
                for (ValveDescription valve : valves) {
                    if (valve != null) {
                        a(valve);
                    }
                }
            }
        }
    }

    private void b(BaseMetaInfo baseMetaInfo) {
        if (baseMetaInfo != null) {
            List services = baseMetaInfo.getServices();
            if (services != null && !services.isEmpty()) {
                for (ServiceDescription serviceDescription : services) {
                    if (serviceDescription != null && !serviceDescription.isLazy()) {
                        this.c.createExternalService(serviceDescription);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006e, code lost:
        if (com.alipay.mobile.quinox.utils.LogUtil.isDebug() == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0070, code lost:
        com.alipay.mobile.quinox.utils.LogUtil.d(com.alipay.mobile.core.init.BootLoader.TAG, "registerReceiver: " + r15.getClassName() + ", actions : " + com.alipay.mobile.quinox.utils.StringUtil.array2String(r15.getMsgCode()));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.alipay.mobile.framework.msg.BroadcastReceiverDescription r15) {
        /*
            r14 = this;
            long r8 = java.lang.System.currentTimeMillis()
            r2 = 0
            java.lang.Class r10 = r15.getClazz()     // Catch:{ Throwable -> 0x0028 }
            java.lang.Object r10 = r10.newInstance()     // Catch:{ Throwable -> 0x0028 }
            r0 = r10
            android.content.BroadcastReceiver r0 = (android.content.BroadcastReceiver) r0     // Catch:{ Throwable -> 0x0028 }
            r2 = r0
        L_0x0011:
            if (r2 == 0) goto L_0x009a
            android.content.IntentFilter r6 = new android.content.IntentFilter
            r6.<init>()
            java.lang.String[] r11 = r15.getMsgCode()
            int r12 = r11.length
            r10 = 0
        L_0x001e:
            if (r10 >= r12) goto L_0x0051
            r7 = r11[r10]
            r6.addAction(r7)
            int r10 = r10 + 1
            goto L_0x001e
        L_0x0028:
            r3 = move-exception
            r10 = 0
            com.alipay.mobile.framework.FrameworkMonitor r10 = com.alipay.mobile.framework.FrameworkMonitor.getInstance(r10)
            r10.handleDescriptionInitFail(r15, r3)
            java.lang.String r10 = "BootLoader.MetaInfo"
            java.lang.RuntimeException r11 = new java.lang.RuntimeException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "Failed to reflect BroadcastReceiver["
            r12.<init>(r13)
            java.lang.StringBuilder r12 = r12.append(r15)
            java.lang.String r13 = "]"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            r11.<init>(r12, r3)
            com.alipay.mobile.quinox.utils.TraceLogger.e(r10, r11)
            goto L_0x0011
        L_0x0051:
            monitor-enter(r15)
            boolean r10 = r15.hasRegisted()     // Catch:{ all -> 0x00d1 }
            if (r10 == 0) goto L_0x005a
            monitor-exit(r15)     // Catch:{ all -> 0x00d1 }
        L_0x0059:
            return
        L_0x005a:
            boolean r10 = r15.isSubThread()     // Catch:{ all -> 0x00d1 }
            if (r10 == 0) goto L_0x00cb
            com.alipay.mobile.core.app.impl.LocalBroadcastManagerWrapper r10 = r14.mLocalBroadcastManagerWrapper     // Catch:{ all -> 0x00d1 }
            r10.registerSubThreadReceiver(r2, r6)     // Catch:{ all -> 0x00d1 }
        L_0x0065:
            r10 = 1
            r15.setHasRegisted(r10)     // Catch:{ all -> 0x00d1 }
            monitor-exit(r15)     // Catch:{ all -> 0x00d1 }
            boolean r10 = com.alipay.mobile.quinox.utils.LogUtil.isDebug()
            if (r10 == 0) goto L_0x009a
            java.lang.String r10 = "BootLoader"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "registerReceiver: "
            r11.<init>(r12)
            java.lang.String r12 = r15.getClassName()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = ", actions : "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String[] r12 = r15.getMsgCode()
            java.lang.String r12 = com.alipay.mobile.quinox.utils.StringUtil.array2String(r12)
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.quinox.utils.LogUtil.d(r10, r11)
        L_0x009a:
            long r4 = java.lang.System.currentTimeMillis()
            boolean r10 = com.alipay.mobile.quinox.utils.LogUtil.isDebug()
            if (r10 == 0) goto L_0x0059
            java.lang.String r10 = "BootLoader.MetaInfo"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "cost ["
            r11.<init>(r12)
            long r12 = r4 - r8
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = "] ms, ["
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r15)
            java.lang.String r12 = "] is a broadcast."
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.quinox.utils.LogUtil.i(r10, r11)
            goto L_0x0059
        L_0x00cb:
            com.alipay.mobile.core.app.impl.LocalBroadcastManagerWrapper r10 = r14.mLocalBroadcastManagerWrapper     // Catch:{ all -> 0x00d1 }
            r10.registerReceiver(r2, r6)     // Catch:{ all -> 0x00d1 }
            goto L_0x0065
        L_0x00d1:
            r10 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x00d1 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.core.init.impl.BundleLoadHelper.a(com.alipay.mobile.framework.msg.BroadcastReceiverDescription):void");
    }

    private void a(ValveDescription valve) {
        this.d.addValve(valve);
    }
}
