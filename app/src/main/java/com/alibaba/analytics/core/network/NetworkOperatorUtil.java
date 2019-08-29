package com.alibaba.analytics.core.network;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.telephony.SubscriptionManager.OnSubscriptionsChangedListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.UTMCDevice;

public class NetworkOperatorUtil {
    private static final String NETWORK_OPERATOR_MOBILE = "中国移动";
    private static final String NETWORK_OPERATOR_TELECOM = "中国电信";
    private static final String NETWORK_OPERATOR_UNICOM = "中国联通";
    private static final String NETWORK_OPERATOR_UNKNOWN = "Unknown";
    private static final String TAG = "NetworkOperatorUtil";
    /* access modifiers changed from: private */
    public static String mCurrentNetworkOperator = "Unknown";
    private static SubscriptionManager mSubscriptionManager;

    static class NetworkOperatorHandler extends Handler {
        public NetworkOperatorHandler(Looper looper) {
            super(looper);
        }

        public void postTask(Runnable runnable) {
            Logger.d();
            if (runnable != null) {
                try {
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.obj = runnable;
                    sendMessage(obtain);
                } catch (Throwable unused) {
                }
            }
        }

        public void handleMessage(Message message) {
            try {
                if (message.obj != null && (message.obj instanceof Runnable)) {
                    ((Runnable) message.obj).run();
                }
            } catch (Throwable th) {
                Logger.e(NetworkOperatorUtil.TAG, th, new Object[0]);
            }
            super.handleMessage(message);
        }
    }

    public static synchronized String getCurrentNetworkOperatorName() {
        String str;
        synchronized (NetworkOperatorUtil.class) {
            try {
                str = mCurrentNetworkOperator;
            }
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void registerSIMCardChangedInHandler(final android.content.Context r3) throws java.lang.Exception {
        /*
            java.lang.Class<com.alibaba.analytics.core.network.NetworkOperatorUtil> r0 = com.alibaba.analytics.core.network.NetworkOperatorUtil.class
            monitor-enter(r0)
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0025 }
            r2 = 22
            if (r1 >= r2) goto L_0x000b
            monitor-exit(r0)
            return
        L_0x000b:
            android.telephony.SubscriptionManager r1 = mSubscriptionManager     // Catch:{ all -> 0x0025 }
            if (r1 != 0) goto L_0x0023
            android.os.Looper.prepare()     // Catch:{ all -> 0x0025 }
            com.alibaba.analytics.core.network.NetworkOperatorUtil$NetworkOperatorHandler r1 = new com.alibaba.analytics.core.network.NetworkOperatorUtil$NetworkOperatorHandler     // Catch:{ all -> 0x0025 }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0025 }
            r1.<init>(r2)     // Catch:{ all -> 0x0025 }
            com.alibaba.analytics.core.network.NetworkOperatorUtil$1 r2 = new com.alibaba.analytics.core.network.NetworkOperatorUtil$1     // Catch:{ all -> 0x0025 }
            r2.<init>(r3)     // Catch:{ all -> 0x0025 }
            r1.postTask(r2)     // Catch:{ all -> 0x0025 }
        L_0x0023:
            monitor-exit(r0)
            return
        L_0x0025:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.network.NetworkOperatorUtil.registerSIMCardChangedInHandler(android.content.Context):void");
    }

    /* access modifiers changed from: private */
    public static synchronized void registerSIMCardChanged(final Context context) {
        synchronized (NetworkOperatorUtil.class) {
            if (VERSION.SDK_INT >= 22) {
                if (mSubscriptionManager == null) {
                    try {
                        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                        mSubscriptionManager = subscriptionManager;
                        if (subscriptionManager == null) {
                            Logger.d((String) TAG, "SubscriptionManager is null");
                            return;
                        }
                        mSubscriptionManager.addOnSubscriptionsChangedListener(new OnSubscriptionsChangedListener() {
                            public final void onSubscriptionsChanged() {
                                super.onSubscriptionsChanged();
                                Logger.d((String) NetworkOperatorUtil.TAG, "onSubscriptionsChanged");
                                NetworkOperatorUtil.updateNetworkOperatorName(context);
                                Logger.d((String) NetworkOperatorUtil.TAG, "CurrentNetworkOperator", NetworkOperatorUtil.mCurrentNetworkOperator);
                                UTMCDevice.updateUTMCDeviceNetworkStatus(context);
                            }
                        });
                        Logger.d((String) TAG, "addOnSubscriptionsChangedListener");
                    } catch (Throwable th) {
                        Logger.e(TAG, th, new Object[0]);
                    }
                }
            }
        }
    }

    static synchronized void updateNetworkOperatorName(Context context) {
        synchronized (NetworkOperatorUtil.class) {
            Logger.d((String) TAG, "updateNetworkOperatorName");
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    mCurrentNetworkOperator = "Unknown";
                    return;
                }
                if (telephonyManager.getSimState() == 5) {
                    String simOperator = telephonyManager.getSimOperator();
                    if (TextUtils.isEmpty(simOperator)) {
                        String simOperatorName = telephonyManager.getSimOperatorName();
                        if (TextUtils.isEmpty(simOperatorName)) {
                            mCurrentNetworkOperator = "Unknown";
                            return;
                        } else {
                            mCurrentNetworkOperator = simOperatorName;
                            return;
                        }
                    } else {
                        if (!simOperator.equals("46000") && !simOperator.equals("46002") && !simOperator.equals("46007")) {
                            if (!simOperator.equals("46008")) {
                                if (!simOperator.equals("46001") && !simOperator.equals("46006")) {
                                    if (!simOperator.equals("46009")) {
                                        if (!simOperator.equals("46003") && !simOperator.equals("46005")) {
                                            if (!simOperator.equals("46011")) {
                                                String simOperatorName2 = telephonyManager.getSimOperatorName();
                                                if (TextUtils.isEmpty(simOperatorName2)) {
                                                    mCurrentNetworkOperator = "Unknown";
                                                    return;
                                                } else {
                                                    mCurrentNetworkOperator = simOperatorName2;
                                                    return;
                                                }
                                            }
                                        }
                                        mCurrentNetworkOperator = NETWORK_OPERATOR_TELECOM;
                                        return;
                                    }
                                }
                                mCurrentNetworkOperator = NETWORK_OPERATOR_UNICOM;
                                return;
                            }
                        }
                        mCurrentNetworkOperator = NETWORK_OPERATOR_MOBILE;
                        return;
                    }
                }
                mCurrentNetworkOperator = "Unknown";
            } catch (Exception e) {
                Logger.e(TAG, e, new Object[0]);
            }
        }
    }
}
