package com.amap.location.sdk.a;

import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.autonavi.core.network.inter.response.StringResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/* compiled from: StringResponseEx */
public class h extends StringResponse {
    public InputStream getBodyInputStream() {
        String header = getHeader(TransportConstants.KEY_X_CONTENT_ENCODING);
        if (header != null && header.contains("gzip")) {
            try {
                return new GZIPInputStream(super.getBodyInputStream());
            } catch (IOException unused) {
            }
        }
        return super.getBodyInputStream();
    }
}
