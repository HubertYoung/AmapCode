package com.alipay.mobile.common.transport.h5;

import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

public class H5HttpUrlResponse extends HttpUrlResponse {
    private InputStream a;
    private StatusLine b;
    protected HttpResponse httpResponse;

    public H5HttpUrlResponse(HttpUrlHeader header, int code, String msg, InputStream inputStream) {
        super(header, code, msg, null);
        this.a = inputStream;
    }

    public InputStream getInputStream() {
        return this.a;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.b = statusLine;
    }

    public StatusLine getStatusLine() {
        return this.b;
    }

    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse2) {
        this.httpResponse = httpResponse2;
    }

    public void release() {
        if (this.httpResponse == null) {
            LogCatUtil.warn((String) "H5HttpUrlResponse", (String) "httpResponse is null");
            return;
        }
        try {
            HttpEntity entity = this.httpResponse.getEntity();
            if (entity != null) {
                entity.consumeContent();
                LogCatUtil.info("H5HttpUrlResponse", "enter release()");
            }
        } catch (Throwable e) {
            LogCatUtil.warn("H5HttpUrlResponse", "release fail", e);
        }
    }
}
