package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.DetectInf;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class Link {
    static final String TAG = "DIAGNOSE-LINK";
    private DiagnoseStateManager callback;
    private String describe;
    private DetectInf detectInf;
    public int errCode;
    private boolean isParameterCorrect;
    private boolean isSendBytes;
    private boolean isSsl;
    /* access modifiers changed from: private */
    public long mark;
    private String name;
    private int protocol;
    private Address proxy;
    private Address server;
    private long tmRtt;
    /* access modifiers changed from: private */
    public long tmSsl;
    private long tmTcp;

    class DftNetTest implements DiagnoseStateManager {
        private String result = null;

        DftNetTest() {
        }

        public void report(boolean fin, boolean ok, boolean done, String summary) {
            if (this.result == null) {
                this.result = summary;
            } else {
                this.result += MergeUtil.SEPARATOR_KV + summary;
            }
        }

        public void notify(String info) {
        }

        public String getReport() {
            return this.result;
        }
    }

    class MyHandshakeListener implements HandshakeCompletedListener {
        private MyHandshakeListener() {
        }

        public void handshakeCompleted(HandshakeCompletedEvent e) {
            Link.this.tmSsl = System.nanoTime() - Link.this.mark;
            LogCatUtil.info(Link.TAG, "[sslSocket]Handshake sucessfull. " + e.toString());
        }
    }

    class MyX509TrustManager implements X509TrustManager {
        private MyX509TrustManager() {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public Link(DetectInf detectInf2, DiagnoseStateManager callback2, Address proxy2) {
        this.errCode = -1;
        this.detectInf = null;
        this.server = null;
        this.proxy = null;
        this.callback = null;
        this.name = null;
        this.describe = "";
        this.isSsl = false;
        this.isSendBytes = false;
        this.isParameterCorrect = false;
        this.protocol = 0;
        this.mark = 0;
        this.tmTcp = 0;
        this.tmSsl = 0;
        this.tmRtt = 0;
        this.detectInf = detectInf2;
        this.callback = callback2;
        this.proxy = proxy2;
        this.isParameterCorrect = initialize();
    }

    private Link(String domain, boolean isSsl2, DiagnoseStateManager callback2, Address proxy2) {
        this.errCode = -1;
        this.detectInf = null;
        this.server = null;
        this.proxy = null;
        this.callback = null;
        this.name = null;
        this.describe = "";
        this.isSsl = false;
        this.isSendBytes = false;
        this.isParameterCorrect = false;
        this.protocol = 0;
        this.mark = 0;
        this.tmTcp = 0;
        this.tmSsl = 0;
        this.tmRtt = 0;
        this.isSendBytes = false;
        this.isSsl = isSsl2;
        this.callback = callback2;
        this.proxy = proxy2;
        this.isParameterCorrect = initialize(domain);
    }

    public static String diagnoseByLink(String domain, boolean isSsl2, Address proxy2) {
        DftNetTest callback2 = new DftNetTest();
        if (domain == null) {
            return null;
        }
        new Link(domain, isSsl2, callback2, proxy2).start();
        return callback2.getReport();
    }

    public boolean start() {
        if (!this.isParameterCorrect) {
            this.errCode = 1;
            finish(false, false);
            return false;
        } else if (!this.isSsl) {
            if (this.proxy == null) {
                return tcpLink(this.server.ip, this.server.port);
            }
            return tcpLinkByProxy(this.proxy.ip, this.proxy.port);
        } else if (this.proxy == null) {
            return sslLink();
        } else {
            return sslLinkByProxy();
        }
    }

    private boolean initialize() {
        if (this.detectInf == null) {
            LogCatUtil.warn((String) TAG, (String) "[initialize] input detectInf is null.");
            this.errCode = 1;
            return false;
        }
        this.isParameterCorrect = true;
        this.isSsl = 1 == this.detectInf.protocol;
        this.protocol = this.detectInf.protocol;
        this.server = NetworkDiagnoseUtil.parse(this.detectInf.ip == null ? this.detectInf.domain : this.detectInf.ip, this.isSsl);
        if (this.server == null) {
            LogCatUtil.warn((String) TAG, (String) "[initialize] server is null.");
            this.errCode = 1;
            return false;
        }
        if (this.detectInf.port != -1) {
            this.server.port = this.detectInf.port;
        }
        this.name = this.server.ip + ":" + this.server.port;
        if (this.proxy != null) {
            this.name += " through proxy-";
            this.name += this.proxy.ip + ":" + this.proxy.port;
            LogCatUtil.info(TAG, "[initialize] proxy. " + this.name);
        }
        this.isSendBytes = true;
        return true;
    }

    private boolean initialize(String domain) {
        int i = 0;
        if (domain == null) {
            LogCatUtil.warn((String) TAG, (String) "[initialize] domain is null.");
            this.errCode = 1;
            return false;
        }
        this.server = NetworkDiagnoseUtil.parse(domain, this.isSsl);
        if (this.server == null || this.server.ip == null) {
            LogCatUtil.warn((String) TAG, (String) "[initialize] server is null.");
            this.errCode = 1;
            return false;
        }
        this.name = this.server.ip + ":" + this.server.port;
        if (this.isSsl) {
            i = 1;
        }
        this.protocol = i;
        this.detectInf = new DetectInf();
        if (this.proxy != null) {
            this.name += " through proxy-";
            this.name += this.proxy.ip + ":" + this.proxy.port;
            LogCatUtil.info(TAG, "[initialize] proxy. " + this.name);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0106 A[SYNTHETIC, Splitter:B:40:0x0106] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean tcpLink(java.lang.String r12, int r13) {
        /*
            r11 = this;
            r5 = 0
            r6 = 1
            boolean r7 = com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkDiagnoseUtil.isSafety(r12)
            if (r7 != 0) goto L_0x0012
            java.lang.String r7 = "DIAGNOSE-LINK"
            java.lang.String r8 = "[tcpLink] input ip is not safety"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r7, r8)
            r11.errCode = r6
        L_0x0011:
            return r5
        L_0x0012:
            r3 = 0
            java.net.Socket r4 = new java.net.Socket     // Catch:{ Throwable -> 0x0127 }
            r4.<init>()     // Catch:{ Throwable -> 0x0127 }
            long r7 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r11.mark = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.net.InetSocketAddress r7 = new java.net.InetSocketAddress     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r7.<init>(r12, r13)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r8 = 15000(0x3a98, float:2.102E-41)
            r4.connect(r7, r8)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r7 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r9 = r11.mark     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r7 = r7 - r9
            r11.tmTcp = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r7 = 15000(0x3a98, float:2.102E-41)
            r4.setSoTimeout(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            boolean r7 = r11.isSendBytes     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            if (r7 == 0) goto L_0x0084
            long r7 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r11.mark = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$DetectInf r7 = r11.detectInf     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r7 = r7.request     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r2 = r11.sendAndReceive(r4, r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r7 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r9 = r11.mark     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            long r7 = r7 - r9
            r11.tmRtt = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r8 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r7 = "[tcpLink] response: "
            r9.<init>(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            if (r2 != 0) goto L_0x0082
            java.lang.String r7 = "rsp is null"
        L_0x005e:
            java.lang.StringBuilder r7 = r9.append(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$DetectInf r7 = r11.detectInf     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            java.lang.String r7 = r7.response     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            r8 = 1
            boolean r1 = r11.checkData(r7, r2, r8)     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
        L_0x0072:
            if (r1 == 0) goto L_0x0086
            r7 = 0
            r11.errCode = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
        L_0x0077:
            r4.close()     // Catch:{ Throwable -> 0x00e7 }
            r3 = r4
        L_0x007b:
            r11.finish(r6, r1)
            if (r1 == 0) goto L_0x0011
            r5 = r6
            goto L_0x0011
        L_0x0082:
            r7 = r2
            goto L_0x005e
        L_0x0084:
            r1 = 1
            goto L_0x0072
        L_0x0086:
            r7 = 2
            r11.errCode = r7     // Catch:{ Throwable -> 0x008a, all -> 0x0124 }
            goto L_0x0077
        L_0x008a:
            r0 = move-exception
            r3 = r4
        L_0x008c:
            java.lang.String r7 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0103 }
            java.lang.String r9 = "[tcpLink ex]"
            r8.<init>(r9)     // Catch:{ all -> 0x0103 }
            java.lang.String r9 = r0.toString()     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0103 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0103 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r7, r8)     // Catch:{ all -> 0x0103 }
            r1 = 0
            r7 = 3
            r11.errCode = r7     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0103 }
            r7.<init>()     // Catch:{ all -> 0x0103 }
            java.lang.String r8 = r11.describe     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0103 }
            java.lang.String r8 = " "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0103 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0103 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0103 }
            r11.describe = r7     // Catch:{ all -> 0x0103 }
            if (r3 == 0) goto L_0x007b
            r3.close()     // Catch:{ Throwable -> 0x00cd }
            goto L_0x007b
        L_0x00cd:
            r0 = move-exception
            java.lang.String r7 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "[tcpLink]close socket error. "
            r8.<init>(r9)
            java.lang.String r9 = r0.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r7, r8)
            goto L_0x007b
        L_0x00e7:
            r0 = move-exception
            java.lang.String r7 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "[tcpLink]close socket error. "
            r8.<init>(r9)
            java.lang.String r9 = r0.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r7, r8)
            r3 = r4
            goto L_0x007b
        L_0x0103:
            r5 = move-exception
        L_0x0104:
            if (r3 == 0) goto L_0x0109
            r3.close()     // Catch:{ Throwable -> 0x010a }
        L_0x0109:
            throw r5
        L_0x010a:
            r0 = move-exception
            java.lang.String r6 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "[tcpLink]close socket error. "
            r7.<init>(r8)
            java.lang.String r8 = r0.toString()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r6, r7)
            goto L_0x0109
        L_0x0124:
            r5 = move-exception
            r3 = r4
            goto L_0x0104
        L_0x0127:
            r0 = move-exception
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.Link.tcpLink(java.lang.String, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x014c A[SYNTHETIC, Splitter:B:49:0x014c] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0186  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean tcpLinkByProxy(java.lang.String r14, int r15) {
        /*
            r13 = this;
            boolean r9 = com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkDiagnoseUtil.isSafety(r14)
            if (r9 != 0) goto L_0x0012
            java.lang.String r9 = "DIAGNOSE-LINK"
            java.lang.String r10 = "[tcpLinkByProxy] input ip is not safety"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r9, r10)
            r9 = 1
            r13.errCode = r9
            r9 = 0
        L_0x0011:
            return r9
        L_0x0012:
            r7 = 0
            java.net.Socket r8 = new java.net.Socket     // Catch:{ Throwable -> 0x018b }
            r8.<init>()     // Catch:{ Throwable -> 0x018b }
            long r9 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r13.mark = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r2 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.net.InetSocketAddress r9 = new java.net.InetSocketAddress     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r9.<init>(r14, r15)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r10 = 15000(0x3a98, float:2.102E-41)
            r8.connect(r9, r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r9 = 15000(0x3a98, float:2.102E-41)
            r8.setSoTimeout(r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = "DIAGNOSE-LINK"
            java.lang.String r10 = "[tcpLinkByProxy] socket connected."
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r9, r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = "CONNECT "
            r9.<init>(r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address r10 = r13.server     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = r10.ip     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = ":"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$Address r10 = r13.server     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            int r10 = r10.port     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = " HTTP/1.1\r\n\r\n"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r4 = r9.toString()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r5 = "HTTP/1.1 200"
            java.lang.String r6 = r13.sendAndReceive(r8, r4)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r9 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r9 = r9 - r2
            r13.tmTcp = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = "[tcpLinkByProxy] response: "
            r11.<init>(r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            if (r6 != 0) goto L_0x00de
            java.lang.String r9 = "rsp is null"
        L_0x0079:
            java.lang.StringBuilder r9 = r11.append(r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r9 = 1
            boolean r1 = r13.checkData(r5, r6, r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            if (r1 == 0) goto L_0x00e0
            r9 = 0
            r13.errCode = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
        L_0x008e:
            if (r1 == 0) goto L_0x00d1
            boolean r9 = r13.isSendBytes     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            if (r9 == 0) goto L_0x00d1
            long r9 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r13.mark = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$DetectInf r9 = r13.detectInf     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = r9.request     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r6 = r13.sendAndReceive(r8, r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r9 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r11 = r13.mark     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            long r9 = r9 - r11
            r13.tmRtt = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r10 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = "[tcpLinkByProxy] response: "
            r11.<init>(r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            if (r6 != 0) goto L_0x0141
            java.lang.String r9 = "rsp is null"
        L_0x00b8:
            java.lang.StringBuilder r9 = r11.append(r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r9)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration$DetectInf r9 = r13.detectInf     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            java.lang.String r9 = r9.response     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            r10 = 1
            boolean r1 = r13.checkData(r9, r6, r10)     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            if (r1 == 0) goto L_0x0144
            r9 = 0
            r13.errCode = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
        L_0x00d1:
            r8.close()     // Catch:{ Throwable -> 0x0150 }
            r7 = r8
        L_0x00d5:
            r9 = 1
            r13.finish(r9, r1)
            if (r1 == 0) goto L_0x0186
            r9 = 1
            goto L_0x0011
        L_0x00de:
            r9 = r6
            goto L_0x0079
        L_0x00e0:
            r9 = 6
            r13.errCode = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            goto L_0x008e
        L_0x00e4:
            r0 = move-exception
            r7 = r8
        L_0x00e6:
            java.lang.String r9 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0189 }
            java.lang.String r11 = "[tcpLinkByProxy]"
            r10.<init>(r11)     // Catch:{ all -> 0x0189 }
            java.lang.String r11 = r0.toString()     // Catch:{ all -> 0x0189 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x0189 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0189 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r9, r10)     // Catch:{ all -> 0x0189 }
            r1 = 0
            r9 = 7
            r13.errCode = r9     // Catch:{ all -> 0x0189 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0189 }
            r9.<init>()     // Catch:{ all -> 0x0189 }
            java.lang.String r10 = r13.describe     // Catch:{ all -> 0x0189 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0189 }
            java.lang.String r10 = " "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0189 }
            java.lang.String r10 = r0.toString()     // Catch:{ all -> 0x0189 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0189 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0189 }
            r13.describe = r9     // Catch:{ all -> 0x0189 }
            if (r7 == 0) goto L_0x00d5
            r7.close()     // Catch:{ Throwable -> 0x0127 }
            goto L_0x00d5
        L_0x0127:
            r0 = move-exception
            java.lang.String r9 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "[tcpLinkByProxy]close socket error. "
            r10.<init>(r11)
            java.lang.String r11 = r0.toString()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r9, r10)
            goto L_0x00d5
        L_0x0141:
            r9 = r6
            goto L_0x00b8
        L_0x0144:
            r9 = 2
            r13.errCode = r9     // Catch:{ Throwable -> 0x00e4, all -> 0x0148 }
            goto L_0x00d1
        L_0x0148:
            r9 = move-exception
            r7 = r8
        L_0x014a:
            if (r7 == 0) goto L_0x014f
            r7.close()     // Catch:{ Throwable -> 0x016c }
        L_0x014f:
            throw r9
        L_0x0150:
            r0 = move-exception
            java.lang.String r9 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "[tcpLinkByProxy]close socket error. "
            r10.<init>(r11)
            java.lang.String r11 = r0.toString()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r9, r10)
            r7 = r8
            goto L_0x00d5
        L_0x016c:
            r0 = move-exception
            java.lang.String r10 = "DIAGNOSE-LINK"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "[tcpLinkByProxy]close socket error. "
            r11.<init>(r12)
            java.lang.String r12 = r0.toString()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r10, r11)
            goto L_0x014f
        L_0x0186:
            r9 = 0
            goto L_0x0011
        L_0x0189:
            r9 = move-exception
            goto L_0x014a
        L_0x018b:
            r0 = move-exception
            goto L_0x00e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.Link.tcpLinkByProxy(java.lang.String, int):boolean");
    }

    private boolean sslLink() {
        boolean eq;
        String str;
        SSLSocket socket = null;
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new TrustManager[]{new MyX509TrustManager()}, null);
            socket = (SSLSocket) context.getSocketFactory().createSocket();
            this.mark = System.nanoTime();
            socket.connect(new InetSocketAddress(this.server.ip, this.server.port), HttpConstants.CONNECTION_TIME_OUT);
            this.tmTcp = System.nanoTime() - this.mark;
            socket.setSoTimeout(HttpConstants.CONNECTION_TIME_OUT);
            LogCatUtil.info(TAG, "[sslLink] socket created.");
            socket.addHandshakeCompletedListener(new MyHandshakeListener());
            this.mark = System.nanoTime();
            socket.startHandshake();
            if (this.isSendBytes) {
                this.mark = System.nanoTime();
                String rsp = sendAndReceive(socket, this.detectInf.request);
                this.tmRtt = System.nanoTime() - this.mark;
                StringBuilder sb = new StringBuilder("[sslLink] response: ");
                if (rsp == null) {
                    str = "rsp is null";
                } else {
                    str = rsp;
                }
                LogCatUtil.info(TAG, sb.append(str).toString());
                eq = checkData(this.detectInf.response, rsp, true);
            } else {
                eq = true;
            }
            if (eq) {
                this.errCode = 0;
            } else {
                this.errCode = 2;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Throwable e) {
                    LogCatUtil.warn((String) TAG, "[sslLink]" + e.toString());
                }
            }
        } catch (Throwable e2) {
            LogCatUtil.warn((String) TAG, "[sslLink]" + e2.toString());
        }
        finish(true, eq);
        if (eq) {
            return true;
        }
        return false;
    }

    private boolean sslLinkByProxy() {
        boolean eq;
        String str;
        String str2;
        SSLSocket sslSocket = null;
        try {
            Socket socket = new Socket();
            long mark_tcp = System.nanoTime();
            socket.connect(new InetSocketAddress(this.proxy.ip, this.proxy.port), HttpConstants.CONNECTION_TIME_OUT);
            String rsp = sendAndReceive(socket, "CONNECT " + this.server.ip + ":" + this.server.port + " HTTP/1.1\r\n\r\n");
            this.tmTcp = System.nanoTime() - mark_tcp;
            StringBuilder sb = new StringBuilder("[sslLinkByProxy] connect response: ");
            if (rsp == null) {
                str = "rsp is null";
            } else {
                str = rsp;
            }
            LogCatUtil.info(TAG, sb.append(str).toString());
            eq = checkData("HTTP/1.1 200", rsp, true);
            if (eq) {
                LogCatUtil.info(TAG, "[sslLinkByProxy] connect by proxy sucessed. continue to ssl.");
                SSLContext context = SSLContext.getInstance("SSL");
                context.init(null, new TrustManager[]{new MyX509TrustManager()}, null);
                SSLSocketFactory factory = context.getSocketFactory();
                this.mark = System.nanoTime();
                sslSocket = (SSLSocket) factory.createSocket(socket, this.server.ip, this.server.port, true);
                sslSocket.addHandshakeCompletedListener(new MyHandshakeListener());
                sslSocket.startHandshake();
                if (this.isSendBytes) {
                    this.mark = System.nanoTime();
                    String rsp2 = sendAndReceive(sslSocket, this.detectInf.request);
                    this.tmRtt = System.nanoTime() - this.mark;
                    StringBuilder sb2 = new StringBuilder("[sslLinkByProxy] ssl response: ");
                    if (rsp2 == null) {
                        str2 = "rsp is null";
                    } else {
                        str2 = rsp2;
                    }
                    LogCatUtil.info(TAG, sb2.append(str2).toString());
                    eq = checkData(this.detectInf.response, rsp2, true);
                } else {
                    eq = true;
                }
                if (eq) {
                    this.errCode = 0;
                } else {
                    this.errCode = 2;
                }
            } else {
                LogCatUtil.info(TAG, "[sslLinkByProxy] connect by proxy failed.");
                this.errCode = 6;
            }
            if (sslSocket != null) {
                try {
                    sslSocket.close();
                } catch (Throwable e) {
                    LogCatUtil.warn((String) TAG, "[sslLinkByProxy] finally. close socket error. " + e.toString());
                }
            }
        } catch (Throwable e2) {
            LogCatUtil.warn((String) TAG, "[sslLinkByProxy] finally. close socket error. " + e2.toString());
        }
        finish(true, eq);
        if (eq) {
            return true;
        }
        return false;
    }

    private String sendAndReceive(Socket socket, String request) {
        if (socket == null || !NetworkDiagnoseUtil.isSafety(request)) {
            LogCatUtil.warn((String) TAG, (String) "[sendAndReceive] input is not available.");
            return null;
        }
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            LogCatUtil.info(TAG, "[sendAndReceive] send:" + request);
            out.print(request);
            out.flush();
            byte[] buf = new byte[512];
            int size = socket.getInputStream().read(buf);
            if (size > 0) {
                return new String(buf, 0, size);
            }
            return null;
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "[sendAndReceive]" + e.toString());
            return null;
        }
    }

    private boolean checkData(String response, String rsp, boolean isTrim) {
        if (!NetworkDiagnoseUtil.isSafety(response) || !NetworkDiagnoseUtil.isSafety(rsp)) {
            LogCatUtil.warn((String) TAG, (String) "[checkData] input is not safety.");
            return false;
        }
        if (isTrim) {
            response = response.trim();
            rsp = rsp.trim();
        }
        int len = response.length() < rsp.length() ? response.length() : rsp.length();
        if (response.substring(0, len).equals(rsp.substring(0, len)) || rsp.startsWith(response)) {
            return true;
        }
        try {
            if (!rsp.startsWith("HTTP/")) {
                return false;
            }
            String rsp2 = rsp.substring(5);
            if (rsp2 == null) {
                return false;
            }
            String rsp3 = rsp2.substring(rsp2.indexOf(Token.SEPARATOR)).trim();
            int state = Integer.parseInt(rsp3.substring(0, rsp3.indexOf(Token.SEPARATOR)));
            if (state >= 200 && state < 300) {
                LogCatUtil.info(TAG, "[checkData] the http rsp state is 200-300.");
                return true;
            } else if (302 != state && 301 != state) {
                return false;
            } else {
                LogCatUtil.info(TAG, "[checkData] the http rsp state is 302.");
                return checkRedirect(rsp3);
            }
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "[checkData] " + e.toString());
            return false;
        }
    }

    private boolean checkRedirect(String response) {
        String rsp;
        String str;
        this.describe = "302 redirect";
        if (response == null) {
            LogCatUtil.warn((String) TAG, (String) "[checkRedirect] input is not null.");
            return false;
        }
        try {
            String rsp2 = response.substring(response.indexOf("Location:") + 9).trim();
            if (rsp2.startsWith(AjxHttpLoader.DOMAIN_HTTP)) {
                rsp = rsp2.substring(7);
            } else if (!rsp2.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                return false;
            } else {
                rsp = rsp2.substring(8);
            }
            String rsp3 = rsp.substring(0, rsp.indexOf("/"));
            StringBuilder sb = new StringBuilder("[checkData]redirect host:");
            if (rsp3 == null) {
                str = "host is null!";
            } else {
                str = rsp3;
            }
            LogCatUtil.info(TAG, sb.append(str).toString());
            this.describe += ", host: " + rsp3;
            if (!rsp3.equals(this.server.ip)) {
                return false;
            }
            LogCatUtil.info(TAG, "[checkRedirect] redirect sucess.");
            return true;
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "[checkRedirect]" + e.toString());
            return false;
        }
    }

    private void finish(boolean fin, boolean ok) {
        if (this.callback != null) {
            if (!fin) {
                this.callback.report(false, false, false, null);
                return;
            }
            String inf = "";
            if (!DftNetTest.class.isInstance(this.callback)) {
                inf = "out_diago:";
            }
            String inf2 = (((((((((inf + (this.server.ip == null ? "-;" : this.server.ip + ";")) + this.server.port + ";") + (ok ? "y;" : "n;")) + "[" + this.name + "] ") + (this.describe == null ? "-" : this.describe) + ";") + (this.protocol == 1 ? "ssl;" : "tcp;")) + (this.tmTcp < 0 ? "-;" : NetworkDiagnoseUtil.nsToMs(this.tmTcp) + ";")) + (this.tmSsl < 0 ? "-;" : NetworkDiagnoseUtil.nsToMs(this.tmSsl) + ";")) + (this.tmRtt < 0 ? "-" : String.valueOf(NetworkDiagnoseUtil.nsToMs(this.tmRtt) + ";"))) + String.valueOf(this.errCode);
            this.callback.report(true, ok, false, inf2);
            LogCatUtil.info(TAG, "[finish]" + inf2);
        }
    }
}
