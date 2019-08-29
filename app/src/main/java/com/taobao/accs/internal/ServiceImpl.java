package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message.ReqType;
import com.taobao.accs.data.MsgDistribute;
import com.taobao.accs.eudemon.EudemonManager;
import com.taobao.accs.net.BaseConnection;
import com.taobao.accs.net.SpdyConnection;
import com.taobao.accs.ut.statistics.MonitorStatistic;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.LoadSoFailUtil;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.util.Map.Entry;
import org.android.agoo.common.Config;
import org.android.agoo.service.IMessageService.Stub;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceImpl extends ElectionServiceImpl {
    private static final String TAG = "ServiceImpl";
    private static EudemonManager eudemonManager;
    /* access modifiers changed from: private */
    public Service mBaseService = null;
    /* access modifiers changed from: private */
    public Context mContext;
    private String mLastNetWorkType = "unknown";
    private final Stub messageServiceBinder = new Stub() {
        public boolean ping() throws RemoteException {
            return true;
        }

        public void probe() throws RemoteException {
            ALog.d(ServiceImpl.TAG, "ReceiverImpl probeTaoBao begin......messageServiceBinder [probe]", new Object[0]);
            ThreadPoolExecutorFactory.execute(new Runnable() {
                public void run() {
                    try {
                        if (ServiceImpl.this.mContext == null || !UtilityImpl.getServiceEnabled(ServiceImpl.this.mContext)) {
                            Process.killProcess(Process.myPid());
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(Constants.ACTION_PING);
                            intent.setClassName(ServiceImpl.this.mContext.getPackageName(), AdapterUtilityImpl.channelService);
                            ServiceImpl.this.mContext.startService(intent);
                            UTMini.getInstance().commitEvent(66001, "probeServiceEnabled", UtilityImpl.getDeviceId(ServiceImpl.this.mContext));
                            ALog.d(ServiceImpl.TAG, "ReceiverImpl probeTaoBao........mContext.startService(intent) [probe][successfully]", new Object[0]);
                        }
                        ALog.d(ServiceImpl.TAG, "ReceiverImpl probeTaoBao........messageServiceBinder [probe][end]", new Object[0]);
                    } catch (Throwable th) {
                        ALog.d(ServiceImpl.TAG, "ReceiverImpl probeTaoBao error........e=".concat(String.valueOf(th)), new Object[0]);
                    }
                }
            });
        }
    };
    private Messenger messenger = new Messenger(new Handler() {
        public void handleMessage(Message message) {
            if (message != null) {
                ALog.i(ServiceImpl.TAG, "handleMessage on receive msg", "msg", message.toString());
                Intent intent = (Intent) message.getData().getParcelable("intent");
                if (intent != null) {
                    ALog.i(ServiceImpl.TAG, "handleMessage get intent success", "intent", intent.toString());
                    if (ServiceImpl.this.mBaseService != null) {
                        ServiceImpl.this.mBaseService.onStartCommand(intent, 0, 0);
                        return;
                    }
                    ALog.e(ServiceImpl.TAG, "handleMessage mBaseService null", new Object[0]);
                }
            }
        }
    });
    private long startTime;

    private void initUt() {
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public ServiceImpl(Service service) {
        super(service);
        this.mBaseService = service;
        this.mContext = service.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                ServiceImpl.this.init();
            }
        });
    }

    public int onHostStartCommand(final Intent intent, int i, int i2) {
        final String str;
        int i3 = 1;
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(TAG, "onHostStartCommand", "intent", intent);
        }
        try {
            if (ALog.isPrintLog(Level.D) && intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str2 : extras.keySet()) {
                        ALog.d(TAG, "onHostStartCommand", "key", str2, " value", extras.get(str2));
                    }
                }
            }
            int soFailTimes = LoadSoFailUtil.getSoFailTimes();
            if (soFailTimes > 3) {
                try {
                    ALog.e(TAG, "onHostStartCommand load SO fail 4 times, don't auto restart", new Object[0]);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_SOFAIL, UtilityImpl.int2String(soFailTimes), 0.0d);
                    i3 = 2;
                } catch (Throwable th) {
                    th = th;
                    i3 = 2;
                    try {
                        ALog.e(TAG, "onHostStartCommand", th, new Object[0]);
                        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                        return i3;
                    } catch (Throwable th2) {
                        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                        throw th2;
                    }
                }
            }
            if (intent == null) {
                str = null;
            } else {
                str = intent.getAction();
            }
            if (TextUtils.isEmpty(str)) {
                tryConnect();
                pingOnAllConns(false, false);
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                return i3;
            }
            ThreadPoolExecutorFactory.execute(new Runnable() {
                public void run() {
                    ServiceImpl.this.handleAction(intent, str);
                }
            });
            AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
            return i3;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* access modifiers changed from: private */
    public void init() {
        ALog.d(TAG, "init start", new Object[0]);
        GlobalClientInfo.getInstance(this.mContext);
        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
        this.startTime = System.currentTimeMillis();
        this.mLastNetWorkType = UtilityImpl.getNetworkTypeExt(this.mContext);
        if (Config.g(this.mContext)) {
            EudemonManager.checkAndRenewPidFile(this.mContext);
            EudemonManager instance = EudemonManager.getInstance(this.mContext, 600, false);
            eudemonManager = instance;
            if (instance != null) {
                eudemonManager.start();
            }
        }
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(TAG, "init", Constants.KEY_SDK_VERSION, Integer.valueOf(Constants.SDK_VERSION_CODE), "procStart", Integer.valueOf(AdapterGlobalClientInfo.mStartServiceTimes.intValue()));
        }
        initUt();
        onPingIpp(this.mContext);
        UTMini.getInstance().commitEvent(66001, "START", UtilityImpl.getProxy(), RPCDataItems.PROXY);
        long serviceAliveTime = UtilityImpl.getServiceAliveTime(this.mContext);
        ALog.d(TAG, "getServiceAliveTime", "aliveTime", Long.valueOf(serviceAliveTime));
        if (serviceAliveTime > 20000) {
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_SERVICE_ALIVE, "", (double) (serviceAliveTime / 1000));
        }
        UtilityImpl.setServiceTime(this.mContext, Constants.SP_KEY_SERVICE_START, System.currentTimeMillis());
        UTMini.getInstance().commitEvent(66001, "NOTIFY", UtilityImpl.isNotificationEnabled(this.mContext));
    }

    /* access modifiers changed from: private */
    public void handleAction(Intent intent, String str) {
        ALog.d(TAG, "handleAction", "action", str);
        try {
            if (!TextUtils.isEmpty(str) && Constants.ACTION_PING.equals(str)) {
                String stringExtra = intent.getStringExtra("source");
                ALog.i(TAG, "org.agoo.android.intent.action.PING_V4,start channel by brothers", "serviceStart", Integer.valueOf(AdapterGlobalClientInfo.mStartServiceTimes.intValue()), "source".concat(String.valueOf(stringExtra)));
                AppMonitorAdapter.commitCount("accs", "startChannel", stringExtra, 0.0d);
                if (AdapterGlobalClientInfo.isFirstStartProc()) {
                    AppMonitorAdapter.commitCount("accs", "createChannel", stringExtra, 0.0d);
                }
            }
            tryConnect();
            if (!TextUtils.equals(str, "android.intent.action.PACKAGE_REMOVED")) {
                if (TextUtils.equals(str, "android.net.conn.CONNECTIVITY_CHANGE")) {
                    String networkTypeExt = UtilityImpl.getNetworkTypeExt(this.mContext);
                    boolean isNetworkConnected = UtilityImpl.isNetworkConnected(this.mContext);
                    StringBuilder sb = new StringBuilder("network change:");
                    sb.append(this.mLastNetWorkType);
                    sb.append(" to ");
                    sb.append(networkTypeExt);
                    String sb2 = sb.toString();
                    ALog.i(TAG, sb2, new Object[0]);
                    if (isNetworkConnected) {
                        this.mLastNetWorkType = networkTypeExt;
                        notifyNetChangeOnAllConns(sb2);
                        pingOnAllConns(true, false);
                        UTMini.getInstance().commitEvent(66001, "CONNECTIVITY_CHANGE", networkTypeExt, UtilityImpl.getProxy(), "0");
                    }
                    if (networkTypeExt.equals("unknown")) {
                        notifyNetChangeOnAllConns(sb2);
                        this.mLastNetWorkType = networkTypeExt;
                    }
                } else if (TextUtils.equals(str, "android.intent.action.BOOT_COMPLETED")) {
                    pingOnAllConns(true, false);
                } else if (TextUtils.equals(str, "android.intent.action.USER_PRESENT")) {
                    ALog.d(TAG, "action android.intent.action.USER_PRESENT", new Object[0]);
                    pingOnAllConns(true, false);
                } else if (str.equals(Constants.ACTION_COMMAND)) {
                    handleCommand(intent);
                } else if (str.equals(Constants.ACTION_START_FROM_AGOO)) {
                    ALog.i(TAG, "ACTION_START_FROM_AGOO", new Object[0]);
                }
            }
        } catch (Throwable th) {
            ALog.e(TAG, "handleAction", th, new Object[0]);
        }
    }

    private void handleCommand(Intent intent) {
        BaseConnection baseConnection;
        com.taobao.accs.data.Message message;
        ReqType reqType;
        URL url;
        Intent intent2 = intent;
        int intExtra = intent2.getIntExtra("command", -1);
        ALog.i(TAG, "handleCommand", "command", Integer.valueOf(intExtra));
        String stringExtra = intent2.getStringExtra("packageName");
        String stringExtra2 = intent2.getStringExtra("serviceId");
        String stringExtra3 = intent2.getStringExtra("userInfo");
        String stringExtra4 = intent2.getStringExtra("appKey");
        String stringExtra5 = intent2.getStringExtra(Constants.KEY_CONFIG_TAG);
        String stringExtra6 = intent2.getStringExtra("ttid");
        String stringExtra7 = intent2.getStringExtra(Constants.KEY_SID);
        String stringExtra8 = intent2.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
        if (intExtra == 201) {
            sendOnAllConnections(com.taobao.accs.data.Message.BuildPing(true, 0), true);
            updateMonitorInfoOnAllConns();
        }
        if (intExtra > 0 && !TextUtils.isEmpty(stringExtra)) {
            BaseConnection connection = getConnection(this.mContext, stringExtra5, true, intExtra);
            if (connection != null) {
                connection.start();
                com.taobao.accs.data.Message message2 = null;
                if (intExtra != 1) {
                    BaseConnection baseConnection2 = connection;
                    if (intExtra == 2) {
                        ALog.e(TAG, "onHostStartCommand COMMAND_UNBIND_APP", new Object[0]);
                        if (baseConnection2.getClientManager().isAppUnbinded(stringExtra)) {
                            com.taobao.accs.data.Message buildUnbindApp = com.taobao.accs.data.Message.buildUnbindApp(baseConnection2, this.mContext, stringExtra, stringExtra7, stringExtra3, stringExtra8);
                            StringBuilder sb = new StringBuilder();
                            sb.append(stringExtra);
                            sb.append(" isAppUnbinded");
                            ALog.i(TAG, sb.toString(), new Object[0]);
                            baseConnection2.onResult(buildUnbindApp, 200);
                            return;
                        }
                        baseConnection = baseConnection2;
                    } else {
                        baseConnection = baseConnection2;
                        if (intExtra == 5) {
                            message2 = com.taobao.accs.data.Message.buildBindService(this.mContext, stringExtra, stringExtra4, stringExtra2, stringExtra7, stringExtra3, stringExtra8);
                        } else if (intExtra == 6) {
                            message2 = com.taobao.accs.data.Message.buildUnbindService(this.mContext, stringExtra, stringExtra4, stringExtra2, stringExtra7, stringExtra3, stringExtra8);
                        } else if (intExtra == 3) {
                            message = com.taobao.accs.data.Message.buildBindUser(this.mContext, stringExtra, stringExtra4, stringExtra7, stringExtra3, stringExtra8);
                            if (baseConnection.getClientManager().isUserBinded(stringExtra, stringExtra3) && !intent2.getBooleanExtra(Constants.KEY_FOUCE_BIND, false)) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(stringExtra);
                                sb2.append("/");
                                sb2.append(stringExtra3);
                                sb2.append(" isUserBinded");
                                ALog.i(TAG, sb2.toString(), new Object[0]);
                                if (message != null) {
                                    baseConnection.onResult(message, 200);
                                }
                                return;
                            }
                        } else if (intExtra == 4) {
                            message2 = com.taobao.accs.data.Message.buildUnbindUser(this.mContext, stringExtra, stringExtra4, stringExtra7, stringExtra3, stringExtra8);
                        } else if (intExtra == 100) {
                            byte[] byteArrayExtra = intent2.getByteArrayExtra("data");
                            String stringExtra9 = intent2.getStringExtra(Constants.KEY_DATA_ID);
                            String stringExtra10 = intent2.getStringExtra("target");
                            String stringExtra11 = intent2.getStringExtra("businessId");
                            String stringExtra12 = intent2.getStringExtra(Constants.KEY_EXT_TAG);
                            try {
                                reqType = (ReqType) intent2.getSerializableExtra(Constants.KEY_SEND_TYPE);
                            } catch (Exception unused) {
                                reqType = null;
                            }
                            if (byteArrayExtra != null) {
                                try {
                                    StringBuilder sb3 = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
                                    sb3.append(((SpdyConnection) baseConnection).getChannelHost());
                                    url = new URL(sb3.toString());
                                } catch (Exception unused2) {
                                    url = null;
                                }
                                String str = stringExtra3;
                                AccsRequest accsRequest = r11;
                                AccsRequest accsRequest2 = new AccsRequest(str, stringExtra2, byteArrayExtra, stringExtra9, stringExtra10, url, stringExtra11);
                                accsRequest.setTag(stringExtra12);
                                if (reqType == null) {
                                    message2 = com.taobao.accs.data.Message.buildSendData(baseConnection, this.mContext, stringExtra, stringExtra4, accsRequest, false);
                                } else if (reqType == ReqType.REQ) {
                                    message2 = com.taobao.accs.data.Message.buildRequest(baseConnection, this.mContext, stringExtra, stringExtra4, accsRequest, false);
                                }
                            }
                        } else if (intExtra == 106) {
                            intent2.setAction(Constants.ACTION_RECEIVE);
                            intent2.putExtra("command", -1);
                            MsgDistribute.distribMessage(this.mContext, intent2);
                            return;
                        }
                    }
                    message = message2;
                } else if (!stringExtra.equals(this.mContext.getPackageName())) {
                    ALog.e(TAG, "handleCommand bindapp pkg error", new Object[0]);
                    return;
                } else {
                    String stringExtra13 = intent2.getStringExtra("app_sercet");
                    baseConnection = connection;
                    String str2 = stringExtra6;
                    com.taobao.accs.data.Message buildBindApp = com.taobao.accs.data.Message.buildBindApp(this.mContext, stringExtra5, stringExtra4, stringExtra13, stringExtra, str2, intent2.getStringExtra("appVersion"), stringExtra7, stringExtra3, stringExtra8);
                    baseConnection.mTtid = str2;
                    UtilityImpl.saveAppKey(this.mContext, stringExtra4, stringExtra13);
                    if (!baseConnection.getClientManager().isAppBinded(stringExtra) || intent2.getBooleanExtra(Constants.KEY_FOUCE_BIND, false)) {
                        message = buildBindApp;
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(stringExtra);
                        sb4.append(" isAppBinded");
                        ALog.i(TAG, sb4.toString(), new Object[0]);
                        baseConnection.onResult(buildBindApp, 200);
                        return;
                    }
                }
                if (message != null) {
                    ALog.d(TAG, "try send message", new Object[0]);
                    if (message.getNetPermanceMonitor() != null) {
                        message.getNetPermanceMonitor().onSend();
                    }
                    baseConnection.send(message, true);
                    return;
                }
                ALog.e(TAG, "message is null", new Object[0]);
                baseConnection.onResult(com.taobao.accs.data.Message.buildParameterError(stringExtra, intExtra), -2);
            } else {
                ALog.e(TAG, "no connection", Constants.KEY_CONFIG_TAG, stringExtra5, "command", Integer.valueOf(intExtra));
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return this.messenger.getBinder();
    }

    public void onDestroy() {
        super.onDestroy();
        ALog.e(TAG, "Service onDestroy", new Object[0]);
        UtilityImpl.setServiceTime(this.mContext, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
        this.mBaseService = null;
        this.mContext = null;
        shutdownAllConns();
        Process.killProcess(Process.myPid());
    }

    private final void onPingIpp(Context context) {
        Context context2 = context;
        try {
            String pullupInfo = OrangeAdapter.getPullupInfo();
            if (!TextUtils.isEmpty(pullupInfo)) {
                ALog.i(TAG, "start pull up", new Object[0]);
                JSONArray jSONArray = new JSONArray(pullupInfo);
                int i = 0;
                while (i < jSONArray.length()) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i).getJSONObject("app".concat(String.valueOf(i)));
                        String string = jSONObject.getString("action");
                        String string2 = jSONObject.getString("pack");
                        String string3 = jSONObject.getString("service");
                        boolean z = jSONObject.getBoolean("enabled") && ((double) jSONObject.getInt("probability")) >= Math.random() * 100.0d;
                        ALog.i(TAG, "pull up", "action", string, "pack", string2, "service", string3, "need pull", Boolean.valueOf(z));
                        if (!z || !UtilityImpl.packageExist(context2, string2)) {
                            i++;
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(string);
                            intent.setClassName(string2, string3);
                            intent.putExtra("packageName", context.getPackageName());
                            intent.setPackage(string2);
                            context2.startService(intent);
                            try {
                                UTMini.getInstance().commitEvent(66001, "pingApp", AdapterUtilityImpl.getDeviceId(this.mContext), string2);
                            } catch (Throwable th) {
                                th = th;
                            }
                            i++;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            ALog.e(TAG, "onPingIpp parse", th, new Object[0]);
                            i++;
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                }
            }
        } catch (Throwable th4) {
            th = th4;
            ALog.e(TAG, "onPingIpp", th, new Object[0]);
        }
    }

    private void shouldStopSelf(boolean z) {
        ALog.e(TAG, "shouldStopSelf, kill:".concat(String.valueOf(z)), new Object[0]);
        if (this.mBaseService != null) {
            this.mBaseService.stopSelf();
        }
        if (z) {
            Process.killProcess(Process.myPid());
        }
    }

    private synchronized void tryConnect() {
        if (mConnections != null) {
            if (mConnections.size() != 0) {
                for (Entry entry : mConnections.entrySet()) {
                    BaseConnection baseConnection = (BaseConnection) entry.getValue();
                    if (baseConnection == null) {
                        ALog.e(TAG, "tryConnect connection null", "appkey", baseConnection.getAppkey());
                        return;
                    }
                    ALog.i(TAG, "tryConnect", "appkey", baseConnection.getAppkey(), Constants.KEY_CONFIG_TAG, entry.getKey());
                    if (!baseConnection.isSecurityOff() || !TextUtils.isEmpty(baseConnection.mConfig.getAppSecret())) {
                        baseConnection.start();
                    } else {
                        ALog.e(TAG, "tryConnect secret is null", new Object[0]);
                    }
                }
                return;
            }
        }
        ALog.w(TAG, "tryConnect no connections", new Object[0]);
    }

    private void sendOnAllConnections(com.taobao.accs.data.Message message, boolean z) {
        if (mConnections != null && mConnections.size() != 0) {
            for (Entry value : mConnections.entrySet()) {
                ((BaseConnection) value.getValue()).send(message, z);
            }
        }
    }

    private void pingOnAllConns(boolean z, boolean z2) {
        if (mConnections != null && mConnections.size() != 0) {
            for (Entry value : mConnections.entrySet()) {
                BaseConnection baseConnection = (BaseConnection) value.getValue();
                baseConnection.ping(z, z2);
                ALog.i(TAG, "ping connection", "appkey", baseConnection.getAppkey());
            }
        }
    }

    private void notifyNetChangeOnAllConns(String str) {
        if (mConnections != null && mConnections.size() != 0) {
            for (Entry value : mConnections.entrySet()) {
                ((BaseConnection) value.getValue()).notifyNetWorkChange(str);
            }
        }
    }

    private void updateMonitorInfoOnAllConns() {
        if (mConnections != null && mConnections.size() != 0) {
            for (Entry value : mConnections.entrySet()) {
                MonitorStatistic updateMonitorInfo = ((BaseConnection) value.getValue()).updateMonitorInfo();
                if (updateMonitorInfo != null) {
                    updateMonitorInfo.startServiceTime = this.startTime;
                    updateMonitorInfo.commitUT();
                }
            }
        }
    }

    private void shutdownAllConns() {
        if (mConnections != null && mConnections.size() != 0) {
            for (Entry value : mConnections.entrySet()) {
                ((BaseConnection) value.getValue()).shutdown();
            }
        }
    }

    private String getVersion(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "null";
            }
            String str2 = this.mContext.getPackageManager().getPackageInfo(str, 0).versionName;
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(TAG, "getVersion###版本号为 : ".concat(String.valueOf(str2)), new Object[0]);
            }
            return str2;
        } catch (Throwable unused) {
            return "null";
        }
    }

    public void startConnect() {
        ALog.i(TAG, "startConnect", new Object[0]);
        try {
            tryConnect();
            pingOnAllConns(false, false);
        } catch (Throwable th) {
            ALog.e(TAG, "tryConnect is error,e=".concat(String.valueOf(th)), new Object[0]);
        }
    }

    public void onVotedHost() {
        startConnect();
    }
}
