package com.alipay.mobile.common.logging;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.wallet.spmtracker.ISemMonitor;
import com.alipay.android.phone.wallet.spmtracker.ISpmMonitor;
import com.alipay.android.phone.wallet.spmtracker.ITinyPageMonitor;
import com.alipay.android.phone.wallet.spmtracker.NullTinyPageMonitor;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.logging.api.DeviceInfo;
import com.alipay.mobile.common.logging.api.LogAppenderistener;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogContext.DevicePerformanceScore;
import com.alipay.mobile.common.logging.api.LogCustomerControl;
import com.alipay.mobile.common.logging.api.LogDAUTracker;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.logging.api.UncaughtExceptionCallback;
import com.alipay.mobile.common.logging.api.abtest.AbtestInfoGetter;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorLogListener;
import com.alipay.mobile.common.logging.api.encrypt.LogEncryptClient;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.api.network.NetworkInfoGetter;
import com.alipay.mobile.common.logging.api.rpc.RpcClient;
import com.alipay.mobile.common.logging.appender.AppenderManager;
import com.alipay.mobile.common.logging.helper.BugReportAnalyzer;
import com.alipay.mobile.common.logging.helper.DevicePerformanceScoreHelper;
import com.alipay.mobile.common.logging.helper.YearClass;
import com.alipay.mobile.common.logging.http.HttpClient;
import com.alipay.mobile.common.logging.http.UploadUrlConfig;
import com.alipay.mobile.common.logging.impl.StatisticalExceptionHandler;
import com.alipay.mobile.common.logging.impl.TraceLogEvent;
import com.alipay.mobile.common.logging.render.BehavorRender;
import com.alipay.mobile.common.logging.render.ExceptionRender;
import com.alipay.mobile.common.logging.render.PendingRender;
import com.alipay.mobile.common.logging.strategy.LogStrategyInfo;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.FileUtil;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.LowEndDeviceUtil;
import com.alipay.mobile.common.logging.util.crash.CrashAnalyzer;
import com.alipay.mobile.common.logging.util.network.NetWorkProvider;
import com.alipay.mobile.common.nativecrash.CrashClientImpl;
import com.alipay.mobile.common.nativecrash.CrashCombineUtils;
import com.alipay.mobile.common.nativecrash.CrashCombineUtils.FlatComparator;
import com.alipay.mobile.common.nativecrash.CrashFilterUtils;
import com.alipay.mobile.common.nativecrash.NativeCrashHandler;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class LogContextImpl implements LogContext {
    private LogEncryptClient A = null;
    private RpcClient B = null;
    private String C = null;
    private LogDAUTracker D;
    private NetworkInfoGetter E = null;
    public long a = System.currentTimeMillis();
    public boolean b = true;
    private LogAppenderistener c = null;
    private LogCustomerControl d = null;
    private Context e;
    private String f;
    private String g = null;
    private String h = null;
    private ThreadLocal<Integer> i = new ThreadLocal<>();
    private Map<String, String> j = new ConcurrentHashMap();
    private Map<String, String> k = new ConcurrentHashMap();
    private InheritableThreadLocal<Map<String, String>> l = new InheritableThreadLocal<>();
    /* access modifiers changed from: private */
    public final Queue<LogEvent> m;
    private ContextInfo n;
    private volatile AppendWorker o;
    /* access modifiers changed from: private */
    public AppenderManager p;
    private MdapLogUploadManager q;
    private AbtestInfoGetter r;
    private long s;
    private long t;
    private long u = System.currentTimeMillis();
    private final boolean v;
    private BehavorLogListener w;
    private ISpmMonitor x;
    private ISemMonitor y;
    private ITinyPageMonitor z = new NullTinyPageMonitor();

    public class AppendWorker extends Thread {
        public AppendWorker() {
        }

        public void run() {
            int priority = Looper.getMainLooper().getThread().getPriority() - 2;
            if (priority < 5) {
                priority = 5;
            }
            setPriority(priority);
            while (LogContextImpl.this.b) {
                try {
                    LogContextImpl.this.a(LogContextImpl.this.m);
                } catch (Throwable t) {
                    Log.e("LogContext", "AppendWorker finally: " + t);
                    return;
                }
            }
            try {
                LogContextImpl.this.a((AppendWorker) null);
                for (LogEvent logEvent : LogContextImpl.this.m) {
                    LogContextImpl.this.syncAppendLogEvent(logEvent);
                    LogContextImpl.this.m.remove(logEvent);
                }
                LogContextImpl.this.a((Queue<LogEvent>) null);
            } catch (Throwable t2) {
                Log.e("LogContext", "AppendWorker finally: " + t2);
            }
        }
    }

    public final void a(Queue<LogEvent> queue) {
        LogEvent event;
        if (queue != null) {
            if (this.v) {
                event = queue.poll();
                if (event == null) {
                    SystemClock.sleep(100);
                    this.b = true;
                    return;
                }
            } else {
                event = (LogEvent) ((BlockingQueue) queue).take();
            }
            syncAppendLogEvent(event);
            this.b = true;
        }
    }

    public LogContextImpl(Context context) {
        new StringBuilder().append(CrashCombineUtils.class.getName()).append(FlatComparator.class).append(CrashFilterUtils.class.getName()).append(NativeCrashHandler.class.getName()).append(CrashClientImpl.class.getName()).append(LogEvent.class.getName()).append(Behavor.class.getName()).append(Builder.class.getName()).append(PerformanceID.class.getName()).append(Performance.class.getName()).append(Performance.Builder.class.getName()).append(ExceptionID.class.getName()).append(LogCategory.class.getName()).append(Level.class.getName()).append(HttpClient.class.getName()).append(TraceLogEvent.class.getName()).append(EventCategory.class.getName()).append(UncaughtExceptionCallback.class.getName());
        this.e = context;
        this.n = new ContextInfo(context);
        LogStrategyManager.createInstance(context, this.n);
        NetWorkProvider.createInstance(context);
        this.p = new AppenderManager(this);
        this.q = MdapLogUploadManager.a(context);
        this.v = false;
        if (this.v) {
            this.m = new ConcurrentLinkedQueue();
        } else {
            this.m = new ArrayBlockingQueue(2048);
        }
    }

    public final void a() {
        this.p.a();
    }

    public String getStorageParam(String key) {
        String param = getLocalParam(key);
        if (param == null) {
            return getContextParam(key);
        }
        return param;
    }

    public void putContextParam(String key, String value) {
        if (key != null && value != null) {
            this.j.put(key, value);
        }
    }

    public String getContextParam(String key) {
        return this.j.get(key);
    }

    public void removeContextParam(String key) {
        if (key != null) {
            this.j.remove(key);
        }
    }

    public Context getApplicationContext() {
        return this.e;
    }

    public void putLocalParam(String key, String value) {
        if (key != null && value != null) {
            Map oldMap = (Map) this.l.get();
            if (a(c()) || oldMap == null) {
                a(oldMap).put(key, value);
            } else {
                oldMap.put(key, value);
            }
        }
    }

    public String getLocalParam(String key) {
        Map map = d();
        if (map == null || key == null) {
            return null;
        }
        return map.get(key);
    }

    public void removeLocalParam(String key) {
        if (key != null) {
            Map oldMap = (Map) this.l.get();
            if (oldMap != null) {
                if (a(c())) {
                    a(oldMap).remove(key);
                } else {
                    oldMap.remove(key);
                }
            }
        }
    }

    public void backupCurrentFile(String category, boolean isBackupOthers) {
        this.p.a(category, isBackupOthers);
    }

    public void flush(boolean sync) {
        flush(null, sync);
    }

    public void flush(String category, boolean sync) {
        flush(category, sync, null);
    }

    public void flush(String category, boolean sync, Bundle bundle) {
        LogEvent logEvent = new LogEvent("flush", null, Level.ERROR, category);
        logEvent.setBundle(bundle);
        if (sync) {
            syncAppendLogEvent(logEvent);
        } else {
            appendLogEvent(logEvent);
        }
    }

    public void uploadAfterSync(String logCategory) {
        appendLogEvent(new LogEvent("uploadByType", null, Level.ERROR, logCategory));
    }

    public void uploadAfterSync(String logCategory, String uploadUrl) {
        LogEvent logEvent = new LogEvent("uploadByType", null, Level.ERROR, logCategory);
        logEvent.setUploadUrl(uploadUrl);
        appendLogEvent(logEvent);
    }

    public void uploadAfterSync(String logCategory, String uploadUrl, Bundle bundle) {
        LogEvent logEvent = new LogEvent("uploadByType", null, Level.ERROR, logCategory);
        logEvent.setUploadUrl(uploadUrl);
        logEvent.setBundle(bundle);
        appendLogEvent(logEvent);
    }

    public void upload(String logCategory) {
        upload(logCategory, null);
    }

    public void upload(String logCategory, String url) {
        upload(logCategory, url, null);
    }

    public void upload(String logCategory, String url, Bundle bundle) {
        if (LoggerFactory.getProcessInfo().isMainProcess() || LoggerFactory.getProcessInfo().isExtProcess() || LoggerFactory.getProcessInfo().isLiteProcess()) {
            Intent intent = new Intent();
            if (LogStrategyManager.getInstance().isRealTimeLogCategory(logCategory)) {
                intent.setClassName(this.e, LogContext.MAIN_SERVICE_CLASS_NAME);
            } else if (LogStrategyManager.getInstance().isDisableToolsProcess()) {
                intent.setClassName(this.e, LogContext.PUSH_LOG_SERVICE_CLASS_NAME);
            } else {
                MdapLogUploadManager.a();
                if (MdapLogUploadManager.a(logCategory) || !g()) {
                    intent.setClassName(this.e, LogContext.TOOLS_SERVICE_CLASS_NAME);
                } else {
                    intent.setClassName(this.e, LogContext.PUSH_LOG_SERVICE_CLASS_NAME);
                }
            }
            a(intent, logCategory, url, bundle);
        } else if (LoggerFactory.getProcessInfo().isPushProcess()) {
            if (LogStrategyManager.getInstance().isRealTimeLogCategory(logCategory)) {
                Intent intent2 = new Intent();
                intent2.setClassName(this.e, LogContext.MAIN_SERVICE_CLASS_NAME);
                a(intent2, logCategory, url, bundle);
            } else if (LogStrategyManager.getInstance().isDisableToolsProcess()) {
                adjustUploadCoreByCategoryDirectly(logCategory, url, bundle);
            } else {
                MdapLogUploadManager.a();
                if (MdapLogUploadManager.a(logCategory) || !g()) {
                    Intent intent3 = new Intent();
                    intent3.setClassName(this.e, LogContext.TOOLS_SERVICE_CLASS_NAME);
                    a(intent3, logCategory, url, bundle);
                } else {
                    adjustUploadCoreByCategoryDirectly(logCategory, url, bundle);
                }
            }
        } else if (LoggerFactory.getProcessInfo().isToolsProcess()) {
            adjustUploadCoreByCategoryDirectly(logCategory, url, bundle);
        } else {
            LoggerFactory.getTraceLogger().error((String) "LogContext", "upload, error: unknown process " + LoggerFactory.getProcessInfo().getProcessAlias());
            adjustUploadCoreByCategoryDirectly(logCategory, url, bundle);
        }
        b();
    }

    private void a(Intent intent, String logCategory, String url, Bundle bundle) {
        try {
            intent.setPackage(this.e.getPackageName());
        } catch (Throwable th) {
        }
        intent.setAction(this.e.getPackageName() + ".monitor.action.upload.mdaplog");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.putExtra("logCategory", logCategory);
        intent.putExtra("uploadUrl", url);
        intent.putExtra("isMonitorBackground", TianyanLoggingStatus.isMonitorBackground());
        intent.putExtra("isStrictBackground", TianyanLoggingStatus.isStrictBackground());
        intent.putExtra("isRelaxedBackground", TianyanLoggingStatus.isRelaxedBackground());
        intent.putExtra("invokerProcessAlias", LoggerFactory.getProcessInfo().getProcessAlias());
        boolean isSuccess = false;
        try {
            isSuccess = this.e.startService(intent) != null;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error("LogContext", "uploadCoreByStartService", e2);
        }
        boolean isDisableTools = LogStrategyManager.getInstance().isDisableToolsProcess();
        StringBuilder message = new StringBuilder("uploadCoreByStartService: start upload service");
        message.append(", logCategory: ").append(logCategory);
        message.append(", success: ").append(isSuccess);
        message.append(", process: ").append(LoggerFactory.getProcessInfo().getProcessAlias());
        message.append(", disableTools: ").append(isDisableTools);
        LoggerFactory.getTraceLogger().info("LogContext", message.toString());
        if (!isSuccess) {
            a(logCategory, url, bundle);
        }
    }

    public void adjustUploadCoreByCategoryDirectly(String logCategory, String url, Bundle bundle) {
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
            a(logCategory, url, bundle);
        } else {
            b(logCategory, url, bundle);
        }
    }

    private void a(String logCategory, String url, Bundle bundle) {
        new Thread(new a(this, logCategory, url, bundle), "LogContext.upload").start();
    }

    /* access modifiers changed from: private */
    public void b(String logCategory, String uploadUrl, Bundle bundle) {
        LoggerFactory.getTraceLogger().info("LogContext", LoggerFactory.getProcessInfo().getProcessAlias() + " syncUploadCoreByCategoryDirectly: " + logCategory);
        if ((!LoggerFactory.getProcessInfo().isMainProcess() && logCategory == null) || (bundle != null && bundle.getBoolean(LogContext.SYNC_ALL_LOG))) {
            try {
                LoggerFactory.getTraceLogger().info("LogContext", "sync all log to upload dir...");
                this.q.b();
            } catch (Throwable t2) {
                LoggerFactory.getTraceLogger().error((String) "LogContext", "syncUploadCoreByCategoryDirectly, syncLog: " + t2);
            }
        }
        try {
            this.q.a(logCategory, uploadUrl, bundle);
        } catch (Throwable t3) {
            LoggerFactory.getTraceLogger().error("LogContext", "syncUploadCoreByCategoryDirectly, uploadLog: " + t3, t3);
        }
    }

    private void b() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - this.u >= LogStrategyManager.MINIMUM_REQUEST_TIME_SPAN) {
            this.u = nowTime;
            LogStrategyManager.getInstance().queryStrategy(LogStrategyManager.ACTION_TYPE_MDAPUPLOAD, false);
        }
    }

    public void updateLogStrategyCfg(String cfg) {
        LogStrategyManager.getInstance().updateLogStrategy(cfg);
    }

    public boolean isPositiveDiagnose() {
        return LogStrategyManager.getInstance().isPositiveDiagnose();
    }

    public boolean isZipAndSevenZip() {
        return LogStrategyManager.getInstance().isZipAndSevenZip();
    }

    public boolean isDisableToolsProcess() {
        return LogStrategyManager.getInstance().isDisableToolsProcess();
    }

    public boolean isEnableTrafficLimit() {
        return LogStrategyManager.getInstance().isEnableTrafficLimit();
    }

    private Map<String, String> a(Map<String, String> oldMap) {
        Map newMap = Collections.synchronizedMap(new HashMap());
        if (oldMap != null) {
            synchronized (oldMap) {
                newMap.putAll(oldMap);
            }
        }
        this.l.set(newMap);
        return newMap;
    }

    private Integer c() {
        Integer lastOp = this.i.get();
        this.i.set(Integer.valueOf(1));
        return lastOp;
    }

    private static boolean a(Integer lastOp) {
        return lastOp == null || lastOp.intValue() == 2;
    }

    private Map<String, String> d() {
        this.i.set(Integer.valueOf(2));
        return (Map) this.l.get();
    }

    public void syncAppendLogEvent(LogEvent logEvent) {
        this.p.a(logEvent);
    }

    public void appendLogEvent(LogEvent logEvent) {
        if (logEvent == null || logEvent.isIllegal()) {
            Log.e("LogContext", "appendLogEvent: illegal logEvent");
            return;
        }
        if (this.o == null) {
            synchronized (this) {
                if (this.o == null) {
                    AppendWorker appendWorker = new AppendWorker();
                    appendWorker.setDaemon(true);
                    appendWorker.setName("LogAppendWorker");
                    appendWorker.start();
                    a(appendWorker);
                }
            }
        }
        try {
            if (!this.m.add(logEvent)) {
                throw new RuntimeException("add log event to queue fail, current size: " + this.m.size());
            }
        } catch (Throwable t2) {
            Log.v("LogContext", "appendLogEvent", t2);
        }
    }

    public final synchronized void a(AppendWorker appendWorker) {
        this.o = appendWorker;
    }

    public String getChannelId() {
        return this.n.b();
    }

    public String getReleaseType() {
        return this.n.c();
    }

    public String getReleaseCode() {
        return this.n.d();
    }

    public String getProductId() {
        return this.n.e();
    }

    public String getProductVersion() {
        return this.n.f();
    }

    public String getUserId() {
        return this.n.g();
    }

    public String getClientId() {
        return this.n.h();
    }

    public String getDeviceId() {
        return this.n.i();
    }

    public String getLanguage() {
        return this.n.j();
    }

    public String getSessionId() {
        return this.n.k();
    }

    public String getSourceId() {
        return this.n.l();
    }

    public String getHotpatchVersion() {
        return this.n.m();
    }

    public String getBundleVersion() {
        return this.n.n();
    }

    public String getBirdNestVersion() {
        return this.n.o();
    }

    public String getPackageId() {
        return this.n.p();
    }

    public String getApkUniqueId() {
        return this.n.q();
    }

    public String getUserSessionId() {
        return this.n.r();
    }

    public void setChannelId(String channelId) {
        this.n.b(channelId);
    }

    public void setReleaseType(String releaseType) {
        this.n.c(releaseType);
    }

    public void setReleaseCode(String releaseCode) {
        this.n.d(releaseCode);
    }

    public void setProductId(String productId) {
        this.n.e(productId);
    }

    public void setProductVersion(String productVersion) {
        this.n.f(productVersion);
    }

    public void setUserId(String userId) {
        this.n.g(userId);
    }

    public void setClientId(String clientId) {
        this.n.h(clientId);
    }

    public void setDeviceId(String deviceId) {
        this.n.i(deviceId);
    }

    public void setLanguage(String language) {
        this.n.j(language);
    }

    public void refreshSessionId() {
        this.n.s();
    }

    public void setSourceId(String sourceId) {
        this.n.k(sourceId);
    }

    public void setHotpatchVersion(String hotpatchVersion) {
        this.n.l(hotpatchVersion);
    }

    public void setBundleVersion(String bundleVersion) {
        this.n.m(bundleVersion);
    }

    public void setBirdNestVersion(String birdNestVersion) {
        this.n.n(birdNestVersion);
    }

    public void setPackageId(String packageId) {
        this.n.o(packageId);
    }

    public void setApkUniqueId(String apkUniqueId) {
        this.n.p(apkUniqueId);
    }

    @Deprecated
    public void setLogHost(String logHost) {
        this.f = logHost;
        this.n.q(logHost);
    }

    public void setLogHostNoCommit(String logHost) {
        this.f = logHost;
        this.n.E(logHost);
    }

    public void resetLogHost() {
        this.f = null;
    }

    public void setChannelIdNoCommit(String channelId) {
        this.n.r(channelId);
    }

    public void setReleaseTypeNoCommit(String releaseType) {
        this.n.s(releaseType);
    }

    public void setReleaseCodeNoCommit(String releaseCode) {
        this.n.t(releaseCode);
    }

    public void setProductIdNoCommit(String productId) {
        this.n.u(productId);
    }

    public void setProductVersionNoCommit(String productVersion) {
        this.n.v(productVersion);
    }

    public void setUserIdNoCommit(String userId) {
        this.n.w(userId);
    }

    public void setClientIdNoCommit(String clientId) {
        this.n.x(clientId);
    }

    public void setDeviceIdNoCommit(String deviceId) {
        this.n.y(deviceId);
    }

    public void setLanguageNoCommit(String language) {
        this.n.z(language);
    }

    public void setHotpatchVersionNoCommit(String hotpatchVersion) {
        this.n.A(hotpatchVersion);
    }

    public void setBundleVersionNoCommit(String bundleVersion) {
        this.n.B(bundleVersion);
    }

    public void setBirdNestVersionNoCommit(String birdNestVersion) {
        this.n.C(birdNestVersion);
    }

    public void setPackageIdNoCommit(String packageId) {
        this.n.D(packageId);
    }

    public void resetExtrasToSet() {
        this.n.t();
    }

    public void commitExtrasToUpdate() {
        this.n.u();
    }

    public void setUserSessionId(String sessionId) {
        this.n.a(sessionId);
    }

    @Deprecated
    public void takedownExceptionHandler() {
        LoggerFactory.getTraceLogger().error((String) "LogContext", (Throwable) new Exception("illegal to invoke 'takedownExceptionHandler' function"));
    }

    public void setupExceptionHandler(UncaughtExceptionCallback callback, int flag) {
        StatisticalExceptionHandler.getInstance().setup();
        StatisticalExceptionHandler.getInstance().setUncaughtExceptionCallback(callback);
    }

    public void notifyClientEvent(String eventType, Object eventParam) {
        if (LogContext.ENVENT_VIEWSWITCH.equals(eventType)) {
            String viewId = (String) eventParam;
            if (viewId != null) {
                if (!viewId.equals(getContextParam(LogContext.STORAGE_VIEWID))) {
                    String storageViewId = getContextParam(LogContext.STORAGE_VIEWID);
                    putContextParam(LogContext.STORAGE_REFVIEWID, storageViewId);
                    if (!TextUtils.isEmpty(storageViewId)) {
                        NativeCrashHandlerApi.addCrashHeadInfo(LogContext.STORAGE_REFVIEWID, storageViewId);
                    }
                    putContextParam(LogContext.STORAGE_VIEWID, viewId);
                    if (!TextUtils.isEmpty(viewId)) {
                        NativeCrashHandlerApi.addCrashHeadInfo(LogContext.STORAGE_VIEWID, viewId);
                    }
                    putContextParam(LogContext.STORAGE_PAGESERIAL, this.n.k() + '_' + System.currentTimeMillis());
                }
            }
        } else if (LogContext.ENVENT_SUBAPPSTART.equals(eventType)) {
            String[] appDes = ((String) eventParam).split(",");
            String appId = appDes[0];
            String appType = "";
            if (appDes.length > 1) {
                appType = appDes[1];
            }
            putContextParam(LogContext.STORAGE_APPID, appId);
            if (!TextUtils.isEmpty(appId)) {
                NativeCrashHandlerApi.addCrashHeadInfo(LogContext.STORAGE_APPID, appId);
            }
            Behavor behavor = new Behavor();
            behavor.setSeedID(H5PageData.FROM_TYPE_START_APP);
            behavor.setParam1(appId);
            behavor.setParam3(appType);
            appendLogEvent(new LogEvent(LogCategory.CATEGORY_ALIVEREPORT, null, Level.ERROR, null, PendingRender.a(new BehavorRender(this), "event", behavor)));
        } else if (LogContext.ENVENT_SUBAPPRESUME.equals(eventType)) {
            String appId2 = (String) eventParam;
            if (!TextUtils.isEmpty(appId2) && !appId2.equals(getContextParam(LogContext.STORAGE_APPID))) {
                putContextParam(LogContext.STORAGE_APPID, appId2);
                if (!TextUtils.isEmpty(appId2)) {
                    NativeCrashHandlerApi.addCrashHeadInfo(LogContext.STORAGE_APPID, appId2);
                }
            }
        } else if (LogContext.ENVENT_GOTOFOREGROUND.equals(eventType)) {
            appendLogEvent(new LogEvent("refreshSession", null, Level.ERROR, null));
            Behavor activeBehavor = new Behavor();
            activeBehavor.setSeedID("reportActive");
            if (VERSION.SDK_INT >= 21) {
                try {
                    activeBehavor.setParam1(new DecimalFormat("##0.00").format((double) (((float) ((BatteryManager) this.e.getSystemService("batterymanager")).getIntProperty(4)) / 100.0f)));
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().error((String) "LogContext", e2);
                }
            }
            appendLogEvent(new LogEvent(LogCategory.CATEGORY_ALIVEREPORT, null, Level.ERROR, null, PendingRender.a(new BehavorRender(this), "event", activeBehavor)));
            b(eventType);
            e();
            f();
        } else if (LogContext.ENVENT_GOTOBACKGROUND.equals(eventType)) {
            this.t = System.currentTimeMillis();
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: gotoBackground, update gotoBackgroundTimestamp to: " + this.t);
            appendLogEvent(new LogEvent(LogContext.ENVENT_GOTOBACKGROUND, null, Level.ERROR, Long.toString(this.t)));
            NativeCrashHandlerApi.addCrashHeadInfo("gotoBackgroundTimestamp", String.valueOf(this.t));
            LogStrategyManager.getInstance().queryStrategy(LogStrategyManager.ACTION_TYPE_LEAVEHINT, false);
            flush(false);
            flush("applog", false);
            if (LoggingUtil.isOfflineMode()) {
                appendLogEvent(new LogEvent("uploadByEvent", null, Level.ERROR, null));
            } else {
                b(eventType);
            }
            flush("shoujichongzhi", false);
            uploadAfterSync("shoujichongzhi");
        } else if (LogContext.CLIENT_ENVENT_GOTOFOREGROUND.equals(eventType)) {
            this.t = 0;
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: ClientEvent_GotoForeground, update gotoBackgroundTimestamp to: " + this.t);
            appendLogEvent(new LogEvent(LogContext.ENVENT_GOTOBACKGROUND, null, Level.ERROR, Long.toString(this.t)));
            NativeCrashHandlerApi.addCrashHeadInfo("gotoBackgroundTimestamp", String.valueOf(this.t));
            LogStrategyManager.getInstance().queryStrategy("timeout", false);
            LogStrategyManager.getInstance().refreshHitState();
            b(eventType);
        } else if (LogContext.CLIENT_ENVENT_PAGELAUNCH.equals(eventType)) {
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: ClientEvent_PageLaunch, clientLaunchTimestamp: " + this.s + ", gotoBackgroundTimestamp: " + this.t);
            if (this.s <= 0) {
                this.s = System.currentTimeMillis();
                NativeCrashHandlerApi.addCrashHeadInfo("clientLaunchTimestamp", String.valueOf(this.s));
            }
            if (this.t > 0) {
                this.t = 0;
                appendLogEvent(new LogEvent(LogContext.ENVENT_GOTOBACKGROUND, null, Level.ERROR, Long.toString(this.t)));
                NativeCrashHandlerApi.addCrashHeadInfo("gotoBackgroundTimestamp", String.valueOf(this.t));
            }
        } else if (LogContext.CLIENT_ENVENT_CLIENTLAUNCH.equals(eventType)) {
            this.s = System.currentTimeMillis();
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: ClientEvent_ClientLaunch, update clientLaunchTimestamp to: " + this.s);
            appendLogEvent(new LogEvent("refreshSession", null, Level.ERROR, null));
            NativeCrashHandlerApi.addCrashHeadInfo("clientLaunchTimestamp", String.valueOf(this.s));
            LogStrategyManager.getInstance().queryStrategy(LogStrategyManager.ACTION_TYPE_BOOT, true);
            Behavor activeBehavor2 = new Behavor();
            activeBehavor2.setSeedID("reportActive");
            activeBehavor2.setUserCaseID("CLIENT_ENVENT_CLIENTLAUNCH");
            appendLogEvent(new LogEvent(LogCategory.CATEGORY_ALIVEREPORT, null, Level.ERROR, null, PendingRender.a(new BehavorRender(this), "event", activeBehavor2)));
            b(eventType);
        } else if (LogContext.CLIENT_ENVENT_CLIENTQUIT.equals(eventType)) {
            flush(true);
            flush("applog", true);
            b(eventType);
        } else if (LogContext.ENVENT_USERLOGIN.equals(eventType)) {
            String curUserid = (String) eventParam;
            if (!TextUtils.isEmpty(curUserid)) {
                boolean isOtherUser = !curUserid.equals(this.n.g());
                this.n.g(curUserid);
                LoggerFactory.getTraceLogger().info("LogContext", "contextInfo.setUserId: " + curUserid);
                LogStrategyManager.getInstance().queryStrategy("login", isOtherUser);
                Behavor behavor2 = new Behavor();
                behavor2.setSeedID("login");
                behavor2.setParam1(curUserid);
                appendLogEvent(new LogEvent(LogCategory.CATEGORY_ALIVEREPORT, null, Level.ERROR, null, PendingRender.a(new BehavorRender(this), "event", behavor2)));
                b(eventType);
            }
        } else if (LogContext.CLIENT_ENVENT_PERIODCHECK.equals(eventType)) {
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: periodCheck");
            appendLogEvent(new LogEvent("uploadByEvent", null, Level.ERROR, eventType));
        } else if (LogContext.CLIENT_ENVENT_SWITCHPAGE.equals(eventType)) {
            LoggerFactory.getTraceLogger().info("LogContext", "notifyClientEvent: switchPage");
            appendLogEvent(new LogEvent("uploadByEvent", null, Level.ERROR, eventType));
        } else if (LogContext.ENVENT_BUGREPORT.equals(eventType)) {
            LogStrategyManager.getInstance().queryStrategy("feedback", true);
            new Thread(new b(this), "LogContext.BUGREPORT").start();
            BugReportAnalyzer.a().a(eventParam);
            flush(null, false);
            uploadAfterSync(null);
        } else if (LogContext.ENVENT_DUMPLOGTOSD.equals(eventType)) {
            String dumpTag = (String) eventParam;
            if (!TextUtils.isEmpty(dumpTag)) {
                new Thread(new c(this, dumpTag), "LogContext.DUMPLOGTOSD").start();
            }
        } else {
            LoggerFactory.getTraceLogger().warn((String) "LogContext", "notifyClientEvent, eventType: " + eventType + ", eventParam: " + eventParam);
        }
    }

    /* access modifiers changed from: private */
    public void a(String logCategory) {
        if (!FileUtil.isCanUseSdCard()) {
            LoggerFactory.getTraceLogger().info("LogContext", "dumpLogToSD fail:" + logCategory);
            return;
        }
        File dumpDir = new File(this.e.getFilesDir(), logCategory);
        File targetDir = new File(new File(LoggingUtil.getCommonExternalStorageDir(), this.e.getPackageName()), logCategory + "_dump");
        if (!targetDir.exists()) {
            try {
                targetDir.mkdirs();
            } catch (Throwable th) {
            }
        }
        if (dumpDir.exists() && dumpDir.isDirectory()) {
            File[] dumpFiles = dumpDir.listFiles();
            if (dumpFiles != null) {
                for (File dumpFile : dumpFiles) {
                    if (dumpFile != null) {
                        try {
                            FileUtil.copyFile(dumpFile, new File(targetDir, dumpFile.getName()));
                        } catch (Throwable e2) {
                            LoggerFactory.getTraceLogger().error((String) "LogContext", e2);
                        }
                    }
                }
            }
        }
    }

    private synchronized void b(String eventType) {
        LoggerFactory.getTraceLogger().info("LogContext", "notifyUpload: " + eventType);
        LogStrategyManager.getInstance().getIntervalEventMap().put(eventType, eventType);
        appendLogEvent(new LogEvent("uploadByEvent", null, Level.ERROR, eventType));
    }

    private static void a(StringBuilder buf) {
        buf.append("[native crash on main thread but NONE returned, java stack traces are used instead]\n");
        try {
            StackTraceElement[] traces = Looper.getMainLooper().getThread().getStackTrace();
            if (traces != null) {
                for (StackTraceElement trace : traces) {
                    buf.append(9).append(trace);
                    buf.append(10);
                }
            }
        } catch (Throwable th) {
        }
    }

    public boolean traceNativeCrash(String filePath, String callStack, boolean isBoot) {
        String processName = LoggerFactory.getProcessInfo().getProcessAlias();
        LoggerFactory.getTraceLogger().error((String) "LogContext", "traceNativeCrash, filePath:" + filePath + ", isBoot:" + isBoot + ", process: " + processName);
        if (LoggerFactory.getProcessInfo().isMainProcess() || LoggerFactory.getProcessInfo().isExtProcess() || LoggerFactory.getProcessInfo().isLiteProcess()) {
            String callStack2 = a(filePath, callStack, isBoot);
            Intent intent = new Intent();
            if (LogStrategyManager.getInstance().isDisableToolsProcess()) {
                intent.setClassName(this.e, LogContext.PUSH_RECEIVER_CLASS_NAME);
            } else {
                intent.setClassName(this.e, LogContext.TOOLS_RECEIVER_CLASS_NAME);
            }
            return a(intent, filePath, callStack2, isBoot);
        } else if (LoggerFactory.getProcessInfo().isPushProcess()) {
            if (LogStrategyManager.getInstance().isDisableToolsProcess()) {
                b(filePath, callStack, isBoot);
                return false;
            }
            Intent intent2 = new Intent();
            intent2.setClassName(this.e, LogContext.TOOLS_RECEIVER_CLASS_NAME);
            return a(intent2, filePath, callStack, isBoot);
        } else if (LoggerFactory.getProcessInfo().isToolsProcess()) {
            b(filePath, callStack, isBoot);
            return false;
        } else {
            LoggerFactory.getTraceLogger().error((String) "LogContext", "traceNativeCrash, error: unknown process " + processName);
            b(filePath, callStack, isBoot);
            return false;
        }
    }

    private static String a(String filePath, String callStack, boolean isBoot) {
        StringBuilder buf;
        if (isBoot) {
            return callStack;
        }
        StringBuilder buf2 = null;
        try {
            if (TextUtils.isEmpty(filePath)) {
                buf = new StringBuilder();
                try {
                    buf.append("file path is empty");
                    buf2 = buf;
                } catch (Throwable th) {
                    t = th;
                    StringBuilder sb = buf;
                    LoggerFactory.getTraceLogger().error((String) "LogContext", t);
                    return callStack;
                }
            } else {
                File file = new File(filePath);
                if (!file.exists() || !file.isFile()) {
                    buf = new StringBuilder();
                    buf.append(filePath).append(" is not exist");
                    buf2 = buf;
                }
            }
            if (buf2 != null) {
                buf2.append(", logType: ").append(callStack);
                buf2.append(10);
                a(buf2);
                callStack = buf2.toString();
            }
        } catch (Throwable th2) {
            t = th2;
            LoggerFactory.getTraceLogger().error((String) "LogContext", t);
            return callStack;
        }
        return callStack;
    }

    private boolean a(Intent intent, String filePath, String callStack, boolean isBoot) {
        try {
            intent.setPackage(this.e.getPackageName());
        } catch (Throwable th) {
        }
        intent.setAction(this.e.getPackageName() + LogContext.ACTION_MONITOR_COMMAND);
        intent.putExtra("action", this.e.getPackageName() + ".monitor.action.TRACE_NATIVE_CRASH");
        intent.putExtra("filePath", filePath);
        intent.putExtra("callStack", callStack);
        intent.putExtra("isBoot", isBoot);
        boolean isSuccess = false;
        try {
            this.e.sendBroadcast(intent);
            isSuccess = true;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error("LogContext", "handleNativeCrashBySendBroadcast", e2);
        }
        boolean isDisableTools = LogStrategyManager.getInstance().isDisableToolsProcess();
        StringBuilder message = new StringBuilder("handleNativeCrashBySendBroadcast: send native crash broadcast");
        message.append(", filePath: ").append(filePath);
        message.append(", isBoot: ").append(isBoot);
        message.append(", success: ").append(isSuccess);
        message.append(", process: ").append(LoggerFactory.getProcessInfo().getProcessAlias());
        message.append(", disableTools: ").append(isDisableTools);
        LoggerFactory.getTraceLogger().info("LogContext", message.toString());
        if (isSuccess) {
            return true;
        }
        b(filePath, callStack, isBoot);
        return false;
    }

    private void b(String filePath, String callStack, boolean isBoot) {
        ExceptionID exceptionID;
        String crashInfo = null;
        if (isBoot) {
            try {
                crashInfo = CrashCombineUtils.getLatestTombAndDelOld(this.e);
                if (crashInfo != null) {
                    NativeCrashHandlerApi.onReportCrash(crashInfo, filePath, callStack, false);
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) "LogContext", e2);
                return;
            }
        } else {
            LoggerFactory.getTraceLogger().error((String) "LogContext", (String) "handleNativeCrashByAppendDirectly, !isBoot");
            crashInfo = CrashCombineUtils.UserTrackReport(filePath, callStack);
            CrashCombineUtils.deleteFileByPath(filePath);
            NativeCrashHandlerApi.onReportCrash(crashInfo, filePath, callStack, true);
        }
        if (!isBoot || crashInfo != null) {
            String logcatMessage = "handleNativeCrashByAppendDirectly: " + crashInfo;
            LoggerFactory.getTraceLogger().error((String) "LogContext", logcatMessage);
            LoggingUtil.reflectErrorLogAutomationCrash(logcatMessage);
            try {
                if (LoggingUtil.isDebuggable(LoggerFactory.getLogContext().getApplicationContext())) {
                    for (String line : logcatMessage.split("\n")) {
                        LoggingUtil.reflectErrorLog("Crash", line, false);
                    }
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "LogContext", tr);
            }
            boolean isCurrentVersionCrash = CrashFilterUtils.isCurrentVersion(crashInfo, getProductVersion(), isBoot);
            boolean isKnownInvalidCrashes = CrashFilterUtils.isKnownInvalidCrash(crashInfo);
            String processAlias = CrashFilterUtils.getProcessAlias(crashInfo);
            if (!isCurrentVersionCrash || isKnownInvalidCrashes) {
                exceptionID = ExceptionID.MONITORPOINT_INVALID_CRASH;
            } else if ("main".equals(processAlias)) {
                int crashSignal = CrashFilterUtils.isIgnoreCrash(crashInfo);
                if (crashSignal == 6 || crashSignal == 9 || crashSignal == 15) {
                    exceptionID = ExceptionID.MONITORPOINT_IGNORE_CRASH;
                } else {
                    exceptionID = ExceptionID.MONITORPOINT_CRASH;
                }
            } else if ("push".equals(processAlias) || ProcessInfo.ALIAS_TOOLS.equals(processAlias) || ProcessInfo.ALIAS_EXT.equals(processAlias) || ProcessInfo.ALIAS_LITE.equals(processAlias)) {
                exceptionID = ExceptionID.MONITORPOINT_IGNORE_CRASH;
                int crashSignal2 = CrashFilterUtils.isIgnoreCrash(crashInfo);
                if (crashSignal2 == 9) {
                    return;
                }
                if (crashSignal2 == 15) {
                    return;
                }
            } else {
                exceptionID = ExceptionID.MONITORPOINT_CLIENTSERR;
            }
            String ext = null;
            try {
                ext = StatisticalExceptionHandler.getInstance().getExternalExceptionInfo(null);
            } catch (Throwable th) {
            }
            syncAppendLogEvent(new LogEvent("crash", null, Level.ERROR, new ExceptionRender(this).a(exceptionID, crashInfo, ext, isBoot, processAlias, "unknown", true)));
            CrashAnalyzer.analyzeNativeCrash(this.e, crashInfo);
        }
    }

    public String getLogHost() {
        if (this.g == null) {
            try {
                ApplicationInfo appInfo = this.e.getPackageManager().getApplicationInfo(this.e.getPackageName(), 128);
                if (!(appInfo == null || appInfo.metaData == null)) {
                    this.g = appInfo.metaData.getString("logging.gateway");
                    if (this.g == null) {
                        this.g = "";
                    }
                }
            } catch (Throwable e2) {
                Log.w("LogContext", e2);
            }
        }
        String logHost = this.g;
        if (this.h == null) {
            this.h = LoggingUtil.getZhizhiSetting(this.e, "content://com.alipay.setting/MdapLogUrlPrefix", logHost);
            if (this.h == null) {
                this.h = "";
            }
        }
        if (LoggingUtil.isDebuggable(this.e) && TextUtils.isEmpty(logHost)) {
            logHost = this.h;
        }
        if (this.n != null && TextUtils.isEmpty(logHost)) {
            if ("dev".equals(this.n.c())) {
                logHost = "http://mdap-1-64.test.alipay.net";
            } else if (!TextUtils.isEmpty(this.n.a())) {
                logHost = this.n.a();
            } else if (UploadUrlConfig.a().c()) {
                logHost = LogContext.LOG_HOST_RELEASE;
            } else {
                logHost = LogContext.LOG_HOST_HTTPS_RELEASE;
            }
        }
        this.f = logHost;
        LoggerFactory.getTraceLogger().info("LogContext", "getLogHost: " + this.f);
        return this.f;
    }

    public String getClientStatus(boolean isNativeCrash) {
        return getClientStatus(false, isNativeCrash, null);
    }

    public String getClientStatus(boolean traceNativeCrashWhenBoot, boolean isNativeCrash, String exception) {
        long now = System.currentTimeMillis();
        LoggerFactory.getTraceLogger().info("LogContext", "getClientStatus: , now: " + now + ", gotoBackgroundTimestamp: " + this.t + ", clientLaunchTimestamp: " + this.s + ", processSetupTimestamp: " + this.a);
        if (isNativeCrash) {
            String clientStatus = CrashFilterUtils.getNativeCrashClientStatus(exception);
            if (!TextUtils.isEmpty(clientStatus)) {
                return clientStatus;
            }
            if (traceNativeCrashWhenBoot) {
                long lastBackgroudTime = LogStrategyManager.getInstance().getBackgroundTime();
                long crashTime = CrashCombineUtils.getCrashTime();
                if (crashTime <= 0 || lastBackgroudTime <= 0 || crashTime >= now || crashTime <= TimeUnit.MINUTES.toMillis(5) + lastBackgroudTime) {
                    return "unknown";
                }
                return Subscribe.THREAD_BACKGROUND;
            }
        }
        if (this.t > 0 && now - this.t > TimeUnit.MINUTES.toMillis(5)) {
            return Subscribe.THREAD_BACKGROUND;
        }
        if (this.s <= 0 && this.a > 0) {
            if (now - this.a > TimeUnit.MINUTES.toMillis(1)) {
                return Subscribe.THREAD_BACKGROUND;
            }
            try {
                Map startupReason = LoggerFactory.getProcessInfo().getStartupReason();
                String actionName = null;
                String componentName = null;
                if (startupReason != null) {
                    actionName = startupReason.get(ProcessInfo.SR_ACTION_NAME);
                    componentName = startupReason.get(ProcessInfo.SR_COMPONENT_NAME);
                }
                boolean isBackgroundLaunch = CrashFilterUtils.isBackgroundLaunch(actionName, componentName);
                boolean isPotentialBackgroundCrash = false;
                if (TextUtils.isEmpty(actionName) && TextUtils.isEmpty(componentName)) {
                    isPotentialBackgroundCrash = CrashFilterUtils.isPotentialBackgroundCrash(exception);
                }
                if (isBackgroundLaunch || isPotentialBackgroundCrash) {
                    return Subscribe.THREAD_BACKGROUND;
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "LogContext", tr);
            }
        }
        return "foreground";
    }

    private void e() {
        DeviceInfo.getInstance(this.e).updateAccessibilityState();
        if (DeviceInfo.getInstance(this.e).getIsAccessibilityEnabled()) {
            putBizExternParams("VoiceOver", "1");
        } else {
            putBizExternParams("VoiceOver", "0");
        }
    }

    private void f() {
        try {
            putBizExternParams("TimeZone", TimeZone.getDefault().getID());
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "LogContext", e2);
        }
    }

    public void adjustRequestSpanByReceived() {
        LogStrategyManager.getInstance().adjustRequestSpanByNetNotMatch();
    }

    public void adjustRequestSpanByNetNotMatch() {
        LogStrategyManager.getInstance().adjustRequestSpanByNetNotMatch();
    }

    public void adjustRequestSpanByZipFail() {
        LogStrategyManager.getInstance().adjustRequestSpanByZipFail();
    }

    public void adjustRequestSpanByUploadFail() {
        LogStrategyManager.getInstance().adjustRequestSpanByUploadFail();
    }

    public void revertRequestSpanToNormal() {
        LogStrategyManager.getInstance().revertRequestSpanToNormal();
    }

    public int getDevicePerformanceScore() {
        try {
            return YearClass.a(this.e);
        } catch (Throwable t2) {
            Log.w("LogContext", t2);
            return Integer.MAX_VALUE;
        }
    }

    public boolean isLowEndDevice() {
        return LowEndDeviceUtil.isLowEndDevice(this.e);
    }

    public void syncLogConfig(String content) {
        LogStrategyManager.getInstance().syncLogConfig(content);
    }

    public void putBizExternParams(String key, String value) {
        if (key != null && value != null) {
            this.k.put(key, value);
        }
    }

    public Map<String, String> getBizExternParams() {
        return this.k;
    }

    public void setLogAppenderistener(LogAppenderistener listener) {
        this.c = listener;
    }

    public LogAppenderistener getLogAppenderistener() {
        return this.c;
    }

    public Map<String, LogStrategyInfo> getLogStrategyInfos() {
        return LogStrategyManager.getInstance().getLogStrategyInfos();
    }

    public void setBehavorLogListener(BehavorLogListener listener) {
        this.w = listener;
    }

    public BehavorLogListener getBehavorLogListener() {
        return this.w;
    }

    public void setAbtestInfoGetter(AbtestInfoGetter abtestInfoGetter) {
        this.r = abtestInfoGetter;
    }

    public AbtestInfoGetter getAbtestInfoGetter() {
        return this.r;
    }

    public void setLogUploadRpcClient(RpcClient rpcClient) {
        this.B = rpcClient;
    }

    public RpcClient getLogUploadRpcClient() {
        return this.B;
    }

    public void setLogEncryptClient(LogEncryptClient logEncryptClient) {
        this.A = logEncryptClient;
    }

    public LogEncryptClient getLogEncryptClient() {
        return this.A;
    }

    public void setSpmMonitor(ISpmMonitor spmMonitor) {
        this.x = spmMonitor;
    }

    public ISpmMonitor getSpmMonitor() {
        return this.x;
    }

    public void setSemMonitor(ISemMonitor semMonitor) {
        this.y = semMonitor;
    }

    public ISemMonitor getSemMonitor() {
        return this.y;
    }

    public void setTinyPageMonitor(ITinyPageMonitor tinyPageMonitor) {
        this.z = tinyPageMonitor;
    }

    public ITinyPageMonitor getTinyPageMonitor() {
        return this.z;
    }

    public void setLogCustomerControl(LogCustomerControl logCustomerControl) {
        this.d = logCustomerControl;
    }

    public LogCustomerControl getLogCustomerControl() {
        return this.d;
    }

    public void setMaxLogSize(int size) {
        MdapLogUploadManager.a(size);
    }

    public DevicePerformanceScore getDevicePerformanceScoreNew() {
        try {
            return DevicePerformanceScoreHelper.a(this.e).a();
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "LogContext", tr);
            return DevicePerformanceScore.LOW;
        }
    }

    public void setLogDAUTracker(LogDAUTracker logDAUTracker) {
        this.D = logDAUTracker;
    }

    public LogDAUTracker getLogDAUTracker() {
        return this.D;
    }

    public void setNetworkInfoGetter(NetworkInfoGetter networkInfoGetter) {
        this.E = networkInfoGetter;
    }

    public NetworkInfoGetter getNetworkInfoGetter() {
        return this.E;
    }

    private boolean g() {
        try {
            if (this.C == null) {
                this.C = this.e.getPackageName();
            }
        } catch (Throwable th) {
        }
        if (this.C != null) {
            return this.C.contains("com.eg.android.AlipayGphone");
        }
        return true;
    }
}
