package com.alipay.mobile.common.transport.multimedia;

import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import java.io.InputStream;

public class DjgHttpUrlResponse extends H5HttpUrlResponse {
    public DjgHttpUrlResponse(HttpUrlHeader header, int code, String msg, InputStream inputStream) {
        super(header, code, msg, inputStream);
    }
}
