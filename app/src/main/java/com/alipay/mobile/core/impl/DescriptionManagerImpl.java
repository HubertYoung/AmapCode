package com.alipay.mobile.core.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.BaseMetaInfo;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MetaInfoCfg;
import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.framework.PackageDescription;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.framework.util.MetaInfoOperator;
import com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback;
import com.alipay.mobile.quinox.apkfile.ApkFileReader;
import com.alipay.mobile.quinox.startup.UpgradeHelper;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.ProcessLock;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class DescriptionManagerImpl extends DescriptionManager {
    /* access modifiers changed from: private */
    public static final String a = DescriptionManager.class.getSimpleName();
    /* access modifiers changed from: private */
    public final Context b;
    private final Set<String> c = new CopyOnWriteArraySet();
    private Map<String, Map<String, ApplicationDescription>> d = new ConcurrentHashMap();
    private Map<String, Map<String, ServiceDescription>> e = new ConcurrentHashMap();
    private Map<String, List<BroadcastReceiverDescription>> f = new ConcurrentHashMap();
    private Map<String, List<ValveDescription>> g = new ConcurrentHashMap();
    private Map<String, List<PackageDescription>> h = new ConcurrentHashMap();
    private Set<String> i = new CopyOnWriteArraySet();
    private String j = MetaInfoOperator.META_INFO_CFG;
    private String k = MetaInfoOperator.META_INFO_EXT_CFG;
    private Map<String, Set<String>> l = new ConcurrentHashMap();

    public DescriptionManagerImpl(Context context) {
        this.b = context;
        a();
    }

    private void a() {
        ProcessLock processLock = new ProcessLock(this.b.getCacheDir() + "/.DescriptionManagerImpl_init.lock");
        try {
            processLock.lock();
            b();
        } finally {
            processLock.unlock();
        }
    }

    private void b() {
        String productVersion = UpgradeHelper.getInstance(this.b).getProductVersion();
        if (!TextUtils.isEmpty(productVersion)) {
            StringBuilder sb = new StringBuilder("metainfos.cfg_");
            this.j = sb.append(productVersion).toString();
            StringBuilder sb2 = new StringBuilder("metainfos-ext.cfg_");
            this.k = sb2.append(productVersion).toString();
            TraceLogger.i(a, "cfgFileName:" + this.j + " extCfgFileName:" + this.k);
        }
        boolean isLite = LiteProcessInfo.g(this.b).isCurrentProcessALiteProcess();
        String[] mapList = LiteProcessInfo.getLiteBundleList();
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < mapList.length; i2++) {
            hashSet.add(mapList[i2]);
        }
        Log.i("mytest", "mList: " + hashSet.size());
        long start = System.currentTimeMillis();
        try {
            Map<String, String> reusedBundleLocations = LauncherApplicationAgent.getmBundleContext().getReusedBundleLocations();
            TraceLogger.i(a, "updateReusedBundles:" + StringUtil.map2String(reusedBundleLocations));
            if (reusedBundleLocations != null && !reusedBundleLocations.isEmpty()) {
                updateDescriptionsFromMetaInfoCfg(null, reusedBundleLocations.keySet(), reusedBundleLocations, true);
            }
        } catch (Throwable e2) {
            TraceLogger.e(a, "updateReusedBundles fail", e2);
        }
        boolean saveCfg = true;
        if (LogUtil.isDebug()) {
            saveCfg = false;
        }
        try {
            int appVersionResId = this.b.getResources().getIdentifier("app_version", ResUtils.STRING, this.b.getPackageName());
            if (appVersionResId != 0) {
                String resDefVersion = this.b.getResources().getString(appVersionResId);
                String classDefVersion = (String) getClass().getClassLoader().loadClass(this.b.getPackageName() + ".BuildConfig").getField("app_version").get(null);
                if (!TextUtils.isEmpty(resDefVersion) && !resDefVersion.equals(classDefVersion)) {
                    TraceLogger.e(a, "description cfg is stale, resDefVersion" + resDefVersion + " vs " + classDefVersion);
                    FrameworkMonitor.getInstance(this.b).handleDescriptionCfgStale(resDefVersion, classDefVersion);
                    saveCfg = false;
                }
            }
        } catch (Throwable e3) {
            TraceLogger.w(a, "read appVersion fail:" + e3.getMessage());
        }
        Map descriptionsMap = a(saveCfg);
        long cost = System.currentTimeMillis() - start;
        if (descriptionsMap != null) {
            synchronized (this.c) {
                this.c.addAll(descriptionsMap.keySet());
            }
            for (String bundleName : descriptionsMap.keySet()) {
                if (isLite) {
                    if (hashSet.contains(bundleName)) {
                        Log.i("mytest", "pass bundle: " + bundleName);
                    }
                }
                List<MicroDescription> descs = descriptionsMap.get(bundleName);
                if (descs != null) {
                    for (MicroDescription desc : descs) {
                        addDescription(bundleName, desc);
                    }
                }
            }
            TraceLogger.i(a, "loadDescriptionsFromCfg, count:" + descriptionsMap.size() + ",cost:" + cost + ",keySet:" + descriptionsMap.keySet());
        }
    }

    public synchronized Set<String> getBundleSet() {
        Set rBundleSet;
        rBundleSet = new HashSet();
        synchronized (this.c) {
            rBundleSet.addAll(this.c);
        }
        return rBundleSet;
    }

    public void addDescription(String bundleName, MicroDescription<?> description) {
        if (TextUtils.isEmpty(bundleName) || description == null || description.getClassName() == null) {
            TraceLogger.e(a, bundleName + ",invalid description:" + description);
        } else if (description instanceof ApplicationDescription) {
            ApplicationDescription app = (ApplicationDescription) description;
            if (app.getAppId() == null) {
                TraceLogger.e(a, bundleName + ",invalid app:" + app);
                return;
            }
            Map applicationDescriptions = this.d.get(bundleName);
            if (applicationDescriptions == null) {
                applicationDescriptions = new HashMap();
                this.d.put(bundleName, applicationDescriptions);
            }
            if (applicationDescriptions.put(app.getAppId(), app) != null) {
                TraceLogger.e(a, bundleName + ",duplicate app:" + app.getClassName());
            }
        } else if (description instanceof ServiceDescription) {
            ServiceDescription service = (ServiceDescription) description;
            if (service.getInterfaceClass() == null) {
                TraceLogger.e(a, bundleName + ",invalid service:" + service);
                return;
            }
            Map serviceDescriptions = this.e.get(bundleName);
            if (serviceDescriptions == null) {
                serviceDescriptions = new HashMap();
                this.e.put(bundleName, serviceDescriptions);
            }
            if (serviceDescriptions.put(service.getInterfaceClass(), service) != null) {
                TraceLogger.e(a, bundleName + ",duplicate service:" + service.getClassName());
            }
        } else if (description instanceof BroadcastReceiverDescription) {
            BroadcastReceiverDescription receiver = (BroadcastReceiverDescription) description;
            if (receiver.getMsgCode() == null || receiver.getMsgCode().length == 0) {
                TraceLogger.e(a, bundleName + ",invalid receiver:" + receiver);
                return;
            }
            List receiverDescriptions = this.f.get(bundleName);
            if (receiverDescriptions == null) {
                receiverDescriptions = new ArrayList();
                this.f.put(bundleName, receiverDescriptions);
            }
            receiverDescriptions.add(receiver);
        } else if (description instanceof ValveDescription) {
            ValveDescription valve = (ValveDescription) description;
            if (valve.getPipelineName() == null) {
                TraceLogger.e(a, bundleName + ",invalid valve:" + valve);
                return;
            }
            List valveDescriptions = this.g.get(bundleName);
            if (valveDescriptions == null) {
                valveDescriptions = new ArrayList();
                this.g.put(bundleName, valveDescriptions);
            }
            valveDescriptions.add(valve);
        } else if (!(description instanceof PackageDescription)) {
        } else {
            if (PackageDescription.TYPE_LAZY_BUNDLE.equals(description.getClassName())) {
                Set lazy_detail_set = ((PackageDescription) description).getInfoSet();
                if (lazy_detail_set == null || lazy_detail_set.size() <= 0) {
                    TraceLogger.e(a, bundleName + ", invalid lazy_bundle info");
                    return;
                }
                Set targetDetailSet = this.l.get(bundleName);
                if (targetDetailSet == null) {
                    targetDetailSet = new HashSet();
                    this.l.put(bundleName, targetDetailSet);
                }
                targetDetailSet.addAll(lazy_detail_set);
            } else if ("package_name".equals(description.getClassName())) {
                PackageDescription packageDes = (PackageDescription) description;
                if (packageDes.getInfo() == null || packageDes.getInfo().length <= 0) {
                    TraceLogger.e(a, bundleName + ", invalid package_name info");
                    return;
                }
                List packageDescriptions = this.h.get(bundleName);
                if (packageDescriptions == null) {
                    packageDescriptions = new ArrayList();
                    this.h.put(bundleName, packageDescriptions);
                }
                packageDescriptions.add(packageDes);
            }
        }
    }

    public List<MicroDescription<?>> getDescription(String bundleName, boolean containApplication, boolean containService, boolean containReceiver, boolean containValve) {
        if (bundleName == null) {
            return null;
        }
        List foundDescs = null;
        if (containApplication) {
            Map apps = this.d.get(bundleName);
            if (apps != null) {
                foundDescs = new ArrayList();
                foundDescs.addAll(apps.values());
            }
        }
        if (containService) {
            Map services = this.e.get(bundleName);
            if (services != null) {
                if (foundDescs == null) {
                    foundDescs = new ArrayList();
                }
                foundDescs.addAll(services.values());
            }
        }
        if (containReceiver) {
            List receivers = this.f.get(bundleName);
            if (receivers != null) {
                if (foundDescs == null) {
                    foundDescs = new ArrayList();
                }
                foundDescs.addAll(receivers);
            }
        }
        if (!containValve) {
            return foundDescs;
        }
        List valves = this.g.get(bundleName);
        if (valves == null) {
            return foundDescs;
        }
        if (foundDescs == null) {
            foundDescs = new ArrayList();
        }
        foundDescs.addAll(valves);
        return foundDescs;
    }

    private Map<String, List<MicroDescription<?>>> a(boolean saveCfg) {
        MetaInfoOperator metaInfoOperator = new MetaInfoOperator(this.b);
        if (MetaInfoCfg.getInstance().hasDescriptionsSave()) {
            File extCfgFile = new File(this.b.getDir("plugins", 0), this.k);
            Map extCfgMap = null;
            if (extCfgFile.exists()) {
                try {
                    extCfgMap = metaInfoOperator.readMetaInfoCfg(extCfgFile);
                } catch (Throwable tr) {
                    TraceLogger.e(a, "readMetaInfoExtCfg fail from " + extCfgFile.getAbsolutePath(), tr);
                    extCfgFile.delete();
                }
            }
            Map descriptionMap = a((String) UCCore.OPTION_LOAD_KERNEL_TYPE);
            a(descriptionMap, extCfgMap);
            if (!saveCfg || extCfgMap == null || extCfgMap.isEmpty()) {
                return descriptionMap;
            }
            try {
                metaInfoOperator.writeMetaInfoCfg(extCfgMap, extCfgFile);
                return descriptionMap;
            } catch (Throwable tr2) {
                TraceLogger.e(a, "writeMetaInfoExtCfg to file", tr2);
                return descriptionMap;
            }
        } else {
            File cfgFile = new File(this.b.getDir("plugins", 0), this.j);
            if (cfgFile.exists()) {
                try {
                    Map descriptionMap2 = metaInfoOperator.readMetaInfoCfg(cfgFile);
                    if (descriptionMap2 != null && !descriptionMap2.isEmpty()) {
                        return descriptionMap2;
                    }
                } catch (Throwable e2) {
                    TraceLogger.e(a, "readMetaInfoCfg fail from " + cfgFile.getAbsolutePath(), e2);
                    cfgFile.delete();
                }
            }
            Map descriptionMap3 = a((String) UCCore.OPTION_LOAD_KERNEL_TYPE);
            if (!saveCfg || descriptionMap3.isEmpty()) {
                return descriptionMap3;
            }
            try {
                metaInfoOperator.writeMetaInfoCfg(descriptionMap3, cfgFile);
                return descriptionMap3;
            } catch (Throwable e3) {
                TraceLogger.e(a, "writeMetaInfoCfg to file", e3);
                return descriptionMap3;
            }
        }
    }

    private static void a(Map<String, List<MicroDescription<?>>> oriMap, Map<String, List<MicroDescription<?>>> extMap) {
        if (extMap != null && !extMap.isEmpty() && oriMap != null) {
            oriMap.putAll(extMap);
        }
    }

    private static Map<String, List<MicroDescription<?>>> b(Map<String, List<MicroDescription<?>>> currentMap, Map<String, List<MicroDescription<?>>> oriMap) {
        if (currentMap == null) {
            return null;
        }
        if (oriMap == null) {
            return currentMap;
        }
        Map extMap = new ConcurrentHashMap();
        for (Entry currentEntry : currentMap.entrySet()) {
            String currentBundleName = (String) currentEntry.getKey();
            List<MicroDescription> currentDescriptions = (List) currentEntry.getValue();
            if (!TextUtils.isEmpty(currentBundleName) && currentDescriptions != null && !currentDescriptions.isEmpty()) {
                List oriDescriptions = oriMap.get(currentBundleName);
                if (oriDescriptions == null || oriDescriptions.isEmpty()) {
                    extMap.put(currentBundleName, currentDescriptions);
                } else {
                    boolean allSame = true;
                    for (MicroDescription currentDesItem : currentDescriptions) {
                        if (currentDesItem != null) {
                            boolean foundAndSame = false;
                            Iterator it = oriDescriptions.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                MicroDescription oriDesItem = (MicroDescription) it.next();
                                if (oriDesItem != null && oriDesItem.equals(currentDesItem)) {
                                    foundAndSame = true;
                                    break;
                                }
                            }
                            allSame &= foundAndSame;
                        }
                    }
                    if (!allSame) {
                        extMap.put(currentBundleName, currentDescriptions);
                    }
                }
            }
        }
        return extMap;
    }

    private Map<String, List<MicroDescription<?>>> a(String reason) {
        final Map descriptionMap = new HashMap();
        final StringBuilder readerMsg = new StringBuilder();
        if (MetaInfoCfg.getInstance().hasDescriptionsSave()) {
            TraceLogger.i(a, (String) "loadOriginalDescriptions by MetaInfoCfg");
            descriptionMap.clear();
            Map javaDescriptionMap = MetaInfoCfg.getInstance().getDescriptions();
            if (javaDescriptionMap != null) {
                descriptionMap.putAll(javaDescriptionMap);
            }
        } else {
            TraceLogger.i(a, (String) "loadOriginalDescriptions by metainfo.cfg");
            new ApkFileReader(new Printer() {
                public void println(String x) {
                    readerMsg.append(x).append(MultipartUtility.LINE_FEED);
                }
            }).readAssets(this.b, MetaInfoOperator.META_INFO_CFG, new ApkFileInputStreamCallback() {
                public boolean onInputStream(InputStream inputStream) {
                    try {
                        Map readFromAssert = new MetaInfoOperator(DescriptionManagerImpl.this.b).readMetaInfoCfg(inputStream);
                        if (readFromAssert != null && !readFromAssert.isEmpty()) {
                            descriptionMap.clear();
                            descriptionMap.putAll(readFromAssert);
                            return true;
                        }
                    } catch (Throwable e) {
                        TraceLogger.e(DescriptionManagerImpl.a, "readMetaInfoCfg fail from asset", e);
                    }
                    return false;
                }
            });
        }
        if (descriptionMap.isEmpty()) {
            TraceLogger.e(a, (String) "loadDescriptionsFromCfg fail!");
            FrameworkMonitor.getInstance(this.b).handleDescriptionCfgLoadFail(reason, readerMsg.toString());
        }
        return descriptionMap;
    }

    public void updateDescriptionsFromMetaInfoCfg(Collection<String> revertBundleNames, Collection<String> addedBundleNames, boolean effective) {
        updateDescriptionsFromMetaInfoCfg(revertBundleNames, addedBundleNames, null, effective);
    }

    public void updateDescriptionsFromMetaInfoCfg(Collection<String> revertBundleNames, Collection<String> addedBundleNames, Map<String, String> reusedBundleLocations, boolean effective) {
        ProcessLock processLock = new ProcessLock(this.b.getCacheDir() + "/.updateDescriptionsFromMetaInfoCfg.lock");
        try {
            processLock.lock();
            a(revertBundleNames, addedBundleNames, reusedBundleLocations, effective);
        } finally {
            processLock.unlock();
        }
    }

    public void preload() {
        for (Entry serviceEntries : this.e.entrySet()) {
            Collection<ServiceDescription> serviceCollections = ((Map) serviceEntries.getValue()).values();
            for (ServiceDescription description : serviceCollections) {
                a((MicroDescription) description, (String) serviceEntries.getKey());
            }
            preloadDescriptionClass(serviceCollections);
        }
        for (Entry valveEntries : this.g.entrySet()) {
            Collection<ValveDescription> valveCollection = (Collection) valveEntries.getValue();
            for (ValveDescription description2 : valveCollection) {
                a((MicroDescription) description2, (String) valveEntries.getKey());
            }
            preloadDescriptionClass(valveCollection);
        }
        for (Entry receiverEntries : this.f.entrySet()) {
            Collection<BroadcastReceiverDescription> receiverCollection = (Collection) receiverEntries.getValue();
            for (BroadcastReceiverDescription description3 : receiverCollection) {
                a((MicroDescription) description3, (String) receiverEntries.getKey());
            }
            preloadDescriptionClass(receiverCollection);
        }
    }

    public static void preloadDescriptionClass(Collection<? extends MicroDescription<?>> descriptions) {
        if (descriptions != null) {
            for (MicroDescription description : descriptions) {
                if (description != null && (!(description instanceof ServiceDescription) || !((ServiceDescription) description).isLazy())) {
                    try {
                        description.getClazz();
                    } catch (Throwable e2) {
                        TraceLogger.w(a, "preload description failed!, className:" + description.getClassName(), e2);
                    }
                }
            }
        }
    }

    private void a(Collection<String> revertBundleNames, Collection<String> addedBundleNames, Map<String, String> reusedBundleLocations, boolean effective) {
        TraceLogger.i(a, "updateMetaInfoCfg,revertBundleNames=" + revertBundleNames + ",addedBundleNames=" + addedBundleNames + ",effective=" + effective);
        MetaInfoOperator metaInfoOperator = new MetaInfoOperator(this.b);
        boolean needSave = false;
        Map currentDescriptionMap = a(false);
        if (currentDescriptionMap == null) {
            TraceLogger.e(a, (String) "currentDescriptionMap is null");
            return;
        }
        if (revertBundleNames != null && !revertBundleNames.isEmpty()) {
            Map originalDescriptionMap = a((String) "update");
            for (String revertBundle : revertBundleNames) {
                if (currentDescriptionMap.remove(revertBundle) != null) {
                    needSave = true;
                }
                if (effective) {
                    this.d.remove(revertBundle);
                    this.e.remove(revertBundle);
                    this.f.remove(revertBundle);
                    this.g.remove(revertBundle);
                    this.h.remove(revertBundle);
                }
                if (addedBundleNames == null || !addedBundleNames.contains(revertBundle)) {
                    List<MicroDescription> originalDescriptions = originalDescriptionMap.get(revertBundle);
                    if (originalDescriptions != null) {
                        needSave = true;
                        currentDescriptionMap.put(revertBundle, originalDescriptions);
                        if (effective) {
                            for (MicroDescription desc : originalDescriptions) {
                                addDescription(revertBundle, desc);
                            }
                        }
                    }
                }
            }
        }
        if (addedBundleNames != null && !addedBundleNames.isEmpty()) {
            for (String addedBundle : addedBundleNames) {
                File bundleFile = null;
                if (reusedBundleLocations != null) {
                    try {
                        if (!reusedBundleLocations.isEmpty()) {
                            bundleFile = new File(reusedBundleLocations.get(addedBundle));
                            if (bundleFile != null || !bundleFile.exists()) {
                                FrameworkMonitor.getInstance(this.b).handleBundleLocationNotFound(addedBundle, bundleFile);
                                TraceLogger.e(a, "bundle location not found for : " + addedBundle);
                            } else {
                                if (currentDescriptionMap.remove(addedBundle) != null) {
                                    needSave = true;
                                }
                                if (effective) {
                                    this.d.remove(addedBundle);
                                    this.e.remove(addedBundle);
                                    this.f.remove(addedBundle);
                                    this.g.remove(addedBundle);
                                    this.h.remove(addedBundle);
                                }
                                List<MicroDescription<?>> list = null;
                                try {
                                    list = metaInfoOperator.readMetaInfoFromZipFile(bundleFile);
                                } catch (Throwable e2) {
                                    TraceLogger.e(a, "readMetaInfoFromZipFile", e2);
                                }
                                if (list != null) {
                                    needSave = true;
                                    currentDescriptionMap.put(addedBundle, list);
                                    if (effective) {
                                        for (MicroDescription<?> desc2 : list) {
                                            addDescription(addedBundle, desc2);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable t) {
                        TraceLogger.e(a, t);
                    }
                }
                LauncherApplicationAgent.getmBundleContext().getBundleLocation(addedBundle);
                if (bundleFile != null) {
                }
                FrameworkMonitor.getInstance(this.b).handleBundleLocationNotFound(addedBundle, bundleFile);
                TraceLogger.e(a, "bundle location not found for : " + addedBundle);
            }
        }
        if (!needSave) {
            return;
        }
        if (MetaInfoCfg.getInstance().hasDescriptionsSave()) {
            try {
                File extCfgFile = new File(this.b.getDir("plugins", 0), this.k);
                Map extCfgMap = b(currentDescriptionMap, a((String) PhotoMenu.TAG_SAVE));
                if (extCfgMap == null || extCfgMap.isEmpty()) {
                    boolean result = extCfgFile.delete();
                    TraceLogger.i(a, "delete MetaInfoExtCfg:" + result);
                    if (!result) {
                        extCfgFile.deleteOnExit();
                        return;
                    }
                    return;
                }
                metaInfoOperator.writeMetaInfoCfg(extCfgMap, extCfgFile);
                TraceLogger.i(a, "writeMetaInfoExtCfg:" + extCfgMap.keySet());
            } catch (IOException e3) {
                TraceLogger.e(a, "writeMetaInfoCfg fail", e3);
            }
        } else {
            try {
                metaInfoOperator.writeMetaInfoCfg(currentDescriptionMap, new File(this.b.getDir("plugins", 0), this.j));
                TraceLogger.i(a, "writeMetaInfoCfg:" + currentDescriptionMap.keySet());
            } catch (IOException e4) {
                TraceLogger.e(a, "writeMetaInfoCfg fail", e4);
            }
        }
    }

    public void addDescriptionsFromMetaInfo(String bundleName, BaseMetaInfo metaInfo) {
        if (metaInfo != null) {
            int appCount = 0;
            if (metaInfo.getApplications() != null) {
                appCount = metaInfo.getApplications().size();
                for (ApplicationDescription applicationDescription : metaInfo.getApplications()) {
                    addDescription(bundleName, applicationDescription);
                }
            }
            int serviceCount = 0;
            if (metaInfo.getServices() != null) {
                serviceCount = metaInfo.getServices().size();
                for (ServiceDescription serviceDescription : metaInfo.getServices()) {
                    addDescription(bundleName, serviceDescription);
                }
            }
            int valveCount = 0;
            if (metaInfo.getValves() != null) {
                valveCount = metaInfo.getValves().size();
                for (ValveDescription valveDescription : metaInfo.getValves()) {
                    addDescription(bundleName, valveDescription);
                }
            }
            int receiverCount = 0;
            if (metaInfo.getBroadcastReceivers() != null) {
                receiverCount = metaInfo.getBroadcastReceivers().size();
                for (BroadcastReceiverDescription receiverDescription : metaInfo.getBroadcastReceivers()) {
                    addDescription(bundleName, receiverDescription);
                }
            }
            if (!LogUtil.isDebug()) {
                return;
            }
            if (appCount == 0 && serviceCount == 0 && valveCount == 0 && receiverCount == 0) {
                TraceLogger.i(a, "empty descriptions from MetaInfo, bundle=" + bundleName);
            } else {
                TraceLogger.i(a, "addDescriptionsFromMetaInfo bundle=" + bundleName + ",(app:" + appCount + ",service:" + serviceCount + ",valve:" + valveCount + ",receiver:" + receiverCount + ")");
            }
        }
    }

    public List<ServiceDescription> findServiceDescription(String serviceNameFilter) {
        List foundServiceDescriptions = new ArrayList();
        Iterator<String> it = this.e.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String bundleName = it.next();
            Map descriptions = this.e.get(bundleName);
            if (descriptions != null) {
                if (serviceNameFilter != null) {
                    ServiceDescription serviceDescription = (ServiceDescription) descriptions.get(serviceNameFilter);
                    if (serviceDescription != null) {
                        this.i.add(bundleName);
                        a((MicroDescription) serviceDescription, bundleName);
                        foundServiceDescriptions.add(serviceDescription);
                        break;
                    }
                } else {
                    for (ServiceDescription serviceDescription2 : descriptions.values()) {
                        this.i.add(bundleName);
                        a((MicroDescription) serviceDescription2, bundleName);
                        foundServiceDescriptions.add(serviceDescription2);
                    }
                }
            }
        }
        if (LogUtil.isDebug()) {
            TraceLogger.v(a, "findServiceDescription:" + serviceNameFilter + "(" + foundServiceDescriptions.size() + ")");
        }
        if (foundServiceDescriptions.isEmpty()) {
            FrameworkMonitor.getInstance(this.b).handleServiceNotFound(serviceNameFilter);
            TraceLogger.e(a, "service not found:" + serviceNameFilter);
        }
        return foundServiceDescriptions;
    }

    public List<ApplicationDescription> findApplicationDescription(String appIdFilter) {
        List foundApplicationDescriptions = new ArrayList();
        Iterator<String> it = this.d.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String bundleName = it.next();
            Map descriptions = this.d.get(bundleName);
            if (descriptions != null) {
                if (appIdFilter != null) {
                    ApplicationDescription applicationDescription = (ApplicationDescription) descriptions.get(appIdFilter);
                    if (applicationDescription != null) {
                        this.i.add(bundleName);
                        a((MicroDescription) applicationDescription, bundleName);
                        foundApplicationDescriptions.add(applicationDescription);
                        break;
                    }
                } else {
                    for (ApplicationDescription applicationDescription2 : descriptions.values()) {
                        this.i.add(bundleName);
                        a((MicroDescription) applicationDescription2, bundleName);
                        foundApplicationDescriptions.add(applicationDescription2);
                    }
                }
            }
        }
        if (LogUtil.isDebug()) {
            TraceLogger.v(a, "findApplicationDescription:" + appIdFilter + "(" + foundApplicationDescriptions.size() + ")");
        }
        return foundApplicationDescriptions;
    }

    public List<ValveDescription> findValveDescription(String pipelineFilter) {
        List foundValveDescriptions = new ArrayList();
        for (String bundleName : this.g.keySet()) {
            if (!isLazyBundle(bundleName) || this.i.contains(bundleName)) {
                List<ValveDescription> descriptions = this.g.get(bundleName);
                if (descriptions != null) {
                    for (ValveDescription desc : descriptions) {
                        if (pipelineFilter == null || pipelineFilter.equals(desc.getPipelineName())) {
                            a((MicroDescription) desc, bundleName);
                            foundValveDescriptions.add(desc);
                        }
                    }
                }
            } else if (LogUtil.isDebug()) {
                TraceLogger.i(a, "findValveDescription ignore:" + bundleName);
            }
        }
        if (LogUtil.isDebug()) {
            TraceLogger.v(a, "findValveDescription:" + pipelineFilter + "(" + foundValveDescriptions.size() + ")");
        }
        if (foundValveDescriptions.isEmpty()) {
            TraceLogger.e(a, "valve not found:" + pipelineFilter);
        }
        return foundValveDescriptions;
    }

    public List<BroadcastReceiverDescription> findBroadcastReceiverDescription(String msgCodeFilter) {
        List foundBroadcastReceiverDescriptions = new ArrayList();
        for (String bundleName : this.f.keySet()) {
            if (!isLazyBundle(bundleName) || this.i.contains(bundleName)) {
                List<BroadcastReceiverDescription> descriptions = this.f.get(bundleName);
                if (descriptions != null) {
                    for (BroadcastReceiverDescription desc : descriptions) {
                        String[] msgCodes = desc.getMsgCode();
                        if (msgCodes != null && (msgCodeFilter == null || Arrays.asList(msgCodes).contains(msgCodeFilter))) {
                            a((MicroDescription) desc, bundleName);
                            foundBroadcastReceiverDescriptions.add(desc);
                        }
                    }
                }
            } else if (LogUtil.isDebug()) {
                TraceLogger.i(a, "findBroadcastReceiverDescription ignore:" + bundleName);
            }
        }
        if (LogUtil.isDebug()) {
            TraceLogger.v(a, "findBroadcastReceiverDescription:" + msgCodeFilter + "(" + foundBroadcastReceiverDescriptions.size() + ")");
        }
        return foundBroadcastReceiverDescriptions;
    }

    private void a(MicroDescription microDescription, String bundleName) {
        if (microDescription != null && microDescription.getClassLoader() == null) {
            try {
                ClassLoader classLoader = LauncherApplicationAgent.getInstance().getBundleContext().findClassLoaderByBundleName(bundleName);
                if (classLoader == null) {
                    throw new IllegalStateException("classloader not found for:" + bundleName);
                }
                microDescription.setClassLoader(classLoader);
            } catch (Exception e2) {
                FrameworkMonitor.getInstance(this.b).handleBundleClassLoaderNotFound(bundleName, e2);
                TraceLogger.e(a, (Throwable) e2);
            }
        }
    }

    public boolean isLazyBundle(String bundleName) {
        if (bundleName == null) {
            return false;
        }
        Map lazyBundlesMap = LauncherApplicationAgent.getInstance().getLazyBundles();
        if (lazyBundlesMap == null || !lazyBundlesMap.containsKey(bundleName)) {
            return false;
        }
        return true;
    }

    public String getBundleNameByAppId(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        for (String bundleName : this.d.keySet()) {
            Map descriptions = this.d.get(bundleName);
            if (descriptions != null && descriptions.get(appId) != null) {
                TraceLogger.i(a, "getBundleNameByAppId:" + appId + "->" + bundleName);
                return bundleName;
            }
        }
        return b(appId);
    }

    public String getBundleNameByServiceName(String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }
        for (String bundleName : this.e.keySet()) {
            Map descriptions = this.e.get(bundleName);
            if (descriptions != null && descriptions.get(serviceName) != null) {
                TraceLogger.i(a, "getBundleNameByServiceName:" + serviceName + "->" + bundleName);
                return bundleName;
            }
        }
        return b(serviceName);
    }

    public Set<String> getPackagesByBundleName(String bundleName) {
        Set packageNames = null;
        if (!TextUtils.isEmpty(bundleName)) {
            List<PackageDescription> packageDescriptions = this.h.get(bundleName);
            if (packageDescriptions != null && !packageDescriptions.isEmpty()) {
                packageNames = new HashSet();
                for (PackageDescription infoSet : packageDescriptions) {
                    Set partPackageSet = infoSet.getInfoSet();
                    if (partPackageSet != null && partPackageSet.size() > 0) {
                        packageNames.addAll(partPackageSet);
                    }
                }
            }
        }
        return packageNames;
    }

    private static String b(String componentName) {
        Map lazyBundlesMap = LauncherApplicationAgent.getInstance().getLazyBundles();
        if (lazyBundlesMap != null) {
            for (String bundle : lazyBundlesMap.keySet()) {
                if (lazyBundlesMap.get(bundle).contains(componentName)) {
                    TraceLogger.i(a, "getBundleNameFromLegcyCfg:" + componentName + "->" + bundle);
                    return bundle;
                }
            }
        }
        return null;
    }

    public Map<String, Set<String>> getLazyBundles() {
        if (this.l.size() > 0) {
            return this.l;
        }
        return null;
    }
}
