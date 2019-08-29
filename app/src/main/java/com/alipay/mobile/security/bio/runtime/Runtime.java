package com.alipay.mobile.security.bio.runtime;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.runtime.FrameworkDesc.ConfigDesc;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioMetaInfo;
import com.alipay.mobile.security.bio.service.BioServiceDescription;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.bio.service.local.dynamicrelease.DynamicReleaseService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Runtime {
    public static final String TAG = "Runtime";
    private static Method a;
    private static Method b;
    private static Object c;
    private static FrameworkDesc d;
    private static HashMap<String, ModuleDesc> e;
    private static Boolean f = null;

    static {
        c = null;
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.framework.LauncherApplicationAgent");
            Method declaredMethod = cls.getDeclaredMethod("getmBundleContext", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(cls, new Object[0]);
            c = invoke;
            Class<?> cls2 = invoke.getClass();
            Method method = cls2.getMethod("getResourcesByBundle", new Class[]{String.class});
            a = method;
            method.setAccessible(true);
            Method method2 = cls2.getMethod("findClassLoaderByBundleName", new Class[]{String.class});
            b = method2;
            method2.setAccessible(true);
        } catch (Throwable th) {
            BioLog.e("Failed to reflect Quinox's Bundle APIs : " + th.getMessage());
        }
    }

    public static boolean isRunningOnQuinox(Context context) {
        if (f == null) {
            synchronized ("com.alipay.mobile.quinox.LauncherApplication") {
                try {
                    if (f == null) {
                        f = Boolean.valueOf("com.alipay.mobile.quinox.LauncherApplication".equals(context.getApplicationContext().getClass().getName()));
                        BioLog.d("Runtime : isOnQuinox=" + f);
                    }
                }
            }
        }
        return f.booleanValue();
    }

    public static ClassLoader getClassLoaderByBundleName(String str) {
        try {
            return (ClassLoader) b.invoke(c, new Object[]{str});
        } catch (Throwable th) {
            BioLog.e(th.toString());
            return null;
        }
    }

    public static Resources getResourcesByBundleName(String str) {
        try {
            return (Resources) a.invoke(c, new Object[]{str});
        } catch (Throwable th) {
            BioLog.e(th.toString());
            return null;
        }
    }

    public static List<BioMetaInfo> getBioMetaInfoList(Context context, BioServiceManager bioServiceManager) {
        ArrayList arrayList = new ArrayList();
        for (ModuleDesc next : e.values()) {
            if (next.mBioMetaInfoList != null && !next.mBioMetaInfoList.isEmpty()) {
                arrayList.addAll(next.mBioMetaInfoList);
            }
        }
        return arrayList;
    }

    public static BioServiceDescription getBioServiceDescriptionByInterface(Context context, String str) {
        a(context, isRunningOnQuinox(context));
        return a(str);
    }

    private static BioServiceDescription a(String str) {
        BioServiceDescription bioServiceDescription = null;
        Iterator<ModuleDesc> it = e.values().iterator();
        while (true) {
            BioServiceDescription bioServiceDescription2 = bioServiceDescription;
            if (!it.hasNext()) {
                return bioServiceDescription2;
            }
            ModuleDesc next = it.next();
            if (next.mBioServiceDescription != null && !next.mBioServiceDescription.isEmpty()) {
                Iterator<BioServiceDescription> it2 = next.mBioServiceDescription.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    bioServiceDescription = it2.next();
                    if (TextUtils.equals(str, bioServiceDescription.getInterfaceName())) {
                        break;
                    }
                }
            }
            bioServiceDescription = bioServiceDescription2;
            if (bioServiceDescription != null) {
                return bioServiceDescription;
            }
        }
    }

    public static void getLocalService(Context context, HashMap<String, LocalService> hashMap, HashMap<String, BioServiceDescription> hashMap2) {
        a(context, isRunningOnQuinox(context));
        ArrayList<BioServiceDescription> arrayList = new ArrayList<>();
        for (ModuleDesc next : e.values()) {
            if (next.mBioServiceDescription != null && !next.mBioServiceDescription.isEmpty()) {
                arrayList.addAll(next.mBioServiceDescription);
            }
        }
        for (BioServiceDescription bioServiceDescription : arrayList) {
            if (bioServiceDescription.isLazy()) {
                if (hashMap2 != null) {
                    hashMap2.put(bioServiceDescription.getInterfaceName(), bioServiceDescription);
                }
            } else if (hashMap != null) {
                try {
                    hashMap.put(bioServiceDescription.getInterfaceName(), (LocalService) bioServiceDescription.getClazz().newInstance());
                } catch (Throwable th) {
                    BioLog.e(th);
                }
            }
        }
    }

    public static Class<?> loadClass(Context context, boolean z, String str, String str2) {
        Class<?> cls = null;
        HashSet hashSet = new HashSet();
        if (z) {
            ClassLoader classLoaderByBundleName = getClassLoaderByBundleName(str2);
            if (classLoaderByBundleName != null) {
                try {
                    cls = classLoaderByBundleName.loadClass(str);
                } catch (ClassNotFoundException e2) {
                    hashSet.add(classLoaderByBundleName);
                    BioLog.w("Failed to loadClass(" + str + ") by " + classLoaderByBundleName);
                }
            }
        }
        if (cls == null) {
            ClassLoader classLoader = Runtime.class.getClassLoader();
            if (!hashSet.contains(classLoader)) {
                try {
                    cls = Class.forName(str);
                } catch (ClassNotFoundException e3) {
                    hashSet.add(classLoader);
                    BioLog.w("Failed to loadClass(" + str + ") by " + classLoader);
                }
            }
        }
        if (cls != null || context == null) {
            return cls;
        }
        ClassLoader classLoader2 = context.getClassLoader();
        if (classLoader2 == null || hashSet.contains(classLoader2)) {
            return cls;
        }
        try {
            return classLoader2.loadClass(str);
        } catch (ClassNotFoundException e4) {
            BioLog.w("Failed to loadClass(" + str + ") by " + classLoader2);
            return cls;
        }
    }

    public static String getMetaInfos(Context context) {
        int i;
        if (context == null) {
            throw new BioIllegalArgumentException("Context is null");
        }
        a(context, isRunningOnQuinox(context));
        String str = d.frameworkVersion;
        int i2 = 0;
        for (BioMetaInfo next : getBioMetaInfoList(context, null)) {
            Iterator<Long> it = next.getProductIDs().iterator();
            while (true) {
                i = i2;
                if (!it.hasNext()) {
                    break;
                }
                i2 = (int) (Math.pow(2.0d, (double) it.next().longValue()) + ((double) i));
            }
            i2 = i;
            for (BioAppDescription productID : next.getApplications()) {
                long productID2 = productID.getProductID();
                if (-1 != productID2) {
                    i2 = (int) (((double) i2) + Math.pow(2.0d, (double) productID2));
                }
            }
        }
        String valueOf = String.valueOf(i2);
        String str2 = str + ":" + valueOf + "," + a(context);
        BioLog.i("MetaInfo:" + str2);
        return str2;
    }

    public static String getFrameworkVersion(Context context) {
        return d.frameworkVersion;
    }

    private static synchronized void a(Context context, boolean z) {
        synchronized (Runtime.class) {
            if (d == null) {
                d = FrameworkDesc.create(context);
            }
            HashSet<String> hashSet = new HashSet<>();
            if (e == null) {
                e = new HashMap<>(d.configs.size());
                for (ConfigDesc next : d.configs) {
                    ModuleDesc create = ModuleDesc.create(context, z, next);
                    BioLog.i("load : configDesc=" + next + ", moduleDesc=" + create);
                    if (z) {
                        if (next.dev) {
                            if (next.dynamic) {
                                if (create == null) {
                                    BioLog.w("No need to trigger dynamicrelease a dev bundle: " + next);
                                } else {
                                    e.put(next.configFileName, create);
                                }
                            } else if (create == null) {
                                BioLog.w("There is a static dev bundle can't be found: " + next);
                            } else {
                                e.put(next.configFileName, create);
                            }
                        } else if (next.dynamic) {
                            if (create == null) {
                                hashSet.add(next.bundleName);
                            } else {
                                e.put(next.configFileName, create);
                            }
                        } else if (create == null) {
                            throw new RuntimeException("There is a static bundle can't be found: " + next);
                        } else {
                            e.put(next.configFileName, create);
                        }
                    } else if (next.dev) {
                        throw new RuntimeException("On no-quinox, there is a dev bundle: " + next);
                    } else if (next.dynamic) {
                        throw new RuntimeException("On no-quinox, there is a dynamic bundle: " + next);
                    } else if (create == null) {
                        throw new RuntimeException("There is a static bundle can't be found: " + next);
                    } else {
                        e.put(next.configFileName, create);
                    }
                }
            } else if (z) {
                for (ConfigDesc next2 : d.configs) {
                    if (next2.dynamic) {
                        ModuleDesc create2 = ModuleDesc.create(context, true, next2);
                        BioLog.i("reload : configDesc=" + next2 + ", moduleDesc=" + create2);
                        if (create2 == null) {
                            hashSet.add(next2.bundleName);
                        } else {
                            e.put(next2.configFileName, create2);
                        }
                    }
                }
            }
            if (z) {
                DynamicReleaseService dynamicReleaseService = (DynamicReleaseService) BioServiceManager.getLocalService(context, a(DynamicReleaseService.class.getName()));
                if (dynamicReleaseService != null) {
                    for (ConfigDesc next3 : d.configs) {
                        if (next3.dynamic) {
                            dynamicReleaseService.monitorCoverage(next3.bundleName, null);
                        }
                    }
                }
                if (!hashSet.isEmpty()) {
                    BioLog.w("Not exist bundle names: " + StringUtil.collection2String(hashSet));
                    if (dynamicReleaseService == null) {
                        BioLog.e((String) "Failed to get DynamicReleaseService, doesn't find it.");
                    } else {
                        for (String trigDynamicRelease : hashSet) {
                            dynamicReleaseService.trigDynamicRelease(context, trigDynamicRelease);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x005a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.content.Context r9) {
        /*
            r2 = 1
            r7 = 4611686018427387904(0x4000000000000000, double:2.0)
            r3 = 0
            r0 = 0
            int r1 = com.alipay.mobile.security.bio.workspace.Env.getProtocolFormat(r9)
            if (r2 == r1) goto L_0x009a
            r0 = 0
            com.alipay.mobile.security.bio.config.BisRuntimeInfoEnum r2 = com.alipay.mobile.security.bio.config.BisRuntimeInfoEnum.PROTOCOL_FORMAT
            int r2 = r2.getProductID()
            double r4 = (double) r2
            double r4 = java.lang.Math.pow(r7, r4)
            double r0 = r0 + r4
            int r0 = (int) r0
            r2 = r0
        L_0x001b:
            java.lang.String r0 = "android-phone-securitycommon-eyemetric"
            java.lang.ClassLoader r1 = getClassLoaderByBundleName(r0)
            if (r1 == 0) goto L_0x0083
            java.lang.Class<com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService> r0 = com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService.class
            com.alipay.mobile.security.bio.service.local.LocalService r0 = com.alipay.mobile.security.bio.service.BioServiceManager.getLocalService(r9, r0)
            com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService r0 = (com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService) r0
            if (r0 == 0) goto L_0x0036
            java.lang.String r4 = "DynamicRelease"
            java.lang.String r5 = "BUNDLE"
            java.lang.String r6 = "EYE_METRIC"
            r0.keyBizTrace(r4, r5, r6, r3)
        L_0x0036:
            java.lang.String r4 = "com.alipay.mobile.security.bio.eye.Config"
            java.lang.Class r1 = r1.loadClass(r4)     // Catch:{ Throwable -> 0x0094 }
            if (r1 == 0) goto L_0x0098
            java.lang.String r4 = "getDownLoadStateKey"
            r5 = 0
            java.lang.Class[] r5 = new java.lang.Class[r5]     // Catch:{ Throwable -> 0x0094 }
            java.lang.reflect.Method r4 = r1.getMethod(r4, r5)     // Catch:{ Throwable -> 0x0094 }
            r5 = 1
            r4.setAccessible(r5)     // Catch:{ Throwable -> 0x0094 }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0094 }
            java.lang.Object r1 = r4.invoke(r1, r5)     // Catch:{ Throwable -> 0x0094 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0094 }
        L_0x0054:
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 != 0) goto L_0x0083
            java.lang.String r1 = com.alipay.mobile.security.bio.utils.PreferenceHelper.getValue(r9, r1)
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 != 0) goto L_0x006a
            boolean r1 = java.lang.Boolean.parseBoolean(r1)
            if (r1 == 0) goto L_0x0083
        L_0x006a:
            if (r0 == 0) goto L_0x0075
            java.lang.String r1 = "DynamicRelease"
            java.lang.String r4 = "BUNDLE"
            java.lang.String r5 = "EYE_METRIC_ASSETS_READY"
            r0.keyBizTrace(r1, r4, r5, r3)
        L_0x0075:
            double r0 = (double) r2
            com.alipay.mobile.security.bio.config.BisRuntimeInfoEnum r2 = com.alipay.mobile.security.bio.config.BisRuntimeInfoEnum.ASSETS_READY
            int r2 = r2.getProductID()
            double r2 = (double) r2
            double r2 = java.lang.Math.pow(r7, r2)
            double r0 = r0 + r2
            int r2 = (int) r0
        L_0x0083:
            com.alipay.mobile.security.bio.service.local.language.LanguageService r0 = new com.alipay.mobile.security.bio.service.local.language.LanguageService
            r0.<init>()
            int r0 = r0.getCurrentLanguage()
            int r0 = r0 * 4
            int r0 = r0 + r2
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0094:
            r1 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.w(r1)
        L_0x0098:
            r1 = r3
            goto L_0x0054
        L_0x009a:
            r2 = r0
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.runtime.Runtime.a(android.content.Context):java.lang.String");
    }

    public static boolean startActivity(Intent intent) {
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.framework.LauncherApplicationAgent");
            Method method = cls.getMethod("getInstance", new Class[0]);
            method.setAccessible(true);
            Object invoke = method.invoke(cls, new Object[0]);
            Method method2 = cls.getMethod("getMicroApplicationContext", new Class[0]);
            method2.setAccessible(true);
            Object invoke2 = method2.invoke(invoke, new Object[0]);
            Method method3 = invoke2.getClass().getMethod("getTopApplication", new Class[0]);
            method3.setAccessible(true);
            Object invoke3 = method3.invoke(invoke2, new Object[0]);
            Method method4 = invoke2.getClass().getMethod("startExtActivity", new Class[]{Class.forName("com.alipay.mobile.framework.app.MicroApplication"), Intent.class});
            method4.setAccessible(true);
            method4.invoke(invoke2, new Object[]{invoke3, intent});
            return true;
        } catch (Throwable th) {
            BioLog.w(th);
            return false;
        }
    }
}
