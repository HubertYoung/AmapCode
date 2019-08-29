package defpackage;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.common.Constants;
import java.util.List;
import java.util.Map;
import org.android.spdy.SessionCb;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bi reason: default package */
/* compiled from: TnetSpdySession */
public final class bi extends p implements SessionCb {
    protected long A = 0;
    protected int B = -1;
    protected l C = null;
    protected an D = null;
    protected n E = null;
    protected String F = null;
    protected ba G = null;
    /* access modifiers changed from: private */
    public int H = 0;
    protected SpdyAgent w;
    protected SpdySession x;
    protected volatile boolean y = false;
    protected long z;

    /* renamed from: bi$a */
    /* compiled from: TnetSpdySession */
    class a extends bf {
        private ay b;
        private o c;
        private int d = 0;
        private long e = 0;

        public a(ay ayVar, o oVar) {
            this.b = ayVar;
            this.c = oVar;
        }

        public final void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
            if (cl.a(1)) {
                cl.a("awcn.TnetSpdySession", "spdyDataChunkRecvCB", this.b.e, "len", Integer.valueOf(spdyByteArray.getDataLength()), "fin", Boolean.valueOf(z));
            }
            this.e += (long) spdyByteArray.getDataLength();
            this.b.k.recDataSize += (long) spdyByteArray.getDataLength();
            if (bi.this.D != null) {
                bi.this.D.b();
            }
            if (this.c != null) {
                ab abVar = defpackage.ab.a.a;
                byte[] byteArray = spdyByteArray.getByteArray();
                int dataLength = spdyByteArray.getDataLength();
                aa a2 = abVar.a(dataLength);
                System.arraycopy(byteArray, 0, a2.a, 0, dataLength);
                a2.c = dataLength;
                spdyByteArray.recycle();
                this.c.onDataReceive(a2, z);
            }
            bi.this.a(32, (ag) null);
        }

        public final void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
            if (cl.a(1)) {
                cl.a("awcn.TnetSpdySession", "spdyStreamCloseCallback", this.b.e, "streamId", Long.valueOf(j), "errorCode", Integer.valueOf(i));
            }
            String str = GenBusCodeService.CODE_SUCESS;
            if (i != 0) {
                this.d = -304;
                str = co.a(-304, String.valueOf(i));
                if (i != -2005) {
                    x.a().a((StatObject) new ExceptionStatistic(-300, str, this.b.k, null));
                }
                cl.d("awcn.TnetSpdySession", "spdyStreamCloseCallback error", this.b.e, "session", bi.this.p, "status code", Integer.valueOf(i), MonitorItemConstants.KEY_URL, this.b.a.f);
            }
            this.b.k.tnetErrorCode = i;
            int i2 = this.d;
            try {
                this.b.k.rspEnd = System.currentTimeMillis();
                if (!this.b.k.isDone.get()) {
                    if (i2 > 0) {
                        this.b.k.ret = 1;
                    }
                    this.b.k.statusCode = i2;
                    this.b.k.msg = str;
                    if (superviseData != null) {
                        this.b.k.rspEnd = superviseData.responseEnd;
                        this.b.k.sendBeforeTime = superviseData.sendStart - superviseData.requestStart;
                        this.b.k.sendDataTime = superviseData.sendEnd - this.b.k.sendStart;
                        this.b.k.firstDataTime = superviseData.responseStart - superviseData.sendEnd;
                        this.b.k.recDataTime = superviseData.responseEnd - superviseData.responseStart;
                        this.b.k.sendDataSize = (long) (superviseData.bodySize + superviseData.compressSize);
                        this.b.k.recDataSize = this.e + ((long) superviseData.recvUncompressSize);
                        this.b.k.reqHeadInflateSize = (long) superviseData.uncompressSize;
                        this.b.k.reqHeadDeflateSize = (long) superviseData.compressSize;
                        this.b.k.reqBodyInflateSize = (long) superviseData.bodySize;
                        this.b.k.reqBodyDeflateSize = (long) superviseData.bodySize;
                        this.b.k.rspHeadDeflateSize = (long) superviseData.recvCompressSize;
                        this.b.k.rspHeadInflateSize = (long) superviseData.recvUncompressSize;
                        this.b.k.rspBodyDeflateSize = (long) superviseData.recvBodySize;
                        this.b.k.rspBodyInflateSize = this.e;
                        if (this.b.k.contentLength == 0) {
                            this.b.k.contentLength = (long) superviseData.originContentLength;
                        }
                        bi.this.q.recvSizeCount += (long) (superviseData.recvBodySize + superviseData.recvCompressSize);
                        bi.this.q.sendSizeCount += (long) (superviseData.bodySize + superviseData.compressSize);
                    }
                }
            } catch (Exception unused) {
            }
            if (this.c != null) {
                this.c.onFinish(this.d, str, this.b.k);
            }
            if (i == -2004) {
                if (!bi.this.y) {
                    bi.this.d();
                }
                if (bi.h(bi.this) >= 2) {
                    bm bmVar = new bm();
                    bmVar.a = false;
                    bu.a().a(bi.this.d, bi.this.k, bmVar);
                    bi.this.a(true);
                }
            }
        }

        public final void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
            this.b.k.firstDataTime = System.currentTimeMillis() - this.b.k.sendStart;
            this.d = cq.d(map);
            bi.this.H = 0;
            cl.b("awcn.TnetSpdySession", "", this.b.e, "statusCode", Integer.valueOf(this.d));
            cl.b("awcn.TnetSpdySession", "", this.b.e, "response headers", map);
            if (this.c != null) {
                this.c.onResponseCode(this.d, cq.a(map));
            }
            bi.this.a(16, (ag) null);
            this.b.k.contentEncoding = cq.a(map, (String) TransportConstants.KEY_X_CONTENT_ENCODING);
            this.b.k.contentType = cq.a(map, (String) "Content-Type");
            this.b.k.contentLength = (long) cq.b(map);
            this.b.k.serverRT = cq.c(map);
            bi.this.a(this.b, this.d);
            bi.this.a(this.b, map);
            if (bi.this.D != null) {
                bi.this.D.b();
            }
        }
    }

    public final void bioPingRecvCallback(SpdySession spdySession, int i) {
    }

    static /* synthetic */ int h(bi biVar) {
        int i = biVar.H + 1;
        biVar.H = i;
        return i;
    }

    public bi(Context context, af afVar) {
        super(context, afVar);
    }

    public final void a(t tVar) {
        if (tVar != null) {
            this.C = tVar.f;
            this.E = tVar.d;
            if (tVar.b) {
                this.q.isKL = 1;
                this.t = true;
                this.D = tVar.e;
                if (this.D == null) {
                    this.D = new am();
                }
            }
        }
        if (j.h() && this.D == null) {
            this.D = new ao();
        }
    }

    public final void a(int i) {
        this.B = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0111 A[Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0125 A[Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0176 A[Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01b1 A[Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01be A[Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.aw a(defpackage.ay r22, defpackage.o r23) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r3 = r23
            az r4 = defpackage.az.a
            if (r2 == 0) goto L_0x000d
            anet.channel.statist.RequestStatistic r5 = r2.k
            goto L_0x0015
        L_0x000d:
            anet.channel.statist.RequestStatistic r5 = new anet.channel.statist.RequestStatistic
            java.lang.String r6 = r1.d
            r7 = 0
            r5.<init>(r6, r7)
        L_0x0015:
            anet.channel.entity.ConnType r6 = r1.j
            r5.setConnType(r6)
            long r6 = r5.start
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 != 0) goto L_0x002a
            long r6 = java.lang.System.currentTimeMillis()
            r5.reqStart = r6
            r5.start = r6
        L_0x002a:
            java.lang.String r6 = r1.f
            int r7 = r1.g
            r5.setIPAndPort(r6, r7)
            bo r6 = r1.k
            int r6 = r6.c()
            r5.ipRefer = r6
            bo r6 = r1.k
            int r6 = r6.b()
            r5.ipType = r6
            java.lang.String r6 = r1.l
            r5.unit = r6
            if (r2 != 0) goto L_0x0051
            r2 = -102(0xffffffffffffff9a, float:NaN)
            java.lang.String r6 = defpackage.co.a(r2)
            r3.onFinish(r2, r6, r5)
            return r4
        L_0x0051:
            r6 = 0
            r7 = 2
            org.android.spdy.SpdySession r8 = r1.x     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 == 0) goto L_0x01d3
            int r8 = r1.n     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 == 0) goto L_0x0060
            int r8 = r1.n     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r9 = 4
            if (r8 != r9) goto L_0x01d3
        L_0x0060:
            boolean r8 = r1.m     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 == 0) goto L_0x006b
            java.lang.String r8 = r1.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            int r9 = r1.g     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r2.a(r8, r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x006b:
            anet.channel.entity.ConnType r8 = r1.j     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            boolean r8 = r8.c()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r2.a(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.net.URL r10 = r22.b()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            boolean r8 = defpackage.cl.a(r7)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r15 = 1
            if (r8 == 0) goto L_0x00be
            java.lang.String r8 = "awcn.TnetSpdySession"
            java.lang.String r9 = ""
            java.lang.String r11 = r2.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.Object[] r12 = new java.lang.Object[r7]     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = "request URL"
            r12[r6] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = r10.toString()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r12[r15] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            defpackage.cl.b(r8, r9, r11, r12)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r8 = "awcn.TnetSpdySession"
            java.lang.String r9 = ""
            java.lang.String r11 = r2.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.Object[] r12 = new java.lang.Object[r7]     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = "request Method"
            r12[r6] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = r2.b     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r12[r15] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            defpackage.cl.b(r8, r9, r11, r12)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r8 = "awcn.TnetSpdySession"
            java.lang.String r9 = ""
            java.lang.String r11 = r2.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.Object[] r12 = new java.lang.Object[r7]     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = "request headers"
            r12[r6] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map<java.lang.String, java.lang.String> r13 = r2.c     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map r13 = java.util.Collections.unmodifiableMap(r13)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r12[r15] = r13     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            defpackage.cl.b(r8, r9, r11, r12)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x00be:
            java.lang.String r8 = r1.h     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 != 0) goto L_0x00f0
            int r8 = r1.i     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 > 0) goto L_0x00cb
            goto L_0x00f0
        L_0x00cb:
            org.android.spdy.SpdyRequest r8 = new org.android.spdy.SpdyRequest     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r11 = r10.getHost()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            int r12 = r10.getPort()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r13 = r1.h     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            int r14 = r1.i     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = r2.b     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            org.android.spdy.RequestPriority r16 = org.android.spdy.RequestPriority.DEFAULT_PRIORITY     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r17 = -1
            int r6 = r2.g     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r19 = 0
            r18 = r9
            r9 = r8
            r7 = 1
            r15 = r18
            r18 = r6
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r6 = r8
            goto L_0x00fe
        L_0x00f0:
            r7 = 1
            org.android.spdy.SpdyRequest r6 = new org.android.spdy.SpdyRequest     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r11 = r2.b     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            org.android.spdy.RequestPriority r12 = org.android.spdy.RequestPriority.DEFAULT_PRIORITY     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r13 = -1
            int r14 = r2.g     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r9 = r6
            r9.<init>(r10, r11, r12, r13, r14)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x00fe:
            int r8 = r2.h     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r6.setRequestRdTimeoutMs(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map<java.lang.String, java.lang.String> r8 = r2.c     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map r8 = java.util.Collections.unmodifiableMap(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = "Host"
            boolean r9 = r8.containsKey(r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r9 != 0) goto L_0x0125
            r6.addHeaders(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r8 = ":host"
            boolean r9 = r1.m     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r9 == 0) goto L_0x011d
            java.lang.String r9 = r1.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            goto L_0x0121
        L_0x011d:
            cs r9 = r2.a     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = r9.b     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x0121:
            r6.addHeader(r8, r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            goto L_0x0146
        L_0x0125:
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map<java.lang.String, java.lang.String> r9 = r2.c     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.util.Map r9 = java.util.Collections.unmodifiableMap(r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r8.<init>(r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = "Host"
            java.lang.Object r9 = r8.remove(r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r10 = ":host"
            boolean r11 = r1.m     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r11 == 0) goto L_0x0140
            java.lang.String r9 = r1.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x0140:
            r8.put(r10, r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r6.addHeaders(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x0146:
            byte[] r8 = r22.c()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            org.android.spdy.SpdyDataProvider r9 = new org.android.spdy.SpdyDataProvider     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r9.<init>(r8)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.RequestStatistic r8 = r2.k     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r8.sendStart = r10     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.RequestStatistic r8 = r2.k     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.RequestStatistic r10 = r2.k     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            long r10 = r10.sendStart     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.RequestStatistic r12 = r2.k     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            long r12 = r12.start     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r14 = 0
            long r10 = r10 - r12
            r8.processTime = r10     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            org.android.spdy.SpdySession r8 = r1.x     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            bi$a r10 = new bi$a     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r10.<init>(r2, r3)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            int r6 = r8.submitRequest(r6, r9, r1, r10)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            boolean r8 = defpackage.cl.a(r7)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            if (r8 == 0) goto L_0x018d
            java.lang.String r8 = "awcn.TnetSpdySession"
            java.lang.String r9 = ""
            java.lang.String r10 = r2.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r11 = 2
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r11 = "streamId"
            r13 = 0
            r12[r13] = r11     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r6)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r12[r7] = r11     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            defpackage.cl.a(r8, r9, r10, r12)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
        L_0x018d:
            az r7 = new az     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            org.android.spdy.SpdySession r8 = r1.x     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            java.lang.String r9 = r2.e     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r7.<init>(r8, r6, r9)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.SessionStatistic r4 = r1.q     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            long r8 = r4.requestCount     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            r10 = 1
            long r8 = r8 + r10
            r4.requestCount = r8     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            anet.channel.statist.SessionStatistic r4 = r1.q     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            long r8 = r4.stdRCount     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            r6 = 0
            long r8 = r8 + r10
            r4.stdRCount = r8     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            r1.z = r8     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            an r4 = r1.D     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            if (r4 == 0) goto L_0x01b6
            an r4 = r1.D     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            r4.b()     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
        L_0x01b6:
            anet.channel.entity.ConnType r4 = r1.j     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            boolean r4 = r4.a()     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            if (r4 == 0) goto L_0x01cb
            anet.channel.statist.RequestStatistic r2 = r2.k     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            java.lang.String r4 = "QuicConnectionID"
            org.android.spdy.SpdySession r6 = r1.x     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            java.lang.String r6 = r6.getQuicConnectionID()     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
            r2.putExtra(r4, r6)     // Catch:{ SpdyErrorException -> 0x01cf, Exception -> 0x01cd }
        L_0x01cb:
            r4 = r7
            goto L_0x0222
        L_0x01cd:
            r4 = r7
            goto L_0x01df
        L_0x01cf:
            r0 = move-exception
            r2 = r0
            r4 = r7
            goto L_0x01eb
        L_0x01d3:
            r6 = -301(0xfffffffffffffed3, float:NaN)
            java.lang.String r7 = defpackage.co.a(r6)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            anet.channel.statist.RequestStatistic r2 = r2.k     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            r3.onFinish(r6, r7, r2)     // Catch:{ SpdyErrorException -> 0x01e9, Exception -> 0x01df }
            goto L_0x0222
        L_0x01df:
            r2 = -101(0xffffffffffffff9b, float:NaN)
            java.lang.String r6 = defpackage.co.a(r2)
            r3.onFinish(r2, r6, r5)
            goto L_0x0222
        L_0x01e9:
            r0 = move-exception
            r2 = r0
        L_0x01eb:
            int r6 = r2.SpdyErrorGetCode()
            r7 = -1104(0xfffffffffffffbb0, float:NaN)
            if (r6 == r7) goto L_0x01fb
            int r6 = r2.SpdyErrorGetCode()
            r7 = -1103(0xfffffffffffffbb1, float:NaN)
            if (r6 != r7) goto L_0x0211
        L_0x01fb:
            java.lang.String r6 = "awcn.TnetSpdySession"
            java.lang.String r7 = "Send request on closed session!!!"
            java.lang.String r8 = r1.p
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]
            defpackage.cl.d(r6, r7, r8, r9)
            r6 = 6
            ag r7 = new ag
            r8 = 2
            r7.<init>(r8)
            r1.b(r6, r7)
        L_0x0211:
            int r2 = r2.SpdyErrorGetCode()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r6 = -300(0xfffffffffffffed4, float:NaN)
            java.lang.String r2 = defpackage.co.a(r6, r2)
            r3.onFinish(r6, r2, r5)
        L_0x0222:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bi.a(ay, o):aw");
    }

    public final void a(int i, byte[] bArr) {
        try {
            if (this.C != null) {
                cl.d("awcn.TnetSpdySession", "sendCustomFrame", this.p, Constants.KEY_DATA_ID, Integer.valueOf(i), "type", Integer.valueOf(200));
                if (this.n != 4 || this.x == null) {
                    String str = this.p;
                    StringBuilder sb = new StringBuilder("sendCustomFrame con invalid mStatus:");
                    sb.append(this.n);
                    cl.d("awcn.TnetSpdySession", "sendCustomFrame", str, sb.toString());
                    a(i, -301, true, "session invalid");
                } else if (bArr == null || bArr.length <= 16384) {
                    this.x.sendCustomControlFrame(i, 200, 0, bArr == null ? 0 : bArr.length, bArr);
                    this.q.requestCount++;
                    this.q.cfRCount++;
                    this.z = System.currentTimeMillis();
                    if (this.D != null) {
                        this.D.b();
                    }
                } else {
                    a(i, -303, false, null);
                }
            }
        } catch (SpdyErrorException e) {
            cl.e("awcn.TnetSpdySession", "sendCustomFrame error", this.p, new Object[0]);
            StringBuilder sb2 = new StringBuilder("SpdyErrorException: ");
            sb2.append(e.toString());
            a(i, -300, true, sb2.toString());
        } catch (Exception e2) {
            cl.e("awcn.TnetSpdySession", "sendCustomFrame error", this.p, new Object[0]);
            a(i, -101, true, e2.toString());
        }
    }

    private void a(int i, int i2, boolean z2, String str) {
        if (this.C != null) {
            this.C.onException(i, i2, z2, str);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x01a4 A[Catch:{ Exception -> 0x0062, Throwable -> 0x01e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01bb A[Catch:{ Exception -> 0x0062, Throwable -> 0x01e2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r20 = this;
            r9 = r20
            int r0 = r9.n
            r10 = 1
            if (r0 == r10) goto L_0x01f1
            int r0 = r9.n
            if (r0 == 0) goto L_0x01f1
            int r0 = r9.n
            r11 = 4
            if (r0 != r11) goto L_0x0012
            goto L_0x01f1
        L_0x0012:
            r12 = 2
            r13 = 0
            r14 = 0
            org.android.spdy.SpdyAgent r0 = r9.w     // Catch:{ Throwable -> 0x01e2 }
            if (r0 != 0) goto L_0x006b
            org.android.spdy.SpdyAgent.enableDebug = r14     // Catch:{ Throwable -> 0x01e2 }
            android.content.Context r0 = r9.a     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SpdyVersion r1 = org.android.spdy.SpdyVersion.SPDY3     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SpdySessionKind r2 = org.android.spdy.SpdySessionKind.NONE_SESSION     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SpdyAgent r0 = org.android.spdy.SpdyAgent.getInstance(r0, r1, r2)     // Catch:{ Throwable -> 0x01e2 }
            r9.w = r0     // Catch:{ Throwable -> 0x01e2 }
            ba r0 = r9.G     // Catch:{ Throwable -> 0x01e2 }
            if (r0 == 0) goto L_0x003d
            ba r0 = r9.G     // Catch:{ Throwable -> 0x01e2 }
            boolean r0 = r0.a()     // Catch:{ Throwable -> 0x01e2 }
            if (r0 != 0) goto L_0x003d
            org.android.spdy.SpdyAgent r0 = r9.w     // Catch:{ Throwable -> 0x01e2 }
            bi$3 r1 = new bi$3     // Catch:{ Throwable -> 0x01e2 }
            r1.<init>()     // Catch:{ Throwable -> 0x01e2 }
            r0.setAccsSslCallback(r1)     // Catch:{ Throwable -> 0x01e2 }
        L_0x003d:
            boolean r0 = defpackage.j.f()     // Catch:{ Throwable -> 0x01e2 }
            if (r0 != 0) goto L_0x006b
            org.android.spdy.SpdyAgent r0 = r9.w     // Catch:{ Exception -> 0x0062 }
            java.lang.Class r0 = r0.getClass()     // Catch:{ Exception -> 0x0062 }
            java.lang.String r1 = "disableHeaderCache"
            java.lang.Class[] r2 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x0062 }
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r1, r2)     // Catch:{ Exception -> 0x0062 }
            org.android.spdy.SpdyAgent r1 = r9.w     // Catch:{ Exception -> 0x0062 }
            java.lang.Object[] r2 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0062 }
            r0.invoke(r1, r2)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r0 = "awcn.TnetSpdySession"
            java.lang.String r1 = "tnet disableHeaderCache"
            java.lang.Object[] r2 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x0062 }
            defpackage.cl.b(r0, r1, r13, r2)     // Catch:{ Exception -> 0x0062 }
            goto L_0x006b
        L_0x0062:
            java.lang.String r0 = "awcn.TnetSpdySession"
            java.lang.String r1 = "tnet disableHeaderCache"
            java.lang.Object[] r2 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x01e2 }
            defpackage.cl.e(r0, r1, r13, r2)     // Catch:{ Throwable -> 0x01e2 }
        L_0x006b:
            defpackage.ct.a()     // Catch:{ Throwable -> 0x01e2 }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r6 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r0 = "awcn.TnetSpdySession"
            java.lang.String r1 = "connect"
            java.lang.String r2 = r9.p     // Catch:{ Throwable -> 0x01e2 }
            r3 = 14
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = "host"
            r3[r14] = r4     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = r9.c     // Catch:{ Throwable -> 0x01e2 }
            r3[r10] = r4     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = "ip"
            r3[r12] = r4     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = r9.f     // Catch:{ Throwable -> 0x01e2 }
            r15 = 3
            r3[r15] = r4     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = "port"
            r3[r11] = r4     // Catch:{ Throwable -> 0x01e2 }
            r4 = 5
            int r5 = r9.g     // Catch:{ Throwable -> 0x01e2 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x01e2 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            r4 = 6
            java.lang.String r5 = "sessionId"
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            r4 = 7
            r3[r4] = r6     // Catch:{ Throwable -> 0x01e2 }
            r4 = 8
            java.lang.String r5 = "SpdyProtocol,"
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            r4 = 9
            anet.channel.entity.ConnType r5 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = "proxyIp,"
            r16 = 10
            r3[r16] = r4     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = r9.h     // Catch:{ Throwable -> 0x01e2 }
            r17 = 11
            r3[r17] = r4     // Catch:{ Throwable -> 0x01e2 }
            r4 = 12
            java.lang.String r5 = "proxyPort,"
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            r4 = 13
            int r5 = r9.i     // Catch:{ Throwable -> 0x01e2 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x01e2 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x01e2 }
            defpackage.cl.d(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SessionInfo r8 = new org.android.spdy.SessionInfo     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r1 = r9.f     // Catch:{ Throwable -> 0x01e2 }
            int r2 = r9.g     // Catch:{ Throwable -> 0x01e2 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01e2 }
            r0.<init>()     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r3 = r9.c     // Catch:{ Throwable -> 0x01e2 }
            r0.append(r3)     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r3 = "_"
            r0.append(r3)     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r3 = r9.F     // Catch:{ Throwable -> 0x01e2 }
            r0.append(r3)     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r4 = r9.h     // Catch:{ Throwable -> 0x01e2 }
            int r5 = r9.i     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.entity.ConnType r0 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            int r7 = r0.c     // Catch:{ Throwable -> 0x01e2 }
            r0 = r8
            r18 = r7
            r7 = r9
            r11 = r8
            r8 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x01e2 }
            int r0 = r9.r     // Catch:{ Throwable -> 0x01e2 }
            float r0 = (float) r0     // Catch:{ Throwable -> 0x01e2 }
            float r1 = defpackage.db.b()     // Catch:{ Throwable -> 0x01e2 }
            float r0 = r0 * r1
            int r0 = (int) r0     // Catch:{ Throwable -> 0x01e2 }
            r11.setConnectionTimeoutMs(r0)     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.entity.ConnType r0 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r1 = "auto"
            java.lang.String r0 = r0.d     // Catch:{ Throwable -> 0x01e2 }
            boolean r0 = r1.equals(r0)     // Catch:{ Throwable -> 0x01e2 }
            if (r0 != 0) goto L_0x0188
            anet.channel.entity.ConnType r0 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            boolean r0 = r0.a()     // Catch:{ Throwable -> 0x01e2 }
            if (r0 != 0) goto L_0x0188
            anet.channel.entity.ConnType r0 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            int r0 = r0.c     // Catch:{ Throwable -> 0x01e2 }
            r1 = 40
            if (r0 != r1) goto L_0x012c
            r0 = 1
            goto L_0x012d
        L_0x012c:
            r0 = 0
        L_0x012d:
            if (r0 == 0) goto L_0x0130
            goto L_0x0188
        L_0x0130:
            int r0 = r9.B     // Catch:{ Throwable -> 0x01e2 }
            if (r0 < 0) goto L_0x013a
            int r0 = r9.B     // Catch:{ Throwable -> 0x01e2 }
            r11.setPubKeySeqNum(r0)     // Catch:{ Throwable -> 0x01e2 }
            goto L_0x0194
        L_0x013a:
            anet.channel.entity.ConnType r0 = r9.j     // Catch:{ Throwable -> 0x01e2 }
            ba r1 = r9.G     // Catch:{ Throwable -> 0x01e2 }
            if (r1 == 0) goto L_0x0147
            ba r1 = r9.G     // Catch:{ Throwable -> 0x01e2 }
            boolean r1 = r1.a()     // Catch:{ Throwable -> 0x01e2 }
            goto L_0x0148
        L_0x0147:
            r1 = 1
        L_0x0148:
            r2 = -1
            java.lang.String r3 = "cdn"
            java.lang.String r4 = r0.d     // Catch:{ Throwable -> 0x01e2 }
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x01e2 }
            if (r3 == 0) goto L_0x0155
            r2 = 1
            goto L_0x0180
        L_0x0155:
            anet.channel.entity.ENV r3 = defpackage.m.d()     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.entity.ENV r4 = anet.channel.entity.ENV.TEST     // Catch:{ Throwable -> 0x01e2 }
            if (r3 != r4) goto L_0x015f
            r2 = 0
            goto L_0x0180
        L_0x015f:
            java.lang.String r3 = "open"
            java.lang.String r4 = r0.d     // Catch:{ Throwable -> 0x01e2 }
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x01e2 }
            if (r3 == 0) goto L_0x0171
            if (r1 == 0) goto L_0x016e
            r2 = 11
            goto L_0x0180
        L_0x016e:
            r2 = 10
            goto L_0x0180
        L_0x0171:
            java.lang.String r3 = "acs"
            java.lang.String r0 = r0.d     // Catch:{ Throwable -> 0x01e2 }
            boolean r0 = r3.equals(r0)     // Catch:{ Throwable -> 0x01e2 }
            if (r0 == 0) goto L_0x0180
            if (r1 == 0) goto L_0x017f
            r2 = 4
            goto L_0x0180
        L_0x017f:
            r2 = 3
        L_0x0180:
            r9.B = r2     // Catch:{ Throwable -> 0x01e2 }
            int r0 = r9.B     // Catch:{ Throwable -> 0x01e2 }
            r11.setPubKeySeqNum(r0)     // Catch:{ Throwable -> 0x01e2 }
            goto L_0x0194
        L_0x0188:
            boolean r0 = r9.m     // Catch:{ Throwable -> 0x01e2 }
            if (r0 == 0) goto L_0x018f
            java.lang.String r0 = r9.e     // Catch:{ Throwable -> 0x01e2 }
            goto L_0x0191
        L_0x018f:
            java.lang.String r0 = r9.d     // Catch:{ Throwable -> 0x01e2 }
        L_0x0191:
            r11.setCertHost(r0)     // Catch:{ Throwable -> 0x01e2 }
        L_0x0194:
            org.android.spdy.SpdyAgent r0 = r9.w     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SpdySession r0 = r0.createSession(r11)     // Catch:{ Throwable -> 0x01e2 }
            r9.x = r0     // Catch:{ Throwable -> 0x01e2 }
            org.android.spdy.SpdySession r0 = r9.x     // Catch:{ Throwable -> 0x01e2 }
            int r0 = r0.getRefCount()     // Catch:{ Throwable -> 0x01e2 }
            if (r0 <= r10) goto L_0x01bb
            java.lang.String r0 = "awcn.TnetSpdySession"
            java.lang.String r1 = "get session ref count > 1!!!"
            java.lang.String r2 = r9.p     // Catch:{ Throwable -> 0x01e2 }
            java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x01e2 }
            defpackage.cl.d(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x01e2 }
            ag r0 = new ag     // Catch:{ Throwable -> 0x01e2 }
            r0.<init>(r10)     // Catch:{ Throwable -> 0x01e2 }
            r9.b(r14, r0)     // Catch:{ Throwable -> 0x01e2 }
            r20.m()     // Catch:{ Throwable -> 0x01e2 }
            return
        L_0x01bb:
            r9.b(r10, r13)     // Catch:{ Throwable -> 0x01e2 }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01e2 }
            r9.z = r0     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.statist.SessionStatistic r0 = r9.q     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r1 = r9.h     // Catch:{ Throwable -> 0x01e2 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x01e2 }
            r1 = r1 ^ r10
            r0.isProxy = r1     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.statist.SessionStatistic r0 = r9.q     // Catch:{ Throwable -> 0x01e2 }
            java.lang.String r1 = "false"
            r0.isTunnel = r1     // Catch:{ Throwable -> 0x01e2 }
            anet.channel.statist.SessionStatistic r0 = r9.q     // Catch:{ Throwable -> 0x01e2 }
            boolean r1 = defpackage.m.h()     // Catch:{ Throwable -> 0x01e2 }
            r0.isBackground = r1     // Catch:{ Throwable -> 0x01e2 }
            r0 = 0
            r9.A = r0     // Catch:{ Throwable -> 0x01e2 }
            return
        L_0x01e2:
            r9.b(r12, r13)
            java.lang.String r0 = "awcn.TnetSpdySession"
            java.lang.String r1 = "connect exception "
            java.lang.String r2 = r9.p
            java.lang.Object[] r3 = new java.lang.Object[r14]
            defpackage.cl.e(r0, r1, r2, r3)
            return
        L_0x01f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bi.a():void");
    }

    public final void b() {
        cl.d("awcn.TnetSpdySession", "force close!", this.p, "session", this);
        b(7, null);
        try {
            if (this.D != null) {
                this.D.a();
                this.D = null;
            }
            if (this.x != null) {
                this.x.closeSession();
            }
        } catch (Exception unused) {
        }
    }

    public final void k() {
        this.y = false;
    }

    public final Runnable c() {
        return new Runnable() {
            public final void run() {
                if (bi.this.y) {
                    cl.d("awcn.TnetSpdySession", "send msg time out!", bi.this.p, "pingUnRcv:", Boolean.valueOf(bi.this.y));
                    try {
                        bi.this.a(2048, (ag) null);
                        if (bi.this.q != null) {
                            bi.this.q.closeReason = "ping time out";
                        }
                        bm bmVar = new bm();
                        bmVar.a = false;
                        bu.a().a(bi.this.d, bi.this.k, bmVar);
                        bi.this.a(true);
                    } catch (Exception unused) {
                    }
                }
            }
        };
    }

    public final void d() {
        if (cl.a(1)) {
            cl.a("awcn.TnetSpdySession", "ping", this.p, "host", this.c, "thread", Thread.currentThread().getName());
        }
        try {
            if (this.x == null) {
                if (this.q != null) {
                    this.q.closeReason = "session null";
                }
                StringBuilder sb = new StringBuilder();
                sb.append(this.c);
                sb.append(" session null");
                cl.d("awcn.TnetSpdySession", sb.toString(), this.p, new Object[0]);
                b();
            } else if (this.n == 0 || this.n == 4) {
                a(64, (ag) null);
                this.y = true;
                this.q.ppkgCount++;
                this.x.submitPing();
                if (cl.a(1)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.c);
                    sb2.append(" submit ping ms:");
                    sb2.append(System.currentTimeMillis() - this.z);
                    sb2.append(" force:true");
                    cl.a("awcn.TnetSpdySession", sb2.toString(), this.p, new Object[0]);
                }
                l();
                this.z = System.currentTimeMillis();
                if (this.D != null) {
                    this.D.b();
                }
            }
        } catch (SpdyErrorException e) {
            if (e.SpdyErrorGetCode() == -1104 || e.SpdyErrorGetCode() == -1103) {
                cl.d("awcn.TnetSpdySession", "Send request on closed session!!!", this.p, new Object[0]);
                b(6, new ag(2));
            }
            cl.e("awcn.TnetSpdySession", "ping", this.p, new Object[0]);
        } catch (Exception unused) {
            cl.e("awcn.TnetSpdySession", "ping", this.p, new Object[0]);
        }
    }

    private void m() {
        if (this.E != null) {
            this.E.auth(this, new defpackage.n.a() {
                public final void a() {
                    bi.this.b(4, null);
                    bi.this.z = System.currentTimeMillis();
                    if (bi.this.D != null) {
                        bi.this.D.a(bi.this);
                    }
                    bi.this.q.ret = 1;
                    cl.a("awcn.TnetSpdySession", "spdyOnStreamResponse", bi.this.p, "authTime", Long.valueOf(bi.this.q.authTime));
                    if (bi.this.A > 0) {
                        bi.this.q.authTime = System.currentTimeMillis() - bi.this.A;
                    }
                }

                public final void a(int i) {
                    bi.this.b(5, null);
                    if (bi.this.q != null) {
                        bi.this.q.closeReason = "Accs_Auth_Fail:".concat(String.valueOf(i));
                        bi.this.q.errorCode = (long) i;
                    }
                    bi.this.b();
                }
            });
            return;
        }
        b(4, null);
        this.q.ret = 1;
        if (this.D != null) {
            this.D.a(this);
        }
    }

    public final boolean e() {
        return this.n == 4;
    }

    public final void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        this.q.connectionTime = (long) superviseConnectInfo.connectTime;
        this.q.sslTime = (long) superviseConnectInfo.handshakeTime;
        this.q.sslCalTime = (long) superviseConnectInfo.doHandshakeTime;
        this.q.netType = NetworkStatusHelper.b();
        this.A = System.currentTimeMillis();
        b(0, new ag(1));
        m();
        cl.d("awcn.TnetSpdySession", "spdySessionConnectCB connect", this.p, "connectTime", Integer.valueOf(superviseConnectInfo.connectTime), "sslTime:", Integer.valueOf(superviseConnectInfo.handshakeTime));
    }

    public final void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        if (cl.a(2)) {
            cl.b("awcn.TnetSpdySession", "ping receive", this.p, "Host", this.c, "id", Long.valueOf(j));
        }
        if (j >= 0) {
            this.y = false;
            this.H = 0;
            if (this.D != null) {
                this.D.b();
            }
            a(128, (ag) null);
        }
    }

    public final void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        cl.d("awcn.TnetSpdySession", "[spdyCustomControlFrameRecvCallback]", this.p, "len", Integer.valueOf(i4), "frameCb", this.C);
        if (cl.a(1) && i4 < 512) {
            String str = "";
            for (byte b : bArr) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Integer.toHexString(b & 255));
                sb.append(Token.SEPARATOR);
                str = sb.toString();
            }
            cl.d("awcn.TnetSpdySession", null, this.p, "str", str);
        }
        if (this.C != null) {
            this.C.onDataReceive(this, bArr, i, i2);
        } else {
            cl.d("awcn.TnetSpdySession", "AccsFrameCb is null", this.p, new Object[0]);
            x.a().a((StatObject) new ExceptionStatistic(-105, null, "rt"));
        }
        this.q.inceptCount++;
        if (this.D != null) {
            this.D.b();
        }
    }

    public final void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception unused) {
                cl.e("awcn.TnetSpdySession", "[spdySessionFailedError]session clean up failed!", null, new Object[0]);
            }
        }
        b(2, new ag(256, i, "tnet connect fail"));
        cl.d("awcn.TnetSpdySession", null, this.p, " errorId:", Integer.valueOf(i));
        this.q.errorCode = (long) i;
        this.q.ret = 0;
        this.q.netType = NetworkStatusHelper.b();
        x.a().a((StatObject) this.q);
        x.a().a(this.q.getAlarmObject());
    }

    public final void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        cl.d("awcn.TnetSpdySession", "spdySessionCloseCallback", this.p, " errorCode:", Integer.valueOf(i));
        if (this.D != null) {
            this.D.a();
            this.D = null;
        }
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception unused) {
                cl.e("awcn.TnetSpdySession", "session clean up failed!", null, new Object[0]);
            }
        }
        b(6, new ag(2));
        if (superviseConnectInfo != null) {
            this.q.requestCount = (long) superviseConnectInfo.reused_counter;
            this.q.liveTime = (long) superviseConnectInfo.keepalive_period_second;
            try {
                if (this.j.a()) {
                    this.q.extra = new JSONObject();
                    this.q.extra.put("QuicConnectionID", this.x.getQuicConnectionID());
                    this.q.extra.put("retransmissionRate", superviseConnectInfo.retransmissionRate);
                    this.q.extra.put("lossRate", superviseConnectInfo.lossRate);
                    this.q.extra.put("tlpCount", superviseConnectInfo.tlpCount);
                    this.q.extra.put("rtoCount", superviseConnectInfo.rtoCount);
                }
            } catch (JSONException unused2) {
            }
        }
        if (this.q.errorCode == 0) {
            this.q.errorCode = (long) i;
        }
        this.q.lastPingInterval = (int) (System.currentTimeMillis() - this.z);
        x.a().a((StatObject) this.q);
        x.a().a(this.q.getAlarmObject());
    }

    public final void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        cl.d("awcn.TnetSpdySession", "spdyCustomControlFrameFailCallback", this.p, Constants.KEY_DATA_ID, Integer.valueOf(i));
        a(i, i2, true, "tnet error");
    }

    public final byte[] getSSLMeta(SpdySession spdySession) {
        String domain = spdySession.getDomain();
        byte[] bArr = null;
        if (TextUtils.isEmpty(domain)) {
            cl.b("awcn.TnetSpdySession", "get sslticket host is null", null, new Object[0]);
            return null;
        }
        try {
            if (this.G != null) {
                bArr = this.G.a(this.a, "accs_ssl_key2_".concat(String.valueOf(domain)));
            }
        } catch (Throwable unused) {
            cl.e("awcn.TnetSpdySession", "getSSLMeta", null, new Object[0]);
        }
        return bArr;
    }

    public final int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        String domain = spdySession.getDomain();
        int i = -1;
        if (TextUtils.isEmpty(domain)) {
            return -1;
        }
        int i2 = 0;
        try {
            if (this.G != null) {
                if (!this.G.a(this.a, "accs_ssl_key2_".concat(String.valueOf(domain)), bArr)) {
                    i2 = -1;
                }
                i = i2;
            }
        } catch (Throwable unused) {
            cl.e("awcn.TnetSpdySession", "putSSLMeta", null, new Object[0]);
        }
        return i;
    }

    public final void a(k kVar) {
        if (kVar != null) {
            this.F = kVar.b;
            this.G = kVar.d;
        }
    }
}
