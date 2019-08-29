package anet.channel.status;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.util.Pair;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public final class NetworkStatusHelper {
    static CopyOnWriteArraySet<a> a = new CopyOnWriteArraySet<>();

    public enum NetworkStatus {
        NONE,
        NO,
        G2,
        G3,
        G4,
        WIFI;

        public final boolean isMobile() {
            return this == G2 || this == G3 || this == G4;
        }

        public final boolean isWifi() {
            return this == WIFI;
        }

        public final String getType() {
            if (this == G2) {
                return "2G";
            }
            if (this == G3) {
                return "3G";
            }
            if (this == G4) {
                return "4G";
            }
            return toString();
        }
    }

    public interface a {
        void a(NetworkStatus networkStatus);
    }

    public static synchronized void a(Context context) {
        synchronized (NetworkStatusHelper.class) {
            if (context == null) {
                try {
                    throw new NullPointerException("context is null");
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                bl.a = context;
                bl.a();
                bl.b();
            }
        }
    }

    public static void a(a aVar) {
        a.add(aVar);
    }

    public static void b(a aVar) {
        a.remove(aVar);
    }

    public static void a(final NetworkStatus networkStatus) {
        ck.a(new Runnable() {
            public final void run() {
                try {
                    Iterator<a> it = NetworkStatusHelper.a.iterator();
                    while (it.hasNext()) {
                        a next = it.next();
                        long currentTimeMillis = System.currentTimeMillis();
                        next.a(networkStatus);
                        if (System.currentTimeMillis() - currentTimeMillis > 500) {
                            cl.d("awcn.NetworkStatusHelper", "call back cost too much time", null, "listener", next);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public static NetworkStatus a() {
        return bl.c;
    }

    public static String b() {
        return bl.d;
    }

    public static String c() {
        return bl.e;
    }

    public static String d() {
        return bl.h;
    }

    public static String e() {
        return bl.i;
    }

    public static boolean f() {
        return bl.k;
    }

    public static String g() {
        return bl.g;
    }

    public static boolean h() {
        if (VERSION.SDK_INT >= 24) {
            if (bl.b) {
                return true;
            }
        } else if (bl.c != NetworkStatus.NO) {
            return true;
        }
        try {
            NetworkInfo d = bl.d();
            if (d == null || !d.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean i() {
        NetworkStatus networkStatus = bl.c;
        return (networkStatus == NetworkStatus.WIFI && k() != null) || (networkStatus.isMobile() && (bl.e.contains("wap") || cv.a() != null));
    }

    public static String j() {
        NetworkStatus networkStatus = bl.c;
        if (networkStatus == NetworkStatus.WIFI && k() != null) {
            return CommonUtils.APN_PROP_PROXY;
        }
        if (!networkStatus.isMobile() || !bl.e.contains("wap")) {
            return (!networkStatus.isMobile() || cv.a() == null) ? "" : "auth";
        }
        return "wap";
    }

    public static Pair<String, Integer> k() {
        if (bl.c != NetworkStatus.WIFI) {
            return null;
        }
        return bl.j;
    }

    public static void l() {
        try {
            NetworkStatus networkStatus = bl.c;
            StringBuilder sb = new StringBuilder(128);
            sb.append("\nNetwork detail*******************************\n");
            sb.append("Status: ");
            sb.append(networkStatus.getType());
            sb.append(10);
            sb.append("Subtype: ");
            sb.append(bl.d);
            sb.append(10);
            if (networkStatus != NetworkStatus.NO) {
                if (networkStatus.isMobile()) {
                    sb.append("Apn: ");
                    sb.append(bl.e);
                    sb.append(10);
                    sb.append("Carrier: ");
                    sb.append(bl.h);
                    sb.append(10);
                } else {
                    sb.append("BSSID: ");
                    sb.append(bl.g);
                    sb.append(10);
                    sb.append("SSID: ");
                    sb.append(bl.f);
                    sb.append(10);
                }
            }
            if (i()) {
                sb.append("Proxy: ");
                sb.append(j());
                sb.append(10);
                Pair<String, Integer> k = k();
                if (k != null) {
                    sb.append("ProxyHost: ");
                    sb.append((String) k.first);
                    sb.append(10);
                    sb.append("ProxyPort: ");
                    sb.append(k.second);
                    sb.append(10);
                }
            }
            sb.append("*********************************************");
            cl.b("awcn.NetworkStatusHelper", sb.toString(), null, new Object[0]);
        } catch (Exception unused) {
        }
    }
}
