package com.alipay.rdssecuritysdk.v2.face;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.mobile.security.senative.APSE;
import com.alipay.rdssecuritysdk.RDSModelService;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.rdssecuritysdk.v3.impl.RDSModelServiceV3Impl;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RDSClient {
    public static long ApkVerify = 0;
    private static final long MAX_WAIT_TIME = 8000;
    private static Context context = null;
    private static boolean debug = false;
    private static AtomicBoolean hasStartInitialize = new AtomicBoolean(false);
    private static Condition initializeCond;
    private static ReentrantLock initializeLock;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static TraceLogger logger = LoggerFactory.f();
    private static APSE se;
    private RDSModelService rdsModelService;

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        initializeLock = reentrantLock;
        initializeCond = reentrantLock.newCondition();
    }

    public static void init(Context context2) {
        if (context2 != null) {
            logger.b((String) CONST.LOG_TAG, (String) "loading.");
            if (!hasStartInitialize.getAndSet(true)) {
                se = APSE.getInstance(context2);
                new Thread(new Runnable() {
                    public final void run() {
                        RDSClient.doApkVerifyWork();
                    }
                }).start();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ca, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cb, code lost:
        initializeLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d0, code lost:
        throw r0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:9:0x0089, B:22:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[ExcHandler: Exception (unused java.lang.Exception), SYNTHETIC, Splitter:B:9:0x0089] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void doApkVerifyWork() {
        /*
            com.alipay.android.phone.inside.log.api.behavior.Behavior r0 = new com.alipay.android.phone.inside.log.api.behavior.Behavior
            r0.<init>()
            java.lang.String r1 = "APK_VERIFY_RESULT"
            r0.a = r1
            r1 = 1
            com.alipay.mobile.security.senative.APSE r2 = se     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r3 = context     // Catch:{ Throwable -> 0x00a7 }
            long r2 = r2.init(r3)     // Catch:{ Throwable -> 0x00a7 }
            r4 = 32
            long r4 = r2 >> r4
            r6 = -1
            long r4 = r4 & r6
            int r4 = (int) r4     // Catch:{ Throwable -> 0x00a7 }
            r5 = 2097151(0x1fffff, float:2.938734E-39)
            r8 = r4 & r5
            int r4 = r4 >> 21
            r4 = r4 & 1023(0x3ff, float:1.434E-42)
            long r2 = r2 & r6
            int r2 = (int) r2     // Catch:{ Throwable -> 0x00a7 }
            r3 = r2 & r5
            int r2 = r2 >> 21
            r2 = r2 & 1023(0x3ff, float:1.434E-42)
            if (r8 != 0) goto L_0x0033
            if (r4 != 0) goto L_0x0033
            if (r3 != 0) goto L_0x0033
            if (r2 == 0) goto L_0x0037
        L_0x0033:
            r5 = 1
            ApkVerify = r5     // Catch:{ Throwable -> 0x00a7 }
        L_0x0037:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = logger     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r6 = "APSecuritySdk"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r9 = "APK_VERIFY_RESULT majorErrCode:"
            r7.<init>(r9)     // Catch:{ Throwable -> 0x00a7 }
            r7.append(r8)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r9 = " minorErrCode:"
            r7.append(r9)     // Catch:{ Throwable -> 0x00a7 }
            r7.append(r3)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r9 = " minorLineNum:"
            r7.append(r9)     // Catch:{ Throwable -> 0x00a7 }
            r7.append(r2)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00a7 }
            r5.b(r6, r7)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r5 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x00a7 }
            r0.g = r5     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00a7 }
            r0.h = r3     // Catch:{ Throwable -> 0x00a7 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a7 }
            r3.<init>()     // Catch:{ Throwable -> 0x00a7 }
            r3.append(r4)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r4 = "-"
            r3.append(r4)     // Catch:{ Throwable -> 0x00a7 }
            r3.append(r2)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x00a7 }
            r0.i = r2     // Catch:{ Throwable -> 0x00a7 }
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ Throwable -> 0x00a7 }
            r2.a(r0)     // Catch:{ Throwable -> 0x00a7 }
            java.util.concurrent.atomic.AtomicBoolean r0 = isInitialized     // Catch:{ Exception -> 0x0098, all -> 0x009e }
            r0.set(r1)     // Catch:{ Exception -> 0x0098, all -> 0x009e }
            java.util.concurrent.locks.ReentrantLock r0 = initializeLock     // Catch:{ Exception -> 0x0098, all -> 0x009e }
            r0.lock()     // Catch:{ Exception -> 0x0098, all -> 0x009e }
            java.util.concurrent.locks.Condition r0 = initializeCond     // Catch:{ Exception -> 0x0098, all -> 0x009e }
            r0.signalAll()     // Catch:{ Exception -> 0x0098, all -> 0x009e }
        L_0x0098:
            java.util.concurrent.locks.ReentrantLock r0 = initializeLock
            r0.unlock()
            return
        L_0x009e:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock
            r1.unlock()
            throw r0
        L_0x00a5:
            r0 = move-exception
            goto L_0x00d1
        L_0x00a7:
            java.lang.String r2 = "0"
            r0.g = r2     // Catch:{ all -> 0x00a5 }
            java.lang.String r2 = "0"
            r0.h = r2     // Catch:{ all -> 0x00a5 }
            java.lang.String r2 = "0"
            r0.i = r2     // Catch:{ all -> 0x00a5 }
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ all -> 0x00a5 }
            r2.a(r0)     // Catch:{ all -> 0x00a5 }
            java.util.concurrent.atomic.AtomicBoolean r0 = isInitialized     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            r0.set(r1)     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            java.util.concurrent.locks.ReentrantLock r0 = initializeLock     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            r0.lock()     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            java.util.concurrent.locks.Condition r0 = initializeCond     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            r0.signalAll()     // Catch:{ Exception -> 0x0098, all -> 0x00ca }
            goto L_0x0098
        L_0x00ca:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock
            r1.unlock()
            throw r0
        L_0x00d1:
            java.util.concurrent.atomic.AtomicBoolean r2 = isInitialized     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            r2.set(r1)     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            r1.lock()     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            java.util.concurrent.locks.Condition r1 = initializeCond     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            r1.signalAll()     // Catch:{ Exception -> 0x00e8, all -> 0x00e1 }
            goto L_0x00e8
        L_0x00e1:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock
            r1.unlock()
            throw r0
        L_0x00e8:
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.rdssecuritysdk.v2.face.RDSClient.doApkVerifyWork():void");
    }

    public static synchronized void enableLog() {
        synchronized (RDSClient.class) {
            debug = true;
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context2) {
        context = context2;
    }

    public static boolean isDebug() {
        return debug;
    }

    public synchronized boolean onPage(Context context2, Map<String, String> map, boolean z) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onPage.");
        if (context2 == null) {
            return false;
        }
        setContext(context2);
        String str = map.get(DictionaryKeys.V2_PAGENAME);
        String str2 = map.get(DictionaryKeys.V2_REFPAGENAME);
        this.rdsModelService = new RDSModelServiceV3Impl();
        if (this.rdsModelService == null) {
            logger.b((String) CONST.LOG_TAG, (String) "onPage(), NOT FIND SERVICE!!!");
        } else {
            logger.b((String) CONST.LOG_TAG, (String) "onPage(), find service success.");
        }
        this.rdsModelService.reInit(context2, map, z);
        this.rdsModelService.onPage(str, str2);
        return true;
    }

    public synchronized String onPageEndAndZip(Context context2, String str) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onPageEndAndZip.");
        this.rdsModelService.updateUser(str);
        this.rdsModelService.onPageEnd();
        if (getContext() == null) {
            return null;
        }
        waitApseFinishInitialize();
        try {
            return this.rdsModelService.getRdsRequestMessage(context2, se);
        } catch (Throwable th) {
            TraceLogger traceLogger = logger;
            StringBuilder sb = new StringBuilder("onPageEndAndZip call manager.getRdsRequestMessage happened exception: ");
            sb.append(th.getMessage());
            traceLogger.d(CONST.LOG_TAG, sb.toString());
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        initializeLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003d, code lost:
        throw r0;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x002a */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037 A[Catch:{ Throwable -> 0x002a, all -> 0x0037, Exception -> 0x0045 }, ExcHandler: all (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:3:0x000e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void waitApseFinishInitialize() {
        /*
            r6 = this;
            long r0 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0045 }
            r2 = 0
            java.util.concurrent.atomic.AtomicBoolean r4 = isInitialized     // Catch:{ Exception -> 0x0045 }
            boolean r4 = r4.get()     // Catch:{ Exception -> 0x0045 }
            if (r4 != 0) goto L_0x0044
            java.util.concurrent.locks.ReentrantLock r4 = initializeLock     // Catch:{ Throwable -> 0x003e, all -> 0x0037 }
            r4.lock()     // Catch:{ Throwable -> 0x003e, all -> 0x0037 }
        L_0x0013:
            java.util.concurrent.atomic.AtomicBoolean r4 = isInitialized     // Catch:{ Throwable -> 0x003e, all -> 0x0037 }
            boolean r4 = r4.get()     // Catch:{ Throwable -> 0x003e, all -> 0x0037 }
            if (r4 != 0) goto L_0x0031
            r4 = 8000(0x1f40, double:3.9525E-320)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x0031
            java.util.concurrent.locks.Condition r2 = initializeCond     // Catch:{ Throwable -> 0x002a, all -> 0x0037 }
            r3 = 2000(0x7d0, double:9.88E-321)
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x002a, all -> 0x0037 }
            r2.await(r3, r5)     // Catch:{ Throwable -> 0x002a, all -> 0x0037 }
        L_0x002a:
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Throwable -> 0x003e, all -> 0x0037 }
            r4 = 0
            long r2 = r2 - r0
            goto L_0x0013
        L_0x0031:
            java.util.concurrent.locks.ReentrantLock r0 = initializeLock     // Catch:{ Exception -> 0x0045 }
            r0.unlock()     // Catch:{ Exception -> 0x0045 }
            return
        L_0x0037:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantLock r1 = initializeLock     // Catch:{ Exception -> 0x0045 }
            r1.unlock()     // Catch:{ Exception -> 0x0045 }
            throw r0     // Catch:{ Exception -> 0x0045 }
        L_0x003e:
            java.util.concurrent.locks.ReentrantLock r0 = initializeLock     // Catch:{ Exception -> 0x0045 }
            r0.unlock()     // Catch:{ Exception -> 0x0045 }
            return
        L_0x0044:
            return
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.rdssecuritysdk.v2.face.RDSClient.waitApseFinishInitialize():void");
    }

    public synchronized String onPageEnd(Context context2, String str) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onPageEnd.");
        return onPageEndAndZip(context2, str);
    }

    public synchronized void onControlClick(String str, String str2) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onControlClick.");
        this.rdsModelService.onControlClick(str, str2);
    }

    public synchronized void onKeyDown(String str, String str2, String str3) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onKeyDown.");
        this.rdsModelService.onKeyDown(str, str2, str3);
    }

    public synchronized void onGetFocus(String str, String str2) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onGetFocus.");
        this.rdsModelService.onFocusChange(str, str2, true);
    }

    public synchronized void onLostFocus(String str, String str2) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onLostFocus.");
        this.rdsModelService.onFocusChange(str, str2, false);
    }

    public synchronized void onLostFocus(String str, String str2, boolean z) {
        logger.b((String) CONST.LOG_TAG, (String) "RDSClient onLostFocus.");
        this.rdsModelService.onFocusChange(str, str2, z);
    }

    public synchronized void onTouchScreen(String str, String str2, double d, double d2) {
        synchronized (this) {
            try {
                logger.b((String) CONST.LOG_TAG, (String) "RDSClient onTouchScreen.");
                this.rdsModelService.onTouchScreen(str, str2, d, d2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
