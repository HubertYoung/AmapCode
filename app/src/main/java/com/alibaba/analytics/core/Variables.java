package com.alibaba.analytics.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alibaba.analytics.core.Constants.Database;
import com.alibaba.analytics.core.Constants.RealTimeDebug;
import com.alibaba.analytics.core.config.DebugPluginSwitch;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.UTBaseConfMgr;
import com.alibaba.analytics.core.config.UTBussinessConfBiz;
import com.alibaba.analytics.core.config.UTConfigMgr;
import com.alibaba.analytics.core.config.UTDefaultConfMgr;
import com.alibaba.analytics.core.config.UTOrangeConfMgr;
import com.alibaba.analytics.core.config.UTRealtimeConfBiz;
import com.alibaba.analytics.core.config.UTSampleConfBiz;
import com.alibaba.analytics.core.config.UTStreamConfBiz;
import com.alibaba.analytics.core.db.DBMgr;
import com.alibaba.analytics.core.db.OldDBTransferMgr;
import com.alibaba.analytics.core.device.Device;
import com.alibaba.analytics.core.device.DeviceInfo;
import com.alibaba.analytics.core.logbuilder.TimeStampAdjustMgr;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.core.selfmonitor.CrashDispatcher;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorHandle;
import com.alibaba.analytics.core.sync.UploadMgr;
import com.alibaba.analytics.core.sync.UploadMode;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.Base64;
import com.alibaba.analytics.utils.DeviceUtil;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.SpSetting;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import com.ut.mini.UTAnalyticsDelegate;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static final String DEBUG_DATE = "debug_date";
    private static final long DEBUG_TIME = 14400000;
    private static final String TAG_OPENID = "_openid";
    private static final String TAG_TURNOFF_REAL_TIME = "real_time_debug";
    private static final String UTRTD_NAME = "UTRealTimeDebug";
    public static final Variables s_instance = new Variables();
    private volatile boolean bApIsRealTimeDebugging = false;
    private volatile boolean bInit = false;
    private boolean bPackageDebugSwitch = false;
    private boolean hasReadPackageBuildId = false;
    private boolean hasReadPackageDebugSwitch = false;
    private boolean isAllServiceClosed = false;
    private boolean isGzipUpload = false;
    private boolean isHttpService = false;
    private boolean isRealtimeServiceClosed = false;
    private String mAppVersion = null;
    private String mAppkey = null;
    private String mChannel = null;
    private UTBaseConfMgr mConfMgr = null;
    private Context mContext = null;
    private DBMgr mDbMgr = null;
    private boolean mDebugSamplingOption = false;
    private String mDebuggingKey = null;
    private boolean mIsOldDevice = false;
    private boolean mIsRealTimeDebugging = false;
    private boolean mIsSelfMonitorTurnOn = true;
    private volatile boolean mIsTurnOffDebugPlugin = false;
    private String mLUserid = null;
    private String mLUsernick = null;
    private String mOpenid;
    private String mPackageBuildId = null;
    private volatile IUTRequestAuthentication mRequestAuthenticationInstance = null;
    private String mSecret = null;
    private Map<String, String> mSessionProperties = null;
    private volatile String mTPKString = null;
    private String mTransferUrl = null;
    private String mUserid = null;
    private String mUsernick = null;

    public static boolean isNotDisAM() {
        return true;
    }

    public static Variables getInstance() {
        return s_instance;
    }

    public void turnOffDebugPlugin() {
        this.mIsTurnOffDebugPlugin = true;
    }

    public void turnOnSelfMonitor() {
        this.mIsSelfMonitorTurnOn = true;
    }

    public void turnOffSelfMonitor() {
        this.mIsSelfMonitorTurnOn = false;
    }

    public boolean isSelfMonitorTurnOn() {
        return this.mIsSelfMonitorTurnOn;
    }

    public synchronized void setHttpService(boolean z) {
        this.isHttpService = z;
    }

    public synchronized boolean isHttpService() {
        return this.isHttpService;
    }

    public synchronized void setAllServiceClosed(boolean z) {
        this.isAllServiceClosed = z;
    }

    public synchronized boolean isAllServiceClosed() {
        return this.isAllServiceClosed;
    }

    public synchronized void setRealtimeServiceClosed(boolean z) {
        this.isRealtimeServiceClosed = z;
    }

    public synchronized boolean isRealtimeServiceClosed() {
        return this.isRealtimeServiceClosed;
    }

    public boolean isGzipUpload() {
        return this.isGzipUpload;
    }

    public void setGzipUpload(boolean z) {
        this.isGzipUpload = z;
    }

    public String getTpkMD5() {
        if (this.mTPKString == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTPKString.hashCode());
        return sb.toString();
    }

    public String getTPKString() {
        return this.mTPKString;
    }

    public void setTPKString(String str) {
        this.mTPKString = str;
    }

    public boolean isTurnOffDebugPlugin() {
        return this.mIsTurnOffDebugPlugin;
    }

    public synchronized void init(Application application) {
        Class<?> cls;
        this.mContext = application.getApplicationContext();
        if (this.mContext == null) {
            Logger.w((String) "UTDC init failed", "context:null");
            return;
        }
        Logger.i((String) null, "init", Boolean.valueOf(this.bInit));
        if (!this.bInit) {
            try {
                CrashDispatcher.getInstance().init();
            } catch (Throwable th) {
                Logger.e(null, th, new Object[0]);
            }
            try {
                SelfMonitorHandle.getInstance().init();
            } catch (Throwable th2) {
                Logger.e(null, th2, new Object[0]);
            }
            getLocalInfo();
            this.mDbMgr = new DBMgr(this.mContext, Database.DATABASE_NAME);
            NetworkUtil.register(this.mContext);
            OldDBTransferMgr.checkAndTransfer();
            try {
                cls = Class.forName("com.taobao.orange.OrangeConfig");
            } catch (Throwable unused) {
                cls = null;
            }
            if (cls != null) {
                this.mConfMgr = new UTOrangeConfMgr();
            } else {
                this.mConfMgr = new UTDefaultConfMgr();
            }
            this.mConfMgr.addConfBiz(UTSampleConfBiz.getInstance());
            this.mConfMgr.addConfBiz(UTStreamConfBiz.getInstance());
            this.mConfMgr.addConfBiz(new UTBussinessConfBiz());
            this.mConfMgr.addConfBiz(AMSamplingMgr.getInstance());
            this.mConfMgr.addConfBiz(UTRealtimeConfBiz.getInstance());
            try {
                this.mConfMgr.addConfBiz(SystemConfigMgr.getInstance());
                SystemConfigMgr.getInstance().register(DebugPluginSwitch.KEY, new DebugPluginSwitch());
            } catch (Throwable th3) {
                Logger.e(null, th3, new Object[0]);
            }
            this.mConfMgr.requestOnlineConfig();
            TimeStampAdjustMgr.getInstance().startSync();
            AppMonitorDelegate.init(application);
            UTAnalyticsDelegate.getInstance().initUT(application);
            initRealTimeDebug();
            UploadMgr.getInstance().start();
            trackInfoForPreLoad();
            this.bInit = true;
            return;
        }
        UTConfigMgr.postAllServerConfig();
        return;
    }

    private void trackInfoForPreLoad() {
        try {
            Map<String, String> infoForPreApk = AppInfoUtil.getInfoForPreApk(this.mContext);
            if (infoForPreApk != null && infoForPreApk.size() > 0) {
                HashMap hashMap = new HashMap();
                hashMap.put(LogField.EVENTID.toString(), "1021");
                hashMap.putAll(infoForPreApk);
                UTAnalyticsDelegate.getInstance().transferLog(hashMap);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public UTBaseConfMgr getConfMgr() {
        return this.mConfMgr;
    }

    private void getLocalInfo() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("UTCommon", 0);
        String string = sharedPreferences.getString("_lun", "");
        if (!StringUtils.isEmpty(string)) {
            try {
                this.mLUsernick = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String string2 = sharedPreferences.getString("_luid", "");
        if (!StringUtils.isEmpty(string2)) {
            try {
                this.mLUserid = new String(Base64.decode(string2.getBytes(), 2), "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        String string3 = sharedPreferences.getString(TAG_OPENID, "");
        if (!StringUtils.isEmpty(string3)) {
            try {
                this.mOpenid = new String(Base64.decode(string3.getBytes(), 2), "UTF-8");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getAppkey() {
        return this.mAppkey;
    }

    public String getSecret() {
        return this.mSecret;
    }

    public void setSecret(String str) {
        this.mSecret = str;
    }

    public void setAppVersion(String str) {
        this.mAppVersion = str;
    }

    public String getAppVersion() {
        if (TextUtils.isEmpty(this.mAppVersion)) {
            Map<String, String> deviceInfo = DeviceUtil.getDeviceInfo(getContext());
            if (deviceInfo != null) {
                this.mAppVersion = deviceInfo.get(LogField.APPVERSION);
            }
        }
        return this.mAppVersion;
    }

    public void setRequestAuthenticationInstance(IUTRequestAuthentication iUTRequestAuthentication) {
        this.mRequestAuthenticationInstance = iUTRequestAuthentication;
        if (iUTRequestAuthentication != null) {
            this.mAppkey = iUTRequestAuthentication.getAppkey();
        }
    }

    public IUTRequestAuthentication getRequestAuthenticationInstance() {
        return this.mRequestAuthenticationInstance;
    }

    public void turnOnDebug() {
        setDebug(true);
    }

    public void setChannel(String str) {
        Logger.d((String) null, str, str);
        this.mChannel = str;
    }

    public String getLongLoginUsernick() {
        return this.mLUsernick;
    }

    public String getLongLoingUserid() {
        return this.mLUserid;
    }

    public String getChannel() {
        if (TextUtils.isEmpty(this.mChannel)) {
            String str = SpSetting.get(getContext(), "channel");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return this.mChannel;
    }

    public String getUsernick() {
        return this.mUsernick;
    }

    public String getUserid() {
        return this.mUserid;
    }

    public String getOpenid() {
        return this.mOpenid;
    }

    public void updateUserAccount(String str, String str2, String str3) {
        setUsernick(str);
        updateUserIdAndOpenId(str2, str3);
        storeUsernick(str);
    }

    private void updateUserIdAndOpenId(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            setUserid(null);
            setOpenid(null);
            return;
        }
        if (!TextUtils.isEmpty(str2) || !str.equals(this.mUserid)) {
            setUserid(str);
            setOpenid(str2);
            storeUserId(str);
            storeOpenId(str2);
        }
    }

    @Deprecated
    public void setIsOldDevice(boolean z) {
        if (this.mContext != null) {
            this.mContext.getSharedPreferences("UTCommon", 0).edit().putBoolean("_isolddevice", z).commit();
        }
    }

    @Deprecated
    public boolean isOldDevice() {
        if (!this.mIsOldDevice && this.mContext != null) {
            this.mIsOldDevice = this.mContext.getSharedPreferences("UTCommon", 0).getBoolean("_isolddevice", false);
        }
        return this.mIsOldDevice;
    }

    public synchronized String getHostPackageImei() {
        DeviceInfo device = Device.getDevice(this.mContext);
        if (device == null) {
            return "";
        }
        return device.getImei();
    }

    public synchronized String getHostPackageImsi() {
        DeviceInfo device = Device.getDevice(this.mContext);
        if (device == null) {
            return "";
        }
        return device.getImsi();
    }

    public synchronized void setDebugSamplingOption() {
        this.mDebugSamplingOption = true;
        AppMonitorDelegate.IS_DEBUG = true;
    }

    public synchronized boolean getDebugSamplingOption() {
        return this.mDebugSamplingOption;
    }

    public synchronized void setSessionProperties(Map<String, String> map) {
        this.mSessionProperties = map;
    }

    public synchronized Map<String, String> getSessionProperties() {
        return this.mSessionProperties;
    }

    public synchronized void setDebugKey(String str) {
        this.mDebuggingKey = str;
    }

    public synchronized String getDebugKey() {
        return this.mDebuggingKey;
    }

    public synchronized boolean isRealTimeDebug() {
        return this.mIsRealTimeDebugging;
    }

    public synchronized void setRealTimeDebugFlag() {
        this.mIsRealTimeDebugging = true;
    }

    public synchronized void resetRealTimeDebugFlag() {
        this.mIsRealTimeDebugging = false;
    }

    public boolean isApRealTimeDebugging() {
        return this.bApIsRealTimeDebugging;
    }

    public void turnOffRealTimeDebug() {
        resetRealTimeDebugFlag();
        setDebugKey(null);
        UploadMgr.getInstance().setMode(UploadMode.INTERVAL);
        storeRealTimeDebugSharePreference(null);
        this.bApIsRealTimeDebugging = false;
    }

    public void turnOnRealTimeDebug(Map<String, String> map) {
        Logger.d();
        if ("0".equalsIgnoreCase(SystemConfigMgr.getInstance().get(TAG_TURNOFF_REAL_TIME))) {
            Logger.w((String) "Variables", "Server Config turn off RealTimeDebug Mode!");
            return;
        }
        if (map != null && map.containsKey(RealTimeDebug.DEBUG_API_URL) && map.containsKey(RealTimeDebug.DEBUG_KEY)) {
            String str = map.get(RealTimeDebug.DEBUG_KEY);
            if (!StringUtils.isEmpty(map.get(RealTimeDebug.DEBUG_API_URL)) && !StringUtils.isEmpty(str)) {
                setRealTimeDebugFlag();
                setDebugKey(str);
            }
            if (map.containsKey(RealTimeDebug.DEBUG_SAMPLING_OPTION)) {
                setDebugSamplingOption();
            }
            setDebug(true);
            UploadMgr.getInstance().setMode(UploadMode.REALTIME);
        }
        storeRealTimeDebugSharePreference(map);
    }

    private void storeRealTimeDebugSharePreference(Map<String, String> map) {
        if (this.mContext != null) {
            Logger.d((String) "", map);
            Editor edit = this.mContext.getSharedPreferences(UTRTD_NAME, 0).edit();
            if (map == null || !map.containsKey(RealTimeDebug.DEBUG_STORE)) {
                edit.putLong(DEBUG_DATE, 0);
            } else {
                edit.putString(RealTimeDebug.DEBUG_API_URL, map.get(RealTimeDebug.DEBUG_API_URL));
                edit.putString(RealTimeDebug.DEBUG_KEY, map.get(RealTimeDebug.DEBUG_KEY));
                edit.putLong(DEBUG_DATE, System.currentTimeMillis());
            }
            edit.commit();
        }
    }

    private void initRealTimeDebug() {
        if (this.mContext != null) {
            Logger.d();
            SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(UTRTD_NAME, 0);
            long j = sharedPreferences.getLong(DEBUG_DATE, 0);
            Logger.d((String) "", "debugDate", Long.valueOf(j));
            if (System.currentTimeMillis() - j <= DEBUG_TIME) {
                HashMap hashMap = new HashMap();
                hashMap.put(RealTimeDebug.DEBUG_API_URL, sharedPreferences.getString(RealTimeDebug.DEBUG_API_URL, ""));
                hashMap.put(RealTimeDebug.DEBUG_KEY, sharedPreferences.getString(RealTimeDebug.DEBUG_KEY, ""));
                turnOnRealTimeDebug(hashMap);
            }
        }
    }

    public void setDebug(boolean z) {
        Logger.setDebug(z);
    }

    @Deprecated
    public String getTransferUrl() {
        return this.mTransferUrl;
    }

    public DBMgr getDbMgr() {
        return this.mDbMgr;
    }

    public boolean isInit() {
        return this.bInit;
    }

    public boolean isDebugPackage() {
        if (this.hasReadPackageDebugSwitch) {
            return this.bPackageDebugSwitch;
        }
        Context context = getContext();
        if (context == null) {
            return false;
        }
        if ("1".equalsIgnoreCase(AppInfoUtil.getString(context, "package_type"))) {
            this.bPackageDebugSwitch = true;
            this.hasReadPackageDebugSwitch = true;
        }
        return this.bPackageDebugSwitch;
    }

    public String getPackageBuildId() {
        if (this.hasReadPackageBuildId) {
            return this.mPackageBuildId;
        }
        Context context = getContext();
        if (context == null) {
            return null;
        }
        this.mPackageBuildId = AppInfoUtil.getString(context, "build_id");
        this.hasReadPackageBuildId = true;
        return this.mPackageBuildId;
    }

    private void setUsernick(String str) {
        this.mUsernick = str;
        if (!StringUtils.isEmpty(str)) {
            this.mLUsernick = str;
        }
    }

    private void storeUsernick(String str) {
        if (!StringUtils.isEmpty(str) && this.mContext != null) {
            try {
                Editor edit = this.mContext.getSharedPreferences("UTCommon", 0).edit();
                edit.putString("_lun", new String(Base64.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserid(String str) {
        this.mUserid = str;
        if (!StringUtils.isEmpty(str)) {
            this.mLUserid = str;
        }
    }

    private void storeUserId(String str) {
        if (!StringUtils.isEmpty(str) && this.mContext != null) {
            try {
                Editor edit = this.mContext.getSharedPreferences("UTCommon", 0).edit();
                edit.putString("_luid", new String(Base64.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void setOpenid(String str) {
        this.mOpenid = str;
    }

    private void storeOpenId(String str) {
        if (this.mContext != null) {
            try {
                Editor edit = this.mContext.getSharedPreferences("UTCommon", 0).edit();
                if (TextUtils.isEmpty(str)) {
                    edit.putString(TAG_OPENID, null);
                } else {
                    edit.putString(TAG_OPENID, new String(Base64.encode(str.getBytes("UTF-8"), 2)));
                }
                edit.commit();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
