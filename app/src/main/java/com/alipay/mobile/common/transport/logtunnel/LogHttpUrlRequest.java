package com.alipay.mobile.common.transport.logtunnel;

import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import org.apache.http.client.methods.HttpUriRequest;

public class LogHttpUrlRequest extends DjgHttpUrlRequest {
    public static final String OPERATION_TYPE = "log_http_request";

    public LogHttpUrlRequest(HttpUriRequest httpUriRequest) {
        super(httpUriRequest);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
        setUseHttpStdRetryModel(false);
        setTimeout(-1);
        this.linkType = 2;
    }

    /* access modifiers changed from: protected */
    public void initUpMediaType(HttpUriRequest httpUriRequest) {
        this.innerBizType = Byte.valueOf(-1);
    }

    /* access modifiers changed from: protected */
    public void initInnerBizType(HttpUriRequest httpUriRequest) {
    }
}
