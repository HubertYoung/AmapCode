package org.android.spdy;

import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class SpdyAgent {
    public static final int ACCS_ONLINE_SERVER = 1;
    public static final int ACCS_TEST_SERVER = 0;
    private static final boolean HAVE_CLOSE = false;
    private static final int KB32 = 32768;
    private static final int KB8 = 8192;
    private static final int MAX_SPDY_SESSION_COUNT = 50;
    private static final int MB5 = 5242880;
    static final int MODE_QUIC = 256;
    static final int SPDY_CUSTOM_CONTROL_FRAME_RECV = 4106;
    static final int SPDY_DATA_CHUNK_RECV = 4097;
    static final int SPDY_DATA_RECV = 4098;
    static final int SPDY_DATA_SEND = 4099;
    static final int SPDY_PING_RECV = 4101;
    static final int SPDY_REQUEST_RECV = 4102;
    static final int SPDY_SESSION_CLOSE = 4103;
    static final int SPDY_SESSION_CREATE = 4096;
    static final int SPDY_SESSION_FAILED_ERROR = 4105;
    static final int SPDY_STREAM_CLOSE = 4100;
    static final int SPDY_STREAM_RESPONSE_RECV = 4104;
    private static final String TNET_SO_VERSION = "tnet-4.0.0";
    private static Object domainHashLock = new Object();
    private static HashMap<String, Integer> domainHashMap = new HashMap<>();
    public static volatile boolean enableDebug = false;
    public static volatile boolean enableTimeGaurd = false;
    private static volatile SpdyAgent gSingleInstance = null;
    private static volatile boolean loadSucc = false;
    private static Object lock = new Object();
    private static final Lock r;
    private static final ReentrantReadWriteLock rwLock;
    private static int totalDomain;
    private static final Lock w = rwLock.writeLock();
    private AccsSSLCallback accsSSLCallback;
    private long agentNativePtr;
    private final QuicCacher cacher = new SecurityGuardCacherImp();
    private AtomicBoolean closed = new AtomicBoolean();
    private volatile boolean enable_header_cache = true;
    private String proxyPassword = null;
    private String proxyUsername = null;
    private HashMap<String, SpdySession> sessionMgr = new HashMap<>(5);
    private LinkedList<SpdySession> sessionQueue = new LinkedList<>();

    private native int closeSessionN(long j);

    private static native int configIpStackModeN(int i);

    private native int configLogFileN(String str, int i, int i2);

    private native int configLogFileN(String str, int i, int i2, int i3);

    private static void crashReporter(int i) {
    }

    private native long createSessionN(long j, SpdySession spdySession, int i, byte[] bArr, char c, byte[] bArr2, char c2, byte[] bArr3, byte[] bArr4, Object obj, int i2, int i3, int i4, byte[] bArr5);

    private native int freeAgent(long j);

    private void getPerformance(SpdySession spdySession, SslPermData sslPermData) {
    }

    private native long getSession(long j, byte[] bArr, char c);

    private native long initAgent(int i, int i2, int i3);

    @Deprecated
    public static void inspect(String str) {
    }

    private native void logFileCloseN();

    private native void logFileFlushN();

    private native int setConTimeout(long j, int i);

    private native int setSessionKind(long j, int i);

    public final native String ResolveHost(String str, String str2, int i);

    public final void close() {
    }

    @Deprecated
    public final void switchAccsServer(int i) {
    }

    public final int VerifyProof(String str, int i, String str2, String str3, String[] strArr, String str4) {
        return QuicProofVerifier.VerifyProof(str, i, str2, 36, str3, strArr, null, str4);
    }

    static {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        rwLock = reentrantReadWriteLock;
        r = reentrantReadWriteLock.readLock();
    }

    /* access modifiers changed from: 0000 */
    public final void clearSpdySession(String str, String str2, int i) {
        if (str != null) {
            w.lock();
            if (str != null) {
                try {
                    HashMap<String, SpdySession> hashMap = this.sessionMgr;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(str2);
                    sb.append(i);
                    hashMap.remove(sb.toString());
                } catch (Throwable th) {
                    w.unlock();
                    throw th;
                }
            }
            w.unlock();
        }
    }

    public static SpdyAgent getInstance(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind) throws UnsatisfiedLinkError, SpdyErrorException {
        if (gSingleInstance == null) {
            synchronized (lock) {
                try {
                    if (gSingleInstance == null) {
                        gSingleInstance = new SpdyAgent(context, spdyVersion, spdySessionKind, null);
                    }
                }
            }
        }
        return gSingleInstance;
    }

    public static boolean checkLoadSucc() {
        return loadSucc;
    }

    @Deprecated
    public static SpdyAgent getInstance(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind, AccsSSLCallback accsSSLCallback2) throws UnsatisfiedLinkError, SpdyErrorException {
        if (gSingleInstance == null) {
            synchronized (lock) {
                if (gSingleInstance == null) {
                    gSingleInstance = new SpdyAgent(context, spdyVersion, spdySessionKind, accsSSLCallback2);
                }
            }
        }
        return gSingleInstance;
    }

    private int getDomainHashIndex(String str) {
        Integer num;
        synchronized (domainHashLock) {
            num = domainHashMap.get(str);
            if (num == null) {
                HashMap<String, Integer> hashMap = domainHashMap;
                int i = totalDomain + 1;
                totalDomain = i;
                hashMap.put(str, Integer.valueOf(i));
                num = Integer.valueOf(totalDomain);
            }
        }
        return num.intValue();
    }

    private SpdyAgent(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind, AccsSSLCallback accsSSLCallback2) throws UnsatisfiedLinkError {
        try {
            SoInstallMgrSdk.init(context);
            loadSucc = SoInstallMgrSdk.initSo(TNET_SO_VERSION, 1);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            this.agentNativePtr = initAgent(spdyVersion.getInt(), spdySessionKind.getint(), SslVersion.SLIGHT_VERSION_V1.getint());
            this.accsSSLCallback = accsSSLCallback2;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        this.cacher.init(context);
        this.closed.set(false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0068 A[SYNTHETIC, Splitter:B:32:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0072 A[SYNTHETIC, Splitter:B:38:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007d A[SYNTHETIC, Splitter:B:43:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0086 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0086 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void InitializeCerts() {
        /*
            org.android.spdy.AndroidTrustAnchors r0 = org.android.spdy.AndroidTrustAnchors.a()
            boolean r1 = org.android.spdy.AndroidTrustAnchors.a
            if (r1 != 0) goto L_0x008c
            java.lang.String r1 = "X.509"
            java.security.cert.CertificateFactory r1 = java.security.cert.CertificateFactory.getInstance(r1)     // Catch:{ CertificateException -> 0x0011 }
            r0.d = r1     // Catch:{ CertificateException -> 0x0011 }
            goto L_0x0015
        L_0x0011:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0015:
            java.io.File r1 = new java.io.File
            java.lang.String r2 = org.android.spdy.AndroidTrustAnchors.b
            r1.<init>(r2)
            java.io.File[] r1 = r1.listFiles()
            int r2 = r1.length
            r3 = 0
        L_0x0022:
            if (r3 >= r2) goto L_0x0089
            r4 = r1[r3]
            boolean r5 = r4.isDirectory()
            if (r5 != 0) goto L_0x0086
            r5 = 0
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x006c, CertificateException -> 0x0062 }
            r6.<init>(r4)     // Catch:{ FileNotFoundException -> 0x006c, CertificateException -> 0x0062 }
            java.security.cert.CertificateFactory r4 = r0.d     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            java.util.Collection r4 = r4.generateCertificates(r6)     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
        L_0x003c:
            boolean r7 = r4.hasNext()     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            if (r7 == 0) goto L_0x0053
            java.util.Set<java.security.cert.TrustAnchor> r7 = r0.c     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            java.security.cert.TrustAnchor r8 = new java.security.cert.TrustAnchor     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            java.lang.Object r9 = r4.next()     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            java.security.cert.X509Certificate r9 = (java.security.cert.X509Certificate) r9     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            r8.<init>(r9, r5)     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            r7.add(r8)     // Catch:{ FileNotFoundException -> 0x005c, CertificateException -> 0x0059, all -> 0x0057 }
            goto L_0x003c
        L_0x0053:
            r6.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0086
        L_0x0057:
            r0 = move-exception
            goto L_0x007b
        L_0x0059:
            r4 = move-exception
            r5 = r6
            goto L_0x0063
        L_0x005c:
            r4 = move-exception
            r5 = r6
            goto L_0x006d
        L_0x005f:
            r0 = move-exception
            r6 = r5
            goto L_0x007b
        L_0x0062:
            r4 = move-exception
        L_0x0063:
            r4.printStackTrace()     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x0086
            r5.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0086
        L_0x006c:
            r4 = move-exception
        L_0x006d:
            r4.printStackTrace()     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x0086
            r5.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x0086
        L_0x0076:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0086
        L_0x007b:
            if (r6 == 0) goto L_0x0085
            r6.close()     // Catch:{ IOException -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0085:
            throw r0
        L_0x0086:
            int r3 = r3 + 1
            goto L_0x0022
        L_0x0089:
            r0 = 1
            org.android.spdy.AndroidTrustAnchors.a = r0
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SpdyAgent.InitializeCerts():void");
    }

    private void checkLoadSo() throws SpdyErrorException {
        if (!loadSucc) {
            try {
                synchronized (lock) {
                    if (!loadSucc) {
                        loadSucc = SoInstallMgrSdk.initSo(TNET_SO_VERSION, 1);
                        this.agentNativePtr = initAgent(0, 0, 0);
                    } else {
                        return;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            return;
        }
        if (!loadSucc) {
            throw new SpdyErrorException((String) "TNET_JNI_ERR_LOAD_SO_FAIL", (int) TnetStatusCode.TNET_JNI_ERR_LOAD_SO_FAIL);
        }
    }

    public final void setProxyUsernamePassword(String str, String str2) {
        this.proxyUsername = str;
        this.proxyPassword = str2;
    }

    static void securityCheck(int i, int i2) {
        if (i >= 32768) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:total=".concat(String.valueOf(i)), (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        } else if (i2 >= 8192) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:value=".concat(String.valueOf(i2)), (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
    }

    static void tableListJudge(int i) {
        if (i >= 5242880) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:total=".concat(String.valueOf(i)), (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
    }

    static void InvlidCharJudge(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 255) < 32 || (bArr[i] & 255) > 126) {
                bArr[i] = 63;
            }
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            if ((bArr2[i2] & 255) < 32 || (bArr2[i2] & 255) > 126) {
                bArr2[i2] = 63;
            }
        }
    }

    static void headJudge(Map<String, String> map) {
        if (map != null) {
            int i = 0;
            for (Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                InvlidCharJudge(str.getBytes(), str2.getBytes());
                i += str.length() + 1 + str2.length();
                securityCheck(i, str2.length());
            }
        }
    }

    static String mapBodyToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map == null) {
            return null;
        }
        int i = 0;
        for (Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            sb.append(str);
            sb.append('=');
            sb.append(str2);
            sb.append('&');
            i += str.length() + 1 + str2.length();
            tableListJudge(i);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    static byte[] dataproviderToByteArray(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider) {
        byte[] bArr;
        headJudge(spdyRequest.getHeaders());
        if (spdyDataProvider == null) {
            return null;
        }
        String mapBodyToString = mapBodyToString(spdyDataProvider.postBody);
        if (mapBodyToString != null) {
            bArr = mapBodyToString.getBytes();
        } else {
            bArr = spdyDataProvider.data;
        }
        if (bArr == null || bArr.length < 5242880) {
            return bArr;
        }
        StringBuilder sb = new StringBuilder("SPDY_JNI_ERR_INVALID_PARAM:total=");
        sb.append(bArr.length);
        throw new SpdyErrorException(sb.toString(), (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
    }

    @Deprecated
    public final SpdySession createSession(String str, Object obj, SessionCb sessionCb, int i) throws SpdyErrorException {
        return createSession(str, "", obj, sessionCb, null, i, 0);
    }

    @Deprecated
    public final SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, int i) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, null, i, 0);
    }

    @Deprecated
    public final SpdySession createSession(String str, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i) throws SpdyErrorException {
        return createSession(str, "", obj, sessionCb, sslCertcb, i, 0);
    }

    public final SpdySession createSession(SessionInfo sessionInfo) throws SpdyErrorException {
        return createSession(sessionInfo.getAuthority(), sessionInfo.getDomain(), sessionInfo.getSessonUserData(), sessionInfo.getSessionCb(), null, sessionInfo.getMode(), sessionInfo.getPubKeySeqNum(), sessionInfo.getConnectionTimeoutMs(), sessionInfo.getCertHost());
    }

    @Deprecated
    public final SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, sslCertcb, i, i2, -1);
    }

    public final SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2, int i3) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, sslCertcb, i, i2, i3, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x01a4 A[Catch:{ all -> 0x01f6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01a9 A[Catch:{ all -> 0x01f6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01ae A[Catch:{ all -> 0x01f6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01dc A[Catch:{ all -> 0x01da }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.android.spdy.SpdySession createSession(java.lang.String r27, java.lang.String r28, java.lang.Object r29, org.android.spdy.SessionCb r30, org.android.spdy.SslCertcb r31, int r32, int r33, int r34, java.lang.String r35) throws org.android.spdy.SpdyErrorException {
        /*
            r26 = this;
            r15 = r26
            r14 = r27
            r13 = r28
            r11 = r32
            if (r14 != 0) goto L_0x0014
            org.android.spdy.SpdyErrorException r1 = new org.android.spdy.SpdyErrorException
            java.lang.String r2 = "SPDY_JNI_ERR_INVALID_PARAM"
            r3 = -1102(0xfffffffffffffbb2, float:NaN)
            r1.<init>(r2, r3)
            throw r1
        L_0x0014:
            java.lang.String r1 = "/"
            java.lang.String[] r1 = r14.split(r1)
            r12 = 0
            r2 = r1[r12]
            r3 = 58
            int r2 = r2.lastIndexOf(r3)
            r3 = r1[r12]
            java.lang.String r10 = r3.substring(r12, r2)
            r3 = r1[r12]
            r9 = 1
            int r2 = r2 + r9
            java.lang.String r8 = r3.substring(r2)
            java.lang.String r2 = "0.0.0.0"
            byte[] r2 = r2.getBytes()
            int r3 = r1.length
            if (r3 == r9) goto L_0x0055
            r1 = r1[r9]
            java.lang.String r2 = ":"
            java.lang.String[] r1 = r1.split(r2)
            r2 = r1[r12]
            byte[] r2 = r2.getBytes()
            r1 = r1[r9]
            int r1 = java.lang.Integer.parseInt(r1)
            char r1 = (char) r1
            r17 = r1
            r16 = r2
            r7 = r14
            goto L_0x006b
        L_0x0055:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            java.lang.String r3 = "/0.0.0.0:0"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r7 = r1
            r16 = r2
            r17 = 0
        L_0x006b:
            r26.agentIsOpen()
            java.util.concurrent.locks.Lock r1 = r
            r1.lock()
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r1 = r15.sessionMgr     // Catch:{ all -> 0x0203 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0203 }
            r2.<init>()     // Catch:{ all -> 0x0203 }
            r2.append(r7)     // Catch:{ all -> 0x0203 }
            r2.append(r13)     // Catch:{ all -> 0x0203 }
            r2.append(r11)     // Catch:{ all -> 0x0203 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0203 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0203 }
            org.android.spdy.SpdySession r1 = (org.android.spdy.SpdySession) r1     // Catch:{ all -> 0x0203 }
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r2 = r15.sessionMgr     // Catch:{ all -> 0x0203 }
            int r2 = r2.size()     // Catch:{ all -> 0x0203 }
            r3 = 50
            if (r2 < r3) goto L_0x0099
            r2 = 1
            goto L_0x009a
        L_0x0099:
            r2 = 0
        L_0x009a:
            java.util.concurrent.locks.Lock r3 = r
            r3.unlock()
            if (r2 == 0) goto L_0x00ab
            org.android.spdy.SpdyErrorException r1 = new org.android.spdy.SpdyErrorException
            java.lang.String r2 = "SPDY_SESSION_EXCEED_MAXED: session count exceed max"
            r3 = -1105(0xfffffffffffffbaf, float:NaN)
            r1.<init>(r2, r3)
            throw r1
        L_0x00ab:
            if (r1 == 0) goto L_0x00b1
            r1.increRefCount()
            return r1
        L_0x00b1:
            java.util.concurrent.locks.Lock r1 = w
            r1.lock()
            r18 = 0
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r1 = r15.sessionMgr     // Catch:{ Throwable -> 0x00d3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d3 }
            r2.<init>()     // Catch:{ Throwable -> 0x00d3 }
            r2.append(r7)     // Catch:{ Throwable -> 0x00d3 }
            r2.append(r13)     // Catch:{ Throwable -> 0x00d3 }
            r2.append(r11)     // Catch:{ Throwable -> 0x00d3 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00d3 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ Throwable -> 0x00d3 }
            org.android.spdy.SpdySession r1 = (org.android.spdy.SpdySession) r1     // Catch:{ Throwable -> 0x00d3 }
            goto L_0x00d5
        L_0x00d3:
            r1 = r18
        L_0x00d5:
            if (r1 == 0) goto L_0x00e0
            java.util.concurrent.locks.Lock r2 = w
            r2.unlock()
            r1.increRefCount()
            return r1
        L_0x00e0:
            org.android.spdy.SpdySession r6 = new org.android.spdy.SpdySession     // Catch:{ all -> 0x01fa }
            r2 = 0
            r1 = r6
            r4 = r15
            r5 = r7
            r19 = r6
            r6 = r13
            r20 = r7
            r7 = r30
            r12 = r8
            r8 = r11
            r22 = 1
            r9 = r33
            r14 = r10
            r10 = r29
            r1.<init>(r2, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x01fa }
            if (r35 != 0) goto L_0x00ff
            r23 = r18
            goto L_0x0105
        L_0x00ff:
            byte[] r1 = r35.getBytes()     // Catch:{ all -> 0x01fa }
            r23 = r1
        L_0x0105:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01fa }
            r1.<init>()     // Catch:{ all -> 0x01fa }
            r1.append(r13)     // Catch:{ all -> 0x01fa }
            r1.append(r11)     // Catch:{ all -> 0x01fa }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01fa }
            int r5 = r15.getDomainHashIndex(r1)     // Catch:{ all -> 0x01fa }
            r1 = r11 & 4
            if (r1 == 0) goto L_0x011f
            r1 = r11 | 8
            r11 = r1
        L_0x011f:
            java.lang.String r1 = r15.proxyUsername     // Catch:{ all -> 0x01fa }
            if (r1 == 0) goto L_0x0164
            java.lang.String r1 = r15.proxyPassword     // Catch:{ all -> 0x015f }
            if (r1 == 0) goto L_0x0164
            long r2 = r15.agentNativePtr     // Catch:{ all -> 0x015f }
            byte[] r6 = r14.getBytes()     // Catch:{ all -> 0x015f }
            int r1 = java.lang.Integer.parseInt(r12)     // Catch:{ all -> 0x015f }
            char r7 = (char) r1     // Catch:{ all -> 0x015f }
            java.lang.String r1 = r15.proxyUsername     // Catch:{ all -> 0x015f }
            byte[] r10 = r1.getBytes()     // Catch:{ all -> 0x015f }
            java.lang.String r1 = r15.proxyPassword     // Catch:{ all -> 0x015f }
            byte[] r12 = r1.getBytes()     // Catch:{ all -> 0x015f }
            r1 = r15
            r4 = r19
            r8 = r16
            r9 = r17
            r14 = r11
            r11 = r12
            r21 = 0
            r12 = r29
            r13 = r14
            r24 = r14
            r14 = r33
            r15 = r34
            r16 = r23
            long r1 = r1.createSessionN(r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0159 }
            goto L_0x018a
        L_0x0159:
            r0 = move-exception
            r2 = r0
            r1 = r26
            goto L_0x01fd
        L_0x015f:
            r0 = move-exception
            r2 = r0
            r1 = r15
            goto L_0x01fd
        L_0x0164:
            r24 = r11
            r21 = 0
            long r2 = r15.agentNativePtr     // Catch:{ all -> 0x01fa }
            byte[] r6 = r14.getBytes()     // Catch:{ all -> 0x01fa }
            int r1 = java.lang.Integer.parseInt(r12)     // Catch:{ all -> 0x01fa }
            char r7 = (char) r1
            r10 = 0
            r11 = 0
            r1 = r15
            r4 = r19
            r8 = r16
            r9 = r17
            r12 = r29
            r13 = r24
            r14 = r33
            r15 = r34
            r16 = r23
            long r1 = r1.createSessionN(r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x01f6 }
        L_0x018a:
            java.lang.String r3 = "tnet-jni"
            java.lang.String r4 = " create new session: "
            java.lang.String r5 = java.lang.String.valueOf(r27)     // Catch:{ all -> 0x01f6 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ all -> 0x01f6 }
            org.android.spdy.spduLog.Logi(r3, r4)     // Catch:{ all -> 0x01f6 }
            r3 = 1
            long r5 = r1 & r3
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            r4 = 0
            if (r3 != 0) goto L_0x01a9
            long r1 = r1 >> r22
            int r12 = (int) r1     // Catch:{ all -> 0x01f6 }
            r1 = r4
            goto L_0x01aa
        L_0x01a9:
            r12 = 0
        L_0x01aa:
            int r3 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x01dc
            r3 = r19
            r3.setSessionNativePtr(r1)     // Catch:{ all -> 0x01f6 }
            r1 = r26
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r2 = r1.sessionMgr     // Catch:{ all -> 0x01da }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01da }
            r4.<init>()     // Catch:{ all -> 0x01da }
            r14 = r20
            r4.append(r14)     // Catch:{ all -> 0x01da }
            r5 = r28
            r4.append(r5)     // Catch:{ all -> 0x01da }
            r11 = r24
            r4.append(r11)     // Catch:{ all -> 0x01da }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01da }
            r2.put(r4, r3)     // Catch:{ all -> 0x01da }
            java.util.LinkedList<org.android.spdy.SpdySession> r2 = r1.sessionQueue     // Catch:{ all -> 0x01da }
            r2.add(r3)     // Catch:{ all -> 0x01da }
            r18 = r3
            goto L_0x01f0
        L_0x01da:
            r0 = move-exception
            goto L_0x01fc
        L_0x01dc:
            r1 = r26
            if (r12 == 0) goto L_0x01f0
            org.android.spdy.SpdyErrorException r2 = new org.android.spdy.SpdyErrorException     // Catch:{ all -> 0x01da }
            java.lang.String r3 = "create session error: "
            java.lang.String r4 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x01da }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x01da }
            r2.<init>(r3, r12)     // Catch:{ all -> 0x01da }
            throw r2     // Catch:{ all -> 0x01da }
        L_0x01f0:
            java.util.concurrent.locks.Lock r2 = w
            r2.unlock()
            return r18
        L_0x01f6:
            r0 = move-exception
            r1 = r26
            goto L_0x01fc
        L_0x01fa:
            r0 = move-exception
            r1 = r15
        L_0x01fc:
            r2 = r0
        L_0x01fd:
            java.util.concurrent.locks.Lock r3 = w
            r3.unlock()
            throw r2
        L_0x0203:
            r0 = move-exception
            r1 = r15
            r2 = r0
            java.util.concurrent.locks.Lock r3 = r
            r3.unlock()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SpdyAgent.createSession(java.lang.String, java.lang.String, java.lang.Object, org.android.spdy.SessionCb, org.android.spdy.SslCertcb, int, int, int, java.lang.String):org.android.spdy.SpdySession");
    }

    @Deprecated
    public final SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, SslCertcb sslCertcb, int i) throws SpdyErrorException {
        SpdySession createSession = createSession(spdyRequest.getAuthority(), spdyRequest.getDomain(), obj, sessionCb, sslCertcb, i, 0, spdyRequest.getConnectionTimeoutMs());
        createSession.submitRequest(spdyRequest, spdyDataProvider, obj2, spdycb);
        return createSession;
    }

    @Deprecated
    public final SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2) throws SpdyErrorException {
        SpdySession createSession = createSession(spdyRequest.getAuthority(), spdyRequest.getDomain(), obj, sessionCb, sslCertcb, i, i2, spdyRequest.getConnectionTimeoutMs());
        createSession.submitRequest(spdyRequest, spdyDataProvider, obj2, spdycb);
        return createSession;
    }

    public final SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, int i, int i2) throws SpdyErrorException {
        return submitRequest(spdyRequest, spdyDataProvider, obj, obj2, spdycb, sessionCb, null, i, i2);
    }

    @Deprecated
    public final SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, int i) throws SpdyErrorException {
        return submitRequest(spdyRequest, spdyDataProvider, obj, obj2, spdycb, sessionCb, (SslCertcb) null, i);
    }

    private void agentIsOpen() {
        if (this.closed.get()) {
            throw new SpdyErrorException((String) "SPDY_JNI_ERR_ASYNC_CLOSE", (int) TnetStatusCode.TNET_JNI_ERR_ASYNC_CLOSE);
        }
        checkLoadSo();
    }

    /* access modifiers changed from: 0000 */
    public final void removeSession(SpdySession spdySession) {
        w.lock();
        try {
            this.sessionQueue.remove(spdySession);
        } finally {
            w.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public final int closeSession(long j) {
        return closeSessionN(j);
    }

    static String[] mapToByteArray(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        String[] strArr = new String[(map.size() * 2)];
        int i = 0;
        for (Entry next : map.entrySet()) {
            strArr[i] = (String) next.getKey();
            strArr[i + 1] = (String) next.getValue();
            i += 2;
        }
        return strArr;
    }

    static Map<String, List<String>> stringArrayToMap(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        HashMap hashMap = new HashMap(5);
        int i = 0;
        while (true) {
            int i2 = i + 2;
            if (i2 <= strArr.length) {
                if (strArr[i] == null) {
                    break;
                }
                int i3 = i + 1;
                if (strArr[i3] == null) {
                    break;
                }
                List list = (List) hashMap.get(strArr[i]);
                if (list == null) {
                    list = new ArrayList(1);
                    hashMap.put(strArr[i], list);
                }
                list.add(strArr[i3]);
                i = i2;
            } else {
                return hashMap;
            }
        }
        return null;
    }

    @Deprecated
    public final int setSessionKind(SpdySessionKind spdySessionKind) {
        agentIsOpen();
        try {
            return setSessionKind(this.agentNativePtr, spdySessionKind.getint());
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Deprecated
    public final int setConnectTimeOut(int i) {
        agentIsOpen();
        try {
            return setConTimeout(this.agentNativePtr, i);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void setAccsSslCallback(AccsSSLCallback accsSSLCallback2) {
        StringBuilder sb = new StringBuilder("[setAccsSslCallback] - ");
        sb.append(accsSSLCallback2.getClass());
        spduLog.Logi("tnet-jni", sb.toString());
        this.accsSSLCallback = accsSSLCallback2;
    }

    private void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdySessionConnectCB(spdySession, superviseConnectInfo);
        }
        if (spdySession != null) {
            spdySession.setQuicConnectionID(superviseConnectInfo.quicConnectionID);
        }
    }

    private void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, int i, SpdyByteArray spdyByteArray, int i2) {
        spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - ");
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataChunkRecvCB(spdySession, z, j, spdyByteArray, i2);
        }
    }

    private void spdyDataRecvCallback(SpdySession spdySession, boolean z, int i, int i2, int i3) {
        spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - ");
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataRecvCallback(spdySession, z, j, i2, i3);
        }
    }

    private void spdyDataSendCallback(SpdySession spdySession, boolean z, int i, int i2, int i3) {
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataSendCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataSendCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataSendCallback(spdySession, z, j, i2, i3);
        }
    }

    private void spdyStreamCloseCallback(SpdySession spdySession, int i, int i2, int i3, SuperviseData superviseData) {
        spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - ");
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyStreamCloseCallback(spdySession, j, i2, i3, superviseData);
        }
    }

    private void spdyPingRecvCallback(SpdySession spdySession, int i, Object obj) {
        spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyPingRecvCallback(spdySession, (long) i, obj);
        }
    }

    private void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyCustomControlFrameRecvCallback(spdySession, obj, i, i2, i3, i4, bArr);
        }
    }

    private void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyCustomControlFrameFailCallback(spdySession, obj, i, i2);
        }
    }

    private void bioPingRecvCallback(SpdySession spdySession, int i) {
        spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.bioPingRecvCallback(spdySession, i);
        }
    }

    private void spdyRequestRecvCallback(SpdySession spdySession, int i, int i2) {
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyRequestRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyRequestRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyRequestRecvCallback(spdySession, j, i2);
        }
    }

    private void spdyStreamResponseRecv(SpdySession spdySession, int i, byte[] bArr, int[] iArr, int i2) {
        spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - ");
        String[] strArr = new String[iArr.length];
        int i3 = 0;
        if (this.enable_header_cache) {
            HTTPHeaderPool a = HTTPHeaderPool.a();
            int i4 = 0;
            while (i3 < iArr.length) {
                strArr[i3] = a.a(ByteBuffer.wrap(bArr, i4, iArr[i3]));
                int i5 = i4 + iArr[i3];
                int i6 = i3 + 1;
                if (iArr[i6] > 32) {
                    strArr[i6] = new String(bArr, i5, iArr[i6]);
                } else {
                    strArr[i6] = a.a(ByteBuffer.wrap(bArr, i5, iArr[i6]));
                }
                i4 = i5 + iArr[i6];
                i3 += 2;
            }
        } else {
            int i7 = 0;
            while (i3 < iArr.length) {
                try {
                    strArr[i3] = new String(bArr, i7, iArr[i3], "utf-8");
                    i7 += iArr[i3];
                    int i8 = i3 + 1;
                    strArr[i8] = new String(bArr, i7, iArr[i8], "utf-8");
                    i7 += iArr[i8];
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i3 += 2;
            }
        }
        Map<String, List<String>> stringArrayToMap = stringArrayToMap(strArr);
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyOnStreamResponse(spdySession, j, stringArrayToMap, i2);
        }
    }

    private void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - errorCode = ".concat(String.valueOf(i)));
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - session is null");
        } else {
            try {
                if (spdySession.intenalcb == null) {
                    spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - session.intenalcb is null");
                } else {
                    spdySession.intenalcb.spdySessionCloseCallback(spdySession, obj, superviseConnectInfo, i);
                }
            } finally {
                spdySession.cleanUp();
            }
        }
        spdySession.releasePptr();
    }

    private void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        spduLog.Logi("tnet-jni", "[spdySessionFailedError] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionFailedError] - session is null");
        } else {
            try {
                if (spdySession.intenalcb == null) {
                    spduLog.Logi("tnet-jni", "[spdySessionFailedError] - session.intenalcb is null");
                } else {
                    spdySession.intenalcb.spdySessionFailedError(spdySession, i, obj);
                }
            } finally {
                spdySession.cleanUp();
            }
        }
        spdySession.releasePptr();
    }

    private void spdySessionOnWritable(SpdySession spdySession, Object obj, int i) {
        spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - session is null");
            return;
        }
        try {
            if (spdySession.intenalcb == null) {
                spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - session.intenalcb is null");
            } else {
                spdySession.intenalcb.spdySessionOnWritable(spdySession, obj, i);
            }
        } catch (Throwable th) {
            spduLog.Loge("tnet-jni", "[spdySessionOnWritable] - exception:".concat(String.valueOf(th)));
        }
    }

    private byte[] getSSLPublicKey(int i, byte[] bArr) {
        if (this.accsSSLCallback != null) {
            return this.accsSSLCallback.getSSLPublicKey(i, bArr);
        }
        spduLog.Logd("tnet-jni", "[getSSLPublicKey] - accsSSLCallback is null.");
        return null;
    }

    private int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[putSSLMeta] - session is null");
            return -1;
        } else if (spdySession.intenalcb != null) {
            return spdySession.intenalcb.putSSLMeta(spdySession, bArr);
        } else {
            spduLog.Logi("tnet-jni", "[putSSLMeta] - session.intenalcb is null");
            return -1;
        }
    }

    private byte[] getSSLMeta(SpdySession spdySession) {
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[getSSLMeta] - session is null");
            return null;
        } else if (spdySession.intenalcb != null) {
            return spdySession.intenalcb.getSSLMeta(spdySession);
        } else {
            spduLog.Logi("tnet-jni", "[getSSLMeta] - session.intenalcb is null");
            return null;
        }
    }

    public final HashMap<String, SpdySession> getAllSession() {
        return this.sessionMgr;
    }

    public final int configLogFile(String str, int i, int i2) {
        if (loadSucc) {
            return configLogFileN(str, i, i2);
        }
        return -1;
    }

    public final int configLogFile(String str, int i, int i2, int i3) {
        if (loadSucc) {
            return configLogFileN(str, i, i2, i3);
        }
        return -1;
    }

    public final void logFileFlush() {
        if (loadSucc) {
            logFileFlushN();
        }
    }

    public final void logFileClose() {
        if (loadSucc) {
            logFileFlushN();
            logFileCloseN();
        }
    }

    public final boolean cacher_store(String str, String str2) {
        return this.cacher.store(str, str2);
    }

    public final byte[] cacher_load(String str) {
        return this.cacher.load(str);
    }

    public static int configIpStackMode(int i) {
        spduLog.Logi("tnet-jni", "[configIpStackMode] - ".concat(String.valueOf(i)));
        return configIpStackModeN(i);
    }

    public final void disableHeaderCache() {
        this.enable_header_cache = false;
    }
}
