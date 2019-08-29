package com.alibaba.analytics.core.sync;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.utils.ByteUtils;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.WuaHelper;
import com.alibaba.analytics.utils.ZipDictUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionExtraCb;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;
import org.android.spdy.SuperviseConnectInfo;

public class TnetUtil {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int ENVIRONMENT_BETA = 1;
    private static final int ENVIRONMENT_DAILY = 3;
    private static final int ENVIRONMENT_ONLINE = 0;
    private static final int ENVIRONMENT_PRE = 2;
    private static final int GCRY_CIPHER_AES128 = 16;
    private static final int HEAD_LENGTH = 8;
    private static final Object Lock_Event = new Object();
    /* access modifiers changed from: private */
    public static final Object Lock_Object = new Object();
    private static final int PROTOCOL_MAX_LENGTH = 131072;
    private static final String SSL_TIKET_KEY2 = "accs_ssl_key2_";
    private static final String SecurityGuard_HOST = "adashx.m.taobao.com";
    private static final int TNET_ENVIRONMENT = 0;
    private static final int WAIT_TIMEOUT = 60000;
    private static boolean bFirstSpdySession = true;
    /* access modifiers changed from: private */
    public static int errorCode = -1;
    private static boolean isGetWuaBeforeSpdySession = false;
    public static int mErrorCode;
    public static final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    /* access modifiers changed from: private */
    public static ByteArrayOutputStream mResponseCache;
    /* access modifiers changed from: private */
    public static long mResponseLen;
    /* access modifiers changed from: private */
    public static long mResponseReceiveLen;
    private static byte[] protocolBytes;
    private static int sendBytes;
    /* access modifiers changed from: private */
    public static SpdySession spdySessionUT;

    static class UTSessionCb implements SessionCb, SessionExtraCb {
        private byte[] sslMeta;

        public void bioPingRecvCallback(SpdySession spdySession, int i) {
        }

        public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        }

        public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        }

        private UTSessionCb() {
        }

        public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
            if (spdySession == TnetUtil.spdySessionUT) {
                TnetUtil.sendCustomControlFrame(spdySession);
            }
        }

        public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
            if (spdySession == TnetUtil.spdySessionUT) {
                if (TnetUtil.mResponseCache == null) {
                    TnetUtil.mResponseCache = new ByteArrayOutputStream(1024);
                    TnetUtil.mResponseLen = TnetUtil.getResponseBodyLen(bArr);
                }
                if (TnetUtil.mResponseLen != -1) {
                    try {
                        TnetUtil.mResponseCache.write(bArr);
                    } catch (IOException unused) {
                    }
                    TnetUtil.mResponseReceiveLen = TnetUtil.mResponseReceiveLen + ((long) bArr.length);
                    if (TnetUtil.mResponseLen == TnetUtil.mResponseReceiveLen - 8) {
                        try {
                            TnetUtil.mResponseCache.flush();
                        } catch (IOException unused2) {
                        }
                        byte[] byteArray = TnetUtil.mResponseCache.toByteArray();
                        try {
                            TnetUtil.mResponseCache.close();
                        } catch (IOException unused3) {
                        }
                        TnetUtil.errorCode = BizRequest.parseResult(byteArray);
                        if (TnetUtil.errorCode != 0) {
                            TnetUtil.closeSession();
                        }
                        TnetUtil.sendCallbackNotify();
                    }
                } else {
                    TnetUtil.errorCode = -1;
                    TnetUtil.closeSession();
                    TnetUtil.sendCallbackNotify();
                }
            } else {
                Logger.w((String) "[spdyCustomControlFrameRecvCallback]", " session != spdySessionUT");
            }
        }

        public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
            if (Variables.getInstance().isSelfMonitorTurnOn()) {
                TnetUtil.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.TNET_REQUEST_ERROR, null, Double.valueOf(1.0d)));
            }
            if (spdySession == TnetUtil.spdySessionUT) {
                TnetUtil.errorCode = i;
                TnetUtil.closeSession();
            }
        }

        public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
            if (spdySession == TnetUtil.spdySessionUT) {
                TnetUtil.errorCode = i;
                synchronized (TnetUtil.Lock_Object) {
                    TnetUtil.spdySessionUT = null;
                }
            }
        }

        public byte[] getSSLMeta(SpdySession spdySession) {
            if (!TnetSecuritySDK.getInstance().getInitSecurityCheck()) {
                return this.sslMeta;
            }
            byte[] byteArray = TnetSecuritySDK.getInstance().getByteArray("accs_ssl_key2_adashx.m.taobao.com");
            if (byteArray != null) {
                return byteArray;
            }
            return new byte[0];
        }

        public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
            if (TnetSecuritySDK.getInstance().getInitSecurityCheck()) {
                return TnetUtil.securityGuardPutSslTicket2(bArr);
            }
            this.sslMeta = bArr;
            return 0;
        }

        public void spdySessionOnWritable(SpdySession spdySession, Object obj, int i) {
            if (spdySession == TnetUtil.spdySessionUT) {
                TnetUtil.sendCustomControlFrame(spdySession);
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:7|8|(2:10|11)|12|13|14|(3:15|16|(5:49|(1:56)(1:55)|61|(3:63|(1:65)|66)|67)(14:22|(1:24)|25|(1:27)|28|(1:30)(1:31)|32|f8|40|41|42|61|(0)|67))) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x002f */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0180 A[Catch:{ all -> 0x01fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01b1 A[SYNTHETIC, Splitter:B:70:0x01b1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.alibaba.analytics.core.sync.BizResponse sendRequest(byte[] r27) {
        /*
            com.alibaba.analytics.utils.Logger.d()
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather r1 = mMonitor
            int r2 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.TNET_REQUEST_SEND
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            java.lang.Double r5 = java.lang.Double.valueOf(r3)
            r6 = 0
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent r2 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.buildCountEvent(r2, r6, r5)
            r1.onEvent(r2)
            com.alibaba.analytics.core.sync.BizResponse r1 = new com.alibaba.analytics.core.sync.BizResponse
            r1.<init>()
            java.lang.Object r2 = Lock_Object
            monitor-enter(r2)
            protocolBytes = r27     // Catch:{ all -> 0x01ff }
            r5 = 0
            sendBytes = r5     // Catch:{ all -> 0x01ff }
            monitor-exit(r2)     // Catch:{ all -> 0x01ff }
            java.lang.Object r7 = Lock_Event
            monitor-enter(r7)
            java.io.ByteArrayOutputStream r2 = mResponseCache     // Catch:{ all -> 0x01fb }
            if (r2 == 0) goto L_0x002f
            java.io.ByteArrayOutputStream r2 = mResponseCache     // Catch:{ IOException -> 0x002f }
            r2.close()     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            mResponseCache = r6     // Catch:{ all -> 0x01fb }
            r8 = 0
            mResponseReceiveLen = r8     // Catch:{ all -> 0x01fb }
            mResponseLen = r8     // Catch:{ all -> 0x01fb }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01fb }
            r2 = -1
            errorCode = r2     // Catch:{ all -> 0x01fb }
            r11 = 3
            r12 = 6
            r15 = 2
            r13 = 1
            org.android.spdy.SpdySession r14 = spdySessionUT     // Catch:{ Exception -> 0x0161 }
            if (r14 != 0) goto L_0x013d
            boolean r14 = bFirstSpdySession     // Catch:{ Exception -> 0x0161 }
            if (r14 != 0) goto L_0x0054
            com.alibaba.analytics.core.Variables r14 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Exception -> 0x0161 }
            boolean r14 = r14.isGzipUpload()     // Catch:{ Exception -> 0x0161 }
            if (r14 == 0) goto L_0x013d
        L_0x0054:
            com.alibaba.analytics.core.Variables r14 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Exception -> 0x0161 }
            boolean r14 = r14.isSelfMonitorTurnOn()     // Catch:{ Exception -> 0x0161 }
            if (r14 == 0) goto L_0x006d
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather r14 = mMonitor     // Catch:{ Exception -> 0x0161 }
            int r2 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.TNET_CREATE_SESSION     // Catch:{ Exception -> 0x0161 }
            java.lang.Double r10 = java.lang.Double.valueOf(r3)     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent r2 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.buildCountEvent(r2, r6, r10)     // Catch:{ Exception -> 0x0161 }
            r14.onEvent(r2)     // Catch:{ Exception -> 0x0161 }
        L_0x006d:
            com.alibaba.analytics.core.Variables r2 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Exception -> 0x0161 }
            android.content.Context r2 = r2.getContext()     // Catch:{ Exception -> 0x0161 }
            org.android.spdy.SpdyVersion r10 = org.android.spdy.SpdyVersion.SPDY3     // Catch:{ Exception -> 0x0161 }
            org.android.spdy.SpdySessionKind r14 = org.android.spdy.SpdySessionKind.NONE_SESSION     // Catch:{ Exception -> 0x0161 }
            org.android.spdy.SpdyAgent r2 = org.android.spdy.SpdyAgent.getInstance(r2, r10, r14)     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.core.sync.TnetSecuritySDK r10 = com.alibaba.analytics.core.sync.TnetSecuritySDK.getInstance()     // Catch:{ Exception -> 0x0161 }
            boolean r10 = r10.getInitSecurityCheck()     // Catch:{ Exception -> 0x0161 }
            if (r10 == 0) goto L_0x008f
            com.alibaba.analytics.core.sync.TnetUtil$1 r10 = new com.alibaba.analytics.core.sync.TnetUtil$1     // Catch:{ Exception -> 0x0161 }
            r10.<init>()     // Catch:{ Exception -> 0x0161 }
            r2.setAccsSslCallback(r10)     // Catch:{ Exception -> 0x0161 }
        L_0x008f:
            com.alibaba.analytics.core.sync.TnetUtil$UTSessionCb r10 = new com.alibaba.analytics.core.sync.TnetUtil$UTSessionCb     // Catch:{ Exception -> 0x0161 }
            r10.<init>()     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.core.sync.TnetHostPortMgr r14 = com.alibaba.analytics.core.sync.TnetHostPortMgr.getInstance()     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.core.sync.TnetHostPortMgr$TnetHostPort r14 = r14.getEntity()     // Catch:{ Exception -> 0x0161 }
            java.lang.String r25 = r14.getHost()     // Catch:{ Exception -> 0x0161 }
            int r14 = r14.getPort()     // Catch:{ Exception -> 0x0161 }
            org.android.spdy.SessionInfo r3 = new org.android.spdy.SessionInfo     // Catch:{ Exception -> 0x0161 }
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r24 = 4240(0x1090, float:5.942E-42)
            r16 = r3
            r17 = r25
            r18 = r14
            r23 = r10
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.core.sync.TnetSecuritySDK r4 = com.alibaba.analytics.core.sync.TnetSecuritySDK.getInstance()     // Catch:{ Exception -> 0x0161 }
            boolean r4 = r4.getInitSecurityCheck()     // Catch:{ Exception -> 0x0161 }
            if (r4 == 0) goto L_0x00cb
            r4 = 8
            r3.setPubKeySeqNum(r4)     // Catch:{ Exception -> 0x0161 }
            goto L_0x00d0
        L_0x00cb:
            r4 = 9
            r3.setPubKeySeqNum(r4)     // Catch:{ Exception -> 0x0161 }
        L_0x00d0:
            java.lang.Object[] r4 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x0161 }
            java.lang.String r10 = "host"
            r4[r5] = r10     // Catch:{ Exception -> 0x0161 }
            r4[r13] = r25     // Catch:{ Exception -> 0x0161 }
            java.lang.String r10 = "port"
            r4[r15] = r10     // Catch:{ Exception -> 0x0161 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x0161 }
            r4[r11] = r10     // Catch:{ Exception -> 0x0161 }
            java.lang.String r10 = "TNET_ENVIRONMENT"
            r14 = 4
            r4[r14] = r10     // Catch:{ Exception -> 0x0161 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x0161 }
            r14 = 5
            r4[r14] = r10     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.utils.Logger.d(r6, r4)     // Catch:{ Exception -> 0x0161 }
            r4 = 10000(0x2710, float:1.4013E-41)
            r3.setConnectionTimeoutMs(r4)     // Catch:{ Exception -> 0x0161 }
            java.lang.Object r4 = Lock_Object     // Catch:{ Exception -> 0x0161 }
            monitor-enter(r4)     // Catch:{ Exception -> 0x0161 }
            org.android.spdy.SpdySession r2 = r2.createSession(r3)     // Catch:{ all -> 0x0139 }
            spdySessionUT = r2     // Catch:{ all -> 0x0139 }
            boolean r2 = isGetWuaBeforeSpdySession     // Catch:{ all -> 0x0139 }
            if (r2 != 0) goto L_0x0122
            java.lang.String r2 = com.alibaba.analytics.utils.WuaHelper.getMiniWua()     // Catch:{ all -> 0x0139 }
            com.alibaba.analytics.core.sync.BizRequest.mMiniWua = r2     // Catch:{ all -> 0x0139 }
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r13]     // Catch:{ all -> 0x0139 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0139 }
            java.lang.String r14 = "GetWua by createSession:"
            r10.<init>(r14)     // Catch:{ all -> 0x0139 }
            java.lang.String r14 = com.alibaba.analytics.core.sync.BizRequest.mMiniWua     // Catch:{ all -> 0x0139 }
            r10.append(r14)     // Catch:{ all -> 0x0139 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0139 }
            r3[r5] = r10     // Catch:{ all -> 0x0139 }
            com.alibaba.analytics.utils.Logger.d(r2, r3)     // Catch:{ all -> 0x0139 }
        L_0x0122:
            isGetWuaBeforeSpdySession = r5     // Catch:{ all -> 0x0139 }
            monitor-exit(r4)     // Catch:{ all -> 0x0139 }
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0161 }
            java.lang.String r4 = "createSession"
            r3[r5] = r4     // Catch:{ Exception -> 0x0161 }
            com.alibaba.analytics.utils.Logger.d(r2, r3)     // Catch:{ Exception -> 0x0161 }
            java.lang.Object r2 = Lock_Event     // Catch:{ Exception -> 0x0161 }
            r3 = 60000(0xea60, double:2.9644E-319)
            r2.wait(r3)     // Catch:{ Exception -> 0x0161 }
            goto L_0x0173
        L_0x0139:
            r0 = move-exception
            r2 = r0
            monitor-exit(r4)     // Catch:{ all -> 0x0139 }
            throw r2     // Catch:{ Exception -> 0x0161 }
        L_0x013d:
            org.android.spdy.SpdySession r2 = spdySessionUT     // Catch:{ Exception -> 0x0161 }
            if (r2 == 0) goto L_0x015d
            boolean r2 = bFirstSpdySession     // Catch:{ Exception -> 0x0161 }
            if (r2 == 0) goto L_0x014f
            com.alibaba.analytics.core.Variables r2 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Exception -> 0x0161 }
            boolean r2 = r2.isGzipUpload()     // Catch:{ Exception -> 0x0161 }
            if (r2 == 0) goto L_0x015d
        L_0x014f:
            org.android.spdy.SpdySession r2 = spdySessionUT     // Catch:{ Exception -> 0x0161 }
            sendCustomControlFrame(r2)     // Catch:{ Exception -> 0x0161 }
            java.lang.Object r2 = Lock_Event     // Catch:{ Exception -> 0x0161 }
            r3 = 60000(0xea60, double:2.9644E-319)
            r2.wait(r3)     // Catch:{ Exception -> 0x0161 }
            goto L_0x0173
        L_0x015d:
            closeSession()     // Catch:{ Exception -> 0x0161 }
            goto L_0x0173
        L_0x0161:
            r0 = move-exception
            r2 = r0
            closeSession()     // Catch:{ all -> 0x01fb }
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r15]     // Catch:{ all -> 0x01fb }
            java.lang.String r10 = "CreateSession Exception"
            r4[r5] = r10     // Catch:{ all -> 0x01fb }
            r4[r13] = r2     // Catch:{ all -> 0x01fb }
            com.alibaba.analytics.utils.Logger.e(r3, r4)     // Catch:{ all -> 0x01fb }
        L_0x0173:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01fb }
            r4 = 0
            long r2 = r2 - r8
            r8 = 60000(0xea60, double:2.9644E-319)
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 < 0) goto L_0x01a7
            com.alibaba.analytics.core.Variables r4 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ all -> 0x01fb }
            boolean r4 = r4.isSelfMonitorTurnOn()     // Catch:{ all -> 0x01fb }
            if (r4 == 0) goto L_0x019b
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather r4 = mMonitor     // Catch:{ all -> 0x01fb }
            int r8 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.TNET_REQUEST_TIMEOUT     // Catch:{ all -> 0x01fb }
            r9 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ all -> 0x01fb }
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent r8 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.buildCountEvent(r8, r6, r9)     // Catch:{ all -> 0x01fb }
            r4.onEvent(r8)     // Catch:{ all -> 0x01fb }
        L_0x019b:
            closeSession()     // Catch:{ all -> 0x01fb }
            java.lang.Object[] r4 = new java.lang.Object[r13]     // Catch:{ all -> 0x01fb }
            java.lang.String r8 = "WAIT_TIMEOUT"
            r4[r5] = r8     // Catch:{ all -> 0x01fb }
            com.alibaba.analytics.utils.Logger.w(r6, r4)     // Catch:{ all -> 0x01fb }
        L_0x01a7:
            monitor-exit(r7)     // Catch:{ all -> 0x01fb }
            int r4 = sendBytes
            long r7 = (long) r4
            com.alibaba.analytics.core.sync.BizRequest.recordTraffic(r7)
            java.lang.Object r4 = Lock_Object
            monitor-enter(r4)
            protocolBytes = r6     // Catch:{ all -> 0x01f7 }
            sendBytes = r5     // Catch:{ all -> 0x01f7 }
            monitor-exit(r4)     // Catch:{ all -> 0x01f7 }
            int r4 = errorCode
            r1.errCode = r4
            r1.rt = r2
            java.lang.String r2 = com.alibaba.analytics.core.sync.BizRequest.mResponseAdditionalData
            r1.data = r2
            com.alibaba.analytics.core.sync.BizRequest.mResponseAdditionalData = r6
            int r2 = errorCode
            mErrorCode = r2
            java.lang.String r2 = "PostData"
            java.lang.Object[] r3 = new java.lang.Object[r12]
            java.lang.String r4 = "isSuccess"
            r3[r5] = r4
            boolean r4 = r1.isSuccess()
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r3[r13] = r4
            java.lang.String r4 = "errCode"
            r3[r15] = r4
            int r4 = r1.errCode
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r11] = r4
            java.lang.String r4 = "rt"
            r5 = 4
            r3[r5] = r4
            long r4 = r1.rt
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r5 = 5
            r3[r5] = r4
            com.alibaba.analytics.utils.Logger.d(r2, r3)
            return r1
        L_0x01f7:
            r0 = move-exception
            r1 = r0
            monitor-exit(r4)     // Catch:{ all -> 0x01f7 }
            throw r1
        L_0x01fb:
            r0 = move-exception
            r1 = r0
            monitor-exit(r7)     // Catch:{ all -> 0x01fb }
            throw r1
        L_0x01ff:
            r0 = move-exception
            r1 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x01ff }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.sync.TnetUtil.sendRequest(byte[]):com.alibaba.analytics.core.sync.BizResponse");
    }

    /* access modifiers changed from: private */
    public static void sendCustomControlFrame(SpdySession spdySession) {
        synchronized (Lock_Object) {
            while (spdySession == spdySessionUT && spdySessionUT != null && protocolBytes != null && protocolBytes.length > sendBytes) {
                try {
                    if (protocolBytes.length - sendBytes > 131072) {
                        spdySession.sendCustomControlFrame(-1, -1, -1, 131072, ByteUtils.subBytes(protocolBytes, sendBytes, 131072));
                        sendBytes += 131072;
                    } else {
                        int length = protocolBytes.length - sendBytes;
                        if (length > 0) {
                            spdySession.sendCustomControlFrame(-1, -1, -1, length, ByteUtils.subBytes(protocolBytes, sendBytes, length));
                            sendBytes += length;
                        }
                    }
                } catch (SpdyErrorException e) {
                    Logger.e((String) "", "SpdyErrorException", e);
                    if (e.SpdyErrorGetCode() != -3848) {
                        errorCode = e.SpdyErrorGetCode();
                        closeSession();
                    }
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void closeSession() {
        Logger.d();
        synchronized (Lock_Object) {
            if (spdySessionUT != null) {
                spdySessionUT.closeSession();
            }
            spdySessionUT = null;
            BizRequest.closeOutputStream();
            ZipDictUtils.clear();
        }
        sendCallbackNotify();
    }

    /* access modifiers changed from: private */
    public static void sendCallbackNotify() {
        synchronized (Lock_Event) {
            Lock_Event.notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public static int securityGuardPutSslTicket2(byte[] bArr) {
        return (bArr == null || TnetSecuritySDK.getInstance().putByteArray("accs_ssl_key2_adashx.m.taobao.com", bArr) == 0) ? -1 : 0;
    }

    /* access modifiers changed from: private */
    public static long getResponseBodyLen(byte[] bArr) {
        if (bArr == null || bArr.length < 12) {
            return -1;
        }
        return (long) ByteUtils.bytesToInt(bArr, 1, 3);
    }

    static void initTnetStream() {
        synchronized (Lock_Object) {
            if (spdySessionUT == null) {
                ZipDictUtils.clear();
                BizRequest.initOutputStream();
                bFirstSpdySession = true;
            } else {
                bFirstSpdySession = false;
            }
        }
    }

    static void refreshMiniWua() {
        if (spdySessionUT == null) {
            BizRequest.mMiniWua = WuaHelper.getMiniWua();
            isGetWuaBeforeSpdySession = true;
        }
    }
}
