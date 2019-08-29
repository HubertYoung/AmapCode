package com.alibaba.analytics.core.network;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.analytics.utils.UTMCDevice;
import java.net.NetworkInterface;

public class NetworkUtil {
    public static final String NETWORK_CLASS_2_G = "2G";
    public static final String NETWORK_CLASS_3_G = "3G";
    public static final String NETWORK_CLASS_4_G = "4G";
    public static final String NETWORK_CLASS_UNKNOWN = "Unknown";
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    private static final String WIFIADDRESS_UNKNOWN = "00:00:00:00:00:00";
    private static String[] arrayOfString = {"Unknown", "Unknown"};
    private static boolean mHaveNetworkStatus = false;
    /* access modifiers changed from: private */
    public static NetWorkStatusChecker netStatusChecker = new NetWorkStatusChecker();
    private static NetworkStatusReceiver netStatusReceiver = new NetworkStatusReceiver();

    static class NetWorkStatusChecker implements Runnable {
        private Context context;

        private NetWorkStatusChecker() {
        }

        public NetWorkStatusChecker setContext(Context context2) {
            this.context = context2;
            return this;
        }

        public void run() {
            if (this.context != null) {
                NetworkUtil.getNetworkStatus(this.context);
                NetworkOperatorUtil.updateNetworkOperatorName(this.context);
                UTMCDevice.updateUTMCDeviceNetworkStatus(this.context);
            }
        }
    }

    static class NetworkStatusReceiver extends BroadcastReceiver {
        private NetworkStatusReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            TaskExecutor.getInstance().submit(NetworkUtil.netStatusChecker.setContext(context));
        }
    }

    private static String getNetworkClass(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String getNetworkType() {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "Unknown";
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return "Unknown";
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "Unknown";
            }
            if (activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    return "Wi-Fi";
                }
                if (activeNetworkInfo.getType() == 0) {
                    return getNetworkClass(activeNetworkInfo.getSubtype());
                }
            }
            return "Unknown";
        } catch (Throwable unused) {
        }
    }

    public static boolean isConnectInternet(Context context) {
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null && context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.isConnected();
                    }
                    return false;
                }
            } catch (Exception unused) {
            }
        }
        return true;
    }

    public static String[] getNetworkState(Context context) {
        if (!mHaveNetworkStatus) {
            getNetworkStatus(context);
        }
        return arrayOfString;
    }

    public static String getAccess(Context context) {
        try {
            return getNetworkState(context)[0];
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    public static String getAccsssSubType(Context context) {
        try {
            String[] networkState = getNetworkState(context);
            return networkState[0].equals("2G/3G") ? networkState[1] : "Unknown";
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    public static String getWifiAddress(Context context) {
        if (VERSION.SDK_INT >= 23) {
            return getWifiMacID23();
        }
        return getWifiMacID22(context);
    }

    @TargetApi(23)
    private static String getWifiMacID23() {
        try {
            byte[] hardwareAddress = NetworkInterface.getByName("wlan0").getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < hardwareAddress.length) {
                Object[] objArr = new Object[2];
                objArr[0] = Byte.valueOf(hardwareAddress[i]);
                objArr[1] = i < hardwareAddress.length - 1 ? ":" : "";
                sb.append(String.format("%02X%s", objArr));
                i++;
            }
            return sb.toString();
        } catch (Exception unused) {
            return WIFIADDRESS_UNKNOWN;
        }
    }

    private static String getWifiMacID22(Context context) {
        if (context != null) {
            try {
                WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                if (connectionInfo == null) {
                    return WIFIADDRESS_UNKNOWN;
                }
                String macAddress = connectionInfo.getMacAddress();
                if (TextUtils.isEmpty(macAddress)) {
                    macAddress = WIFIADDRESS_UNKNOWN;
                }
                return macAddress;
            } catch (Throwable unused) {
            }
        }
        return WIFIADDRESS_UNKNOWN;
    }

    private static String convertIntToIp(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i & 255);
        sb.append(".");
        sb.append((i >> 8) & 255);
        sb.append(".");
        sb.append((i >> 16) & 255);
        sb.append(".");
        sb.append((i >> 24) & 255);
        return sb.toString();
    }

    @Deprecated
    public static String getWifiIpAddress(Context context) {
        if (context != null) {
            try {
                WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                if (connectionInfo != null) {
                    return convertIntToIp(connectionInfo.getIpAddress());
                }
                return null;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    @Deprecated
    public static boolean isWifi(Context context) {
        if (context != null) {
            try {
                if (getNetworkState(context)[0].equals("Wi-Fi")) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static void register(Context context) {
        if (context != null) {
            context.registerReceiver(netStatusReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            try {
                NetworkOperatorUtil.registerSIMCardChangedInHandler(context);
            } catch (Exception unused) {
            }
            TaskExecutor.getInstance().submit(netStatusChecker.setContext(context));
        }
    }

    public static void unRegister(Context context) {
        if (context != null && netStatusReceiver != null) {
            context.unregisterReceiver(netStatusReceiver);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0077, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0074 A[Catch:{ Exception -> 0x006c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void getNetworkStatus(android.content.Context r5) {
        /*
            java.lang.Class<com.alibaba.analytics.core.network.NetworkUtil> r0 = com.alibaba.analytics.core.network.NetworkUtil.class
            monitor-enter(r0)
            r1 = 1
            android.content.pm.PackageManager r2 = r5.getPackageManager()     // Catch:{ Exception -> 0x006c }
            java.lang.String r3 = "android.permission.ACCESS_NETWORK_STATE"
            java.lang.String r4 = r5.getPackageName()     // Catch:{ Exception -> 0x006c }
            int r2 = r2.checkPermission(r3, r4)     // Catch:{ Exception -> 0x006c }
            r3 = 0
            if (r2 == 0) goto L_0x001d
            java.lang.String[] r5 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Unknown"
            r5[r3] = r2     // Catch:{ Exception -> 0x006c }
            monitor-exit(r0)
            return
        L_0x001d:
            java.lang.String r2 = "connectivity"
            java.lang.Object r5 = r5.getSystemService(r2)     // Catch:{ Exception -> 0x006c }
            android.net.ConnectivityManager r5 = (android.net.ConnectivityManager) r5     // Catch:{ Exception -> 0x006c }
            if (r5 != 0) goto L_0x002f
            java.lang.String[] r5 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Unknown"
            r5[r3] = r2     // Catch:{ Exception -> 0x006c }
            monitor-exit(r0)
            return
        L_0x002f:
            android.net.NetworkInfo r5 = r5.getActiveNetworkInfo()     // Catch:{ Exception -> 0x006c }
            if (r5 == 0) goto L_0x005d
            boolean r2 = r5.isConnected()     // Catch:{ Exception -> 0x006c }
            if (r2 == 0) goto L_0x005d
            int r2 = r5.getType()     // Catch:{ Exception -> 0x006c }
            if (r1 != r2) goto L_0x0048
            java.lang.String[] r5 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Wi-Fi"
            r5[r3] = r2     // Catch:{ Exception -> 0x006c }
            goto L_0x0070
        L_0x0048:
            int r2 = r5.getType()     // Catch:{ Exception -> 0x006c }
            if (r2 != 0) goto L_0x0070
            java.lang.String[] r2 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r4 = "2G/3G"
            r2[r3] = r4     // Catch:{ Exception -> 0x006c }
            java.lang.String[] r2 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r5 = r5.getSubtypeName()     // Catch:{ Exception -> 0x006c }
            r2[r1] = r5     // Catch:{ Exception -> 0x006c }
            goto L_0x0070
        L_0x005d:
            java.lang.String[] r5 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Unknown"
            r5[r3] = r2     // Catch:{ Exception -> 0x006c }
            java.lang.String[] r5 = arrayOfString     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Unknown"
            r5[r1] = r2     // Catch:{ Exception -> 0x006c }
            goto L_0x0070
        L_0x006a:
            r5 = move-exception
            goto L_0x0078
        L_0x006c:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x006a }
        L_0x0070:
            boolean r5 = mHaveNetworkStatus     // Catch:{ all -> 0x006a }
            if (r5 != 0) goto L_0x0076
            mHaveNetworkStatus = r1     // Catch:{ all -> 0x006a }
        L_0x0076:
            monitor-exit(r0)
            return
        L_0x0078:
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.network.NetworkUtil.getNetworkStatus(android.content.Context):void");
    }
}
