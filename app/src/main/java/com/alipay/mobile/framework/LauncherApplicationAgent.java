package com.alipay.mobile.framework;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.quinox.utils.AppState;
import com.alipay.mobile.quinox.utils.Constants;
import com.alipay.mobile.quinox.utils.TimingLogger;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class LauncherApplicationAgent {
    public static boolean NEED_PRELOAD = false;
    public static boolean NEED_SYNC = false;
    private static final String a = PackageDescription.TYPE_LAZY_BUNDLE.concat("s.cfg");
    private static final String b = PackageDescription.TYPE_LAZY_BUNDLE.concat("s_plain.cfg");
    private static CountDownLatch c = new CountDownLatch(1);
    private static LauncherApplicationAgent d;
    private static CountDownLatch e = new CountDownLatch(1);
    private static WaitInitObservable g = new WaitInitObservable();
    private static AtomicBoolean h = new AtomicBoolean(true);
    private static final Object i = new Object();
    private ExceptionHandlerAgent f;
    protected BundleContext mBundleContext;
    protected Application mContext;
    protected Map<String, Set<String>> mLazyBundles;
    protected MicroApplicationContext mMicroApplicationContext;

    @Deprecated
    public interface ExceptionHandlerAgent {
        boolean uncaughtException(Thread thread, Throwable th);
    }

    public static abstract class StandardExceptionHandlerAgent implements ExceptionHandlerAgent {
        @Deprecated
        public boolean uncaughtException(Thread thread, Throwable ex) {
            return false;
        }

        public boolean uncaughtException(UncaughtExceptionHandler defaultHandler, Thread thread, Throwable ex) {
            return uncaughtException(thread, ex);
        }

        public boolean filter(Throwable ex) {
            return false;
        }
    }

    private static class WaitInitObservable extends Observable {
        private WaitInitObservable() {
        }

        public void setChanged() {
            super.setChanged();
        }
    }

    public LauncherApplicationAgent(Application context, Object bundleContext) {
        if (d != null) {
            throw new IllegalStateException("LauncherApplicationAgent must be single instance mode : sInstance=" + d + ", sInstance.getClass().getClassLoader()=" + d.getClass().getClassLoader() + ", LauncherApplicationAgent.class.getClassLoader()=" + LauncherApplicationAgent.class.getClassLoader());
        }
        TraceLogger.d((String) "LaunchApplicationAgent", "new LauncherApplicationAgent(" + context + ", bundleContext=" + bundleContext + ")");
        this.mContext = context;
        this.mBundleContext = new BundleContext(bundleContext);
        try {
            this.mMicroApplicationContext = (MicroApplicationContext) Class.forName("com.alipay.mobile.core.impl.MicroApplicationContextImpl").newInstance();
            d = this;
            TraceLogger.d((String) "LaunchApplicationAgent", "sInstance=" + d.getClass().getName());
            try {
                Field field = context.getClass().getDeclaredField("NEED_SYNC");
                field.setAccessible(true);
                NEED_SYNC = field.getBoolean(null);
            } catch (Throwable tr) {
                TraceLogger.w("LaunchApplicationAgent", "get NEED_SYNC error", tr);
            }
            Log.i("mytest", "NEED_SYNC: " + NEED_SYNC);
            NEED_PRELOAD = AppState.isPreloadActivityLaunch();
            Log.i("mytest", "NEED_PRELOAD: " + NEED_PRELOAD);
            e.countDown();
            TraceLogger.d((String) "LaunchApplicationAgent", (String) "sInitCountDownLatch2.countDown()");
        } catch (Exception e2) {
            TraceLogger.e((String) "LaunchApplicationAgent", (Throwable) e2);
            throw new RuntimeException("Failed to instantiate MicroApplicationContextImpl", e2);
        }
    }

    /* access modifiers changed from: protected */
    public final void setExceptionHandlerAgent(ExceptionHandlerAgent agent) {
        TraceLogger.d((String) "LaunchApplicationAgent", "setExceptionHandlerAgent(" + agent + ")");
        this.f = agent;
    }

    public void preload() {
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "preload()");
        this.mMicroApplicationContext.preload(this.mContext);
    }

    public final void init() {
        TimingLogger.getBootLogger().addSplit("t_agentinit");
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "init()");
        TimingLogger.getBootLogger().addSplit("t_loadPackageProperties");
        this.mMicroApplicationContext.attachContext(this.mContext, this.f);
        TimingLogger.getBootLogger().addSplit("t_agentinitover");
        c.countDown();
    }

    public final void postLoad() {
        this.mMicroApplicationContext.postInit();
    }

    public final void loadServices() {
        this.mMicroApplicationContext.initSerivces();
    }

    public final void restoreState() {
        this.mMicroApplicationContext.restoreState();
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "startup : finish restoreState()");
    }

    public MicroApplicationContext getMicroApplicationContext() {
        return this.mMicroApplicationContext;
    }

    public BundleContext getBundleContext() {
        return this.mBundleContext;
    }

    @Deprecated
    public static BundleContext getmBundleContext() {
        try {
            TraceLogger.d((String) "LaunchApplicationAgent", (String) "sInitCountDownLatch2.await() start.");
            e.await();
        } catch (Throwable e2) {
            TraceLogger.w((String) "LaunchApplicationAgent", e2);
        } finally {
            r2 = "LaunchApplicationAgent";
            r4 = "sInitCountDownLatch2.await() end. sInstance=";
            TraceLogger.d(r2, d);
        }
        return d.mBundleContext;
    }

    public Application getApplicationContext() {
        return this.mContext;
    }

    public void recover() {
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "Default: LauncherApplicationAgent.recover()");
    }

    public Map<String, Set<String>> getLazyBundles() {
        if (this.mLazyBundles == null) {
            synchronized (this) {
                if (this.mLazyBundles == null) {
                    this.mLazyBundles = this.mMicroApplicationContext.getLazyBundles();
                    if (this.mLazyBundles == null || this.mLazyBundles.size() <= 0) {
                        this.mLazyBundles = null;
                        a();
                    }
                }
            }
        }
        return this.mLazyBundles;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0057 A[SYNTHETIC, Splitter:B:11:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:51:0x0115=Splitter:B:51:0x0115, B:69:0x0139=Splitter:B:69:0x0139} */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:65:0x0131=Splitter:B:65:0x0131, B:34:0x00c5=Splitter:B:34:0x00c5, B:53:0x0118=Splitter:B:53:0x0118} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r15 = this;
            boolean r12 = com.alipay.mobile.framework.quinoxless.QuinoxlessFramework.isQuinoxlessMode()
            if (r12 == 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            java.io.File r10 = new java.io.File
            android.app.Application r12 = r15.mContext
            java.lang.String r13 = "plugins"
            r14 = 0
            java.io.File r12 = r12.getDir(r13, r14)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = a
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r14 = "_"
            java.lang.StringBuilder r13 = r13.append(r14)
            android.app.Application r14 = r15.mContext
            com.alipay.mobile.quinox.startup.UpgradeHelper r14 = com.alipay.mobile.quinox.startup.UpgradeHelper.getInstance(r14)
            java.lang.String r14 = r14.getProductVersion()
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r13 = r13.toString()
            r10.<init>(r12, r13)
            r1 = 0
            boolean r12 = r10.exists()
            if (r12 == 0) goto L_0x0006
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0075 }
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0075 }
            r12.<init>(r10)     // Catch:{ Exception -> 0x0075 }
            r2.<init>(r12)     // Catch:{ Exception -> 0x0075 }
            java.util.Map r12 = readLazyBundles(r2)     // Catch:{ Exception -> 0x0154, all -> 0x0150 }
            r15.mLazyBundles = r12     // Catch:{ Exception -> 0x0154, all -> 0x0150 }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)
            r1 = r2
        L_0x0053:
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles
            if (r12 != 0) goto L_0x0006
            android.app.Application r12 = r15.mContext     // Catch:{ Exception -> 0x0089 }
            android.content.res.AssetManager r12 = r12.getAssets()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r13 = a     // Catch:{ Exception -> 0x0089 }
            java.io.InputStream r11 = r12.open(r13)     // Catch:{ Exception -> 0x0089 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0089 }
            r2.<init>(r11)     // Catch:{ Exception -> 0x0089 }
            java.util.Map r12 = readLazyBundles(r2)     // Catch:{ Exception -> 0x014c, all -> 0x013d }
            r15.mLazyBundles = r12     // Catch:{ Exception -> 0x014c, all -> 0x013d }
            com.alipay.mobile.quinox.utils.StreamUtil.streamToFile(r11, r10)     // Catch:{ Exception -> 0x014c, all -> 0x013d }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)
            goto L_0x0006
        L_0x0075:
            r8 = move-exception
        L_0x0076:
            r10.delete()     // Catch:{ all -> 0x0084 }
            java.lang.String r12 = "LaunchApplicationAgent"
            java.lang.String r13 = "Failed to load /data/data/{pkgname}/plugins/lazy_bundles.cfg"
            com.alipay.mobile.quinox.utils.TraceLogger.e(r12, r13, r8)     // Catch:{ all -> 0x0084 }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)
            goto L_0x0053
        L_0x0084:
            r12 = move-exception
        L_0x0085:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)
            throw r12
        L_0x0089:
            r12 = move-exception
        L_0x008a:
            r11 = 0
            r3 = 0
            java.lang.String r12 = "LaunchApplicationAgent"
            java.lang.String r13 = "Failed to load .apk/asset/lazy_bundles.cfg, try to load .apk/asset/lazy_bundles_plain.cfg"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r12, r13)     // Catch:{ Exception -> 0x00d0 }
            android.app.Application r12 = r15.mContext     // Catch:{ Exception -> 0x00d0 }
            android.content.res.AssetManager r12 = r12.getAssets()     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r13 = b     // Catch:{ Exception -> 0x00d0 }
            java.io.InputStream r11 = r12.open(r13)     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r0 = com.alipay.mobile.quinox.utils.StreamUtil.streamToString(r11)     // Catch:{ Exception -> 0x00d0 }
            java.util.Map r12 = r15.parseBundles(r0)     // Catch:{ Exception -> 0x00d0 }
            r15.mLazyBundles = r12     // Catch:{ Exception -> 0x00d0 }
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x00d0 }
            if (r12 == 0) goto L_0x00c5
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x00d0 }
            boolean r12 = r12.isEmpty()     // Catch:{ Exception -> 0x00d0 }
            if (r12 != 0) goto L_0x00c5
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x00d0 }
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00d0 }
            r12.<init>(r10)     // Catch:{ Exception -> 0x00d0 }
            r4.<init>(r12)     // Catch:{ Exception -> 0x00d0 }
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x0149, all -> 0x0140 }
            writeLazyBundles(r12, r4)     // Catch:{ Exception -> 0x0149, all -> 0x0140 }
            r3 = r4
        L_0x00c5:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r11)     // Catch:{ all -> 0x011f }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r3)     // Catch:{ all -> 0x011f }
        L_0x00cb:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)
            goto L_0x0006
        L_0x00d0:
            r12 = move-exception
        L_0x00d1:
            java.lang.String r12 = "LaunchApplicationAgent"
            java.lang.String r13 = "Failed to load .apk/asset/lazy_bundles.cfg, try to read meta-data"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r12, r13)     // Catch:{ all -> 0x0130 }
            r5 = 0
            android.app.Application r12 = r15.mContext     // Catch:{ Exception -> 0x0124 }
            android.content.pm.PackageManager r12 = r12.getPackageManager()     // Catch:{ Exception -> 0x0124 }
            android.app.Application r13 = r15.mContext     // Catch:{ Exception -> 0x0124 }
            java.lang.String r13 = r13.getPackageName()     // Catch:{ Exception -> 0x0124 }
            r14 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r12 = r12.getApplicationInfo(r13, r14)     // Catch:{ Exception -> 0x0124 }
            android.os.Bundle r12 = r12.metaData     // Catch:{ Exception -> 0x0124 }
            java.lang.String r13 = "lazy_bundle"
            java.lang.String r7 = r12.getString(r13)     // Catch:{ Exception -> 0x0124 }
            java.util.Map r12 = r15.parseBundles(r7)     // Catch:{ Exception -> 0x0124 }
            r15.mLazyBundles = r12     // Catch:{ Exception -> 0x0124 }
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x0124 }
            if (r12 == 0) goto L_0x0115
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x0124 }
            boolean r12 = r12.isEmpty()     // Catch:{ Exception -> 0x0124 }
            if (r12 != 0) goto L_0x0115
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0124 }
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0124 }
            r12.<init>(r10)     // Catch:{ Exception -> 0x0124 }
            r6.<init>(r12)     // Catch:{ Exception -> 0x0124 }
            java.util.Map<java.lang.String, java.util.Set<java.lang.String>> r12 = r15.mLazyBundles     // Catch:{ Exception -> 0x0146, all -> 0x0143 }
            writeLazyBundles(r12, r6)     // Catch:{ Exception -> 0x0146, all -> 0x0143 }
            r5 = r6
        L_0x0115:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r5)     // Catch:{ all -> 0x0130 }
        L_0x0118:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r11)     // Catch:{ all -> 0x011f }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r3)     // Catch:{ all -> 0x011f }
            goto L_0x00cb
        L_0x011f:
            r12 = move-exception
        L_0x0120:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)
            throw r12
        L_0x0124:
            r9 = move-exception
        L_0x0125:
            java.lang.String r12 = "LaunchApplicationAgent"
            java.lang.String r13 = "Failed to load meta-data (lazy_bundle)"
            com.alipay.mobile.quinox.utils.TraceLogger.e(r12, r13, r9)     // Catch:{ all -> 0x0138 }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r5)     // Catch:{ all -> 0x0130 }
            goto L_0x0118
        L_0x0130:
            r12 = move-exception
        L_0x0131:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r11)     // Catch:{ all -> 0x011f }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r3)     // Catch:{ all -> 0x011f }
            throw r12     // Catch:{ all -> 0x011f }
        L_0x0138:
            r12 = move-exception
        L_0x0139:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r5)     // Catch:{ all -> 0x0130 }
            throw r12     // Catch:{ all -> 0x0130 }
        L_0x013d:
            r12 = move-exception
            r1 = r2
            goto L_0x0120
        L_0x0140:
            r12 = move-exception
            r3 = r4
            goto L_0x0131
        L_0x0143:
            r12 = move-exception
            r5 = r6
            goto L_0x0139
        L_0x0146:
            r9 = move-exception
            r5 = r6
            goto L_0x0125
        L_0x0149:
            r12 = move-exception
            r3 = r4
            goto L_0x00d1
        L_0x014c:
            r12 = move-exception
            r1 = r2
            goto L_0x008a
        L_0x0150:
            r12 = move-exception
            r1 = r2
            goto L_0x0085
        L_0x0154:
            r8 = move-exception
            r1 = r2
            goto L_0x0076
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.LauncherApplicationAgent.a():void");
    }

    /* access modifiers changed from: 0000 */
    public Map<String, Set<String>> parseBundles(String data) {
        Map lazyBundles = null;
        if (!TextUtils.isEmpty(data)) {
            String[] bundles = data.split("\\|");
            if (bundles.length > 0) {
                lazyBundles = new HashMap(bundles.length);
                for (String split : bundles) {
                    String[] strArray = split.split(":");
                    if (strArray.length == 2 && !TextUtils.isEmpty(strArray[0]) && !TextUtils.isEmpty(strArray[1])) {
                        String[] microContents = strArray[1].split(",");
                        if (microContents.length > 0) {
                            Set set = new HashSet();
                            for (String trim : microContents) {
                                String content = trim.trim();
                                if (!TextUtils.isEmpty(content)) {
                                    set.add(content);
                                }
                            }
                            if (!set.isEmpty()) {
                                lazyBundles.put(strArray[0], set);
                            }
                        }
                    }
                }
            }
        }
        return lazyBundles;
    }

    public static void writeLazyBundles(Map<String, Set<String>> lazyBundles, BufferedOutputStream bufferedOutputStream) {
        if (lazyBundles != null && !lazyBundles.isEmpty()) {
            ByteOrderDataUtil.writeInt(bufferedOutputStream, lazyBundles.size());
            for (Entry entity : lazyBundles.entrySet()) {
                ByteOrderDataUtil.writeString(bufferedOutputStream, (String) entity.getKey());
                String[] microContents = new String[((Set) entity.getValue()).size()];
                ((Set) entity.getValue()).toArray(microContents);
                ByteOrderDataUtil.writeStringArray(bufferedOutputStream, microContents);
            }
            bufferedOutputStream.flush();
        }
    }

    public static Map<String, Set<String>> readLazyBundles(BufferedInputStream bufferedInputStream) {
        Set set;
        int size = ByteOrderDataUtil.readInt(bufferedInputStream);
        if (size <= 0) {
            return null;
        }
        Map lazyBundles = new HashMap();
        int i2 = 0;
        while (i2 < size) {
            try {
                String bundleName = ByteOrderDataUtil.readString(bufferedInputStream);
                if (!TextUtils.isEmpty(bundleName)) {
                    String[] microContentArray = ByteOrderDataUtil.readStringArray(bufferedInputStream);
                    if (microContentArray != null && microContentArray.length > 0) {
                        if (lazyBundles.containsKey(bundleName)) {
                            set = (Set) lazyBundles.get(bundleName);
                        } else {
                            set = new HashSet();
                            lazyBundles.put(bundleName, set);
                        }
                        set.addAll(Arrays.asList(microContentArray));
                    }
                }
                i2++;
            } catch (Throwable e2) {
                TraceLogger.w("LaunchApplicationAgent", "readLazyBundles(BufferedInputStream) error.", e2);
                return null;
            }
        }
        return lazyBundles;
    }

    public void preInit() {
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "Default: LauncherApplicationAgent.preInit()");
    }

    public void postInit() {
        TraceLogger.d((String) "LaunchApplicationAgent", (String) "Default: LauncherApplicationAgent.postInit()");
    }

    public void onTerminate() {
        Log.i("LaunchApplicationAgent", "Default: LauncherApplicationAgent.onTerminate()");
    }

    public void onLowMemory() {
        Log.i("LaunchApplicationAgent", "Default: LauncherApplicationAgent.onLowMemory()");
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("LaunchApplicationAgent", "Default: LauncherApplicationAgent.onConfigurationChanged() locale = " + newConfig.locale.getLanguage());
        LocaleHelper.systemLocale = newConfig.locale;
    }

    public static LauncherApplicationAgent getInstance() {
        boolean needWait;
        boolean needWait2;
        if (QuinoxlessFramework.isQuinoxlessMode()) {
            if (d == null) {
                QuinoxlessFramework.init();
            }
            return d;
        }
        if (d == null || (!Thread.currentThread().getName().startsWith(Constants.LAUNCHER_APPLICATION_INIT) && h.get())) {
            needWait = true;
        } else {
            needWait = false;
        }
        if (needWait) {
            synchronized (i) {
                try {
                    if (d == null || (!Thread.currentThread().getName().startsWith(Constants.LAUNCHER_APPLICATION_INIT) && h.get())) {
                        needWait2 = true;
                    } else {
                        needWait2 = false;
                    }
                }
            }
        }
        if (needWait) {
            try {
                g.setChanged();
                g.notifyObservers(Boolean.valueOf(true));
                c.await();
                h.set(false);
                g.setChanged();
                g.notifyObservers(Boolean.valueOf(false));
            } catch (Throwable e2) {
                TraceLogger.w((String) "LaunchApplicationAgent", e2);
            }
            if (d == null) {
                TraceLogger.e((String) "LaunchApplicationAgent", (Throwable) new RuntimeException("LauncherApplicationAgent.getInstance() return null."));
            }
        }
        return d;
    }

    public static boolean isInited() {
        boolean needWait;
        boolean needWait2;
        if (d == null || (!Thread.currentThread().getName().startsWith(Constants.LAUNCHER_APPLICATION_INIT) && h.get())) {
            needWait = true;
        } else {
            needWait = false;
        }
        if (needWait) {
            synchronized (i) {
                if (d == null || (!Thread.currentThread().getName().startsWith(Constants.LAUNCHER_APPLICATION_INIT) && h.get())) {
                    needWait2 = true;
                } else {
                    needWait2 = false;
                }
            }
        }
        if (!needWait) {
            return true;
        }
        return false;
    }

    public static void addWaitInitObserver(Observer observer) {
        g.addObserver(observer);
    }

    public Context getBaseContext() {
        return this.mContext.getBaseContext();
    }

    public ClassLoader getClassLoader() {
        return this.mContext.getClassLoader();
    }

    public String getPackageName() {
        return this.mContext.getPackageName();
    }

    public Object getSystemService(String name) {
        return this.mContext.getSystemService(name);
    }

    public PackageManager getPackageManager() {
        return this.mContext.getPackageManager();
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return this.mContext.getSharedPreferences(name, mode);
    }

    public File getCacheDir() {
        return this.mContext.getCacheDir();
    }

    public ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    public File getFilesDir() {
        return this.mContext.getFilesDir();
    }
}
