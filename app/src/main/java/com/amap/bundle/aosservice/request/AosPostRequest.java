package com.amap.bundle.aosservice.request;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

public class AosPostRequest extends AosRequest {
    protected byte[] mBody;
    protected int mCommonParamFormatStrategy = 0;
    protected String mContentType;
    protected String mEncryptBodyIN;
    protected boolean mIsBinaryBody = false;
    protected boolean mIsContentCompression = false;
    protected bpj mPostRequest;
    protected int mReqParamFormatStrategy = 0;

    public AosPostRequest() {
        setMethod(1);
    }

    public void setContentCompression(boolean z) {
        this.mIsContentCompression = z;
    }

    public boolean isContentCompression() {
        return this.mIsContentCompression;
    }

    public void setIsBinaryBody(boolean z) {
        this.mIsBinaryBody = z;
    }

    public boolean isBinaryBody() {
        return this.mIsBinaryBody;
    }

    public void setBody(byte[] bArr) {
        this.mBody = bArr;
    }

    public byte[] getBody() {
        return this.mBody;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public void setReqParamFormatStrategy(int i) {
        this.mReqParamFormatStrategy = i;
    }

    public int getReqParamFormatStrategy() {
        return this.mReqParamFormatStrategy;
    }

    public void setCommonParamFormatStrategy(int i) {
        this.mCommonParamFormatStrategy = i;
    }

    public int getCommonParamFormatStrategy() {
        return this.mCommonParamFormatStrategy;
    }

    public bph buildHttpRequest() {
        bph buildHttpRequest = super.buildHttpRequest();
        if (this.mIsBinaryBody) {
            StringBuilder sb = new StringBuilder();
            sb.append(buildHttpRequest.getUrl());
            sb.append("&is_bin=1");
            buildHttpRequest.setUrl(sb.toString());
        }
        return buildHttpRequest;
    }

    /* access modifiers changed from: protected */
    public bph createHttpRequest() {
        this.mPostRequest = new bpj();
        this.mPostRequest.setBody(this.mBody);
        this.mPostRequest.setContentType(this.mContentType);
        return this.mPostRequest;
    }

    /* access modifiers changed from: protected */
    public void processParams(jc jcVar, Map<String, String> map, Map<String, String> map2) {
        byte[] bArr;
        boolean z = true;
        if ("1".equalsIgnoreCase(jcVar.a("is_bin"))) {
            setIsBinaryBody(true);
        }
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        if (this.mPostRequest.getBody() != null || this.mCommonParamFormatStrategy == 1) {
            hashMap.putAll(map);
        } else {
            hashMap2.putAll(map);
        }
        if (this.mPostRequest.getBody() == null && this.mReqParamFormatStrategy != 1) {
            z = false;
        }
        if (z) {
            hashMap.putAll(map2);
        } else {
            hashMap2.putAll(map2);
        }
        if (!hashMap.isEmpty()) {
            for (Entry entry : hashMap.entrySet()) {
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    jcVar.a((String) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        if (!hashMap2.isEmpty()) {
            String paramsToString = paramsToString(hashMap2);
            is a = ip.a();
            if (this.mEncryptStrategy == 2) {
                paramsToString = a.a(paramsToString);
            }
            this.mEncryptBodyIN = paramsToString;
            try {
                bArr = paramsToString.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                bArr = null;
            }
            this.mPostRequest.setBody(bArr);
            if (TextUtils.isEmpty(this.mContentType)) {
                this.mPostRequest.setContentType("application/x-www-form-urlencoded");
            }
        }
        if (this.mIsContentCompression) {
            this.mPostRequest.setBody(compressWithGzip(this.mPostRequest.getBody()));
        }
    }

    /* access modifiers changed from: protected */
    public void securityGuardSign(bph bph, String str) {
        securityGuardSign(bph, str, this.mEncryptBodyIN);
    }

    private byte[] compressWithGzip(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception unused) {
            return null;
        }
    }
}
