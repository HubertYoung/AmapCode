package com.alipay.mobile.common.nbnet.biz.transport;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.download.MMDPTransport;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConnectionEntity;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConntionManagerFactory;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetReqConn;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NBNetDownloadTransport extends MMDPTransport {
    private final NBNetContext d;
    private NBNetConnectionEntity e;
    private boolean f;

    private NBNetDownloadTransport(Route route, NBNetContext context) {
        super(route);
        this.f = false;
        this.d = context;
    }

    public NBNetDownloadTransport(Route route, boolean secure, NBNetContext context) {
        this(route, context);
        this.f = secure;
    }

    public final OutputStream a() {
        NBNetReqConn nbNetReqConn = new NBNetReqConn();
        nbNetReqConn.a = this.b.a();
        nbNetReqConn.b = this.b.b();
        nbNetReqConn.c = this.f;
        nbNetReqConn.d = true;
        this.e = NBNetConntionManagerFactory.a().a(nbNetReqConn, this.a != null ? this.a : this.d);
        return this.e.b();
    }

    public final InputStream c() {
        if (this.e != null) {
            return this.e.a();
        }
        throw new IOException("NetConnection is not establish");
    }

    public final boolean d() {
        if (this.e == null) {
            return false;
        }
        this.e.d();
        return true;
    }

    public final boolean e() {
        if (this.e == null) {
            return false;
        }
        this.e.e();
        return true;
    }
}
