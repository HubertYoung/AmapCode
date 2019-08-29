package com.alipay.mobile.common.nbnet.biz.token;

import android.util.Pair;
import com.alipay.mobile.common.nbnet.biz.io.LengthInputStream;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConnectionEntity;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConntionManager;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConntionManagerFactory;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetReqConn;
import com.alipay.mobile.common.nbnet.biz.transport.Transport;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.ProtocolUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class TokenTransport implements Transport<NBNetTokenRequest, Pair> {
    private NBNetTokenRequest a;
    private NBNetConntionManager b = NBNetConntionManagerFactory.a();
    private NBNetConnectionEntity c;

    private void a(NBNetTokenRequest request) {
        this.a = request;
        URL requestUrl = this.a.c();
        NBNetReqConn reqConn = new NBNetReqConn();
        reqConn.a = requestUrl.getHost();
        reqConn.b = NBNetCommonUtil.a(requestUrl.getPort(), requestUrl.getProtocol());
        reqConn.e = 2;
        this.c = this.b.a(reqConn, new BasicNBNetContext());
        OutputStream outputStream = this.c.b();
        outputStream.write(this.a.a());
        outputStream.write(this.a.b().getBytes("UTF-8"));
        outputStream.flush();
    }

    public final long a() {
        a(new NBNetTokenRequest());
        return 0;
    }

    public final Pair<Map<String, String>, String> b() {
        try {
            if (this.c == null) {
                a();
            }
            InputStream inputStream = this.c.a();
            Map headers = ProtocolUtils.a(inputStream);
            int contentLength = NBNetCommonUtil.b(headers.get("Content-Length"));
            if (contentLength > 0) {
                inputStream = new LengthInputStream(inputStream, contentLength);
            }
            return new Pair<>(headers, ProtocolUtils.a(headers, inputStream));
        } finally {
            d();
        }
    }

    public final InputStream c() {
        return this.c.a();
    }

    public final boolean d() {
        if (this.c != null) {
            this.c.d();
        }
        return true;
    }

    public final boolean e() {
        if (this.c != null) {
            this.c.e();
        }
        return true;
    }
}
