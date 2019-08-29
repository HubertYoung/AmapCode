package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import java.io.File;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class LegalHttpResponse extends BasicHttpResponse {
    public LegalHttpResponse(StatusLine statusline) {
        super(statusline);
    }

    public LegalHttpResponse(HttpResponse response, File replaceFile) {
        this(new BasicStatusLine(response.getProtocolVersion(), 200, "intercept illegal response OK"));
        setEntity(new LegalHttpEntity(replaceFile));
    }

    public LegalHttpResponse(HttpResponse response, byte[] replaceData) {
        this(new BasicStatusLine(response.getProtocolVersion(), 200, "intercept illegal response OK"));
        setEntity(new LegalHttpEntity(replaceData));
    }
}
