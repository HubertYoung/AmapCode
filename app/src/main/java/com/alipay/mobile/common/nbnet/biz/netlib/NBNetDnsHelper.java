package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public final class NBNetDnsHelper {
    private static Class a = null;

    public static final InetAddress[] a(NBNetRoute nbNetRoute) {
        return a(nbNetRoute.a());
    }

    public static final InetAddress[] a(String hostName) {
        InetAddress[] inetAddresses;
        if (!a()) {
            InetAddress[] inetAddresses2 = e(hostName);
            if (inetAddresses2 != null && inetAddresses2.length != 0) {
                return inetAddresses2;
            }
            throw new UnknownHostException("Host is unresolved: " + hostName);
        }
        if (DnsUtil.isLogicIP(hostName)) {
            inetAddresses = new InetAddress[]{InetAddress.getByAddress(DnsUtil.ipToBytesByReg(hostName))};
            TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
        } else {
            inetAddresses = c(hostName);
        }
        if (inetAddresses != null && inetAddresses.length > 0) {
            return inetAddresses;
        }
        throw new UnknownHostException("Host is unresolved: " + hostName);
    }

    private static boolean a() {
        if (a == null) {
            try {
                a = Class.forName("com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient");
                return true;
            } catch (ClassNotFoundException e) {
                a = Void.TYPE;
                return false;
            }
        } else if (a != Void.TYPE) {
            return true;
        } else {
            return false;
        }
    }

    private static final InetAddress[] c(String hostname) {
        try {
            InetAddress[] addresses = d(hostname);
            if (addresses != null) {
                return addresses;
            }
        } catch (UnknownHostException e) {
            throw e;
        } catch (Throwable e2) {
            LogCatUtil.error("NBNetDnsHelper", "getInetAddresses2 Exception", e2);
        }
        LogCatUtil.info("NBNetDnsHelper", "getInetAddresses2 dnsClient is null");
        return e(hostname);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.net.InetAddress[] d(java.lang.String r12) {
        /*
            r7 = 0
            r8 = 0
            com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient r2 = com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient.getDnsClient()
            if (r2 != 0) goto L_0x0009
        L_0x0008:
            return r7
        L_0x0009:
            java.util.LinkedHashSet r6 = new java.util.LinkedHashSet
            r6.<init>()
            java.net.InetAddress[] r0 = r2.getAllByName(r12)
            if (r0 == 0) goto L_0x0042
            int r9 = r0.length
            if (r9 <= 0) goto L_0x0042
            java.lang.String r9 = "NBNetDnsHelper"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "getAllByNameFromHttpDnsClient addressesFromHttpDns  hostname = "
            r10.<init>(r11)
            java.lang.StringBuilder r10 = r10.append(r12)
            java.lang.String r11 = ",  len = "
            java.lang.StringBuilder r10 = r10.append(r11)
            int r11 = r0.length
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r9, r10)
            int r10 = r0.length
            r9 = r8
        L_0x0038:
            if (r9 >= r10) goto L_0x0042
            r5 = r0[r9]
            r6.add(r5)
            int r9 = r9 + 1
            goto L_0x0038
        L_0x0042:
            com.alipay.mobile.common.transport.httpdns.HttpDns r9 = com.alipay.mobile.common.transport.httpdns.HttpDns.getInstance()     // Catch:{ Throwable -> 0x0093 }
            com.alipay.mobile.common.transport.httpdns.HttpDns$GetAllByNameHelper r4 = r9.getGetAllByNameHelper()     // Catch:{ Throwable -> 0x0093 }
            java.net.InetAddress[] r1 = r4.getCache(r12)     // Catch:{ Throwable -> 0x0093 }
            if (r1 == 0) goto L_0x0053
            int r9 = r1.length     // Catch:{ Throwable -> 0x0093 }
            if (r9 > 0) goto L_0x0069
        L_0x0053:
            r4.asyncLocalDns2Cache(r12)     // Catch:{ Throwable -> 0x0093 }
        L_0x0056:
            int r8 = r6.size()
            if (r8 <= 0) goto L_0x0008
            int r7 = r6.size()
            java.net.InetAddress[] r7 = new java.net.InetAddress[r7]
            java.lang.Object[] r7 = r6.toArray(r7)
            java.net.InetAddress[] r7 = (java.net.InetAddress[]) r7
            goto L_0x0008
        L_0x0069:
            java.lang.String r9 = "NBNetDnsHelper"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0093 }
            java.lang.String r11 = "getAllByNameFromHttpDnsClient addressesFromLocalDnsCache  hostname = "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x0093 }
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ Throwable -> 0x0093 }
            java.lang.String r11 = ", len = "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0093 }
            int r11 = r1.length     // Catch:{ Throwable -> 0x0093 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0093 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0093 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r9, r10)     // Catch:{ Throwable -> 0x0093 }
            int r9 = r1.length     // Catch:{ Throwable -> 0x0093 }
        L_0x0089:
            if (r8 >= r9) goto L_0x0056
            r5 = r1[r8]     // Catch:{ Throwable -> 0x0093 }
            r6.add(r5)     // Catch:{ Throwable -> 0x0093 }
            int r8 = r8 + 1
            goto L_0x0089
        L_0x0093:
            r3 = move-exception
            java.lang.String r8 = "NBNetDnsHelper"
            java.lang.String r9 = "getAllByNameFromHttpDnsClient error"
            com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat.a(r8, r9, r3)
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nbnet.biz.netlib.NBNetDnsHelper.d(java.lang.String):java.net.InetAddress[]");
    }

    public static void b(String host) {
        AlipayHttpDnsClient dnsClient = AlipayHttpDnsClient.getDnsClient();
        if (dnsClient != null) {
            dnsClient.setErrorByHost(host);
        }
    }

    private static InetAddress[] e(String hostname) {
        InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
        if (inetAddresses != null && inetAddresses.length > 0) {
            TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
        }
        return inetAddresses;
    }

    public static InetAddress[] a(String host, InetAddress[] oldInetAddresses) {
        InetAddress[] newAddresses = e(host);
        if (newAddresses == null || newAddresses.length <= 0) {
            return null;
        }
        return a(oldInetAddresses, newAddresses);
    }

    public static InetAddress[] b(String host, InetAddress[] oldInetAddresses) {
        InetAddress[] rankAddresses = AlipayDNSHelper.getInstance().getAllByName(host);
        if (rankAddresses == null || rankAddresses.length <= 0) {
            return null;
        }
        return a(oldInetAddresses, rankAddresses);
    }

    private static InetAddress[] a(InetAddress[] oldInetAddresses, InetAddress[] newAddresses) {
        List diffInetAddressList = new ArrayList(oldInetAddresses.length);
        for (InetAddress rankInetAddress : newAddresses) {
            boolean contain = false;
            int length = oldInetAddresses.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (rankInetAddress.equals(oldInetAddresses[i])) {
                    contain = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!contain) {
                diffInetAddressList.add(rankInetAddress);
            }
        }
        if (diffInetAddressList.size() > 0) {
            return (InetAddress[]) diffInetAddressList.toArray(new InetAddress[diffInetAddressList.size()]);
        }
        return null;
    }
}
