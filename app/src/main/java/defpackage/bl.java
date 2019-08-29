package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.status.NetworkStatusMonitor$2;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* renamed from: bl reason: default package */
/* compiled from: NetworkStatusMonitor */
public final class bl {
    public static volatile Context a = null;
    public static volatile boolean b = false;
    public static volatile NetworkStatus c = NetworkStatus.NONE;
    public static volatile String d = "unknown";
    public static volatile String e = "";
    public static volatile String f = "";
    public static volatile String g = "";
    public static volatile String h = "unknown";
    public static volatile String i = "";
    public static volatile Pair<String, Integer> j = null;
    public static volatile boolean k = false;
    static volatile List<InetAddress> l = Collections.EMPTY_LIST;
    private static String[] m = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
    private static volatile boolean n = false;
    private static ConnectivityManager o;
    private static TelephonyManager p;
    private static WifiManager q;
    private static SubscriptionManager r;
    private static Method s;
    private static BroadcastReceiver t = new NetworkStatusMonitor$2();

    public static void a() {
        if (!n && a != null) {
            synchronized (a) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                try {
                    a.registerReceiver(t, intentFilter);
                } catch (Exception unused) {
                    cl.d("awcn.NetworkStatusMonitor", "registerReceiver failed", null, new Object[0]);
                }
            }
            c();
            n = true;
        }
    }

    public static void b() {
        if (VERSION.SDK_INT >= 24) {
            NetworkInfo d2 = d();
            b = d2 != null && d2.isConnected();
            try {
                o.registerDefaultNetworkCallback(new NetworkCallback() {
                    public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                        super.onLinkPropertiesChanged(network, linkProperties);
                        bl.l = new ArrayList(linkProperties.getDnsServers());
                    }

                    public final void onLost(Network network) {
                        super.onLost(network);
                        cl.b("awcn.NetworkStatusMonitor", "network onLost", null, new Object[0]);
                        bl.b = false;
                    }

                    public final void onAvailable(Network network) {
                        super.onAvailable(network);
                        cl.b("awcn.NetworkStatusMonitor", "network onAvailable", null, new Object[0]);
                        bl.b = true;
                    }
                });
            } catch (Exception unused) {
                cl.e("awcn.NetworkStatusMonitor", "registerDefaultNetworkCallback exception", null, new Object[0]);
            }
        }
    }

    public static void c() {
        NetworkInfo networkInfo;
        boolean z;
        cl.a("awcn.NetworkStatusMonitor", "[checkNetworkStatus]", null, new Object[0]);
        NetworkStatus networkStatus = c;
        String str = e;
        String str2 = f;
        try {
            networkInfo = d();
            z = false;
        } catch (Exception unused) {
            try {
                cl.e("awcn.NetworkStatusMonitor", "getNetworkInfo exception", null, new Object[0]);
                a(NetworkStatus.NONE, (String) "unknown");
                networkInfo = null;
                z = true;
            } catch (Exception unused2) {
                cl.e("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, new Object[0]);
                return;
            }
        }
        if (!z) {
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    cl.b("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "info.isConnected", Boolean.valueOf(networkInfo.isConnected()), "info.isAvailable", Boolean.valueOf(networkInfo.isAvailable()));
                    if (networkInfo.getType() == 0) {
                        String subtypeName = networkInfo.getSubtypeName();
                        String replace = !TextUtils.isEmpty(subtypeName) ? subtypeName.replace(Token.SEPARATOR, "") : "";
                        a(a(networkInfo.getSubtype(), replace), replace);
                        e = a(networkInfo.getExtraInfo());
                        e();
                    } else if (networkInfo.getType() == 1) {
                        a(NetworkStatus.WIFI, (String) "wifi");
                        WifiInfo f2 = f();
                        if (f2 != null) {
                            g = f2.getBSSID();
                            f = f2.getSSID();
                        }
                        h = "wifi";
                        i = "wifi";
                        j = g();
                    } else {
                        a(NetworkStatus.NONE, (String) "unknown");
                    }
                    k = networkInfo.isRoaming();
                    ct.d();
                }
            }
            a(NetworkStatus.NO, (String) "no network");
            cl.b("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "no network");
        }
        if (c != networkStatus || !e.equalsIgnoreCase(str) || !f.equalsIgnoreCase(str2)) {
            if (cl.a(2)) {
                NetworkStatusHelper.l();
            }
            NetworkStatusHelper.a(c);
        }
    }

    private static void a(NetworkStatus networkStatus, String str) {
        c = networkStatus;
        d = str;
        e = "";
        f = "";
        g = "";
        j = null;
        h = "";
        i = "";
    }

    private static NetworkStatus a(int i2, String str) {
        switch (i2) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkStatus.G2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkStatus.G3;
            case 13:
            case 18:
            case 19:
                return NetworkStatus.G4;
            default:
                if (str.equalsIgnoreCase("TD-SCDMA") || str.equalsIgnoreCase("WCDMA") || str.equalsIgnoreCase("CDMA2000")) {
                    return NetworkStatus.G3;
                }
                return NetworkStatus.NONE;
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "unknown";
        }
        String lowerCase = str.toLowerCase(Locale.US);
        if (lowerCase.contains("cmwap")) {
            return "cmwap";
        }
        if (lowerCase.contains("uniwap")) {
            return "uniwap";
        }
        if (lowerCase.contains("3gwap")) {
            return "3gwap";
        }
        if (lowerCase.contains("ctwap")) {
            return "ctwap";
        }
        if (lowerCase.contains("cmnet")) {
            return "cmnet";
        }
        if (lowerCase.contains("uninet")) {
            return "uninet";
        }
        if (lowerCase.contains("3gnet")) {
            return "3gnet";
        }
        return lowerCase.contains("ctnet") ? "ctnet" : "unknown";
    }

    private static void e() {
        try {
            if (p == null) {
                p = (TelephonyManager) a.getSystemService("phone");
            }
            i = p.getSimOperator();
            if (VERSION.SDK_INT >= 22) {
                if (r == null) {
                    SubscriptionManager from = SubscriptionManager.from(a);
                    r = from;
                    s = from.getClass().getDeclaredMethod("getDefaultDataSubscriptionInfo", new Class[0]);
                }
                if (s != null) {
                    h = ((SubscriptionInfo) s.invoke(r, new Object[0])).getCarrierName().toString();
                }
            }
        } catch (Exception unused) {
        }
    }

    public static NetworkInfo d() {
        if (o == null) {
            o = (ConnectivityManager) a.getSystemService("connectivity");
        }
        return o.getActiveNetworkInfo();
    }

    private static WifiInfo f() {
        try {
            if (q == null) {
                q = (WifiManager) a.getSystemService("wifi");
            }
            return q.getConnectionInfo();
        } catch (Throwable unused) {
            cl.e("awcn.NetworkStatusMonitor", "getWifiInfo", null, new Object[0]);
            return null;
        }
    }

    private static Pair<String, Integer> g() {
        try {
            String property = System.getProperty("http.proxyHost");
            if (!TextUtils.isEmpty(property)) {
                return Pair.create(property, Integer.valueOf(Integer.parseInt(System.getProperty("http.proxyPort"))));
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }
}
