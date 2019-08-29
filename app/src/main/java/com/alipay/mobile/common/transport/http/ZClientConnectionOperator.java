package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.httpdns.HttpDns;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.SocketUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class ZClientConnectionOperator extends DefaultClientConnectionOperator {
    public static byte DEFAULT_MIN_CONNECT_COUNT = 4;
    private static final PlainSocketFactory a = new PlainSocketFactory();
    private byte b = 0;
    private byte c = 1;
    private List<String> d;

    public ZClientConnectionOperator(SchemeRegistry schemes) {
        super(schemes);
    }

    public void openConnection(OperatedClientConnection conn, HttpHost target, InetAddress local, HttpContext context, HttpParams params) {
        if (this.c == 2) {
            try {
                ZClientConnectionOperator.super.openConnection(conn, target, local, context, params);
                this.c = 2;
                try {
                    a(context).getCurrentDataContainer().putDataItem(RPCDataItems.ORIGHC, "T");
                } catch (Exception e) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "putDataItem1 exception : " + e.toString());
                }
            } catch (IOException e2) {
                if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", (String) "ZClientConnectionOperator. isNetworkAvailable == false ");
                    throw e2;
                }
                assertShutdown(e2);
                byte b2 = (byte) (this.b + 1);
                this.b = b2;
                if (b2 < 5) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "ZClientConnectionOperator. orig err count : " + this.b);
                    throw e2;
                }
                this.b = 0;
                LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator. use cust retry!");
                openConnectionCustome(conn, target, local, context, params);
                this.c = 1;
            }
        } else {
            try {
                openConnectionCustome(conn, target, local, context, params);
                this.c = 1;
            } catch (IOException e3) {
                if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", (String) "ZClientConnectionOperator. isNetworkAvailable == false ");
                    throw e3;
                }
                assertShutdown(e3);
                byte b3 = (byte) (this.b + 1);
                this.b = b3;
                if (b3 < 5) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "ZClientConnectionOperator. cust err count : " + this.b);
                    throw e3;
                }
                this.b = 0;
                LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator. use origin retry!");
                ZClientConnectionOperator.super.openConnection(conn, target, local, context, params);
                this.c = 2;
                try {
                    a(context).getCurrentDataContainer().putDataItem(RPCDataItems.ORIGHC, "T");
                } catch (Exception e1) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "putDataItem exception : " + e1.toString());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void assertShutdown(IOException e) {
        if (e != null && !TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("shutdown")) {
            LogCatUtil.info("ClientConnectionOperator", "Connection shutdown, don't retry!");
            throw e;
        }
    }

    public void openConnectionCustome(OperatedClientConnection conn, HttpHost target, InetAddress local, HttpContext context, HttpParams params) {
        SocketFactory plain_sf;
        LayeredSocketFactory layered_sf;
        InetAddress[] addresses;
        int connRetryCount;
        InetAddress[] inetAddresses;
        InetAddress[] allByNameByLocalDNS;
        InetAddress[] allByNameFromInetAddr;
        if (conn == null) {
            throw new IllegalArgumentException("Connection must not be null.");
        } else if (target == null) {
            throw new IllegalArgumentException("Target host must not be null.");
        } else if (params == null) {
            throw new IllegalArgumentException("Parameters must not be null.");
        } else if (conn.isOpen()) {
            throw new IllegalArgumentException("Connection must not be open.");
        } else {
            Scheme schm = this.schemeRegistry.getScheme(target.getSchemeName());
            SocketFactory sf = schm.getSocketFactory();
            if (sf instanceof LayeredSocketFactory) {
                plain_sf = a;
                layered_sf = (LayeredSocketFactory) sf;
            } else {
                plain_sf = sf;
                layered_sf = null;
            }
            addresses = DnsUtil.isLogicIP(target.getHostName()) ? new InetAddress[]{InetAddress.getByAddress(DnsUtil.ipToBytesByReg(target.getHostName()))} : a(target, context);
            connRetryCount = 0;
            while (true) {
                try {
                    connect(conn, target, local, context, params, schm, sf, plain_sf, layered_sf, addresses);
                    return;
                } catch (IOException e) {
                    LogCatUtil.debug("ClientConnectionOperator", "openConnectionCustome ex=[" + e.toString() + "]");
                    if (connRetryCount >= 4) {
                        LogCatUtil.debug("ClientConnectionOperator", "connRetryCount>=4, Don't retry");
                        throw e;
                    } else if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                        LogCatUtil.debug("ClientConnectionOperator", "Network not available, Don't retry");
                        throw e;
                    } else if (DnsUtil.isLogicIP(target.getHostName())) {
                        LogCatUtil.debug("ClientConnectionOperator", "Logic ip, Don't retry");
                        throw e;
                    } else if (TransportContextThreadLocalUtils.isFromLocalDns()) {
                        LogCatUtil.debug("ClientConnectionOperator", "Local dns, Don't retry");
                        throw e;
                    } else {
                        assertShutdown(e);
                        if (TransportContextThreadLocalUtils.isFromHttpDns()) {
                            LogCatUtil.debug("ClientConnectionOperator", "openConnectionCustome ex,remove ips in httpdns,try iprank");
                            AlipayHttpDnsClient dnsClient = AlipayHttpDnsClient.getDnsClient();
                            if (dnsClient != null) {
                                dnsClient.setErrorByHost(target.getHostName());
                            }
                            allByNameFromInetAddr = null;
                            allByNameFromInetAddr = AlipayDNSHelper.getInstance().getAllByName(target.getHostName(), a(context));
                        } else if (TransportContextThreadLocalUtils.isFromIpRank()) {
                            LogCatUtil.debug("ClientConnectionOperator", "openConnectionCustome ex,remove ips in iprank,try localdns");
                            AlipayDNSHelper.getInstance().removeIpsInIpRank(target.getHostName());
                            allByNameByLocalDNS = null;
                            allByNameByLocalDNS = AlipayDNSHelper.getInstance().getAllByNameByLocalDNS(target.getHostName());
                        } else if (!TransportContextThreadLocalUtils.isFromLocalCacheDns() || a().contains(RPCDataItems.VALUE_DT_LOCAL_CACHE_DNS)) {
                            throw e;
                        } else {
                            LogCatUtil.debug("ClientConnectionOperator", "openConnectionCustome exception,remove ips in local cache,try localdns");
                            HttpDns.getInstance().getGetAllByNameHelper().removeCache(target.getHostName());
                            inetAddresses = null;
                            inetAddresses = HttpDns.getInstance().getGetAllByNameHelper().getAllByName(target.getHostName());
                        }
                        connRetryCount++;
                    }
                } catch (Throwable e1) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "getGetAllByNameHelper#getAllByName error: " + e1.toString());
                }
            }
            throw e;
        }
        if (allByNameFromInetAddr == null || allByNameFromInetAddr.length == 0) {
            throw e;
        }
        addresses = allByNameFromInetAddr;
        connRetryCount++;
        if (inetAddresses == null || inetAddresses.length <= 0) {
            throw e;
        }
        a().add(RPCDataItems.VALUE_DT_LOCAL_CACHE_DNS);
        addresses = inetAddresses;
        connRetryCount++;
        if (allByNameByLocalDNS == null || allByNameByLocalDNS.length == 0) {
            throw e;
        }
        addresses = allByNameByLocalDNS;
        connRetryCount++;
    }

    public void connect(OperatedClientConnection conn, HttpHost target, InetAddress local, HttpContext context, HttpParams params, Scheme schm, SocketFactory sf, SocketFactory plain_sf, LayeredSocketFactory layered_sf, InetAddress[] addresses) {
        Throwable exception;
        ConnectException cause;
        TransportContext transportContext;
        long startTime;
        int maxRetryCount = Math.max(DEFAULT_MIN_CONNECT_COUNT, addresses.length);
        int i = 0;
        int indexAddr = 0;
        while (i < maxRetryCount) {
            if (indexAddr >= addresses.length) {
                indexAddr = 0;
            }
            InetAddress currAddress = addresses[indexAddr];
            Socket sock = plain_sf.createSocket();
            conn.opening(sock, target);
            try {
                transportContext = a(context);
                startTime = System.currentTimeMillis();
                String hostAddress = currAddress.getHostAddress();
                int port = schm.resolvePort(target.getPort());
                if (transportContext != null) {
                    transportContext.getCurrentDataContainer().putDataItem("TARGET_HOST", hostAddress + ":" + port);
                }
                Socket connsock = plain_sf.connectSocket(sock, hostAddress, port, local, 0, params);
                if (sock != connsock) {
                    sock = connsock;
                    conn.opening(sock, target);
                }
                long tcpTime = System.currentTimeMillis() - startTime;
                if (transportContext != null) {
                    transportContext.getCurrentDataContainer().putDataItem("TCP_TIME", String.valueOf(tcpTime));
                }
                LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator tcp connect success. host=[" + currAddress.getHostAddress() + "]");
                prepareSocket(sock, context, params);
                if (layered_sf != null) {
                    if (transportContext != null) {
                        transportContext.getCurrentDataContainer().timeItemDot("SSL_TIME");
                    }
                    Socket layeredsock = layered_sf.createSocket(sock, target.getHostName(), schm.resolvePort(target.getPort()), true);
                    LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator ssl connect success. host=[" + currAddress.getHostAddress() + "]");
                    a(target, currAddress, true, (int) tcpTime);
                    if (transportContext != null) {
                        transportContext.getCurrentDataContainer().timeItemRelease("SSL_TIME");
                    }
                    if (layeredsock != sock) {
                        conn.opening(layeredsock, target);
                    }
                    conn.openCompleted(sf.isSecure(layeredsock), params);
                    return;
                }
                if (!DnsUtil.isLogicIP(target.getHostName())) {
                    a(target, currAddress, true, (int) tcpTime);
                }
                conn.openCompleted(sf.isSecure(sock), params);
                return;
            } catch (SocketException ex) {
                Throwable exception2 = ex;
                try {
                    exception = exception2;
                    a(target, currAddress, false, -1);
                    if (i == maxRetryCount - 1) {
                        if (ex instanceof ConnectException) {
                            cause = (ConnectException) ex;
                        } else {
                            cause = new ConnectException(ex.getMessage());
                            cause.initCause(ex);
                        }
                        throw new HttpHostConnectException(target, cause);
                    }
                    LogCatUtil.warn((String) "ClientConnectionOperator", "Connect " + currAddress.getHostAddress() + ":" + target.getPort() + " exception: " + exception2.toString() + ", execute retry connect.");
                    assertShutdown((IOException) exception2);
                    i++;
                    indexAddr++;
                } catch (Throwable th) {
                    if (exception != 0) {
                        LogCatUtil.warn((String) "ClientConnectionOperator", "Connect " + currAddress.getHostAddress() + ":" + target.getPort() + " exception: " + exception.toString() + ", execute retry connect.");
                        if (exception instanceof IOException) {
                            assertShutdown((IOException) exception);
                        }
                    }
                    throw th;
                }
            } catch (ConnectTimeoutException ex2) {
                Throwable exception3 = ex2;
                exception = exception3;
                a(target, currAddress, false, -1);
                if (i == maxRetryCount - 1) {
                    throw ex2;
                }
                LogCatUtil.warn((String) "ClientConnectionOperator", "Connect " + currAddress.getHostAddress() + ":" + target.getPort() + " exception: " + exception3.toString() + ", execute retry connect.");
                assertShutdown((IOException) exception3);
                i++;
                indexAddr++;
            } catch (SocketTimeoutException ex3) {
                Throwable exception4 = ex3;
                exception = exception4;
                a(target, currAddress, false, -1);
                if (i == maxRetryCount - 1) {
                    ConnectTimeoutException cause2 = new ConnectTimeoutException(ex3.getMessage());
                    cause2.initCause(ex3);
                    throw cause2;
                }
                LogCatUtil.warn((String) "ClientConnectionOperator", "Connect " + currAddress.getHostAddress() + ":" + target.getPort() + " exception: " + exception4.toString() + ", execute retry connect.");
                assertShutdown((IOException) exception4);
                i++;
                indexAddr++;
            } catch (Throwable e) {
                Throwable exception5 = e;
                exception = exception5;
                b(target, currAddress, false, -1);
                if (i != maxRetryCount - 1) {
                    LogCatUtil.warn((String) "ClientConnectionOperator", "Connect " + currAddress.getHostAddress() + ":" + target.getPort() + " exception: " + exception5.toString() + ", execute retry connect.");
                    if (exception5 instanceof IOException) {
                        assertShutdown((IOException) exception5);
                    }
                    i++;
                    indexAddr++;
                } else if (e instanceof IOException) {
                    throw ((IOException) e);
                } else {
                    InterruptedIOException interruptedIOException = new InterruptedIOException(e.toString());
                    interruptedIOException.initCause(e);
                    throw interruptedIOException;
                }
            }
        }
    }

    private static void a(HttpHost target, InetAddress address, boolean connectSuccess, int rtt) {
        if (!DnsUtil.isLogicIP(target.getHostName())) {
            b(target, address, connectSuccess, rtt);
        }
        AlipayQosService.getInstance().estimate(connectSuccess ? (double) rtt : 5000.0d, 2);
    }

    private static void b(HttpHost target, InetAddress address, boolean connectSuccess, int rtt) {
        try {
            AlipayDNSHelper.getInstance().feedback(target.getHostName(), address.getHostAddress(), connectSuccess, rtt);
            AlipayHttpDnsClient.getDnsClient().feedback(target.getHostName(), address.getHostAddress(), connectSuccess, rtt);
            if (!connectSuccess) {
                HttpDns.getInstance().getGetAllByNameHelper().removeCache(target.getHostName());
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ClientConnectionOperator", ex);
        }
    }

    private static TransportContext a(HttpContext context) {
        return (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT);
    }

    private static InetAddress[] a(HttpHost target, HttpContext httpContext) {
        InetAddress[] addresses;
        TransportContext transportContext = a(httpContext);
        if (transportContext != null) {
            transportContext.getCurrentDataContainer().timeItemDot("DNS_TIME");
        }
        try {
            AlipayHttpDnsClient dnsClient = AlipayHttpDnsClient.getDnsClient();
            if (dnsClient != null) {
                addresses = dnsClient.getAllByName(target.getHostName(), transportContext);
                if (addresses == null || addresses.length == 0) {
                    addresses = DnsUtil.getAllByName(target.getHostName());
                } else {
                    LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator addresses len = " + addresses.length + ",ips = [" + Arrays.toString(addresses) + "]");
                }
            } else {
                LogCatUtil.info("ClientConnectionOperator", "ZClientConnectionOperator dnsClient is null");
                addresses = DnsUtil.getAllByName(target.getHostName());
            }
            if (transportContext != null) {
                transportContext.getCurrentDataContainer().timeItemRelease("DNS_TIME");
            }
        } catch (UnknownHostException e) {
            LogCatUtil.error((String) "ClientConnectionOperator", "ZClientConnectionOperator ex:" + e.toString());
            throw e;
        } catch (Exception e2) {
            LogCatUtil.error("ClientConnectionOperator", "ZClientConnectionOperator Exception", e2);
            addresses = DnsUtil.getAllByName(target.getHostName());
            if (transportContext != null) {
                transportContext.getCurrentDataContainer().timeItemRelease("DNS_TIME");
            }
        } catch (Throwable th) {
            if (transportContext != null) {
                transportContext.getCurrentDataContainer().timeItemRelease("DNS_TIME");
            }
            throw th;
        }
        return addresses;
    }

    private List<String> a() {
        if (this.d == null) {
            this.d = new ArrayList(2);
        }
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void prepareSocket(Socket sock, HttpContext context, HttpParams params) {
        ZClientConnectionOperator.super.prepareSocket(sock, context, params);
        int soTimeout = HttpConnectionParams.getSoTimeout(params);
        if (soTimeout > 0) {
            LogCatUtil.info("ClientConnectionOperator", "setSndTimeOut result: " + SocketUtil.setSndTimeOut(sock, (long) soTimeout));
        }
    }

    public OperatedClientConnection createConnection() {
        try {
            return new ZDefaultClientConnection();
        } catch (Throwable e) {
            LogCatUtil.warn("ClientConnectionOperator", "[createConnection] Exception", e);
            return new DefaultClientConnection();
        }
    }
}
