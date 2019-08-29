package com.taobao.accs;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsClientConfig.ENV;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.Utils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ACCSClient {
    private static String TAG = "ACCSClient";
    public static Map<String, ACCSClient> mACCSClients = new ConcurrentHashMap(2);
    private static Context mContext;
    private String OTAG = TAG;
    protected IACCSManager mAccsManager;
    private AccsClientConfig mConfig;

    @Deprecated
    public String getUserUnit() {
        return null;
    }

    public ACCSClient(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        StringBuilder sb = new StringBuilder();
        sb.append(this.OTAG);
        sb.append(accsClientConfig.getTag());
        this.OTAG = sb.toString();
        this.mAccsManager = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
    }

    public static synchronized String init(Context context, String str) throws AccsException {
        String init;
        synchronized (ACCSClient.class) {
            if (context != null) {
                if (!TextUtils.isEmpty(str)) {
                    AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
                    if (!AccsClientConfig.loadedStaticConfig) {
                        configByTag = new Builder().setAppKey(str).build();
                        ALog.i(TAG, "init", "create config, appkey as tag");
                    }
                    init = init(context, configByTag);
                }
            }
            throw new AccsException((String) "params error");
        }
        return init;
    }

    public static synchronized String init(Context context, AccsClientConfig accsClientConfig) throws AccsException {
        String tag;
        synchronized (ACCSClient.class) {
            if (context == null || accsClientConfig == null) {
                throw new AccsException((String) "init AccsClient params error");
            }
            if ((context.getApplicationInfo().flags & 2) != 0) {
                ALog.isUseTlog = false;
                cl.a();
            }
            mContext = context.getApplicationContext();
            ALog.d(TAG, "init", "config", accsClientConfig);
            tag = accsClientConfig.getTag();
        }
        return tag;
    }

    public static ACCSClient getAccsClient() throws AccsException {
        return getAccsClient(null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009e, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.taobao.accs.ACCSClient getAccsClient(java.lang.String r9) throws com.taobao.accs.AccsException {
        /*
            java.lang.Class<com.taobao.accs.ACCSClient> r0 = com.taobao.accs.ACCSClient.class
            monitor-enter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x009f }
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x001a
            java.lang.String r9 = "default"
            java.lang.String r1 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r4 = "getAccsClient"
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x009f }
            java.lang.String r6 = "configTag is null, use default!"
            r5[r3] = r6     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.w(r1, r4, r5)     // Catch:{ all -> 0x009f }
        L_0x001a:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r4 = "getAccsClient"
            r5 = 2
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ all -> 0x009f }
            java.lang.String r7 = "configTag"
            r6[r3] = r7     // Catch:{ all -> 0x009f }
            r6[r2] = r9     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.i(r1, r4, r6)     // Catch:{ all -> 0x009f }
            com.taobao.accs.AccsClientConfig r1 = com.taobao.accs.AccsClientConfig.getConfigByTag(r9)     // Catch:{ all -> 0x009f }
            if (r1 != 0) goto L_0x0045
            java.lang.String r9 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "getAccsClient"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x009f }
            java.lang.String r4 = "configTag not exist, please init first!!"
            r2[r3] = r4     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.e(r9, r1, r2)     // Catch:{ all -> 0x009f }
            com.taobao.accs.AccsException r9 = new com.taobao.accs.AccsException     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "configTag not exist"
            r9.<init>(r1)     // Catch:{ all -> 0x009f }
            throw r9     // Catch:{ all -> 0x009f }
        L_0x0045:
            java.util.Map<java.lang.String, com.taobao.accs.ACCSClient> r4 = mACCSClients     // Catch:{ all -> 0x009f }
            java.lang.Object r4 = r4.get(r9)     // Catch:{ all -> 0x009f }
            com.taobao.accs.ACCSClient r4 = (com.taobao.accs.ACCSClient) r4     // Catch:{ all -> 0x009f }
            if (r4 != 0) goto L_0x0067
            java.lang.String r2 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r4 = "getAccsClient create client"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.d(r2, r4, r3)     // Catch:{ all -> 0x009f }
            com.taobao.accs.ACCSClient r2 = new com.taobao.accs.ACCSClient     // Catch:{ all -> 0x009f }
            r2.<init>(r1)     // Catch:{ all -> 0x009f }
            java.util.Map<java.lang.String, com.taobao.accs.ACCSClient> r3 = mACCSClients     // Catch:{ all -> 0x009f }
            r3.put(r9, r2)     // Catch:{ all -> 0x009f }
            r2.updateConfig(r1)     // Catch:{ all -> 0x009f }
            monitor-exit(r0)
            return r2
        L_0x0067:
            com.taobao.accs.AccsClientConfig r9 = r4.mConfig     // Catch:{ all -> 0x009f }
            boolean r9 = r1.equals(r9)     // Catch:{ all -> 0x009f }
            if (r9 == 0) goto L_0x0079
            java.lang.String r9 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "getAccsClient exists"
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.i(r9, r1, r2)     // Catch:{ all -> 0x009f }
            goto L_0x009d
        L_0x0079:
            java.lang.String r9 = TAG     // Catch:{ all -> 0x009f }
            java.lang.String r6 = "getAccsClient update config"
            r7 = 4
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x009f }
            java.lang.String r8 = "old config"
            r7[r3] = r8     // Catch:{ all -> 0x009f }
            com.taobao.accs.AccsClientConfig r3 = r4.mConfig     // Catch:{ all -> 0x009f }
            java.lang.String r3 = r3.getTag()     // Catch:{ all -> 0x009f }
            r7[r2] = r3     // Catch:{ all -> 0x009f }
            java.lang.String r2 = "new config"
            r7[r5] = r2     // Catch:{ all -> 0x009f }
            r2 = 3
            java.lang.String r3 = r1.getTag()     // Catch:{ all -> 0x009f }
            r7[r2] = r3     // Catch:{ all -> 0x009f }
            com.taobao.accs.utl.ALog.i(r9, r6, r7)     // Catch:{ all -> 0x009f }
            r4.updateConfig(r1)     // Catch:{ all -> 0x009f }
        L_0x009d:
            monitor-exit(r0)
            return r4
        L_0x009f:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.ACCSClient.getAccsClient(java.lang.String):com.taobao.accs.ACCSClient");
    }

    private void updateConfig(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        this.mAccsManager = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
        if (this.mAccsManager != null) {
            this.mAccsManager.updateConfig(accsClientConfig);
        }
    }

    public static synchronized void setEnvironment(Context context, @ENV int i) {
        synchronized (ACCSClient.class) {
            if (context != null) {
                try {
                    if ((context.getApplicationInfo().flags & 2) != 0) {
                        ALog.isUseTlog = false;
                        cl.a();
                    }
                } catch (Throwable th) {
                    ALog.e(TAG, "setEnvironment", th, new Object[0]);
                    Utils.setMode(context, i);
                    return;
                }
            }
            if (i < 0 || i > 2) {
                ALog.e(TAG, "env error", "env", Integer.valueOf(i));
                i = 0;
            }
            int i2 = AccsClientConfig.mEnv;
            AccsClientConfig.mEnv = i;
            if (i2 != i && Utils.isMainProcess(context)) {
                ALog.i(TAG, "setEnvironment", "preEnv", Integer.valueOf(i2), "toEnv", Integer.valueOf(i));
                Utils.clearAllSharePreferences(context);
                Utils.clearAgooBindCache(context);
                Utils.killService(context);
                if (i == 2) {
                    r.a(anet.channel.entity.ENV.TEST);
                } else if (i == 1) {
                    r.a(anet.channel.entity.ENV.PREPARE);
                }
                for (Entry key : mACCSClients.entrySet()) {
                    try {
                        getAccsClient((String) key.getKey());
                    } catch (AccsException e) {
                        ALog.e(TAG, "setEnvironment update client", e, new Object[0]);
                    }
                }
            }
            Utils.setMode(context, i);
        }
    }

    public void bindApp(String str, IAppReceiver iAppReceiver) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "bindApp mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.bindApp(mContext, this.mConfig.getAppKey(), this.mConfig.getAppSecret(), str, iAppReceiver);
        }
    }

    public void bindUser(String str) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "bindUser mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.bindUser(mContext, str);
        }
    }

    public void bindUser(String str, boolean z) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "bindUser mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.bindUser(mContext, str, z);
        }
    }

    public void unbindUser() {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "unbindUser mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.unbindUser(mContext);
        }
    }

    public void bindService(String str) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "bindService mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.bindService(mContext, str);
        }
    }

    public void unbindService(String str) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "unbindService mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.unbindService(mContext, str);
        }
    }

    public String sendData(AccsRequest accsRequest) {
        if (this.mAccsManager != null) {
            return this.mAccsManager.sendData(mContext, accsRequest);
        }
        ALog.e(this.OTAG, "sendData mAccsManager null", new Object[0]);
        return null;
    }

    public String sendRequest(AccsRequest accsRequest) {
        if (this.mAccsManager != null) {
            return this.mAccsManager.sendRequest(mContext, accsRequest);
        }
        ALog.e(this.OTAG, "sendRequest mAccsManager null", new Object[0]);
        return null;
    }

    public String sendPushResponse(AccsRequest accsRequest, ExtraInfo extraInfo) {
        if (this.mAccsManager != null) {
            return this.mAccsManager.sendPushResponse(mContext, accsRequest, extraInfo);
        }
        ALog.e(this.OTAG, "sendPushResponse mAccsManager null", new Object[0]);
        return null;
    }

    public boolean isNetworkReachable() {
        if (this.mAccsManager != null) {
            return this.mAccsManager.isNetworkReachable(mContext);
        }
        ALog.e(this.OTAG, "isNetworkReachable mAccsManager null", new Object[0]);
        return false;
    }

    public void forceDisableService() {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "forceDisableService mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.forceDisableService(mContext);
        }
    }

    public void forceEnableService() {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "forceEnableService mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.forceEnableService(mContext);
        }
    }

    public void startInAppConnection(String str, IAppReceiver iAppReceiver) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "startInAppConnection mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.startInAppConnection(mContext, this.mConfig.getAppKey(), this.mConfig.getAppSecret(), str, iAppReceiver);
        }
    }

    public void setLoginInfo(ILoginInfo iLoginInfo) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "setLoginInfo mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.setLoginInfo(mContext, iLoginInfo);
        }
    }

    public void clearLoginInfo() {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "clearLoginInfo mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.clearLoginInfo(mContext);
        }
    }

    public boolean cancel(String str) {
        if (this.mAccsManager != null) {
            return this.mAccsManager.cancel(mContext, str);
        }
        ALog.e(this.OTAG, "cancel mAccsManager null", new Object[0]);
        return false;
    }

    public boolean isChannelError(int i) {
        if (this.mAccsManager != null) {
            return this.mAccsManager.isChannelError(i);
        }
        ALog.e(this.OTAG, "isChannelError mAccsManager null", new Object[0]);
        return true;
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        if (this.mAccsManager != null) {
            return this.mAccsManager.getChannelState();
        }
        ALog.e(this.OTAG, "getChannelState mAccsManager null", new Object[0]);
        return null;
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        if (this.mAccsManager != null) {
            return this.mAccsManager.forceReConnectChannel();
        }
        ALog.e(this.OTAG, "forceReConnectChannel mAccsManager null", new Object[0]);
        return null;
    }

    public void registerSerivce(String str, String str2) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "registerSerivce mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.registerSerivce(mContext, str, str2);
        }
    }

    public void unRegisterSerivce(String str) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "unRegisterSerivce mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.unRegisterSerivce(mContext, str);
        }
    }

    public void registerDataListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "registerDataListener mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.registerDataListener(mContext, str, accsAbstractDataListener);
        }
    }

    public void unRegisterDataListener(String str) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "unRegisterDataListener mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.unRegisterDataListener(mContext, str);
        }
    }

    public void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map) {
        if (this.mAccsManager == null) {
            ALog.e(this.OTAG, "sendBusinessAck mAccsManager null", new Object[0]);
        } else {
            this.mAccsManager.sendBusinessAck(str, str2, str3, s, str4, map);
        }
    }
}
