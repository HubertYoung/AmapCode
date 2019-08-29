package com.alipay.mobile.security.stun;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class StunClient {
    private static int a = -1;
    private static boolean b = false;

    private static native String getMappedIpAddressNative(String str, String str2, int i);

    public static String a(String str, int i) {
        if (!b) {
            return "";
        }
        String a2 = a();
        if (str == null || str.length() == 0) {
            return "";
        }
        if (a2 == null || a2.length() <= 0 || a != 1) {
            return (a2 == null || a2.length() <= 0 || a != 2) ? "" : getMappedIpAddressNative("0.0.0.0", str, i);
        }
        return getMappedIpAddressNative(a2, str, i);
    }

    private static String a() {
        String str;
        String str2 = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            str = null;
            while (networkInterfaces.hasMoreElements()) {
                try {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    String name = nextElement.getName();
                    if (name != null && nextElement.isUp() && !name.startsWith("ppp") && !name.startsWith("p2p") && !name.startsWith("lo")) {
                        Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement2 = inetAddresses.nextElement();
                            if (!nextElement2.isLoopbackAddress() && (nextElement2 instanceof Inet4Address)) {
                                String str3 = nextElement2.getHostAddress().toString();
                                if (str3 != null && str3.length() > 0) {
                                    if (name != null && name.startsWith("rmnet")) {
                                        str2 = str3;
                                    } else if (name != null && name.startsWith("wlan")) {
                                        str = str3;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            str = null;
        }
        if (str != null && str.length() > 0) {
            a = 2;
            return str;
        } else if (str2 == null || str2.length() <= 0) {
            return "";
        } else {
            a = 1;
            return str2;
        }
    }

    static {
        try {
            System.loadLibrary("APSE_1.1.5");
            b = true;
        } catch (Throwable unused) {
            b = false;
        }
    }
}
