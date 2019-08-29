package defpackage;

import android.text.TextUtils;
import anet.channel.statist.NetTypeStat;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* renamed from: ct reason: default package */
/* compiled from: Inet64Util */
public final class ct {
    static final byte[][] a = {new byte[]{-64, 0, 0, -86}, new byte[]{-64, 0, 0, -85}};
    static volatile String b;
    static cu c;
    static ConcurrentHashMap<String, cu> d = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, Integer> e = new ConcurrentHashMap<>();

    public static boolean a() {
        return false;
    }

    static {
        try {
            c = new cu((Inet6Address) InetAddress.getAllByName("64:ff9b::")[0], 96);
            b = b(NetworkStatusHelper.a());
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public static String b(NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String g = NetworkStatusHelper.g();
            if (TextUtils.isEmpty(g)) {
                g = "";
            }
            return "WIFI$".concat(String.valueOf(g));
        } else if (!networkStatus.isMobile()) {
            return "UnknownNetwork";
        } else {
            StringBuilder sb = new StringBuilder(networkStatus.getType());
            sb.append("$");
            sb.append(NetworkStatusHelper.c());
            return sb.toString();
        }
    }

    public static boolean b() {
        Integer num = e.get(b);
        return num != null && num.intValue() == 1;
    }

    public static int c() {
        Integer num = e.get(b);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static void d() {
        b = b(NetworkStatusHelper.a());
        if (e.putIfAbsent(b, Integer.valueOf(0)) == null) {
            int g = g();
            e.put(b, Integer.valueOf(g));
            final NetTypeStat netTypeStat = new NetTypeStat();
            netTypeStat.ipStackType = g;
            final String str = b;
            if (g == 2 || g == 3) {
                ck.a(new Runnable() {
                    public final void run() {
                        ck.a(new Runnable() {
                            public final void run() {
                                try {
                                    if (str.equals(ct.b(NetworkStatusHelper.a()))) {
                                        cl.d("awcn.Inet64Util", "startIpStackDetect double check", null, new Object[0]);
                                        int e = ct.g();
                                        if (netTypeStat.ipStackType != e) {
                                            ct.e.put(str, Integer.valueOf(e));
                                            netTypeStat.lastIpStackType = netTypeStat.ipStackType;
                                            netTypeStat.ipStackType = e;
                                        }
                                        if (e == 2 || e == 3) {
                                            cu f = ct.h();
                                            if (f != null) {
                                                ct.d.put(str, f);
                                                netTypeStat.nat64Prefix = f.toString();
                                            }
                                        }
                                        if (m.b()) {
                                            x.a().a((StatObject) netTypeStat);
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }, c.c);
                    }
                }, 1500, TimeUnit.MILLISECONDS);
                return;
            }
            if (m.b()) {
                x.a().a((StatObject) netTypeStat);
            }
        }
    }

    private static boolean a(InetAddress inetAddress) {
        return inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress();
    }

    /* access modifiers changed from: private */
    public static cu h() throws UnknownHostException {
        InetAddress inetAddress;
        boolean z;
        try {
            inetAddress = InetAddress.getByName("ipv4only.arpa");
        } catch (Exception unused) {
            inetAddress = null;
        }
        if (inetAddress instanceof Inet6Address) {
            StringBuilder sb = new StringBuilder("Resolved AAAA: ");
            sb.append(inetAddress.toString());
            cl.b("awcn.Inet64Util", sb.toString(), null, new Object[0]);
            byte[] address = inetAddress.getAddress();
            if (address.length != 16) {
                return null;
            }
            int i = 12;
            while (true) {
                z = true;
                if (i < 0) {
                    z = false;
                    break;
                }
                if ((address[i] & a[0][0]) != 0 && address[i + 1] == 0 && address[i + 2] == 0) {
                    int i2 = i + 3;
                    if (address[i2] == a[0][3] || address[i2] == a[1][3]) {
                        break;
                    }
                }
                i--;
            }
            if (z) {
                address[i + 3] = 0;
                address[i + 2] = 0;
                address[i + 1] = 0;
                address[i] = 0;
                return new cu(Inet6Address.getByAddress("ipv4only.arpa", address, 0), i * 8);
            }
        } else if (inetAddress instanceof Inet4Address) {
            StringBuilder sb2 = new StringBuilder("Resolved A: ");
            sb2.append(inetAddress.toString());
            cl.b("awcn.Inet64Util", sb2.toString(), null, new Object[0]);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static int g() {
        int i;
        int i2;
        try {
            TreeMap treeMap = new TreeMap();
            Iterator<T> it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                    String displayName = networkInterface.getDisplayName();
                    cl.b("awcn.Inet64Util", "find NetworkInterface:".concat(String.valueOf(displayName)), null, new Object[0]);
                    int i3 = 0;
                    for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                        InetAddress address2 = address.getAddress();
                        if (address2 instanceof Inet6Address) {
                            Inet6Address inet6Address = (Inet6Address) address2;
                            if (!a((InetAddress) inet6Address)) {
                                StringBuilder sb = new StringBuilder("Found IPv6 address:");
                                sb.append(inet6Address.toString());
                                cl.d("awcn.Inet64Util", sb.toString(), null, new Object[0]);
                                i3 |= 2;
                            }
                        } else if (address2 instanceof Inet4Address) {
                            Inet4Address inet4Address = (Inet4Address) address2;
                            if (!a((InetAddress) inet4Address) && !inet4Address.getHostAddress().startsWith("192.168.43.")) {
                                StringBuilder sb2 = new StringBuilder("Found IPv4 address:");
                                sb2.append(inet4Address.toString());
                                cl.d("awcn.Inet64Util", sb2.toString(), null, new Object[0]);
                                i3 |= 1;
                            }
                        }
                    }
                    if (i3 != 0) {
                        treeMap.put(displayName.toLowerCase(), Integer.valueOf(i3));
                    }
                }
            }
            if (treeMap.isEmpty()) {
                i = 0;
                cl.d("awcn.Inet64Util", "startIpStackDetect", null, "ip stack", Integer.valueOf(i));
                return i;
            }
            if (treeMap.size() == 1) {
                i = ((Integer) treeMap.firstEntry().getValue()).intValue();
            } else {
                String str = NetworkStatusHelper.a().isWifi() ? "wlan" : NetworkStatusHelper.a().isMobile() ? "rmnet" : null;
                if (str != null) {
                    Iterator it2 = treeMap.entrySet().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Entry entry = (Entry) it2.next();
                        if (((String) entry.getKey()).startsWith(str)) {
                            i2 = ((Integer) entry.getValue()).intValue();
                            break;
                        }
                    }
                    if (i2 == 2 && treeMap.containsKey("v4-wlan0")) {
                        i2 |= ((Integer) treeMap.remove("v4-wlan0")).intValue();
                    }
                    i = i2;
                }
                i2 = 0;
                i2 |= ((Integer) treeMap.remove("v4-wlan0")).intValue();
                i = i2;
            }
            cl.d("awcn.Inet64Util", "startIpStackDetect", null, "ip stack", Integer.valueOf(i));
            return i;
        } catch (Exception unused) {
        }
    }
}
