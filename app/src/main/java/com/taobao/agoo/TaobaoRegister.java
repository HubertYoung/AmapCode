package com.taobao.agoo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsClientConfig.ENV;
import com.taobao.accs.AccsException;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.control.RequestListener;
import com.taobao.agoo.control.data.RegisterDO;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.common.MurmurHashUtil;
import org.android.agoo.control.NotifManager;
import org.android.agoo.control.NotifManager.AnonymousClass2;

public final class TaobaoRegister {
    private static final int EVENT_ID = 66001;
    static final String PREFERENCES = "Agoo_AppStore";
    static final String PROPERTY_APP_NOTIFICATION_CUSTOM_SOUND = "app_notification_custom_sound";
    static final String PROPERTY_APP_NOTIFICATION_ICON = "app_notification_icon";
    static final String PROPERTY_APP_NOTIFICATION_SOUND = "app_notification_sound";
    static final String PROPERTY_APP_NOTIFICATION_VIBRATE = "app_notification_vibrate";
    private static final String SERVICEID = "agooSend";
    protected static final String TAG = "TaobaoRegister";
    /* access modifiers changed from: private */
    public static RequestListener mRequestListener;

    @Deprecated
    public static void setBuilderSound(Context context, String str) {
    }

    @Deprecated
    public static void setNotificationIcon(Context context, int i) {
    }

    @Deprecated
    public static void setNotificationSound(Context context, boolean z) {
    }

    @Deprecated
    public static void setNotificationVibrate(Context context, boolean z) {
    }

    private TaobaoRegister() {
        throw new UnsupportedOperationException();
    }

    public static synchronized void setAccsConfigTag(Context context, String str) {
        synchronized (TaobaoRegister.class) {
            Config.a = str;
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null) {
                throw new RuntimeException("accs config not exist!! please set accs config first!!");
            }
            ALog.i(TAG, "setAccsConfigTag", "config", configByTag.toString());
            AdapterGlobalClientInfo.mAuthCode = configByTag.getAuthCode();
            Config.setAgooAppKey(context, configByTag.getAppKey());
            String appSecret = configByTag.getAppSecret();
            AdapterUtilityImpl.mAgooAppSecret = appSecret;
            if (!TextUtils.isEmpty(appSecret)) {
                AdapterGlobalClientInfo.mSecurityType = 2;
            }
        }
    }

    @Deprecated
    public static synchronized void register(Context context, String str, String str2, String str3, IRegister iRegister) throws AccsException {
        synchronized (TaobaoRegister.class) {
            register(context, str, str, str2, str3, iRegister);
        }
    }

    /* JADX INFO: finally extract failed */
    public static synchronized void register(Context context, String str, String str2, String str3, String str4, IRegister iRegister) throws AccsException {
        Context context2 = context;
        String str5 = str;
        String str6 = str2;
        String str7 = str3;
        synchronized (TaobaoRegister.class) {
            if (context2 != null) {
                try {
                    if (!TextUtils.isEmpty(str2)) {
                        if (!TextUtils.isEmpty(str5)) {
                            if ((context2.getApplicationInfo().flags & 2) != 0) {
                                ALog.isUseTlog = false;
                                cl.a();
                            }
                            ALog.i(TAG, "register", "appKey", str6, Constants.KEY_CONFIG_TAG, str5);
                            Context applicationContext = context2.getApplicationContext();
                            Config.a = str5;
                            Config.setAgooAppKey(context2, str6);
                            AdapterUtilityImpl.mAgooAppSecret = str7;
                            if (!TextUtils.isEmpty(str3)) {
                                AdapterGlobalClientInfo.mSecurityType = 2;
                            }
                            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str5);
                            if (configByTag == null) {
                                new Builder().setAppKey(str6).setAppSecret(str7).setTag(str5).build();
                            } else {
                                AdapterGlobalClientInfo.mAuthCode = configByTag.getAuthCode();
                            }
                            IACCSManager accsInstance = ACCSManager.getAccsInstance(context2, str6, str5);
                            final Context context3 = applicationContext;
                            final IACCSManager iACCSManager = accsInstance;
                            final IRegister iRegister2 = iRegister;
                            final String str8 = str6;
                            final String str9 = str4;
                            AnonymousClass1 r1 = new IAgooAppReceiver() {
                                public final void onBindApp(int i, String str) {
                                    try {
                                        ALog.i(TaobaoRegister.TAG, "onBindApp", "errorCode", Integer.valueOf(i));
                                        if (i == 200) {
                                            if (TaobaoRegister.mRequestListener == null) {
                                                TaobaoRegister.mRequestListener = new RequestListener(context3);
                                            }
                                            iACCSManager.registerDataListener(context3, TaobaoConstants.SERVICE_ID_DEVICECMD, TaobaoRegister.mRequestListener);
                                            TaobaoRegister.mRequestListener;
                                            if (RequestListener.mAgooBindCache.isAgooRegistered(context3.getPackageName())) {
                                                ALog.i(TaobaoRegister.TAG, "agoo aready Registered return ", new Object[0]);
                                                if (iRegister2 != null) {
                                                    iRegister2.onSuccess(Config.h(context3));
                                                }
                                                return;
                                            }
                                            byte[] buildRegister = RegisterDO.buildRegister(context3, str8, str9);
                                            if (buildRegister == null) {
                                                if (iRegister2 != null) {
                                                    iRegister2.onFailure(TaobaoConstants.REGISTER_ERROR, "req data null");
                                                }
                                                return;
                                            }
                                            String sendRequest = iACCSManager.sendRequest(context3, new AccsRequest(null, TaobaoConstants.SERVICE_ID_DEVICECMD, buildRegister, null));
                                            if (TextUtils.isEmpty(sendRequest)) {
                                                if (iRegister2 != null) {
                                                    iRegister2.onFailure(TaobaoConstants.REGISTER_ERROR, "accs channel disabled!");
                                                    return;
                                                }
                                            } else if (iRegister2 != null) {
                                                TaobaoRegister.mRequestListener.mListeners.put(sendRequest, iRegister2);
                                            }
                                            return;
                                        }
                                        if (iRegister2 != null) {
                                            iRegister2.onFailure(String.valueOf(i), "accs bindapp error!");
                                        }
                                    } catch (Throwable th) {
                                        ALog.e(TaobaoRegister.TAG, "register onBindApp", th, new Object[0]);
                                    }
                                }

                                public final String getAppkey() {
                                    return str8;
                                }
                            };
                            accsInstance.bindApp(applicationContext, str6, str7, str4, r1);
                            return;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ALog.e(TAG, "register params null", "appkey", str6, Constants.KEY_CONFIG_TAG, str5);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a8, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b3, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void setAlias(android.content.Context r9, java.lang.String r10, com.taobao.agoo.ICallback r11) {
        /*
            java.lang.Class<com.taobao.agoo.TaobaoRegister> r0 = com.taobao.agoo.TaobaoRegister.class
            monitor-enter(r0)
            java.lang.String r1 = "TaobaoRegister"
            java.lang.String r2 = "setAlias"
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x00f3 }
            java.lang.String r5 = "alias"
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x00f3 }
            r5 = 1
            r4[r5] = r10     // Catch:{ all -> 0x00f3 }
            com.taobao.accs.utl.ALog.i(r1, r2, r4)     // Catch:{ all -> 0x00f3 }
            java.lang.String r1 = org.android.agoo.common.Config.h(r9)     // Catch:{ all -> 0x00f3 }
            java.lang.String r2 = org.android.agoo.common.Config.a(r9)     // Catch:{ all -> 0x00f3 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x00f3 }
            if (r4 != 0) goto L_0x00c0
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00f3 }
            if (r4 != 0) goto L_0x00c0
            if (r9 == 0) goto L_0x00c0
            boolean r4 = android.text.TextUtils.isEmpty(r10)     // Catch:{ all -> 0x00f3 }
            if (r4 == 0) goto L_0x0033
            goto L_0x00c0
        L_0x0033:
            com.taobao.agoo.control.RequestListener r4 = mRequestListener     // Catch:{ Throwable -> 0x00b4 }
            if (r4 != 0) goto L_0x0042
            com.taobao.agoo.control.RequestListener r4 = new com.taobao.agoo.control.RequestListener     // Catch:{ Throwable -> 0x00b4 }
            android.content.Context r7 = r9.getApplicationContext()     // Catch:{ Throwable -> 0x00b4 }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x00b4 }
            mRequestListener = r4     // Catch:{ Throwable -> 0x00b4 }
        L_0x0042:
            com.taobao.agoo.control.AgooBindCache r4 = com.taobao.agoo.control.RequestListener.mAgooBindCache     // Catch:{ Throwable -> 0x00b4 }
            boolean r4 = r4.isAgooAliasBinded(r10)     // Catch:{ Throwable -> 0x00b4 }
            if (r4 == 0) goto L_0x0060
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r1 = "setAlias already set"
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00b4 }
            java.lang.String r3 = "alias"
            r2[r6] = r3     // Catch:{ Throwable -> 0x00b4 }
            r2[r5] = r10     // Catch:{ Throwable -> 0x00b4 }
            com.taobao.accs.utl.ALog.i(r9, r1, r2)     // Catch:{ Throwable -> 0x00b4 }
            if (r11 == 0) goto L_0x005e
            r11.onSuccess()     // Catch:{ Throwable -> 0x00b4 }
        L_0x005e:
            monitor-exit(r0)
            return
        L_0x0060:
            java.lang.String r3 = org.android.agoo.common.Config.b(r9)     // Catch:{ Throwable -> 0x00b4 }
            com.taobao.accs.IACCSManager r3 = com.taobao.accs.ACCSManager.getAccsInstance(r9, r2, r3)     // Catch:{ Throwable -> 0x00b4 }
            com.taobao.agoo.control.AgooBindCache r4 = com.taobao.agoo.control.RequestListener.mAgooBindCache     // Catch:{ Throwable -> 0x00b4 }
            java.lang.String r5 = r9.getPackageName()     // Catch:{ Throwable -> 0x00b4 }
            boolean r4 = r4.isAgooRegistered(r5)     // Catch:{ Throwable -> 0x00b4 }
            if (r4 == 0) goto L_0x00a9
            java.lang.String r4 = "AgooDeviceCmd"
            com.taobao.agoo.control.RequestListener r5 = mRequestListener     // Catch:{ Throwable -> 0x00b4 }
            r3.registerDataListener(r9, r4, r5)     // Catch:{ Throwable -> 0x00b4 }
            byte[] r1 = com.taobao.agoo.control.data.AliasDO.buildsetAlias(r2, r1, r10)     // Catch:{ Throwable -> 0x00b4 }
            com.taobao.accs.ACCSManager$AccsRequest r2 = new com.taobao.accs.ACCSManager$AccsRequest     // Catch:{ Throwable -> 0x00b4 }
            java.lang.String r4 = "AgooDeviceCmd"
            r5 = 0
            r2.<init>(r5, r4, r1, r5)     // Catch:{ Throwable -> 0x00b4 }
            java.lang.String r9 = r3.sendRequest(r9, r2)     // Catch:{ Throwable -> 0x00b4 }
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x00b4 }
            if (r1 == 0) goto L_0x009c
            if (r11 == 0) goto L_0x00a7
            java.lang.String r9 = "504.1"
            java.lang.String r10 = "accs channel disabled!"
            r11.onFailure(r9, r10)     // Catch:{ Throwable -> 0x00b4 }
            monitor-exit(r0)
            return
        L_0x009c:
            if (r11 == 0) goto L_0x00a7
            r11.extra = r10     // Catch:{ Throwable -> 0x00b4 }
            com.taobao.agoo.control.RequestListener r10 = mRequestListener     // Catch:{ Throwable -> 0x00b4 }
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r10 = r10.mListeners     // Catch:{ Throwable -> 0x00b4 }
            r10.put(r9, r11)     // Catch:{ Throwable -> 0x00b4 }
        L_0x00a7:
            monitor-exit(r0)
            return
        L_0x00a9:
            if (r11 == 0) goto L_0x00b2
            java.lang.String r9 = "504.1"
            java.lang.String r10 = "bindApp first!!"
            r11.onFailure(r9, r10)     // Catch:{ Throwable -> 0x00b4 }
        L_0x00b2:
            monitor-exit(r0)
            return
        L_0x00b4:
            r9 = move-exception
            java.lang.String r10 = "TaobaoRegister"
            java.lang.String r11 = "setAlias"
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ all -> 0x00f3 }
            com.taobao.accs.utl.ALog.e(r10, r11, r9, r1)     // Catch:{ all -> 0x00f3 }
            monitor-exit(r0)
            return
        L_0x00c0:
            if (r11 == 0) goto L_0x00c9
            java.lang.String r4 = "504.1"
            java.lang.String r7 = "input params null!!"
            r11.onFailure(r4, r7)     // Catch:{ all -> 0x00f3 }
        L_0x00c9:
            java.lang.String r11 = "TaobaoRegister"
            java.lang.String r4 = "setAlias param null"
            r7 = 8
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x00f3 }
            java.lang.String r8 = "appkey"
            r7[r6] = r8     // Catch:{ all -> 0x00f3 }
            r7[r5] = r2     // Catch:{ all -> 0x00f3 }
            java.lang.String r2 = "deviceId"
            r7[r3] = r2     // Catch:{ all -> 0x00f3 }
            r2 = 3
            r7[r2] = r1     // Catch:{ all -> 0x00f3 }
            r1 = 4
            java.lang.String r2 = "alias"
            r7[r1] = r2     // Catch:{ all -> 0x00f3 }
            r1 = 5
            r7[r1] = r10     // Catch:{ all -> 0x00f3 }
            r10 = 6
            java.lang.String r1 = "context"
            r7[r10] = r1     // Catch:{ all -> 0x00f3 }
            r10 = 7
            r7[r10] = r9     // Catch:{ all -> 0x00f3 }
            com.taobao.accs.utl.ALog.e(r11, r4, r7)     // Catch:{ all -> 0x00f3 }
            monitor-exit(r0)
            return
        L_0x00f3:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.setAlias(android.content.Context, java.lang.String, com.taobao.agoo.ICallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0073, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void removeAlias(android.content.Context r7, java.lang.String r8, com.taobao.agoo.ICallback r9) {
        /*
            java.lang.Class<com.taobao.agoo.TaobaoRegister> r0 = com.taobao.agoo.TaobaoRegister.class
            monitor-enter(r0)
            java.lang.String r1 = "TaobaoRegister"
            java.lang.String r2 = "removeAlias"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x00b5 }
            com.taobao.accs.utl.ALog.i(r1, r2, r4)     // Catch:{ all -> 0x00b5 }
            java.lang.String r1 = org.android.agoo.common.Config.h(r7)     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r2 = org.android.agoo.common.Config.a(r7)     // Catch:{ Throwable -> 0x00a9 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x00a9 }
            if (r4 != 0) goto L_0x0074
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x00a9 }
            if (r4 != 0) goto L_0x0074
            if (r7 == 0) goto L_0x0074
            boolean r4 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x00a9 }
            if (r4 == 0) goto L_0x002a
            goto L_0x0074
        L_0x002a:
            java.lang.String r4 = org.android.agoo.common.Config.b(r7)     // Catch:{ Throwable -> 0x00a9 }
            com.taobao.accs.IACCSManager r4 = com.taobao.accs.ACCSManager.getAccsInstance(r7, r2, r4)     // Catch:{ Throwable -> 0x00a9 }
            com.taobao.agoo.control.RequestListener r5 = mRequestListener     // Catch:{ Throwable -> 0x00a9 }
            if (r5 != 0) goto L_0x0041
            com.taobao.agoo.control.RequestListener r5 = new com.taobao.agoo.control.RequestListener     // Catch:{ Throwable -> 0x00a9 }
            android.content.Context r6 = r7.getApplicationContext()     // Catch:{ Throwable -> 0x00a9 }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00a9 }
            mRequestListener = r5     // Catch:{ Throwable -> 0x00a9 }
        L_0x0041:
            java.lang.String r5 = "AgooDeviceCmd"
            com.taobao.agoo.control.RequestListener r6 = mRequestListener     // Catch:{ Throwable -> 0x00a9 }
            r4.registerDataListener(r7, r5, r6)     // Catch:{ Throwable -> 0x00a9 }
            byte[] r8 = com.taobao.agoo.control.data.AliasDO.buildremoveAliasByName(r2, r1, r8)     // Catch:{ Throwable -> 0x00a9 }
            com.taobao.accs.ACCSManager$AccsRequest r1 = new com.taobao.accs.ACCSManager$AccsRequest     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r2 = "AgooDeviceCmd"
            r5 = 0
            r1.<init>(r5, r2, r8, r5)     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r7 = r4.sendRequest(r7, r1)     // Catch:{ Throwable -> 0x00a9 }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x00a9 }
            if (r8 == 0) goto L_0x0069
            if (r9 == 0) goto L_0x0072
            java.lang.String r7 = "504.1"
            java.lang.String r8 = "accs channel disabled!"
            r9.onFailure(r7, r8)     // Catch:{ Throwable -> 0x00a9 }
            monitor-exit(r0)
            return
        L_0x0069:
            if (r9 == 0) goto L_0x0072
            com.taobao.agoo.control.RequestListener r8 = mRequestListener     // Catch:{ Throwable -> 0x00a9 }
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r8 = r8.mListeners     // Catch:{ Throwable -> 0x00a9 }
            r8.put(r7, r9)     // Catch:{ Throwable -> 0x00a9 }
        L_0x0072:
            monitor-exit(r0)
            return
        L_0x0074:
            if (r9 == 0) goto L_0x007d
            java.lang.String r4 = "504.1"
            java.lang.String r5 = "input params null!!"
            r9.onFailure(r4, r5)     // Catch:{ Throwable -> 0x00a9 }
        L_0x007d:
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r4 = "setAlias param null"
            r5 = 8
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00a9 }
            java.lang.String r6 = "appkey"
            r5[r3] = r6     // Catch:{ Throwable -> 0x00a9 }
            r6 = 1
            r5[r6] = r2     // Catch:{ Throwable -> 0x00a9 }
            r2 = 2
            java.lang.String r6 = "deviceId"
            r5[r2] = r6     // Catch:{ Throwable -> 0x00a9 }
            r2 = 3
            r5[r2] = r1     // Catch:{ Throwable -> 0x00a9 }
            r1 = 4
            java.lang.String r2 = "alias"
            r5[r1] = r2     // Catch:{ Throwable -> 0x00a9 }
            r1 = 5
            r5[r1] = r8     // Catch:{ Throwable -> 0x00a9 }
            r8 = 6
            java.lang.String r1 = "context"
            r5[r8] = r1     // Catch:{ Throwable -> 0x00a9 }
            r8 = 7
            r5[r8] = r7     // Catch:{ Throwable -> 0x00a9 }
            com.taobao.accs.utl.ALog.e(r9, r4, r5)     // Catch:{ Throwable -> 0x00a9 }
            monitor-exit(r0)
            return
        L_0x00a9:
            r7 = move-exception
            java.lang.String r8 = "TaobaoRegister"
            java.lang.String r9 = "removeAlias"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x00b5 }
            com.taobao.accs.utl.ALog.e(r8, r9, r7, r1)     // Catch:{ all -> 0x00b5 }
            monitor-exit(r0)
            return
        L_0x00b5:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.removeAlias(android.content.Context, java.lang.String, com.taobao.agoo.ICallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0077, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void removeAlias(android.content.Context r8, com.taobao.agoo.ICallback r9) {
        /*
            java.lang.Class<com.taobao.agoo.TaobaoRegister> r0 = com.taobao.agoo.TaobaoRegister.class
            monitor-enter(r0)
            java.lang.String r1 = "TaobaoRegister"
            java.lang.String r2 = "removeAlias"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x00b9 }
            com.taobao.accs.utl.ALog.i(r1, r2, r4)     // Catch:{ all -> 0x00b9 }
            java.lang.String r1 = org.android.agoo.common.Config.h(r8)     // Catch:{ Throwable -> 0x00ad }
            java.lang.String r2 = org.android.agoo.common.Config.i(r8)     // Catch:{ Throwable -> 0x00ad }
            java.lang.String r4 = org.android.agoo.common.Config.a(r8)     // Catch:{ Throwable -> 0x00ad }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00ad }
            if (r5 != 0) goto L_0x0078
            boolean r5 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x00ad }
            if (r5 != 0) goto L_0x0078
            if (r8 == 0) goto L_0x0078
            boolean r5 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x00ad }
            if (r5 == 0) goto L_0x002e
            goto L_0x0078
        L_0x002e:
            java.lang.String r5 = org.android.agoo.common.Config.b(r8)     // Catch:{ Throwable -> 0x00ad }
            com.taobao.accs.IACCSManager r5 = com.taobao.accs.ACCSManager.getAccsInstance(r8, r4, r5)     // Catch:{ Throwable -> 0x00ad }
            com.taobao.agoo.control.RequestListener r6 = mRequestListener     // Catch:{ Throwable -> 0x00ad }
            if (r6 != 0) goto L_0x0045
            com.taobao.agoo.control.RequestListener r6 = new com.taobao.agoo.control.RequestListener     // Catch:{ Throwable -> 0x00ad }
            android.content.Context r7 = r8.getApplicationContext()     // Catch:{ Throwable -> 0x00ad }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x00ad }
            mRequestListener = r6     // Catch:{ Throwable -> 0x00ad }
        L_0x0045:
            java.lang.String r6 = "AgooDeviceCmd"
            com.taobao.agoo.control.RequestListener r7 = mRequestListener     // Catch:{ Throwable -> 0x00ad }
            r5.registerDataListener(r8, r6, r7)     // Catch:{ Throwable -> 0x00ad }
            byte[] r1 = com.taobao.agoo.control.data.AliasDO.buildremoveAlias(r4, r1, r2)     // Catch:{ Throwable -> 0x00ad }
            com.taobao.accs.ACCSManager$AccsRequest r2 = new com.taobao.accs.ACCSManager$AccsRequest     // Catch:{ Throwable -> 0x00ad }
            java.lang.String r4 = "AgooDeviceCmd"
            r6 = 0
            r2.<init>(r6, r4, r1, r6)     // Catch:{ Throwable -> 0x00ad }
            java.lang.String r8 = r5.sendRequest(r8, r2)     // Catch:{ Throwable -> 0x00ad }
            boolean r1 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x00ad }
            if (r1 == 0) goto L_0x006d
            if (r9 == 0) goto L_0x0076
            java.lang.String r8 = "504.1"
            java.lang.String r1 = "accs channel disabled!"
            r9.onFailure(r8, r1)     // Catch:{ Throwable -> 0x00ad }
            monitor-exit(r0)
            return
        L_0x006d:
            if (r9 == 0) goto L_0x0076
            com.taobao.agoo.control.RequestListener r1 = mRequestListener     // Catch:{ Throwable -> 0x00ad }
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r1 = r1.mListeners     // Catch:{ Throwable -> 0x00ad }
            r1.put(r8, r9)     // Catch:{ Throwable -> 0x00ad }
        L_0x0076:
            monitor-exit(r0)
            return
        L_0x0078:
            if (r9 == 0) goto L_0x0081
            java.lang.String r5 = "504.1"
            java.lang.String r6 = "input params null!!"
            r9.onFailure(r5, r6)     // Catch:{ Throwable -> 0x00ad }
        L_0x0081:
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r5 = "setAlias param null"
            r6 = 8
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00ad }
            java.lang.String r7 = "appkey"
            r6[r3] = r7     // Catch:{ Throwable -> 0x00ad }
            r7 = 1
            r6[r7] = r4     // Catch:{ Throwable -> 0x00ad }
            r4 = 2
            java.lang.String r7 = "deviceId"
            r6[r4] = r7     // Catch:{ Throwable -> 0x00ad }
            r4 = 3
            r6[r4] = r1     // Catch:{ Throwable -> 0x00ad }
            r1 = 4
            java.lang.String r4 = "pushAliasToken"
            r6[r1] = r4     // Catch:{ Throwable -> 0x00ad }
            r1 = 5
            r6[r1] = r2     // Catch:{ Throwable -> 0x00ad }
            r1 = 6
            java.lang.String r2 = "context"
            r6[r1] = r2     // Catch:{ Throwable -> 0x00ad }
            r1 = 7
            r6[r1] = r8     // Catch:{ Throwable -> 0x00ad }
            com.taobao.accs.utl.ALog.e(r9, r5, r6)     // Catch:{ Throwable -> 0x00ad }
            monitor-exit(r0)
            return
        L_0x00ad:
            r8 = move-exception
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r1 = "removeAlias"
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ all -> 0x00b9 }
            com.taobao.accs.utl.ALog.e(r9, r1, r8, r2)     // Catch:{ all -> 0x00b9 }
            monitor-exit(r0)
            return
        L_0x00b9:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.removeAlias(android.content.Context, com.taobao.agoo.ICallback):void");
    }

    @Deprecated
    public static void bindAgoo(Context context, String str, String str2, CallBack callBack) {
        bindAgoo(context, null);
    }

    @Deprecated
    public static void unBindAgoo(Context context, String str, String str2, CallBack callBack) {
        unbindAgoo(context, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void sendSwitch(android.content.Context r8, com.taobao.agoo.ICallback r9, boolean r10) {
        /*
            java.lang.Class<com.taobao.agoo.TaobaoRegister> r0 = com.taobao.agoo.TaobaoRegister.class
            monitor-enter(r0)
            r1 = 0
            java.lang.String r2 = org.android.agoo.common.Config.h(r8)     // Catch:{ Throwable -> 0x00aa }
            java.lang.String r3 = org.android.agoo.common.Config.a(r8)     // Catch:{ Throwable -> 0x00aa }
            java.lang.String r4 = com.taobao.accs.utl.UtilityImpl.getDeviceId(r8)     // Catch:{ Throwable -> 0x00aa }
            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x00aa }
            if (r5 != 0) goto L_0x006f
            if (r8 == 0) goto L_0x006f
            boolean r5 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x00aa }
            if (r5 == 0) goto L_0x0025
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00aa }
            if (r5 == 0) goto L_0x0025
            goto L_0x006f
        L_0x0025:
            java.lang.String r5 = org.android.agoo.common.Config.b(r8)     // Catch:{ Throwable -> 0x00aa }
            com.taobao.accs.IACCSManager r5 = com.taobao.accs.ACCSManager.getAccsInstance(r8, r3, r5)     // Catch:{ Throwable -> 0x00aa }
            com.taobao.agoo.control.RequestListener r6 = mRequestListener     // Catch:{ Throwable -> 0x00aa }
            if (r6 != 0) goto L_0x003c
            com.taobao.agoo.control.RequestListener r6 = new com.taobao.agoo.control.RequestListener     // Catch:{ Throwable -> 0x00aa }
            android.content.Context r7 = r8.getApplicationContext()     // Catch:{ Throwable -> 0x00aa }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x00aa }
            mRequestListener = r6     // Catch:{ Throwable -> 0x00aa }
        L_0x003c:
            java.lang.String r6 = "AgooDeviceCmd"
            com.taobao.agoo.control.RequestListener r7 = mRequestListener     // Catch:{ Throwable -> 0x00aa }
            r5.registerDataListener(r8, r6, r7)     // Catch:{ Throwable -> 0x00aa }
            byte[] r10 = com.taobao.agoo.control.data.SwitchDO.buildSwitchDO(r3, r2, r4, r10)     // Catch:{ Throwable -> 0x00aa }
            com.taobao.accs.ACCSManager$AccsRequest r2 = new com.taobao.accs.ACCSManager$AccsRequest     // Catch:{ Throwable -> 0x00aa }
            java.lang.String r3 = "AgooDeviceCmd"
            r4 = 0
            r2.<init>(r4, r3, r10, r4)     // Catch:{ Throwable -> 0x00aa }
            java.lang.String r8 = r5.sendRequest(r8, r2)     // Catch:{ Throwable -> 0x00aa }
            boolean r10 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x00aa }
            if (r10 == 0) goto L_0x0064
            if (r9 == 0) goto L_0x006d
            java.lang.String r8 = "503.2"
            java.lang.String r10 = "accs channel disabled!"
            r9.onFailure(r8, r10)     // Catch:{ Throwable -> 0x00aa }
            monitor-exit(r0)
            return
        L_0x0064:
            if (r9 == 0) goto L_0x006d
            com.taobao.agoo.control.RequestListener r10 = mRequestListener     // Catch:{ Throwable -> 0x00aa }
            java.util.Map<java.lang.String, com.taobao.agoo.ICallback> r10 = r10.mListeners     // Catch:{ Throwable -> 0x00aa }
            r10.put(r8, r9)     // Catch:{ Throwable -> 0x00aa }
        L_0x006d:
            monitor-exit(r0)
            return
        L_0x006f:
            if (r9 == 0) goto L_0x0078
            java.lang.String r4 = "503.3"
            java.lang.String r5 = "input params null!!"
            r9.onFailure(r4, r5)     // Catch:{ Throwable -> 0x00aa }
        L_0x0078:
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r4 = "sendSwitch param null"
            r5 = 8
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00aa }
            java.lang.String r6 = "appkey"
            r5[r1] = r6     // Catch:{ Throwable -> 0x00aa }
            r6 = 1
            r5[r6] = r3     // Catch:{ Throwable -> 0x00aa }
            r3 = 2
            java.lang.String r6 = "deviceId"
            r5[r3] = r6     // Catch:{ Throwable -> 0x00aa }
            r3 = 3
            r5[r3] = r2     // Catch:{ Throwable -> 0x00aa }
            r2 = 4
            java.lang.String r3 = "context"
            r5[r2] = r3     // Catch:{ Throwable -> 0x00aa }
            r2 = 5
            r5[r2] = r8     // Catch:{ Throwable -> 0x00aa }
            r8 = 6
            java.lang.String r2 = "enablePush"
            r5[r8] = r2     // Catch:{ Throwable -> 0x00aa }
            r8 = 7
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)     // Catch:{ Throwable -> 0x00aa }
            r5[r8] = r10     // Catch:{ Throwable -> 0x00aa }
            com.taobao.accs.utl.ALog.e(r9, r4, r5)     // Catch:{ Throwable -> 0x00aa }
            monitor-exit(r0)
            return
        L_0x00a8:
            r8 = move-exception
            goto L_0x00b6
        L_0x00aa:
            r8 = move-exception
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r10 = "sendSwitch"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00a8 }
            com.taobao.accs.utl.ALog.e(r9, r10, r8, r1)     // Catch:{ all -> 0x00a8 }
            monitor-exit(r0)
            return
        L_0x00b6:
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.sendSwitch(android.content.Context, com.taobao.agoo.ICallback, boolean):void");
    }

    public static void bindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, true);
        UTMini.getInstance().commitEvent(66001, "bindAgoo", UtilityImpl.getDeviceId(context));
    }

    public static void unbindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, false);
        UTMini.getInstance().commitEvent(66001, "unregister", UtilityImpl.getDeviceId(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void clickMessage(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            org.android.agoo.control.NotifManager r0 = new org.android.agoo.control.NotifManager
            r0.<init>()
            r1 = 0
            r2 = 0
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0065 }
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)     // Catch:{ Throwable -> 0x0065 }
            if (r3 == 0) goto L_0x0028
            java.lang.String r3 = "TaobaoRegister"
            java.lang.String r4 = "clickMessage"
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r6 = "msgid"
            r5[r2] = r6     // Catch:{ Throwable -> 0x0065 }
            r6 = 1
            r5[r6] = r9     // Catch:{ Throwable -> 0x0065 }
            r6 = 2
            java.lang.String r7 = "extData"
            r5[r6] = r7     // Catch:{ Throwable -> 0x0065 }
            r6 = 3
            r5[r6] = r10     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.i(r3, r4, r5)     // Catch:{ Throwable -> 0x0065 }
        L_0x0028:
            java.lang.String r3 = "accs"
            java.lang.String r4 = "8"
            boolean r5 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x0065 }
            if (r5 == 0) goto L_0x003c
            java.lang.String r8 = "TaobaoRegister"
            java.lang.String r9 = "messageId == null"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.d(r8, r9, r10)     // Catch:{ Throwable -> 0x0065 }
            return
        L_0x003c:
            r0.a(r8)     // Catch:{ Throwable -> 0x0065 }
            org.android.agoo.common.MsgDO r5 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0065 }
            r5.<init>()     // Catch:{ Throwable -> 0x0065 }
            r5.a = r9     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.b = r10     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.e = r3     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.l = r4     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.AgooFactory r10 = new org.android.agoo.control.AgooFactory     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r10.<init>()     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r10.a(r8, r0, r1)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            java.lang.String r8 = "8"
            r10.a(r9, r8)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.NotifManager.a(r5)
            return
        L_0x005d:
            r8 = move-exception
            r1 = r5
            goto L_0x007e
        L_0x0060:
            r8 = move-exception
            r1 = r5
            goto L_0x0066
        L_0x0063:
            r8 = move-exception
            goto L_0x007e
        L_0x0065:
            r8 = move-exception
        L_0x0066:
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r10 = "clickMessage,error="
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0063 }
            java.lang.String r8 = r10.concat(r8)     // Catch:{ all -> 0x0063 }
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x0063 }
            com.taobao.accs.utl.ALog.e(r9, r8, r10)     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x007d
            org.android.agoo.control.NotifManager.a(r1)
            return
        L_0x007d:
            return
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            org.android.agoo.control.NotifManager.a(r1)
        L_0x0083:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.clickMessage(android.content.Context, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void dismissMessage(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            org.android.agoo.control.NotifManager r0 = new org.android.agoo.control.NotifManager
            r0.<init>()
            r1 = 0
            r2 = 0
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0065 }
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)     // Catch:{ Throwable -> 0x0065 }
            if (r3 == 0) goto L_0x0028
            java.lang.String r3 = "TaobaoRegister"
            java.lang.String r4 = "dismissMessage"
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r6 = "msgid"
            r5[r2] = r6     // Catch:{ Throwable -> 0x0065 }
            r6 = 1
            r5[r6] = r9     // Catch:{ Throwable -> 0x0065 }
            r6 = 2
            java.lang.String r7 = "extData"
            r5[r6] = r7     // Catch:{ Throwable -> 0x0065 }
            r6 = 3
            r5[r6] = r10     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.i(r3, r4, r5)     // Catch:{ Throwable -> 0x0065 }
        L_0x0028:
            java.lang.String r3 = "accs"
            java.lang.String r4 = "9"
            boolean r5 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x0065 }
            if (r5 == 0) goto L_0x003c
            java.lang.String r8 = "TaobaoRegister"
            java.lang.String r9 = "messageId == null"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.d(r8, r9, r10)     // Catch:{ Throwable -> 0x0065 }
            return
        L_0x003c:
            r0.a(r8)     // Catch:{ Throwable -> 0x0065 }
            org.android.agoo.common.MsgDO r5 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0065 }
            r5.<init>()     // Catch:{ Throwable -> 0x0065 }
            r5.a = r9     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.b = r10     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.e = r3     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.l = r4     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.AgooFactory r10 = new org.android.agoo.control.AgooFactory     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r10.<init>()     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r10.a(r8, r0, r1)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            java.lang.String r8 = "9"
            r10.a(r9, r8)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.NotifManager.a(r5)
            return
        L_0x005d:
            r8 = move-exception
            r1 = r5
            goto L_0x007e
        L_0x0060:
            r8 = move-exception
            r1 = r5
            goto L_0x0066
        L_0x0063:
            r8 = move-exception
            goto L_0x007e
        L_0x0065:
            r8 = move-exception
        L_0x0066:
            java.lang.String r9 = "TaobaoRegister"
            java.lang.String r10 = "clickMessage,error="
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0063 }
            java.lang.String r8 = r10.concat(r8)     // Catch:{ all -> 0x0063 }
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x0063 }
            com.taobao.accs.utl.ALog.e(r9, r8, r10)     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x007d
            org.android.agoo.control.NotifManager.a(r1)
            return
        L_0x007d:
            return
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            org.android.agoo.control.NotifManager.a(r1)
        L_0x0083:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.dismissMessage(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void pingApp(Context context, String str, String str2, String str3, int i) {
        NotifManager notifManager = new NotifManager();
        notifManager.a(context);
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            if (ALog.isPrintLog(Level.I)) {
                StringBuilder sb = new StringBuilder("pingApp [print param],percent=");
                sb.append(i);
                sb.append(",pack=");
                sb.append(str2);
                sb.append(",service=");
                sb.append(str3);
                sb.append(",action=");
                sb.append(str);
                ALog.i("NotifManager", sb.toString(), new Object[0]);
            }
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = notifManager.a;
            AnonymousClass2 r0 = new Runnable(i, str2, str, str3) {
                final /* synthetic */ int a;
                final /* synthetic */ String b;
                final /* synthetic */ String c;
                final /* synthetic */ String d;

                {
                    this.a = r2;
                    this.b = r3;
                    this.c = r4;
                    this.d = r5;
                }

                public void run() {
                    try {
                        if ((this.a == 100 || MurmurHashUtil.a(AdapterUtilityImpl.getDeviceId(NotifManager.b)) <= this.a) && NotifManager.d(this.b)) {
                            Intent intent = new Intent();
                            intent.setAction(this.c);
                            intent.setClassName(this.b, this.d);
                            intent.putExtra("source", NotifManager.b.getPackageName());
                            NotifManager.b.startService(intent);
                            String c2 = NotifManager.e(this.b);
                            if (ALog.isPrintLog(Level.I)) {
                                StringBuilder sb = new StringBuilder("pingApp [begin],action=");
                                sb.append(this.c);
                                sb.append(",pack=");
                                sb.append(this.b);
                                sb.append(",service=");
                                sb.append(this.d);
                                sb.append(",appVersion=");
                                sb.append(c2);
                                ALog.i("NotifManager", sb.toString(), new Object[0]);
                            }
                            UTMini.getInstance().commitEvent(66001, "pingApp", AdapterUtilityImpl.getDeviceId(NotifManager.b), this.b, c2);
                        }
                    } catch (Throwable th) {
                        UTMini.getInstance().commitEvent(66002, "pingApp", AdapterUtilityImpl.getDeviceId(NotifManager.b), th.toString());
                        ALog.e("NotifManager", "pingApp", th, new Object[0]);
                    }
                }
            };
            scheduledThreadPoolExecutor.execute(r0);
        }
    }

    public static void isEnableDaemonServer(Context context, boolean z) {
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(TAG, "isEnableDaemonServer begin,enable=".concat(String.valueOf(z)), new Object[0]);
        }
        Config.a(context, z);
    }

    public static void setAgooMsgReceiveService(String str) {
        AdapterGlobalClientInfo.mAgooCustomServiceName = str;
    }

    public static void setEnv(Context context, @ENV int i) {
        ACCSClient.setEnvironment(context, i);
    }

    @Deprecated
    public static void unregister(Context context, CallBack callBack) {
        unbindAgoo(context, null);
    }
}
