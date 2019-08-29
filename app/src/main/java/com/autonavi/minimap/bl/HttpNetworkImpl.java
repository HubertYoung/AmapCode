package com.autonavi.minimap.bl;

import android.content.Context;
import android.util.SparseArray;
import com.amap.bundle.aosservice.request.AosFileUploadRequest;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosHeadRequest;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosInputStreamResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.bl.net.HttpRequest;
import com.autonavi.minimap.bl.net.HttpResponse;
import com.autonavi.minimap.bl.net.IHttpNetwork;
import com.autonavi.minimap.bl.net.IHttpResponseCallback;
import com.autonavi.minimap.bl.net.IHttpUploadCallback;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.Closeable;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpNetworkImpl implements IHttpNetwork {
    protected Context a;
    protected AtomicInteger b = new AtomicInteger(0);
    protected ctg c;
    protected SparseArray<SparseArray<a>> d = new SparseArray<>();
    protected InternalResponseCallback e = new InternalResponseCallback(this, 0);

    class InternalResponseCallback implements AosResponseCallback<AosInputStreamResponse>, it {
        private InternalResponseCallback() {
        }

        /* synthetic */ InternalResponseCallback(HttpNetworkImpl httpNetworkImpl, byte b) {
            this();
        }

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosInputStreamResponse aosInputStreamResponse = (AosInputStreamResponse) aosResponse;
            a a2 = HttpNetworkImpl.this.a(aosInputStreamResponse.getAosRequest());
            if (a2 != null) {
                if (bpv.a(3)) {
                    StringBuilder sb = new StringBuilder("send async success, result code: ");
                    sb.append(aosInputStreamResponse.getStatusCode());
                    sb.append(", wormhole url: ");
                    sb.append(a2.c.getUrl());
                    bpv.b("Wormhole", sb.toString());
                }
                if (!(a2.e == null || a2.c == null)) {
                    synchronized (a2.c) {
                        HttpNetworkImpl.a(a2, aosInputStreamResponse);
                    }
                }
                HttpNetworkImpl.a(HttpNetworkImpl.this, a2);
            }
        }

        public final void a(AosRequest aosRequest, long j, long j2) {
            a a2 = HttpNetworkImpl.this.a(aosRequest);
            if (a2 != null && a2.e != null && a2.c != null) {
                synchronized (a2.c) {
                    HttpNetworkImpl.a(a2, a2.b, j, j2);
                }
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            a a2 = HttpNetworkImpl.this.a(aosRequest);
            if (a2 != null) {
                if (bpv.a(3)) {
                    StringBuilder sb = new StringBuilder("send async error, error code: ");
                    sb.append(aosResponseException.errorCode);
                    sb.append("， msg: ");
                    sb.append(aosResponseException.getLocalizedMessage());
                    sb.append(", wormhole url: ");
                    sb.append(a2.c.getUrl());
                    bpv.b("Wormhole", sb.toString());
                }
                if (!(a2.e == null || a2.c == null)) {
                    synchronized (a2.c) {
                        HttpResponse a3 = HttpNetworkImpl.b(a2.b, a2.d);
                        if (!(aosResponseException == null || aosResponseException.response == null || aosResponseException.isCallbackError)) {
                            Map<String, List<String>> headers = aosResponseException.response.getHeaders();
                            if (headers != null && headers.size() > 0) {
                                HttpNetworkImpl.b(a2, a3, headers, aosRequest.getUrl());
                            }
                        }
                        a3.setErrorCode(HttpNetworkImpl.a(aosResponseException));
                        HttpNetworkImpl.d(a2, a3);
                    }
                }
                HttpNetworkImpl.a(HttpNetworkImpl.this, a2);
            }
        }
    }

    static class a {
        final int a;
        final int b;
        final AosRequest c;
        final HttpRequest d;
        final IHttpResponseCallback e;
        volatile boolean f = false;
        volatile boolean g = false;

        a(int i, int i2, HttpRequest httpRequest, AosRequest aosRequest, IHttpResponseCallback iHttpResponseCallback) {
            this.b = i;
            this.a = i2;
            this.d = httpRequest;
            this.c = aosRequest;
            this.e = iHttpResponseCallback;
        }
    }

    protected HttpNetworkImpl(Context context, ctg ctg) {
        this.a = context;
        this.c = ctg;
    }

    public int send(HttpRequest httpRequest, IHttpResponseCallback iHttpResponseCallback) {
        return send(httpRequest, iHttpResponseCallback, -11999);
    }

    public int send(HttpRequest httpRequest, IHttpResponseCallback iHttpResponseCallback, int i) {
        String str;
        if (httpRequest == null) {
            throw new IllegalArgumentException("param request is null!");
        }
        int incrementAndGet = this.b.incrementAndGet();
        AosRequest a2 = a(httpRequest);
        a aVar = new a(incrementAndGet, httpRequest.getHttpBodyRecvType(), httpRequest, a2, iHttpResponseCallback);
        synchronized (this.d) {
            SparseArray sparseArray = this.d.get(i);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                this.d.put(i, sparseArray);
            }
            sparseArray.put(incrementAndGet, aVar);
        }
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("send async, wormhole url: ");
            sb.append(httpRequest.getUrl());
            sb.append("\naos url: ");
            sb.append(a2.getUrl());
            sb.append(", method: ");
            sb.append(httpRequest.getMethod());
            sb.append(", groupID: ");
            sb.append(i);
            sb.append(", requestID: ");
            sb.append(incrementAndGet);
            String sb2 = sb.toString();
            if (httpRequest.getBody() != null) {
                byte[] body = httpRequest.getBody();
                if (body == null) {
                    str = "null";
                } else {
                    str = new String(body);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("\nbody: ");
                sb3.append(str);
                sb2 = sb3.toString();
            }
            bpv.b("Wormhole", sb2);
        }
        in.a().a(a2, (AosResponseCallback<T>) this.e);
        return incrementAndGet;
    }

    public void cancel(int i) {
        if (bpv.a(3)) {
            bpv.b("Wormhole", "cancel wormhole request, request ID: ".concat(String.valueOf(i)));
        }
        a aVar = null;
        synchronized (this.d) {
            int size = this.d.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                SparseArray valueAt = this.d.valueAt(size);
                if (valueAt != null) {
                    aVar = (a) valueAt.get(i);
                    if (!(aVar == null || aVar.c == null)) {
                        aVar.g = true;
                        valueAt.remove(i);
                        if (valueAt.size() == 0) {
                            this.d.removeAt(size);
                        }
                    }
                }
                size--;
            }
        }
        if (aVar != null && aVar.c != null) {
            in.a().a(aVar.c);
            HttpResponse b2 = b(aVar.b, aVar.d);
            synchronized (aVar.c) {
                b(aVar, b2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        if (r6 < 0) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        r2 = (com.autonavi.minimap.bl.HttpNetworkImpl.a) r1.valueAt(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        if (r2 == null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        if (r2.c == null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        r2.g = true;
        defpackage.in.a().a(r2.c);
        r3 = b(r2.b, r2.d);
        r4 = r2.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        b(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        r6 = r6 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        r6 = r1.size() - 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancelGroup(int r6) {
        /*
            r5 = this;
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r0 = r5.d
            monitor-enter(r0)
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r1 = r5.d     // Catch:{ all -> 0x004b }
            java.lang.Object r1 = r1.get(r6)     // Catch:{ all -> 0x004b }
            android.util.SparseArray r1 = (android.util.SparseArray) r1     // Catch:{ all -> 0x004b }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x000f:
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r2 = r5.d     // Catch:{ all -> 0x004b }
            r2.remove(r6)     // Catch:{ all -> 0x004b }
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            int r6 = r1.size()
            r0 = 1
            int r6 = r6 - r0
        L_0x001b:
            if (r6 < 0) goto L_0x004a
            java.lang.Object r2 = r1.valueAt(r6)
            com.autonavi.minimap.bl.HttpNetworkImpl$a r2 = (com.autonavi.minimap.bl.HttpNetworkImpl.a) r2
            if (r2 == 0) goto L_0x0047
            com.amap.bundle.aosservice.request.AosRequest r3 = r2.c
            if (r3 == 0) goto L_0x0047
            r2.g = r0
            in r3 = defpackage.in.a()
            com.amap.bundle.aosservice.request.AosRequest r4 = r2.c
            r3.a(r4)
            int r3 = r2.b
            com.autonavi.minimap.bl.net.HttpRequest r4 = r2.d
            com.autonavi.minimap.bl.net.HttpResponse r3 = b(r3, r4)
            com.amap.bundle.aosservice.request.AosRequest r4 = r2.c
            monitor-enter(r4)
            b(r2, r3)     // Catch:{ all -> 0x0044 }
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            goto L_0x0047
        L_0x0044:
            r6 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            throw r6
        L_0x0047:
            int r6 = r6 + -1
            goto L_0x001b
        L_0x004a:
            return
        L_0x004b:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bl.HttpNetworkImpl.cancelGroup(int):void");
    }

    /* access modifiers changed from: protected */
    public void a(AosRequest aosRequest, HttpRequest httpRequest) {
        aosRequest.setWithoutSign(true);
        aosRequest.setCommonParamStrategy(-1);
        aosRequest.setEncryptStrategy(-1);
    }

    /* access modifiers changed from: protected */
    public void b(AosRequest aosRequest, HttpRequest httpRequest) {
        aosRequest.setUrl(httpRequest.getUrl());
        aosRequest.setTimeout(httpRequest.getTimeout());
        aosRequest.setRetryTimes(httpRequest.getRetryTimes());
        aosRequest.setPriority(httpRequest.getPriority());
        if (!b(httpRequest)) {
            aosRequest.addReqParams(httpRequest.getReqParams());
        }
        Map<String, String> headers = httpRequest.getHeaders();
        if (headers != null) {
            for (String next : headers.keySet()) {
                if (!ctb.a(next)) {
                    aosRequest.addHeader(next, headers.get(next));
                }
            }
        }
        if (ctb.a(httpRequest) && this.c != null) {
            aosRequest.addHeader(OfflineUtil.CDN_HEADER_MAC, this.c.a());
        }
    }

    /* access modifiers changed from: protected */
    public void a(AosPostRequest aosPostRequest, HttpRequest httpRequest) {
        aosPostRequest.setBody(httpRequest.getBody());
        int reqParamFormat = httpRequest.getReqParamFormat();
        if (reqParamFormat == 3) {
            reqParamFormat = 0;
        }
        aosPostRequest.setReqParamFormatStrategy(reqParamFormat);
        aosPostRequest.setContentCompression(httpRequest.isContentCompression());
    }

    /* access modifiers changed from: protected */
    public void a(AosMultipartRequest aosMultipartRequest, HttpRequest httpRequest) {
        if (b(httpRequest)) {
            for (Entry next : httpRequest.getReqParams().entrySet()) {
                aosMultipartRequest.a((String) next.getKey(), (String) next.getValue());
            }
        }
        if (c(httpRequest)) {
            Map<String, String> uploadFiles = httpRequest.getUploadFiles();
            for (String next2 : uploadFiles.keySet()) {
                aosMultipartRequest.a(uploadFiles.get(next2), new File(next2));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(AosFileUploadRequest aosFileUploadRequest, HttpRequest httpRequest) {
        aosFileUploadRequest.a(new File(httpRequest.getUploadFiles().keySet().iterator().next()));
    }

    private static boolean b(HttpRequest httpRequest) {
        return e(httpRequest) && httpRequest.getReqParamFormat() == 3;
    }

    private static boolean c(HttpRequest httpRequest) {
        return d(httpRequest) && httpRequest.getUploadFileFormat() == 1;
    }

    private static boolean d(HttpRequest httpRequest) {
        Map<String, String> uploadFiles = httpRequest.getUploadFiles();
        return uploadFiles != null && !uploadFiles.isEmpty();
    }

    private static boolean e(HttpRequest httpRequest) {
        Map<String, String> reqParams = httpRequest.getReqParams();
        return reqParams != null && !reqParams.isEmpty();
    }

    /* access modifiers changed from: private */
    public a a(AosRequest aosRequest) {
        synchronized (this.d) {
            for (int i = 0; i < this.d.size(); i++) {
                SparseArray valueAt = this.d.valueAt(i);
                if (valueAt != null) {
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        a aVar = (a) valueAt.valueAt(i2);
                        if (aVar != null && aVar.c == aosRequest) {
                            return aVar;
                        }
                    }
                    continue;
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static HttpResponse b(int i, HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setUrl(httpRequest.getUrl());
        httpResponse.setRequestID(i);
        httpResponse.setRequest(httpRequest);
        return httpResponse;
    }

    /* access modifiers changed from: private */
    public static void b(a aVar, HttpResponse httpResponse, Map<String, List<String>> map, String str) {
        if (map != null && map.size() > 0) {
            for (String next : map.keySet()) {
                List list = map.get(next);
                if (list != null && list.size() > 0) {
                    httpResponse.addHeader(next, (String) list.get(list.size() - 1));
                }
            }
        }
        httpResponse.addHeader("ASL_SEND_URL", str);
        if (!(aVar == null || aVar.e == null || aVar.c == null || aVar.g)) {
            aVar.e.onReceiveHeader(httpResponse);
        }
    }

    private static void b(a aVar, HttpResponse httpResponse) {
        if (!(aVar == null || aVar.e == null || aVar.c == null || aVar.f)) {
            aVar.e.onCanceled(httpResponse);
        }
    }

    private static void c(a aVar, HttpResponse httpResponse) {
        if (!(aVar == null || aVar.e == null || aVar.c == null || aVar.g)) {
            aVar.f = true;
            aVar.e.onSuccess(httpResponse);
        }
    }

    /* access modifiers changed from: private */
    public static void d(a aVar, HttpResponse httpResponse) {
        if (!(aVar == null || aVar.e == null || aVar.c == null || aVar.g)) {
            aVar.f = true;
            aVar.e.onFailed(httpResponse);
        }
    }

    private static void a(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public AosRequest a(HttpRequest httpRequest) {
        AosRequest aosRequest;
        int method = httpRequest.getMethod();
        if (method == 0) {
            boolean z = false;
            if (httpRequest.getMethod() == 0 && (c(httpRequest) || b(httpRequest))) {
                AosMultipartRequest aosMultipartRequest = new AosMultipartRequest();
                a(aosMultipartRequest, httpRequest);
                aosRequest = aosMultipartRequest;
            } else {
                if (httpRequest.getMethod() == 0 && d(httpRequest) && httpRequest.getUploadFileFormat() == 0) {
                    z = true;
                }
                if (z) {
                    AosFileUploadRequest aosFileUploadRequest = new AosFileUploadRequest();
                    a(aosFileUploadRequest, httpRequest);
                    aosRequest = aosFileUploadRequest;
                } else {
                    AosPostRequest aosPostRequest = new AosPostRequest();
                    a(aosPostRequest, httpRequest);
                    aosRequest = aosPostRequest;
                }
            }
        } else if (method != 2) {
            aosRequest = new AosGetRequest();
        } else {
            aosRequest = new AosHeadRequest();
        }
        b(aosRequest, httpRequest);
        a(aosRequest, httpRequest);
        return aosRequest;
    }

    static /* synthetic */ void a(a aVar, int i, long j, long j2) {
        if (!(aVar == null || aVar.e == null || aVar.c == null || !(aVar.e instanceof IHttpUploadCallback) || aVar.g)) {
            ((IHttpUploadCallback) aVar.e).onProgress(i, "", j2, j);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x009c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.autonavi.minimap.bl.HttpNetworkImpl.a r10, com.amap.bundle.aosservice.response.AosInputStreamResponse r11) {
        /*
            int r0 = r10.b
            com.autonavi.minimap.bl.net.HttpRequest r1 = r10.d
            com.autonavi.minimap.bl.net.HttpResponse r0 = b(r0, r1)
            int r1 = r11.getStatusCode()
            r0.setStatusCode(r1)
            java.util.Map r1 = r11.getHeaders()
            bph r2 = r11.getRequest()
            java.lang.String r2 = r2.getUrl()
            b(r10, r0, r1, r2)
            com.amap.bundle.aosservice.request.AosRequest r1 = r10.c
            int r1 = r1.getMethod()
            r2 = 2
            if (r1 != r2) goto L_0x002b
            c(r10, r0)
            return
        L_0x002b:
            java.io.InputStream r1 = r11.getBodyInputStream()
            if (r1 != 0) goto L_0x0035
            c(r10, r0)
            return
        L_0x0035:
            int r2 = r10.a
            r3 = 1
            r4 = 4
            r5 = 0
            r6 = 0
            switch(r2) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x0046;
                default: goto L_0x003e;
            }
        L_0x003e:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "error body recvtype!"
            r10.<init>(r11)
            throw r10
        L_0x0046:
            cte r2 = new cte
            r2.<init>(r1)
            ctd r1 = new ctd
            r1.<init>()
        L_0x0050:
            boolean r7 = r10.g     // Catch:{ Exception -> 0x008a }
            if (r7 != 0) goto L_0x0083
            com.autonavi.minimap.bl.net.IHttpBuffer r7 = r1.a()     // Catch:{ Exception -> 0x008a }
            boolean r8 = r7 instanceof defpackage.ctd.a     // Catch:{ Exception -> 0x008a }
            if (r8 == 0) goto L_0x0064
            r8 = r7
            ctd$a r8 = (defpackage.ctd.a) r8     // Catch:{ Exception -> 0x008a }
            java.nio.ByteBuffer r8 = r8.a()     // Catch:{ Exception -> 0x008a }
            goto L_0x0065
        L_0x0064:
            r8 = r6
        L_0x0065:
            int r8 = r2.read(r8)     // Catch:{ Exception -> 0x008a }
            if (r8 < 0) goto L_0x0083
            r0.setBody(r7)     // Catch:{ Exception -> 0x008a }
            if (r10 == 0) goto L_0x0050
            com.autonavi.minimap.bl.net.IHttpResponseCallback r7 = r10.e     // Catch:{ Exception -> 0x008a }
            if (r7 == 0) goto L_0x0050
            com.amap.bundle.aosservice.request.AosRequest r7 = r10.c     // Catch:{ Exception -> 0x008a }
            if (r7 != 0) goto L_0x0079
            goto L_0x0050
        L_0x0079:
            boolean r7 = r10.g     // Catch:{ Exception -> 0x008a }
            if (r7 != 0) goto L_0x0050
            com.autonavi.minimap.bl.net.IHttpResponseCallback r7 = r10.e     // Catch:{ Exception -> 0x008a }
            r7.onReceiveBody(r0)     // Catch:{ Exception -> 0x008a }
            goto L_0x0050
        L_0x0083:
            a(r2)
            r3 = 0
            goto L_0x009a
        L_0x0088:
            r10 = move-exception
            goto L_0x00a3
        L_0x008a:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x0088 }
            r0.setBody(r6)     // Catch:{ all -> 0x0088 }
            r0.setErrorCode(r4)     // Catch:{ all -> 0x0088 }
            d(r10, r0)     // Catch:{ all -> 0x0088 }
            a(r2)
        L_0x009a:
            if (r3 != 0) goto L_0x00f8
            r0.setBody(r6)
            c(r10, r0)
            goto L_0x00f8
        L_0x00a3:
            a(r2)
            throw r10
        L_0x00a7:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream
            r2.<init>(r1)
            r1 = 65536(0x10000, float:9.18355E-41)
            byte[] r7 = new byte[r1]
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream
            r8.<init>(r1)
        L_0x00b5:
            int r1 = r2.read(r7)     // Catch:{ Exception -> 0x00cd }
            if (r1 <= 0) goto L_0x00c3
            boolean r9 = r10.g     // Catch:{ Exception -> 0x00cd }
            if (r9 != 0) goto L_0x00c3
            r8.write(r7, r5, r1)     // Catch:{ Exception -> 0x00cd }
            goto L_0x00b5
        L_0x00c3:
            r8.flush()     // Catch:{ Exception -> 0x00cd }
            a(r2)
            r3 = 0
            goto L_0x00e0
        L_0x00cb:
            r10 = move-exception
            goto L_0x0111
        L_0x00cd:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x00cb }
            r0.setBody(r6)     // Catch:{ all -> 0x00cb }
            r0.setErrorCode(r4)     // Catch:{ all -> 0x00cb }
            d(r10, r0)     // Catch:{ all -> 0x00cb }
            a(r8)     // Catch:{ all -> 0x00cb }
            a(r2)
        L_0x00e0:
            if (r3 != 0) goto L_0x00f8
            boolean r1 = r10.g
            if (r1 != 0) goto L_0x00f8
            ctf r1 = new ctf
            byte[] r2 = r8.toByteArray()
            r1.<init>(r2)
            r0.setBody(r1)
            c(r10, r0)
            a(r8)
        L_0x00f8:
            bph r10 = r11.getRequest()
            if (r10 == 0) goto L_0x0110
            bpp r0 = r10.requestStatistics
            long r0 = r0.o
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0110
            bpp r10 = r10.requestStatistics
            long r0 = r11.getContentLength()
            r10.o = r0
        L_0x0110:
            return
        L_0x0111:
            a(r2)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bl.HttpNetworkImpl.a(com.autonavi.minimap.bl.HttpNetworkImpl$a, com.amap.bundle.aosservice.response.AosInputStreamResponse):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0033, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.autonavi.minimap.bl.HttpNetworkImpl r6, com.autonavi.minimap.bl.HttpNetworkImpl.a r7) {
        /*
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r0 = r6.d
            monitor-enter(r0)
            r1 = 0
            r2 = 0
        L_0x0005:
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r3 = r6.d     // Catch:{ all -> 0x003c }
            int r3 = r3.size()     // Catch:{ all -> 0x003c }
            if (r2 >= r3) goto L_0x003a
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r3 = r6.d     // Catch:{ all -> 0x003c }
            java.lang.Object r3 = r3.valueAt(r2)     // Catch:{ all -> 0x003c }
            android.util.SparseArray r3 = (android.util.SparseArray) r3     // Catch:{ all -> 0x003c }
            if (r3 == 0) goto L_0x0037
            r4 = 0
        L_0x0018:
            int r5 = r3.size()     // Catch:{ all -> 0x003c }
            if (r4 >= r5) goto L_0x0037
            java.lang.Object r5 = r3.valueAt(r4)     // Catch:{ all -> 0x003c }
            if (r5 != r7) goto L_0x0034
            r3.removeAt(r4)     // Catch:{ all -> 0x003c }
            int r7 = r3.size()     // Catch:{ all -> 0x003c }
            if (r7 != 0) goto L_0x0032
            android.util.SparseArray<android.util.SparseArray<com.autonavi.minimap.bl.HttpNetworkImpl$a>> r6 = r6.d     // Catch:{ all -> 0x003c }
            r6.removeAt(r2)     // Catch:{ all -> 0x003c }
        L_0x0032:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x0034:
            int r4 = r4 + 1
            goto L_0x0018
        L_0x0037:
            int r2 = r2 + 1
            goto L_0x0005
        L_0x003a:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x003c:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bl.HttpNetworkImpl.a(com.autonavi.minimap.bl.HttpNetworkImpl, com.autonavi.minimap.bl.HttpNetworkImpl$a):void");
    }

    static /* synthetic */ int a(AosResponseException aosResponseException) {
        int i = aosResponseException.errorCode;
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 5;
            case 3:
                return 3;
            default:
                switch (i) {
                    case 11:
                    case 12:
                        return 2;
                    default:
                        return 6;
                }
        }
    }
}
