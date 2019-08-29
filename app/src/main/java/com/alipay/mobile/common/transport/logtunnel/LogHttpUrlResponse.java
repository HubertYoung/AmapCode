package com.alipay.mobile.common.transport.logtunnel;

import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlResponse;
import java.io.InputStream;

public class LogHttpUrlResponse extends DjgHttpUrlResponse {
    public LogHttpUrlResponse(HttpUrlHeader header, int code, String msg, InputStream inputStream) {
        super(header, code, msg, inputStream);
    }
}
