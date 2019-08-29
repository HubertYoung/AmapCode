package com.alipay.mobile.common.transport.multimedia;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpForm;
import com.alipay.mobile.common.transport.utils.HttpClientUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpUriRequest;

public class DjgHttpUrlRequest extends H5HttpUrlRequest {
    public static final byte INNER_BIZ_TYPE_UNKNOWN = -1;
    public static final byte INNER_BIZ_TYPE_UPANDRECORD = 1;
    public static final byte INNER_BIZ_TYPE_UPINSECONDS = 2;
    public static final String OPERATION_TYPE = "django_http_request";
    protected Byte innerBizType = null;
    protected String upMediaType = "";

    public DjgHttpUrlRequest(String url) {
        super(url);
    }

    public DjgHttpUrlRequest(String url, byte[] reqData, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, reqData, headers, tags);
    }

    public DjgHttpUrlRequest(String url, HttpForm httpForm, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, httpForm, headers, tags);
    }

    public DjgHttpUrlRequest(String url, InputStream inputStream, int length, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, inputStream, length, headers, tags);
    }

    public DjgHttpUrlRequest(HttpUriRequest httpUriRequest) {
        super(httpUriRequest);
        try {
            initInnerBizType(httpUriRequest);
            initUpMediaType(httpUriRequest);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "DjgHttpUrlRequest", "ex:" + ex.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
        setUseHttpStdRetryModel(false);
        setTimeout(-1);
        this.linkType = 2;
    }

    public Byte getInnerBizType() {
        return this.innerBizType;
    }

    public String getUpMediaType() {
        return this.upMediaType;
    }

    /* access modifiers changed from: protected */
    public void initInnerBizType(HttpUriRequest httpUriRequest) {
        String upType = HttpClientUtils.removeParamter(httpUriRequest, "uploadType");
        try {
            if (!TextUtils.isEmpty(upType)) {
                this.innerBizType = Byte.valueOf(Byte.parseByte(upType));
            }
        } catch (Throwable e) {
            LogCatUtil.warn("DjgHttpUrlRequest", "parseByte error, upType:" + upType, e);
        }
        if (this.innerBizType == null) {
            String url = httpUriRequest.getURI().toString();
            if (!TextUtils.isEmpty(url) && url.contains("file/head")) {
                this.innerBizType = Byte.valueOf(2);
            }
            if (this.innerBizType != null) {
                return;
            }
            if (!(httpUriRequest instanceof HttpEntityEnclosingRequest)) {
                this.innerBizType = Byte.valueOf(-1);
                return;
            }
            String method = httpUriRequest.getMethod();
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity();
            if (entity != null && entity.getContentLength() == -1 && method.equalsIgnoreCase("POST")) {
                this.innerBizType = Byte.valueOf(1);
            }
            if (this.innerBizType == null) {
                this.innerBizType = Byte.valueOf(-1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initUpMediaType(HttpUriRequest httpUriRequest) {
        String upMediaType2 = HttpClientUtils.removeParamter(httpUriRequest, TransportConstants.KEY_UP_MEDIA_TYPE);
        if (!TextUtils.isEmpty(upMediaType2)) {
            this.upMediaType = upMediaType2;
        }
    }
}
