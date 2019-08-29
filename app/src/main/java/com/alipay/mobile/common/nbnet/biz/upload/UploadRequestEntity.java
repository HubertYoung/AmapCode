package com.alipay.mobile.common.nbnet.biz.upload;

import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.biz.io.NBNetChunkedOutputStream;
import com.alipay.mobile.common.nbnet.biz.io.UploadBytesInputStream;
import com.alipay.mobile.common.nbnet.biz.io.UploadFileInputStream;
import com.alipay.mobile.common.nbnet.biz.io.UploadInputStream;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.platform.DeviceInfoManagerFactory;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.token.TokenSignFactory;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.ProtocolUtils;
import com.alipay.mobile.common.nbnet.biz.util.URLConfigUtil;
import com.alipay.mobile.common.transport.http.HeaderMap;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;

public class UploadRequestEntity {
    private UploadInputStream a;
    private NBNetUploadRequest b;
    private HeaderMap<String, String> c = null;
    private HeaderMap<String, String> d;
    private byte[] e;
    private byte[] f;
    private URL g;
    private String h;
    private long i = -1;
    private boolean j;
    private UploadActionSession k;
    private ContentDescription l;
    private BasicNBNetContext m;
    private String n;
    private long o = 0;
    private boolean p = true;
    private long q = -1;
    private long r = -1;
    private int s = -1;
    private int t = 0;

    public UploadRequestEntity(NBNetUploadRequest nbNetUploadRequest) {
        this.b = nbNetUploadRequest;
    }

    private void m() {
        if (this.l.d()) {
            w();
        } else if (this.l.e()) {
            v();
        } else {
            throw new IllegalArgumentException("Unknown contentType in which NBNetUploadRequest");
        }
    }

    private UploadInputStream n() {
        if (this.a != null && !this.a.a()) {
            return this.a;
        }
        synchronized (this) {
            if (this.a == null || this.a.a()) {
                m();
                return this.a;
            }
            UploadInputStream uploadInputStream = this.a;
            return uploadInputStream;
        }
    }

    private URL o() {
        if (this.g != null) {
            return this.g;
        }
        synchronized (this) {
            if (this.g != null) {
                URL url = this.g;
                return url;
            }
            URL uploadServerURL = URLConfigUtil.b();
            this.g = new URL(uploadServerURL.getProtocol(), uploadServerURL.getHost(), NBNetCommonUtil.a(uploadServerURL.getPort(), uploadServerURL.getProtocol()), "/f/" + NBNetEnvUtils.h() + "/" + this.l.h());
            return this.g;
        }
    }

    public final boolean a() {
        return this.b.isRepeatable();
    }

    private String p() {
        if (!TextUtils.isEmpty(this.h)) {
            return this.h;
        }
        this.h = "PUT " + o().getPath() + ' ' + "HTTP/1.1";
        NBNetLogCat.a((String) "UploadRequestEntity", "request line: " + this.h);
        return this.h;
    }

    private HeaderMap<String, String> q() {
        if (this.c != null) {
            return this.c;
        }
        HeaderMap header = new HeaderMap();
        header.put("host", ProtocolUtils.a(o()));
        header.put("content-type", "application/offset+octet-stream");
        header.put(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
        header.put(H5AppHttpRequest.HEADER_UA, "android-nbnet");
        header.put("Transfer-Encoding", "chunked");
        String bizId = this.b.getBizId();
        if (!TextUtils.isEmpty(bizId)) {
            header.put("x-mmup-biztype", bizId);
        }
        if (!MiscUtils.isInAlipayClient(NBNetEnvUtils.a())) {
            header.put("x-mmup-backend", "afts");
        } else if (NBNetConfigUtil.s()) {
            String specifiedBackend = NBNetConfigUtil.t();
            if (!TextUtils.isEmpty(specifiedBackend)) {
                header.put("x-mmup-backend", specifiedBackend);
            }
        }
        header.put("x-arup-trace-id", this.n);
        String appKey = NBNetEnvUtils.c();
        header.put("x-arup-appkey", appKey);
        String utdid = NBNetEnvUtils.e();
        header.put("x-arup-device-id", utdid);
        String timestamp = String.valueOf(System.currentTimeMillis());
        header.put("x-arup-timestamp", timestamp);
        header.put("x-mmup-public", String.valueOf(b().isPublicScope()));
        header.put("x-mmup-prodname", DeviceInfoManagerFactory.a().a());
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append(appKey);
        signBuilder.append(timestamp);
        signBuilder.append(this.n);
        signBuilder.append(utdid);
        String beforeSignContent = signBuilder.toString();
        String afterSignContent = TokenSignFactory.a().a(beforeSignContent);
        if (!TextUtils.isEmpty(afterSignContent)) {
            header.put("x-arup-sign", afterSignContent);
        }
        ProtocolUtils.a(this.b.refHeaderMap(), header);
        NBNetLogCat.a((String) "UploadRequestEntity", "beforeSignContent: " + beforeSignContent + ", afterSignContent: " + afterSignContent);
        this.c = header;
        NBNetLogCat.a((String) "UploadRequestEntity", "[getHeaders] headers: " + this.c.toString());
        return this.c;
    }

    private HeaderMap<String, String> r() {
        String range;
        if (this.d != null) {
            return this.d;
        }
        HeaderMap headers = new HeaderMap();
        headers.put("x-arup-file-md5", this.l.a());
        String fileLen = String.valueOf(this.l.b());
        headers.put("x-arup-file-length", fileLen);
        String fileNameExt = this.l.g();
        if (!TextUtils.isEmpty(fileNameExt)) {
            headers.put("x-mmup-file-ext", fileNameExt);
        }
        if (this.k.c()) {
            range = "0," + fileLen;
        } else if (this.k.d()) {
            range = fileLen + ",0";
        } else {
            Pair offset = this.k.b;
            range = offset.first + "," + offset.second;
        }
        headers.put("x-arup-range", range);
        this.d = headers;
        NBNetLogCat.a((String) "UploadRequestEntity", "getBodyHeaders: " + this.d.toString());
        return headers;
    }

    public final void a(OutputStream outstream) {
        if (!this.j) {
            outstream.write(t());
            b(t().length);
            b(outstream);
        }
    }

    private void b(OutputStream outstream) {
        NBNetChunkedOutputStream nbNetChunkedOutputStream = new NBNetChunkedOutputStream(outstream, 0);
        try {
            nbNetChunkedOutputStream.write(s());
            b(s().length);
            if (this.q == -1) {
                this.q = System.currentTimeMillis();
            }
            if (this.k.d()) {
                nbNetChunkedOutputStream.close();
                return;
            }
            nbNetChunkedOutputStream.flush();
            c(nbNetChunkedOutputStream);
            nbNetChunkedOutputStream.flush();
            nbNetChunkedOutputStream.close();
            b((int) x());
        } finally {
            nbNetChunkedOutputStream.flush();
            nbNetChunkedOutputStream.close();
            b((int) x());
        }
    }

    private byte[] s() {
        if (this.e != null) {
            return this.e;
        }
        StringBuilder dividLineBuilder = new StringBuilder();
        dividLineBuilder.append("--").append(this.l.h()).append("--\r\n");
        String divideLineString = dividLineBuilder.toString();
        NBNetLogCat.a((String) "UploadRequestEntity", "divideLine: " + divideLineString);
        byte[] dividLineBytes = divideLineString.getBytes("UTF-8");
        byte[] bodyHeaderBytes = ProtocolUtils.a((Map<String, String>) r());
        ByteBuffer byteBuffer = ByteBuffer.allocate(dividLineBytes.length + bodyHeaderBytes.length);
        byteBuffer.put(dividLineBytes);
        byteBuffer.put(bodyHeaderBytes);
        this.e = byteBuffer.array();
        NBNetLogCat.a((String) "UploadRequestEntity", "bodyHeadersBytes length : " + this.e.length);
        return this.e;
    }

    private byte[] t() {
        if (this.f != null) {
            return this.f;
        }
        this.f = ProtocolUtils.a((Map<String, String>) q(), p());
        NBNetLogCat.a((String) "UploadRequestEntity", "getHeadersBytes length : " + this.f.length);
        return this.f;
    }

    public final NBNetUploadRequest b() {
        return this.b;
    }

    public final void c() {
        this.j = true;
        NBNetLogCat.a((String) "UploadRequestEntity", (String) "aborted");
        UploadInputStream uploadInputStream = n();
        if (!uploadInputStream.a()) {
            try {
                uploadInputStream.close();
            } catch (Throwable e2) {
                NBNetLogCat.d("UploadRequestEntity", "UploadInputStream closed, " + e2.toString());
            }
        }
    }

    public final UploadActionSession d() {
        return this.k;
    }

    public final void a(UploadActionSession uploadActionSession) {
        this.k = uploadActionSession;
    }

    public final void a(BasicNBNetContext nbNetContext) {
        this.m = nbNetContext;
    }

    public final ContentDescription e() {
        return this.l;
    }

    public final void a(ContentDescription contentDescription) {
        this.l = contentDescription;
    }

    public final long f() {
        String str;
        String str2;
        try {
            if (this.s != -1) {
                return (long) this.s;
            } else if (!a()) {
                this.s = 0;
                NBNetLogCat.a((String) "UploadRequestEntity", "transferContentLength: " + this.s);
                return 0;
            } else if (this.k.c()) {
                this.s = (int) this.l.b();
                long j2 = (long) this.s;
                NBNetLogCat.a((String) "UploadRequestEntity", "transferContentLength: " + this.s);
                return j2;
            } else if (this.k.d()) {
                this.s = 0;
                NBNetLogCat.a((String) "UploadRequestEntity", "transferContentLength: " + this.s);
                return 0;
            } else if (this.k.e()) {
                this.s = ((Integer) this.k.b.second).intValue();
                long j3 = (long) this.s;
                NBNetLogCat.a((String) "UploadRequestEntity", "transferContentLength: " + this.s);
                return j3;
            } else {
                this.s = 0;
                NBNetLogCat.a((String) "UploadRequestEntity", "transferContentLength: " + this.s);
                return 0;
            }
        } finally {
            str = "UploadRequestEntity";
            str2 = "transferContentLength: ";
            NBNetLogCat.a(str, this.s);
        }
    }

    public final void g() {
        if (!a()) {
            u();
        } else if (!this.k.d() && x() > 0) {
            if (x() >= f()) {
                u();
            } else if (this.k.e()) {
                Pair offset = this.k.b;
                int[] offsetAndCount = a((int) (((long) ((Integer) offset.first).intValue()) + x()), ((Integer) offset.second).intValue());
                if (offsetAndCount != null) {
                    int start = offsetAndCount[0];
                    int len = offsetAndCount[1];
                    this.k.b = new Pair<>(Integer.valueOf(start), Integer.valueOf(len));
                    NBNetLogCat.a((String) "UploadRequestEntity", "useAfterWriteAction1 md5=" + this.l.a() + ", status keep offset, start=" + start + ", len=" + len);
                }
            } else {
                int[] offsetAndCount2 = a((int) x(), (int) f());
                if (offsetAndCount2 != null) {
                    int start2 = offsetAndCount2[0];
                    int len2 = offsetAndCount2[1];
                    NBNetLogCat.a((String) "UploadRequestEntity", "useAfterWriteAction update upload action " + this.k.a + " -> 2");
                    Pair offset2 = new Pair(Integer.valueOf(start2), Integer.valueOf(len2));
                    this.k.b(offset2);
                    NBNetLogCat.a((String) "UploadRequestEntity", "useAfterWriteAction2 md5=" + this.l.a() + ", status=offset, start=" + offset2.first + ", len=" + offset2.second);
                }
            }
        }
    }

    private void u() {
        NBNetLogCat.a((String) "UploadRequestEntity", "useAfterWriteAction update upload action " + this.k.a + " -> 1");
        NBNetLogCat.a((String) "UploadRequestEntity", "useAfterWriteAction status=ask, md5=" + this.l.a());
        this.k.h();
    }

    private void v() {
        try {
            if (this.k.e()) {
                Pair offset = this.k.b;
                this.a = new UploadFileInputStream(this.b.getFile(), (long) ((Integer) offset.first).intValue(), (long) ((Integer) offset.second).intValue());
                return;
            }
            this.a = new UploadFileInputStream(this.b.getFile());
        } catch (Throwable e2) {
            NBNetLogCat.b("UploadRequestEntity", "initFileInputStream", e2);
            throw new IllegalArgumentException("create UploadFileInputStream exception. file=" + this.b.getFile().toString(), e2);
        }
    }

    private void w() {
        if (this.k.e()) {
            Pair offset = this.k.b;
            this.a = new UploadBytesInputStream(this.b.getData(), ((Integer) offset.first).intValue(), ((Integer) offset.second).intValue());
            return;
        }
        this.a = new UploadBytesInputStream(this.b.getData());
    }

    private void c(OutputStream outstream) {
        if (!this.k.d() && !this.j) {
            byte[] buff = new byte[2048];
            while (!this.j) {
                int len = n().read(buff);
                if (len == -1) {
                    break;
                }
                long startTime = System.currentTimeMillis();
                outstream.write(buff, 0, len);
                NetworkQoeManagerFactory.a().a(startTime);
                a(len);
            }
            NBNetLogCat.a((String) "UploadRequestEntity", "writeFileContent. writedLength: " + this.o);
        }
    }

    private long x() {
        return this.o;
    }

    private long a(int len) {
        long j2 = this.o + ((long) len);
        this.o = j2;
        return j2;
    }

    public final boolean h() {
        return this.j;
    }

    public final boolean i() {
        return this.p;
    }

    public final void j() {
        this.p = false;
    }

    public final void a(String traceId) {
        this.n = traceId;
    }

    private int[] a(int offset, int count) {
        try {
            IOUtils.a((int) this.l.b(), offset, count);
            return new int[]{offset, count};
        } catch (Throwable e2) {
            NBNetLogCat.d("UploadRequestEntity", "checkOffsetAndCount. " + e2.toString());
            int[] offsetAndCount = IOUtils.b((int) this.l.b(), offset, count);
            if (offsetAndCount != null) {
                NBNetLogCat.a((String) "UploadRequestEntity", "fixed offset: [" + offsetAndCount[0] + "," + offsetAndCount[1] + "]");
                return offsetAndCount;
            }
            u();
            return null;
        }
    }

    public final long k() {
        return this.q;
    }

    public final long l() {
        return this.r;
    }

    public final void a(long waitTime) {
        this.r = waitTime;
    }

    private int b(int size) {
        int i2 = this.t + size;
        this.t = i2;
        return i2;
    }
}
