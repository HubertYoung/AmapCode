package com.sina.weibo.sdk.network.impl;

import android.content.Context;
import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.IRequestParam.RequestType;
import com.sina.weibo.sdk.network.IRequestParam.ValuePart;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestParam implements IRequestParam {
    public static final String KEY_PARAM_BODY_BYTE_ARRAY = "body_byte_array";
    private Context appContext;
    private Map<String, byte[]> byteArrays = new HashMap();
    private boolean defaultHost;
    private Bundle extraBundle = new Bundle();
    private Map<String, ValuePart<File>> files = new HashMap();
    private boolean gZip = false;
    private Bundle getBundle = new Bundle();
    private Bundle headerBundle = new Bundle();
    private ArrayList<IRequestIntercept> interceptList = new ArrayList<>();
    private HashMap<String, Object> interceptResult;
    private boolean needIntercept = true;
    private Bundle postBundle = new Bundle();
    private int requestTimeout = HttpConstants.CONNECTION_TIME_OUT;
    private RequestType requestType;
    private int responseTimeout = HttpConstants.CONNECTION_TIME_OUT;
    private String shortUrl;

    public static final class Builder {
        Context appContext;
        /* access modifiers changed from: private */
        public Map<String, byte[]> byteArrays = new HashMap();
        boolean defaultHost = true;
        Bundle extraBundle = new Bundle();
        /* access modifiers changed from: private */
        public Map<String, ValuePart<File>> files = new HashMap();
        boolean gZip = false;
        Bundle getBundle = new Bundle();
        Bundle headerBundle = new Bundle();
        ArrayList<IRequestIntercept> interceptList = new ArrayList<>();
        boolean needIntercept = true;
        Bundle postBundle = new Bundle();
        /* access modifiers changed from: private */
        public int requestTimeout = HttpConstants.CONNECTION_TIME_OUT;
        /* access modifiers changed from: private */
        public int responseTimeout = HttpConstants.CONNECTION_TIME_OUT;
        String shortUrl;
        RequestType type = RequestType.POST;

        public Builder(Context context) {
            this.appContext = context;
        }

        public final void setShortUrl(String str) {
            this.shortUrl = str;
        }

        public final void addPostParam(String str, String str2) {
            this.postBundle.putString(str, str2);
        }

        public final void addPostParam(String str, int i) {
            this.postBundle.putInt(str, i);
        }

        public final void addPostParam(String str, long j) {
            this.postBundle.putLong(str, j);
        }

        public final void addPostParam(Bundle bundle) {
            this.postBundle.putAll(bundle);
        }

        public final void addIntercept(IRequestIntercept iRequestIntercept) {
            this.interceptList.add(iRequestIntercept);
        }

        public final void addGetParam(String str, String str2) {
            this.getBundle.putString(str, str2);
        }

        public final void addGetParam(String str, int i) {
            this.getBundle.putInt(str, i);
        }

        public final void addGetParam(String str, long j) {
            this.getBundle.putLong(str, j);
        }

        public final void addGetParam(Bundle bundle) {
            this.getBundle.putAll(bundle);
        }

        public final void addExtParam(String str, String str2) {
            this.extraBundle.putString(str, str2);
        }

        public final void addExtParam(String str, int i) {
            this.extraBundle.putInt(str, i);
        }

        public final void setgZip(boolean z) {
            this.gZip = z;
        }

        public final void addExtParam(String str, long j) {
            this.extraBundle.putLong(str, j);
        }

        public final void addExtParam(Bundle bundle) {
            this.extraBundle.putAll(bundle);
        }

        public final void addBodyParam(byte[] bArr) {
            this.postBundle.putByteArray(RequestParam.KEY_PARAM_BODY_BYTE_ARRAY, bArr);
        }

        public final void addHeader(String str, String str2) {
            this.headerBundle.putString(str, str2);
        }

        public final void setRequestTimeout(int i) {
            this.requestTimeout = i;
        }

        public final void setResponseTimeout(int i) {
            this.responseTimeout = i;
        }

        public final void setNeedIntercept(boolean z) {
            this.needIntercept = z;
        }

        public final Builder addBodyParam(String str, File file, String str2) {
            ValuePart valuePart = new ValuePart();
            valuePart.value = file;
            valuePart.mimeType = str2;
            this.files.put(str, valuePart);
            return this;
        }

        public final Builder addBodyParam(String str, byte[] bArr) {
            this.byteArrays.put(str, bArr);
            return this;
        }

        public final void setRequestType(RequestType requestType) {
            this.type = requestType;
        }

        public final void defaultHostEnable(boolean z) {
            this.defaultHost = z;
        }

        public final RequestParam build() {
            return new RequestParam(this);
        }
    }

    public boolean needGzip() {
        return false;
    }

    public RequestParam(Builder builder) {
        this.shortUrl = builder.shortUrl;
        this.getBundle.putAll(builder.getBundle);
        this.postBundle.putAll(builder.postBundle);
        this.requestType = builder.type;
        this.headerBundle.putAll(builder.headerBundle);
        this.extraBundle.putAll(builder.extraBundle);
        this.defaultHost = builder.defaultHost;
        this.files.putAll(builder.files);
        this.byteArrays.putAll(builder.byteArrays);
        this.needIntercept = builder.needIntercept;
        this.appContext = builder.appContext;
        this.interceptResult = new HashMap<>();
        this.interceptList = builder.interceptList;
        this.gZip = builder.gZip;
        this.requestTimeout = builder.requestTimeout;
        this.responseTimeout = builder.responseTimeout;
    }

    public int getRequestTimeout() {
        return this.requestTimeout;
    }

    public int getResponseTimeout() {
        return this.responseTimeout;
    }

    public ArrayList<IRequestIntercept> getIntercept() {
        return this.interceptList;
    }

    public void setUrl(String str) {
        this.shortUrl = str;
    }

    public boolean needIntercept() {
        return this.needIntercept;
    }

    public void addInterceptResult(String str, Object obj) {
        this.interceptResult.put(str, obj);
    }

    public Object getInterceptResult(String str) {
        return this.interceptResult.get(str);
    }

    public Bundle getExtraBundle() {
        return this.extraBundle;
    }

    public Context getContext() {
        return this.appContext;
    }

    public String getUrl() {
        return this.shortUrl;
    }

    public Bundle getGetBundle() {
        return this.getBundle;
    }

    public Bundle getPostBundle() {
        return this.postBundle;
    }

    public RequestType getMethod() {
        return this.requestType;
    }

    public Bundle getHeader() {
        return this.headerBundle;
    }

    public boolean useDefaultHost() {
        return this.defaultHost;
    }

    public Map<String, ValuePart<File>> files() {
        return this.files;
    }

    public Map<String, byte[]> byteArrays() {
        return this.byteArrays;
    }
}
