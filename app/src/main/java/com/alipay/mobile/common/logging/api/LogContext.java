package com.alipay.mobile.common.logging.api;

import android.content.Context;
import android.os.Bundle;
import com.alipay.android.phone.wallet.spmtracker.ISemMonitor;
import com.alipay.android.phone.wallet.spmtracker.ISpmMonitor;
import com.alipay.android.phone.wallet.spmtracker.ITinyPageMonitor;
import com.alipay.mobile.common.logging.api.abtest.AbtestInfoGetter;
import com.alipay.mobile.common.logging.api.behavor.BehavorLogListener;
import com.alipay.mobile.common.logging.api.encrypt.LogEncryptClient;
import com.alipay.mobile.common.logging.api.network.NetworkInfoGetter;
import com.alipay.mobile.common.logging.api.rpc.RpcClient;
import com.alipay.mobile.common.logging.strategy.LogStrategyInfo;
import java.util.Map;

public interface LogContext {
    public static final String ACTION_DYNAMIC_RELEASE = ".monitor.action.DYNAMIC_RELEASE";
    public static final String ACTION_MONITOR_COMMAND = ".monitor.command";
    public static final String ACTION_TRACE_NATIVECRASH = ".monitor.action.TRACE_NATIVE_CRASH";
    public static final String ACTION_UPDATE_LOG_CONTEXT = ".monitor.action.UPDATE_LOG_CONTEXT";
    public static final String ACTION_UPDATE_LOG_CONTEXT_BATCH = ".monitor.action.UPDATE_LOG_CONTEXT_BATCH";
    public static final String ACTION_UPDATE_LOG_STRATEGY = ".monitor.action.UPDATE_LOG_STRATEGY";
    public static final String ACTION_UPLOAD_MDAPLOG = ".monitor.action.upload.mdaplog";
    public static final String AMAP_LOG_SERVICE_CLASS_NAME = "com.autonavi.miniapp.monitor.biz.logmonitor.ClientMonitorService";
    public static final String CLIENT_ENVENT_CLIENTLAUNCH = "ClientEvent_ClientLaunch";
    public static final String CLIENT_ENVENT_CLIENTQUIT = "ClientEvent_ClientQuit";
    public static final String CLIENT_ENVENT_GOTOFOREGROUND = "ClientEvent_GotoForeground";
    public static final String CLIENT_ENVENT_PAGELAUNCH = "ClientEvent_PageLaunch";
    public static final String CLIENT_ENVENT_PERIODCHECK = "periodCheck";
    public static final String CLIENT_ENVENT_SWITCHPAGE = "switchPage";
    public static final String ENVENT_BUGREPORT = "bugReport";
    @Deprecated
    public static final String ENVENT_CLIENTLAUNCH = "clientLaunch";
    @Deprecated
    public static final String ENVENT_CLIENTQUIT = "clientQuit";
    public static final String ENVENT_DUMPLOGTOSD = "dumpLogToSD";
    @Deprecated
    public static final String ENVENT_GOTOBACKGROUND = "gotoBackground";
    @Deprecated
    public static final String ENVENT_GOTOFOREGROUND = "gotoForeground";
    public static final String ENVENT_SUBAPPRESUME = "subappResume";
    public static final String ENVENT_SUBAPPSTART = "subappStart";
    public static final String ENVENT_USERLOGIN = "userLogin";
    public static final String ENVENT_VIEWSWITCH = "viewSwitch";
    public static final String IS_DEGRADE_UPLOAD = "isDegradeUpload";
    public static final String IS_MERGE_UPLOAD = "isMergeUpload";
    public static final String LOCAL_STORAGE_ACTIONDESC = "actionDesc";
    public static final String LOCAL_STORAGE_ACTIONID = "actionID";
    public static final String LOCAL_STORAGE_ACTIONTIMESTAMP = "actionTimestamp";
    public static final String LOCAL_STORAGE_ACTIONTOKEN = "actionToken";
    public static final String LOCAL_STORAGE_REFER = "actionRefer";
    public static final String LOG_HOST_CONFIG_SP = "LoggingUrlConfig";
    public static final String LOG_HOST_CONFIG_SP_DISABLE_HTTPS = "LogUploadDisableHttps";
    public static final String LOG_HOST_CONFIG_SP_DISABLE_HTTPS_TIME = "LogUploadDisableHttpsTime";
    public static final String LOG_HOST_HTTPS_RELEASE = "https://mdap.alipay.com";
    public static final String LOG_HOST_RELEASE = "http://mdap.alipaylog.com";
    public static final String MAIN_SERVICE_CLASS_NAME = "com.alipay.mobile.common.logging.process.LogServiceInMainProcess";
    public static final String NEED_MOVE = "needMove";
    public static final String PARAM_KLOG_TRACEID_KEY = "kLogTraceIdKey";
    public static final int PERFORMANCE_SCORE_ENDURE = 2013;
    public static final int PERFORMANCE_SCORE_ENDURE_2013 = 2013;
    public static final int PERFORMANCE_SCORE_ENDURE_2014 = 2014;
    public static final int PERFORMANCE_SCORE_ENDURE_2015 = 2015;
    public static final String PUSH_LOG_SERVICE_CLASS_NAME = "com.alipay.mobile.common.logging.process.LogServiceInPushProcess";
    public static final String PUSH_RECEIVER_CLASS_NAME = "com.alipay.mobile.logmonitor.ClientMonitorWakeupReceiver";
    public static final String PUSH_SERVICE_CLASS_NAME = "com.alipay.mobile.logmonitor.ClientMonitorService";
    public static final String RELEASETYPE_DEV = "dev";
    public static final String RELEASETYPE_RC = "rc";
    public static final String RELEASETYPE_RCPRE = "rcpre";
    public static final String RELEASETYPE_RELEASE = "release";
    public static final String RELEASETYPE_TEST = "test";
    public static final String RELEASETYPE_TESTPRE = "testpre";
    public static final String STORAGE_APPID = "appID";
    public static final String STORAGE_LOGCATEGORY_UPLOAD_PERFIX = "updateSize_";
    public static final String STORAGE_PAGESERIAL = "pageSerial";
    public static final String STORAGE_REFVIEWID = "refViewID";
    public static final String STORAGE_VIEWID = "viewID";
    public static final String SYNC_ALL_LOG = "syncAllLog";
    public static final String TOOLS_RECEIVER_CLASS_NAME = "com.alipay.mobile.common.logging.process.LogReceiverInToolsProcess";
    public static final String TOOLS_SERVICE_CLASS_NAME = "com.alipay.mobile.common.logging.process.LogServiceInToolsProcess";

    public enum DevicePerformanceScore {
        HIGH,
        LOW
    }

    void adjustRequestSpanByNetNotMatch();

    void adjustRequestSpanByReceived();

    void adjustRequestSpanByUploadFail();

    void adjustRequestSpanByZipFail();

    void adjustUploadCoreByCategoryDirectly(String str, String str2, Bundle bundle);

    void appendLogEvent(LogEvent logEvent);

    void backupCurrentFile(String str, boolean z);

    void commitExtrasToUpdate();

    void flush(String str, boolean z);

    void flush(String str, boolean z, Bundle bundle);

    void flush(boolean z);

    AbtestInfoGetter getAbtestInfoGetter();

    String getApkUniqueId();

    Context getApplicationContext();

    BehavorLogListener getBehavorLogListener();

    String getBirdNestVersion();

    Map<String, String> getBizExternParams();

    String getBundleVersion();

    String getChannelId();

    String getClientId();

    String getClientStatus(boolean z);

    String getClientStatus(boolean z, boolean z2, String str);

    String getContextParam(String str);

    String getDeviceId();

    int getDevicePerformanceScore();

    DevicePerformanceScore getDevicePerformanceScoreNew();

    String getHotpatchVersion();

    String getLanguage();

    String getLocalParam(String str);

    LogAppenderistener getLogAppenderistener();

    LogCustomerControl getLogCustomerControl();

    LogDAUTracker getLogDAUTracker();

    LogEncryptClient getLogEncryptClient();

    String getLogHost();

    Map<String, LogStrategyInfo> getLogStrategyInfos();

    RpcClient getLogUploadRpcClient();

    NetworkInfoGetter getNetworkInfoGetter();

    String getPackageId();

    String getProductId();

    String getProductVersion();

    String getReleaseCode();

    String getReleaseType();

    ISemMonitor getSemMonitor();

    String getSessionId();

    String getSourceId();

    ISpmMonitor getSpmMonitor();

    String getStorageParam(String str);

    ITinyPageMonitor getTinyPageMonitor();

    String getUserId();

    String getUserSessionId();

    boolean isDisableToolsProcess();

    boolean isEnableTrafficLimit();

    boolean isLowEndDevice();

    boolean isPositiveDiagnose();

    boolean isZipAndSevenZip();

    void notifyClientEvent(String str, Object obj);

    void putBizExternParams(String str, String str2);

    void putContextParam(String str, String str2);

    void putLocalParam(String str, String str2);

    void refreshSessionId();

    void removeContextParam(String str);

    void removeLocalParam(String str);

    void resetExtrasToSet();

    void resetLogHost();

    void revertRequestSpanToNormal();

    void setAbtestInfoGetter(AbtestInfoGetter abtestInfoGetter);

    void setApkUniqueId(String str);

    void setBehavorLogListener(BehavorLogListener behavorLogListener);

    void setBirdNestVersion(String str);

    void setBirdNestVersionNoCommit(String str);

    void setBundleVersion(String str);

    void setBundleVersionNoCommit(String str);

    void setChannelId(String str);

    void setChannelIdNoCommit(String str);

    void setClientId(String str);

    void setClientIdNoCommit(String str);

    void setDeviceId(String str);

    void setDeviceIdNoCommit(String str);

    void setHotpatchVersion(String str);

    void setHotpatchVersionNoCommit(String str);

    void setLanguage(String str);

    void setLanguageNoCommit(String str);

    void setLogAppenderistener(LogAppenderistener logAppenderistener);

    void setLogCustomerControl(LogCustomerControl logCustomerControl);

    void setLogDAUTracker(LogDAUTracker logDAUTracker);

    void setLogEncryptClient(LogEncryptClient logEncryptClient);

    @Deprecated
    void setLogHost(String str);

    void setLogHostNoCommit(String str);

    void setLogUploadRpcClient(RpcClient rpcClient);

    void setMaxLogSize(int i);

    void setNetworkInfoGetter(NetworkInfoGetter networkInfoGetter);

    void setPackageId(String str);

    void setPackageIdNoCommit(String str);

    void setProductId(String str);

    void setProductIdNoCommit(String str);

    void setProductVersion(String str);

    void setProductVersionNoCommit(String str);

    void setReleaseCode(String str);

    void setReleaseCodeNoCommit(String str);

    void setReleaseType(String str);

    void setReleaseTypeNoCommit(String str);

    void setSemMonitor(ISemMonitor iSemMonitor);

    void setSourceId(String str);

    void setSpmMonitor(ISpmMonitor iSpmMonitor);

    void setTinyPageMonitor(ITinyPageMonitor iTinyPageMonitor);

    void setUserId(String str);

    void setUserIdNoCommit(String str);

    void setUserSessionId(String str);

    void setupExceptionHandler(UncaughtExceptionCallback uncaughtExceptionCallback, int i);

    void syncAppendLogEvent(LogEvent logEvent);

    void syncLogConfig(String str);

    @Deprecated
    void takedownExceptionHandler();

    boolean traceNativeCrash(String str, String str2, boolean z);

    void updateLogStrategyCfg(String str);

    void upload(String str);

    void upload(String str, String str2);

    void upload(String str, String str2, Bundle bundle);

    void uploadAfterSync(String str);

    void uploadAfterSync(String str, String str2);

    void uploadAfterSync(String str, String str2, Bundle bundle);
}
