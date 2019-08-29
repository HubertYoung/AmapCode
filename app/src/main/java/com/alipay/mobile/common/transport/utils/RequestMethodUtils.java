package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transport.http.ZNetworkHttpEntityWrapper;
import com.alipay.mobile.common.transport.http.method.HttpGetExt;
import com.alipay.mobile.common.transport.http.method.HttpPostExt;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;

public final class RequestMethodUtils {
    public static final HttpUriRequest createHttpUriRequestByMethod(HttpEntity entity, String requestMethod, URI uri) {
        if (TextUtils.equals(requestMethod, RequestMethodConstants.PUT_METHOD)) {
            HttpPut httpPut = new HttpPut(uri);
            if (entity == null) {
                return httpPut;
            }
            httpPut.setEntity(new ZNetworkHttpEntityWrapper(entity));
            return httpPut;
        } else if (TextUtils.equals(requestMethod, "POST")) {
            HttpPost httpPost = new HttpPost(uri);
            if (entity != null) {
                httpPost.setEntity(new ZNetworkHttpEntityWrapper(entity));
            }
            return httpPost;
        } else if (TextUtils.equals(requestMethod, RequestMethodConstants.HEAD_METHOD)) {
            return new HttpHead(uri);
        } else {
            if (TextUtils.equals(requestMethod, RequestMethodConstants.DELETE_METHOD)) {
                return new HttpDelete(uri);
            }
            if (TextUtils.equals(requestMethod, RequestMethodConstants.OPTIONS_METHOD)) {
                return new HttpOptions(uri);
            }
            if (TextUtils.equals(requestMethod, RequestMethodConstants.TRACE_METHOD)) {
                return new HttpTrace(uri);
            }
            if (TextUtils.equals(requestMethod, "GET")) {
                return new HttpGet(uri);
            }
            if (entity == null) {
                return new HttpGetExt(uri, requestMethod);
            }
            HttpPostExt httpPostExt = new HttpPostExt(uri, requestMethod);
            httpPostExt.setEntity(new ZNetworkHttpEntityWrapper(entity));
            return httpPostExt;
        }
    }

    public static final String getMethodByHttpUriRequest(HttpRequest request) {
        if (request instanceof HttpPut) {
            return RequestMethodConstants.PUT_METHOD;
        }
        if (request instanceof HttpPost) {
            return "POST";
        }
        if (request instanceof HttpHead) {
            return RequestMethodConstants.HEAD_METHOD;
        }
        if (request instanceof HttpDelete) {
            return RequestMethodConstants.DELETE_METHOD;
        }
        if (request instanceof HttpOptions) {
            return RequestMethodConstants.OPTIONS_METHOD;
        }
        return "GET";
    }
}
