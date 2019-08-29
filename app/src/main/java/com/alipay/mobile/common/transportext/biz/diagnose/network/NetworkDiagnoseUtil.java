package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

class NetworkDiagnoseUtil {
    private static final String CHARSET = "UTF-8";
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    public static final String FORMAT_HMS = "HH:mm:ss.S";
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";

    NetworkDiagnoseUtil() {
    }

    public static String getTime(String format) {
        String time = "";
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            return new SimpleDateFormat(format).format(new Date());
        } catch (Throwable th) {
            return time;
        }
    }

    public static String dns(String domain) {
        String ip = null;
        if (TextUtils.isEmpty(domain)) {
            return null;
        }
        try {
            ip = InetAddress.getByName(domain).getHostAddress();
        } catch (Throwable th) {
        }
        return ip;
    }

    public static byte[] convert(String src) {
        try {
            return src.getBytes("UTF-8");
        } catch (Throwable th) {
            return null;
        }
    }

    public static String convert(byte[] src) {
        try {
            return new String(src, "UTF-8");
        } catch (Throwable th) {
            return null;
        }
    }

    public static boolean isSafety(String name) {
        if (name == null || name.isEmpty() || name.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static int defaultPort(boolean isSsl) {
        return isSsl ? 443 : 80;
    }

    public static String parse(String domain) {
        String ip;
        if (!isSafety(domain)) {
            return null;
        }
        int pos = domain.lastIndexOf(58);
        if (pos == -1) {
            ip = domain.trim();
        } else {
            ip = domain.substring(0, pos).trim();
        }
        if (!isSafety(ip)) {
            return null;
        }
        return ip;
    }

    public static Address parse(String domain, boolean isSsl) {
        String ip;
        Address a = new Address();
        if (domain != null) {
            String port = null;
            int pos = domain.lastIndexOf(58);
            if (pos == -1) {
                ip = domain.trim();
            } else {
                ip = domain.substring(0, pos).trim();
                port = domain.substring(pos + 1).trim();
            }
            if (ip.isEmpty()) {
                a.ip = "127.0.0.1";
            } else {
                a.ip = ip;
            }
            if (port == null || port.isEmpty()) {
                a.port = defaultPort(isSsl);
            } else {
                try {
                    a.port = Integer.parseInt(port);
                    if (a.port < 0 || a.port > 65535) {
                        a.port = 0;
                    }
                } catch (Throwable th) {
                    if (port.compareToIgnoreCase("http") == 0) {
                        a.port = 80;
                    } else if (port.compareToIgnoreCase("https") == 0) {
                        a.port = 443;
                    } else {
                        a.port = 0;
                    }
                }
            }
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0048 A[Catch:{ Throwable -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0075 A[Catch:{ Throwable -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address sysProxy(com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address r14, boolean r15) {
        /*
            r13 = 10
            r6 = 0
            if (r15 == 0) goto L_0x0089
            java.lang.String r9 = "https://"
        L_0x0007:
            if (r14 == 0) goto L_0x008d
            java.lang.String r11 = r14.ip     // Catch:{ Throwable -> 0x00a1 }
            if (r11 == 0) goto L_0x008d
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a1 }
            r11.<init>()     // Catch:{ Throwable -> 0x00a1 }
            java.lang.StringBuilder r11 = r11.append(r9)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r12 = r14.ip     // Catch:{ Throwable -> 0x00a1 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r12 = ":"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x00a1 }
            int r12 = r14.port     // Catch:{ Throwable -> 0x00a1 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r9 = r11.toString()     // Catch:{ Throwable -> 0x00a1 }
        L_0x002c:
            java.net.URI r8 = new java.net.URI     // Catch:{ Throwable -> 0x00a1 }
            r8.<init>(r9)     // Catch:{ Throwable -> 0x00a1 }
            java.net.ProxySelector r11 = java.net.ProxySelector.getDefault()     // Catch:{ Throwable -> 0x00a1 }
            java.util.List r11 = r11.select(r8)     // Catch:{ Throwable -> 0x00a1 }
            java.util.Iterator r5 = r11.iterator()     // Catch:{ Throwable -> 0x00a1 }
            r11 = 10
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address[] r0 = new com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address[r11]     // Catch:{ Throwable -> 0x00a1 }
            r1 = 0
        L_0x0042:
            boolean r11 = r5.hasNext()     // Catch:{ Throwable -> 0x00a1 }
            if (r11 == 0) goto L_0x0073
            java.lang.Object r2 = r5.next()     // Catch:{ Throwable -> 0x00a1 }
            java.net.Proxy r2 = (java.net.Proxy) r2     // Catch:{ Throwable -> 0x00a1 }
            if (r2 == 0) goto L_0x0042
            java.net.SocketAddress r7 = r2.address()     // Catch:{ Throwable -> 0x00a1 }
            java.net.InetSocketAddress r7 = (java.net.InetSocketAddress) r7     // Catch:{ Throwable -> 0x00a1 }
            if (r7 == 0) goto L_0x0042
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address r11 = new com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address     // Catch:{ Throwable -> 0x00a1 }
            r11.<init>()     // Catch:{ Throwable -> 0x00a1 }
            r0[r1] = r11     // Catch:{ Throwable -> 0x00a1 }
            r11 = r0[r1]     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r12 = r7.getHostName()     // Catch:{ Throwable -> 0x00a1 }
            r11.ip = r12     // Catch:{ Throwable -> 0x00a1 }
            r11 = r0[r1]     // Catch:{ Throwable -> 0x00a1 }
            int r12 = r7.getPort()     // Catch:{ Throwable -> 0x00a1 }
            r11.port = r12     // Catch:{ Throwable -> 0x00a1 }
            int r1 = r1 + 1
            if (r1 != r13) goto L_0x0042
        L_0x0073:
            if (r1 == 0) goto L_0x0088
            long r3 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00a1 }
            r11 = 32
            long r11 = r3 >>> r11
            int r11 = (int) r11     // Catch:{ Throwable -> 0x00a1 }
            int r12 = (int) r3     // Catch:{ Throwable -> 0x00a1 }
            r10 = r11 ^ r12
            if (r10 >= 0) goto L_0x0084
            int r10 = -r10
        L_0x0084:
            int r11 = r10 % r1
            r6 = r0[r11]     // Catch:{ Throwable -> 0x00a1 }
        L_0x0088:
            return r6
        L_0x0089:
            java.lang.String r9 = "http://"
            goto L_0x0007
        L_0x008d:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a1 }
            r11.<init>()     // Catch:{ Throwable -> 0x00a1 }
            java.lang.StringBuilder r11 = r11.append(r9)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r12 = "www.taobao.com"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.String r9 = r11.toString()     // Catch:{ Throwable -> 0x00a1 }
            goto L_0x002c
        L_0x00a1:
            r11 = move-exception
            goto L_0x0088
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkDiagnoseUtil.sysProxy(com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address, boolean):com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address");
    }

    public static double nsToMs(long ns) {
        return ((double) ns) / 1000000.0d;
    }

    public static long sToMs(int s) {
        return ((long) s) * 1000;
    }

    public static long msToS(long ms) {
        return ms / 1000;
    }

    public static boolean digital(byte b) {
        return b >= 48 && b <= 57;
    }

    public static boolean blank(byte b) {
        return b == 32 || b == 9;
    }
}
