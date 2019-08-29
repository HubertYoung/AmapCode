package com.taobao.accs;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.taobao.accs.AccsClientConfig.ENV;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.update.ACCSClassLoader;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.Utils;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public final class ACCSManager {
    private static final String TAG = "ACCSManager";
    public static Map<String, IACCSManager> mAccsInstances = new ConcurrentHashMap(2);
    public static Context mContext = null;
    public static String mDefaultAppkey = null;
    public static String mDefaultConfigTag = "default";
    public static int mEnv;

    public static class AccsRequest implements Serializable {
        public String businessId;
        public byte[] data;
        public String dataId;
        public URL host;
        public boolean isUnitBusiness = false;
        public String serviceId;
        public String tag;
        public String target;
        public String targetServiceName;
        public int timeout;
        public String userId;

        public AccsRequest(String str, String str2, byte[] bArr, String str3, String str4, URL url, String str5) {
            this.userId = str;
            this.serviceId = str2;
            this.data = bArr;
            this.dataId = str3;
            this.target = str4;
            this.host = url;
            this.businessId = str5;
        }

        public AccsRequest(String str, String str2, byte[] bArr, String str3) {
            this.userId = str;
            this.serviceId = str2;
            this.data = bArr;
            this.dataId = str3;
        }

        public void setTag(String str) {
            this.tag = str;
        }

        public void setTimeOut(int i) {
            this.timeout = i;
        }

        public void setIsUnitBusiness(boolean z) {
            this.isUnitBusiness = z;
        }

        public void setTarget(String str) {
            this.target = str;
        }

        public void setTargetServiceName(String str) {
            this.targetServiceName = str;
        }

        public void setHost(URL url) {
            this.host = url;
        }

        public void setBusinessId(String str) {
            this.businessId = str;
        }
    }

    @Deprecated
    public static String getUserUnit(Context context) {
        return null;
    }

    @Deprecated
    public static void setServiceListener(Context context, String str, IServiceReceiver iServiceReceiver) {
    }

    @Deprecated
    public static void unbindApp(Context context) {
    }

    private ACCSManager() {
    }

    @Deprecated
    public static void setAppkey(Context context, String str, @ENV int i) {
        Utils.setAgooAppkey(context, str);
        mContext = context.getApplicationContext();
        mDefaultAppkey = str;
        Utils.setSpValue(mContext, Constants.SP_KEY_DEFAULT_APPKEY, mDefaultAppkey);
        mEnv = i;
        AccsClientConfig.mEnv = i;
    }

    @Deprecated
    public static String getDefaultAppkey(Context context) {
        if (TextUtils.isEmpty(mDefaultAppkey)) {
            ALog.e(TAG, "old interface!!, please AccsManager.setAppkey() first!", new Object[0]);
            String spValue = Utils.getSpValue(context, Constants.SP_KEY_DEFAULT_APPKEY, null);
            mDefaultAppkey = spValue;
            if (TextUtils.isEmpty(spValue)) {
                try {
                    mDefaultAppkey = SecurityGuardManager.getInstance(context).getStaticDataStoreComp().getAppKeyByIndex(0, null);
                } catch (Throwable th) {
                    ALog.e(TAG, "getDefaultAppkey", th, new Object[0]);
                }
            }
            if (TextUtils.isEmpty(mDefaultAppkey)) {
                mDefaultAppkey = "0";
            }
        }
        return mDefaultAppkey;
    }

    @Deprecated
    public static void setDefaultConfig(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            ALog.i(TAG, "setDefaultConfig", Constants.KEY_CONFIG_TAG, str);
            mDefaultConfigTag = str;
        }
    }

    public static String getDefaultConfig(Context context) {
        return mDefaultConfigTag;
    }

    @Deprecated
    public static void bindApp(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        if (TextUtils.isEmpty(mDefaultAppkey)) {
            throw new RuntimeException("old interface!!, please AccsManager.setAppkey() first!");
        }
        Utils.initConfig();
        getManagerImpl(context).bindApp(context, mDefaultAppkey, str2, str3, iAppReceiver);
    }

    @Deprecated
    public static void bindApp(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        bindApp(context, mDefaultAppkey, "", str2, iAppReceiver);
    }

    @Deprecated
    public static void bindUser(Context context, String str) {
        bindUser(context, str, false);
    }

    @Deprecated
    public static void bindUser(Context context, String str, boolean z) {
        if (TextUtils.isEmpty(mDefaultAppkey)) {
            throw new RuntimeException("old interface!!, please AccsManager.setAppkey() first!");
        }
        getManagerImpl(context).bindUser(context, str, z);
    }

    @Deprecated
    public static void unbindUser(Context context) {
        getManagerImpl(context).unbindUser(context);
    }

    @Deprecated
    public static void bindService(Context context, String str) {
        getManagerImpl(context).bindService(context, str);
    }

    @Deprecated
    public static void unbindService(Context context, String str) {
        getManagerImpl(context).unbindService(context, str);
    }

    @Deprecated
    public static String sendData(Context context, String str, String str2, byte[] bArr, String str3) {
        return getManagerImpl(context).sendData(context, str, str2, bArr, str3);
    }

    @Deprecated
    public static String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        return getManagerImpl(context).sendData(context, str, str2, bArr, str3, str4, url);
    }

    @Deprecated
    public static String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return getManagerImpl(context).sendData(context, str, str2, bArr, str3, str4);
    }

    @Deprecated
    public static String sendData(Context context, AccsRequest accsRequest) {
        return getManagerImpl(context).sendData(context, accsRequest);
    }

    @Deprecated
    public static String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        return getManagerImpl(context).sendRequest(context, str, str2, bArr, str3, str4, url);
    }

    @Deprecated
    public static String sendRequest(Context context, String str, String str2, byte[] bArr, String str3) {
        return sendRequest(context, str, str2, bArr, str3, null);
    }

    @Deprecated
    public static String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return getManagerImpl(context).sendRequest(context, str, str2, bArr, str3, str4);
    }

    @Deprecated
    public static String sendRequest(Context context, AccsRequest accsRequest) {
        return getManagerImpl(context).sendRequest(context, accsRequest);
    }

    @Deprecated
    public static String sendPushResponse(Context context, AccsRequest accsRequest, ExtraInfo extraInfo) {
        return getManagerImpl(context).sendPushResponse(context, accsRequest, extraInfo);
    }

    @Deprecated
    public static boolean isNetworkReachable(Context context) {
        return getManagerImpl(context).isNetworkReachable(context);
    }

    @Deprecated
    public static void setMode(Context context, int i) {
        mEnv = i;
        getManagerImpl(context).setMode(context, i);
    }

    @Deprecated
    public static void setProxy(Context context, String str, int i) {
        getManagerImpl(context).setProxy(context, str, i);
    }

    @Deprecated
    public static void startInAppConnection(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        Utils.initConfig();
        getManagerImpl(context).startInAppConnection(context, mDefaultAppkey, str2, str3, iAppReceiver);
    }

    @Deprecated
    public static void startInAppConnection(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        startInAppConnection(context, mDefaultAppkey, "", str2, iAppReceiver);
    }

    @Deprecated
    public static void setLoginInfoImpl(Context context, ILoginInfo iLoginInfo) {
        getManagerImpl(context).setLoginInfo(context, iLoginInfo);
    }

    @Deprecated
    public static void clearLoginInfoImpl(Context context) {
        getManagerImpl(context).clearLoginInfo(context);
    }

    @Deprecated
    public static Map<String, Boolean> getChannelState(Context context) throws Exception {
        return getManagerImpl(context).getChannelState();
    }

    @Deprecated
    public static Map<String, Boolean> forceReConnectChannel(Context context) throws Exception {
        return getManagerImpl(context).forceReConnectChannel();
    }

    @Deprecated
    public static boolean isChannelError(Context context, int i) {
        return getManagerImpl(context).isChannelError(i);
    }

    @Deprecated
    public static void registerSerivce(Context context, String str, String str2) {
        getManagerImpl(context).registerSerivce(context, str, str2);
    }

    @Deprecated
    public static void unregisterService(Context context, String str) {
        getManagerImpl(context).unRegisterSerivce(context, str);
    }

    @Deprecated
    public static void registerDataListener(Context context, String str, AccsAbstractDataListener accsAbstractDataListener) {
        if (getManagerImpl(context) == null) {
            ALog.e(TAG, "getManagerImpl null, return", new Object[0]);
        } else {
            getManagerImpl(context).registerDataListener(context, str, accsAbstractDataListener);
        }
    }

    @Deprecated
    public static void unRegisterDataListener(Context context, String str) {
        getManagerImpl(context).unRegisterDataListener(context, str);
    }

    private static synchronized IACCSManager getManagerImpl(Context context) {
        IACCSManager accsInstance;
        synchronized (ACCSManager.class) {
            accsInstance = getAccsInstance(context, null, getDefaultConfig(context));
        }
        return accsInstance;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.taobao.accs.IACCSManager getAccsInstance(android.content.Context r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.Class<com.taobao.accs.ACCSManager> r8 = com.taobao.accs.ACCSManager.class
            monitor-enter(r8)
            r0 = 1
            r1 = 0
            r2 = 2
            if (r7 == 0) goto L_0x0053
            boolean r3 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0051 }
            if (r3 == 0) goto L_0x000f
            goto L_0x0053
        L_0x000f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0051 }
            r3.<init>(r9)     // Catch:{ all -> 0x0051 }
            java.lang.String r4 = "|"
            r3.append(r4)     // Catch:{ all -> 0x0051 }
            int r4 = com.taobao.accs.AccsClientConfig.mEnv     // Catch:{ all -> 0x0051 }
            r3.append(r4)     // Catch:{ all -> 0x0051 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0051 }
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ all -> 0x0051 }
            boolean r4 = com.taobao.accs.utl.ALog.isPrintLog(r4)     // Catch:{ all -> 0x0051 }
            if (r4 == 0) goto L_0x003a
            java.lang.String r4 = "ACCSManager"
            java.lang.String r5 = "getAccsInstance"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0051 }
            java.lang.String r6 = "key"
            r2[r1] = r6     // Catch:{ all -> 0x0051 }
            r2[r0] = r3     // Catch:{ all -> 0x0051 }
            com.taobao.accs.utl.ALog.d(r4, r5, r2)     // Catch:{ all -> 0x0051 }
        L_0x003a:
            java.util.Map<java.lang.String, com.taobao.accs.IACCSManager> r0 = mAccsInstances     // Catch:{ all -> 0x0051 }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x0051 }
            com.taobao.accs.IACCSManager r0 = (com.taobao.accs.IACCSManager) r0     // Catch:{ all -> 0x0051 }
            if (r0 != 0) goto L_0x004f
            com.taobao.accs.IACCSManager r0 = createAccsInstance(r7, r9)     // Catch:{ all -> 0x0051 }
            if (r0 == 0) goto L_0x004f
            java.util.Map<java.lang.String, com.taobao.accs.IACCSManager> r7 = mAccsInstances     // Catch:{ all -> 0x0051 }
            r7.put(r3, r0)     // Catch:{ all -> 0x0051 }
        L_0x004f:
            monitor-exit(r8)
            return r0
        L_0x0051:
            r7 = move-exception
            goto L_0x0065
        L_0x0053:
            java.lang.String r7 = "ACCSManager"
            java.lang.String r3 = "getAccsInstance param null"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0051 }
            java.lang.String r4 = "configTag"
            r2[r1] = r4     // Catch:{ all -> 0x0051 }
            r2[r0] = r9     // Catch:{ all -> 0x0051 }
            com.taobao.accs.utl.ALog.e(r7, r3, r2)     // Catch:{ all -> 0x0051 }
            r7 = 0
            monitor-exit(r8)
            return r7
        L_0x0065:
            monitor-exit(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.ACCSManager.getAccsInstance(android.content.Context, java.lang.String, java.lang.String):com.taobao.accs.IACCSManager");
    }

    protected static IACCSManager createAccsInstance(Context context, String str) {
        try {
            ALog.i(TAG, "createAccsInstance", Constants.KEY_CONFIG_TAG, str);
            IACCSManager iACCSManager = (IACCSManager) ACCSClassLoader.getInstance().getClassLoader().loadClass(Constants.ACCS_IMPL_NAME).getDeclaredConstructor(new Class[]{Context.class, String.class}).newInstance(new Object[]{context, str});
            if (iACCSManager != null) {
                return iACCSManager;
            }
            try {
                return (IACCSManager) Class.forName(Constants.ACCS_IMPL_NAME).getDeclaredConstructor(new Class[]{Context.class, String.class}).newInstance(new Object[]{context, str});
            } catch (Exception e) {
                ALog.e(TAG, "createAccsInstance", e, new Object[0]);
                return iACCSManager;
            }
        } catch (Exception e2) {
            ALog.e(TAG, "createAccsInstance", e2, new Object[0]);
            try {
                return (IACCSManager) Class.forName(Constants.ACCS_IMPL_NAME).getDeclaredConstructor(new Class[]{Context.class, String.class}).newInstance(new Object[]{context, str});
            } catch (Exception e3) {
                ALog.e(TAG, "createAccsInstance", e3, new Object[0]);
                return null;
            }
        } finally {
            try {
                Class.forName(Constants.ACCS_IMPL_NAME).getDeclaredConstructor(new Class[]{Context.class, String.class}).newInstance(new Object[]{context, str});
            } catch (Exception e4) {
                ALog.e(TAG, "createAccsInstance", e4, new Object[0]);
            }
        }
    }

    public static void forceEnableService(Context context) {
        getManagerImpl(context).forceEnableService(context);
    }

    public static void forceDisableService(Context context) {
        getManagerImpl(context).forceDisableService(context);
    }

    public static String[] getAppkey(Context context) {
        try {
            String string = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getString("appkey", null);
            ALog.i(TAG, "getAppkey:".concat(String.valueOf(string)), new Object[0]);
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            return string.split("\\|");
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
