package mtopsdk.xstate.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import java.util.Locale;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.xstate.NetworkClassEnum;

public class NetworkStateReceiver extends BroadcastReceiver {
    static ConnectivityManager a = null;
    static WifiManager b = null;
    public static volatile String c = "";
    public static volatile String d = "";
    public static volatile String e = "unknown";

    public void onReceive(final Context context, Intent intent) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.NetworkStateReceiver", "[onReceive] networkStateReceiver onReceive");
        }
        ffy.a(new Runnable() {
            public final void run() {
                NetworkInfo networkInfo;
                WifiInfo wifiInfo;
                NetworkClassEnum networkClassEnum;
                String str;
                String str2;
                try {
                    Context context = context;
                    if (context != null) {
                        if (NetworkStateReceiver.a == null) {
                            NetworkStateReceiver.a = (ConnectivityManager) context.getSystemService("connectivity");
                        }
                        networkInfo = NetworkStateReceiver.a.getActiveNetworkInfo();
                        if (networkInfo != null) {
                            if (networkInfo.isConnected()) {
                                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                    StringBuilder sb = new StringBuilder("[updateNetworkStatus] NetworkInfo isConnected=");
                                    sb.append(networkInfo.isConnected());
                                    sb.append(", isAvailable=");
                                    sb.append(networkInfo.isAvailable());
                                    TBSdkLog.b("mtopsdk.NetworkStateReceiver", sb.toString());
                                }
                                int type = networkInfo.getType();
                                if (type == 0) {
                                    int subtype = networkInfo.getSubtype();
                                    switch (subtype) {
                                        case 1:
                                        case 2:
                                        case 4:
                                        case 7:
                                        case 11:
                                            networkClassEnum = NetworkClassEnum.NET_2G;
                                            break;
                                        case 3:
                                        case 5:
                                        case 6:
                                        case 8:
                                        case 9:
                                        case 10:
                                        case 12:
                                        case 14:
                                        case 15:
                                            networkClassEnum = NetworkClassEnum.NET_3G;
                                            break;
                                        case 13:
                                            networkClassEnum = NetworkClassEnum.NET_4G;
                                            break;
                                        default:
                                            networkClassEnum = NetworkClassEnum.NET_UNKONWN;
                                            break;
                                    }
                                    if (TBSdkLog.a(LogEnable.DebugEnable)) {
                                        StringBuilder sb2 = new StringBuilder("[updateNetworkStatus]Mobile network,");
                                        sb2.append(networkClassEnum.getNetClass());
                                        TBSdkLog.a((String) "mtopsdk.NetworkStateReceiver", sb2.toString());
                                    }
                                    String extraInfo = networkInfo.getExtraInfo();
                                    if (!TextUtils.isEmpty(extraInfo)) {
                                        String lowerCase = extraInfo.toLowerCase(Locale.US);
                                        str = lowerCase.contains("cmwap") ? "cmwap" : lowerCase.contains("uniwap") ? "uniwap" : lowerCase.contains("3gwap") ? "3gwap" : lowerCase.contains("ctwap") ? "ctwap" : lowerCase.contains("cmnet") ? "cmnet" : lowerCase.contains("uninet") ? "uninet" : lowerCase.contains("3gnet") ? "3gnet" : lowerCase.contains("ctnet") ? "ctnet" : "unknown";
                                    } else {
                                        str = "unknown";
                                    }
                                    NetworkStateReceiver.e = str;
                                    fgy.c("nq", networkClassEnum.getNetClass());
                                    switch (subtype) {
                                        case 1:
                                            str2 = "GPRS";
                                            break;
                                        case 2:
                                            str2 = "EDGE";
                                            break;
                                        case 3:
                                            str2 = "UMTS";
                                            break;
                                        case 4:
                                            str2 = "CDMA";
                                            break;
                                        case 5:
                                            str2 = "CDMA - EvDo rev. 0";
                                            break;
                                        case 6:
                                            str2 = "CDMA - EvDo rev. A";
                                            break;
                                        case 7:
                                            str2 = "CDMA - 1xRTT";
                                            break;
                                        case 8:
                                            str2 = "HSDPA";
                                            break;
                                        case 9:
                                            str2 = "HSUPA";
                                            break;
                                        case 10:
                                            str2 = "HSPA";
                                            break;
                                        case 11:
                                            str2 = "iDEN";
                                            break;
                                        case 12:
                                            str2 = "CDMA - EvDo rev. B";
                                            break;
                                        case 13:
                                            str2 = "LTE";
                                            break;
                                        case 14:
                                            str2 = "CDMA - eHRPD";
                                            break;
                                        case 15:
                                            str2 = "HSPA+";
                                            break;
                                        default:
                                            str2 = "UNKNOWN";
                                            break;
                                    }
                                    fgy.c("netType", str2);
                                    return;
                                }
                                if (type == 1) {
                                    try {
                                        if (NetworkStateReceiver.b == null) {
                                            NetworkStateReceiver.b = (WifiManager) context.getSystemService("wifi");
                                        }
                                        wifiInfo = NetworkStateReceiver.b.getConnectionInfo();
                                    } catch (Throwable th) {
                                        TBSdkLog.b((String) "mtopsdk.NetworkStateReceiver", (String) "[updateNetworkStatus]getWifiInfo error.", th);
                                        wifiInfo = null;
                                    }
                                    if (wifiInfo != null) {
                                        NetworkStateReceiver.d = wifiInfo.getBSSID();
                                        NetworkStateReceiver.c = wifiInfo.getSSID();
                                    }
                                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                        StringBuilder sb3 = new StringBuilder("[updateNetworkStatus]WIFI network.ssid= ");
                                        sb3.append(NetworkStateReceiver.c);
                                        sb3.append(", bssid=");
                                        sb3.append(NetworkStateReceiver.d);
                                        TBSdkLog.b("mtopsdk.NetworkStateReceiver", sb3.toString());
                                    }
                                    fgy.c("nq", NetworkClassEnum.NET_WIFI.getNetClass());
                                    fgy.c("netType", NetworkClassEnum.NET_WIFI.getNetClass());
                                }
                                return;
                            }
                        }
                        if (TBSdkLog.a(LogEnable.InfoEnable)) {
                            TBSdkLog.b("mtopsdk.NetworkStateReceiver", "[updateNetworkStatus]no network");
                        }
                        fgy.c("nq", NetworkClassEnum.NET_NO.getNetClass());
                        fgy.c("netType", NetworkClassEnum.NET_NO.getNetClass());
                    }
                } catch (Throwable th2) {
                    TBSdkLog.b((String) "mtopsdk.NetworkStateReceiver", (String) "[onReceive] updateNetworkStatus error", th2);
                }
            }
        });
    }
}
