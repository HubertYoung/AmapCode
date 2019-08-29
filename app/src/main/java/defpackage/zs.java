package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import anetwork.channel.statist.StatisticData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.amap.app.AMapAppGlobal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: zs reason: default package */
/* compiled from: RequestInfoBuilder */
public final class zs {
    bpk a;
    bph b;
    bpp c;
    StatisticData d;
    zo e;
    private Map<String, String> f = new HashMap();

    zs(Context context, bpk bpk) {
        this.a = bpk;
        if (context != null) {
            try {
                this.e = new zo((TelephonyManager) context.getSystemService("phone"));
                if (this.e != null) {
                    zo zoVar = this.e;
                    if (zoVar.a != null) {
                        zoVar.a.start();
                    }
                }
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("Exception ");
                sb.append(e2.getMessage());
                AMapLog.e("ApmSiginalListener", sb.toString());
                e2.printStackTrace();
            }
        }
        Object obj = null;
        this.b = bpk != null ? bpk.getRequest() : null;
        if (this.b != null) {
            this.c = this.b.requestStatistics;
            this.f.putAll(NetworkParam.getNetworkParamMap(this.b.getUrl()));
            Map<String, String> params = this.b.getParams();
            if (params != null) {
                this.f.putAll(params);
            }
            Map<String, String> headers = this.b.getHeaders();
            if (headers != null) {
                this.f.putAll(headers);
            }
        }
        obj = this.c != null ? this.c.r : obj;
        if (obj instanceof StatisticData) {
            this.d = (StatisticData) obj;
        }
    }

    static String a() {
        try {
            Context a2 = aaf.a();
            if (a2 == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) a2.getSystemService("phone");
            return (telephonyManager == null || telephonyManager.getSimState() != 5) ? "" : telephonyManager.getNetworkOperatorName();
        } catch (Exception unused) {
            return "";
        }
    }

    static String b() {
        String str = "";
        if (TextUtils.isEmpty(str)) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            str = nextElement.getHostAddress();
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    @SuppressLint({"MissingPermission"})
    static String c() {
        try {
            Context a2 = aaf.a();
            if (!NetworkReachability.a() || a2 == null) {
                return "";
            }
            return ((WifiManager) a2.getApplicationContext().getSystemService("wifi")).getConnectionInfo().toString();
        } catch (Exception unused) {
            return "";
        }
    }

    /* access modifiers changed from: 0000 */
    public final String d() {
        String str = "";
        if (this.c != null) {
            str = String.valueOf(this.c.e);
        }
        return this.d != null ? String.valueOf(this.d.resultCode) : str;
    }

    /* access modifiers changed from: 0000 */
    public final String e() {
        return this.a == null ? "" : this.a.getResponseBodyString();
    }

    /* access modifiers changed from: 0000 */
    public final String f() {
        return this.b == null ? "" : this.b.getUrl();
    }

    /* access modifiers changed from: 0000 */
    public final String g() {
        if (this.d != null) {
            return String.valueOf(this.d.firstDataTime);
        }
        return "";
    }

    static String h() {
        switch (aaw.b(AMapAppGlobal.getApplication())) {
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            case 4:
                return "wifi";
            default:
                return "unknown";
        }
    }

    static String i() {
        StringBuilder sb = new StringBuilder();
        try {
            Context a2 = aaf.a();
            if (!NetworkReachability.a() && a2 != null) {
                TelephonyManager telephonyManager = (TelephonyManager) a2.getApplicationContext().getSystemService("phone");
                if (telephonyManager != null && VERSION.SDK_INT >= 17) {
                    List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
                    if (allCellInfo != null && allCellInfo.size() > 0) {
                        for (int i = 0; i < allCellInfo.size(); i++) {
                            CellInfo cellInfo = allCellInfo.get(i);
                            if (cellInfo != null) {
                                StringBuilder sb2 = new StringBuilder(" info ");
                                sb2.append(i);
                                sb2.append(" [");
                                sb2.append(cellInfo.toString());
                                sb2.append("]");
                                sb.append(sb2.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = this.f.get(str);
            if (str2 != null) {
                return str2;
            }
        }
        r3 = "";
        return "";
    }

    public final String j() {
        try {
            String[] split = f().split("&csid=");
            return split[split.length - 1].split("&")[0];
        } catch (Exception unused) {
            return "";
        }
    }

    public final String k() {
        if (this.c != null) {
            return this.c.a;
        }
        return "";
    }
}
